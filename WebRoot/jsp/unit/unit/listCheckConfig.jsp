<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="pageHeader">
<form id="pagerForm" method="post"  action="listCheckConfig.do" onsubmit="return navTabSearch(this);">
	<input type="hidden" name="status" value="${param.status}" />
	<input type="hidden" name="keywords" value="${param.keywords}" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	
	<div class="searchBar">
		<table  class="searchContent" >
					<tr>
						<td>
							检查名称:<input name="checkName" type="text" id="checkName" value="${checkName}"/>
						</td>
					</tr>

				</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent" >
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="addCheck.do?1=1" target="navTab"><span>添加</span></a></li>
			<li><a title="确定要删除吗?" target="ajaxTodo" rel="ids" href="deleteCheck.do?checkId={sid_checkId}" class="delete"><span>删除</span></a></li>
			<!-- <li><a class="delete" href="deleteCustomer.do?uid={sid_pkCustomer}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li> -->
			<li><a class="edit" href="addCheck.do?checkId={sid_checkId}" target="navTab"><span>修改</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>


	<table class="table" width="100%" layoutH="138" >
				<thead>
				<tr >
					<th>
						检查名称
					</th>
					<th>
						检查单位
					</th>
					<th>
						创建日期
					</th>
					<th>
						创建人
					</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${dataList}" var="obj" varStatus="i">
				<tr  target="sid_checkId" rel="${obj.checkId }">
					<td class="td_text_align">
							${obj.checkName}
						</td>
						<td class="td_text_align td_nowrap">
							${obj.checkUnit}
						</td>
						<td class="td_text_align td_nowrap">
							<fmt:formatDate value="${obj.createdDate}" pattern="yyyy-MM-dd"/>
						</td>
						<td class="td_text_align td_nowrap">
							${obj.createdBy}
						</td>
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
<script language="javascript">
		function addCheck(){
			var returnValue = ModelWindow_dialog("addCheck.do?1=1",800,600);
			if(returnValue=="ok"){
		    	spPageSubmit();
			}
		}
</script>
