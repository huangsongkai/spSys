package com.exam.po;

import java.util.Date;

import com.xunj.core.CorePo;

/**
 * UnitInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class BaseAttach extends CorePo implements java.io.Serializable {

	// Fields

	private Integer pkAttach;
	private Integer pkbaseApp;
	private String fileName;
	private String filePath;
	private String fileType;
	// Constructors
	/** default constructor */      
	public BaseAttach() {
	}
	/** full constructor */
	public Integer getPkAttach() {
		return pkAttach;
	}
	public void setPkAttach(Integer pkAttach) {
		this.pkAttach = pkAttach;
	}
	public Integer getPkbaseApp() {
		return pkbaseApp;
	}
	public void setPkbaseApp(Integer pkbaseApp) {
		this.pkbaseApp = pkbaseApp;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	

	
	// Property accessors
	
}