package com.xunj.po;

import java.util.Date;

import com.xunj.core.CorePo;

/**
 * LcPublicEnclosure entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UploadFileGroup extends CorePo implements java.io.Serializable {

	/**
	 * 
	 */
	private Integer fileGroupId;
	private String fileId;
	private Integer certTaskId;
	private String fileType;
	
	
	

	public Integer getFileGroupId() {
		return fileGroupId;
	}

	public void setFileGroupId(Integer fileGroupId) {
		this.fileGroupId = fileGroupId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public Integer getCertTaskId() {
		return certTaskId;
	}

	public void setCertTaskId(Integer certTaskId) {
		this.certTaskId = certTaskId;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	
}