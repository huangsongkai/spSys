var xjSpPage = {
	//自定义页数跳转
	theForm : null,
	customPage : function()
	{
		var varpage=document.getElementById("pageNumber").value;
		var pagecount=document.getElementById("pageCount").value;
		if(isNaN(varpage)||parseInt(varpage)<=0||parseInt(varpage)>parseInt(pagecount))
		{
			alert("输入页数有误！");
			return;
		}
		else
		{
			xjSpPage.getPage(varpage);
		}
	},
	//跳到指定页数
	getPage : function(varpage)
	{
		xjSpPage.setForm();
		var nowpage=document.getElementById("nowPage").value;
		var pagecount=document.getElementById("pageCount").value;
		if(pagecount!=0)
		{
			if(varpage!="")
			{
				if(nowpage!=1&&varpage==1)
				{
					document.getElementById("pageNumber").value=varpage;
					xjSpPage.spPageSubmit();
				}
				else
				if(nowpage!=pagecount&&varpage==pagecount)
				{
					document.getElementById("pageNumber").value=varpage;
					xjSpPage.spPageSubmit();
				}
				else
				if(nowpage!=varpage)
				{
					document.getElementById("pageNumber").value=varpage;
					xjSpPage.spPageSubmit();
				}
				else
				if(('#pageSize').val()!=20)
				{
					document.getElementById("pageNumber").value=varpage;
					xjSpPage.spPageSubmit();
				}
			}
		}
	},
	//上一页下一页
	steppage : function(varstep)
	{
		xjSpPage.setForm();
		var nowpage=parseInt(document.getElementById("nowPage").value);
		var pagecount=parseInt(document.getElementById("pageCount").value);
		if(varstep=='previous')
		{
			if(nowpage-1>0)
			{
				document.getElementById("pageNumber").value=parseInt(document.getElementById("pageNumber").value)-1;
				xjSpPage.spPageSubmit();
			}
		}
		else
		{
			if(nowpage+1<=pagecount)
			{
				document.getElementById("pageNumber").value=parseInt(document.getElementById("pageNumber").value)+1;
				xjSpPage.spPageSubmit();
			}
		}
	},
	//分页提交表单
	spPageSubmit : function()
	{	
		var formId = document.getElementById("formId").value;
		if(formId != "")
			xjSpPage.theForm = document.getElementById(formId);
		else
			xjSpPage.theForm = document.forms[0]; //新增获取form
		document.getElementById("submitType").value="sppage";
		var spPageType = document.getElementById("spPageType").value;
		var spPageContainerId = document.getElementById("spPageContainerId").value;
		if(spPageType == "submit")
		{
			xjSpPage.theForm.method="post";
			xjSpPage.theForm.submit();
		}
		else
		{
			var url = xjSpPage.theForm.action;
			var tmp = parseInt(Math.random()*100000+1);
			if(url.indexOf("?")==-1)
				url+="?xjTmp="+tmp;
			else
				url+="&xjTmp="+tmp;
			$.ajax({
				type: "POST",
				url: url,
				data: $(xjSpPage.theForm).serialize(),
				success: function(data){
				var x = document.getElementById("tab").value;
				$("#"+spPageContainerId).empty();
				$("#"+spPageContainerId).append("<div id='content"+x+"' class='tabContent' width='100%'>"+data+"</div>");
				$("#content"+x).show();
				
			   }
			});
		}
	},
	setForm : function()
	{
		var formId = document.getElementById("formId").value;
		if(formId != "")
			xjSpPage.theForm = document.getElementById(formId);
		else
			xjSpPage.theForm = document.forms[0];
		xjSpPage.theForm.target="_self";
		//xjSpPage.theForm.reset();
		//取消reset form
	},
		//控制输入页数位置内容
	page_onkeypress :function(e)
	{
		var key = window.event ? e.keyCode:e.which;
	    var keychar = String.fromCharCode(key);
	    if(key==8)
	    	return true;
	    reg = /\d/;
	    return reg.test(keychar);
	},
	//控制输入页数位置内容
	checkIsNum :function(obj)
	{
		if(isNaN(obj.value))
		{
			alert("所输入的内容不符合格式要求");
			obj.focus();
		}
	}
	
}