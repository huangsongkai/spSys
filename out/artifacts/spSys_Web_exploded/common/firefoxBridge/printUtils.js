/* ------------------------------------------------------------
MTG RCL (Rich Client Library)
Copyright (c) 2004-2009 Mobigator Technology Group

The source code within this file is released by Mobigator Technology Group as a free software:
you can redistribute it and/or modify it UNDER THE TERMS of the
GNU General Public License version 2 as published by the Free Software Foundation.

This file is distributed WITHOUT ANY WARRANTY.
Please See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License version 2
along with the file.  If not, see <http://www.gnu.org/licenses/>.

For more information, please see:
http://www.mobigator.com
------------------------------------------------------------ */

var printUtils = new Object();

printUtils.printContent = function(content, docType, settings) {
	//if(!settings)settings = {}
	//settings = Ext.util.JSON.encode(settings);
	debugger
	settings="{}";
	return printUtils._printContent(content, docType, settings, "cmsPrintEvent");
}

printUtils.printPreviewContent = function(content, docType, settings){
	if(!settings)settings = {}
	//settings = Ext.util.JSON.encode(settings);
	settings="{}";
	return printUtils._printContent(content, docType, settings, "cmsPrintPreviewContent");
}

printUtils.printPreviewWindow = function(content, docType, settings){
	if(!settings)settings = {}
	//settings = Ext.util.JSON.encode(settings);
	settings="{}";
	return printUtils._printContent(content, docType, settings, "cmsPrintPreviewWindow");
}

printUtils.printWindow = function(settings){
	if (XULUtils.isXUL()) {
		if(settings){
			var element = document.createElement("MyPrintSetting");
			element.id = "MyPrintSetting";
			element.setAttribute("id", "MyPrintSetting");
			element.setAttribute("printSettings", Ext.encode(settings));
			document.documentElement.appendChild(element);
		}
	
		var evt = document.createEvent("Events");
		evt.initEvent("cmsPrintWindow", true, false);
		window.dispatchEvent(evt);
		return true;
	}
	return false;
}

printUtils._printContent = function(content, docType, settings, event){
	alert(XULUtils.isXUL());
	if (XULUtils.isXUL()){
		printUtils.getPageStyleSheet();
		var element = document.createElement("MyExtensionDataElement");
		element.setAttribute("content", printUtils.getPageStyleSheetLink() + content);
		element.setAttribute("docType", docType);
		element.setAttribute("settings", settings);
		document.documentElement.appendChild(element);
		var evt = document.createEvent("Events");
		evt.initEvent(event, true, false);
		element.dispatchEvent(evt);
		return true;
	}
	return false;
}

printUtils.getPageStyleSheetLink = function(){
	var editorCss = "/proxy/tinymce/js/ext-2.2/tiny_mce/themes/advanced/skins/default/content.css";
	var fullURL = window.location.protocol + "//" + window.location.host + "/database/common/css/mainStyle-12px.css";
	//window.location.protocol + "//" + window.location.host + SysConfig.createLink(editorCss);
	return '<link rel="stylesheet" type="text/css" media="all" href="' + fullURL + '" />';
}

printUtils.getPageStyleSheet = function(){
	var result = '';
	var elems, elemsRules;
	var sheetText = '';
	for (var i=0; i<document.styleSheets.length; i++){
		elems = document.styleSheets[i];
		elemsRules = elems.cssRules ? elems.cssRules : elems.rules;
		if( elems.cssText ) {	//IE
			sheetText = elems.cssText;
		} else {				//Firefox
			for(var j=0; j< elemsRules.length; j++){
				var cssText = elemsRules[j].cssText;
				if (cssText.include){
					if (!cssText.include(".x-") && !cssText.include(".ext-")){
					sheetText += cssText;
					}
				}
			}
		}
	}
	result = "<style media='all'>" + sheetText + "</style>";
	return result;
}

printUtils.pageSetup = function() {
	XULUtils.fireSimpleEvent("cmsPageSetup");
}

printUtils.printerSetup = function(){
	XULUtils.fireSimpleEvent("cmsPrinterSetup");
}