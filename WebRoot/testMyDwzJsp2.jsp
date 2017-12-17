<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="common/dwz.jsp"%>
<%@ include file="common/include.jsp" %>
<%@ include file="common/include2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>testMyDwzJsp</title>
  </head>
 <body scroll="no">
	<div id="layout" >
		<div id="header">
			<div class="headerNav">
				<a class="logo" href="javascript:;">军工安全保密资源数据库</a>
				<ul class="nav">
					<li><a href="mainWork.do" target="navTab" rel="main">主页</a></li> 
					<li><a href="changepwd.jsp?userId=${sessionScope.sessionbean.userId}&userName=${sessionScope.sessionbean.userName}" target="dialog" width="600">修改密码</a></li>
					<li><a href="javascript:;" onclick="quit()">注销</a></li>
				</ul>
				<ul class="themeList" id="themeList">
					<li >用户：${sessionScope.sessionbean.userName}</li>
					<li >部门：${sessionScope.sessionbean.deptName}</li>  
				</ul>
			</div>

			<!-- navMenu -->
			
		</div>

		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse"><div></div></div>
				</div>
			</div>
			<div id="sidebar">
			<div class="accordion" fillSpace="sidebar">
				<div class="toggleCollapse"><h2>主要菜单</h2><div>收缩</div></div>
					<div class="accordionHeader">
						<h2><span>Folder</span>功能列表</h2>
					</div>
					
					<div class="accordionContent">
						<ul class="tree treeFolder">
						<c:forEach var="obj" items="${menu}">
							<c:if test="${fn:length(obj.funcParentId)==1}">
								<li><a>${obj.funcName}</a>
								<ul>
								<c:forEach var="child" items="${menu}">
								<c:if test="${child.funcParentId==obj.funcId&&child.url!=null}">
									<li><a href="${child.url}" target="navTab" rel="${child.funcName}">${child.funcName}</a></li>
								</c:if>
								</c:forEach>
								</ul>
							</li>
							</c:if>
						</c:forEach>
						</ul>
					</div>
				</div>
			</div>
			
			
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">我的主页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">我的主页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
						<div class="searchBar" layoutH="80" >
							<table class="searchContent" width="100%" >
								<tbody>
           							 <tr>
                  						<td align="center">
                  							<table class="list" width="100%" align="center" height="100%">   
                  									<tr align="center"><td><font color=red ">审查认证工作</font></br></td> </tr>  
                  									<tr align="center"><td>  <a href="listCensor.do" target="navTab" >您本月已完成<font color=red ">${scountM}</font>家的审查认证</a> </br></td></tr>
                  									<tr align="center"><td>  <a href="listCensor.do" target="navTab" >您本年已完成<font color=red ">${scountY}</font>家的审查认证</a>  </br></td></tr>  
                  									<tr align="center"><td>  <a href="listCensor.do" target="navTab" >今年内剩余<font color=red ">${scountYY}</font>家需要认证</a> </br> </td></tr>
                  									<tr align="center"><td>  <a href="addCensor.do?1=1" target="navTab" >新建工作<font color=red "></a> </br> </td></tr>
                  							</table>
                  						</td>
                  						
                  						<td align="center">
	                  						<table class="list" width="100%" align="center" height="100%">
                  									<tr align="center"><td><font color=red >复查工作</font></br></td> </tr>
                  									<tr align="center"><td>  <a href="listReview.do" target="navTab" >您本月已完成<font color=red ">${fcountM}</font>家的复查</a> </br></td></tr>
                  									<tr align="center"><td>  <a href="listReview.do" target="navTab" >您本年已完成<font color=red ">${fcountY}</font>家的复查</a>  </br></td></tr>  
                  									<tr align="center"><td>  <a href="listReview.do" target="navTab" >今年内剩余<font color=red ">${fcountYY}</font>家需要复查</a> </br> </td></tr>
                  									<tr align="center"><td>  <a href="addCert.do?1=1" target="navTab" >新建工作<font color=red "></a> </br> </td></tr>
                  							</table>
                  						</td>             
                  						
           							 </tr>
           							 
           							 <tr>
           								 <td align="center" >
                  							<table class="list" width="100%" align="center" height="100%">
                  									<tr align="center"><td><font color=red >试题工作</font></br></td> </tr>
                  									<tr align="center"><td> <a href="listPaper.do" target="navTab" >您本月新出试题<font color=red ">${stCountM}</font>套</a> </br></td></tr>
                  									<tr align="center"><td><a href="listPaper.do" target="navTab" >您本年新出试题<font color=red ">${stCountY}</font>套</a> </br></td></tr>
                  									<tr align="center"><td>  <a href="addPaper.do?1=1" target="navTab" >新建工作<font color=red "></a> </br> </td></tr>  
                  							</table>
                  						</td>
           							 	<td align="center">
                  							<table class="list" width="100%" align="center" height="100%">
                  									<tr align="center"><td><font color=red >司局检查工作</font></br></td> </tr>
                  									<tr align="center"><td> <a href="listDepartmentSituation.do" target="navTab" >您本月已完成<font color=red ">${sjcountM}</font>家的检查</a> </br></td></tr>
                  									<tr align="center"><td> <a href="listDepartmentSituation.do" target="navTab" >您本年已完成<font color=red ">${sjcountY}</font>家的检查</a> </br></td></tr>
                  									<tr align="center"><td> <a href="listDepartmentSituation.do" target="navTab" >您本年内剩余<font color=red ">${sjcountYY}</font>家需要检查</a></br> </td></tr>
                  									<tr align="center"><td>  <a href="addDepartmentSituation.do?1=1" target="navTab" >新建工作<font color=red "></a> </br> </td></tr> 
                  							</table>
                  						</td>
                  						
           							 </tr>
           							 
           							 
           							  <tr>
           							 	<td align="center">
                  							<table class="list" width="100%" align="center" height="100%">  
                  									<tr align="center"><td><font color=red >达标检查工作</font></br></td> </tr>
                  									<tr align="center"><td>  <a href="listStandInfo.do" target="navTab" >您本月已完成<font color=red ">${dbcountM}</font>家的达标检查</a> </br></td></tr>
                  									<tr align="center"><td> <a href="listStandInfo.do" target="navTab" >您本年已完成<font color=red ">${dbcountY}</font>家的达标检查</a> </br></td></tr>
                  									<tr align="center"><td>  <a href="listStandInfo.do" target="navTab" >您本年剩余<font color=red ">${dbcountYY}</font>需要达标检查</a> </br> </td></tr>
                  									<tr align="center"><td>  <a href="addStandInfo.do?1=1" target="navTab" >新建工作<font color=red "></a> </br> </td></tr> 
                  							</table>   
                  						</td>
                  						<td align="center">  
                  							<table class="list" width="100%" align="center" height="100%">
                  									<tr align="center"><td><font color=red >军工单位信息工作</font></br></td> </tr>
                  									<tr align="center"><td><a href="listUnit.do" target="navTab" >您本月已完成<font color=red ">${jccountM}</font>家的军工信息录入</a></br></td></tr>
                  									<tr align="center"><td><a href="listUnit.do" target="navTab" >您本年已完成<font color=red ">${jccountY}</font>家的军工信息录入</a></br></td></tr>
                  									<tr align="center"><td><a href="listUnit.do" target="navTab" >系统中共有<font color=red ">${jccountall}</font>家军工单位信息</a></br> </td></tr> 
                  									<tr align="center"><td>  <a href="addUnit.do?1=1" target="navTab" >新建工作<font color=red "></a> </br> </td></tr>  
                  							</table>
                  						</td>
           							 </tr>
           							 
           							
           							 </tr>
     							</tbody>
							</table>

							<div class="divider"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		
	</div>




</body>
</html>
<script type="text/javascript">

	setTimeout("ajaxCd()", 1000 );//定时读取cd信息
	$(function(){
		var jsonStr = $('#list_noJSON').val();
		//alert(jsonStr)
		$('#unitName1').jsonSuggest(eval(jsonStr),{onSelect:checkedListCallback, maxResults:10});
	})         
	function checkedListCallback(item){
		$('#unitName1').val(item.text.split("，")[1]);
	}
	function showDiv(){
		var unitName=$("#unitName1").val();  
		$.pdialog.open("listOneUnitInfo.do?unitName="+unitName,"listOneUnitInfo","详细",{width:800,height:600});	   
	}                  
	function ajaxCd(){
	  $.ajax({
				async: false,
				type : "POST",
				url : 'readCdState.do',
				success : function(data) {
					var str=data.trim();   
					//alert(data)
					if(str=='Ok'){  
						$.pdialog.open("TiaoZhuan.do", "saleBill", "导入意见书",{width:900,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true} );
						//alert("有光盘跳转")    
					  	return false;
					}else{
						setTimeout("ajaxCd()", 5000 );         
					}
					if(str=='Error'){      
						//alert("读盘错误，请重新插入光盘")  
						setTimeout("ajaxCd()", 5000 );     
					}
				
				
				}
			});
	} 

function delCookie(name)
	{
 		var exp = new Date();
 		exp.setTime(exp.getTime() - 1);
 		var cval=getCookie(name);
 		if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();
	} 
	function getCookie(name)
	{
	 var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
	 if(arr != null) 
	 return unescape(arr[2]); 
	 return null;
	} 
	function quit()
	{
		if(confirm("确认退出？"))
		{
			delCookie('aktm');
			window.location.href="quit!LogOut.do";
		}
	}
//初始化的对象

$(document).ready(function () {
	var str1="";
	$.post("listAlreadyList.do",function(data){
		var datas=data.split("|");
		for(i=0;i<datas.length-1;i++){
			var datass = datas[i].split(";");
			str1=str1+"<tr><td><a href='#' onclick='openListCensor("+datass[2]+")'>"+datass[0]+"</a></td><td>"+datass[1]+"</td>";
		}
		PopBoxPage(str1)
	})
})


function PopBoxPage(str) {
    var lmy_TopRemind = 0; //top坐标
    var lmy_LeftRemind = 0; //left坐标
    var lmy_Top; //屏幕高度
    var lmy_Left; //屏幕宽度
    var lmy_Setting = $.extend(
                    {
                        width: "400", height: "125",
                        border: "2px solid rgb(167, 205, 240)",
                        backgroundColor: "White", title: "<font color='red'>一个月内需要办理的审查认证工作</font>",
                        refreshTime: 7200000//刷新时间
                    }, '');
    var settime = new Array();

    lmy_Top = $(window).height()-$(window).height()/15;//$("html").height(); 此处根据情况可修改

    lmy_Left = $("html").width(); //此处根据情况可修改
    //绑定关闭事件
    $("#d_Close").live("click", function () {
        $("#myxxDIV").remove();
    });
    var Pop_Init = function () {
        //弹出层中的html代码
        var lmyHtml = str;
        //弹出层的坐标
        lmy_TopRemind = lmy_Top;
        lmy_LeftRemind = lmy_Left - lmy_Setting.width - 20;  //left坐标值
        var html = "<div id=\"myxxDIV\" style=\"z-index: 10000;position: absolute; display:none;overflow:scroll; \">";
        html += "<table style='width:100%;padding-left:5px; padding-right:5px; padding-top:5px;'><tr>";
        
        if(str == null || str ==""){
        	html += "<td><font color='red'>无待办工作</font></td>";//标题
        	html += "<td><div id='d_Close' style='cursor:pointer; text-align:right;'>×关闭</div></td>";//关闭按钮
        	html += "</tr></table><table border='1' style='width:100%;padding-left:5px; padding-right:5px; padding-top:5px;'>";
        }else{
        	html += "<td>" + lmy_Setting.title + "</td>";//标题
        	html += "<td><div id='d_Close' style='cursor:pointer; text-align:right;'>×关闭</div></td>";//关闭按钮
        	html += "</tr></table><table border='1' style='width:100%;padding-left:5px; padding-right:5px; padding-top:5px;'><tr><td>单位名称</td><td>计划审查时间</td></tr>";
        }
        html += lmyHtml;
        html += "</table></div>";
        $("body").css("overflow", "hidden");
        $("body").append(html);
        $("#myxxDIV").css("width", lmy_Setting.width);
        $("#myxxDIV").offset({ top: $("html").height(), left: lmy_LeftRemind });
        $("#myxxDIV").css("height", lmy_Setting.height);
        $("#myxxDIV").css("border", lmy_Setting.border);
        $("#myxxDIV").css("backgroundColor", lmy_Setting.backgroundColor);
        $("#myxxDIV").css("display", "block");
        setTimeout(Pop_SetTop, 1);
    }
    //设置高度
    var Pop_SetTop = function () {
        var top1 = lmy_Top - lmy_Setting.height - 10;
        $("#myxxDIV").offset({ top: lmy_TopRemind, left: lmy_LeftRemind });
        if (lmy_TopRemind > top1) {
            lmy_TopRemind = lmy_TopRemind - 1;
            setTimeout(Pop_SetTop, 1);
        } else {
            Pop_SetDingTime(); //隔断时间扫描数据
        }
    }
    //定时刷新内容                       
    var Pop_SetDingTime = function () {
        //停用所有定时器
        for (var i = 0; i < settime.length; i++) {
            clearTimeout(settime[i]);
        }
        settime.splice(0, settime.length); //清空数组
        var time = setTimeout(Pop_LoadDataByTime, lmy_Setting.refreshTime); //10分钟600000
        settime.push(time);
    }
    //重新加载内容
    var Pop_LoadDataByTime = function () {
    	var str1="";
    	$("#myxxDIV").remove();
	    $.post("listAlreadyList.do",function(data){
			var datas=data.split("|");
			for(i=0;i<datas.length-1;i++){
				var datass = datas[i].split(";");
				str1=str1+"<tr><td><a href='#' onclick='openListCensor("+datass[2]+")'>"+datass[0]+"</a></td><td>"+datass[1]+"</td>";
			}
			str=str1;
			Pop_Init()
		})
        
    }
    Pop_Init();
}
function openListCensor(certTaskId){
	navTab.openTab("openListCensor", "listCensor.do?1=1", { title:"审查任务", fresh:false, data:{"certTaskId":certTaskId} });
}
	</script>