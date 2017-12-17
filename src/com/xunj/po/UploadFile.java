package com.xunj.po;

import java.util.Date;

import com.xunj.core.CorePo;

/**
 * LcPublicEnclosure entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UploadFile extends CorePo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4349592935100549289L;
	private String fileId;
	private String fileName;
	private String putPath;
	private String uploadLocation;
	private Date enterTime;
	private Long fileSize;
	private String extensionName;
	private String belongTable;
	private String belongId;
	private String remark;

	public String getUploadLocation() {
		return uploadLocation;
	}

	public void setUploadLocation(String uploadLocation) {
		this.uploadLocation = uploadLocation;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPutPath() {
		return this.putPath;
	}

	public void setPutPath(String putPath) {
		this.putPath = putPath;
	}

	public Date getEnterTime() {
		return this.enterTime;
	}

	public void setEnterTime(Date enterTime) {
		this.enterTime = enterTime;
	}

	public String getBelongTable() {
		return this.belongTable;
	}

	public void setBelongTable(String belongTable) {
		this.belongTable = belongTable;
	}

	public String getBelongId() {
		return this.belongId;
	}

	public void setBelongId(String belongId) {
		this.belongId = belongId;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getExtensionName() {
		return extensionName;
	}

	public void setExtensionName(String extensionName) {
		this.extensionName = extensionName;
	}

}