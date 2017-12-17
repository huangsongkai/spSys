<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../../../common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>数据字典详情</title>
	<jsp:include page="../../../common/head.jsp">
			 <jsp:param name="_spPage" value="true"/>
			 <jsp:param name="_useCalendar" value="false"/>
			 <jsp:param name="_useTab" value="false"/>
			 <jsp:param name="_useTree" value="false"/>
			 <jsp:param name="_useModal" value="false"/>
			 <jsp:param name="_useOrgSuggest" value="false"/>
			 <jsp:param name="_useEasyUi" value="true"/>
	</jsp:include>
		<script language="javaScript">	
	$(document).ready(function() {	
				$("#dictTable tr").live("mousemove",function(){ 
				 	if($(this).attr('selectFlag')!='1'){//如果当前行未选中,修改背景
					 	$(this).css("background-color","#BDDFFF"); 
			            $(this).children().css("background-color","#BDDFFF"); 
				 	}
		            }); 
		            
       			$("#dictTable tr").live("mouseout",function(){ 
       				if($(this).attr('selectFlag')!='1'){//如果当前行未选中,修改背景
		          	  $(this).css("background-color",""); 
		          	  $(this).children().css("background-color",""); 
		            }
       			}); 
       			
				$("#dictTable tr").click(function(){
					$("#dictTable tr").attr('selectFlag','0');
		            $("#dictTable tr").css("background-color",""); 
		            $("#dictTable tr").children().css("background-color",""); 
		            $(this).css("background-color","#6699FF"); 
		            $(this).children().css("background-color","#6699FF"); 
		            $(this).attr('selectFlag','1');
			   });
			   
			 
			   $("#dictTable tr").dblclick(function(){
		            var id =$(this).attr('id');
		            window.location.href="upd_CodeDict.do?codeId="+id;
			   });
			   
			});			
	</script>
	</head>
	<body>
	<form name="form1" action="list_CodeList.do" method="post" onsubmit="doSearch()">
	<div id="body_div">
	<w:ShowTitle name="数据字典详情">
	<w:TitleButtons funcParentId="001001001"/>
	<w:TitleButton funcName="查询" onClickFunction="_queryDivBg(this)" urlImg="${ctx}/images/main/query.png"/>
	<w:TitleButton funcName="修改" onClickFunction="editDict()" urlImg="${ctx}/images/main/update.png"/>
	<w:TitleButton funcName="删除" onClickFunction="delDict()" urlImg="${ctx}/images/main/delete.png"/>
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
			
			<div id="div">
				<table width="100%" id='dictTable' class="XJContexTable"  >
					<tr >
						<td class="td_title" width="10%" nowrap>字典大类编号</td>
						<td class="td_title" width="10%" nowrap>字典码字母代码</td>
						<td class="td_title" width="10%" nowrap>本级字典编号</td>
						<td class="td_title" width="20%" nowrap>本级字典名称</td>
						<td class="td_title" width="10%" nowrap>上级字典编号</td>
						<td class="td_title" width="10%" nowrap>字典特殊标记</td>
						<td class="td_title" width="30%" nowrap>字典描述</td>
					</tr>
					<c:forEach items="${dataList}" var="obj" varStatus="i">
						<tr id="${obj.codeId}" onclick="xjCommon.TrClick(this)" onmouseover="xjCommon.TrMousemove(this)" onmouseout="xjCommon.TrMouseout(this)">		
						<td>${obj.kindId}</td>
						<td>${obj.codeCode}</td>
						<td>${obj.codeId}</td>
						<td>${obj.codeName}</td>
						<td>${obj.parentCodeId}</td>
						<td>${obj.spMark}</td>
						<td>${obj.dictRemark}</td>							
						</tr>
					</c:forEach>
				</table>
          </div>
	     <w:spPage/>
	</div>
	</form>
	<script language="javaScript">	
	//修改字典码
	function editDict(str){
		var id;
		if(xjCommon.varClkTr!=null){
		id=$(xjCommon.varClkTr).attr('id');
		}
		if(id == "" || id == null)
			{
				alert("请选中一行")
			}else{
				window.location.href="upd_CodeDict.do?codeId="+id;
			}
	}
	//删除字典码
	function delDict(str){
			var id;
			if(xjCommon.varClkTr!=null){
				id=$(xjCommon.varClkTr).attr('id');
			}
			if(id == "" || id == null)
			{
				alert("请选中一行")
				return;
			}
			if(confirm("确认删除该字典码？"))
			{	
				var returnValue=ModalWindow("jump.do?path=del_CodeDict.do&codeId="+id,800,600);
				if(returnValue=="ok")
				{
					xjSpPage.spPageSubmit();
				}
			}	
	}	
	// 查看字典码大类
	function show_CodeKind(id)
	{
		window.location.href="show_CodeKind.do?kindId="+id;
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
	

	</script>
</html>