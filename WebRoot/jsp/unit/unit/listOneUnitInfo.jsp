<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.xunj_prj/tag/webwidget" prefix="w"%>
<%@ include file="../../../common/include.jsp"%>
<%@ include file="../../../common/include2.jsp"%>
<style>
table.b{
	border-right:0px solid #000000;
	border-bottom:0px solid #000000;
	border-left:0;
	border-top:0;
	font-family:"宋体";
}
table.b td {
	padding:5px;
	border-left:1px solid #c2c2c2;
	border-top:1px solid #c2c2c2;
	border-right:1px;
	border-bottom:1px;
	font-family:"宋体";
	background: white;
	align:center;
}

table.b th {  
	padding:5px;
	border-left:1px solid #9a9a9a;
	border-top:1px solid #9a9a9a;
	border-right:0;
	border-bottom:0;
	font-family:"宋体" ; 
	background:  #f7f7f7  ;  
	align:center;
}  
</style>
<div class="tabs" currentIndex="0" eventType="click">  
    <div class="tabsHeader">  
        <div class="tabsHeaderContent">  
            <ul>  
                <li><a href="#" class="selected"><span>基础信息</span></a></li>  
                <li><a href="#"><span>隶属关系</span></a></li> 
                <li><a href="#"><span>保密检查</span></a></li> 
                <li><a href="#"><span>审查认证</span></a></li>  
            </ul>  
        </div>  
    </div>  
    <div class="tabsContent" layoutH="50">  
        <div  class="pageContent" >
			<table class="b" width="100%" height="300px">
				<tr>
					<th width="30%">单位名称:</th>
					<td width="70%">${unitInfo.unitName }</td>
				</tr>
				<tr>
					<th width="30%">
						法定代表人
					</th>
					<td width="70%">${unitInfo.legalRepresentative }</td>
				</tr>
				<tr>
					<th width="30%">单位性质:</th>
					<td width="70%"><w:CodeIdDictOut value="${unitInfo.unitCategory }"></w:CodeIdDictOut></td>
				</tr>
				<tr>
					<th width="30%">
					认证等级:
					</th>
					<td width="70%">
						<w:CodeIdDictOut value="${unitInfo.censorLevel }"></w:CodeIdDictOut>
					</td>
				</tr>
				<tr>
					<th width="30%">注册地址:</th>
					<td width="70%">${unitInfo.unitLoginAddress }</td>
				</tr>
				<tr>
					<th width="30%">通讯地址:</th>
					<td width="70%">${unitInfo.unitCommunicationAddress }</td>
				</tr>
				<tr>
					<th width="30%">电话:</th>
					<td width="70%">${unitInfo.unitPhone }</td>
				</tr>
				<tr>
					<th width="30%">
						邮编:
					</th>
					<td width="70%">
						${unitInfo.zipCode }
					</td>
				</tr>
			</table>
		</div>  
        <div>
			<w:tree returnValue="unitNo"
				displayValue="unitName" pidname="superiorInfo" content="${datalist}"
				idname="unitNo" headValue="${headValue}" headDisplayValue="${headDisplayValue}">
		   </w:tree>
		</div>
		<div>
			<table class="b" width="100%">
				<tr>
					<th class="td_title" width="25%">检查名称</th>
					<th class="td_title" width="25%">检查结论</th>
					<th class="td_title" width="25%">时间</th>
					<th class="td_title" width="25%">分数</th>
				</tr>
				<c:forEach items="${checkTaskInfoList}" var="obj">
					<tr>
						<td>
							${obj.checkTaskName }
						</td>
						<td>
							<w:CodeIdDictOut value="${obj.checkResult }"></w:CodeIdDictOut>
						</td>
						<td>
							<fmt:formatDate value="${obj.passedTime }" pattern="yyyy-MM-dd"/>
						</td>
						<td>
							${obj.passedScore }
						</td>					
					</tr>
				</c:forEach>
			</table>
		</div>
		<div>
			<table class="b" width="100%">
				<tr>
					<th >认证名称</th>
					<th>认证状态</th>
					<th >时间</th>
					<th >分数</th>
					<th >最高分</th>
					<th >最低分</th>
					<th >平均分</th>
				</tr>
				<c:forEach items="${certTaskInfoList}" var="obj">
					<tr>
						<td>
							<w:CodeIdDictOut value="${obj.certType }"></w:CodeIdDictOut>
						</td>
						<td>
							<c:if test="${obj.certStatus == '012004' || obj.certStatus == '012005'}">认证通过</c:if><c:if test="${obj.certStatus != '012004' && obj.certStatus != '012005'}"><w:CodeIdDictOut value="${obj.certStatus }"></w:CodeIdDictOut></c:if>
						</td>
						<td>
							<fmt:formatDate value="${obj.passedTime }" pattern="yyyy-MM-dd"/>
						</td>
						<td>
							${obj.passedScore }
						</td>	
						<td>
							${obj.highestScore }
						</td>
						<td>
							${obj.numPeople }
						</td>		
						<td>
							${obj.average }
						</td>					
					</tr>
				</c:forEach>		
			</table>
		</div> 
    </div>  
</div>



<script language="javascript">
		 function  clickEvent(thisObj)
	{
		obj=thisObj;
	}
	$(document).ready(function() {
	var thisNode=document.getElementById('${unitInfo.unitNo }');
		xjTree.c(thisNode);
    })
		
		
</script>
