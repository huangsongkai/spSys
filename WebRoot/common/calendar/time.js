var str = ""; 
document.writeln(" <div id=\"_contents\" style=\"padding:6px; background-color:#808080; font-size: 12px; border: 1px solid #777777;  position:absolute; left:?px; top:?px; width:?px; height:?px; z-index:1; visibility:hidden\">"); 
str += "\u65f6 <select name=\"_hour\" id=\"_hour\">"; 
for (h = 0; h <= 9; h++) { 
    str += " <option value=\"0" + h + "\">0" + h + " </option>"; 
} 
for (h = 10; h <= 23; h++) { 
    str += " <option value=\"" + h + "\">" + h + " </option>"; 
} 
str += " </select> \u5206 <select name=\"_minute\"  id=\"_minute\">"; 
for (m = 0; m <= 9; m++) { 
    str += " <option value=\"0" + m + "\">0" + m + " </option>"; 
} 
for (m = 10; m <= 59; m++) { 
    str += " <option value=\"" + m + "\">" + m + " </option>"; 
} 

str += " </select> <input name=\"queding\" type=\"button\" onclick=\"_select()\" value=\"ȷ��\" style=\"font-size:12px\" /> </div>"; 
document.writeln(str); 
var _fieldname; 
function setTime(tt) { 
debugger
    _fieldname = tt; 
    var ttop = tt.offsetTop;    //TT�ؼ��Ķ�λ��� 
    var thei = tt.clientHeight;    //TT�ؼ�����ĸ� 
    var tleft = tt.offsetLeft;    //TT�ؼ��Ķ�λ��� 
    while (tt = tt.offsetParent) { 
        ttop += tt.offsetTop; 
        tleft += tt.offsetLeft; 
    } 
    document.all._contents.style.top = ttop + thei + 4; 
    document.all._contents.style.left = tleft; 
    document.all._contents.style.visibility = "visible"; 
    var _value=_fieldname.value;
    if(_value!=''){
    	var valSpl=_value.split(":");
    	var h=document.getElementById("_hour");
    	for(var i=0;i<h.options.length;i++ ){
    		if(h.options[i].value==valSpl[0]) 
    		  	h.options[i].selected = true;
    	}
    	var m=document.getElementById("_minute");
    	for(var i=0;i<m.options.length;i++){
    		if(m.options[i].value==valSpl[1]) 
    			m.options[i].selected = true;
    	}
    }
} 
function _select() { 
    _fieldname.value = document.all._hour.value + ":" + document.all._minute.value; 
    document.all._contents.style.visibility = "hidden"; 
} 




