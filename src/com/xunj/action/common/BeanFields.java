package com.xunj.action.common;

public 	class BeanFields
{
	/**
	 * 字段名称
	 */
	private String fieldName;
	/**
	 * 数据类型
	 */
	private String dataType;
	/**
	 * 查询规则
	 */
	private String role;
	/**
	 * 字段值
	 */
	private String value;
	/**
	 * 排序
	 */
	private String order;
	/**
	 * 排序顺序
	 */
	private String orderSeq;
	/**
	 * 文本框名称添加的后缀，为返回值使用
	 */
	private String  suffix;
	/**
	 * 子类集合名称
	 */
	private String setName;
	/**
	 * 映射子类bean
	 */
	private String mappingBeanName;


	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getOrderSeq() {
		return orderSeq;
	}
	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getSetName() {
		return setName;
	}
	public void setSetName(String setName) {
		this.setName = setName;
	}
	public String getMappingBeanName() {
		return mappingBeanName;
	}
	public void setMappingBeanName(String mappingBeanName) {
		this.mappingBeanName = mappingBeanName;
	}
	
}