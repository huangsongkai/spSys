<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../common/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="pageHeader">
	<form method="post" action="saveTaskInfo.do" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageContent"  layoutH="57">
		<table width="100%" class="searchContent" >
			<tr>
				<td width="25%" >
					审查任务名称
					<input type="hidden" id="list_noJSONAddTask" value="${unitData}">
					<input type="hidden" name="checkTaskInfo.checkState"  value="${checkTaskInfo.checkState}">
					<input type="hidden" id="pkUnitAddTask" name="checkTaskInfo.pkUnit"  value="${checkTaskInfo.pkUnit}">
				</td>
				<td width="75%" >
					<select name="checkTaskInfo.taskName" class="inputLength"  id="checkTaskInfo.taskName" value="${checkTaskInfo.taskName }" class="Required" msg="审查任务名称不能为空" onchange="loadCheckConfigRule( this.value )">
						<option></option>
						<c:forEach items="${checkConfigList}" var="item">
							 <option value="${item.checkId}" <c:if test="${item.checkId==checkTaskInfo.taskName}">selected</c:if> >${item.checkName}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			
			<tr>
				<td >
					审查单位名称
				</td>
				<td  >
					<input size="80" name="checkTaskInfo.checkUnitName" id="unitNameAddTask" class="required"  type="text"  value="${checkTaskInfo.checkUnitName}" >
				</td>
			</tr>
			<tr>
				<td width="25%">
					所属集团
				</td>
				<td width="75%">
					<select name="checkTaskInfo.checkUnitGroup" class="required" id="checkTaskInfo.checkUnitGroup" value="${checkTaskInfo.checkUnitGroup}" >
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
							<c:if test="${item.parentCodeId=='009'}">
								<option value="${item.codeId}" <c:if test="${item.codeId==checkTaskInfo.checkUnitGroup}">selected</c:if> >${item.codeName}</option>
							</c:if>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td width="25%">
					上级单位
				</td>
				<td width="75%">
					<select name="checkTaskInfo.productionPermitRegistration" class="inputLength" id="checkTaskInfo.productionPermitRegistration" value="${checkTaskInfo.productionPermitRegistration}">
						<option></option>
						<c:forEach items="${unitInfoList}" var="item">
							<option value="${item.unitNo}" <c:if test="${item.unitNo==checkTaskInfo.productionPermitRegistration}">selected</c:if> >${item.unitName}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td width="25%">
					单位性质
				</td>
				<td width="75%" >
					<select name="checkTaskInfo.checkUnitCategory" class="inputLength"  id="checkTaskInfo.checkUnitCategory" value="${checkTaskInfo.checkUnitCategory}" >
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='005'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==checkTaskInfo.checkUnitCategory}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td width="25%" >
					通讯地址
				</td>
				<td width="75%" >
					<input type="text" name="checkTaskInfo.checkUnitDistrict" id="checkUnitDistrict"  class="required" value="${checkTaskInfo.checkUnitDistrict}">
				</td>
			</tr>

			<tr>
				<td width="25%" >
					保密资格等级
				</td>
				<td width="75%" >
					<select name="checkTaskInfo.confidentialityLevelQualifications" class="inputLength"  id="checkTaskInfo.confidentialityLevelQualifications" value="${checkTaskInfo.confidentialityLevelQualifications }">
					<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='004'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==checkTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td width="25%">
					检查结论
				</td>
				<td width="75%">
					<select name="checkTaskInfo.checkResult" class="inputLength"  id="checkTaskInfo.checkResult" value="${checkTaskInfo.checkResult}">
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='016'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==checkTaskInfo.checkResult}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
				</td>
			</tr>

			<tr>
				<td width="25%" >
					检查时间
				</td>
				<td width="75%" >
					<input name="checkTaskInfo.passedTime" id="checkTaskInfo.passedTime" value="<fmt:formatDate value="${checkTaskInfo.passedTime}" pattern="yyyy-MM-dd"/>" type="text"  class="date" >
				</td>
			</tr>
			<tr>
				<td width="25%">
					检查分数
				</td>
				<td width="75%" >
					<input name="checkTaskInfo.passedScore" class="required number" id="fs" type="text" value="${checkTaskInfo.passedScore }"  msg="不能为空且必须为数字" >
				</td>
			</tr>
			<tr>
				<td width="25%" >
					涉密人员数
				</td>
				<td width="75%" >
					<input id="checkTaskInfo.confidentialityStaffNumber" name="checkTaskInfo.confidentialityStaffNumber" class="required number"  type="text" value="${checkTaskInfo.confidentialityStaffNumber }" >
				</td>
			</tr>
			<tr>
				<td width="25%" >
					现场审查专家组
				</td>
				<td width="75%" >
					<input name="checkTaskInfo.checkGroup" class="inputLength"  type="text" value="${checkTaskInfo.checkGroup }"  >
				</td>
			</tr>
		</table>
		<table width="100%" border="1"  >
				<thead>
						<tr>
							<td  width="10%">编号</td>
							<td  width="40%">扣分标准</td>
							<td width="10%">扣分</td>
							<td width="40%">说明</td>
						</tr>
				</thead>
						<tbody id="new">
							<c:forEach items="${scoreList}" var="obj" varStatus="status">
								<tr>
									<td class="td_text_align" >${status.count}</td>
									<td class="td_text_align td_nowrap">
										${obj.scoreRule}
										<input type="hidden" name="scoreRule"  class="inputLength" value="${obj.scoreRule}">
										<input type="hidden" name="ruleNo"  class="inputLength" value="${obj.ruleNo}">
						
									</td>
									<td class="td_text_align td_nowrap" width="10%">
										<input type="text" name="scoreNumber" style="width: 50px;"  onchange="js()" value="${obj.scoreNumber}"  msg="扣分必须为整数">
									</td>
									<td class="td_text_align td_nowrap" width="40%">
										<input type="text" name="scoreExplain"   class="inputLength"value="${obj.scoreExplain}">
									</td>
								</tr>				
							</c:forEach>
						</tbody>
			</table>
		<div class="formBar">
					<input name="checkTaskInfo.taskId" type="hidden" value="${checkTaskInfo.taskId }" id="taskId"/>
					<input name="taskEdit" value="${param.taskEdit}" type="hidden"/>	
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<c:if test="${taskUpdate!=1}">
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
				</c:if>
			</ul>
		</div>
	</form>
</div>
<script language="javascript">
	function loadCheckConfigRule( id ){
  	$.ajax({
		async: false,
		type : "POST",
		url : 'ajaxCheckRole.do?id='+id,
		success : function(data) {
			$('#new').html('');
			var arr = eval('(' + data + ')'); 
			for(var i=0;i<arr.length;i++){
				var obj = arr[i];
				$('#new').append('<tr><td>'+(i+1)+'</td><td>'+obj.ruleContent+'</td><td><input type="hidden" name="scoreRule" value="'+obj.ruleContent+'" /><input type="hidden" name="ruleNo" value="'+obj.ruleId+'" /><input name="scoreNumber" onchange="js()" / ></td><td><input name="scoreExplain" /></td></tr>');
			}
		}
	}); 

}
	function js(){
		var value = 0;
		$('input[name=\'scoreNumber\']').each(function(i){
			var temp='0';
			if($(this).val()!='')temp=$(this).val();
			value = value+parseInt(temp);
		});
		$('#fs').val(100-value);
	}
  	$(function(){
      if($('#fs').val()=='')$('#fs').val(100);
       var jsonStr1 = $('#list_noJSONAddTask').val();
	   $('#unitNameAddTask').jsonSuggest(eval(jsonStr1),{onSelect:checkedListCallbackAddTask, maxResults:10});
  	})	
 	function checkedListCallbackAddTask(item){
		$('#unitNameAddTask').val(item.text.split("，")[1]);
		$('#pkUnitAddTask').val(item.text.split("，")[0]);
		var unitId=item.text.split("，")[0];
		$.post("showUnit.do?unitId="+unitId,function(data){
				var str=data.split('&');
				//主键+名字+集团+上级+性质+工业+地址+等级+时间+分数
				$('#checkTaskInfo\\.pkUnit').val(str[0]);
				$('#checkTaskInfo\\.checkUnitGroup').val(str[2]);
				$('#checkTaskInfo\\.checkUnitCategory').val(str[4]);
				$('#checkUnitDistrict').val(str[5]);
				$('#checkTaskInfo\\.productionPermitRegistration').val(str[3]);
				$('#checkTaskInfo\\.confidentialityStaffNumber').val(str[7]);
			})
	}
</script>

