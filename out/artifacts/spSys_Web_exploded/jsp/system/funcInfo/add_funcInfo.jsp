<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../../../common/include.jsp"%>
<div class="pageContent" layoutH="1" style="border-top:1px #B8D0D6 solid;border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
	<form method="post" action="saveFuncInfo.do" class="pageForm required-validate" onsubmit="return validateCallback(this,navTabAjaxDone);">
		<div class="pageFormContent" style="height:380px;">
			<p>
				<label>本级ID：</label>
					<input type="text"  name="funcInfo.funcId" value="${newId}" readonly="readonly" />
			</p>
			<p>
				<label>上级ID：</label>
					<input type="text"  name="funcInfo.funcParentId" value="${parentId}" readonly="readonly" />
			</p>
			<p>
				<label>本级名称：</label>
					<input type="text" name="funcInfo.funcName" value="" class="required"/>
			</p>
			<p>
				<label>菜单级别(标识)：</label>
					<input type="text"  name="funcInfo.funcType" value="3" />
			</p>
			<p>
				<label>排序：</label>
					<input type="text"  name="funcInfo.orderCol"  value="" class="required number"/>
			</p>
			<p>
			<label>url：</label>
			<textarea cols="30" rows="5" name="funcInfo.url" id="funcInfo.url" class="required"></textarea>
			</p>
			<p>
				<label>备注：</label>
				<textarea cols="30" rows="5" name="funcInfo.remark" id="funcInfo.remark"></textarea>
			</p>
			
		</div>
		 
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
</script>
	