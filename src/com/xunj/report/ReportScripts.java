package com.xunj.report;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;

public class ReportScripts extends JRDefaultScriptlet{
	/**
	 *
	 */
	public void beforeReportInit() throws JRScriptletException
	{
		System.out.println("---------------beforeReportInit--------------");
	}


	/**
	 *
	 */
	public void afterReportInit() throws JRScriptletException
	{
		System.out.println("---------------afterReportInit--------------");
	}


	/**
	 *
	 */
	public void beforePageInit() throws JRScriptletException
	{
		System.out.println("---------------beforePageInit--------------");
	}


	/**
	 *
	 */
	public void afterPageInit() throws JRScriptletException
	{
		System.out.println("---------------afterPageInit--------------");
	}


	/**
	 *
	 */
	public void beforeColumnInit() throws JRScriptletException
	{
		System.out.println("---------------beforeColumnInit--------------");
	}


	/**
	 *
	 */
	public void afterColumnInit() throws JRScriptletException
	{
		System.out.println("---------------afterColumnInit--------------");
	}


	/**
	 *
	 */
	public void beforeGroupInit(String groupName) throws JRScriptletException
	{
		System.out.println("---------------beforeGroupInit--------------"+groupName);
	}


	/**
	 *
	 */
	public void afterGroupInit(String groupName) throws JRScriptletException
	{
		System.out.println("---------------afterGroupInit--------------"+groupName);
	}


	/**
	 *
	 */
	public void beforeDetailEval() throws JRScriptletException
	{
		System.out.println("---------------beforeDetailEval--------------"+this.getFieldValue("bookPrice"));
		this.setVariableValue("v_test", "hahha");
		System.out.println(this.getVariableValue("v_test"));
		super.beforeDetailEval();
	}


	/**
	 *
	 */
	public void afterDetailEval() throws JRScriptletException
	{
		System.out.println("---------------afterDetailEval--------------");
		this.setVariableValue("v_test", "hahha1");
		System.out.println(this.getVariableValue("v_test"));
	}


}
