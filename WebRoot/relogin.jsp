<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<style type="text/css">
.middlebody {
	margin-left: 0px;
	margin-top: 0px;
	background-image: url(${path}/general/oa/common/img/doing_3.jpg);
	background-repeat:no-repeat;
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

	alert('登录超时，请重新登录！');
	window.top.close();
	window.top.location.href = "login.jsp";

</script>${sessionTimeOut}1
</body>
</html>
