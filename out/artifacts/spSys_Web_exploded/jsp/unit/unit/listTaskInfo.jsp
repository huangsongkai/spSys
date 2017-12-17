<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../../../common/include.jsp"%>

<div class="pageHeader">
<form id="pagerForm" method="post"  action="listTaskInfo.do" onsubmit="return navTabSearch(this);">
	<input type="hidden" name="status" value="${param.status}" />
	<input type="hidden" name="keywords" value="${param.keywords}" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<div class="searchBar">
		<table  class="searchContent" >
					<tr>
				<td class="" width="15%" >
					军工单位名称
					<input type="hidden" id="list_noJSON" value="${unitData}">
				</td>
				<td  class="left_align"  width="85%">
					&nbsp;&nbsp;<input type="text" name="unitName" id="unitName" class="inputLength" value="${unitName }" size="80">
				</td>
			</tr>
			<tr>
				<td class="" width="15%" >
					检查任务名称
				</td>
				<td  class="left_align"  width="35%">
					&nbsp;&nbsp;<select name="taskName" class="inputLength"  id="taskName" >
					<option></option>
					<c:forEach items="${checkConfigList}" var="item">
						 	<option value="${item.checkId}" <c:if test="${item.checkId==taskName}">selected</c:if> >${item.checkName}</option>
					</c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<td width="15%" class="">
					保密资格等级
				</td>
				<td width="35%" class="left_align">
				&nbsp;&nbsp;<select name="confidentialityLevelQualifications" class="inputLength"  id="confidentialityLevelQualifications" >
					<option></option>
					 <c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='004'}">  
						 		<option value="${item.codeId}" <c:if test="${item.codeId==confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
					 </c:forEach>
				</select>
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
			<li><a class="add" href="addTaskInfo.do?1=1" target="navTab"><span>添加</span></a></li>
			<li><a title="确定要删除吗?" target="ajaxTodo" rel="ids" href="deleteTaskInfo.do?taskId={sid_taskId}&taskEdit=true" class="delete"><span>删除</span></a></li>
			<!-- <li><a class="delete" href="deleteCustomer.do?uid={sid_pkCustomer}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li> -->
			<li><a class="edit" href="addTaskInfo.do?taskId={sid_taskId}&taskEdit=true&taskUpdate=1" target="navTab"><span>修改</span></a></li>
			<li><a class="icon" href="taskInfoExportExcel.do" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="188" >
				<thead>
				<tr >
					<th>
						检查任务名称
					</th>
					<th>
						单位名称
					</th>
					<th>
						所属集团名称
					</th>
					<th>
						保密资格等级
					</th>
					
					<th>
						检查结论
					</th>
					<th>
						检查时间
					</th>
					<th>
						检查分数
					</th>
					<th>
						涉密人员数
					</th>
					<th>
						现场审查专家组
					</th>
					<th>
						处理
					</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${dataList}" var="obj">
					<tr  target="sid_taskId" rel="${obj.taskId }">
						<td >
							${obj.checkConfig.checkName}  
						</td>
						<td >
							${obj.unitInfo.unitName}
						</td>
						<td >
							<w:CodeIdDictOut value="${obj.unitInfo.unitGroup}" />
						</td>
						<td >
							<w:CodeIdDictOut value="${obj.confidentialityLevelQualifications}" />  
						</td>
						<td >
							<w:CodeIdDictOut value="${obj.checkResult}" />
						</td>
						<td >
							<fmt:formatDate value="${obj.passedTime}" pattern="yyyy-MM-dd"/>
						</td>
						<td >
							${obj.passedScore}
						</td>
						<td >
							${obj.confidentialityStaffNumber}
						</td>
						<td >
						    ${obj.checkGroup} 
						    
						</td>
						<td>      
						<img src="${ctx }/images/fileInfo/renwu/tip.png">&nbsp;&nbsp;
					    <input type="button" name="button" value="处理" onclick="handleCertflow('${obj.taskId}')"> 
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
		$(function(){
			var jsonStr = $('#list_noJSON').val();
			$('#unitName').jsonSuggest(eval(jsonStr),{onSelect:checkedListCallback, maxResults:10});
		})
		function checkedListCallback(item){
			$('#unitName').val(item.text.split("，")[1]);
		}
		function manageUnit(){
			ModelWindow_dialog("${ctx}/jsp/unit/unit/manageUnit.jsp?1=1",800,600);	
		}
		
		function deleteTaskInfo(taskId){
			var returnValue = ModelWindow_dialog("deleteTaskInfo.do?taskId="+taskId+'&taskEdit=true',800,600);
			if(returnValue == "ok"){
		   		spPageSubmit();
			}
		}
		closeDialog = function(){
			$('#dialog').dialog('close');
		}		
		function handleCertflow(taskId){
   			var pkUnit=$("#pkUnit").val();	
   			navTab.openTab("baomijianchaFlow", "checkFlow.do?1=11", { title:"保密检查流程", fresh:false, data:{"taskId":taskId,"pkUnit":pkUnit} });
   			// window.location.href="certificationProcess.do?edit=true&certTaskId="+certTaskId+"&pkUnit="+pkUnit;
   			//ModelWindow_dialog("addCertTaskInfo.do?certTaskId="+certTaskId+"&pkUnit="+pkUnit,1000,800);
   			
   			//$.pdialog.open("addCertTaskInfo.do?certTaskId="+certTaskId+"&pkUnit="+pkUnit, "yijianshu"," ",{width:800,height:600,mask:false,mixable:true,minable:true,resizable:true,drawable:true} );   
   		}
</script>
