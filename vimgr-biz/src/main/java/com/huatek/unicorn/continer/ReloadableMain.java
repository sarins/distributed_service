package com.huatek.unicorn.continer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang.StringUtils;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.alibaba.dubbo.container.Container;

public class ReloadableMain {

	private static final Logger logger = LoggerFactory
			.getLogger(ReloadableMain.class);

	private static final CoreThread CORE_THREAD = new CoreThread();

	private static final CmdExecutor CMD_EXECUTOR = new CmdExecutor();

	public static void main(String[] args) {

		// start command listener
		CMD_EXECUTOR.start();

		offerCmd(new Cmd(CMD_STARTUP, args));

		synchronized (ReloadableMain.class) {
			try {
				ReloadableMain.class.wait();
			} catch (Exception e) {
			}
		}
	}

	private static final Queue<Cmd> CMD_QUEUE = new ConcurrentLinkedQueue<Cmd>();

	private static final Object EXECUTE_CMD_LOCK = new Object();

	public static final String CMD_STARTUP = "__startup__";
	public static final String CMD_STOP = "__stop__";
	public static final String CMD_RESTART = "__restart__";

	static void offerCmd(Cmd cmd) {
		CMD_QUEUE.offer(cmd);
		synchronized (EXECUTE_CMD_LOCK) {
			try {
				EXECUTE_CMD_LOCK.notify();
			} catch (Exception e) {
			}
		}
	}

	static Cmd peekCmd() {
		return CMD_QUEUE.poll();
	}

	static void execute(Cmd cmd) {
		logger.info("Executing: " + cmd);
		switch (cmd.getCmd()) {
		case CMD_STARTUP:
			if (!CORE_THREAD.isRunning()) {
				CORE_THREAD.startCore(cmd.getArgs());
			}
			break;
		case CMD_STOP:
			if (CORE_THREAD.isRunning()) {
				CORE_THREAD.stopCore();
			}
			break;
		case CMD_RESTART:
			if (CORE_THREAD.isRunning()) {
				CORE_THREAD.stopCore();
				CORE_THREAD.startCore(cmd.getArgs());
			}
			break;
		default:

		}
	}

	// listening to execute command.
	static class CmdExecutor extends Thread {
		@Override
		public void run() {
			while (true) {
				Cmd cmd = peekCmd();
				if (null == cmd || StringUtils.isBlank(cmd.getCmd())) {
					synchronized (EXECUTE_CMD_LOCK) {
						try {
							EXECUTE_CMD_LOCK.wait();
						} catch (Exception e) {
						}
					}
				} else {
					execute(cmd);
				}
			}
		}
	}

	static class Cmd {
		public Cmd(String cmd, String[] args) {
			this.cmd = cmd;
			this.args = args;
		}

		private String cmd;

		private String[] args;

		public String getCmd() {
			return cmd;
		}

		public void setCmd(String cmd) {
			this.cmd = cmd;
		}

		public String[] getArgs() {
			return args;
		}

		public void setArgs(String[] args) {
			this.args = args;
		}
	}

	static class CoreThread extends Thread {

		private CoreThread() {
		}

		public static final Object __INNER_SWITCH__ = new Object();

		public static final String CONTAINER_KEY = "dubbo.container";

		public static final String SHUTDOWN_HOOK_KEY = "dubbo.shutdown.hook";

		private static final ExtensionLoader<Container> loader = ExtensionLoader
				.getExtensionLoader(Container.class);

		private static final Logger CORE_THREAD_LOGGER = LoggerFactory
				.getLogger(CoreThread.class);

		private volatile boolean isRun;

		private String[] args;

		public void stopCore() {
			synchronized (CoreThread.class) {
				CoreThread.class.notify();
			}
			synchronized (__INNER_SWITCH__) {
				try {
					__INNER_SWITCH__.wait();
				} catch (Exception e) {
				}
			}
		}

		public void startCore(String[] args) {
			this.args = args;
			this.start();
			synchronized (__INNER_SWITCH__) {
				try {
					__INNER_SWITCH__.wait();
				} catch (Exception e) {
				}
			}
		}

		public boolean isRunning() {
			return isRun;
		}

		public void run() {

			isRun = true;

			final List<Container> containers = new ArrayList<Container>();

			try {
				if (args == null || args.length == 0) {
					String config = ConfigUtils.getProperty(CONTAINER_KEY,
							loader.getDefaultExtensionName());
					args = Constants.COMMA_SPLIT_PATTERN.split(config);
				}

				for (int i = 0; i < args.length; i++) {
					containers.add(loader.getExtension(args[i]));
				}
				CORE_THREAD_LOGGER.info("Use container type("
						+ Arrays.toString(args) + ") to run dubbo serivce.");

				for (Container container : containers) {
					container.start();
					CORE_THREAD_LOGGER.info("Dubbo "
							+ container.getClass().getSimpleName()
							+ " started!");
				}
				System.out
						.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]")
								.format(new Date())
								+ " Dubbo service server started!");
			} catch (RuntimeException e) {
				e.printStackTrace();
				CORE_THREAD_LOGGER.error(e.getMessage(), e);
				// System.exit(1);
			}

			synchronized (__INNER_SWITCH__) {
				__INNER_SWITCH__.notify();
			}

			synchronized (CoreThread.class) {
				try {
					CoreThread.class.wait();
				} catch (Throwable e) {
				}
			}

			if ("true".equals(System.getProperty(SHUTDOWN_HOOK_KEY))) {
				for (Container container : containers) {
					try {
						container.stop();
						CORE_THREAD_LOGGER.info("Dubbo "
								+ container.getClass().getSimpleName()
								+ " stopped!");
					} catch (Throwable t) {
						CORE_THREAD_LOGGER.error(t.getMessage(), t);
					}
				}
			}

			isRun = false;
			synchronized (__INNER_SWITCH__) {
				__INNER_SWITCH__.notify();
			}
		}
	}
}
