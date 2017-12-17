package com.xunj.po;

import java.util.HashSet;
import java.util.Set;

import com.xunj.core.CorePo;

/**
 * 权限组表(角色)
 */

public class SysFuncGroup extends CorePo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -9134865145773861280L;
	/**
	 * 权限组ID
	 */
	private String funcGroupId;
	/**
	 * 权限组名称
	 */
	private String funcGroupName;
	/**
	 * 权限组描述
	 */
	private String funcGroupDescription;

	private Set funcGroupLists = new HashSet(0);

	private Set funcGroupUsers = new HashSet(0);



	public String getFuncGroupId() {
		return funcGroupId;
	}

	public void setFuncGroupId(String funcGroupId) {
		this.funcGroupId = funcGroupId;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getFuncGroupName() {
		return this.funcGroupName;
	}

	public void setFuncGroupName(String funcGroupName) {
		this.funcGroupName = funcGroupName;
	}

	public String getFuncGroupDescription() {
		return this.funcGroupDescription;
	}

	public void setFuncGroupDescription(String funcGroupDescription) {
		this.funcGroupDescription = funcGroupDescription;
	}

	public Set getFuncGroupLists() {
		return this.funcGroupLists;
	}

	public void setFuncGroupLists(Set funcGroupLists) {
		this.funcGroupLists = funcGroupLists;
	}

	public Set getFuncGroupUsers() {
		return this.funcGroupUsers;
	}

	public void setFuncGroupUsers(Set funcGroupUsers) {
		this.funcGroupUsers = funcGroupUsers;
	}

}