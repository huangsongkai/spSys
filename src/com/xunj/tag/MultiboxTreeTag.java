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

public class MultiboxTreeTag extends BodyTagSupport
{
  private static final long serialVersionUID = -2977807460132670304L;
  private String chooseType;
  private String returnValue;
  private String displayValue;
  private List content;
  private String headValue;
  private String headDisplayValue;
  private String idname;
  private String pidname;
  private String regex;
  private String imgUrlClose;
  private String useDblClick;
  private String[] checkboxvalues;
  private String radiovalue;

  public String getChooseType()
  {
    return this.chooseType;
  }

  public void setChooseType(String chooseType) {
    this.chooseType = chooseType;
  }

  public String getRegex() {
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

  public String[] getCheckboxvalues() {
    return this.checkboxvalues;
  }

  public void setCheckboxvalues(String[] checkboxvalues) {
    this.checkboxvalues = checkboxvalues;
  }

  public String getRadiovalue() {
    return this.radiovalue;
  }

  public void setRadiovalue(String radiovalue) {
    this.radiovalue = radiovalue;
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

  public String getUseDblClick() {
    return this.useDblClick;
  }

  public void setUseDblClick(String useDblClick) {
    this.useDblClick = useDblClick;
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

  public void release()
  {
    super.release();
  }

  public int doStartTag() throws JspException {
    String contentPath = this.pageContext.getServletContext().getContextPath();
    contentPath = contentPath + "/common/xjTree/";
    this.imgUrlClose = contentPath + "+.gif";

    if ((!(this.chooseType.equals("checkbox"))) && (!(this.chooseType.equals("radio")))) {
      throw new JspException("ChooseType's value must be 'checkbox' or 'radio'");
    }
    StringBuffer treesb = new StringBuffer();
    treesb.append("<script language=\"javaScript\">xjTree.init('" + contentPath + "-.gif" + "','" + contentPath + "+.gif" + "');</script>");
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
      int level = 1;
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
        if ((getHeadValue() == null) || (lpid == null) || (!(lpid.equals(getHeadValue())))) {
          continue;
        }
        if (this.regex != null)
        {
          String[] lname = this.displayValue.split(this.regex);
          String[] lvalue = this.returnValue.split(this.regex);
          label530: for (int i = 0; i < lvalue.length; ++i) {
            try
            {
              if (getid.equals(""))
              {
                if (PropertyUtils.getProperty(o, lvalue[i]) != null) {
                  getid = String.valueOf(PropertyUtils.getProperty(o, lvalue[i])); break label530;
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
          label671: for (int i = 0; i < lname.length; ++i)
            try
            {
              if (getvalue.equals(""))
              {
                if (PropertyUtils.getProperty(o, lname[i]) != null) {
                  getvalue = String.valueOf(PropertyUtils.getProperty(o, lname[i])); break label671;
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
        treesb.append("<li><img src=\"" + this.imgUrlClose + "\" id=\"img" + lid + "\" onClick=\"xjTree.t('tbl" + lid + "','img" + lid + "')\" plevel=\"0\" level=\"" + level + "\" " + ifChecked(getid) + ">");
        if (this.chooseType.equals("checkbox"))
          treesb.append("<input type=\"checkbox\" name=\"" + this.idname + "\" value=\"" + getid + "\" onclick=\"xjTree.chooseNext(this)\" plevel=\"0\" level=\"" + level + "\" " + ifChecked(getid) + " nextlevel=\"" + nextlevel + "\">");
        else
          treesb.append("<input type=\"radio\" name=\"" + this.idname + "\" value=\"" + getid + "\" onclick=\"xjTree.chooseMe(this)\">");
        String dblclickStr = "";
        if ((this.useDblClick == null) || (this.useDblClick.equals("true")))
          dblclickStr = " ondblclick=\"xjTree.g(this,tbl" + lid + ",img" + lid + ")\"";
        treesb.append("<span onClick=\"xjTree.c(this)\" nextlevel=\"" + nextlevel + "\" id=\"" + getid + "\"");
        treesb.append(dblclickStr);
        treesb.append(">");
        treesb.append(getvalue);
        treesb.append("</span>");

        treesb.append("<ul style=\"display:none\" id=\"tbl" + lid + "\">");
        if ((!(ifhasnext.equals(""))) && (!(lid.equals(lpid))))
          treesb.append(rendeTreeNextElement(lid, String.valueOf(level)));
        treesb.append("</ul></li>");
        ++level;
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

  private StringBuffer rendeTreeNextElement(String parapid, String plevel)
  {
    Iterator treeit = this.content.iterator();
    StringBuffer treesb = new StringBuffer();
    int level = 1;
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
        label301: for (int i = 0; i < lvalue.length; ++i) {
          try
          {
            if (getid.equals(""))
            {
              if (PropertyUtils.getProperty(o, lvalue[i]) != null) {
                getid = String.valueOf(PropertyUtils.getProperty(o, lvalue[i])); break label301;
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
        label442: for (int i = 0; i < lname.length; ++i)
          try
          {
            if (getvalue.equals(""))
            {
              if (PropertyUtils.getProperty(o, lname[i]) != null) {
                getvalue = String.valueOf(PropertyUtils.getProperty(o, lname[i])); break label442;
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
      if (this.chooseType.equals("checkbox"))
        treesb.append("<input type=\"checkbox\" name=\"" + this.idname + "\" value=\"" + getid + "\" onclick=\"xjTree.chooseNext(this)\" plevel=\"" + plevel + "\" level=\"" + plevel + level + "\" " + ifChecked(getid) + " nextlevel=\"" + nextlevel + "\">");
      else
        treesb.append("<input type=\"radio\" name=\"" + this.idname + "\" value=\"" + getid + "\" onclick=\"xjTree.chooseMe(this)\" plevel=\"" + plevel + "\" level=\"" + plevel + level + "\" " + ifChecked(getid) + ">");
      String dblclickStr = "";
      if ((this.useDblClick == null) || (this.useDblClick.equals("true")))
        dblclickStr = " ondblclick=\"xjTree.g(this,tbl" + lid + ",img" + lid + ")\"";
      treesb.append("<span onClick=\"xjTree.c(this)\" nextlevel=\"" + nextlevel + "\" id=\"" + getid + "\"");
      treesb.append(dblclickStr);
      treesb.append(">");
      treesb.append(getvalue);
      treesb.append("</span>");

      treesb.append("<ul style=\"display:none\" id=\"tbl" + lid + "\">");
      if ((!(ifhasnext.equals(""))) && (!(lid.equals(lpid))))
        treesb.append(rendeTreeNextElement(lid, plevel + level));
      treesb.append("</ul></li>");
      ++level;
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
      try {
        if (PropertyUtils.getProperty(o, this.pidname) != null)
          lpid = String.valueOf(PropertyUtils.getProperty(o, this.pidname));
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      }
      if (lpid.equals(id))
        returnstr = "nextlevel=\"true\"";
    }
    return returnstr;
  }

  private String ifChecked(String value)
  {
    String str = "";
    if (this.chooseType.equals("checkbox"))
    {
      if (this.checkboxvalues != null)
      {
        for (int i = 0; i < this.checkboxvalues.length; ++i)
        {
          if (value.equals(this.checkboxvalues[i])) {
            str = "checked=\"true\"";
          }
        }
      }

    }
    else if ((this.radiovalue != null) && (value.equals(this.radiovalue))) {
      str = "checked=\"true\"";
    }
    return str;
  }
}