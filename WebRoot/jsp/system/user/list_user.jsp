<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="pageHeader">
<form id="pagerForm" method="post"  action="listUser.do" onsubmit="return navTabSearch(this);">
	<input type="hidden" name="status" value="${param.status}" />
	<input type="hidden" name="keywords" value="${param.keywords}" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />



	<div class="searchBar">
		<table  class="searchContent" >
					<tr>
						<td>
							用户ID:<input name="userlist.userId" type="text" id=userlist.userId value="${userlist.userId}"/>
						</td>
						<td>
							用户姓名:<input name="userlist.userName" type="text" id="userlist.userName" value="${userlist.userName}"/>
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
<div class="pageContent" >
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="addUser.do" target="navTab"><span>添加</span></a></li>
			<li><a class="delete" href="deleteUser.do?uid={sid_user}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li><a class="edit" href="updateUser.do?uid={sid_user}" target="navTab"><span>修改</span></a></li>
			<li class="line">line</li>
			<%
				String userName = request.getParameter("userlist.userName")!=null?request.getParameter("userlist.userName"):"";
				userName = java.net.URLEncoder.encode(userName);
			 %>
			<li><a class="icon" href="listUserExportExcel.do?userId=${userlist.userId}&userName=<%=userName %>" target="dwzExport" targetType="navTab" title="确定要导出这些记录吗?"><span>导出EXCEL</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138" >
				<thead>
				<tr>
					<th align="center" width="30">行号</th>
					<th width="120">登录ID</th>
					<th width="120">昵称</th>
					<th  width="80">用户姓名</th>
					<!-- <th  width="80">所属部门</th> -->
					<th width="150">办公电话</th>
					<th width="80" align="center">移动电话</th>
					<th width="80">电子邮件</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${datalist}" var="obj" varStatus="status">
				<tr target="sid_user" rel="${obj.userId}">
					<td align="center">${status.index+1}</td>
					<td>${obj.userId}</td>
					<td>${obj.nickName}</td>
					<td>${obj.userName}</td>
					<!-- <td>${obj.deptName}</td> -->
					<td>${obj.officeTel}</td>
					<td>${obj.mobileTelephone}</td>
					<td>${obj.email}</td>
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
		
		<div class="pagination" targetType="navTab" totalCount="${totalCount}" numPerPage="${numPerPage}" pageNumShown="10" currentPage="${pageNum}"></div>

	</div>
</div>

