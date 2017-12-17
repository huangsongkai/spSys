package com.xunj.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.ServletContext;


public class RunWorkThread extends Thread {

	ServletContext application;

	public RunWorkThread(ServletContext application) {
		this.application = application;
	}

	public void run() {

		try {
			Calendar c = Calendar.getInstance();
//			int wd = c.get(Calendar.DAY_OF_WEEK);
			int h = c.get(Calendar.HOUR_OF_DAY);
			// 每周日23点执行任务
			if(h==23)
			{
				
				System.out.println("---------------------开始自动同步部门用户数据" + Common.getCurrentDateTime() + "----------------------");
				URL stdURL = null;
				HashMap config = (HashMap) application.getAttribute("sysConfig");
				String url = (String) config.get("AutoUrl1");
				try {
					stdURL = new URL(url);
					stdURL.openStream();
				} catch (MalformedURLException e) {
					throw e;
				}
				System.out.println("---------------------自动同步部门用户数据完成" + Common.getCurrentDateTime() + "----------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
