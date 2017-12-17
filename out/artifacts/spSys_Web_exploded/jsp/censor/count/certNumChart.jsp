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
                text: '按年度统计审查人员认证次数'
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                categories: ${histograms},
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
    

		</script>
	</head>
	<body>

<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>

	</body>
</html>


