<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../../../common/include.jsp"%>
<style>
table.b{
	border-right:0px solid #000000;
	border-bottom:0px solid #000000;
	border-left:0;
	border-top:0;
	font-family:"宋体";
}
table.b td {
	padding:5px;
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
	<form method="post" id="form1"   name="form1" action="wordToCertTask.do"  enctype="multipart/form-data" class="pageForm required-validate" onsubmit="return iframeCallback(this, navTabAjaxDone);">
		<input type="hidden" name="certTaskId" id="certTaskId"> 
		<div class="pageContent" layOutH="538">
			<table class="searchContent"  width="80%">
				<tr>
					<td align="center" width="40%">
						军工单位名称
						<input type="hidden" id="list_noJSONImprot" value="${unitData}">
					</td>
					<td width="60%">
						&nbsp;&nbsp;<input type="text" name="unitName" id="unitNameImprot" size="80" class="inputLength"  value="${unitName }">
					<input type="hidden" id="unitNo" value="${unitNo}" >
					<input type="hidden" id="list_noJSON" value="${unitData}">  
					<input type="button" name="button" value="查询"  onclick="query()">
					</td>
				</tr>
				<tr>
					<td  align="center" >
						认证类型   
					</td>
					<td width="60%">
						&nbsp;&nbsp;<select name="certType" class="inputLength"  id="certType">
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
						导入文件
					</td>
					<td >
						&nbsp;&nbsp;<input name="doc" id="doc"  type="file" size="40" fileQueue="fileQueue" >
					</td>
  				</tr>
			</table>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button name="button" type="submit" >导入</button></div></div></li>
			</ul>
		</div>
		<div id="tableQuery" style="overflow:scroll">
		</div>
	</form>
</div>
      <script language="javascript">
         $(function(){
			var jsonStr1 = $('#list_noJSONImprot',navTab.getCurrentPanel()).val();
			$('#unitNameImprot',navTab.getCurrentPanel()).jsonSuggest(eval(jsonStr1),{onSelect:checkedListCallbackImprot, maxResults:10});	
			
		})
		
		function checkedListCallbackImprot(item){
			$('#unitNameImprot',navTab.getCurrentPanel()).val(item.text.split("，")[1]);
			$('#unitNo',navTab.getCurrentPanel()).val(item.text.split("，")[0]);
		}
		function upLoad(){
		  	var item = $(":radio:checked"); 
			var len=item.length; 
			if(len==0){ 
  				alert("请选择上传单位");
  				return false;
			}
		  	if($("#	").val()==""){
		  		alert("请选择上传文件");
		  		return false;
		  	}
		  	selRadio();
			$("#form1",navTab.getCurrentPanel())[0].submit();    
		 }			
		  function query(){
		   var unitNo=$('#unitNo').val(); 
		   var certType=$("#certType",navTab.getCurrentPanel()).find("option:selected").val();    
		   		  $.post("queryWordToCertTask.do?unitNo="+unitNo+"&certType="+certType,function(data){
		    	  $("#tableQuery",navTab.getCurrentPanel()).html("");
		          $("#tableQuery",navTab.getCurrentPanel()).append(data);  
    				});
		 }
		  function selRadio(){
			var item = $(":radio:checked"); 
			var len=item.length; 
			if(len>0){ 
  				var certTaskId=item.val();
  				$("#certTaskId",navTab.getCurrentPanel()).val(certTaskId);
			} 
		 } 
      </script>
