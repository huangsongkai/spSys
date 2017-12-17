package com.xunj.action.system;

import com.xunj.action.common.AbstractAction;


/**
 * 系统退出
 * 
 */
public class QuitAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5977008215778431424L;

	/**
	 * 注销、退出
	 */
	public String LogOut() {
		// 清空session
		request.getSession().invalidate();
		if (request.getParameter("quit") != null)
			request.setAttribute("quit", "true");
		else
			request.setAttribute("quit", "false");
		return toJsp("/login.jsp");
	}
	/**
	 * 注销、退出
	 */
	public String VendorLogOut() {
		// 清空session
		request.getSession().invalidate();
		if (request.getParameter("quit") != null)
			request.setAttribute("quit", "true");
		else
			request.setAttribute("quit", "false");
		return toJsp("/vendor_login.jsp");
	}
}
