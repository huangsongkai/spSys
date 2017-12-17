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

var XULUtils = {
	el: null,
	catcherId: "xulEventCatcher",
	
	listeners: {},
	
	init: function(){
		if(!this.el){
			var element = document.createElement("MyEventCatcherElement");
			element.setAttribute("id", this.catcherId);
			element.id = this.catcherId;
			document.documentElement.appendChild(element);
			
			window.addEventListener("xulevent", function(event){
				return XULUtils.dispatchEvent(event);
			}, false);
			
			this.el = element;
		}
	},
	
	onEvent: function(eventName, func){
		if(!this.listeners[eventName]){
			this.listeners[eventName] = [];
		}
		
		this.listeners[eventName].push(func);
	},
	
	dispatchEvent: function(event){
		var elm = event.target;
		
		if (elm){
			var attrs = elm.attributes;
			var args = {};
			for(var i=0; i<attrs.length; ++i){
				var attr = attrs[i];
				args[attr.nodeName] = attr.nodeValue;	// nodeName is in small case!!
			}
			for(var nName in args){
				if(nName == "id") continue;
				elm.removeAttribute(nName);
			}
			
			var eventName = args.eventname;
			
			var listeners = this.listeners[eventName];
			for(var i=0; i<listeners.length; ++i){
				listeners[i](args);
			}
		}
	},
	
	_isXUL: null,

	isXUL: function(){
	debugger
		if(typeof XULUtils._isXUL != "boolean"){
			var index = -1, firefoxIndex = -1;
			
			if (navigator.userAgent){
				index = navigator.userAgent.indexOf("CMS/3.0");
				firefoxIndex = navigator.userAgent.indexOf("Firefox");
			}
			if ((index != -1) && (firefoxIndex == -1) && ("createEvent" in document)){
				XULUtils._isXUL = true;
			}else{
				XULUtils._isXUL = false;
			}
		}
		
		return XULUtils._isXUL;
	},
	
	refresh: function(){
		return XULUtils.fireSimpleEvent("cmsRefresh");
	},
	
	fireSimpleEvent: function(eventName){
		if (XULUtils.isXUL()){
			var element = document.createElement("MyExtensionDataElement");
			document.documentElement.appendChild(element);
			var evt = document.createEvent("Events");
			evt.initEvent(eventName, true, false);
			element.dispatchEvent(evt);
			return true;
		}
		return false;
	},
	
	startIdleObserver: function(duration){
		if (XULUtils.isXUL()){
			var element = document.createElement("MyExtensionDataElement");
			document.documentElement.appendChild(element);
			element.setAttribute("duration", duration);
			var evt = document.createEvent("Events");
			evt.initEvent("cmsStartIdleObserver", true, false);
			element.dispatchEvent(evt);
			return true;
		}
		return false;
	},
	
	stopIdleObserver: function(duration){
		if (XULUtils.isXUL()){
			var element = document.createElement("MyExtensionDataElement");
			document.documentElement.appendChild(element);
			element.setAttribute("duration", duration);
			var evt = document.createEvent("Events");
			evt.initEvent("cmsStopIdleObserver", true, false);
			element.dispatchEvent(evt);
			return true;
		}
		return false;
	},
	
	launchDefaultBrowser: function(url){
		if (XULUtils.isXUL()){
			var element = document.createElement("MyExtensionDataElement");
			document.documentElement.appendChild(element);
			element.setAttribute("url", url);
			var evt = document.createEvent("Events");
			evt.initEvent("cmsOpenURL", true, false);
			element.dispatchEvent(evt);
			return true;
		}
		return false;
	},
	
	launchScannerImport: function(sessionId, patientNo, patientName, url){
		if (XULUtils.isXUL()){
			var element = document.createElement("MyExtensionDataElement");
			document.documentElement.appendChild(element);
			element.setAttribute("sessionId", sessionId);
			element.setAttribute("patientNo", patientNo);
			element.setAttribute("patientName", patientName);
			element.setAttribute("uploadURL", url);
			var evt = document.createEvent("Events");
			evt.initEvent("cmsScannerImport", true, false);
			element.dispatchEvent(evt);
			return true;
		}
		return false;
	}
};

XULUtils.init();
