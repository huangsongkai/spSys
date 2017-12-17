/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.xunj.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;

import com.xunj.po.CodeDict;

/**
 * �ֵ���������
 * ʹ��codeCode �����Ҫʹ��codeId��Ҫ�½�Tag
 */
public class CodeIdDictOutTag extends BaseHandlerTag {
    /**
	 *  
	 */
	private static final long serialVersionUID = 8768861483759900735L;

	// ----------------------------------------------------- Instance Variables


    /**
     * The server value for this option.
     */
    protected String value = null;
    private String regex=null;


    // --------------------------------------------------------- Public Methods

    /**
	 * @return value
	 */
	public String getValue() {
		return value;
	}


	/**
	 * @param value Ҫ���õ� value
	 */
	public void setValue(String value) {
		this.value = value;
	}


	public String getRegex() {
		return regex;
	}


	public void setRegex(String regex) {
		this.regex = regex;
	}


	/**
     * Generate the required input tag. [Indexed property since Struts 1.1]
     *
     * @throws JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
        String outText = renderValue();

        try {
			pageContext.getOut().write(outText);
		} catch (IOException e) {
			// TODO �Զ���� catch ��
			e.printStackTrace();
		}



        return 1;
    }


    /**
     * Renders an HTML &lt;input type="radio"&gt; element.
     *
     * @param serverValue  The data to be used in the tag's <code>value</code>
     *                     attribute and sent to the server when the form is
     *                     submitted.
     * @param checkedValue If the serverValue equals this value the radio
     *                     button will be checked.
     * @return A radio input element.
     * @throws JspException
     * @since Struts 1.1
     */
    protected String renderValue()
        throws JspException {
        StringBuffer results = new StringBuffer("");

        try {
			List<?> dictlist=(List<?>) this.pageContext.getServletContext().getAttribute("CodeDict");
			String str= "";
			for(int i=0;i<dictlist.size();i++)
			{
				CodeDict vo=(CodeDict) dictlist.get(i);
				if(regex!=null)
				{
					String s[]=value.split(regex);
					for(int j=0;j<s.length-1;j++)
					{
						if(s[j].equals(vo.getCodeId()))
						{
							results.append(vo.getCodeName()).append(regex);
							break;
						}
					}
					if(s[s.length-1].equals(vo.getCodeId()))
					{
						str = vo.getCodeName();
					}
				}
				else
				{
					if(value.equals(vo.getCodeId()))
					{
						results.append(vo.getCodeName());
						break;
					}
				}
			}
			results.append(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if(results.toString().equals(""))
        	results.append(value);
        
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
        value = null;
    }

}
