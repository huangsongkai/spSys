package com.exam.po;

import java.util.Date;

import com.xunj.core.CorePo;

/**
 * CheckTaskInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CheckTaskInfo extends CorePo implements java.io.Serializable {

	// Fields

	private Integer taskId;
	private Integer pkUnit;
	private String taskName;
	private String checkUnitName;
	private String checkUnitCategory;
	private String checkUnitBusiness;
	private String checkUnitDistrict;
	private String productionPermitRegistration;
	private String confidentialityLevelQualifications;
	private Date passedTime;
	private Double passedScore;
	private String advicePaper;
	private Integer confidentialityStaffNumber;
	private String checkGroup;
	private String checkUnitGroup;
	private String checkTaskName;
	private String checkResult;
	private String checkState;
	
	private UnitInfo unitInfo;
	private CheckConfig checkConfig;
	
	// Constructors


	public UnitInfo getUnitInfo() {
		return unitInfo;
	}

	public void setUnitInfo(UnitInfo unitInfo) {
		this.unitInfo = unitInfo;
	}

	public CheckTaskInfo(String taskName, String checkUnitName,
			String checkUnitCategory, String checkUnitBusiness,
			String checkUnitDistrict, String productionPermitRegistration,
			String confidentialityLevelQualifications, Date passedTime,
			Double passedScore, String advicePaper,
			Integer confidentialityStaffNumber, String checkGroup,
			String checkUnitGroup) {
		super();
		this.taskName = taskName;
		this.checkUnitName = checkUnitName;
		this.checkUnitCategory = checkUnitCategory;
		this.checkUnitBusiness = checkUnitBusiness;
		this.checkUnitDistrict = checkUnitDistrict;
		this.productionPermitRegistration = productionPermitRegistration;
		this.confidentialityLevelQualifications = confidentialityLevelQualifications;
		this.passedTime = passedTime;
		this.passedScore = passedScore;
		this.advicePaper = advicePaper;
		this.confidentialityStaffNumber = confidentialityStaffNumber;
		this.checkGroup = checkGroup;
		this.checkUnitGroup = checkUnitGroup;
	}

	/** default constructor */
	public CheckTaskInfo() {
	}

	/** full constructor */
	

	// Property accessors

	public Integer getTaskId() {
		return this.taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getCheckUnitName() {
		return this.checkUnitName;
	}

	public void setCheckUnitName(String checkUnitName) {
		this.checkUnitName = checkUnitName;
	}

	public String getCheckUnitCategory() {
		return this.checkUnitCategory;
	}

	public void setCheckUnitCategory(String checkUnitCategory) {
		this.checkUnitCategory = checkUnitCategory;
	}

	public String getCheckUnitBusiness() {
		return this.checkUnitBusiness;
	}

	public void setCheckUnitBusiness(String checkUnitBusiness) {
		this.checkUnitBusiness = checkUnitBusiness;
	}

	public String getCheckUnitDistrict() {
		return this.checkUnitDistrict;
	}

	public void setCheckUnitDistrict(String checkUnitDistrict) {
		this.checkUnitDistrict = checkUnitDistrict;
	}

	public String getProductionPermitRegistration() {
		return this.productionPermitRegistration;
	}

	public void setProductionPermitRegistration(
			String productionPermitRegistration) {
		this.productionPermitRegistration = productionPermitRegistration;
	}

	public String getConfidentialityLevelQualifications() {
		return this.confidentialityLevelQualifications;
	}

	public void setConfidentialityLevelQualifications(
			String confidentialityLevelQualifications) {
		this.confidentialityLevelQualifications = confidentialityLevelQualifications;
	}

	public Date getPassedTime() {
		return this.passedTime;
	}

	public void setPassedTime(Date passedTime) {
		this.passedTime = passedTime;
	}

	public Double getPassedScore() {
		return this.passedScore;
	}

	public void setPassedScore(Double passedScore) {
		this.passedScore = passedScore;
	}

	public String getAdvicePaper() {
		return this.advicePaper;
	}

	public void setAdvicePaper(String advicePaper) {
		this.advicePaper = advicePaper;
	}

	public Integer getConfidentialityStaffNumber() {
		return this.confidentialityStaffNumber;
	}

	public void setConfidentialityStaffNumber(Integer confidentialityStaffNumber) {
		this.confidentialityStaffNumber = confidentialityStaffNumber;
	}

	public String getCheckGroup() {
		return this.checkGroup;
	}

	public void setCheckGroup(String checkGroup) {
		this.checkGroup = checkGroup;
	}

	public String getCheckUnitGroup() {
		return checkUnitGroup;
	}

	public void setCheckUnitGroup(String checkUnitGroup) {
		this.checkUnitGroup = checkUnitGroup;
	}
	public CheckTaskInfo copyPo(){
		CheckTaskInfo copyPo=new CheckTaskInfo();
		copyPo.setCheckGroup(this.checkGroup);
		copyPo.setCheckUnitBusiness(this.checkUnitBusiness);
		copyPo.setCheckUnitCategory(this.checkUnitCategory);
		copyPo.setCheckUnitDistrict(this.checkUnitDistrict);
		copyPo.setCheckUnitGroup(this.checkUnitGroup);
		copyPo.setCheckUnitName(this.checkUnitName);
		copyPo.setConfidentialityLevelQualifications(this.confidentialityLevelQualifications);
		copyPo.setConfidentialityStaffNumber(this.confidentialityStaffNumber);
		copyPo.setPassedScore(this.passedScore);
		copyPo.setPassedTime(this.passedTime);
		copyPo.setProductionPermitRegistration(this.productionPermitRegistration);
		copyPo.setTaskId(this.taskId);
		copyPo.setTaskName(this.taskName);
		copyPo.setCheckResult(this.checkResult);
		copyPo.setUnitInfo(this.unitInfo);
		return copyPo;
	}

	public Integer getPkUnit() {
		return pkUnit;
	}

	public void setPkUnit(Integer pkUnit) {
		this.pkUnit = pkUnit;
	}

	public String getCheckTaskName() {
		return checkTaskName;
	}

	public void setCheckTaskName(String checkTaskName) {
		this.checkTaskName = checkTaskName;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public CheckConfig getCheckConfig() {
		return checkConfig;
	}

	public void setCheckConfig(CheckConfig checkConfig) {
		this.checkConfig = checkConfig;
	}

	public String getCheckState() {
		return checkState;
	}

	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}

	
	
}