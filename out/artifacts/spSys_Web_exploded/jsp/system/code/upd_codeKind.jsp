<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../../../common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
<jsp:include page="../../../common/head.jsp"></jsp:include>
<script language="javascript">
	function saveinfo()
	{
		var objarr = new Array(
		["codeKind.kindName","字典大类名称","notEmpty",""],
		["codeKind.kindName","字典大类名称","max","String:80"],
		["codeKind.levelTal","可扩展层级数","notEmpty",""],
		["codeKind.levelTal","可扩展层级数","int","1"],
		["codeKind.kindName","所属模块","max","String:20"],
		["codeKind.remark","字典码说明",,"max","String:250"]
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
	//返回
	function goback()
	{
		window.location.href="list_CodeList.do";
	}
</script>

<title>修改字典码大类</title>
</head>
<body>
<form action="upd_CodeKind.do" method="post" name="form1" onsubmit="return saveinfo()">
<div id="body_div">
	<w:ShowTitle name="修改字典码大类">
	<w:TitleButtons funcParentId="001001001"/>
	<w:TitleButton funcName="保存" onClickFunction="" urlImg="${ctx}/images/main/save.png" funcType="submit"/>
	<w:TitleButton funcName="返回" onClickFunction="goback()" urlImg="${ctx}/images/main/export.png"/>
	</w:ShowTitle>
	<div id="body_content">
  <table width="100%" border="0" cellpadding="0" cellspacing="1" class="tableBorder" >
    <tr>
	   <td>字典大类编号</td>
	   <td class="left_align"><input type="text" name="codeKind.kindId" readonly="readonly" value="${codeKind.kindId}"/></td>
    </tr>
    <tr>
	   <td>字典大类名称</td>
	   <td class="left_align"><input type="text" name="codeKind.kindName" maxlength="40" value="${codeKind.kindName}"/></td>
    </tr>
    <tr>
	   <td>字典大类字母代码</td>
	   <td class="left_align"><input type="text" name="codeKind.kindCode" maxlength="40" value="${codeKind.kindCode}"/></td>
    </tr>
    <tr>
	   <td>可扩展层级数</td>
	   <td class="left_align"><input type="text" name="codeKind.levelTal" maxlength="1" value="${codeKind.levelTal}"/></td>
    </tr>
    <tr>
	   <td>所属模块</td>
	   <td class="left_align"><input type="text" name="codeKind.codeType" value="${codeKind.codeType}"/></td>
    </tr>
	<tr>
		<td>备&nbsp;注</td>
        <td class="left_align"><textarea name="codeKind.remark" rows="5"   cols="80" >${codeKind.remark}</textarea></td>
	</tr>
</table>
</div>
</div>
</form>
</body>
</html>
