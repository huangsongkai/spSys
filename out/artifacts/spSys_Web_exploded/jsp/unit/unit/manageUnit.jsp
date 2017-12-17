<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../../../common/include.jsp"%>
<%@ include file="../../../common/include2.jsp"%>
<script language="javascript">
	function editManageUnit(pkManageUnit){
	alert(pkManageUnit)
				var returnValue=ModelWindow_dialog("editManageUnit.do?pkManageUnit="+pkManageUnit,400,300);
				if(returnValue=="ok"){
			    	window.location.href=window.location.href ;
				}	
			}
			
	function deleteManageUnit(pkManageUnit){
		var returnValue=ModelWindow_dialog("delManageUnit.do?pkManageUnit="+pkManageUnit,400,300);
		if(returnValue=="ok"){
	    	window.location.href=window.location.href ;
		}
	}
</script>

<div class="pageContent">
	<form action="saveManageUnit.do" name="form1" id="form1" method="post" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="57">
			<p>
				<label>管控军工单位信息</label>
			</p>
			<br><br><br>
			<p>
				<label>单位名称</label>
			</p>
			<br><br><br>
			<p>
				<label >单位性质</label>
			</p>
			<br><br><br>
			<p>
				<label >通讯地址</label>
			</p>
			<br><br><br>
			<p>
				<label >保密资格等级</label>
				<c:forEach items="${dataList}" var="obj">
					<tr>
						<td width="50%" class="">
							${obj.manageData }
						</td>
						<td width="50%">
							<a href="#"  onClick="editManageUnit('${obj.pkManageUnit}')">
								<span>修改&nbsp;</span>
							</a>	
							<a href="#"  onClick="deleteManageUnit('${obj.pkManageUnit}')">
								<span>删除</span>
							</a>
						</td>
					</tr>
				</c:forEach>
			</p>
			<br><br><br>
			<p>
				<input type="text" name="manageUnitData" >
			</p>
			<br><br><br>
			<p>
				<input type="text" name="manageUnitData" >
			</p>
			<br><br><br>
			<p>
				<input type="text" name="manageUnitData" >
			</p>
			<br><br><br>
			<p>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
						</div>
					</div>
				</li>
				
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">

</script>

