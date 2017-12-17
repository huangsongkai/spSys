package com.xunj.action.common;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;

public class FileDownloadAction implements Action {
	// 该属性是依赖注入的属性，该属性可以在配置文件中动态指定该属性值
	private String inputPath;
	private String filename;

	// 处理用户请求的execute方法，该方法返回success字符串
	public String execute() throws Exception {
		ServletContext ctx = ServletActionContext.getServletContext();
		File file = new File(ctx.getRealPath(inputPath));
	
		System.out.println("文件地址"+file.getPath());  
		if(file.exists())
			return SUCCESS;
		else
		{
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setAttribute("msgType", "4");
			request.setAttribute("msg", "文件不存在！");
			return "message";
		}
	}

	public String getFilename() {
		try {
			filename = new String(filename.getBytes(), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return filename;
	}

	public String getInputPath() {
		return inputPath;
	}

	/*
	 * 下载用的Action应该返回一个InputStream实例， 该方法对应在result里的inputName属性值为targetFile
	 */
	public InputStream getTargetFile() throws Exception {
		return ServletActionContext.getServletContext().getResourceAsStream(
				inputPath);
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	// 依赖注入该属性值的setter方法
	public void setInputPath(String value) {
		inputPath = value;
	}

}
