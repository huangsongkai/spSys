package com.xunj.tag;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xunj.core.CoreDao;
import com.xunj.util.Common;


public class SelectTag extends BaseHandlerTag {
    /**
	 *  
	 */
	private static final long serialVersionUID = 8768861483759900735L;
    private String selectValue = "";
    private String name = null;
    public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}



	private String codeName=null;
    private String optionValue=null;
    private String optionText=null;
    private String tables=null;
	private String wheres=null;
	private String width=null;
	private String nullOption=null;
	private String onchange=null;
	
	public String getNullOption() {
		return nullOption;
	}


	public void setNullOption(String nullOption) {
		this.nullOption = nullOption;
	}


	public String getWidth() {
		return width;
	}


	public void setWidth(String width) {
		this.width = width;
	}


	public String getTables() {
		return tables;
	}


	public void setTables(String tables) {
		this.tables = tables;
	}


	public String getWheres() {
		return wheres;
	}


	public void setWheres(String wheres) {
		this.wheres = wheres;
	}




    public String getCodeName() {
		return codeName;
	}


	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}



    public String getSelectValue() {
		return selectValue;
	}


	public void setSelectValue(String selectValue) {
		this.selectValue = selectValue;
	}


	public String getOptionValue() {
		return optionValue;
	}


	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}


	public String getOptionText() {
		return optionText;
	}


	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}


	public int doStartTag() throws JspException {
        String outText = renderValue();

        try {
			pageContext.getOut().write(outText);
		} catch (IOException e) {
			e.printStackTrace();
		}
        return 1;
    }

    protected String renderValue() throws JspException {
  
    	StringBuffer results = new StringBuffer("");
    	String styleWidth = this.getWidth()==null?"80":this.getWidth();
    	String onChangeEvent="";
    	if(StringUtils.isNotEmpty(this.getOnchange()))
    		onChangeEvent="onchange=\""+this.getOnchange()+"\"";
    	results.append("<select name='"+this.getName()+"' style='width:"+styleWidth+"px;height: 21px' "+onChangeEvent+">");
    	if(!"0".equals(this.getNullOption()))
    	results.append("<option></option>");
    	try {
    		//CoreDao dao=(CoreDao) WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext()).getBean("dao");
    		Common dao = new Common();
    		ResultSet rs = dao.getResult("select t.column_name,t.comments from user_col_comments t where t.table_name = upper('"+this.tables+"')");
    		 while (rs.next()){
   			  	String column = rs.getString(1);
   			  	String comment = rs.getString(2);
	   			results.append("<option value='"+column+"' ");
		  		if(this.getSelectValue()!=null&&this.getSelectValue().equals(column)){
		    			results.append("selected");
		    		}	
		  		results.append(">");
		  		results.append(comment+"</option>");   		 
    		 }
    		 rs.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	results.append("</select>");
        return results.toString();
    }
    /**
     * Save the associated label from the body content.
     *
     * @throws JspException if a JSP exception has occurred
     */
    public int doAfterBody() throws JspException {
        return 1;
    }

    /**
     * Optionally render the associated label from the body content.
     *
     * @throws JspException if a JSP exception has occurred
     */
    public int doEndTag() throws JspException {
        return 1;
    }



    /**
     * Release any acquired resources.
     */
    public void release() {
        super.release();
    }

}
