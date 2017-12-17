package com.xunj.tag;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.commons.beanutils.PropertyUtils;

public class TreeTag extends BodyTagSupport
{
  private static final long serialVersionUID = -2977807460132670304L;
  private List content;
  private String returnValue;
  private String displayValue;
  private String headValue;
  private String headDisplayValue;
  private String idname;
  private String pidname;
  private String regex;
  private String imgUrlClose;
  private String useDblClick;

  public String getRegex()
  {
    return this.regex;
  }

  public void setRegex(String regex) {
    this.regex = regex;
  }

  public String getHeadDisplayValue() {
    return this.headDisplayValue;
  }

  public void setHeadDisplayValue(String headDisplayValue) {
    this.headDisplayValue = headDisplayValue;
  }

  public String getHeadValue() {
    return this.headValue;
  }

  public void setHeadValue(String headValue) {
    this.headValue = headValue;
  }

  public String getIdname() {
    return this.idname;
  }

  public void setIdname(String idname) {
    this.idname = idname;
  }

  public String getPidname() {
    return this.pidname;
  }

  public void setPidname(String pidname) {
    this.pidname = pidname;
  }

  public List getContent() {
    return this.content;
  }

  public void setContent(List content) {
    this.content = content;
  }

  public String getDisplayValue() {
    return this.displayValue;
  }

  public void setDisplayValue(String displayValue) {
    this.displayValue = displayValue;
  }

  public String getReturnValue() {
    return this.returnValue;
  }

  public void setReturnValue(String returnValue) {
    this.returnValue = returnValue;
  }

  public String getUseDblClick() {
    return this.useDblClick;
  }

  public void setUseDblClick(String useDblClick) {
    this.useDblClick = useDblClick;
  }

  public void release()
  {
    super.release();
  }

  public int doStartTag()
    throws JspException
  {
    String contentPath = this.pageContext.getServletContext().getContextPath();
    contentPath = contentPath + "/common/xjTree/";
    this.imgUrlClose = contentPath + "+.gif";
    String headvaluecontent = this.headValue;

    StringBuffer treesb = new StringBuffer();
    treesb.append("<script language=\"javaScript\">xjTree.init('" + contentPath + "-.gif" + "','" + contentPath + "+.gif" + "')</script>");
    treesb.append("<div class=\"treeBorder\">");
    if (this.headDisplayValue != null)
    {
      treesb.append("<ul>\n");
      treesb.append("<li><span name=\"title\" id=\"" + this.headValue + "\" onclick=\"xjTree.c(this)\">");
      treesb.append(this.headDisplayValue);
      treesb.append("</span></li></ul>\n");
    }
    treesb.append("<ul>");
    if (this.content != null)
    {
      Iterator treeit = this.content.iterator();
      while (treeit.hasNext())
      {
        Object o = treeit.next();
        String lid = null;
        String lpid = null;
        String getid = "";
        String getvalue = "";
        try {
          if (PropertyUtils.getProperty(o, this.idname) != null)
            lid = String.valueOf(PropertyUtils.getProperty(o, this.idname));
          if (PropertyUtils.getProperty(o, this.pidname) != null)
            lpid = String.valueOf(PropertyUtils.getProperty(o, this.pidname));
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          e.printStackTrace();
        } catch (NoSuchMethodException e) {
          e.printStackTrace();
        }
        if (!(lpid.equals(headvaluecontent))) {
          continue;
        }
        if (this.regex != null)
        {
          String[] lname = this.displayValue.split(this.regex);
          String[] lvalue = this.returnValue.split(this.regex);
          label485: for (int i = 0; i < lvalue.length; ++i) {
            try
            {
              if (getid.equals(""))
              {
                if (PropertyUtils.getProperty(o, lvalue[i]) != null) {
                  getid = String.valueOf(PropertyUtils.getProperty(o, lvalue[i])); break label485;
                }
              }

              if (PropertyUtils.getProperty(o, lvalue[i]) != null)
                getid = getid + this.regex + String.valueOf(PropertyUtils.getProperty(o, lvalue[i]));
            }
            catch (IllegalAccessException e) {
              e.printStackTrace();
            } catch (InvocationTargetException e) {
              e.printStackTrace();
            } catch (NoSuchMethodException e) {
              e.printStackTrace();
            }
          }
          label626: for (int i = 0; i < lname.length; ++i)
            try
            {
              if (getvalue.equals(""))
              {
                if (PropertyUtils.getProperty(o, lname[i]) != null) {
                  getvalue = String.valueOf(PropertyUtils.getProperty(o, lname[i])); break label626;
                }
              }

              if (PropertyUtils.getProperty(o, lname[i]) != null)
                getvalue = getvalue + this.regex + String.valueOf(PropertyUtils.getProperty(o, lname[i]));
            }
            catch (IllegalAccessException e) {
              e.printStackTrace();
            } catch (InvocationTargetException e) {
              e.printStackTrace();
            } catch (NoSuchMethodException e) {
              e.printStackTrace();
            }
        }
        else
        {
          try
          {
            if (PropertyUtils.getProperty(o, this.returnValue) != null)
              getid = String.valueOf(PropertyUtils.getProperty(o, this.returnValue));
            if (PropertyUtils.getProperty(o, this.displayValue) != null)
              getvalue = String.valueOf(PropertyUtils.getProperty(o, this.displayValue));
          } catch (IllegalAccessException e) {
            e.printStackTrace();
          } catch (InvocationTargetException e) {
            e.printStackTrace();
          } catch (NoSuchMethodException e) {
            e.printStackTrace();
          }

        }

        String ifhasnext = ifHasNext(lid);
        String nextlevel = "false";
        if (!(ifhasnext.equals("")))
          nextlevel = "true";
        treesb.append("<li><img src=\"" + this.imgUrlClose + "\" id=\"img" + lid + "\" onClick=\"xjTree.t('tbl" + lid + "','img" + lid + "')\">");
        String dblclickStr = "";
        if ((this.useDblClick != null) && (this.useDblClick.equals("true")))
          dblclickStr = " ondblclick=\"xjTree.g(this,'tbl" + lid + "','img" + lid + "')\"";
        treesb.append("<span onClick=\"xjTree.c(this)\" nextlevel=\"" + nextlevel + "\" id=\"" + getid + "\"");
        treesb.append(dblclickStr);
        treesb.append(">");
        treesb.append(getvalue);
        treesb.append("</span>");

        treesb.append("<ul style=\"display:none\" id=\"tbl" + lid + "\">");
        if ((!(ifhasnext.equals(""))) && (!(lid.equals(lpid))))
          treesb.append(rendeTreeNextElement(lid));
        treesb.append("</ul></li>");
      }
    }

    treesb.append("</ul></div>");
    try {
      this.pageContext.getOut().write(treesb.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 1;
  }

  private StringBuffer rendeTreeNextElement(String parapid)
  {
    Iterator treeit = this.content.iterator();
    StringBuffer treesb = new StringBuffer();
    while (treeit.hasNext())
    {
      Object o = treeit.next();
      String lid = null;
      String lpid = null;
      String getid = "";
      String getvalue = "";
      try {
        if (PropertyUtils.getProperty(o, this.idname) != null)
          lid = String.valueOf(PropertyUtils.getProperty(o, this.idname));
        if (PropertyUtils.getProperty(o, this.pidname) != null)
          lpid = String.valueOf(PropertyUtils.getProperty(o, this.pidname));
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      }
      if (!(lpid.equals(parapid))) {
        continue;
      }
      if (this.regex != null)
      {
        String[] lname = this.displayValue.split(this.regex);
        String[] lvalue = this.returnValue.split(this.regex);
        label297: for (int i = 0; i < lvalue.length; ++i) {
          try
          {
            if (getid.equals(""))
            {
              if (PropertyUtils.getProperty(o, lvalue[i]) != null) {
                getid = String.valueOf(PropertyUtils.getProperty(o, lvalue[i])); break label297;
              }
            }

            if (PropertyUtils.getProperty(o, lvalue[i]) != null)
              getid = getid + this.regex + String.valueOf(PropertyUtils.getProperty(o, lvalue[i]));
          }
          catch (IllegalAccessException e) {
            e.printStackTrace();
          } catch (InvocationTargetException e) {
            e.printStackTrace();
          } catch (NoSuchMethodException e) {
            e.printStackTrace();
          }
        }
        label438: for (int i = 0; i < lname.length; ++i)
          try
          {
            if (getvalue.equals(""))
            {
              if (PropertyUtils.getProperty(o, lvalue[i]) != null) {
                getvalue = String.valueOf(PropertyUtils.getProperty(o, lname[i])); break label438;
              }
            }

            if (PropertyUtils.getProperty(o, lvalue[i]) != null)
              getvalue = getvalue + this.regex + String.valueOf(PropertyUtils.getProperty(o, lname[i]));
          }
          catch (IllegalAccessException e) {
            e.printStackTrace();
          } catch (InvocationTargetException e) {
            e.printStackTrace();
          } catch (NoSuchMethodException e) {
            e.printStackTrace();
          }
      }
      else
      {
        try
        {
          if (PropertyUtils.getProperty(o, this.returnValue) != null)
            getid = String.valueOf(PropertyUtils.getProperty(o, this.returnValue));
          if (PropertyUtils.getProperty(o, this.displayValue) != null)
            getvalue = String.valueOf(PropertyUtils.getProperty(o, this.displayValue));
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          e.printStackTrace();
        } catch (NoSuchMethodException e) {
          e.printStackTrace();
        }

      }

      String ifhasnext = ifHasNext(lid);
      String nextlevel = "false";
      if (!(ifhasnext.equals("")))
        nextlevel = "true";
      treesb.append("<li><img src=\"" + this.imgUrlClose + "\" id=\"img" + lid + "\" onClick=\"xjTree.t('tbl" + lid + "','img" + lid + "')\">");
      String dblclickStr = "";
      if ((this.useDblClick != null) && (this.useDblClick.equals("true")))
        dblclickStr = " ondblclick=\"xjTree.g(this,'tbl" + lid + "','img" + lid + "')\"";
      treesb.append("<span onClick=\"xjTree.c(this)\" nextlevel=\"" + nextlevel + "\" id=\"" + getid + "\"");
      treesb.append(dblclickStr);
      treesb.append(">");
      treesb.append(getvalue);
      treesb.append("</span>");

      treesb.append("<ul style=\"display:none\" id=\"tbl" + lid + "\">");
      if ((!(ifhasnext.equals(""))) && (!(lid.equals(lpid))))
        treesb.append(rendeTreeNextElement(lid));
      treesb.append("</ul></li>");
    }

    return treesb;
  }

  private String ifHasNext(String id) {
    String returnstr = "";
    String lpid = null;
    Iterator it = this.content.iterator();
    while (it.hasNext())
    {
      Object o = it.next();
      try
      {
        lpid = String.valueOf(PropertyUtils.getProperty(o, this.pidname));
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      }
      if (lpid.equals(id))
        returnstr = "true";
    }
    return returnstr;
  }
}