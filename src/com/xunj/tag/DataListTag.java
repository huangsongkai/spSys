package com.xunj.tag;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.commons.beanutils.PropertyUtils;

public class DataListTag extends BodyTagSupport
{
  private static final long serialVersionUID = -9179602294981876808L;
  private List<?> dataList;
  int m = 0;
  int cnt = 0;
  private Object obj;
  private String filterName;
  private String filterValue;
  private String regex;
  private String outRowCount;
  private int allowRowCount;
  private String html = "";

  private boolean subTagExecuted = false;

  public String getFilterName()
  {
    return this.filterName;
  }

  public void setFilterName(String filterName) {
    this.filterName = filterName;
  }

  public String getRegex() {
    return this.regex;
  }

  public void setRegex(String regex) {
    this.regex = regex;
  }

  public String getFilterValue() {
    return this.filterValue;
  }

  public void setFilterValue(String filterValue) {
    this.filterValue = filterValue;
  }

  public List<?> getDataList()
  {
    return this.dataList;
  }

  public void setDataList(List<?> dataList) {
    this.dataList = dataList;
  }

  public Object getObj() {
    return this.obj; }

  public void setObj(Object obj) {
    this.obj = obj; }

  public String getOutRowCount() {
    return this.outRowCount;
  }

  public void setOutRowCount(String outRowCount) {
    this.outRowCount = outRowCount;
  }

  public void release()
  {
    super.release();
  }

  public synchronized boolean getPerimission()
  {
    return this.subTagExecuted;
  }

  public synchronized void subTagSuccessed()
  {
  }

  public int doStartTag()
    throws JspException
  {
    this.m = 0;
    this.cnt = 0;
    this.html = "";
    if (this.dataList != null)
    {
      if (this.outRowCount != null)
        this.allowRowCount = Integer.parseInt(this.outRowCount);
      else
        this.allowRowCount = this.dataList.size();
      listItem();
    }
    return 2;
  }

  public int doAfterBody() throws JspException
  {
    if (!(this.subTagExecuted)) {
      try
      {
        getBodyContent().clearBuffer();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    this.html += getBodyContent().getString();
    int val = listItem();
    try {
      if (getBodyContent() != null)
        getBodyContent().clear();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return val; }

  public int doEndTag() throws JspException {
    try {
      this.pageContext.getOut().write(this.html);
      getBodyContent().clearBuffer();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 6;
  }

  private int listItem()
  {
    if (this.dataList == null)
    {
      return 0;
    }
    this.subTagExecuted = false;
    if ((this.m >= this.dataList.size()) || (this.cnt == this.allowRowCount)) {
      return 0;
    }

    this.obj = this.dataList.get(this.m);
    this.pageContext.setAttribute("dataObj", this.obj);
    this.pageContext.setAttribute("indx", Integer.valueOf(this.cnt));
    this.m += 1;
    if ((this.filterName != null) && (this.filterValue != null))
    {
      String[] fName = { this.filterName };
      String[] fValue = { this.filterValue };
      if (this.regex != null)
      {
        fName = this.filterName.split(this.regex);
        fValue = this.filterValue.split(this.regex);
      }
      boolean test = true;
      for (int i = 0; i < fName.length; ++i)
      {
        Object getvalues = null;
        try {
          getvalues = PropertyUtils.getProperty(this.obj, fName[i]);
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          e.printStackTrace();
        } catch (NoSuchMethodException e) {
          e.printStackTrace();
        }
        if ((getvalues != null) && (fValue[i].equals(String.valueOf(getvalues))))
        {
          test = true;
        }
        else
        {
          test = false;
          break;
        }
      }
      if (test)
      {
        this.subTagExecuted = true;
        this.cnt += 1;
      }
      else
      {
        this.subTagExecuted = false;
      }
    }
    else
    {
      this.subTagExecuted = true;
      this.cnt += 1;
    }
    return 2;
  }
}