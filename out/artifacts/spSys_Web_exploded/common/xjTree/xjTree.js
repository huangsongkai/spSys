var xjTree = {
	//被点中的节点对象
	obj:null,
	//树节点展开时图片
	nodeOpenImg:'',
	//树节点关闭时图片
	nodeCloseImg:'',
	//节点是否使用双击事件
	useChooseNext:true,
	//双击树节点时是否只允许选择跟节点
	chooseLeaf:'true',
	init : function(openImg,closeImg)
	{
		this.nodeOpenImg = openImg;
		this.nodeCloseImg = closeImg;
	},
	//图片点击事件，展开，收缩（turnIt方法简写）
	t : function(nodeId,imgId){
		var nowNode=document.getElementById(nodeId);
		var nowimg=document.getElementById(imgId);
		if(nowNode.style.display=="none")
		{
			nowNode.style.display="";
			nowimg.src=this.nodeOpenImg;
		}
		else
		{
			nowNode.style.display="none";
			nowimg.src=this.nodeCloseImg;
		}
	},
	//单击节点事件，变色，需实现clickEvent方法（changeStyle方法简写)
	c : function(thisNode){
		try{
			this.obj.style.color='#1c459b';
		}
		catch(Ex)
		{}
		this.obj=thisNode;

		thisNode.style.color='red'; 
		try{
			clickEvent(thisNode);
		}catch(oe)
		{
			//alert("请在调用页实现clickEvent(var1)事件，var1为当前点击节点对象，其属性中的id为可返回的数据值！");
		}
	},
	//双击事件，需实现dblClickEvent方法（goNext方法简写）
	g : function (nodeObj,nodeId,imgId){
		var thisNode=document.getElementById(nodeId);
		var nodeImg=document.getElementById(imgId);
		try
		{
			//包含下一节点，并且不允许选择中间节点
			if(nodeObj.getAttribute("nextlevel")=="true" && chooseLeaf=='true')
			{
				if(thisNode.style.display=="none")
				{
					thisNode.style.display="";
					nodeImg.src=this.nodeOpenImg;
				}
				else
				{
					thisNode.style.display="none";
					nodeImg.src=this.nodeCloseImg;
				}
			}
			else
			{
				try{
					dblClickEvent(nodeObj);
				}catch(oe)
				{
					//alert("请在调用页实现dblClickEvent(var1)事件，var1为当前点击节点对象，其属性中的id为可返回的数据值！");
				}
			}	
		}
		catch(Ex)
		{
			try{
				dblClickEvent(nodeObj);
			}catch(oe)
			{
				//alert("请在调用页实现dblClickEvent(var1)事件，var1为当前点击节点对象，其属性中的id为可返回的数据值！");
			}
		}
	},
	chooseNext : function (nodeObj){
		if(!this.useChooseNext)
			return;
		this.chooseChile(nodeObj);
		//是否为头节点，如果为不为头节点需要选择父节点
		if(nodeObj.getAttribute("plevel")!="0")
			this.chooseParent(nodeObj);
	},
	//设置其下子节点状态
	chooseChile : function (nodeObj){
		var objname=nodeObj.name;
		var objvalue=nodeObj.value;
		var e=document.getElementsByName(objname);
		if(nodeObj.getAttribute("nextlevel")=="true")
		{
			for(var i=0;i<e.length;i++)
			{
				if(e[i].getAttribute("plevel")==nodeObj.getAttribute("level"))
				{
					e[i].checked=nodeObj.checked;
					this.chooseChile(e[i]);
				}
			}
		}
	},
	//设置父节点状态
	chooseParent : function (nodeObj){
		var objname=nodeObj.name;
		var objvalue=nodeObj.value;
		var e=document.getElementsByName(objname);
		var test=false;
		for(var i=0;i<e.length;i++)
		{
			if(e[i].getAttribute("plevel")==nodeObj.getAttribute("plevel"))
			{
				if(e[i].checked==true)
				{
					test=true;
					break;	
				}
			}
		}
		for(var i=0;i<e.length;i++)
		{
			//节点的ID与所选节点的父级ID相同，即为所选节点的父级
			if(e[i].getAttribute("level")==nodeObj.getAttribute("plevel"))
			{
				e[i].checked=test;
				this.chooseParent(e[i]);
			}
		}
	},
	//单选
	chooseMe : function (thisObj){
		try{
			radioClickEvent(thisObj)
		}catch(oe)
		{
			//alert("请在调用页实现radioClickEvent(thisObj)事件，thisObj为当前点击radio！");
		}
	},
	checkByValue :　function (idName,strValue){
		var e = document.getElementsByName(idName);
		var values = strValue.split(",");
		if(e==null||strValue==null||e.length==0||values.length==0)
			return;
		for(var i=0;i < e.length; i++)
		{
			var chk =e[i];
			for(var v=0;v<values.length;v++)
			{
				if(chk.value==values[v])
				{
					chk.checked=true;
					openNode(idName,chk.getAttribute("plevel"));
				}
			}
		}
	},
	openNode : function (idName,plevel){
		var e = document.getElementsByName(idName);
		for(var p=0;p < e.length; p++)
		{
			if(e[p].getAttribute("level")==plevel)
			{
				var nowtbl=document.getElementById("tbl"+e[p].value);
				if(nowtbl.style.display=="none")
				{
					eval("turnit('tbl"+e[p].value+"','img"+e[p].value+"')");
					openNode(idName,e[p].getAttribute("plevel"));
					break;
				}
			}
		}
	},
	//取得当前复选树的所选中的节点值
	getCheckedValue : function (checkboxName,regexStr){
		//复选框或单选框的名称
		try{
			var e = document.getElementsByName(checkboxName);
			var returnStr="";
			for(var i=0;i<e.length;i++)
			{
				if(e[i].checked==true)
					returnStr+=e[i].value+regexStr;
			}
			if(returnStr!=""){
				returnStr=returnStr.substring(0,returnStr.length-1);
			}
			return returnStr;
		}
		catch(e)
		{
			alert(e)
		}
	},
	//用于调试，查看节点信息
	seeNode : function(){
		if(this.obj==null)
		{
			alert("尚未选中树节点数据！");
		}
		else
		{
			var showStr="";
			showStr+="节点id = "+this.obj.id+"\n";
			showStr+="节点是否包含下一级 nextlevel = "+this.obj.getAttribute("nextlevel");
			alert(showStr);
		}
	},
	//根据所需属性返回对应值
	getV : function(attributeName){
		if(this.obj==null)
		{
			alert("尚未选中树节点数据！");
		}
		else
		{
			return this.obj.getAttribute(attributeName);
		}
	}
}
