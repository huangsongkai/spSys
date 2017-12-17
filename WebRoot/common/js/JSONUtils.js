/**
 * @require "json.js"
 * @include "json.js"
 */
 
//if (!this.JSONUtils) {
//    JSONUtils = {};
//}

var JSONUtils = {
	version : '0.1',
	bean : null,
	beanFields : null,
	or : null,
	getQueryBean : function(formID,beanFieldConfig){
		var frm  = document.forms[formID];
		var coll = frm.elements;
		var collItem;

		bean = new Bean();
		beanFields = new Array();//
		or = new Array();//
		var orders = [];//
		var beanConfig = new BeanConfig();
		
		var beanField;
		var orField;
		var beanAtts;
		var beanFiledAtts;
		var name;
		var type;
		for(i=0; i<coll.length; i++){
			collItem = coll.item(i);
			
			if( JSONUtils.isXNull( collItem.attributes['beanName']) ){
				
				bean.beanName = collItem.attributes["beanName"].value ;
				beanField = new BeanField()
				
				beanAtts = beanConfig.attributs;
				beanFiledAtts = beanFieldConfig.attributs;
				
				if(JSONUtils.isXNull(collItem.value)){
					for(var j = 0; j< beanFiledAtts.length; j++){
						
						try{
							if( JSONUtils.isXNull(collItem.attributes[beanFiledAtts[j]]) ){
								eval('beanField.'+beanFiledAtts[j] +' = collItem.attributes["' + beanFiledAtts[j] + '"].value') ;
							}
						}catch(e){
							//alert(e+'属性装配错误');
							continue;
						}
					}
					name = collItem.name;
					type = collItem.type;
					if('radio' == type){
						beanField.value = JSONUtils.getRadioValue(name);
					}else{
						beanField.value = collItem.value;
					}
					beanFields.push( beanField );
	//				alert(beanField.fieldName);
					if( JSONUtils.isXNull( collItem.attributes['or']) ){
						or.push(beanField.fieldName);
						
					}
				}
				if( JSONUtils.isXNull( collItem.attributes['order']) ){
					var order = {};//
					order.fieldName = collItem.attributes['fieldName'].value;
					order.orderSeq = collItem.attributes['orderSeq'].value;
					order.order = collItem.attributes['order'].value;
					orders.push(order);
					
				}
				
			
			}
		
		}
		bean.beanFields = beanFields;
		bean.orders = orders;
		bean.or = or;
		
		return bean;
	},
	getInsertBean : function(formID){
		var frm  = document.forms[formID];
		var coll = frm.elements;
		var collItem;

		var fieldName;
		var value;
		var name;
		var type;
		var dataType = "String"
		bean = new Bean();
		for(i=0; i<coll.length; i++){
			collItem = coll.item(i);
			if( JSONUtils.isXNull( collItem.attributes['fieldName']) && JSONUtils.isXNull( collItem.value) ){
			//alert(collItem.attributes['fieldName'].value);
				
				try{
							dataType = JSONUtils.isNull(collItem.attributes["dataType"].value)? "String":collItem.attributes['dataType'].value ;
						}catch(e){
							//alert(e+'属性装配错误');
							//continue;
						}
						
				fieldName = collItem.attributes["fieldName"].value ;
				name = collItem.name;
				type = collItem.type;
				if('radio' == type){
					value = JSONUtils.getRadioValue(name);
				}else{
					value = collItem.value ;
				}
				//eval('bean.'+fieldName+'="'+value+'"');  如果有特殊字符出错
				if(dataType=="String")
					eval('bean.'+fieldName+'=value');
				else if(dataType=="Integer")
					eval('bean.'+fieldName+'=parseInt(value)');
				else if(dataType=="Double")
				{
//					alert(value);
					if(value.indexOf(".")==-1)
					{
						value = parseFloat(value).toFixed(1)
					}
					else
						value=parseFloat(value);
					eval('bean.'+fieldName+'=value');
				}
				
//				alert(dataType);
			}
		
		}
		return bean;
	},
	getInsertBeanWithSet : function(formID,beanName){
		var frm  = document.forms[formID];
		var coll = frm.elements;
		var collItem;

		var fieldName;
		var value;
		var name;
		var type;
		
		var dataType = "String"
		bean = new Bean();
		//迭代表单内所有对象元素
		for(i=0; i<coll.length; i++){
			collItem = coll.item(i);
			//封装包含fieldName属性并且value不为空的对象
			if( JSONUtils.isXNull( collItem.attributes['fieldName']) && JSONUtils.isXNull( collItem.value)){
			//alert(collItem.attributes['fieldName'].value);
				//组织主表
				if(collItem.attributes["beanName"].value==beanName)
				{
					fieldName = collItem.attributes["fieldName"].value ;
					name = collItem.name;
					type = collItem.type;
					if('radio' == type){
						value = JSONUtils.getRadioValue(name);
					}else{
						value = collItem.value ;
					}
					eval('bean.'+fieldName+'=value');
				}
			}
		}
		var setObj = document.getElementsByName("setName");//作为set集合的对象
		for(var scnt=0;scnt<setObj.length;scnt++)
		{
			var subCount=0;		//从表对象数量
			var setName = setObj[scnt].value;
			var setArr = new Array();
			var strss="";
			strss+="setName="+setName+"--\n";
			for(i=0; i<coll.length; i++){
				collItem = coll.item(i);
				try{
					
					if(subCount<parseInt(collItem.attributes["seq"].value)&& collItem.attributes['setName'].value==setName)
					{
						subCount=parseInt(collItem.attributes["seq"].value);
					}
				}catch(e){
				}
			}
//			alert(subCount);
			for(var cnt=0;cnt<=subCount;cnt++)
			{
				var subBean = new Bean();
				var isEmptyBean = true;//子对象是否不包含任何属性
				for(i=0; i<coll.length; i++){
					collItem = coll.item(i);
					try{
						//集合对象并且值不为空,包含setName,并且与指定的setName相同
						if( JSONUtils.isXNull( collItem.attributes['setName']) 
						&& JSONUtils.isXNull( collItem.value) 
						&& cnt==parseInt(collItem.attributes["seq"].value)
						&& collItem.attributes['setName'].value==setName){
							fieldName = collItem.attributes["fieldName"].value ;
							name = collItem.name;
							type = collItem.type;
							if('radio' == type){
								value = JSONUtils.getRadioValue(name);
							}else if('checkbox' == type){
								value = JSONUtils.getSingleCheckBoxValue(collItem);
							}else{
								value = collItem.value ;
							}
							eval('subBean.'+fieldName+'=value');
							if(value!=null)//当属性为复选或单选框时，不被选中但值会存在
								isEmptyBean = false;
							
						}
					}catch(e){
					}
					
				}
				
				if(!isEmptyBean)
					setArr.push(subBean)
			}
			
			eval('bean.'+setName+'=setArr');
		}
		
		return bean;
	},
	/**组织mapping bean 的json String*/
	getMappingStr : function(formID,beanName){
		var sets=document.getElementsByName("setName");
		bean = new Bean();
		var mappingBeanArr = new Array();
		for(var i=0;i<sets.length;i++)
		{
			var obj = new Bean();
			var e=sets[i];
			obj.setName=e.value;
			obj.mappingBeanName=e.attributes['mappingBean'].value;
			mappingBeanArr.push(obj);
		}
		bean.mappingBean=mappingBeanArr;
		bean.beanName=beanName;
		return bean;
	},
	/**是否非空*/
	isXNull : function(obj){
		return !JSONUtils.isNull(obj);
	},
	/**是否为空*/
	isNull : function(obj){
		if(obj == undefined || obj == null || obj.length == 0){
			return true;
		}else{
			return false;
		}
	},
	getRadioValue : function(item){
	    var a = document.getElementsByName(item);
	    for (var i=0; i<a.length; i++){
	      if (a[i].checked){ 
	         return a[i].value; 
	      }
	    }
	},
	getSingleCheckBoxValue : function(item){
      if (item.checked){
        return item.value; 
      }
	},
	toStr : function(obj){
		var str  = $.json.encode(bean);
		return str;
	},
	getObjectById : function(objId){
		try{
			var obj= $("#"+objId);
			if(obj.length==0)
				alert("未找到对象"+objId);
			else
				return obj[0];
		}catch(e){
			alert("未找到对象"+objId);
		}
	},
	queryBean : function(formID,hiddenInputID,beanFieldConfig){
		if( JSONUtils.isNull(beanFieldConfig) ){
			beanFieldConfig = new BeanFieldConfig( ['fieldName','dataType','role','suffix']);
		}else{
			beanFieldConfig = new BeanFieldConfig( beanFieldConfig);
			
		}
		var bean = JSONUtils.getQueryBean(formID,beanFieldConfig);
		JSONUtils.getObjectById(hiddenInputID).value = JSONUtils.toStr(bean);
	},
	insertBean : function(formID,hiddenInputID){
		var bean = JSONUtils.getInsertBean(formID);
		var str  = JSONUtils.toStr(bean);
		JSONUtils.getObjectById(hiddenInputID).value = str;
		
	},
	insertBeanWithSet : function(formID,hiddenInputID,beanName,mapContent){
		var bean = JSONUtils.getInsertBeanWithSet(formID,JSONUtils.getObjectById("beanName").value);
		var mappingBean = JSONUtils.getMappingStr(formID,JSONUtils.getObjectById("beanName").value);

		JSONUtils.getObjectById(hiddenInputID).value = JSONUtils.toStr(bean);;
		JSONUtils.getObjectById(mapContent).value = JSONUtils.toStr(mappingBean);
	}

}

var Bean = function(){
};
var BeanField = function(){
};
var BeanConfig = function(){
	this.attributs = ['beanName','beanFields','or'];
};
var BeanFieldConfig = function(str){
	if(JSONUtils.isNull(str)){
		this.attributs = ['fieldName','dataType','role','value'];
	}else{
		this.attributs = str;
	}
};

