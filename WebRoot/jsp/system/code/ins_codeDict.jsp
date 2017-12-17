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
		["codeDict.codeName","字典码名称","notEmpty",""],
		["codeDict.dictRemark","字典码说明","max","String:250"]
		);
	
		if(xj.CheckAll(objarr))
		{
			if(confirm("确认保存？"))
				return true;
			else
				return false
		}
		else
			return false;
	}
	//返回
	function goback()
	{
		window.parent.location.href="list_CodeList.do";
	}
</script>

<title>添加数据字典项</title>
</head>
<body>
<form action="ins_CodeDict.do" method="post" name="form1" onsubmit="return saveinfo()">
<div id="body_div">
	<w:ShowTitle name="添加字典码">
	<w:TitleButtons funcParentId="001001001"/>
	<w:TitleButton funcName="保存" onClickFunction="" urlImg="${ctx}/images/main/save.png" funcType="submit"/>
	<w:TitleButton funcName="返回" onClickFunction="goback()" urlImg="${ctx}/images/main/back.png"/>
	</w:ShowTitle>
<div id="body_content">

  <table width="100%" border="0" cellpadding="0" cellspacing="1" class="tableBorder" >
    <tr>
	   <td>上级字典编码 </td>
	   <td  class="left_align">
	   <input type="text" name="codeDict.parentCodeId" value="${pid}"/>
	   <input type="hidden" name="codeDict.kindId" value="${kindId}"/>
	   </td>
    </tr>
    <tr>
	   <td>上级字典码名称</td>
	   <td  class="left_align"><input type="text" name="parentCodeName" readonly="readonly" value="${codeName}"/></td>
    </tr>
    <tr>
	   <td>本级字典编码</td>
	   <td  class="left_align"><input type="text" name="codeDict.codeId" readonly="readonly" value="${codeDict.codeId}"/></td>
    </tr>
    <tr>
	   <td>本级字典名称</td>
	   <td  class="left_align"><input type="text" name="codeDict.codeName"  maxlength="40"/></td>
    </tr>
    <tr>
	   <td>字典码字母代码</td>
	   <td  class="left_align"><input type="text" name="codeDict.codeCode" readonly="readonly" value="${codeDict.codeCode}"/></td>
    </tr>
    <tr>
	   <td>字典特殊标记</td>
	   <td  class="left_align"><input type="text" name="codeDict.spMark"  maxlength="25"/></td>
    </tr>
	<tr>
		<td>字典描述</td>
        <td class="left_align"><textarea name="codeDict.dictRemark" rows="5" cols="80"></textarea></td>
	</tr>
</table>
</div>
</div>
</form>
</body>
</html>
