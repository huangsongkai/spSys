package com.xunj.service.system;

import java.util.Iterator;
import java.util.List;

import com.xunj.core.CoreDao;
import com.xunj.util.Common;

public class FuncInfoService {

	private CoreDao dao;

	public FuncInfoService(CoreDao dao) {
		this.dao = dao;
	}

	/**
	 * 获取菜单ID
	 * @param parentId 上级菜单ID
	 * @return
	 * @throws Exception 
	 */
	public String getNewFuncInfoId(String parentId) throws Exception
	{
		String newId = null;
		String maxId = null;
		List maxlist=dao.findAll("select max(model.funcId) as funcId from SysFuncInfo as model where model.funcParentId='"+parentId+"'");
		Iterator it=maxlist.iterator();
		while(it.hasNext())
		{
			Object value=it.next();
			
			if(value!=null)
				maxId=(String)value;
		}
		newId =Common.createSequence(maxId, parentId, 3);
		return newId;
	}
}
