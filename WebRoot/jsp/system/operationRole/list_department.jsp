<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../common/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="pageHeader">
	<form id="pagerForm" method="post"  action="listDepartmentOperationRole.do" onsubmit="return navTabSearch(this);">
		<input type="hidden" name="status" value="${param.status}" />
		<input type="hidden" name="keywords" value="${param.keywords}" />
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		<input type="hidden" name="orderField" value="${param.orderField}" />
		<div class="searchBar">
			<table  class="searchContent" >							
				<tr>
					<td>
						部门名称
					</td>
					<td class="left_align">
						<input name="operationRole.roleName" type="text" id="roleNameSearch" value="${operationRole.roleName}"/>
					</td>
					<td>
						部门代码
					</td>
					<td class="left_align">
						<input name="operationRole.roleCode" type="text" id="roleCodeSearch" value="${operationRole.roleCode}"/>
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
			<li><a class="add" href="addOperationRole.do" target="navTab"><span>添加</span></a></li>
			<!-- <li><a title="确定要删除吗?" target="selectedTodo" rel="ids" href="deleteProduct.do" class="delete"><span>删除</span></a></li>-->
			<li><a class="delete" href="delOperationRole.do?roleId={sid_pkRoleId}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li> 
			<li><a class="edit" href="updateOperationRole.do?roleId={sid_pkRoleId}" target="navTab"><span>修改</span></a></li>
			<li class="line">line</li>
			<%
				String roleName = request.getParameter("operationRole.roleName")!=null?request.getParameter("operationRole.roleName"):"";
				roleName = java.net.URLEncoder.encode(roleName);
			 %>
			<li><a class="icon" href="listOperationRoleExportExcel.do?roleCode=${operationRole.roleCode}&roleName=<%=roleName %>" target="dwzExport" targetType="navTab" title="确定要导出这些记录吗?"><span>导出EXCEL</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138" >
		<thead>
		<tr >
			<th>部门代码</th>
			<th>部门名称</th>
			<th>部门标识</th>
		</tr>
		</thead>
		<c:forEach items="${datalist}" var="obj" varStatus="i">
			<tr target="sid_pkRoleId" rel="${obj.roleId}">
				<td>${obj.roleCode}</td>
				<td>${obj.roleName}</td>
				<td><w:CodeDictOut value="${obj.roleSign}" parentId="050"></w:CodeDictOut>  
				</td>
			</tr>
		</c:forEach>
	</table>
</div>
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