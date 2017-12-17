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
		 </div>
			 <div  id="highchartDivCheck157" ></div>
			 <div  id="detail" style="min-width: 310px; height: 300px; margin: 0 auto;overflow:scroll"></div>  
	</form>
</div>
    <script language="javascript">
$(function () {

// Radialize the colors
		
    $('#highchartDivCheck157').highcharts({       
     //credits: '啊啊啊啊',
        //图表区选项
         credits:  //设置右下角的标记。highchart.com (这个也可以在highcharts.js里中修改)
	    {    
	        enabled: false,      //是否显示
	        
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
            pointFormat: '',
           
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
    
	
        function showResult(){
	       var pkUnita = $("#pkUnita").val();
	        var pkUnitb = $("#pkUnitb").val();
	        var pkUnitc = $("#pkUnitc").val();
	        var pkUnitd = $("#pkUnitd").val();
	        var certType = $("#certType").val();
	        var year = $("#year").val();
	       	var yearTo = $("#yearTo").val();
			$("#highchartDivCheck15").loadUrl("listCertAnalyse3.do?pkUnita="+pkUnita+"&pkUnitb="+pkUnitb+"&pkUnitc="+pkUnitc+"&pkUnitd="+pkUnitd+"&year="+year+"&certType="+certType+"&yearTo="+yearTo);  
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