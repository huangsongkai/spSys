<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../../../common/include.jsp"%>
<script language="javascript">
	function ajaxUnit(num,str){
           $.ajax({
				async: false,
				type : "POST",
				url : 'ajaxData.do?type=group&str='+str+'&id='+num,
				success : function(data) {
					var units = data.split(";");
					$("#pkUnit"+str+"",navTab.getCurrentPanel()).empty();
					$("#pkUnit"+str+"",navTab.getCurrentPanel()).append("<option></option>");
					for(var i=0;i<units.length-1;i++){
						var unit = units[i].split(",");
						$("#pkUnit"+str+"",navTab.getCurrentPanel()).append("<option value='"+unit[0]+"'>"+unit[1]+"</option>");
					}
					document.getElementById("pkUnit"+str+"",navTab.getCurrentPanel()).onchange();
				}
			});
        }
        function dataExp(){
        	if($("#unitName",navTab.getCurrentPanel()).val() != "" ){
	        	var pkUnit=$('#pkUnit',navTab.getCurrentPanel()).val();
				with (document.form1)
				{
					enctype="multipart/form-data";
					action="dataExpWord.do?pkUnit="+pkUnit;
					submit();
				}
        	}else{
        		alertMsg.info("请输入单位名称")
        	}
        	
			
        }
        function dataImp(){
   			$.pdialog.open("sqlImport.do?1=1","sqlImport","上传文件",{width:800,height:600});	
   			
   		}
   		$(function(){
			var jsonStr = $('#list_noJSON_die').val();
			$('#unitName',navTab.getCurrentPanel()).jsonSuggest(eval(jsonStr),{onSelect:checkedListCallback, maxResults:10});
		})

		function checkedListCallback(item){
			$('#unitName',navTab.getCurrentPanel()).val(item.text.split("，")[2]);
			$('#pkUnit',navTab.getCurrentPanel()).val(item.text.split("，")[0]);
		}
		function showDiv(){
			var unitName=$("#unitName",navTab.getCurrentPanel()).val();
			$.pdialog.open("listOneUnitInfo.do?unitName="+unitName,"listOneUnitInfo1","详细",{width:800,height:600});	
		}
</script>

<div class="pageHeader">
	<form action="" name="form1" method="post" onsubmit="return navTabSearch(this);">
		<table width="100%" >
			<tr>
				<td class="" width="7%" >
					&nbsp;&nbsp;军工单位名称:
					<input type="hidden" id="list_noJSON_die" value="${unitData}">
				</td>
				<td>
					&nbsp;&nbsp;<input type="text" size="50" name="unitName" id="unitName" class="inputLength" value="${unitName}" dataType="Require" msg="单位名称不能为空">
					<input type="button" value="详细" onclick="showDiv()">
					<input type="hidden" name="pkUnit" id="pkUnit" value="${pkUnit}" >
				</td>
			</tr>
			<tr>
				<td colspan="" align="center">
					<input name="button" onclick="dataExp();" type="button" class="btn_standard" value="  数据导出 " >
				</td>
				<td>
					<input name="button" onclick="dataImp();" type="button" class="btn_standard" value="  数据导入 " >
				</td>
			</tr>
			
			<tr>
				<td colspan="" align="left"height="500px">
				      <iframe name="ifrmId" id="ifrmId" src="" marginwidth="300" width="100%" height="750px" frameborder="0" scrolling="no" style="display: none" ></iframe>
				</td>
			</tr>
			
  			</table>
	</form>
</div>
<script type="text/javascript">

</script>

