package com.exam.po;

import java.util.Date;

import com.xunj.core.CorePo;

/**
 * UnitInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class BaseSecretPeople extends CorePo implements java.io.Serializable {

	// Fields

	private Integer pkSecretPeople;
	private Integer pkbaseApp;
	private String name;
	private String dept;
	private String duty;
	// Constructors
	/** default constructor */      
	public BaseSecretPeople() {
	}
	/** full constructor */
	public Integer getPkSecretPeople() {
		return pkSecretPeople;
	}
	public void setPkSecretPeople(Integer pkSecretPeople) {
		this.pkSecretPeople = pkSecretPeople;
	}
	public Integer getPkbaseApp() {
		return pkbaseApp;
	}
	public void setPkbaseApp(Integer pkbaseApp) {
		this.pkbaseApp = pkbaseApp;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}

	
	// Property accessors
	
}