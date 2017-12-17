<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="common/include.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/jquery/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/css/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/css/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/common/jquery/jquery.easyui.min.js"></script>
<script src="${pageContext.request.contextPath}/common/js/jquery.portal.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/common/js/portal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" charset="utf-8">
	var portal;
	var panels;
	$(function() {
		
		panels = [ {
			id : 'p1',
			title : '短信通知',
			height : 200,
			collapsible : true,
			href:''
		}, {
			id : 'p2',
			title : '代码生成器说明',
			height : 200,
			collapsible : true,
			href:''
		}, {
			id : 'p3',
			title : '环境初始化',
			height : 200,
			collapsible : true,
			href:''
		}, {
			id : 'p4',
			title : '架构优势',
			height : 200,
			collapsible : true,
			href:''
		}, {
			id : 'p5',
			title : '查询条件生成器说明',
			height : 200,
			collapsible : true,
			href:''
		} , {
			id : 'p6',
			title : '联系方式',
			height : 200,
			collapsible : true,
			href:''
		} ];

		portal = $('#portal').portal({
			border : false,
			fit : true,
			onStateChange : function() {
				
			}
		});
		var state = 'p1,p2,p3:p4,p5,p6';/*冒号代表列，逗号代表行*/
		addPanels(state);
		portal.portal('resize');

	});

	function getPanelOptions(id) {
		for ( var i = 0; i < panels.length; i++) {
			if (panels[i].id == id) {
				return panels[i];
			}
		}
		return undefined;
	}
	function addPanels(portalState) {
		var columns = portalState.split(':');
		for (var columnIndex = 0; columnIndex < columns.length; columnIndex++) {
			var cc = columns[columnIndex].split(',');
			for (var j = 0; j < cc.length; j++) {
				var options = getPanelOptions(cc[j]);
				if (options) {
					var p = $('<div/>').attr('id', options.id).appendTo('body');
					p.panel(options);
					portal.portal('add', {
						panel : p,
						columnIndex : columnIndex
					});
				}
			}
		}
	}
</script>
<div id="portal" style="position:relative">
	<div></div>
	<div></div>
</div>