package com.xunj.util;



public class StateConst {

	// 系统模块状态标识
	/**
	 * "A"--使用or已发布
	 */
	public static final String STATE_USE = "A";
	/**
	 * "P"--作废or未发布
	 */
	public static final String STATE_DELETE = "P";
	
	/**
	 * "H"--作废or未发布
	 */
	public static final String STATE_HISTORY = "H";	
	
	/**
	 * "S"--系统内定
	 */
	public static final String STATE_SYSTEM = "S";	
	//---------------数据字典管理-----------------
	/**
	 * "0"--数据字典不可修改
	 */
	public static final String CODE_STATE_READONLY = "0";
	/**
	 * "1"--数据字典可修改
	 */
	public static final String CODE_STATE_EDITABLE = "1";
	/**
	 * "N"--数据字典子类不可修改
	 */
	public static final String CODE_STATE_NOTDEFAULT = "N";
	/**
	 * "Y"--数据字典子类可修改
	 */
	public static final String CODE_STATE_DEFAULT = "Y";

	//------------------公共信息--------------
	/**
	 * "N"--数据字典子类不可修改
	 */
	public static final String INFO_STATE_NOPUBLISH = "N";
	/**
	 * "Y"--数据字典子类可修改
	 */
	public static final String INFO_STATE_PUBLISHED = "Y";
	
	//信息是否需要审核
	/**
	 * "Y"--需进行审核
	 */
	public static final String EXAMINE_Y = "Y";
	/**
	 * "N"--不需审核
	 */
	public static final String EXAMINE_N = "N";
	
	//信息审核状态
	/**
	 * "NOT_EXAMINE"--未审核
	 */
	public static final String EXAMINE_NOT = "NOT_EXAMINE";
	/**
	 * "NOT_PASS"--审核未通过
	 */
	public static final String EXAMINE_NOT_PASS = "NOT_PASS";
	/**
	 * "PASS"--审核通过
	 */
	public static final String EXAMINE_PASS = "PASS";
	/**
	 * "NEED_NOT"--无须审核
	 */
	public static final String EXAMINE_NEED_NOT = "NEED_NOT";
	


}
