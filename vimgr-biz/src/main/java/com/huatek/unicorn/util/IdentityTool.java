package com.huatek.unicorn.util;

import java.util.UUID;

public class IdentityTool {

	/**
	 * 快捷方法生成java.util.UUID
	 * @return java.util.UUID
	 */
	public static UUID uuid() {
		return UUID.randomUUID();
	}
	
	/**
	 * 快捷方法生成 uuid as java.lang.String
	 * @return java.util.UUID
	 */
	public static String uuidStr() {
		return uuid().toString();
	}
}
