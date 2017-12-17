<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<html>
<head> 
<script type="text/javascript">
	function printFuc(){
		window.location.href="listSellBillNoQuery.do?forprint="+${forprint};
	
	
	}
</script>
<title><p>您是否要打印？</p></title>
</head>
<body onload="printFuc()">
	
</body>
</html>