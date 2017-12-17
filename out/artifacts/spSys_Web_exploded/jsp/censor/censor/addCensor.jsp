<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../../../common/include.jsp"%>
<div class="pageHeader">
	<form method="post" action="saveCensor.do" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageContent" layOutH="138">
			<table class="searchContent"  width="80%">
				<tr>
					<td align="center" width="40%">
						军工单位名称
						<input type="hidden" id="list_noJSONAddCensor" value="${unitData}">
					</td>
					<td width="60%">
						&nbsp;&nbsp;<input type="text" name="unitName" id="unitNameAddCensor" size="80" class="inputLength required"  value="${unitName }">
					<input type="hidden" name="unitNo" id="unitNoAddCensor">
					</td>
				</tr>
				<tr>
					<td  align="center" >
						认证类型
					</td>
					<td width="60%">
						&nbsp;&nbsp;<select name="certType" class="inputLength required"  id="certType">
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='013'}">
						 		<option value="${item.codeId}" <c:if test="${item.codeId == certType }">selected</c:if>>${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					</td>
				</tr>
				<tr>
					<td  align="center" >
						认证等级
					</td>
					<td width="60%">
						&nbsp;&nbsp;<select name="censorLevel" class="inputLength required"  id="censorLevel">
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='004'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId == censorLevel }">selected</c:if>>${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					</td>
				</tr>   
				<tr>
					<td align="center" width="40%">
						计划审查时间
					</td>
					<td width="60%">
						&nbsp;&nbsp;<input type="text" name="data19" id="data19"  class="date required" dateFmt="yyyy-MM-dd" value="${data19 }">
					<input type="hidden" name="unitNo" id="unitNo">
					</td>
				</tr>
				<tr>
					<td align="center" width="40%">
						计划审查人员
					</td>
					<td width="60%">
						&nbsp;&nbsp;<input type="text" name="data20" id="data20" class="inputLength required"  value="${data20 }">
					<input type="hidden" name="unitNo" id="unitNo">
					</td>
				</tr>
			</table>
		</div>
		<c:if test="${empty certTaskId}">
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
			</ul>
		</div>
		</c:if>
	</form>
</div>
      <script language="javascript">
         $(function(){
			var jsonStr1 = $('#list_noJSONAddCensor').val();
			$('#unitNameAddCensor').jsonSuggest(eval(jsonStr1),{onSelect:checkedListCallbackAddCensor, maxResults:10});	
		})
		
		function checkedListCallbackAddCensor(item){
			$('#unitNameAddCensor').val(item.text.split("，")[1]);
			$('#unitNoAddCensor').val(item.text.split("，")[0]);
		}
      </script>
