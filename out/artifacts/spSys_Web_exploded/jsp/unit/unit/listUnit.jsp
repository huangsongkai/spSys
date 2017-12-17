<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../../../common/include.jsp"%>
<%@ include file="../../../common/include2.jsp"%>
<script language="javascript">
$(function(){
			var jsonStr = $('#list_noJSON',navTab.getCurrentPanel()).val();
			$('#unitName',navTab.getCurrentPanel()).jsonSuggest(eval(jsonStr),{onSelect:checkedListCallback, maxResults:10});
		})
function checkedListCallback(item){
	$('#unitName',navTab.getCurrentPanel()).val(item.text.split("，")[1]);
}
function showDiv(){
			var unitName=$("#unitName",navTab.getCurrentPanel()).val();
			$.pdialog.open("listOneUnitInfo.do?unitName="+unitName,"listOneUnitInfo","详细",{width:800,height:600});	
		}
</script>
<div class="pageHeader">
<form action="listUnit.do" name="form1" id="pagerForm"  method="post" onsubmit="return navTabSearch(this);">
	<input type="hidden" name="status" value="${param.status}" />
	<input type="hidden" name="keywords" value="${param.keywords}" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />



	<div class="searchBar">
		<table  class="searchContent" >
					<tr>
						<td>
							军工单位名称:
							<input type="hidden" id="list_noJSON" value="${unitData}">
							<input type="text" name="unitName" id="unitName" class="inputLength" value="${unitName }" size="80">
							<input type="button" value="详细" onclick="showDiv()">
						</td>
					</tr>
					<tr>
						<td>
							所属集团&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:
							<select name="unitGroup"   id="unitGroup">
								<option></option>
								<c:forEach items="${applicationScope.CodeDict}" var="item">
								 	<c:if test="${item.parentCodeId=='009'}">  
									 		<option value="${item.codeId}" <c:if test="${item.codeId == unitGroup }">selected</c:if>>
									 			${item.codeName}
									 		</option>
								 	</c:if>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td class="" >
							注册地址&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:
						
							<input type="text" name="unitLoginAddress" id="unitLoginAddress"  size="80" class="inputLength" value="${unitLoginAddress }">
						</td>
					</tr>
				</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
				
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent" >
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="addUnit.do" target="navTab"><span>添加</span></a></li>
			<li><a title="确定要删除吗" target="ajaxTodo" rel="ids" href="deleteUnit.do?pkUnit={pkUnit}" class="delete"><span>删除</span></a></li>
			<li><a class="edit" href="addUnit.do?pkUnit={pkUnit}" target="navTab"><span>修改</span></a></li>
			<!-- <li><a class="edit" href="manageUnit.do?1=1" target="navTab"><span>管控内容</span></a></li> -->
			<li class="line">line</li>
		</ul>
	</div>


	<table class="table" width="100%" layoutH="188" >
				<thead>
				<tr >
					<th width="30">序号</th>
					<th width="22" align="center"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
					<th width="120">单位编码</th>
					<th  width="80">单位名称</th>
					<th width="100">单位性质</th>
					<th  width="80">保密资格等级</th>
					<th  width="80">所属集团</th>
					<th  width="80">法定代表人</th>
					<th  width="80">单位人数</th>
					<th  width="80">涉密人数</th>
					<th  width="80">是否上市</th>
					<th  width="80">资质通过时间</th>
					<th  width="80">资质过期时间</th>
					<th  width="80">操作</th>  
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${unitInfoList}" var="obj" varStatus="i">
				<tr  target="pkUnit" rel="${obj.pkUnit}">
					<td  align="center">${i.index+1}</td>
					<td  align="center"><input name="ids" value="${obj.pkUnit }" type="checkbox"></td>
					<td>${obj.unitNo }</td>
						<td><a href="addUnit.do?show=1&pkUnit=${obj.pkUnit }" target="navTab" >${obj.unitName }</a></td>
						<td><w:CodeIdDictOut value="${obj.unitCategory }"></w:CodeIdDictOut></td>
						<td><w:CodeIdDictOut value="${obj.licenseLevel }"></w:CodeIdDictOut></td>
						<td><w:CodeIdDictOut value="${obj.unitGroup }"></w:CodeIdDictOut></td>
						<td>${obj.legalRepresentative }</td>
						<td>${obj.unitPeople }</td>
						<td>${obj.classifiedPeople }</td>
						<td>
						 <c:if test="${obj.listedCompanies=='1'}">是</c:if>
						 <c:if test="${obj.listedCompanies=='2'}">否</c:if>                  
						</td>
						
						<td>
							${obj.qualificationByTime }
						</td>
						<td>
							${obj.qualificationFailTime }
						</td>
						<td>
							
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
