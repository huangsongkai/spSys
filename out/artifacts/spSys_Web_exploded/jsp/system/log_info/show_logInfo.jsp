<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="../../../common/include.jsp"%>
<html>
	<head> 
		<title>��־����</title>
		<link rel="stylesheet" href="../../../common/css/mainStyle.css" type="text/css" />
	</head>
	<body>
	<table width="98%">
			<tr>
				<th colspan="4">��־�鿴</th>
			</tr>
			<tr>
				<td>�û��ʺ�</td>
				<td class="left_align">
					${loginfo.userId}
				</td>
				<td>�û�����</td>
				<td class="left_align">
					${loginfo.userName}
				</td>
			</tr>
			<tr>
				<td>�û�IP 	</td>
				<td class="left_align">
					${loginfo.ipAddress}
				</td>
				<td>��������D</td>
				<td class="left_align">
					${loginfo.handleType}
				</td>
			</tr>
			<tr>
				<td>����ʱ��</td>
				<td class="left_align" colspan="3">
					${loginfo.handleTime}
				</td>
			</tr>
			<tr>
				<td colspan="4">
					����
				</td>
			</tr>
			<tr>
				<td colspan="4">
					${loginfo.handleCon}
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<input type="button" name="Submit2" value="ȡ  ��" class="btn_standard" onClick="window.parent.closeDialog()">
				</td>
			</tr>
		</table>
	</body>
</html>
