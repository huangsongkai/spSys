package com.xunj.action.common;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


/**
 * 公共跳转Action
 * 
 */
public class JumpAction extends AbstractAction {

	private Map left;
	private Map right;
	/**
	 * 传递参数跳转到指定URL，用于middle页跳转
	 */
	public String GoUrl() {

		String path = request.getParameter("path");
		int width = Integer.parseInt((String) request.getParameter("width"));
		int height = Integer.parseInt((String) request.getParameter("height"))-4;
		String url = path+"?a=b";
		Map map=request.getParameterMap();
		Iterator its = map.entrySet().iterator();
		
		while (its.hasNext()) {
			Entry e = (Entry) its.next();

			if ((!e.getKey().equals("path"))
					&& (!e.getKey().equals("width"))
					&& (!e.getKey().equals("height"))) {
				String[] values = (String[]) e.getValue();
				String name = (String) e.getKey();
				for (int i = 0; i < values.length; i++) {
					url = url + "&" + name + "=" + values[i];
				}
			}
		}
		request.setAttribute("width", width);
		request.setAttribute("height", height);
		request.setAttribute("url", url);
		return toJsp("/middle.jsp");
	}
	/**
	 * 传递参数跳转到指定URL，用于index页跳转
	 */
	public String GoIndexUrl() {
		String leftUrl="";
		String rightUrl="";
		String width=request.getParameter("width");
		String[] temp;
		if(left!=null){
			temp =(String[]) left.get("url"); 
			leftUrl = temp[0]+"?a=b";
			Iterator its = left.entrySet().iterator();
			for(;its.hasNext();){
				Entry en = (Entry)its.next();
				if(!"url".equals(en.getKey()))
				{
					temp =(String[]) en.getValue(); 
					leftUrl = leftUrl+"&"+en.getKey()+"="+temp[0];
				}
			}
		}
		if(right!=null){
			temp =(String[]) right.get("url"); 
			rightUrl = temp[0]+"?a=b";
			Iterator its = right.entrySet().iterator();
			for(;its.hasNext();){
				Entry en = (Entry)its.next();
				if(!"url".equals(en.getKey()))
				{
					temp =(String[]) en.getValue(); 
					rightUrl = rightUrl+"&"+en.getKey()+"="+temp[0];
				}
			}
		}
//		System.out.println(leftUrl);
//		System.out.println(rightUrl);
		request.setAttribute("leftUrl", leftUrl);
		request.setAttribute("rightUrl", rightUrl);
		request.setAttribute("width", width);
		return toJsp("/comon_index.jsp");
	}
	
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
		return toJsp("index.jsp");
	}
	public Map getLeft() {
		return left;
	}
	public void setLeft(Map left) {
		this.left = left;
	}
	public Map getRight() {
		return right;
	}
	public void setRight(Map right) {
		this.right = right;
	}
	
}
