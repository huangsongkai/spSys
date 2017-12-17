package com.xunj.po;

import com.xunj.core.CorePo;

/**
 * �û����ܵ��
 */

public class SysUserFuncInfo extends CorePo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -113068778499201869L;

	/**
	 * ID
	 */
	private String userFuncInfoId;
	
	private String funcId;
	
	private String userId;

	private SysUserList userList;

	private SysFuncInfo funcInfo;




	public String getUserFuncInfoId() {
		return userFuncInfoId;
	}

	public void setUserFuncInfoId(String userFuncInfoId) {
		this.userFuncInfoId = userFuncInfoId;
	}


	public SysUserList getUserList() {
		return userList;
	}

	public void setUserList(SysUserList userList) {
		this.userList = userList;
	}

	public SysFuncInfo getFuncInfo() {
		return funcInfo;
	}

	public void setFuncInfo(SysFuncInfo funcInfo) {
		this.funcInfo = funcInfo;
	}

	public String getFuncId() {
		return funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}



}