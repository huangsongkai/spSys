<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../../common/include.jsp" %>
<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block; margin-left:5px}
</style>

<div class="pageContent" style="padding:5px">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a id="addFunc" class="add" onclick="judgeFunc(this)" target="ajax" rel="jbsxBox" ><span>添加</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" href="listFuncInfoExportExcel.do" target="dwzExport" targetType="navTab" title="确定要导出这些记录吗?"><span>导出EXCEL</span></a></li>
		</ul>
	</div>
	<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:;"><span>菜单管理</span></a></li>
				</ul>
			</div>
		</div>
		<div id="dd" class="tabsContent" layoutH="75">
			<div>
				<div  layoutH="77" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
				     <ul class="tree treeFolder treeCheck " oncheck="kkk">
						<c:forEach var="obj" items="${datalist}">
							<c:if test="${fn:length(obj.funcParentId)==1}">
								<li><a href="updateFuncInfo.do?funcId=${obj.funcId}" target="ajax" rel="jbsxBox"   tname="${obj.funcId}" tvalue="${obj.funcId}">${obj.funcName}</a>
								<ul >
								<c:forEach var="child" items="${datalist}">
								<c:if test="${child.funcParentId==obj.funcId}">
									<li ><a href="updateFuncInfo.do?funcId=${child.funcId}" target="ajax" rel="jbsxBox"  tname="${child.funcId}" tvalue="${child.funcId}">${child.funcName}</a></li>
								</c:if>
								</c:forEach>
								</ul>
							</li>
							</c:if>
						</c:forEach>
						</ul>
				</div>
				
				<div id="jbsxBox" class="" style="margin-left:246px;" >
				</div>
	
			</div>
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
	
</div>
<script type="text/javascript">

function kkk() {
}



function judgeFunc(obj){
	var parentId='';
	$("#dd input:checked").each(function(i,a){
	  if (a.name.length == 6) {
	        parentId=a.name;
			$("#addFunc").attr("href","addFuncInfo.do?parentId="+parentId)
			$("#addFunc").attr("target","ajax")
			$("#addFunc").attr("rel","jbsxBox")
			//return false;
	   }
	  });
	if(obj.href==''){
		alert('请选择菜单')
		$("#addFunc").attr("target","")
		$("#addFunc").attr("rel","")
		$("#delFunc").attr("target","")
		$("#delFunc").attr("title","")
		return false;
	}
}
</script>


	


