<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.xunj_prj/tag/webwidget" prefix="w"%>
<%@ include file="../../../common/include.jsp"%>
<%@ include file="../../../common/include2.jsp"%>  
<style>
table.b{
	border-right:0px solid #000000;
	border-bottom:0px solid #000000;
	border-left:0;
	border-top:0;
	font-family:"宋体";
}
table.b td {
	border-left:1px solid #c2c2c2;
	border-top:1px solid #c2c2c2;
	border-right:1px;
	border-bottom:1px;
	font-family:"宋体";
	background: white;
}

table.b th {  
	padding:5px;
	border-left:1px solid #9a9a9a;
	border-top:1px solid #9a9a9a;
	border-right:0;
	border-bottom:0;
	font-family:"宋体" ; 
	background:  #f7f7f7  ;  
}  
</style>
<div class="pageHeader">
<form id="pagerForm" method="post" name="form1" action="listCensor.do" onsubmit="return navTabSearch(this);">
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
				<td  >
					&nbsp;&nbsp;<input size="80" type="text" name="unitName" id="unitName" class="inputLength" value="${unitName }">
				</td>
			</tr>
			<tr>
				<td  class="">
					所属集团
				</td>
				<td  >
					&nbsp;&nbsp;<select name="unitGroup" class="inputLength"  id="unitGroup">
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='009'}">  
							 	<option value="${item.codeId}" <c:if test="${item.codeId == unitGroup }">selected</c:if>>${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
				</td>
				<td >
					 其他资料
				</td>
				<td>
					&nbsp;&nbsp;<select name="otherFileStatus" class="inputLength" id="otherFileStatus">
						<option></option>
						<option value="1" <c:if test="${otherFileStatus == '1' }">selected="selected"</c:if> >有</option>
						<option value="0" <c:if test="${otherFileStatus == '0' }">selected="selected"</c:if>>无</option>
					</select>
				</td>
			
				<td >
					认证申请书
				</td>
				<td >
					&nbsp;&nbsp;<select name="applicationStatus" class="inputLength" id="applicationStatus">
						<option></option>
						<option value="1" <c:if test="${applicationStatus == '1' }">selected="selected"</c:if>>有</option>
						<option value="0" <c:if test="${applicationStatus == '0' }">selected="selected"</c:if>>无</option>
					</select>
				</td>
				</tr>
			<tr>
				<td >
					汇报资料
				</td>
				<td >
					&nbsp;&nbsp;<select name="reviewInfoStatus" class="inputLength" id="reviewInfoStatus">
						<option></option>
						<option value="1" <c:if test="${reviewInfoStatus == '1' }">selected="selected"</c:if> >有</option>
						<option value="0" <c:if test="${reviewInfoStatus == '0' }">selected="selected"</c:if>>无</option>
					</select>
				</td>
				<td >
					分数情况	
				</td>
				<td>
					&nbsp;&nbsp;<select name="scoreStatus" class="inputLength" id="scoreStatus">
						<option></option>
						<option value="1" <c:if test="${scoreStatus == '1' }">selected="selected"</c:if> >有</option>
						<option value="0" <c:if test="${scoreStatus == '0' }">selected="selected"</c:if>>无</option>
					</select>
				</td>
				<td >
					认证意见书
				</td>
				<td>
					&nbsp;&nbsp;<select name="submissionStatus" class="inputLength" id="submissionStatus">
						<option></option>
						<option value="1" <c:if test="${submissionStatus == '1' }">selected="selected"</c:if> >有</option>
						<option value="0" <c:if test="${submissionStatus == '0' }">selected="selected"</c:if>>无</option>
					</select>
				</td>
				</tr>
				<tr>
				
				<td >
					注册地址
				</td>
				<td>
					&nbsp;&nbsp;<input type="text" name="unitLoginAddress" id="unitLoginAddress"  class="inputLength" value="${unitLoginAddress }">
				</td>
				<td >
					认证通过时间
				</td>
				<td>
					&nbsp;&nbsp;<input type="text" name="dateFrom" id="dateFrom" value="${dateFrom }" class="date" >至<input type="text" name="dateTo" id="dateTo" value="${dateTo }" class="date">
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
			<li><a class="add" href="addCensor.do?1=1" target="navTab"><span>添加</span></a></li>
			
			<!-- <li><a class="delete" href="deleteCustomer.do?uid={sid_pkCustomer}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li> -->
			<li><a class="icon" href="listExportExcel.do?unitName=${unitName}&unitGroup=${unitGroup}&applicationStatus=${applicationStatus}&reviewInfoStatus=${reviewInfoStatus}&scoreStatus=${scoreStatus}&submissionStatus=${submissionStatus}&otherFileStatus=${otherFileStatus}&dateFrom=${dateFrom}&dateTo=${dateTo}" target="dwzExport" targetType="navTab" title="确定要导出这些记录吗?"><span>导出EXCEL</span></a></li>
			<li><a class="add" target="navTab" href="certTaskInfoImport.do" ><span>导入意见书</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
<div class="tabs" currentIndex="0" eventType="click">  
    <div class="tabsHeader">  
        <div class="tabsHeaderContent">  
            <ul>  
                <li><a href="#" class="selected"><span>已完成认证(${totalCount2}家)
				<input type="hidden" id="list_noJSON" value="${unitData}"></span></a></li>  
                <li><a href="#"><span>待处理认证(${totalCount1}家)</span></a></li>  
            </ul>  
        </div>  
    </div>  
    <div class="tabsContent" layoutH="210">  
        <div  class="pageContent" >
			<table class="b" width="100%">
				<tr >
					<th>
						认证状态
					</th>
					<th>
						认证单位名称
					</th>
					<th>
						审核认证等级
					</th>
					<th>
						认证通过时间
					</th>
					
					<th>
						通过分数
					</th>
					<th>
						法定代表人
					</th>
					<th>
						认证申请书
					</th>
					<th>
						汇报资料
					</th>
					<th>
						分数情况
					</th>
					<th>
						认证结论
					</th>
					<th>
						整改计划
					</th>
					<th>
						自查1
					</th>
					<th>
						自查2
					</th>
					<th>
						其他资料
					</th>
					<th>
						操作
					</th>
				</tr>
				<c:forEach items="${alreadyList}" var="obj">
					<tr>
						<td nowrap>
							<img src="${ctx }/images/fileInfo/renwu/tip.png">&nbsp;&nbsp;
					    <input type="button" name="button" value="处理" onclick="handleCert2('${obj.certTaskId}')">
							<c:if test="${obj.certStatus == '012004' || obj.certStatus == '012005'}"><w:CodeIdDictOut value="${obj.certType }"></w:CodeIdDictOut>通过</c:if><c:if test="${obj.certStatus != '012004' && obj.certStatus != '012005'}"><w:CodeIdDictOut value="${obj.certType }"></w:CodeIdDictOut><w:CodeIdDictOut value="${obj.certStatus }"></w:CodeIdDictOut></c:if>
						</td>
						<c:forEach items="${obj.unitInfoList}" var="obj1">
							
							<td >
								${obj1.unitName }
								<input type="hidden" name="pkUnit1" id="pkUnit1" value="${obj1.pkUnit }">
							</td>
							<td nowrap>
								<w:CodeIdDictOut value="${obj1.censorLevel}"></w:CodeIdDictOut>
							</td>
							<td>
								<fmt:formatDate value="${obj.passedTime}" pattern="yyyy-MM-dd"/>
							</td>
							<td>
								${obj.passedScore }
							</td>
							<td nowrap>
								${obj1.legalRepresentative }
							</td>
						</c:forEach>
						<td style="background-color:<c:if test="${obj.applicationStatus != '1'}">#99CC33</c:if>">
							<input type="button"  name="button" value="申请书" onclick="upLoadFile('${obj.certTaskId }','application')">
						</td>
						<td style="background-color:<c:if test="${obj.reviewInfoStatus != '1'}">#99CC33</c:if>">
							<input type="button"  name="button" value="汇报资料" onclick="upLoadFile('${obj.certTaskId }','review')">
						</td>
						<td style="background-color:<c:if test="${obj.numPeople == null && obj.highestScore == null && obj.average == null}">#99CC33</c:if>">
							<input type="button"  name="button" value="分数情况" onclick="viewScore('${obj.certTaskId }')">
						</td>
						<td>
							<c:if test="${obj.certStatus != '012006'}">
								<input type="button"  name="button" value="意见书" onclick="viewCert('${obj.certTaskId }')">
							</c:if>
						</td>
						<td style="background-color:<c:if test="${obj.planFileId == null || obj.planFileId == ''}">#99CC33</c:if>">
							<c:if test="${obj.certStatus != '012006'}">
								<input type="button" name="button" value="整改报告" onclick="upLoadFile('${obj.certTaskId }','plan')">
							</c:if>	
						</td>
						<td style="background-color:<c:if test="${obj.checkFile1Status != '1'}">#99CC33</c:if>">
							<input type="button"  name="button" value="自查1" onclick="upLoadFile('${obj.certTaskId }','checkFile1')">
						</td>
						<td style="background-color:<c:if test="${obj.checkFile2Status != '1'}">#99CC33</c:if>">
							<input type="button"  name="button" value="自查2" onclick="upLoadFile('${obj.certTaskId }','checkFile2')">
						</td>
						<td style="background-color:<c:if test="${obj.otherFileStatus != '1'}">#99CC33</c:if>">
							<input type="button"  name="button" value="其他资料" onclick="upLoadFile('${obj.certTaskId }','otherFile')">
						</td>
						<td>
							<a class="delete" href="deleteCertTaskInfo.do?certTaskId=${obj.certTaskId}" target="ajaxTodo" title="你确定要删除吗？" ><span>删除</span></a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>  
        <div>
			<table class="b" width="100%">
				<tr >
					<th>
						认证状态
					</th>
					<th>
						认证单位名称
					</th>
					<th>
						审核认证等级
					</th>
					<th>
						认证通过时间
					</th>
					<th>
						法定代表人
					</th>
					<th>
						认证类型
					</th>
					<th>
						认证进度
					</th>
					
					<th>
						操作
					</th>
				</tr>
				<c:forEach items="${havaNotList}" var="obj">
				<tr>
					<td>
						<img src="${ctx }/images/fileInfo/renwu/tip.png">&nbsp;&nbsp;
					    <input type="button" name="button" value="处理" onclick="handleCert2('${obj.certTaskId}')">
					</td>
					<c:forEach items="${obj.unitInfoList}" var="obj1">
					<td nowrap>
						${obj1.unitName}
						<input type="hidden" name="pkUnit" id="pkUnit" value="${obj1.pkUnit }">
					</td>
					<td>
						<w:CodeIdDictOut value="${obj1.censorLevel}"></w:CodeIdDictOut>
					</td>
					<td>
						<fmt:formatDate value="${obj.passedTime }" pattern="yyyy-MM-dd"/>
					</td>
					<td>
						${obj1.legalRepresentative }
					</td>
					</c:forEach>
					<td>
						<w:CodeIdDictOut value="${obj.certType }"></w:CodeIdDictOut>
					</td>
					<td style="background-color:<c:if test="${obj.certStatus =='012001'}">#663366</c:if><c:if test="${obj.certStatus =='012002'}">yellow</c:if> ">
						<w:CodeIdDictOut value="${obj.certStatus }"></w:CodeIdDictOut>
					</td>
					<td >
						<a class="delete" href="deleteCertTaskInfo.do?certTaskId=${obj.certTaskId}" target="ajaxTodo" title="你确定要删除吗？" ><span>删除</span></a>
					</td>	
				</tr>
				</c:forEach>		
			</table>
		</div> 
    </div>  
</div>



</div>
<script type="text/javascript" language="javascript">          
		 $(function(){
			var jsonStr = $('#list_noJSON',navTab.getCurrentPanel()).val();
			$('#unitName',navTab.getCurrentPanel()).jsonSuggest(eval(jsonStr),{onSelect:checkedListCallbackCensor, maxResults:10});	
			$("#checkAll",navTab.getCurrentPanel()).bind("click",checkAllClick);
		})
		
		function checkedListCallbackCensor(item){
			$('#unitName',navTab.getCurrentPanel()).val(item.text.split("，")[1]);
		}
		function handleCert2(certTaskId){
   			var pkUnit=$("#pkUnit").val();	
   			navTab.openTab("certificationProcess", "certificationProcess.do?edit=true", { title:"审查认证流程", fresh:false, data:{"certTaskId":certTaskId,"pkUnit":pkUnit} });
   			// window.location.href="certificationProcess.do?edit=true&certTaskId="+certTaskId+"&pkUnit="+pkUnit;
   			//ModelWindow_dialog("addCertTaskInfo.do?certTaskId="+certTaskId+"&pkUnit="+pkUnit,1000,800);
   			
   			//$.pdialog.open("addCertTaskInfo.do?certTaskId="+certTaskId+"&pkUnit="+pkUnit, "yijianshu"," ",{width:800,height:600,mask:false,mixable:true,minable:true,resizable:true,drawable:true} );   
   		}
		function handleCert(certStatus,certTaskId){
			var pkUnit=$("#pkUnit").val();
			if(certStatus == '012001'){//待认证状态
				if(confirm("确定要开始认证吗？")){
    				$.post("addStartCensor.do?certTaskId="+certTaskId,function(data){
    					window.location.href="listCensor.do?unitName="+$('#unitName').val()+"&unitGroup="+$('#unitGroup').val()+"&applicationStatus="+$('#applicationStatus').val()+"&reviewInfoStatus="+$("#reviewInfoStatus").val()+"&scoreStatus="+$("#scoreStatus").val()+"&submissionStatus="+$("#submissionStatus").val()+"&otherFileStatus="+$("#otherFileStatus").val();
    				})
    			}
   			}else if(certStatus == '012002'){//认证中状态
	   			if(confirm("是否结束认证？")){
	   				if(confirm("确定通过认证，取消未通过认证")){
	   					$.post("addCertTaskInfo.do?certTaskId="+certTaskId+"&certStatus=012003&pkUnit="+pkUnit+"&selStatus=true",function(data){
					   		// alert(data)
					    	// $("#tableQuery").html("");
					        // $("#tableQuery").append(data);
    					});
    				}else{
    					$.post("addCertTaskInfo.do?certTaskId="+certTaskId+"&certStatus=012006&pkUnit="+pkUnit,function(data){
					   		// alert(data)
					    	// $("#tableQuery").html("");
					        // $("#tableQuery").append(data);
    					});
    				}
	   			}
   			}
   		}
   		
   		function upLoadFile(certTaskId,fileType){   
   		  	//var tab = $('#tt').tabs('getSelected');          
   			//var returnValue=ModelWindow_dialog("upLoadFile.do?certTaskId="+certTaskId+"&fileType="+fileType,800,600);
   			$.pdialog.open("upLoadFile.do?certTaskId="+certTaskId+"&fileType="+fileType, "shenqingshu"," ",{width:800,height:600,mask:false,mixable:true,minable:true,resizable:true,drawable:true} );   
   			//window.parent.document.getElementById("")  
   			//if(returnValue == 'ok'){  
   				 //window.location.href="listCensor.do?unitName="+$('#unitName').val()+"&unitGroup="+$('#unitGroup').val()+"&applicationStatus="+$('#applicationStatus').val()+"&reviewInfoStatus="+$("#reviewInfoStatus").val()+"&scoreStatus="+$("#scoreStatus").val()+"&submissionStatus="+$("#submissionStatus").val()+"&otherFileStatus="+$("#otherFileStatus").val();  				
   			//}
   		}
   		
   		function viewCert(certTaskId){
   			var pkUnit=$("#pkUnit1").val();	
   			navTab.openTab("yijianshu", "addCertTaskInfo.do?edit=true", { title:"意见书", fresh:false, data:{"certTaskId":certTaskId,"pkUnit":pkUnit} });
   			 
   			//ModelWindow_dialog("addCertTaskInfo.do?certTaskId="+certTaskId+"&pkUnit="+pkUnit,1000,800);
   			
   			//$.pdialog.open("addCertTaskInfo.do?certTaskId="+certTaskId+"&pkUnit="+pkUnit, "yijianshu"," ",{width:800,height:600,mask:false,mixable:true,minable:true,resizable:true,drawable:true} );   
   		}
   		
   		function viewScore(certTaskId){
   			$.pdialog.open("addCertScore.do?certTaskId="+certTaskId, "fenshuqingkuang"," ",{width:800,height:600,mask:false,mixable:true,minable:true,resizable:true,drawable:true} );     
   			//var returnValue=ModelWindow_dialog("addCertScore.do?certTaskId="+certTaskId,800,600);	
   			if(returnValue == 'ok'){
    			window.location.href="listCensor.do?unitName="+$('#unitName').val()+"&unitGroup="+$('#unitGroup').val()+"&applicationStatus="+$('#applicationStatus').val()+"&reviewInfoStatus="+$("#reviewInfoStatus").val()+"&scoreStatus="+$("#scoreStatus").val()+"&submissionStatus="+$("#submissionStatus").val()+"&otherFileStatus="+$("#otherFileStatus").val();
    		}
   		}
   		
   		
   		function wordToCertTask(){
   			var returnValue=ModelWindow_dialog("certTaskInfoImport.do?1=1",800,600);	
   			if(returnValue == 'ok'){
    			window.location.href="listCensor.do?unitName="+$('#unitName').val()+"&unitGroup="+$('#unitGroup').val()+"&applicationStatus="+$('#applicationStatus').val()+"&reviewInfoStatus="+$("#reviewInfoStatus").val()+"&scoreStatus="+$("#scoreStatus").val()+"&submissionStatus="+$("#submissionStatus").val()+"&otherFileStatus="+$("#otherFileStatus").val();
    		}
   		}
   		
   		function checkAllClick(thisClick){
    		if($("#checkAll").attr("checked") == true){
    			 $("input[name='check']").attr("checked",$("#checkAll").attr("checked"));
    			 $("input[name='checkValue']").each(function(){
    			 	$(this).val('1');
    			 		
    			 })
    		}else{
    			 $("input[name='check']").attr("checked",false);
    			 $("input[name='checkValue']").each(function(){
    			 	$(this).val('0');
    			 })	
    		}
    	}
    	function closewindow(){
    		window.location.href="listCensor.do?unitName="+$('#unitName').val()+"&unitGroup="+$('#unitGroup').val()+"&applicationStatus="+$('#applicationStatus').val()+"&reviewInfoStatus="+$("#reviewInfoStatus").val()+"&scoreStatus="+$("#scoreStatus").val()+"&submissionStatus="+$("#submissionStatus").val()+"&otherFileStatus="+$("#otherFileStatus").val();
    	}
</script>
