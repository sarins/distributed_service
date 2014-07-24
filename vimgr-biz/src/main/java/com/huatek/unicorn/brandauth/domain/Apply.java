package com.huatek.unicorn.brandauth.domain;

import java.util.Date;

public class Apply {
	
	/**
	 * 申请id
	 */
	private String id;
	
	/**
	 * 源申请id
	 */
	private String sourceApplyId;
	
	/**
	 * 申请类型（新品牌、新类别、续约）
	 */
	private String applyType;
	
	/**
	 * 处理流程序列号
	 */
	private String processInstanceId;
	
	/**
	 * 申请序列号[呈现]
	 */
	private String applySerial;
	
	/**
	 * 品牌id
	 */
	private String brandId;
	
	/**
	 * 接口部门id
	 */
	private String contactDeptId;
	
	/**
	 * 申请提交时间
	 */
	private Date submitTime;
	
	/**
	 * 公司类型
	 */
	private String companyType;
	
	/**
	 * 授权时效开始时间
	 */
	private Date authStartTime;
	
	/**
	 * 授权时效结束时间
	 */
	private Date authEndTime;
	
	/**
	 * 授权生效地区
	 */
	private String authEffectRegion;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSourceApplyId() {
		return sourceApplyId;
	}

	public void setSourceApplyId(String sourceApplyId) {
		this.sourceApplyId = sourceApplyId;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public String getApplySerial() {
		return applySerial;
	}

	public void setApplySerial(String applySerial) {
		this.applySerial = applySerial;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	
	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getContactDeptId() {
		return contactDeptId;
	}

	public void setContactDeptId(String contactDeptId) {
		this.contactDeptId = contactDeptId;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public Date getAuthStartTime() {
		return authStartTime;
	}

	public void setAuthStartTime(Date authStartTime) {
		this.authStartTime = authStartTime;
	}

	public Date getAuthEndTime() {
		return authEndTime;
	}

	public void setAuthEndTime(Date authEndTime) {
		this.authEndTime = authEndTime;
	}

	public String getAuthEffectRegion() {
		return authEffectRegion;
	}

	public void setAuthEffectRegion(String authEffectRegion) {
		this.authEffectRegion = authEffectRegion;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
}
