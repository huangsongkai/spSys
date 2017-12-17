package com.xunj.core;

import com.xunj.po.SysLoginfo;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

public class TableLog
{
  private static String[] exceptString = { "class", "value", "servletWrapper", "servlet", "multipartRequestHandler" };
  public static String[][] logbean = { 
    { "com.xunj.po.CodeDict", "字典码明细" }, 
    { "com.xunj.po.CodeKind", "字典码大类" }, 
    { "com.xunj.po.Dept", "部门表" }, 
    { "com.xunj.po.SysFuncGroup", "权限组表" }, 
    { "com.xunj.po.SysFuncGroupList", "权限组权限列表" }, 
    { "com.xunj.po.SysFuncGroupUser", "用户权限组表" }, 
    { "com.xunj.po.SysFuncInfo", "功能权限表" }, 
    { "com.xunj.po.UploadFile", "附件表" }, 
    { "com.xunj.po.SysUserFuncInfo", "用户权限表" }, 
    { "com.xunj.po.SysUserList", "用户表" } };

  private String getTableText(String className)
  {
    String tabelText = "";

    for (int i = 0; i < logbean.length; ++i)
    {
      if (!(logbean[i][0].equals(className)))
        continue;
      tabelText = logbean[i][1];
      break;
    }

    if (tabelText.equals("")) {
      tabelText = className;
    }
    return tabelText;
  }

  public String getLogContent(String action, Object obj)
  {
    String log = "";
    if (obj != null)
    {
      if ((obj.getClass().getName().equals("java.util.ArrayList")) || (obj.getClass().getName().equals("java.util.List")))
      {
        List list = (List)obj;
        for (int i = 0; i < list.size(); ++i)
        {
          log = log + getLogContent(action, list.get(i));
        }
      }
      else
      {
        log = log + "<table width='100%'><tr><th colspan='4'>" + action + "，对象:" + obj.getClass().getName() + "</th></tr>";

        PropertyDescriptor[] pdsrc = PropertyUtils.getPropertyDescriptors(obj);
        boolean test = true;
        for (int i = 0; i < pdsrc.length; ++i)
        {
          for (int es = 0; es < exceptString.length; ++es)
          {
            if (pdsrc[i].getName().equals(exceptString[es])) {
              test = false;
              break;
            }
            test = true;
          }

          if (!(test)) continue;
          try {
            if ((PropertyUtils.getProperty(obj, pdsrc[i].getName()) != null) && (StringUtils.isNotBlank(PropertyUtils.getProperty(obj, pdsrc[i].getName()).toString())))
              log = log + "<tr><td width='40'>字段:</td><td align='left' style='word-break:keep-all;white-space:nowrap;'>" + pdsrc[i].getName() + "</td><td width='40'>值:</td><td align='left'>" + PropertyUtils.getProperty(
                obj, pdsrc[i].getName()) + 
                "</td></tr>";
          }
          catch (IllegalAccessException e) {
            e.printStackTrace();
          } catch (InvocationTargetException e) {
            e.printStackTrace();
          } catch (NoSuchMethodException e) {
            e.printStackTrace();
          }
        }

        log = log + "</table>";
      }
    }
    if ((log.length() >= 1000) && (log.length() <= 2000)) {
      log = StringUtils.rightPad(log, 2100);
    }
    return log;
  }

  public SysLoginfo organizLog(Object obj, String action, String logText)
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    SysLoginfo vo = new SysLoginfo();
    String log = "";

    if (logText == null)
      log = getLogContent(action, obj);
    else
      log = logText;
    if (request != null)
    {
      new SessionBean(); SessionBean sessionbean = SessionBean.getSessionBean(request);
      if (sessionbean != null)
      {
        vo.setUserId(sessionbean.getUserId());
        vo.setUserName(sessionbean.getUserName());
      }
      vo.setIpAddress(request.getRemoteAddr());
    }
    else
    {
      vo.setUserId("");
      vo.setUserName("");
      vo.setIpAddress("");
    }
    vo.setHandleType(action);
    vo.setHandleCon(log);
    if (request != null)
      //vo.setRequestUrl(request.getRequestURI());
    vo.setState("A");
    vo.setHandleTime(new Date());   

    return vo;
  }

  public boolean getLogSwitch()
  {
    boolean logSwitch = true;
    ServletContext ctx = ServletActionContext.getServletContext();
    if (ctx != null)
    {
      HashMap map = (HashMap)ctx.getAttribute("sysConfig");
      String state = (String)map.get("LogSwitch");
      if ((state != null) && (state.equals("off")))
        logSwitch = false;
    }
    return logSwitch;
  }
}