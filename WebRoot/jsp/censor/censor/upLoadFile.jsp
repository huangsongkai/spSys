<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../common/include.jsp"%>
<%@ include file="../../../common/include2.jsp"%>
<html>
  <head>
    <title>上传文件</title>
    <script type="text/javascript">
    	function deleteFile(id,certTaskId,fileType){
    		var urlStr='';
    		if(fileType=='img'){
    			var count = $("#count").val();
    			urlStr= 'ajaxDeleteFile.do?id='+id+'&certTaskId='+certTaskId+'&count='+count+'&fileType='+fileType
    		}else{
    			urlStr= 'ajaxDeleteFile.do?id='+id+'&certTaskId='+certTaskId+'&fileType='+fileType
    		}
			$.ajax({
				async: false,
				type : "POST",
				url : urlStr,
				dataType : 'json',
				success : function() {
							alert("删除成功");
						  }
			}); 
			
			
			
			if(fileType=='img'){
				var idStr = "";
				var ids = certTaskId.split("|");
				for(var i=0;i<ids.length;i++){
					var idss = ids[i].substring(0,ids[i].length-1)
					if(idss != id){
						idStr += ids[i]+"|";
					}
				}
				idStr = idStr.substring(0,idStr.length-1);
				$("#imgId").val(idStr);
				$("#downloadTabel").find("tr").get(1).innerHTML='<tr><td width="15%">上传文件</td><td width="35%" class="left_align"><input name="doc" type="file" size="40"></td></tr>';
				$("#downloadTabel").append('<tr><td colspan="2"><input type="submit" name="button" value="保存" class="btn_standard"></td></tr>');
				
			}else{
				$("#download").find("td").get(0).innerHTML='是否存在文件';
				$("#download").find("td").get(1).innerHTML='<input type="checkbox" name="fileStatus">';
				$("#downloadTabel").append('<tr><td width="15%">上传文件</td><td width="35%" class="left_align"><input name="doc" type="file" size="40"></td></tr>');
				$("#downloadTabel").append('<tr><td colspan="2"><input type="submit" name="button" value="保存" class="btn_standard"></td></tr>');
			}
			
		}	
    
       function downLoad(id){
       		//ModelWindow_dialog("jump.do?path=docdownload.do&id="+id,800,600);
       		//$.pdialog.open("jump.do?path=docdownload.do&id"+id, "dakaiwenjian"," ",{width:800,height:600,mask:false,mixable:true,minable:true,resizable:true,drawable:true} );      
      	
      	
      	 $.ajax({
					async: false,
					type : "POST",
					url : 'dload.do?id='+id,  
					dataType : 'json',
					success : function() {
								alert("删除成功");
							  }
				}); 
       }
       


       
    </script>
  </head>
  <body>
  	<div align="center">
  		<form method="post" action="saveUpLoadFile.do" class="pageForm required-validate" enctype="multipart/form-data" onsubmit="return iframeCallback(this, dialogAjaxDone);">
  			<input type="hidden" id="certTaskId" name="certTaskId" value="${param.certTaskId }">
			<input type="hidden"  name="certId" value="${param.certId}">
			<input type="hidden"  name="ruleId" value="${param.ruleId}">
			<input type="hidden"  name="fileType" value="${param.fileType}">
			<input type="hidden" id="imgId" name="imgId" value="${param.imgId}">
			<input type="hidden" id ="count"   name="count" value="${param.count}">
  			<table width="80%" id="downloadTabel">
  				<tr>
  					<th colspan="2">文件管理</th>
  				</tr>	
  				<c:if test="${tag == 1}">
  					<c:if test="${param.fileType=='img'}">
		  				<tr id="download">
		  					<td width="15%">
		  						下载图片       
		  					</td>
		  					<td width="35%" class="left_align">
		  						<a href="download.do?fileName=${uploadFile.putPath}">${uploadFile.fileName }</a>              
		  						<input type="button" name="button" value="删除" onclick="deleteFile('${uploadFile.fileId}','${param.imgId }','${fileType }' )">
		  					</td>
		  				</tr>
	  				</c:if>
	  				<c:if test="${param.fileType!='img'}">
		  				<tr id="download">
		  					<td width="15%">
		  						下载文件
		  					</td>
		  					<td width="35%" class="left_align">
		  						<a href="download.do?fileName=${uploadFile.putPath}">${uploadFile.fileName }</a>      
		  						<input type="button" name="button" value="删除" onclick="deleteFile('${uploadFile.fileId}','${certTaskId }','${fileType }' )">
		  					</td>
		  				</tr>
	  				</c:if>
  				</c:if>
  				<c:if test="${tag != 1}">
  					<c:if test="${param.fileType!='img'}">
	  					<tr>
	  						<td width="15%">
	  							是否存在文件
	  						</td>
	  						<td class="left_align"  width="35%">
	  							<input type="checkbox" name="fileStatus" value="0" 
	  							<c:if test="${fileStatus=='1'}">checked="checked"</c:if>
	  							>
	  						</td>
	  					</tr>
		  				<tr>
		  					<td width="15%"  class="">
								上传文件
							</td>
							<td class="left_align"  width="35%">
								<input name="doc" type="file" size="40"> 
							</td>
		  				</tr>
		  				<tr>
		  					<td colspan="2">
		  						<input type="submit" name="button" value="保存" class="btn_standard">
		  					</td>
		  				</tr>
	  				</c:if>
	  				<c:if test="${param.fileType=='img'}">
		  				<tr>
		  					<td width="15%"  class="">
								上传图片
							</td>
							<td class="left_align"  width="35%">
								<input name="doc" type="file" size="40"> 
							</td>
		  				</tr>
		  				<tr>
		  					<td colspan="2">
		  						<input type="submit" name="button" value="保存" class="close">
		  					</td>
		  				</tr>
	  				</c:if>
  				</c:if>
  			</table>
  		</form>
  	</div>
  </body>
</html>
