<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/common/include.jsp"%>
	
<!-- 	
	//为部门添加用户
	function addUser()
	{
		var userIds = '${userIds}';
		var users=ModalWindow("jump.do?path=${ctx}/jsp/common/user/index.jsp&userIds="+userIds,1000,600);
		if(users!=null)
		{
			//用户信息格式：userId,userName;...
			if(confirm("确认向部门内添加所选用户"))
				window.location.href = 'insUserOperationRole.do?users='+users+'&roleId=${roleId}';    
		}
	}
	//为部门删除指定的对应用户
	function deleteUser()
	{
		var users=document.getElementsByName("user1");
		var str = xjCommon.getValueStr("user1",",");
		if(str!=null && str.length>0){
			if(confirm("确定要删除该部门关联的用户吗！")){
				var a=ModalWindow('jump.do?path=delUserOperationRole.do&users='+str+'&roleId=${roleId}',800,600);    
				if(a=="ok")
					xjSpPage.spPageSubmit();
			}
		}
		else
			alert("请选择要移除的用户!");
	}
	 -->
<div class="pageHeader"  style="border:1px #B8D0D6 solid">
<form id="pagerForm" method="post"  action="listUserOperationRole.do" onsubmit="return divSearch(this,'roleBox');">
 	<input type="hidden" name="roleId" value="${roleId }">
	<input type="hidden" name="status" value="${param.status}" />
	<input type="hidden" name="keywords" value="${param.keywords}" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />

	<div class="searchBar">
		<table  class="searchContent" >
					<tr>
						<td>
							用户ID:<input name="userId" type="text" id="userId" value="${userId}"/>
				                  <input type="hidden" name="funcGoupId" value="${roleId }"/>
						</td>
						<td>
							用户姓名:<input name="userName" type="text" id="userName" value="${userName}"/>
						</td>
					</tr>

				</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
				<!-- <li><a class="button" href="demo_page6.html" target="dialog" mask="true" title="查询框"><span>高级检索</span></a></li> -->
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="listForChooseUser.do?roleId=${roleId}" target="dialog" mask="true" title="添加"><span>添加</span></a></li>
			<li><a title="确定要删除吗?" target="selectedTodo" rel="ids" href="setDeptForUser.do?roleId=${roleId}&opFlag=delete" class="delete"><span>删除</span></a></li>
			<!-- <li><a class="delete" href="deleteCustomer.do?uid={sid_pkCustomer}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li> -->
			<!-- <li><a class="edit" href="updateCustomer.do?uid={sid_pkCustomer}" target="navTab"><span>修改</span></a></li> -->
			<li class="line">line</li>
			<%
				String userName = request.getParameter("userName")!=null?request.getParameter("userName"):"";
				userName = java.net.URLEncoder.encode(userName);
			 %>
			<li><a class="icon" href="listUserExportExcel.do?userId=${userId}&deptId=${roleId }&userName=<%=userName %>" target="dwzExport" targetType="navTab" title="确定要导出这些记录吗?"><span>导出EXCEL</span></a></li>
		</ul>
	</div>
           <table width="100%" class="table" layoutH="185" >
          
           <thead>
           	<tr>
           		<th width="40" align="center" >行号</th>
               	<th width="22" align="center"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
                <th width="120" >用户ID</th>
                <th width="150" >用户名</th>
                <th width="100">办公电话</th>
                <th width="100">电子邮件</th>
             </tr>
            </thead>
			<tbody>
			<c:forEach items="${userList}" var="obj" varStatus="i">
               <tr>
               	   <td  align="center">${i.index+1}</td>
               	   <td  align="center"><input name="ids" value="${obj.userId }" type="checkbox"></td>
                   <td width="120">${obj.userId}</td>
                   <td width="150">${obj.userName}</td>
                   <td width="100">${obj.officeTel}</td>
                   <td width="100">${obj.email}</td>
               </tr>
               </c:forEach>
            </tbody>
            </table>
            <div class="panelBar">
				<div class="pages">
					<span>显示</span>
					<select class="combox" name="numPerPage"  onchange="navTabPageBreak({numPerPage:this.value})">
						<option value="20"
						<c:if test="${numPerPage==20}">selected</c:if>
						>20</option>
						<option value="50"
						<c:if test="${numPerPage==50}">selected</c:if>
						>50</option>
						<option value="100"
						<c:if test="${numPerPage==100}">selected</c:if>
						>100</option>
						<option value="200"
						<c:if test="${numPerPage==200}">selected</c:if>
						>200</option>
					</select>
					<span>条，共${totalCount}条</span>
				</div>
				
				<div class="pagination" targetType="div" totalCount="${totalCount}" numPerPage="${numPerPage}" pageNumShown="10" currentPage="${pageNum}"></div>
		
			</div>	
    
</div>
	 
	 
	 
