<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../../../common/include.jsp"%>
<%@ include file="../../../common/include2.jsp"%>
<div class="pageHeader">
<form id="pagerForm" method="post"  action="listTaskTODO.do" onsubmit="return navTabSearch(this);">
	<input type="hidden" name="status" value="${param.status}" />
	<input type="hidden" name="keywords" value="${param.keywords}" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<div class="searchBar">
		<table  class="searchContent" >
					<tr>
						<td colspan="3">
							单位名称:<input type="hidden" id="list_noJSONTaskTodo" value="${unitData}">
							<input type="text" name="unitName" id="unitNameTaskTodo" class="inputLength" value="${unitName }" size="80">
						</td>
					</tr>
					<tr>
						<td>
							所在省份:<input type="text" name="region" value="${region}" size="30">
						</td>
					
						<td>
							经济性质:
							<select name="unitCategory"  value="${unitCategory}">
								<option ></option>
								<c:forEach items="${applicationScope.CodeDict}" var="item">
								 	<c:if test="${item.parentCodeId=='005'}">  
									 		<option value="${item.codeId}" <c:if test="${item.codeId==unitCategory}">selected</c:if> >${item.codeName}</option>
								 	</c:if>
								</c:forEach>
							</select>
						</td>
					
						<td>
							上报时间:<input type="text" class="date" name="time" value="${time }" size="30">
						</td>
					</tr>
					<tr>
						<td>
							<input type="checkbox" name="check1" <c:if test="${check1==1}">checked="true"</c:if> value="1" >全部 &emsp;&emsp;
							<input type="checkbox" name="check2" <c:if test="${check2==2}">checked="true"</c:if> value="2" >未处理&emsp;&emsp;
							<input type="checkbox" name="check3" <c:if test="${check3==3}">checked="true"</c:if> value="3" >未处理(临近超期)&emsp;&emsp;
							<input type="checkbox" name="check4" <c:if test="${check4==4}">checked="true"</c:if> value="4" >已处理
						</td>
					</tr>
				</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent" >
	<table class="table" width="100%" layoutH="188" >
				<thead>
				<tr >
					<th>
						序号
					</th>
					<th>
						单位名称
					</th>
					<th>
						所属集团
					</th>
					<th>
						经济性质
					</th>
					<th>
						法人
					</th>
					<th>
						认证状态
					</th>
					<th>
						认证类型
					</th>
					<th>
						操作
					</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${listData}" var="obj" varStatus="i">
					<tr  target="sid_certId" rel="">
						<td >
							${i.count}
						</td>
						<td >
							${obj.unitInfo.unitName}
						</td>
						<td >
							<w:CodeIdDictOut value="${obj.unitInfo.unitGroup }"></w:CodeIdDictOut>
						</td>
						<td >
							<w:CodeIdDictOut value="${obj.unitInfo.unitCategory }"></w:CodeIdDictOut>
						</td>
						<td >
							${obj.unitInfo.legalRepresentative}
						</td>
						<td >
							<w:CodeIdDictOut value="${obj.certStatus }"></w:CodeIdDictOut>
						</td>
						<td >
							<w:CodeIdDictOut value="${obj.certType }"></w:CodeIdDictOut>
						</td>
						<td >
							<input type="button" value="查看/处理" onclick="certTask('${obj.certType}','${obj.certTaskId}')">
						</td>
					</tr>			
				</c:forEach>
				</tbody>			
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage"  onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20"
				<c:if test="${numPerPage==20}">selected</c:if>
				>20</option>
				<option value="50"
				<c:if test="${numPerPage==50}">selected</c:if>
				>50</option>
				<option value="100"
				<c:if test="${numPerPage==100}">selected</c:if>
				>100</option>
				<option value="200"
				<c:if test="${numPerPage==200}">selected</c:if>
				>200</option>
			</select>
			<span>条，共${totalCount}条</span>
		</div>
		
		<div class="pagination" targetType="navTab" totalCount="${totalCount}" numPerPage="${numPerPage}" pageNumShown="10" currentPage="${pageNum}"></div>

	</div>
</div>
<script language="javascript">
		$(function(){
			var jsonStr = $('#list_noJSONTaskTodo').val();
			$('#unitNameTaskTodo').jsonSuggest(eval(jsonStr),{onSelect:checkedListCallback, maxResults:10});
		})
		function checkedListCallback(item){
			$('#unitNameTaskTodo').val(item.text.split("，")[1]);
		}
		function certTask(certType,certTaskId){
			if(certType=='013001' || certType=='013004'){
				navTab.openTab("shencharenwu", "listCensor.do?edit=true", { title:"审查任务", fresh:false, data:{"certTaskId":certTaskId} });
			}else if(certType=='013002' || certType=='013003'){
				navTab.openTab("shencharenwu", "listReview.do?edit=true", { title:"复查任务", fresh:false, data:{"certTaskId":certTaskId} });
			}
			

		//	$.pdialog.open("listPaperScore.do?edit=true&pkPaperInfo="+pkPaperInfo, "fenshushezhi","分数设置",{width:800,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true} );   
			//$("#paperScore").val(returnValue);
		}
</script>
