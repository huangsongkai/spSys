<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="../../../common/include.jsp"%>
<%@ include file="../../../common/include2.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link rel="stylesheet" href="../../../common/xjTree/xjTree.css" type="text/css"/>
<script type='text/javascript' src='../../../common/xjTree/xjTree.js'></script>
<link rel="stylesheet" href="../../../common/css/mainStyle.css" type="text/css" />
<title>用户管理</title>
<script language="javascript">
	function  clickEvent(obj)
	{
		var val = obj.id.split("#");
		parent.user_right.location.href='listAnyuser.do?userlist.dept.deptId='+val[0]+"&userlist.dept.deptName="+val[1];
	}
</script>
</head>
<body>
<div align="center">
  <table width="100%" class="tableNoBorder">
	<tr>
	  <td>
      <w:tree 
      returnValue="deptId#deptName"  
      displayValue="deptName" 
      pidname="deptPid" 
      content="${datalist}" 
      idname="deptId" 
      headValue="0" 
      regex="#"
      headDisplayValue="选择部门" useDblClick="false">
      </w:tree>
	  </td>
	</tr>
  </table>
</div>
</body>

</html> 
