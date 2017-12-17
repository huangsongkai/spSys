package com.xunj.tag;

import com.xunj.po.SysFuncInfo;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class TitleButtonsTag extends TagSupport
{
  private static final long serialVersionUID = -1816474302337818307L;
  private String funcParentId;
  ShowTitleTag parentObj;

  public int doEndTag()
    throws JspException
  {
    return super.doEndTag();
  }

  public int doStartTag() throws JspException
  {
    Tag parent = getParent();

    this.parentObj = ((ShowTitleTag)parent);

    if (!(this.parentObj.getPerimission())) {
      return 0;
    }

    String options = renderOptionElement();
    try {
      this.pageContext.getOut().write(options);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 1;
  }

  protected String renderOptionElement() throws JspException
  {
    StringBuffer results = new StringBuffer();
    if (this.funcParentId != null)
    {
      List dictlist = (List)this.pageContext.getServletContext().getAttribute("FuncInfo");
      for (int i = 0; i < dictlist.size(); ++i)
      {
        SysFuncInfo vo = (SysFuncInfo)dictlist.get(i);
        if (!(this.funcParentId.equals(vo.getFuncParentId())))
          continue;
        if ("4B".equals(vo.getFuncType()))
          results.append("<img src=\"" + this.pageContext.getServletContext().getContextPath() + "/" + vo.getUrlImg() + "\" width=\"20\" height=\"25\" /><input type=\"submit\" value=\"" + vo.getFuncName() + "\" class=\"toolBarButton\"/>");
        else {
          results.append("<img src=\"" + this.pageContext.getServletContext().getContextPath() + "/" + vo.getUrlImg() + "\" width=\"20\" height=\"25\" /><a href=\"#\" onclick=" + vo.getOnclickFunction() + " >" + vo.getFuncName() + "</a>");
        }
      }
    }
    else
    {
      throw new JspException(
        this.parentObj.getName() + 
        "'s optionlabel and optionvalue must use when options property be defined");
    }

    return results.toString(); }

  public String getFuncParentId() {
    return this.funcParentId;
  }

  public void setFuncParentId(String funcParentId) {
    this.funcParentId = funcParentId;
  }
}