package com.xunj.tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class TitleButtonTag extends TagSupport
{
  private static final long serialVersionUID = -6544114758086098486L;
  private String onClickFunction;
  private String urlImg;
  private String funcName;
  private String funcType;
  private String funcId;
  private String funcValue;
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
    if (this.funcName != null) {
      if ("submit".equals(this.funcType)){
    	  results.append("<img src=\"" + this.urlImg + "\" width=\"20\" height=\"25\" /><input type=\"submit\" value=\"" + this.funcName + "\" class=\"toolBarButton\"/>");
      }else if ("text".equals(this.funcType)){
    	  results.append("" + this.funcName + ":<input type=\"text\" id=\""+this.funcId+"\" onclick=\"" + this.onClickFunction +"\" class=\"toolBarInput\"/>");
      }else if ("T".equals(this.funcType)){
    	  results.append("<input type=\"text\" id=\""+this.funcId+"\" onclick=\"" + this.onClickFunction +"\" class=\"toolBarInput\"/>" + this.funcName + "");
      }else if("button".equals(this.funcType)){
    	  results.append("<input type=\"button\" value=\"" + this.funcName + "\" class=\"toolBarButton2\" onclick=\"" + this.onClickFunction +"\" />");
      }else if("select".equals(this.funcType)){
    	  results.append("<select id=\""+this.funcId+"\">");
    	  String[] funcNames=this.funcName.split(",");
    	  String[] funcValues=this.funcValue.split(",");
    	  for(int i=0;i<funcNames.length;i++){
    		  results.append("<option value=\"" + funcValues[i]  + "\">" +funcNames[i] +"</option>");
    	  }
    	  results.append("</select>");
      }else {
        results.append("<img src=\"" + this.urlImg + "\" width=\"20\" height=\"25\" /><a href=\"#\"  onclick=\"" + this.onClickFunction +"\">" + this.funcName + "</a>");
      }
    }
    else {
      throw new JspException(
        this.parentObj.getName() + 
        "'s optionlabel and optionvalue must use when options property be defined");
    }

    return results.toString();
  }

  public String getOnClickFunction()
  {
    return this.onClickFunction;
  }

  public void setOnClickFunction(String onClickFunction) {
    this.onClickFunction = onClickFunction;
  }

  public String getUrlImg() {
    return this.urlImg;
  }

  public void setUrlImg(String urlImg) {
    this.urlImg = urlImg;
  }

  public String getFuncName() {
    return this.funcName;
  }

  public void setFuncName(String funcName) {
    this.funcName = funcName;
  }

  public ShowTitleTag getParentObj() {
    return this.parentObj;
  }

  public void setParentObj(ShowTitleTag parentObj) {
    this.parentObj = parentObj;
  }

  public String getFuncType() {
    return this.funcType;
  }

  public void setFuncType(String funcType) {
    this.funcType = funcType;
  }

public String getFuncId() {
	return funcId;
}

public void setFuncId(String funcId) {
	this.funcId = funcId;
}

public String getFuncValue() {
	return funcValue;
}

public void setFuncValue(String funcValue) {
	this.funcValue = funcValue;
}
  
}