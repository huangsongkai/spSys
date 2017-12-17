<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${applicationScope.sysConfig.applicationName}</title>
<script type="text/javascript" src="common/jquery/jquery.min.js"></script>
<script language="javascript" src="common/js/frametop.js"></script>
<link href="common/css/top.css" rel="stylesheet" type="text/css" />
<script type=text/javascript>
	function showPage(pageUrl)
	{
		window.parent.mainFrame.location.href = pageUrl;
	}
</script>
</head>

<body>
	<c:set var="cnt" value="0"></c:set>
	<div id="menu">
		<div class="Sliding_tab">
			<ul>
				<c:forEach var="item" items="${menu}">
				<c:if test="${item.funcType eq '1' and item.funcParentId eq '0'}">
				<li></li><li><span class="tabsLink" id="setTab${cnt}">${item.funcName}</span></li>	<li></li><c:set var="cnt" value="${cnt+1}"></c:set>
				</c:if>
				</c:forEach>
			</ul>
		</div>
	</div>
	<div id="icoBar">
		<c:set var="cnt" value="0"></c:set>
		<c:forEach var="item" items="${menu}" varStatus="sts">
			<c:if test="${item.funcType eq '1' and item.funcParentId eq '0'}">
				<c:set var="display" value=""></c:set>
				<c:if test="${cnt!=0}"><c:set var="display" value="display:none;"></c:set></c:if>
				<ul class="groupBorder" id="menuDiv${cnt}" style="${display}">
					<c:set var="cnt" value="${cnt+1}"></c:set>
					<c:forEach var="item1" items="${menu}">
					<c:if test="${item1.funcType eq '2' and item1.funcParentId eq item.funcId }">
					<li class="groupBorder_left"></li>
					<li class="detailUL">
						<div>
							<ul class="icoUl">
								<c:forEach var="item2" items="${menu}">
									<c:if test="${item2.funcType eq '3' and item2.funcParentId eq item1.funcId }">
									<li onclick="showPage('${ctx}/${item2.url}')"><a href="#" title=""><img src="${item2.urlImg}" width="35" height="35" /><br/>${item2.funcName}</a></li>
									</c:if>
								</c:forEach>
							</ul>
						</div>
						<div class="groupText">
								${item1.funcName}
						</div>
					</li>
					<li class="groupBorder_right"></li>
						</c:if>
					</c:forEach>
				</ul>
			</c:if>
		</c:forEach>
	</div>
	
	<div id="logoMenu" class="logoMenuBg"></div>

</body>
</html>

