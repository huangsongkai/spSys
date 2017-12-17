package com.xunj.service.system;

import java.util.ArrayList;
import java.util.List;

import com.xunj.core.CoreDao;
import com.xunj.po.SysFuncGroup;
import com.xunj.po.SysFuncGroupUser;
import com.xunj.po.SysFuncInfo;
import com.xunj.po.SysUserFuncInfo;
import com.xunj.po.SysUserList;
import com.xunj.po.SysUserRole;

public class UserService {

	private CoreDao dao;

	public UserService(CoreDao dao) {
		this.dao = dao;
	}
	
	/**
	 * 更新用户权限数据
	 * @param userlist	用户信息
	 * @param group		功能组
	 * @param funcId	权限ID
	 * @param deleteBeforeInsert 更新前是否删除以前保存的数据，新增时传入false
	 * @throws Exception
	 */
	public void updateUserFuncData(SysUserList userlist,String[] group,String[] funcId,boolean deleteBeforeInsert) throws Exception
	{
		if(deleteBeforeInsert)
		{
			String hql="delete SysFuncGroupUser fg where fg.userId='"+userlist.getUserId()+"'";
			String hql_func="delete SysUserFuncInfo uf where uf.userId='"+userlist.getUserId()+"'";
			dao.bulkUpdate(hql.toString());
			dao.bulkUpdate(hql_func.toString());
		}
		if(group!=null&&group.length>0)
		{
			ArrayList funcGroupUserList=new ArrayList();
			
			for(int i=0;i<group.length;i++)
			{
				SysFuncGroupUser funcGroupUser=new SysFuncGroupUser();
				SysFuncGroup funcGroup=new SysFuncGroup();
				funcGroup.setFuncGroupId(group[i]);
				funcGroupUser.setFuncGroupId(funcGroup.getFuncGroupId());
				funcGroupUser.setUserId(userlist.getUserId());
				funcGroupUserList.add(funcGroupUser);
			}
			dao.saveOrUpdateAll(funcGroupUserList);
		}
		if(funcId!=null&&funcId.length>0)
		{
			ArrayList userFuncInfoList=new ArrayList();
			for(int i=0;i<funcId.length;i++)
			{
				SysUserFuncInfo userFuncInfo=new SysUserFuncInfo();
				SysFuncInfo funcInfo=new SysFuncInfo();
				funcInfo.setFuncId(funcId[i]);
				userFuncInfo.setFuncId(funcInfo.getFuncId());
				userFuncInfo.setUserId(userlist.getUserId());
				userFuncInfoList.add(userFuncInfo);
			}
			dao.saveOrUpdateAll(userFuncInfoList);
		}
	}

	public void updateUserRoleData(SysUserList userlist, String[] roleId,boolean deleteBeforeInsert) throws Exception {
		if(deleteBeforeInsert)
		{
			String hql="delete SysUserRole ur where ur.userId='"+userlist.getUserId()+"'";
			dao.bulkUpdate(hql);
		}
		List userRoleList = new ArrayList();
		for(int i=0;i<roleId.length;i++)
		{
			SysUserRole userRole=new SysUserRole();
			userRole.setRoleId(roleId[i]);
			userRole.setUserId(userlist.getUserId());
			userRoleList.add(userRole);
		}
		dao.saveOrUpdateAll(userRoleList);
	}
}
