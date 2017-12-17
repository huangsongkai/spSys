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

var mifareUtils = {
	el: null,
	id: "mifareEl",
	callback: null,
	readStartTs: null,
	lastPollTs: null,
	activationLimit: 10,	// seconds that the program will read from the reader
	pollPeriod: 100,		// period in milliseconds to poll the reader 
	
	// private
	init: function(){
		if(!this.el){
			var element = document.createElement("MyMifareDataElement");
			element.setAttribute("id", this.id);
			element.id = this.id;
			document.documentElement.appendChild(element);
			
			window.addEventListener("sendmifaresnrevent", function(event){
				return mifareUtils.dispatchEvent.call(mifareUtils, event);
			}, false);
			
			this.el = element;
		}
	},
	
	// public
	readSNR: function(callback){
		if (XULUtils.isXUL() && !this.readStartTs){
			this.callback = callback;
			this.readStartTs = this.getTimestamp();
			
			this.activateReader();
			
			return true;
		}
		return false;
	},
	
	// private
	activateReader: function(){
		this.lastPollTs = this.getTimestamp();
		
		var element = document.createElement("MyExtensionDataElement");
		element.setAttribute("elementId", this.id);
		document.documentElement.appendChild(element);
		var evt = document.createEvent("Events");
		evt.initEvent("cmsGetMifareSNR", true, false);
		element.dispatchEvent(evt);
	},
	
	// private
	dispatchEvent: function(event){
		if(!this.callback) return false;
		
		var elm = event.target;
		 
		if (elm){
			var snr_value = elm.getAttribute("snr_value");
			
			if(snr_value == "CCCCCCCC"){
				var now = this.getTimestamp();
				
				if(now - this.readStartTs > this.activationLimit * 1000){
					alert("Please place a card on the card reader");
					this.reset();
					return;
				}
				
				var elapsed = now - this.lastPollTs;
				
				if(this.pollPeriod - elapsed < 0){
					this.activateReader();
				}else{
					var self = this;
					setTimeout(function(){
						self.activateReader();
					}, this.pollPeriod - elapsed)
				}
				
				return;
			}else{
				this.callback(snr_value);
				
				this.reset();
				if (elm.hasAttribute("snr_value"))
					elm.removeAttribute("snr_value");
			}
		}
	},
	
	reset: function(){
		this.callback = null;
		this.readStartTs = null;
	},
	
	getTimestamp: function(){
		return (new Date()).getTime();
	}
};

mifareUtils.init();