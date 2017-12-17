<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="../../../common/include.jsp"%>
<html>
	<head> 
		<title>日志管理</title>
		<link rel="stylesheet" href="../../../common/css/mainStyle.css" type="text/css" />
	</head>
	<body>
	<table width="98%">
			<tr>
				<th colspan="4">日志查看</th>
			</tr>
			<tr>
				<td>用户帐号</td>
				<td class="left_align">
					${loginfo.userId}
				</td>
				<td>用户姓名</td>
				<td class="left_align">
					${loginfo.userName}
				</td>
			</tr>
			<tr>
				<td>用户IP 	</td>
				<td class="left_align">
					${loginfo.ipAddress}
				</td>
				<td>操作类型D</td>
				<td class="left_align">
					${loginfo.handleType}
				</td>
			</tr>
			<tr>
				<td>操作时间</td>
				<td class="left_align" colspan="3">
					${loginfo.handleTime}
				</td>
			</tr>
			<tr>
				<td colspan="4">
					内容
				</td>
			</tr>
			<tr>
				<td colspan="4">
					${loginfo.handleCon}
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<input type="button" name="Submit2" value="取  消" class="btn_standard" onClick="window.parent.closeDialog()">
				</td>
			</tr>
		</table>
	</body>
</html>
