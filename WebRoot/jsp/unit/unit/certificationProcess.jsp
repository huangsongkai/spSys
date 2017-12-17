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
filter:"progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale')";
-moz-background-size:98% 98%;
background-size:98% 98%;
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
.arrow9{
background-image:url('./images/button/arrow9.png');
background-repeat: no-repeat;
}
.arrow10{
background-image:url('./images/button/arrow10.png');
background-repeat: no-repeat;
}
.arrow11{
background-image:url('./images/button/arrow11.png');
background-repeat: no-repeat;
}
#table12{
border:0px solid #b8d0d6;
}
#table12 tr td{
border:0px solid #b8d0d6;
}
</style>
<div class="pageHeader" >
	<ul>
		<li id="li11" style="font-size:25px">审查认证流程</li>
	</ul>
	<form id="pagerForm" method="post" name="form1" action="certificationProcess.do" onsubmit="return navTabSearch(this);">
		<div class="searchBar">
				<input type="hidden" name="certTaskId" id="certTaskId" value="${certTaskId }">
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
			<td width="30" style="font-size:25px;">
			
			<input type="hidden" name="pkUnit" id="pkUnit" value="${obj1.pkUnit }">
		
			</td>
			<td width="900">
				<table id="table12"  width="100%" >
					<tr height="100px">
						<td class="process2" style="font-size:18px;color:green">开始</td>
						<td class="arrow1"></td>
						<td class="process" style="font-size:18px;color:<c:if test="${applicationStatus == '1'}">green</c:if>" onclick="processUpLoadFile('${certTaskId }','application');" >录入审查认<br>证申请书</td>
						<td class="arrow1"></td>
						<td class="process" ><a style="font-size:18px;text-decoration:none;color:green" href="addCensor.do?certTaskId=${certTaskId }" target="navTab">编制审<br>查计划</a></td>
						<td class="arrow1"></td>
						<td class="process" ><a style="font-size:18px;text-decoration:none;color:blue" href="listExportExcel2.do?certTaskId=${certTaskId }"target="dwzExport" targetType="navTab" title="确定要导出这些记录吗?" target="navTab">导出单位<br>基本信息</a></td>
					</tr>
					<tr height="100px">
						<td class="arrow5" style="font-size:18px;"></td>
						<td></td>
						<td class="arrow8" ></td>
						<td class="arrow11"></td>
						<td class="process" style="font-size:18px;color:<c:if test="${passedTime != null}">green</c:if>" onclick="viewCert('${certTaskId }')" target="navTab">编制审<br>查意见书</td>
						<td class="arrow10" ></td>
						<td class="arrow4"></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td width="30" style="font-size:25px;">
			
			</td>
			<td>
				<table id="table12"  width="100%" >
					<tr height="100px">
						<td class="arrow5" style="font-size:18px;"></td>
						<td></td>
						<td class="arrow7"></td>
						<td></td>
						<td class="arrow5" style="font-size:18px;"></td>
						<td class="arrow9" ></td>
						<td class="arrow7"></td>
					</tr>
					<tr height="100px">
						<td class="process2" style="font-size:18px;color:<c:if test="${certStatus == '012003'}">green</c:if><c:if test="${certStatus == '012006'}">red</c:if><c:if test="${certStatus != '012003' && certStatus != '012006'}">#df9d44</c:if>" onclick="certificationAffirm('${certTaskId }');" target="navTab">结束</td>
						<td class="arrow2"></td>
						<td class="process" style="font-size:18px;color:blue" onclick="viewCert('${certTaskId }')" target="navTab">打印审查<br>意见书</td>
						<td class="arrow2"></td>
						<td class="process"><a style="font-size:18px;text-decoration:none;color:<c:if test="${passedTime != null}">green</c:if>" href="#" onclick="impYJS('${passedTime}')" target="navTab">选择录入<br>审查意见</a></td>
						<td class="arrow2"></td>
						<td class="process" onclick="addPaper2('${certTaskId }')" style="font-size:18px;color:blue" target="navTab">生成试卷</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table><tr><td  style="font-size:18px;color:green">绿色字体表示已完成的工作。</td><td  style="font-size:18px;color:blue">蓝色字体表示非必须的工作。</td><td  style="font-size:18px;color:#df9d44">黄色字体表示认证中或待认证。</td></tr></table>
</div>


<script type="text/javascript" language="javascript">    
function addPaper2(certTaskId){
	//$.pdialog.open("processAddPaper.do?certTaskId="+certTaskId, "processAddPaper","生成试卷",{width:800,height:600,mask:false,mixable:true,minable:true,resizable:true,drawable:true} );
	navTab.openTab("processAddPaper?certTaskId="+certTaskId, "processAddPaper.do", { title:"生成试卷", fresh:false, data:{"certTaskId":certTaskId} });
}
function certTaskInfoImport(certTaskId){
	var pkUnit=$("#pkUnit1").val();	
	navTab.openTab("listpaper", "listPaper.do?edit=true", { title:"生成试卷", fresh:false, data:{"certTaskId":certTaskId,"pkUnit":pkUnit} });
//window.location.href="certTaskInfoImport.do";
}       
function processUpLoadFile(certTaskId,fileType){
	$.pdialog.open("upLoadFile.do?certTaskId="+certTaskId+"&fileType="+fileType, "upLoadFile","申请书",{width:800,height:600,mask:false,mixable:true,minable:true,resizable:true,drawable:true} );
}
function certificationAffirm(certTaskId){
	$.pdialog.open("certificationAffirm.do?certTaskId="+certTaskId, "certificationAffirm","认证确认",{width:800,height:600,mask:false,mixable:true,minable:true,resizable:true,drawable:true} );
}
function viewCert(certTaskId){
	var pkUnit=$("#pkUnit1").val();	
			navTab.openTab("yijianshu", "addCertTaskInfo.do?edit=true", { title:"意见书", fresh:false, data:{"certTaskId":certTaskId,"pkUnit":pkUnit,"certStatus":"012002","selStatus":"true"} });
}
function impYJS(passTime){
alert(passTime)
	var pkUnit=$("#pkUnit1").val();	
			navTab.openTab("yijianshu", "certTaskInfoImport.do", { title:"选择录入意见书", fresh:false, data:{"certTaskId":certTaskId,"pkUnit":pkUnit,"certStatus":"012002","selStatus":"true"} });
}
</script>
