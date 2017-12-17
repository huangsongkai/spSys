<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=utf-8" %>
<%	
	response.setHeader("Pragma","No-Cache");
	response.setHeader("Cache-Control","No-Cache");
	response.setDateHeader("Expires", 0);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="content-type" content="text/html; charset=utf-8"><META   HTTP-EQUIV="pragma"   CONTENT="no-cache">   <META   HTTP-EQUIV="Cache-Control"   CONTENT="no-cache,   must-revalidate">   <META   HTTP-EQUIV="expires"   CONTENT="0"> 

<head>

<link href="../../../common/css/mainStyle.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
}
-->
</style></head>
<body scroll="no">

	<iframe name="rr" id="rr" src='<c:out value="${url}"/>' frameborder="0" width='<c:out value="${width}"/>' height='<c:out value="${height}"/>' scrolling="auto"></iframe>

</body>
</html>
