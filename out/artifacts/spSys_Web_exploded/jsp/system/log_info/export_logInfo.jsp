<%@ page contentType="application/vnd.ms-excel;charset=GBK" %>
<%@ include file="../../../common/include.jsp"%>
<html>
	<head> 
		<title></title>
		<meta http-equiv="content-type" content="text/html; charset=GBK">
	</head>
	<body>
		<table width="100%" border="1">
			<tr>
				<td class="td_title">
					����ʱ��
				</td>
				<td class="td_title">
					��������
				</td>
				<td class="td_title">
					�û�IP
				</td>
				<td class="td_title">
					�û��ʺ�
				</td>
				<td class="td_title">
					�û�����
				</td>
				<td class="td_title">
					��־����
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
						${l.userId}
					</td>
					<td>
						${l.userName}
					</td>
					<td>
						${l.handleCon}
					</td>
					</tr>
				</c:forEach>
		</table>
	</body>
</html>
