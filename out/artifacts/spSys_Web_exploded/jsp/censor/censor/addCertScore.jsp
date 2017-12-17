<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../common/include.jsp"%>
<%@ include file="../../../common/include2.jsp"%>
<div class="pageContent">
	<form action="saveCertScore.do" name="form1" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>分数情况：</label>
				<input type="hidden" name="certTaskId" value="${certTaskInfo.certTaskId }">	
			</p>
			
			<p>
				<label>最高分：</label>
				<input type="text" name="highestScore" value="${certTaskInfo.highestScore}" class="number" alt="最低分为数字">  
			</p>
		
			<p>
				<label>最低分：</label>
				<input type="text" name="numPeople" value="${certTaskInfo.numPeople}" class="number" alt="最低分为数字">
			</p>
			
			<p>
				<label>平均分：</label>
				<input type="text" name="average" value="${certTaskInfo.average}" class="number" alt="平均分为数字">     
			</p>
			
			<p>
				<label>平均分：</label>
				<input type="text" name="average" value="${certTaskInfo.average}" class="number" alt="平均分为数字">   
			</p>   
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"   >保存</button></div></div></li>  
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>	