package com.xunj.core;

import java.io.Serializable;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.xunj.po.SysOperationRole;
import com.xunj.po.SysUserRole;

public class SessionBean
  implements Serializable
{
  private static final long serialVersionUID = 3136687902721712440L;
  private String userId;
  private String userName;
  private String gender;
  private String deptId;
  private String deptName;
  private String password;
  private Long userOrder;
  private String loginRole;//登录角色
  private String state;
  public static SessionBean getSessionBean(HttpServletRequest request)
  {
    SessionBean sessionBean = (SessionBean)request.getSession().getAttribute("sessionbean");
    return sessionBean; }

  public String getUserId() {
    return this.userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return this.userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getGender() {
    return this.gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getDeptId() {
    return this.deptId;
  }

  public void setDeptId(String deptId) {
    this.deptId = deptId;
  }

  public Long getUserOrder() {
    return this.userOrder;
  }

  public void setUserOrder(Long userOrder) {
    this.userOrder = userOrder;
  }

  public String getState()
  {
    return this.state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getDeptName() {
    return this.deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

public String getLoginRole() {
	return loginRole;
}

public void setLoginRole(String loginRole) {
	this.loginRole = loginRole;
}
  
}