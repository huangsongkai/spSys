package com.exam.po;

import javax.persistence.Transient;

import com.xunj.core.CorePo;

/**
 * CertTaskScore entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CertTaskScoreDetail extends CorePo implements java.io.Serializable {

	// Fields

	private Integer ruleId;
	private Integer scoreDetailId;
	private Integer certTaskId;
	private String certPeopleId;
	// Constructors
	
	/** default constructor */
	public CertTaskScoreDetail() {
	}

	/** full constructor */
	public CertTaskScoreDetail(Integer ruleId, Integer scoreDetailId,String certPeopleId) {
		super();
		this.ruleId = ruleId;
		this.scoreDetailId=scoreDetailId;
		this.certPeopleId=certPeopleId;
		
	}

	// Property accessors

	

	public Integer getScoreDetailId() {
		return scoreDetailId;
	}

	public void setScoreDetailId(Integer scoreDetailId) {
		this.scoreDetailId = scoreDetailId;
	}

	public String getCertPeopleId() {
		return certPeopleId;
	}

	public void setCertPeopleId(String certPeopleId) {
		this.certPeopleId = certPeopleId;
	}
	
	public Integer getCertTaskId() {
		return certTaskId;
	}

	public void setCertTaskId(Integer certTaskId) {
		this.certTaskId = certTaskId;
	}

	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}



	
}