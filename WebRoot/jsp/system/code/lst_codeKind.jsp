<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../../../common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>数据字典管理</title>
<jsp:include page="../../../common/head.jsp">
	<jsp:param name="_spPage" value="true"/>
</jsp:include>
		<script language="javaScript">	
	// 添加字典码大类
	function ins_CodeKind()
	{
		window.location.href="ins_CodeKind.do";
	}
	// 修改字典码大类
	function upd_CodeKind(idValue,editAble)
	{
		if(editAble!="0")
		{
			window.location.href="upd_CodeKind.do?kindId="+idValue;
		}
		else
			alert("当前字典码类型为系统内部代码，不能修改！");
	}
	// 查看字典码大类
	function show_CodeKind(idValue)
	{
		window.location.href="show_CodeKind.do?kindId="+idValue;
	}
	function doSearch(){
		form1.submit();
	}
	function updateApplica(){
		var tmp = parseInt(Math.random()*10000+1);//设定随机数防止浏览器以相同url重复多次访问不进行请求
		if(confirm("确认更新应用服务器中缓存字典码数据？"))
		{
			var url = 'updApplication_CodeDict.do?t='+tmp;
			$.get(url, {},function (data, textStatus){
				if(data=="ok")
				{
					alert("更新服务器缓存字典码数据成功!");
				}
			});
		}
	}
	function showCodeDict(kindId,kindName,levelTal){
		var path1="list_CodeDict.do?kindId="+kindId+"&kindName="+kindName+"&levelTal="+levelTal;
		var path2="${ctx}/blank_common.jsp?title=数据字典管理";
		var url = conversionUrl(path1,path2,370);
		window.location.href="jump!GoIndexUrl.do?a=b"+url;
	}
</script>
	</head>
	<body>
	<form name="form1" action="list_CodeKind.do" method="post" onsubmit="doSearch()">
	<div id="body_div">
	<w:ShowTitle name="字典码大类">
	<w:TitleButtons funcParentId="001001001"/>
	<w:TitleButton funcName="查询" onClickFunction="_queryDivBg(this)" urlImg="${ctx}/images/main/query.png"/>
	<w:TitleButton funcName="添加" onClickFunction="ins_CodeKind()" urlImg="${ctx}/images/main/add.png"/>
	<w:TitleButton funcName="刷新" onClickFunction="updateApplica()" urlImg="${ctx}/images/main/export.png"/>
	</w:ShowTitle>
	<div id="body_content">
			<div id="queryDiv">
				<table width="100%">
					<tr>
						<td colspan="4" class="td_title">
							字典码查询
						</td>
					</tr>
					<tr>
						<td width="110">
							字典码编号
						</td>
						<td width="120" class="left_align">
							<input name="codeKind.kindId" type="text" value="${codeKind.kindId}"/>
						</td>
						<td width="110">
							字典码名称
						</td>
						<td class="left_align">
							<input name="codeKind.kindName" type="text" value="${codeKind.kindName}"/>
						</td>
					</tr>
					
					<tr>
						<td colspan="4">
							<input name="button" type="submit" class="btn_standard" value="　查 询" />
							<input name="button" type="button" class="btn_standard" onclick="_cancelQuery()" value="取消" />
						</td>
					</tr>
				</table>
			</div>	
				<table width="100%">
					<tr>
						<td colspan="5" class="warnText left_align">
							*更新字典码数据后请点击“刷新”按钮更新服务器缓存数据使所做修改生效
						</td>
					</tr>
					<tr>
						<td class="td_title">
							字典大类编号
						</td>
						<td class="td_title">
							字典大类名称
						</td>
						<td class="td_title">
							可扩展层级数
						</td>
						<td class="td_title">
							所属模块
						</td>
						<td class="td_title">
							字典大类描述
						</td>
						<td class="td_title">
							操作
						</td>
					</tr>
					<c:forEach items="${datalist}" var="obj" varStatus="indx">
						<tr>
							<td>
								${obj.kindId}
							</td>
							<td>
								<a href='#' onclick="show_CodeKind('${obj.kindId}')">${obj.kindName}</a>
							</td>
							<td>
								${obj.levelTal}
							</td>
							<td>
								${obj.codeType}
							</td>
							<td>
								${obj.remark}
							</td>
							<td>
								<a href="#" onclick="upd_CodeKind('${obj.kindId}','${obj.codeType}')">修改</a>
								<a href="#" onclick="showCodeDict('${obj.kindId}','${obj.kindName}','${obj.levelTal}')">详细</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<w:spPage />
			</div>
	</div>
	</form>
	</body>
</html>




