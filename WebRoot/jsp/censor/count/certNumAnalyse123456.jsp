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
		
		<div class="pageFormContent" >
			<table class="searchContent"  width="100%" id="table1">							
				<tr>
					<td>
						年度 	
					</td>  
					<td class="left_align">
					<input id="timeFrom" type="text"  value="${timeFrom}" class="date" dateFmt="yyyy">
					<input type="text" id="timeTo"  value="${timeTo}"  class="date" dateFmt="yyyy">
					</td>
				
				<tr>
						<td>
						<input type="button" name="button" onclick="showResult()"  value="人数统计" >     
						</td>
				</tr>	
				
			</table>
		 </div>
			 <div  id="highchartDivCheck2" ></div>
	</form>
</div>
<script language="javascript">           
 $(function () {    
        $('#highchartDivCheck2').highcharts({  
        
            chart: {
                type: 'column'
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
            title: {
                text: '工作量统计分析'      
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                categories: '',  
                labels:{
	            	rotation:0
	            },
                tickmarkPlacement: 'on',
                title: {
                    enabled: false
                }
            },
            yAxis: {
                title: {
                    text: '次数'
                },
                labels: {
                    formatter: function() {
                        return this.value;
                    }
                }
            },
            tooltip: {
                shared: true,
                valueSuffix: ' 次数'
            },
            plotOptions: {
                area: {
                    stacking: 'normal',
                    lineColor: '#666666',
                    lineWidth: 1,
                    marker: {
                        lineWidth: 1,
                        lineColor: '#666666'
                    }
                }
            },
            series: ${yBars}   
        });
    });

    
	
        function showResult(){  
        	alert("1113")
	       	var timeTo=$("#timeTo").val();  
	       	var timeFrom=$("#timeFrom").val();    
			$("#highchartDivCheck2").loadUrl("certNumCount.do?timeTo="+timeTo+"&timeFrom="+timeFrom+"");
        }
        
	</script>