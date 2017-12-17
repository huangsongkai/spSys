<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../../../common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<jsp:include page="../../../common/head.jsp">
			 <jsp:param name="_spPage" value="true"/>
			 <jsp:param name="_useCalendar" value="false"/>
			 <jsp:param name="_useTab" value="false"/>
			 <jsp:param name="_useTree" value="false"/>
			 <jsp:param name="_useModal" value="false"/>
		</jsp:include>

		<title>用户管理</title>
		<script language="JavaScript">   
		function addUser(){
			window.location.href="add_user.jsp?type=dept";
		}
		
		//修改用户
		function editUser(str){
			window.location.href="updateUser.do?userId="+str+"&type=dept";
		}
		
		//删除用户
		function delUser(str){
			if(confirm("确认删除该用户？"))
			var returnValue=ModalWindow("jump.do?path=deleteUser.do&userId="+str,800,600);
			if(returnValue=="ok")
			{
				xjSpPage.spPageSubmit();
			}
		}
	
		//重置密码
		function resetPassword(str){
			if(confirm("确认重置该用户密码？"))
			{
				var tmp = parseInt(Math.random()*10000+1);//设定随机数防止浏览器以相同url重复多次访问不进行请求
				var url = 'updateResetPasswordUser.do?tmp='+tmp+'&userId='+str;
				$.get(url, {},function (data, textStatus){
					if(data=="ok")
					{
						alert("密码重置成功，已被修改为123456!");
					}
				});
			}
		}
		
		$(function(){
			/*ligerUI工具条*/
			$("#listDeptUser").ligerToolBar({items:[
			{text:'添加',click:addUser,img:'../../../images/main/add.png'},
			{text:'查询',id:'queryButton',click:_queryDivBg,img:'../../../images/main/query.png'}
			
			]
			})
		});
	</script>
	</head>
	<body>
	<form action="listDeptUser.do" name="from1" method="post">
	<div id="listDeptUser" title="用户管理" style="height:30px"></div>
	<div id="body_div">
	
	<div id="body_content">
				<div id="queryDiv">
				<table width="100%" border="0">
					<tr>
						<td colspan="4" class="td_title">
							用户查询
						</td>
					</tr>
					<tr>
						<td>
							用户ID
						</td>
						<td class="left_align">
							<input name="userlist.userId" type="text" id="useruserId" value="${userlist.userId}"/>
						</td>
						<td>
							用户姓名
						</td>
						<td class="left_align">
							<input name="userlist.userName" type="text" id="useruserName" value="${userlist.userName}"/>
						</td>
					</tr>
					<tr>
						<td>
							分组标识
						</td>
						<td class="left_align">
							<input name="userlist.groupSign" type="text" id="groupSign" value="${userlist.groupSign}"/>
						</td>
						<td>
						</td>
						<td class="left_align">
						</td>
					</tr>
					<tr>
						<td colspan="4">
							<input name="button" type="submit" class="btn_standard" value="　查 询　" />
							<input name="button" type="button" class="btn_standard" onclick="_cancelQuery()" value="取消" />
						</td>
					</tr>
				</table>
				</div>	
				<table width="100%">
					<tr>
						<td width="120" class="td_title">登录ID</td>
						<td class="td_title">用户姓名</td>
						<td width="120" class="td_title">分组标识</td>
						<td width="180" class="td_title">电子邮件</td>
						<td width="120" class="td_title">办公电话</td>
						<td width="120" class="td_title">移动电话</td>
						<td width="160" class="td_title">
							操作
						</td>
					</tr>
					<c:forEach items="${datalist}" var="obj">
						<tr>
							<td>${obj.userId}</td>
							<td>${obj.userName}</td>
							<td>${obj.groupSign}</td>
							<td>${obj.email}</td>
							<td>${obj.officeTel}</td>
							<td>${obj.mobileTelephone}</td>
							<td>
								<a href="#" onclick="javascript:editUser('${obj.userId}')">修改</a>
								<a href="#" onclick="javascript:delUser('${obj.userId}')">删除</a>
								<a href="#" onclick="javascript:resetPassword('${obj.userId}')">密码重置</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<w:spPage />
			</div>
		</div>	
		</form>
	</body>
</html>