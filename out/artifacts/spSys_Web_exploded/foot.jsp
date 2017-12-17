<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 liansitional//EN" "http://www.w3.org/li/xhtml1/DTD/xhtml1-liansitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>top</title>
<script type="text/javascript" src="common/jquery/jquery.min.js"></script>
<link href="common/css/bottom.css" rel="stylesheet" type="text/css" />
<SCRIPT type=text/javascript>
	
	$.ready = function()
	{
		var isIE6Up = true;
		if(navigator.appName == "Microsoft Internet Explorer") 
		{
			if(navigator.appVersion.match(/MSIE 6./i)=='MSIE 6.') 
			{
				isIE6Up=false; 
			}
		} 
		setInterval("showTime('"+isIE6Up+"')",1000);
	}
	function showTime(test)
	{
		var d=new Date();
		var week=' 星期'+'日一二三四五六'.charAt(new Date().getDay());
		if(test=="false")
			week="";
		document.getElementById('dateTime').innerHTML=d.toLocaleString()+week;
	}
	function delCookie(name)
	{
 		var exp = new Date();
 		exp.setTime(exp.getTime() - 1);
 		var cval=getCookie(name);
 		if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();
	} 
	function getCookie(name)
	{
	 var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
	 if(arr != null) 
	 return unescape(arr[2]); 
	 return null;
	} 
	function quit()
	{
		if(confirm("确认退出？"))
		{
			delCookie('aktm');
			window.top.location.href="quit!LogOut.do";
		}
	}
	
	function showMain()
	{
		window.parent.mainFrame.location.href = "mainright.jsp";
	}
	
	function updPassword()
	{
		parent.mainFrame.location.href = "jsp/system/user/password.jsp";
		return false;
	}
	
	
</SCRIPT>
</head>

<body>
<div class="statusBar">
	<ul>
		<li id="currentUser">当前用户：${sessionScope.sessionbean.userId}</li>
		<li id="splitLine">&nbsp;</li>
		<li id="currentDept">所在部门：
		
		</li>
		<li id="splitLine">&nbsp;</li>
		<li id="usualFeatures">
			<!-- 消息 -->
			<a href="jsp/main/desktop/lst_myMessage.htm" target="mainFrame"><img src="images/bottom/message1.png" width="16" height="16" title="我的消息" /><span style="position:relative;top:-3px; ">(3)</span></a>&nbsp;
			<a href="#" onclick='showMain()'><img src="images/bottom/home.png" width="16" height="16" title="首页" /></a>&nbsp;&nbsp;
			<a href="jsp/main/desktop/index_desktop.htm" target="mainFrame"><img src="images/bottom/desktop.png" width="16" height="16" title="我的工作台" /></a>&nbsp;&nbsp;
			<a href="#" onclick='updPassword()'><img src="images/bottom/password.png" width="16" height="16" title="修改密码" /></a>&nbsp;&nbsp;
			<a href="#" onclick='quit()'><img src="images/bottom/quit.png" width="16" height="16" title="退出" /></a>&nbsp;&nbsp;
		</li>
	</ul>
	<div>
	<ul id="rightBar">
		<li id="splitLine">&nbsp;</li>
		<li id="dateTime"></li>
	</ul>
	</div>
</div>
</body>
</html>
