package com.xunj.util;

import javax.servlet.http.HttpServletRequest;

import net.fckeditor.requestcycle.UserPathBuilder;

import com.xunj.core.SessionBean;

public class FCKUserPath implements UserPathBuilder {

	public String getUserFilesPath(HttpServletRequest arg0) {
		// TODO 自动生成方法存根
		String path = arg0.getContextPath()+"/userfiles/" + SessionBean.getSessionBean(arg0).getUserId();
		return path;
	}

	public String getUserFilesAbsolutePath(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		String path = "/userfiles/" + SessionBean.getSessionBean(arg0).getUserId();
		return path;
	}
}