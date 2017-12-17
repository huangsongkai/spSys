package com.xunj.service.system;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.xunj.core.CoreDao;
import com.xunj.core.ParaUnit;
import com.xunj.po.CodeDict;
import com.xunj.util.Common;
import com.xunj.util.StateConst;

public class CodeService {

	private CoreDao dao;

	public CodeService(CoreDao dao) {
		this.dao = dao;
	}
	/**
	 * 检索字典码最大ID值，计算新增加的字典码的ID
	 * @return
	 * @throws Exception
	 */
	public String getNewKindId() throws Exception {
		String newId = null;
		String maxId = null;
		String hql = "select new Map(max(kindId) as kindId) from CodeKind";
		List list = dao.findAll(hql);
		if (!list.isEmpty()) {
			Map valueMap = (Map) list.get(0);
			if(valueMap.get("kindId")!=null)
				maxId = valueMap.get("kindId").toString();
		}
		newId =Common.createSequence(maxId, "0", 3);
		return newId;
	}
	/**
	 * 获取新添加的字典码ID
	 * @param parentId 上级字典码ID
	 * @return
	 * @throws Exception 
	 */
	public String getNewCodeId(String parentId) throws Exception
	{
		String newId = null;
		String maxId = null;
		List maxlist=dao.findAll("select max(model.codeId) as codeId from CodeDict as model where model.parentCodeId='"+parentId+"'");
		Iterator it=maxlist.iterator();
		while(it.hasNext())
		{
			Object value=it.next();
			
			if(value!=null)
				maxId = (String)value;
		}
		newId =Common.createSequence(maxId, parentId, 3);
		return newId;
	}
	/**
	 * 获取指定大类下的字典信息
	 * @param kindId 字典大类ID
	 * @return
	 * @throws Exception 
	 */
	public List getCodeDictByKindId(String kindId) throws Exception
	{
		ArrayList<ParaUnit> paralist = new ArrayList<ParaUnit>();
		paralist.add(new ParaUnit("kindId", kindId,ParaUnit.EQ));
		paralist.add(new ParaUnit("state", StateConst.STATE_USE,ParaUnit.EQ));
		paralist.add(new ParaUnit("codeId", ParaUnit.ASC,ParaUnit.ORDER));
		
		List list = dao.criteriaQuery(CodeDict.class, paralist);
		return list;
	}
}
