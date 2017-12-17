closeDialog = function(){
		$('#dialog').dialog('close');
}
function ModelWindow_dialog(url, width, height,title) {
	var inf = "edge: Raised; center: Yes; help: Yes; resizable: no; status: No;dialogHeight:" + height + "px;dialogWidth:" + width + "px;dialogLeft:400px;dialogTop:250px;scrolling:no";
	return showModalDialog(url + "&width=" + width + "&height=" + height+ "&title=" + title, window, inf);
}
function ModelWindow(url, width, height,title) {
	if($("#dialog").length == 0) 
		$("<div id='dialog'><iframe frameborder=0 id='dialogIframe' src='' width='100%' height='100%'>test</iframe></div>").appendTo("body");
	$("#dialogIframe")[0].src = url + "&width=" + width + "&height=" + height+"&title="+title;
	$('#dialog').dialog({
		autoOpen: false,
		width: width+50,
		height: height-100,
		title: title,
		modal:true
	});
	return $('#dialog').dialog('open');
} 
window.onload = function autoSetTitle() {
	if (parent.document.title == "\u516c\u5171\u6a21\u5f0f\u7a97\u53e3") {
		var ths = document.getElementsByTagName("th");//alert(ths.length);
		for (var i = 0; i < ths.length; i++) {
			parent.document.title = ths[i].innerText;
		}
	}
};
function setTitle(str) {
	parent.document.title = str;
}
var now;
now = new Date();
function getDateFormat() {
	return now.getFullYear() + "-" + (now.getMonth() + 1) + "-" + now.getDate();
}
function getFirstLast() {
	oToday = new Date();
	currentDay = oToday.getDay();
	if (currentDay == 0) {
		currentDay = 7;
	}
	mondayTime = oToday.getTime() - (currentDay - 1) * 24 * 60 * 60 * 1000;
	sundayTime = oToday.getTime() + (7 - currentDay) * 24 * 60 * 60 * 1000;
	var datearr = new Array();
	var mondayDay = new Date(mondayTime);
	var sundayDay = new Date(sundayTime);
	datearr[0] = mondayDay.getFullYear() + "-" + (mondayDay.getMonth() + 1) + "-" + mondayDay.getDate();
	datearr[1] = sundayDay.getFullYear() + "-" + (sundayDay.getMonth() + 1) + "-" + sundayDay.getDate();
	return datearr;
}
var xjCommon = {
	/**
	 *	Ò³ÃæcheckboxÈ«Ñ¡¹¦ÄÜ£¬´«ÈëcheckboxÃû³Æ
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
	 *	·´»ØÒ³Ãæ¶ÔÏó×éµÄÖµ£¬ÒÔÖ¸¶¨·Ö¸ô·û½øÐÐÁ¬½Ó
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
	//¸ñÊ½»¯ÈÕÆÚÊäÈë
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
	}
}
