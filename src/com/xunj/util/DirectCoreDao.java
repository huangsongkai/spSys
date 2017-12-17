package com.xunj.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class DirectCoreDao {

	private HashMap dbMap;
	
	public DirectCoreDao(HashMap dbMap)
	{
		this.dbMap = dbMap;
	}
	
	public ArrayList getQueryResult(String sql)//部门信息
	{
		ArrayList list = new ArrayList();
		ManagerConnection mc = new ManagerConnection(dbMap);
		Connection conn = mc.getConn();
		Statement st = null;
		ResultSet rs = null;
		String strSQL=null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			ResultSetMetaData rsmd =rs.getMetaData();
			while (rs.next()) {
				HashMap rsData = new HashMap();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					rsData.put(rsmd.getColumnName(i),rs.getString(rsmd.getColumnName(i)));
				}
				list.add(rsData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally{
			mc.close(rs,st,conn);
		}
		return list;
	}
}
