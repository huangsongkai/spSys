<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.xunj_prj/tag/webwidget" prefix="w"%>
<%@ include file="../../../common/include.jsp"%>
<div class="pageFormContent" >
		<form action="saveProcessSaveScore.do" name="formListPaperScore" id="formListPaperScore" method="post" onsubmit="return validateCallback(this, dialogAjaxDone);"  class="pageForm required-validate" >
		<input name="createdDateAddPaper" type="hidden" value="${createdDateAddPaper}">
		<input name="knowledgeCountFromAddPaper" type="hidden" value="${knowledgeCountFromAddPaper}">
		<input name="knowledgeCountToAddPaper" type="hidden" value="${knowledgeCountToAddPaper}">
		<input name="knowledgeQuestionCountAddPaper" type="hidden" value="${knowledgeQuestionCountAddPaper}">
		<input name="paperNoAddPaper" type="hidden" value="${paperNoAddPaper}">
		<input name="paperNameAddPaper" type="hidden" value="${paperNameAddPaper}">
		<input name="remarkAddPaper" type="hidden" value="${remarkAddPaper}">
		<input name="paperTypeAddPaper" type="hidden" value="${paperTypeAddPaper}">
		<input name="paperScoreAddPaper" type="hidden" value="${paperScoreAddPaper}">
		<input type="hidden" id="certTaskId" name="certTaskId" value="${certTaskId }">
		<input type="hidden" id="year" name="year" value="${year }">
		<input name="checkValue" type="hidden" id="checkValue">
		
		<table class="table" width="100%" >   
					<thead>	
						<tr>
							<th >操作</th>
							<th >题目类型</th>
							<th >题目分值</th>
							<th ></th>
							<th >题目数量</th>  
							<th >合计</th>
						</tr>
					</thead>
			<tbody>
				
					<c:forEach items="${dataList}" var="obj" varStatus="status">
					<tr>
						<td>
						<input type="checkbox" <c:if test="${obj.checkValue=='1' }">checked="checked"</c:if>  name="check" id="check<c:out value="${status.index}"/>" onclick="computeScore()"  questionType="${obj.questionType}">
						</td>
						<td>
							<w:CodeDictOut value="${obj.questionType}" parentId="002"/>
							<input name="questionType" type="text" value="${obj.questionType}">
						</td>
						<td>
							<input size="2" name="questionScore" id="questionScore<c:out value="${status.index}"/>" value="${obj.questionScore}" onkeyup="computeScore()"> 
						</td>
						<td>
						*
						</td>
						<td>
							<input name="questionCount" id="questionCount<c:out value="${status.index}"/>" size="2" value="${obj.questionCount}"  onkeyup="computeScore()">
						</td>
						<td>
							<input name="result" id="result<c:out value="${status.index}"/>" class="inputReadOnly" size="2" readOnly>
						</td>
					</tr>
				</c:forEach>
					<tr>
						<td>
						</td>
						<td>
						</td>
						<td>
						</td>
						<td>
						</td>
						<td>
						</td>
						<td>
							<input name="resultAll" id="resultAll" size="2" class="inputReadOnly" readOnly>
						</td>
					</tr>
					<tr>
						<td colspan="5">
						<input type="button" name="button" onclick="returnCheckValue()" value="保存设置" />
						</td>
						<td>
						<button type="button" class="close">取消</button>
						</td>
					</tr>
			</tbody>			
	</table>
	</form>
	
</div>
  <script type="text/javascript"> 
			$(function(){	
				setTimeout("computeScore()",300);
			})
			function computeScore(){
				var resultAll=0;
				$("input[name=check]").each(function(){ 
					if($(this).attr("checked")==true){
						var id = $(this).attr("id").substr(5);
						var score=$("#questionScore"+id).val();
						var count=$("#questionCount"+id).val()
						$("#result"+id).val(score*count);
						resultAll+=score*count;
					}
				});
				$('#resultAll').val(resultAll);
			}
			function returnCheckValue(){
				var ids='';
				var resultAll=0;
				$("input[name=check]").each(function(){ 
					if($(this).attr("checked",navTab.getCurrentPanel())==true){
						ids+=$(this).attr("questionType")+';';
						var id = $(this).attr("id").substr(5);
						var score=$("#questionScore"+id,navTab.getCurrentPanel()).val();
						var count=$("#questionCount"+id,navTab.getCurrentPanel()).val()
						$("#result"+id,navTab.getCurrentPanel()).val(score*count);
						resultAll+=score*count;
					}
				});
				$("#checkValue").val(ids);
				$("#resultAll").val(resultAll);
				$("#formListPaperScore").submit();
			}

</script>
