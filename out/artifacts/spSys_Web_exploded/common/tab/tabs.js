var xjTabs = {
	initTab : function()
	{
		xjTabs.bindTabEventAndClass();
		$("#body_content").append("<div id='preloader' style='display:none'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;加载中...</div>");
	},
	bindTabEventAndClass : function()
	{
		$(".tabsLink").each(function(i){
			//绑定click事件，显示菜单图标，样式改变
			$(this).bind('click', function() {
				return xjTabs.tabClick(i);
			});
			//绑定mousevoer事件，显示背景色
			$(this).bind('mouseover', function() {
				xjTabs.tabMouseOver(this);
			});
			//绑定mousevout事件，显示背景色
			$(this).bind('mouseout', function() {
				xjTabs.tabMouseOut(this);
			});
			//绑定上层li的初始样式
			var currentLi = $(this).parent();
			var prevLi = $(currentLi).prev();
			var nextLi =  $(currentLi).next();
			if(i==0){
				$(currentLi).addClass("currentTab_center");
				$(prevLi).addClass("currentTab_l");
				$(nextLi).addClass("currentTab_r");
				$('#content0').show();
			}else{
				$(currentLi).addClass("noSelectTab_center");
				$(prevLi).addClass("noSelectTab_l");
				$(nextLi).addClass("noSelectTab_r");
			}
			
		});
	},
	tabClick : function (nowId)
	{
		$(".tabsLink").each(function(i){
			$('#content'+i).hide();
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
		//如果当前表格已经存在
		if($('#content'+nowId).length>0)
		{
			$('#content'+nowId).show();
		}
		else
		{
			var nowLink = $('#setTab'+nowId);
			if($(nowLink).attr("link")!="#")
			{
				$("#preloader").show();
				$.ajax(
				{
					url: $(nowLink).attr("link"), 
					cache: false,
					success: function(message) 
					{
						$("#preloader").hide(); 
						$("#body_content").empty();
						$("#body_content").append("<div id='content"+nowId+"' class='tabContent' width='100%'>"+message+"</div>");
						$("#content"+nowId).show();
					}
				});
			}
		}
		var isIE6Up = true;
		if(navigator.appName == "Microsoft Internet Explorer") 
		{
			if(navigator.appVersion.match(/MSIE 6./i)=='MSIE 6.') 
			{
				isIE6Up=false; 
			}
		}
		if(isIE6Up)
		{
			$('#content'+nowId).css("width",$('#content'+nowId).attr("minWidth")+"px");
			//alert($("#body_content").css("overflow"));
			//$("#body_content").css("overflow","hidden");
		}
		return false;
	},
	tabMouseOver:function(currentObj)
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
	},
	tabMouseOut:function(currentObj)
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
}