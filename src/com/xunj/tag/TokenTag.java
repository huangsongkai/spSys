package com.xunj.tag;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class TokenTag extends BodyTagSupport
{
  public void release()
  {
    super.release();
  }

  public int doStartTag()
    throws JspException
  {
    try
    {
      String html = "<input type='hidden' name='xunj.submitKeyId' value='" + UUID.randomUUID().toString() + "'>";
      this.pageContext.getOut().write(html);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return 1;
  }
}