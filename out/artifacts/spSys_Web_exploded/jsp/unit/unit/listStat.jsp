<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../../../common/include.jsp"%>
<%@ include file="../../../common/include2.jsp"%>
<div class="pageHeader">
<form name="form1" id="form1" method="post"  action="listChart.do" onsubmit="return navTabSearch(this);">
	<input type="hidden" name="status" value="${param.status}" />
	<input type="hidden" name="keywords" value="${param.keywords}" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<div class="searchBar">
		<table  class="searchContent"  width="100%" id="table1">
		<tr>
				<td width="30%" align="right">
					统计时间
				</td>
				<td  width="70%"  align="left" >
					从<input type="text" id="statDateFrom" name="statDateFrom" value="${statDateFrom}"  readOnly="readOnly" class="date"/>
					至<input type="text" id="statDateTo" name="statDateTo" value="${statDateTo}" readOnly="readOnly" class="date"/>
				</td>
			</tr>
			<tr>
				<td width="30%" align="right">
					审查任务名称
				</td>
				<td   width="70%"  align="left" >
					&nbsp;&nbsp;<select name="taskName" class="inputLength"  id="taskName" >
					<option></option>
					<c:forEach items="${checkConfigList}" var="item">
						 	<option value="${item.checkId}" <c:if test="${item.checkId==taskName}">selected</c:if> >${item.checkName}</option>
					</c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<td width="30%" align="right">
					统计要素
				</td>
				<td  width="70%"  align="left" >
				&nbsp;&nbsp;<select name="element" class="inputLength" id="element">
					<option></option>
					<option value="004" >保密资格等级</option>
					<option value="009">集团</option>
					<option value="010">保密人数</option>
					<option value="011">军工单位</option>
					</select>
				</td>
			</tr>
			<tr>
				<td width="30%" align="right">
					统计结果
				</td>
				<td  width="70%"  align="left" >
				&nbsp;&nbsp;<select name="result" class="inputLength" id="result">
					<option></option>
					<option value="1">军工单位数量</option>
					<option value="2" >保密检查次数</option>
					<option value="3" >保密检查人数</option>
					<option value="4">检查分数情况</option>
					<option value="5">检查扣分项分析</option>
					</select>
				</td>
			</tr>
			<tr><td colspan="2"  align="center">
					<input name="button" type="button"  onclick="showJpg()" class="btn_standard" value="生成报表" >
				</td></tr>
				<tr>
				<td colspan="2">
				      <iframe name="ifrmId" id="ifrmId" src='' marginwidth="100" width="100%" height="480" frameborder="0" scrolling="no" style="display: none" ></iframe>
				</td>
				</tr>
				</table>
		
	</div>
	</form>
</div>
<script language="javascript">
		function showJpg(){
			document.getElementById("ifrmId").style.display="block";
			var element = document.getElementById("element").value;
			var statDateFrom = document.getElementById("statDateFrom").value;
			var statDateTo = document.getElementById("statDateTo").value;
			var result = document.getElementById("result").value;
			var taskName = document.getElementById("taskName").value;
			if(result=='检查扣分项分析'||(result!=''&&element!='') ){
				document.getElementById("ifrmId").src="listChart.do?element="+element+"&statDateFrom="+statDateFrom+"&statDateTo="+statDateTo+"&taskName="+taskName+"&result="+result;
				
			}else{
			alert("请选择统计条件");	
			}
		}
		
</script>
