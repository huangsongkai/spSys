package com.exam.po;

import java.util.Date;
import java.util.List;

import com.xunj.core.CorePo;

/**
 * CertTaskInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CertTaskInfo extends CorePo implements java.io.Serializable {

	// Fields

	private Integer certTaskId;
	private String productionPermitRegistration;
	private String confidentialityLevelQualifications;
	private Date passedTime;
	private Double passedScore;
	private String certUnitGroup;
	private String certStatus;
	private String remark;
	private String pkUnit;
	private String certType;
	private String planFileId;
	private String selfexamFileId;
	private String memberName1;
	private String memberName2;
	private String memberName3;
	private String memberName4;
	private String memberName5;
	private String memberName6;
	private String memberName7;
	private String memberName8;
	private String memberName9;
	private String memberName10;
	private String memberUnit1;
	private String memberUnit2;
	private String memberUnit3;
	private String memberUnit4;
	private String memberUnit5;
	private String memberUnit6;
	private String memberUnit7;
	private String memberUnit8;
	private String memberUnit9;
	private String memberUnit10;
	private String memberPosition1;
	private String memberPosition2;
	private String memberPosition3;
	private String memberPosition4;
	private String memberPosition5;
	private String memberPosition6;
	private String memberPosition7;
	private String memberPosition8;
	private String memberPosition9;
	private String memberPosition10;
	private String data1;
	private String data2;
	private String data3;
	private String data4;
	private String data5;
	private String data6;
	private String data7;
	private String data8;
	private String data9;
	private String data10;
	private String data11;
	private String data12;
	private String data13;
	private String data14;
	private String data15;
	private String data16;
	private String data17;
	private String data18;
	private String data19;//计划审查时间
	private String data20;//计划审查人员
	private String textarea1;
	private String textarea2;
	private List unitInfoList;

	private String application;
	private String applicationStatus;
	private String reviewInfo;
	private String reviewInfoStatus;
	private Integer numPeople;
	private Double highestScore;
	private Double average;
	private String submissionStatus;
	private String textarea3;
	private String textarea4;
	private String textarea5;
	private String textarea6;
	private String otherFile;
	private String otherFileStatus;
	private String reviewResult;
	private String checkFile1Status;
	private String checkFile2Status;
	private String checkFile1;
	private String checkFile2;
	
	private UnitInfo unitInfo;

	public UnitInfo getUnitInfo() {
		return unitInfo;
	}

	public void setUnitInfo(UnitInfo unitInfo) {
		this.unitInfo = unitInfo;
	}

	public CertTaskInfo() {
	}

	public CertTaskInfo(String productionPermitRegistration,
			String confidentialityLevelQualifications, Date passedTime,
			Double passedScore, String certUnitGroup, String certStatus,
			String remark, String pkUnit, String certType, String planFileId,
			String selfexamFileId, String memberName1, String memberName2,
			String memberName3, String memberName4, String memberName5,
			String memberName6, String memberName7, String memberName8,
			String memberName9,String memberName10,
			String memberUnit1, String memberUnit2, String memberUnit3,
			String memberUnit4, String memberUnit5, String memberUnit6,
			String memberUnit7, String memberUnit8,String memberUnit9,
			String memberUnit10,String memberPosition1,
			String memberPosition2, String memberPosition3,
			String memberPosition4, String memberPosition5,
			String memberPosition6, String memberPosition7,String memberPosition9,
			String memberPosition10,
			String memberPosition8, String data1, String data2, String data3,
			String data4, String data5, String data6, String data7,
			String data8, String data9, String data10, String data11,
			String data12, String data13, String data14, String data15,
			String data16, String data17, String data18, String data19,
			String data20, String textarea1, String textarea2,String otherFile,String otherFilestatus,String reviewResult) {
		this.productionPermitRegistration = productionPermitRegistration;
		this.confidentialityLevelQualifications = confidentialityLevelQualifications;
		this.passedTime = passedTime;
		this.passedScore = passedScore;
		this.certUnitGroup = certUnitGroup;
		this.certStatus = certStatus;
		this.remark = remark;
		this.pkUnit = pkUnit;
		this.certType = certType;
		this.planFileId = planFileId;
		this.selfexamFileId = selfexamFileId;
		this.memberName1 = memberName1;
		this.memberName2 = memberName2;
		this.memberName3 = memberName3;
		this.memberName4 = memberName4;
		this.memberName5 = memberName5;
		this.memberName6 = memberName6;
		this.memberName7 = memberName7;
		this.memberName8 = memberName8;
		this.memberName9 = memberName9;
		this.memberName10 = memberName10;
		this.memberUnit1 = memberUnit1;
		this.memberUnit2 = memberUnit2;
		this.memberUnit3 = memberUnit3;
		this.memberUnit4 = memberUnit4;
		this.memberUnit5 = memberUnit5;
		this.memberUnit6 = memberUnit6;
		this.memberUnit7 = memberUnit7;
		this.memberUnit8 = memberUnit8;
		this.memberUnit9 = memberUnit9;
		this.memberUnit10 = memberUnit10;
		this.memberPosition1 = memberPosition1;
		this.memberPosition2 = memberPosition2;
		this.memberPosition3 = memberPosition3;
		this.memberPosition4 = memberPosition4;
		this.memberPosition5 = memberPosition5;
		this.memberPosition6 = memberPosition6;
		this.memberPosition7 = memberPosition7;
		this.memberPosition8 = memberPosition8;
		this.memberPosition9 = memberPosition9;
		this.memberPosition10 = memberPosition10;
		this.data1 = data1;
		this.data2 = data2;
		this.data3 = data3;
		this.data4 = data4;
		this.data5 = data5;
		this.data6 = data6;
		this.data7 = data7;
		this.data8 = data8;
		this.data9 = data9;
		this.data10 = data10;
		this.data11 = data11;
		this.data12 = data12;
		this.data13 = data13;
		this.data14 = data14;
		this.data15 = data15;
		this.data16 = data16;
		this.data17 = data17;
		this.data18 = data18;
		this.data19 = data19;
		this.data20 = data20;
		this.textarea1 = textarea1;
		this.textarea2 = textarea2;
		this.otherFile=otherFile;
		this.otherFileStatus=otherFilestatus;
		this.reviewResult=reviewResult;
	}

	public String getReviewResult() {
		return reviewResult;
	}

	public void setReviewResult(String reviewResult) {
		this.reviewResult = reviewResult;
	}

	public Integer getCertTaskId() {
		return this.certTaskId;
	}

	public void setCertTaskId(Integer certTaskId) {
		this.certTaskId = certTaskId;
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

	public String getCertUnitGroup() {
		return this.certUnitGroup;
	}

	public void setCertUnitGroup(String certUnitGroup) {
		this.certUnitGroup = certUnitGroup;
	}

	public String getCertStatus() {
		return this.certStatus;
	}

	public void setCertStatus(String certStatus) {
		this.certStatus = certStatus;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPkUnit() {
		return pkUnit;
	}

	public void setPkUnit(String pkUnit) {
		this.pkUnit = pkUnit;
	}

	public String getCertType() {
		return this.certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getPlanFileId() {
		return this.planFileId;
	}

	public void setPlanFileId(String planFileId) {
		this.planFileId = planFileId;
	}

	public String getSelfexamFileId() {
		return this.selfexamFileId;
	}

	public void setSelfexamFileId(String selfexamFileId) {
		this.selfexamFileId = selfexamFileId;
	}

	public String getMemberName1() {
		return this.memberName1;
	}

	public void setMemberName1(String memberName1) {
		this.memberName1 = memberName1;
	}

	public String getMemberName2() {
		return this.memberName2;
	}

	public void setMemberName2(String memberName2) {
		this.memberName2 = memberName2;
	}

	public String getMemberName3() {
		return this.memberName3;
	}

	public void setMemberName3(String memberName3) {
		this.memberName3 = memberName3;
	}

	public String getMemberName4() {
		return this.memberName4;
	}

	public void setMemberName4(String memberName4) {
		this.memberName4 = memberName4;
	}

	public String getMemberName5() {
		return this.memberName5;
	}

	public void setMemberName5(String memberName5) {
		this.memberName5 = memberName5;
	}

	public String getMemberName6() {
		return this.memberName6;
	}

	public void setMemberName6(String memberName6) {
		this.memberName6 = memberName6;
	}

	public String getMemberName7() {
		return this.memberName7;
	}

	public void setMemberName7(String memberName7) {
		this.memberName7 = memberName7;
	}

	public String getMemberName8() {
		return this.memberName8;
	}

	public void setMemberName8(String memberName8) {
		this.memberName8 = memberName8;
	}

	public String getMemberUnit1() {
		return this.memberUnit1;
	}

	public void setMemberUnit1(String memberUnit1) {
		this.memberUnit1 = memberUnit1;
	}

	public String getMemberUnit2() {
		return this.memberUnit2;
	}

	public void setMemberUnit2(String memberUnit2) {
		this.memberUnit2 = memberUnit2;
	}

	public String getMemberUnit3() {
		return this.memberUnit3;
	}

	public void setMemberUnit3(String memberUnit3) {
		this.memberUnit3 = memberUnit3;
	}

	public String getMemberUnit4() {
		return this.memberUnit4;
	}

	public void setMemberUnit4(String memberUnit4) {
		this.memberUnit4 = memberUnit4;
	}

	public String getMemberUnit5() {
		return this.memberUnit5;
	}

	public void setMemberUnit5(String memberUnit5) {
		this.memberUnit5 = memberUnit5;
	}

	public String getMemberUnit6() {
		return this.memberUnit6;
	}

	public void setMemberUnit6(String memberUnit6) {
		this.memberUnit6 = memberUnit6;
	}

	public String getMemberUnit7() {
		return this.memberUnit7;
	}

	public void setMemberUnit7(String memberUnit7) {
		this.memberUnit7 = memberUnit7;
	}

	public String getMemberUnit8() {
		return this.memberUnit8;
	}

	public void setMemberUnit8(String memberUnit8) {
		this.memberUnit8 = memberUnit8;
	}

	public String getMemberPosition1() {
		return this.memberPosition1;
	}

	public void setMemberPosition1(String memberPosition1) {
		this.memberPosition1 = memberPosition1;
	}

	public String getMemberPosition2() {
		return this.memberPosition2;
	}

	public void setMemberPosition2(String memberPosition2) {
		this.memberPosition2 = memberPosition2;
	}

	public String getMemberPosition3() {
		return this.memberPosition3;
	}

	public void setMemberPosition3(String memberPosition3) {
		this.memberPosition3 = memberPosition3;
	}

	public String getMemberPosition4() {
		return this.memberPosition4;
	}

	public void setMemberPosition4(String memberPosition4) {
		this.memberPosition4 = memberPosition4;
	}

	public String getMemberPosition5() {
		return this.memberPosition5;
	}

	public void setMemberPosition5(String memberPosition5) {
		this.memberPosition5 = memberPosition5;
	}

	public String getMemberPosition6() {
		return this.memberPosition6;
	}

	public void setMemberPosition6(String memberPosition6) {
		this.memberPosition6 = memberPosition6;
	}

	public String getMemberPosition7() {
		return this.memberPosition7;
	}

	public void setMemberPosition7(String memberPosition7) {
		this.memberPosition7 = memberPosition7;
	}

	public String getMemberPosition8() {
		return this.memberPosition8;
	}

	public void setMemberPosition8(String memberPosition8) {
		this.memberPosition8 = memberPosition8;
	}

	public String getData1() {
		return this.data1;
	}

	public void setData1(String data1) {
		this.data1 = data1;
	}

	public String getData2() {
		return this.data2;
	}

	public void setData2(String data2) {
		this.data2 = data2;
	}

	public String getData3() {
		return this.data3;
	}

	public void setData3(String data3) {
		this.data3 = data3;
	}

	public String getData4() {
		return this.data4;
	}

	public void setData4(String data4) {
		this.data4 = data4;
	}

	public String getData5() {
		return this.data5;
	}

	public void setData5(String data5) {
		this.data5 = data5;
	}

	public String getData6() {
		return this.data6;
	}

	public void setData6(String data6) {
		this.data6 = data6;
	}

	public String getData7() {
		return this.data7;
	}

	public void setData7(String data7) {
		this.data7 = data7;
	}

	public String getData8() {
		return this.data8;
	}

	public void setData8(String data8) {
		this.data8 = data8;
	}

	public String getData9() {
		return this.data9;
	}

	public void setData9(String data9) {
		this.data9 = data9;
	}

	public String getData10() {
		return this.data10;
	}

	public void setData10(String data10) {
		this.data10 = data10;
	}

	public String getData11() {
		return this.data11;
	}

	public void setData11(String data11) {
		this.data11 = data11;
	}

	public String getData12() {
		return this.data12;
	}

	public void setData12(String data12) {
		this.data12 = data12;
	}

	public String getData13() {
		return this.data13;
	}

	public void setData13(String data13) {
		this.data13 = data13;
	}

	public String getData14() {
		return this.data14;
	}

	public void setData14(String data14) {
		this.data14 = data14;
	}

	public String getData15() {
		return this.data15;
	}

	public void setData15(String data15) {
		this.data15 = data15;
	}

	public String getData16() {
		return this.data16;
	}

	public void setData16(String data16) {
		this.data16 = data16;
	}

	public String getData17() {
		return this.data17;
	}

	public void setData17(String data17) {
		this.data17 = data17;
	}

	public String getData18() {
		return this.data18;
	}

	public void setData18(String data18) {
		this.data18 = data18;
	}

	public String getData19() {
		return this.data19;
	}

	public void setData19(String data19) {
		this.data19 = data19;
	}

	public String getData20() {
		return this.data20;
	}

	public void setData20(String data20) {
		this.data20 = data20;
	}

	public String getTextarea1() {
		return this.textarea1;
	}

	public void setTextarea1(String textarea1) {
		this.textarea1 = textarea1;
	}

	public String getTextarea2() {
		return this.textarea2;
	}

	public void setTextarea2(String textarea2) {
		this.textarea2 = textarea2;
	}


	public List getUnitInfoList() {
		return unitInfoList;
	}

	public void setUnitInfoList(List unitInfoList) {
		this.unitInfoList = unitInfoList;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public String getReviewInfo() {
		return reviewInfo;
	}

	public void setReviewInfo(String reviewInfo) {
		this.reviewInfo = reviewInfo;
	}

	public String getReviewInfoStatus() {
		return reviewInfoStatus;
	}

	public void setReviewInfoStatus(String reviewInfoStatus) {
		this.reviewInfoStatus = reviewInfoStatus;
	}

	public Integer getNumPeople() {
		return numPeople;
	}

	public void setNumPeople(Integer numPeople) {
		this.numPeople = numPeople;
	}

	public Double getHighestScore() {
		return highestScore;
	}

	public void setHighestScore(Double highestScore) {
		this.highestScore = highestScore;
	}

	public Double getAverage() {
		return average;
	}

	public void setAverage(Double average) {
		this.average = average;
	}

	public String getSubmissionStatus() {
		return submissionStatus;
	}

	public void setSubmissionStatus(String submissionStatus) {
		this.submissionStatus = submissionStatus;
	}

	public String getTextarea3() {
		return textarea3;
	}

	public void setTextarea3(String textarea3) {
		this.textarea3 = textarea3;
	}

	public String getTextarea4() {
		return textarea4;
	}

	public void setTextarea4(String textarea4) {
		this.textarea4 = textarea4;
	}

	public String getTextarea5() {
		return textarea5;
	}

	public void setTextarea5(String textarea5) {
		this.textarea5 = textarea5;
	}

	public String getTextarea6() {
		return textarea6;
	}

	public void setTextarea6(String textarea6) {
		this.textarea6 = textarea6;
	}

	public String getOtherFile() {
		return otherFile;
	}

	public void setOtherFile(String otherFile) {
		this.otherFile = otherFile;
	}

	public String getOtherFileStatus() {
		return otherFileStatus;
	}

	public void setOtherFileStatus(String otherFileStatus) {
		this.otherFileStatus = otherFileStatus;
	}

	public String getMemberName9() {
		return memberName9;
	}

	public void setMemberName9(String memberName9) {
		this.memberName9 = memberName9;
	}

	public String getMemberName10() {
		return memberName10;
	}

	public void setMemberName10(String memberName10) {
		this.memberName10 = memberName10;
	}

	public String getMemberUnit9() {
		return memberUnit9;
	}

	public void setMemberUnit9(String memberUnit9) {
		this.memberUnit9 = memberUnit9;
	}

	public String getMemberUnit10() {
		return memberUnit10;
	}

	public void setMemberUnit10(String memberUnit10) {
		this.memberUnit10 = memberUnit10;
	}

	public String getMemberPosition9() {
		return memberPosition9;
	}

	public void setMemberPosition9(String memberPosition9) {
		this.memberPosition9 = memberPosition9;
	}

	public String getMemberPosition10() {
		return memberPosition10;
	}

	public void setMemberPosition10(String memberPosition10) {
		this.memberPosition10 = memberPosition10;
	}

	public String getCheckFile1Status() {
		return checkFile1Status;
	}

	public void setCheckFile1Status(String checkFile1Status) {
		this.checkFile1Status = checkFile1Status;
	}

	public String getCheckFile2Status() {
		return checkFile2Status;
	}

	public void setCheckFile2Status(String checkFile2Status) {
		this.checkFile2Status = checkFile2Status;
	}

	public String getCheckFile1() {
		return checkFile1;
	}

	public void setCheckFile1(String checkFile1) {
		this.checkFile1 = checkFile1;
	}

	public String getCheckFile2() {
		return checkFile2;
	}

	public void setCheckFile2(String checkFile2) {
		this.checkFile2 = checkFile2;
	}

	
}