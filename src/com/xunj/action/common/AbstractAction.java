package com.xunj.action.common;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.xunj.core.CoreDao;
import com.xunj.core.CorePo;
import com.xunj.core.MD5;
import com.xunj.core.ParaUnit;
//import com.xunj.po.SysUploadFile;
import com.xunj.po.UploadFile;

public class AbstractAction extends ActionSupport implements SessionAware,
		ServletRequestAware, ServletResponseAware ,ServletContextAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2182436656436810881L;

	private static final String JSPURL = "toJsp";
	
//	protected static ArrayList<ParaUnit> paralist;

	protected HttpServletRequest request;
	protected HttpServletResponse response;

	private Map<String, Object> session;

	protected ServletContext servletContext;
	protected WebApplicationContext context;
	private String jspUrl;

	protected String path;

	protected String basePath;
	protected Map mapreq;

	protected CoreDao dao;

	protected List<File> upload = new ArrayList<File>();
	protected List<String> uploadFileName = new ArrayList<String>();

	protected List<String> uploadContentType = new ArrayList<String>();
	protected String[] fileMark;
	
	protected List<UploadFile> uploadfileList;
	
	protected CorePo corePo;
	public AbstractAction() {
		super();
	}

	/*
	 * 提示完毕返回值，关闭窗口
	 */
	public void Alert_Close(String msg, String returnValue) {
		request.setAttribute("msgType", "1");
		request.setAttribute("msg", msg);
		request.setAttribute("callbackType", "closeCurrent");  
		request.setAttribute("returnValue", returnValue);
	}
	
	/*
	 * 提示完毕返回值，关闭窗口
	 */
	public void Alert_CloseDialog(String msg, String returnValue) {
		request.setAttribute("msgType", "Dialog");
		request.setAttribute("msg", msg);
		request.setAttribute("returnValue", returnValue);
	}
		
	
	/**
	 * DWZ信息提示使用
	 * @param msg 提示
	 * @param callbackType  返回类型  closeCurrent
	 * @param navTabId 返回刷新的页面ID  如角色管理
	 * @param submitTg  暂未使用
	 */
	public void Alert_GoUrl(String msg, String callbackType,String navTabId,String url) {
		request.setAttribute("msg", msg);
		request.setAttribute("callbackType", callbackType);  
		request.setAttribute("navTabId", navTabId);  
		request.setAttribute("url", url);  
	}
	/**
	 * 提示完毕返回值，跳转url
	 * frame_left
	 * frame_right
	 * @param msg
	 * @param url
	 */
	public void Alert_GoUrl(String msg, String url) {
		request.setAttribute("msgType", "2");
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
	}
	/*
	 * 不弹出提示消息，跳转url
	 */
	public void GoUrl(String url,String target,String submitTg) {
		request.setAttribute("msgType", "3");
		if(target!=null && !"".equals(target))
		{
			request.setAttribute("target", target);
		}
		if(submitTg!=null && !"".equals(submitTg))
		{
			request.setAttribute("submitTg", submitTg);
		}
		request.setAttribute("url", url);
	}
	/*
	 * 提示完毕返回值，跳转url
	 */
	public void GoUrl(String url) {
		request.setAttribute("msgType", "3");
		request.setAttribute("url", url);
	}
	/*
	 * 提示完毕返回值，并关闭当前窗口
	 */
	public void closeDialog(String msg) {
		request.setAttribute("msgType", "4");
		request.setAttribute("msg", msg);
	}
	/*
	 * 提示完毕返回值
	 */
	public void Alert(String msg) {
		request.setAttribute("msgType", "5");
		request.setAttribute("msg", msg);
	}
	/*
	 * 不弹出提示消息，关闭窗口
	 */
	public void Close(String returnValue) {
		request.setAttribute("msgType", "4");
		request.setAttribute("returnValue", returnValue);
	}
	/**
	 * src : true //退出_关闭窗口 src : false //退出_返回登陆页
	 * 
	 * @param src
	 */
	public void Alert_Quit(String msg) {
		request.setAttribute("msg", msg);
		request.setAttribute("quit_login", "false");
	}


	public String getBasePath() {

		basePath = request.getScheme() + "://" + request.getServerName() + ":"
				+ request.getServerPort() + path + "/";

		return basePath;
	}

	/**
	 * 得到当前日期
	 * 
	 * @return
	 */
	String getCurDate() {
		String nowdate = null;
		// 速度快
		Calendar cal;
		int year;
		int month;
		int day;
		int hour24;
		int min;
		int sec;
		int ms;

		cal = new GregorianCalendar();
		year = cal.get(Calendar.YEAR); // 2002
		month = cal.get(Calendar.MONTH); // 0=Jan, 1=Feb, ...
		day = cal.get(Calendar.DAY_OF_MONTH); // 1...
		hour24 = cal.get(Calendar.HOUR_OF_DAY); // 0..23
		min = cal.get(Calendar.MINUTE); // 0..59
		sec = cal.get(Calendar.SECOND); // 0..59
		ms = cal.get(Calendar.MILLISECOND); // 0..999

		// 格式化数字
		NumberFormat formatter = new DecimalFormat("00");
		String strMonth = null;
		String strDay = null;
		String strHour24 = null;
		String strMin = null;
		String strSec = null;
		String strMs = null;

		strMonth = formatter.format(month + 1);
		strDay = formatter.format(day);
		strHour24 = formatter.format(hour24);
		strMin = formatter.format(min);
		strSec = formatter.format(sec);

		formatter = new DecimalFormat("000");
		strMs = formatter.format(ms);

		nowdate = year + strMonth + strDay + strHour24 + strMin + strSec
				+ strMs;
		return nowdate;
	}
	/**
	 * 权限验证方法
	 * if(!verifyPower("005001"))
	 * 	return toMessage();
	 * @param funcId	//功能点ID
	 * @return			//验证通过返回true，无权限返回false
	 */
	protected boolean verifyPower(String funcId)
	{
		String sessionId = request.getSession().getId();
		MD5 md5 = new MD5();
		String VFID = md5.toMD5(sessionId+"_X-J_"+funcId);
		HashMap verifyMap = (HashMap) session.get("verifyMap");
		if(verifyMap.get(VFID)==null)
		{
			request.setAttribute("msgType", "3");
			request.setAttribute("msg", "抱歉，您是无权用户！");
			return false;
		}
		else
			return true;
	}
	public CoreDao getDao() {
		return dao;
	}

	public String getJspUrl() {
		return jspUrl;
	}

	public Map getMapreq() {
		mapreq = (Map) ActionContext.getContext().get("request");
		return mapreq;
	}

	public String getPath() {

		path = request.getContextPath();

		return path;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public ServletContext getServletContext() {
		return this.servletContext;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public List<File> getUpload() {
		return this.upload;
	}

	public List<String> getUploadContentType() {
		return this.uploadContentType;
	}

	public List<String> getUploadFileName() {
		return this.uploadFileName;
	}

	/**
	 * 判断的提交形式，post返回true，get返回false
	 * 
	 * @return
	 */
	protected boolean POST() {
		if (request.getMethod().equals("POST"))
			return true;
		else
			return false;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public void setDao(CoreDao dao) {
		this.dao = dao;
	}

	public void setJspUrl(String jspUrl) {
		this.jspUrl = jspUrl;
	}

	public void setMapreq(Map mapreq) {
		this.mapreq = mapreq;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
		this.context= WebApplicationContextUtils.getWebApplicationContext(servletContext);

	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;

	}

	public void setSession(Map session) {
		this.session = session;
	}

	public void setUpload(List<File> upload) {
		this.upload = upload;
	}

	public void setUploadContentType(List<String> contentTypes) {
		this.uploadContentType = contentTypes;
	}

	public void setUploadFileName(List<String> uploadFileNames) {
		this.uploadFileName = uploadFileNames;
	}

	protected String toJsp(String jspUrl) {
		this.jspUrl = jspUrl;
		return JSPURL;
	}

	protected String toMessage() {
		return toJsp("/message.jsp");
	}
	protected String toPrintPage() {
		return toJsp("/printPage.jsp");
	}
	protected String toResult() {
		return toJsp("/result.jsp");
	}
	protected String toError() {
		return toJsp("/error.jsp");
	}
	protected String toErrorMessage() {
		return toJsp("/errorMessage.jsp");
	}
	protected String toCustomerData() {
		return toJsp("/customerData.jsp");
	}
	protected String toCitySalesmanData() {
		return toJsp("/citySalesmanData.jsp");
	}
	protected String toChartData() {
		return toJsp("/chartData.jsp");
	}
	protected String toSalesmanData() {
		return toJsp("/salesmanData.jsp");
	}
	protected String toData() {
		return toJsp("/data.jsp");
	}
	protected String toSelect() {
		return toJsp("/select.jsp");
	}
	protected String toHandlerData() {
		return toJsp("/handlerData.jsp");
	}
	/**
	 * 根据所传入的所属附件表和对应ID返回附件信息
	 * @param belongTable       所属表
	 * @param belongId          对应ID
	 * @return
	 */
	protected List<UploadFile> findFileList(String belongTable,String belongId)throws BusinessException{
		List<UploadFile> list = new ArrayList<UploadFile>();
		UploadFile uploadFile = new UploadFile();
		uploadFile.setBelongId(belongId);
		uploadFile.setBelongTable(belongTable);
		list = dao.findByExample(uploadFile);
		return list;
	}
	/**
	 * 附件上传
	 * @param tableName
	 * @param id
	 * @return
	 */
	public ArrayList<UploadFile> upload(String tableName,String id) {
		ArrayList<UploadFile> arr = new ArrayList<UploadFile>();
		if(StringUtils.isEmpty(id)){
			return arr;
		}
		HashMap<String, String> config=(HashMap<String, String>) servletContext.getAttribute("sysConfig");
		//文件保存形式，网络或本地native、network
		String uploadLocation=config.get("uploadFileSavePath");

		int num = upload.size();
		UploadFile uploadfile;
//		String enclosureId;
		String fileName = "";
		Date enterTime;
		Long size;
		String belongTable = "";
		String belongId = "";
		String remark = "";
		String folder = request.getParameter("folder");
		if(folder!=null&&!folder.equals(""))
			folder="/"+folder;
		else
			folder="";
		File fOld ;
		FileUtil fileUtil=new FileUtil(servletContext);
		for (int i = 0; i < num; i++) {
			
			
			if(uploadContentType.size() == 0){
				return arr;
			}
			fileName = uploadFileName.get(i);
			int position = fileName.lastIndexOf(".");    
	        String ext = ""; 
	        if(position!=-1)
	        	ext=fileName.substring(position);
			fOld = upload.get(i);
			uploadfile = new UploadFile();
			
			String newFileName = getCurDate() + "_" + i + ext;
			fileUtil.uploadFile(newFileName,upload.get(i),folder);
			
			enterTime = new Date();
			
			size = fOld.length() ;
			
			belongTable = tableName;
			belongId = id;
			
			if(fileMark !=null && fileMark[i] != null){
				
				remark = fileMark[i];
			}
			
			uploadfile.setBelongTable(belongTable);
			uploadfile.setUploadLocation(uploadLocation);
			if(uploadLocation.equals("native"))
				uploadfile.setPutPath(config.get("nativeUpLoadPath") + folder +"/"+newFileName);
			else
				uploadfile.setPutPath(folder + "/"+newFileName);
			uploadfile.setEnterTime(enterTime);
			uploadfile.setFileName(fileName);
			uploadfile.setRemark(remark);
			uploadfile.setFileSize(size);
			uploadfile.setExtensionName(ext);
			uploadfile.setBelongId(belongId);
			
			arr.add(uploadfile);

		}
		return arr;
	}



	public String[] getFileMark() {
		return fileMark;
	}

	public void setFileMark(String[] fileMark) {
		this.fileMark = fileMark;
	}

	public CorePo getCorePo() {
		return corePo;
	}

	public void setCorePo(CorePo corePo) {
		this.corePo = corePo;
	}

	public List<UploadFile> getUploadfileList() {
		return uploadfileList;
	}

	public void setUploadfileList(List<UploadFile> uploadfileList) {
		this.uploadfileList = uploadfileList;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
		
	}
	public HttpServletResponse getResponse(){
		return this.response;
	}

}
