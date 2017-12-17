<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../common/include.jsp"%>
<%@ include file="../../../common/include2.jsp"%>

  <script type="text/javascript">
    	$(document).ready(function() {
            //隐藏模板tr
            $("#tb tr").eq(2).hide();
            
          	setTimeout("computeScore2()",300);
            
            
            
            
        })
        function computeScore2(){
				var resultAll=0;
				$("input[name=check2]").each(function(){ 
					if($(this).attr("checked")==true){
						var id = $(this).attr("id").substr(6);
						var score=$("#questionScore2"+id).val();
						var count=$("#questionCount2"+id).val()
						$("#result2"+id).val(score*count);
						resultAll+=score*count;
					}
				});
				$("#resultAll2").val(resultAll);
			}
			function returnCheckValue2(){
				var ids='';
				var resultAll=0;
				$("input[name=check2]").each(function(){ 
					if($(this).attr("checked")==true){
						ids+=$(this).attr("questionType")+';';
						var id = $(this).attr("id").substr(6);
						var score=$("#questionScore2"+id).val();
						var count=$("#questionCount2"+id).val()
						$("#result2"+id).val(score*count);
						resultAll+=score*count;
					}
				});
				$("#checkValue2").val(ids);
				$("#resultAll2").val(resultAll);
				$("#formListPaperScore2").submit();
			}
        function changeType(){
			
			if($("#paperType",navTab.getCurrentPanel()).val()=="1"){
				$("#tb",navTab.getCurrentPanel()).show();
			}else if($("#paperType",navTab.getCurrentPanel()).val()=="0"){
				$("#tb",navTab.getCurrentPanel()).hide();
				var createdDateAddPaper=$("#createdDateAddPaper",navTab.getCurrentPanel()).val();
				var knowledgeCountFromAddPaper=$("#knowledgeCountFromAddPaper",navTab.getCurrentPanel()).val();
				var knowledgeCountToAddPaper=$("#knowledgeCountToAddPaper",navTab.getCurrentPanel()).val();
				var knowledgeQuestionCountAddPaper=$("#knowledgeQuestionCountAddPaper",navTab.getCurrentPanel()).val();
				var paperNoAddPaper=$("#paperNoAddPaper",navTab.getCurrentPanel()).val();
				var paperNameAddPaper=$("#paperNameAddPaper",navTab.getCurrentPanel()).val();
				var remarkAddPaper=$("#remarkAddPaper",navTab.getCurrentPanel()).val();
				var paperTypeAddPaper=$("#paperTypeAddPaper",navTab.getCurrentPanel()).val();
				var paperScoreAddPaper=$("#paperScoreAddPaper",navTab.getCurrentPanel()).val();
				var year=$("#year").val();
				$.pdialog.open("processAddScore.do?createdDateAddPaper="+createdDateAddPaper+"&knowledgeCountFromAddPaper="+knowledgeCountFromAddPaper+"&knowledgeCountToAddPaper="+knowledgeCountToAddPaper+"&knowledgeQuestionCountAddPaper="+knowledgeQuestionCountAddPaper+"&paperNoAddPaper="+paperNoAddPaper+"&paperNameAddPaper="+paperNameAddPaper+"&remarkAddPaper="+remarkAddPaper+"&paperTypeAddPaper="+paperTypeAddPaper+"&paperScoreAddPaper1="+paperScoreAddPaper+"&certTaskId="+certTaskId+"&year="+year, "processAddScore","新规则",{width:800,height:600,mask:false,mixable:true,minable:true,resizable:true,drawable:true} );
			}
		}
    </script>
<div align="pageHeader">
	<form action="saveProcessSaveScore.do" name="form1"id="formListPaperScore2" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent">
			<input name="checkValue" type="hidden" id="checkValue2">
			<input type="hidden" id="certTaskId" name="certTaskId" value="${certTaskId }">
			<p>
				<label>    
					创建日期  
				</label>
				<input name="createdDateAddPaper" id="createdDateAddPaper" value="<fmt:formatDate value="${paperInfo.createdDate}" pattern="yyyy-MM-dd"/>" type="text" onClick="WdatePicker()" readOnly="readOnly" class="inputCal" >
				<input name="knowledgeCountFromAddPaper"  id="knowledgeCountFromAddPaper" type="hidden" value="${paperInfo.knowledgeCountFrom}">
				<input name="knowledgeCountToAddPaper"  id="knowledgeCountToAddPaper" type="hidden" value="${paperInfo.knowledgeCountTo}">
				<input name="knowledgeQuestionCountAddPaper"  id="knowledgeQuestionCountAddPaper" type="hidden" value="${paperInfo.knowledgeQuestionCount}">
				
			</p>
			<p>
				<label>
					试卷编号  
				</label>
				<fmt:formatNumber value="${paperInfo.paperNo}" pattern="0000"></fmt:formatNumber>
				<input name="paperNoAddPaper" id="paperNoAddPaper" type="hidden" size="4" value="${paperInfo.paperNo}" readonly>
			</p>
			<p>
				<label>
					试卷名称
				</label>
				<input name="paperNameAddPaper" id="paperNameAddPaper" type="text"  value="${paperInfo.paperName}" class="required" alt="试卷名称不能为空">
			</p>
			<p>
				<label>
					试卷类型
				</label>
							<select name="paperTypeAddPaper" id="paperTypeAddPaper" >
							 <c:forEach items="${applicationScope.CodeDict}" var="item">
							 	<c:if test="${item.parentCodeId=='001'}">  
							 		<option value="${item.codeCode}" <c:if test="${item.codeCode==paperInfo.paperType}">selected</c:if> >${item.codeName}</option>
							 	</c:if>
							 </c:forEach>
							</select>
			</p>
			<p>
				<label>
					备注
				</label>
				<input name="remarkAddPaper" id="remarkAddPaper" type="text" value="${paperInfo.remark}" >
			</p>
			<p>
				<label>
					试卷总分
				</label>
					<input name="paperScoreAddPaper" id="paperScoreAddPaper" type="text" readOnly <c:if test="${paperInfo.paperScore!=null}"> value="${paperInfo.paperScore}"  </c:if><c:if test="${paperInfo.paperScore==null}"> value="100"  </c:if> > 
			</p>
			<p>
				<label>
					试卷创建人
				</label>
				<input name="createdByAddPaper" type="text" readOnly id="createdByAddPaper"
							<c:if test="${paperInfo.createdBy!=null}"> value="${paperInfo.createdBy}"  </c:if>
							<c:if test="${paperInfo.createdBy==null}"> value="${sessionScope.sessionbean.userName}"  </c:if>
						>
			</p>
			<p>
				<label>保持与前()年无重复</label>
				<input name="year" id="year" value="${year}" >
			</p>	
			<br><br><br>
			<p>
				<label>考核类型：</label>
				<select name="" class="selectLength" id="paperType" onchange="changeType()">
					<option></option>
					<option value="1" <c:if test="${otherFileStatus == '1' }">selected="selected"</c:if>>固有规则</option>
					<option value="0" <c:if test="${otherFileStatus == '0' }">selected="selected"</c:if>>新规则</option>
				</select>
			</p>
			<br><br><br>
			<table width="80%" id="tb" style="display:none">
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
						<input type="checkbox" <c:if test="${obj.checkValue=='1' }">checked="checked"</c:if>  name="check2" id="check2<c:out value="${status.index}"/>" readOnly questionType="${obj.questionType}">
						</td>
						<td>
							<w:CodeDictOut value="${obj.questionType}" parentId="002"/>
							<input name="questionType" type="text" value="${obj.questionType}">
						</td>
						<td>
							<input size="2" name="questionScore" id="questionScore2<c:out value="${status.index}"/>" value="${obj.questionScore}"readOnly> 
						</td>
						<td>
						*
						</td>
						<td>
							<input name="questionCount" id="questionCount2<c:out value="${status.index}"/>" size="2" value="${obj.questionCount}" readOnly >
						</td>
						<td>
							<input name="result" id="result2<c:out value="${status.index}"/>" class="inputReadOnly" size="2" readOnly>
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
							<input name="resultAll" id="resultAll2" size="2" class="inputReadOnly" readOnly>
						</td>
					</tr>
					<tr>
						<td colspan="6">
						<input type="button" name="button" onclick="returnCheckValue2()" value="保存设置" />
						</td>
					</tr>
			</tbody>
			</table>	
		</div>
	</form>
</div>
