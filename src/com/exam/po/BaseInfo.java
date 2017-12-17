package com.exam.po;

import java.util.Date;

import com.xunj.core.CorePo;

/**
 * UnitInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class BaseInfo extends CorePo implements java.io.Serializable {

	// Fields

	private Integer pkbase;
	private String companyName;
	private String creditCode;
	private String companyType;
	private String legalBody;
	private Integer companyPersonCount;
	private Integer secretPersonCount;
	
	private String regAddress;
	private String officeAddress;
	private String mailingAddress;
	private Integer postalCode;
	private String phone; 
	private Date companyCreateTime;
	private String isShangshi;
	private String regMoney;
	private String fixedAssets;
	private String equityStructure;
	private String foreignRelations;
	private String punishments;
	private String summaryOfCompany;
	
	// Constructors
	/** default constructor */      
	public BaseInfo() {
	}
	/** full constructor */
	public Integer getPkbase() {
		return pkbase;
	}
	public void setPkbase(Integer pkbase) {
		this.pkbase = pkbase;
	}
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
	public String getCompanyType() {
		return companyType;
	}
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
	public String getLegalBody() {
		return legalBody;
	}
	public void setLegalBody(String legalBody) {
		this.legalBody = legalBody;
	}
	public Integer getCompanyPersonCount() {
		return companyPersonCount;
	}
	public void setCompanyPersonCount(Integer companyPersonCount) {
		this.companyPersonCount = companyPersonCount;
	}
	public Integer getSecretPersonCount() {
		return secretPersonCount;
	}
	public void setSecretPersonCount(Integer secretPersonCount) {
		this.secretPersonCount = secretPersonCount;
	}
	public String getRegAddress() {
		return regAddress;
	}
	public void setRegAddress(String regAddress) {
		this.regAddress = regAddress;
	}
	public String getOfficeAddress() {
		return officeAddress;
	}
	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}
	public String getMailingAddress() {
		return mailingAddress;
	}
	public void setMailingAddress(String mailingAddress) {
		this.mailingAddress = mailingAddress;
	}
	public Integer getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(Integer postalCode) {
		this.postalCode = postalCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getCompanyCreateTime() {
		return companyCreateTime;
	}
	public void setCompanyCreateTime(Date companyCreateTime) {
		this.companyCreateTime = companyCreateTime;
	}
	public String getIsShangshi() {
		return isShangshi;
	}
	public void setIsShangshi(String isShangshi) {
		this.isShangshi = isShangshi;
	}
	public String getRegMoney() {
		return regMoney;
	}
	public void setRegMoney(String regMoney) {
		this.regMoney = regMoney;
	}
	public String getFixedAssets() {
		return fixedAssets;
	}
	public void setFixedAssets(String fixedAssets) {
		this.fixedAssets = fixedAssets;
	}
	public String getEquityStructure() {
		return equityStructure;
	}
	public void setEquityStructure(String equityStructure) {
		this.equityStructure = equityStructure;
	}
	public String getForeignRelations() {
		return foreignRelations;
	}
	public void setForeignRelations(String foreignRelations) {
		this.foreignRelations = foreignRelations;
	}
	public String getPunishments() {
		return punishments;
	}
	public void setPunishments(String punishments) {
		this.punishments = punishments;
	}
	public String getSummaryOfCompany() {
		return summaryOfCompany;
	}
	public void setSummaryOfCompany(String summaryOfCompany) {
		this.summaryOfCompany = summaryOfCompany;
	}
	
	
	// Property accessors
	
}