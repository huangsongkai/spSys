package com.xunj.core;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Common {

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
	
	
	/**汉字转拼音
	 * @param src
	 * @return
	 * @throws Exception
	 */
	public static String getPinYin(String src) throws Exception {
		char[] t1 = null;
		t1 = src.toCharArray();
		// System.out.println(t1.length);
		String[] t2 = new String[t1.length];
		// System.out.println(t2.length);
		// 设置汉字拼音输出的格式
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		String t4 = "";
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断是否为汉字字符
				// System.out.println(t1[i]);
				if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// 将汉字的几种全拼都存到t2数组中
					t4 += t2[0];// 取出该汉字全拼的第一种读音并连接到字符串t4后
				} else {
					t4 += Character.toString(t1[i]);
				}
			}
		}catch (Exception e) {
		}
		return t4;
	}

	/**汉字转拼音首子母
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String getPinYinHeadChar(String str) throws Exception {
		String convert = "";
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			//提取汉字的首字母
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word, t3);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}
		return convert;
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
		System.out.println(Common.createSequence("0000100002", "00001", 5));
	}
	
	/**
	 * 
	 * @return
	 */
	public static Date getCurrentDateFromZeroAM(){
		Calendar c=Calendar.getInstance();
		c.set(Calendar.HOUR,0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}
}
