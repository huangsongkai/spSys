<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="pageHeader">
<form id="pagerForm" method="post"  action="listFuncGroup.do" onsubmit="return navTabSearch(this);">
	<input type="hidden" name="status" value="${param.status}" />
	<input type="hidden" name="keywords" value="${param.keywords}" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<div class="searchBar" >
		<table  class="searchContent" >
					<tr>
						<td>
							角色名称:<input name="funcGroup.funcGroupName" type="text" id="funcGroup.funcGroupName" value="${funcGroup.funcGroupName}"/>
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

</div>
<div class="pageContent"  style="float:left; display:block; overflow:auto; width:340px; border:solid 1px #CCC; line-height:21px; background:#fff">
	<div class="panelBar" >
		<ul class="toolBar" >
			<li><a class="add" href="addFuncGroup.do" target="navTab"><span>添加</span></a></li>
			<li><a class="delete" href="deleteFuncGroup.do?uid={sid_funcGroup}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li><a class="edit" href="updateFuncGroup.do?uid={sid_funcGroup}" target="navTab"><span>修改</span></a></li>
			<!-- <li class="line">line</li>
			<li><a class="icon" href="listExportExcel.do" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li> -->
		</ul>
	</div>
	<div id="table1">
	<table  class="table" width="100%" layoutH="138" >
				<thead>
				<tr>
					<th width="30">行号</th>
					<th width="120">角色名称</th>
					<th  width="180">角色描述</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${datalist}" var="obj" varStatus="status" >
				<tr target="sid_funcGroup" rel="${obj.funcGroupId}" trClick="FuncGroup">
					<td align="center">${status.index+1}</td>
					<td>${obj.funcGroupName}</td>
					<td>${obj.funcGroupDescription}</td>
				</tr>
				</c:forEach>
				</tbody>			
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
		
		<div class="pagination" rel="table1" targetType="navTab" totalCount="${totalCount}" numPerPage="${numPerPage}" pageNumShown="10" currentPage="${pageNum}"></div>
		
	</div>
</div>

<a href="listUserFuncGroup.do?funcGroupId=" type="hidden" id="userFuncGroupA" target="ajax" rel="userFuncGroupBox"></a>
<div id="userFuncGroupBox" class="unitBox" style="margin-left:346px;">
					<!--#include virtual="list1.html" -->
</div>
	</form>
<script type="text/javascript">
	function trClickEventFuncGroup(tr){
		if($(tr).attr('target')=='sid_funcGroup')
		$("#userFuncGroupA").attr("href","listUserFuncGroup.do?funcGroupId="+$(tr).attr('rel')).click();
	}
</script>
