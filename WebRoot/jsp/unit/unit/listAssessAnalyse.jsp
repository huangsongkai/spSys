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
		
		<div class="searchBar" >
			<table class="searchContent"  width="100%" id="table1">							
				<tr>
					<td>
						统计年份 	
					</td>  
					<td class="left_align">
					<input id="statDateFromassess" type="text"  value="${statDateFrom}" class="date" dateFmt="yyyy">至
					<input type="text" id="statDateToassess"  value="${statDateTo}"  class="date" dateFmt="yyyy">
					</td>
					
					<td>
						考核人员名称	
					</td> 
					<td> 
					<select id="selectMemberName" >
						<option></option>
						<c:forEach items="${applicationScope.CodeDict}" var="item">
						 	<c:if test="${item.parentCodeId=='017'}">  
							 		<option value="${item.codeId}" <c:if test="${item.codeId==certTaskInfo.confidentialityLevelQualifications}">selected</c:if> >${item.codeName}</option>
						 	</c:if>
						</c:forEach>
					</select>
					</td>
				
				<tr>
						<td>
						<input type="button" name="button" onclick="showResult1()"  value="人数统计" >     
						</td>
				</tr>	
				
			</table>
		 </div>
			 <div  id="highchartDivCheck4" ></div>
	</form>
</div>
<script language="javascript">           
 $(function () {
        $('#highchartDivCheck4',navTab.getCurrentPanel()).highcharts({
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

    
	
        function showResult1(){  
        	var statDateFrom = $("#statDateFromassess",navTab.getCurrentPanel()).val();
	        var statDateTo = $("#statDateToassess",navTab.getCurrentPanel()).val();
	        var selectMemberName = $("#selectMemberName",navTab.getCurrentPanel()).val();     
			$("#highchartDivCheck4",navTab.getCurrentPanel()).loadUrl("AssessScoreDetailQuery.do?statDateFrom="+statDateFrom+"&statDateTo="+statDateTo+"&selectMemberName="+selectMemberName);
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