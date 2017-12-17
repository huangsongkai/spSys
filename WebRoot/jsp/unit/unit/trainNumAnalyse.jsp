<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/include.jsp"%>
<script src="${pageContext.request.contextPath}/common/highChart/js/highcharts.js" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8">
$(function () {
    $('#highchartDiv').highcharts({
        chart: {
            zoomType: 'xy'
        },
        title: {
            text: '温湿度曲线'
        },
        subtitle: {
            text: ''
        },
        xAxis: [{
            categories: ${xAxis},
            labels: { rotation: -45}
        }],
        yAxis: [{ // Primary yAxis
            labels: {
                format: '{value}°C',
                style: {
                    color: '#89A54E'
                }
            },
            title: {
                text: '温度',
                style: {
                    color: '#89A54E'
                }
            }
        }, { // Secondary yAxis
            title: {
                text: '湿度',
                style: {
                    color: '#4572A7'
                }
            },
            labels: {
                format: '{value} %',
                style: {
                    color: '#4572A7'
                }
            },
            opposite: true
        }],
        tooltip: {
            shared: true
        },
        legend: {
            layout: 'vertical',
            align: 'left',
            x: 120,
            verticalAlign: 'top',
            y: 100,
            floating: true,
            backgroundColor: '#FFFFFF'
        },
        series: ${yAxis}
    });
});

</script>
<div class="pageHeader">
<form action="" id="pagerForm"  name="pagerForm"  method="post" onsubmit="return navTabSearch(this);">
		<div class="pageFormContent" layoutH="700">
			<!-- 	<p>
					<label>选择统计因素：</label>
					<select name="selCondition"  id="selCondition"> 
							<option value="">--请选择--</option> 
							<option value="W" <c:if test="${selCondition=='W'}">selected</c:if>>温度</option> 
							<option value="S" <c:if test="${selCondition=='S'}">selected</c:if>>湿度</option>
					</select>
				</p> -->
				<p>
					<label>库房：</label>
					<select  class="combox" name="house" id="house" style="width:150px"  ref="locationReport" refUrl="ajaxQueryLocation.do?id={value}">
						<option value="">请选择</option>     	
						<c:forEach items="${dataList}" var="obj" varStatus="i">
						
						<option  value="${obj.houseId }" <c:if test="${obj.houseId == house}">selected="selected"</c:if> >${obj.houseName}</option>
						
						</c:forEach>
					</select>
				</p>
				<p>
					<label>点位：</label>
					<select  class="combox"  id="locationReport" name="locationReport" style="width:150px" value="${locationReport}">
						<option  value="">请选择</option>     	
						
					</select>
				</p>
				<p>
					<label>时间：</label>
					<input type="text" class="date" id="timePeport" >
				</p>
				<div class="divider"></div>
				
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button  type="button" onclick="ajaxSubmit()" >查询</button></div></div></li>
				</ul>
				<div class="divider"></div>
			</div>
			<div  id="highchartDiv" ></div>
		</form>
</div>
<script type="text/javascript">
function ajaxSubmit(){
	if($("#house").val()==""){
		alertMsg.warn("请选择库房！")
		return false;
	}
	if($("#locationReport").val()==""){
		alertMsg.warn("请选择点位！")
		return false;
	}
	if($("#timePeport").val()==""){
		alertMsg.warn("请选择时间！")
		return false;
	}

	$("#highchartDiv").loadUrl('trainNumAnalyse.do?timePeport='+$("#timePeport").val()+'&locationReport='+$("#locationReport").val()+'&house='+$("#house").val()+'&selCondition='+$("#selCondition").val()+'','', '');
}
</script>

