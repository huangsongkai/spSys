<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../../../common/include.jsp" %>
<div class="pageContent">
	<form method="post" action="saveOperationRole.do" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="57">
			<p>
				<label>部门名称：</label>
					<input type="text" id="roleName" name="operationRole.roleName" value="${operationRole.roleName}" />
					<input name="operationRole.roleId" id="roleId" value="${operationRole.roleId}" type="hidden"/> 
			</p>			
			<p>
				<label>部门标识：</label>
					<select name="operationRole.roleSign" id="roleSign" class="required combox" >
						<option>请选择</option>
							<c:forEach items="${applicationScope.CodeDict}" var="item">
								<c:if test="${item.parentCodeId=='050'}">
									<option value="${item.codeCode}" <c:if test="${item.codeCode==operationRole.roleSign}">selected</c:if> >${item.codeName}</option>
								</c:if>
						    </c:forEach>
					</select>
			</p>
			<p>
				<label>部门代码：</label>
					<input type="text"  name="operationRole.roleCode" value="${operationRole.roleCode}" readonly="readonly"/>
			</p>
		</div>
		 
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
