<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/common/include.jsp"%>

<script language="javascript">
function clickEvent(id)
{
}
function checkBoxClickEvent(checkObj)
{
	if(checkObj.checked==true)
	{
	
	}
}
//右移
function add(){
	var alloptions = $("#selectRoleLeft option");
    var so = $("#selectRoleLeft option:selected");
	if(alloptions.length==0 || so.length==0)
		return;

    var a = (so.get(so.length-1).index == alloptions.length-1)? so.prev().attr("selected",true):so.next().attr("selected",true);
   
    $("#selectRoleRight").append(so);
}
//右移全部
function addAll(){
	$("#selectRoleRight").append($("#selectRoleLeft option").attr("selected",true));
}
//左移
function del(){
	var alloptions = $("#selectRoleRight option");
	var so = $("#selectRoleRight option:selected");
	if(alloptions.length==0 || so.length==0)
		return;
	var a = (so.get(so.length-1).index == alloptions.length-1)? so.prev().attr("selected",true):so.next().attr("selected",true);
	$("#selectRoleLeft").append(so);
}
//左移全部
function delAll(){
	$("#selectRoleLeft").append($("#selectRoleRight option").attr("selected",true));
}
//全选右侧select
function selectFuncRightAll()
{
	var o = document.getElementById("selectRoleRight");
	for(i=0;i<o.options.length;i++){
		o.options[i].selected=true;
	}
}
</script>
<input type="hidden" name="updFunc" value="true" id="updFunc"/>
<table width="100%">
	<tr>
		<td width="160" class="td_title">组权限名称</td>
		<td width="40" class="td_title"></td>
		<td width="160" class="td_title">分配到权限组</td>
		<td class="td_title">选择权限组以外的权限</td>
	</tr>
	<tr>
		<td valign="top">
			<select name="selectRoleLeft" id="selectRoleLeft" multiple="multiple" size="9" class="mulitSelectLength">
			<c:forEach items="${funcGroupList}" var="obj">
			<option value="${obj.funcGroupId}">${obj.funcGroupName}</option>
			</c:forEach>
			</select>
		</td>
		<td valign="top">
			<br/>
			<a href="#" onclick="addAll()" title="全部右移"><img src="../../../images/main/right_all.gif" width="30" height="20"></img></a>
			<br/>
			<br/>
			<a href="#" onclick="add()" title="右移"><img src="../../../images/main/right.gif" width="30" height="20"></img></a>
			<br/>
			<br/>
			<a href="#" onclick="del()" title="左移"><img src="../../../images/main/left.gif" width="30" height="20"></img></a>
			<br/>
			<br/>
			<a href="#" onclick="delAll()" title="全部左移"><img src="../../../images/main/left_all.gif" width="30" height="20"></img></a>
		</td>
		<td valign="top">
			<select name="selectRoleRight" id="selectRoleRight" multiple="multiple" size="9" class="mulitSelectLength">
			<c:forEach items="${userFuncGroupList}" var="obj">
			<option value="${obj.funcGroupId}">${obj.funcGroupName}</option>
			</c:forEach>
			</select>
		</td>
		<td class="left_align" valign="top">
		
		<w:mulitboxtree chooseType="checkbox" returnValue="funcId"
				displayValue="funcName" pidname="funcParentId"
				content="${funcList}" idname="funcId" headValue="0"
				checkboxvalues="${userFuncs}" useDblClick="false"></w:mulitboxtree>
				
		</td>
	</tr>
</table>
