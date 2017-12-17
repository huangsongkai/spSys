package com.xunj.tag;

import com.xunj.po.SysFuncInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class MenuTag extends BodyTagSupport
{
  private static final long serialVersionUID = -2977807460132670304L;
  protected String funcParentId = null;
  private String menu;

  public void release()
  {
    super.release();
  }

  public String getFuncParentId()
  {
    return this.funcParentId;
  }

  public void setFuncParentId(String funcParentId)
  {
    this.funcParentId = funcParentId;
  }

  public int doStartTag()
    throws JspException
  {
    try
    {
      rendeMenuElement(this.funcParentId, true);
      this.pageContext.getOut().write(this.menu);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return 1;
  }

  private void rendeMenuElement(String pid, boolean isFirstTime)
    throws JspException
  {
    ArrayList list = (ArrayList)this.pageContext.getSession().getAttribute("menu");
    if (list == null)
      return;
    Iterator it = list.iterator();

    if (isFirstTime)
      this.menu = "<ul id=\"nav\">\n";
    else {
      this.menu += "<ul>\n";
    }

    while (it.hasNext())
    {
      SysFuncInfo funcInfo = (SysFuncInfo)it.next();
      if (!(funcInfo.getFuncParentId().equals(pid)))
        continue;
      this.menu += "<li>";
      this.menu += "<a ";
      if (funcInfo.getUrl() != null)
      {
        MenuTag tmp159_158 = this; tmp159_158.menu = tmp159_158.menu + "href='" + funcInfo.getUrl() + "' ";
      } else {
        this.menu += "href=\"#\" "; }
      if (funcInfo.getOnclickFunction() != null)
      {
        MenuTag tmp237_236 = this; tmp237_236.menu = tmp237_236.menu + "onClick='" + funcInfo.getOnclickFunction() + "' "; }
      this.menu += ">";
      if (funcInfo.getUrlImg() != null)
      {
        MenuTag tmp312_311 = this; tmp312_311.menu = tmp312_311.menu + "<img src=\"" + funcInfo.getUrlImg() + "\">"; }
      this.menu += replaceSpace(funcInfo.getFuncName());
      this.menu += "</a>";

      if (funcInfo.getIsLeaf().equals("false"))
      {
        this.menu += "\n";
        rendeMenuElement(funcInfo.getFuncId(), false);
      }
      this.menu += "</li>\n";
    }

    this.menu += "</ul>\n";
  }

  private String replaceSpace(String str) {
    return str.replaceAll(" ", "&nbsp;");
  }
}