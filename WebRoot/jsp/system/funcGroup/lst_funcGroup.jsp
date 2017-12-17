<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../../../common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>权限组管理</title>
<jsp:include page="../../../common/head.jsp">
	 <jsp:param name="_spPage" value="true"/>
</jsp:include>
	<script language="javaScript">
	//添加权限组
	function insFuncGroup()
	{
		window.parent.frame_right.location.href="addFuncGroup.do";
	}
	//修改权限组
	function edit(str)
	{
		window.parent.frame_right.location.href="updateFuncGroup.do?funcGroupId="+str;
	}
	//删除权限组
	function del(str)
	{
		if(confirm("确定要删除该权限组吗？"))
		{
			var a=ModalWindow("jump.do?path=removeFuncGroup.do&funcGroupId="+str,800,600);
			if(a=="ok")
				xjSpPage.spPageSubmit();
		}	
	}
	//查询该功能组对应的用户集合
	function showUser(funcGroupId)
	{
		window.parent.frame_right.location.href="listUserFuncGroup.do?funcGroupId="+funcGroupId;
	}
	</script>
	</head>
	<body>
		<form name="form1" method="post" action='listFuncGroup.do'>
<div id="body_div">
	<w:ShowTitle name="角色管理">
	<w:TitleButtons funcParentId="001001001"/>
	<w:TitleButton funcName="添加" onClickFunction="insFuncGroup()" urlImg="${ctx}/images/main/add.png"/>
	</w:ShowTitle>
	<div id="body_content">
				<table width="100%">
					<tr>
						<td width="160" class="td_title">角色名称</td>
						<td class="td_title">角色说明</td>
						<td width="100" class="td_title">操作</td>
					</tr>

					<c:forEach items="${datalist}" var="funcGroupList" varStatus="indx">
						<tr>
							<td>${funcGroupList.funcGroupName}</td>
							<td>${funcGroupList.funcGroupDescription}</td>
							<td>
								<a href="#" onclick="edit('${funcGroupList.funcGroupId}')">修改</a>
								<a href="#" onclick="del('${funcGroupList.funcGroupId}')">删除</a>
								<a href="#" onclick="showUser('${funcGroupList.funcGroupId}')">人员</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<w:spPage style="mini"/>
			</div>
			</div>
		</form>
	</body>
</html>
