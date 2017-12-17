<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../../../common/include.jsp"%>
<div class="pageContent" layoutH="1" style="border-top:1px #B8D0D6 solid;border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
	<form method="post" action="saveUpdateFuncInfo.do" class="pageForm required-validate" onsubmit="return validateCallback(this,navTabAjaxDone);">
	<input type="hidden" id="funcId" name="funcInfo.funcId" value="${funcInfo.funcId }">
	<input type="hidden" id="funcParentId" name="funcInfo.funcParentId" value="${funcInfo.funcParentId }">
		<div class="pageFormContent" style="height:380px;">
			<p>
				<label>本级ID：</label>
					<input type="text"  name="funcInfo.funcId" value="${funcInfo.funcId}" readonly="readonly" />
			</p>
			<p>
				<label>本级名称：</label>
					<input type="text" name="funcInfo.funcName" value="${funcInfo.funcName}" />
			</p>
			<p>
				<label>菜单级别(标识)：</label>
					<input type="text"  name="funcInfo.funcType" value="${funcInfo.funcType}" />
			</p>
			<p>
				<label>排序：</label>
					<input type="text"  name="funcInfo.orderCol"  value="${funcInfo.orderCol}"/>
			</p>
			<p>
				<label>图标位置：</label>
					<input type="text" name="funcInfo.urlImg" id="urlImg" value="${funcInfo.urlImg}"/>
			</p>
			<input type="hidden" name="funcInfo.onclickFunction" id="funcInfo.onclickFunction"  value="${funcInfo.onclickFunction}" />
			<p>
			<label>url：</label>
			<textarea cols="30" rows="5" name="funcInfo.url" id="funcInfo.url">${funcInfo.url}</textarea>
			</p>
			<p>
				<label>备注：</label>
				<textarea cols="30" rows="5" name="funcInfo.remark" id="funcInfo.remark">${funcInfo.remark}</textarea>
			</p>
			
		</div>
		 
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div>
				</li>
				<li>
					<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="delFuncInfo()">删除</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
function delFuncInfo(){
	$("form").attr("action","delFuncInfo.do");
	$("form").submit();
}
</script>
	