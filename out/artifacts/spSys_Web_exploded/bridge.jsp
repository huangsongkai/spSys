<%@ include file="/common/include.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<META   HTTP-EQUIV="pragma"   CONTENT="no-cache">
<META   HTTP-EQUIV="Cache-Control"   CONTENT="no-cache,   must-revalidate">   
<META   HTTP-EQUIV="expires"   CONTENT="0"> 

    
    <title>${applicationScope.applicationName}</title>
<script language="JavaScript">
    function SetCookie(name,value)//两个参数，一个是cookie的名子，一个是值
	{ 
		 var Days = 30;
		 var exp = new Date(); //new Date("December 31, 9998");
		 exp.setTime(exp.getTime() + Days*24*60*60*1000); //此 cookie 将被保存 30 天
		 document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
	}
	<c:if test="${passed eq 'yes'}">
				 var m_url = "main.jsp";
				 var m_height = parseInt(screen.availHeight-25);
				 var m_width = screen.availWidth-10;
				 SetCookie('aktm','${userId}');
				 window.location="main.do";
	</c:if>
	<c:if test="${passed eq 'yes'}">
		parent.location.href="main.do";
	</c:if>
	<c:if test="${passed eq 'noUser'}">
		alert("用户名错误，请重新输入！");
		window.location.href="login.jsp";
	</c:if>
	<c:if test="${passed eq 'pwderr'}">
		alert("密码错误，请重新输入！");
		window.location.href="login.jsp";
	</c:if>
	<c:if test="${passed eq 'deleted'}">
		alert("该用户已被删除，无法登录！");
		window.location.href="login.jsp";
	</c:if>
	<c:if test="${passed eq 'nodb'}">
		alert("数据库连接失败！");
		window.location.href="login.jsp";
	</c:if>
</script>
  </head>

  <body>

	
  </body>
</html>
