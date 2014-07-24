package com.huatek.unicorn.brandauth.model;

public class ApplyBaseInfo {

	/**
	 * 接口部门编号
	 */
	private String contactDeptId;
	
	/**
	 * 提交时间
	 */
	private String submitTime;
	
	/**
	 * 授权开始时间
	 */
	private String authStartDate;
	
	/**
	 * 授权结束时间
	 */
	private String authEndDate;
	
	/**
	 * 授权生效区域
	 */
	private String authRegion;

	public String getContactDeptId() {
		return contactDeptId;
	}

	public void setContactDeptId(String contactDeptId) {
		this.contactDeptId = contactDeptId;
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public String getAuthStartDate() {
		return authStartDate;
	}

	public void setAuthStartDate(String authStartDate) {
		this.authStartDate = authStartDate;
	}

	public String getAuthEndDate() {
		return authEndDate;
	}

	public void setAuthEndDate(String authEndDate) {
		this.authEndDate = authEndDate;
	}

	public String getAuthRegion() {
		return authRegion;
	}

	public void setAuthRegion(String authRegion) {
		this.authRegion = authRegion;
	}
}
