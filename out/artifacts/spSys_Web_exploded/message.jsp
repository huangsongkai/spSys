<%@ include file="common/include.jsp"%>

<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<style type="text/css">
.middlebody {
	margin-left: 0px;
	margin-top: 0px;
}

</style>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<META   HTTP-EQUIV="pragma"   CONTENT="no-cache">   
<META   HTTP-EQUIV="Cache-Control"   CONTENT="no-cache,   must-revalidate">   
<META   HTTP-EQUIV="expires"   CONTENT="0"> 
<title></title>
</head>  
<body class="middlebody">
<script language="JavaScript">
//提示后关闭
<c:if test="${msgTypeNew eq '1'}">
	alert('<c:out value="${msg}"/>');
	window.parent.returnValue='<c:out value="${returnValue}"/>';
</c:if>

//提示后关闭
<c:if test="${msgType eq 'Dialog'}">
	alert('<c:out value="${msg}"/>');
	window.parent.returnValue(${returnValue});
</c:if>

//提示后关闭
<c:if test="${msgType eq '1'}">
	alert('${msg}');
	//firefox IE 兼容
	window.top.returnValue='${returnValue}';
	$.pdialog.closeCurrent();   
</c:if>
//提示后跳转url
<c:if test="${msgType eq '2'}">
	alert('${msg}');
	window.location.href='${url}';
</c:if>
//直接跳转跳转url
<c:if test="${msgType eq '3'}">
	<c:if test="${target ne '' && target ne null}">
		window.location.target='${target}';
	</c:if>
	<c:if test="${submitTg ne '' && submitTg ne null}">
		window.parent.frame_left.location.reload();
	</c:if>
		window.location.href='${url}';
</c:if>
//退出
<c:if test="${quit eq 'true'}">
	window.close();
</c:if>
//退出
<c:if test="${quit eq 'false'}">
	window.open("${ctx}/login.htm");
	window.top.close();
</c:if>
//退出_返回登录页
<c:if test="${quit_login eq 'false'}">
	alert('${msg}');
	window.open("${ctx}/login.htm");
	window.top.close();
</c:if>
</script>
</body>
</html>
