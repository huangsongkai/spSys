<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="../../../common/include.jsp"%>
<html>
	<head>
		<title>��־����</title>
		<meta http-equiv="content-type" content="text/html; charset=GBK">
		<script type="text/javascript" src="../../../common/jquery/jquery-1.4.2.min.js"></script>
		<script type='text/javascript' src='../../../common/js/common.js'></script>
		<script type="text/javascript" src="../../../common/js/steppage.js"></script>
		<script type="text/javascript" src="../../../common/My97DatePicker/WdatePicker.js"></script>
		<link rel="stylesheet" href="../../../common/css/mainStyle.css" type="text/css" />
		<script language="javascript" src="../../../common/js/xjValidate.js"></script>

		<SCRIPT LANGUAGE="javaScript">
	//����
	function exportLog()
	{
		if(confirm("�������Ϸ���ѯ��������־���������������ݽ���ɾ��,ȷ��Ҫ������־��"))
		{
			form1.target="_blank";
			form1.action="removeLog.do";
			form1.submit();
			queryLog();
		}	
	}
	//��ѯ
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
								��־����
							</th>
						</tr>
						<tr>
							<td width="110">
								�û�����
							</td>
							<td width="120" class="left_align">
								<input type="text" name="loginfo.userName" maxlength="10" value="${loginfo.userName}">
							</td>
							<td width="110">
								�û��ʺ�
							</td>
							<td width="120" class="left_align">
								<input type="text" name="loginfo.userId" maxlength="20" value="${loginfo.userId}">
							</td>
							<td width="110">
								�û�IP
							</td>
							<td width="120" class="left_align">
								<input type="text" name="loginfo.ipAddress" maxlength="50" value="${loginfo.ipAddress}">
							</td>
						</tr>
						<tr>
							<td width="110">
								��������
							</td>
							<td width="120" class="left_align">
								<input type="text" name="loginfo.handleType" maxlength="20" value="${loginfo.handleType}">
							</td>
							<td width="110">
								��ʼʱ��
							</td>
							<td width="120" class="left_align">
								<input name="dateFrom" value="${dateFrom}" type="text" onClick="WdatePicker()" readOnly="readOnly" class="inputCal">
							</td>
							<td width="110">
								����ʱ��
							</td>
							<td width="120" class="left_align">
							<input name="dateTo" value="${dateTo}" type="text" onClick="WdatePicker()" readOnly="readOnly" class="inputCal">
							</td>
						</tr>
						<tr>
							<td colspan="6">
								<input name="new_button5" type="button" class="btn_standard" onClick="queryLog()" value="��  ѯ">
									<input name="new_button5" type="button" onClick="exportLog()" class="btn_standard" value="��  ��">
							</td>
						</tr>
					</table>

					<table  class="table"  width="100%" >
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
								����
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
										�鿴
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
