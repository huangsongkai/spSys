<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../../../common/include.jsp"%>
<script language="javascript">
	 function loadSuperiorInfo(id){
			 	$.post("ajaxSuperiorInfo.do?unitGroup="+id,function(data){
				 	var arr = eval('(' + data + ')'); 
				 	$("#superiorInfo").empty();
				 	$("<option value='0'></option>").appendTo("#superiorInfo");
					for(var i=0;i<arr.length;i++){
						var obj = arr[i];
						$("<option value='"+obj.unitNo+"'>"+obj.unitName+"</option>").appendTo("#superiorInfo");
					}
				})
			 }
</script>

<div class="pageContent">
	<form action="saveUnit.do" name="form1" id="form1" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="57">
			<p>
				<label>单位编号：</label>
				<input type="text" name="unitInfo.unitNo" readonly="readonly"  value="${unitInfo.unitNo}">
			</p>
			<p>
				<label>单位名称：</label>
				<input name="unitInfo.unitName" type="text" value="${unitInfo.unitName}" class="required" alt="军工单位不能为空">
			</p>
			<br><br><br>
			<p>
			<label >所属集团：</label>
				<select name="unitInfo.unitGroup" id="unitGroup" onchange="loadSuperiorInfo(this.value)">
					<option ></option>
					<c:forEach items="${applicationScope.CodeDict}" var="item">
						<c:if test="${item.parentCodeId=='009'}">
							<option value="${item.codeId}" <c:if test="${item.codeId==unitInfo.unitGroup}">selected</c:if> >${item.codeName}</option>
						</c:if>
					</c:forEach>
				</select>
			</p>
			<p>
				<label>上级单位：</label><label>
				<select name="unitInfo.superiorInfo" id="superiorInfo">
					<option value="0"></option>
					<c:forEach items="${unitInfoList}" var="item">
						<option value="${item.unitNo}" <c:if test="${item.unitNo==unitInfo.superiorInfo}">selected</c:if> >${item.unitName}</option>
					</c:forEach>
				</select></label>
			</p>
			<br><br><br>
			<p>
				<label>单位性质：</label>
				<select name="unitInfo.unitCategory" id="unitCategory" value="${unitInfo.unitCategory}">
					<option ></option>
					<c:forEach items="${applicationScope.CodeDict}" var="item">
					 	<c:if test="${item.parentCodeId=='005'}">  
						 		<option value="${item.codeId}" <c:if test="${item.codeId==unitInfo.unitCategory}">selected</c:if> >${item.codeName}</option>
					 	</c:if>
					</c:forEach>
				</select>
			</p>
			<p>
				<label>法定代表人：</label>
				<input type="text" name="unitInfo.legalRepresentative" value="${unitInfo.legalRepresentative }" >
			</p>
			<br><br><br>
			<p>
				<label>注册地址：</label>
				<input type="text" name="unitInfo.unitLoginAddress" value="${unitInfo.unitLoginAddress }">  
			</p>
			<p>
				<label>通讯地址：</label>
				<input type="text" name="unitInfo.unitCommunicationAddress" value="${unitInfo.unitCommunicationAddress }" >  
			</p>
			<br><br><br>
			<p>
				<label>单位人数：</label>
				<input type="text" name="unitInfo.unitPeople" value="${unitInfo.unitPeople }" >
			</p>
			<p>
				<label>涉密人数：</label>
				<input type="text" name="unitInfo.classifiedPeople" value="${unitInfo.classifiedPeople }" >
			</p>
			<br><br><br>
			<p>
				<label>邮政编码：</label>
				<input type="text" name="unitInfo.zipCode" value="${unitInfo.zipCode }" >
			</p>
			<p>
				<label>联系电话：</label>
				<input name="unitInfo.unitPhone" value="${unitInfo.unitPhone}" >	
			</p>
			<br><br><br>
			<p>
				<label>保密资格等级：</label>
				<select name="unitInfo.licenseLevel" id="confidentialityLevelQualifications" >
					 <c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='004'}">  
						 		<option value="${item.codeId}" <c:if test="${item.codeId==unitInfo.licenseLevel}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
					 </c:forEach>
				</select>
			</p>
			<p>
				<label>摆放位置：</label>
				<input type="text" name="unitInfo.placement" value="${unitInfo.placement }"  >
			</p>
			<br><br><br>
			<c:forEach items="${dataList}" var="obj">
				<p>
					<label>${obj.manageData}</label>
					<input type="text"  name="unitInfo.${obj.unitData }">
				</p>
			</c:forEach>
			<p>
				<label>单位简介：</label>
				<textarea rows="5" cols="35" name="unitInfo.unitSummary">${unitInfo.unitSummary }</textarea>
			</p>
			<p>
				<label>是否上市：</label>
				<select name="unitInfo.listedCompanies"  >
				<option value="1" <c:if test="${unitInfo.listedCompanies=='1'}">selected</c:if> >是</option>  
				<option value="2" <c:if test="${unitInfo.listedCompanies=='2'}">selected</c:if> >否</option>    
				</select>
			</p>
			<br><br><br><br><br><br><br><br><br>
			<p>
				<label>资质通过时间：</label>
				<input class="date"  name="unitInfo.qualificationByTime" value="${unitInfo.qualificationByTime}" >
			</p>
			<p>
				<label>资质过期时间：</label>
				<input class="date" name="unitInfo.qualificationFailTime" value="${unitInfo.qualificationFailTime}" >  
			
			</p>
			
			<p>
		<div class="formBar">
			<input type="hidden" name="unitInfo.pkUnit" id="pkUnit" value="${unitInfo.pkUnit }">
			<input type="hidden" name="unitInfo.censorLevel" id="censorLevel" value="${unitInfo.censorLevel }">
			<ul>
				<li>
					<c:if test="${param.show!='1'}">
						<input name="button" type="submit" value="保存" >
					</c:if>
				</li>
				<li>
					<c:if test="${param.show=='1'}">
						<input name="button" type="button" class="close" value="关闭">
					</c:if>
				</li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">

</script>

