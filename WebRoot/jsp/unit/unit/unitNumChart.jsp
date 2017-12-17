<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="../../../common/include.jsp"%>
<%@ include file="../../../common/include2.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>Highcharts Example</title>
 
		<script type="text/javascript">
$(function () {
        $('#container').highcharts({
            chart: {
                type: 'column'
            },
            title: {
                text: '${name}'
            },
            xAxis: {
                categories: ${histogram},
              
	            labels:{
	            	rotation:90
	            	
	            	
	            },
	            
            	
            },
            yAxis: {
                title: {
                    text: 'ÈËÊý'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            credits: {
                enabled: false
            },
            series: ${yBar}
        });
    });
    
 
		</script>
	</head>
	<body>
 
<div id="container" style="min-width: 310px; height: 700px; margin: 0 auto"></div>
 
	</body>
</html>
