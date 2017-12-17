package com.exam.po;

import com.xunj.core.CorePo;

/**
 * CheakTaskScore entity. @author MyEclipse Persistence Tools
 */

public class CheakTaskScore extends CorePo implements java.io.Serializable {

	// Fields

	private Integer scoreId;
	private String scoreRule;
	private Integer scoreNumber;
	private String scoreExplain;
	private Integer taskId;
	private Integer ruleNo;
	private String checkTroubler;
	
	private Long repeatProblem;
	// Constructors

	

	public String getCheckTroubler() {
		return checkTroubler;
	}

	public void setCheckTroubler(String checkTroubler) {
		this.checkTroubler = checkTroubler;
	}

	/** default constructor */
	public CheakTaskScore() {
	}

	/** minimal constructor */
	public CheakTaskScore(Integer scoreId) {
		this.scoreId = scoreId;
	}

	/** full constructor */
	public CheakTaskScore(Integer scoreId, String scoreRule,
			Integer scoreNumber, String scoreExplain, Integer taskId) {
		this.scoreId = scoreId;
		this.scoreRule = scoreRule;
		this.scoreNumber = scoreNumber;
		this.scoreExplain = scoreExplain;
		this.taskId = taskId;
	}

	// Property accessors

	public Integer getScoreId() {
		return this.scoreId;
	}

	public void setScoreId(Integer scoreId) {
		this.scoreId = scoreId;
	}

	public String getScoreRule() {
		return this.scoreRule;
	}

	public void setScoreRule(String scoreRule) {
		this.scoreRule = scoreRule;
	}

	public Integer getScoreNumber() {
		return this.scoreNumber;
	}

	public void setScoreNumber(Integer scoreNumber) {
		this.scoreNumber = scoreNumber;
	}

	public String getScoreExplain() {
		return this.scoreExplain;
	}

	public void setScoreExplain(String scoreExplain) {
		this.scoreExplain = scoreExplain;
	}

	public Integer getTaskId() {
		return this.taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Integer getRuleNo() {
		return ruleNo;
	}

	public void setRuleNo(Integer ruleNo) {
		this.ruleNo = ruleNo;
	}

	public Long getRepeatProblem() {
		return repeatProblem;
	}

	public void setRepeatProblem(Long repeatProblem) {
		this.repeatProblem = repeatProblem;
	}

}