<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="../../../common/include.jsp"%>
<html>
	<head>
		<title>日志管理</title>
		<meta http-equiv="content-type" content="text/html; charset=GBK">
		<script type="text/javascript" src="../../../common/jquery/jquery-1.4.2.min.js"></script>
		<script type='text/javascript' src='../../../common/js/common.js'></script>
		<script type="text/javascript" src="../../../common/js/steppage.js"></script>
		<script type="text/javascript" src="../../../common/My97DatePicker/WdatePicker.js"></script>
		<link rel="stylesheet" href="../../../common/css/mainStyle.css" type="text/css" />
		<script language="javascript" src="../../../common/js/xjValidate.js"></script>

		<SCRIPT LANGUAGE="javaScript">
	//导出
	function exportLog()
	{
		if(confirm("将按照上方查询条件将日志导出，导出的数据将被删除,确定要导出日志吗？"))
		{
			form1.target="_blank";
			form1.action="removeLog.do";
			form1.submit();
			queryLog();
		}	
	}
	//查询
	function queryLog()
	{
		form1.target="_self";
		form1.action="listLog.do";
		form1.submit();
	}
	
	function showLog(src){
		var url="showLog.do?loginfo.logId="+src;
		var a = ModelWindow_dialog(url,800,600);
		
	}
</SCRIPT>
	</head>
	<body>
		<form id="form1" method="post" action="listLog.do">
			<div align="center">
					<table width="100%">
						<tr>
							<th colspan="7">
								日志管理
							</th>
						</tr>
						<tr>
							<td width="110">
								用户姓名
							</td>
							<td width="120" class="left_align">
								<input type="text" name="loginfo.userName" maxlength="10" value="${loginfo.userName}">
							</td>
							<td width="110">
								用户帐号
							</td>
							<td width="120" class="left_align">
								<input type="text" name="loginfo.userId" maxlength="20" value="${loginfo.userId}">
							</td>
							<td width="110">
								用户IP
							</td>
							<td width="120" class="left_align">
								<input type="text" name="loginfo.ipAddress" maxlength="50" value="${loginfo.ipAddress}">
							</td>
						</tr>
						<tr>
							<td width="110">
								操作类型
							</td>
							<td width="120" class="left_align">
								<input type="text" name="loginfo.handleType" maxlength="20" value="${loginfo.handleType}">
							</td>
							<td width="110">
								开始时间
							</td>
							<td width="120" class="left_align">
								<input name="dateFrom" value="${dateFrom}" type="text" onClick="WdatePicker()" readOnly="readOnly" class="inputCal">
							</td>
							<td width="110">
								结束时间
							</td>
							<td width="120" class="left_align">
							<input name="dateTo" value="${dateTo}" type="text" onClick="WdatePicker()" readOnly="readOnly" class="inputCal">
							</td>
						</tr>
						<tr>
							<td colspan="6">
								<input name="new_button5" type="button" class="btn_standard" onClick="queryLog()" value="查  询">
									<input name="new_button5" type="button" onClick="exportLog()" class="btn_standard" value="导  出">
							</td>
						</tr>
					</table>

					<table  class="table"  width="100%" >
						<tr>
							<td class="td_title">
								操作时间
							</td>
							<td class="td_title">
								操作类型
							</td>
							<td class="td_title">
								用户IP
							</td>
							<td class="td_title">
								用户帐号
							</td>
							<td class="td_title">
								用户名称
							</td>
							<td class="td_title">
								操作
							</td>
							
						</tr>

						<c:forEach items="${logList}" var="l" varStatus="indx">
							<tr>
								<td>
									${l.handleTime}
								</td>
								<td >
									${l.handleType}
								</td>
								<td>
									${l.ipAddress}
								</td>
								<td>
									<c:out value="${l.userId}" />
								</td>
								<td>
									<c:out value="${l.userName}" />
								</td>
								<td>
									<a href="#" onclick="showLog('${l.logId}')">
										查看
									</a>
								</td>
								
							</tr>
						</c:forEach>
					</table>
					<w:spPage></w:spPage>
			</div>
		</form>
	</body>
</html>
