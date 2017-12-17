<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="common/include.jsp" %>
<%@ include file="common/head.jsp"%>
<html>
<head>
	<script language="JavaScript">  
			var tab = null;
            var accordion = null;
            var tree = null;
            $(function ()
            {
                //布局
                $("#layout1").ligerLayout({ leftWidth: 190, height: '100%',heightDiff:-34,space:4, onHeightChanged: f_heightChanged });

                var height = $(".l-layout-center").height();

                //Tab
                $("#framecenter").ligerTab({ height: height });

                //面板
                $("#accordion1").ligerAccordion({ height: height - 24, speed: null });

                $(".l-link").hover(function ()
                {
                    $(this).addClass("l-link-over");
                }, function ()
                {
                    $(this).removeClass("l-link-over");
                });
                //树
                $("#tree1").ligerTree({
                    data :${funcInfoData},
                    checkbox: false,
                    slide: false,
                    nodeWidth: 120,
                    attribute: ['nodename', 'url'],
                    onSelect: function (node)
                    {
                        if (!node.data.url) return;
                        var tabid = $(node.target).attr("tabid");
                        if (!tabid)
                        {
                            tabid = new Date().getTime();
                            $(node.target).attr("tabid", tabid)
                        } 
                        f_addTab(tabid, node.data.text, node.data.url);
                    }
                });

                tab = $("#framecenter").ligerGetTabManager();
                accordion = $("#accordion1").ligerGetAccordionManager();
                tree = $("#tree1").ligerGetTreeManager();
                $("#pageloading").hide();

				var isIE6Up = true;
				if(navigator.appName == "Microsoft Internet Explorer") 
				{
					if(navigator.appVersion.match(/MSIE 6./i)=='MSIE 6.') 
					{
						isIE6Up=false; 
					}
				} 
				setInterval("showTime('"+isIE6Up+"')",1000);
            });
            function f_heightChanged(options)
            {
                if (tab)
                    tab.addHeight(options.diff);
                if (accordion && options.middleHeight - 24 > 0)
                    accordion.setHeight(options.middleHeight - 24);
            }
            function f_addTab(tabid,text, url)
            { 
                tab.addTabItem({ tabid : tabid,text: text, url: url });
            } 
             
            function showTime(test)
	{
		var d=new Date();
		var week=' 星期'+'日一二三四五六'.charAt(new Date().getDay());
		if(test=="false")
			week="";
		document.getElementById('dateTime').innerHTML=d.toLocaleString()+week;
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
			window.top.location.href="quit!LogOut.do";
		}
	}
	
	function showMain()
	{
		window.parent.mainFrame.location.href = "mainright.jsp";
	}
	
	function updPassword()
	{
		parent.mainFrame.location.href = "jsp/system/user/password.jsp";
		return false;
	}
	</script>
	<style type="text/css"> 
	    body,html{height:100%;}
	    body{ padding:0px; margin:0;   overflow:hidden;}  
	    .l-link{ display:block; height:26px; line-height:26px; padding-left:10px; text-decoration:underline; color:#333;}
	    .l-link2{text-decoration:underline; color:white; margin-left:2px;margin-right:2px;}
	    .l-layout-top{background:#102A49; color:White;}
	    .l-layout-bottom{ background:#E5EDEF; text-align:center;}
	    #pageloading{position:absolute; left:0px; top:0px; background:white url('loading.gif') no-repeat center; width:100%; height:100%;z-index:99999;}
	    .l-link{ display:block; line-height:22px; height:22px; padding-left:16px;border:1px solid white; margin:4px;}
	    .l-link-over{ background:#FFEEAC; border:1px solid #DB9F00;} 
	    .l-winbar{ background:#2B5A76; height:30px; position:absolute; left:0px; bottom:0px; width:100%; z-index:99999;}
	    .space{ color:#E7E7E7;}
	    /* 顶部 */ 
	    .l-topmenu{ margin:0; padding:0; height:31px; line-height:31px; background:url('common/ligerui/Source/lib/images/top.jpg') repeat-x bottom;  position:relative; border-top:1px solid #1D438B;  }
	    .l-topmenu-logo{ color:#E7E7E7; padding-left:35px; line-height:26px;background:url('common/ligerui/Source/lib/images/topicon.gif') no-repeat 10px 5px;}
	    .l-topmenu-welcome{  position:absolute; height:24px; line-height:24px;  right:30px; top:2px;color:#070A0C;}
	    .l-topmenu-welcome a{ color:#E7E7E7; text-decoration:underline} 
 	</style>
</head>
<body style="padding:0px;background:#EAEEF5;">
	<div id="pageloading"></div>  
	<div id="topmenu" class="l-topmenu">
	    <div class="l-topmenu-logo">军工安全保密资源数据库</div>
	    <div class="l-topmenu-welcome">
	        <a href="index.aspx" class="l-link2">服务器版本</a>
	        <span class="space">|</span>
	        <a href="https://me.alipay.com/daomi" class="l-link2" target="_blank">捐赠</a> 
	        <span class="space">|</span>
	         <a href="http://bbs.ligerui.com" class="l-link2" target="_blank">论坛</a>
	    </div> 
	</div>
  <div id="layout1" style="width:99.2%; margin:0 auto; margin-top:4px; "> 
        <div position="left"  title="主要菜单" id="accordion1"> 
                     <div title="功能列表" class="l-scroll" >
                         <ul id="tree1" style="margin-top:3px;">
                    </div>
                    <div title="应用场景">
                    <div style=" height:7px;"></div>
                         <a class="l-link" href="javascript:f_addTab('listpage','列表页面','demos/case/listpage.htm')">列表页面</a> 
                         <a class="l-link" href="../../../common/ligerui/Source/demos/dialog/win7.htm" target="_blank">模拟Window桌面</a> 
                    </div>    
                     <div title="实验室">
                    <div style=" height:7px;"></div>
                          <a class="l-link" href="../../../common/ligerui/Source/lab/generate/index.htm" target="_blank">表格表单设计器</a> 
                    </div> 
        </div>
        <div position="center" id="framecenter"> 
            <div tabid="home" title="我的主页" style="height:300px" >
                <iframe frameborder="0" name="home" id="home" src="portal.jsp"></iframe>
            </div> 
        </div> 
        
    </div>
    <div  style="height:24px; line-height:24px; text-align:center;">
     <div class="statusBar">
		<ul>
		<li id="currentUser">当前用户：${sessionScope.sessionbean.userId}</li>
		<li id="splitLine">&nbsp;</li>
		<li id="currentDept">所在部门：
		
		</li>
		<li id="splitLine">&nbsp;</li>
		<li id="usualFeatures">
			<!-- 消息 -->
			<a href="jsp/main/desktop/lst_myMessage.htm" target="mainFrame"><img src="images/bottom/message1.png" width="16" height="16" title="我的消息" /><span style="position:relative;top:-3px; ">(3)</span></a>&nbsp;
			<a href="#" onclick='showMain()'><img src="images/bottom/home.png" width="16" height="16" title="首页" /></a>&nbsp;&nbsp;
			<a href="jsp/main/desktop/index_desktop.htm" target="mainFrame"><img src="images/bottom/desktop.png" width="16" height="16" title="我的工作台" /></a>&nbsp;&nbsp;
			<a href="#" onclick='updPassword()'><img src="images/bottom/password.png" width="16" height="16" title="修改密码" /></a>&nbsp;&nbsp;
			<a href="#" onclick='quit()'><img src="images/bottom/quit.png" width="16" height="16" title="退出" /></a>&nbsp;&nbsp;
		</li>
	</ul>
	<div>
	<ul id="rightBar">
		<li id="splitLine">&nbsp;</li>
		<li id="dateTime">&nbsp;</li> 
	</ul>
	</div>
</div>
    </div>                       
    <div style="display:none"></div>
</body>
</html>