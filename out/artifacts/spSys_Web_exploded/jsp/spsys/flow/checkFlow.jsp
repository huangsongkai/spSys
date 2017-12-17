<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.xunj_prj/tag/webwidget" prefix="w"%>
<%@ include file="../../../common/include.jsp"%>
<%@ include file="../../../common/include2.jsp"%>  
<style>
.table1 {
border-collapse:collapse;
}
.table1 tr td {
border:1px solid #b8d0d6;
}
#li11{ 
display:block;
text-align:center;
}
.process{
width:150px;
text-align:center;
background-image:url('./images/button/Process.png');
background-repeat: no-repeat;
background-position: center;
}
.process2{
width:150px;
text-align:center;
background-image:url('./images/button/Process2.png');
background-repeat: no-repeat;
background-position: center;
}
.arrow1{
background-image:url('./images/button/arrow1.png');
background-repeat: no-repeat;
}
.arrow2{
background-image:url('./images/button/arrow2.png');
background-repeat: no-repeat;
}
.arrow3{
width:150px;
background-image:url('./images/button/arrow3.png');
background-repeat: no-repeat;
}
.arrow4{
background-image:url('./images/button/arrow4.png');
background-repeat: no-repeat;
}
.arrow5{
width:150px;
background-image:url('./images/button/arrow5.png');
background-repeat: no-repeat;
}
.arrow6{
background-image:url('./images/button/arrow6.png');
background-repeat: no-repeat;
}
.arrow7{
width:150px;
background-image:url('./images/button/arrow7.png');
background-repeat: no-repeat;
}
.arrow8{
width:150px;
background-image:url('./images/button/arrow8.png');
background-repeat: no-repeat;
}
.arrow11{
width:150px;
background-image:url('./images/button/arrow11.png');
background-repeat: no-repeat;
}
#table12{
border:0px solid #b8d0d6;
}
#table12 tr td{
border:0px solid #b8d0d6;
}
.divcss5{ background:#F00; color:#FFF}
</style>

<div class="pageHeader" >
	<ul>
		<li id="li11" style="font-size:18px;">申请审批受理流程</li>
	</ul>
	<form id="pagerForm" method="post" name="form1" action="checkFlow.do?pkbaseApp=${pkbaseApp }" onsubmit="return navTabSearch(this);">
		<div class="searchBar">
				<input type="hidden" name="pkbaseApp" id="pkbaseApp" value="${pkbaseApp }">
			<div class="subBar">
				<ul>

					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">点击刷新</button></div></div></li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent" >
	<table class="table1" width="100%">
		<tr>
			<td width="30" style="font-size:18px;">
			</td>
			<td width="900">
				<table id="table12"  width="100%" >
					<tr height="100px">
						<td class="process2" style="font-size:18px;">开始</td>
						<td class="arrow11"></td>
						<td class="arrow11"></td>
						<td class="arrow1"></td>
						<td class="process" style="font-size:18px;" class="divcss5"<c:if test="${planState == '1' }">class="divcss5"</c:if> onclick="dataImpFlow();" >q导入数据<br>文件</td>
						<td class="arrow11"></td>
						<td class="arrow1"></td>
						<td class="process"><a style="font-size:18px;	" target="dwzExport"  href="exportSMJY.do?pkbaseApp=${pkbaseApp}" >导出书面<br>建议</a></td>
					</tr>
					<tr height="100px">
						<td style="font-size:18px;"></td>
						<td ></td>
						<td><!-- <a style="font-size:18px;" href="listAssess.do?1=1" target="navTab">导出材料补正<br>通知书</a> --></td>
						<td ></td>
						<td class="process"><a style="font-size:18px;" href="exportCLBZTZS.do?pkbaseApp=${pkbaseApp}"  target="dwzExport"  >导出材料补<br>正通知书</a> </td>
						<td class="arrow2"></td>
						<td class="arrow11"></td>
						<td class="arrow4"></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td width="30" style="font-size:18px;">
			</td>
			<td>
				<table id="table12"  width="100%" >
					<tr height="100px">
						<td class="arrow8" ></td>
						<td class="arrow11"></td>
						<td class="arrow11"></td>
						<td class="arrow11"></td>
						<td class="arrow11"></td>
						<td class="process"><a style="font-size:18px;" href="exportBSLYJ.do?pkbaseApp=${pkbaseApp}"  target="dwzExport"  >导出不受理<br>意见及通知书</a> </td>
						<td  class="arrow2"></td>
						<td class="arrow7"></td>
					</tr>
					<tr height="100px">
						<td class="process2" style="font-size:18px;color:<c:if test="${spStatus == '0'}">green</c:if>" onclick="updateCertificationAffirm('${pkbaseApp}',this)" >结束</td>
						<td class="arrow2"></td>
						<td class="process" style="font-size:18px;" onclick="dataImpFlow1();" >导入认证<br>数据</td>
						<td class="arrow2"></td>
						<td class="arrow11"></td>
						<td class="process"><a style="font-size:18px;" href="exportSMSCJLD.do?pkbaseApp=${pkbaseApp}"  target="dwzExport"  >导出书面审<br>查记录单</a></td>
						<td class="arrow2"></td>
						<td class="process"><a style="font-size:18px;" href="exportSLD.do?pkbaseApp=${pkbaseApp}"  target="dwzExport"  >导出<br>受理单</a></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>

<script language="javascript">           
 	function dataImpFlow(){
   		$.pdialog.open("sqlImport.do?1=1", "sqlImportFlow","上传文件",{width:800,height:600,mask:false,mixable:true,minable:true,resizable:true,drawable:true} );
   	}
   	
   	function updateCertificationAffirm(pkbaseApp,e){
		if(confirm("是否完成申请书审批流程？")){
			$.post("updateCertificationAffirm.do?pkbaseApp="+pkbaseApp,function(data){
				//window.location.href="addAssessTaskInfo.do?assessTaskId="+data;
				alert("流程结束")
				e.style.color="green"
				
			})
		}
	}
</script>
