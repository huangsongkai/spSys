package com.xunj.po;


import java.util.Date;

import com.xunj.core.CorePo;

/**
 * SysUserList entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysUserList extends CorePo  implements java.io.Serializable {

	// Fields

	/**
	 * 用户ID
	 */
	private String userId;
	
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 用户昵称
	 */
	private String nickName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 用户排序
	 */
	private Long userOrder;
	/**
	 * email
	 */
	private String email;
	/**
	 * 办公室电话
	 */
	private String officeTel;
	/**
	 * 移动电话
	 */
	private String mobileTelephone;
	/**
	 * 状态
	 */
	private String state;
	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 身份证号
	 */
	private String userIdNumber;
	/**
	 * 是否为部门领导
	 */
	private String isLeader;
	/**
	 * 生日
	 */
	private Date birthday;
	/**
	 * 职务
	 */
	private String userPosition;
	/**
	 * 职称
	 */
	private String userTitle;
	/**
	 * 家庭电话
	 */
	private String familyTel;
	/**
	 * 所属部门号
	 */
	private String deptId;
	private String deptName;
	private String gender;
	// Property accessors
	
	
	

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getUserOrder() {
		return this.userOrder;
	}

	public void setUserOrder(Long userOrder) {
		this.userOrder = userOrder;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOfficeTel() {
		return this.officeTel;
	}

	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}

	public String getMobileTelephone() {
		return this.mobileTelephone;
	}

	public void setMobileTelephone(String mobileTelephone) {
		this.mobileTelephone = mobileTelephone;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getUserIdNumber() {
		return userIdNumber;
	}

	public void setUserIdNumber(String userIdNumber) {
		this.userIdNumber = userIdNumber;
	}

	public String getIsLeader() {
		return isLeader;
	}

	public void setIsLeader(String isLeader) {
		this.isLeader = isLeader;
	}

	public String getUserPosition() {
		return userPosition;
	}

	public void setUserPosition(String userPosition) {
		this.userPosition = userPosition;
	}

	public String getUserTitle() {
		return userTitle;
	}

	public void setUserTitle(String userTitle) {
		this.userTitle = userTitle;
	}

	public String getFamilyTel() {
		return familyTel;
	}

	public void setFamilyTel(String familyTel) {
		this.familyTel = familyTel;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}