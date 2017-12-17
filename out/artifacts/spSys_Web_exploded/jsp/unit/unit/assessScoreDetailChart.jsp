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
            title: {
                text: ${name},
                x: -20 //center
            },
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
    
 
		</script>
	</head>
	<body>
 
<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
 
	</body>
</html>
