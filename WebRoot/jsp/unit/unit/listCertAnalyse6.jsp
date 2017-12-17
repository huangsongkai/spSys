<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.xunj_prj/tag/webwidget" prefix="w"%>
<%@ include file="../../../common/include.jsp"%>
<%@ include file="../../../common/include2.jsp"%>  

<script src="${pageContext.request.contextPath}/common/highChart/js/highcharts.js" type="text/javascript"></script>
<div class="pageHeader" >
	<form id="pagerForm" action=""  method="post"  onsubmit="return navTabSearch(this);">    
		
		<div class="pageFormContent" >
			<table class="searchContent"  width="100%" id="table1">							
				<tr>
					<td>
					</td>  
					
					
					<td>
					</td> 
					<td> 
					</td>
				
				<tr>
						<td>
						<input type="button" name="button" onclick="showResult()"  value="统计" >     
						</td>
				</tr>	
				
			</table>
		 </div>
			 <div  id="highchartDivCheck20" ></div>   
	</form>
</div>
<script language="javascript">           
 $(function () {
        $('#highchartDivCheck20').highcharts({
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
                    text: '百分比 (%)'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            tooltip: {
                valueSuffix: '%'
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
			$("#highchartDivCheck20").loadUrl("listAssessAnalyse6.do");    
        }
        
        
        
	</script>