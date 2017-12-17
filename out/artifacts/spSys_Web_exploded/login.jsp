<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${applicationScope.sysConfig.applicationName}</title>
<script language="javascript" src="common/js/xjValidate.js"></script>
<link href="common/css/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="common/jquery/jquery-1.4.2.min.js"></script>
<script type=text/javascript>
	function checkform()  
	{
		var objarr = new Array(
		["userList.userId","用户帐号","notEmpty",""],
		["userList.password","密码","notEmpty",""]
		);
	
		if(xj.CheckAll(objarr))
			document.getElementById("form1").submit();
	}
	function keys(evt){
		var isie = (document.all) ? true : false;
		var key;
		var srcobj;
		if (isie) {
			key = event.keyCode;
			srcobj = event.srcElement;
		} else {
			key = evt.which;
			srcobj = evt.target;
		}
		if (key==13){
			checkform();
		}
	}
	function getCookie(name)
	{
	 var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
	 if(arr != null) 
	 return unescape(arr[2]); 
	 return null;
	} 
	$(document).ready(function(){
	 elementSize();
		var yy;
		if($.browser.msie){ 
			yy = document.compatMode == "CSS1Compat"? document.documentElement.clientHeight : document.body.clientHeight; 
		}else{ 
			yy = self.innerHeight; 
		}
		$("#mid").height(yy+"px");
		$("#box").css("top",(yy-550)/2+"px");
		 var username = $('#userId');
    	 username.bind('keydown', function (e) {
            var key = e.which;
            if (key == 13) {
                e.preventDefault();
                 $('#password').focus();
            }
        });
		/*
		var userId=getCookie('aktm');
		if(userId!=null&&userId!=''){
			$("#userId").val(userId);
			$("#password").val('cookie');
			$("#cookie").val('passed');
			document.form1.submit();
		}
		*/	
	});
	
	
	
	function elementSize()
	 {
		var ws=document.body.clientWidth;  
		var hs=document.body.clientHeight;
		var $loginDiv=$('.login');	   	
		
		$loginDiv.css('width',ws+"px");
		$loginDiv.css('height',hs+"px");
		
 		$('#username').focus();
 		var ulLeft=ws/2-53;   
 		var ulTop=hs/2-88; 
 		$('#username').css({'top':ulTop+'px','left':ulLeft+'px','padding':'0px','margin':'0px','position':'absolute'});
 		$('#password').css({'top':ulTop+35+'px','left':ulLeft+'px','padding':'0px','margin':'0px','position':'absolute'});
 		$('.napa').css({'width':'125px','height':'20px','border':'#FFF solid 0px'});
		$('#imageField').css({'top':ulTop+70+'px','left':ulLeft-5+'px','padding':'0px','margin':'0px','position':'absolute'});
 		$('#resetField').css({'top':ulTop+70+'px','left':ulLeft+ 70+'px','padding':'0px','margin':'0px','position':'absolute'});
 
 	}
 	window.onresize=elementSize;  	
</script>
</head>


<body onload="">
<div class="login">
  <form action="login.do" method="post" name="form1" onsubmit="return checkform()">
      <input type="hidden" name="loginType" value="system"/>
      <input name="userList.userId" type="text" id="username" class="napa" /><br/><br/>
      <input name="userList.password" type="password" id="password" class="napa"  />
      <input type="image" name="imageField" id="imageField" src="images/login/denglu.jpg"  />
      <input type="image"  id="resetField"  src="images/login/chongzhi.jpg"  onclick="reset()"/>
  </form>
 </div>
</body>
</html>
