<%@ include file="/common/include.jsp" %>
<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
<jsp:include page="../../../common/head.jsp"></jsp:include>
<script language="javascript">
		var path1="listFuncInfo.do";
		var path2="${ctx}/blank_common.jsp?title=功能菜单";
		var url = conversionUrl(path1,path2,280);
		window.location.href="jump!GoIndexUrl.do?a=b"+url;
</script>

<title></title>
</head>
<body>
</body>
</html>
