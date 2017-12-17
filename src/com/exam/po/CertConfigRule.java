package com.exam.po;

import com.xunj.core.CorePo;

/**
 * CertConfigRule entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CertConfigRule extends CorePo implements java.io.Serializable {

	// Fields

	private Integer ruleId;
	private String certId;
	private Integer ruleNo;
	private String ruleContent;
	private Integer rulePoint;
	private String level;
	// Constructors

	/** default constructor */
	public CertConfigRule() {
	}

	/** full constructor */
	public CertConfigRule(String certId, Integer ruleNo,
			String ruleContent, Integer rulePoint) {
		this.certId = certId;
		this.ruleNo = ruleNo;
		this.ruleContent = ruleContent;
		this.rulePoint = rulePoint;
	}

	
	// Property accessors

	public CertConfigRule(Integer ruleNo, String ruleContent, Integer rulePoint,String level,String certId) {
		super();
		this.ruleNo = ruleNo;
		this.ruleContent = ruleContent;
		this.rulePoint = rulePoint;
		this.level = level;
		this.certId = certId;
	}

	public Integer getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public String getCertId() {
		return this.certId;
	}

	public void setCertId(String certId) {
		this.certId = certId;
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

	public Integer getRulePoint() {
		return this.rulePoint;
	}

	public void setRulePoint(Integer rulePoint) {
		this.rulePoint = rulePoint;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}