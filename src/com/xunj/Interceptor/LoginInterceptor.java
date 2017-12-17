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
public class LoginInterceptor implements Interceptor {
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
		Map session = ActionContext.getContext().getSession();
		
		HttpServletRequest request = ServletActionContext.getRequest();
		SessionBean sessionbean = (SessionBean) session.get("sessionbean");
		if(sessionbean==null)
		{
			String sessionID = request.getSession().getId();
			String getSessionId = request.getParameter("sessionId");
			if(getSessionId!=null)
				result = invocation.invoke();
			else
			{
				request.setAttribute("sessionTimeOut", "true");
				request.setAttribute("url", "login");
				result="relogin";
			}
			
		}
		else
		{
			String submitKeyId = request.getParameter("xunj.submitKeyId");
			String submitKeyIdInSession = (String) session.get("submitKeyId");
			//本次提交设置了访问次数控制
			if(submitKeyId!=null)
			{
				session.put("submitKeyId", submitKeyId);
				//session中存在上次存放的提交key值
				if(submitKeyIdInSession!=null&&submitKeyId.equals(submitKeyIdInSession))
				{
					return "reSubmit";
				}
			}
			result = invocation.invoke();
		}
		return result;
	}

}
