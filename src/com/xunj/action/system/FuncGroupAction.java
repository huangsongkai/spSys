package com.xunj.action.system;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.xunj.action.common.AbstractAction;
import com.xunj.core.ParaUnit;
import com.xunj.po.SysFuncGroup;
import com.xunj.po.SysFuncGroupList;
import com.xunj.po.SysFuncGroupUser;
import com.xunj.po.SysFuncInfo;
import com.xunj.po.SysUserList;
import com.xunj.util.StateConst;

public class FuncGroupAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6490103300433629280L;
	private SysFuncGroup funcGroup;

	/**
	 * 显示权限列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String listFuncGroup() throws Exception {
		ArrayList<ParaUnit> paralist = new ArrayList<ParaUnit>();
		if(POST()){
			if(funcGroup.getFuncGroupName()!=null){
				paralist.add(new ParaUnit("funcGroupName", "%"+funcGroup.getFuncGroupName()+"%",ParaUnit.LIKE));
			}
		}
		paralist.add(new ParaUnit("funcGroupId", ParaUnit.ASC,ParaUnit.ORDER));
		List list = dao.criteriaByPage(SysFuncGroup.class, paralist);
		request.setAttribute("datalist", list);
		return toJsp("/jsp/system/funcGroup/list_funcGroup.jsp");
	}
	/**
	 * 查询出权限功能点，作为权限组中包含的权限。新增权限组对象、和权限组-权限功能点对应对象。
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String addFuncGroup() throws Exception{
		String hqlList="from SysFuncInfo where state = '"+StateConst.STATE_USE+"' order by orderCol asc";
		List list=dao.findAll(hqlList);
		request.setAttribute("hqlList",list);
		return toJsp("/jsp/system/funcGroup/add_funcGroup.jsp");
	}
	public String saveFuncGroup() throws Exception{
		if(StringUtils.isEmpty(funcGroup.getFuncGroupId())){
			String funcId[] =request.getParameterValues("funcId");
			List saveList = new ArrayList();
			funcGroup.setFuncGroupId(UUID.randomUUID().toString());
			dao.save(funcGroup);
			for(int i=0;i<funcId.length;i++)
			{
				SysFuncInfo funcInfo = new SysFuncInfo();
				funcInfo.setFuncId(funcId[i]);
				SysFuncGroupList fgl=new SysFuncGroupList();
				fgl.setFuncGroupListId(UUID.randomUUID().toString());
				fgl.setFuncGroupId(funcGroup.getFuncGroupId());
				fgl.setFuncId(funcInfo.getFuncId());
				saveList.add(fgl);
			}
			dao.saveAll(saveList);
			this.Alert_GoUrl("添加角色成功！", "closeCurrent", "角色管理", "");
			return toResult();
		}else{
			String funcId[] =request.getParameterValues("funcId");
			Set<SysFuncGroupList> funcGroupLists = new HashSet<SysFuncGroupList>();
//			先删除权限组对应的功能点，再添加
			String hql="delete from SysFuncGroupList where funcGroupId = '"+funcGroup.getFuncGroupId()+"'";
			dao.bulkUpdate(hql);
			//根据页面中选择的功能点，重新组织funcGroup对象中的FuncGroupLists集合
			List saveList = new ArrayList();
			for(int i=0;i<funcId.length;i++)
			{
				SysFuncInfo funcInfo = new SysFuncInfo();
				funcInfo.setFuncId(funcId[i]);
				SysFuncGroupList fgl=new SysFuncGroupList();
				fgl.setFuncGroupListId(UUID.randomUUID().toString());
				fgl.setFuncGroupId(funcGroup.getFuncGroupId());
				fgl.setFuncId(funcInfo.getFuncId());
				
				saveList.add(fgl);
			}
			dao.update(funcGroup);
			dao.saveAll(saveList);
			this.Alert_GoUrl("修改角色成功！", "closeCurrent", "角色管理", "");
			return toResult();
		}
	
	}
	
	/**
	 * 根据指定的权限组对象编号，查询出并修改权限组对象，及其对象的功能点对象集。
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String updateFuncGroup() throws Exception{
		String funcGroupId=request.getParameter("uid");
		funcGroup=(SysFuncGroup)dao.findOne(SysFuncGroup.class,funcGroupId);
		List funcList = dao.findAll("from SysFuncGroupList where funcGroupId='"+funcGroupId+"'");
		String[] funArr = new String[funcList.size()];
		for(int i=0;i<funcList.size();i++)
		{
			SysFuncGroupList fgl = (SysFuncGroupList) funcList.get(i);
			funArr[i]=fgl.getFuncId();
		}
		
		String hqlList="from SysFuncInfo where state = '"+StateConst.STATE_USE+"' order by orderCol asc";
		List list=dao.findAll(hqlList);
		request.setAttribute("hqlList",list);
		request.setAttribute("funArr",funArr);
		return toJsp("/jsp/system/funcGroup/update_funcGroup.jsp");
	}
	public String deleteFuncGroup() throws Exception{
		//批量删除权限组功能点对应对象
		String funcGroupId=request.getParameter("uid");
		String hql = "delete from SysFuncGroupList where funcGroupId ='"+funcGroupId+"'";
		dao.bulkUpdate(hql);
		
		//删除用户所拥有权限组对象

		String hql1 = "delete from SysFuncGroupUser where funcGroupId ='"+funcGroupId+"'";
		dao.bulkUpdate(hql1);
		
		//删除权限组对象
		int returnSts2 = -1;
		String hql2 =" delete from SysFuncGroup where funcGroupId ='"+funcGroupId+"'";
		returnSts2 = dao.bulkUpdate(hql2);
		
		if(returnSts2 > -1)
		{
			this.Alert_GoUrl("删除角色成功！", "", "角色管理", "");
		}
		else
		{
			this.Alert_GoUrl("删除角色失败！", "", "角色管理", "");
		}
		return toResult();
	}
	/**
	 * 查询权限组所对应的用户集合
	 * @return
	 * @throws Exception
	 */
	public String listUserFuncGroup() throws Exception
	{
		String funcGroupId=request.getParameter("funcGroupId");
		request.setAttribute("funcGroupId", funcGroupId);
		String sql="from SysUserList where userId in(select userId from SysFuncGroupUser where funcGroupId='"+funcGroupId+"') order by userId";
		List<SysUserList> userList=dao.findAll(sql);
		request.setAttribute("userFuncList", userList);
		return toJsp("/jsp/system/funcGroup/list_user_funcGroup.jsp");
	}
	
	
	public String deleteUserFuncGroup() throws Exception
	{
		String[] userArr=request.getParameterValues("ids");
		String funcGroupId = request.getParameter("funcGroupId");
		
		//根据从页面接收到的要删除用户的复选框的值，组织删除用户的查询条件
		StringBuffer whereUserIds = new StringBuffer();
		if(userArr!=null ){
			int len = userArr.length;
			for(int i=0;i<len-1;i++){
				whereUserIds.append("'");
				whereUserIds.append(userArr[i]);
				whereUserIds.append("',");
			}
			whereUserIds.append("'");
			whereUserIds.append(userArr[len-1]);
			whereUserIds.append("'");
			
			String hql = "delete SysFuncGroupUser fgu where fgu.funcGroupId = '"+funcGroupId+"' and fgu.userId in ( "+whereUserIds.toString()+" )";
			dao.bulkUpdate(hql);
			this.Alert_GoUrl("从角色内移除用户成功！", "", "角色管理", "");
		}else{
			this.Alert_GoUrl("从角色内移除用户成功！", "", "角色管理", "");
		}
		return toResult();
	}
	
	

	/**
	 * 添加用户需显示的用户列表
	 */
	public String addUserFuncGroup() throws Exception {
		ArrayList<ParaUnit> paralist = new ArrayList<ParaUnit>();
		String funcGroupId=request.getParameter("funcGroupId");
		request.setAttribute("funcGroupId", funcGroupId);
		String sql="from SysUserList where userId not in(select userId from SysFuncGroupUser where funcGroupId='"+funcGroupId+"') and state='A' order by userId";
		String userId=request.getParameter("userId");
		if(StringUtils.isNotEmpty(userId)){
			sql+=" and userId like '%"+userId+"%'";
			request.setAttribute("userId", userId);
		}	
		String userName=request.getParameter("userName");
		if(StringUtils.isNotEmpty(request.getParameter("userName"))){
			sql+=" and userName like '%"+request.getParameter("userName")+"%'";
			request.setAttribute("userName", userName);
		}
		List list = dao.findByPage(sql);
		request.setAttribute("datalist", list);
		return toJsp("/jsp/system/funcGroup/add_user_funcGroup.jsp");
	}
	
	
	/**
	 * 为权限组添加用户
	 * @return
	 * @throws Exception
	 */
	public String saveUserFuncGroup() throws Exception
	{
		String[] userArr=request.getParameterValues("ids");
		String funcGroupId = request.getParameter("funcGroupId");
		
		ArrayList<SysFuncGroupUser> funcGroupUserList = new ArrayList<SysFuncGroupUser>();
		if(userArr!=null && userArr.length>0){
		for(int i=0;i<userArr.length;i++){
				dao.bulkUpdate("delete from SysFuncGroupUser where userId='"+userArr[i]+"'");
				SysFuncGroupUser funcGroupUser = new SysFuncGroupUser();
				funcGroupUser.setUserId(userArr[i]);
				funcGroupUser.setFuncGroupId(funcGroupId);
				funcGroupUserList.add(funcGroupUser);
			}
		}
		dao.saveAll(funcGroupUserList);
		Alert_GoUrl("分配用户成功！","closeCurrent","角色管理","");
		return toResult();
	}
	
	
	
	
	
/**************************以下功能不用*******************************/	
	public String updateSaveFuncGroup() throws Exception{
		String funcId[] =request.getParameterValues("funcId");
		Set<SysFuncGroupList> funcGroupLists = new HashSet<SysFuncGroupList>();
//		先删除权限组对应的功能点，再添加
		String hql="delete from SysFuncGroupList where funcGroupId = '"+funcGroup.getFuncGroupId()+"'";
		dao.bulkUpdate(hql);

		//根据页面中选择的功能点，重新组织funcGroup对象中的FuncGroupLists集合
		List saveList = new ArrayList();
		for(int i=0;i<funcId.length;i++)
		{
			SysFuncInfo funcInfo = new SysFuncInfo();
			funcInfo.setFuncId(funcId[i]);
			SysFuncGroupList fgl=new SysFuncGroupList();
			fgl.setFuncGroupListId(UUID.randomUUID().toString());
			fgl.setFuncGroupId(funcGroup.getFuncGroupId());
			fgl.setFuncId(funcInfo.getFuncId());
			
			saveList.add(fgl);
		}
		
		dao.update(funcGroup);
		dao.saveAll(saveList);

		this.Alert_GoUrl("修改权限组成功！", "updateFuncGroup.do?funcGroupId="+funcGroup.getFuncGroupId(), "frame_right", "frame_left");

		return toMessage();
	}
	
	
	
	/**
	 *  根据指定的权限组对象编号，删除对应的权限组对象，以及权限组--功能点对应对象集。
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	

	public SysFuncGroup getFuncGroup() {
		return funcGroup;
	}
	public void setFuncGroup(SysFuncGroup funcGroup) {
		this.funcGroup = funcGroup;
	}
	
	
}
