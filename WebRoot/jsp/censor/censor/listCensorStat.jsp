<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../../../common/include.jsp"%>
<%@ include file="../../../common/include2.jsp"%>
<div class="pageHeader">
<form name="form1" id="form1" method="post"  action="listCensorChart.do" onsubmit="return navTabSearch(this);">
	<input type="hidden" name="status" value="${param.status}" />
	<input type="hidden" name="keywords" value="${param.keywords}" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<div class="searchBar">
		<table  class="searchContent"  width="100%" id="table1">
		<tr>
				<td   width="30%" align="right">
					统计时间
				</td>
				<td   width="70%"  align="left" >
					从<input type="text" id="statDateFrom" name="statDateFrom" value="${statDateFrom}" readOnly="readOnly" class="date"/>
					至<input type="text" id="statDateTo" name="statDateTo" value="${statDateTo}" readOnly="readOnly" class="date"/>
				</td>
			</tr>
			<tr>
				<td  width="30%" align="right">
					统计要素
				</td>
				<td  width="70%"  align="left" >
				&nbsp;&nbsp;<select name="element" class="selectLength" id="element">
					<option></option>
					<option value="004" >审查认证等级</option>
					<option value="009">集团</option>
					<option value="year">年度</option>
					</select>
				</td>
			</tr>
			<tr>
				<td width="30%" align="right">
					统计结果
				</td>
				<td  width="70%"  align="left">
				&nbsp;&nbsp;<select name="result" class="selectLength" id="result">
					<option></option>
					<option value="1">军工单位数量</option>
					<option value="2" >审查认证次数</option>
					<option value="3">认证得分情况</option>
					<option value="4">认证扣分项分析</option>
					</select>
				</td>
			</tr>
			<tr><td colspan="2" align="center">
					<input name="button" type="button"  onclick="showJpg()" class="btn_standard" value="生成报表" >
					<!-- <input name="button" type="button"  onclick="showJpgaaa()" class="btn_standard" value="生成report" > -->
				</td>
			</tr>
				<tr>
				<td colspan="2" align="left">
				      <iframe name="ifrmId" id="ifrmId" src="" marginwidth="100" width="100%" height="580" frameborder="0" scrolling="no" style="display: none" ></iframe>
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
			if(result!="认证扣分项分析" &&(element == "" || result == "")){
				alert("请选择统计条件");
			}else{
				document.getElementById("ifrmId").src="listCensorChart.do?element="+element+"&statDateFrom="+statDateFrom+"&statDateTo="+statDateTo+"&result="+result;
			}
		}
		function showJpgaaa(){
			var element = document.getElementById("element").value;
			var result = document.getElementById("result").value;
			document.getElementById("ifrmId").style.display="block";
			if(element == "004" && result == "军工单位数量"){
				document.getElementById("ifrmId").src="/database/ReportServer?reportlet=Quantity_004.cpt&__bypagesize__=false";
			}else if(element == "009" && result == "军工单位数量"){
				document.getElementById("ifrmId").src="/database/ReportServer?reportlet=Quantity_009.cpt&__bypagesize__=false";
			}else if(element == "year" && result == "军工单位数量"){
				document.getElementById("ifrmId").src="/database/ReportServer?reportlet=Quantity_year.cpt&__bypagesize__=false";
			}else if(element == "004" && result == "审查认证次数"){
				document.getElementById("ifrmId").src="/database/ReportServer?reportlet=Times_004.cpt&__bypagesize__=false";
			}else if(element == "009" && result == "审查认证次数"){
				document.getElementById("ifrmId").src="/database/ReportServer?reportlet=Times_009.cpt&__bypagesize__=false";
			}else if(element == "year" && result == "审查认证次数"){
				document.getElementById("ifrmId").src="/database/ReportServer?reportlet=Times_year.cpt&__bypagesize__=false";
			}else if(element == "004" && result == "认证得分情况"){
				document.getElementById("ifrmId").src="/database/ReportServer?reportlet=Situation_004.cptt&__bypagesize__=false";
			}else if(element == "009" && result == "认证得分情况"){
				document.getElementById("ifrmId").src="/database/ReportServer?reportlet=Situation_009.cpt&__bypagesize__=false";
			}else if(element == "year" && result == "认证得分情况"){
				document.getElementById("ifrmId").src="/database/ReportServer?reportlet=Situation_year.cpt&__bypagesize__=false";
			}else if(element == "004" && result == "认证扣分项分析"){
				document.getElementById("ifrmId").src="/database/ReportServer?reportlet=Marking_items.cpt&__bypagesize__=false";
			}
			
			//else if(element == "009" && result == "军工单位数量"){
			//	document.getElementById("ifrmId").src="/database/ReportServer?reportlet=Statistical.cpt";
			//}else if(element == "year" && result == "军工单位数量"){
			//	document.getElementById("ifrmId").src="/database/ReportServer?reportlet=Statistical.cpt";
			//} 
			//window.open("http://localhost:8888/database/ReportServer?reportlet=11111111.cpt"); 
		}
		
</script>
