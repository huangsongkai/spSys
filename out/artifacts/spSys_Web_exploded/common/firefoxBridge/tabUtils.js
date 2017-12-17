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

var tabUtils = new Object();

tabUtils.switchTab = function(url, tabName){
	if (XULUtils.isXUL()) {
		var element = document.createElement("MyExtensionDataElement");
		element.setAttribute("url", url);
		element.setAttribute("tabname", tabName);
		document.documentElement.appendChild(element);
		var evt = document.createEvent("Events");
		evt.initEvent("cmsSwitchTab", true, false);
		element.dispatchEvent(evt);
		return true;
	}
	return false;
};

tabUtils.sendEventToTab = function(tabName, args){
	args.tabName = tabName;
	args.elementId = XULUtils.catcherId;
	// expect args.eventName (where the target tab will catch this event)
	
	var element = document.createElement("MyExtensionDataElement");
	for(var k in args){
		element.setAttribute(k, args[k]);
	}
	document.documentElement.appendChild(element);
	var evt = document.createEvent("Events");
	evt.initEvent("cmsSendEventToTab", true, false);
	element.dispatchEvent(evt);
}