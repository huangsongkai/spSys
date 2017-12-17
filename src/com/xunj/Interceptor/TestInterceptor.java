package com.xunj.Interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.xunj.core.SessionBean;

@SuppressWarnings("serial")
public class TestInterceptor implements Interceptor {
	protected static final Log log = LogFactory.getLog(LoginInterceptor.class);
	private static final String RAW_TEXT = "rawText";

	public String rawText;

	Map stack;

	public void destroy() {
	}

	public void init() {

	}

	public String intercept(ActionInvocation invocation) throws Exception {
		
		String result = null;	
		HttpServletRequest request = ServletActionContext.getRequest();
		System.out.println("userList.userId:"+request.getParameter("userList.userId"));
		result = invocation.invoke();		
		return result;
	}

}
