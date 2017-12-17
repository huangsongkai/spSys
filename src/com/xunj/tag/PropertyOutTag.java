package com.xunj.tag;

import com.xunj.util.Common;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.commons.beanutils.PropertyUtils;

public class PropertyOutTag extends BodyTagSupport
{
  private static final long serialVersionUID = -3959625271381871390L;
  private Object valueBean;
  private String propertyName;
  private String dataType;

  public void release()
  {
    super.release();
  }

  public String getPropertyName()
  {
    return this.propertyName;
  }

  public void setPropertyName(String propertyName) {
    this.propertyName = propertyName; }

  public Object getValueBean() {
    return this.valueBean;
  }

  public void setValueBean(Object valueBean) {
    this.valueBean = valueBean; }

  public int doStartTag() throws JspException {
    String values = "";
    try {
      try {
        Object getvalues = PropertyUtils.getProperty(this.valueBean, this.propertyName);
        if (getvalues != null)
          values = formateType(getvalues);
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      }

      this.pageContext.getOut().write(values);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return 1;
  }

  private String formateType(Object value)
  {
    if (value != null)
    {
      if (this.dataType != null)
      {
        if (this.dataType.equals("dateTime"))
          return Common.getDateTimeFormat((Date)value, "yyyy-MM-dd HH:mm:ss");
        if (this.dataType.equals("date"))
          return Common.getDateTimeFormat((Date)value, "yyyy-MM-dd");
      }
      else {
        return String.valueOf(value); }
    }
    return null;
  }

  public String getDataType() {
    return this.dataType;
  }

  public void setDataType(String dataType) {
    this.dataType = dataType;
  }
}