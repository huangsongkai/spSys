<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../../../common/include.jsp"%>
<HTML>
	<head>
		<script language="javascript">
		$(document).ready(function() {
			totalCal();
			$("input[name*=localTotal]").each(function(){
				var num = $(this).attr("id");
				scoreNumberOnChange(num);
			})
            $("input[name*=BtAdd]").click(function() {
　　　　　	//复制一行
				var num = $(this).attr("id");//table的id
				var vNum=$("#tb"+num+" tr").size()-2;//每个table中的行号
				
                var tr = $("#tb"+num+" tr").eq(2).clone(true);
                var a = vNum++;
                tr.find("td").get(0).innerHTML = a +'<input type="hidden" name="ruleNo" value="'+a+'">';
                tr.show();
                tr.appendTo("#tb"+num);
            });
            $("input[name*=BtDel]").click(function() {
				var num = $(this).attr("id");
            	var vNum=$("#tb"+num+" tr").size()-2;
                $("#tb"+num+" tr:gt(2)").each(function() {
                    if ($(this).find("#CK").get(0).checked == true) {
                        $(this).remove();
                    }
                });
                  vNum = 1;
                $("#tb"+num+" tr:gt(2)").each(function() {
                	a =vNum++;
                    $(this).find("td").get(0).innerHTML =  a+'<input type="hidden" name="ruleNo" value="'+a+'">';
                });
                scoreNumberOnChange(num);
            });
            initData();	
        })
        function initData(){
      	 	var flag=$("#flag").val();
         	if(flag=="true"){
            	$("input[name=exportButton]").hide();
          	}
        }
        function putDataIn(num){
            $("#tb"+num+" tr:gt(2)").each(function() {
            	var tthhiiss = $(this);
            	var v = tthhiiss.find("select[name=chooseRule]").get(0).value;
            	tthhiiss.find("input[name=ruleId]").get(0).value=v;
            	
                if (v != "0") {
                    $.ajax({
						async: false,
						type : "POST",
						url : 'ajaxCertRule.do?ruleId='+v,
						success : function(data) {
						//alert(data);
							var tmp = eval('(' + data + ')');
							tthhiiss.find("input[name=scoreRule]").get(0).value ="依据评分标准第" +tmp.ruleNo +"条";
							if(tmp.rulePoint!=0)
								tthhiiss.find("input[name=scoreNumber]").get(0).value = tmp.rulePoint;
						}
					});
                }
            });
            scoreNumberOnChange(num);
        }
        
		function scoreNumberOnChange(num){		
			realTotalLocalCal(num);
			realTotalCal();
		}
		
		function realTotalLocalCal(num){
			var sum = 0;
			$("input[name=scoreNumber]").each(function(){
				var eachNum = $(this).attr("id");
				if(eachNum==num){
					var temp = $(this).val()*1;
					sum+=temp;
				}
			})
			var total = $("input[name=localTotal"+num+"]").val();
			$("input[name=realLocalTotal"+num+"]").val(total-sum);
		}
		
		function realTotalCal(){
			var sum = 0;
			$("input[name*=realLocalTotal]").each(function(){
				var temp = $(this).val()*1;
				sum+=temp;
			})
			$("#passedScoreAdd").val(sum);
		}
		
		function totalCal(){
			var sum = 0;
			$("input[name*=realLocalTotal]").each(function(){
				var temp = $(this).val()*1;
				sum+=temp;
			})
			$("input[name=total]").val(sum);
		}
		
		function changeTextArea2(){
			var level = $("#confidentialityLevelQualifications").find("option:selected").text();
			$("textarea[name=certTaskInfo.textarea2]").val("符合标准，同意报国家军工保密资格认证委批准为"+level+"保密资格单位。");
		}
		function setIsOut(){
			//$("input[name=isOut]").val(1);
			//$.pdialog.open("certTaskToWord.do?certTaskId="+$("#certTaskId").val(), "daochuyijianshu","显示",{width:820,height:500,mask:true,mixable:true,minable:true,resizable:true,drawable:true} );
			var m_url ="certTaskToWord.do?certTaskId="+$("#certTaskId").val();
			window.location.href=m_url;
			//var returnValue=ModelWindow_dialog("certTaskToWord.do?certTaskId="+$("#certTaskId").val()+"&unitNo="+$("input[name=certTaskInfo.unitNo]").val(),800,600);	
			//if(returnValue=="ok"){
			//	spPageSubmit();
			//}
		}
		
		closeDialog = function(){
			$('#dialog').dialog('close');
		}
		
		
		function setIsIn() {
   			var returnValue=ModelWindow_dialog("certTaskInfoImport.do?certTaskId="+$("#certTaskId").val(),800,600);	
   			if(returnValue == 'ok'){
   				spPageSubmit();
   			}
		}
		
		function upLoadFile(certId,num1,num2,count){
   			var ruleId = $("#ruleId"+num1+""+num2+"").val();
		    var certTaskId = $("#certTaskId").val();
		    var imgId= $("#fileId"+num1+""+num2+"").val();
   			var returnValue=ModelWindow_dialog("upLoadFile.do?certId="+certId+"&ruleId="+ruleId+"&certTaskId="+certTaskId+"&count="+count+"&imgId="+imgId+"&fileType=img",800,600);	
   			if(returnValue != null){
   				var fileIdValue = $("#fileId"+num1+""+num2+"").val();
   				if(fileIdValue == null||fileIdValue == undefined||fileIdValue == "" ){
   					$("#fileId"+num1+""+num2+"").val(returnValue+count);
   				}else{
   					$("#fileId"+num1+""+num2+"").val(fileIdValue+"|"+returnValue+count);
   				}
   				spPageSubmit();	
   			}		
   		}
		
      	function changeTextArea(num,type){
	      	if(type=='member'){
	      		var a = $("#selectMemberName"+num+" option:selected").text();
	      		$("#memberName"+num+"").val(a);	
	      	}else if(type=='unit'){
	      		var a = $("#selectUnit"+num+" option:selected").text();
	      		$("#unit"+num+"").val(a);	
	      	}else if(type=='position'){
	      		var a = $("#selectPosition"+num+" option:selected").text();
	      		$("#position"+num+"").val(a);	
	      	}
      		
      	}
      	function checkPeoples(e,num){
      		var numS = $(e).parent().parent("tr").prevAll().length;
      		
      		var pkUnit=$("#pkUnit").val();
      		var certTaskId=$("#certTaskId").val();
      		var returnValue=ModelWindow_dialog("listCheckPeople.do?num="+num+"&numS="+numS+"",800,600);	
      		if(returnValue == 'ok'){
   				spPageSubmit();
   			}
      	}
      	function bringback(checkp,num,numS){
      		var certPeople = "";
      		var certPeopleId="";
      		
      		if(checkp != null){
      			var checkps = checkp.split(';');
      			for(var i=0;i<checkps.length;i++){
      				var checkpss = checkps[i].split(',');
      				certPeople+=checkpss[1]+",";
      				certPeopleId+=checkpss[0]+",";
      			}
      		}
      		
      		
            
            $("#tb"+num+" tr:eq("+numS+")").find("input[name=certPeople]").get(0).value=certPeople.substring(0,certPeople.length-1);
            $("#tb"+num+" tr:eq("+numS+")").find("input[name=certPeopleId]").get(0).value=certPeopleId.substring(0,certPeopleId.length-1);
            
            
            
      	}
		</script>
	</head>
	<body >
	<div align="center"  layOutH="138">
		<form action="saveCertTaskInfo.do" name="form1" id="form1" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<input type="hidden" id="flag" value="${flag}">
		<table class="list" width="100%" id="certTable">
			<tr>
				<th height="24" colspan="5">
					武器装备科研生产单位保密资格审查意见书
				</th>
			</tr>
			<tr>
				<td colspan="2">
					单位名称
				</td>
				<td >
					<input name="certTaskInfo.pkUnit" id="pkUnit" type="hidden" value="${CertTaskInfo.pkUnit}">
					&nbsp;&nbsp;<input name="certUnitName" id="certUnitName" size="38" type="text" readonly="readonly" value="${unitInfo.unitName}">
				</td>
				<td>
					法定代表人
				</td>
				<td >
					&nbsp;&nbsp;<input name="legalRepresentative" type="text" readonly="readonly" value="${unitInfo.legalRepresentative}">
				</td>
			</tr>
			<tr>
				<td colspan="2">
					单位性质
				</td>
				<td >
					&nbsp;&nbsp;<input name="unitCategory" size="38" type="text" readonly="readonly" value="<w:CodeIdDictOut value="${unitInfo.unitCategory}"></w:CodeIdDictOut>">
				</td>
				<td>
					申请等级
				</td>
				<td  align="left">
					&nbsp;&nbsp;<select onchange="changeTextArea2()" style="width: 154" id="confidentialityLevelQualifications" name="certTaskInfo.confidentialityLevelQualifications" value="${certTaskInfo.confidentialityLevelQualifications}" dataType="Require" msg="资格等级不能为空">
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='004'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					注册地址
				</td>
				<td  align="left">
					&nbsp;&nbsp;<input name="unitLoginAddress" size="38" type="text" readonly="readonly" value="${unitInfo.unitLoginAddress}">
				</td>
				<td nowrap="nowrap">
					<c:if test="${certTaskInfo.certStatus == '012006' }">不</c:if>通过时间
				</td>
				<td  align="left">
					&nbsp;&nbsp;<input name="certTaskInfo.passedTime" id="passedTime" value="<fmt:formatDate value="${certTaskInfo.passedTime}" pattern="yyyy-MM-dd"/>" type="text" class="date required" readonly="readonly" >
				</td>
			</tr>
			<tr>
				<td colspan="2">
					通信地址
				</td>
				<td  align="left">
					&nbsp;&nbsp;<input name="unitCommunicationAddress" size="38" type="text" readonly="readonly" value="${unitInfo.unitCommunicationAddress}">
				</td>
				<td>
					邮 编
				</td>
				<td  align="left">
					&nbsp;&nbsp;<input name="zipCode" type="text" readonly="readonly" value="${unitInfo.zipCode}">
				</td>
			</tr>			
			<tr>
				<td rowspan="11" colspan="1" width="5%">
				审<br>查<br>组<br>组<br>成<br>人<br>员				
				</td>				
				<td width="20%">
					姓名
				</td>
				<td width="40%">
					单位
				</td>
				<td colspan="2" width="35%">
					职务或职称
				</td>
			</tr>
			<tr>
				<td  align="left">
					&nbsp;&nbsp;<select id="selectMemberName1" onchange="changeTextArea('1','member')" >
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='017'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					<input name="certTaskInfo.memberName1" width="100%" id="memberName1" size="9"   type="text"  value="${certTaskInfo.memberName1}">
				</td>
				<td  align="left">
				&nbsp;&nbsp;<select id="selectUnit1" onchange="changeTextArea('1','unit')" style="width: 100px">
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='018'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					<input name="certTaskInfo.memberUnit1" id="unit1" width="100%" size="30" value="${certTaskInfo.memberUnit1}"  type="text" >
				</td>
				<td  align="left" colspan="2">
				&nbsp;&nbsp;<select id="selectPosition1" onchange="changeTextArea('1','position')" value="${certTaskInfo.memberPosition1}" >
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='019'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					<input name="certTaskInfo.memberPosition1" id="position1" width="100%"  size="16"   type="text" <c:if test="${certTaskInfo.memberPosition1==null}">value="副巡视员 副主任"</c:if> <c:if test="${certTaskInfo.memberPosition1!=null}">value="${certTaskInfo.memberPosition1}"</c:if> >
				</td>
			</tr>
			<tr>
				<td  align="left">
				&nbsp;&nbsp;<select id="selectMemberName2" onchange="changeTextArea('2','member')" value="${certTaskInfo.memberName2}" >
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='017'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					<input name="certTaskInfo.memberName2" id="memberName2" width="100%" size="9"   type="text" value="${certTaskInfo.memberName2}">
				</td>
				<td  align="left">
				&nbsp;&nbsp;<select id="selectUnit2" onchange="changeTextArea('2','unit')" value="${certTaskInfo.memberUnit2}" style="width: 100px">
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='018'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					<input name="certTaskInfo.memberUnit2" id="unit2" width="100%" size="30" type="text" value="${certTaskInfo.memberUnit2}">
				</td>
				<td  align="left" colspan="2">
				&nbsp;&nbsp;<select id="selectPosition2" onchange="changeTextArea('2','position')" value="${certTaskInfo.memberPosition2}" >
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='019'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					<input name="certTaskInfo.memberPosition2" id="position2" width="100%"  size="16"   type="text" <c:if test="${certTaskInfo.memberPosition2==null}">value="参  谋"</c:if> <c:if test="${certTaskInfo.memberPosition2!=null}">value="${certTaskInfo.memberPosition2}"</c:if> >
				</td>
			</tr>
			<tr>
				<td  align="left">
					&nbsp;&nbsp;<select id="selectMemberName3" onchange="changeTextArea('3','member')" value="${certTaskInfo.memberName3}" >
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='017'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					<input name="certTaskInfo.memberName3" id="memberName3" width="100%" size="9"   type="text" value="${certTaskInfo.memberName3}">
				</td>
				<td  align="left">
				&nbsp;&nbsp;<select id="selectUnit3" onchange="changeTextArea('3','unit')" value="${certTaskInfo.memberUnit3}" style="width: 100px">
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='018'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					<input name="certTaskInfo.memberUnit3" id="unit3" width="100%" size="30" type="text" value="${certTaskInfo.memberUnit3}">
				</td>
				<td  align="left" colspan="2">
				&nbsp;&nbsp;<select id="selectPosition3" onchange="changeTextArea('3','position')" value="${certTaskInfo.memberPosition3}" >
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='019'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					<input name="certTaskInfo.memberPosition3" id="position3" width="100%"  size="16"   type="text" <c:if test="${certTaskInfo.memberPosition3==null}">value="高  工"</c:if> <c:if test="${certTaskInfo.memberPosition3!=null}">value="${certTaskInfo.memberPosition3}"</c:if> >
				</td>
			</tr>
			<tr>
				<td  align="left">
				&nbsp;&nbsp;<select id="selectMemberName4" onchange="changeTextArea('4','member')" value="${certTaskInfo.memberName4}" >
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='017'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					<input name="certTaskInfo.memberName4" id="memberName4" width="100%" size="9"   type="text" value="${certTaskInfo.memberName4}">
				</td>
				<td  align="left">
				&nbsp;&nbsp;<select id="selectUnit4" onchange="changeTextArea('4','unit')" value="${certTaskInfo.memberUnit4}" style="width: 100px" >
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='018'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					<input name="certTaskInfo.memberUnit4" id="unit4" width="100%" size="30" type="text" value="${certTaskInfo.memberUnit4}">
				</td>
				<td  align="left" colspan="2">
				&nbsp;&nbsp;<select id="selectPosition4" onchange="changeTextArea('4','position')" value="${certTaskInfo.memberPosition4}" >
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='019'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					<input name="certTaskInfo.memberPosition4" id="position4" width="100%"  size="16"   type="text" <c:if test="${certTaskInfo.memberPosition4==null}">value="处  长"</c:if> <c:if test="${certTaskInfo.memberPosition4!=null}">value="${certTaskInfo.memberPosition4}"</c:if> >
				</td>
			</tr>
			<tr>
				<td  align="left">
				&nbsp;&nbsp;<select id="selectMemberName5" onchange="changeTextArea('5','member')" value="${certTaskInfo.memberName5}" >
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='017'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					<input name="certTaskInfo.memberName5" id="memberName5" width="100%" size="9"   type="text" value="${certTaskInfo.memberName5}">
				</td>
				<td  align="left">
				&nbsp;&nbsp;<select id="selectUnit5" onchange="changeTextArea('5','unit')" value="${certTaskInfo.memberUnit5}" style="width: 100px">
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='018'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					<input name="certTaskInfo.memberUnit5" id="unit5" width="100%" size="30" type="text" value="${certTaskInfo.memberUnit5}">
				</td>
				<td  align="left" colspan="2">
				&nbsp;&nbsp;<select id="selectPosition5" onchange="changeTextArea('5','position')" value="${certTaskInfo.memberPosition5}" >
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='019'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					<input name="certTaskInfo.memberPosition5" id="position5" width="100%"  size="16"   type="text" <c:if test="${certTaskInfo.memberPosition5==null}">value="研究员"</c:if> <c:if test="${certTaskInfo.memberPosition5!=null}">value="${certTaskInfo.memberPosition5}"</c:if> >
				</td>
			</tr>
			<tr>
				<td  align="left">
				&nbsp;&nbsp;<select id="selectMemberName6" onchange="changeTextArea('6','member')" value="${certTaskInfo.memberName6}" >
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='017'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					<input name="certTaskInfo.memberName6" id="memberName6" width="100%" size="9"   type="text" value="${certTaskInfo.memberName6}">
				</td>
				<td  align="left">
				&nbsp;&nbsp;<select id="selectUnit6" onchange="changeTextArea('6','unit')" value="${certTaskInfo.memberUnit6}" style="width: 100px">
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='018'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					<input name="certTaskInfo.memberUnit6" id="unit6" width="100%" size="30" type="text" value="${certTaskInfo.memberUnit6}">
				</td>
				<td  align="left" colspan="2">
				&nbsp;&nbsp;<select id="selectPosition6" onchange="changeTextArea('6','position')" value="${certTaskInfo.memberPosition6}" >
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='019'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					<input name="certTaskInfo.memberPosition6" id="position6" width="100%"  size="16"   type="text" <c:if test="${certTaskInfo.memberPosition6==null}">value="处  长"</c:if> <c:if test="${certTaskInfo.memberPosition6!=null}">value="${certTaskInfo.memberPosition6}"</c:if> >
				</td>
			</tr>
			<tr>
				<td  align="left">
					&nbsp;&nbsp;<select id="selectMemberName7" onchange="changeTextArea('7','member')" value="${certTaskInfo.memberName7}" >
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='017'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					<input name="certTaskInfo.memberName7" id="memberName7" width="100%" size="9"   type="text" value="${certTaskInfo.memberName7}">
				</td>
				<td  align="left">
					&nbsp;&nbsp;<select id="selectUnit7" onchange="changeTextArea('7','unit')" value="${certTaskInfo.memberUnit7}" style="width: 100px" >
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='018'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					<input name="certTaskInfo.memberUnit7" id="unit7" width="100%" size="30" type="text" value="${certTaskInfo.memberUnit7}">
				</td>
				<td  align="left" colspan="2">
				&nbsp;&nbsp;<select id="selectPosition7" onchange="changeTextArea('7','position')" value="${certTaskInfo.memberPosition7}" >
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='019'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					<input name="certTaskInfo.memberPosition7" id="position7" width="100%"  size="16"   type="text" <c:if test="${certTaskInfo.memberPosition7==null}">value="工程师"</c:if> <c:if test="${certTaskInfo.memberPosition7!=null}">value="${certTaskInfo.memberPosition7}"</c:if> >
				</td>
			</tr>
			<tr>
				<td  align="left">
				&nbsp;&nbsp;<select id="selectMemberName8" onchange="changeTextArea('8','member')" value="${certTaskInfo.memberName8}" >
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='017'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					<input name="certTaskInfo.memberName8" id="memberName8" width="100%" size="9"   type="text" value="${certTaskInfo.memberName8}">
				</td>
				<td  align="left">
				&nbsp;&nbsp;<select id="selectUnit8" onchange="changeTextArea('8','unit')" value="${certTaskInfo.memberUnit8}" style="width: 100px">
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='018'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					<input name="certTaskInfo.memberUnit8" id="unit8" width="100%" size="30" type="text" value="${certTaskInfo.memberUnit8}">
				</td>
				<td  align="left" colspan="2">
				&nbsp;&nbsp;<select  id="selectPosition8" onchange="changeTextArea('8','position')" value="${certTaskInfo.memberPosition8}" >
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='019'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					<input name="certTaskInfo.memberPosition8" id="position8" width="100%"  size="16"   type="text" <c:if test="${certTaskInfo.memberPosition8==null}">value="工程师"</c:if> <c:if test="${certTaskInfo.memberPosition8!=null}">value="${certTaskInfo.memberPosition8}"</c:if> >
				</td>
			</tr>
			<tr>
				<td  align="left">
				&nbsp;&nbsp;<select  id="selectMemberName9" onchange="changeTextArea('9','member')" value="${certTaskInfo.memberName9}" >
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='017'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					<input name="certTaskInfo.memberName9" id="memberName9" width="100%" size="9"   type="text" value="${certTaskInfo.memberName9}">
				</td>
				<td  align="left">
				&nbsp;&nbsp;<select id="selectUnit9" onchange="changeTextArea('9','unit')" value="${certTaskInfo.memberUnit9}" style="width: 100px">
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='018'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					<input name="certTaskInfo.memberUnit9" id="unit9" width="100%" size="30" type="text" value="${certTaskInfo.memberUnit9}">
				</td>
				<td  align="left" colspan="2">
				&nbsp;&nbsp;<select  id="selectPosition9" onchange="changeTextArea('9','position')" value="${certTaskInfo.memberPosition9}" >
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='019'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					<input name="certTaskInfo.memberPosition9" id="position9" width="100%" size="16"   type="text" <c:if test="${certTaskInfo.memberPosition9==null}">value="工程师"</c:if> <c:if test="${certTaskInfo.memberPosition9!=null}">value="${certTaskInfo.memberPosition9}"</c:if> >
				</td>
			</tr>
			<tr>
				<td  align="left">
				&nbsp;&nbsp;<select  id="selectMemberName10" onchange="changeTextArea('10','member')" value="${certTaskInfo.memberName10}" >
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='017'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					<input name="certTaskInfo.memberName10" id="memberName10" width="100%" size="9"   type="text" value="${certTaskInfo.memberName10}">
				</td>
				<td  align="left">
					&nbsp;&nbsp;<select  id="selectUnit10" onchange="changeTextArea('10','unit')" value="${certTaskInfo.memberUnit10}" style="width: 100px">
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='018'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					<input name="certTaskInfo.memberUnit10" id="unit10" width="100%" size="30" type="text" value="${certTaskInfo.memberUnit10}">
				</td>
				<td  align="left" colspan="2">
				&nbsp;&nbsp;<select  id="selectPosition10" onchange="changeTextArea('10','position')" value="${certTaskInfo.memberPosition10}" >
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='019'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					<input name="certTaskInfo.memberPosition10" id="position10" width="100%" size="16"   type="text" <c:if test="${certTaskInfo.memberPosition10==null}">value="工程师"</c:if> <c:if test="${certTaskInfo.memberPosition10!=null}">value="${certTaskInfo.memberPosition10}"</c:if> >
				</td>
			</tr>


			<c:if test="${certTaskInfo.certType == '013001'}">
			<tr>
				<td colspan="1" width="20%">
				审<br>查<br>工<br>作<br>简<br>况				
				</td>
				<td colspan="4"  align="left">
					<textarea rows="8" cols="120" id="textarea3" name="certTaskInfo.textarea3">${textarea3 }</textarea>			
				</td>
			</tr>
			<c:forEach items="${certConfigList}" var="item" varStatus="count">
				<tr>
					<td  align="left" colspan="5">
						${item.certName}
						（分值
						${item.certPoint}
						分）：<br>
						
						
						
						<table id="tb${count.count}">
							<tr>
								<td colspan="3"></td>
								<td colspan="3">
									<input name="BtAdd" type="button" value="增加扣分项" id="${count.count}" />	
									<input name="BtDel" type="button" value="删除扣分项" id="${count.count}" />
								</td>
							</tr>
							<tr>
								<td colspan="3" width="30%" nowrap="nowrap">发现的主要问题是：</td>
								<td width="45%">审查规则</td>
								<td width="15%">扣分</td>
								<!-- <td width="10%">操作</td> -->
							</tr>
							<tr style="display:none">
								<td width="5%" class="td_text_align">
									
								</td>
								<td width="5%" class="td_text_align">
									<input type="hidden" name="certId" value="${item.certId}">
			                    	<input id="CK" type="checkbox" name="CK"/>
			                    </td>
								<td>
									<select name="chooseRule"  id="${count.count}" onchange="putDataIn(this.id)" style="border-bottom-style:none">
										<option  value="0">---选择已有审查规则---</option>
										<c:forEach items="${certConfigRuleList}" var="obj">
											<c:if test="${obj.certId == item.certId}">
											 <option value="${obj.ruleId}">${obj.ruleNo}${obj.ruleContent}</option>
											</c:if>
										</c:forEach>
									</select>
									<input name="ruleId" type="hidden" id="ruleId${count.count}" value="">
								</td>
								<td>
									<input name="scoreRule" class="noborderInput" size="55" value="${each.scoreRule}">
									问题表象：	
									<input name="detailContent" id="detailContent${count.count}" width="100%" size="50" type="text" value="${each.detailContent}">
									<!-- <input name="button4" type="button" id="button4${count.count}" class="btn_standard" value="发现人员" onclick="checkPeoples(this,${count.count})"/>
									<input name="certPeople" id="certPeople${count.count}" width="100%" size="30" type="text" value="">
									<input name="certPeopleId" id="certPeopleId${count.count}" width="100%" size="30" type="hidden" value=""> -->
								</td>
								<td>
									扣<input  name="scoreNumber" id="${count.count}" onchange="scoreNumberOnChange(this.id)" class="noborderInput" size="2">分
								</td> 
							 <!-- 	<td  align="left" width="60%">
							 		<input name="imgId" type="hidden" id="fileId${count.count}${status.count}" value="${each.imgId }"/>
									<input name="button1" type="button" class="btn_standard" value="上传图片①" onclick="upLoadFile(${each.certId},${count.count},${status.count},'①')"/>
									
									<input name="button2" type="button" class="btn_standard" value="上传图片②" onclick="upLoadFile(${each.certId},${count.count},${status.count},'②')"/>
									
									<input name="button3" type="button" class="btn_standard" value="上传图片③" onclick="upLoadFile(${each.certId},${count.count},${status.count},'③')"/>
								</td> -->
							</tr>
							<c:forEach items="${scoreList}" var="each" varStatus="status">
								<c:if test="${each.certId==item.certId}">
									<tr >
										<td width="5%" class="td_text_align">
										
										<br></td>
										<td width="5%" class="td_text_align">
											<input type="hidden" name="certId" value="${each.certId}">
					                    	<input id="CK" type="checkbox" name="CK"/>
					                    </td>
										<td>
											<select class="inputLength" style="border-bottom-style:none" name="chooseRule" id="${count.count}" onchange="putDataIn(this.id)" >
												<option value="0">---选择已有审查规则---</option>
												<c:forEach items="${certConfigRuleList}" var="obj">
													<c:if test="${obj.certId == item.certId}">
													 <option value="${obj.ruleId}" <c:if test="${obj.ruleId == each.ruleId}">selected</c:if>>${obj.ruleNo}${obj.ruleContent}</option>
													 </c:if>
												</c:forEach>
											</select>
											<input name="ruleId" type="hidden" id="ruleId${count.count}${status.count}" value="${each.ruleId}">
										</td>
										<td>
											<input name="scoreRule" class="noborderInput" size="55" value="${each.scoreRule}">
											问题表象：	
											<input name="detailContent" id="detailContent${count.count}" width="100%" size="50" type="text" value="${each.detailContent}">
											<!-- <input name="button4" type="button" id="button4${count.count}" class="btn_standard" value="发现人员" onclick="checkPeoples(this,${count.count})"/>
											<input name="certPeople" id="certPeople${count.count}" width="100%" size="30" type="text" value="">
											<input name="certPeopleId" id="certPeopleId${count.count}" width="100%" size="30" type="hidden" value=""> -->
										</td>
										<td>
											扣<input name="scoreNumber" id="${count.count}" value="${each.scoreNumber}" onchange="scoreNumberOnChange(this.id)" class="noborderInput" size="2">分
										</td>
											
									
										<!-- <td  align="left" width="60%">
											<input name="imgId" id="fileId${count.count}${status.count}" type="hidden" value="${each.imgId }" />
											<input name="button1" type="button" class="btn_standard" value="上传图片①" onclick="upLoadFile(${each.certId},${count.count},${status.count},'①')"/>
											
											<input name="button2" type="button" class="btn_standard" value="上传图片②" onclick="upLoadFile(${each.certId},${count.count},${status.count},'②')"/>
											
											<input name="button3" type="button" class="btn_standard" value="上传图片③" onclick="upLoadFile(${each.certId},${count.count},${status.count},'③')"/>
										</td>
										 -->
									</tr>   
								</c:if>
							</c:forEach>
						</table>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						得：<input type="hidden" name="localTotal${count.count}" id="${count.count}" value="${item.certPoint}"/>
						<input type="text" name="realLocalTotal${count.count}" size="2" readonly="readonly" value="${item.certPoint}" class="noborderInput" style="border-bottom-style:none"/>
						分
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td align="left" colspan="5">
					总评：<br>
					<textarea name="certTaskInfo.textarea1" rows="5" cols="100"><c:if test="${certTaskInfo.textarea1==null}">    公司领导对保密工作高度重视，组织机构健全，保密责任制落实，在涉密人员管理、宣传报道、外场试验、协作配套、涉外活动等方面管理严格。但在涉密载体和计算机及信息系统保密管理等方面，还存在一定差距，需要进一步整改。</c:if><c:if test="${certTaskInfo.textarea1!=null}">${certTaskInfo.textarea1}</c:if></textarea>
				</td>
			</tr>
			<tr>
				<td align="left" colspan="5">
					审查组意见：<br>
					&nbsp;&nbsp;&nbsp;&nbsp;
					经研究，${unitInfo.unitName}申请理由充分，现场审查实有项目总分值
					<input type="text" name="total" size="2" readonly="readonly" class="noborderInput" style="border-bottom-style:none"/>
					分，实际得分
					<input type="text" name="certTaskInfo.passedScore" id="passedScoreAdd" size="2" readonly="readonly" value="${certTaskInfo.passedScore}" class="noborderInput" style="border-bottom-style:none"/>
					分。
					<textarea name="certTaskInfo.textarea2" rows="2" cols="100"><c:if test="${certTaskInfo.textarea2!=null}">${certTaskInfo.textarea2}</c:if><c:if test="${certTaskInfo.textarea2==null}">符合标准同意报国家军工保密资格认证委批准为一级保密资格单位。</c:if></textarea>
					<br>&nbsp;&nbsp;&nbsp;&nbsp;
					审查组组长（签名）：
					<br><br>&nbsp;&nbsp;&nbsp;&nbsp;					
				</td>
			</tr>
			<tr>
				<td align="left" colspan="5">
					被审查单位意见：
					<br><br><br>&nbsp;&nbsp;&nbsp;&nbsp;
					单位负责人（签名）：
					<br><br>&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
			</c:if>
			<c:if test="${certTaskInfo.certType == '013002'}">
				<tr>
					<td colspan="1" width="20%">
					复<br>查<br>情<br>况				
					</td>
					<td colspan="4"  align="left">
						<textarea rows="8" cols="120" id="textarea4" name="certTaskInfo.textarea4">${textarea4 }</textarea>			
					</td>
				</tr>
				<tr>
					<td colspan="1" width="20%" >
					存<br>在<br>的<br>主<br>要<br>问<br>题				
					</td>
					<td colspan="4" >
						<textarea rows="8" cols="120" id="textarea5" name="certTaskInfo.textarea5">${certTaskInfo.textarea5 }</textarea>			
					</td>
				</tr>
				<tr>
					<td colspan="1" width="20%">
					复<br>查<br>意<br>见				
					</td>
					<td colspan="4"  align="left">
						<textarea rows="8" cols="120" id="textarea6" name="certTaskInfo.textarea6">${textarea6 }</textarea>			
					</td>
				</tr>
			</c:if>
			<tr>
				<td colspan="5">
					<input name="button" type="submit" class="btn_standard" value="保存">
					<input name="certTaskInfo.certTaskId" type="hidden" value="${certTaskInfo.certTaskId}" id="certTaskId"/>
					<input name="certTaskInfo.certType" type="hidden" value="${certTaskInfo.certType}" />
					<input name="certTaskInfo.certStatus" type="hidden" value="${certTaskInfo.certStatus}" />
					<input name="certTaskEdit" value="${param.certTaskEdit}" type="hidden"/>	
					<!--   <c:if test="${certTaskUpdate!=1}">-->
					<!--   	<input name="button" type="reset" class="btn_standard" value="重置" onclick="resetPage()">-->
					<!--   </c:if>-->
					<c:if test="${certTaskInfo.certStatus=='012003' || certTaskInfo.certStatus=='012004' || certTaskInfo.certStatus=='012005'}">
						<input name="exportButton" id="exportButton" type="button" class="btn_standard" value="导出" onclick="setIsOut()" >
					</c:if>
				</td>
			</tr>
		</table>
		
		</form>
	</div>
	</body>
</HTML>