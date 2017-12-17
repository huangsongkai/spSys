<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/common/include.jsp"%>
<%@ include file="/common/include2.jsp"%>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<script type="text/javascript" src="../../../common/js/steppage.js"></script>
	<script type="text/javascript" src="../../../common/js/common.js"></script>
	<script type="text/javascript" src="../../../common/jquery/jquery_json.js"></script>
	<script type="text/javascript" src="../../../common/js/JSONUtils.js"></script>
	<link rel="stylesheet" href="../../../common/css/mainStyle.css" type="text/css"/>
	<title>按人分配权限</title>
	<script language="JavaScript">   
		//分配权限
		function editUserFun(str1,str2){
			ModelWindow_dialog("addFuncByUserFunc.do?userId="+str1+"&userName="+str2,800,600);
		}
		
		function doSearch1()
		{
			var userName=document.getElementById("userName").value;
			document.form1.jsonContent.innerHTML='{"beanName":"userlist","beanFields":[{"fieldName":"userName","dataType":"String","role":"like","value":"'+userName+'"}],"orders":'+
'[{"fieldName":"userName","orderSeq":"1","order":"desc"}]}';
			document.form1.action='system~userFunc~lstByUser_userFunc@listCommon.do?'+
			'jsonContent={"beanName":"userlist","beanFields":[{"fieldName":"userName","dataType":"String","role":"like","value":"'+userName+'"}],"orders":'+
'[{"fieldName":"userName","orderSeq":"1","order":"desc"}],"or":[]}';
			document.form1.submit();
		}
		function showPage(varType)
		{
			if(varType=="user")
			{
				window.location.href='system~userFunc~lstByUser_userFunc@listCommon.do?jsonContent={"beanName":"userlist","orders":[{"fieldName":"userName","orderSeq":"1","order":"desc"}]}';
			}
			else if(varType=="group")
			{
				window.location.href='system~userFunc~lstByGroup_userFunc@listCommon.do?jsonContent={"beanName":"funcGroup","orders":[{"fieldName":"funcGroupName","orderSeq":"1","order":"desc"}]}';
			
			}
		}
	</script>
	</head>
	<body>
		<form action='system~userFunc~lstByUser_userFunc@listCommon.do?jsonContent={"beanName":"userlist",
		"beanFields":[{"fieldName":"state","dataType":"String","role":"eq","value":"A"}],
		"orders":[{"fieldName":"userName","orderSeq":"1","order":"desc"}]}' name="form1"  method="post">
			<textarea name="jsonContent" id="jsonContent" rows="10" cols="80" style="display:none">${getValue.jsonContent}</textarea>
			<table width="100%">
				<tr>
					<th>用户权限管理</th>
				</tr>
				<tr>
					<td class="right_align">
						<input type="button" onClick="showPage('user')" class="btn_standard" value="按人分配">
						<input type="button" onClick="showPage('group')" class="btn_standard" value="按组分配">
					</td>
				</tr>
			</table>
			<div  align="center">
				<table width="100%">
					<tr>
					 <td>姓名：</td>
					 <td class="left_align">
						<input name="userName" type="text" id="userName" value="${getValue.userName}" beanName="userlist" fieldName="userName" dataType="String"  role="like" order="desc" orderSeq="1"/>
				        <input name="state" type="hidden" id="state" value="A" beanName="userlist" fieldName="state" dataType="String"  role="eq"/>  
					 </td>
					</tr>
					<tr>
					 <td colspan="2">
						<input name="button" type="button" class="btn_standard"  onClick="doSearch1()" value="　查 询　"/>
					 </td>
					</tr>
				</table>
				<table width="100%" cellspacing="0" >
				  <tr class="td_title">
				    <td width="120">账号</td>
				    <td width="120">姓名</td>
				    <td>性别</td>
				    <td>办公电话</td>
				    <td>电子邮件</td>
				    <td width="120">操作</td>
				    </tr>
				    <c:choose>
					<c:when test="${fn:length(datalist)>0 }">
				    <c:forEach items="${datalist}" var="obj" >
				        <tr>
				        <td>${obj.userId }</td>
				        <td>${obj.userName }</td>
				        <td>        
							<c:if test="${obj.gender eq 'male'}">男</c:if>
							<c:if test="${obj.gender eq 'female'}">女</c:if>
						</td>
				        <td>${obj.officeTel }</td>
				        <td >${obj.email }</td>
				        <td>
					        <a href="javascript:editUserFun('${obj.userId }','${obj.userName }')">分配权限</a>
						</td>
				        </tr>
				    </c:forEach>
				    </c:when>
			   		<c:otherwise>
			               	<tr><td colspan="6">暂无符合条件的数据！</td></tr>
              		</c:otherwise>
               </c:choose>
				</table>
				<w:spPage/>
			</div>
		
		</form>
	</body>
</html>
