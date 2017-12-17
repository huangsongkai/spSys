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
		<form action="saveCertTaskInfo.do" name="form1" id="form1" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
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
					<input name="baseApplicationInfo.pkbaseApp" id="pkbaseApp" type="hidden" value="${baseApplicationInfo.pkbaseApp}">
					<input name="baseApplicationInfo.certScore" id="certScore" type="hidden" value="${baseApplicationInfo.certScore}">
					<input name="baseApplicationInfo.spStatus" id="spStatus" type="hidden" value="${baseApplicationInfo.spStatus}">
					<input name="baseApplicationInfo.appTime" id="appTime" type="hidden" value="${baseApplicationInfo.appTime}">
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
				<td colspan="2">
					申请理由
				</td>
				<td  align="left"  colspan="6">
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.applyReason">${baseApplicationInfo.applyReason }</textarea>
				</td>
			</tr>
			<tr><td colspan="8" align="center">保密责任落实情况</td></tr>		
			<tr>
				<td colspan="2">
					法定代表人或<br>主要负责人
				</td>
				<td  align="left"  colspan="6">
					上年度至本年度解决了保密工作哪些重要问题：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.mianLegalBody">${baseApplicationInfo.mianLegalBody }</textarea>
				</td>
			</tr>	
			<tr>
				<td colspan="2">
					分管保密工作<br>负责人
				</td>
				<td  align="left"  colspan="6">
					上年度至本年度在保密工作方面抓了哪几项主要工作：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.chargeOfSecretLeader">${baseApplicationInfo.chargeOfSecretLeader }</textarea>
				</td>
			</tr>	
			<tr>
				<td colspan="2">
					其他负责人
				</td>
				<td  align="left"  colspan="6">
					上年度至本年度在保密工作方面做了哪些实际工作（按分工分别填写）：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.otherLeader">${baseApplicationInfo.otherLeader }</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					涉密部门或项目负责人
				</td>
				<td  align="left"  colspan="6">
					上年度至本年度在保密管理方面结合实际落实了哪些具体措施：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.secretDepartment">${baseApplicationInfo.secretDepartment }</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					涉密人员
				</td>
				<td  align="left"  colspan="6">
					上年度至本年度在保密管理方面结合实际落实了哪些具体措施：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.secretPerson">${baseApplicationInfo.secretPerson }</textarea>
				</td>
			</tr>
			<tr><td colspan="8" align="center">归口管理情况</td></tr>		
			<tr>
				<td colspan="2">
					归口管理
				</td>
				<td  align="left"  colspan="6">
					上年度至本年度在保密工作方面做了哪些实际工作（按分工分别填写）：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.centralizingMng">${baseApplicationInfo.centralizingMng }</textarea>
				</td>
			</tr>
			<tr><td colspan="8" align="center">保密组织机构情况</td></tr>		
			<tr>
				<td colspan="2">
					保密委员会<br>或保密工作<br>领导小组
				</td>
				<td  align="left"  colspan="6">
					<table>
						<tr>
							<th>姓名</th>
							<th>部门及职务</th>
							<th>职责分工</th>
						</tr>
						<c:forEach items="${listBSP}" var="obj">
							<tr>
								<input name="pkSecretPeople" type="hidden" value="${obj.pkSecretPeople}">
								<td><input name="name" size="38" type="text" value="${obj.name}"></td>
								<td><input name="dept" size="38" type="text" value="${obj.dept}"></td>
								<td><input name="duty" size="38" type="text" value="${obj.duty}"></td>
							</tr>
						</c:forEach>
							<tr>
								<td><input name="name1" size="38" type="text" ></td>
								<td><input name="dept1" size="38" type="text" ></td>
								<td><input name="duty1" size="38" type="text" ></td>
							</tr>
							<tr>
								<td><input name="name1" size="38" type="text" ></td>
								<td><input name="dept1" size="38" type="text" ></td>
								<td><input name="duty1" size="38" type="text" ></td>
							</tr>
							<tr>
								<td><input name="name1" size="38" type="text" ></td>
								<td><input name="dept1" size="38" type="text" ></td>
								<td><input name="duty1" size="38" type="text" ></td>
							</tr>
							<tr>
								<td><input name="name1" size="38" type="text" ></td>
								<td><input name="dept1" size="38" type="text" ></td>
								<td><input name="duty1" size="38" type="text" ></td>
							</tr>
							<tr>
								<td><input name="name1" size="38" type="text" ></td>
								<td><input name="dept1" size="38" type="text" ></td>
								<td><input name="duty1" size="38" type="text" ></td>
							</tr>
							<tr>
								<td><input name="name1" size="38" type="text" ></td>
								<td><input name="dept1" size="38" type="text" ></td>
								<td><input name="duty1" size="38" type="text" ></td>
							</tr>
							<tr>
								<td><input name="name1" size="38" type="text" ></td>
								<td><input name="dept1" size="38" type="text" ></td>
								<td><input name="duty1" size="38" type="text" ></td>
							</tr>
							<tr>
								<td><input name="name1" size="38" type="text" ></td>
								<td><input name="dept1" size="38" type="text" ></td>
								<td><input name="duty1" size="38" type="text" ></td>
							</tr>
					</table>
				</td>
			</tr>
			
			<tr >
				<td colspan="2" rowspan="2">
					工作机构情况
				</td>
				<td  align="left"  colspan="6">
					保密工作机构设置情况：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.workSituation1">${baseApplicationInfo.workSituation1 }</textarea>
				</td>
			</tr>
			<tr >
				<td  align="left"  colspan="6">
					保密工作机构负责人姓名、职务及任命文号：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.workSituation2">${baseApplicationInfo.workSituation2 }</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					专（兼）职<br>工作人员
				</td>
				<td  align="left"  colspan="6">
					<table>
						<tr >
							<th>姓名</th>
							<th>性别</th>
							<th>年龄</th>
							<th>职务</th>
							<th>学历及专业</th>
							<th>保密培训情况</th>
						</tr>
						<c:forEach items="${listBMP}" var="obj">
							<tr>
								<input name="pkMtcsol" type="hidden" value="${obj.pkMtcsol}">
								<td><input name="mtcsolName" size="10" type="text" value="${obj.mtcsolName}"></td>
								<td><input name="mtcsolSex" size="5" type="text" value="${obj.mtcsolSex}"></td>
								<td><input name="mtcsolAge" size="5" type="text" value="${obj.mtcsolAge}"></td>
								<td><input name="mtcsolJob" size="10" type="text" value="${obj.mtcsolJob}"></td>
								<td><input name="mtcsolSchool" size="38" type="text" value="${obj.mtcsolSchool}"></td>
								<td><input name="confidentiality" size="50" type="text" value="${obj.confidentiality}"></td>
							</tr>
						</c:forEach>
							<tr>
								<td><input name="mtcsolName1" size="10" type="text" ></td>
								<td><input name="mtcsolSex1" size="5" type="text" ></td>
								<td><input name="mtcsolAge1" size="5" type="text" ></td>
								<td><input name="mtcsolJob1" size="10" type="text" ></td>
								<td><input name="mtcsolSchool1" size="38" type="text" ></td>
								<td><input name="confidentiality1" size="50" type="text" ></td>
							</tr>
							<tr>
								<td><input name="mtcsolName1" size="10" type="text" ></td>
								<td><input name="mtcsolSex1" size="5" type="text" ></td>
								<td><input name="mtcsolAge1" size="5" type="text" ></td>
								<td><input name="mtcsolJob1" size="10" type="text" ></td>
								<td><input name="mtcsolSchool1" size="38" type="text" ></td>
								<td><input name="confidentiality1" size="50" type="text" ></td>
							</tr>
							<tr>
								<td><input name="mtcsolName1" size="10" type="text" ></td>
								<td><input name="mtcsolSex1" size="5" type="text" ></td>
								<td><input name="mtcsolAge1" size="5" type="text" ></td>
								<td><input name="mtcsolJob1" size="10" type="text" ></td>
								<td><input name="mtcsolSchool1" size="38" type="text" ></td>
								<td><input name="confidentiality1" size="50" type="text" ></td>
							</tr>
							<tr>
								<td><input name="mtcsolName1" size="10" type="text" ></td>
								<td><input name="mtcsolSex1" size="5" type="text" ></td>
								<td><input name="mtcsolAge1" size="5" type="text" ></td>
								<td><input name="mtcsolJob1" size="10" type="text" ></td>
								<td><input name="mtcsolSchool1" size="38" type="text" ></td>
								<td><input name="confidentiality1" size="50" type="text" ></td>
							</tr>
							<tr>
								<td><input name="mtcsolName1" size="10" type="text" ></td>
								<td><input name="mtcsolSex1" size="5" type="text" ></td>
								<td><input name="mtcsolAge1" size="5" type="text" ></td>
								<td><input name="mtcsolJob1" size="10" type="text" ></td>
								<td><input name="mtcsolSchool1" size="38" type="text" ></td>
								<td><input name="confidentiality1" size="50" type="text" ></td>
							</tr>
							<tr>
								<td><input name="mtcsolName1" size="10" type="text" ></td>
								<td><input name="mtcsolSex1" size="5" type="text" ></td>
								<td><input name="mtcsolAge1" size="5" type="text" ></td>
								<td><input name="mtcsolJob1" size="10" type="text" ></td>
								<td><input name="mtcsolSchool1" size="38" type="text" ></td>
								<td><input name="confidentiality1" size="50" type="text" ></td>
							</tr>
							<tr>
								<td><input name="mtcsolName1" size="10" type="text" ></td>
								<td><input name="mtcsolSex1" size="5" type="text" ></td>
								<td><input name="mtcsolAge1" size="5" type="text" ></td>
								<td><input name="mtcsolJob1" size="10" type="text" ></td>
								<td><input name="mtcsolSchool1" size="38" type="text" ></td>
								<td><input name="confidentiality1" size="50" type="text" ></td>
							</tr>
							<tr>
								<td><input name="mtcsolName1" size="10" type="text" ></td>
								<td><input name="mtcsolSex1" size="5" type="text" ></td>
								<td><input name="mtcsolAge1" size="5" type="text" ></td>
								<td><input name="mtcsolJob1" size="10" type="text" ></td>
								<td><input name="mtcsolSchool1" size="38" type="text" ></td>
								<td><input name="confidentiality1" size="50" type="text" ></td>
							</tr>
					</table>
				</td>
			</tr>
			<tr >
				<td colspan="2" >
					保密委员会<br>或保密工作<br>领导小组
				</td>
				<td  align="left"  colspan="6">
					上年度至本年度抓的重点工作：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.secretCommit">${baseApplicationInfo.secretCommit }</textarea>
				</td>
			</tr>
			<tr >
				<td colspan="2" >
					保密工作机构
				</td>
				<td  align="left"  colspan="6">
					上年度至本年度做的主要工作：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.secretSituation">${baseApplicationInfo.secretSituation }</textarea>
				</td>
			</tr>
			<tr><td colspan="8" align="center">保密制度建设情况</td></tr>		
			<tr >
				<td colspan="2" >
					基本制度
				</td>
				<td  align="left"  colspan="6">
					制度名称、文号、生效时间：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.basicSystem">${baseApplicationInfo.basicSystem }</textarea>
				</td>
			</tr>
			<tr >
				<td colspan="2" >
					专项制度
				</td>
				<td  align="left"  colspan="6">
					制度名称、文号、生效时间：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.specialSystem">${baseApplicationInfo.specialSystem }</textarea>
				</td>
			</tr>
			<tr><td colspan="8" align="center">保密监督管理情况</td></tr>
			<tr >
				<td colspan="2" rowspan="4">
					定密管理
				</td>
				<td  align="left"  colspan="6">
					定密工作情况：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.tightMng1">${baseApplicationInfo.tightMng1 }</textarea>
				</td>
			</tr>
			<tr >
				<td  align="left"  colspan="6">
					定密责任人情况：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.tightMng2">${baseApplicationInfo.tightMng2 }</textarea>
				</td>
			</tr>
			<tr >
				<td  align="left"  colspan="6">
					本单位国家秘密事项范围确定情况：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.tightMng3">${baseApplicationInfo.tightMng3 }</textarea>
				</td>
			</tr>
			<tr >
				<td  align="left"  colspan="6">
					确定和调整密级情况：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.tightMng4">${baseApplicationInfo.tightMng4 }</textarea>
				</td>
			</tr>
			
			<tr>
				<td colspan="2" rowspan="2">
					涉密人员管理
				</td>
				<td  align="left"  colspan="6">
					<table>
						<tr >
							<th>人员类别</th>
							<th>人员数量</th>
						</tr>
						<tr>
							<td>核心涉密人员</td>
							<td><input name="baseApplicationInfo.secretStaffMng1" onchange="changeStaffMng()" size="38" id="secretStaffMng1" type="text" value="${baseApplicationInfo.secretStaffMng1}"></td>
						</tr>
						<tr>
							<td>重要涉密人员</td>
							<td><input name="baseApplicationInfo.secretStaffMng2" onchange="changeStaffMng()" size="38" id="secretStaffMng2" type="text" value="${baseApplicationInfo.secretStaffMng2}"></td>
						</tr>
						<tr>
							<td>一般涉密人员</td>
							<td><input name="baseApplicationInfo.secretStaffMng3" onchange="changeStaffMng()" size="38" id="secretStaffMng3" type="text" value="${baseApplicationInfo.secretStaffMng3}"></td>
						</tr>
						<tr>
							<td>合计</td>
							<td><input name="" size="38" type="text" readonly="readonly" id="heji" value="${baseApplicationInfo.secretStaffMng1+baseApplicationInfo.secretStaffMng2+baseApplicationInfo.secretStaffMng3}"></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr >
				<td  align="left"  colspan="6">
					上年度至本年度做的主要工作：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.secretStaffMng4">${baseApplicationInfo.secretStaffMng4 }</textarea>
				</td>
			</tr>
			<tr >
				<td colspan="2" >
					国家秘密载体管理
				</td>
				<td  align="left"  colspan="6">
					上年度至本年度做的主要工作：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.countriesSys">${baseApplicationInfo.countriesSys }</textarea>
				</td>
			</tr>
			<tr >
				<td colspan="2" >
					密品管理
				</td>
				<td  align="left"  colspan="6">
					上年度至本年度做的主要工作：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.denseProductMng">${baseApplicationInfo.denseProductMng }</textarea>
				</td>
			</tr>
			<tr >
				<td colspan="2" rowspan="2">
					保密要害部门<br>部位管理
				</td>
				<td  align="left"  colspan="6">
					保密要害部门部位确定情况：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.theImportSecretMng1">${baseApplicationInfo.theImportSecretMng1 }</textarea>
				</td>
			</tr>
			<tr >
				<td  align="left"  colspan="6">
					保密防护措施落实情况：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.theImportSecretMng2">${baseApplicationInfo.theImportSecretMng2 }</textarea>
				</td>
			</tr>
			<tr >
				<td colspan="2" rowspan="6">
					信息系统、<br>信息设备和<br>存储设备管理
				</td>
				<td  align="left"  colspan="6">
					涉密信息系统建设、防护情况：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.sysAndEquiAndStorageMng1">${baseApplicationInfo.sysAndEquiAndStorageMng1 }</textarea>
				</td>
			</tr>
			<tr >
				<td  align="left"  colspan="6">
					涉密信息设备建设、防护情况：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.sysAndEquiAndStorageMng2">${baseApplicationInfo.sysAndEquiAndStorageMng2 }</textarea>
				</td>
			</tr>
			<tr >
				<td  align="left"  colspan="6">
					涉密存储设备建设、防护情况：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.sysAndEquiAndStorageMng3">${baseApplicationInfo.sysAndEquiAndStorageMng3 }</textarea>
				</td>
			</tr>
			<tr >
				<td  align="left"  colspan="6">
					涉密信息系统、涉密信息设备和涉密存储设备的管理情况：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.sysAndEquiAndStorageMng4">${baseApplicationInfo.sysAndEquiAndStorageMng4 }</textarea>
				</td>
			</tr>
			<tr >
				<td  align="left"  colspan="6">
					非涉密信息系统、非涉密信息设备和非涉密存储设备的管理情况：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.sysAndEquiAndStorageMng5">${baseApplicationInfo.sysAndEquiAndStorageMng5 }</textarea>
				</td>
			</tr>
			<tr >
				<td  align="left"  colspan="6">
					专用系统或者设备建设、防护和管理情况：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.sysAndEquiAndStorageMng6">${baseApplicationInfo.sysAndEquiAndStorageMng6 }</textarea>
				</td>
			</tr>
			<tr >
				<td colspan="2" >
					新闻宣传管理
				</td>
				<td  align="left"  colspan="6">
					采取了哪些保密管理措施：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.newsMng">${baseApplicationInfo.newsMng }</textarea>
				</td>
			</tr>
			<tr >
				<td colspan="2" >
					涉密会议管理
				</td>
				<td  align="left"  colspan="6">
					采取了哪些保密管理措施，是否符合国家有关保密标准：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.meetingMng">${baseApplicationInfo.meetingMng }</textarea>
				</td>
			</tr>
			<tr >
				<td colspan="2" >
					外场试验管理
				</td>
				<td  align="left"  colspan="6">
					采取了哪些保密管理措施：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.testMng">${baseApplicationInfo.testMng }</textarea>
				</td>
			</tr>
			
			<tr >
				<td colspan="2" rowspan="2">
					协作配套管理
				</td>
				<td  align="left"  colspan="6">
					采取了哪些保密管理措施：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.collaborationMng1">${baseApplicationInfo.collaborationMng1 }</textarea>
				</td>
			</tr>
			<tr>
				<td  align="left"  colspan="6">
					采取了哪些保密管理措施：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.collaborationMng2">${baseApplicationInfo.collaborationMng2 }</textarea>
				</td>
			</tr>
			<tr >
				<td colspan="2" rowspan="2">
					涉外管理
				</td>
				<td  align="left"  colspan="6">
					采取了哪些保密管理措施：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.foreignNationals">${baseApplicationInfo.foreignNationals }</textarea>
				</td>
			</tr>
			
			<tr><td colspan="8" align="center">监督与保障</td></tr>
			<tr >
				<td colspan="2" >
					保密检查
				</td>
				<td  align="left"  colspan="6">
					上年度至本年度开展保密检查工作情况：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.secretCheck">${baseApplicationInfo.secretCheck }</textarea>
				</td>
			</tr>
			<tr >
				<td colspan="2" >
					考核与奖惩
				</td>
				<td  align="left"  colspan="6">
					上年度至本年度保密考核与奖惩情况：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.kpAndRewardsAndPunishments">${baseApplicationInfo.kpAndRewardsAndPunishments }</textarea>
				</td>
			</tr>
			<tr >
				<td colspan="2" >
					工作档案管理
				</td>
				<td  align="left"  colspan="6">
					保密工作档案管理情况：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.workFileMng">${baseApplicationInfo.workFileMng }</textarea>
				</td>
			</tr>
			
			<tr >
				<td colspan="2" rowspan="2">
					保密工作经费
				</td>
				<td  align="left"  colspan="6">
					上年度至本年度保密管理经费预算和支出情况：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.workingFundsMng1">${baseApplicationInfo.workingFundsMng1 }</textarea>
				</td>
			</tr>
			<tr >
				<td  align="left"  colspan="6">
					上年度至本年度保密专项经费支出情况：<br>
					&nbsp;&nbsp;<textarea rows="5" cols="120" name="baseApplicationInfo.workingFundsMng2">${baseApplicationInfo.workingFundsMng2 }</textarea>
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