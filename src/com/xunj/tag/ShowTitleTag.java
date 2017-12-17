package com.xunj.tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

public class ShowTitleTag extends BaseHandlerTag
{
  private boolean subTagExecuted = false;
  protected String name;

  public String getName()
  {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public synchronized boolean getPerimission()
  {
    return (!(this.subTagExecuted));
  }

  public synchronized void subTagSuccessed()
  {
    this.subTagExecuted = true;
  }

  public int doStartTag()
    throws JspException
  {
    this.subTagExecuted = false;

    String options = renderSelectStartElement();
    try {
      this.pageContext.getOut().write(options);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 1;
  }

  protected String renderSelectStartElement()
    throws JspException
  {
    StringBuffer results = new StringBuffer("");

    results.append("<div id=\"body_title\">");
    results.append("<table class=\"title_table\"><tr><td><div class=\"body_title_text\">" + this.name + "</div></td>");
    results.append("<td class=\"right_align\"><div class=\"body_title_func\">");
    return results.toString();
  }

  public int doEndTag() throws JspException
  {
    try
    {
      StringBuffer results = new StringBuffer("");
      results.append("</div></td></tr></table>");
      results.append("</div>");

      this.pageContext.getOut().write(results.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return super.doEndTag();
  }

  public void release()
  {
    this.subTagExecuted = false;
  }
}