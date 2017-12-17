package com.exam.po;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.xunj.core.CorePo;

/**
 * CertConfig entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CertConfig extends CorePo implements java.io.Serializable {

	// Fields

	private Integer certId;
	private String certName;
	private Date createdDate;
	private String createdBy;
	private Integer certPoint;
	private Set certConfigRules = new HashSet(0);
	private String level;
	// Constructors
	
	/** default constructor */
	public CertConfig() {
	}

	/** minimal constructor */
	public CertConfig(String certName) {
		this.certName = certName;
	}

	/** full constructor */
	public CertConfig(Integer certId, String certName, Date createdDate,
			String createdBy, Integer certPoint, Set certConfigRules) {
		super();
		this.certId = certId;
		this.certName = certName;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.certPoint = certPoint;
		this.certConfigRules = certConfigRules;
	}
	
	// Property accessors

	public Integer getCertId() {
		return this.certId;
	}

	public void setCertId(Integer certId) {
		this.certId = certId;
	}

	public String getCertName() {
		return this.certName;
	}

	public void setCertName(String certName) {
		this.certName = certName;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Set getCertConfigRules() {
		return this.certConfigRules;
	}

	public void setCertConfigRules(Set certConfigRules) {
		this.certConfigRules = certConfigRules;
	}

	public Integer getCertPoint() {
		return certPoint;
	}

	public void setCertPoint(Integer certPoint) {
		this.certPoint = certPoint;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	
}