package com.xunj.tag;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.beanutils.PropertyUtils;

public class DataOutTag extends TagSupport
{
  private static final long serialVersionUID = -6544114758086098486L;
  DataListTag parentObj;
  private String fieldName;
  private String length;
  private String dataValue;
  private String function;
  private String patten;
  private String useTitle;

  public String getLength()
  {
    return this.length;
  }

  public void setLength(String length) {
    this.length = length;
  }

  public String getFieldName() {
    return this.fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName; }

  public String getPatten() {
    return this.patten;
  }

  public void setPatten(String patten) {
    this.patten = patten;
  }

  public String getDataValue() {
    return this.dataValue;
  }

  public void setDataValue(String dataValue) {
    this.dataValue = dataValue;
  }

  public String getUseTitle() {
    return this.useTitle;
  }

  public void setUseTitle(String useTitle) {
    this.useTitle = useTitle;
  }

  public String getFunction() {
    return this.function;
  }

  public void setFunction(String function) {
    this.function = function;
  }

  public int doEndTag()
    throws JspException
  {
    return super.doEndTag();
  }

  public int doStartTag() throws JspException
  {
    String value = "";
    if ((this.dataValue == null) && (this.function == null))
    {
      Tag parent = getParent();

      this.parentObj = ((DataListTag)parent);

      if (!(this.parentObj.getPerimission())) {
        return 0;
      }

      Object getvalues = null;
      try {
        getvalues = PropertyUtils.getProperty(this.parentObj.getObj(), this.fieldName);
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      }
      if (getvalues != null)
        value = String.valueOf(getvalues);
      if ((this.length != null) && (value.length() > Integer.parseInt(this.length))) {
        value = subString(value, Integer.parseInt(this.length));
      }

    }
    else if (this.dataValue != null)
    {
      value = this.dataValue;
      if ((this.length != null) && (this.dataValue.length() > Integer.parseInt(this.length))) {
        value = subString(value, Integer.parseInt(this.length));
      }

    }
    else if (this.function.equals("date"))
    {
      SimpleDateFormat df = null;
      if (this.patten == null)
        df = new SimpleDateFormat("yyyy-MM-dd");
      else
        df = new SimpleDateFormat(this.patten);
      value = df.format(new Date());
    }

    try
    {
      String html = "";
      if (this.useTitle == null)
      {
        html = "<div title='" + this.dataValue + "'>";
        html = html + value;
        html = html + "</div>";
      }
      else {
        html = value; }
      this.pageContext.getOut().write(html);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 1;
  }

  protected String renderList() throws JspException {
    StringBuffer results = new StringBuffer();

    return results.toString();
  }

  private String subString(String s, int length) {
    String returnStr = "";
    int strLength = s.getBytes().length;
    double len = 0.0D;
    if (strLength > length)
    {
      for (int m = 0; m < s.length(); ++m)
      {
        String t = s.substring(m, m + 1);
        byte[] b = t.getBytes();
        if (len < length)
          returnStr = returnStr + t;
        if (b.length == 2)
          len += 1.0D;
        else
          len += 0.5D;
      }
    }
    if (s.length() > returnStr.length())
      returnStr = returnStr + "...";
    return returnStr;
  }
}