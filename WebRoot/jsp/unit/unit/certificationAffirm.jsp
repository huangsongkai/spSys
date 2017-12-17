<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../../common/include.jsp"%>
<%@ include file="../../../common/include2.jsp"%>
<html>
  <head>
	<script type="text/javascript">
	function updateCertificationAffirm(certTaskId){
		if(confirm("是否结束认证？")){
	 		if(confirm("点击确定认证通过，点击取消认证不通过")){
				//navTab.openTab("updateCertificationAffirm", "updateCertificationAffirm.do", { title:"意见书", fresh:false, data:{"certTaskId":certTaskId,"certStatus":"012003"} });
				document.formf1.action ="updateCertificationAffirm.do?certTaskId="+certTaskId+"&certStatus=012003";
			}else{
				//navTab.openTab("updateCertificationAffirm", "updateCertificationAffirm.do", { title:"意见书", fresh:false, data:{"certTaskId":certTaskId,"certStatus":"012006"} });
				document.formf1.action ="updateCertificationAffirm.do?certTaskId="+certTaskId+"&certStatus=012006";
			}
			if(document.formf1.onsubmit()){
				document.formf1.submit()
			}
		}
	}
	</script>
  
  </head>
  <body>
  	<div align="center">
  		<form method="post" name="formf1" action="" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
  			<table width="80%" class="table">
  				<tr>
  					<th>
  						审查流程
  					</th>
  					<th>
  						完成情况
  					</th>
  				</tr>
  				<tr>
  					<td>
  						审查认证申请书
  					</td>
  					<td style="color:<c:if test="${empty certTaskInfo.application}">red</c:if>">
  						<c:if test="${empty certTaskInfo.application}">未完成</c:if>
  						<c:if test="${!empty certTaskInfo.application}">已完成</c:if>
  					</td>
  				</tr>
  				<tr>
  					<td>
  						审查计划
  					</td>
  					<td>
  						已完成
  					</td>
  				</tr>
  				<tr>
  					<td>
  						审查意见书
  					</td>
  					<td style="color:<c:if test="${empty certTaskInfo.passedTime}">red</c:if>">
  						<c:if test="${empty certTaskInfo.passedTime}">未完成</c:if>
  						<c:if test="${!empty certTaskInfo.passedTime}">已完成</c:if>
  					</td>
  				</tr>
  			</table>
			<div class="formBar">
  				<ul>
  					<li>
  						<input name="button" type="button" class="close" onclick="updateCertificationAffirm('${certTaskId }')" value="完成认证">
  					</li>
  				</ul>
			</div>  				
  		</form>
  	</div>
  </body>
</html>
