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
			
				<td  class="">
					申请年份
				</td>
				<td  class="left_align">
					&nbsp;&nbsp;<input type="text" name="appTime" id="appTime" class="inputLength" value="${appTime }" >
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
			<!-- <li><a class="add" href="addTaskInfo.do?1=1" target="navTab"><span>添加</span></a></li> -->
			<li><a title="确定要删除吗?" target="ajaxTodo" rel="ids" href="deleteTaskInfo.do?taskId={sid_taskId}&taskEdit=true" class="delete"><span>删除</span></a></li>
			<!-- <li><a class="delete" href="deleteCustomer.do?uid={sid_pkCustomer}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li> -->
			<li><a class="edit" href="addTaskInfo.do?taskId={sid_taskId}&taskEdit=true&taskUpdate=1" target="navTab"><span>修改</span></a></li>
			
			<li><a class="add"  href="#" onclick="dataImpFlow()" ><span>导入申请书附件等文件</span></a></li>
			
			<!-- <li><a class="icon" href="taskInfoExportExcel.do" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li> -->
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138" >
				<thead>
				<tr >
					<th>
						处理
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
						申请理由
					</th>
					<th>
						申请年份
					</th>
					<th>
						状态
					</th>
					<th>审查分数</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${dataList}" var="obj">
					<tr  target="sid_taskId" rel="${obj.pkbaseApp }">
						<td>      
							<img src="${ctx }/images/fileInfo/renwu/tip.png">&nbsp;&nbsp;
						    <input type="button" name="button" value="处理" onclick="handleCertflow('${obj.pkbaseApp}')"> 
						</td>
						<td >
							${obj.companyName}  
						</td>
						<td >
							${obj.creditCode}
						</td>
						<td >
							${obj.baseInfo.companyType }
						</td>
						<td >
							${obj.baseInfo.legalBody }
						</td>
						<td >
							${obj.baseInfo.companyPersonCount }
						</td>
						
						<td >
							${obj.baseInfo.secretPersonCount }
						</td>
						<td >
							${obj.applyReason}
						</td>
						<td >
						    ${obj.appTime} 
						    
						</td>
						<td >
						    <c:if test="${obj.spStatus=='1'}">  
						    	已导入申请书数据
						    </c:if>
						</td>
						<td ><input type="text"  size="10" id="certScore${i.index+1}" value="${obj.certScore}"><input onclick="ajaxCertScore('${i.index+1}','${obj.pkbaseApp}');" type="button" value="√"/></td>
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
		function deleteTaskInfo(taskId){
			var returnValue = ModelWindow_dialog("deleteTaskInfo.do?taskId="+taskId+'&taskEdit=true',800,600);
			if(returnValue == "ok"){
		   		spPageSubmit();
			}
		}
		closeDialog = function(){
			$('#dialog').dialog('close');
		}		
		function handleCertflow(pkbaseApp){
   			navTab.openTab("spsysCheckFlow", "checkFlow.do?1=11", { title:"申请审批受理流程", fresh:false, data:{"pkbaseApp":pkbaseApp} });
   		}
   		
   		function dataImpFlow(){
	   		$.pdialog.open("sqlImport.do?type=1", "sqlImportFlow","上传文件",{width:800,height:600,mask:false,mixable:true,minable:true,resizable:true,drawable:true} );
	   	}
	   	
	   	function ajaxCertScore(num,pkbaseApp){
	   		$.post("ajaxCertScore.do?pkbaseApp="+pkbaseApp+"&certScore="+$("#certScore"+num+"").val(),function(data){
				//window.location.href="addAssessTaskInfo.do?assessTaskId="+data;
				alert("分数保存成功")
			})
	   	}
	   	
</script>
