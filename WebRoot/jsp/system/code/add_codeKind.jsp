<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="pageContent">
	<form method="post" action="saveCodeKind.do"class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="57">
			<p>
				<label>字典大类编号：</label>
					<input name="codeKind.state" id="codeKind.state" type="hidden" value="${codeKind.state}"/>
					<input name="codeKind.kindId" id="codeKind.kindId" type="text" value="${codeKind.kindId}" readonly="readonly"/>
			</p>
			<p>
				<label>字典大类名称：</label>
					<input name="codeKind.kindName" class="required" type="text" size="30" value="" alt="请输入字典大类名称" />
			</p>
			<p>
				<label>字典大类代码：</label>
					<input name="codeKind.kindCode" id="codeKind.kindCode" type="text" />
			</p>
			<p>
			<label>层级：</label>
					<input name="codeKind.levelTal" id="codeKind.levelTal" type="text" value="2" class="required number" alt="请输入层级数,默认填2"/>
			</p>
			<p>
			<label>字典描述：</label>
					<input name="codeKind.remark" id="codeKind.remark" type="text" />
			</p>
			<div class="divider"></div>
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