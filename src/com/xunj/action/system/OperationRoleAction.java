package com.xunj.action.system;


import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.xunj.action.common.AbstractAction;
import com.xunj.core.ParaUnit;
import com.xunj.po.CodeDict;
import com.xunj.po.SysOperationRole;
import com.xunj.po.SysUserList;
import com.xunj.po.SysUserRole;
import com.xunj.util.StateConst;

/**
 * 角色管理
 *
 */
public class OperationRoleAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4654574303236951447L;
	private SysOperationRole operationRole;
	private List<SysOperationRole> dataList;

	/**
	 * 角色列表显示
	 * @throws Exception 
	 * @throws Exception
	 */
	public String saveUserOperationRole() throws Exception{
		
		ArrayList<ParaUnit> paralist = new ArrayList<ParaUnit>();
		paralist.add(new ParaUnit("state", "A", ParaUnit.EQ));
		paralist.add(new ParaUnit("groupSign", ParaUnit.ASC,ParaUnit.ORDER));
		paralist.add(new ParaUnit("userOrder", ParaUnit.ASC,ParaUnit.ORDER));
		
		List<SysUserList> list = new ArrayList<SysUserList>();//未对应的用户集合
		
		//已经关联的用户列表，根据调用时传入的参数进行拆分
		String userIds = request.getParameter("checkUserIds");
		String roleId = request.getParameter("roleId");
		String dialogNo = request.getParameter("dialogNo");
		
		//如果userIds不为空，说明之前已经对应了用户，则根据部门条件，排除掉已经对应的用户，分页查询出状态有效的用户
		if(userIds!=null&&!"".equals(userIds)){
			String[] rejectUser = userIds.split(",");
			//-----组织尚未对应的用户集合-----	
			paralist.add(new ParaUnit("userId", rejectUser, ParaUnit.NOT_IN));
			
			request.setAttribute("userIds", userIds);
			for(int i=0;i<rejectUser.length;i++){
				SysUserRole userRole=new SysUserRole();
	            userRole.setRoleId(roleId);
	            userRole.setUserId(rejectUser[i]);
				dao.save(userRole);
			
			}
		}
		
			
		
		
		
		this.Alert_CloseDialog("保存成功", dialogNo);
		
		return toMessage();
		
	}
//	saveUserOperationRole() 
	
	public String listDepartmentOperationRole() throws Exception{
		ArrayList<ParaUnit> paralist = new ArrayList<ParaUnit>();
		if (operationRole!=null) {
			if(operationRole.getRoleName()!=null && !"".equals(operationRole.getRoleName())){
//				paralist.add(new ParaUnit("roleName", operationRole.getRoleName().toUpperCase(),ParaUnit.LIKE));
				paralist.add(new ParaUnit("roleName", operationRole.getRoleName(),ParaUnit.LIKE));
			}
			if(operationRole.getRoleCode()!=null && !"".equals(operationRole.getRoleCode())){
				paralist.add(new ParaUnit("roleCode", operationRole.getRoleCode(),ParaUnit.LIKE));
			}
		}
		
		paralist.add(new ParaUnit("state", StateConst.STATE_DELETE, ParaUnit.NE));
		paralist.add(new ParaUnit("roleCode", ParaUnit.ASC, ParaUnit.ORDER));
		dataList = dao.criteriaByPage(SysOperationRole.class,paralist);
		String xjTmp = request.getParameter("xjTmp");
		
		if(StringUtils.isNotEmpty(xjTmp)){
			request.setAttribute("datalist", dataList);
			return toResult();
		}else{
			request.setAttribute("datalist", dataList);
			return toJsp("/jsp/system/operationRole/list_department.jsp");
		}
	}
	
	public String listOperationRole() throws Exception{
		ArrayList<ParaUnit> paralist = new ArrayList<ParaUnit>();
		String handler=request.getParameter("handler");
		if(handler!=null&&!"".equals(handler)){
			request.setAttribute("handler", handler);
		}
		paralist.add(new ParaUnit("state", StateConst.STATE_DELETE, ParaUnit.NE));
		paralist.add(new ParaUnit("roleCode", ParaUnit.ASC, ParaUnit.ORDER));
		//List list = dao.criteriaByPage(SysOperationRole.class, paralist);
		List list=dao.findAll("from SysOperationRole where state='A'");
		request.setAttribute("datalist", list);
		
		
		
		String roleId=request.getParameter("roleId");
		request.setAttribute("roleId", roleId);
		String pageNumber = request.getParameter("pageNumber");
		
		StringBuffer hqlShow=new StringBuffer();//用来查询要显示用户的语句
		StringBuffer counthqlShow=new StringBuffer();//用来查询要显示用户的语句
		StringBuffer hqlAll=new StringBuffer();//用来查询该角色对应的全部用户的语句
		int countShow = 0;//显示在页面中的符合条件的用户总数
		StringBuffer userIds=null;//角色对应的用户ID大字符串
		
		List listShow=null;//显示在页面中的符合条件的用户集合
		List listAll=null;//角色对应的用户集合
		
		//查询该角色对应的，并且状态为有效的用户集合
		hqlShow.append("from  SysUserList ul where ul.userId in (select ur.userId from SysUserRole ur where ur.roleId='"+roleId+"')");
		counthqlShow.append("select count(*) from SysUserList ul where ul.userId in (select ur.userId from SysUserRole ur where ur.roleId='"+roleId+"')");
		
		ArrayList<ParaUnit> wherelist=new ArrayList<ParaUnit>();
		wherelist.add(new ParaUnit("roleId",roleId,ParaUnit.StringType()));
		//wherelist.add(new ParaUnit("state", StateConst.STATE_DELETE, ParaUnit.StringType()));
		
		if(POST()){
			String userId = request.getParameter("userId");
			String userName = request.getParameter("userName");
			
			request.setAttribute("userId", userId);
			request.setAttribute("userName", userName);
			
			//----------根据查询条件，查询出该角色对应的、符合查询条件的、状态为有效的用户集合，并向页面返回------
			hqlShow.append(" and ul.userId like '%"+userId+"%' and ul.userName like '%"+userName+"%'");
			counthqlShow.append(" and ul.userId like '%"+userId+"%' and ul.userName like '%"+userName+"%'");
		}
		
		//角色所对应的用户总数
		countShow=dao.findRowCount(counthqlShow.toString());
		//角色所对应的用户集合
		listShow=dao.findAll(hqlShow.toString());
		request.setAttribute("list", listShow);
		
		//----------查询出该角色对应的、状态为有效的全部用户集合，利用其组织userIds，向页面返回------
		hqlAll.append("from SysUserList ul where ul.userId in (select ur.userId from SysUserRole ur where ur.roleId='"+roleId+"')");
		//角色所对应的、符合查询条件的用户集合
		listAll=dao.findAll(hqlAll.toString());
		if(listAll!=null&&listAll.size()>0)
		{
			userIds=new StringBuffer();
			for(int i=0;i<listAll.size();i++)
			{
				userIds.append(((SysUserList)listAll.get(i)).getUserId());
				userIds.append(",");
			}
			request.setAttribute("userIds", userIds.toString());
		}
		
		return toJsp("/jsp/system/operationRole/list_operationRole.jsp");
	}
	
	/**
	 * 添加角色。
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String saveOperationRole() throws Exception{
		boolean isInsertStorage = false;
		if(operationRole.getRoleId()==null|| "".equals(operationRole.getRoleId()))
			isInsertStorage = true;
		
		if (isInsertStorage){
			SysOperationRole tempOperationRole  = (SysOperationRole) dao.findFirst("from SysOperationRole where roleName='"+ operationRole.getRoleName()+"' and state = 'A'");
			if(tempOperationRole!=null){ //分类码存在
				Alert_GoUrl("该部门已存在！", "",null,null);
				return toError();
			}
		}
		operationRole.setState(StateConst.STATE_USE);
		if(isInsertStorage){
			String roleId = dao.save(operationRole);
			Alert_GoUrl("角色添加成功！","closeCurrent","部门管理",null);
			return toResult();
		}else{
			dao.update(operationRole);
			Alert_GoUrl("修改角色成功！","closeCurrent","部门管理",null);
			return toResult();
		}
//		String dialogNo=request.getParameter("dialogNo");
	}
	
	/**
	 * 获取角色代码
	 * @return
	 * @throws Exception
	 */
	public String addOperationRole() throws Exception{
		String roleCode = dao.getNewTableId("SysOperationRole", "roleCode", "", 3);
		request.setAttribute("roleCode",roleCode );
		return toJsp("/jsp/system/operationRole/add_operationRole.jsp");
	}
	/**
	 * 根据角定的角色ID，修改角色。
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String updateOperationRole() throws Exception{
		if(POST())
		{
//			if(operationRole.getState().equals(StateConst.STATE_SYSTEM))
//			{
				
//				Alert_GoUrl("修改角色成功！","updateOperationRole.do?roleId="+operationRole.getRoleId());
//			}
//			else
//				Alert_GoUrl("系统内定角色不能修改！","updateOperationRole.do?roleId="+operationRole.getRoleId());
				dao.update(operationRole);
//				String dialogNo=request.getParameter("dialogNo");
				Alert_GoUrl("修改角色成功！","closeCurrent","部门管理",null);
			return toResult();
		}
		else
		{
			String roleId=request.getParameter("roleId");
			operationRole=(SysOperationRole)dao.findOne(SysOperationRole.class,roleId);
			request.setAttribute("operationRole",operationRole);
			
			return toJsp("/jsp/system/operationRole/update_operationRole.jsp");
		}
	}
	
	public String delOperationRole() throws Exception {
		String roleId=request.getParameter("roleId");
		operationRole=(SysOperationRole) dao.findOne(SysOperationRole.class, roleId);
		if(!operationRole.getState().equals(StateConst.STATE_SYSTEM))
		{
//			String hql = "delete from SysUserRole where roleId='"+roleId+"'";
//			dao.bulkUpdate(hql);
			operationRole.setState(StateConst.STATE_DELETE);
			dao.update(operationRole);
			Alert_Close("删除成功！", "ok");
			return toResult();
		}
		else
		{
			Alert_GoUrl("当前角色为系统内定，不能删除!", "", null,null);
			return toError();
		}
//		return toMessage();
	}

	/**
	 * 查询角色所对应的用户集合
	 * @return
	 * @throws Exception
	 */
	
	/**
	 * 为角色添加用户
	 * @return
	 * @throws Exception
	 */
	public String insUserOperationRole() throws Exception
	{
		String users = request.getParameter("users");
		
		String roleId = request.getParameter("roleId");
		request.setAttribute("roleId", roleId);
		operationRole = (SysOperationRole)dao.findOne(SysOperationRole.class, roleId);
		//获取该角色已经对应的用户集合
		ArrayList<SysUserRole> userRoleList = new ArrayList<SysUserRole>();
		String userId = "";//用户ID
		
		if(users!=null && !"".equals(users)){
			//用;进行拆分，得到每组用户信息，存放在user数组中
			String[] userArr = users.split(";");
			if(userArr!=null && userArr.length>0){
				for(int i=0;i<userArr.length;i++){
					SysUserRole userRole = new SysUserRole();
					//用,进行拆分，得到每组用户信息中具体信息内容，包括用户ID，用户名，用户所在的部门ID，用户所在的部门名称
					userId = userArr[i];
					if(userId!=null ){
						userRole.setUserId(userId.split(",")[0]);
						userRole.setRoleId(roleId);
						
						userRoleList.add(userRole);
					}
				}
				dao.saveAll(userRoleList);
			}
		}
		Alert_GoUrl("分配用户成功！","listUserOperationRole.do?roleId="+roleId);
		return toMessage();
	}
	
	/**
	 * 为角色删除用户
	 * @return
	 * @throws Exception
	 */
	public String delUserOperationRole() throws Exception
	{
		String users = request.getParameter("users");
		
		String roleId = request.getParameter("roleId");
		String roleName = request.getParameter("roleName");
		request.setAttribute("roleId", roleId);
		request.setAttribute("roleName", roleName);
		
		//根据从页面接收到的要删除用户的复选框的值，组织删除用户的查询条件
		StringBuffer whereUserIds = new StringBuffer();
		if(users!=null && !"".equals(users)){
			String[] usersArr = users.split(",");
			int len = usersArr.length;
			for(int i=0;i<len-1;i++){
				whereUserIds.append("'");
				whereUserIds.append(usersArr[i]);
				whereUserIds.append("',");
			}
			whereUserIds.append("'");
			whereUserIds.append(usersArr[len-1]);
			whereUserIds.append("'");
			
			String hql = "delete SysUserRole ur where ur.roleId = '"+roleId+"' and ur.userId in ( "+whereUserIds.toString()+" )";
			dao.bulkUpdate(hql);
			Alert_Close("删除用户成功！","ok");
		}else{
			Alert_Close("删除用户失败！","ok");
		}
		return toMessage();
	}
	
	/**
	 * 按人分配角色
	 * @return
	 * @throws Exception
	 */
	public String addRoleByOperationRole() throws Exception
	{
		if(POST())
		{
			String group[]=request.getParameterValues("selectRoleRight");
			String roleId[]=request.getParameterValues("roleId");
			String userId=request.getParameter("userId");
			StringBuffer hql=new StringBuffer("delete SysUserRole ur where ur.userId='"+userId+"'");
			dao.bulkUpdate(hql.toString());
			
			if(group!=null&&group.length>0)
			{
				ArrayList userRoleList=new ArrayList();
				
				for(int i=0;i<group.length;i++)
				{
					SysUserRole userRole=new SysUserRole();
					SysUserList userlist=new SysUserList();
					SysOperationRole operationRole=new SysOperationRole();
					userlist.setUserId(userId);
					operationRole.setRoleId(group[i]);
					userRole.setRoleId(operationRole.getRoleId());
					userRole.setUserId(userlist.getUserId());
					userRoleList.add(userRole);
				}
				dao.saveOrUpdateAll(userRoleList);
			}
			this.Alert_Close("保存成功", "ok");
			return toMessage();
		}
		else
		{
			String userId=request.getParameter("userId");
			String userName=request.getParameter("userName");
			
			//用户所拥有角色
			ArrayList<ParaUnit> paralist1 = new ArrayList<ParaUnit>();
			paralist1.add(new ParaUnit("userId", userId, ParaUnit.EQ));
			List userRoleList = dao.criteriaQuery(SysUserRole.class, paralist1);
			
			//所有角色
			List operationRoleList = dao.criteriaQuery(SysOperationRole.class, new ArrayList());
			//用户所拥有的角色
			List roleByUser = new ArrayList();
			//用户未拥有的角色
			List roleNotByUser = new ArrayList();
			for(int i=0;i<operationRoleList.size();i++)
			{
				SysOperationRole or = (SysOperationRole) operationRoleList.get(i);
				boolean test = false;
				for(int j=0;j<userRoleList.size();j++)
				{
					SysUserRole ur = (SysUserRole) userRoleList.get(j);
					if(or.getRoleId().equals(ur.getRoleId()))
						test=true;
				}
				if(test)
					roleByUser.add(or);
				else
					roleNotByUser.add(or);
			}
			request.setAttribute("userByRoleList",roleByUser);
			request.setAttribute("roleList",roleNotByUser);
			request.setAttribute("userId",userId);
			request.setAttribute("userName",userName);
	
			return toJsp("/jsp/system/operationRole/addRoleBy_operationRole.jsp");
		}
	}
	public String listUserOperationRole() throws Exception{
		ArrayList<ParaUnit> paralist = new ArrayList<ParaUnit>();
		String roleId = request.getParameter("roleId");
		String userId= request.getParameter("userId");
		String userName=request.getParameter("userName");
		request.setAttribute("roleId", roleId);
		request.setAttribute("userId", userId);
		request.setAttribute("userName", userName);
		//operationRole = (SysOperationRole)dao.findOne(SysOperationRole.class, roleId);
//		String userIdSql = "";
		if (userId==null) {
			userId="";
		}else{
			if(!userId.trim().equals("")){
//				userIdSql += " and userId like '%" + userId + "%'";
				paralist.add(new ParaUnit("userId", userId,ParaUnit.LIKE));
			}
		}
//		String userNameSql = "";
		if (userName==null) {
			userName="";
		}else{
			if(!userName.trim().equals("")){
//				userNameSql += " and userName like '%" + userName + "%'";
				paralist.add(new ParaUnit("userName", userName,ParaUnit.LIKE));
			}
		}
		paralist.add(new ParaUnit("deptId", roleId,ParaUnit.EQ));
//		List userList=dao.findAll("from SysUserList where userId in( select userId from SysUserRole where roleId='"+roleId+"') and userId like'%"+userId+"%' and userName like'%"+userName+"%'");
//		List userList=dao.findAll("from SysUserList where deptId='"+roleId+"'" + userIdSql + userNameSql);
		List userList=dao.criteriaByPage(SysUserList.class, paralist);
//		super.paralist = paralist;
		request.setAttribute("userList", userList);
		
		return toJsp("/jsp/system/operationRole/listUser_operationRole.jsp");
		
		
	}
	
	public String listOperationRoleExportExcel() throws Exception{
		SimpleDateFormat sf= new SimpleDateFormat("yyyy-MM-dd");
		ArrayList<ParaUnit> paralist = new ArrayList<ParaUnit>();
		String roleName=request.getParameter("roleName");
		if(StringUtils.isNotEmpty(roleName)){
			paralist.add(new ParaUnit("roleName", roleName,ParaUnit.LIKE));
		}
		String roleCode=request.getParameter("roleCode");
		if(StringUtils.isNotEmpty(roleCode)){
			paralist.add(new ParaUnit("roleCode", roleCode,ParaUnit.LIKE));
		}
		
		paralist.add(new ParaUnit("state", StateConst.STATE_DELETE, ParaUnit.NE));
		paralist.add(new ParaUnit("roleCode", ParaUnit.ASC, ParaUnit.ORDER));
		dataList = dao.criteriaAll(SysOperationRole.class,paralist);
//		List sysUserList=dao.criteriaAll(SysUserList.class, super.paralist);
		
		HSSFWorkbook hssfworkbook = new HSSFWorkbook();
		HSSFSheet hssfsheet = hssfworkbook.createSheet();
		HSSFRow row = null;
		HSSFCell cell = null;
		row = hssfsheet.createRow(0);
//		cell= row.createCell(3);
		HSSFFont titleFont = hssfworkbook.createFont();  //设置字体
        titleFont.setFontName("黑体"); 
        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        titleFont.setFontHeightInPoints((short) 20);  
        HSSFCellStyle titleStyle = hssfworkbook.createCellStyle(); 
        titleStyle.setFont(titleFont);  
//		row=hssfsheet.createRow(1);
		cell=row.createCell(0);
		cell.setCellValue("部门代码");
		cell=row.createCell(1);
		cell.setCellValue("部门名称");
		hssfsheet.setColumnWidth(1, 10000);
		cell=row.createCell(2);
		cell.setCellValue("部门标识");
		hssfsheet.setColumnWidth(2, 10000);
		int rowNum=1;
		for(int i=0;i<dataList.size();i++){
			row=hssfsheet.createRow(rowNum);
			rowNum++;
			SysOperationRole operationRole=(SysOperationRole) dataList.get(i);
		
			cell=row.createCell(0);
			cell.setCellValue(operationRole.getRoleCode());
			
			cell=row.createCell(1);
			cell.setCellValue(operationRole.getRoleName());
			
			String roleSign="";
			if(StringUtils.isNotEmpty(operationRole.getRoleSign())){
				CodeDict sysCode = (CodeDict) dao.findFirst(" from SysCodeDict where codeId like '050%' and codeCode ='"+operationRole.getRoleSign().toString()+"'");
				if(sysCode!=null){
					roleSign=sysCode.getCodeName().toString();
				}
			}
			cell=row.createCell(2);
			cell.setCellValue(roleSign);
			
			
	
		}
		String x = new String(("部门信息").getBytes("GBK"), "ISO8859-1");
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
	
	public SysOperationRole getOperationRole() {
		return operationRole;
	}
	public void setOperationRole(SysOperationRole operationRole) {
		this.operationRole = operationRole;
	}
}