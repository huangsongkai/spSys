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
     //credits: '��������',
        //ͼ����ѡ��
         credits:  //�������½ǵı�ǡ�highchart.com (���Ҳ������highcharts.js�����޸�)
	    {    
	        enabled: false,      //�Ƿ���ʾ
	        
	    },
        
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        //����ѡ��
        title: {
            text: '${name}',
	        //x: -20,                  //center //ˮƽƫ���� 
	        //y: 100                   //y:��ֱƫ����
	        //align: 'right'         //ˮƽ����left, right, bottom, top��
	       // floating: true,          //�Ƿ񸡶���ʾ
	       // margin:20,
	        style: {
	        	color:  'red',
  			}

	       // verticalAlign: "top"    //��ֱ����left, right, bottom, top��
        },
        //���ݵ���ʾ��
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
            
	        shadow: true,              //��ʾ���Ƿ�Ӧ����Ӱ  ?û�п�������Ч��?????????
	        //shared: true,               //����������ԣ���꼸��ĳһ�����ʱ������ж����ߣ����е����ϵľݵ㶼������Ӧ(ipad
	        style: {  //��ʾ�����ݵ���ʽ
	            //color: 'white',
	            padding: '10px',    //�ڱ߾� (����᳣�õ�)
	            fontSize: '12px',            
        	}
        },
         
        //���ݵ�ѡ��
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
                events:{//�����������¼�  
                    click: function(e) {
                    	$("#detail").empty();
                    	$("#detail").append("<tr><td>�۷ֹ���</td><td>��λ����</td><td>�۷�</td></tr>");
	                    var strss = "${divScore}";
		            	var strs = strss.split(";");
		            	for(var i=0;i<strs.length;i++){
		            		var str = strs[i].split(",");
		            		if(e.point.name==str[0]){
		            			if(str[1] == 'null'){
		            				$("#detail").append("<tr><td>"+str[0]+"</td><td>"+str[2]+"</td><td> </td></tr>");
		            			}else{
		            				$("#detail").append("<tr><td>"+str[0]+"</td><td>"+str[2]+"</td><td>��"+str[1]+"��</td></tr>");
		            			}
		            			
		            		}
		            	}
                    }  
                }
            }
           

            
        },
        //������ѡ��
        series: [{
            type: 'pie',
            name: '�ÿ۷���۷�ռȫ���۷���۷ֵİٷֱ�',
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
