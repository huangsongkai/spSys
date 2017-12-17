<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../../../common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>功能菜单</title>
		<jsp:include page="../../../common/head.jsp">
			 <jsp:param name="_useTree" value="true"/>
		</jsp:include>
		<script>
	var obj=null;
	function  clickEvent(nodeObj)
	{
		obj = nodeObj;
		var str=obj.getAttribute("id");
		var tmp=str.split("#");
		var funcId=tmp[0];
		var funcName = tmp[2];
		if(obj.getAttribute("name")!="title")
		{
			updFuncInfo(funcId);
		}
	}


	function insFuncInfo()
	{  		
		if(obj!=null)
		{
			var str=obj.id;

			var tmp=str.split("#");
			var funcId = tmp[0];
			var funcName = tmp[2];
			
			if(obj.getAttribute("name")=="title")
			{
				funcName="功能菜单";
			}
			window.location.href="addFuncInfo.do?parentId="+funcId+"&funcName="+funcName;
		}
		else
		{
		    alert("请选择要增加功能菜单的父节点！");
		}
	}
	function updFuncInfo(funcId)
	{
		window.parent.frame_right.location.href="updateFuncInfo.do?funcId="+funcId;
	}
	
	function delFuncInfo(){
		if(obj!=null)
		{
			var str=obj.getAttribute("id");
			var tmp=str.split("#");
			var funcId=tmp[0];
			if(obj.getAttribute("name")=="title")
			{
				alert("首级菜单标题不能删除");
			}
			else
			{
				if(obj.getAttribute("nextlevel")=="true")
					alert("所选功能菜单含有下级功能菜单不能删除！");
				else
				{
					if(confirm("确认删除？"))
					{
						var a=ModalWindow("jump.do?path=delFuncInfo.do&funcId="+funcId,800,600);
						if(a=="ok"){
							window.parent.frame_right.location.href="${ctx}/blank_common.jsp?title=菜单管理";
							window.location.reload();
						}	
					}
				}
			}
		}
		else
		{
		    alert("请选择要删除的功能菜单！");
		}
	}
</script>
	</head>
	<body>
		<div id="body_div">
	<w:ShowTitle name="功能菜单">
	<w:TitleButtons funcParentId="001001002"/>
	<w:TitleButton funcName="添加" onClickFunction="insFuncInfo()" urlImg="${ctx}/images/main/add.png"/>
	<w:TitleButton funcName="删除" onClickFunction="delFuncInfo()" urlImg="${ctx}/images/main/delete.png"/>
	</w:ShowTitle>
	<div id="body_content" style="width:100%;">
<table width="100%">
           <tr>
             <td>

      <w:tree returnValue="funcId#funcType#funcName" regex="#"
      displayValue="funcName" pidname="funcParentId" 
      content="${datalist}" idname="funcId" headValue="0" 
      headDisplayValue="功能菜单"></w:tree>
			</td>
	</tr>
       </table>
       </div>
	</div>	
	</body>
</html>
