<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../common/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="pageContent">
	<form method="post" action="saveCert.do" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="57">
			<table class="table" width="80%">
				<tr>
					<td align="center" width="40%">
						创建日期  
					</td>
					<td width="60%">
						<input name="certConfig.createdDate" value="<fmt:formatDate value="${certConfig.createdDate}" pattern="yyyy-MM-dd"/>" type="text" onClick="WdatePicker()" class="inputCal" >
						<input name="edit" value="${param.edit}" type="hidden" >
					</td>
				</tr>
				<tr>
					<td  align="center" >
						审查名称  
					</td>
					<td width="60%">
						<input type="hidden" name="certConfig.certId" value="${certConfig.certId }" >
						<input type="text" name="certConfig.certName" value="${certConfig.certName}" dataType="Require" msg="请输入名称">
						
					</td>
				</tr>
				<tr>
					<td  align="center" >
						保密资格等级 
					</td>
					<td width="60%">
						<select name="certConfig.level" class="selectLength"  id="level" >
						 <c:forEach items="${applicationScope.CodeDict}" var="item">
							 	<c:if test="${item.parentCodeId=='004'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certConfig.level}">selected</c:if> >${item.codeName}</option>
							 	</c:if>
						 </c:forEach>
						</select>
						<input type="hidden" name="level" value="${certConfig.level }">
					</td>
				</tr>   
				<tr>
					<td  align="center">
						分值  
					</td>
					<td class="left_align"  width="60%">
						<input type="text" name="certConfig.certPoint" value="${certConfig.certPoint}" dataType="Number,Require" msg="请输入正确的分值">
					</td>
				</tr>
				
				<tr>
				
					<td  align="center" >
						创建人
					</td>
					<td class="left_align"  width="60%">
						 	<input name="certConfig.createdBy" type="text"
							<c:if test="${certConfig.createdBy!=null}"> value="${certConfig.createdBy}"  </c:if>
							<c:if test="${certConfig.createdBy==null}"> value="${sessionScope.sessionbean.userName}"  </c:if>
						>

					</td>
				</tr>
			</table>
			<table id="tb" border="1" width="80%" layoutH="208" >
			<thead>
				<tr>
					<td colspan="4" >
						<input name="button" type="button" value="增加规则"  id="BtAdd" />	
						<input id="BtDel" type="button" value="删除规则"  />
					</td>
				</tr>
				<tr>
					<td  align="center" >编号</td> 
					<td  align="center"><input  id="CKA" name="CKA" type="checkbox" group="CK" class="checkboxCtrl"></td>
					<td  align="center" >
						规则内容
					</td  align="center" >
					<td>
						扣分
					</td>
				</tr>
				</thead>
				<tbody>
				<tr  >
					<td align="center">${status.count}<input type="hidden" name="ruleNo" value="${status.count}"></td>
					<td  align="center"><input id="CK"  name="CK" type="checkbox"></td>
					<td >
						<input type="text" name="ruleContent" size="65" >
						<input type="hidden" name="ruleId"/>
					</td>
					<td >
						<input type="text" name="rulePoint" size="4">
					</td>
				</tr>
				<c:forEach items="${ruleList}" var="obj" varStatus="status">
					<tr>
						<td align="center">${status.count}<input type="hidden" name="ruleNo" value="${status.count}"></td>
						<td  align="center"><input id="CK"  name="CK" type="checkbox"></td>
						<td >
							<input type="text" name="ruleContent" value="${obj.ruleContent}" size="65" dataType="Require" msg="请输入名称">
							<input type="hidden" name="ruleId" value="${obj.ruleId}" />			
						</td>
						<td >
							<input type="text" name="rulePoint" value="${obj.rulePoint}" size="4">	
						</td>
					</tr>				
				</c:forEach>
				</tbody>			
			</table>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
 <script type="text/javascript" ></script>
      <script language="javascript">
         $(document).ready(function() {
            //隐藏模板tr
            $("#tb tr").eq(2).hide();
            var vNum=$("#tb tr").size()-2;
            $("#BtAdd").click(function() {
　　　　　//复制一行
                var tr = $("#tb tr").eq(2).clone(true);
                var a = vNum++;
                tr.find("td").get(0).innerHTML = a +'<input type="hidden" name="ruleNo" value="'+a+'">';
                tr.show();
                tr.appendTo("#tb");
            });
            $("#BtDel").click(function() {
                $("#tb tr:gt(2)").each(function() {
                    if ($(this).find("#CK").get(0).checked == true) {
                        $(this).remove();
                    }
                });
                  vNum = 1;
                $("#tb tr:gt(2)").each(function() {
                	a =vNum++;
                    $(this).find("td").get(0).innerHTML =  a+'<input type="hidden" name="ruleNo" value="'+a+'">';
                });
                
                $("#CKA").attr("checked", false);
            });
            $("#CKA").click(function() {
                $("#tb tr:gt(2)").each(function() {
                    $(this).find("#CK").get(0).checked = $("#CKA").get(0).checked;
                });
            });
        })
        closeDialog = function(){
			$('#dialog').dialog('close');
		}
      </script>
