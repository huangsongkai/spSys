package com.xunj.tag;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class UploadFileTag extends BodyTagSupport
{
  private static final long serialVersionUID = -8504034966084192223L;
  private String beanName;
  private String belongId;
  private String allowDeletions;
  private String deleteFile;
  private String isAdd;
  private String fileCount = "-1";
  private String isList;
  private String folder;
  private String isWeb;
  private String callBackOnDelete;
  private String hideContainerWhenNoFile;
  private String containerId;

  public void release()
  {
    super.release();
  }

  public String getHideContainerWhenNoFile()
  {
    return this.hideContainerWhenNoFile;
  }

  public void setHideContainerWhenNoFile(String hideContainerWhenNoFile) {
    this.hideContainerWhenNoFile = hideContainerWhenNoFile;
  }

  public String getContainerId() {
    return this.containerId;
  }

  public void setContainerId(String containerId) {
    this.containerId = containerId;
  }

  public String getAllowDeletions() {
    return this.allowDeletions;
  }

  public void setAllowDeletions(String allowDeletions) {
    this.allowDeletions = allowDeletions;
  }

  public String getBeanName() {
    return this.beanName;
  }

  public void setBeanName(String beanName) {
    this.beanName = beanName;
  }

  public String getBelongId() {
    return this.belongId;
  }

  public void setBelongId(String belongId) {
    this.belongId = belongId;
  }

  public String getIsAdd() {
    return this.isAdd;
  }

  public void setIsAdd(String isAdd) {
    this.isAdd = isAdd;
  }

  public String getIsList() {
    return this.isList;
  }

  public void setIsList(String isList) {
    this.isList = isList;
  }

  public String getFileCount() {
    return this.fileCount;
  }

  public void setFileCount(String fileCount) {
    this.fileCount = fileCount;
  }

  public String getFolder() {
    return this.folder;
  }

  public void setFolder(String folder) {
    this.folder = folder;
  }

  public int doStartTag()
    throws JspException
  {
    try
    {
      String html = "";
      html = rendeditElement();
      this.pageContext.getOut().write(html);
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    return 1;
  }

  private String rendeditElement() {
    if ((this.deleteFile == null) || (!(this.deleteFile.equalsIgnoreCase("false"))))
      this.deleteFile = "true";
    String uuid = UUID.randomUUID().toString();
    String returnHtml = "";
    returnHtml = "<script type=\"text/javascript\">";
    if (this.isList.equals("true"))
    {
      String appendScript = "";

      if ((this.isAdd.equals("false")) && 
        (this.hideContainerWhenNoFile != null) && 
        (this.hideContainerWhenNoFile.equals("true")) && 
        (this.containerId != null))
      {
        appendScript = " if($('#fileCount').val()==0){$('#" + this.containerId + "').hide();}";
      }

      if (this.isWeb == null)
      {
        returnHtml = returnHtml + "$.get(\"common~uploadFile~lstFile@getFileListCommon.do?nt=" + uuid + "\", " + 
          "{'beanName':'" + this.beanName + "','belongId' :'" + this.belongId + "', 'allowDeletions' : '" + this.allowDeletions + "', 'deleteFile' : '" + this.deleteFile + "', 'callBackOnDelete' : '" + this.callBackOnDelete + "' }," + 
          "function (data, textStatus){document.getElementById('uploadFileDiv" + uuid + "').innerHTML=document.getElementById('uploadFileDiv" + uuid + "').innerHTML+data;" + appendScript + 
          "});";
      }
      else
      {
        returnHtml = returnHtml + "$.get(\"getFileListWebUtil.do?nt=" + uuid + "\", " + 
          "{'beanName':'" + this.beanName + "','belongId' :'" + this.belongId + "' }," + 
          "function (data, textStatus){document.getElementById('uploadFileDiv').innerHTML=document.getElementById('uploadFileDiv').innerHTML+data;" + appendScript + 
          "});";
      }
    }
    if (this.isAdd.equals("true"))
    {
      returnHtml = returnHtml + "$.get(\"" + this.pageContext.getServletContext().getContextPath() + "/jsp/common/uploadFile/create.jsp?nt=" + uuid + "\", " + 
        "{'fileCount':'" + this.fileCount + "','folder':'" + this.folder + "'}," + 
        "function (data, textStatus){document.getElementById('uploadFileDiv" + uuid + "').innerHTML=data+document.getElementById('uploadFileDiv" + uuid + "').innerHTML;";
      if (this.isList.equals("false"))
        returnHtml = returnHtml + "createUploadFile(" + this.fileCount + ",false);});\n";
      else {
        returnHtml = returnHtml + "});\n";
      }
    }

    returnHtml = returnHtml + "</script>";
    returnHtml = returnHtml + "<div id='uploadFileDiv" + uuid + "' class=\"left_align\"></div>";
    return returnHtml;
  }

  public String getDeleteFile() {
    return this.deleteFile;
  }

  public void setDeleteFile(String deleteFile) {
    this.deleteFile = deleteFile;
  }

  public String getCallBackOnDelete() {
    return this.callBackOnDelete;
  }

  public void setCallBackOnDelete(String callBackOnDelete) {
    this.callBackOnDelete = callBackOnDelete;
  }

  public String getIsWeb() {
    return this.isWeb;
  }

  public void setIsWeb(String isWeb) {
    this.isWeb = isWeb;
  }
}