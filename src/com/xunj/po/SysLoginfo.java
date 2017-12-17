package com.xunj.po;

import java.util.Date;

import com.xunj.core.CorePo;

/**
 * ��־��
 */

public class SysLoginfo extends CorePo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1144018108683654079L;

	// Fields
	/**
	 * ��־ID
	 */
	private String logId;
	/**
	 * �û�ID
	 */
	private String userId;
	/**
	 * �û�����
	 */
	private String userName;
	/**
	 * ip��ַ
	 */
	private String ipAddress;
	/**
	 * ��������
	 */
	private String handleType;
	/**
	 * ��������
	 */
	private Date handleTime;
	/**
	 * ��������
	 */
	private String handleCon;
	/**
	 * ״̬
	 */
	private String state;



	public String getLogId() {
		return this.logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getHandleType() {
		return this.handleType;
	}

	public void setHandleType(String handleType) {
		this.handleType = handleType;
	}

	public Date getHandleTime() {
		return this.handleTime;
	}

	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}

	public String getHandleCon() {
		return this.handleCon;
	}

	public void setHandleCon(String handleCon) {
		this.handleCon = handleCon;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}
}