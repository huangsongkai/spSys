<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../../../common/include.jsp"%>
<HTML>
	<head>
		<script language="javascript">
		function changeName(companyName){
			$("#companyNameApp").val(companyName.value)
		}
		function changeCode(companyCode){
			$("#creditCodeApp").val(companyCode.value)
		}
		
		function changeStaffMng(){
			$("#heji").val(parseFloat($("#secretStaffMng1").val())+parseFloat($("#secretStaffMng2").val())+parseFloat($("#secretStaffMng3").val()))
		}
		
		</script>
	</head>
	<body >
	<div align="center" >
		<form action="saveUnit.do" name="form1" id="form1" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<input type="hidden" id="flag" value="${flag}">
		<table class="list" width="100%" id="certTable">
			<tr>
				<th height="24" colspan="8">
					武器装备科研生产单位保密资格申请书内容修改
				</th>
			</tr>
			<tr>
				<td colspan="2">
					单位名称
				</td>
				<td  colspan="6"  align="left">
					<input name="baseInfo.pkbase" id="pkbase" type="hidden" value="${baseInfo.pkbase}">
					&nbsp;&nbsp;<input name="baseInfo.companyName"  size="38" type="text"   value="${baseInfo.companyName}" onchange="changeName(this)">
					&nbsp;&nbsp;<input name="baseApplicationInfo.companyName" id="companyNameApp" size="38"  type="hidden" value="${baseApplicationInfo.companyName}">
				</td>
			</tr>
			<tr>
				<td  colspan="2">
					社会统一信用代码
				</td>
				<td  colspan="6"  align="left">
					&nbsp;&nbsp;<input name="baseInfo.creditCode" type="text"  size="38"   value="${baseInfo.creditCode}" onchange="changeCode(this)">
					&nbsp;&nbsp;<input name="baseApplicationInfo.creditCode" id="creditCodeApp" size="38"  type="hidden" value="${baseApplicationInfo.creditCode}">
				</td>
			</tr>
			<tr>
				<td  colspan="2">
					单位性质
				</td>
				<td  colspan="2"  align="left">
					&nbsp;&nbsp;<input name="baseInfo.companyType" size="38" type="text"  value="${baseInfo.companyType}">
				</td>
				<td  colspan="2">
					法定代表人
				</td>
				<td  align="left"  colspan="2"  align="left">
					&nbsp;&nbsp;<input name="baseInfo.legalBody" size="38" type="text"  value="${baseInfo.legalBody}">
				</td>
			</tr>
			<tr>
				<td colspan="2">
					单位人数
				</td>
				<td  colspan="2"  align="left">
					&nbsp;&nbsp;<input name="baseInfo.companyPersonCount" id="companyPersonCount" size="38" type="text"   value="${baseInfo.companyPersonCount}">
				</td>
				<td  colspan="2">
					涉密人员数
				</td>
				<td  colspan="2"  align="left">
					&nbsp;&nbsp;<input name="baseInfo.secretPersonCount" type="text"   size="38"  value="${baseInfo.secretPersonCount}">
				</td>
			</tr>
			<tr>
				<td colspan="2">
					注册地址
				</td>
				<td  align="left"  colspan="6" >
					&nbsp;&nbsp;<input name="baseInfo.regAddress" size="38" type="text" value="${baseInfo.regAddress}">
				</td>
			</tr>
			
			<tr>
				<td colspan="2">
					科研生产（办公）地址
				</td>
				<td  colspan="6"  align="left">
					&nbsp;&nbsp;<input name="baseInfo.officeAddress" size="38" type="text" value="${baseInfo.officeAddress}">
				</td>
				
			</tr>
			<tr>
				<td colspan="2">
					通信地址
				</td>
				<td  colspan="6"  align="left">
					&nbsp;&nbsp;<input name="baseInfo.mailingAddress" size="38" type="text" value="${baseInfo.mailingAddress}">
				</td>
				
			</tr>
			<tr>
				<td colspan="2">
					邮政编码
				</td>
				<td  align="left"  colspan="2">
					&nbsp;&nbsp;<input name="baseInfo.postalCode" size="38" type="text" class="number" value="${baseInfo.postalCode}">
				</td>
				<td  colspan="2">
					联系电话
				</td>
				<td  align="left"  colspan="2">
					&nbsp;&nbsp;<input name="baseInfo.phone" type="text"  size="38"  value="${baseInfo.phone}">
				</td>
			</tr>	
			<tr>
				<td colspan="2">
					单位创建时间
				</td>
				<td  align="left"  colspan="2">
					&nbsp;&nbsp;<input name="baseInfo.companyCreateTime" size="38" type="text" value="${baseInfo.companyCreateTime}">
				</td>
				<td  colspan="2">
					是否为上市公司
				</td>
				<td  align="left"  colspan="2">
					&nbsp;&nbsp;
					<label><input name="baseInfo.isShangshi" type="radio" <c:if test="${baseInfo.isShangshi=='1'}">checked</c:if> value="1"/>是 </label>
					<label><input name="baseInfo.isShangshi" type="radio" <c:if test="${baseInfo.isShangshi=='0'}">checked</c:if> value="0" />否 </label>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					注册资金
				</td>
				<td  align="left"  colspan="2">
					&nbsp;&nbsp;<input name="baseInfo.regMoney" size="38" type="text" value="${baseInfo.regMoney}">
				</td>
				<td  colspan="2">
					固定总资产
				</td>
				<td  align="left"  colspan="2">
					&nbsp;&nbsp;<input name="baseInfo.fixedAssets" type="text"  size="38"  value="${baseInfo.fixedAssets}">
				</td>
			</tr>	
			<tr>
				<td colspan="2">
					股权结构
				</td>
				<td  align="left"  colspan="6">
					&nbsp;&nbsp;<input name="baseInfo.equityStructure" size="138" type="text"  value="${baseInfo.equityStructure}">
				</td>
			</tr>		
			<tr>
				<td colspan="2">
					外国国籍、境外永久居<br>留权或者长期居留许可<br>及涉外婚姻关系情况
				</td>
				<td  align="left"  colspan="6">
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseInfo.foreignRelations">${baseInfo.foreignRelations }</textarea>		
				</td>
			</tr>		
			<tr>
				<td colspan="2">
					证券监管机构的<br>行政处罚情况
				</td>
				<td  align="left"  colspan="6">
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseInfo.punishments">${baseInfo.punishments }</textarea>	
				</td>
			</tr>		
			<tr>
				<td colspan="2">
					单位概况
				</td>
				<td  align="left"  colspan="6">
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseInfo.summaryOfCompany">${baseInfo.summaryOfCompany }</textarea>
				</td>
			</tr>		
			<tr>
				<td colspan="8">
					<input name="button" type="submit" class="btn_standard" value="保存">
				</td>
			</tr>
		</table>
		
		</form>
	</div>
	</body>
</HTML>