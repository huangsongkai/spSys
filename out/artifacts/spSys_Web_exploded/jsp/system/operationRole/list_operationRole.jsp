<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../../common/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block; margin-left:5px}
</style>
<div class="pageHeader">
<form id="pagerForm" method="post"  action="listOperationRole.do" onsubmit="return navTabSearch(this);">
	<!-- <div class="searchBar">
		<table  class="searchContent" >
					<tr>
						<td>
							用户ID: <input name="userId" type="text" id="userId" value="${userId}"/>
				    			   <input type="hidden" name="funcGoupId" value="${roleId}"/>
						</td>
						<td>
							用户姓名:<input name="userName" type="text" id="userlist.userName" value="${userName}"/>
						</td>
					</tr>

				</table>
	
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
				<li><a class="button" href="demo_page6.html" target="dialog" mask="true" title="查询框"><span>高级检索</span></a></li>
			</ul>
		</div>
	   </div> -->
	</form>
</div>
<div class="pageContent" >
	<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:;"><span>部门管理</span></a></li>
					<!-- <li><a href="javascript:;"><span>部门详细信息</span></a></li> -->
				</ul>
			</div>
		</div>
		<div class="tabsContent">
			<div>

		<div layoutH="50" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
			   <ul class="tree treeFolder">
					<li><a href="javascript">部门名称</a>
						<ul>
							<c:forEach items="${datalist}" var="obj" varStatus="i">
							<li><a href="listUserOperationRole.do?roleId=${obj.roleId}"  target="ajax" rel="roleBox">${obj.roleName }</a></li>
							</c:forEach>
						</ul>
					</li>
			     </ul>
		</div>
				<div id="roleBox" class="unitBox" style="margin-left:246px;">
					<!--#include virtual="list1.html" -->
				</div>
			
</div>
</div>
	
	<!-- 
	<script language="JavaScript"> 
	       var tab = null;
            var accordion = null;
            var tree = null;
              $(function ()
            {
            	
                //布局
                $("#layout1").ligerLayout({ leftWidth: 400, height: '100%',heightDiff:-34,space:4, onHeightChanged: f_heightChanged ,isleftCollapse: true });

                var height = $(".l-layout-center").height();
                
               
                //Tab
               

                //创建可伸缩面板
                
                $("#accordion1").ligerAccordion({ height: height - 24, speed: null });
                

                $(".l-link").hover(function ()
                {
                    $(this).addClass("l-link-over");
                }, function ()
                {
                    $(this).removeClass("l-link-over");
                });
                //树
                
              
                accordion = $("#accordion1").ligerGetAccordionManager();
                tree = $("#tree1").ligerGetTreeManager();
                $("#tree1").append(accordion);
                $("#pageloading").hide();   
                                          
				if($("#conditions").val()=='handler'){
					$("#userTd").html('经手人');
				}
			    /*部门名称的跳转*/
			
            function f_heightChanged(options)
            {
                if (tab)
                    tab.addHeight(options.diff);
                if (accordion && options.middleHeight - 24 > 0)
                    accordion.setHeight(options.middleHeight - 24);
            }
            function f_addTab(tabid,text, url)
            { 
                tab.addTabItem({ tabid : tabid,text: text, url: url });
            }
           } );
            
              /*Dialog窗口*/
	       var id=0;
			$.ligerui.controls.Dialog.prototype._borderX = 2;
        	$.ligerui.controls.Dialog.prototype._borderY = 0;
	     function ModalDialog(url,width,height,title){	
		    url=url.trim();
	    	if(url.indexOf("?")>1){
	    		url +='&dialogNo='+id;
	    	}else{
	    		url +='?dialogNo='+id;
	    	}
	    	var m=$.ligerDialog.open({id:'dialog'+id, url:url,title:title,height:height,width:width,showMax: true, showToggle: true, showMin: true, isResize: true, modal: false });			
	     	id++;
	     	return m;
     	}
     
	     function ModalDialog_Close(id){
	    	var x=$.ligerui.get('dialog'+id);
	        x.close(); 	
	     }
	     function returnValue(id){
			ModalDialog_Close(id);
				window.location.href=window.location.href;
		}	
	  	
		//添加部门
		  function insOperationRole(){
			var m=ModalDialog('showOperationRole.do',800,300,'添加');
			m.max()
		}	
		
		//修改部门
		function updateOperationRole()
		{  
			if(xjCommon.varClkTr==null)
				alert("请选择要修改的部门信息");
			else
			{
				if($(xjCommon.varClkTr).attr("edit")!="false"){
				var m=ModalDialog('updateOperationRole.do?roleId='+$(xjCommon.varClkTr).attr("id"),800,300,'修改');
			 m.max()
			
				}else
					alert("所选部门为系统内定，不能修改！");
			}
		}
		//删除部门
		function delOperationRole()
		{
			if(xjCommon.varClkTr==null)
				alert("请选择要删除的部门信息");
			else
			{
				if($(xjCommon.varClkTr).attr("delete")!="false")
				{
					if(confirm("确定要删除该部门吗？"))
					{
							var a=ModalWindow("jump.do?path=delOperationRole.do&roleId="+$(xjCommon.varClkTr).attr("id"),640,480);
							if(a=="ok")
							{
									
								xjSpPage.spPageSubmit();
								
							}
						}
					}
				else
					alert("所选角色为系统内定，不能删除！");
			}
		}
		
		xjCommon.trClickUserFunc = function()
		{
		
		
			var roleId = $(xjCommon.varClkTr).attr("id");
			
			var handler='${param.handler}';
			if(handler=='1'){
				window.location.href="list_HandlerList.do?roleId="+roleId+"&pageNumber="+$("#pageNumber").val();			
			}else{
			window.location.href="listOperationRole.do?roleId="+roleId+"&pageNumber="+$("#pageNumber").val();
			
			}
		}
		
		$(function(){
			/*ligerUI工具条*/
			$("#listOperationRoleDiv1").ligerToolBar({items:[
			{text:'添加',click:insOperationRole,img:'../../../images/main/add.png'},
			{text:'修改',click:updateOperationRole,img:'../../../images/main/update.png'},
			{text:'删除',click:delOperationRole,img:'../../../images/main/delete.png'}
			
			]
			});
		
			/*ligerUI工具条*/
			$("#listOperationRoleDiv2").ligerToolBar({items:[
			{text:'查询',id:'queryButton',click:_queryDivBg,img:'../../../images/main/query.png'},
			{text:'添加用户',click:addUser,img:'../../../images/main/add.png'},
			{text:'删除用户',click:deleteUser,img:'../../../images/main/delete.png'}
			
			]
			})
			
			var roleId='${param.roleId}';
			xjCommon.TrClickNoEvent($("#"+roleId+"").get(0));
			
		});

      function addUser(){
       var userIds = '${userIds}';
      var roleId = $(xjCommon.varClkTr).attr("id");
       if(roleId!=null){
       
       var m=ModalDialog('listCommonUser.do?userIds='+userIds+'&roleId=${param.roleId}' ,800,300,'添加');
			m.max()
			}else{
			alert("请选择要添加的部门名称");
			
			}

}


	//为部门删除指定的对应用户
	function deleteUser()
	{   
		var users=document.getElementsByName("user1");
		var str = xjCommon.getValueStr("user1",",");
		if(str!=null && str.length>0){
			if(confirm("确定要删除该部门关联的用户吗！")){
				var a=ModalWindow('jump.do?path=delUserOperationRole.do&users='+str+'&roleId=${roleId}',800,600);   
				if(a=="ok"){
					window.location.href="listOperationRole.do?pageNumber="+$("#pageNumber").val()+"&roleId=${roleId}";
				}                      
			}
		}
		else
			alert("请选择要移除的用户!");
	}
</script>
 -->