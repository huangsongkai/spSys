package com.exam.dao;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import com.xunj.core.CoreDao;

public class CommonDAO {
	private CoreDao dao;

	public CommonDAO(CoreDao dao) {
		this.dao = dao;
	}
	public BigDecimal getSequenceId(String seqName) throws Exception {
		return (BigDecimal) dao.findFirst("select "+seqName+".nextval from dual");
	}
	public int getNewTableId(String tableName,String columnName) throws Exception {
		int maxId = 1;
		List maxlist = dao.findAll("select max("+columnName+") as "+columnName +"  from "+tableName );
		for (Iterator it = maxlist.iterator(); it.hasNext();) {
			Object value = it.next();
			if (value != null)
				maxId = Integer.parseInt(String.valueOf(value))+1;
		}
		return maxId;
	}
}
