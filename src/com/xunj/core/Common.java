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
	 * Double��ֵ�Ӻ�
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
	 * ������
	 * 
	 * @param corporationBirthday
	 * @return
	 */
	public static String getYearToString() {

		// ������ڵı���
		String dateStr = "";
		// ��ȡ�ַ��͵�ǰ����
		String time = getDateTimeFormat(new Date(), "yyyy-MM-dd");
		// ����ַ������ڲ�Ϊ��,����ʱ��ת��
		if (StringUtils.isNotBlank(time)) {
			// �ַ���ת��������
			Date corporationBirthday = newDate(time, "yyyy-MM-dd");
			// Calendarʵ������ת��
			Calendar cal = Calendar.getInstance();
			// ������������ڲ�Ϊ��,�������
			if (corporationBirthday != null) {
				// cal��ȡ����
				cal.setTime(corporationBirthday);
				// ��ȡ���͵���
				int year = cal.get(Calendar.YEAR);
				// ���
				dateStr = year + "";
			}
		}
		// ��������
		return dateStr;

	}
	
	/**
	 * ���ڴ�����
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
	 * ���ڴ�����
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
	 * ���ݴ���ID�����µ����У����ֵ+1����������001001������001002
	 * @param id
	 * @param pid	�ϼ�ID������ID������ʹ���ϼ�ID�����ۼ�001
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
	
	
	/**����תƴ��
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
		// ���ú���ƴ������ĸ�ʽ
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		String t4 = "";
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// �ж��Ƿ�Ϊ�����ַ�
				// System.out.println(t1[i]);
				if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// �����ֵļ���ȫƴ���浽t2������
					t4 += t2[0];// ȡ���ú���ȫƴ�ĵ�һ�ֶ��������ӵ��ַ���t4��
				} else {
					t4 += Character.toString(t1[i]);
				}
			}
		}catch (Exception e) {
		}
		return t4;
	}

	/**����תƴ������ĸ
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
			//��ȡ���ֵ�����ĸ
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
