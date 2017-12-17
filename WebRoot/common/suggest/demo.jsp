<%@ include file="/common/include.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<jsp:include page="../../common/head.jsp">
			<jsp:param name="_useCalendar" value="true"/>
			<jsp:param name="_useOrgSuggest" value="true"/>
		</jsp:include>
<script language="javascript">
	$().ready(function() {
	xjOrgSuggest.inputId = "suggest13";
	xjOrgSuggest.setOrgIdTo = "ids";
	xjOrgSuggest.initOrgSuggest();
})
</script>

		<title>添加组织结构信息</title>
	</head>
	<body>
部门名称<input type="text" id="suggest13" />

部门ID<input type="text" id="ids" />
	</body>
</html>
