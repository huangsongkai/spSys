<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../../../common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<jsp:include page="../../../common/head.jsp"></jsp:include>
		<title>菜单管理</title>
	<script language="JavaScript"> 
		isModalWindow=true;
		//修改用户
		function selectIco(imgPath){
			window.top.returnValue=imgPath;
			window.top.close();
		}

	</script>
	</head>
	<body>
		<div id="body_div">
			<w:ShowTitle name="选择图标">
			<w:TitleButton funcName="返回" onClickFunction="window.top.close();" urlImg="${ctx}/images/main/back.png"/>
			</w:ShowTitle>
		<div id="body_content">
				<c:forEach items="${icos}" var="ico">
					<img src="${ctx}/${folder}${ico}" onclick="selectIco('${folder}${ico}')"/>
				</c:forEach>
		</div>
	</div>
	</body>
</html>