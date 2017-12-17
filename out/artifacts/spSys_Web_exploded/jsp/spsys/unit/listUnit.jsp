<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../../../common/include.jsp"%>

<div class="pageHeader">
<form id="pagerForm" method="post"  action="listUnit.do" onsubmit="return navTabSearch(this);">
	<input type="hidden" name="status" value="${param.status}" />
	<input type="hidden" name="keywords" value="${param.keywords}" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<div class="searchBar">
		<table  class="searchContent" >
			<tr>
				<td class="" >
					军工单位名称
				</td>
				<td  class="left_align" >
					&nbsp;&nbsp;<input type="text" name="companyName" id="companyName" class="inputLength" value="${companyName }" >
				</td>
			
				<td class="">
					社会统一信用代码
				</td>
				<td  class="left_align">
					&nbsp;&nbsp;<input type="text" name="creditCode" id="creditCode" class="inputLength" value="${creditCode }" >
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
			<li><a title="确定要删除吗?" target="ajaxTodo" rel="ids" href="deleteUnit.do?pkbase={pkbase}" class="delete"><span>删除</span></a></li>
			<li><a class="edit" href="addUnit.do?pkbase={pkbase}" target="navTab"><span>修改</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138" >
				<thead>
				<tr >
					<th>
						序号
					</th>
					<th>
						单位名称
					</th>
					<th>
						社会统一信用代码
					</th>
					<th>
						单位性质
					</th>
					<th>
						法人
					</th>
					<th>
						单位人数
					</th>
					<th>
						涉密人员数
					</th>
					<th>
						注册地址
					</th>
					<th>
						科研生产地址
					</th>
					<th>
						通信地址
					</th>
					<th>邮编</th>
					<th>是否是上市公司</th>
					<th>注册资金</th>
					<th>固定总资产</th>
					<th>单位创建时间</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${dataList}" var="obj">
					<tr  target="pkbase" rel="${obj.pkbase }">
						<td  align="center">${i.index+1}</td>
						<td >
							${obj.companyName}  
						</td>
						<td >
							${obj.creditCode}
						</td>
						<td >
							${obj.companyType }
						</td>
						<td >
							${obj.legalBody }
						</td>
						<td >
							${obj.companyPersonCount }
						</td>
						
						<td >
							${obj.secretPersonCount }
						</td>
						<td >
							${obj.regAddress}
						</td>
						<td >
						    ${obj.officeAddress} 
						    
						</td> 
						<td >${obj.mailingAddress} 
						</td>
						<td >${obj.postalCode} </td>
						<td >
						    <c:if test="${obj.isShangshi=='1'}" >是</c:if> 
						    <c:if test="${obj.isShangshi=='0'}" >否</c:if> 
						</td> 
						<td >
							${obj.regMoney}
						</td>
						<td >
							${obj.fixedAssets}
						</td>
						<td >
						   <fmt:formatDate value="${obj.companyCreateTime}" pattern="yyyy-MM-dd"/> 
						    
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
		
	   	
</script>
