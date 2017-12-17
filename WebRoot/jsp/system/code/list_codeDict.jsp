<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="tabsContent" style="height: 400px;">
	<form id="" action="saveCodeDict.do" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
	
	<div class="">
	<ul>
			<li><div class="button"><div class="buttonContent"><button id="addButton" type="button" onclick="addRow()">新加行</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button  type="submit" >保存</button></div></div></li>
		</ul>
	</div>
	<input  type="hidden" value="${kindId}" name="codeDict.parentCodeId">
	
		<table class="list nowrap" id="codeDictTable"  width="100%">
			<thead>
				<tr>
					<th type="hidden" style="display: none;" name="codeId" ></th>
					<th type="text">字典代码</th>
					<th type="text" >字典名称</th>
					<th type="del" width="60">操作</th>
				</tr>
				
			</thead>
			<tbody>
			<c:forEach items="${codeDictList}" var="obj" varStatus="status">
				<tr >
					<td style="display: none;"><input type="text" name="codeDict.codeId" value="${obj.codeId}"/></td>
					<td><input class="required"  type="text" readOnly="readOnly" name="codeDict.codeCode" value="${obj.codeCode}"/></td>
					<td><input class="required" type="text" name="codeDict.codeName" value="${obj.codeName}"/></td>
					<td><a class="btnDel "  onclick="delRow(this)">删除</a></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	</form>
<script type="text/javascript">
function delRow(obj){
	$(obj).parent().parent().remove();
}
function addRow(){
debugger
	var maxValue=0;
	$("input[name='codeDict.codeCode']").each(function(){
			var actValue=$(this).val();
			if(actValue.substr(0,1)=='0')//解决多浏览器问题  IE8中parseInt('08')==0  其他浏览器中==8
			actValue=actValue.substr(1,actValue.length);
			if(parseInt(actValue)>=maxValue)
			maxValue=parseInt(actValue)+1;
	})		
	debugger
	maxValue=''+maxValue;
	if(maxValue.length==1)maxValue='0'+maxValue;
	$('<tr ><td style="display: none;"><input type="text" name="codeDict.codeId" value=""/></td><td><input type="text" readOnly="readOnly" class="required textInput readonly" name="codeDict.codeCode" value="'+maxValue+'"/></td><td><input type="text"  class="required textInput" name="codeDict.codeName" value=""/></td><td><a class="btnDel "  onclick="delRow(this)">删除</a></td></tr>').appendTo("#codeDictTable")
}
</script>