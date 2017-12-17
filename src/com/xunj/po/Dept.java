package com.xunj.po;

import java.util.HashSet;
import java.util.Set;

import com.xunj.core.CorePo;

/**
 * Dept entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Dept extends CorePo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8138513972525208589L;
	private String deptId;
	private String deptCode;
	private String deptName;
	private String deptPid;
	private Long deptOrder;
	private String deptDescription;
	private String state;
	private String remark;
	private String leader;
	private String deptKind;
	// Property accessors


	public String getDeptCode() {
		return this.deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptPid() {
		return this.deptPid;
	}

	public void setDeptPid(String deptPid) {
		this.deptPid = deptPid;
	}

	public Long getDeptOrder() {
		return this.deptOrder;
	}

	public void setDeptOrder(Long deptOrder) {
		this.deptOrder = deptOrder;
	}

	public String getDeptDescription() {
		return this.deptDescription;
	}

	public void setDeptDescription(String deptDescription) {
		this.deptDescription = deptDescription;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDeptKind() {
		return deptKind;
	}

	public void setDeptKind(String deptKind) {
		this.deptKind = deptKind;
	}
	
	
}