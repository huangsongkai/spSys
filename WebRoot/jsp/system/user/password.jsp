<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../../../common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>添加用户</title>
<jsp:include page="../../../common/head.jsp"></jsp:include>
		<script language="javascript">
	        function update()
	        {
				var objarr = new Array(
					["userlist.password","原始密码","notEmpty",""],
					["newPassword","新密码","notEmpty",""],
				//	["newPassword","新密码","min","String:6"],
					["rePassword","新密码确认","notEmpty",""],
					["newPassword","两次输入密码不一致！","eqO","rePassword"]
				);
				if(xj.CheckAll(objarr))
				{
					if(window.confirm("确认修改？"))
					{
						var tmp = parseInt(Math.random()*10000+1);
						var url = 'listCheckPasswordUser.do?userlist.userId=${sessionScope.sessionbean.userId}&userlist.password='+document.getElementById("userlist.password").value+'&tmp='+tmp;
						$.get(url, {},function (data, textStatus){
							if(data=="ok")
							{
								var url = 'updatePasswordUser.do?userlist.userId=${sessionScope.sessionbean.userId}&newPassword='+document.getElementById("newPassword").value+'&tmp='+tmp;
								$.get(url, {},function (data, textStatus){
									if(data=="ok")
									{
										alert("密码修改成功！");
									}
								});
							}
							else
							{
								alert("原始密码输入错误，请重新输入！");
							}
						});
					}
				}
			}
        </script>
	</head>

	<body>
		<form action="" name="frm" id="frm" method="post">
		<div id="body_div">
	<w:ShowTitle name="修改用户密码">
	<w:TitleButtons funcParentId="001001001"/>
	<w:TitleButton funcName="保存" onClickFunction="update()" urlImg="${ctx}/images/main/save.png"/>
	</w:ShowTitle>
	<div id="body_content">
			<table width="100%">
				<tr>
					<td width="40%">
						用&nbsp;&nbsp;户&nbsp;&nbsp;ID
					</td>
					<td width="50%" class="left_align">
						<input type="text" name="userlist.userId"
							value="${sessionScope.sessionbean.userId}" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td>
						用&nbsp;&nbsp;户&nbsp;&nbsp;名
					</td>
					<td width="50%" class="left_align">
						<input type="text" name="userlist.userId"
							value="${sessionScope.sessionbean.userName}" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td>
						密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码
					</td>
					<td class="left_align">
						<input type="password" name="userlist.password" id="userlist.password" />
					</td>
				</tr>
				<tr>
					<td>
						输入新密码
					</td>
					<td class="left_align">
						<input type="password" name="newPassword" id="newPassword" />
					</td>
				</tr>
				<tr>
					<td>
						确认新密码
					</td>
					<td width="50%" class="left_align">
						<input type="password" name="rePassword" />
					</td>
				</tr>
			</table>
		</div>
		</div>
		</form>
	</body>
</html>
