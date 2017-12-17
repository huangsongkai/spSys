<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.xunj_prj/tag/webwidget" prefix="w"%>
<script type='text/javascript' src='${pageContext.request.contextPath}/common/xjTree/xjTree.js'></script>
<div class="pageContent">
	<form method="post" action="saveFuncGroup.do"class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="57">
			<p>
				<label>角色名称：</label>
					<input name="funcGroup.funcGroupId" id="funcGroup.funcGroupId" type="hidden" value="${funcGroup.funcGroupId}" />
					<input name="funcGroup.funcGroupName" class="required" id="funcGroup.funcGroupName" type="text" value="${funcGroup.funcGroupName}" />
			</p>
			<p>
				<label>角色描述：</label>
					<input name="funcGroup.funcGroupDescription" class="required" id="funcGroup.funcGroupDescription" type="text" value="${funcGroup.funcGroupDescription}" />
			</p>
			
			
			<div class="divider"></div>
			<div>
			
			<w:mulitboxtree chooseType="checkbox" returnValue="funcId"
							displayValue="funcName" pidname="funcParentId"
							content="${hqlList}" idname="funcId" headValue="0"
							useDblClick="false" headDisplayValue="系统菜单" checkboxvalues="${funArr}"></w:mulitboxtree>
			</div>
		</div>
		 
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>

	