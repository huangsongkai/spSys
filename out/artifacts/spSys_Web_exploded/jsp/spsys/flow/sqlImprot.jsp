<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../../../common/include.jsp"%>


<div class="pageContent">
	<form action="dataImpWord.do" name="form1" id="form1" method="post" class="pageForm required-validate" enctype="multipart/form-data" onsubmit="return iframeCallback(this, dialogAjaxDone);">
  			<input type="hidden"  name="certTaskId" id="certTaskId" >
  			<input type="hidden"  name="type" id="type" value="${type }">
  			<table width="80%" id="downloadTabel">
  				<tr>
  					<th colspan="2">文件管理</th>
  				</tr>

  				<tr>
  					<td width="15%"  class="">
						导入文件
					</td>
					<td class="left_align"  width="35%">
						&nbsp;&nbsp;<input name="doc" type="file" size="40" id="doc">
					</td>
  				</tr>
  				<tr>
  					<td colspan="2"align="center">
  						<input type="submit" name="button" value="保存" class="close">
  						
  					</td>
  				</tr>
  			</table>
			<div id="tableQuery">
			
			</div>
			<div style = "text-align:right;">
			<button class="close" value="关闭">关闭</button>
			</div>
  		</form>
</div>
<script type="text/javascript">

</script>

