<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/common/include.jsp"%>
<form name="roleForm" id="roleForm"  action="listRoleUser.do?userId=${userlist.userId}" method="post">
<input type="hidden" name="updRole" value="true" id="updRole"/>
<table width="100%" cellspacing="0" >
  <tr >
    <td class="td_title" width="40">选择</td>
    <td class="td_title" width="200">角色名称</td>
    <td class="td_title" width="150">角色类型</td>
    <td class="td_title" width="180">角色代码</td>
    <td class="td_title" >角色描述</td>
    </tr>
    <c:forEach items="${rolelist}" var="obj" >
        <tr>
	        <td><w:checkbox name="roleId" value="${obj.roleId}" serverValue="${userRoles}"></w:checkbox> </td>
	        <td class="left_align">${obj.roleName}</td>
	        <td class="left_align">${obj.roleType}</td>
	        <td class="left_align">${obj.roleCode}</td>
	        <td class="left_align">${obj.roleDescription }</td>
        </tr>
    </c:forEach>
</table>
<w:spPage formId="roleForm" spPageType="ajax" spPageContainerId="content2"/>
</form>