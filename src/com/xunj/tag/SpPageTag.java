package com.xunj.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;

public class SpPageTag extends BodyTagSupport
{
  private static final long serialVersionUID = -2977807460132670304L;
  private String style;
  private String spPageType;
  private String spPageContainerId;
  private String formId;
  private String nowId;

  public String getStyle()
  {
    return this.style; }

  public void setStyle(String style) {
    this.style = style;
  }

  public void release()
  {
    super.release();
  }

  public int doStartTag()
    throws JspException
  {
    try
    {
      TagBase tagbase = (TagBase)this.pageContext.getRequest().getAttribute("tagbase");
      if (tagbase == null) {
        throw new JspException("please initialize the split page properties in your actions");
      }

      StringBuffer spTag = rendeElement(tagbase);
      this.pageContext.getOut().write(spTag.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }

    return 1;
  }

  private StringBuffer rendeElement(TagBase tagbase)
  {
    StringBuffer str = new StringBuffer("");
    String hidden = "";
    if (tagbase.getRowCount() == 0)
    {
      hidden = "style=\"display:none\"";
    }
    if (this.formId == null)
      this.formId = "";
    if (this.spPageContainerId == null)
      this.spPageContainerId = "";
    String selected;
    int i;
    int pn;
    int rc;
    int s;
    int e;
    if (this.style == null)
    {
      str.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
      str.append("<tr " + hidden + ">\n<td>\n");
      str.append("第<select name=\"pageNumber\" id=\"pageNumber\" class=\"selectLengthAuto\" onchange=\"xjSpPage.getPage(this.value)\">");
      selected = "";
      for (i = 1; i <= tagbase.getPageCount(); ++i)
      {
        selected = "";
        if (i == tagbase.getPageNumber())
          selected = "selected";
        str.append("<option value=\"" + i + "\" " + selected + ">" + i + "/" + tagbase.getPageCount() + "</option>");
      }
      str.append("</select>页");
      str.append(" 共" + tagbase.getRowCount() + "条 ");
      //str.append(tagbase.getPageSize() + "条/页 ");
//      str.append("<input name=\"pageSize\" id=\"pageSize\" type=\"text\"  style=\"width:3em;\"class=\"input_area\" size=\"4\" maxlength=\"5\" value=\"" + tagbase.getPageSize() + "\" onblur=\"xjSpPage.checkIsNum(this);xjSpPage.getPage();\" >条/页");
      String pageSizeOption = tagbase.getPageSizeOption();
      String[] pageSizeOptionArray = StringUtils.split(pageSizeOption, ";");

      str.append("<select name=\"pageSize\" id=\"pageSize\" class=\"selectLengthAuto\" onchange=\"xjSpPage.getPage()\">" );
      for(int j=0;j<pageSizeOptionArray.length;j++){
    	  selected = "";
          if (Integer.parseInt(pageSizeOptionArray[j]) == tagbase.getPageSize())
              selected = "selected";
          str.append("<option value=\""+pageSizeOptionArray[j]+"\" "+selected+">"+pageSizeOptionArray[j]+"</option>");
      }
      str.append("</select> 条/页");

      
      if (tagbase.getPageCount() > 10)
      {
        str.append("<a style=\"cursor:pointer\" id=\"1\" onClick=\"xjSpPage.getPage(this.id)\" title=\"首页\"><<</a>&nbsp;\n");
        str.append("<a style=\"cursor:pointer\" id=\"previous\" onClick=\"xjSpPage.steppage(this.id)\" title=\"上一页\"><&nbsp;</a>&nbsp;\n");
      }
      pn = tagbase.getPageNumber();
      rc = tagbase.getPageCount();
      s = 0;
      e = 0;
      s = (pn - 4 < 1) ? 1 : pn - 4;
      if ((rc >= 10) && (pn + 5 <= 10))
        e = 10;
      else {
        e = (pn + 5 >= rc) ? rc : pn + 5;
      }
      if (rc - e < 5)
        s = (e - 9 > 0) ? e - 9 : 1;
      else if (rc <= 10) {
        s = 1;
      }
      for (; s <= e; ++s)
      {
        if (s == pn)
          str.append("<a style=\"cursor:pointer\" id=\"" + s + "\" onClick=\"xjSpPage.getPage(this.id)\">[<b><u>" + s + "</u></b>]</a>\n");
        else {
          str.append("<a style=\"cursor:pointer\" id=\"" + s + "\" onClick=\"xjSpPage.getPage(this.id)\">[" + s + "]</a>\n");
        }
      }
      if (tagbase.getPageCount() > 10)
      {
        str.append("<a style=\"cursor:pointer\" id=\"next\" onClick=\"xjSpPage.steppage(this.id)\" title=\"下一页\">&nbsp;>&nbsp;</a>\n");
        str.append("<a style=\"cursor:pointer\" id=\"" + tagbase.getPageCount() + "\"  onClick=\"xjSpPage.getPage(this.id)\" title=\"尾页\">>>&nbsp;</a>\n");
      }
      str.append("<input name=\"pageNumber\" id=\"pageNumber\" type=\"hidden\" value=\"" + tagbase.getPageNumber() + "\">");
      str.append("<input name=\"nowPage\" id=\"nowPage\" type=\"hidden\" value=\"" + tagbase.getPageNumber() + "\">\n");
      str.append("<input name=\"pageCount\" id=\"pageCount\" type=\"hidden\" value=\"" + tagbase.getPageCount() + "\">\n");
      str.append("<input name=\"pageSize\" id=\"pageSize\" type=\"hidden\" value=\"" + tagbase.getPageSize() + "\">\n");
      str.append("<input name=\"submitType\" id=\"submitType\" type=\"hidden\" value=\"search\">\n");
      if ((this.spPageType != null) && (this.spPageType.equals("ajax")))
        str.append("<input name=\"spPageType\" id=\"spPageType\" type=\"hidden\" value=\"ajax\">\n");
      else
        str.append("<input name=\"spPageType\" id=\"spPageType\" type=\"hidden\" value=\"submit\">\n");
      
      if (this.nowId != null) 
    	  str.append("<input name=\"nowId\" id=\"nowId\" type=\"hidden\" value=\"" + this.nowId + "\">\n");
 
      
      str.append("<input name=\"spPageContainerId\" id=\"spPageContainerId\" type=\"hidden\" value=\"" + this.spPageContainerId + "\">\n");

      str.append("<input name=\"formId\" id=\"formId\" type=\"hidden\" value=\"" + this.formId + "\">\n");
      str.append("</td></tr>\n");
      if (tagbase.getRowCount() == 0)
        str.append("<tr><td>未找到符合条件的记录</td></tr>\n");
      str.append("</table>\n");
    }
    else if (this.style.equals("mini"))
    {
      str.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" " + hidden + ">\n");
      str.append("<tr>\n<td>\n");
      str.append("第<select name=\"pageNumber\" id=\"pageNumber\" class=\"selectLengthAuto\" onchange=\"xjSpPage.getPage(this.value)\">");
      selected = "";
      for (pn = 1; pn <= tagbase.getPageCount(); ++pn)
      {
        selected = "";
        if (pn == tagbase.getPageNumber())
          selected = "selected";
        str.append("<option value=\"" + pn + "\" " + selected + ">" + pn + "/" + tagbase.getPageCount() + "</option>");
      }
      str.append("</select>页");
      str.append(" 共" + tagbase.getRowCount() + "条 ");
      str.append(tagbase.getPageSize() + "条/页 ");
      if (tagbase.getPageCount() > 10)
      {
        str.append("<a style=\"cursor:pointer\" id=\"1\" onClick=\"xjSpPage.getPage(this.id)\" title=\"首页\"><<</a>&nbsp;\n");
        str.append("<a style=\"cursor:pointer\" id=\"previous\" onClick=\"xjSpPage.steppage(this.id)\" title=\"上一页\"><&nbsp;</a>&nbsp;\n");
      }
      pn = tagbase.getPageNumber();
      rc = tagbase.getPageCount();
      s = 0;
      e = 0;
      s = (pn - 2 < 1) ? 1 : pn - 2;
      if (rc >= 5)
        if (pn + 2 <= 5)
          e = 5;
        else
          e = (pn + 2 >= rc) ? rc : pn + 2;
      else {
        e = rc;
      }
      if (rc - e < 2)
        s = (e - 4 > 0) ? e - 4 : 1;
      else if (rc <= 5) {
        s = 1;
      }
      for (; s <= e; ++s)
      {
        if (s == pn)
          str.append("<a style=\"cursor:pointer\" id=\"" + s + "\" onClick=\"xjSpPage.getPage(this.id)\">[<b><u>" + s + "</u></b>]</a>\n");
        else {
          str.append("<a style=\"cursor:pointer\" id=\"" + s + "\" onClick=\"xjSpPage.getPage(this.id)\">[" + s + "]</a>\n");
        }
      }
      if (tagbase.getPageCount() > 5)
      {
        str.append("<a style=\"cursor:pointer\" id=\"next\" onClick=\"xjSpPage.steppage(this.id)\" title=\"下一页\">&nbsp;>&nbsp;</a>\n");
        str.append("<a style=\"cursor:pointer\" id=\"" + tagbase.getPageCount() + "\"  onClick=\"xjSpPage.getPage(this.id)\" title=\"尾页\">>>&nbsp;</a>\n");
      }
      str.append("<input name=\"pageNumber\" id=\"pageNumber\" type=\"hidden\" value=\"" + tagbase.getPageNumber() + "\">");
      str.append("<input name=\"nowPage\" id=\"nowPage\" type=\"hidden\" value=\"" + tagbase.getPageNumber() + "\">\n");
      str.append("<input name=\"pageCount\" id=\"pageCount\" type=\"hidden\" value=\"" + tagbase.getPageCount() + "\">\n");
      str.append("<input name=\"pageSize\" id=\"pageSize\" type=\"hidden\" value=\"" + tagbase.getPageSize() + "\">\n");
      str.append("<input name=\"submitType\" id=\"submitType\" type=\"hidden\" value=\"search\">\n");
      if ((this.spPageType != null) && (this.spPageType.equals("ajax")))
        str.append("<input name=\"spPageType\" id=\"spPageType\" type=\"hidden\" value=\"ajax\">\n");
      else
        str.append("<input name=\"spPageType\" id=\"spPageType\" type=\"hidden\" value=\"submit\">\n");
      
      if (this.nowId != null) 
    	  str.append("<input name=\"nowId\" id=\"nowId\" type=\"hidden\" value=\"" + this.nowId + "\">\n");
 
      str.append("<input name=\"spPageContainerId\" id=\"spPageContainerId\" type=\"hidden\" value=\"" + this.spPageContainerId + "\">\n");
      str.append("<input name=\"formId\" id=\"formId\" type=\"hidden\" value=\"" + this.formId + "\">\n");
      str.append("</td></tr>\n");
      if (tagbase.getRowCount() == 0)
        str.append("<tr><td>未找到符合条件的记录</td></tr>\n");
      str.append("</table>\n");
    }
    else if (this.style.equals("standard")) {
      str.append("<table width=\"100%\" border=\"0\" cellpadding=\"4\" cellspacing=\"0\" " + hidden + ">\n");
      str.append("<tr>\n<td>\n");
      str.append("共" + tagbase.getRowCount() + "条 ");
      str.append("<a style=\"cursor:pointer\" id=\"1\" onClick=\"xjSpPage.getPage(this.id)\">首页</a>\n");
      str.append("<a style=\"cursor:pointer\" id=\"previous\" onClick=\"xjSpPage.steppage(this.id)\">前一页</a>\n");
      str.append("<a style=\"cursor:pointer\" id=\"next\" onClick=\"xjSpPage.steppage(this.id)\">后一页</a>\n");
      str.append("<a style=\"cursor:pointer\" id=\"" + tagbase.getPageCount() + "\"  onClick=\"xjSpPage.getPage(this.id)\">尾页</a>\n");
      str.append("到第<select name=\"pageNumber\" id=\"pageNumber\" class=\"selectLengthAuto\" onchange=\"xjSpPage.getPage(this.value)\">");
      selected = "";
      for (i = 1; i <= tagbase.getPageCount(); ++i)
      {
        selected = "";
        if (i == tagbase.getPageNumber())
          selected = "selected";
        str.append("<option value=\"" + i + "\" " + selected + ">" + i + "/" + tagbase.getPageCount() + "</option>");
      }
      //str.append("</select>页 每页显示<input name=\"pageSize\" id=\"pageSize\" type=\"text\"  style=\"width:3em;\"class=\"input_area\" size=\"4\" maxlength=\"5\" value=\"" + tagbase.getPageSize() + "\" onblur=\"xjSpPage.checkIsNum(this);xjSpPage.getPage();\" onkeypress=\"return xjSpPage.page_onkeypress(event)\">条记录");
      String pageSizeOption = tagbase.getPageSizeOption();
      String[] pageSizeOptionArray = StringUtils.split(pageSizeOption, ";");

      str.append("</select>页 每页显示<select name=\"pageSize\" id=\"pageSize\" class=\"selectLengthAuto\" onchange=\"xjSpPage.getPage()\">" );
      for(int j=0;j<pageSizeOptionArray.length;j++){
    	  selected = "";
          if (Integer.parseInt(pageSizeOptionArray[j]) == tagbase.getPageSize())
              selected = "selected";
          str.append("<option value=\""+pageSizeOptionArray[j]+"\" "+selected+">"+pageSizeOptionArray[j]+"</option>");
      }
      str.append("</select> 条记录");
      
      str.append("<input name=\"nowPage\" id=\"nowPage\" type=\"hidden\" value=\"" + tagbase.getPageNumber() + "\">\n");
      str.append("<input name=\"pageCount\" id=\"pageCount\" type=\"hidden\" value=\"" + tagbase.getPageCount() + "\">\n");
      str.append("<input name=\"submitType\" id=\"submitType\" type=\"hidden\" value=\"search\">\n");
      if ((this.spPageType != null) && (this.spPageType.equals("ajax")))
        str.append("<input name=\"spPageType\" id=\"spPageType\" type=\"hidden\" value=\"ajax\">\n");
      else
        str.append("<input name=\"spPageType\" id=\"spPageType\" type=\"hidden\" value=\"submit\">\n");
     
      if (this.nowId != null) 
    	  str.append("<input name=\"nowId\" id=\"nowId\" type=\"hidden\" value=\"" + this.nowId + "\">\n");
       
      str.append("<input name=\"spPageContainerId\" id=\"spPageContainerId\" type=\"hidden\" value=\"" + this.spPageContainerId + "\">\n");
      str.append("<input name=\"formId\" id=\"formId\" type=\"hidden\" value=\"" + this.formId + "\">\n");
      str.append("</td></tr>\n");
      if (tagbase.getRowCount() == 0)
        str.append("<tr><td>未找到符合条件的记录</td></tr>\n");
      str.append("</table>\n");
    }

    return str; }

  public String getSpPageType() {
    return this.spPageType; }

  public void setSpPageType(String spPageType) {
    this.spPageType = spPageType; }

  public String getSpPageContainerId() {
    return this.spPageContainerId; }

  public void setSpPageContainerId(String spPageContainerId) {
    this.spPageContainerId = spPageContainerId; }

  public String getFormId() {
    return this.formId; }

  public void setFormId(String formId) {
    this.formId = formId;
  }

public String getNowId() {
	return nowId;
}

public void setNowId(String nowId) {
	this.nowId = nowId;
}
}