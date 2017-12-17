<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../../../common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
<jsp:include page="../../../common/head.jsp"></jsp:include>
<script language="javascript">
	
	//修改
	function updCode()
	{
		if("${codeDict.isDefult}"=="Y")
		{
			alert("所选字典码内容为系统内定，不能修改");
		}
		else
		{
			window.parent.frame_right.location.href="upd_CodeDict.do?codeId=${codeDict.codeId}";
		}
	}
</script>

<title>查看数据字典项</title>
</head>
<body>
<div id="body_div">
	<w:ShowTitle name="查看字典码明细">
	<w:TitleButton funcName="修改" onClickFunction="updCode()" urlImg="${ctx}/images/main/update.png"/>
	</w:ShowTitle>
	<div id="body_content"> 
		  <table width="100%">
		    <tr>
			   <td width="150">本级字典编码</td>
			   <td  class="left_align">
			   ${codeDict.codeId}
			   </td>
		    </tr>
		    <tr>
			   <td>本级字典名称 </td>
			   <td  class="left_align">
			   ${codeDict.codeName}
			   </td>
		    </tr>
		    <tr>
			   <td>字典码字母代码 </td>
			   <td  class="left_align">
			   ${codeDict.codeCode}
			   </td>
		    </tr>
		    <tr>
			   <td>字典特殊标记</td>
			   <td  class="left_align">
			   ${codeDict.spMark}
			   </td>
		    </tr>
			<tr>
				<td >字典描述</td>
		        <td  class="left_align">${codeDict.dictRemark}</td>
			</tr>
		</table>
	</div>
</div>
</body>

</html>
