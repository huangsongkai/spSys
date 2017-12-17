<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../../../common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>字典码维护</title>
<jsp:include page="../../../common/head.jsp">
	<jsp:param name="_useTree" value="true"/>
</jsp:include>
<script language="javaScript">
	var levelTal=${levelTal};
	var kindId='${kindId}';
	var kindName='${kindName}';
	var obj=null;
	function  clickEvent(nodeObj)
	{
		obj = nodeObj;
		showCode();
	}
	function  dblClickEvent(nodeObj)
	{
		obj = nodeObj;
		editCode();
	}
	//添加
	function insCode()
	{  		
		if(obj!=null)
		{
			var str=obj.id;
			var tmp=str.split("#");
			var codeId=tmp[0];
			var codeName=tmp[2];
			var level=codeId.length/3;
			if(level==1)
			{
				codeName=kindName;
			}
			if(level<levelTal && level>=1)
			{
				window.parent.frame_right.location.href="ins_CodeDict.do?pid="+codeId+"&codeName="+codeName+"&kindId="+kindId;
			}
			else if(obj.getAttribute("name")=="title")
			{
				window.parent.frame_right.location.href="ins_CodeKind.do?pid="+codeId+"&codeName="+codeName+"&kindId="+kindId;	
			}
			else if (level==levelTal)
			{
				alert("所选字典码大类层级限定为1级，不能向所选节点中添加子类！");
			}
		}
		else
		{
		    alert("请选择要增加字典信息的父节点！");
		}
	}
	function showCode()
	{
		var str=obj.getAttribute("id");
		var tmp=str.split("#");
		var codeId=tmp[0];
		var isDefalue=tmp[1];
		var level=codeId.length/3;
		
		str=tmp[0];
		if(obj.getAttribute("name")=="title")
		{
			window.parent.frame_right.location.href="list_CodeList.do";
		}
		else if (level==1)
		{
			var kindId=codeId;
			window.parent.frame_right.location.href="list_CodeList.do?kindId="+kindId;
		}
		else
		{
			window.parent.frame_right.location.href="show_CodeDict.do?codeId="+codeId;
		}
	}
	//edit
	function editCode()
	{
		var str=obj.getAttribute("id");
		var tmp=str.split("#");
		var codeId=tmp[0];
		var isDefalue=tmp[1];
		var level=codeId.length/3;
		
		str=tmp[0];
		if (level==1)
		{
			var kindId=codeId;
			window.parent.frame_right.location.href="upd_CodeKind.do?kindId="+kindId;
		}
		else
		{
			window.parent.frame_right.location.href="upd_CodeDict.do?codeId="+codeId;
		}
	}
	//删除
	function del(){
		var str=obj.id;
		if(str!=null)
		{
			var tmp=str.split("#");
			var codeId=tmp[0];
			var isDefalue=tmp[1];
			if(obj.getAttribute("name")=="title")
			{
				alert("字典码大类不能删除");
			}
			else
			{
				if(obj.getAttribute("nextlevel")=="true")
					alert("所选字典码含有下级项目不能删除！");
				else
				{
					if(isDefalue=="Y")
					{
						alert("所选字典码内容为系统内定，不能删除！");
					}
					else
					{
						if(confirm("确认删除？"))
						{
							var a = ModalWindow("jump.do?path=del_CodeDict.do&codeId="+codeId,800,600);    
							if(a=="ok")
								window.parent.frame_right.location.href="list_CodeList.do";
								window.location.reload();
						}
					}
				}
			}
		}
	}

</script>
	</head>
	<body>
	<div id="body_div">
	<w:ShowTitle name="字典码管理">
	<w:TitleButtons funcParentId="001001001"/>
	<w:TitleButton funcName="添加" onClickFunction="insCode()" urlImg="${ctx}/images/main/add.png"/>
	<w:TitleButton funcName="删除" onClickFunction="del()" urlImg="${ctx}/images/main/delete.png"/>
	</w:ShowTitle>
	<div id="body_content">
		<table width="100%">
			<tr>
				<td>
				<w:tree returnValue="codeId#isDefult#codeName#kindId" regex="#"
					displayValue="codeName" pidname="parentCodeId"
					content="${dataList}" idname="codeId" headValue="${kindId}"
					headDisplayValue="${kindName}" useDblClick="true"></w:tree>
				</td>
			</tr>
		</table>
	</div>
	</div>	
	</body>
</html>
