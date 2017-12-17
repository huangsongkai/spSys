<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../../../common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<jsp:include page="../../../common/head.jsp">
			 <jsp:param name="_useModal" value="true"/>
		</jsp:include>
<script language="javascript">
	function saveinfo()
	{
		var objarr = new Array(
		["funcInfo.funcName","功能菜单名称","notEmpty",""],
		["funcInfo.funcName","功能菜单名称","max","String:100"],
		["funcInfo.funcType","菜单级别（类型）","notEmpty",""],
		["funcInfo.funcType","菜单级别（类型）","max","String:1"],
		["funcInfo.urlImg","链接urlImg","max","String:300"],
		//["funcInfo.urlImgMini","链接urlImgMini","max","String:300"],
		["funcInfo.url","链接url","max","String:500"],
		["funcInfo.onclickFunction","点击javaScript事件","max","String:100"],
		["funcInfo.orderCol","功能点排序","notEmpty",""],
		["funcInfo.orderCol","功能点排序","number","max:2"],
		["funcInfo.remark","备注","max","String:200"]
		);
	
		if(xj.CheckAll(objarr))
		{
			if(confirm("确认保存？"))
			{
				return true;
			}
			else
				return false
		}
		else
			return false;
	}
	function changeState()
	{
		var state = document.getElementById("funcInfo.isLeaf").options[document.getElementById("funcInfo.isLeaf").selectedIndex].value
		if(state=="true")
			document.getElementById("funcInfo.url").readOnly=false;
		else
		{
			document.getElementById("funcInfo.url").value="";
			document.getElementById("funcInfo.url").readOnly=true;
		}
	}
	function selectIco(){
		var imgPath = ModalWindow("jump.do?path=lstIcoFuncInfo.do",800,600);
		if(imgPath!=null)
			$("#urlImg").val(imgPath);
	}
	$(function() {
		$('.nyroModal').nyroModal({
		});
	});
</script>

<title>修改功能菜单</title>
	</head>
	<body>
		<form action="updateFuncInfo.do" name="form1" method="post" onsubmit="return saveinfo()">
		<input type="hidden"  name="funcInfo.funcParentId" value="${funcInfo.funcParentId}"/>
		<input type="hidden"  name="funcInfo.state" value="${funcInfo.state}"/>
		<div id="body_div">
			<w:ShowTitle name="修改功能菜单">
			<w:TitleButtons funcParentId="001001002"/>
			<w:TitleButton funcName="保存" onClickFunction="" urlImg="${ctx}/images/main/save.png" funcType="submit"/>
			</w:ShowTitle>
			<div id="body_content">
				<table width="100%">
					<tr>
						<td width="120">本级ID</td>
						<td class="left_align" width="150">
							<input type="text"  name="funcInfo.funcId" value="${funcInfo.funcId}" readonly="readonly" />
						</td>
						<td width="120">本级名称</td>
						<td class="left_align">
							<input  name="funcInfo.funcName" value="${funcInfo.funcName}" maxlength="100" />
						</td>
					</tr>
					<tr>
						<td>菜单级别(标识)</td>
						<td class="left_align">
							<input type="text"  name="funcInfo.funcType" value="${funcInfo.funcType}" maxlength="1"/>
						</td>
						<td>排序</td>
						<td class="left_align">
							<input type="text"  name="funcInfo.orderCol" maxlength="2" value="${funcInfo.orderCol}"/>
						</td>
					</tr>
					<tr>
						<td>图标位置</td>
						<td class="left_align" colspan="3">
							<input type="text" name="funcInfo.urlImg" id="urlImg" maxlength="300" style="width:400px" value="${funcInfo.urlImg}"/>
							<a href="#" onclick="selectIco()"><img src="../../../images/main/query_min.png" width="16" height="16" /></a>
						</td>
					</tr>
					<tr>
						<td>url</td>
						<td class="left_align" colspan="3">
							<textarea   name="funcInfo.url" id="funcInfo.url" rows="5" cols="70">${funcInfo.url}</textarea>
						</td>
					</tr>
					<tr>
						<td>onClick function</td>
						<td class="left_align" colspan="3">
							<textarea   name="funcInfo.onclickFunction" rows="5" cols="70">${funcInfo.onclickFunction}</textarea>
						</td>
					</tr>
					<tr>
						<td>备注</td>
						<td class="left_align" colspan="3">
							<textarea name="funcInfo.remark" rows="5" cols="70">${funcInfo.remark}</textarea>
						</td>
					</tr>
				</table>
			</div>
			</div>
		</form>
		
	</body>
</html>
