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

var bugUtils = new Object();

bugUtils.bugReport = function() {
	XULUtils.fireSimpleEvent("cmsBugReport");
}