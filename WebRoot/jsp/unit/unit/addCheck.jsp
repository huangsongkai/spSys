<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../common/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="pageContent">
	<form method="post" action="saveCheck.do" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="57">
			<table class="table" width="80%">
				<tr>
					<td align="center" width="40%">
						创建日期  
					</td>
					<td width="60%">
						<input name="checkConfig.createdDate" value="<fmt:formatDate value="${checkConfig.createdDate}" pattern="yyyy-MM-dd"/>" type="text" onClick="WdatePicker()" class="inputCal" >
						<input name="edit" value="${edit}" type="hidden" >
					</td>
				</tr>
				<tr>
					<td  align="center" >
						检查名称  
					</td>
					<td width="60%">
						<input type="hidden" name="checkConfig.checkId" value="${checkConfig.checkId }" >
						<input type="hidden" name="checkConfig.checkState" value="${checkConfig.checkState }" >
						<input type="text" name="checkConfig.checkName" value="${checkConfig.checkName}" >
						
					</td>
				</tr>
				<tr>
					<td  align="center" >
						检查单位 
					</td>
					<td width="60%">
						<input name="checkConfig.checkUnit"  type="text"  value="${checkConfig.checkUnit}">
					</td>
				</tr>   
				<tr>
					<td  align="center" >
						创建人
					</td>
					<td class="left_align"  width="60%">
						 	<input name="checkConfig.createdBy" type="text" 
						 	<c:if test="${assessConfig.createdBy!=null}"> value="${assessConfig.createdBy}"  </c:if>
							<c:if test="${assessConfig.createdBy==null}"> value="${sessionScope.sessionbean.userName}"  </c:if>
						>

					</td>
				</tr>
			</table>
			<table id="tableCheck" border="1" width="80%" layoutH="208" >
			<thead>
				<tr>
					<td colspan="4" >
						<input name="button" type="button" value="增加规则" onclick="BtAdd()"/>	
						<input id="BtDel" type="button" value="删除规则"  onclick="DelAdd()"/>
					</td>
				</tr>
				<tr>
					<td  align="center" >编号</td> 
					<td  align="center"><input  id="CKA" name="CKA" type="checkbox" group="CK" class="checkboxCtrl"></td>
					<td  align="center" >
						规则内容
					</td>
					<td>
						分值
					</td>
				</tr>
				</thead>
				<tbody>
				<tr  >
					<td align="center">${status.count}<input type="hidden" name="ruleNo" value="${status.count}"></td>
					<td  align="center"><input id="CK"  name="CK" type="checkbox"></td>
					<td>
						<input id="a"  type="text" name="ruleContent" value="" size="85">
					</td>
					<td>
						<input type="text" name="remark" value="" size="35">
						<input type="hidden" name="ruleId" value="${obj.ruleId }">
					</td>
				</tr>
				<c:forEach items="${ruleList}" var="obj" varStatus="status">
					<tr>
						<td align="center">${status.count}<input type="hidden" name="ruleNo" value="${status.count}"></td>
						<td  align="center"><input id="CK"  name="CK" type="checkbox"></td>
						<td >
							<input type="text" name="ruleContent" value="${obj.ruleContent}" size="85">
						</td>
						<td >
							<input type="text" name="remark" value="${obj.remark}" size="35">
							<input type="hidden" name="ruleId" value="${obj.ruleId }">
							
						</td>
					</tr>				
				</c:forEach>
				</tbody>			
			</table>
			
		<div class="formBar">
			<ul>
				<c:if test="${flow!='1'}">
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
				</c:if>
			</ul>
		</div>
	</form>
</div>
 <script type="text/javascript" ></script>
      <script language="javascript">
         $(document).ready(function() {
            //隐藏模板tr
            $("#tableCheck tr").eq(2).hide();
        })
        function  BtAdd(){
        		var vNum=$("#tableCheck tr").size()-2;
				var tr = $("#tableCheck").find("tr:eq(2)").clone();
                var a = vNum++;
                tr.find("td").get(0).innerHTML = a +'<input type="hidden" name="ruleNo" value="'+a+'">';
                tr.show();
                tr.appendTo("#tableCheck");
        }
       function  DelAdd(){
      			var vNum=$("#tableCheck tr").size()-2;
        		$("#tableCheck tr:gt(2)").each(function() {
                    if ($(this).find("#CK").get(0).checked == true) {
                        $(this).remove();
                    }
                });
                  vNum = 1;
                $("#tableCheck tr:gt(2)").each(function() {
                	a =vNum++;
                    $(this).find("td").get(0).innerHTML =  a+'<input type="hidden" name="ruleNo" value="'+a+'">';
                });
                
                $("#CKA").attr("checked", false);
        }
      </script>
