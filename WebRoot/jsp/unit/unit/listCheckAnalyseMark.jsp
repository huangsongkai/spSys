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
					<td>检查任务名称</td>
						<td>
							<select name="checkId" class="inputLength" id="checkId" >
								<c:forEach items="${listConfig}" var="item">
									<option value="${item.checkId}"  >${item.checkName}</option>
								</c:forEach>
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
					
					<td>年度</td>
					<td>
						<input name="year" size="10" class="date" dateFmt="yyyy"  id="year"/>至<input   class="date" dateFmt="yyyy" name="yearTo" size="10" id="yearTo"/>
					</td>
					<td>检查任务名称</td>
						<td>
							<select name="checkId"  id="checkId" ">
								<option value=""  ></option>
								<c:forEach items="${listConfig}" var="item">
									<option value="${item.checkId}"  >${item.checkName}</option>
								</c:forEach>
							</select>
						</td>
				</tr>
				<tr>
						<td>
						<input name="button" type="button"  onclick="showResult('param4');" class="btn_standard" value="查询" >
						</td>
				</tr>	
				
			</table>
		 </div>
		<div  id="highchartDivCheck2" ></div>
		<div  id="detail" style="min-width: 310px; height: 300px; margin: 0 auto;overflow:scroll">
			 <table id="detail2" border="1" class="list"></table>
		</div>  
	</form>
</div>
    <script language="javascript">
    $(function () {
    	Highcharts.getOptions().colors = Highcharts.map(Highcharts.getOptions().colors, function(color) {
		    return {
		        radialGradient: { cx: 0.5, cy: 0.3, r: 0.7 },
		        stops: [
		            [0, color],
		            [1, Highcharts.Color(color).brighten(-0.3).get('rgb')] // darken
		        ]
		    };
		});
    $('#highchartDivCheck2').highcharts({
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
                    	$("#detail2").empty();
                    	$("#detail2").append("<tr><td>扣分规则</td><td>单位名称</td><td>扣分</td></tr>");
	                    var strss = "${divScore}";
		            	var strs = strss.split(";");
		            	for(var i=0;i<strs.length;i++){
		            		var str = strs[i].split("|");
		            		if(e.point.name==str[0]){
		            			if(str[1] == 'null'){
		            				$("#detail2").append("<tr><td>"+str[0]+"</td><td>"+str[2]+"</td><td> </td></tr>");
		            			}else{
		            				$("#detail2").append("<tr><td>"+str[0]+"</td><td>"+str[2]+"</td><td>扣"+str[1]+"分</td></tr>");
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
        function showResult(type1){
	        var pkUnita = $("#pkUnita").val();
	        var pkUnitb = $("#pkUnitb").val();
	        var pkUnitc = $("#pkUnitc").val();  
	        var pkUnitd = $("#pkUnitd").val();
	        var certType = $("#certType").val();
	        var year = $("#year").val();
	       	var yearTo = $("#yearTo").val();
	       	 var type = $("#type").val();
	      	var checkId = $("#checkId").val();
			$("#highchartDivCheck2").loadUrl("listCheckAnalyse.do?type=mark&checkId="+checkId+"&pkUnita="+pkUnita+"&pkUnitb="+pkUnitb+"&pkUnitc="+pkUnitc+"&pkUnitd="+pkUnitd+"&year="+year+"&type="+type+"&certType="+certType+"&yearTo="+yearTo,"", "");
        }
        
	</script>