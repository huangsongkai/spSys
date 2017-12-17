<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../../../common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
<jsp:include page="../../../common/head.jsp"></jsp:include>
<script language="javascript">
	//返回
	function goback()
	{
		window.location.href="list_CodeKind.do";
	}
</script>

<title>字典码大类查看</title>
</head>
<body>
<form action="upd_CodeKind.do" method="post" name="form1" onsubmit="return saveinfo()">
 <div id="body_div">
	<w:ShowTitle name="查看字典码大类">
	<w:TitleButtons funcParentId="001001001"/>
	<w:TitleButton funcName="返回" onClickFunction="goback()" urlImg="${ctx}/images/main/back.png"/>
	</w:ShowTitle>
	<div id="body_content"> 
  <table width="100%" border="0" cellpadding="0" cellspacing="1" class="tableBorder" >
    <tr>
	   <td width="150">本级字典码ID</td>
	   <td  class="left_align">${codeKind.kindId}</td>
    </tr>
    <tr>
	   <td>字典大类名称 </td>
	   <td  class="left_align">${codeKind.kindName}</td>
    </tr>
    <tr>
	   <td>字典大类字母代码</td>
	   <td  class="left_align">${codeKind.kindCode}</td>
    </tr>
    <tr>
	   <td>可扩展层级数</td>
	   <td  class="left_align">${codeKind.levelTal}</td>
    </tr>
	<tr>
		<td >备&nbsp;注</td>
        <td  class="left_align">${codeKind.remark}</td>
	</tr>
</table>
</div>
</div>
</form>
</body>
</html>
