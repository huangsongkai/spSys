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
		["codeDict.codeName","名称","notEmpty",""],
		["codeDict.dictRemark","说明","max","String:250"]
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
</script>

<title>修改数据字典项</title>
</head>
<body>
<form action="updDictComm.do" method="post" name="form1" onsubmit="return saveinfo()">
<div id="body_div">
	<w:ShowTitle name="修改${param.kindName}">
	<w:TitleButton funcName="保存" onClickFunction="" urlImg="${ctx}/images/main/save.png" funcType="submit"/>
	</w:ShowTitle>
	<div id="body_content">


  <input type="hidden" name="kindName" value="${param.kindName}"/>
  <input type="hidden" name="codeDict.kindId" value="${codeDict.kindId}"/>
  <input type="hidden" name="codeDict.parentCodeId" value="${codeDict.parentCodeId}"/>
  <input type="hidden" name="codeDict.isDefult" value="${codeDict.isDefult}"/>
  <input type="hidden" name="codeDict.state" value="${codeDict.state}"/>
  <table width="100%">
    <tr>
	   <td>编码</td>
	   <td class="left_align">
	   <input type="text" name="codeDict.codeId"  readonly="readonly" value="${codeDict.codeId}"/>
	   </td>
    </tr>
    <tr>
	   <td>名称</td>
	   <td class="left_align">
	   <input type="text" name="codeDict.codeName"  maxlength="40" value="${codeDict.codeName}"/>
	   </td>
    </tr>
	<tr>
		<td>描述</td>
        <td class="left_align"><textarea  name="codeDict.dictRemark" rows="5" cols="80">${codeDict.dictRemark}</textarea></td>
	</tr>
</table>
</div>
</div>
</form>
</body>

</html>
