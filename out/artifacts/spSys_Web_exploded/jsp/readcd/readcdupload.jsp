<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../../../common/include.jsp"%>
<div class="pageHeader">
	<form method="post" id="form1"   name="form1" action="importCdWord.do"  enctype="multipart/form-data" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<input type="hidden" name="certTaskId" id="certTaskId"> 
		<div class="pageContent" layOutH="138">
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
				<c:forEach items="${docfile}" var="item1" varStatus="s" >
				<tr>
					<td>    
					<label><input name="cdindex"  id="cdindex"  type="radio" value="${item1.path}"  <c:if test="${s.index == 0}">checked="checked"</c:if>  >${item1.name}</label>   
					</td>
					  
  				</tr>  
  				</c:forEach>
			</table>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><input type="button" name="button" value="导入" class="button" onclick="upLoad()"/></div></div></li>
			</ul>
		</div>
		<div id="tableQuery">
			</div>
	</form>
</div>
      <script language="javascript">
      	
         $(function(){	
			var jsonStr1 = $('#list_noJSONImprot').val();
			$('#unitNameImprot').jsonSuggest(eval(jsonStr1),{onSelect:checkedListCallbackImprot, maxResults:10});	
			
		})
		
		function checkedListCallbackImprot(item){
			$('#unitNameImprot').val(item.text.split("，")[1]);
			$('#unitNo').val(item.text.split("，")[0]);
			alert(item.text.split("，")[1])
		}
		function upLoad(){
		alert($("#doc").val());
		  	var item = $(":radio:checked"); 
			var len=item.length; 
			if(len==0){ 
  				alert("请选择上传单位");
  				return false;
			}   
			if($("#cdindex").val()==null || $("#cdindex").val()==''){ 
  				alert("请选择要上传的doc文档");
  				return false;
			}
			
		  	selRadio();
			$("#form1")[0].submit();    
			alert("已经提交了")  
		 }			
		  function query(){
		   var unitNo=$('#unitNo').val(); 
		   var certType=$("#certType").find("option:selected").val();    
		   alert(certType)  
		   		  $.post("queryWordToCertTask.do?unitNo="+unitNo+"&certType="+certType,function(data){
		   		  alert(data)
		    	  $("#tableQuery").html("");
		          $("#tableQuery").append(data);  
    				});
		 }
		  function selRadio(){
			var item = $(":radio:checked"); 
			var len=item.length; 
			if(len>0){ 
  				var certTaskId=item.val();
  				$("#certTaskId").val(certTaskId);
			} 
		 } 
      </script>
