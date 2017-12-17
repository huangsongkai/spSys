package com.exam.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.struts2.ServletActionContext;

import com.exam.po.CertConfig;
import com.exam.po.CertConfigRule;
import com.exam.po.CertTaskInfo;
import com.exam.po.CertTaskScore;
import com.exam.po.CheakTaskScore;
import com.exam.po.CheckConfig;
import com.exam.po.CheckTaskInfo;
import com.exam.po.UnitInfo;
import com.xunj.action.common.AbstractAction;
import com.xunj.action.common.BusinessException;
import com.xunj.core.Common;
import com.xunj.core.Java2JSON;
import com.xunj.po.CodeDict;
import com.xunj.po.SysOperationRole;
import com.xunj.po.UploadFile;



public class ReadcdAction extends AbstractAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2313597202779433824L;
	private ArrayList<File> filecd = new ArrayList<File>();	
	private ArrayList<File> filecd1 = new ArrayList<File>();
	private File doc;
	private String docContentType;
	private String docFileName;    
	private ArrayList<UploadFile> list; 
	private CodeDict codeDict;
	
	
	public CodeDict getCodeDict() {
		return codeDict;
	}
	public void setCodeDict(CodeDict codeDict) {
		this.codeDict = codeDict;
	}
	public ArrayList<File> getFilecd1() {
		return filecd1;
	}
	public void setFilecd1(ArrayList<File> filecd1) {
		this.filecd1 = filecd1;
	}
	public ArrayList<UploadFile> getList() {
		return list;
	}
	public void setList(ArrayList<UploadFile> list) {
		this.list = list;
	}
	public ArrayList<File> getFilecd() {
		return filecd;
	}
	public void setFilecd(ArrayList<File> filecd) {
		this.filecd = filecd;
	}
	public File getDoc() {
		return doc;
	}
	public void setDoc(File doc) {
		this.doc = doc;
	}
	public String getDocContentType() {
		return docContentType;
	}
	public void setDocContentType(String docContentType) {
		this.docContentType = docContentType;
	}
	public String getDocFileName() {
		return docFileName;
	}
	public void setDocFileName(String docFileName) {
		this.docFileName = docFileName;
	}
	/**
	 * 判断前台传过来的定时器
	 * @time 2016-02-19
	 * @name 黄嵩凯
	 * @return
	 * @throws Exception
	 */
	public String readCdState() throws Exception{
		String cdDrive = "";
		
	    try {
	    	List<File> docfile = new ArrayList<File>();
			
			 if(findCDWin32().size() > 0) {  
		        File file = findCDWin32().toArray(new File[0])[0];  
		        cdDrive = file.getPath();  
//		        System.out.println("****************打开cd成功***********"+cdDrive);     
		    } 
			
		    if (cdDrive!=null&&cdDrive!="") {
		    	filecd = traversal(cdDrive);
//		    	System.out.println("光驱内总文件数量"+filecd.size());    
		    	for (int i = 0; i < filecd.size(); i++) {
		    		File f = filecd.get(i);  
		    	if (f.getName().indexOf(".")!=-1) {
		    		String houzhi = f.getName().substring(f.getName().lastIndexOf("."));
		    		
		    		if (".doc".equals(houzhi)||".docx".equals(houzhi)) {
		    			docfile.add(f);
		    			
//		    			System.out.println("光盘里有word文档 文档数量");
		    			String reg=".*审查意见书.*";
		    			for (int r = 0; r < filecd.size(); r++) {
				    		
		    				if (filecd.get(r).getName().matches(reg)) {
			    				request.setAttribute("data", "Ok"); 
			    				return toData();
			    				
							}
//				    		System.out.println("/*/-*/*-/-*zhengze    "+filecd.get(i).getName().matches(reg));
//				    		System.out.println("光盘里有word文档 文档数量"+filecd.get(i).getName());   
						}
		    			
						}
		    		
					}
		    	}
		    	
		    	
			}
	    } catch (Exception e) {
	    	e.printStackTrace();  
	    	
	    	//throw new RuntimeException();
	    	request.setAttribute("data", "Error");
			return toData();
	    	
		}
		request.setAttribute("data", "No");
		return toData();
	}
	
	/**
	 *  跳转到导入页面
	 * @return
	 * @throws Exception
	 */
	public String TiaoZhuan() throws Exception{
		
		List<File> docfile = new ArrayList<File>();
		List resultList = new ArrayList();
		List unitList=dao.findAll("from UnitInfo");  
		for(int i=0;i<unitList.size();i++){
			UnitInfo unitInfo=(UnitInfo)unitList.get(i);
			resultList.add(unitInfo.getUnitNo()+"，"+unitInfo.getUnitName()+"，"+Common.getPinYinHeadChar(unitInfo.getUnitName()));
		}
			String cdDrive = "";
			File file = findCDWin32().toArray(new File[0])[0];  
	        cdDrive = file.getPath();  
	        filecd1 = traversal(cdDrive);
	        for (int i = 0; i < filecd.size(); i++) {
	    		File f = filecd.get(i);
	    		System.out.println("46545646545645645645646/*-/-*/*-/*-/*-/-*/*-/-*/*-/-*/"+f.getName());
	    		if (f.getName().lastIndexOf(".")!=-1) {
	    		String houzhi = f.getName().substring(f.getName().lastIndexOf("."));  
	    			if (".doc".equals(houzhi)||".docx".equals(houzhi)) {   
		    			docfile.add(f);
					}	  
				}
	    		
	        }
	        request.setAttribute("docfile",docfile);
	        System.out.println("跳转信息/*-/*-/*-/-*/*-/*-/-*/-*/"+docfile.get(0).getName());  
		String unitData = Java2JSON.list2jsonSuggest(resultList);
		request.setAttribute("unitData",unitData);
		return SUCCESS;
	}
	/**
	 * 导入光片里边的word文档
	 * @time 2016-02-19
	 * @name 黄嵩凯
	 * @return
	 * @throws Exception
	 */
	
	public String importCdWord() throws Exception{  
		String certType=request.getParameter("certType");
		String unitName=request.getParameter("unitName");  
		String certTaskId=request.getParameter("certTaskId");
		String cdindex=request.getParameter("cdindex");
		
     	String pkUnit=dao.findFirst("select pkUnit from UnitInfo where unitName = '"+unitName+"'").toString();
//		System.out.println("/*-/*/-/*-/*-/-*/*-/*-/-*/-*/*-/-*/*"+doc);
//			this.getUploadFileName().add(docFileName);
//			this.getUpload().add(doc);   
//			this.getUploadContentType().add(docContentType);
//			ArrayList<UploadFile> list = this.upload("word_to_cert_task",certTaskId);
			//*-*--*--*-*-*-*-*-*-*--------------------------------------------------------------------------------
			String cdDrive = "";
			 if(findCDWin32().size() > 0) {  
			        File file = findCDWin32().toArray(new File[0])[0];  
			        cdDrive = file.getPath();  
//			        System.out.println("****************读取cd完成***********"+cdDrive);     
			    } 
			    if (cdDrive!=null&&cdDrive!="") {
			    	filecd = traversal(cdDrive);
//			    	System.out.println("光驱内总文件数量"+filecd.size());  
			    	List<File> docfile = new ArrayList<File>();
			    	for (int i = 0; i < filecd.size(); i++) {
			    		File f = filecd.get(i);     
			    	if (f.getName().indexOf(".")!=-1) {
			    		String houzhi = f.getName().substring(f.getName().lastIndexOf("."));
			    		if (".doc".equals(houzhi)||".docx".equals(houzhi)) {//判断有多少个word文档
			    			docfile.add(f);
			    			
//			    			System.out.println("光盘共有Word文档数量为"+docfile.size());   
							}
			    		
						}   
			    	}
			    	for (int t = 0; t < docfile.size(); t++) {
			    		File wordfile = docfile.get(t);
//			    		System.out.println("/*-/*-/*-/*-/*-/*-/-*cdpath "+wordfile.getPath().equals(cdindex)+"/*-/*-/-/*-/*-/*-/-");
			    		if (wordfile.getPath().equals(cdindex)) {          
			    			File f11 ;
			    			this.getUploadFileName().add(wordfile.getName());  
			    			this.getUpload().add(wordfile);
			    			this.getUploadContentType().add("application/msword");
			    			list = this.upload("word_to_cert_task",certTaskId);
			    			docfile.remove(wordfile);
			    			break;
						}
						
					}
			    	for (int i = 0; i < docfile.size(); i++) {
			    		this.getUploadFileName().add(docfile.get(i).getName());
		    			this.getUpload().add(docfile.get(i));
		    			this.getUploadContentType().add("application/msword");
		    			ArrayList<UploadFile> list2 = this.upload("word_to_cert_task",certTaskId); 
		    			dao.saveAll(list2);
					}
				}
			//*-*--*--*-*-*-*-*-*-*--------------------------------------------------------------------------------
			
			String sp=request.getRealPath("upload");
			String docName = sp+	list.get(0).getPutPath().substring(7);
			
	        /** 1. 读取WORD表格内容 */
	        HWPFDocument doc = null;
	        try {
//	        	System.out.println(docName+"-------------------docName----------------");
	            doc = new HWPFDocument(new FileInputStream(docName));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        String text = doc.getRange().text();
//	        System.out.println(text+"---------------------text-------------------");
	        /** 2. 放置分隔符:将不可见字符使用空格(32)替换 */
	        char[] charArray = text.toCharArray();
	        for(int i = 0; i < charArray.length; i++)
	        {
	            if (charArray[i] == 7)
	            {
	                charArray[i] = 35;
	            }
	        }  
	        CertTaskInfo cti = new CertTaskInfo();
	        text = String.valueOf(charArray);
	        /** 3. 将内容用#切片 */
	        String[] textArray = text.trim().split("#");
	        if("013001".equals(certType)){
	 	        	List certTaskList=(List)dao.findAll("from CertTaskInfo where certTaskId='"+certTaskId+"'");
//	 	        	if(certTaskList.size()== 0){
//	 	        		certTaskList=(List)dao.findAll("from CertTaskInfo where pkUnit = '"+pkUnit+"' and certType='013001' order by passed_time desc");
//	 	        	}
	 	        	if(certTaskList.size()> 0){
	 	        		cti = (CertTaskInfo) certTaskList.get(0);
	 	        		cti.setConfidentialityLevelQualifications("004001");
	 		        	cti.setMemberName1(textArray[24]);
	 		        	cti.setMemberUnit1(textArray[25]);
	 		        	cti.setMemberPosition1(textArray[26]);
	 		        	cti.setMemberName2(textArray[29]);
	 		        	cti.setMemberUnit2(textArray[30]);
	 		        	cti.setMemberPosition2(textArray[31]);
	 		        	cti.setMemberName3(textArray[34]);
	 		        	cti.setMemberUnit3(textArray[35]);
	 		        	cti.setMemberPosition3(textArray[36]);
	 		        	cti.setMemberName4(textArray[39]);
	 		        	cti.setMemberUnit4(textArray[40]);
	 		        	cti.setMemberPosition4(textArray[41]);
	 		        	if (textArray[44].length()<100) 
	 		        	cti.setMemberName5(textArray[44]);
	 		        	cti.setMemberUnit5(textArray[45]);
	 		        	cti.setMemberPosition5(textArray[46]);
	 		        	if (textArray[49].length()<100) 
	 		        	cti.setMemberName6(textArray[49]);
	 		        	cti.setMemberUnit6(textArray[50]);
	 		        	cti.setMemberPosition6(textArray[51]);
	 		        	if (textArray[54].length()<100) 
	 		        	cti.setMemberName7(textArray[54]);
	 		        	cti.setMemberUnit7(textArray[55]);
	 		        	cti.setMemberPosition7(textArray[56]);
	 		        	if (textArray[59].length()<100) 
		 		        cti.setMemberName8(textArray[59]);
	 		        	if(textArray[60].length()<100)
		 		        cti.setMemberUnit8(textArray[60]);
	 		        	if(textArray[61].length()<100)
		 		        cti.setMemberPosition8(textArray[61]);
		 		        if(textArray[64].length()<100)
		 		        cti.setMemberName9(textArray[64]);
		 		        if(textArray[65].length()<100)
		 		        cti.setMemberUnit9(textArray[65]);
		 		        if(textArray[66].length()<100)
		 		        cti.setMemberPosition9(textArray[66]);
		 		        if(textArray[69].length()<100){
		 		        cti.setMemberName10(textArray[69]);
		 		        cti.setMemberUnit10(textArray[70]);
		 		       	cti.setMemberPosition10(textArray[71]);
		 		        }
	 		        	//64 65 66 69 70 71
	 		        	int k=0;
	 		        	for(int  i=0;i<textArray.length;i++){
	 		        		if("审查工作简况".equals(textArray[i]) || "工作简况".equals(textArray[i])){
	 		        			k= i+1;
	 		        			break;
	 		        		}
	 		        	}
	 		        	if(k>=69){
	 		        		cti.setData1(textArray[64]);
	 		        		cti.setData2(textArray[65]);
	 		        		cti.setData3(textArray[66]);
	 		        	}
	 		        	if(k>=74){
	 		        		cti.setData4(textArray[69]);
	 		        		cti.setData5(textArray[70]);
	 		        		cti.setData6(textArray[71]);
	 		        	}
	 		        	String shenchagongzuojiankuang = textArray[k];
	 		        	cti.setTextarea3(shenchagongzuojiankuang);
	 		        	shenchagongzuojiankuang = shenchagongzuojiankuang.replaceFirst("年","#");
	 		        	shenchagongzuojiankuang = shenchagongzuojiankuang.replaceFirst("月","#");
	 		        	shenchagongzuojiankuang = shenchagongzuojiankuang.replaceFirst("日至","#");
	 		        	shenchagongzuojiankuang = shenchagongzuojiankuang.replaceFirst("月","#");
	 		        	shenchagongzuojiankuang = shenchagongzuojiankuang.replaceFirst("日，国家军工保密资格认证委审查组一行","#");
	 		        	String data[] = shenchagongzuojiankuang.trim().split("#");
	 		        	
//	 		        	System.out.println("/**-/*-/*-/*-/-*/-*/*-/*-/-*/*-/*-/*-/*-/*-/测试"+data[0]);
//	 		        	System.out.println("/**-/*-/*-/*-/-*/-*/*-/*-/-*/*-/*-/*-/*-/*-/测试"+data.length);  
	 		        	if(Integer.parseInt(data[1]) <10){
	 		        		data[1] = "0"+data[1];
	 		        	}
	 		        	if(Integer.parseInt(data[2]) <10){
	 		        		data[2] = "0"+data[2];
	 		        	}
	 		        	String passedTime = data[0]+"-"+data[1]+"-"+data[2];
	 		        	SimpleDateFormat sf= new SimpleDateFormat("yyyy-MM-dd");
	 		        	cti.setPassedTime(sf.parse(passedTime));
	 		        	List rmvList =dao.findAll("from CertTaskScore where certTaskId ="+cti.getCertTaskId());
	 		    		if ( rmvList!=null && list.size()>0 ) {
	 		    			dao.bulkUpdate("delete from CertTaskScore where certTaskId="+cti.getCertTaskId());
	 		    		}
	 		    		int i=k+2;
	 		    		for(;;i+=2){
	 		        		String s = textArray[i];
	 		        		int e = StringUtils.indexOf(s,"分值");
	 		        		s=s.replaceAll("（", "");
	 		        		if(e==-1)
	 		        			break;
	 		        		String certName = s.substring(0, e-1);
	 		        		Integer certId = (Integer)dao.findFirst("select certId from CertConfig where 1=1 and certName='"+certName+"'");
	 		        		if(certId != null && !"".equals(certId)){
	 		        			for(int j=1;;j++){
	 		        				s=s.replaceAll("．", ".");
	 		            			e = StringUtils.indexOf(s,j+".");
	 		            			if(e==-1)
	 		            				break;
	 		            			int ee = StringUtils.indexOf(s,"；",e);
	 		            			if(ee==-1)
	 		            				ee = StringUtils.indexOf(s,"。",e);
	 		            			String ss;
	 		            			if(ee==-1)
	 		            				ss = s.substring(e+2);
	 		            			else
	 		            				ss = s.substring(e+2, ee);
	 		            			e = StringUtils.indexOf(ss,"扣");
	 		            			if(e!=-1){
	 		            				String scoreRule = ss.substring(0, e);
	 		            				int w=StringUtils.indexOf(scoreRule,"第");
	 		            				int Q=StringUtils.lastIndexOf(scoreRule, "条");
	 		            				int ruleNo =0;
	 		            				if(w != -1 && Q != -1){
	 		            					ruleNo = Integer.parseInt(ss.substring(w+1, Q).trim());
	 			            				scoreRule="依据评分标准第"+ruleNo+"条";
	 		            				}
	 		                			ee = StringUtils.indexOf(ss,"分", e);
	 		                			if(ee!=-1){
	 		                				String scoreNumber = ss.substring(e+1, ee).trim();
	 		                				CertTaskScore cts = new CertTaskScore(null, scoreRule, Integer.parseInt(scoreNumber),cti.getCertTaskId(), certId,ruleNo,null,null);
	 		                				dao.save(cts);
	 		                			}        				
	 		            			}
	 		            		}
	 		        		}        		
	 		        	}
	 		    		
	 		        	for(int p=0;p<textArray.length;p++ ){
	 		        		String zongping = textArray[p];
	 			        	if(StringUtils.indexOf(zongping, "总评")!=-1){
	 			        		cti.setTextarea1(zongping.substring(3));
	 			        		break;
	 			        	}
	 		        	}
	 		        	String passedScore="";
	 		        	for(int p=0;p<textArray.length;p++ ){
	 		        		String shenchazuyijian = textArray[p];
	 			        	int e = StringUtils.indexOf(shenchazuyijian, "实际得分");
	 			        	if(e!=-1){
	 			        		e+=4;
	 			        		int ee = StringUtils.indexOf(shenchazuyijian, "分", e);
	 			        		if(ee!=-1){
	 			        			passedScore = shenchazuyijian.substring(e, ee).trim();
	 			        			if(passedScore.matches("[0-9]*")){
	 			        				cti.setPassedScore(Double.parseDouble(passedScore));
	 			        			}
	 			        			cti.setTextarea2(shenchazuyijian.substring(ee+2));
	 			        			break;
	 			        		}
	 			        	}        	
	 		        	}
	 		        	cti.setCertStatus("012003");
	 		        	cti.setSubmissionStatus("1");
	 		        	dao.update(cti);
	 		        	dao.bulkUpdate("update from UnitInfo set censorDate='"+passedTime+"',censorScore='"+passedScore+"',censorPassed='012003',censorLevel='004001',censorFirst='"+cti.getCertTaskId()+"' where pkUnit = '"+pkUnit+"'");
	 		        	close(cdDrive);
	 		        	this.Alert_GoUrl("导入成功", "closeCurrent", "", "");                       
	 	        	}else{
	 	        		close(cdDrive);
	 	        		this.Alert_GoUrl("导入失败", "closeCurrent", "", "");
	 	        	}        	
	        }else{		
	        	List certTaskList=(List)dao.findAll("from CertTaskInfo where certTaskId='"+certTaskId+"' and certType='013002' ");
	        	if(certTaskList.size()>0){
	        		
	        		cti = (CertTaskInfo) certTaskList.get(0);
 	        		cti.setConfidentialityLevelQualifications("004001");
 		        	cti.setMemberName1(textArray[26]);
 		        	cti.setMemberUnit1(textArray[27]);
 		        	cti.setMemberPosition1(textArray[28]);
 		        	cti.setMemberName2(textArray[31]);
 		        	cti.setMemberUnit2(textArray[32]);
 		        	cti.setMemberPosition2(textArray[33]);
 		        	cti.setMemberName3(textArray[36]);
 		        	cti.setMemberUnit3(textArray[37]);
 		        	cti.setMemberPosition3(textArray[38]);
 		        	cti.setMemberName4(textArray[41]);
 		        	cti.setMemberUnit4(textArray[42]);
 		        	cti.setMemberPosition4(textArray[43]);
 		        	if (textArray[46].length()<100) 
 		        	cti.setMemberName5(textArray[46]);
 		        	cti.setMemberUnit5(textArray[47]);
 		        	cti.setMemberPosition5(textArray[48]);
 		        	if (textArray[51].length()<100) 
 		        	cti.setMemberName6(textArray[51]);
 		        	cti.setMemberUnit6(textArray[52]);
 		        	cti.setMemberPosition6(textArray[53]);
 		        	if (textArray[56].length()<100) 
 		        	cti.setMemberName7(textArray[56]);
 		        	cti.setMemberUnit7(textArray[57]);
 		        	cti.setMemberPosition7(textArray[58]);
 		        	if (textArray[61].length()<100) 
	 		        cti.setMemberName8(textArray[61]);
 		        	if(textArray[62].length()<100)
	 		        cti.setMemberUnit8(textArray[62]);
 		        	if(textArray[63].length()<100)
	 		        cti.setMemberPosition8(textArray[63]);
	 		        if(textArray[66].length()<100)
	 		        cti.setMemberName9(textArray[66]);
	 		        if(textArray[67].length()<100)
	 		        cti.setMemberUnit9(textArray[67]);
	 		        if(textArray[68].length()<100)
	 		        cti.setMemberPosition9(textArray[68]);
	 		        if(textArray[71].length()<100){
	 		        cti.setMemberName10(textArray[71]);
	 		        cti.setMemberUnit10(textArray[72]);
	 		       	cti.setMemberPosition10(textArray[73]);
	 		        }
	        		int k=0;
		        	for(int  i=0;i<textArray.length;i++){
		        		if(StringUtils.contains(textArray[i],"复查情况")){
		        			k= i+1;
		        			break;
		        		}
		        	}
		        	String shenchagongzuojiankuang = textArray[k];
		        	cti.setTextarea4(shenchagongzuojiankuang);
		        	shenchagongzuojiankuang = shenchagongzuojiankuang.replaceFirst("年","#");
		        	shenchagongzuojiankuang = shenchagongzuojiankuang.replaceFirst("月","#");
		        	shenchagongzuojiankuang = shenchagongzuojiankuang.replaceFirst("日","#");
		        	String data[] = shenchagongzuojiankuang.trim().split("#");
		        	if (data[1].trim()!=""&&data[2].trim()!="") {
 		        	if(Integer.parseInt(data[1].trim()) <10){
 		        		data[1] = "0"+data[1].trim();
 		        	}
 		        	if(Integer.parseInt(data[2].trim()) <10){
 		        		data[2] = "0"+data[2].trim();
 		        	}
		        	}
 		        	String passedTime = data[0]+"-"+data[1]+"-"+data[2];
 		        	SimpleDateFormat sf= new SimpleDateFormat("yyyy-MM-dd");
 		        	cti.setPassedTime(sf.parse(passedTime));
 		        	cti.setCertStatus("012003");
 		        	cti.setSubmissionStatus("1");
 		        	cti.setConfidentialityLevelQualifications("004001");
 		        	
 		        	List rmvList =dao.findAll("from CertTaskScore where certTaskId ="+cti.getCertTaskId());
 		    		if ( rmvList!=null && list.size()>0 ) {
 		    			dao.bulkUpdate("delete from CertTaskScore where certTaskId="+cti.getCertTaskId());
 		    		}
 		    		int i=k+2;
 		    		for(;;i+=2){
 		        		String s = textArray[i];
 		        		int e = StringUtils.indexOf(s,"分值");
 		        		s=s.replaceAll("（", "");
 		        		if(e==-1)
 		        			break;
 		        		String certName = s.substring(0, e-1);
 		        		Integer certId = (Integer)dao.findFirst("select certId from CertConfig where 1=1 and certName='"+certName+"'");
 		        		if(certId != null && !"".equals(certId)){
 		        			for(int j=1;;j++){
 		        				s=s.replaceAll("．", ".");
 		            			e = StringUtils.indexOf(s,j+".");
 		            			if(e==-1)
 		            				break;
 		            			int ee = StringUtils.indexOf(s,"；",e);
 		            			if(ee==-1)
 		            				ee = StringUtils.indexOf(s,"。",e);
 		            			String ss;
 		            			if(ee==-1)
 		            				ss = s.substring(e+2);
 		            			else
 		            				ss = s.substring(e+2, ee);
 		            			e = StringUtils.indexOf(ss,"扣");
 		            			if(e!=-1){
 		            				String scoreRule = ss.substring(0, e);
 		            				int w=StringUtils.indexOf(scoreRule,"第");
 		            				int Q=StringUtils.lastIndexOf(scoreRule, "条");
 		            				int ruleNo =0;
 		            				if(w != -1 && Q != -1){
 		            					ruleNo = Integer.parseInt(ss.substring(w+1, Q).trim());
 			            				scoreRule="依据评分标准第"+ruleNo+"条";
 		            				}
 		                			ee = StringUtils.indexOf(ss,"分", e);
 		                			if(ee!=-1){
 		                				String scoreNumber = ss.substring(e+1, ee).trim();
 		                				CertTaskScore cts = new CertTaskScore(null, scoreRule, Integer.parseInt(scoreNumber),cti.getCertTaskId(), certId,ruleNo,null,null);
 		                				dao.save(cts);
 		                			}        				
 		            			}
 		            		}
 		        		}        		
 		        	}
		        	int j=0;
		        	for(int  n=k;n<textArray.length;n++){
		        		if(StringUtils.contains(textArray[n],"总评")){
		        			j= n+1;
		        			break;
		        		}
		        	}
		        	String fuchayijian = textArray[j-1];
		        	cti.setTextarea6(fuchayijian);
		        	dao.update(cti);
		        	dao.bulkUpdate("update UnitInfo set censorSecond='"+cti.getCertTaskId()+"',secondTime='"+passedTime+"' where  pkUnit = '"+pkUnit+"'");
		        	close(cdDrive);
		        	this.Alert_GoUrl("导入成功", "closeCurrent", "", "");	
	        	}else{
	        		close(cdDrive);
	        		this.Alert_GoUrl("导入失败", "closeCurrent", "", "");	
	        	}		
	        }    
			
		return toData();
	}
	/**
	 * 保密检查单位导出
	 * @name 黄嵩凯
	 * @return
	 * @throws Exception
	 */
	
	public String checkExportExcel() throws Exception{
		SimpleDateFormat sf= new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer sql = new StringBuffer();
		sql.append(" 1=1 ");
		if(POST()){
			String taskName = request.getParameter("taskName");
			request.setAttribute("taskName",taskName);
			if(StringUtils.isNotEmpty(taskName)){
				sql.append("and taskName='"+taskName+"'");
			}
			String unitName = request.getParameter("unitName");
			request.setAttribute("unitName",unitName);
			if(StringUtils.isNotEmpty(unitName)){
				sql.append("and checkUnitName like '%"+unitName+"%'");
			}
			String taskId = request.getParameter("taskId");   
			if(StringUtils.isNotEmpty(taskId)){
				sql.append("and taskId = '"+taskId+"'");    
			}
						
			String confidentialityLevelQualifications=request.getParameter("confidentialityLevelQualifications");
			if(StringUtils.isNotEmpty(confidentialityLevelQualifications)){
				sql.append("and confidentialityLevelQualifications='"+confidentialityLevelQualifications+"'");
			}
			request.setAttribute("confidentialityLevelQualifications", confidentialityLevelQualifications);
		}
		List list = dao.findByPage("from CheckTaskInfo a where"+sql+"and checkState='A' order by a.passedTime desc");
	
		ArrayList<CheckTaskInfo> dataList=new ArrayList<CheckTaskInfo>();
		for(int i=0;i<list.size();i++){
			CheckTaskInfo checkTaskInfo1=(CheckTaskInfo) list.get(i);
			CheckTaskInfo checkTask=checkTaskInfo1.copyPo();
 			String taskName =checkTaskInfo1.getTaskName();
 			CheckConfig checkConfig=(CheckConfig) dao.findOne(CheckConfig.class, Integer.parseInt(taskName));
 			if(checkConfig!=null){
// 				String checkName=checkConfig.getCheckName();
 				checkTaskInfo1.setCheckConfig(checkConfig);
 			}
 			if(checkTaskInfo1.getPkUnit()!= null ){
 				System.out.println(checkTaskInfo1.getPkUnit());
// 				DepartmentInfo di = (DepartmentInfo)dao.findFirst("from DepartmentInfo where departmentId='"+checkTaskInfo1.getPkUnit()+"'");
// 				System.out.println(di);
// 				checkTaskInfo1.setDepartmentInfo(di);
 			}
 			dataList.add(checkTaskInfo1);
 		}
		
		
		HSSFWorkbook hssfworkbook = new HSSFWorkbook();
		HSSFSheet hssfsheet = hssfworkbook.createSheet();
		HSSFRow row = null;
		HSSFCell cell = null;
		row = hssfsheet.createRow(0);
		cell= row.createCell(6);
		HSSFFont titleFont = hssfworkbook.createFont();  //设置字体
        titleFont.setFontName("黑体");  
        titleFont.setFontHeightInPoints((short) 20);  
        HSSFCellStyle titleStyle = hssfworkbook.createCellStyle(); 
        titleStyle.setFont(titleFont);  
		row=hssfsheet.createRow(1);
		cell=row.createCell(0);
		cell.setCellValue("检查任务名称");
		cell=row.createCell(1);
		cell.setCellValue("单位名称");
		cell=row.createCell(2);
		cell.setCellValue("检查结论");
		cell=row.createCell(3);
		cell.setCellValue("检查时间");
		cell=row.createCell(4);
		cell.setCellValue("检查分数");
		cell=row.createCell(5);
		cell.setCellValue("检查组人员清单");
		
		int rowNum=2;
		for(int i=0;i<dataList.size();i++){
			row=hssfsheet.createRow(rowNum);
			rowNum++;
			CheckTaskInfo checkTaskInfo=(CheckTaskInfo) dataList.get(i);
			cell=row.createCell(0);
			cell.setCellValue(checkTaskInfo.getCheckConfig().getCheckName());
			cell=row.createCell(1);
//			if (checkTaskInfo.getDepartmentInfo()!=null) {
//				cell.setCellValue(checkTaskInfo.getDepartmentInfo().getDepartmentName());
//			}else {
//				cell.setCellValue("");
//			}
		
			cell=row.createCell(2);
			cell.setCellValue(checkTaskInfo.getCheckResult());
			String censorDate="";
			if(checkTaskInfo.getPassedTime() != null){
				censorDate=sf.format(checkTaskInfo.getPassedTime());
			}
			cell=row.createCell(3);
			cell.setCellValue(censorDate);
			cell=row.createCell(4);
			String censorScore="";
			if(checkTaskInfo.getPassedScore() != null){
				censorScore = checkTaskInfo.getPassedScore().toString();
			}
			cell.setCellValue(censorScore);
			cell=row.createCell(5);
			String checkGroup = "";
			if(checkTaskInfo.getCheckGroup() != null){
				checkGroup=checkTaskInfo.getCheckGroup();
			}
			cell.setCellValue(checkGroup);
			
			Integer taskId=checkTaskInfo.getTaskId();
			List listcheakTaskScore = dao.findAll("from CheakTaskScore where taskId='"+taskId+"'");
			if(listcheakTaskScore != null && listcheakTaskScore.size()>0){
				row=hssfsheet.createRow(rowNum);
				rowNum++;
				cell=row.createCell(0);
				cell.setCellValue("");
				cell=row.createCell(1);
				cell.setCellValue("扣分标准");
				cell=row.createCell(2);
				cell.setCellValue("扣分");
				cell=row.createCell(3);
				cell.setCellValue("问题发现人");
				cell=row.createCell(4);
				cell.setCellValue("说明");
				for(int j=0;j<listcheakTaskScore.size();j++){
					CheakTaskScore cheakTaskScore=(CheakTaskScore)listcheakTaskScore.get(j);
					row=hssfsheet.createRow(rowNum);
					rowNum++;
					cell=row.createCell(0);
					cell.setCellValue("");
					cell=row.createCell(1);
					cell.setCellValue(cheakTaskScore.getScoreRule());
					cell=row.createCell(2);
					String scoreNumber="";
					if(cheakTaskScore.getScoreNumber() != null){
						scoreNumber=String.valueOf(cheakTaskScore.getScoreNumber());
					}
					cell.setCellValue(scoreNumber);
					cell=row.createCell(3);
					String troubler="";
					if(cheakTaskScore.getCheckTroubler() != null){
						troubler=cheakTaskScore.getCheckTroubler();
					}
					cell.setCellValue(troubler);
					cell=row.createCell(4);
					cell.setCellValue(cheakTaskScore.getScoreExplain());
				}
			}
		}
		String x = new String(("历次检查信息.xls").getBytes("GBK"), "ISO8859-1");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("content-disposition",
		"attachment;filename=\""
		+x+ "\";size="
		+ hssfworkbook.getBytes().length);
		OutputStream out = response.getOutputStream();
		//将工作簿输出到response
		hssfworkbook.write(out);
		out.flush();
		out.close();
		Alert_GoUrl("导出成功！","",null,null);
		return toResult();
	}
	/**
	 * 检查任务导出
	 * @return 
	 * @name 黄嵩凯   
	 * @throws Exception
	 */	
	public String taskInfoExportExcel() throws Exception{
		List checkConfigList =dao.findAll("from CheckConfig where checkState='A'");
		StringBuffer sql = new StringBuffer();
		sql.append(" 1=1 ");
		if(POST()){
			String taskName = request.getParameter("taskName");
			request.setAttribute("taskName",taskName);
			if(StringUtils.isNotEmpty(taskName)){
				sql.append("and taskName='"+taskName+"'");
			}
			String unitName = request.getParameter("unitName");
			request.setAttribute("unitName",unitName);
			if(StringUtils.isNotEmpty(unitName)){
				sql.append("and checkUnitName like '%"+unitName+"%'");
			}
						
			String confidentialityLevelQualifications=request.getParameter("confidentialityLevelQualifications");
			if(StringUtils.isNotEmpty(confidentialityLevelQualifications)){
				sql.append("and confidentialityLevelQualifications='"+confidentialityLevelQualifications+"'");
			}
			request.setAttribute("confidentialityLevelQualifications", confidentialityLevelQualifications);
		}
		List list = dao.findByPage("from CheckTaskInfo a where"+sql+"and checkState='A' order by a.passedTime desc");
	
		ArrayList<CheckTaskInfo> dataList=new ArrayList<CheckTaskInfo>();
		for(int i=0;i<list.size();i++){
			CheckTaskInfo checkTaskInfo1=(CheckTaskInfo) list.get(i);
			CheckTaskInfo checkTask=checkTaskInfo1.copyPo();
 			String taskName =checkTaskInfo1.getTaskName();
 			CheckConfig checkConfig=(CheckConfig) dao.findOne(CheckConfig.class, Integer.parseInt(taskName));
 			if(checkConfig!=null){
// 				String checkName=checkConfig.getCheckName();
 				checkTaskInfo1.setCheckConfig(checkConfig);
 			}
 			if(checkTaskInfo1.getPkUnit()!= null ){
 				System.out.println(checkTaskInfo1.getPkUnit());
 				UnitInfo ui = (UnitInfo)dao.findOne(UnitInfo.class,checkTaskInfo1.getPkUnit());
 				checkTaskInfo1.setUnitInfo(ui);
 			}
 			dataList.add(checkTaskInfo1);
 		}
		request.setAttribute("dataList",dataList);
//************************************************************************************************
		HSSFWorkbook hssfworkbook = new HSSFWorkbook();
		HSSFSheet hssfsheet = hssfworkbook.createSheet();
		HSSFRow row = null;
		HSSFCell cell = null;
		row = hssfsheet.createRow(0);
		cell= row.createCell(9);
		HSSFFont titleFont = hssfworkbook.createFont();  //设置字体
        titleFont.setFontName("黑体");  
        titleFont.setFontHeightInPoints((short) 20);  
        HSSFCellStyle titleStyle = hssfworkbook.createCellStyle(); 
        titleStyle.setFont(titleFont);  
		row=hssfsheet.createRow(1);
		cell=row.createCell(0);
		cell.setCellValue("检查任务名称");
		cell=row.createCell(1);
		cell.setCellValue("单位名称");
		cell=row.createCell(2);
		cell.setCellValue("所属集团名称");
		cell=row.createCell(3);
		cell.setCellValue("保密资格等级");
		cell=row.createCell(4);
		cell.setCellValue("检查结论");
		cell=row.createCell(5);
		cell.setCellValue("检查时间");
		cell=row.createCell(6);
		cell.setCellValue("检查分数");
		cell=row.createCell(7);
		cell.setCellValue("涉密人员人数");
		cell=row.createCell(8);
		cell.setCellValue("现场审查专家组");
	
		
		int rowNum=2;
		for(int i=0;i<dataList.size();i++){
			row=hssfsheet.createRow(rowNum);
			rowNum++;
			CheckTaskInfo checkTaskInfo=(CheckTaskInfo) dataList.get(i);
			cell=row.createCell(0);
			
			if (checkTaskInfo.getCheckConfig()!=null) {
				cell.setCellValue(checkTaskInfo.getCheckConfig().getCheckName());
			}else {
				cell.setCellValue("");
			}
			
			
			cell=row.createCell(1);
			if (checkTaskInfo.getUnitInfo()!=null) {
				cell.setCellValue(checkTaskInfo.getUnitInfo().getUnitName());
			}else {  
				cell.setCellValue("");
			}
		
			cell=row.createCell(2);  
			if (checkTaskInfo.getUnitInfo()!=null&&checkTaskInfo.getUnitInfo().getUnitGroup()!="") {
				codeDict=(CodeDict) dao.findOne(CodeDict.class, checkTaskInfo.getUnitInfo().getUnitGroup());
				if (codeDict!=null) {
					cell.setCellValue(codeDict.getCodeName());   
				}else {
					cell.setCellValue("");
				}
			}
			cell=row.createCell(3);
			if (checkTaskInfo.getConfidentialityLevelQualifications()!=null) {
					codeDict=(CodeDict) dao.findOne(CodeDict.class,checkTaskInfo.getConfidentialityLevelQualifications());  
					if (codeDict!=null) {
						cell.setCellValue(codeDict.getCodeName());
					}else {
						cell.setCellValue("");
					}
				
			}
			
			cell=row.createCell(4);
			if (checkTaskInfo.getCheckResult()!=null&&checkTaskInfo.getCheckResult()!="") {
				codeDict=(CodeDict) dao.findOne(CodeDict.class, checkTaskInfo.getCheckResult());
				if (codeDict!=null) {
					cell.setCellValue(codeDict.getCodeName());
				}else {
					cell.setCellValue("");
				}
			}else {
				cell.setCellValue("");
			}
			
			cell=row.createCell(5);
			if (checkTaskInfo.getPassedTime()!=null) {
				cell.setCellValue(checkTaskInfo.getPassedTime());
			}else {
				cell.setCellValue("");
			}
			
			cell=row.createCell(6);
			if (checkTaskInfo.getPassedScore()!=null) {
				cell.setCellValue(checkTaskInfo.getPassedScore());
			}else {
				cell.setCellValue("");
			}
			
			cell=row.createCell(7);
			if (checkTaskInfo.getConfidentialityStaffNumber()!=null) {
				cell.setCellValue(checkTaskInfo.getConfidentialityStaffNumber());
			}else {
				cell.setCellValue("");
			}
			
			cell=row.createCell(8);
			if (checkTaskInfo.getCheckGroup()!=null) {
				cell.setCellValue(checkTaskInfo.getCheckGroup());
			}else {
				cell.setCellValue("");
			}
			
		
		}
		String x = new String(("检查结论.xls").getBytes("GBK"), "ISO8859-1");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("content-disposition",
		"attachment;filename=\""
		+x+ "\";size="
		+ hssfworkbook.getBytes().length);
		OutputStream out = response.getOutputStream();
		//将工作簿输出到response
		hssfworkbook.write(out);
		out.flush();
		out.close();
		Alert_GoUrl("导出成功！","",null,null);
		return toResult();
	}
	/**
	 * 管理部分扣分的单位占比统计
	 * @return
	 * @throws Exception
	 * @name 黄嵩凯
	 * @time 2016.03.08
	 */
	public String listAssessAnalyse6() throws  Exception{
		String statDateFrom=request.getParameter("statDateFrom");
		String statDateTo=request.getParameter("statDateTo");  
		if (StringUtils.isNotEmpty(statDateFrom)) {
			statDateFrom=statDateFrom.substring(0, 3);
		}  
		if (StringUtils.isNotEmpty(statDateTo)) {
			statDateTo=statDateTo.substring(0, 3);  
		}
		String selectMemberName=request.getParameter("selectMemberName");
		String sql="";
		if(StringUtils.isNotEmpty(statDateFrom)){
			sql+=" and assessYear >="+statDateFrom+" ";
		}
		if(StringUtils.isNotEmpty(statDateTo)){
			sql+=" and statDateTo >="+statDateTo+" ";
		}
		if(StringUtils.isNotEmpty(selectMemberName)){
			sql+=" and selectMemberName >="+selectMemberName+" ";
		}
		//**************************************************************************
		StringBuilder histogram = new StringBuilder();
		StringBuilder yBar = new StringBuilder();
		yBar.append("[{name:'占比',");
		histogram.append("["); 
		yBar.append("data:[");
		//[{ name: '人数',data: [0.0,1021.0,261.0,716.0,754.0,912.0]}]
		List list=new ArrayList();
		List listP=new ArrayList();
		
		List certConfig1 = dao.findByPage("from CertConfig "+sql+" order by certId");
		Long zongkoufen = dao.findCount( "select coalesce(sum(scoreNumber),0)  from CertTaskScore ");//总扣分
		System.out.println("总扣分/*-/*-/*-/-*/*-/*-/-*"+zongkoufen);
		for (int j = 0; j < certConfig1.size(); j++) {
			CertConfig certconfig = (CertConfig) certConfig1.get(j);
			Long koufen = dao.findCount("select coalesce(sum(scoreNumber),0)  from CertTaskScore where certId ='"+certconfig.getCertId()+"' ");
			System.out.println("扣分/*-/*-/*-/-*/*-/*-/-*"+(double) koufen/1000);
			NumberFormat formatter = new DecimalFormat("0.00");   
			Double x=new Double(((double) koufen/1000)/((double) zongkoufen/1000));     
			System.out.println("biliqwewq eq "+x);
			String xx = formatter.format(x*100);    
			System.out.println("biliqwewq eq "+xx);
			if(j==certConfig1.size()-1){
				yBar.append(""+(xx+"")+"");
			}else{
				yBar.append(""+(xx+"")+",");    
			}
		}
		for (int j = 0; j < certConfig1.size(); j++) {
			CertConfig certconfig = (CertConfig) certConfig1.get(j);
			if(j==certConfig1.size()-1){
				histogram.append("'"+certconfig.getCertName()+"'");
			}else{
				histogram.append("'"+certconfig.getCertName()+"',");
			}
		}
		System.out.println(yBar+"yBar /*-/-*/-*/*-/*-/*-/*-");
		histogram.append("]");
		yBar.append("]}]");
		request.setAttribute("yBar", yBar);//[{ name: '人数',data: [0.0,1021.0,261.0,716.0,754.0,912.0]}]
		System.out.println("histogram /*-/-*/-*/*-/*-/*-/*-"+histogram);//['2012','2013','2014','2015','2016']
		request.setAttribute("histogram", histogram);
		request.setAttribute("name", "'管理部分扣分的单位占比统计'");
		return SUCCESS;
	}
	/**
	 * 基本制度扣分的单位占比统计
	 * @name 黄嵩凯
	 * @time 2016.03.09
	 */
	public String listAssessAnalyse20() throws  Exception{
 		String statDateFrom=request.getParameter("statDateFrom");
		String statDateTo=request.getParameter("statDateTo");  
		if (StringUtils.isNotEmpty(statDateFrom)) {
			statDateFrom=statDateFrom.substring(0, 3);
		}  
		if (StringUtils.isNotEmpty(statDateTo)) {
			statDateTo=statDateTo.substring(0, 3);  
		}
		String selectMemberName=request.getParameter("selectMemberName");
		String sql="";
		if(StringUtils.isNotEmpty(statDateFrom)){
			sql+=" and assessYear >="+statDateFrom+" ";
		}
		if(StringUtils.isNotEmpty(statDateTo)){
			sql+=" and statDateTo >="+statDateTo+" ";
		}
		if(StringUtils.isNotEmpty(selectMemberName)){
			sql+=" and selectMemberName >="+selectMemberName+" ";
		}
		//**************************************************************************
		StringBuilder histogram = new StringBuilder();
		StringBuilder yBar = new StringBuilder();
		histogram.append("["); 
		yBar.append("[");
		//[['现场审查成绩在480分以上',0.2140],['现场审查不合格',0.786]]
		List list=new ArrayList();
		List listP=new ArrayList();
		
		List certConfig1 = dao.findByPage("from CertConfig "+sql+" order by certId");
		Long zongkoufen = dao.findCount( "select coalesce(sum(scoreNumber),0)  from CertTaskScore ");//总扣分
		System.out.println("总扣分/*-/*-/*-/-*/*-/*-/-*"+zongkoufen);
		for (int j = 0; j < certConfig1.size(); j++) {
			CertConfig certconfig = (CertConfig) certConfig1.get(j);
			Long koufen = dao.findCount("select coalesce(sum(scoreNumber),0)  from CertTaskScore where certId ='"+certconfig.getCertId()+"' ");
			System.out.println("扣分/*-/*-/*-/-*/*-/*-/-*"+(double) koufen/1000);
			NumberFormat formatter = new DecimalFormat("0.00");     
			Double x=new Double(((double) koufen/1000)/((double) zongkoufen/1000));     
			System.out.println("biliqwewq eq "+x);
			String xx = formatter.format(x*100);    
			System.out.println("biliqwewq eq "+xx);
			if(j==certConfig1.size()-1){
				yBar.append("['"+certconfig.getCertName()+"',"+(xx+"")+"]");
			}else{
				yBar.append("['"+certconfig.getCertName()+"',"+(xx+"")+"],");    
			}
		}
		for (int j = 0; j < certConfig1.size(); j++) {
			CertConfig certconfig = (CertConfig) certConfig1.get(j);
			if(j==certConfig1.size()-1){
				histogram.append("'"+certconfig.getCertName()+"'");
			}else{
				histogram.append("'"+certconfig.getCertName()+"',");
			}
		}
		
		histogram.append("]");  	
		yBar.append("]");
		request.setAttribute("yBar", yBar);//[{ name: '人数',data: [0.0,1021.0,261.0,716.0,754.0,912.0]}]
		System.out.println("YBAR " +yBar );   
		request.setAttribute("name", "基本制度扣分的单位占比统计");
		return SUCCESS;
	}
	/**
	 * 涉密载体管理扣分的单位占比统计
	 * @name 黄嵩凯
	 * @time 2016.03.09
	 */
	public String listAssessAnalyse101() throws  Exception{
 		String statDateFrom=request.getParameter("statDateFrom");
		String statDateTo=request.getParameter("statDateTo");  
		if (StringUtils.isNotEmpty(statDateFrom)) {
			statDateFrom=statDateFrom.substring(0, 3);
		}  
		String sql="";
		//**************************************************************************
		StringBuilder yBar = new StringBuilder();
		yBar.append("[");
		//[['现场审查成绩在480分以上',0.2140],['现场审查不合格',0.786]]
		List certConfigRule = dao.findAll(" from CertConfigRule where certId='18'");
		
		Long koufen;
		Long zongkoufen = dao.findCount( "select coalesce(sum(scoreNumber),0)  from CertTaskScore where certId='18'");//总扣分
		System.out.println("总扣分/*-/*-/*-/-*/*-/*-/-*"+zongkoufen);
		for (int j = 0; j < certConfigRule.size(); j++) {
			CertConfigRule CertConfigRule1 = (CertConfigRule) certConfigRule.get(j);
			if (CertConfigRule1.getRuleNo()!=null&&CertConfigRule1.getRuleNo()!=null) {
				 koufen = dao.findCount(" select coalesce(sum(scoreNumber),0) from CertTaskScore where ruleId ='"+CertConfigRule1.getRuleNo()+"' ");                 	
			}else {
				  koufen=0L;   
			}
			if (koufen==null) {
				 koufen=0L;      
			}
			
			System.out.println("扣分/*-/*-/*-/-*/*-/*-/-*"+(double) koufen/1000);
			NumberFormat formatter = new DecimalFormat("0.00");     
			Double x=new Double(((double) koufen/1000)/((double) zongkoufen/1000));     
			System.out.println("biliqwewq eq "+x);
			String xx = formatter.format(x*100);    
			System.out.println("biliqwewq eq "+xx);
			
			
			if(j==certConfigRule.size()-1){
				yBar.append("['"+CertConfigRule1.getRuleContent()+"',"+(xx+"")+"]");
			}else{
				yBar.append("['"+CertConfigRule1.getRuleContent()+"',"+(xx+"")+"],");    
			}
		}
			
		yBar.append("]");
		request.setAttribute("yBar", yBar);//[{ name: '人数',data: [0.0,1021.0,261.0,716.0,754.0,912.0]}]
		System.out.println("YBAR " +yBar );   
		request.setAttribute("name", "涉密载体管理扣分的单位占比统计");  
		return SUCCESS;
	}
	public void  dload() throws BusinessException, FileNotFoundException{  
		String id = request.getParameter("id");
		UploadFile uploadFile = (UploadFile) dao.findOne(UploadFile.class, id);   
		String str=uploadFile.getPutPath();
		String str1=str.substring(str.lastIndexOf("/")+1, str.length());  
		System.out.println("名字"+str1);  
	 
		HttpServletResponse response = ServletActionContext.getResponse();
		System.out.println("名字'\'"+uploadFile.getPutPath().lastIndexOf("/"));  
		System.out.println("路径"+request.getSession().getServletContext().getRealPath(str));  
		//download(request.getSession().getServletContext().getRealPath(str), response);   
		
		 // 下载本地文件
        String fileName = uploadFile.getFileName(); // 文件的默认保存名  
        // 读到流中
        InputStream inStream = new FileInputStream(request.getSession().getServletContext().getRealPath(str));// 文件的存放路径
        // 设置输出的格式
        response.reset();
        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inStream.read(b)) > 0)
                response.getOutputStream().write(b, 0, len);
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		  	
		
	}
	 
//-----------------------------------------------工具类--7---------------------------------------------------------------------------	
	/**
	 * 文件下载
	 * @time 2016-03-14           
	 * @name 黄嵩凯 
	 */
	 public HttpServletResponse ssdownload(String path, HttpServletResponse response) {
	        try {
	            // path是指欲下载的文件的路径。
	            File file = new File(path);
	            // 取得文件名。
	            String filename = file.getName();
	            // 取得文件的后缀名。
	            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

	            // 以流的形式下载文件。
	            InputStream fis = new BufferedInputStream(new FileInputStream(path));
	            byte[] buffer = new byte[fis.available()];
	            fis.read(buffer);
	            fis.close();
	            // 清空response
	            response.reset();
	            // 设置response的Header
	            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
	            response.addHeader("Content-Length", "" + file.length());
	            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
	            response.setContentType("application/octet-stream");
	            toClient.write(buffer);
	            toClient.flush();
	            toClient.close();
	        } catch (IOException ex) {        
	            ex.printStackTrace();
	        }
	        return response;
	    }
	 /**
	  * 本地文件下载
	  * @param response
	  * @throws FileNotFoundException
	  */
	    
	 public void downloadLocal(HttpServletResponse response) throws FileNotFoundException {
	        // 下载本地文件
	        String fileName = "Operator.doc".toString(); // 文件的默认保存名
	        // 读到流中
	        InputStream inStream = new FileInputStream("c:/Operator.doc");// 文件的存放路径
	        // 设置输出的格式
	        response.reset();
	        response.setContentType("bin");
	        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
	        // 循环取出流中的数据
	        byte[] b = new byte[100];
	        int len;
	        try {
	            while ((len = inStream.read(b)) > 0)
	                response.getOutputStream().write(b, 0, len);
	            inStream.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	/**
	 * 传要获取当前时间几个月后的时间 传月数
	 * @time 2016-02-19  
	 * @name 黄嵩凯
	 * @param number
	 * @return
	 */
	public static String datetime(int number){
		SimpleDateFormat sdf1 =  new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, number);
		String str = sdf1.format(cal.getTime());//一个月后的时间
		return str;
	}   
	/**
	 * 打开光驱
	 * @time 2016-02-19
	 * @name 黄嵩凯
	 * @param drive
	 */
	public static void open(String drive) {  
        try {  
            File file = File.createTempFile("realhowto", ".vbs");  
            file.deleteOnExit();    
            FileWriter fw = new java.io.FileWriter(file);  
            String vbs = "Set wmp = CreateObject(\"WMPlayer.OCX\") \n"  
                    + "Set cd = wmp.cdromCollection.getByDriveSpecifier(\""  
                    + drive + "\") \n cd.Eject";  
  
            fw.write(vbs);  
            fw.close();  
            Runtime.getRuntime().exec("wscript " + file.getPath()).waitFor();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
	/**
	 * 利用VB script 关闭光驱
	 * @param drive
	 * @time 2016-02-19
	 * @name 黄嵩凯
	 */
    
	 // 利用VB script 关闭光驱  
    public static void close(String drive) {  
        try {  
            File file = File.createTempFile("realhowto", ".vbs");  
            file.deleteOnExit();  
            FileWriter fw = new FileWriter(file);  
  
            // to close a CD, we need eject two times!  
            String vbs = "Set wmp = CreateObject(\"WMPlayer.OCX\") \n"  
                    + "Set cd = wmp.cdromCollection.getByDriveSpecifier(\""  
                    + drive + "\") \n cd.Eject \n cd.Eject ";  
            fw.write(vbs);  
            fw.close();  
            Runtime.getRuntime().exec("wscript " + file.getPath()).waitFor();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    } 
	 /**
     * 获取所有光驱的盘符列表  
     * @return
     * @time 2016-02-19
	 * @name 黄嵩凯
     */
    public static ArrayList<File> findCDWin32() {    
        FileSystemView fsv = FileSystemView.getFileSystemView();  
        File[] roots = fsv.getRoots();  
        if (roots.length == 1) {  
            roots = roots[0].listFiles()[0].listFiles();  
        } else {  
            System.out.println("I guess you're not on Windows");  
            return null;  
        }  
        ArrayList<File> foundDrives = new ArrayList<File>();  
        for (int i = 0; i < roots.length; i++) {  
            if (fsv.isDrive(roots[i])) {  
                if (fsv.getSystemTypeDescription(roots[i]).indexOf("CD") != -1) {  
                    foundDrives.add(roots[i]);  
                }  
            }  
        }  
        return foundDrives;  
    } 
     /**
      * 获取传入目录所有内容
      * @param path
      * @time 2016-02-19
      * @name 黄嵩凯
      */
    public ArrayList<File>  traversal(String path) {
    	  File f = new File(path);
    	  if (f.isDirectory()) { //如果是文件夹，则递归遍历
    	    File[] array = f.listFiles();// 取文件夹下所有文件
    	   for (int i=0;i<array.length;i++) {
    	    traversal(array[i].getPath());
    	   }
    	  	} else {
    		  filecd.add(f);  
    	                   //如果是文件输出文件名，这里可以做删除等操作
    	  	}
    	   return filecd;
    }
    /**
     * 控制保留小数点后几位，2：表示保留2位小数点
     * @time 2016-02-19
     * @name 黄嵩凯
     */
	public String getPercent(Integer num,Integer totalPeople ){
		String percent ;
		Double p3 = 0.0;
		if(totalPeople == 0){
		p3 = 0.0;
		}else{
		p3 = num*1.0/totalPeople;
		}
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMinimumFractionDigits(4);//控制保留小数点后几位，2：表示保留2位小数点
		percent = nf.format(p3);
		return percent;
	}  
	
	
}
