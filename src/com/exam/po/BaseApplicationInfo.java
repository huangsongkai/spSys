package com.exam.po;

import java.util.Date;

import com.xunj.core.CorePo;

/**
 * UnitInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class BaseApplicationInfo extends CorePo implements java.io.Serializable {

	// Fields

	private Integer pkbaseApp;
	private String companyName;
	private String creditCode;
	private String applyReason;
	private String mianLegalBody;
	private String chargeOfSecretLeader;
	private String otherLeader;
	private String secretDepartment;    
	private String secretPerson;
	private String centralizingMng;
	private String secretCommittee;
	private String workSituation1;
	private String workSituation2;
	private String mtcsol;
	
	private String secretCommit; 
	private String secretSituation;
	private String basicSystem;
	private String specialSystem; 
	private String tightMng1;
	private String tightMng2;
	private String tightMng3;
	private String tightMng4;
	private Integer secretStaffMng1;
	private Integer secretStaffMng2;
	private Integer secretStaffMng3; 
	private String secretStaffMng4;
	private String countriesSys;
	private String denseProductMng;
	private String theImportSecretMng1;
	private String theImportSecretMng2;
	private String sysAndEquiAndStorageMng1;
	private String sysAndEquiAndStorageMng2;
	private String sysAndEquiAndStorageMng3;
	private String sysAndEquiAndStorageMng4;
	private String sysAndEquiAndStorageMng5;
	private String sysAndEquiAndStorageMng6;
	
	private String newsMng;
	private String meetingMng;
	private String testMng;
	private String collaborationMng1;
	private String collaborationMng2;
	
	private String foreignNationals;
	private String secretCheck;
	private String kpAndRewardsAndPunishments;
	private String workFileMng;
	private String workingFundsMng1;
	private String workingFundsMng2;
	
	private String appTime;
	private String spStatus;
	private String certScore;
	
	private BaseInfo baseInfo;
	
	

	// Constructors
	/** default constructor */      
	
	public BaseApplicationInfo() {
	}
	
	public String getAppTime() {
		return appTime;
	}

	public void setAppTime(String appTime) {
		this.appTime = appTime;
	}

	/** full constructor */
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCreditCode() {
		return creditCode;
	}
	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}
	public String getApplyReason() {
		return applyReason;
	}
	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}
	public String getMianLegalBody() {
		return mianLegalBody;
	}
	public void setMianLegalBody(String mianLegalBody) {
		this.mianLegalBody = mianLegalBody;
	}
	public String getChargeOfSecretLeader() {
		return chargeOfSecretLeader;
	}
	public void setChargeOfSecretLeader(String chargeOfSecretLeader) {
		this.chargeOfSecretLeader = chargeOfSecretLeader;
	}
	public String getOtherLeader() {
		return otherLeader;
	}
	public void setOtherLeader(String otherLeader) {
		this.otherLeader = otherLeader;
	}
	public String getSecretDepartment() {
		return secretDepartment;
	}
	public void setSecretDepartment(String secretDepartment) {
		this.secretDepartment = secretDepartment;
	}
	public String getSecretPerson() {
		return secretPerson;
	}
	public void setSecretPerson(String secretPerson) {
		this.secretPerson = secretPerson;
	}
	public String getCentralizingMng() {
		return centralizingMng;
	}
	public void setCentralizingMng(String centralizingMng) {
		this.centralizingMng = centralizingMng;
	}
	public String getSecretCommittee() {
		return secretCommittee;
	}
	public void setSecretCommittee(String secretCommittee) {
		this.secretCommittee = secretCommittee;
	}
	public String getWorkSituation1() {
		return workSituation1;
	}
	public void setWorkSituation1(String workSituation1) {
		this.workSituation1 = workSituation1;
	}
	public String getWorkSituation2() {
		return workSituation2;
	}
	public void setWorkSituation2(String workSituation2) {
		this.workSituation2 = workSituation2;
	}
	public String getMtcsol() {
		return mtcsol;
	}
	public void setMtcsol(String mtcsol) {
		this.mtcsol = mtcsol;
	}
	public String getSecretCommit() {
		return secretCommit;
	}
	public void setSecretCommit(String secretCommit) {
		this.secretCommit = secretCommit;
	}
	public String getSecretSituation() {
		return secretSituation;
	}
	public void setSecretSituation(String secretSituation) {
		this.secretSituation = secretSituation;
	}
	public String getBasicSystem() {
		return basicSystem;
	}
	public void setBasicSystem(String basicSystem) {
		this.basicSystem = basicSystem;
	}
	public String getSpecialSystem() {
		return specialSystem;
	}
	public void setSpecialSystem(String specialSystem) {
		this.specialSystem = specialSystem;
	}
	public String getTightMng1() {
		return tightMng1;
	}
	public void setTightMng1(String tightMng1) {
		this.tightMng1 = tightMng1;
	}
	public String getTightMng2() {
		return tightMng2;
	}
	public void setTightMng2(String tightMng2) {
		this.tightMng2 = tightMng2;
	}
	public String getTightMng3() {
		return tightMng3;
	}
	public void setTightMng3(String tightMng3) {
		this.tightMng3 = tightMng3;
	}
	public String getTightMng4() {
		return tightMng4;
	}
	public void setTightMng4(String tightMng4) {
		this.tightMng4 = tightMng4;
	}
	public Integer getSecretStaffMng1() {
		return secretStaffMng1;
	}
	public void setSecretStaffMng1(Integer secretStaffMng1) {
		this.secretStaffMng1 = secretStaffMng1;
	}
	public Integer getSecretStaffMng2() {
		return secretStaffMng2;
	}
	public void setSecretStaffMng2(Integer secretStaffMng2) {
		this.secretStaffMng2 = secretStaffMng2;
	}
	public Integer getSecretStaffMng3() {
		return secretStaffMng3;
	}
	public void setSecretStaffMng3(Integer secretStaffMng3) {
		this.secretStaffMng3 = secretStaffMng3;
	}
	public String getSecretStaffMng4() {
		return secretStaffMng4;
	}
	public void setSecretStaffMng4(String secretStaffMng4) {
		this.secretStaffMng4 = secretStaffMng4;
	}
	public String getCountriesSys() {
		return countriesSys;
	}
	public void setCountriesSys(String countriesSys) {
		this.countriesSys = countriesSys;
	}
	public String getDenseProductMng() {
		return denseProductMng;
	}
	public void setDenseProductMng(String denseProductMng) {
		this.denseProductMng = denseProductMng;
	}
	public String getTheImportSecretMng1() {
		return theImportSecretMng1;
	}
	public void setTheImportSecretMng1(String theImportSecretMng1) {
		this.theImportSecretMng1 = theImportSecretMng1;
	}
	public String getTheImportSecretMng2() {
		return theImportSecretMng2;
	}
	public void setTheImportSecretMng2(String theImportSecretMng2) {
		this.theImportSecretMng2 = theImportSecretMng2;
	}
	public String getSysAndEquiAndStorageMng1() {
		return sysAndEquiAndStorageMng1;
	}
	public void setSysAndEquiAndStorageMng1(String sysAndEquiAndStorageMng1) {
		this.sysAndEquiAndStorageMng1 = sysAndEquiAndStorageMng1;
	}
	public String getSysAndEquiAndStorageMng2() {
		return sysAndEquiAndStorageMng2;
	}
	public void setSysAndEquiAndStorageMng2(String sysAndEquiAndStorageMng2) {
		this.sysAndEquiAndStorageMng2 = sysAndEquiAndStorageMng2;
	}
	public String getSysAndEquiAndStorageMng3() {
		return sysAndEquiAndStorageMng3;
	}
	public void setSysAndEquiAndStorageMng3(String sysAndEquiAndStorageMng3) {
		this.sysAndEquiAndStorageMng3 = sysAndEquiAndStorageMng3;
	}
	public String getSysAndEquiAndStorageMng4() {
		return sysAndEquiAndStorageMng4;
	}
	public void setSysAndEquiAndStorageMng4(String sysAndEquiAndStorageMng4) {
		this.sysAndEquiAndStorageMng4 = sysAndEquiAndStorageMng4;
	}
	public String getSysAndEquiAndStorageMng5() {
		return sysAndEquiAndStorageMng5;
	}
	public void setSysAndEquiAndStorageMng5(String sysAndEquiAndStorageMng5) {
		this.sysAndEquiAndStorageMng5 = sysAndEquiAndStorageMng5;
	}
	public String getSysAndEquiAndStorageMng6() {
		return sysAndEquiAndStorageMng6;
	}
	public void setSysAndEquiAndStorageMng6(String sysAndEquiAndStorageMng6) {
		this.sysAndEquiAndStorageMng6 = sysAndEquiAndStorageMng6;
	}
	public String getNewsMng() {
		return newsMng;
	}
	public void setNewsMng(String newsMng) {
		this.newsMng = newsMng;
	}
	
	public String getMeetingMng() {
		return meetingMng;
	}

	public void setMeetingMng(String meetingMng) {
		this.meetingMng = meetingMng;
	}

	public String getTestMng() {
		return testMng;
	}

	public void setTestMng(String testMng) {
		this.testMng = testMng;
	}

	public String getCollaborationMng1() {
		return collaborationMng1;
	}
	public void setCollaborationMng1(String collaborationMng1) {
		this.collaborationMng1 = collaborationMng1;
	}
	public String getCollaborationMng2() {
		return collaborationMng2;
	}
	public void setCollaborationMng2(String collaborationMng2) {
		this.collaborationMng2 = collaborationMng2;
	}
	public String getForeignNationals() {
		return foreignNationals;
	}
	public void setForeignNationals(String foreignNationals) {
		this.foreignNationals = foreignNationals;
	}
	public String getSecretCheck() {
		return secretCheck;
	}
	public void setSecretCheck(String secretCheck) {
		this.secretCheck = secretCheck;
	}
	public String getKpAndRewardsAndPunishments() {
		return kpAndRewardsAndPunishments;
	}
	public void setKpAndRewardsAndPunishments(String kpAndRewardsAndPunishments) {
		this.kpAndRewardsAndPunishments = kpAndRewardsAndPunishments;
	}
	public String getWorkFileMng() {
		return workFileMng;
	}
	public void setWorkFileMng(String workFileMng) {
		this.workFileMng = workFileMng;
	}
	public String getWorkingFundsMng1() {
		return workingFundsMng1;
	}
	public void setWorkingFundsMng1(String workingFundsMng1) {
		this.workingFundsMng1 = workingFundsMng1;
	}
	public String getWorkingFundsMng2() {
		return workingFundsMng2;
	}
	public void setWorkingFundsMng2(String workingFundsMng2) {
		this.workingFundsMng2 = workingFundsMng2;
	}
	public Integer getPkbaseApp() {
		return pkbaseApp;
	}
	public void setPkbaseApp(Integer pkbaseApp) {
		this.pkbaseApp = pkbaseApp;
	}

	public BaseInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(BaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public String getSpStatus() {
		return spStatus;
	}

	public void setSpStatus(String spStatus) {
		this.spStatus = spStatus;
	}

	public String getCertScore() {
		return certScore;
	}

	public void setCertScore(String certScore) {
		this.certScore = certScore;
	}
	
	// Property accessors
	
}