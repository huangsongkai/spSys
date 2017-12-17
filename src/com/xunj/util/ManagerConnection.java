/*
 * Created on 2005-5-3
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.xunj.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ManagerConnection {
	private String userId;
	private String password;
	private String connectURL;
	private String driverName;
	
	public ManagerConnection(HashMap dbMap)
	{
		userId = (String) dbMap.get("userId");
		password = (String) dbMap.get("password");
		connectURL = (String) dbMap.get("connectURL");
		driverName = (String) dbMap.get("driverName");
	}
	
	/**get connection object*/
	public Connection getConn() {
		Connection conn = null;
		try {
			Class.forName(driverName); 
			conn=DriverManager.getConnection(
		    	      connectURL,
		    	      userId,password);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
    /**
     * 关闭数据库连接
     * 
     * @param con
     */
    public void close(Connection con) {
        close(null, null, con);
    }

    /**
     * 关闭数据库连接
     * 
     * @param stmt
     * @param con
     */
    public void close(Statement stmt, Connection con) {
        close(null, stmt, con);
    }

    /**
     * 关闭数据库连接:这是最安全的关闭连接方法.
     * 
     * @param rs
     *            ResultSet
     * @param stmt
     *            Statement
     * @param con
     *            Connection
     */
    public void close(ResultSet rs, Statement stmt, Connection con) {
        if (rs != null) {
            try {
                rs.close();
                rs = null;
            } catch (SQLException e) {
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
                stmt = null;
            } catch (SQLException e1) {
            }
        }
        if (con != null) {
            try {
                con.close();
                con = null;
            } catch (SQLException e2) {
            }
        }
    }
}
