package com.xunj.util;


import java.io.*;
import java.net.*;
/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Tohtml {
	public static void jspToHtml(String jsp_url,String html_filepath)//第一个参数是要转换文件的url第二个参数是要转换成的文件名和绝对路径
	throws Exception
	{
		 URL stdURL = null;
		 BufferedReader stdIn = null;
		 PrintWriter stdOut = null;
		 try {
		   stdURL = new URL(jsp_url);
		 }
		 catch (MalformedURLException e) {
		   throw e;
		 }

		 try {
		   stdIn = new BufferedReader(new InputStreamReader(stdURL.openStream()));
		   stdOut = new PrintWriter(new BufferedWriter(new FileWriter(html_filepath)));
		 }
		 catch (IOException e) {
		 }

		 /***把URL指定的页面以流的形式读出，写成指定的文件***/
		 try {
		   String strHtml = "";
		   while((strHtml = stdIn.readLine())!=null) {
		     stdOut.println(strHtml);
		   }
		 }
		 catch (IOException e) {
		   throw e;
		 }
		 finally {
		   try {
		     if(stdIn != null)
		       stdIn.close();
		     if(stdOut != null)
		       stdOut.close();
		   }
		   catch (Exception e) {
		     System.out.println(e);
		   }
		 }

	}
//	public void jsp_to_html(String jsp_url,String html_filepath)
//	throws Exception
//	{
//		FileOutputStream fileout = new FileOutputStream(html_filepath);
//		URL uu = new URL(jsp_url);
//		InputStream ii = uu.openStream();
//		int i1 = 0;
//		while (i1 != -1 )
//		{
//			int i2 = ii.available();
//			if (i2==0) i2 = i2 + 2;
//			byte c[] = new byte[i2];
//			i1 = ii.read(c);
//			if (i1 != 0)	
//			fileout.write(c);
//		}
//		ii.close();
//		fileout.close();
//	}

}
