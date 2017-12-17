<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="common/include.jsp"%>
<%	
	response.setHeader("Pragma","No-Cache");
	response.setHeader("Cache-Control","No-Cache");
	response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
<meta   http-equiv="pragma"   content="no-cache"/>
<meta   http-equiv="Cache-Control"   content="no-cache,   must-revalidate"/>
<meta   http-equiv="expires"   content="0"/> 
<link rel="stylesheet" href="${ctx}/common/css/mainStyle.css" type="text/css" />
<script type="text/javascript" src="${ctx}/common/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/common/js/main.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title></title>
</head>

<body class="liftbody">
<table width="100%" border="0" class="tableNoBorder">
	<tr>
	 <td width="${width}"  valign="top">
	 	<iframe name="frame_left" id="frame_left" src='${leftUrl}' width="100%" frameborder="0" scrolling="no"></iframe>
	 </td>
	<td valign="top" >
		<iframe name="frame_right" id="frame_right" src='${rightUrl}' width="100%" frameborder="0" scrolling="no"></iframe>
	</td>
	</tr>
</table>
<script type="text/javascript">
	_init_iFrame_height("frame_left");
	_init_iFrame_height("frame_right");
</script>
</body>
</html>
