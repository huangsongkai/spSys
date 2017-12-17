package com.xunj.po;

import com.xunj.core.CorePo;

/**
 * SysFuncInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysFuncInfo extends CorePo  implements java.io.Serializable {

	// Fields

	private String funcId;
	private String funcParentId;
	private String funcName;
	private String urlImg;
	private String url;
	private String onclickFunction;
	private String funcType;
	private String isLeaf;
	private Integer orderCol;
	private String state;
	private String remark;


	// Property accessors

	public String getFuncId() {
		return this.funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}

	public String getFuncParentId() {
		return this.funcParentId;
	}

	public void setFuncParentId(String funcParentId) {
		this.funcParentId = funcParentId;
	}

	public String getFuncName() {
		return this.funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getUrlImg() {
		return this.urlImg;
	}

	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOnclickFunction() {
		return this.onclickFunction;
	}

	public void setOnclickFunction(String onclickFunction) {
		this.onclickFunction = onclickFunction;
	}

	public String getFuncType() {
		return this.funcType;
	}

	public void setFuncType(String funcType) {
		this.funcType = funcType;
	}

	public String getIsLeaf() {
		return this.isLeaf;
	}

	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
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

	public Integer getOrderCol() {
		return orderCol;
	}

	public void setOrderCol(Integer orderCol) {
		this.orderCol = orderCol;
	}

}