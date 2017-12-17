package com.xunj.tag;

import com.xunj.po.CodeDict;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import org.apache.commons.beanutils.PropertyUtils;

public class CodeDictOutTag extends BaseHandlerTag
{
  private static final long serialVersionUID = 8768861483759900735L;
  protected String value = null;
  private String fieldName = null;
  private String parentId = null;
  private String regex = null;

  public String getValue()
  {
    return this.value;
  }

  public void setValue(String value)
  {
    this.value = value; }

  public String getRegex() {
    return this.regex; }

  public void setRegex(String regex) {
    this.regex = regex; }

  public String getFieldName() {
    return this.fieldName; }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public int doStartTag()
    throws JspException
  {
    String outText = renderValue();
    try {
      this.pageContext.getOut().write(outText);
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    return 1;
  }

  protected String renderValue()
    throws JspException
  {
    StringBuffer results = new StringBuffer("");
    try
    {
      List dictlist = (List)this.pageContext.getServletContext().getAttribute("CodeDict");
      String str = "";
      for (int i = 0; i < dictlist.size(); ++i)
      {
        CodeDict vo = (CodeDict)dictlist.get(i);
        String val = "";

        if ((this.fieldName == null) || ("".equals(this.fieldName))) {
          this.fieldName = "codeName";
        }

        Object getvalues = null;
        try {
          getvalues = PropertyUtils.getProperty(vo, this.fieldName);
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          e.printStackTrace();
        } catch (NoSuchMethodException e) {
          e.printStackTrace();
        }
        if (getvalues != null)
          val = (String)getvalues;
        else {
          val = vo.getCodeName();
        }

        if (this.regex != null)
        {
          String[] s = this.value.split(this.regex);
          for (int j = 0; j < s.length - 1; ++j)
          {
            if (!(s[j].equals(vo.getCodeCode())))
              continue;
            results.append(val).append(this.regex);
            break;
          }

          if (!(s[(s.length - 1)].equals(vo.getCodeCode())))
            continue;
          str = val;
        }
        else if(parentId!=null){
        	if(value.equals(vo.getCodeCode())&&parentId.equals(vo.getParentCodeId()))
			{
				results.append(val);
				break;
			}
        }
        else
        {
          if (!(this.value.equals(vo.getCodeCode())))
            continue;
          results.append(val);
          break;
        }
      }
      results.append(str);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    if (results.toString().equals(""))
      results.append("");
    return results.toString();
  }

  public int doAfterBody()
    throws JspException
  {
    return 1;
  }

  public int doEndTag()
    throws JspException
  {
    return 1;
  }

  public void release()
  {
    super.release();
    this.value = null;
  }

public String getParentId() {
	return parentId;
}

public void setParentId(String parentId) {
	this.parentId = parentId;
}
}