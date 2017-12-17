<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="../../../common/include.jsp"%>
<%@ include file="../../../common/include2.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>Highcharts Example</title>
 
		<script type="text/javascript">
$(function () {

// Radialize the colors
		Highcharts.getOptions().colors = Highcharts.map(Highcharts.getOptions().colors, function(color) {
		    return {
		        radialGradient: { cx: 0.5, cy: 0.3, r: 0.7 },
		        stops: [
		            [0, color],
		            [1, Highcharts.Color(color).brighten(-0.3).get('rgb')] // darken
		        ]
		    };
		});
    $('#container').highcharts({
     //credits: '啊啊啊啊',
        //图表区选项
         credits:  //设置右下角的标记。highchart.com (这个也可以在highcharts.js里中修改)
	    {    
	        enabled: false,      //是否显示
	        
	    },
        
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        //标题选项
        title: {
            text: '${name}',
	        //x: -20,                  //center //水平偏移量 
	        //y: 100                   //y:垂直偏移量
	        //align: 'right'         //水平方向（left, right, bottom, top）
	       // floating: true,          //是否浮动显示
	       // margin:20,
	        style: {
	        	color:  'red',
  			}

	       // verticalAlign: "top"    //垂直方向（left, right, bottom, top）
        },
        //数据点提示框
        tooltip: {
           // pointFormat: '{point.name}<br/>{series.name}: <b>{point.percentage:.1f}%</b>',
            formatter: function (){
            	var strss = "${viewStrs}";
            	var strs = strss.split(",");
            	for(var i=0;i<strs.length;i++){
            		var str = strs[i].split(":");
            		if(this.point.name==str[0]){
            			strName = str[1];
            			break;
            		}
            	}
                return this.point.name + '<br/>'+strName+'<br/>'+this.series.name+': <b>' + Highcharts.numberFormat(this.percentage, 0) + '%</b>';
            },
            percentageDecimals: 1,
            
	        shadow: true,              //提示框是否应用阴影  ?没有看出明显效果?????????
	        //shared: true,               //当打开这个属性，鼠标几个某一区域的时候，如果有多条线，所有的线上的据点都会有响应(ipad
	        style: {  //提示框内容的样式
	            //color: 'white',
	            padding: '10px',    //内边距 (这个会常用到)
	            fontSize: '12px',            
        	}
        },
         
        //数据点选项
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    color: '#000000',
                    connectorColor: '#000000',
                    format: '<b>{point.name}</b>: {point.percentage:.1f}%'
                },
                events:{//监听点的鼠标事件  
                    click: function(e) {
                    	$("#detail").empty();
                    	$("#detail").append("<tr><td>扣分规则</td><td>单位名称</td><td>扣分</td></tr>");
	                    var strss = "${divScore}";
		            	var strs = strss.split(";");
		            	for(var i=0;i<strs.length;i++){
		            		var str = strs[i].split(",");
		            		if(e.point.name==str[0]){
		            			if(str[1] == 'null'){
		            				$("#detail").append("<tr><td>"+str[0]+"</td><td>"+str[2]+"</td><td> </td></tr>");
		            			}else{
		            				$("#detail").append("<tr><td>"+str[0]+"</td><td>"+str[2]+"</td><td>扣"+str[1]+"分</td></tr>");
		            			}
		            			
		            		}
		            	}
                    }  
                }
            }
           

            
        },
        //数据列选项
        series: [{
            type: 'pie',
            name: '该扣分项扣分占全部扣分项扣分的百分比',
            data: ${yBar}
        }]
    });
});

 
		</script>
	</head>
	<body>
 
<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
<div id="detail" style="min-width: 310px; height: 300px; margin: 0 auto;overflow:scroll"></div>
 
	</body>
</html>
