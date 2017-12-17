package com.xunj.util;

import java.util.HashMap;

/**
 * 保存各类直连数据库链接数据
 * @author www
 *
 */
public class DBUtil {

	public static HashMap getDb1()
	{
		HashMap dbMap;
		dbMap = new HashMap();
		dbMap.put("driverName", "oracle.jdbc.driver.OracleDriver");
		dbMap.put("userId", "zdbom");
		dbMap.put("password", "hitzd");
		dbMap.put("connectURL", "jdbc:oracle:thin:@192.168.17.74:1521:orcl");
		return dbMap;
	}
}
