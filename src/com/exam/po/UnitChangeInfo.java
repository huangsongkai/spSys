package com.exam.po;

import java.util.Date;

import com.xunj.core.CorePo;

/**
 * UnitInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UnitChangeInfo extends CorePo implements java.io.Serializable {

	// Fields

	private Integer pkChange; //Ö÷¼ü
	private Integer pkUnit; 
	private String changeOld; 
	private String changeNew; 
	private String changeType;
	private Date changeTime; 
	

	/** default constructor */
	public UnitChangeInfo() {
	}

	/** full constructor */
	public UnitChangeInfo(Integer pkUnit, String changeOld,
			String changeNew,String changeType, Date changeTime ) {
		this.pkUnit = pkUnit;
		this.changeOld = changeOld;
		this.changeNew = changeNew;
		this.changeType = changeType;
		this.changeTime = changeTime;
		
		
	}

	// Property accessors

	public Integer getPkUnit() {
		return this.pkUnit;
	}

	public void setPkUnit(Integer pkUnit) {
		this.pkUnit = pkUnit;
	}

	public Integer getPkChange() {
		return pkChange;
	}

	public void setPkChange(Integer pkChange) {
		this.pkChange = pkChange;
	}

	public String getChangeOld() {
		return changeOld;
	}

	public void setChangeOld(String changeOld) {
		this.changeOld = changeOld;
	}

	public String getChangeNew() {
		return changeNew;
	}

	public void setChangeNew(String changeNew) {
		this.changeNew = changeNew;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}


}