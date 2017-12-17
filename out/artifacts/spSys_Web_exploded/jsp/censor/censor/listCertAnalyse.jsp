<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.xunj_prj/tag/webwidget" prefix="w"%>
<%@ include file="../../../common/include.jsp"%>
<%@ include file="../../../common/include2.jsp"%> 
<script src="${pageContext.request.contextPath}/common/highChart/js/highcharts.js" type="text/javascript"></script>
<div class="pageHeader" >
	<form id="pagerForm" action=""  method="post"  onsubmit="return navTabSearch(this);">    
		<input type="hidden" id="type" name="type" value="${type}" />
		
		<div class="pageFormContent"  >
			<table class="searchContent"  width="100%" id="table1">							
				<tr>
					<td>
						集团
					</td>  
					<td class="left_align">
					<select name="unitInfo.pkUnita" class="inputLength" id="pkUnita"  onchange="ajaxUnit(this.value,'b')">
						<option></option>
						<c:forEach items="${listGroup}" var="item">
							<option value="${item.unitNo}" <c:if test="${item.pkUnit==unitInfo.pkUnit}">selected</c:if> >${item.unitName}</option>
						</c:forEach>
					</select>
					</td>
					
					<td>
						下属单位
					</td>
					<td class="left_align">
					<select name="unitInfo.pkUnitb" id="pkUnitb"  onchange="ajaxUnit(this.value,'c')">
					<option> </option>
					</select>
					</td>
				</tr>	
				<tr>
					<td>
					    下属单位
					</td>
						<td>
					<select name="unitInfo.pkUnitc" id="pkUnitc"  onchange="ajaxUnit(this.value,'d')">
					<option> </option>
					</select>
						</td>
						
					<td>下属单位</td>
					<td>
					<select name="unitInfo.pkUnitd" id="pkUnitd" class="inputLength" >
					<option> </option>
					</select>
					</td>
					
				
					  
					
				</tr>
				<tr>
						<td>
						<input name="button" type="button"  onclick="showResult();" value="查询" >     
						</td>
				</tr>	
				
			</table>
		 </div>
			 <div  id="highchartDivCheck14" ></div>
	</form>
</div>
    <script language="javascript" >
  $(function () {
        $('#highchartDivCheck14').highcharts({  
            title: {
                text: ${name},
                x: -20 //center
            },
             colors:[
	            'red',//第一个颜色，欢迎加入Highcharts学习交流群294191384
	            'blue',//第二个颜色
	            'yellow',//第三个颜色
	            '#1aadce', //。。。。
	            '#492970',
	            '#f28f43', 
	            '#77a1e5', 
	            '#c42525', 
	            '#a6c96a'
	        ],
            subtitle: {
                text: '',
                x: -20
            },
            xAxis: {
                categories: ${histogram}
            },
            yAxis: {
                title: {
                    text: '分数 (分)'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            tooltip: {
                valueSuffix: '分'
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle',
                borderWidth: 0
            },
            series: ${yBar}
        });
    });
    
	
        function showResult(){
	       var pkUnita = $("#pkUnita").val();
	        var pkUnitb = $("#pkUnitb").val();
	        var pkUnitc = $("#pkUnitc").val();
	        var pkUnitd = $("#pkUnitd").val();
	        var certType = $("#certType").val();
	        var year = $("#year").val();
	       	var yearTo = $("#yearTo").val();
			$("#highchartDivCheck14").loadUrl("listCertAnalyse.do?type=score&pkUnita="+pkUnita+"&pkUnitb="+pkUnitb+"&pkUnitc="+pkUnitc+"&pkUnitd="+pkUnitd+"&year="+year+"&certType="+certType+"&yearTo="+yearTo,"","");  
        }
        
       	function ajaxUnit(num,str){
           $.ajax({
				async: false,
				type : "POST",
				url : 'ajaxData.do?type=group&str='+str+'&id='+num,
				success : function(data) {
					var units = data.split(";");
					$("#pkUnit"+str+"").empty();
					$("#pkUnit"+str+"").append("<option></option>");
					for(var i=0;i<units.length-1;i++){
						var unit = units[i].split(",");
						$("#pkUnit"+str+"").append("<option value='"+unit[0]+"'>"+unit[1]+"</option>");
					}
					document.getElementById("pkUnit"+str+"").onchange();
				}
			});
        }    
          
	</script>