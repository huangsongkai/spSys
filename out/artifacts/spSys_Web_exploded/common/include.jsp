<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.xunj_prj/tag/webwidget" prefix="w"%>
<%@ taglib uri="http://www.xunj_prj/tag/webfunction" prefix="wfn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%	
	response.setHeader("Pragma","No-Cache");
	response.setHeader("Cache-Control","No-Cache");
	response.setDateHeader("Expires", 0);
%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="../common/firefoxBridge/printUtils.js"></script>
		<script type="text/javascript" src="../common/firefoxBridge/common.js"></script>
		<script type="text/javascript" src="../common/firefoxBridge/bugUtils.js"></script>
		<script type="text/javascript" src="../common/firefoxBridge/mifareUtils.js"></script>
		<script type="text/javascript" src="../common/firefoxBridge/findUtils.js"></script>
		<script type="text/javascript" src="../common/firefoxBridge/tabUtils.js"></script>
		<script type="text/javascript" src="../common/calendar/time.js"></script>
		<script src="../common/dwz-ria/js/dwz.database.js" type="text/javascript"></script>
