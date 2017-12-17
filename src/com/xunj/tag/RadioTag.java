package com.xunj.tag;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import org.apache.commons.beanutils.PropertyUtils;

public class RadioTag extends BaseHandlerTag
{
  private static final long serialVersionUID = -6207280791115238328L;
  protected String name;
  private String beanName;
  private String fieldName;
  private String refValueBean;
  private String dataType;
  private String role;
  private String or;
  private String order;
  private String orderSeq;
  protected String text = null;

  protected String value = null;

  public String getDataType()
  {
    return this.dataType;
  }

  public void setDataType(String dataType) {
    this.dataType = dataType;
  }

  public String getRole() {
    return this.role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getOr() {
    return this.or;
  }

  public void setOr(String or) {
    this.or = or;
  }

  public String getOrder() {
    return this.order;
  }

  public void setOrder(String order) {
    this.order = order;
  }

  public String getOrderSeq() {
    return this.orderSeq;
  }

  public void setOrderSeq(String orderSeq) {
    this.orderSeq = orderSeq;
  }

  public String getFieldName() {
    return this.fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public String getRefValueBean() {
    return this.refValueBean;
  }

  public void setRefValueBean(String refValueBean) {
    this.refValueBean = refValueBean;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue()
  {
    return this.value;
  }

  public void setValue(String value)
  {
    this.value = value;
  }

  public int doStartTag()
    throws JspException
  {
    try
    {
      String radioTag = renderRadioElement(serverValue(), currentValue());
      this.pageContext.getOut().write(radioTag);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      throw new JspException("no getter mehtod for property " + getName());
    }

    this.text = null;
    return 1;
  }

  private String serverValue() throws JspException
  {
    return this.value;
  }

  private String currentValue()
    throws JspException, NoSuchMethodException
  {
    String val = null;
    Object o = this.pageContext.getRequest().getAttribute(this.refValueBean);
    if (o != null) {
      try
      {
        Object getvalues = PropertyUtils.getProperty(o, getName());
        if (getvalues != null)
          val = String.valueOf(getvalues);
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
      catch (ClassCastException e)
      {
        throw new JspException("radiobutton '" + getName() + "' must have a String value ");
      }
    }
    return val;
  }

  protected String renderRadioElement(String serverValue, String checkedValue)
    throws JspException
  {
    StringBuffer results = new StringBuffer("<input type=\"radio\"");
    results.append(" name=\"");

    results.append(this.name);
    results.append("\"");
    if (this.accesskey != null) {
      results.append(" accesskey=\"");
      results.append(this.accesskey);
      results.append("\"");
    }
    if (this.tabindex != null) {
      results.append(" tabindex=\"");
      results.append(this.tabindex);
      results.append("\"");
    }
    results.append(" value=\"");
    results.append(serverValue);
    results.append("\"");

    if (this.beanName != null)
    {
      results.append(" beanName=\"");
      results.append(this.beanName);
      results.append("\"");
    }
    if (this.fieldName != null)
    {
      results.append(" fieldName=\"");
      results.append(this.fieldName);
      results.append("\"");
    }
    if (this.dataType != null)
    {
      results.append(" dataType=\"");
      results.append(this.dataType);
      results.append("\"");
    }
    if (this.role != null)
    {
      results.append(" role=\"");
      results.append(this.role);
      results.append("\"");
    }
    if (this.or != null)
    {
      results.append(" or=\"");
      results.append(this.or);
      results.append("\"");
    }
    if (this.order != null)
    {
      results.append(" order=\"");
      results.append(this.order);
      results.append("\"");
    }
    if (this.orderSeq != null)
    {
      results.append(" orderSeq=\"");
      results.append(this.orderSeq);
      results.append("\"");
    }

    if (serverValue.equals(checkedValue)) {
      results.append(" checked=\"checked\"");
    }
    results.append(prepareEventHandlers());
    results.append(prepareStyles());
    results.append(getElementClose());
    return results.toString();
  }

  public int doAfterBody()
    throws JspException
  {
    if (this.bodyContent != null) {
      String value = this.bodyContent.getString().trim();
      if (value.length() > 0) {
        this.text = value;
      }
    }

    return 0;
  }

  public int doEndTag()
    throws JspException
  {
    if (this.text != null) {
      try {
        this.pageContext.getOut().write(this.text);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return 6;
  }

  public void release()
  {
    super.release();
    this.name = null;
    this.text = null;
    this.value = null;
  }

  public String getBeanName()
  {
    return this.beanName;
  }

  public void setBeanName(String beanName) {
    this.beanName = beanName;
  }
}