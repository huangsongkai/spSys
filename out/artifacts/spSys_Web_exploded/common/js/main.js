// JavaScript Document
	//模式窗口中指定此参数为true设置div框
	var isModalWindow=false;
	var id=0;
	$.ligerui.controls.Dialog.prototype._borderX = 2;
    $.ligerui.controls.Dialog.prototype._borderY = 0;
	 function ModalDialog(url,width,height,title){
	    if(url.indexOf("?")>1){
	    	url += '&dialogNo='+id;
	    }else{
	    	url += '?dialogNo='+id;
	    }
	    $.ligerDialog.open({id:'dialog'+id, url:url,title:title,height:height,width:width,showMax: true, showToggle: true, showMin: true, isResize: true, modal: false });			
	   	id++;
     }
     
	function ModalDialog_Close(id){
    var x=$.ligerui.get('dialog'+id);
	   x.close(); 	
	 }
	$(document).ready(function(){
		var yy;
		
		 
		if($.browser.msie){ 
			yy = document.compatMode == "CSS1Compat"? document.documentElement.clientHeight : document.body.clientHeight; 
		}else{ 
			yy = self.innerHeight; 
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
			$("#body_div").height(yy-12);
			$("#body_content").height(yy-38);
			if(isModalWindow)
			{
//				$("#body_div").css("width","99.5%");
				$("#body_content").height(yy-20);
				var ths = $(".body_title_text");
				
				$.each(ths, function(key, val) {
					parent.document.title = $(val).html();
					return false; 
				}); 

			}
		}
		else
		{
			$("#body_div").height(yy-8);
			$("#body_content").height(yy-38);
//			$("#body_div").css("width","98.9%");
		}
		var queryDivHeight = $("#queryDiv").height();
		$("#queryDiv").height(queryDivHeight+12);
		_setTableWidth();
	});
	//表单提交时出发方法，自动遮盖页面，显示等待区域，使用需手动调用此方法
	function _waitFormSubmiting()
	{
		$("#body_content").append("<style>.nyroModalDom{background-image:url(../../../images/main/loading.gif);background-repeat: no-repeat;background-position: -5px -5px;padding: 5px;text-align: center;background-color: #FFFFFF;border: 1px solid #92C8FA;font-size:12px;width:150px;height:20px;} </style><a href='#waitingFormSubmit' class='nyroModal' id='nyroWaitFormSubmit' rev='modal'></a><div id='waitingFormSubmit' style='display:none;'>&nbsp;&nbsp;&nbsp;&nbsp;数据提交中，请稍候...</div>");
		$('#nyroWaitFormSubmit').nyroModal({
			sizes: {
				initW: 300,	// Initial width
				initH: 200,
				w : 300,
				h : 200
			}
		});
		$('#nyroWaitFormSubmit').nmCall();
		alert("submiting......");
	}
	//设置查询区的背景
  	function _queryDivBg()
	{
		if($('#queryDiv').css("display")=="none")
		{
			nowId = "queryDiv";
			var offset = $("div[toolbarid='queryButton']").offset();
			$('#queryDiv').css({ 
				'left':offset.left, 
				'top':offset.top+20,
				'display':'block'
				});
			$('#queryDiv').offset().left=offset.left;
			$('#queryDiv').offset().top=offset.top+20;
			var isIE6Up = true;
			if(navigator.appName == "Microsoft Internet Explorer") 
			{
				if(navigator.appVersion.match(/MSIE 6./i)=='MSIE 6.') 
				{
					$('#queryDiv table').css({ 
						'left':"0px"
						});
				}
			}
			
		}
		else
		{
			$('#queryDiv').css("display","none");
		}
	}
		function _queryDivRg(linkobj)
	{
		if($('#queryDiv').css("display")=="none")
		{
			nowId = "queryDiv";
			var offset = $(linkobj).offset();
			$('#queryDiv').css({ 
				'left':offset.left+100, 
				'top':offset.top+20,
				'display':'block'
				});
			$('#queryDiv').offset().left=offset.left;
			$('#queryDiv').offset().top=offset.top+20;
			var isIE6Up = true;
			if(navigator.appName == "Microsoft Internet Explorer") 
			{
				if(navigator.appVersion.match(/MSIE 6./i)=='MSIE 6.') 
				{
					$('#queryDiv table').css({ 
						'left':"0px"
						});
				}
			}
			
		}
		else
		{
			$('#queryDiv').css("display","none");
		}
	}
	//影藏查询区
	function _cancelQuery(){
		$('#queryDiv').css("display","none");
	}
	//初始化框架页iframe的高度
	function _init_iFrame_height(iframeId)
	{
		var yy;
		if($.browser.msie){ 
			yy = document.compatMode == "CSS1Compat"? document.documentElement.clientHeight : document.body.clientHeight; 
		}else{ 
			yy = self.innerHeight; 
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
			$("#"+iframeId).height(yy);
		}
		else
		{
			$("#"+iframeId).height(yy-5);
		}
	}
	//调整设置自动表格宽度的表格大小
	function _setTableWidth()
	{
		$(".tableAutoWidth").each(function(i){
			if(parseInt($(this).css("width"))<parseInt($(this).attr("minWidth")))
			{
				var isIE6Up = true;
				if(navigator.appName == "Microsoft Internet Explorer") 
				{
					if(navigator.appVersion.match(/MSIE 6./i)=='MSIE 6.') 
					{
						isIE6Up=false; 
					}
				}
				//IE6以上全部应用最小宽度
				if(isIE6Up)
				{
					$(this).css("width",$(this).attr("minWidth")+"px");
					//alert($("#body_content").css("overflow"));
					$("#body_content").css("overflow","auto");
				}
				else
				{
					//IE6一下不出现tab的情况下使用
					if(!$(this).hasClass("tabContent"))
					{
						$(this).css("width",$(this).attr("minWidth")+"px");
						//alert($("#body_content").css("overflow"));
						$("#body_content").css("overflow","auto");
					}
				}
			}
		});
	}
	
//调用模式窗口
function ModalWindow(url, width, height) {
	var inf = "edge: Raised; center: Yes; help: Yes; resizable: no; status: No;dialogHeight:" + height + "px;dialogWidth:" + width + "px;scrolling:no";
	return showModalDialog(url + "&width=" + width + "&height=" + height, window, inf);
}

var xjCommon = {
	/**
	 *	页面checkbox全选功能，传入checkbox名称
	 */ 
	checkAll : function (objName) {
		try {
			var checkedcount = 0;
			var checkObj = document.getElementsByName(objName);
			for (var i = 0; i < checkObj.length; i++) {
				if (checkObj[i].checked) {
					checkedcount++;
				}
			}
			if (checkedcount == checkObj.length) {
				for (var i = 0; i < checkObj.length; i++) {
					checkObj[i].checked = false;
				}
			} else {
				for (var i = 0; i < checkObj.length; i++) {
					checkObj[i].checked = true;
				}
			}
		}
		catch (ex) {
		}
	},
	/**
	 *	反回页面对象组的值，以指定分隔符进行连接
	 */ 
	getValueStr : function(objName,regex) {
		try {
				var str = "";
				var checkObj = document.getElementsByName(objName);
				for (var i = 0; i < checkObj.length; i++) {
					if (checkObj[i].checked) {
						str+=checkObj[i].value+regex;
					}
				}
				if(str.length>0)
					str=str.substring(0,str.length-1);
				return str;
			}
			catch (ex) {
			}
	},
	//格式化日期输入
	formatdate : function (varobj,e)
	{
	    var nc = window.event ? e.keyCode:e.which;
	    if(nc!=8||nc!=46)
	    {
	    	if(nc>=48 && nc<=57)
	    	{
		    	var i=0;
		    	var arrval=varobj.value.split("-");
		    	i=arrval.length;
		    	if(i<3)
		    	{
				    	if(varobj.value.length==4)
				    		varobj.value+="-";
				    	else
				    	if(varobj.value.length==7)
				    		varobj.value+="-";
		    	}
			}
	  		else
	  		{
	  			return false;
	  		}
	    }
	},
	/*
	控制tr行选中，变换背景色；
	需要在tr中添加onclick事件：<tr onClick="xjCommon.TrClick(this)" id="${obj.id}">
	*/
	varClkTr:null,   
	oldcolor:null,
	trClickUserFunc : function(){},
	TrClick : function(vartr)   
	{
		if(this.varClkTr!=null)   
	 	{   
	    	$(this.varClkTr).css("background-color",""); 
			$(this.varClkTr).children().css("background-color",""); 
		}
		$(vartr).css("background-color","#6699FF"); 
		$(vartr).children().css("background-color","#6699FF"); 
		this.varClkTr=vartr;
		this.trClickUserFunc();
	},
	TrClickNoEvent : function(vartr)   
	{
		if(this.varClkTr!=null)   
	 	{   
	    	$(this.varClkTr).css("background-color",""); 
			$(this.varClkTr).children().css("background-color",""); 
		}
		$(vartr).css("background-color","#6699FF"); 
		$(vartr).children().css("background-color","#6699FF"); 
		this.varClkTr=vartr;
	},
	getVarClkTr:function(){return varClkTr;}
	,
	TrMousemove : function(vartr)
	{
		if(vartr!=this.varClkTr){
			$(vartr).css("background-color","#BDDFFF"); 
			$(vartr).children().css("background-color","#BDDFFF"); 
		}
	},   	
	TrMouseout : function(vartr)
	{
		if(vartr!=this.varClkTr){
			$(vartr).css("background-color",""); 
			$(vartr).children().css("background-color",""); 
		}
	},
	getVarClkTr: function(){
		return this.varClkTr;
	}
}
	function conversionUrl(path1,path2,width){
		var url='&width='+width;
		var url1;
		var url2;
		var str1;
		var str2;
		var elements1;
		var elements2;
		if(path1.indexOf("?")>1){
			url1 = path1.split("?");
			url+="&left.url="+url1[0];
			
			if(url1[1].indexOf("&")>1){
				elements1 = url1[1].split("&");
				for(var i=0;i<elements1.length;i++)
				{
					if(elements1[i].indexOf("=")>1)
					{
						str1 = elements1[i].split("=");
						url+="&left."+str1[0]+"="+str1[1];
					}
				}
			}else{
				if(url1[1].indexOf("=")>1)
				{
					str1 = url1[1].split("=");
					url+="&left."+str1[0]+"="+str1[1];
				}
			}
		}else{
			url+="&left.url="+path1;
		}
		if(path2.indexOf("?")>1){
			url2 = path2.split("?");
			url+="&right.url="+url2[0];
			
			if(url2[1].indexOf("&")>1){
				elements2 = url2[1].split("&");
				for(var i=0;i<elements2.length;i++)
				{
					if(elements2[i].indexOf("=")>1)
					{
						str2 = elements2[i].split("=");
						url+="&right."+str2[0]+"="+str2[1];
					}
				}
			}else{
				if(url2[1].indexOf("=")>1)
				{
					str2 = url2[1].split("=");
					url+="&right."+str2[0]+"="+str2[1];
				}
			}
		}else{
			url+="&right.url="+path2;
		}
		return url;
}
	
     
