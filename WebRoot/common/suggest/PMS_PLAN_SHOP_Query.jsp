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
              �嵥��:<input name="QueryPartListNo"  id="QueryPartListNo" size="15" reset="true" value="<%=JillUtils.getParameter(request, "QueryPartListNo", "")%>" >&nbsp;
			���:<input name="QueryWORK_NO" size="15" reset="true" value="<%=JillUtils.getParameter(request, "QueryWORK_NO", "")%>" >&nbsp;
			<%
			  String QueryPlanOwnMth=JillUtils.getParameter(request, "QueryPlanOwnMth", "");
			 String QueryPlanGenDate=JillUtils.getParameter(request, "QueryPlanGenDate", "");
			 
			if("".equals(QueryPlanOwnMth)&&"".equals(QueryPlanGenDate)){
				java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("yyyy-MM");
				String sim = f.format(new java.util.Date());
				 QueryPlanOwnMth=sim;
			}
				 
			%>
			
			��������:<input name="QueryPlanOwnMth" id="QueryPlanOwnMth" size="10" default="1" reset="true" value="<%=QueryPlanOwnMth%>"
			onclick="setMonth(this)">&nbsp;
			�·�����:<input name="QueryPlanGenDate" id="QueryPlanGenDate" size="10" default="1" reset="true" value="<%=JillUtils.getParameter(request, "QueryPlanGenDate", "")%>" onclick="setMonth(this)">&nbsp;

            ��������:
            <w:SelectTag  name="QueryShopNo"  optionText="DEPTNAME" optionValue="DEPTID" tables="jill_deptdef" wheres="DEPTPID='0'"   width="70" selectValue="<%=JillUtils.getParameter(request, "QueryShopNo", "")%>"></w:SelectTag>
         �ƻ�����:<w:SelectTag name="QueryPlanAlias" codeName="JHBM" width="70" selectValue="<%=JillUtils.getParameter(request, "QueryPlanAlias", "")%>"></w:SelectTag>
        �ƻ����:<w:SelectTag name="QueryRESERVE1" codeName="JHLB" width="70" selectValue="<%=JillUtils.getParameter(request, "QueryRESERVE1", "")%>"></w:SelectTag>
          </td>
          	<td align="center">
	  <font face="Webdings" color="red" >4</font>
      <a href="javascript:ThisQuery()" >��ѯ</a> 
      <font face="Webdings" color="red">4</font>
      <a href="javascript:ThisReset()">����</a>
      </td>    
        </tr>
      </table>
    </td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td align="left">
      <input type="checkbox" class="noline" onclick="javascript:selectAllCheckbox(this, 'Checker')">ȫѡ
      <a href="javascript:ThisDeletePage()"><img src="images/delete.gif" alt="ɾ��">ɾ��</a>
      <a href="javascript:ThisAddNew()"><img src="images/new.gif" alt="����">����</a>
    </td>
    <td align="right">
  <input type="Hidden" name="Operation" value="<%=Operate%>">
  <input type="Hidden" name="CurPage"   value="<%=CurPage%>">
  <input type="Hidden" name="PageCount" value="<%=PageCount%>">
  <input type="Hidden" name="myPageSize" value="<%=request.getParameter("myPageSize")%>">
        <%
        String List_NoJSON =    (String)request.getAttribute("list_noJSON");
        %>
  <input id="list_noJSON" name="list_noJSON" type="Hidden" value="<%=List_NoJSON%>">	 <!-- �����嵥���Զ������������ݴ洢-->
  
  <input name="InputID" type="Hidden">
  <input name="CurChild" type="Hidden">
   
   <img src="images/xiachuan.gif" alt="����">
          �·�<input id="importMonth" name="importMonth" type="text" value=""  onclick="setMonth(this)" size="6">
   <a href="javascript:ThisImport()" >�����롿</a>
    </a>
   
     <img src="images/xiachuan.gif" alt="����">
      ����
      <%
		Customer cs = (Customer)session.getAttribute("Customer");
		String DeptId = "DEPTPID='"+cs.getDeptCode()+"'";
	  %>
    <w:SelectTag  name="InputSECTION_NO"  optionText="DEPTNAME" optionValue="DEPTID" tables="jill_deptdef" wheres="<%=DeptId%>"   width="70"></w:SelectTag>
	ʵ�����ʱ��
	 <input name="InputPLAN_ACTUAL_DATE"   onclick="WdatePicker()" size="10">
	 <a href="javascript:ThisUpdateAll()">�����桿</a>
    
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
        	<th width='8%'>����</th>    
        	<th width='5%'>���</th>    
        	<th width='8%'>����</th>         
			<th width='8%'>  �������        </th>
			<th width='8%'>  �嵥��          </th>
			<th width='8%'>  ��װ�嵥        </th>
			<th width='10%'>  ��Ʒ����        </th>
			<th width='5%'>  ����            </th>
			<th width='5%'>  ����            </th>
			<th width='5%'>  ��������        </th>
			<th width='5%'>  �ƻ�ʱ��    </th>
			<th width='5%'>  ʵ��ʱ��    </th>
			<th width='5%'>  �ƻ�����        </th>
			<th width='5%'>  ������־           </th>
			<th width='5%'>  ����Э��           </th>
			<th width='5%'>  ����Э��           </th>
		</tr>	
<%

  rs.first();
  while (!rs.eof())
  {
%>
    <tr id="<%=rs.getValue("ID")%>" class="tabtd<%=rs.getCurPos()%2+1%>" onMouseOver="MouseMoveIn(this);" onMouseOut="MouseMoveOut(this);">
            <td align="center">
         			<input name="Checker" value="<%=rs.getValue("ID")%>" class="noline" type="checkbox">
	      			<a href="javascript:ThisModify('<%=rs.getValue("ID")%>')"><img src="/images/modify.gif" alt="�޸�"></a>
    	  		<a href="javascript:ThisUpdateInfo('<%=rs.getValue("ID")%>')"><img src="/images/xiachuan.gif" alt="��������"></a>
    	  	
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
