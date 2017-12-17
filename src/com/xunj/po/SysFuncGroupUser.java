package com.xunj.po;

import com.xunj.core.CorePo;

/**
 * �û����ܵ��
 */

public class SysFuncGroupUser extends CorePo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3757949280708421747L;

	/**
	 * �û����ܵ��Ӧ��ID
	 */
	private String funcGroupUserId;
	private String userId;
	private String funcGroupId;
	/**
	 * ������
	 */
	private SysFuncGroup funcGroup;
	/**
	 * �û�
	 */
	private SysUserList userList;

	// Constructors

	/** default constructor */
	public SysFuncGroupUser() {
	}



	public String getFuncGroupUserId() {
		return funcGroupUserId;
	}



	public void setFuncGroupUserId(String funcGroupUserId) {
		this.funcGroupUserId = funcGroupUserId;
	}



	public SysFuncGroup getFuncGroup() {
		return funcGroup;
	}



	public void setFuncGroup(SysFuncGroup funcGroup) {
		this.funcGroup = funcGroup;
	}



	public SysUserList getUserList() {
		return userList;
	}



	public void setUserList(SysUserList userList) {
		this.userList = userList;
	}






	public String getFuncGroupId() {
		return funcGroupId;
	}



	public void setFuncGroupId(String funcGroupId) {
		this.funcGroupId = funcGroupId;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}

	
}