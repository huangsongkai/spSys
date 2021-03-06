package com.xunj.po;

import com.xunj.core.CorePo;

/**
 * CodeDict generated by MyEclipse - Hibernate Tools
 */

public class CodeDict extends CorePo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3181150655126375035L;
	/**
	 * �ֵ���ID
	 */
	private String codeId;
	/**
	 * �ֵ������ID
	 */
	private String kindId;
	/**
	 * �ֵ������
	 */
	private CodeKind codeKind;
	/**
	 * �ֵ�������
	 */
	private String codeName;
	/**
	 * �ֵ��븸ID
	 */
	private String parentCodeId;
	/**
	 * �Ƿ�Ϊϵͳ����
	 */
	private String isDefult;
	/**
	 * ������
	 */
	private String spMark;
	/**
	 * �ֵ���״̬
	 */
	private String state;
	/**
	 * �ֵ��뱸ע
	 */
	private String codeRemark;
	/**
	 * �ֵ����ֹ�����
	 */
	private String codeCode;
	/**
	 * �ֵ����ֹ�����
	 */
	private String dictRemark;

	// Constructors

	private Integer assessTimes;

	private Integer questionTimes;
	
	public Integer getAssessTimes() {
		return assessTimes;
	}

	public void setAssessTimes(Integer assessTimes) {
		this.assessTimes = assessTimes;
	}

	public String getCodeId() {
		return this.codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public CodeKind getCodeKind() {
		return this.codeKind;
	}

	public void setCodeKind(CodeKind codeKind) {
		this.codeKind = codeKind;
	}

	public String getCodeName() {
		return this.codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getParentCodeId() {
		return this.parentCodeId;
	}

	public void setParentCodeId(String parentCodeId) {
		this.parentCodeId = parentCodeId;
	}

	public String getIsDefult() {
		return this.isDefult;
	}

	public void setIsDefult(String isDefult) {
		this.isDefult = isDefult;
	}

	public String getSpMark() {
		return this.spMark;
	}

	public void setSpMark(String spMark) {
		this.spMark = spMark;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCodeRemark() {
		return codeRemark;
	}

	public void setCodeRemark(String codeRemark) {
		this.codeRemark = codeRemark;
	}

	public String getCodeCode() {
		return codeCode;
	}

	public void setCodeCode(String codeCode) {
		this.codeCode = codeCode;
	}

	public String getDictRemark() {
		return dictRemark;
	}

	public void setDictRemark(String dictRemark) {
		this.dictRemark = dictRemark;
	}

	public String getKindId() {
		return kindId;
	}

	public void setKindId(String kindId) {
		this.kindId = kindId;
	}

	public Integer getQuestionTimes() {
		return questionTimes;
	}

	public void setQuestionTimes(Integer questionTimes) {
		this.questionTimes = questionTimes;
	}
}