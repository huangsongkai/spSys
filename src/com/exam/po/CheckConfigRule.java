package com.exam.po;

import com.xunj.core.CorePo;

/**
 * CheckConfigRule entity. @author MyEclipse Persistence Tools
 */

public class CheckConfigRule extends CorePo implements java.io.Serializable {

	// Fields

	private Integer ruleId;
	private Integer checkId;
	private CheckConfig checkConfig;
	private Integer ruleNo;
	private String ruleContent;
	private String remark;

	// Constructors

	/** default constructor */
	public CheckConfigRule() {
	}

	/** full constructor */
	public CheckConfigRule(Integer checkId, Integer ruleNo,
			String ruleContent, String remark) {
		this.checkId = checkId;
		this.ruleNo = ruleNo;
		this.ruleContent = ruleContent;
		this.remark = remark;
	}

	// Property accessors

	public Integer getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public CheckConfig getCheckConfig() {
		return this.checkConfig;
	}

	public void setCheckConfig(CheckConfig checkConfig) {
		this.checkConfig = checkConfig;
	}

	public Integer getRuleNo() {
		return this.ruleNo;
	}

	public void setRuleNo(Integer ruleNo) {
		this.ruleNo = ruleNo;
	}

	public String getRuleContent() {
		return this.ruleContent;
	}

	public void setRuleContent(String ruleContent) {
		this.ruleContent = ruleContent;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getCheckId() {
		return checkId;
	}

	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}


}