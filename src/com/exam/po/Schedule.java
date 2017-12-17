package com.exam.po;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.xunj.core.CorePo;

/**
 * TrainInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Schedule extends CorePo implements java.io.Serializable {

	// Fields

	private Integer pkSchedule;
	
	private Integer pkTrain;
	private Integer scheduleNo;
	
	private String ement1;
	private String ement2;
	private String ement3;
	private String ement4;
	private String ement7;
	private String ement8;
	private String ement9;
	private String ement10;
	private String ement13;
	private String ement14;
	private String ement15;
	private String ement16;
	private String ement1a;
	private String ement2a;
	private String ement3a;
	private String ement4a;
	// Constructors

	/** default constructor */
	public Schedule() {
	}

	/** full constructor */
	public Schedule( Integer pkTrain,Integer scheduleNo,String ement1,String ement2,String ement3,String ement4,
			String ement1a,String ement2a,String ement3a,String ement4a,
			String ement7,String ement8,String ement9,String ement10,String ement13,
			String ement14,String ement15,String ement16
			) {
		this.pkTrain=pkTrain;
		this.scheduleNo=scheduleNo;
		this.ement1 = ement1;
		this.ement2 = ement2;
		this.ement3 = ement3;
		this.ement4 = ement4;
		
		this.ement1a = ement1a;
		this.ement2a = ement2a;
		this.ement3a = ement3a;
		this.ement4a = ement4a;
		
		this.ement7 = ement7;
		this.ement8 = ement8;
		this.ement9 = ement9;
		this.ement10 = ement10;
		
		this.ement13 = ement13;
		this.ement14 = ement14;
		this.ement15 = ement15;
		this.ement16 = ement16;
	}

	// Property accessors



	public Integer getPkSchedule() {
		return pkSchedule;
	}

	public void setPkSchedule(Integer pkSchedule) {
		this.pkSchedule = pkSchedule;
	}

	public String getEment1() {
		return ement1;
	}

	public void setEment1(String ement1) {
		this.ement1 = ement1;
	}

	public String getEment2() {
		return ement2;
	}

	public void setEment2(String ement2) {
		this.ement2 = ement2;
	}

	public String getEment3() {
		return ement3;
	}

	public void setEment3(String ement3) {
		this.ement3 = ement3;
	}

	public String getEment4() {
		return ement4;
	}

	public void setEment4(String ement4) {
		this.ement4 = ement4;
	}

	public String getEment7() {
		return ement7;
	}

	public void setEment7(String ement7) {
		this.ement7 = ement7;
	}

	public String getEment8() {
		return ement8;
	}

	public void setEment8(String ement8) {
		this.ement8 = ement8;
	}

	public String getEment9() {
		return ement9;
	}

	public void setEment9(String ement9) {
		this.ement9 = ement9;
	}

	public String getEment10() {
		return ement10;
	}

	public void setEment10(String ement10) {
		this.ement10 = ement10;
	}

	public String getEment13() {
		return ement13;
	}

	public void setEment13(String ement13) {
		this.ement13 = ement13;
	}

	public String getEment14() {
		return ement14;
	}

	public void setEment14(String ement14) {
		this.ement14 = ement14;
	}

	public String getEment15() {
		return ement15;
	}

	public void setEment15(String ement15) {
		this.ement15 = ement15;
	}

	public String getEment16() {
		return ement16;
	}

	public void setEment16(String ement16) {
		this.ement16 = ement16;
	}

	public String getEment1a() {
		return ement1a;
	}

	public void setEment1a(String ement1a) {
		this.ement1a = ement1a;
	}

	public String getEment2a() {
		return ement2a;
	}

	public void setEment2a(String ement2a) {
		this.ement2a = ement2a;
	}

	public String getEment3a() {
		return ement3a;
	}

	public void setEment3a(String ement3a) {
		this.ement3a = ement3a;
	}

	public String getEment4a() {
		return ement4a;
	}

	public void setEment4a(String ement4a) {
		this.ement4a = ement4a;
	}

	public Integer getPkTrain() {
		return pkTrain;
	}

	public void setPkTrain(Integer pkTrain) {
		this.pkTrain = pkTrain;
	}

	public Integer getScheduleNo() {
		return scheduleNo;
	}

	public void setScheduleNo(Integer scheduleNo) {
		this.scheduleNo = scheduleNo;
	}

	
	
}