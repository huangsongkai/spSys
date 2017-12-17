<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
<form id="pagerForm" method="post"  action="listUserFuncGroup.do" onsubmit="return divSearch(this,'userFuncGroupBox');">
 	<input type="hidden" name="funcGroupId" value="${funcGroupId }">
 	<input type="hidden" name="status" value="${param.status}" />
	<input type="hidden" name="keywords" value="${param.keywords}" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	  </form>
	 
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="addUserFuncGroup.do?funcGroupId=${funcGroupId}"" target="dialog" mask="true" title="添加"><span>添加</span></a></li>
			<li><a title="确定要删除吗?" target="selectedTodo"  href="deleteUserFuncGroup.do?funcGroupId=${funcGroupId}" class="delete"><span>删除</span></a></li>
			<!-- <li class="line">line</li>
			<li><a class="icon" href="listExportExcel.do" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li> -->
		</ul>
	</div>
            <div id="table2">
            <table class="table" width="100%" layoutH="138" rel="userFuncGroupBox">
				<thead>
				<tr>
					<th width="30">行号</th>
					<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
					<th width="120">登录ID</th>
					<th width="120">昵称</th>
					<th  width="80">用户姓名</th>
					<th width="150">办公电话</th>
					<th width="80" align="center">移动电话</th>
					<th width="80">电子邮件</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${userFuncList}" var="obj" varStatus="status">
				<tr target="sid_userFunc" rel="${obj.userId}">
					<td align="center">${status.index+1}</td>
					 <td align="center"><input name="ids" value="${obj.userId}" type="checkbox"></td>
					<td>${obj.userId}</td>
					<td>${obj.nickName}</td>
					<td>${obj.userName}</td>
					<td>${obj.officeTel}</td>
					<td>${obj.mobileTelephone}</td>
					<td>${obj.email}</td>
				</tr>
				</c:forEach>
				</tbody>			
				</table>
	</div>
