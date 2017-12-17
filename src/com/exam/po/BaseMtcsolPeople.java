package com.exam.po;

import java.util.Date;

import com.xunj.core.CorePo;

/**
 * UnitInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class BaseMtcsolPeople extends CorePo implements java.io.Serializable {

	// Fields

	private Integer pkMtcsol;
	private String mtcsolName;
	private String mtcsolSex;
	private Integer mtcsolAge;
	private String mtcsolJob;
	private String mtcsolSchool;
	private Integer pkbaseApp;
	
	private String confidentiality;
	
	// Constructors
	/** default constructor */      
	public BaseMtcsolPeople() {
	}
	/** full constructor */

	public Integer getPkMtcsol() {
		return pkMtcsol;
	}

	public void setPkMtcsol(Integer pkMtcsol) {
		this.pkMtcsol = pkMtcsol;
	}

	public String getMtcsolName() {
		return mtcsolName;
	}

	public void setMtcsolName(String mtcsolName) {
		this.mtcsolName = mtcsolName;
	}

	public String getMtcsolSex() {
		return mtcsolSex;
	}

	public void setMtcsolSex(String mtcsolSex) {
		this.mtcsolSex = mtcsolSex;
	}

	public Integer getMtcsolAge() {
		return mtcsolAge;
	}

	public void setMtcsolAge(Integer mtcsolAge) {
		this.mtcsolAge = mtcsolAge;
	}

	public String getMtcsolJob() {
		return mtcsolJob;
	}

	public void setMtcsolJob(String mtcsolJob) {
		this.mtcsolJob = mtcsolJob;
	}

	public String getMtcsolSchool() {
		return mtcsolSchool;
	}

	public void setMtcsolSchool(String mtcsolSchool) {
		this.mtcsolSchool = mtcsolSchool;
	}

	public Integer getPkbaseApp() {
		return pkbaseApp;
	}

	public void setPkbaseApp(Integer pkbaseApp) {
		this.pkbaseApp = pkbaseApp;
	}
	public String getConfidentiality() {
		return confidentiality;
	}
	public void setConfidentiality(String confidentiality) {
		this.confidentiality = confidentiality;
	}

	

	// Property accessors
	
}