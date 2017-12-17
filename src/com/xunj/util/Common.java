package com.xunj.util;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Common {
	
	/**
	 * double类型格式化
	 * @param d
	 * @param fmt
	 * @return
	 */
	public static String doublefmt(Double d,String fmt) {
		if(d==null){
			return null;
		}
		DecimalFormat df=new DecimalFormat(fmt); 
		return df.format(d);
	}

	/**
	 * Double数值加和
	 * @param d
	 * @return
	 */
	public static Double sum(Double... d)
	{
		BigDecimal bd=new BigDecimal("0");
		for(Double obj:d)
		{
			if(obj!=null)
				bd=bd.add(new BigDecimal(obj));
		}
		return bd.doubleValue();
	}
	public static Double NullToZero(Double d)
	{
		if(d==null)
			d=new Double("0");
		return d;
	}
	public static Double StringToDouble(String s)
	{
		try
		{
			if(s!=null&&!s.equals(""))
				return new Double(s);
			else
				return null;
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	
	/**
	 * 返回年
	 * 
	 * @param corporationBirthday
	 * @return
	 */
	public static String getYearToString() {

		// 存放日期的变量
		String dateStr = "";
		// 获取字符型当前日期
		String time = getDateTimeFormat(new Date(), "yyyy-MM-dd");
		// 如果字符型日期不为空,进行时间转换
		if (StringUtils.isNotBlank(time)) {
			// 字符型转成日期型
			Date corporationBirthday = newDate(time, "yyyy-MM-dd");
			// Calendar实体整型转换
			Calendar cal = Calendar.getInstance();
			// 如果日期型日期不为空,进行组合
			if (corporationBirthday != null) {
				// cal获取日期
				cal.setTime(corporationBirthday);
				// 获取整型的年
				int year = cal.get(Calendar.YEAR);
				// 组合
				dateStr = year + "";
			}
		}
		// 返回日期
		return dateStr;

	}
	/**
	 * 日期比较函数
	 * 
	 * @param dt
	 * @param format
	 *            can be one of "yyyy/MM/dd/ HH:mm:ss:SSS"
	 * @return
	 */
	public static int compareTo(Date dt, Date dt2) {

		Calendar c1 = Calendar.getInstance(); 
		Calendar c2 = Calendar.getInstance(); 
		c1.setTime(dt);
		c2.setTime(dt2);
		//如果 c1时间比c2大 则返回1  否则返回-1 相等则返回0
		return c1.compareTo(c2);

	}
	
	/**
	 * 日期比较函数
	 * 
	 * @param dt 
	 * @param dt2
	 * @param hh 小时
	 * @param MM 分钟
	 * @return
	 */
	public static int compareTo(Date dt, Date dt2,int hh,int MM) {

		Calendar c1 = Calendar.getInstance(); 
		Calendar c2 = Calendar.getInstance(); 
		c1.setTime(dt);
		c2.setTime(dt2);
		if(hh!=0){
			c1.add(Calendar.HOUR_OF_DAY, hh);
		}
		if(MM!=0){
			c1.add(Calendar.MINUTE, MM);
		}	
		System.out.println("1:"+c1.getTime()+":2:"+c2.getTime());
		//如果 c1时间比c2大 则返回1  否则返回-1 相等则返回0
		return c1.compareTo(c2);

	}
	/**
	 * 日期处理函数
	 * 
	 * @param dt
	 * @param format
	 *            can be one of "yyyy/MM/dd/ HH:mm:ss:SSS"
	 * @return
	 */
	public static String getDateTimeFormat(Date dt, String format) {
		if (dt == null||dt.toString().equals("1970-01-01 08:00:01.899")||dt.toString().equals("1970-01-01 00:00:00.0")) {
			return "";
		}

		SimpleDateFormat sf = new SimpleDateFormat(format);

		return sf.format(dt);

	}
	
	/**
	 * 日期处理函数
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date newDate(String date, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		try {
			if(date!=null&&!date.equals(""))
				return df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	Log log=LogFactory.getLog(this.getClass());

	/** get current year */
	public static String getCurrentyear() {
		Date nowTime = new Date();
		SimpleDateFormat matyear = new SimpleDateFormat("yyyy");
		return String.valueOf(matyear.format(nowTime));
	}
	/** get current month */
	public static String getCurrentmonth() {
		Date nowTime = new Date();
		SimpleDateFormat matyear = new SimpleDateFormat("MM");
		return String.valueOf(matyear.format(nowTime));
	}
	/** get nowdatetime */
	public static String getCurrentDateTime() {
		Date nowTime = new Date();
		SimpleDateFormat matyear = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return String.valueOf(matyear.format(nowTime));
	}
	/** get nowdate */
	public static String getCurrentdate() {
		Date nowTime = new Date();
		SimpleDateFormat matyear = new SimpleDateFormat("yyyy-MM-dd");
		return String.valueOf(matyear.format(nowTime));
	}
	/** get nowdate */
	public static String getCurrentdate(String format) {
		Date nowTime = new Date();
		SimpleDateFormat matyear = new SimpleDateFormat(format);
		return String.valueOf(matyear.format(nowTime));
	}
	public static String[] getStringTokenizer(String src,String str){
		StringTokenizer stringTokenizer = new StringTokenizer(src,str);
		String[] array = new String[stringTokenizer.countTokens()];
			for(int i=0;stringTokenizer.hasMoreTokens();i++){
				array[i] = stringTokenizer.nextToken();
			}
		
		return array;
	}

	public static Object[] getLongTokenizer(String src,String str){
		StringTokenizer stringTokenizer = new StringTokenizer(src,str);
		Object[] array = new Object[stringTokenizer.countTokens()];
			for(int i=0;stringTokenizer.hasMoreTokens();i++){
				array[i] = new Long(stringTokenizer.nextToken());
			}
		
		return array;
	}
	
	public static Object[] getIntegerTokenizer(String src,String str){
		StringTokenizer stringTokenizer = new StringTokenizer(src,str);
		Object[] array = new Object[stringTokenizer.countTokens()];
			for(int i=0;stringTokenizer.hasMoreTokens();i++){
				array[i] = new Integer(stringTokenizer.nextToken());
			}
		
		return array;
	}
	public Float F(Object o) {
		NumberFormat formatter = new DecimalFormat("0.0");
		if (o == null) {
			return 0.0F;
		} else {
			Float f = Float.parseFloat(o.toString());
			String strf = formatter.format(f);
			return Float.parseFloat(strf);
		}
	}
	public static Object convertNull(Object value,Object returnValue) {
		if (value == null) {
			return returnValue;
		} else {
			return value;
		}
	}
	/**
	 * 根据传入ID创建新的序列，最大值+1，例：传入001001，返回001002
	 * @param id
	 * @param pid	上级ID，如新ID不存在使用上级ID进行累加001
	 * @return
	 */
	public static String createSequence(String maxId,String pid,int jumpLength)
	{
		String newId="";
		if(maxId==null)
		{
			if(pid.equals("0"))
			{
				for(int i=0;i<jumpLength-1;i++)
					newId += "0";
				newId += "1";
			}
			else
			{
				newId+=pid;
				for(int i=0;i<jumpLength-1;i++)
					newId += "0";
				newId += "1";
			}
		}
		else
		{
			BigDecimal bd = new BigDecimal(maxId);
			BigDecimal sum = bd.add(new BigDecimal("1"));
			newId = sum.toString();
			newId = maxId.substring(0,maxId.length()-newId.length())+newId;
		}
		return newId;
	}
	/**
	 * 处理将要创建的文件名称，从源值中替换掉文件系统不支持的字符
	 * 将“/\:*?"<>|”替换为“_”
	 * @param src
	 * @return
	 */
	public static String replaceCharForCreatFileName(String src)
	{
		String illegalCharacter[] = {"/","\\",":","*","?","\"","<",">","|"};
		String dest="";
		if(src!=null&&!"".equals(src))
		{
			for(int j=0;j<src.length();j++)
			{
				String tmp = src.substring(j,j+1);
				for(int i=0;i<illegalCharacter.length;i++)
				{
					if(tmp.equals(illegalCharacter[i]))
						tmp="_";
				}
				dest+=tmp;
			}
		}
		return dest;
	}
	public static void deleteFile(File file){
		   if(file.exists()){
		    if(file.isFile()){
		     file.delete();
		    }else if(file.isDirectory()){
		     File files[] = file.listFiles();
		     for(int i=0;i<files.length;i++){
		      Common.deleteFile(files[i]);
		     }
		    }
		    file.delete();
		   }else{
		    System.out.println("所删除的文件不存在！"+'\n');
		   }
		} 
	
	public static void main(String s[])
	{
//		Double d1=new Double("2");
//		Double d2=new Double("3");
//		Double d3=new Double("4");
//		Double d4=new Double("5");
//		Double d5=null;
//		System.out.println(Common.sum(d1,d2,d3,d4,d5));
		//d5=Commons.NullToZero(d5);
		//System.out.println(d5);
//		System.out.println(Common.createSequence("0000100002", "00001", 5));
//		Calendar d = Calendar.getInstance(); 
//		d.setTime(new Date("1988/2/7 00:00:00"));
//		d.add(Calendar.HOUR_OF_DAY, 16);
//		d.add(Calendar.MINUTE, 20);
//		System.out.println(d.getTime()+":"+Calendar.HOUR_OF_DAY);
//		System.out.println(Common.compareTo(new Date("1988/2/7 00:00:00"),new Date("1988/2/10 00:00:00"),16,5));
	}
	
	
	
	public Statement getJDBC() throws Exception{
		  Connection conn = null ;
		  Statement stmt = null;
		  try {
			  Class.forName("oracle.jdbc.driver.OracleDriver");
			  conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.104:1521:orcl","aktmxz", "aktmxz");
			  stmt= conn.createStatement(); 
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stmt;
	}
	
	public ResultSet getResult(String sql) throws Exception{
		  Connection conn = null ;
		  Statement stmt = null;
		  ResultSet rs = null;
		  try {
			  Class.forName("oracle.jdbc.driver.OracleDriver");
			  conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.101:1521:orcl","aktmxz", "aktmxz");
			  stmt= conn.createStatement(); 
			  rs = stmt.executeQuery(sql);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
}
