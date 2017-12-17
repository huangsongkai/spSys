package com.exam.po;

import javax.persistence.Transient;

import com.xunj.core.CorePo;

/**
 * CertTaskScore entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CertTaskScore extends CorePo implements java.io.Serializable {

	// Fields1

	private Integer scoreId;
	private String scoreRule;
	private Integer scoreNumber;
	private Integer certTaskId;
	private Integer certId;
	private Integer ruleId;
	private String imgId;
	private String people;
	@Transient 
	private String detailContent;
	// Constructors
	@Transient 
	public String getDetailContent() {
		return detailContent;
	}
	@Transient   
	public void setDetailContent(String detailContent) {
		this.detailContent = detailContent;
	}

	/** default constructor */
	public CertTaskScore() {
	}

	/** full constructor */
	public CertTaskScore(Integer scoreId, String scoreRule,
			Integer scoreNumber, Integer certTaskId,
			Integer certId ,Integer ruleId,String imgId,String detailContent) {
		super();
		this.scoreId = scoreId;
		this.scoreRule = scoreRule;
		this.scoreNumber = scoreNumber;
		this.certTaskId = certTaskId;
		this.certId = certId;
		this.ruleId = ruleId;
		this.imgId = imgId;
		this.detailContent = detailContent;
	}

	// Property accessors

	public Integer getScoreId() {
		return this.scoreId;
	}

	public void setScoreId(Integer scoreId) {
		this.scoreId = scoreId;
	}

	public String getScoreRule() {
		return this.scoreRule;
	}

	public void setScoreRule(String scoreRule) {
		this.scoreRule = scoreRule;
	}

	public Integer getScoreNumber() {
		return this.scoreNumber;
	}

	public void setScoreNumber(Integer scoreNumber) {
		this.scoreNumber = scoreNumber;
	}
	   
	public Integer getCertTaskId() {
		return this.certTaskId;
	}

	public void setCertTaskId(Integer certTaskId) {
		this.certTaskId = certTaskId;
	}

	public Integer getCertId() {
		return certId;
	}

	public void setCertId(Integer certId) {
		this.certId = certId;
	}

	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public String getImgId() {
		return imgId;
	}

	public void setImgId(String imgId) {
		this.imgId = imgId;
	}

	public String getPeople() {
		return people;
	}

	public void setPeople(String people) {
		this.people = people;
	}

	
}