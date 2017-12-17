	var lastClickIco=null;
	var lastMouseOverIco=null;
	$(document).ready(function(){
		$(".icoUl").each(function(i){
			var childCount = $(this).find("li").length;
			$(this).parent().parent().css("width",82*childCount+"px");
		});
		bindTabEventAndClass();
		bindIcoBarEventAndClass();
		setIcoBarWidth(0);
	});
	//为菜单文字所在的tab绑定事件并且为其上层li对象绑定css样式
	function bindTabEventAndClass()
	{
		$(".tabsLink").each(function(i){
			//绑定click事件，显示菜单图标，样式改变
			$(this).bind('click', function() {
				tabClick(i);
			});
			//绑定mousevoer事件，显示背景色
			$(this).bind('mouseover', function() {
				tabMouseOver(this);
			});
			//绑定mousevout事件，显示背景色
			$(this).bind('mouseout', function() {
				tabMouseOut(this);
			});
			//绑定上层li的初始样式
			var currentLi = $(this).parent();
			var prevLi = $(currentLi).prev();
			var nextLi =  $(currentLi).next();
			if(i==0){
				$(currentLi).addClass("currentTab_center");
				$(prevLi).addClass("currentTab_l");
				$(nextLi).addClass("currentTab_r");
			}else{
				$(currentLi).addClass("noSelectTab_center");
				$(prevLi).addClass("noSelectTab_l");
				$(nextLi).addClass("noSelectTab_r");
			}
			
		});
	}
	function tabClick(nowId)
	{
		$(".tabsLink").each(function(i){
			$('#menuDiv'+i).hide();
			var currentLi = $(this).parent();
			var prevLi = $(currentLi).prev();
			var nextLi =  $(currentLi).next();
			$(currentLi).removeClass();
			$(currentLi).addClass("noSelectTab_center");
			$(prevLi).removeClass();
			$(prevLi).addClass("noSelectTab_l");
			$(nextLi).removeClass();
			$(nextLi).addClass("noSelectTab_r");
		});
		var clickLi = $('#setTab'+nowId).parent();
		var prevLiClick = $(clickLi).prev();
		var nextLiClick =  $(clickLi).next();
		$(clickLi).removeClass();
		$(clickLi).addClass("currentTab_center");
		$(prevLiClick).removeClass();
		$(prevLiClick).addClass("currentTab_l");
		$(nextLiClick).removeClass();
		$(nextLiClick).addClass("currentTab_r");
		$('#menuDiv'+nowId).show();
		setIcoBarWidth(nowId);
	}
	function setIcoBarWidth(nowId)
	{
		var xx;
		if($.browser.msie){ 
			xx = document.compatMode == "CSS1Compat"? document.documentElement.clientWidth : document.body.clientWidth; 
		}else{ 
			xx = self.innerWidth; 
		}
		if($('#menuDiv'+nowId).find("a").length*90>xx)
		{
			$("#icoBar").css("width",$('#menuDiv'+nowId).find("a").length*90);
		}
	}
	function tabMouseOver(currentObj)
	{
		var mouseOverLi = $(currentObj).parent();
		var prevLimouseOver = $(mouseOverLi).prev();
		var nextLimouseOver =  $(mouseOverLi).next();
		if(!$(mouseOverLi).hasClass("currentTab_center"))
		{
			$(mouseOverLi).removeClass();
			$(mouseOverLi).addClass("mouseOverTab_center");
			$(prevLimouseOver).removeClass();
			$(prevLimouseOver).addClass("mouseOverTab_l");
			$(nextLimouseOver).removeClass();
			$(nextLimouseOver).addClass("mouseOverTab_r");
		}
	}
	function tabMouseOut(currentObj)
	{
		var mouseOutLi = $(currentObj).parent();
		var prevLimouseOut = $(mouseOutLi).prev();
		var nextLimouseOut =  $(mouseOutLi).next();
		if(!$(mouseOutLi).hasClass("currentTab_center"))
		{
			$(mouseOutLi).removeClass();
			$(mouseOutLi).addClass("noSelectTab_center");
			$(prevLimouseOut).removeClass();
			$(prevLimouseOut).addClass("noSelectTab_l");
			$(nextLimouseOut).removeClass();
			$(nextLimouseOut).addClass("noSelectTab_r");
		}
	}
	function bindIcoBarEventAndClass()
	{
		$(".icoUl").each(function(i){
			var liObj = $(this).children();
			var len=$(liObj).length;
			var tmp=$(liObj)[0]
			for(j=0;j<len;j++)
			{
				//绑定click事件，图标变色
				$($(liObj)[j]).bind('click', function() {
					liObjClick(this);
				});
				//绑定mousevoer事件，图标变色
				$($(liObj)[j]).bind('mouseover', function() {
					liObjMouseOver(this);
				});
				//绑定mousevout事件，图标变色
				$($(liObj)[j]).bind('mouseout', function() {
					liObjMouseOut(this);
				});
			}
		});
	}
	function liObjClick(liObj)
	{
		if(lastClickIco!=null){
			$(lastClickIco).removeClass();
		}
		lastClickIco=liObj;
		$(liObj).removeClass();
		$(liObj).addClass("icoBg");
		if($(liObj).find("a").attr("href")!="#")
			window.parent.mainFrame.location.href = $(liObj).find("a").attr("href");
	}
	function liObjMouseOver(liObj)
	{
		//不是当前选中的菜单
		if(!$(liObj).hasClass("icoBg"))
		{
			//上一个鼠标移过的菜单
			if(lastMouseOverIco!=null&&!$(lastMouseOverIco).hasClass("icoBg")){
				$(lastMouseOverIco).removeClass();
			}
			lastMouseOverIco=liObj;
			$(liObj).removeClass();
			$(liObj).addClass("icoBg_mouseOver");
		}
	}
	function liObjMouseOut(liObj)
	{
		//不是当前选中的菜单
		if(!$(liObj).hasClass("icoBg"))
		{
			//上一个鼠标移过的菜单
			if(lastMouseOverIco!=null){
				$(lastMouseOverIco).removeClass();
				lastMouseOverIco=null;
			}
		}
	}