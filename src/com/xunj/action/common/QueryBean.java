package com.xunj.action.common;

import java.util.List;

public class QueryBean{
	/**
	 * 进行查询操作的类名称
	 */
	private String beanName;
	/**
	 * 进行查询操作的类名称
	 */
	private String[] or;
	/**
	 * 类字段名称及相关属性
	 */
	private BeanFields[] beanFields;
	/**
	 * 排序
	 */
	private BeanFields[] orders;
	/**
	 * 返回list名称
	 */
	private String listName;
	/**
	 * 是否为静态条件查询，为空，动态查询，true静态条件查询
	 */
	private String staticCondition;
	/**
	 * 静态条件查询时是否写入返回值map，调用名称
	 */
	private String getValueName;
	/**
	 * 是否分页
	 */
	private String isSplitPage;
	
	/**
	 * 参数list
	 */
	private List paraList;
	/**
	 * 映射类list
	 */
	private List mappingBean;
	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String[] getOr() {
		return or;
	}

	public void setOr(String[] or) {
		this.or = or;
	}

	public BeanFields[] getBeanFields() {
		return beanFields;
	}

	public void setBeanFields(BeanFields[] beanFields) {
		this.beanFields = beanFields;
	}

	public BeanFields[] getOrders() {
		return orders;
	}

	public void setOrders(BeanFields[] orders) {
		this.orders = orders;
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public String getStaticCondition() {
		return staticCondition;
	}

	public void setStaticCondition(String staticCondition) {
		this.staticCondition = staticCondition;
	}

	public String getGetValueName() {
		return getValueName;
	}

	public void setGetValueName(String getValueName) {
		this.getValueName = getValueName;
	}

	public String getIsSplitPage() {
		return isSplitPage;
	}

	public void setIsSplitPage(String isSplitPage) {
		this.isSplitPage = isSplitPage;
	}

	public List getParaList() {
		return paraList;
	}

	public void setParaList(List paraList) {
		this.paraList = paraList;
	}

	public List getMappingBean() {
		return mappingBean;
	}

	public void setMappingBean(List mappingBean) {
		this.mappingBean = mappingBean;
	}
	
}
