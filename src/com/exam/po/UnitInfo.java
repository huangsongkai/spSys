package com.exam.po;

import java.util.Date;

import com.xunj.core.CorePo;

/**
 * UnitInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UnitInfo extends CorePo implements java.io.Serializable {

	// Fields

	private Integer pkUnit; //���
	private String unitName; //��λ���
	private String unitCategory; //��λ����
	private String unitLoginAddress; //��λע���ַ
	private String unitCommunicationAddress; //��λͨѶ��ַ
	private String legalRepresentative; //���������
	private String unitSummary; //��λ���
	private String zipCode; //�ʱ�
	private String licenseLevel; //���ܵȼ�
	private String unitNo; //��λ���
	private String unitGroup; //������
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
	private String data19;
	private String data20;
	private String dataValue; 
	private String superiorInfo;// �ϼ���λ  
	private Date censorDate; //��֤ͨ��ʵ��
	private Double censorScore; //��֤����
	private String censorStatus; //��֤״̬
	private String censorPassed; //��֤�Ƿ�ͨ��
	private String unitPhone; //��λ�绰
	private String censorLevel; //��֤�ȼ�
	private String censorFirst; //��֤���
	private String censorSecond; //�������
	private Date secondTime; //����ͨ��ʱ��
	private String unitPeople; //��λ����
	private String classifiedPeople;//��������
	private String placement; //�ڷ�λ��
	private String listedCompanies;
	private Date qualificationByTime;
	private Date qualificationFailTime;
	
	// Constructors

	/** default constructor */      
	public UnitInfo() {
	}

	/** full constructor */
	public UnitInfo(String unitName, String unitCategory,
			String unitLoginAddress, String unitCommunicationAddress,
			String legalRepresentative, String unitSummary, String zipCode,
			String unitRegion, String licenseLevel,
			String unitNo, String unitGroup, String data1, String data2,
			String data3, String data4, String data5, String data6,
			String data7, String data8, String data9, String data10,
			String data11, String data12, String data13, String data14,
			String data15, String data16, String data17, String data18,
			String data19, String data20) {
		this.unitName = unitName;
		this.unitCategory = unitCategory;
		this.unitLoginAddress = unitLoginAddress;
		this.unitCommunicationAddress = unitCommunicationAddress;
		this.legalRepresentative = legalRepresentative;
		this.unitSummary = unitSummary;
		this.zipCode = zipCode;
		this.licenseLevel = licenseLevel;
		this.unitNo = unitNo;
		this.unitGroup = unitGroup;
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
	}

	// Property accessors

	public Integer getPkUnit() {
		return this.pkUnit;
	}

	public void setPkUnit(Integer pkUnit) {
		this.pkUnit = pkUnit;
	}

	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitCategory() {
		return this.unitCategory;
	}

	public void setUnitCategory(String unitCategory) {
		this.unitCategory = unitCategory;
	}

	public String getUnitLoginAddress() {
		return this.unitLoginAddress;
	}

	public void setUnitLoginAddress(String unitLoginAddress) {
		this.unitLoginAddress = unitLoginAddress;
	}

	public String getUnitCommunicationAddress() {
		return this.unitCommunicationAddress;
	}

	public void setUnitCommunicationAddress(String unitCommunicationAddress) {
		this.unitCommunicationAddress = unitCommunicationAddress;
	}

	public String getLegalRepresentative() {
		return this.legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}

	public String getUnitSummary() {
		return this.unitSummary;
	}

	public void setUnitSummary(String unitSummary) {
		this.unitSummary = unitSummary;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getLicenseLevel() {
		return licenseLevel;
	}

	public void setLicenseLevel(String licenseLevel) {
		this.licenseLevel = licenseLevel;
	}

	public Double getCensorScore() {
		return censorScore;
	}

	public void setCensorScore(Double censorScore) {
		this.censorScore = censorScore;
	}

	public String getUnitNo() {
		return this.unitNo;
	}

	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}

	public String getUnitGroup() {
		return this.unitGroup;
	}

	public void setUnitGroup(String unitGroup) {
		this.unitGroup = unitGroup;
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

	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

	public String getSuperiorInfo() {
		return superiorInfo;
	}

	public void setSuperiorInfo(String superiorInfo) {
		this.superiorInfo = superiorInfo;
	}

	public Date getCensorDate() {
		if(censorDate == null)
			return null;
		return censorDate;
	}

	public void setCensorDate(Date censorDate) {
		this.censorDate = censorDate;
	}

	public String getCensorStatus() {
		return censorStatus;
	}

	public void setCensorStatus(String censorStatus) {
		this.censorStatus = censorStatus;
	}

	public String getCensorPassed() {
		return censorPassed;
	}

	public void setCensorPassed(String censorPassed) {
		this.censorPassed = censorPassed;
	}

	public String getUnitPhone() {
		return unitPhone;
	}

	public void setUnitPhone(String unitPhone) {
		this.unitPhone = unitPhone;
	}

	public String getCensorLevel() {
		return censorLevel;
	}

	public void setCensorLevel(String censorLevel) {
		this.censorLevel = censorLevel;
	}

	public String getCensorFirst() {
		return censorFirst;
	}

	public void setCensorFirst(String censorFirst) {
		this.censorFirst = censorFirst;
	}

	public String getCensorSecond() {
		return censorSecond;
	}

	public void setCensorSecond(String censorSecond) {
		this.censorSecond = censorSecond;
	}

	public Date getSecondTime() {
		return secondTime;
	}

	public void setSecondTime(Date secondTime) {
		this.secondTime = secondTime;
	}

	public String getUnitPeople() {
		return unitPeople;
	}

	public void setUnitPeople(String unitPeople) {
		this.unitPeople = unitPeople;
	}

	public String getClassifiedPeople() {
		return classifiedPeople;
	}

	public void setClassifiedPeople(String classifiedPeople) {
		this.classifiedPeople = classifiedPeople;
	}

	public String getPlacement() {
		return placement;
	}

	public void setPlacement(String placement) {
		this.placement = placement;
	}

	public String getListedCompanies() {
		return listedCompanies;
	}

	public void setListedCompanies(String listedCompanies) {
		this.listedCompanies = listedCompanies;
	}

	public Date getQualificationByTime() {
		return qualificationByTime;
	}

	public void setQualificationByTime(Date qualificationByTime) {
		this.qualificationByTime = qualificationByTime;
	}

	public Date getQualificationFailTime() {
		return qualificationFailTime;
	}

	public void setQualificationFailTime(Date qualificationFailTime) {
		this.qualificationFailTime = qualificationFailTime;
	}

	


}