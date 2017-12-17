package com.exam.po;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.xunj.core.CorePo;

/**
 * CheckConfig entity. @author MyEclipse Persistence Tools
 */

public class CheckConfig extends CorePo implements java.io.Serializable {

	// Fields

	private Integer checkId;
	private String checkName;
	private String checkUnit;
	private Date createdDate;
	private String createdBy;
	private String checkState;
	// Constructors

	public String getCheckState() {
		return checkState;
	}

	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}

	/** default constructor */
	public CheckConfig() {
	}

	/** minimal constructor */
	public CheckConfig(String checkName) {
		this.checkName = checkName;
	}


	// Property accessors

	public Integer getCheckId() {
		return this.checkId;
	}

	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}

	public String getCheckName() {
		return this.checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	public String getCheckUnit() {
		return this.checkUnit;
	}

	public void setCheckUnit(String checkUnit) {
		this.checkUnit = checkUnit;
	}

	public Date getCreatedDate() {
		return createdDate;
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

}