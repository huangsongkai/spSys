<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="pageHeader">
	<form id="pagerForm" rel="pagerForm" method="post" action="listForChooseUser.do?roleId=${param.roleId}" onsubmit="return dialogSearch(this, 'dialog');">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<div class="searchBar">
		<ul class="searchContent">
			<table  class="searchContent" >
				<tr>
					<td>
						用户ID:<input name="userlist.userId" type="text" id="userlist.userId" value="${userlist.userId}"/>
					</td>
					<td>
						用户姓名:<input name="userlist.userName" type="text" id="userlist.userName" value="${userlist.userName}"/>
					</td>
				</tr>

			</table>			
		</ul>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
				<li><a title="确定选择这些用户吗？" target="selectedTodo" targetType="dialog" rel="ids" href="setDeptForUser.do?roleId=${param.roleId}&opFlag=add" class="button"><span>选择用户</span></a></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" layoutH="118"  width="100%">
		<thead>
			<tr>
				<th>行号</th>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th>用户ID</th>
				<th>用户名</th>
				<th>所属部门</th>
				<th>办公电话</th>
				<th>电子邮件</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${datalist}" var="obj" varStatus="status">
				<tr >
					<td align="center">${status.index+1}</td>
					<td align="center"><input name="ids" value="${obj.userId}" type="checkbox"></td>
					<td>${obj.userId}</td>
					<td>${obj.userName}</td>
					<td>${obj.deptName}</td>
					<td>${obj.officeTel}</td>
					<td>${obj.email}</td>
				</tr>
			</c:forEach>
		</tbody>	
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage"  onchange="dialogPageBreak({numPerPage:this.value})">
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
		
		<div class="pagination" targetType="dialog" totalCount="${totalCount}" numPerPage="${numPerPage}" pageNumShown="10" currentPage="${pageNum}"></div>

	</div>
</div>