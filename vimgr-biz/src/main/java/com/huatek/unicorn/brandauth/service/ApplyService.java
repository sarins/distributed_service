package com.huatek.unicorn.brandauth.service;

import com.huatek.unicorn.brandauth.domain.Apply;

public interface ApplyService {

	/**
	 * 申请类型品牌授权
	 */
	public static final String APPLY_TYPE_BRANDAUTH = "brandAuthApplyProcess";

	/**
	 * 申请类型续约
	 */
	public static final String APPLY_TYPE_RENEWAL = "renewalAuthApplyProcess";

	/**
	 * 根据申请编号获取申请实例
	 * 
	 * @param applyId
	 * @return
	 */
	public Apply getApplyById(String applyId);
	
	/**
	 * 根据用户编号查询用户组别
	 * @param userId 用户编号
	 * @return 用户组别
	 */
	public String getUserGroup(String userId);
	
	/**
	 * 根据用户编号查询用户名称
	 * @param userId
	 * @return 用户名称
	 */
	public String getUserName(String userId);
}
