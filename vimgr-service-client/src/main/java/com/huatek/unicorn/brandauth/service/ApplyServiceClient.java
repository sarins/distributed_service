package com.huatek.unicorn.brandauth.service;

public interface ApplyServiceClient {

	/**
	 * 通过申请类型获取applyId
	 * 
	 * @param applyType
	 * @return applyId
	 */
	public String newApply(String applyType, String submitterId);
	
}
