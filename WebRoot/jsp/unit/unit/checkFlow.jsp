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
#table12{
border:0px solid #b8d0d6;
}
#table12 tr td{
border:0px solid #b8d0d6;
}
</style>

<div class="pageHeader" >
	<ul>
		<li id="li11" style="font-size:18px;">保密检查</li>
	</ul>
	<form id="pagerForm" method="post" name="form1" action="checkFlow.do" onsubmit="return navTabSearch(this);">
		<div class="searchBar">
				<input type="hidden" name="taskId" id="taskId" value="${taskId }">
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
						<td class="process2" style="font-size:18px;color:green">开始</td>
						<td class="arrow1"></td>
						<td class="process"><a style="font-size:18px;color:green" href="#"  onclick="dataImpFlow();"  >导入数据<br>文件</a></td>
						<td class="arrow1"></td>
						<td class="process"><a style="font-size:18px;color:green" href="addUnit.do?pkUnit=${pkUnit}" target="navTab">导出数据<br>文件</a></td>  
						<td class="arrow1"></td>
						<td class="process"><a style="font-size:18px;color:blue" target="dwzExport" targetType="navTab" href="checkExportExcel.do?taskId=${taskId}" target="navTab">导出书面<br>建议</a></td>  
					</tr>
					<tr height="100px">
						<td style="font-size:18px;"></td>
						<td ></td>
						<td><!-- <a style="font-size:18px;" href="listAssess.do?1=1" target="navTab">将检查结<br>果导回系统</a> --></td>
						<td ></td>
						<td><!-- <a style="font-size:18px;" href="listAssess.do?1=1" target="navTab">录入检<br>查结论</a> --></td>
						<td></td>
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
						<td class="" style="font-size:18px;"></td>
						<td></td>
						<td class="" style="font-size:18px;"></td>
						<td class=""></td>
						<td style="font-size:18px;"></td>
						<td ></td>
						<td class="arrow7"></td>
					</tr>
					<tr height="100px">
						<td class="process2" style="font-size:18px;">结束</td>
						<td class="arrow2"></td>
						<td class="process"><a style="font-size:18px;color:blue" href="listTaskInfo.do?1=1" target="navTab">导入认证<br>数据</a></td>
						<td class="arrow2"></td>
						<td class="process"><a style="font-size:18px;color:<c:if test="${checkResult == '016001'}">green</c:if>" href="addTaskInfo.do?taskId=${taskId}" target="navTab">导出书面审<br>查记录单</a></td>
						<td class="arrow2"></td>
						<td class="process"><a style="font-size:18px;color:green" href="addUnit.do?pkUnit=${pkUnit}" target="navTab">导出<br>受理单</a></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>

<script language="javascript">           
 function dataImpFlow(){
   			$.pdialog.open("sqlImport.do?1=1","sqlImport","上传文件",{width:800,height:600});	
   			
   		}
</script>
