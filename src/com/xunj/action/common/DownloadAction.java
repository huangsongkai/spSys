package com.xunj.action.common;

import java.io.InputStream;

import org.apache.struts2.ServletActionContext;

public class DownloadAction {

	private String fileName;

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileName() {
		return fileName;
	}



    

	//下载的流  
	public InputStream getInputStream() {
		 String name=this.getDownloadFileName();
		//  String realPath=ServletActionContext.getServletContext().getRealPath("/uploadImages")+ "/"+name; 路径错误
		   String realPath=""+name;        
		   InputStream in=ServletActionContext.getServletContext().getResourceAsStream(realPath);
		   if(null==in){
		    System.out.println("Can not find a java.io.InputStream with the name [inputStream] in the invocation stack. Check the <param name=\"inputName\"> tag specified for this action.检查action中文件下载路径是否正确.");  
		   }
		   return ServletActionContext.getServletContext().getResourceAsStream(realPath);      
	}
	
	
	/** 文件名 转换编码 防止中文乱码*/
	public String getDownloadFileName() {
	   String fileName=ServletActionContext.getRequest().getParameter("fileName");
	   String downFileName = fileName;    
	   try {
	    downFileName = new String(downFileName.getBytes(), "ISO8859-1");
	   } catch (Exception e) {
	    e.printStackTrace();
	   }
	   return downFileName;
	}

	public String execute() {      
		return "success";
	}

}