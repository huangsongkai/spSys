<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="pageHeader">
<form id="pagerForm" method="post"  action="listCodeKind.do" onsubmit="return navTabSearch(this);">
	<input type="hidden" name="status" value="${param.status}" />
	<input type="hidden" name="keywords" value="${param.keywords}" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />



	<div class="searchBar">
		<table  class="searchContent" >
					<tr>
						<td>
							字典码编号:<input name="codeKind.kindId" type="text" id=codeKind.kindId value="${codeKind.kindId}"/>
						</td>
						<td>
							字典码名称:<input name="codeKind.kindName" type="text" id="codeKind.kindName" value="${codeKind.kindName}"/>
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
			<li><a class="add" href="addCodeKind.do" target="navTab"><span>添加</span></a></li>
			<li><a class="delete" href="deleteCodeKind.do?kindId={sid_kindId}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li><a class="edit" href="updateCodeKind.do?kindId={sid_kindId}" target="navTab"><span>修改</span></a></li>
			<!-- <li class="line">line</li>
			<li><a class="icon" href="listExportExcel.do" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li> -->
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138" >
				<thead>
				<tr>
					<th width="30">行号</th>
					<th width="120">字典大类编号</th>
					<th width="120">字典大类名称</th>
					<th  width="120">字典大类代码</th>
					<th width="120">层级</th>
					<th width="180">字典描述</th>
					<th width="120">字典细项</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${kindList}" var="obj" varStatus="status">
				<tr target="sid_kindId" rel="${obj.kindId}">
					<td align="center">${status.index+1}</td>
					<td>${obj.kindId}</td>
					<td>${obj.kindName}</td>
					<td>${obj.kindCode}</td>
					<td>${obj.levelTal}</td>
					<td>${obj.remark}</td>
					<td><a class="add" href="listCodeDict.do?kindId=${obj.kindId}" target="dialog"><span>修改</span></a></td>
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