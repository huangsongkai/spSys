<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>军工安全保密资源数据库</title>

<link href="${pageContext.request.contextPath}/common/dwz-ria/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${pageContext.request.contextPath}/common/dwz-ria/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${pageContext.request.contextPath}/common/dwz-ria/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
<link href="${pageContext.request.contextPath}/common/dwz-ria/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>
<!--[if IE]>
<link href="${pageContext.request.contextPath}/common/dwz-ria/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->

<!--[if lte IE 9]>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/speedup.js" type="text/javascript"></script>
<![endif]-->

<script src="${pageContext.request.contextPath}/common/dwz-ria/js/jquery-1.7.2.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/jquery/jquery-ui-1.8.13.custom/js/jquery-1.5.1.min.js" ></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/jquery.cookie.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/jquery.validate.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/jquery.bgiframe.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/xheditor/xheditor-1.2.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/xheditor/xheditor_lang/zh-cn.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/uploadify/scripts/jquery.uploadify.js" type="text/javascript"></script>

<!-- svg图表  supports Firefox 3.0+, Safari 3.0+, Chrome 5.0+, Opera 9.5+ and Internet Explorer 6.0+ -->
<script type="text/javascript" src="${pageContext.request.contextPath}/common/dwz-ria/chart/raphael.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/dwz-ria/chart/g.raphael.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/dwz-ria/chart/g.bar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/dwz-ria/chart/g.line.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/dwz-ria/chart/g.pie.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/dwz-ria/chart/g.dot.js"></script>

<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.core.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.util.date.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.validate.method.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.regional.zh.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.barDrag.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.drag.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.tree.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.accordion.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.ui.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.theme.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.switchEnv.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.alertMsg.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.contextmenu.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.navTab.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.tab.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.resize.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.dialog.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.dialogDrag.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.sortDrag.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.cssTable.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.stable.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.taskBar.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.ajax.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.pagination.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.database.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.datepicker.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.effects.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.panel.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.checkbox.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.history.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.combox.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.print.js" type="text/javascript"></script>
<!--
<script src="${pageContext.request.contextPath}/common/dwz-ria/bin/dwz.min.js" type="text/javascript"></script>
-->
<script src="${pageContext.request.contextPath}/common/dwz-ria/js/dwz.regional.zh.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/common/js/pinyinma.js" type="text/javascript"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/common/xjTree/xjTree.css" type="text/css" />

<script src="${pageContext.request.contextPath}/common/xjTree/xjTree.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/suggest/jquery.jsonSuggest.js" type="text/javascript"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/suggest/jsonSuggest.css" type="text/css" />
	
<script type="text/javascript">
$(function(){
	DWZ.init("dwz.frag.xml", {
		loginUrl:"login_dialog.html", loginTitle:"登录",	// 弹出登录对话框
//		loginUrl:"login.html",	// 跳到登录页面
		statusCode:{ok:200, error:300, timeout:301}, //【可选】
		pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
		debug:false,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"themes"}); // themeBase 相对于index页面的主题base路径
		}
	});
});
function ReYoPrint(URL){
	if(URL!=null){
    ReYoWebPrint.ContentURL="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/"+URL+"&forprint=1";    //打印地址
    }
    ReYoWebPrint.Orientation = 1;
	//ReYoWebPrint.defaultPrinterName="Canon MP250 series Printer";
	ReYoWebPrint.pageHeight =120;   //纸张高度
   	ReYoWebPrint.pageWidth = 300;    //纸张宽度
	ReYoWebPrint.ReYoPrint(true);
}
</script>
<object classid ="clsid:DC2A3C42-24DC-4FD6-836A-A2E8594ABC15" codebase ="/aktm/common/print/ReYoPrint.CAB#version=2010,428,0,2118" id="ReYoWebPrint" width="0" height="0"></object>

	