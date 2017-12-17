package com.xunj.po;

import com.xunj.core.CorePo;

/**
 * SysFuncGroupList entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysFuncGroupList extends CorePo  implements java.io.Serializable {

	// Fields

	private String funcGroupListId;
	private String funcGroupId;
	private String funcId;

	
	// Property accessors

	public String getFuncGroupListId() {
		return this.funcGroupListId;
	}

	public void setFuncGroupListId(String funcGroupListId) {
		this.funcGroupListId = funcGroupListId;
	}

	public String getFuncGroupId() {
		return funcGroupId;
	}

	public void setFuncGroupId(String funcGroupId) {
		this.funcGroupId = funcGroupId;
	}

	public String getFuncId() {
		return funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}

}