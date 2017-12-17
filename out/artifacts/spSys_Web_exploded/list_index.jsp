<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.xunj_prj/tag/webwidget" prefix="w"%>
<%@ include file="../../../common/include.jsp"%>
<%@ include file="../../../common/include2.jsp"%>  
<style>
table.b{
	border-right:1px solid #000000;
	border-bottom:1px solid #000000;
	border-left:1;
	border-top:1;
	font-family:"宋体";
}
table.b td {
	padding:5px;
	border-left:1px solid #c2c2c2;
	border-top:1px solid #c2c2c2;
	border-right:1px;
	border-bottom:1px;
	font-family:"宋体";
	background: white;
}

table.b th {  
	padding:15px;
	border-left:1px solid #9a9a9a;
	border-top:1px solid #9a9a9a;
	border-right:0;
	border-bottom:0;
	font-family:"宋体" ; 
	font-size:12;
	background:  #f7f7f7  ;  
}  
</style>
<div class="pageContent">
	
	<form method="post" action="updatePasswordUser.do" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
		<table class="b" width="100%">
		<th align=center colspan=3>
			点击进入各实时统计分析界面
		</th>
		<tr >
			<td>
				<a href="jsp/unit/unit/listCertAnalyse.do?type=score" target="navTab" rel="审查认证得分统计"><font color="blue" style="text-decoration:underline">审查认证得分统计</font></a> 	
			</td>	
			<td>
				<a href="jsp/unit/unit/listCertAnalyse1.do?type=mark" target="navTab" rel="审查认证扣分统计"><font color="blue" style="text-decoration:underline">审查认证扣分统计</font></a> 
			</td>	
			<td>	
				<a href="jsp/unit/unit/listCheckAnalyse.do?type=score" target="navTab" rel="保密检查得分统计"><font color="blue" style="text-decoration:underline">保密检查得分统计</font></a>
			</td>	
			
		</tr>
		<tr>	
			<td>
			 	<a href="jsp/unit/unit/listCheckAnalyse.do?type=mark" target="navTab" rel="保密检查扣分统计"><font color="blue" style="text-decoration:underline">保密检查扣分统计</font></a> 
			</td>						
			<td>
			 	<a href="jsp/unit/unit/listAssessAnalyse1.do?type=score" target="navTab" rel="现场认证考核得分统计"><font color="blue" style="text-decoration:underline">现场认证考核得分统计</font></a> 
			</td>
			<td>
			 	<a href="jsp/unit/unit/listAssessAnalyse.do?type=mark" target="navTab" rel="现场认证考核扣分统计"><font color="blue" style="text-decoration:underline">现场认证考核扣分统计</font></a> 
			</td>	
			
		</tr>
		<tr>						
				
			<td>
			 	<a href="jsp/unit/unit/unitNumAnalyse.do" target="navTab" rel="军工单位人数统计"><font color="blue" style="text-decoration:underline">军工单位人数统计</font></a> 
			</td>
			<td>
			 	<a href="jsp/censor/censor/certNumAnalyse.do" target="navTab" rel="审查人员认证数统计"><font color="blue" style="text-decoration:underline">审查人员认证数统计</font></a> 
			</td>		
			<td>
			 	<a href="listCertAnalyse2.do?type=pass" target="navTab" rel="审查认证成绩合格与不合格率统计"><font color="blue" style="text-decoration:underline">审查认证成绩合格与不合格率统计</font></a> 
			</td>
		</tr>
		<tr>	
			<td>
				<a href="listAssessAnalyse101.do" target="navTab" rel="涉密载体管理扣分的单位占比统计"><font color="blue" style="text-decoration:underline">涉密载体管理扣分的单位占比统计</font></a> 	
			</td>		
			<td>						
			    <a href="jsp/uni/unit/listAssessAnalyse6.do" target="navTab" rel="管理部分扣分的单位占比统计"><font color="blue" style="text-decoration:underline">管理部分扣分的单位占比统计</font></a> 
			</td>					
			<td>
				<a href="listAssessAnalyse20.do" target="navTab" rel="基本制度扣分的单位占比统计"><font color="blue" style="text-decoration:underline">基本制度扣分的单位占比统计</font></a> 		
			</td>	
			
		</tr>
		
		<tr>						
			<td>
			 	<a href="listCertAnalyse3.do" target="navTab" rel="审查认证五大方面扣分概率统计"><font color="blue" style="text-decoration:underline">审查认证五大方面扣分概率统计</font></a> 
			</td>		
			<td>
			 	<a href="listCertAnalyse4.do" target="navTab" rel="定密管理扣分的单位占比统计"><font color="blue" style="text-decoration:underline">定密管理扣分的单位占比统计</font></a> 
			</td>
							
			<td>
			 	<a href="listCertAnalyse5.do" target="navTab" rel="涉密人员管理扣分的单位占比统计"><font color="blue" style="text-decoration:underline">涉密人员管理扣分的单位占比统计</font></a> 
			</td>	
		</tr>
		<tr>		
			<td>
				<a href="listCertAnalyse111.do" target="navTab" rel="台帐、维修、报废"><font color="blue" style="text-decoration:underline">台帐、维修、报废</font></a> 
			</td>
			<td>
			 	<a href="listCertAnalyse8.do" target="navTab" rel="安全保密策略与审计"><font color="blue" style="text-decoration:underline">安全保密策略与审计</font></a> 
			</td>		
			<td>
			 	<a href="listCertAnalyse112.do" target="navTab" rel="安全保密措施扣分的单位占比统计"><font color="blue" style="text-decoration:underline">安全保密措施扣分的单位占比统计</font></a> 
			</td>
		</tr>
		<tr>	
			<td>
			 	<a href="listCertAnalyse111.do" target="navTab" rel="外出携带扣分单位占比统计"><font color="blue" style="text-decoration:underline">外出携带扣分单位占比统计</font></a> 
			</td>		
			<td>
			<a href="listCertAnalyse10.do" target="navTab" rel="上市公司统计分析"><font color="blue" style="text-decoration:underline">上市公司统计分析</font></a>
			</td>		
			<td>
			<a href="certNumAnalyse123456.do" target="navTab" rel="工作量统计分析"><font color="blue" style="text-decoration:underline">工作量统计分析</font></a>
			</td>  
		</tr>
		</table>
	</form>
	
</div>
