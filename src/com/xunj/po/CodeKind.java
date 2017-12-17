package com.xunj.po;

import java.util.HashSet;
import java.util.Set;

import com.xunj.core.CorePo;

/**
 * �ֵ��������Ϣ��
 */
public class CodeKind extends CorePo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5479051576555271060L;

	// Fields
	/**
	 * �ֵ������ID
	 */
	private String kindId;
	/**
	 * �ֵ����������
	 */
	private String kindName;
	/**
	 * �ֵ���������չ����
	 */
	private Long levelTal;
	/**
	 * �ֵ���������ͣ�ϵͳ�����
	 */
	private String codeType;
	/**
	 * �ֵ�����౸ע
	 */
	private String remark;
	/**
	 * �ֵ�������ֹ�����
	 */
	private String kindCode;
	/**
	 * �ֵ������״̬��ɾ�������
	 */
	private String state;
	/**
	 * �ӱ�
	 */
	private Set codeDicts = new HashSet(0);

	// Constructors

	/** default constructor */
	public CodeKind() {
	}

	/** minimal constructor */
	public CodeKind(String kindId, String kindName, Long levelTal) {
		this.kindId = kindId;
		this.kindName = kindName;
		this.levelTal = levelTal;
	}

	/** full constructor */
	public CodeKind(String kindId, String kindName, Long levelTal,
			String codeType, String remark, String kindCode,
			String state, Set codeDicts) {
		this.kindId = kindId;
		this.kindName = kindName;
		this.levelTal = levelTal;
		this.codeType = codeType;
		this.remark = remark;
		this.kindCode = kindCode;
		this.state = state;
		this.codeDicts = codeDicts;
	}

	// Property accessors

	public String getKindId() {
		return this.kindId;
	}

	public void setKindId(String kindId) {
		this.kindId = kindId;
	}

	public String getKindName() {
		return this.kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	public Long getLevelTal() {
		return this.levelTal;
	}

	public void setLevelTal(Long levelTal) {
		this.levelTal = levelTal;
	}

	public String getCodeType() {
		return this.codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set getCodeDicts() {
		return this.codeDicts;
	}

	public void setCodeDicts(Set codeDicts) {
		this.codeDicts = codeDicts;
	}

	public String getKindCode() {
		return kindCode;
	}

	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}