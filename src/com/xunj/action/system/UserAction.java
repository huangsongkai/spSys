package com.xunj.action.system;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.xunj.action.common.AbstractAction;
import com.xunj.action.common.BusinessException;
import com.xunj.core.MD5;
import com.xunj.core.ParaUnit;
import com.xunj.core.SessionBean;
import com.xunj.po.SysFuncGroup;
import com.xunj.po.SysFuncGroupUser;
import com.xunj.po.SysFuncInfo;
import com.xunj.po.SysOperationRole;
import com.xunj.po.SysUserFuncInfo;
import com.xunj.po.SysUserList;
import com.xunj.po.SysUserRole;
import com.xunj.util.StateConst;

public class UserAction extends AbstractAction {

	/**
	 * 用户管理
	 */
	private static final long serialVersionUID = 1329846600050347220L;
	private SysUserList userlist;
	private List<SysUserList> dataList;
	

	/**
	 * 显示用户列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String listUser() throws Exception {
		ArrayList<ParaUnit> paralist = new ArrayList<ParaUnit>();
		if(POST())
		{
			String userId=request.getParameter("userlist.userId");
			if(StringUtils.isNotEmpty(userId)){
				paralist.add(new ParaUnit("userId", userId,ParaUnit.LIKE));
			}
			if(userlist.getUserName()!=null && !"".equals(userlist.getUserName())){
				paralist.add(new ParaUnit("userName", userlist.getUserName(),ParaUnit.LIKE));
			}
		}
		paralist.add(new ParaUnit("state", "A", ParaUnit.EQ));
		paralist.add(new ParaUnit("userId", ParaUnit.ASC,ParaUnit.ORDER));
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if(StringUtils.isNotEmpty(orderField)){
			paralist.add(new ParaUnit(orderField, orderDirection,ParaUnit.ORDER));
		}
		dataList = dao.criteriaByPage(SysUserList.class, paralist);
		
		for(int i = 0; i < dataList.size(); i++){
			SysUserList user = dataList.get(i);
			String deptId = user.getDeptId()!=null?user.getDeptId():"";
			if(!deptId.trim().equals("")){
				SysOperationRole opRole = (SysOperationRole) dao.findOne(SysOperationRole.class, deptId);
				user.setDeptName(opRole.getRoleName());
			}else{
				user.setDeptName("");
			}
			
		}
		
//		super.paralist = paralist;
		request.setAttribute("datalist", dataList);
		return toJsp("/jsp/system/user/list_user.jsp");
	}
	
	/**
	 * 添加部门用户是需显示的用户列表
	 */
	public String listForChooseUser() throws Exception {
		ArrayList<ParaUnit> paralist = new ArrayList<ParaUnit>();
			if(POST())
		{
			String userId=request.getParameter("userlist.userId");
			if(StringUtils.isNotEmpty(userId)){
				paralist.add(new ParaUnit("userId", userId,ParaUnit.LIKE));
			}
			if(userlist.getUserName()!=null && !"".equals(userlist.getUserName())){
				paralist.add(new ParaUnit("userName", userlist.getUserName(),ParaUnit.LIKE));
			}
//			if(userlist.getGroupSign()!=null && !"".equals(userlist.getGroupSign())){
//				paralist.add(new ParaUnit("groupSign", userlist.getGroupSign(),ParaUnit.LIKE));
//			}
		}
		String deptId=request.getParameter("roleId");
		request.setAttribute("roleId", deptId);
		List temp=new ArrayList();
		temp.add(new ParaUnit("deptId", deptId, ParaUnit.NE));
		temp.add(new ParaUnit("deptId", "", ParaUnit.NULL));
		
		paralist.add(new ParaUnit("", temp, ParaUnit.OR));
//		
		paralist.add(new ParaUnit("state", "A", ParaUnit.EQ));
//		paralist.add(new ParaUnit("groupSign", ParaUnit.ASC,ParaUnit.ORDER));
//		paralist.add(new ParaUnit("userOrder", ParaUnit.ASC,ParaUnit.ORDER));
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if(StringUtils.isNotEmpty(orderField)){
			paralist.add(new ParaUnit(orderField, orderDirection,ParaUnit.ORDER));
		}
		List<SysUserList> list = dao.criteriaByPage(SysUserList.class, paralist);
		
		for(int i = 0; i < list.size(); i++){
			SysUserList user = list.get(i);
			String tempDeptId = user.getDeptId()!=null?user.getDeptId():"";
			if(!tempDeptId.trim().equals("")){
				SysOperationRole opRole = (SysOperationRole) dao.findOne(SysOperationRole.class, tempDeptId);
				user.setDeptName(opRole.getRoleName());
			}else{
				user.setDeptName("");
			}
			
		}

		request.setAttribute("datalist", list);
		return toJsp("/jsp/system/operationRole/selectUsers.jsp");
	}
	
	/**
	 * 更新用户部门信息
	 */
	public String setDeptForUser() throws Exception {
		String[] ids=request.getParameterValues("ids");
		String deptId=request.getParameter("roleId");
		String opFlag=request.getParameter("opFlag");
		
//		ArrayList userDeptList= new ArrayList();
		
		String conditionSql = "from SysUserList where userId in('";
		for(int i = 0; i < ids.length;i++){
			if(i==0){
				conditionSql += ids[i] + "'";
			} else {
				conditionSql += ", '" + ids[i] + "'"; 
			}
		}
		conditionSql += ")";
		
		List userList=dao.findAll(conditionSql);
		
		for(int i=0;i<userList.size();i++){
			SysUserList sysUserList=(SysUserList)userList.get(i);
			if (opFlag.trim().equals("add")){
				sysUserList.setDeptId(deptId);
			}else{
				sysUserList.setDeptId("");
			}
//			userDeptList.add(sysUserList);
			dao.update(sysUserList);
		}
		//saveall批量保存
//		dao.saveAll(userDeptList);
		if (opFlag.trim().equals("add")){
			Alert_GoUrl("部门用户设置成功！", "closeCurrent","部门管理信息",null);
		}else{
			Alert_GoUrl("部门用户设置成功！", "部门管理信息");
		}
		return toResult();
	}
	
	/**
	 * 显示用户列表，部门管理员，显示当前用户所创建的用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public String listDeptUser() throws Exception {
		
		
		ArrayList<ParaUnit> paralist = new ArrayList<ParaUnit>();
		
		if(POST())
		{
			if(userlist.getUserId()!=null && !"".equals(userlist.getUserId())){
				paralist.add(new ParaUnit("userId", userlist.getUserId(),ParaUnit.LIKE));
			}
			if(userlist.getUserName()!=null && !"".equals(userlist.getUserName())){
				paralist.add(new ParaUnit("userName", userlist.getUserName(),ParaUnit.LIKE));
			}
//			if(userlist.getGroupSign()!=null && !"".equals(userlist.getGroupSign())){
//				paralist.add(new ParaUnit("groupSign", userlist.getGroupSign(),ParaUnit.LIKE));
//			}
		}
		SessionBean sessionbean = SessionBean.getSessionBean(request);
		paralist.add(new ParaUnit("createUserId", sessionbean.getUserId(),ParaUnit.EQ));
		request.getSession().setAttribute("deptManager", "true");
		paralist.add(new ParaUnit("state", "A", ParaUnit.EQ));
		paralist.add(new ParaUnit("groupSign", ParaUnit.ASC,ParaUnit.ORDER));
		paralist.add(new ParaUnit("userOrder", ParaUnit.ASC,ParaUnit.ORDER));
		
		List list = dao.criteriaByPage(SysUserList.class, paralist);
		
		request.setAttribute("datalist", list);
		return toJsp("/jsp/system/user/lst_dept_user.jsp");
	}

	/**
	 * 判读用户是否存在
	 * 
	 * @return
	 * @throws Exception
	 */
	public String checkExistsUser() throws Exception {

		List listUid = dao.findAll("from SysUserList where userId='"+ userlist.getUserId() + "'");
		String data = "ok";
		if (listUid.size() > 0) {
			data = "exists";
		}
		request.setAttribute("data", data);
		return toResult();

	}
	/**
	 * 添加用户保存方法
	 * @return
	 * @throws Exception
	 */
	public String saveUser() throws Exception {
		SysUserList tempUser = (SysUserList) dao.findOne(SysUserList.class, userlist.getUserId());
		if(tempUser!=null){//用户ID被占用
			userlist.setUserId(getMaxUserId());
		}
		tempUser  = (SysUserList) dao.findFirst("from SysUserList where userName='"+ userlist.getUserName()+"'");
		if(tempUser!=null){//用户昵称被占用
			Alert_GoUrl("用户昵称被占用", "",null,null);
			return toError();
		}
		MD5 md5 = new MD5();
		String pwd = md5.toMD5(userlist.getUserId()+userlist.getPassword());
		userlist.setPassword(pwd); // 用户密码
		SessionBean sessionbean = SessionBean.getSessionBean(request);
//		userlist.setCreateUserId(sessionbean.getUserId());
		dao.save(userlist);
//		UserService userService = new UserService(dao);
//		//读取页标，判断是否需要更新权限数据
//		String updFunc = request.getParameter("updFunc");
//		if(updFunc!=null)
//		{
//			String group[]=request.getParameterValues("selectRoleRight");
//			String funcId[]=request.getParameterValues("funcId");
//			//处理用户权限信息
//			userService.updateUserFuncData(userlist, group, funcId,false);
//		}
//		String updRole = request.getParameter("updRole");
//		if(updRole!=null)
//		{
//			String roleId[]=request.getParameterValues("roleId");
//			//处理用户权限信息
//			userService.updateUserRoleData(userlist,roleId,false);
//		}
//		String type =  request.getParameter("type");
//		String dialogNo=request.getParameter("dialogNo");
			Alert_GoUrl("添加成功！", "closeCurrent","用户管理",null);
			
		return toResult();

	}
	
	/**
	 * 添加用户方法
	 * @throws Exception 
	 * **/
	public String addUser() throws Exception{
		String userId=getMaxUserId();
		request.setAttribute("userId", userId);
		return toJsp("/jsp/system/user/add_user.jsp");
	}
	
	
	/**
	 * 返回最大用户ID
	 * @return
	 * @throws Exception
	 */
	private String getMaxUserId() throws Exception {
		List list = dao.findAll("select max(userId) from SysUserList ");
		String maxId = list.get(0).toString();
		BigDecimal bd = new BigDecimal(maxId);
		BigDecimal sum = bd.add(new BigDecimal("1"));
		String num =sum.toString();
		if(sum.toString().length()==1) 
		{
			num ="0"+num;
		}
		return num;
	}
	/**
	 * 修改用户方法
	 * @return
	 * @throws Exception
	 */
	public String updateUser() throws BusinessException{
		String id=request.getParameter("uid");
		userlist=(SysUserList) dao.findOne(SysUserList.class,id);
		return toJsp("/jsp/system/user/update_user.jsp");
	}
	public String saveUpdateUser() throws Exception{
		MD5 md5 = new MD5();
		String pwd = md5.toMD5(userlist.getUserId()+userlist.getPassword());
		userlist.setPassword(pwd); // 用户密码
		SessionBean sessionbean = SessionBean.getSessionBean(request);
//		userlist.setCreateUserId(sessionbean.getUserId());
		dao.update(userlist);
		
		Alert_GoUrl("保存成功！","closeCurrent","用户管理",null);
		return toResult();
	}
	
	/**
	 * 用户修改密码更新新密码方法
	 * @return
	 * @throws Exception
	 */
	public String updatePasswordUser() throws Exception
	{
		MD5 md5 = new MD5();
		String userId = request.getParameter("userId");
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		String pwd = md5.toMD5(userId+oldPassword);
		SysUserList u=(SysUserList) dao.findOne(SysUserList.class, userId);
		if(!u.getPassword().equals(pwd)){
			this.Alert_GoUrl("旧密码输入有误", "", "", "");
			return toError();
		}
		else{
			pwd = md5.toMD5(userId+newPassword);
			u.setPassword(pwd);
			dao.update(u);
			this.Alert_GoUrl("密码修改成功", "closeCurrent", "", "");
			return toResult();
		}
	}
	
	
	/**
	 * 修改用户方法
	 * @return
	 * @throws Exception
	 */
//	public String updateUser() throws Exception {
//		
//		if (POST()) {
//			dao.update(userlist);
//			
//			UserService userService = new UserService(dao);
//			//读取页标，判断是否需要更新权限数据
//			String updFunc = request.getParameter("updFunc");
//			if(updFunc!=null)
//			{
//				String group[]=request.getParameterValues("selectRoleRight");
//				String funcId[]=request.getParameterValues("funcId");
//				//处理用户权限信息
//				userService.updateUserFuncData(userlist, group, funcId,true);
//			}
//			String updRole = request.getParameter("updRole");
//			if(updRole!=null)
//			{
//				String roleId[]=request.getParameterValues("roleId");
//				//处理用户权限信息
//				userService.updateUserRoleData(userlist,roleId,true);
//			}
//			String type = request.getParameter("type");
//			String dialogNo=request.getParameter("dialogNo");
//			//超级管理员
//			if(type==null||"".equals(type)){
//				
//
//				Alert_CloseDialog("修改成功!!1！",dialogNo);
//				
//			}
//				
//			else{
//				Alert_GoUrl("保存成功！", "listDeptUser.do",null,null);}
//			
//			return toMessage();
//		} else {
//			String userId = request.getParameter("userId");
//			userlist = (SysUserList) dao.findOne(SysUserList.class, userId);
//			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//			Date birthday = userlist.getBirthday();
//			if(birthday != null){
//				request.setAttribute("birthday", sf.format(userlist.getBirthday()));
//			}
//			request.setAttribute("userlist",userlist);
//			return toJsp("/jsp/system/user/update_user.jsp");
//		}
//	}
	
	/**
	 * 删除用户方法
	 * @return
	 * @throws Exception
	 */
	public String deleteUser() throws Exception {
		
		String userId = request.getParameter("uid");
		userlist = (SysUserList) dao.findOne(SysUserList.class, userId);
		userlist.setState("P");
		dao.update(userlist);
//		this.Alert_Close("删除成功!", "ok");
//		return toMessage();
		Alert_GoUrl("删除成功！","",null,null);
		return toResult();
	}
	/**
	 * 重置用户密码方法
	 * @return
	 * @throws Exception
	 */
	public String updateResetPasswordUser() throws Exception {
		
		String userid = request.getParameter("userId");
		String newpwd = "123456";
		MD5 md5 = new MD5();
		String pwd = md5.toMD5(userid+newpwd);
		userlist = (SysUserList) dao.findOne(SysUserList.class, userid);
		userlist.setPassword(pwd);
		dao.update(userlist);
		request.setAttribute("data", "ok");
		return toResult();
	}
	
	/**
	 * 为所创建的用户分配权限
	 * @return
	 * @throws Exception
	 */
	public String listFuncUser() throws Exception
	{
		String userId=request.getParameter("userId");
		//所有功能组
		List funcGroupList =  new ArrayList();
		//所有功能点
		List funcList = new ArrayList();
		String type =  request.getParameter("type");
		//超级管理员下属权限管理
		if(type==null||"".equals(type))
		{
			funcGroupList = dao.criteriaQuery(SysFuncGroup.class, new ArrayList());
			ArrayList<ParaUnit> paralist2 = new ArrayList<ParaUnit>();
			paralist2.add(new ParaUnit("state", "A", ParaUnit.EQ));
			paralist2.add(new ParaUnit("orderCol", ParaUnit.ASC, ParaUnit.ORDER));
			funcList = dao.criteriaQuery(SysFuncInfo.class, paralist2);
		}
		else
		{
			//部门用户管理员
			SessionBean sessionbean = SessionBean.getSessionBean(request);
			//用户所拥有的功能组
			String functionGroupHql = "from SysFuncGroup sfg where sfg.funcGroupId in (select sfgu.funcGroupId from SysFuncGroupUser sfgu where sfgu.userId='"+sessionbean.getUserId()+"')";
			funcGroupList = dao.findAll(functionGroupHql);
			String funcGroupIds = "";
			for(int i=0;i<funcGroupList.size();i++)
			{
				SysFuncGroup sfg = (SysFuncGroup) funcGroupList.get(i);
				funcGroupIds+="'"+sfg.getFuncGroupId()+"',";
			}
			//用户所拥有的所有功能点，包括功能组所包含的
			String hql_userfunc="from SysFuncInfo where funcId in(select funcId from SysUserFuncInfo where userId='"+sessionbean.getUserId()+"')";
			if(funcGroupIds.length()>0)
			{
				funcGroupIds = funcGroupIds.substring(0, funcGroupIds.length()-1);
				hql_userfunc +=" or funcId in(select funcId from SysFuncGroupList where funcGroupId in ("+funcGroupIds+"))";
			}
			hql_userfunc+=" order by orderCol,funcId";
			
			funcList = dao.findAll(hql_userfunc);
		}
		
		request.setAttribute("funcList",funcList);
		
		//用户所拥有的功能组
		List funcGroupByUser = new ArrayList();
		//用户未拥有的功能组
		List funcGroupNotByUser = new ArrayList();
		
		if(userId!=null)
		{
			//用户所拥有功能点
			ArrayList<ParaUnit> paralist = new ArrayList<ParaUnit>();
			paralist.add(new ParaUnit("userId", userId, ParaUnit.EQ));
			List userFuncList = dao.criteriaQuery(SysUserFuncInfo.class, paralist);
			String[] userFuncs = new String[userFuncList.size()];
			for(int i=0;i<userFuncList.size();i++)
			{
				SysUserFuncInfo ufi = (SysUserFuncInfo) userFuncList.get(i);
				userFuncs[i]=ufi.getFuncId();
			}
			request.setAttribute("userFuncs",userFuncs);
			
			//用户所拥有功能组
			ArrayList<ParaUnit> paralist1 = new ArrayList<ParaUnit>();
			paralist1.add(new ParaUnit("userId", userId, ParaUnit.EQ));
			List userFuncGroupList = dao.criteriaQuery(SysFuncGroupUser.class, paralist1);
			
			for(int i=0;i<funcGroupList.size();i++)
			{
				SysFuncGroup fg = (SysFuncGroup) funcGroupList.get(i);
				boolean test = false;
				for(int j=0;j<userFuncGroupList.size();j++)
				{
					SysFuncGroupUser fgu = (SysFuncGroupUser) userFuncGroupList.get(j);
					if(fg.getFuncGroupId().equals(fgu.getFuncGroupId()))
						test=true;
				}
				if(test)
					funcGroupByUser.add(fg);
				else
					funcGroupNotByUser.add(fg);
			}
		}
		else
		{
			funcGroupNotByUser.addAll(funcGroupList);
		}
		request.setAttribute("userFuncGroupList",funcGroupByUser);
		request.setAttribute("funcGroupList",funcGroupNotByUser);
		
		

		return toJsp("/jsp/system/user/upd_userfunc.jsp");
	}
	/**
	 * 角色列表显示
	 * @return
	 * @throws Exception
	 */
	public String listRoleUser() throws Exception{
		ArrayList<ParaUnit> paralist = new ArrayList<ParaUnit>();
		
		paralist.add(new ParaUnit("state", StateConst.STATE_DELETE, ParaUnit.NE));
		paralist.add(new ParaUnit("roleType", ParaUnit.DESC, ParaUnit.ORDER));
		paralist.add(new ParaUnit("roleName", ParaUnit.DESC, ParaUnit.ORDER));
		String type =  request.getParameter("type");
		
		//筛选部门管理员所拥有的角色
		if(type!=null&&!"".equals(type))
		{
			SessionBean sessionbean = SessionBean.getSessionBean(request);
			String userRoleHql = "select roleId from SysUserRole where userId ='"+sessionbean.getUserId()+"'";
			List maxUserRoleList = dao.findAll(userRoleHql);
			if(maxUserRoleList.size()>0)
			{
				String[] roleIds = new String[maxUserRoleList.size()];
				for(int i=0;i<roleIds.length;i++)
				{
					roleIds[i] = (String) maxUserRoleList.get(i);
				}
				paralist.add(new ParaUnit("roleId", roleIds, ParaUnit.IN));
			}
		}
		List list = dao.criteriaByPage(SysOperationRole.class, paralist);
		
		request.setAttribute("rolelist", list);
		
		String userId=request.getParameter("userId");
		if(userId!=null&&!"".equals(userId))
		{
			//用户所拥有角色
			ArrayList<ParaUnit> paralist1 = new ArrayList<ParaUnit>();
			paralist1.add(new ParaUnit("userId", userId, ParaUnit.EQ));
			List userRoleList = dao.criteriaQuery(SysUserRole.class, paralist1);
			
			String[] userRoles = new String[userRoleList.size()];
			
			for(int i=0;i<userRoles.length;i++)
			{
				SysUserRole userRole = (SysUserRole) userRoleList.get(i);
				userRoles[i] = userRole.getRoleId();
			}
			userlist = new SysUserList();
			userlist.setUserId(userId);
			request.setAttribute("userRoles",userRoles);
			
		}
		return toJsp("/jsp/system/user/update_userOperationRole.jsp");
	}
	
	/**
	 * 显示用户列表公共页面方法1
	 * 
	 * 从部门树的链接进入方法时，以部门ID和用户有效状态为条件，查询出所选部门中的全部有效用户。
	 * 用页面传递的已对应用户ID总数（userIds），作为过滤条件，分别获取已经对应了的用户集合，以及尚未对应的用户集合。
	 * 获取已经对应了的用户集合：拆分userIds，在循环中组装userlist对象，放入集合对象userListInUse中。
	 * 获取尚未对应的用户集合：在查询用户的条件中加入“非userIds”的过滤条件，查询得到userListNotInUse。
	 * 
	 * 条件查询用户集合时，在之前的查询条件中，增加用户名条件。
	 * 
	 * @return
	 * @throws Exception
	 */
	public String listCommonUser() throws Exception {

		ArrayList<ParaUnit> paralist = new ArrayList<ParaUnit>();
		paralist.add(new ParaUnit("state", "A", ParaUnit.EQ));
		paralist.add(new ParaUnit("groupSign", ParaUnit.ASC,ParaUnit.ORDER));
		paralist.add(new ParaUnit("userOrder", ParaUnit.ASC,ParaUnit.ORDER));
		
		List<SysUserList> list = new ArrayList<SysUserList>();//未对应的用户集合
		
		//已经关联的用户列表，根据调用时传入的参数进行拆分
		String userIds = request.getParameter("userIds");
		
		//如果userIds不为空，说明之前已经对应了用户，则根据部门条件，排除掉已经对应的用户，分页查询出状态有效的用户
		if(userIds!=null&&!"".equals(userIds)){
			String[] rejectUser = userIds.split(",");
			//-----组织尚未对应的用户集合-----	
			paralist.add(new ParaUnit("userId", rejectUser, ParaUnit.NOT_IN));
			
			request.setAttribute("userIds", userIds);
		}
		if(userlist!=null)
		{
			paralist.add(new ParaUnit("userId", userlist.getUserId(), ParaUnit.LIKE));
			paralist.add(new ParaUnit("userName", userlist.getUserName(), ParaUnit.LIKE));
		}
		list = dao.criteriaByPage(SysUserList.class, paralist);//部门中的全部有效人员集合
		request.setAttribute("datalist", list);
		
		String maxCount = request.getParameter("maxCount");
		request.setAttribute("maxCount", maxCount);
		
		return toJsp("/jsp/common/user/list_user.jsp");
	}
	public String listUserExportExcel() throws Exception{
		
		
//		StringBuffer userSql=new StringBuffer();
		SimpleDateFormat sf= new SimpleDateFormat("yyyy-MM-dd");
//		userSql.append("");
//		String userName=request.getParameter("userName");
//		String userId=request.getParameter("userId");
//		if(StringUtils.isNotEmpty(userId)){
//			userSql.append(" and userId like '%"+userId+"%'");
//		}
//	    request.setAttribute("userId", userId);
		
		ArrayList<ParaUnit> paralist = new ArrayList<ParaUnit>();
		String userId=request.getParameter("userId");
		if(StringUtils.isNotEmpty(userId)){
			paralist.add(new ParaUnit("userId", userId,ParaUnit.LIKE));
		}
		String userName=request.getParameter("userName");
		if(StringUtils.isNotEmpty(userName)){
			paralist.add(new ParaUnit("userName", userName,ParaUnit.LIKE));
		}
		String deptId=request.getParameter("deptId");
		if(StringUtils.isNotEmpty(deptId)){
			paralist.add(new ParaUnit("deptId", deptId,ParaUnit.LIKE));
		}
		
		paralist.add(new ParaUnit("state", "A", ParaUnit.EQ));
		List sysUserList=dao.criteriaAll(SysUserList.class, paralist);
		
		HSSFWorkbook hssfworkbook = new HSSFWorkbook();
		HSSFSheet hssfsheet = hssfworkbook.createSheet();
		HSSFRow row = null;
		HSSFCell cell = null;
		row = hssfsheet.createRow(0);
		cell= row.createCell(5);
		HSSFFont titleFont = hssfworkbook.createFont();  //设置字体
        titleFont.setFontName("黑体"); 
        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        titleFont.setFontHeightInPoints((short) 20);  
        HSSFCellStyle titleStyle = hssfworkbook.createCellStyle(); 
        titleStyle.setFont(titleFont);  
//		row=hssfsheet.createRow(1);
		cell=row.createCell(0);
		cell.setCellValue("登录ID");
		cell=row.createCell(1);
		cell.setCellValue("用户姓名");
		hssfsheet.setColumnWidth(1, 10000);
		cell=row.createCell(2);
		cell.setCellValue("分组标识");
		hssfsheet.setColumnWidth(2, 10000);
		cell=row.createCell(3);
		cell.setCellValue("所属部门");
		hssfsheet.setColumnWidth(3, 10000);
		cell=row.createCell(4);
		cell.setCellValue("办公电话");
		hssfsheet.setColumnWidth(4, 8000);
		cell=row.createCell(5);
		cell.setCellValue("移动电话");
		hssfsheet.setColumnWidth(5, 9000);
		cell=row.createCell(6);
		cell.setCellValue("电子邮件");
		hssfsheet.setColumnWidth(6, 9000);
		int rowNum=1;
		for(int i=0;i<sysUserList.size();i++){
			row=hssfsheet.createRow(rowNum);
			rowNum++;
			SysUserList user=(SysUserList) sysUserList.get(i);
		
			cell=row.createCell(0);
			cell.setCellValue(user.getUserId());
			
			cell=row.createCell(1);
			cell.setCellValue(user.getUserName());
			
			String groupSign="";
//			if(StringUtils.isNotEmpty(user.getGroupSign())){
//				groupSign=user.getGroupSign().toString();
//			}
			cell=row.createCell(2);
			cell.setCellValue(groupSign);
			
			String deptName="";
			if(user.getDeptId()!= null){
				SysOperationRole opRole = (SysOperationRole) dao.findOne(SysOperationRole.class, user.getDeptId().toString());
				deptName=opRole.getRoleName().toString();
			}
			cell=row.createCell(3);
			cell.setCellValue(deptName);
			
			String officeTel="";
			if(user.getOfficeTel()!= null){
				officeTel=user.getOfficeTel().toString();
			}
			cell=row.createCell(4);
			cell.setCellValue(officeTel);
			
			String mobileTelephone="";
			if(user.getMobileTelephone() != null){
				mobileTelephone = user.getMobileTelephone().toString();
			}
			cell=row.createCell(5);
			cell.setCellValue(mobileTelephone);
			
			String email="";
			if (user.getEmail()!=null) {
				email=user.getEmail().toString();
			}
			cell=row.createCell(6);
			cell.setCellValue(email);
			
	
		}
		String x = new String(("职员信息").getBytes("GBK"), "ISO8859-1");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("content-disposition",
		"attachment;filename=\""
		+x+ "\";size="
		+ hssfworkbook.getBytes().length);
		OutputStream out = response.getOutputStream();
		//将工作簿输出到response
		hssfworkbook.write(out);
		out.flush();
		out.close();
		Alert_GoUrl("导出成功！","",null,null);
		return toResult();
	}
	
	
	
	public SysUserList getUserlist() {
		return userlist;
	}

	public void setUserlist(SysUserList userlist) {
		this.userlist = userlist;
	}

	public List<SysUserList> getDataList() {
		return dataList;
	}

	public void setDataList(List<SysUserList> dataList) {
		this.dataList = dataList;
	}
	
}