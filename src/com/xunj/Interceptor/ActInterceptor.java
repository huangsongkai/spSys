package com.xunj.Interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.xunj.core.SessionBean;
import com.xunj.po.SysFuncInfo;

@SuppressWarnings("serial")
public class ActInterceptor implements Interceptor {
	protected static final Log log = LogFactory.getLog(ActInterceptor.class);


	public void destroy() {
	}

	public void init() {

	}

	public String intercept(ActionInvocation invocation) throws Exception {
		
		String result = null;	
		boolean me=false;
		HttpServletRequest request = ServletActionContext.getRequest();
		String uri=request.getRequestURI();
		String cp=request.getContextPath();
		String menu=uri.substring(cp.length()+1, uri.length());	
		String ref=request.getHeader("referer");
		//System.out.println("===ref:"+ref);
		List<SysFuncInfo> menuList=(ArrayList) request.getSession().getAttribute("menu");
		if(menuList!=null ){
			//System.out.println("~~~~~~~~~~~~~~~~"+menuList.size()+"~~~~~~~~~~~~~~~~~~~~~");
			for(int k=0;k<menuList.size();k++)
			{
				//System.out.println("menu:"+menu);
				String url=menuList.get(k).getUrl();
				//System.out.println("url1:"+url);
				if(url!=null){
					int index=url.indexOf("?");
					if(index>0){
						url=url.substring(0, index);
					}			
				}		
//				System.out.println("url2:"+url);
//				System.out.println();
//				System.out.println();
//				System.out.println();
//				System.out.println();
				
				if(menu.equals(url)){
					me=true;
					break;
				}			
			}
		}
//		System.out.println("me:"+me);
		if(!me){
//			System.out.println("mememe-menu:"+menu);
		}
		me=true;
		if(me){
			result = invocation.invoke();					
		}else{
			request.getSession().setAttribute("sessionTimeOut", "没有权限，不能访问");
			request.getSession().setAttribute("referer", ref);
			result="relogin";
		}
		return result;
	}
}
