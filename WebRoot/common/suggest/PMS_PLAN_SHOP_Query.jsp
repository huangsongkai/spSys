<%@ page contentType="text/html; charset=gbk"%>
<%@ include file="../../../common/include.jsp"%>
<%@ include file="../../../common/include2.jsp"%>
<style>
TH
{
	POSITION: relative; 
	TOP: expression(div1.scrollTop-2); 
	height:40px; 
}
</style>
<script type="text/javascript">
	$(document).ready(function(){
		var jsonStr = $('#list_noJSON').val();
		$('#QueryPartListNo').jsonSuggest(eval(jsonStr),{onSelect:checkedListCallback, maxResults:10});
	});
	function checkedListCallback(item){
		$('#QueryPartListNo').val(item.text);
	}

</script>
<table width="100%" cellspacing="1" cellpadding="2" align="center" class="table1">
<form name="formPost" method="POST" action="<%=request.getAttribute("URL")%>">
  <tr bgcolor=white>
    <td>
      <table width="100%" border=0 cellspacing="1" cellpadding="2" class="table1">
        <tr bgcolor=white>
               <td align="left">
              清单号:<input name="QueryPartListNo"  id="QueryPartListNo" size="15" reset="true" value="<%=JillUtils.getParameter(request, "QueryPartListNo", "")%>" >&nbsp;
			令号:<input name="QueryWORK_NO" size="15" reset="true" value="<%=JillUtils.getParameter(request, "QueryWORK_NO", "")%>" >&nbsp;
			<%
			  String QueryPlanOwnMth=JillUtils.getParameter(request, "QueryPlanOwnMth", "");
			 String QueryPlanGenDate=JillUtils.getParameter(request, "QueryPlanGenDate", "");
			 
			if("".equals(QueryPlanOwnMth)&&"".equals(QueryPlanGenDate)){
				java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("yyyy-MM");
				String sim = f.format(new java.util.Date());
				 QueryPlanOwnMth=sim;
			}
				 
			%>
			
			所属年月:<input name="QueryPlanOwnMth" id="QueryPlanOwnMth" size="10" default="1" reset="true" value="<%=QueryPlanOwnMth%>"
			onclick="setMonth(this)">&nbsp;
			下发年月:<input name="QueryPlanGenDate" id="QueryPlanGenDate" size="10" default="1" reset="true" value="<%=JillUtils.getParameter(request, "QueryPlanGenDate", "")%>" onclick="setMonth(this)">&nbsp;

            所属车间:
            <w:SelectTag  name="QueryShopNo"  optionText="DEPTNAME" optionValue="DEPTID" tables="jill_deptdef" wheres="DEPTPID='0'"   width="70" selectValue="<%=JillUtils.getParameter(request, "QueryShopNo", "")%>"></w:SelectTag>
         计划特征:<w:SelectTag name="QueryPlanAlias" codeName="JHBM" width="70" selectValue="<%=JillUtils.getParameter(request, "QueryPlanAlias", "")%>"></w:SelectTag>
        计划类别:<w:SelectTag name="QueryRESERVE1" codeName="JHLB" width="70" selectValue="<%=JillUtils.getParameter(request, "QueryRESERVE1", "")%>"></w:SelectTag>
          </td>
          	<td align="center">
	  <font face="Webdings" color="red" >4</font>
      <a href="javascript:ThisQuery()" >查询</a> 
      <font face="Webdings" color="red">4</font>
      <a href="javascript:ThisReset()">重置</a>
      </td>    
        </tr>
      </table>
    </td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td align="left">
      <input type="checkbox" class="noline" onclick="javascript:selectAllCheckbox(this, 'Checker')">全选
      <a href="javascript:ThisDeletePage()"><img src="images/delete.gif" alt="删除">删除</a>
      <a href="javascript:ThisAddNew()"><img src="images/new.gif" alt="增加">增加</a>
    </td>
    <td align="right">
  <input type="Hidden" name="Operation" value="<%=Operate%>">
  <input type="Hidden" name="CurPage"   value="<%=CurPage%>">
  <input type="Hidden" name="PageCount" value="<%=PageCount%>">
  <input type="Hidden" name="myPageSize" value="<%=request.getParameter("myPageSize")%>">
        <%
        String List_NoJSON =    (String)request.getAttribute("list_noJSON");
        %>
  <input id="list_noJSON" name="list_noJSON" type="Hidden" value="<%=List_NoJSON%>">	 <!-- 用于清单号自动完成组件的数据存储-->
  
  <input name="InputID" type="Hidden">
  <input name="CurChild" type="Hidden">
   
   <img src="images/xiachuan.gif" alt="导入">
          月份<input id="importMonth" name="importMonth" type="text" value=""  onclick="setMonth(this)" size="6">
   <a href="javascript:ThisImport()" >【导入】</a>
    </a>
   
     <img src="images/xiachuan.gif" alt="保存">
      工段
      <%
		Customer cs = (Customer)session.getAttribute("Customer");
		String DeptId = "DEPTPID='"+cs.getDeptCode()+"'";
	  %>
    <w:SelectTag  name="InputSECTION_NO"  optionText="DEPTNAME" optionValue="DEPTID" tables="jill_deptdef" wheres="<%=DeptId%>"   width="70"></w:SelectTag>
	实际完成时间
	 <input name="InputPLAN_ACTUAL_DATE"   onclick="WdatePicker()" size="10">
	 <a href="javascript:ThisUpdateAll()">【保存】</a>
    
    </td>
  </tr>
</table>
      <div style="OVERFLOW: auto;HEIGHT: 400px"  id="tbl-container">
      <script>
  	  var div1 = document.getElementById('tbl-container');  
    </script> 
<table width=100% border="0" cellspacing="0" cellpadding="0">
 <tr>
    <td align="left" valign="top" width="100%">

      <table border="0" cellspacing="1" cellpadding="2" class="table1" style="table-layout:fixed" id="tb1">
        <tr class="tabtitletd" align="center" >
        	<th width='8%'>操作</th>    
        	<th width='5%'>序号</th>    
        	<th width='8%'>工段</th>         
			<th width='8%'>  工作令号        </th>
			<th width='8%'>  清单号          </th>
			<th width='8%'>  包装清单        </th>
			<th width='10%'>  产品名称        </th>
			<th width='5%'>  数量            </th>
			<th width='5%'>  重量            </th>
			<th width='5%'>  出产车间        </th>
			<th width='5%'>  计划时间    </th>
			<th width='5%'>  实际时间    </th>
			<th width='5%'>  计划特征        </th>
			<th width='5%'>  主辅标志           </th>
			<th width='5%'>  已完协作           </th>
			<th width='5%'>  本月协作           </th>
		</tr>	
<%

  rs.first();
  while (!rs.eof())
  {
%>
    <tr id="<%=rs.getValue("ID")%>" class="tabtd<%=rs.getCurPos()%2+1%>" onMouseOver="MouseMoveIn(this);" onMouseOut="MouseMoveOut(this);">
            <td align="center">
         			<input name="Checker" value="<%=rs.getValue("ID")%>" class="noline" type="checkbox">
	      			<a href="javascript:ThisModify('<%=rs.getValue("ID")%>')"><img src="/images/modify.gif" alt="修改"></a>
    	  		<a href="javascript:ThisUpdateInfo('<%=rs.getValue("ID")%>')"><img src="/images/xiachuan.gif" alt="单条保存"></a>
    	  	
    	  	</td>  
            <td nowrap align="center" title="<%=rs.getValue("SEQ_NO")%>">
			<input name="InputSEQ_NO<%=rs.getValue("ID")%>" value="<%=rs.getValue("SEQ_NO")%>"  >
			</td>
			<%
			  String sectionNo="InputSECTION_NO"+rs.getValue("ID");
			%>
			
			<td nowrap align="center" title="<%=rs.getValue("SECTION_NO")%>">
			<w:SelectTag  name="<%=sectionNo%>"  optionText="DEPTNAME" optionValue="DEPTID" tables="jill_deptdef" wheres="<%=DeptId%>"   width="70" selectValue="<%=rs.getValue("SECTION_NO")%>"></w:SelectTag>
			
			</td>
			
            <td nowrap align="center" title="<%=rs.getValue("WORK_NO")%>"><%=rs.getValue("WORK_NO")%></td>
			<td nowrap align="center" title="<%=rs.getValue("PART_LIST_NO")%>"><%=rs.getValue("PART_LIST_NO")%>&nbsp;</td>
			<td nowrap align="center" title="<%=rs.getValue("PACK_LIST_NO")%>"><%=rs.getValue("PACK_LIST_NO")%>&nbsp;</td>
			<td nowrap align="center" title="<%=rs.getValue("PART_LIST_NAME")%>"><%=rs.getValue("PART_LIST_NAME")%>&nbsp;</td>
   			<td nowrap align="center" title="<%=rs.getValue("QUANTITY")%><%=rs.getValue("UNIT_NO")%>">
   			<%=rs.getValue("QUANTITY")%><w:CodeOut value="<%=rs.getValue("UNIT_NO")%>" codeName="DW" />
			</td>
            <td nowrap align="center" title="<%=rs.getValue("WEIGHT")%>"><%=rs.getValue("WEIGHT")%></td>
			<td nowrap align="center" title="<%=rs.getValue("SHOP_NO")%>"><%=rs.getValue("SHOP_NO")%>&nbsp;</td>
			<td nowrap align="center" title="<%=rs.getValue("PLAN_CMP_DATE")%>"><%=DateFmt(rs.getValue("PLAN_CMP_DATE"))%>&nbsp;</td>
            <td nowrap align="center" title="<%=rs.getValue("PLAN_ACTUAL_DATE")%>"><%=DateFmt(rs.getValue("PLAN_ACTUAL_DATE"))%>&nbsp;</td>
            
            <td nowrap align="center" title="<%=rs.getValue("PLAN_ALIAS")%>"><w:CodeOut value="<%=rs.getValue("PLAN_ALIAS")%>" codeName="JHBM" /></td>
            <td nowrap align="center" title="<%=rs.getValue("NOTES2")%>" 
            <%if("1".equals(rs.getValue("NOTES2"))) {
            	%>
            	style="background-color: GREEN"
            	<% 
            }
            %>
            <%if("2".equals(rs.getValue("NOTES2"))) {
            	%>
            	style="background-color: YELLOW"
            	<%           	
            }
            %>          
            ><w:CodeOut value="<%=rs.getValue("NOTES2")%>" codeName="ZFBZ" /></td>
   	  <td nowrap align="center" title="<%=rs.getValue("RESERVE3")%>"><%=rs.getValue("RESERVE3")%>&nbsp;</td>
   	  <td nowrap align="center" title="<%=rs.getValue("RESERVE4")+";"+rs.getValue("RESERVE5")+";"+rs.getValue("RESERVE6")%>"><%=rs.getValue("RESERVE4")+";"+rs.getValue("RESERVE5")+";"+rs.getValue("RESERVE6")%>&nbsp;</td>
         
     </tr>
<% 
    rs.next();
  }
%>
      </table>
      </div>
    </td>
   
       <table width="100%" border="0" cellspacing="0" cellpadding="0" ><tr><td height="20"></td></tr></table>
    
    </td>
  </tr>
</table>
  </div>
<table width="100%" border=0 cellpadding=0 cellspacing=0><tr><td align=left><%rs.WriteNavigate(out);%></td></tr></table>
</center>
</form>
