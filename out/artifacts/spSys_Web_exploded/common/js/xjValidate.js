/*
 * 公共js验证方法3.0
 * @author 王瑾 homeabc@126.com
 *
 */
var xj = {
	extArrayNotAllowed : new Array('.php','.php3','.php5','.phtml','.asp','.aspx','.ascx','.jsp','.cfm','.cfc','.pl','.bat','.exe','.dll','.reg','.cgi','.htm','.html','.shtml'),
	extArrayAllowed  :null,
	//当前需验证的formId
	formId : null ,
	theForm : null ,
	//是否alert进行提示
	showNotice : null ,
	//忽略不存在
	ignoreNotExistElment : false,
	//使用复选树时指定为true，由树形标签进行赋值;
	checkBoxTree : false,
	//数组条件进行循环判断
	CheckAll : function (objArray)
	{
		if(this.formId!=null)
			this.theForm = document.getElementById(this.formId);
		var runValue=true;
		var ex = true;
		var i;
		xj.replaceAllNoticeDiv();	
		//循环进行验证，每循环一次验证一个或一组对象
		for (i=0; i < objArray.length; i++)
		{
			//验证未通过返回false，否则不进行操作
			runValue = this.CheckOne(objArray[i][0],objArray[i][1],objArray[i][2],objArray[i][3]);
			if(!runValue)
				ex = false;
		}
		if(!ex){
			if(this.showNotice==true)
				alert("当前表单有校验未通过的数据项，请检查后再进行提交！");
			return false;
		}
		var fileObj = getElementFromDocumentOrForm(this.theForm,"upload");
		
		for(var i=0;i<fileObj.length;i++)
		{
			
			var allowSubmit = true;
			var filePath = fileObj[i].value;
			if(filePath=="")
				continue;
			while (filePath.indexOf("\\") != -1)
			filePath = filePath.slice(filePath.indexOf("\\") + 1);
			ext = filePath.slice(filePath.lastIndexOf(".")).toLowerCase();
			if(this.extArrayAllowed!=null)
			{
				allowSubmit=false;
				for (var j = 0; j < this.extArrayAllowed.length; j++) {
					if (this.extArrayAllowed[j] == ext) { allowSubmit = true; }
				}
			}
			else
			{
				for (var j = 0; j < this.extArrayNotAllowed.length; j++) {
					if (this.extArrayNotAllowed[j] == ext) { allowSubmit = false; }
				}
			}
			if(allowSubmit==false)
			{
				var fileType = "";
				for(var i=0;i<this.extArrayAllowed.length;i++)
					fileType+=this.extArrayAllowed[i]+",";
				if(fileType.length>0)
					fileType = fileType.subString(0,fileType.length-1);
				alert("所上传的文件类型不符合系统要求，当前功能只允许上传"+fileType+"类型文件！");
				return false
			}
		}
		return true;
	},
	CheckOne : function (objName,msg,role,compareValue)
	{
//	alert("objName:"+objName+"   msg:"+msg+"  role:"+role+"   compareValue:"+compareValue);
		var e=getElementFromDocumentOrForm(this.theForm,objName);
		var test=true;
		try{
			var objlen=e.length;
			var objtype;
			var chkcnt=0;
			var objValue;
			if(objlen==0&&!this.ignoreNotExistElment)
			{
				alert("对象'"+objName+"'不存在！");
				return false;
			}
			//----------------------循环对象--------------------------
			for(var i=0;i<e.length;i++)
			{
				objtype=e[i].type;
//			    alert(e[i].name);
				var div_id = xj.createNoticeDiv(e[i]);
				
				//根据不同的对象类型进行取值
				if(e[i].type=="text"||e[i].type=="password"||e[i].type=="textarea" ||e[i].type=="hidden"||e[i].type=="file")//文本框，密码框
				{
					
					objValue=e[i].value;
					objValue=objValue.trim();
					try{
						e[i].value = objValue.trim();
					}catch(e){}
					if(role == "notEmpty")
					{
						if(this.IsEmpty(objValue))
						{

							div_id.style.display="";
							div_id.innerHTML=msg+"不能为空！";
							test = false;
						}
					}else if(role == "date")
					{
						if(!this.isDate(objValue))
						{
							
							div_id.style.display="";
							div_id.innerHTML=msg+"输入日期格式不符合系统规则，请按照yyyy-MM-dd格式输入！";
							test = false;
						}
					}else if(role == "dateTime")
					{
						if(!this.isDateTime(objValue))
						{
							
							div_id.style.display="";
							div_id.innerHTML=msg+"输入日期格式不符合系统规则，请按照yyyy-MM-dd hh:mm:ss格式输入！";
							test = false;
						}
					}else if(role == "int")
					{
						if(!this.isInt(objValue,msg,compareValue,div_id))
						{
							test = false;
						}
					}else if(role == "float")
					{
						if(!this.IsFloatLength(objValue,msg,compareValue,div_id))
						{
							test = false;
						}
					}
					else if(role == "email")
					{
						if(!this.isEmail(objValue))
						{
							
							div_id.style.display="";
							div_id.innerHTML=msg+"所输入的格式不正确！";
							//alert(msg+"，所输入的格式不正确！");
							test = false;
						}
					}
					else if(role == "phone")
					{
						if(!this.isPhone(objValue))
						{
							
							div_id.style.display="";
							div_id.innerHTML=msg+"所输入的格式不正确！";
							//alert(msg+"，所输入的格式不正确！");
							test = false;
						}
					}
					else if(role == "eqO")
					{
						var cObj = getElementFromDocumentOrForm(this.theForm,compareValue);
						
						if(cObj.length==0&&!this.ignoreNotExistElment)
						{
							alert("对象"+compareValue+"不存在");
							return false;
						}
						for(var c=0;c<cObj.length;c++)
						{
							if(objValue!=cObj[c].value)
							{
								//alert(msg);
								div_id.style.display="";
								div_id.innerHTML=msg+"";
								test = false;
								//break;
							}
						}
					}
					else if(role == "neO")
					{
						var cObj = getElementFromDocumentOrForm(this.theForm,compareValue);
						
						if(cObj.length==0&&!this.ignoreNotExistElment)
						{
							alert("对象"+compareValue+"不存在");
							return false;
						}
						for(var c=0;c<cObj.length;c++)
						{
							if(objValue==cObj[c].value)
							{
								div_id.style.display="";
								div_id.innerHTML=msg+"";
								//alert(msg);
								test = false;
								//break;
							}
						}
					}
					else if(role == "eqV")
					{
						if(objValue!=compareValue)
						{
							div_id.style.display="";
							div_id.innerHTML=msg+"";
							//alert(msg);
							test = false;
							//break;
						}
					}
					else if(role == "neV")
					{
						if(objValue==compareValue)
						{
							div_id.style.display="";
							div_id.innerHTML=msg+"";
							//alert(msg);
							test = false;
							//break;
						}
					}
					else if(role == "max")
					{
						if(!this.isMax(objValue,compareValue,msg,div_id))
						{
							test = false;
						}
					}
					else if(role == "min")
					{
						if(!this.isMin(objValue,compareValue,msg,div_id))
						{
							test = false;
						}
					}else if(role=="function"){
						var returnMessage = eval(msg+"('"+e[i].value+"')");
						if(returnMessage!=compareValue)
						{
							div_id.style.display="";
							div_id.innerHTML=returnMessage;
						}
					}
				}
				else if(e[i].type=="select-one")//下拉选择框
				{
					objValue=e[i].options[e[i].selectedIndex].value;
					if(objValue==compareValue)
					{
						div_id.style.display="";
						div_id.innerHTML=msg+"必须选择！";
						//alert(msg+"必须选择！");
						test = false;
					}
				}
				else if(e[i].type=="checkbox"||objtype=="radio")//复选框，单选按钮
				{
					if(e[i].checked==true)
					{
						chkcnt+=1;
					}
					if(chkcnt==0&&i==e.length-1)
					{
						if(this.checkBoxTree!=true)
						{
							div_id.style.display="";
							div_id.innerHTML=msg+"必须选择！";
						}
						else
							alert(msg+"必须选择！");
						test = false;
					}
				}
			//	if(test==false)
			//		return false;
			}
		}catch(e)
		{
			alert(e);
		}
		return test;
	},
//***************************功能分区，以下为内部工具性方法********************************

	/**
	 *名    称：setFocus
	 *功    能：设置对象焦点
	 *入口参数：对象元素
	 *示    例：xj.setFocus(document.getElementById("userName"));
	 */
	setFocus : function(getObj){
		if(getObj.type!="hidden"){
			getObj.focus();
		}
	},
	
	/**
	 *名　　称：IsEmpty
	 *功    能：判断是否为空
	 *入口参数：fData：要检查的数据
	 *出口参数：True：空  False：非空
	 *示    例：xj.IsEmpty(document.getElementById("userName").value);
	 */
	IsEmpty : function (fData)
	{
		return ((fData==null) || typeof(fData)=="undefined" || (fData.length==0) )
	},
	/**
	 *名　　称：getDataLength
	 *功    能：返回传入数据的字符型长度,
	 *入口参数：fData：需要计算的数据 
	 *出口参数：返回数据长度，支持中文字符数量判断
	 *示    例：xj.getDataLength(document.getElementById("userName").value);
	 */
	getDataLength : function (fData)
	{
		var intLength=0;
		for (var i=0;i<fData.length;i++){
			if ((fData.charCodeAt(i) < 0) || (fData.charCodeAt(i) > 255))
				intLength=intLength+2;
			else
				intLength=intLength+1;
		}
		return intLength

	},
	/**
	 *名　　称：greaterLength
	 *功    能：计算数据的长度,
	 *入口参数：fData：需要计算的数据 msg 验证对象返回消息使用的名称 elen:指定的长度
	 *出口参数：数据长度小于指定长度返回true,大于返回false
	 *示    例：xj.greaterLength(document.getElementById("userName").value,"用户名称",20);
	 */
	greaterLength : function (fData,msg,elen,div_id)
	{
		var intLength=this.getDataLength(fData);
		if (parseInt(intLength,10)<=parseInt(elen,10)){
			return true
		}else{
			div_id.style.display="";
			div_id.innerHTML=msg+"长度超出限定"+elen+"位范围";
			//alert(msg+"长度超出限定"+elen+"位范围");
			return false;
		}
	},
	/**
	 *名　　称：lessThanLength
	 *功    能：计算数据的长度,
	 *入口参数：fData：需要计算的数据 msg 验证对象返回消息使用的名称 elen:指定的长度
	 *出口参数：数据长度小于指定长度返回true,大于返回false
	 *示    例：xj.lessThanLength(document.getElementById("userName").value,"用户名称",20);
	 */
	lessThanLength : function (fData,msg,elen,div_id)
	{
		var intLength=this.getDataLength(fData);
		if (parseInt(intLength,10)>=parseInt(elen,10)){
			return true
		}else{
			div_id.style.display="";
			div_id.innerHTML=msg+"长度小于限定"+elen+"位范围";
			//alert(msg+"长度小于限定"+elen+"位范围");
			return false;
		}
	},
	/**
	 *名　　称：isInt
	 *功    能：判断是否为整数
	 *入口参数：objValue：需要计算的数据 msg 验证对象返回消息使用的名称 elen:指定的长度
	 *出口参数：为整数返回true,非整数返回false，长度超过制定length返回false
	 *示    例：xj.isInt(document.getElementById("age").value,"年龄",20);
	 */
	isInt : function(objValue,msg,elen,div_id){
		if(objValue=="")
			return true;
		
		var regex= "^-?\\d+$";
		var test = this.isRegex(objValue,regex);
		if(test)
			test=this.greaterLength(objValue,msg,elen,div_id);
		else
		{
			test = false;
			div_id.style.display="";
			div_id.innerHTML=msg+"必须输入整数！";
			//alert(msg+"必须输入整数！");
		}
		return test;
	},
	/**
	 *名　　称：IsFloatLength
	 *功    能：计算浮点数据的长度,数据长度小于指定长度返回真,大于返回假
	 *入口参数：fData：需要计算的数据 msg 验证对象返回消息使用的名称 elen:指定的长度
	 *出口参数：为浮点数返回true,非整数返回false，整数或小数长度超过指定length返回false
	 *示    例：xj.isInt(document.getElementById("age").value,"年龄",20);
	 */
	IsFloatLength : function (fData,msg,elen,div_id)
	{
		if(fData=="")
			return true;
		if(!this.isNumber(fData))
		{
			div_id.style.display="";
			div_id.innerHTML=msg+"必须输入数值型数据！";
			//alert(msg+"必须输入数值型数据！");
			return false;
		}
		var len = elen.split('.');
		var data = fData.split('.');
		var src = "";
		for(var i=0;i<data.length;i++){
			if (parseInt(data[i].length,10)>parseInt(len[i],10)){
				src = i==0?"所输入的整数位长度超出限定"+len[i]+"位范围":"所输入的小数位长度超出限定"+len[i]+"位范围";
				div_id.style.display="";
				div_id.innerHTML=msg+src;
				//alert(msg+src);
				return false;
			}
		}
		return true
	},
	/**
	 *名　　称：isNumber
	 *功    能：判断是否为实数
	 *入口参数：objValue：需要计算的数据
	 *出口参数：为实数数返回true,否则返回false
	 *示    例：xj.isNumber(document.getElementById("price").value);
	 */
	isNumber : function(objValue){
			var regex = "^(-?\\d+)(\.\\d+)?$";
			return this.isRegex(objValue,regex);
		},
	/**
	 *名　　称：isDate
	 *功    能：判断是短日期，形如 (yyyy-MM-dd)
	 *入口参数：str：需要计算的数据
	 *出口参数：格式匹配返回true,否则返回false
	 *示    例：xj.isDate(document.getElementById("bornDate").value);
	 */
	isDate : function(str)
	{
		if(str=="")
			return true;
		var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
		if(r==null)return false; 
		var d= new Date(r[1], r[3]-1, r[4]); 
		return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);
	},
	/**
	 *名　　称：isDateTime
	 *功    能：判断是长时间，形如(yyyy-MM-dd hh:mm:ss)
	 *入口参数：str：需要计算的数据
	 *出口参数：格式匹配返回true,否则返回false
	 *示    例：xj.isDateTime(document.getElementById("bornDate").value);
	 */
	isDateTime : function(str)
	{
		if(str=="")
			return true;
		var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/; 
		var r = str.match(reg); 
		if(r==null) return false; 
		var d= new Date(r[1], r[3]-1,r[4],r[5],r[6],r[7]); 
		return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]&&d.getHours()==r[5]&&d.getMinutes()==r[6]&&d.getSeconds()==r[7]);
	},
	/**
	 *名　　称：isRegex
	 *功    能：判断是否符合传入的正则规则
	 *入口参数：objValue：需要计算的数据 regex正则表达式
	 *出口参数：格式匹配返回true,否则返回false
	 *示    例：xj.isRegex(document.getElementById("price").value,"^(-?\\d+)(\.\\d+)?$");
	 */
	isRegex : function(objValue,regex){
		if(objValue.match(regex)!=null) 
			return true;
		else
			return false;
	},
	/**
	 *名　　称：isEmail
	 *功    能：判断是否是email
	 *入口参数：objValue：需要判断的数据
	 *出口参数：格式匹配返回true,否则返回false
	 *示    例：xj.isEmail(document.getElementById("email").value);
	 */
	isEmail : function(objValue){
		if(objValue=="")
			return true;
		var regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\.\\w+([-.]\\w+)*";
		return this.isRegex(objValue,regex);
	},
	/**
	 *名　　称：isPhone
	 *功    能：判断是否是电话号码
	 *入口参数：objValue：需要判断的数据
	 *出口参数：格式匹配返回true,否则返回false
	 *示    例：xj.isPhone(document.getElementById("email").value);
	 */
	isPhone : function(objValue){
		if(objValue=="")
			return true;
		var regex = "(^(\\d{2,4}[-_－—]?)?\\d{3,8}([-_－—]?\\d{3,8})?([-_－—]?\\d{1,7})?$)|(^0?1[35]\\d{9}$)";
		return this.isRegex(objValue,regex);
	},
	/**
	 *名　　称：isMax
	 *功    能：判断所输入的值不能超过max值的限制
	 *入口参数：objValue：需要判断的数据，maxValue，带有类型标识的值，msg控件显示消息
	 *出口参数：不大于返回true,否则返回false
	 *示   例1：xj.isMax(document.getElementById("name").value,"String:20","用户名");
	 *示   例2：xj.isMax(document.getElementById("price").value,"number:100","用户名");
	 */
	isMax : function(objValue,maxStr,msg,div_id){
		var str = maxStr.split(":")
		if(str.length!=2)
		{
			//alert("验证标识输入不合法，示例(字符型String:20或数值型number:100)");
			div_id.style.display="";
			div_id.innerHTML=msg+"验证标识输入不合法，示例(字符型String:20或数值型number:100)";
			return false;
		}
		if(str[0]=="String")
		{
			return this.greaterLength(objValue,msg,str[1],div_id);
		}
		else if(str[0]=="number")
		{
			if(parseFloat(objValue)>parseFloat(str[1]))
			{
				//alert(msg+"不能大于最大值"+str[1]);
				div_id.style.display="";
				div_id.innerHTML=msg+"不能大于最大值"+str[1];
				return false;
			}
		}
		else
		{
			//alert(msg+",不合法的验证规则");
			div_id.style.display="";
			div_id.innerHTML=msg+"不合法的验证规则!";
			return false;
		}
		return true;
	},
	/**
	 *名　　称：isMax
	 *功    能：判断所输入的值不能超过max值的限制
	 *入口参数：objValue：需要判断的数据，maxValue，带有类型标识的值，msg控件显示消息
	 *出口参数：不大于返回true,否则返回false
	 *示   例1：xj.isMax(document.getElementById("name").value,"String:20","用户名");
	 *示   例2：xj.isMax(document.getElementById("price").value,"number:100","用户名");
	 */
	isMin : function(objValue,maxStr,msg,div_id){
		var str = maxStr.split(":")
		if(str.length!=2)
		{
			//alert("验证标识输入不合法，示例(字符型String:20或数值型number:100)");
			div_id.style.display="";
			div_id.innerHTML=msg+"验证标识输入不合法，示例(字符型String:20或数值型number:100)";
			return false;
		}
		if(str[0]=="String")
		{
			return this.lessThanLength(objValue,msg,str[1],div_id);
		}
		else if(str[0]=="number")
		{
			if(parseFloat(objValue)<parseFloat(str[1]))
			{
				//alert(msg+"不能小于最小值"+str[1]);
				div_id.style.display="";
				div_id.innerHTML=msg+"不能小于最小值"+str[1];
				return false;
			}
		}
		else
		{
			//alert(msg+",不合法的验证规则");
			div_id.style.display="";
			div_id.innerHTML=msg+"不合法的验证规则!";
			return false;
		}
		return true;
	},
	/**
	*创建消息显示Div
	*/
	createNoticeDiv : function(obj)
	{
		var did = xj.getTimeStamp();
		var oDiv = document.createElement('div');
	    oDiv.id = "div_"+did;
	    oDiv.className = "XJNoticeDiv";
	    oDiv.style.display="none";
	    obj.parentNode.appendChild(oDiv);
	    return document.getElementById("div_"+did);
	},
	/**
	*创建消息显示Div，并进行显示
	*/
	showNoticeDiv : function(obj,msg,ifReplace)
	{
		if(ifReplace==true)
			xj.replaceAllNoticeDiv();
		var div_id = xj.createNoticeDiv(obj);
		div_id.style.display="";
		div_id.innerHTML=msg;
	},
	replaceAllNoticeDiv : function()
	{
		var i;
		var s = document.getElementsByTagName("div");
		//循环进行验证，每循环一次验证一个或一组对象
		for (i=s.length-1; i >=0 ; i--)
		{
			
				if(s[i].className=='XJNoticeDiv')
				{
					s[i].parentNode.removeChild(s[i]);
				}
					
		}	
	},
	//时间随机数
	getTimeStamp : function()
	{
	    // 声明变量。
	    var d, s;
	
	    // 创建 Date 对象。
	    d = new Date();
	    s = ("0"+d.getHours()).slice(-2) + "";
	    s += ("0"+d.getMinutes()).slice(-2) + "";
	    s += ("0"+d.getSeconds()).slice(-2) + "";
	    s += ("00"+d.getMilliseconds()).slice(-3);
		var tmp = parseInt(Math.random()*1000000+1);
	    return s+tmp;
	}
}
	/**
	 *名　　称：trim
	 *功    能：去除空格
	 *出口参数：返回去除空格后的结果
	 *示    例：str.trim();
	 */
String.prototype.trim=function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");
}
	/**
	 *名　　称：ltrim
	 *功    能：去除左侧空格
	 *出口参数：返回去除空格后的结果
	 *示    例：str.ltrim();
	 */
String.prototype.ltrim=function(){
	return this.replace(/(^\s*)/g,"");
}
	/**
	 *名　　称：rtrim
	 *功    能：去除右侧空格
	 *出口参数：返回去除空格后的结果
	 *示    例：str.rtrim();
	 */
String.prototype.rtrim=function(){
	return this.replace(/(\s*$)/g,"");
}

//获取表单指定名称元素
function getElementFromDocumentOrForm(obj,name){
	var formTmp=null;
		if(obj!=null){
			formTmp  = obj.elements[name];
			if((formTmp==null) || typeof(formTmp)=="undefined" || (formTmp.length==0)){
				fileObj = new Array();
			}else if(typeof(formTmp.length)=="undefined"){
				fileObj = new Array(formTmp);
			}else if(formTmp.length>0){
				if(typeof(formTmp.type)=="undefined"){
					fileObj = formTmp;
				}else{
					fileObj = new Array(formTmp);
				}
			}else{
				fileObj = new Array();
			}
		}else
			fileObj  = document.getElementsByName(name);
	return fileObj;
		
}
