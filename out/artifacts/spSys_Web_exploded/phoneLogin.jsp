<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>${applicationScope.sysConfig.applicationName}</title>
<script language="javascript" src="common/js/xjValidate.js"></script>
<link href="common/css/loginphone.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="common/jquery/jquery.min.js"></script>
<script type=text/javascript>
	var winHeight; var winWidth;   
	 if (window.innerWidth)    
	  winWidth = window.innerWidth;  
	  else if ((document.body) && (document.body.clientWidth)) 
	      winWidth = document.body.clientWidth; // 获取窗口高度 
	      if (window.innerHeight)   
	        winHeight = window.innerHeight;
	         else if ((document.body) && (document.body.clientHeight))   
	           winHeight = document.body.clientHeight;     // 通过深入 Document 内部对 body 进行检测，获取窗口大小
	          if (document.documentElement && document.documentElement.clientHeight  && document.documentElement.clientWidth){
	               winHeight = document.documentElement.clientHeight;  
	              winWidth = document.documentElement.clientWidth; }        
	            window.onload = function(){     // 先获取想要改变的所有图片对象（集合）   
	              var obj=document.getElementById("content").getElementsByTagName("img");   
	                for(var i=0;i<obj.length;i++){      
	                   var width = obj[i].width;          // 判断图片宽度是否大于屏幕宽度     
	                      if(width > winWidth){            obj[i].width = winWidth;        }     } } 
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
</script>
</head>

<body >
  <form action="phoneLogin.do" method="post" name="form1" id="form1">
		<div id="box" >
			<ul>
			    <input type="hidden" name="cookie" id="cookie"/>
				<li><input name="userList.userId" type="text" class="input" id="userId" name="userList.userId" value=""/></li>
			    <li><input name="userList.password"  type="password" class="input" id="password" name="userList.password" onkeydown="keys(event)" value=""/></li>
			</ul>
			
			<img src="images/login/login_btn.jpg"  class="login_button" onclick="checkform()" width="60" height="30"/>
		</div> 
	</form>
</body>
</html>
