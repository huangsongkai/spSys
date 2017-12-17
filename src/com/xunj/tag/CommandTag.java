package com.xunj.tag;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import org.apache.commons.beanutils.PropertyUtils;

public class CommandTag extends BaseHandlerTag
{
  private static final long serialVersionUID = -2690084675040249219L;
  protected String name = null;
  protected String text = null;
  protected String value = null;
  protected String href = null;
  private String type;
  private List<?> roleList;
  private String role;
  private String roleKey;

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
    this.text = null;

    return 2;
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
    StringBuffer results = new StringBuffer();

    results.append(getElementOpen());
    prepareAttribute(results, "name", this.name);
    if (canUse())
      results.append(prepareEventHandlers());
    else
      prepareAttribute(results, "onClick", "alert('您无权进行此操作！');return false;");
    results.append(prepareStyles());
    prepareButtonAttributes(results);
    results.append(getElementClose());
    try
    {
      this.pageContext.getOut().write(results.toString());
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return 6;
  }

  private boolean canUse() {
    boolean test = false;
    if (this.roleList == null)
      return false;
    for (int i = 0; i < this.roleList.size(); ++i)
    {
      Object obj = this.roleList.get(i);
      String getvalue = null;
      try {
        getvalue = PropertyUtils.getProperty(obj, this.roleKey).toString();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
        return false;
      } catch (InvocationTargetException e) {
        e.printStackTrace();
        return false;
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
        return false;
      }
      if (this.role == null)
        return false;
      if (!(getvalue.equals(this.role)))
        continue;
      test = true;
      break;
    }

    return test;
  }

  protected String getElementOpen()
  {
    if (this.type.equals("link")) {
      return "<a href=\"" + this.href + "\"";
    }
    return "<input type=\"" + this.type + "\""; }

  protected String getElementClose() {
    if (this.type.equals("link")) {
      return "</a>";
    }
    return "/>";
  }

  protected void prepareButtonAttributes(StringBuffer results)
    throws JspException
  {
    prepareAttribute(results, "accesskey", getAccesskey());
    prepareAttribute(results, "tabindex", getTabindex());
    prepareValue(results);
  }

  protected void prepareValue(StringBuffer results)
  {
    String label = this.value;

    if ((label == null) && (this.text != null)) {
      label = this.text;
    }

    if ((label == null) || (label.length() < 1)) {
      label = getDefaultValue();
    }
    if (this.type.equals("link"))
      results.append(">" + label);
    else
      prepareAttribute(results, "value", label);
  }

  protected String getDefaultValue()
  {
    return "Submit";
  }

  public void release()
  {
    super.release();
    this.name = null;
    this.text = null;
    this.value = null;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getHref() {
    return this.href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public List getRoleList() {
    return this.roleList;
  }

  public void setRoleList(List roleList) {
    this.roleList = roleList;
  }

  public String getRole() {
    return this.role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getRoleKey() {
    return this.roleKey;
  }

  public void setRoleKey(String roleKey) {
    this.roleKey = roleKey;
  }
}