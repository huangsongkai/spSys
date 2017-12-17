package com.exam.action;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

import com.exam.dao.CommonDAO;
import com.exam.po.CertConfigRule;
import com.exam.po.CertTaskInfo;
import com.exam.po.CertTaskScore;
import com.exam.po.UnitInfo;
import com.xunj.action.common.AbstractAction;
import com.xunj.core.Common;
import com.xunj.core.Java2JSON;
import com.xunj.core.ParaUnit;
import com.xunj.core.SessionBean;
import com.xunj.po.CodeDict;
import com.xunj.po.CodeKind;
import com.xunj.po.UploadFile;



public class CensorAction extends AbstractAction{

	private static final long serialVersionUID = 4747379774042416043L;
	private CertTaskInfo certTaskInfo;
	private UploadFile uploadFile;
	private int reListPaperQuestion = 0;
	private final int maxReListPaperQuestion = 100;
	
	private File doc;
	private String docFilePath;
	private String docContentType;
	private String docFileName;
	
	private class QuestionListIndex{
		private int from;
		private int to;
		private int codeId;
		public QuestionListIndex(int codeId, int from) {
			super();
			this.codeId = codeId;
			this.from = from;
		}
		public int getFrom() {
			return from;
		}
		public void setFrom(int from) {
			this.from = from;
		}
		public int getTo() {
			return to;
		}
		public void setTo(int to) {
			this.to = to;
		}
		public int getCodeId() {
			return codeId;
		}
		public void setCodeId(int codeId) {
			this.codeId = codeId;
		}
	}
	private String getCodeDict(String questionType) {
		List<CodeDict> codeDictList=(List) this.getServletContext().getAttribute("CodeDict");
		String codeName="";
		for(CodeDict codeDict : codeDictList){
			if(questionType.equals(codeDict.getCodeCode())){
				codeName=codeDict.getCodeName();
			}
		}
		return codeName;
	}
	public CertTaskInfo getCertTaskInfo() {
		return certTaskInfo;
	}

	public void setCertTaskInfo(CertTaskInfo certTaskInfo) {
		this.certTaskInfo = certTaskInfo;
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

	public String getDocFilePath() {
		return docFilePath;
	}

	public void setDocFilePath(String docFilePath) {
		this.docFilePath = docFilePath;
	}

	public UploadFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(UploadFile uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String listCensor() throws Exception{
		StringBuilder unitSql = new StringBuilder();
		StringBuilder certSql = new StringBuilder();
		StringBuilder dateSql = new StringBuilder();
		String dateFrom = request.getParameter("dateFrom");
		String dateTo = request.getParameter("dateTo");
		String tiaozhuan = request.getParameter("tiaozhuan");
		if (StringUtils.isNotEmpty(tiaozhuan)) {
			dateSql.append(" and DATEDIFF(str_to_date(data19,'%Y-%m-%d'),sysdate())<30 ");
		}

		
		if (StringUtils.isNotEmpty(dateFrom)&&StringUtils.isNotEmpty(dateTo)) {
			dateSql.append("and passedTime>='"+dateFrom+"' and passedTime<='"+dateTo+"'    ");
		}

		request.setAttribute("dateFrom", dateFrom);
		request.setAttribute("dateTo", dateTo);
		
		SimpleDateFormat sf= new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sfd = new SimpleDateFormat("yyyy");
		unitSql.append("");
		String unitGroup=request.getParameter("unitGroup");
		String unitLoginAddress=request.getParameter("unitLoginAddress");

		if(StringUtils.isNotEmpty(unitLoginAddress)){
			unitSql.append(" and unitLoginAddress like '%"+unitLoginAddress+"%'");
		}
		request.setAttribute("unitLoginAddress", unitLoginAddress);

		if(StringUtils.isNotEmpty(unitGroup)){
			unitSql.append(" and unitGroup = '"+unitGroup+"'");
		}
	    request.setAttribute("unitGroup", unitGroup);
		String unitName=request.getParameter("unitName");
		if(StringUtils.isNotEmpty(unitName)){
			unitSql.append(" and unitName like '%"+unitName+"%'");
		}
		request.setAttribute("unitName", unitName);
		certSql.append("");
		String applicationStatus=request.getParameter("applicationStatus");
		if("1".equals(applicationStatus)){
			certSql.append(" and applicationStatus ='1' ");
		}else if ("0".equals(applicationStatus)) {
			certSql.append(" and (applicationStatus is null or applicationStatus ='0') ");
		}
		request.setAttribute("applicationStatus", applicationStatus);
		String reviewInfoStatus=request.getParameter("reviewInfoStatus");
		if("1".equals(reviewInfoStatus)){
			certSql.append(" and reviewInfoStatus ='1' ");
		}else if ("0".equals(reviewInfoStatus)) {
			certSql.append(" and (reviewInfoStatus is null or reviewInfoStatus ='0') ");
		}
		request.setAttribute("reviewInfoStatus", reviewInfoStatus);
		String scoreStatus = request.getParameter("scoreStatus");
		if("1".equals(scoreStatus)){
			certSql.append(" and (numPeople is not null or highestScore is not null or average is not null )");
		}else if ("0".equals(scoreStatus)) {
			certSql.append(" and (numPeople is null && highestScore is null && average is null) ");
		}
		request.setAttribute("scoreStatus", scoreStatus);
		String submissionStatus=request.getParameter("submissionStatus");
		if("1".equals(submissionStatus)){
			certSql.append(" and submissionStatus ='1' ");
		}else if ("0".equals(submissionStatus)) {
			certSql.append(" and (submissionStatus is null or submissionStatus ='0') ");
		}
		request.setAttribute("submissionStatus", submissionStatus);
		String otherFileStatus=request.getParameter("otherFileStatus");
		if("1".equals(otherFileStatus)){
			certSql.append(" and otherFileStatus ='1' ");
		}else if ("0".equals(otherFileStatus)) {
			certSql.append(" and (otherFileStatus is null or otherFileStatus ='0') ");
		}
		request.setAttribute("otherFileStatus", otherFileStatus);
		String certTaskId=request.getParameter("certTaskId");
		if(StringUtils.isNotEmpty(certTaskId)){
			certSql.append(" and certTaskId = '"+certTaskId+"'");
		}
		ArrayList<CertTaskInfo> alreadyList=new ArrayList<CertTaskInfo>();
		ArrayList<CertTaskInfo> haveNotList=new ArrayList<CertTaskInfo>();
		DateUtils f=new DateUtils();
		//Date dt=new Date();  
		//String a = sf.format(f.addYears(dt, -5));
		
		List CertTaskInfoList=dao.findAll("from CertTaskInfo where certStatus in ('012003','012006') and certType='013001' and pkUnit in (select pkUnit from UnitInfo where 1=1 "+unitSql+" ) "+certSql+" "+dateSql+" order by confidentialityLevelQualifications,passedTime desc");
		
		
		List unitInfoList=dao.findAll("from UnitInfo");

		for(int i=0;i<CertTaskInfoList.size();i++){
			certTaskInfo=(CertTaskInfo) CertTaskInfoList.get(i);
			String pkUnit=certTaskInfo.getPkUnit();
			for(int j=0;j<unitInfoList.size();j++){
				UnitInfo  unit=(UnitInfo )unitInfoList.get(j);
				if(unit.getPkUnit().toString().equals(pkUnit)){
					List temp=new ArrayList();
					temp.add(unit);
					certTaskInfo.setUnitInfoList(temp);
				}
			}
			alreadyList.add(certTaskInfo);
		}
		List CertTaskInfoList1=dao.findAll("from CertTaskInfo where  certStatus in ('012001','012002') and certType='013001' and pkUnit in (select pkUnit from UnitInfo where 1=1 "+unitSql+" ) "+certSql+" order by certStatus desc");
		request.setAttribute("totalCount2",alreadyList.size() );
		for(int i=0;i<CertTaskInfoList1.size();i++){
			certTaskInfo=(CertTaskInfo) CertTaskInfoList1.get(i);
			String pkUnit=certTaskInfo.getPkUnit();
			for(int j=0;j<unitInfoList.size();j++){
				UnitInfo  unit=(UnitInfo )unitInfoList.get(j);
				if(unit.getPkUnit().toString().equals(pkUnit)){
					List temp=new ArrayList();
					temp.add(unit);
					certTaskInfo.setUnitInfoList(temp);
				}
			}
			haveNotList.add(certTaskInfo);
		}
		request.setAttribute("totalCount1",haveNotList.size() );
		request.setAttribute("alreadyList", alreadyList);
		request.setAttribute("havaNotList", haveNotList);
		List resultList = new ArrayList();
		List unitList=dao.findAll("from UnitInfo");
		for(int i=0;i<unitList.size();i++){
			UnitInfo unitInfo=(UnitInfo)unitList.get(i);
			resultList.add(unitInfo.getUnitNo()+"，"+unitInfo.getUnitName()+"，"+Common.getPinYinHeadChar(unitInfo.getUnitName()));
		}
		String unitData = Java2JSON.list2jsonSuggest(resultList);
		request.setAttribute("unitData",unitData);
		return SUCCESS;
	}
	public String listReview() throws Exception{
		StringBuilder unitSql = new StringBuilder();
		StringBuilder certSql = new StringBuilder();
		StringBuilder dateSql = new StringBuilder();
		String dateFrom = request.getParameter("dateFrom");
		String dateTo = request.getParameter("dateTo");
		
		String reviewResult = request.getParameter("reviewResult");
		if (StringUtils.isNotEmpty(reviewResult)) {
			dateSql.append("and reviewResult='"+reviewResult+"' ");
			request.setAttribute("reviewResult", reviewResult);
		}
		
		if (StringUtils.isNotEmpty(dateFrom)&&StringUtils.isNotEmpty(dateTo)) {
			dateSql.append("and passedTime>='"+dateFrom+"' and passedTime<='"+dateTo+"'    ");
		}

		request.setAttribute("dateFrom", dateFrom);
		request.setAttribute("dateTo", dateTo);
		
		SimpleDateFormat sf= new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sfd = new SimpleDateFormat("yyyy");
		unitSql.append("");
		String unitGroup=request.getParameter("unitGroup");
		String unitLoginAddress=request.getParameter("unitLoginAddress");

		if(StringUtils.isNotEmpty(unitLoginAddress)){
			unitSql.append(" and unitLoginAddress like '%"+unitLoginAddress+"%'");
		}
		request.setAttribute("unitLoginAddress", unitLoginAddress);

		if(StringUtils.isNotEmpty(unitGroup)){
			unitSql.append(" and unitGroup = '"+unitGroup+"'");
		}
	    request.setAttribute("unitGroup", unitGroup);
		String unitName=request.getParameter("unitName");
		if(StringUtils.isNotEmpty(unitName)){
			unitSql.append(" and unitName like '%"+unitName+"%'");
		}
		request.setAttribute("unitName", unitName);
		certSql.append("");
		String applicationStatus=request.getParameter("applicationStatus");
		if("1".equals(applicationStatus)){
			certSql.append(" and applicationStatus ='1' ");
		}else if ("0".equals(applicationStatus)) {
			certSql.append(" and (applicationStatus is null or applicationStatus ='0') ");
		}
		request.setAttribute("applicationStatus", applicationStatus);
		String reviewInfoStatus=request.getParameter("reviewInfoStatus");
		if("1".equals(reviewInfoStatus)){
			certSql.append(" and reviewInfoStatus ='1' ");
		}else if ("0".equals(reviewInfoStatus)) {
			certSql.append(" and (reviewInfoStatus is null or reviewInfoStatus ='0') ");
		}
		request.setAttribute("reviewInfoStatus", reviewInfoStatus);
		String scoreStatus = request.getParameter("scoreStatus");
		if("1".equals(scoreStatus)){
			certSql.append(" and (numPeople is not null or highestScore is not null or average is not null )");
		}else if ("0".equals(scoreStatus)) {
			certSql.append(" and (numPeople is null && highestScore is null && average is null) ");
		}
		request.setAttribute("scoreStatus", scoreStatus);
		String submissionStatus=request.getParameter("submissionStatus");
		if("1".equals(submissionStatus)){
			certSql.append(" and submissionStatus ='1' ");
		}else if ("0".equals(submissionStatus)) {
			certSql.append(" and (submissionStatus is null or submissionStatus ='0') ");
		}
		request.setAttribute("submissionStatus", submissionStatus);
		String otherFileStatus=request.getParameter("otherFileStatus");
		if("1".equals(otherFileStatus)){
			certSql.append(" and otherFileStatus ='1' ");
		}else if ("0".equals(otherFileStatus)) {
			certSql.append(" and (otherFileStatus is null or otherFileStatus ='0') ");
		}
		request.setAttribute("otherFileStatus", otherFileStatus);
		String certTaskId=request.getParameter("certTaskId");
		if(StringUtils.isNotEmpty(certTaskId)){
			certSql.append(" and certTaskId = '"+certTaskId+"'");
		}
		ArrayList<CertTaskInfo> alreadyList=new ArrayList<CertTaskInfo>();
		ArrayList<CertTaskInfo> haveNotList=new ArrayList<CertTaskInfo>();
		DateUtils f=new DateUtils();
		//Date dt=new Date();
		//String a = sf.format(f.addYears(dt, -5));
		
		List CertTaskInfoList=dao.findAll("from CertTaskInfo where certStatus in ('012003','012006') and certType='013002' and pkUnit in (select pkUnit from UnitInfo where 1=1 "+unitSql+" ) "+certSql+" "+dateSql+" order by confidentialityLevelQualifications,passedTime desc");
		List unitInfoList=dao.findAll("from UnitInfo");

		for(int i=0;i<CertTaskInfoList.size();i++){
			certTaskInfo=(CertTaskInfo) CertTaskInfoList.get(i);
			String pkUnit=certTaskInfo.getPkUnit();
			for(int j=0;j<unitInfoList.size();j++){
				UnitInfo  unit=(UnitInfo )unitInfoList.get(j);
				if(unit.getPkUnit().toString().equals(pkUnit)){
					List temp=new ArrayList();
					temp.add(unit);
					certTaskInfo.setUnitInfoList(temp);
				}
			}
			alreadyList.add(certTaskInfo);
		}
		List CertTaskInfoList1=dao.findAll("from CertTaskInfo where  certStatus in ('012001','012002') and certType='013002' and pkUnit in (select pkUnit from UnitInfo where 1=1 "+unitSql+" ) "+certSql+" order by certStatus desc");
		//-------------------------------------------------
		request.setAttribute("totalCount", alreadyList.size());
		//-------------------------------------------------
		for(int i=0;i<CertTaskInfoList1.size();i++){
			certTaskInfo=(CertTaskInfo) CertTaskInfoList1.get(i);
			String pkUnit=certTaskInfo.getPkUnit();
			for(int j=0;j<unitInfoList.size();j++){
				UnitInfo  unit=(UnitInfo )unitInfoList.get(j);
				if(unit.getPkUnit().toString().equals(pkUnit)){
					List temp=new ArrayList();
					temp.add(unit);
					certTaskInfo.setUnitInfoList(temp);
				}
			}
			haveNotList.add(certTaskInfo);
		}
		request.setAttribute("totalCount1",haveNotList.size() );
		request.setAttribute("alreadyList", alreadyList);
		request.setAttribute("havaNotList", haveNotList);
		List resultList = new ArrayList();
		List unitList=dao.findAll("from UnitInfo");
		for(int i=0;i<unitList.size();i++){
			UnitInfo unitInfo=(UnitInfo)unitList.get(i);
			resultList.add(unitInfo.getUnitNo()+"，"+unitInfo.getUnitName()+"，"+Common.getPinYinHeadChar(unitInfo.getUnitName()));
		}
		String unitData = Java2JSON.list2jsonSuggest(resultList);
		request.setAttribute("unitData",unitData);
		return SUCCESS;
	}
	public String listCensorQuery() throws Exception{
		SimpleDateFormat sf= new SimpleDateFormat("yyyy-MM-dd");
		ArrayList<CertTaskInfo> alreadyList=new ArrayList<CertTaskInfo>();
		ArrayList<CertTaskInfo> haveNotList=new ArrayList<CertTaskInfo>();
		List dataList=dao.findAll("from UnitInfo where 1=1 order by pkUnit");
		for(int i=0;i<dataList.size();i++){
			UnitInfo unitInfo = (UnitInfo) dataList.get(i); 
			String pkUnit = unitInfo.getPkUnit()+"";
			Date cenSorDate=unitInfo.getCensorDate();
			if(cenSorDate == null || DateUtils.addMonths(cenSorDate, 54).before(new Date())){//距离最后一次超过四年半
				List checkList =dao.findAll("from CertTaskInfo where  certTaskId ='"+unitInfo.getCensorFirst()+"'");
				if(checkList.size()>0){
					certTaskInfo=(CertTaskInfo)checkList.get(0);
					if(certTaskInfo.getPassedTime() != cenSorDate){
						CertTaskInfo certTast = new CertTaskInfo();
						certTast.setCertStatus("012001");
						certTast.setCertType("013001");
						certTast.setPkUnit(pkUnit);
						certTast.setConfidentialityLevelQualifications(unitInfo.getCensorLevel());
						dao.save(certTast);
					}
				}else{
					List certInfoList=dao.findAll("from CertTaskInfo where certType='013001' and certStatus in ('012001','012002') and pkUnit= '"+pkUnit+"'");
					if(certInfoList.size() == 0){
						CertTaskInfo certTast = new CertTaskInfo();
						certTast.setCertStatus("012001");
						certTast.setCertType("013001");
						certTast.setPkUnit(pkUnit);
						certTast.setConfidentialityLevelQualifications(unitInfo.getCensorLevel());
						dao.save(certTast);
					}
								
				}
			}else if(DateUtils.addMonths(cenSorDate,42).before(new Date()) || DateUtils.addMonths(cenSorDate,18).before(new Date())){
					List certInfoList=dao.findAll("from CertTaskInfo where certType='013002' and certStatus in ('012001','012002') and pkUnit= '"+pkUnit+"'");
					if(certInfoList.size() == 0){
						CertTaskInfo certTast = new CertTaskInfo();
						certTast.setCertStatus("012001");
						certTast.setCertType("013002");
						certTast.setPkUnit(pkUnit);
						certTast.setConfidentialityLevelQualifications(unitInfo.getCensorLevel());
						dao.save(certTast);	
				}
			}
		}
		List CertTaskInfoList=dao.findAll("from CertTaskInfo where certStatus in ('012003','012006') and certType = '013001' order by confidentialityLevelQualifications,passedTime desc");
		for(int i=0;i<CertTaskInfoList.size();i++){
			certTaskInfo=(CertTaskInfo) CertTaskInfoList.get(i);
			String pkUnit=certTaskInfo.getPkUnit();
			List unitInfoList=dao.findAll("from UnitInfo where pkUnit='"+pkUnit+"'");
			certTaskInfo.setUnitInfoList(unitInfoList);
			alreadyList.add(certTaskInfo);
		}
		List CertTaskInfoList1=dao.findAll("from CertTaskInfo where  certStatus in ('012001','012002')  and certType = '013001' order by certStatus desc");
		for(int i=0;i<CertTaskInfoList1.size();i++){
			certTaskInfo=(CertTaskInfo) CertTaskInfoList1.get(i);
			String pkUnit=certTaskInfo.getPkUnit();
			List unitInfoList=dao.findAll("from UnitInfo where pkUnit='"+pkUnit+"'");
			certTaskInfo.setUnitInfoList(unitInfoList);
			haveNotList.add(certTaskInfo);
		}
		request.setAttribute("alreadyList", alreadyList);
		request.setAttribute("havaNotList", haveNotList);
		String unitName=request.getParameter("unitName");
		request.setAttribute("unitName", unitName);
		List resultList = new ArrayList();
		List unitList=dao.findAll("from UnitInfo");
		for(int i=0;i<unitList.size();i++){
			UnitInfo unitInfo=(UnitInfo)unitList.get(i);
			resultList.add(unitInfo.getUnitNo()+"，"+unitInfo.getUnitName()+"，"+Common.getPinYinHeadChar(unitInfo.getUnitName()));
		}
		String unitData = Java2JSON.list2jsonSuggest(resultList);
		request.setAttribute("unitData",unitData);
		return SUCCESS;
	}
	public String listReviewQuery() throws Exception{
		SimpleDateFormat sf= new SimpleDateFormat("yyyy-MM-dd");
		ArrayList<CertTaskInfo> alreadyList=new ArrayList<CertTaskInfo>();
		ArrayList<CertTaskInfo> haveNotList=new ArrayList<CertTaskInfo>();
		List dataList=dao.findAll("from UnitInfo where 1=1 order by pkUnit");
		for(int i=0;i<dataList.size();i++){
			UnitInfo unitInfo = (UnitInfo) dataList.get(i); 
			String pkUnit = unitInfo.getPkUnit()+"";
			Date cenSorDate=unitInfo.getCensorDate();
			if(cenSorDate == null || DateUtils.addMonths(cenSorDate, 54).before(new Date())){//距离最后一次超过四年半
				List checkList =dao.findAll("from CertTaskInfo where  certTaskId ='"+unitInfo.getCensorFirst()+"'");
				if(checkList.size()>0){
					certTaskInfo=(CertTaskInfo)checkList.get(0);
					if(certTaskInfo.getPassedTime() != cenSorDate){
						CertTaskInfo certTast = new CertTaskInfo();
						certTast.setCertStatus("012001");
						certTast.setCertType("013001");
						certTast.setPkUnit(pkUnit);
						certTast.setConfidentialityLevelQualifications(unitInfo.getCensorLevel());
						dao.save(certTast);
					}
				}else{
					List certInfoList=dao.findAll("from CertTaskInfo where certType='013001' and certStatus in ('012001','012002') and pkUnit= '"+pkUnit+"'");
					if(certInfoList.size() == 0){
						CertTaskInfo certTast = new CertTaskInfo();
						certTast.setCertStatus("012001");
						certTast.setCertType("013001");
						certTast.setPkUnit(pkUnit);
						certTast.setConfidentialityLevelQualifications(unitInfo.getCensorLevel());
						dao.save(certTast);
					}
								
				}
			}else if(DateUtils.addMonths(cenSorDate,42).before(new Date()) || DateUtils.addMonths(cenSorDate,18).before(new Date())){
					List certInfoList=dao.findAll("from CertTaskInfo where certType='013002' and certStatus in ('012001','012002') and pkUnit= '"+pkUnit+"'");
					if(certInfoList.size() == 0){
						CertTaskInfo certTast = new CertTaskInfo();
						certTast.setCertStatus("012001");
						certTast.setCertType("013002");
						certTast.setPkUnit(pkUnit);
						certTast.setConfidentialityLevelQualifications(unitInfo.getCensorLevel());
						dao.save(certTast);	
				}
			}
		}
		List CertTaskInfoList=dao.findAll("from CertTaskInfo where certStatus in ('012003','012006') and certType = '013002'  order by confidentialityLevelQualifications,passedTime desc");
		for(int i=0;i<CertTaskInfoList.size();i++){
			certTaskInfo=(CertTaskInfo) CertTaskInfoList.get(i);
			String pkUnit=certTaskInfo.getPkUnit();
			List unitInfoList=dao.findAll("from UnitInfo where pkUnit='"+pkUnit+"'");
			certTaskInfo.setUnitInfoList(unitInfoList);
			alreadyList.add(certTaskInfo);
		}
		List CertTaskInfoList1=dao.findAll("from CertTaskInfo where  certStatus in ('012001','012002')  and certType = '013002' order by certStatus desc");
		for(int i=0;i<CertTaskInfoList1.size();i++){
			certTaskInfo=(CertTaskInfo) CertTaskInfoList1.get(i);
			String pkUnit=certTaskInfo.getPkUnit();
			List unitInfoList=dao.findAll("from UnitInfo where pkUnit='"+pkUnit+"'");
			certTaskInfo.setUnitInfoList(unitInfoList);
			haveNotList.add(certTaskInfo);
		}
		request.setAttribute("alreadyList", alreadyList);
		request.setAttribute("havaNotList", haveNotList);
		String unitName=request.getParameter("unitName");
		request.setAttribute("unitName", unitName);
		List resultList = new ArrayList();
		List unitList=dao.findAll("from UnitInfo");
		for(int i=0;i<unitList.size();i++){
			UnitInfo unitInfo=(UnitInfo)unitList.get(i);
			resultList.add(unitInfo.getUnitNo()+"，"+unitInfo.getUnitName()+"，"+Common.getPinYinHeadChar(unitInfo.getUnitName()));
		}
		String unitData = Java2JSON.list2jsonSuggest(resultList);
		request.setAttribute("unitData",unitData);
		return SUCCESS;
	}
	
	public String addStartCensor() throws Exception{
		String certTaskId=request.getParameter("certTaskId");
		int data=dao.bulkUpdate("update CertTaskInfo set certStatus='012002' where certTaskId='"+certTaskId+"'");
		certTaskInfo=(CertTaskInfo) dao.findOne(CertTaskInfo.class, Integer.parseInt(certTaskId));
		dao.bulkUpdate("update UnitInfo set censorStatus='012002' where pkUnit='"+certTaskInfo.getPkUnit()+"'");
		request.setAttribute("data", data);
		return toResult();
	}
	
	public  String addEndCensor() throws Exception{
		String certTaskId=request.getParameter("certTaskId");
		int data=dao.bulkUpdate("update CertTaskInfo set certStatus='012006' where certTaskId='"+certTaskId+"'");
		certTaskInfo=(CertTaskInfo) dao.findOne(CertTaskInfo.class, Integer.parseInt(certTaskId));
		dao.bulkUpdate("update UnitInfo set censorStatus='012006' where pkUnit='"+certTaskInfo.getPkUnit()+"'");
		request.setAttribute("data", data);
		return toResult();	
	}
	
	public String upLoadFile() throws Exception{
		String certTaskId=request.getParameter("certTaskId");
		String fileType=request.getParameter("fileType");
		request.setAttribute("certTaskId", certTaskId);
		request.setAttribute("fileType", fileType);
		CertTaskScore certTaskScore = new CertTaskScore();
		String tag="";
		System.out.println("sdfsdoij"+certTaskId);
		if("img".equals(fileType)){
			String imgId = request.getParameter("imgId");
			String certId=request.getParameter("certId");
			String ruleId=request.getParameter("ruleId");
			certTaskScore=(CertTaskScore) dao.findFirst("from CertTaskScore where certTaskId = "+certTaskId+" and certId = "+certId+"and ruleId = "+ruleId+"");
		}else{
			certTaskInfo=(CertTaskInfo) dao.findOne(CertTaskInfo.class, Integer.parseInt(certTaskId));
		}
		
		if("plan".equals(fileType)){
			if(StringUtils.isNotEmpty(certTaskInfo.getPlanFileId())){
				uploadFile=(UploadFile) dao.findOne(UploadFile.class, certTaskInfo.getPlanFileId());
				tag="1";
			}
		}else if ("selfExam".equals(fileType)) {
			if(StringUtils.isNotEmpty(certTaskInfo.getSelfexamFileId())){
				uploadFile=(UploadFile) dao.findOne(UploadFile.class, certTaskInfo.getSelfexamFileId());
				tag="1";
			}
		}else if ("application".equals(fileType)) {
			if(StringUtils.isNotEmpty(certTaskInfo.getApplication())){
				uploadFile=(UploadFile) dao.findOne(UploadFile.class, certTaskInfo.getApplication());
				tag="1";
			}
		}
		else if ("review".equals(fileType)) {
			if(StringUtils.isNotEmpty(certTaskInfo.getReviewInfo())){
				uploadFile=(UploadFile) dao.findOne(UploadFile.class, certTaskInfo.getReviewInfo());
				tag="1";
			}
		}   else if (("otherFile".equals(fileType)) && 
	    	      (StringUtils.isNotEmpty(this.certTaskInfo.getOtherFile()))) {
  	      this.uploadFile = ((UploadFile)this.dao.findOne(UploadFile.class, this.certTaskInfo.getOtherFile()));
  	      tag = "1";
  	    } 
		else if (("checkFile1".equals(fileType)) && 
	    	      (StringUtils.isNotEmpty(this.certTaskInfo.getCheckFile1()))) {
    	      this.uploadFile = ((UploadFile)this.dao.findOne(UploadFile.class, this.certTaskInfo.getCheckFile1()));
    	      tag = "1";
    	}
  	    else if (("checkFile2".equals(fileType)) && 
  	    	      (StringUtils.isNotEmpty(this.certTaskInfo.getCheckFile2()))) {
    	  	      this.uploadFile = ((UploadFile)this.dao.findOne(UploadFile.class, this.certTaskInfo.getCheckFile2()));
    	  	      tag = "1";
    	  	    }
    	    else if("img".equals(fileType)){
  	    	String count=request.getParameter("count");
			String imgId = request.getParameter("imgId");
  	    	if(StringUtils.isNotEmpty(imgId)){
  				if(imgId != null){
  					String[] imgIds = imgId.split("\\|");
  					for(int i=0;i<imgIds.length;i++){
  						String num = imgIds[i].substring(imgIds[i].length()-1,imgIds[i].length());
  						String imgIdStr = imgIds[i].substring(0,imgIds[i].length()-1);
  						if(num.equals(count)){
  							uploadFile=(UploadFile) dao.findOne(UploadFile.class, imgIdStr);
  							if(uploadFile != null){
  								tag="1";
  							}
  						}
  					}
  				}
  	  	    }
  	    }
		 if (("checkFile1".equals(fileType)) && 
	    	      (StringUtils.isNotEmpty(this.certTaskInfo.getCheckFile1Status()))) {
			 request.setAttribute("fileStatus","1");
   	}
 	    else if (("checkFile2".equals(fileType)) && 
 	    	      (StringUtils.isNotEmpty(this.certTaskInfo.getCheckFile2Status()))) {
 	    	 request.setAttribute("fileStatus","1");
   	  	    }
		request.setAttribute("uploadFile",uploadFile);
		request.setAttribute("tag", tag);
		return SUCCESS;
	}
	
	public String saveUpLoadFile() throws Exception{
		String statusValue="";
		String certTaskId=request.getParameter("certTaskId");
		String fileType=request.getParameter("fileType");
		String fileStatus=request.getParameter("fileStatus");
		if(StringUtils.isNotEmpty(fileStatus)||doc!=null){
			statusValue="1";	
		}
		String fileId="";
		String str = "ok";
		if (doc!=null) {
			this.getUploadFileName().add(docFileName);
			this.getUpload().add(doc);
			this.getUploadContentType().add(docContentType);
			if("img".equals(fileType)){
				String certId=request.getParameter("certId");
				String ruleId=request.getParameter("ruleId");
				String belongId=certTaskId+"|"+certId+"|"+ruleId;
				ArrayList<UploadFile> list = this.upload("cert_task_score",belongId);
				dao.saveAll(list);
			}else{
				ArrayList<UploadFile> list = this.upload("cert_task_info",certTaskId);
				dao.saveAll(list);
			}
			List UploadFileList=dao.findAll("from UploadFile order by enterTime desc");
			UploadFile uploadFile=(UploadFile) UploadFileList.get(0);
			fileId=uploadFile.getFileId();
		}
		if("plan".equals(fileType)){
			dao.bulkUpdate("update CertTaskInfo set planFileId='"+fileId+"' where certTaskId='"+certTaskId+"'");	
		}else if ("selfExam".equals(fileType)) {
			dao.bulkUpdate("update CertTaskInfo set selfexamFileId='"+fileId+"' where certTaskId='"+certTaskId+"'");	
		}else if ("application".equals(fileType)) {
			dao.bulkUpdate("update CertTaskInfo set application='"+fileId+"',applicationStatus='"+statusValue+"' where certTaskId='"+certTaskId+"'");	
		}else if ("review".equals(fileType)) {
			dao.bulkUpdate("update CertTaskInfo set reviewInfo='"+fileId+"',reviewInfoStatus='"+statusValue+"' where certTaskId='"+certTaskId+"'");	
		}else if ("otherFile".equals(fileType)) {
			dao.bulkUpdate("update CertTaskInfo set otherFile='"+fileId+"',otherFileStatus='"+statusValue+"' where certTaskId='"+certTaskId+"'");	
		}
		else if("checkFile1".equals(fileType)) {
			dao.bulkUpdate("update CertTaskInfo set checkFile1='"+fileId+"',checkFile1Status='"+statusValue+"' where certTaskId='"+certTaskId+"'");	
		}else if("checkFile2".equals(fileType)) {
			dao.bulkUpdate("update CertTaskInfo set checkFile2='"+fileId+"',checkFile2Status='"+statusValue+"' where certTaskId='"+certTaskId+"'");	
		}
		else if ("img".equals(fileType)) {
			str=fileId;
		}
		CertTaskInfo cti = (CertTaskInfo)dao.findOne(CertTaskInfo.class, Integer.parseInt(certTaskId));
		if("013002".equals(cti.getCertType().trim())){
			Alert_GoUrl("保存成功！", "closeCurrent","复查任务","");
		}else{
			Alert_GoUrl("保存成功！", "closeCurrent","certificationProcess","");
		}
		
		return toResult();
	}
	
	public String ajaxDeleteFile() throws Exception{
		String id=request.getParameter("id");
		String certTaskId=request.getParameter("certTaskId");
		String fileType=request.getParameter("fileType");
		dao.bulkUpdate("delete from UploadFile where fileId='"+id+"'");
		if("plan".equals(fileType)){
			dao.bulkUpdate("update CertTaskInfo set planFileId='' where certTaskId='"+certTaskId+"'");	
		}else if ("selfExam".equals(fileType)) {
			dao.bulkUpdate("update CertTaskInfo set selfexamFileId='' where certTaskId='"+certTaskId+"'");	
		}else if ("application".equals(fileType)) {
			dao.bulkUpdate("update CertTaskInfo set application='',applicationStatus='' where certTaskId='"+certTaskId+"'");	
		}else if ("review".equals(fileType)) {
			dao.bulkUpdate("update CertTaskInfo set reviewInfo='',reviewInfoStatus='' where certTaskId='"+certTaskId+"'");	
		}else if ("otherFile".equals(fileType)) {
			dao.bulkUpdate("update CertTaskInfo set otherFile='',otherFileStatus='' where certTaskId='"+certTaskId+"'");	
		}else if ("img".equals(fileType)) {
			String count=request.getParameter("count");
			//String imgId = certTaskId.replace(id+count, "");
			String imgId = "";
			if(id!=""){
				String[] imgIds = certTaskId.split("\\|");
				for(int i=0;i<imgIds.length;i++){
					
					String imgIdStr = imgIds[i].substring(0,imgIds[i].length()-1);
					if(!id.equals(imgIdStr)){
						imgId += imgIds[i]+"|";
					}
				}
				if(imgId!=""){
					imgId=imgId.substring(0,imgId.length()-1);
				}
				dao.bulkUpdate("update CertTaskScore set imgId='"+imgId+"' where imgId = '"+certTaskId+"'");	
			}
		}
		return toResult();
	}
	
	public String listCensorStat() throws Exception{
		List certConfigList=dao.findAll("from CertConfig ");
		request.setAttribute("certConfigList",certConfigList);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sfd = new SimpleDateFormat("yyyy");
		String statDateFrom=sfd.format(new Date())+"-01-01";
		String statDateTo=sf.format(new Date());
		request.setAttribute("statDateFrom", statDateFrom);
		request.setAttribute("statDateTo", statDateTo);
		return SUCCESS;
	}
	
	public String listCensorChart() throws Exception{
		 String element=request.getParameter("element").trim();//下拉条件1
		 String result = request.getParameter("result").trim();//下拉条件2
		 String result1="";//下拉条件2
//			<option value="1">军工单位数量</option>
//			<option value="2" >审查认证次数</option>
//			<option value="3">认证得分情况</option>
//			<option value="4">认证扣分项分析</option>
		 if("1".equals(result)){
			 result1+="军工单位数量";
		 }else if("2".equals(result)){
			 result1+="审查认证次数";
		 }else if("3".equals(result)){
			 result1+="认证得分情况";
		 }else if("4".equals(result)){
			 result1+="认证扣分项分析";
		 }
		 String statDateFrom = request.getParameter("statDateFrom");
		 String statDateTo = request.getParameter("statDateTo");
		 //String certName=request.getParameter("certName");
			 String date = "";
			 if(StringUtils.isNotEmpty(statDateFrom)){
				 date = "and b.passedTime >='"+statDateFrom+"'";
				 request.setAttribute("statDateFrom", statDateFrom);
			 }
			 if(StringUtils.isNotEmpty(statDateTo)){
				 date += "and b.passedTime<='"+statDateTo+"'";
				 request.setAttribute("statDateTo", statDateTo);
			 }
			 String sql ="";
			 if("004".equals(element)){//审查认证等级;
				 sql = " a.licenseLevel";
			 }
			 if("009".equals(element)){//集团
				 sql=" a.unitGroup";
			 }
			 String hql ="";
			 String kindName ="";
			 String certStatus= "";
			 if("1".equals(result)){
				hql=" ,count(distinct a.pkUnit)";
			 }
			 if("2".equals(result)){
				hql=" ,count(*)";
				certStatus = " and b.certStatus in ('012003','012004','012005')";
			 }
			 if("3".equals(result)){
				hql=" ,round(avg(b.passedScore),1)";
			 }
			 DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			 if("4".equals(result)){
				List resultData =dao.findAll(" select a.ruleId,count(a.ruleId) from CertTaskInfo b,CertTaskScore a where a.certTaskId=b.certTaskId and b.certStatus in ('012003','012004','012005') "+date+" group by a.ruleId ");
				Number tmp=0;
				String a ="";
				for(int j= 0;j<resultData.size();j++){
					Object[] obj= (Object[])resultData.get(j);
					a ="第"+ obj[0].toString()+"条";
					tmp = (Number) obj[1];
					dataset.addValue(tmp, "数值", a);
				}
			 }else if ("year".equals(element)) {
				 List resultData =dao.findAll(" select date_format( a.censorDate , '%Y') "+hql+" from UnitInfo a,CertTaskInfo b where a.pkUnit=b.pkUnit  "+certStatus+" and a.censorDate not like 'null%' group by date_format( a.censorDate , '%Y') ");
				 Number tmp=0;
					String a ="";
					kindName="年度";
					for(int i= 0;i<resultData.size();i++){
						Object[] obj= (Object[])resultData.get(i);
						a = obj[0].toString();
						tmp = (Number) obj[1];
						dataset.addValue(tmp, "数值", a);
					}
				} else{
				 List elements =dao.findAll("from CodeDict where kindId = '"+element+"'");
				 CodeKind codeKind = (CodeKind) dao.findOne(CodeKind.class, element);
				 kindName = codeKind.getKindName();
				 List resultData =dao.findAll(" select "+sql+" "+hql+" from UnitInfo a,CertTaskInfo b where a.pkUnit=b.pkUnit "+date+" "+certStatus+" group by "+sql+" ");
				 for(int i=0;i<elements.size();i++  ){
					CodeDict codeDict = (CodeDict) elements.get(i);
					String codeId = codeDict.getCodeId();
					String codeName = codeDict.getCodeName();
					Number tmp=0;
					for(int j= 0;j<resultData.size();j++){
						Object[] obj= (Object[])resultData.get(j);
						String a = obj[0].toString();
						if(obj[0].toString().equals(codeId)){
							tmp = (Number) obj[1];
						}
					}
					dataset.addValue(tmp, "数值", codeName);
				}
			 }//("按"+kindName+"统计"+result+"图",kindName,"数量", dataset, PlotOrientation.VERTICAL, true,false, false)
				JFreeChart chart = ChartFactory.createBarChart3D("按"+kindName+"统计"+result1+"图", kindName, "数量", dataset, PlotOrientation.VERTICAL,true ,false ,false);
				TextTitle textTitle = chart.getTitle();
				textTitle.setFont(new Font("黑体",Font.BOLD,17));//头标题
				CategoryPlot plot=chart.getCategoryPlot();//获取图表区域对象
			    plot.setBackgroundPaint(Color.WHITE);//背景颜色   
			    plot.setForegroundAlpha(0.5f); //设置透明度
			    plot.setRangeGridlinePaint(Color.lightGray);//纵线的颜色   
			    plot.setDomainGridlinesVisible(true);
				CategoryAxis domainAxis=plot.getDomainAxis();//水平底部列表
				domainAxis.setLabelFont(new Font("黑体",Font.ROMAN_BASELINE,12));//水平底部标题
				domainAxis.setTickLabelFont(new Font("黑体",Font.PLAIN,12));//垂直标题
				ValueAxis rangeAxis=plot.getRangeAxis();//获取柱状
				rangeAxis.setLabelFont(new Font("宋体",Font.BOLD,12));
				chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 12));
				BarRenderer3D renderer = new BarRenderer3D();
				renderer.setSeriesPaint(0,new Color(0x436EEE)); //设置柱的颜色
				renderer.setMinimumBarLength(0.02); //最小柱宽      
				renderer.setMaximumBarWidth(0.05);  //最大柱宽
				renderer.setWallPaint(Color.lightGray); 
				renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());//柱上的数值
				renderer.setItemLabelFont(new Font("黑体",Font.BOLD,15)); 
				renderer.setItemLabelsVisible(true);
				renderer.setItemLabelPaint(Color.WHITE);
				if("009".equals(element)){
					domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
				 }
				plot.setRenderer(renderer);
			    ServletOutputStream rOut = response.getOutputStream();
				try {
					if("009".equals(element)){
						ChartUtilities.writeChartAsPNG(rOut, chart,800,450);
					}
					ChartUtilities.writeChartAsPNG(rOut, chart,1000, 400);
					rOut.flush();
				} catch (RuntimeException e){
					e.printStackTrace();
				} finally{
					rOut.close();
				}
		
		return SUCCESS;
	}
	
	public String addCensor() throws Exception{
		
		
		List resultList = new ArrayList();
		List unitList=dao.findAll("from UnitInfo");
		for(int i=0;i<unitList.size();i++){
			UnitInfo unitInfo=(UnitInfo)unitList.get(i);
			resultList.add(unitInfo.getUnitNo()+"，"+unitInfo.getUnitName()+"，"+Common.getPinYinHeadChar(unitInfo.getUnitName()));
		}
		String unitData = Java2JSON.list2jsonSuggest(resultList);
		String certTaskId=request.getParameter("certTaskId");
		
		if(!"".equals(certTaskId) && certTaskId!=null){
			CertTaskInfo certTaskInfo=(CertTaskInfo)dao.findFirst("from CertTaskInfo where certTaskId='"+certTaskId+"'");
			request.setAttribute("certType",certTaskInfo.getCertType());
			request.setAttribute("data19",certTaskInfo.getData19());
			request.setAttribute("data20",certTaskInfo.getData20());
			UnitInfo unitInfo=(UnitInfo)dao.findFirst("from UnitInfo where pkUnit='"+certTaskInfo.getPkUnit()+"'");
			request.setAttribute("censorLevel",unitInfo.getCensorLevel());
			request.setAttribute("unitName",unitInfo.getUnitName());
			request.setAttribute("certTaskId",certTaskId);
		}
		request.setAttribute("unitData",unitData);
		return SUCCESS;
	}
	
	public String saveCensor() throws Exception{
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		String unitNo=request.getParameter("unitNo");
		String certType=request.getParameter("certType");
		String censorLevel=request.getParameter("censorLevel");
		String data19=request.getParameter("data19");
		String data20=request.getParameter("data20");
		UnitInfo a = (UnitInfo) dao.findFirst("from UnitInfo where unitNo='"+unitNo+"'");
		if(StringUtils.isEmpty(a.getCensorLevel()) || "null".equals(a.getCensorLevel())){
			a.setCensorLevel(censorLevel);
			dao.update(a);
		}
		certTaskInfo = new CertTaskInfo();
		certTaskInfo.setCertStatus("012002");
		certTaskInfo.setCertType(certType);
		certTaskInfo.setPkUnit(a.getPkUnit().toString());
		certTaskInfo.setData19(data19);
		certTaskInfo.setData20(data20);
		certTaskInfo.setConfidentialityLevelQualifications(censorLevel);
		dao.save(certTaskInfo);
		if("013001".equals(certType)){
			Alert_GoUrl("保存成功！","closeCurrent","审查任务",null);
		}else{
			Alert_GoUrl("保存成功！","closeCurrent","复查任务",null);
		}
		
		return toResult();
	}
	
	public String addCertScore() throws Exception{
		String certTaskId=request.getParameter("certTaskId");
		certTaskInfo=(CertTaskInfo) dao.findOne(CertTaskInfo.class, Integer.parseInt(certTaskId));
		request.setAttribute("certTaskInfo", certTaskInfo);
		return SUCCESS;
	}
	
	public String saveCertScore() throws Exception{
		String certTaskId=request.getParameter("certTaskId");
		String numPeople=request.getParameter("numPeople");
		String highestScore=request.getParameter("highestScore");
		String average=request.getParameter("average");
		dao.bulkUpdate("update CertTaskInfo set numPeople='"+numPeople+"',highestScore='"+highestScore+"',average='"+average+"' where certTaskId='"+certTaskId+"' ");
		this.Alert_GoUrl("密码修改成功", "closeCurrent", "审查任务", "");  
		return toResult();
	}
	/**
	 * 审查认证流程 生成试卷
	 * @return
	 * @throws Exception
	 */
	public String processAddPaper()throws Exception{
		String certTaskId=request.getParameter("certTaskId");
		request.setAttribute("certTaskId", certTaskId);
		CommonDAO commonDAO=new CommonDAO(dao);
		int paperNo=commonDAO.getNewTableId("PaperInfo", "paperNo");
//		PaperInfo paperInfo=new PaperInfo();
//		paperInfo.setPaperNo(paperNo);
//		paperInfo.setCreatedDate(new Date());
//		paperInfo.setKnowledgeCountFrom("20");
//		paperInfo.setKnowledgeCountTo("25");
//		paperInfo.setKnowledgeQuestionCount("2");
//		request.setAttribute("paperInfo",paperInfo);
//		
//		List<PaperScore> paperScoreList=new ArrayList<PaperScore>();
//		PaperScore paperScore=new PaperScore();
//		paperScore.setQuestionType("01");
//		paperScore.setQuestionScore((long)2);
//		paperScore.setQuestionCount((long)10);  
//		paperScore.setCheckValue("1");
//		paperScoreList.add(paperScore);
//		
//		paperScore=new PaperScore();
//		paperScore.setQuestionType("02");
//		paperScore.setQuestionScore((long)1);
//		paperScore.setQuestionCount((long)10);
//		paperScore.setCheckValue("1");
//		paperScoreList.add(paperScore);
//		
//		paperScore=new PaperScore();
//		paperScore.setQuestionType("03");
//		paperScore.setQuestionScore((long)2);
//		paperScore.setQuestionCount((long)10);
//		paperScore.setCheckValue("1");
//		paperScoreList.add(paperScore);
//		
//		paperScore=new PaperScore();
//		paperScore.setQuestionType("04");
//		paperScore.setQuestionScore((long)3);
//		paperScore.setQuestionCount((long)10);
//		paperScore.setCheckValue("1");
//		paperScoreList.add(paperScore);
//		
//		paperScore=new PaperScore();
//		paperScore.setQuestionType("05");
//		paperScore.setQuestionScore((long)10);
//		paperScore.setQuestionCount((long)2);
//		paperScore.setCheckValue("1");
//		paperScoreList.add(paperScore);
//		request.setAttribute("dataList", paperScoreList);
		return SUCCESS;
	}
	/**
	 * 审查认证流程 生成试卷 新规则
	 * @return
	 * @throws Exception
	 */
	public String processAddScore()throws Exception{
		String certTaskId=request.getParameter("certTaskId");
		request.setAttribute("certTaskId", certTaskId);
		String createdDate=request.getParameter("createdDateAddPaper");
		request.setAttribute("createdDateAddPaper", createdDate);
		String knowledgeCountFrom=request.getParameter("knowledgeCountFromAddPaper");
		request.setAttribute("knowledgeCountFromAddPaper", knowledgeCountFrom);
		String knowledgeCountTo=request.getParameter("knowledgeCountToAddPaper");
		request.setAttribute("knowledgeCountToAddPaper", knowledgeCountTo);
		String knowledgeQuestionCount=request.getParameter("knowledgeQuestionCountAddPaper");
		request.setAttribute("knowledgeQuestionCountAddPaper", knowledgeQuestionCount);
		String paperNo=request.getParameter("paperNoAddPaper");
		request.setAttribute("paperNoAddPaper", paperNo);
		String paperName=request.getParameter("paperNameAddPaper");
		request.setAttribute("paperNameAddPaper", paperName);
		String remark=request.getParameter("remarkAddPaper");
		request.setAttribute("remarkAddPaper", remark);
		String paperType=request.getParameter("paperTypeAddPaper");
		request.setAttribute("paperTypeAddPaper", paperType);
		String paperScore1=request.getParameter("paperScoreAddPaper1");
		System.out.println("paperScore1:::"+paperScore1);
		request.setAttribute("paperScoreAddPaper", paperScore1);
		String year=request.getParameter("year");
		request.setAttribute("year", year);
//		List<PaperScore> paperScoreList=new ArrayList<PaperScore>();
//		PaperScore paperScore=new PaperScore();
//		paperScore.setQuestionType("01");
//		paperScore.setQuestionScore((long)2);
//		paperScore.setQuestionCount((long)10);  
//		paperScore.setCheckValue("1");
//		paperScoreList.add(paperScore);
//		
//		paperScore=new PaperScore();
//		paperScore.setQuestionType("02");
//		paperScore.setQuestionScore((long)1);
//		paperScore.setQuestionCount((long)10);
//		paperScore.setCheckValue("1");
//		paperScoreList.add(paperScore);
//		
//		paperScore=new PaperScore();
//		paperScore.setQuestionType("03");
//		paperScore.setQuestionScore((long)2);
//		paperScore.setQuestionCount((long)10);
//		paperScore.setCheckValue("1");
//		paperScoreList.add(paperScore);
//		
//		paperScore=new PaperScore();
//		paperScore.setQuestionType("04");
//		paperScore.setQuestionScore((long)3);
//		paperScore.setQuestionCount((long)10);
//		paperScore.setCheckValue("1");
//		paperScoreList.add(paperScore);
//		
//		paperScore=new PaperScore();
//		paperScore.setQuestionType("05");
//		paperScore.setQuestionScore((long)10);
//		paperScore.setQuestionCount((long)2);
//		paperScore.setCheckValue("1");
//		paperScoreList.add(paperScore);
//		request.setAttribute("dataList", paperScoreList);
		return SUCCESS;
	}
	/**
	 * 审查认证流程 生成试卷 新规则 保存
	 * @return
	 * @throws Exception
	 */
	public String saveProcessSaveScore()throws Exception{
		//添加试卷
		String certTaskId=request.getParameter("certTaskId");
		request.setAttribute("certTaskId", certTaskId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String createdDate=request.getParameter("createdDateAddPaper");
		System.out.println("createdDate"+createdDate);
		String knowledgeCountFrom2=request.getParameter("knowledgeCountFromAddPaper");
		String knowledgeCountTo=request.getParameter("knowledgeCountToAddPaper");
		String knowledgeQuestionCount=request.getParameter("knowledgeQuestionCountAddPaper");
		String paperNo=request.getParameter("paperNoAddPaper");
		String paperName=request.getParameter("paperNameAddPaper");
		String remark=request.getParameter("remarkAddPaper");
		String paperType=request.getParameter("paperTypeAddPaper");
		String paperScore1=request.getParameter("paperScoreAddPaper");
		System.out.println("paperScore1:"+paperScore1);
		SessionBean sessionbean = SessionBean.getSessionBean(request);
//		PaperInfo paperInfo=new PaperInfo();
//		paperInfo.setCreatedDate(sdf.parse(createdDate));
//		paperInfo.setKnowledgeCountFrom(knowledgeCountFrom2);
//		paperInfo.setKnowledgeCountTo(knowledgeCountTo);
//		paperInfo.setKnowledgeQuestionCount(knowledgeQuestionCount);
//		paperInfo.setPaperNo(Integer.parseInt(paperNo));
//		paperInfo.setPaperName(paperName);
//		paperInfo.setRemark(remark);
//		paperInfo.setPaperType(paperType);
//		paperInfo.setPaperScore(Integer.parseInt(paperScore1));
//		paperInfo.setCreatedBy(sessionbean.getUserName());
//		dao.save(paperInfo);
		//设置分数
//		paperInfo=(PaperInfo)dao.findFirst("from PaperInfo where paperNo='"+paperNo+"'");
//		String pkPaperInfo = paperInfo.getPkPaperInfo();
		String checkValue=request.getParameter("checkValue");
		String[] questionType2=request.getParameterValues("questionType");
		String[] questionScore=request.getParameterValues("questionScore");
		String[] questionCount=request.getParameterValues("questionCount");
		String resultAll = request.getParameter("resultAll");
//		List<PaperScore> paperScoreList=new ArrayList<PaperScore>();
		String checkValueStr="";
		for(int i=0;i<questionType2.length;i++){
				if(!StringUtils.contains(checkValue, questionType2[i])){
					checkValueStr="0";
				}else{
					checkValueStr="1";
				}
//				PaperScore o=new PaperScore();
//				o.setPaperInfo(paperInfo);
//				o.setPkPaperInfo(pkPaperInfo);
//				o.setQuestionCount(Long.valueOf(questionCount[i]));
//				o.setQuestionScore(Long.valueOf(questionScore[i]));
//				o.setQuestionType(questionType2[i]);
//				o.setCheckValue(checkValueStr);
//				paperScoreList.add(o);
		}
//		dao.saveAll(paperScoreList);
//		paperInfo=(PaperInfo) dao.findOne(PaperInfo.class, pkPaperInfo);
//		paperInfo.setPaperScore(Integer.valueOf(resultAll));
//		dao.update(paperInfo);
//		//初始题目
		String year=request.getParameter("year");
		request.setAttribute("year", year);
		
//		List<QuestionInfo> resultList = new ArrayList<QuestionInfo>();
//		//最后保存题目列表
//		ArrayList<ParaUnit> paralist = new ArrayList<ParaUnit>();
//		paralist.add(new ParaUnit("pkPaperInfo",pkPaperInfo, ParaUnit.EQ));
//		paralist.add(new ParaUnit("questionType",ParaUnit.ASC, ParaUnit.ORDER));
//		paralist.add(new ParaUnit("checkValue","1",ParaUnit.EQ));
//		List<PaperScore> paperScorelist = null;
//		try {
//			paperScorelist = dao.criteriaQuery(PaperScore.class, paralist);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}//查询试卷题目类型及分值
//		int knowledgeCountFrom = Integer.parseInt(paperInfo.getKnowledgeCountFrom());//知识点数起
//		//int knowledgeCountTo = Integer.parseInt(paperInfo.getKnowledgeCountTo());//知识点数止
//		int maxCount = Integer.parseInt(paperInfo.getKnowledgeQuestionCount());//最大重复数
//		if(!paperScorelist.isEmpty()){
//			String conditionSQL;
//			if("4".equals(paperInfo.getPaperType()))//审查+技术需要or操作
//				conditionSQL = "(paperType = 2 or paperType = 3)";
//			else
//				conditionSQL = "paperType = "+paperInfo.getPaperType();
//			//限制试卷类型
//			String addSQL = "questionType = "+paperScorelist.get(0).getQuestionType();
//			for(int j=1;j<paperScorelist.size();j++)
//				addSQL +=(" or questionType = "+paperScorelist.get(j).getQuestionType());
//			conditionSQL += (" and ("+addSQL+")");
//			//限制题目类型
//			if(year!=null && !"".equals(year)){
//				Calendar cal = Calendar.getInstance();
//				paperInfo.getCreatedDate();
//					cal.setTime(paperInfo.getCreatedDate());
//					cal.add(Calendar.YEAR,-Integer.parseInt(year));
//					System.out.println("year:"+cal.getTime());
//					conditionSQL += (" and pkQuestionInfo not in " +
//							"(select pkQuestionInfo from PaperQuestionInfo where pkPaperInfo in " +
//							"(select pkPaperInfo from PaperInfo where createdDate>'"+cal.getTime()+"'))");
//					
//			}
//			List<QuestionInfo> questionAllList = new ArrayList<QuestionInfo>();
//			try {
//				questionAllList = dao.findAll("from QuestionInfo where "+conditionSQL+" order by questionType ,codeId");
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			List<ArrayList<QuestionListIndex>> questionListIndex = new ArrayList<ArrayList<QuestionListIndex>>();
//			//保存题型，知识点的索引
//			long[] eachTypeCount = new long[paperScorelist.size()];
//			long questionTotalNum = 0;
//			HashSet<String> knowledgeAllSet = new HashSet<String>();
//			{
//				int i = 0;
//				for(int j=0;j<paperScorelist.size();j++){
//					eachTypeCount[j] = paperScorelist.get(j).getQuestionCount();
//					questionTotalNum += eachTypeCount[j];
//					String questionType = paperScorelist.get(j).getQuestionType();
//					String codeId = questionAllList.get(i).getCodeId();
//					ArrayList<QuestionListIndex> tempList = new ArrayList<QuestionListIndex>();
//					tempList.add(new QuestionListIndex(Integer.parseInt(codeId),i));
//					for(;i<questionAllList.size();i++){
//						QuestionInfo qi = questionAllList.get(i);
//						if(!qi.getCodeId().equals(codeId)){
//							tempList.get(tempList.size()-1).setTo(i-1);
//							if(!qi.getQuestionType().equals(questionType))
//								break;
//							tempList.add(new QuestionListIndex(Integer.parseInt(qi.getCodeId()),i));
//							knowledgeAllSet.add(qi.getCodeId());
//						}
//						questionType = qi.getQuestionType();
//						codeId = qi.getCodeId();							
//					}
//					questionListIndex.add(tempList);
//				}
//				questionListIndex.get(questionListIndex.size()-1).get(questionListIndex.get(questionListIndex.size()-1).size()-1).setTo(questionAllList.size()-1);
//			}
//			//将QuestionListIndex类按线性表形式在questionListIndex里按题型知识点划分 生成知识点Map curKnowledge
//			int resultIndex[] = new int[(int)questionTotalNum];
//			if(questionTotalNum<knowledgeCountFrom){
//				request.setAttribute("exception","共需筛选"+questionTotalNum+"道题，此条件覆盖"+knowledgeCountFrom+"个知识点！请增大题目数或减小知识点考查数！");
//			    this.Alert_GoUrl("共需筛选"+questionTotalNum+"道题，此条件覆盖"+knowledgeCountFrom+"个知识点！请增大题目数或减小知识点考查数！", "", "", "");
//				return toErrorMessage();
//			}
//			int maxTotalNum=0;
//			for(int i=0;i<questionListIndex.size();i++){
//				if(questionListIndex.get(i).size()*maxCount>maxTotalNum)
//					maxTotalNum=questionListIndex.get(i).size()*maxCount;
//				if(eachTypeCount[i]>questionListIndex.get(i).size()*maxCount){
//					request.setAttribute("exception","共需筛选"+eachTypeCount[i]+"道"+getCodeDict(paperScorelist.get(i).getQuestionType())+"，此条件下可筛选"+questionListIndex.get(i).size()*maxCount+"道题！请减小其题目数或增大知识点最大重复数！");
//					this.Alert_GoUrl("共需筛选"+eachTypeCount[i]+"道"+getCodeDict(paperScorelist.get(i).getQuestionType())+"，此条件下可筛选"+questionListIndex.get(i).size()*maxCount+"道题！请减小其题目数或增大知识点最大重复数！", "", "", "");
//					return toErrorMessage();
//				}
//			}
//			if(questionTotalNum>maxTotalNum){
//				request.setAttribute("exception","共需筛选"+questionTotalNum+"道题，此条件下可筛选"+maxTotalNum+"道题！请减小题目数或增大知识点最大重复数！");
//				this.Alert_GoUrl("共需筛选"+questionTotalNum+"道题，此条件下可筛选"+maxTotalNum+"道题！请减小题目数或增大知识点最大重复数！", "", "", "");
//				return toErrorMessage();
//			}
//			//判断是否可选题，若不可选题则返回异常信息
//			for(int questionCurNum = 0;questionCurNum<questionTotalNum;questionCurNum++){
//				List<Boolean> isFull = new ArrayList<Boolean>();
//				List<QuestionListIndex> except = new ArrayList<QuestionListIndex>();
//				long[] curEachTypeCount = new long[paperScorelist.size()];
//				for(int i=0;i<questionCurNum;i++){
//					int typeIndex = 0;
//					while(resultIndex[i]>questionListIndex.get(typeIndex).get(questionListIndex.get(typeIndex).size()-1).getTo())
//						typeIndex++;
//					curEachTypeCount[typeIndex]++;
//				}
//				//得到目前的各类型题目数量
//				for(int i=0;i<curEachTypeCount.length;i++){
//					if(curEachTypeCount[i]==eachTypeCount[i]){
//						QuestionListIndex tempQLI = new QuestionListIndex(0,questionListIndex.get(i).get(0).getFrom());
//						tempQLI.setTo(questionListIndex.get(i).get(questionListIndex.get(i).size()-1).getTo());
//						except.add(tempQLI);
//						//except.addAll(questionListIndex.get(i));
//						isFull.add(true);
//					}
//					else
//						isFull.add(false);
//				}
//				//将题目数量已满的送入排除列表 随机数将不在这其中生成
//				HashMap<String, List<Integer>> curKnowledgeMap = new HashMap<String, List<Integer>>();
//				List<String> curKnowledgeList = new ArrayList<String>();
//				for(int i=0;i<questionCurNum;i++){
//					String codeId = questionAllList.get(resultIndex[i]).getCodeId();
//					QuestionListIndex tempQLI = new QuestionListIndex(Integer.parseInt(codeId),resultIndex[i]);
//					tempQLI.setTo(resultIndex[i]);
//					except.add(tempQLI);
//					List<Integer> tempKnowledgeList = curKnowledgeMap.get(codeId);
//					if(tempKnowledgeList==null){
//						tempKnowledgeList = new ArrayList<Integer>();
//						tempKnowledgeList.add(resultIndex[i]);
//						curKnowledgeList.add(codeId);
//					}
//					else
//						tempKnowledgeList.add(resultIndex[i]);
//					curKnowledgeMap.put(codeId, tempKnowledgeList);
//				}
//				int exceptTypeCount[] = new int[eachTypeCount.length];
//				//得到目前的题目按知识点的Map与其知识点列表
//				for(String s : curKnowledgeList){
//					List<Integer> l = curKnowledgeMap.get(s);
//					if(l.size()>=maxCount){
//						int codeId = Integer.parseInt(s);
//						for(int i=0;i<isFull.size();i++){
//							if(!isFull.get(i)){
//								List<QuestionListIndex> tempIndexList = questionListIndex.get(i);
//								for(int j=0;j<tempIndexList.size();j++)
//									if(tempIndexList.get(j).getCodeId()==codeId){
//										except.add(tempIndexList.get(j));
//										exceptTypeCount[i]++;
//										break;
//									}
//							}
//						}
//					}
//					else{
//						for(int i=0;i<l.size();i++){
//							int temp = l.get(i);
//							for(int j=0;j<exceptTypeCount.length;j++){
//								if(temp>=questionListIndex.get(j).get(0).getFrom() && temp<=questionListIndex.get(j).get(questionListIndex.get(j).size()-1).getTo()){
//									if(!isFull.get(j)){
//										exceptTypeCount[j]++;
//										break;
//									}
//								}
//							}
//						}
//					}
//				}
//				boolean isReplaced = false;
//				for(int i=0;i<questionListIndex.size();i++){
//					int size = questionListIndex.get(i).size();
//					if(!isFull.get(i) && size==exceptTypeCount[i]){							
//						try {
//							int replaceIndex = 0;
//							int codeId;
//							List<QuestionListIndex> exceptReplace;
//							while(true){
//								exceptReplace = new ArrayList<QuestionListIndex>();
//								boolean isReplace = false;
//									codeId = questionListIndex.get(i).get(randIndexInScope(size-1, new int[0])).getCodeId();
//								List<Integer> tempKnowledgeList = curKnowledgeMap.get("0"+codeId);
//								int j=0;
//								if(tempKnowledgeList==null){
//									System.out.println("error");
//								}
//								for(;j<tempKnowledgeList.size();j++){
//									if(tempKnowledgeList.get(j)<questionListIndex.get(i).get(0).getFrom() || tempKnowledgeList.get(j)>questionListIndex.get(i).get(questionListIndex.get(i).size()-1).getTo()){
//										replaceIndex = tempKnowledgeList.get(j);
//										isReplace = true;
//									}
//									else{
//										QuestionListIndex qli = new QuestionListIndex(codeId,tempKnowledgeList.get(j));
//										qli.setTo(tempKnowledgeList.get(j));
//										exceptReplace.add(qli);
//									}
//								}
//								if(isReplace && exceptReplace.size()<tempKnowledgeList.size())
//									break;
//							}
//							QuestionListIndex qli = null;
//							for(int j=0;j<questionListIndex.get(i).size();j++){
//								qli=questionListIndex.get(i).get(j);
//								if(qli.getCodeId()==codeId)
//									break;
//							}
//							if(qli.getFrom()!=0){
//								QuestionListIndex qliBefore = new QuestionListIndex(qli.getCodeId(),0);
//								qliBefore.setTo(qli.getFrom()-1);
//								exceptReplace.add(qliBefore);
//							}
//							if(qli.getTo()!=questionAllList.size()-1){
//								QuestionListIndex qliAfter = new QuestionListIndex(qli.getCodeId(),qli.getTo()+1);
//								qliAfter.setTo(questionAllList.size()-1);
//								exceptReplace.add(qliAfter);
//							}
//							for(int j=0;!isReplaced;j++){
//								if(resultIndex[j]==replaceIndex){
//									resultIndex[j]=randIndexInScope(questionAllList.size(), questionListIndexToExceptIndex(exceptReplace));
//									isReplaced = true;
//								}
//							}							
//						} catch (Exception e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//							request.setAttribute("exception","系统内部题库调度异常，请重试或修改设置！");
//							reListPaperQuestion = 0;
//							this.Alert_GoUrl("系统内部题库调度异常，请重试或修改设置！", "", "", "");
//							return toErrorMessage();
//						}
//						break;
//					}
//				}
//				if(isReplaced){
//					questionCurNum--;
//					continue;
//				}//若替换了则重新生成排除列表
//				//若某题型所有知识点不可选 踢出其它题型的题 并直接用原题号分量生成新题替换之
//				if(curKnowledgeList.size()<knowledgeCountFrom){
//					if(knowledgeCountFrom - curKnowledgeList.size() == questionTotalNum - questionCurNum){
//						for(String s : curKnowledgeList){
//							int codeId = Integer.parseInt(s);
//							for(int i=0;i<isFull.size();i++){
//								if(!isFull.get(i)){
//									List<QuestionListIndex> tempIndexList = questionListIndex.get(i);
//									for(int j=0;j<tempIndexList.size();j++)
//										if(tempIndexList.get(j).getCodeId()==codeId){
//											except.add(tempIndexList.get(j));
//											break;
//										}
//								}
//							}
//						}
//					}
//					//限制知识点数下限
//				}
//				else{
//					
//				}
//				//算法：生成不可选的题目List<QuestionListIndex> except
//				int index;
//				int[] x = questionListIndexToExceptIndex(except);
////				if(x.length==2 && x[1]-x[0]+1==questionAllList.size()){
////					questionCurNum--;
////					continue;
////				}
//				try {
//					index = randIndexInScope(questionAllList.size(),questionListIndexToExceptIndex(except));
//					resultIndex[questionCurNum] = index;
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					
//					request.setAttribute("exception","系统内部题库调度异常，请重试或修改设置！");
//					reListPaperQuestion = 0;
//					this.Alert_GoUrl("系统内部题库调度异常，请重试或修改设置！", "", "", "");
//					return toErrorMessage();
//			
//				}
//			}
//			Arrays.sort(resultIndex);
//			HashMap<String, List<Integer>> curKnowledgeMap = new HashMap<String, List<Integer>>();
//			for(int i=0;i<resultIndex.length;i++){
//				resultList.add(questionAllList.get(resultIndex[i]));
//				String codeId = questionAllList.get(resultIndex[i]).getCodeId();
//				List<Integer> tempKnowledgeList = curKnowledgeMap.get(codeId);
//				if(tempKnowledgeList==null){
//					tempKnowledgeList = new ArrayList<Integer>();
//					tempKnowledgeList.add(resultIndex[i]);
//				}
//				else
//					tempKnowledgeList.add(resultIndex[i]);
//				curKnowledgeMap.put(codeId, tempKnowledgeList);
//			}
//			int count = curKnowledgeMap.size();
//			int markIndex = 0;
//			for(int i=0;i<eachTypeCount.length;i++){
//				QuestionInfo question=new QuestionInfo();//增加题目标识
//				for(int j=0;j<eachTypeCount[i];j++){
//					if(markIndex+i+j>=resultList.size()){
//						System.out.println("");
//					}
//					QuestionInfo qi = resultList.get(markIndex+i+j);
//					qi.setQuestionNo((long)j+1);
//				}
//				if("03".equals(resultList.get(markIndex+i).getQuestionType())||"04".equals(resultList.get(markIndex+i).getQuestionType()))
//					for(int j=0;j<eachTypeCount[i];j++){
//						List questionDetailList = null;
//						try{
//							questionDetailList = dao.findAll("from QuestionDetailInfo where pkQuestionInfo='"+resultList.get(markIndex+i+j).getPkQuestionInfo()+"' order by questionSeq");
//						}catch(Exception e){
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						String questionDetail="";
//						for (int m = 0; m < questionDetailList.size(); m++) {
//							QuestionDetailInfo qdi = (QuestionDetailInfo)questionDetailList.get(m);
//							questionDetail+=qdi.getQuestionSeq()+"、"+qdi.getQuestionDetail()+" ";
//						}
//						resultList.get(markIndex+i+j).setQuestionDetail(questionDetail);
//						resultList.get(markIndex+i+j).setQuestionDetailList(questionDetailList);
//					}
//				question.setQuestion(getQuestionTitle(i+1)+"、"+getCodeDict(paperScorelist.get(i).getQuestionType())+"(共计"+paperScorelist.get(i).getQuestionCount()+"题)");
//				question.setQuestionTitle("1");
//				resultList.add(markIndex+i, question);
//				markIndex+=eachTypeCount[i];
//			}
//			QuestionInfo qi = new QuestionInfo();
//			qi.setQuestion("*******************************************共"+count+"种知识点*******************************************");
//			qi.setQuestionTitle("1");
//			resultList.add(qi);
//			System.out.print(count);
//			//保存最后每个题型的题目的结果		
//		}
//		try {
//			dao.bulkUpdate("delete from PaperQuestionInfo where pkPaperInfo='"+pkPaperInfo+"'");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}//删除之前存储的题目，只有rebuild=1时有用
//		List<PaperQuestionInfo> paperQuestionList=new ArrayList<PaperQuestionInfo>();
//		for(QuestionInfo question:resultList){
//			if(!"1".equals(question.getQuestionTitle())){
//				PaperQuestionInfo paperQuestionInfo=new PaperQuestionInfo();
//				paperQuestionInfo.setPkPaperInfo(pkPaperInfo);
//				paperQuestionInfo.setPkQuestionInfo(question.getPkQuestionInfo());
//				paperQuestionInfo.setQuestionNo(question.getQuestionNo());
//				paperQuestionInfo.setQuestionType(question.getQuestionType());
//				paperQuestionList.add(paperQuestionInfo);
//			}
//		}
//		dao.saveAll(paperQuestionList);
//		
		Alert_GoUrl("添加规则成功！", "closeCurrent","审查认证流程","");
		return toResult();
	}
	public String certTaskInfoImport() throws Exception{
		List resultList = new ArrayList();
		List unitList=dao.findAll("from UnitInfo");
		
		for(int i=0;i<unitList.size();i++){
			UnitInfo unitInfo=(UnitInfo)unitList.get(i);
			resultList.add(unitInfo.getUnitNo()+"，"+unitInfo.getUnitName()+"，"+Common.getPinYinHeadChar(unitInfo.getUnitName()));
			
		}
		String unitData = Java2JSON.list2jsonSuggest(resultList);
		request.setAttribute("unitData",unitData);
		return SUCCESS;
	}
	public String queryWordToCertTask() throws Exception{
		String unitNo=request.getParameter("unitNo");
		String certType=request.getParameter("certType");
		String data="<table width='100%' class='b' id='tab'>"+
				"<tr>" +
				"<th  >选择</th>"+
				"<th  >单位名称</th>" +
				"<th  >认证状态</th>" +
				"<th  >认证通过时间</th>"+
				"<th  >认证通过分数</th>"+
				"</tr>";
		if (unitNo!=null) {
			List dataList = (List) dao.findAll("from CertTaskInfo where pkUnit in(select pkUnit from UnitInfo where unitNo='"+unitNo+"' ) and  certType='"+certType+"' and passedTime is null order by passedTime desc ");
			for (int i = 0; i < dataList.size(); i++) {
				CertTaskInfo certTaskInfo=(CertTaskInfo) dataList.get(i);
				String unitName=dao.findFirst("select unitName from UnitInfo where pkUnit='"+certTaskInfo.getPkUnit()+"'").toString();
				String typeName=dao.findFirst("select codeName from CodeDict where codeId='"+certTaskInfo.getCertType()+"'").toString();
				String statusName=dao.findFirst("select codeName from CodeDict where codeId='"+certTaskInfo.getCertStatus()+"'").toString();
				if ("012004".equals(certTaskInfo.getCertStatus()) ||"012005".equals(certTaskInfo.getCertStatus())) {
				data +="<tr>"+
 				"<td><input type='radio' name='radio' onclick='selRadio()' value='"+certTaskInfo.getCertTaskId()+"'></td>"+
 				"<td>"+unitName+"</td>"+
 				"<td>"+typeName+"通过</td>" +
 				"<c:if test=''"+certTaskInfo.getCertStatus()+"' != '012004' && '"+certTaskInfo.getCertStatus()+"' != '012005''><w:CodeIdDictOut value='"+certTaskInfo.getCertType()+"'></w:CodeIdDictOut><w:CodeIdDictOut value='"+certTaskInfo.getCertStatus()+"'></w:CodeIdDictOut></c:if></td>"+
 				"<td>"+certTaskInfo.getPassedTime()+"</td>"+
 				"<td>"+certTaskInfo.getPassedScore()+"</td>"+
 				"</tr>";	
				}else if (!"012004".equals(certTaskInfo.getCertStatus())&&!"012005".equals(certTaskInfo.getCertStatus())) {
				data +="<tr>"+
	 			"<td><input type='radio' name='radio' onclick='selRadio()' value='"+certTaskInfo.getCertTaskId()+"'></td>"+
	 			"<td>"+unitName+"</td>"+
	 			"<td>"+typeName+""+statusName+"</td>" +
	 			"<c:if test=''"+certTaskInfo.getCertStatus()+"' != '012004' && '"+certTaskInfo.getCertStatus()+"' != '012005''><w:CodeIdDictOut value='"+certTaskInfo.getCertType()+"'></w:CodeIdDictOut><w:CodeIdDictOut value='"+certTaskInfo.getCertStatus()+"'></w:CodeIdDictOut></c:if></td>"+
	 			"<td>"+certTaskInfo.getPassedTime()+"</td>"+
	 			"<td>"+certTaskInfo.getPassedScore()+"</td>"+
	 			"</tr>";
				}
			}
			
			data +="</table>";
		}
		System.out.println("*-*-*----*****-----"+data);
		request.setAttribute("data", data);
		return toData();
	}
	         
	public String wordToCertTask() throws Exception{  
		String certType=request.getParameter("certType");
		String unitName=request.getParameter("unitName");
		String certTaskId=request.getParameter("radio");
		if(StringUtils.isEmpty(certTaskId)){  
			Alert_GoUrl("请选择一次认证", "","","");
			return toErrorMessage();
		}else{
	     	String pkUnit=dao.findFirst("select pkUnit from UnitInfo where unitName = '"+unitName+"'").toString();
			if (doc!=null && !"".equals(doc)) {
				this.getUploadFileName().add(docFileName);
				this.getUpload().add(doc);
				this.getUploadContentType().add(docContentType);
				ArrayList<UploadFile> list = this.upload("word_to_cert_task",certTaskId);
				String sp=request.getRealPath("upload");
				String docName = sp+	list.get(0).getPutPath().substring(7);
		        /** 1. 读取WORD表格内容 */
		        HWPFDocument doc = null;
		        try {
		            doc = new HWPFDocument(new FileInputStream(docName));
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        String text = doc.getRange().text();
		        /** 2. 放置分隔符:将不可见字符使用空格(32)替换 */
		        System.out.println(text);
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
		 	        	for (int i = 0; i < textArray.length; i++) {
							System.out.println(i+":"+textArray[i]);
						}
		 	        	if(certTaskList.size()> 0){
		 	        		cti = (CertTaskInfo) certTaskList.get(0);
		 	        		cti.setConfidentialityLevelQualifications("004001");
		 	        		cti.setMemberName1(textArray[24]);//cti.setMemberName1(textArray[24]);
		 	        		cti.setMemberUnit1(textArray[25]);//cti.setMemberUnit1(textArray[25]);
		 	        		cti.setMemberPosition1(textArray[26]);//cti.setMemberPosition1(textArray[26]);
		 		        	cti.setMemberName2(textArray[29]);
		 		        	cti.setMemberUnit2(textArray[30]);
		 		        	cti.setMemberPosition2(textArray[31]);
		 		        	cti.setMemberName3(textArray[34]);
		 		        	cti.setMemberUnit3(textArray[35]);
		 		        	cti.setMemberPosition3(textArray[36]);
		 		        	cti.setMemberName4(textArray[39]);
		 		        	cti.setMemberUnit4(textArray[40]);
		 		        	cti.setMemberPosition4(textArray[41]);
		 		        	if (textArray[44].length()<100) {
		 		        		cti.setMemberName5(textArray[44]);
			 		        	cti.setMemberUnit5(textArray[45]);
			 		        	cti.setMemberPosition5(textArray[46]);
		 		        	}
		 		        	if (textArray[49].length()<100) {
		 		        		cti.setMemberName6(textArray[49]);
			 		        	cti.setMemberUnit6(textArray[50]);
			 		        	cti.setMemberPosition6(textArray[51]);
		 		        	}
		 		        	if (textArray[54].length()<100) {
		 		        		cti.setMemberName7(textArray[54]);
			 		        	cti.setMemberUnit7(textArray[55]);
			 		        	cti.setMemberPosition7(textArray[56]);
		 		        	}
		 		        	if (textArray[59].length()<100) {
		 		        		 cti.setMemberName8(textArray[59]);
		 		        		 cti.setMemberUnit8(textArray[60]);
		 		        		 cti.setMemberPosition8(textArray[61]);
		 		        	}
			 		        
			 		        if(textArray[64].length()<100){
			 		        	cti.setMemberName9(textArray[64]);
			 		        	cti.setMemberUnit9(textArray[65]);
			 		        	cti.setMemberPosition9(textArray[66]);
			 		        }
			 		        
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
		 		                				CertConfigRule ccr = (CertConfigRule)dao.findFirst("from CertConfigRule where certId='"+certId+"' and ruleNo='"+ruleNo+"'");
		 		                				CertTaskScore cts = new CertTaskScore(null, scoreRule, Integer.parseInt(scoreNumber),cti.getCertTaskId(), certId,ccr.getRuleId(),null,null);
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
		 		        	cti.setCertStatus("012002");
		 		        	cti.setSubmissionStatus("1");
		 		        	dao.update(cti);
		 		        	dao.bulkUpdate("update from UnitInfo set censorDate='"+passedTime+"',censorScore='"+passedScore+"',censorPassed='012003',censorLevel='004001',censorFirst='"+cti.getCertTaskId()+"' where pkUnit = '"+pkUnit+"'");
		 		        	Alert_GoUrl("导入成功！", "closeCurrent","审查任务","");
		 	        	}else{
		 	        		Alert_GoUrl("导入失败！", "closeCurrent","审查任务","");
		 	        		
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
	 		        	cti.setCertStatus("012002");
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
	 		                				CertConfigRule ccr = (CertConfigRule)dao.findFirst("from CertConfigRule where certId='"+certId+"' and ruleNo='"+ruleNo+"'");
	 		                				CertTaskScore cts = new CertTaskScore(null, scoreRule, Integer.parseInt(scoreNumber),cti.getCertTaskId(), certId,ccr.getRuleId(),null,null);
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
			        	Alert_GoUrl("导入成功！", "closeCurrent","审查任务","");
		        	}else{
		        		Alert_GoUrl("导入失败！", "closeCurrent","审查任务","");
		        	}		
		        }    
			} else {
				Alert_GoUrl("导入失败！", "closeCurrent","审查任务","");
			}		
			return toResult();
		}
	}
	
	public String listExportExcel() throws Exception{
		StringBuilder unitSql = new StringBuilder();
		StringBuilder certSql = new StringBuilder();
		SimpleDateFormat sf= new SimpleDateFormat("yyyy-MM-dd");
		unitSql.append("");
		String unitGroup=request.getParameter("unitGroup");
		if(StringUtils.isNotEmpty(unitGroup)){
			unitSql.append(" and unitGroup = '"+unitGroup+"'");
		}
	    request.setAttribute("unitGroup", unitGroup);
		String unitName=request.getParameter("unitName");
		if(StringUtils.isNotEmpty(unitName)){
			unitSql.append(" and unitName like '%"+unitName+"'");
		}
		request.setAttribute("unitName", unitName);
		certSql.append("");
		String applicationStatus=request.getParameter("applicationStatus");
		if("1".equals(applicationStatus)){
			certSql.append(" and applicationStatus ='1' ");
		}else if ("0".equals(applicationStatus)) {
			certSql.append(" and (applicationStatus is null or applicationStatus ='0') ");
		}
		request.setAttribute("applicationStatus", applicationStatus);
		String reviewInfoStatus=request.getParameter("reviewInfoStatus");
		if("1".equals(reviewInfoStatus)){
			certSql.append(" and reviewInfoStatus ='1' ");
		}else if ("0".equals(reviewInfoStatus)) {
			certSql.append(" and (reviewInfoStatus is null or reviewInfoStatus ='0') ");
		}
		request.setAttribute("reviewInfoStatus", reviewInfoStatus);
		String scoreStatus = request.getParameter("scoreStatus");
		if("1".equals(scoreStatus)){
			certSql.append(" and (numPeople is not null or highestScore is not null or average is not null )");
		}else if ("0".equals(scoreStatus)) {
			certSql.append(" and (numPeople is null && highestScore is null && average is null) ");
		}
		request.setAttribute("scoreStatus", scoreStatus);
		String submissionStatus=request.getParameter("submissionStatus");
		if("1".equals(submissionStatus)){
			certSql.append(" and submissionStatus ='1' ");
		}else if ("0".equals(submissionStatus)) {
			certSql.append(" and (submissionStatus is null or submissionStatus ='0') ");
		}
		request.setAttribute("submissionStatus", submissionStatus);
		List CertTaskInfoList=dao.findAll("from CertTaskInfo where certStatus = '012003' and pkUnit in (select pkUnit from UnitInfo where 1=1 "+unitSql+" ) "+certSql+" order by confidentialityLevelQualifications,passedTime desc");
		
		System.out.println("type:"+CertTaskInfoList);
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
		cell.setCellValue("认证状态");
		cell=row.createCell(1);
		cell.setCellValue("认证单位名称");
		cell=row.createCell(2);
		cell.setCellValue("认证等级");
		cell=row.createCell(3);
		cell.setCellValue("认证通过时间");
		cell=row.createCell(4);
		cell.setCellValue("通过分数");
		cell=row.createCell(5);
		cell.setCellValue("法定代表人");
		cell=row.createCell(6);
		cell.setCellValue("认证申请书");
		cell=row.createCell(7);
		cell.setCellValue("汇报材料");
		cell=row.createCell(8);
		cell.setCellValue("分数情况");
		cell=row.createCell(9);
		cell.setCellValue("认证意见书");
		cell=row.createCell(10);
		cell.setCellValue("整改计划"); 
		int rowNum=2;
		for(int i=0;i<CertTaskInfoList.size();i++){
			row=hssfsheet.createRow(rowNum);
			rowNum++;
			CertTaskInfo certTaskInfo=(CertTaskInfo) CertTaskInfoList.get(i);
			String pkUnit=certTaskInfo.getPkUnit();
			UnitInfo unitInfo=(UnitInfo) dao.findOne(UnitInfo.class, Integer.parseInt(pkUnit));
			cell=row.createCell(0);
			cell.setCellValue("认证通过");
			cell=row.createCell(1);
			cell.setCellValue(unitInfo.getUnitName());
			String censorLevel="一级";
//			if(StringUtils.isNotEmpty(unitInfo.getCensorLevel())){
//				censorLevel=getCodeName(unitInfo.getCensorLevel());
//			}
			cell=row.createCell(2);
			cell.setCellValue(censorLevel);
			String censorDate="";
			if(certTaskInfo.getPassedTime() != null){
				censorDate=sf.format(certTaskInfo.getPassedTime());
			}
			cell=row.createCell(3);
			cell.setCellValue(censorDate);
			cell=row.createCell(4);
			String censorScore="";
			if(certTaskInfo.getPassedScore() != null){
				censorScore = certTaskInfo.getPassedScore().toString();
			}
			cell.setCellValue(censorScore);
			cell=row.createCell(5);
			cell.setCellValue(unitInfo.getLegalRepresentative());
			if("1".equals(certTaskInfo.getApplicationStatus())){
				applicationStatus= "有";
			}else{
				applicationStatus= "无";
			}
			cell=row.createCell(6);
			cell.setCellValue(applicationStatus);
			if("1".equals(certTaskInfo.getReviewInfoStatus())){
				reviewInfoStatus= "有";
			}else{
				reviewInfoStatus= "无";
			}
			cell=row.createCell(7);
			cell.setCellValue(reviewInfoStatus);
			if(certTaskInfo.getNumPeople() != null || certTaskInfo.getHighestScore() !=null || certTaskInfo.getAverage() != null){
				scoreStatus= "有";
			}else{
				scoreStatus= "无";
			}
			cell=row.createCell(8);
			cell.setCellValue(scoreStatus);
			if("1".equals(certTaskInfo.getSubmissionStatus())){
				submissionStatus= "有";
			}else{
				submissionStatus= "无";
			}
			cell=row.createCell(9);
			cell.setCellValue(submissionStatus);
			String planFileId="";
			if(StringUtils.isNotEmpty(certTaskInfo.getPlanFileId())){
				planFileId= "有";
			}else{
				planFileId= "无";
			}
			cell=row.createCell(10);
			cell.setCellValue(planFileId); 	
			cell=row.createCell(11);
			cell.setCellValue(certTaskInfo.getCertTaskId()); 
		}
		String x = new String(("审查认证情况.xls").getBytes("GBK"), "ISO8859-1");
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
	public String listExportExcel2() throws Exception{
		StringBuilder unitSql = new StringBuilder();
		StringBuilder certSql = new StringBuilder();
		System.out.println("222222222");
		SimpleDateFormat sf= new SimpleDateFormat("yyyy-MM-dd");
		certSql.append("");
		Integer certTaskId=Integer.valueOf(request.getParameter("certTaskId"));
		List CertTaskInfoList=dao.findAll("from CertTaskInfo where certTaskId='"+certTaskId+"' order by confidentialityLevelQualifications,passedTime desc");
		
		System.out.println("type:"+CertTaskInfoList);
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
		cell.setCellValue("认证状态");
		cell=row.createCell(1);
		cell.setCellValue("认证单位名称");
		cell=row.createCell(2);
		cell.setCellValue("认证等级");
		cell=row.createCell(3);
		cell.setCellValue("认证通过时间");
		cell=row.createCell(4);
		cell.setCellValue("通过分数");
		cell=row.createCell(5);
		cell.setCellValue("法定代表人");
		cell=row.createCell(6);
		cell.setCellValue("认证申请书");
		cell=row.createCell(7);
		cell.setCellValue("汇报材料");
		cell=row.createCell(8);
		cell.setCellValue("分数情况");
		cell=row.createCell(9);
		cell.setCellValue("认证意见书");
		cell=row.createCell(10);
		cell.setCellValue("整改计划"); 
		int rowNum=2;
		for(int i=0;i<CertTaskInfoList.size();i++){
			row=hssfsheet.createRow(rowNum);
			rowNum++;
			CertTaskInfo certTaskInfo=(CertTaskInfo) CertTaskInfoList.get(i);
			String pkUnit=certTaskInfo.getPkUnit();
			UnitInfo unitInfo=(UnitInfo) dao.findOne(UnitInfo.class, Integer.parseInt(pkUnit));
			cell=row.createCell(0);
			cell.setCellValue("认证通过");
			cell=row.createCell(1);
			cell.setCellValue(unitInfo.getUnitName());
			String censorLevel="一级";
//			if(StringUtils.isNotEmpty(unitInfo.getCensorLevel())){
//				censorLevel=getCodeName(unitInfo.getCensorLevel());
//			}
			cell=row.createCell(2);
			cell.setCellValue(censorLevel);
			String censorDate="";
			if(certTaskInfo.getPassedTime() != null){
				censorDate=sf.format(certTaskInfo.getPassedTime());
			}
			cell=row.createCell(3);
			cell.setCellValue(censorDate);
			cell=row.createCell(4);
			String censorScore="";
			if(certTaskInfo.getPassedScore() != null){
				censorScore = certTaskInfo.getPassedScore().toString();
			}
			cell.setCellValue(censorScore);
			cell=row.createCell(5);
			cell.setCellValue(unitInfo.getLegalRepresentative());
			String applicationStatus="";
			if("1".equals(certTaskInfo.getApplicationStatus())){
				 applicationStatus= "有";
			}else{
				 applicationStatus= "无";
			}
			cell=row.createCell(6);
			cell.setCellValue(applicationStatus);
			String reviewInfoStatus="";
			if("1".equals(certTaskInfo.getReviewInfoStatus())){
				reviewInfoStatus= "有";
			}else{
				reviewInfoStatus= "无";
			}
			cell=row.createCell(7);
			cell.setCellValue(reviewInfoStatus);
			String scoreStatus="";
			if(certTaskInfo.getNumPeople() != null || certTaskInfo.getHighestScore() !=null || certTaskInfo.getAverage() != null){
				scoreStatus= "有";
			}else{
				scoreStatus= "无";
			}
			cell=row.createCell(8);
			cell.setCellValue(scoreStatus);
			String submissionStatus="";
			if("1".equals(certTaskInfo.getSubmissionStatus())){
				submissionStatus= "有";
			}else{
				submissionStatus= "无";
			}
			cell=row.createCell(9);
			cell.setCellValue(submissionStatus);
			String planFileId="";
			if(StringUtils.isNotEmpty(certTaskInfo.getPlanFileId())){
				planFileId= "有";
			}else{
				planFileId= "无";
			}
			cell=row.createCell(10);
			cell.setCellValue(planFileId); 	
			cell=row.createCell(11);
			cell.setCellValue(certTaskInfo.getCertTaskId()); 
			certTaskId=certTaskInfo.getCertTaskId();
			List ListcertTaskScore=dao.findAll("from CertTaskScore where certTaskId='"+certTaskId+"'");
			System.out.println("ListcertTaskScore"+ListcertTaskScore);
			if(ListcertTaskScore!=null && ListcertTaskScore.size()>0){
				for(int j=0;j<ListcertTaskScore.size();j++){
					row=hssfsheet.createRow(rowNum);
					rowNum++;
					CertTaskScore certTaskScore=(CertTaskScore)ListcertTaskScore.get(j);
					cell=row.createCell(1);
					if(certTaskScore.getScoreRule()!=null){
						cell.setCellValue(certTaskScore.getScoreRule());
					}
					cell=row.createCell(1);
					if(certTaskScore.getScoreNumber()!=null){
						cell.setCellValue(certTaskScore.getScoreNumber());
					}
				}
			}
		}
		String x = new String(("审查认证情况.xls").getBytes("GBK"), "ISO8859-1");
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
	
	public String listCertAnalyse() throws Exception{
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		List listGroup = dao.findAll("from UnitInfo where superiorInfo = '0'");
		request.setAttribute("listGroup", listGroup);
		
		
			String pkUnita=request.getParameter("pkUnita");
			String pkUnitb=request.getParameter("pkUnitb");
			String pkUnitc=request.getParameter("pkUnitc");
			String pkUnitd=request.getParameter("pkUnitd");
			
			StringBuilder histogram = new StringBuilder();
			StringBuilder yBar = new StringBuilder();
			StringBuilder name = new StringBuilder();
			
			SimpleDateFormat sf = new SimpleDateFormat("yyyy");
			String nowYear=sf.format(new Date());
			String lastYear = (Integer.parseInt(nowYear)-1)+"";
			String lastYear2 = (Integer.parseInt(lastYear)-1)+"";
			UnitInfo unitInfo =new UnitInfo();
			String strs1="";
			String strs2="";
			if (StringUtils.isNotEmpty(pkUnita) && StringUtils.isEmpty(pkUnitb) && StringUtils.isEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)) {
				strs1+=" and b.unitNo in( select unitNo from UnitInfo where superiorInfo like '%"+pkUnita+"%') ";
				unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+pkUnita+"'");
				name.append("'"+unitInfo.getUnitName()+"下属单位近三年审查认证得分情况'");
				strs2=pkUnita;
			}else if(StringUtils.isNotEmpty(pkUnita) && StringUtils.isNotEmpty(pkUnitb) && StringUtils.isEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)){
				strs1+=" and b.unitNo in ( select unitNo from UnitInfo where superiorInfo like '%"+pkUnitb+"%') ";
				unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+pkUnitb+"'");
				name.append("'"+unitInfo.getUnitName()+"下属单位近三年审查认证得分情况'");
				strs2=pkUnitb;
			}else if(StringUtils.isNotEmpty(pkUnita) && StringUtils.isNotEmpty(pkUnitb) && StringUtils.isNotEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)){
				strs1+=" and b.unitNo in ( select unitNo from UnitInfo where superiorInfo like '%"+pkUnitc+"%') ";
				unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+pkUnitc+"'");
				name.append("'"+unitInfo.getUnitName()+"下属单位近三年审查认证得分情况'");
				strs2=pkUnitc;
			}else if(StringUtils.isNotEmpty(pkUnita) && StringUtils.isNotEmpty(pkUnitb) && StringUtils.isNotEmpty(pkUnitc) && StringUtils.isNotEmpty(pkUnitd)){
				strs1+=" and b.unitNo in ( select unitNo from UnitInfo where superiorInfo like '%"+pkUnitd+"%') ";
				unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+pkUnitd+"'");
				name.append("'"+unitInfo.getUnitName()+"下属单位近三年审查认证得分情况'");
				strs2=pkUnitd;
			}else if(StringUtils.isEmpty(pkUnita) && StringUtils.isEmpty(pkUnitb) && StringUtils.isEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)){
				name.append("'所有单位近三年审查认证得分情况'");
			}
			
			String sql = "SELECT b.unitName,YEAR(a.passedTime) as years, COALESCE(ROUND(a.passedScore,1),0) as score "+
			" FROM CertTaskInfo a,UnitInfo b  "+
			" WHERE a.pkUnit=b.pkUnit  "+
			" "+strs1+" " +
			"AND YEAR(a.passedTime) IN ('"+nowYear+"','"+lastYear+"','"+lastYear2+"') " +
			"ORDER BY b.unitName,YEAR(a.passedTime) ";
			
			String sql1 = "SELECT b.unitName,count(*) as num " +
					" FROM  CertTaskInfo a,UnitInfo b   " +
					" WHERE a.pkUnit=b.pkUnit "+strs1+" " +
					"AND YEAR(a.passedTime) IN ('"+nowYear+"','"+lastYear+"','"+lastYear2+"') "+
					" GROUP BY b.unitName";
			String sql2="SELECT unitName FROM UnitInfo " +
					"WHERE unitName NOT IN(" +
					"SELECT b.unitName AS num FROM  " +
					"CertTaskInfo a,UnitInfo b " +
					"WHERE a.pkUnit=b.pkUnit" +strs1+" " +
					"AND YEAR(a.passedTime) IN ('"+nowYear+"','"+lastYear+"','"+lastYear2+"') " +
					"GROUP BY b.unitName ) " +
					"AND superiorInfo LIKE '%"+strs2+"%'";
			List list1=dao.findAll(sql1);
			List list2=dao.findAll(sql);
			List list3=dao.findAll(sql2);
			List strYear=new ArrayList();
			strYear.add(lastYear2);
			strYear.add(lastYear);
			strYear.add(nowYear);
				if (list1.size()>0) {
					yBar.append("[");
					name.append("");
					for(int i=0;i<list1.size();i++){
						Object[] obj1=(Object[])list1.get(i);
						yBar.append("{name:'"+obj1[0].toString()+"',data:[");
						
						for(int a=0;a<strYear.size();a++){
							String year = (String) strYear.get(a);
							boolean temp=false;
							for(int j=0;j<list2.size();j++){
								Object[] obj2=(Object[])list2.get(j);
								if(year.equals(obj2[1].toString())&&obj1[0].toString().equals(obj2[0].toString())){	
									yBar.append(""+obj2[2].toString()+"");
									if(a!=strYear.size()-1){
										yBar.append(",");
									}
									temp=true;
									break;
								}
								
							}
							if(!temp){
								yBar.append("0");
								if(a!=strYear.size()-1){
									yBar.append(",");
								}
							}
						}
						if(list3.size()>0){
							yBar.append("]},");
						}else{
							if(i==list1.size()-1){
								yBar.append("]}");
							}else{
								yBar.append("]},");
							}
						}
					}
					if(list3.size()>0){
						for(int b=0;b<list3.size();b++){
							if(b==list3.size()-1){
								yBar.append("{name:'"+list3.get(b).toString()+"',data:[0,0,0]}");
							}else{
								yBar.append("{name:'"+list3.get(b).toString()+"',data:[0,0,0]},");
							}
						}
					}
					
					yBar.append("]");
				}
				else{
					name.setLength(0);
					name.append("'"+unitInfo.getUnitName()+"下属单位近三年没有审查认证'");
					yBar.append("[{name:'"+unitInfo.getUnitName()+"',data:[0,0,0]}]");
				}
			histogram.append("['"+lastYear2+"','"+lastYear+"','"+nowYear+"']");
			request.setAttribute("yBar", yBar);
			request.setAttribute("histogram", histogram);
			request.setAttribute("name", name);
			System.out.println(yBar+"yBar");
			System.out.println(histogram+"histogram");
			System.out.println(name+"name");
			return SUCCESS;        
	}

	public String CertScoreQuery() throws  Exception{
		String pkUnita=request.getParameter("pkUnita");
		String pkUnitb=request.getParameter("pkUnitb");
		String pkUnitc=request.getParameter("pkUnitc");
		String pkUnitd=request.getParameter("pkUnitd");
		
		StringBuilder histogram = new StringBuilder();
		StringBuilder yBar = new StringBuilder();
		StringBuilder name = new StringBuilder();
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy");
		String nowYear=sf.format(new Date());
		String lastYear = (Integer.parseInt(nowYear)-1)+"";
		String lastYear2 = (Integer.parseInt(lastYear)-1)+"";
		UnitInfo unitInfo =new UnitInfo();
		String strs1="";
		String strs2="";
		if (StringUtils.isNotEmpty(pkUnita) && StringUtils.isEmpty(pkUnitb) && StringUtils.isEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)) {
			strs1+=" and b.unitNo in( select unitNo from UnitInfo where superiorInfo like '%"+pkUnita+"%') ";
			unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+pkUnita+"'");
			name.append("'"+unitInfo.getUnitName()+"下属单位近三年审查认证得分情况'");
			strs2=pkUnita;
		}else if(StringUtils.isNotEmpty(pkUnita) && StringUtils.isNotEmpty(pkUnitb) && StringUtils.isEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)){
			strs1+=" and b.unitNo in ( select unitNo from UnitInfo where superiorInfo like '%"+pkUnitb+"%') ";
			unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+pkUnitb+"'");
			name.append("'"+unitInfo.getUnitName()+"下属单位近三年审查认证得分情况'");
			strs2=pkUnitb;
		}else if(StringUtils.isNotEmpty(pkUnita) && StringUtils.isNotEmpty(pkUnitb) && StringUtils.isNotEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)){
			strs1+=" and b.unitNo in ( select unitNo from UnitInfo where superiorInfo like '%"+pkUnitc+"%') ";
			unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+pkUnitc+"'");
			name.append("'"+unitInfo.getUnitName()+"下属单位近三年审查认证得分情况'");
			strs2=pkUnitc;
		}else if(StringUtils.isNotEmpty(pkUnita) && StringUtils.isNotEmpty(pkUnitb) && StringUtils.isNotEmpty(pkUnitc) && StringUtils.isNotEmpty(pkUnitd)){
			strs1+=" and b.unitNo in ( select unitNo from UnitInfo where superiorInfo like '%"+pkUnitd+"%') ";
			unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+pkUnitd+"'");
			name.append("'"+unitInfo.getUnitName()+"下属单位近三年审查认证得分情况'");
			strs2=pkUnitd;
		}else if(StringUtils.isEmpty(pkUnita) && StringUtils.isEmpty(pkUnitb) && StringUtils.isEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)){
			name.append("'所有单位近三年审查认证得分情况'");
		}
		
		String sql = "SELECT b.unitName,YEAR(a.passedTime) as years, COALESCE(ROUND(a.passedScore,1),0) as score "+
		" FROM CertTaskInfo a,UnitInfo b  "+
		" WHERE a.pkUnit=b.pkUnit  "+
		" "+strs1+" " +
		"AND YEAR(a.passedTime) IN ('"+nowYear+"','"+lastYear+"','"+lastYear2+"') " +
		"ORDER BY b.unitName,YEAR(a.passedTime) ";
		
		String sql1 = "SELECT b.unitName,count(*) as num " +
				" FROM  CertTaskInfo a,UnitInfo b   " +
				" WHERE a.pkUnit=b.pkUnit "+strs1+" " +
				"AND YEAR(a.passedTime) IN ('"+nowYear+"','"+lastYear+"','"+lastYear2+"') "+
				" GROUP BY b.unitName";
		String sql2="SELECT unitName FROM UnitInfo " +
				"WHERE unitName NOT IN(" +
				"SELECT b.unitName AS num FROM  " +
				"CertTaskInfo a,UnitInfo b " +
				"WHERE a.pkUnit=b.pkUnit" +strs1+" " +
				"AND YEAR(a.passedTime) IN ('"+nowYear+"','"+lastYear+"','"+lastYear2+"') " +
				"GROUP BY b.unitName ) " +
				"AND superiorInfo LIKE '%"+strs2+"%'";
		List list1=dao.findAll(sql1);
		List list2=dao.findAll(sql);
		List list3=dao.findAll(sql2);
		List strYear=new ArrayList();
		strYear.add(lastYear2);
		strYear.add(lastYear);
		strYear.add(nowYear);
			if (list1.size()>0) {
				yBar.append("[");
				name.append("");
				for(int i=0;i<list1.size();i++){
					Object[] obj1=(Object[])list1.get(i);
					yBar.append("{name:'"+obj1[0].toString()+"',data:[");
					
					for(int a=0;a<strYear.size();a++){
						String year = (String) strYear.get(a);
						boolean temp=false;
						for(int j=0;j<list2.size();j++){
							Object[] obj2=(Object[])list2.get(j);
							if(year.equals(obj2[1].toString())&&obj1[0].toString().equals(obj2[0].toString())){	
								yBar.append(""+obj2[2].toString()+"");
								if(a!=strYear.size()-1){
									yBar.append(",");
								}
								temp=true;
								break;
							}
							
						}
						if(!temp){
							yBar.append("0");
							if(a!=strYear.size()-1){
								yBar.append(",");
							}
						}
					}
					if(list3.size()>0){
						yBar.append("]},");
					}else{
						if(i==list1.size()-1){
							yBar.append("]}");
						}else{
							yBar.append("]},");
						}
					}
				}
				if(list3.size()>0){
					for(int b=0;b<list3.size();b++){
						if(b==list3.size()-1){
							yBar.append("{name:'"+list3.get(b).toString()+"',data:[0,0,0]}");
						}else{
							yBar.append("{name:'"+list3.get(b).toString()+"',data:[0,0,0]},");
						}
					}
				}
				
				yBar.append("]");
			}
			else{
				name.append("'"+unitInfo.getUnitName()+"下属单位近三年没有审查认证'");
				yBar.append("[{name:'"+unitInfo.getUnitName()+"',data:[]}]");
			}
			
			
			
			
		System.out.println(yBar+"yBar");
		histogram.append("['"+lastYear2+"','"+lastYear+"','"+nowYear+"']");
		request.setAttribute("yBar", yBar);
		request.setAttribute("histogram", histogram);
		request.setAttribute("name", name);
		return SUCCESS;
	}
	
	public String listCertAnalyse1() throws  Exception{
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		List listGroup = dao.findAll("from UnitInfo where superiorInfo = '0'");  
		request.setAttribute("listGroup", listGroup);
		
		String pkUnita=request.getParameter("pkUnita");
		String pkUnitb=request.getParameter("pkUnitb");
		String pkUnitc=request.getParameter("pkUnitc");  
		String pkUnitd=request.getParameter("pkUnitd");
		String year=request.getParameter("year");
		
		String certType=request.getParameter("certType");
		UnitInfo unitInfo =new UnitInfo();
		StringBuilder yBar = new StringBuilder();
		StringBuilder viewStrs = new StringBuilder();
		StringBuilder name = new StringBuilder();
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy");
		String nowYear=year;//
		if(StringUtils.isEmpty(nowYear)){
			nowYear=sf.format(new Date());
		}
		//--------------------------------
		String yearTo=request.getParameter("yearTo");
		String strDate="";
		if(StringUtils.isNotEmpty(nowYear)){
			strDate +=" and YEAR(b.passedTime) >= '"+nowYear+"'";
		}
		if(StringUtils.isNotEmpty(yearTo)){
			strDate +=" and YEAR(b.passedTime) <= '"+yearTo+"'";
		}
		
		//--------------------------------
		String strs1="";
		if (StringUtils.isNotEmpty(pkUnita) && StringUtils.isEmpty(pkUnitb) && StringUtils.isEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)) {
			strs1+=" AND c.superiorInfo LIKE '%"+pkUnita+"%' ";
			unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+pkUnita+"'");
			name.append(""+unitInfo.getUnitName()+"下属单位的认证扣分情况");
		}else if(StringUtils.isNotEmpty(pkUnita) && StringUtils.isNotEmpty(pkUnitb) && StringUtils.isEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)){
			strs1+=" and c.superiorInfo like '%"+pkUnitb+"%' ";
			unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+pkUnita+"'");
			name.append(""+unitInfo.getUnitName()+"下属单位的认证扣分情况");
		}else if(StringUtils.isNotEmpty(pkUnita) && StringUtils.isNotEmpty(pkUnitb) && StringUtils.isNotEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)){
			strs1+=" and c.superiorInfo like '%"+pkUnitc+"%' ";
			unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+pkUnita+"'");
			name.append(""+unitInfo.getUnitName()+"下属单位的认证扣分情况");
		}else if(StringUtils.isNotEmpty(pkUnita) && StringUtils.isNotEmpty(pkUnitb) && StringUtils.isNotEmpty(pkUnitc) && StringUtils.isNotEmpty(pkUnitd)){
			strs1+=" and c.superiorInfo like '%"+pkUnitd+"%' ";
			unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+pkUnita+"'");
			name.append(""+unitInfo.getUnitName()+"下属单位的认证扣分情况");
		}else if(StringUtils.isEmpty(pkUnita) && StringUtils.isEmpty(pkUnitb) && StringUtils.isEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)){
			name.append("所有单位的认证扣分情况");
		}
		String str="";
		if(StringUtils.isNotEmpty(certType)){
			str +=" and b.certType='"+certType+"'";
		}else{
			str+=" and b.certType='013001'";
		}
		request.setAttribute("certType", certType);
		
		String sql = "SELECT a.scoreRule,count(*),a.certTaskId,a.ruleId,d.ruleContent " +
				"FROM CertTaskScore a,CertTaskInfo b,UnitInfo c,CertConfigRule d " +
				"WHERE a.certTaskId = b.certTaskId AND b.pkUnit = c.pkUnit AND a.ruleId = d.ruleId " +
				""+strs1+str+strDate+"  " +
				"GROUP BY a.scoreRule,a.ruleId ";
		
		String sql1 = "SELECT count(*) FROM CertTaskInfo b WHERE b.pkUnit IN" +
		"(SELECT pkUnit FROM UnitInfo c WHERE 1=1 "+strs1+") "+str+strDate+" ";

		List list1=dao.findAll(sql);
		//['IE',       26.8],{name: 'Chrome',y: 12.8,sliced: true,selected: true},
		yBar.append("[");
		if(list1.size()>0){
			int a = dao.findRowCount(sql1);
			for(int i=0;i<list1.size();i++){
				Object[] obj = (Object[]) list1.get(i);
				String sql3 = "SELECT count(*) FROM CertTaskInfo b WHERE b.pkUnit IN" +
				"(SELECT pkUnit FROM UnitInfo c WHERE 1=1 "+strs1+") "+str+strDate+" " +
				" and certTaskId in(select certTaskId from CertTaskScore where ruleId = '"+obj[3]+"')";
				int b = dao.findRowCount(sql3);
				yBar.append("[");
				if(obj[1]==null){
					obj[1]=0;
				}
				String percent = getPercent(b,a );
				yBar.append("'"+obj[0].toString()+"',"+percent+"");
				viewStrs.append(""+obj[0].toString()+":"+obj[4].toString()+"");
				if(i==list1.size()-1){
					yBar.append("]");
				}else{
					yBar.append("],");
					viewStrs.append(",");
				}
			}
		}else{
			yBar.append("['无扣分项',0]");
		}
		
		String sql3 = "SELECT a.scoreRule as scoreRule,a.scoreNumber as scoreNumber,b.pkUnit as pkUnit,c.unitName as unitName " +
		"FROM CertTaskScore a,CertTaskInfo b,UnitInfo c " +
		"WHERE a.certTaskId = b.certTaskId " +
		"AND b.pkUnit = c.pkUnit " +
		""+strs1+str+strDate+"" +
		"order by a.scoreRule";
		StringBuilder divScore = new StringBuilder();
		List list3 = dao.findAll(sql3);
		for(int j=0;j<list3.size();j++){
			Object[] obj2 = (Object[]) list3.get(j);
			divScore.append(obj2[0]+","+obj2[1]+","+obj2[3]);
			if(j!=list3.size()-1){
				divScore.append(";");
			}
		}
		request.setAttribute("divScore", divScore);
		yBar.append("]");
		System.out.println(yBar+"divScore");
		request.setAttribute("yBar", yBar);
		request.setAttribute("viewStrs", viewStrs);
		request.setAttribute("name", name);
		request.setAttribute("nowYear", "'"+nowYear+"'");
		return SUCCESS;
	}
	
	public String listCertAnalyse2() throws  Exception{
		UnitInfo unitInfo =new UnitInfo();
		StringBuilder yBar = new StringBuilder();
		StringBuilder viewStrs = new StringBuilder();
		StringBuilder name = new StringBuilder();
		
		//--------------------------------
		
		name.append("审查认证单位现场审查成绩占比");
		
//		String sql = "SELECT a.scoreRule,count(*),a.certTaskId,a.ruleId,d.ruleContent " +
//				"FROM CertTaskScore a,CertTaskInfo b,UnitInfo c,CertConfigRule d " +
//				"WHERE a.certTaskId = b.certTaskId AND b.pkUnit = c.pkUnit AND a.ruleId = d.ruleId " +
//				""+strs1+str+strDate+"  " +
//				"GROUP BY a.scoreRule,a.ruleId ";
//		
//		String sql1 = "SELECT count(*) FROM CertTaskInfo b WHERE b.pkUnit IN" +
//		"(SELECT pkUnit FROM UnitInfo c WHERE 1=1 "+strs1+") "+str+strDate+" ";

//		List list1=dao.findAll(sql);
		//['IE',       26.8],{name: 'Chrome',y: 12.8,sliced: true,selected: true},
		double a=dao.findRowCount("select count(*) from CertTaskInfo where passedScore>=480");
		double b=dao.findRowCount("select count(*) from CertTaskInfo");
		double percent=a/b;
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMinimumFractionDigits(4);
		String percenta = nf.format(percent);
		String percentb =String.valueOf((1-Double.valueOf(percenta)));
		yBar.append("[['现场审查成绩在480分以上',"+percenta+"],['现场审查不合格',"+percentb+"]]");
//		if(list1.size()>0){
//			int a = dao.findRowCount(sql1);
//			for(int i=0;i<list1.size();i++){
//				Object[] obj = (Object[]) list1.get(i);
//				String sql3 = "SELECT count(*) FROM CertTaskInfo b WHERE b.pkUnit IN" +
//				"(SELECT pkUnit FROM UnitInfo c WHERE 1=1 "+strs1+") "+str+strDate+" " +
//				" and certTaskId in(select certTaskId from CertTaskScore where ruleId = '"+obj[3]+"')";
//				int b = dao.findRowCount(sql3);
//				yBar.append("[");
//				if(obj[1]==null){
//					obj[1]=0;
//				}
//				String percent = getPercent(b,a );
//				yBar.append("'"+obj[0].toString()+"',"+percent+"");
//				viewStrs.append(""+obj[0].toString()+":"+obj[4].toString()+"");
//				if(i==list1.size()-1){
//					yBar.append("]");
//				}else{
//					yBar.append("],");
//					viewStrs.append(",");
//				}
//			}
//		}else{
//			yBar.append("['无扣分项',0]");
//		}
		
//		String sql3 = "SELECT a.scoreRule as scoreRule,a.scoreNumber as scoreNumber,b.pkUnit as pkUnit,c.unitName as unitName " +
//		"FROM CertTaskScore a,CertTaskInfo b,UnitInfo c " +
//		"WHERE a.certTaskId = b.certTaskId " +
//		"AND b.pkUnit = c.pkUnit " +
//		""+strs1+str+strDate+"" +
//		"order by a.scoreRule";
//		StringBuilder divScore = new StringBuilder();
//		List list3 = dao.findAll(sql3);
//		for(int j=0;j<list3.size();j++){
//			Object[] obj2 = (Object[]) list3.get(j);
//			divScore.append(obj2[0]+","+obj2[1]+","+obj2[3]);
//			if(j!=list3.size()-1){
//				divScore.append(";");
//			}
//		}
//		request.setAttribute("divScore", divScore);
//		yBar.append("]");
		System.out.println(yBar+"divScore");
		request.setAttribute("yBar", yBar);
		request.setAttribute("viewStrs", viewStrs);
		request.setAttribute("name", name);
		return SUCCESS;
	}
	
	
	
	public String listCertAnalyse3() throws  Exception{
		UnitInfo unitInfo =new UnitInfo();
		StringBuilder yBar = new StringBuilder();
		StringBuilder viewStrs = new StringBuilder();
		StringBuilder name = new StringBuilder();
		
		//--------------------------------
		
		name.append("审查认证扣分统计");
		double all=dao.findRowCount("select coalesce(sum(scoreNumber),0) from CertTaskScore ");
		double a18=dao.findRowCount("select coalesce(sum(scoreNumber),0) from CertTaskScore where certId='18'");//涉密载体管理
		double a19=dao.findRowCount("select coalesce(sum(scoreNumber),0) from CertTaskScore where certId='19'");//要害部门部位管理
		double a17=dao.findRowCount("select coalesce(sum(scoreNumber),0) from CertTaskScore where certId='17'");//涉密人员管理
		double a16=dao.findRowCount("select coalesce(sum(scoreNumber),0) from CertTaskScore where certId='16'");//定密管理
		double a20=dao.findRowCount("select coalesce(sum(scoreNumber),0) from CertTaskScore where certId='20'");//计算机和信息系统管理
		double percent18=a18/all;
		double percent19=a19/all;
		double percent17=a17/all;
		double percent16=a16/all;
		double percent20=a20/all;
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMinimumFractionDigits(4);
		String rePercent18 = nf.format(percent18);
		String rePercent19 = nf.format(percent19);
		String rePercent17 = nf.format(percent17);
		String rePercent16 = nf.format(percent16);
		String rePercent20 = nf.format(percent20);
		double percenta=Double.valueOf(rePercent18)+Double.valueOf(rePercent19)+Double.valueOf(rePercent17)+Double.valueOf(rePercent16)+Double.valueOf(rePercent20);
		String percentb =nf.format(((1-Double.valueOf(percenta))));
		yBar.append("[['涉密载体管理',"+rePercent18+"],['要害部门部位管理',"+rePercent19+"],['涉密人员管理',"+rePercent17+"],['定密管理',"+rePercent16+"]," +
				"['计算机和信息系统管理',"+rePercent20+"],['其他',"+percentb+"]]");
		
		System.out.println(yBar+"divScore");
		request.setAttribute("yBar", yBar);
		request.setAttribute("viewStrs", viewStrs);
		request.setAttribute("name", name);
		return SUCCESS;
	}
	public String listCertAnalyse4() throws  Exception{
		UnitInfo unitInfo =new UnitInfo();
		StringBuilder yBar = new StringBuilder();
		StringBuilder viewStrs = new StringBuilder();
		StringBuilder name = new StringBuilder();
		
		//--------------------------------
		
		name.append("各单位在定密管理上的扣分比");
//		double all=dao.findRowCount("select count(distinct pkUnit) from CertTaskInfo ");
//		double a1225=dao.findRowCount("select count(distinct pkUnit) from CertTaskInfo where certTaskId in( select certTaskId from CertTaskScore where ruleId='1225')");
//		System.out.println("all:"+a1225);
//		double a1223=dao.findRowCount("select count(distinct pkUnit) from CertTaskInfo where certTaskId in( select certTaskId from CertTaskScore where ruleId='1223')");
//		NumberFormat nf = NumberFormat.getNumberInstance();
//		double other=dao.findRowCount("select count(distinct pkUnit) from CertTaskInfo where certTaskId in( select certTaskId from CertTaskScore where ruleId<>'1225' and ruleId<>'1223' )");
//		nf.setMinimumFractionDigits(4);
//		String percent1225 =nf.format(a1225/all);
//		String percent1223 =nf.format(a1223/all);
//		String percentOther =nf.format(other/all);
//		yBar.append("[['秘密范围细目制定不准确',"+percent1225+"],['密级确定不够准确的',"+percent1223+"],['其他',"+percentOther+"]]");
		yBar.append("[");
		//[['现场审查成绩在480分以上',0.2140],['现场审查不合格',0.786]]
		List certConfigRule = dao.findAll(" from CertConfigRule where certId='16'");
		
		Long koufen;
		Long zongkoufen = dao.findCount( "select coalesce(sum(scoreNumber),0)  from CertTaskScore where certId='16'");//总扣分
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
			String xx;
			System.out.println("扣分/*-/*-/*-/-*/*-/*-/-*"+(double) koufen/1000);
			if(zongkoufen==0){
				xx="0.00";
			}else{
				NumberFormat formatter = new DecimalFormat("0.00");     
				Double x=new Double(((double) koufen/1000)/((double) zongkoufen/1000));     
				System.out.println("biliqwewq eq "+x);
				xx = formatter.format(x*100);    
				System.out.println("biliqwewq eq "+xx);
			}
			
			if(j==certConfigRule.size()-1){
				yBar.append("['"+CertConfigRule1.getRuleContent()+"',"+(xx+"")+"]");
			}else{
				yBar.append("['"+CertConfigRule1.getRuleContent()+"',"+(xx+"")+"],");    
			}
		}
			
		yBar.append("]");
		
		System.out.println(yBar+"divScore");
		request.setAttribute("yBar", yBar);
		request.setAttribute("viewStrs", viewStrs);
		request.setAttribute("name", name);
		return SUCCESS;
	}
	public String listCertAnalyse5() throws  Exception{
		UnitInfo unitInfo =new UnitInfo();
		StringBuilder yBar = new StringBuilder();
		StringBuilder viewStrs = new StringBuilder();
		StringBuilder name = new StringBuilder();
		
		//--------------------------------
		
		name.append("各单位在涉密人员管理上的扣分比");
		yBar.append("[");
		//[['现场审查成绩在480分以上',0.2140],['现场审查不合格',0.786]]
		List certConfigRule = dao.findAll(" from CertConfigRule where certId='17'");
		
		Long koufen;
		Long zongkoufen = dao.findCount( "select coalesce(sum(scoreNumber),0)  from CertTaskScore where certId='17'");//总扣分
		System.out.println("总扣分/*-/*-/*-/-*/*-/*-/-*"+(double)zongkoufen);
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
			String xx;
			System.out.println("扣分/*-/*-/*-/-*/*-/*-/-*"+(double) koufen/1000);
			if(zongkoufen==0){
				xx="0.00";
			}else{
				NumberFormat formatter = new DecimalFormat("0.00");     
				Double x=new Double(((double) koufen/1000)/((double) zongkoufen/1000));     
				System.out.println("biliqwewq eq "+x);
				xx = formatter.format(x*100);    
				System.out.println("biliqwewq eq "+xx);
			}
			
			if(j==certConfigRule.size()-1){
				yBar.append("['"+CertConfigRule1.getRuleContent()+"',"+(xx+"")+"]");
			}else{
				yBar.append("['"+CertConfigRule1.getRuleContent()+"',"+(xx+"")+"],");    
			}
		}
			
		yBar.append("]");
		
		System.out.println(yBar+"divScore");
		request.setAttribute("yBar", yBar);
		request.setAttribute("viewStrs", viewStrs);
		request.setAttribute("name", name);
		return SUCCESS;
	}
	public String listCertAnalyse7() throws  Exception{
		UnitInfo unitInfo =new UnitInfo();
		StringBuilder yBar = new StringBuilder();
		StringBuilder viewStrs = new StringBuilder();
		StringBuilder name = new StringBuilder();
		
		//--------------------------------
		
		name.append("台帐、维修、报废");
		yBar.append("[");
		//[['现场审查成绩在480分以上',0.2140],['现场审查不合格',0.786]]
		List certConfigRule = dao.findAll(" from CertConfigRule where ruleContent like '%台账%' or ruleContent like '%维修%' or ruleContent like '%报废%'");
		
		Long koufen;
		Long zongkoufen =new Long(0);
		
		for (int i = 0; i < certConfigRule.size(); i++) {
			CertConfigRule ccr = (CertConfigRule)certConfigRule.get(i);
			Long a = dao.findCount( "select coalesce(sum(scoreNumber),0)  from CertTaskScore where ruleId ="+ccr.getRuleId()+" ");//总扣分
			if(a!=null){
				zongkoufen+=a;
			}
		}
		
		System.out.println("总扣分/*-/*-/*-/-*/*-/*-/-*"+zongkoufen);
		for (int j = 0; j < certConfigRule.size(); j++) {
			CertConfigRule CertConfigRule1 = (CertConfigRule) certConfigRule.get(j);
			if (CertConfigRule1.getRuleNo()!=null&&CertConfigRule1.getRuleNo()!=null) {
				 koufen = dao.findCount(" select coalesce(sum(scoreNumber),0) from CertTaskScore where ruleId ='"+CertConfigRule1.getRuleNo()+"' ");                 	
			}else {
				  koufen=0l;   
			}
			if (koufen==null) {
				 koufen=0l;      
			}
			String xx;
			System.out.println("扣分/*-/*-/*-/-*/*-/*-/-*"+(double) koufen/1000);
			if(zongkoufen==0){
				xx="0.00";
			}else{
				NumberFormat formatter = new DecimalFormat("0.00");     
				Double x=new Double(((double) koufen/1000)/((double) zongkoufen/1000));     
				System.out.println("biliqwewq eq "+x);
				xx = formatter.format(x*100);    
				System.out.println("biliqwewq eq "+xx);
			}
			
			if(j==certConfigRule.size()-1){
				yBar.append("['"+CertConfigRule1.getRuleContent()+"',"+(xx+"")+"]");
			}else{
				yBar.append("['"+CertConfigRule1.getRuleContent()+"',"+(xx+"")+"],");    
			}
		}
			
		yBar.append("]");
		
		System.out.println(yBar+"divScore");
		request.setAttribute("yBar", yBar);
		request.setAttribute("viewStrs", viewStrs);
		request.setAttribute("name", name);
		return SUCCESS;
	}
	public String listCertAnalyse8() throws  Exception{
		UnitInfo unitInfo =new UnitInfo();
		StringBuilder yBar = new StringBuilder();
		StringBuilder viewStrs = new StringBuilder();
		StringBuilder name = new StringBuilder();
		
		//--------------------------------
		
		name.append("安全保密策略与审计");
		yBar.append("[");
		//[['现场审查成绩在480分以上',0.2140],['现场审查不合格',0.786]]
		List certConfigRule = dao.findAll(" from CertConfigRule where ruleContent like '%安全保密策略%' or ruleContent like '%安全保密策略%'");
		
		Long koufen;
		Long zongkoufen =new Long(0);
		
		for (int i = 0; i < certConfigRule.size(); i++) {
			CertConfigRule ccr = (CertConfigRule)certConfigRule.get(i);
			Long a = dao.findCount( "select coalesce(sum(scoreNumber),0)  from CertTaskScore where ruleId ="+ccr.getRuleId()+" ");//总扣分
			if(a!=null){
				zongkoufen+=a;
			}
		}
		
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
			String xx;
			System.out.println("扣分/*-/*-/*-/-*/*-/*-/-*"+(double) koufen/1000);
			if(zongkoufen==0){
				xx="0.00";
			}else{
				NumberFormat formatter = new DecimalFormat("0.00");     
				Double x=new Double(((double) koufen/1000)/((double) zongkoufen/1000));     
				System.out.println("biliqwewq eq "+x);
				xx = formatter.format(x*100);    
				System.out.println("biliqwewq eq "+xx);
			}
			if(j==certConfigRule.size()-1){
				yBar.append("['"+CertConfigRule1.getRuleContent()+"',"+(xx+"")+"]");
			}else{
				yBar.append("['"+CertConfigRule1.getRuleContent()+"',"+(xx+"")+"],");    
			}
		}
			
		yBar.append("]");
		
		
		System.out.println(yBar+"divScore");
		request.setAttribute("yBar", yBar);
		request.setAttribute("viewStrs", viewStrs);
		request.setAttribute("name", name);
		return SUCCESS;
	}
	//-----------------------------------------------------------------------------------------------------
	public String listCheckAnalyse() throws Exception{
		String type = request.getParameter("type");
		List listGroup = dao.findAll("from UnitInfo where superiorInfo = '0'");
		request.setAttribute("listGroup", listGroup);
		
		List listConfig = dao.findAll("from CheckConfig");
		request.setAttribute("listConfig", listConfig);
		request.setAttribute("type", type);
		if("score".equals(type))
		{
			String pkUnita=request.getParameter("pkUnita");
			String pkUnitb=request.getParameter("pkUnitb");
			String pkUnitc=request.getParameter("pkUnitc");
			String pkUnitd=request.getParameter("pkUnitd");
			String checkId=request.getParameter("checkId");
			
			StringBuilder histogram = new StringBuilder();
			StringBuilder yBar = new StringBuilder();
			StringBuilder name = new StringBuilder();
			
			SimpleDateFormat sf = new SimpleDateFormat("yyyy");
			String nowYear=sf.format(new Date());
			String lastYear = (Integer.parseInt(nowYear)-1)+"";
			String lastYear2 = (Integer.parseInt(lastYear)-1)+"";
			UnitInfo unitInfo =new UnitInfo();
			String strs1="";
			String strs2="";
			if (StringUtils.isNotEmpty(pkUnita) && StringUtils.isEmpty(pkUnitb) && StringUtils.isEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)) {
				strs1+=" and b.unitNo in( select unitNo from UnitInfo where superiorInfo like '%"+pkUnita+"%') ";
				unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+pkUnita+"'");
				name.append("'"+unitInfo.getUnitName()+"下属单位近三年检查得分情况'");
				strs2=pkUnita;
			}else if(StringUtils.isNotEmpty(pkUnita) && StringUtils.isNotEmpty(pkUnitb) && StringUtils.isEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)){
				strs1+=" and b.unitNo in ( select unitNo from UnitInfo where superiorInfo like '%"+pkUnitb+"%') ";
				unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+pkUnitb+"'");
				name.append("'"+unitInfo.getUnitName()+"下属单位近三年检查得分情况'");
				strs2=pkUnitb;
			}else if(StringUtils.isNotEmpty(pkUnita) && StringUtils.isNotEmpty(pkUnitb) && StringUtils.isNotEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)){
				strs1+=" and b.unitNo in ( select unitNo from UnitInfo where superiorInfo like '%"+pkUnitc+"%') ";
				unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+pkUnitc+"'");
				name.append("'"+unitInfo.getUnitName()+"下属单位近三年检查得分情况'");
				strs2=pkUnitc;
			}else if(StringUtils.isNotEmpty(pkUnita) && StringUtils.isNotEmpty(pkUnitb) && StringUtils.isNotEmpty(pkUnitc) && StringUtils.isNotEmpty(pkUnitd)){
				strs1+=" and b.unitNo in ( select unitNo from UnitInfo where superiorInfo like '%"+pkUnitd+"%') ";
				unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+pkUnitd+"'");
				name.append("'"+unitInfo.getUnitName()+"下属单位近三年检查得分情况'");
				strs2=pkUnitd;
			}else if(StringUtils.isEmpty(pkUnita) && StringUtils.isEmpty(pkUnitb) && StringUtils.isEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)){
				name.append("'所有单位近三年检查得分情况'");
			}
			
			String sql = "SELECT b.unitName,YEAR(a.passedTime) as years, COALESCE(ROUND(a.passedScore,1),0) as score "+
			" FROM CheckTaskInfo a,UnitInfo b  "+
			" WHERE a.pkUnit=b.pkUnit  "+
			" "+strs1+" " +
			" and a.taskName like '%"+checkId+"%' "+
			"AND YEAR(a.passedTime) IN ('"+nowYear+"','"+lastYear+"','"+lastYear2+"') " +
			"ORDER BY b.unitName,YEAR(a.passedTime) ";
			
			String sql1 = "SELECT b.unitName,count(*) as num " +
					" FROM  CheckTaskInfo a,UnitInfo b   " +
					" WHERE a.pkUnit=b.pkUnit "+strs1+" " +
					"AND YEAR(a.passedTime) IN ('"+nowYear+"','"+lastYear+"','"+lastYear2+"') "+
					" and a.taskName like '%"+checkId+"%' "+
					" GROUP BY b.unitName";
			String sql2="SELECT unitName FROM UnitInfo " +
					"WHERE unitName NOT IN(" +
					"SELECT b.unitName AS num FROM  " +
					"CheckTaskInfo a,UnitInfo b " +
					"WHERE a.pkUnit=b.pkUnit "+strs1+" " +
					" and a.taskName like '%"+checkId+"%' "+
					"AND YEAR(a.passedTime) IN ('"+nowYear+"','"+lastYear+"','"+lastYear2+"') " +
					"GROUP BY b.unitName ) " +
					"AND superiorInfo LIKE '%"+strs2+"%'";
			List list1=dao.findAll(sql1);//有几个单位
			List list2=dao.findAll(sql);//单位、年份、扣分信息
			List list3=dao.findAll(sql2);//一共有多少个符合要求的单位
			List strYear=new ArrayList();
			strYear.add(lastYear2);
			strYear.add(lastYear);
			strYear.add(nowYear);
				if (list1.size()>0) {
					yBar.append("[");
					name.append("");
					for(int i=0;i<list1.size();i++){
						Object[] obj1=(Object[])list1.get(i);
						yBar.append("{name:'"+obj1[0].toString()+"',data:[");
						
						for(int a=0;a<strYear.size();a++){
							String year = (String) strYear.get(a);
							boolean temp=false;
							for(int j=0;j<list2.size();j++){
								Object[] obj2=(Object[])list2.get(j);
								if(year.equals(obj2[1].toString())&&obj1[0].toString().equals(obj2[0].toString())){	
									yBar.append(""+obj2[2].toString()+"");
									if(a!=strYear.size()-1){
										yBar.append(",");
									}
									temp=true;
									break;
								}
								
							}
							if(!temp){
								yBar.append("0");
								if(a!=strYear.size()-1){
									yBar.append(",");
								}
							}
						}
						if(list3.size()>0){
							yBar.append("]},");
						}else{
							if(i==list1.size()-1){
								yBar.append("]}");
							}else{
								yBar.append("]},");
							}
						}
					}
					if(list3.size()>0){
						for(int b=0;b<list3.size();b++){
							if(b==list3.size()-1){
								yBar.append("{name:'"+list3.get(b).toString()+"',data:[0,0,0]}");
							}else{
								yBar.append("{name:'"+list3.get(b).toString()+"',data:[0,0,0]},");
							}
						}
					}
					
					yBar.append("]");
				}
				else{
					//name.append("'"+unitInfo.getUnitName()+"下属单位近三年没有审查认证'");
					yBar.append("[{name:'"+unitInfo.getUnitName()+"',data:[]}]");
				}
				
				
				
				
			System.out.println(yBar+"yBar");
			histogram.append("['"+lastYear2+"','"+lastYear+"','"+nowYear+"']");
			request.setAttribute("yBar", yBar);
			request.setAttribute("histogram", histogram);
			request.setAttribute("name", name);
			return toJsp("/jsp/unit/unit/listCheckAnalyse.jsp");
		}	else if("mark".equals(type)){
			String pkUnita=request.getParameter("pkUnita");
			String pkUnitb=request.getParameter("pkUnitb");
			String pkUnitc=request.getParameter("pkUnitc");
			String pkUnitd=request.getParameter("pkUnitd");
			String checkId=request.getParameter("checkId");
			String year=request.getParameter("year");
			
			
			UnitInfo unitInfo =new UnitInfo();
			StringBuilder yBar = new StringBuilder();
			StringBuilder viewStrs = new StringBuilder();
			StringBuilder name = new StringBuilder();
			
			SimpleDateFormat sf = new SimpleDateFormat("yyyy");
			String nowYear=year;//
			if(StringUtils.isEmpty(nowYear)){
				nowYear=sf.format(new Date());
			}
			
			//--------------------------------
			String yearTo=request.getParameter("yearTo");
			String strDate="";
			if(StringUtils.isNotEmpty(nowYear)){
				strDate +=" and YEAR(a.passedTime) >= '"+nowYear+"'";
			}
			if(StringUtils.isNotEmpty(yearTo)){
				strDate +=" and YEAR(a.passedTime) <= '"+yearTo+"'";
			}
			
			//--------------------------------
			
			String strs1="";
			if (StringUtils.isNotEmpty(pkUnita) && StringUtils.isEmpty(pkUnitb) && StringUtils.isEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)) {
				strs1+=" AND g.superiorInfo LIKE '%"+pkUnita+"%' ";
				unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+pkUnita+"'");
				name.append(""+unitInfo.getUnitName()+"下属单位的检查扣分情况");
			}else if(StringUtils.isNotEmpty(pkUnita) && StringUtils.isNotEmpty(pkUnitb) && StringUtils.isEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)){
				strs1+=" and g.superiorInfo like '%"+pkUnitb+"%' ";
				unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+pkUnita+"'");
				name.append(""+unitInfo.getUnitName()+"下属单位的检查扣分情况");
			}else if(StringUtils.isNotEmpty(pkUnita) && StringUtils.isNotEmpty(pkUnitb) && StringUtils.isNotEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)){
				strs1+=" and g.superiorInfo like '%"+pkUnitc+"%' ";
				unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+pkUnita+"'");
				name.append(""+unitInfo.getUnitName()+"下属单位的检查扣分情况");
			}else if(StringUtils.isNotEmpty(pkUnita) && StringUtils.isNotEmpty(pkUnitb) && StringUtils.isNotEmpty(pkUnitc) && StringUtils.isNotEmpty(pkUnitd)){
				strs1+=" and g.superiorInfo like '%"+pkUnitd+"%' ";
				unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+pkUnita+"'");
				name.append(""+unitInfo.getUnitName()+"下属单位的检查扣分情况");
			}else if(StringUtils.isEmpty(pkUnita) && StringUtils.isEmpty(pkUnitb) && StringUtils.isEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)){
				name.append("所有单位的认证扣分情况");
			}
			
			String sql = "SELECT  c.scoreRule,SUM(c.scoreNumber),d.taskId,c.ruleNo,f.ruleContent " +
			" FROM CheckConfigRule f,CheakTaskScore c,CheckTaskInfo d,UnitInfo g" +
			" WHERE  f.ruleId = c.ruleNo AND c.taskId = d.taskId AND d.pkUnit = g.pkUnit AND" +
			" CONCAT(f.ruleId,f.checkId) IN" +
			" (" +
			"	SELECT CONCAT(b.ruleNo,a.taskName) " +
			"	FROM CheckTaskInfo a,CheakTaskScore b " +
			"	WHERE a.taskId = b.taskId " +
			"	AND a.taskName = '"+checkId+"'" +
			"	AND YEAR(d.passedTime)>= '"+nowYear+"' and YEAR(d.passedTime)<= '"+yearTo+"' " +
			" ) AND   YEAR(d.passedTime)>= '"+nowYear+"' and YEAR(d.passedTime)<= '"+yearTo+"' AND d.taskName = '"+checkId+"'" +
			 " "+strs1+" "+
			" GROUP BY c.scoreRule,c.ruleNo";
			
			String sql1 = "SELECT SUM(scoreNumber) " +
			"FROM CheakTaskScore b WHERE taskId IN" +
			"(SELECT taskId FROM CheckTaskInfo a WHERE pkUnit IN" +
			"(SELECT pkUnit FROM UnitInfo g WHERE 1=1 "+strs1+") " +
			" and taskName like '%"+checkId+"%' "+
			" "+strDate+")";

			List list1=dao.findAll(sql);
			//['IE',       26.8],{name: 'Chrome',y: 12.8,sliced: true,selected: true},
			yBar.append("[");
			if(list1.size()>0){
				int a = dao.findRowCount(sql1);
				for(int i=0;i<list1.size();i++){
					Object[] obj = (Object[]) list1.get(i);
					yBar.append("[");
					String percent = getPercent(Integer.parseInt(obj[1].toString()),a );
					yBar.append("'"+obj[4].toString()+"',"+percent+"");
					viewStrs.append(""+obj[0].toString()+"|"+obj[4].toString()+"");
					if(i==list1.size()-1){
						yBar.append("]");
					}else{
						yBar.append("],");
						viewStrs.append(";");
					}
				}
			}else{
				yBar.append("['无扣分项',0]");
			}
			
			String sql3 = "SELECT f.ruleContent,b.scoreNumber,g.pkUnit,g.unitName  " +
			" FROM CheakTaskScore b,CheckTaskInfo a,UnitInfo g,CheckConfigRule f " +
			" WHERE a.taskId = b.taskId AND f.ruleId = b.ruleNo " +
			" AND CONCAT(f.ruleId,f.checkId) IN" +
			" (" +
			"	SELECT CONCAT(b.ruleNo,a.taskName) " +
			"	FROM CheckTaskInfo a,CheakTaskScore b " +
			"	WHERE a.taskId = b.taskId " +
			"	AND a.taskName = '"+checkId+"'" +
			"	AND YEAR(a.passedTime)>= '"+nowYear+"' and YEAR(a.passedTime)<= '"+yearTo+"' " +
			" )"+
			" AND a.pkUnit = g.pkUnit " +
			" "+strDate+"" +
			" and a.taskName like '%"+checkId+"%' "+
			" "+strs1+"" +
			" order by b.scoreRule";
			
			StringBuilder divScore = new StringBuilder();
			List list3 = dao.findAll(sql3);
			if(list3.size()>0){
				for(int j=0;j<list3.size();j++){
					Object[] obj2 = (Object[]) list3.get(j);
					divScore.append(obj2[0]+"|"+obj2[1]+"|"+obj2[3]);
					if(j!=list3.size()-1){
						divScore.append(";");
					}
				}
			}
			request.setAttribute("divScore", divScore);
			yBar.append("]");
			System.out.println(viewStrs+"viewStrs");
			System.out.println(yBar+"yBar");
			System.out.println(divScore+"divScore");
			request.setAttribute("yBar", yBar);
			request.setAttribute("viewStrs", viewStrs);
			request.setAttribute("name", name);
			request.setAttribute("nowYear", "'"+nowYear+"'");
			return toJsp("/jsp/unit/unit/listCheckAnalyseMark.jsp");
		}else{
			return toJsp("/jsp/unit/unit/listCheckAnalyse.jsp");
		}
	}
	public String CheckScoreQuery() throws  Exception{
		String pkUnita=request.getParameter("pkUnita");
		String pkUnitb=request.getParameter("pkUnitb");
		String pkUnitc=request.getParameter("pkUnitc");
		String pkUnitd=request.getParameter("pkUnitd");
		String checkId=request.getParameter("checkId");
		
		StringBuilder histogram = new StringBuilder();
		StringBuilder yBar = new StringBuilder();
		StringBuilder name = new StringBuilder();
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy");
		String nowYear=sf.format(new Date());
		String lastYear = (Integer.parseInt(nowYear)-1)+"";
		String lastYear2 = (Integer.parseInt(lastYear)-1)+"";
		UnitInfo unitInfo =new UnitInfo();
		String strs1="";
		String strs2="";
		if (StringUtils.isNotEmpty(pkUnita) && StringUtils.isEmpty(pkUnitb) && StringUtils.isEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)) {
			strs1+=" and b.unitNo in( select unitNo from UnitInfo where superiorInfo like '%"+pkUnita+"%') ";
			unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+pkUnita+"'");
			name.append("'"+unitInfo.getUnitName()+"下属单位近三年检查得分情况'");
			strs2=pkUnita;
		}else if(StringUtils.isNotEmpty(pkUnita) && StringUtils.isNotEmpty(pkUnitb) && StringUtils.isEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)){
			strs1+=" and b.unitNo in ( select unitNo from UnitInfo where superiorInfo like '%"+pkUnitb+"%') ";
			unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+pkUnitb+"'");
			name.append("'"+unitInfo.getUnitName()+"下属单位近三年检查得分情况'");
			strs2=pkUnitb;
		}else if(StringUtils.isNotEmpty(pkUnita) && StringUtils.isNotEmpty(pkUnitb) && StringUtils.isNotEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)){
			strs1+=" and b.unitNo in ( select unitNo from UnitInfo where superiorInfo like '%"+pkUnitc+"%') ";
			unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+pkUnitc+"'");
			name.append("'"+unitInfo.getUnitName()+"下属单位近三年检查得分情况'");
			strs2=pkUnitc;
		}else if(StringUtils.isNotEmpty(pkUnita) && StringUtils.isNotEmpty(pkUnitb) && StringUtils.isNotEmpty(pkUnitc) && StringUtils.isNotEmpty(pkUnitd)){
			strs1+=" and b.unitNo in ( select unitNo from UnitInfo where superiorInfo like '%"+pkUnitd+"%') ";
			unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+pkUnitd+"'");
			name.append("'"+unitInfo.getUnitName()+"下属单位近三年检查得分情况'");
			strs2=pkUnitd;
		}else if(StringUtils.isEmpty(pkUnita) && StringUtils.isEmpty(pkUnitb) && StringUtils.isEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)){
			name.append("'所有单位近三年检查得分情况'");
		}
		
		String sql = "SELECT b.unitName,YEAR(a.passedTime) as years, COALESCE(ROUND(a.passedScore,1),0) as score "+
		" FROM CheckTaskInfo a,UnitInfo b  "+
		" WHERE a.pkUnit=b.pkUnit  "+
		" "+strs1+" " +
		" and a.taskName like '%"+checkId+"%' "+
		"AND YEAR(a.passedTime) IN ('"+nowYear+"','"+lastYear+"','"+lastYear2+"') " +
		"ORDER BY b.unitName,YEAR(a.passedTime) ";
		
		String sql1 = "SELECT b.unitName,count(*) as num " +
				" FROM  CheckTaskInfo a,UnitInfo b   " +
				" WHERE a.pkUnit=b.pkUnit "+strs1+" " +
				"AND YEAR(a.passedTime) IN ('"+nowYear+"','"+lastYear+"','"+lastYear2+"') "+
				" and a.taskName like '%"+checkId+"%' "+
				" GROUP BY b.unitName";
		String sql2="SELECT unitName FROM UnitInfo " +
				"WHERE unitName NOT IN(" +
				"SELECT b.unitName AS num FROM  " +
				"CheckTaskInfo a,UnitInfo b " +
				"WHERE a.pkUnit=b.pkUnit "+strs1+" " +
				" and a.taskName like '%"+checkId+"%' "+
				"AND YEAR(a.passedTime) IN ('"+nowYear+"','"+lastYear+"','"+lastYear2+"') " +
				"GROUP BY b.unitName ) " +
				"AND superiorInfo LIKE '%"+strs2+"%'";
		List list1=dao.findAll(sql1);//有几个单位
		List list2=dao.findAll(sql);//单位、年份、扣分信息
		List list3=dao.findAll(sql2);//一共有多少个符合要求的单位
		List strYear=new ArrayList();
		strYear.add(lastYear2);
		strYear.add(lastYear);
		strYear.add(nowYear);
			if (list1.size()>0) {
				yBar.append("[");
				name.append("");
				for(int i=0;i<list1.size();i++){
					Object[] obj1=(Object[])list1.get(i);
					yBar.append("{name:'"+obj1[0].toString()+"',data:[");
					
					for(int a=0;a<strYear.size();a++){
						String year = (String) strYear.get(a);
						boolean temp=false;
						for(int j=0;j<list2.size();j++){
							Object[] obj2=(Object[])list2.get(j);
							if(year.equals(obj2[1].toString())&&obj1[0].toString().equals(obj2[0].toString())){	
								yBar.append(""+obj2[2].toString()+"");
								if(a!=strYear.size()-1){
									yBar.append(",");
								}
								temp=true;
								break;
							}
							
						}
						if(!temp){
							yBar.append("0");
							if(a!=strYear.size()-1){
								yBar.append(",");
							}
						}
					}
					if(list3.size()>0){
						yBar.append("]},");
					}else{
						if(i==list1.size()-1){
							yBar.append("]}");
						}else{
							yBar.append("]},");
						}
					}
				}
				if(list3.size()>0){
					for(int b=0;b<list3.size();b++){
						if(b==list3.size()-1){
							yBar.append("{name:'"+list3.get(b).toString()+"',data:[0,0,0]}");
						}else{
							yBar.append("{name:'"+list3.get(b).toString()+"',data:[0,0,0]},");
						}
					}
				}
				
				yBar.append("]");
			}
			else{
				//name.append("'"+unitInfo.getUnitName()+"下属单位近三年没有审查认证'");
				yBar.append("[{name:'"+unitInfo.getUnitName()+"',data:[]}]");
			}
			
			
			
			
		System.out.println(yBar+"yBar");
		histogram.append("['"+lastYear2+"','"+lastYear+"','"+nowYear+"']");
		request.setAttribute("yBar", yBar);
		request.setAttribute("histogram", histogram);
		request.setAttribute("name", name);
		return SUCCESS;
	}
	public String CheckScoreDetailQuery() throws  Exception{
		String pkUnita=request.getParameter("pkUnita");
		String pkUnitb=request.getParameter("pkUnitb");
		String pkUnitc=request.getParameter("pkUnitc");
		String pkUnitd=request.getParameter("pkUnitd");
		String checkId=request.getParameter("checkId");
		String year=request.getParameter("year");
		
		
		UnitInfo unitInfo =new UnitInfo();
		StringBuilder yBar = new StringBuilder();
		StringBuilder viewStrs = new StringBuilder();
		StringBuilder name = new StringBuilder();
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy");
		String nowYear=year;//
		if(StringUtils.isEmpty(nowYear)){
			nowYear=sf.format(new Date());
		}
		
		//--------------------------------
		String yearTo=request.getParameter("yearTo");
		String strDate="";
		if(StringUtils.isNotEmpty(nowYear)){
			strDate +=" and YEAR(b.passedTime) >= '"+nowYear+"'";
		}
		if(StringUtils.isNotEmpty(yearTo)){
			strDate +=" and YEAR(b.passedTime) <= '"+yearTo+"'";
		}
		
		//--------------------------------
		
		String strs1="";
		if (StringUtils.isNotEmpty(pkUnita) && StringUtils.isEmpty(pkUnitb) && StringUtils.isEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)) {
			strs1+=" AND g.superiorInfo LIKE '%"+pkUnita+"%' ";
			unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+pkUnita+"'");
			name.append(""+unitInfo.getUnitName()+"下属单位的检查扣分情况");
		}else if(StringUtils.isNotEmpty(pkUnita) && StringUtils.isNotEmpty(pkUnitb) && StringUtils.isEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)){
			strs1+=" and g.superiorInfo like '%"+pkUnitb+"%' ";
			unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+pkUnita+"'");
			name.append(""+unitInfo.getUnitName()+"下属单位的检查扣分情况");
		}else if(StringUtils.isNotEmpty(pkUnita) && StringUtils.isNotEmpty(pkUnitb) && StringUtils.isNotEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)){
			strs1+=" and g.superiorInfo like '%"+pkUnitc+"%' ";
			unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+pkUnita+"'");
			name.append(""+unitInfo.getUnitName()+"下属单位的检查扣分情况");
		}else if(StringUtils.isNotEmpty(pkUnita) && StringUtils.isNotEmpty(pkUnitb) && StringUtils.isNotEmpty(pkUnitc) && StringUtils.isNotEmpty(pkUnitd)){
			strs1+=" and g.superiorInfo like '%"+pkUnitd+"%' ";
			unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+pkUnita+"'");
			name.append(""+unitInfo.getUnitName()+"下属单位的检查扣分情况");
		}else if(StringUtils.isEmpty(pkUnita) && StringUtils.isEmpty(pkUnitb) && StringUtils.isEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)){
			name.append("所有单位的认证扣分情况");
		}
		
		String sql = "SELECT  c.scoreRule,SUM(c.scoreNumber),d.taskId,c.ruleNo,f.ruleContent " +
		" FROM CheckConfigRule f,CheakTaskScore c,CheckTaskInfo d,UnitInfo g" +
		" WHERE  f.ruleNo = c.ruleNo AND c.taskId = d.taskId AND d.pkUnit = g.pkUnit AND" +
		" CONCAT(f.ruleNo,f.checkId) IN" +
		" (" +
		"	SELECT CONCAT(b.ruleNo,a.taskName) " +
		"	FROM CheckTaskInfo a,CheakTaskScore b " +
		"	WHERE a.taskId = b.taskId " +
		"	AND a.taskName = '"+checkId+"'" +
		"	AND YEAR(a.passedTime)>= '"+nowYear+"' and YEAR(a.passedTime)<= '"+yearTo+"' " +
		" ) AND   YEAR(d.passedTime)>= '"+nowYear+"' and YEAR(d.passedTime)<= '"+yearTo+"' AND d.taskName = '"+checkId+"'" +
		 " "+strs1+" "+
		" GROUP BY c.scoreRule,c.ruleNo";
		
		String sql1 = "SELECT SUM(scoreNumber) " +
		"FROM CheakTaskScore b WHERE taskId IN" +
		"(SELECT taskId FROM CheckTaskInfo WHERE pkUnit IN" +
		"(SELECT pkUnit FROM UnitInfo g WHERE 1=1 "+strs1+") " +
		" and taskName like '%"+checkId+"%' "+
		" "+strDate+")";

		List list1=dao.findAll(sql);
		//['IE',       26.8],{name: 'Chrome',y: 12.8,sliced: true,selected: true},
		yBar.append("[");
		if(list1.size()>0){
			int a = dao.findRowCount(sql1);
			for(int i=0;i<list1.size();i++){
				Object[] obj = (Object[]) list1.get(i);
				yBar.append("[");
				String percent = getPercent(Integer.parseInt(obj[1].toString()),a );
				yBar.append("'"+obj[4].toString()+"',"+percent+"");
				viewStrs.append(""+obj[0].toString()+"|"+obj[4].toString()+"");
				if(i==list1.size()-1){
					yBar.append("]");
				}else{
					yBar.append("],");
					viewStrs.append(";");
				}
			}
		}else{
			yBar.append("['无扣分项',0]");
		}
		
		String sql3 = "SELECT f.ruleContent,a.scoreNumber,g.pkUnit,g.unitName  " +
		" FROM CheakTaskScore a,CheckTaskInfo b,UnitInfo g,CheckConfigRule f " +
		" WHERE a.taskId = b.taskId AND f.ruleNo = a.ruleNo " +
		" AND CONCAT(f.ruleNo,f.checkId) IN" +
		" (" +
		"	SELECT CONCAT(b.ruleNo,a.taskName) " +
		"	FROM CheckTaskInfo a,CheakTaskScore b " +
		"	WHERE a.taskId = b.taskId " +
		"	AND a.taskName = '"+checkId+"'" +
		"	AND YEAR(a.passedTime)>= '"+nowYear+"' and YEAR(a.passedTime)<= '"+yearTo+"' " +
		" )"+
		" AND b.pkUnit = g.pkUnit " +
		" "+strDate+"" +
		" and b.taskName like '%"+checkId+"%' "+
		" "+strs1+"" +
		" order by a.scoreRule";
		
		StringBuilder divScore = new StringBuilder();
		List list3 = dao.findAll(sql3);
		if(list3.size()>0){
			for(int j=0;j<list3.size();j++){
				Object[] obj2 = (Object[]) list3.get(j);
				divScore.append(obj2[0]+"|"+obj2[1]+"|"+obj2[3]);
				if(j!=list3.size()-1){
					divScore.append(";");
				}
			}
		}
		request.setAttribute("divScore", divScore);
		yBar.append("]");
		System.out.println(viewStrs+"viewStrs");
		System.out.println(yBar+"yBar");
		System.out.println(divScore+"divScore");
		request.setAttribute("yBar", yBar);
		request.setAttribute("viewStrs", viewStrs);
		request.setAttribute("name", name);
		request.setAttribute("nowYear", "'"+nowYear+"'");
		return SUCCESS;
	}
	//-----------------------------审查人员认证数统计------------------------------------------		
	public String certNumAnalyse() throws Exception{  
		String timeFrom = request.getParameter("timeFrom");
		String timeTo = request.getParameter("timeTo");
		
		StringBuffer sqlStr = new StringBuffer();
		 if(StringUtils.isNotEmpty(timeFrom)){
			 sqlStr.append(" and a.time >= '"+timeFrom+"'");
		 }
		 if(StringUtils.isNotEmpty(timeTo)){
			 sqlStr.append(" and a.time <= '"+timeTo+"'");
		 }
		StringBuilder histogram = new StringBuilder();
		
		StringBuilder yBars = new StringBuilder();
		StringBuilder name = new StringBuilder();
		String year="";
		
		String sql=	" select replace(a.member_name,' ',''),a.time,count(*) from ( "+
					" select member_name1 member_name,YEAR(passed_time) time from cert_task_info where member_name1 is not null and  member_name1 !='' union all "+
					" select member_name2 member_name,YEAR(passed_time) from cert_task_info where member_name2 is not null and  member_name2 !='' union all "+
					" select member_name3 member_name,YEAR(passed_time) from cert_task_info where member_name3 is not null and  member_name3 !='' union all "+
					" select member_name4 member_name,YEAR(passed_time) from cert_task_info where member_name4 is not null and  member_name4 !='' union all "+
					" select member_name5 member_name,YEAR(passed_time) from cert_task_info where member_name5 is not null and  member_name5 !='' union all "+
					" select member_name6 member_name,YEAR(passed_time) from cert_task_info where member_name6 is not null and  member_name6 !='' union all "+
					" select member_name7 member_name,YEAR(passed_time) from cert_task_info where member_name7 is not null and  member_name7 !='' union all "+
					" select member_name8 member_name,YEAR(passed_time) from cert_task_info where member_name8 is not null and  member_name8 !='' union all "+
					" select member_name9 member_name,YEAR(passed_time) from cert_task_info where member_name9 is not null and  member_name9 !='' union all "+
					" select member_name10 member_name,YEAR(passed_time) from cert_task_info where member_name10 is not null and  member_name10 !='') a where a.time is not null "+sqlStr+" group by a.time,a.member_name order by a.time";
		List list = new ArrayList();

//		//x轴年度: ['1750', '1800', '1850', '1900', '1950', '1999', '2050'],
		yBars.append("[");
		histogram.append("[");
		java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.101:3306/exam?useUnicode=true&amp;characterEncoding=utf-8", "root", "root");  
		Statement stmt = conn.createStatement();  
		ResultSet rs = stmt.executeQuery(sql);  
		while (rs.next()) {  
			Object note[] = new Object[3];  
			for (int i = 0; i < note.length; i++) {  
				note[i] = rs.getObject(i + 1);  
			}  
			    list.add(note);  
		}  
		stmt.close();  
		conn.close();  
		
		List listMember  =dao.findAll("from CodeDict where kindId = '017'");
		List listMemberX = new ArrayList();
		for(int k=0;k<listMember.size();k++){//循环所有的人
			CodeDict codeDict = (CodeDict) listMember.get(k);
			//------------拼X轴---------------
//			histogram.append("'"+codeDict.getCodeName()+"',");
			if(list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					Object[] obj = (Object[])list.get(i);
					if((obj[0].toString().trim()).equals(codeDict.getCodeName().replaceAll(" +",""))){
						histogram.append("'"+codeDict.getCodeName().replaceAll(" +","")+"',");
						listMemberX.add(codeDict.getCodeName().replaceAll(" +",""));
						break;
					}
				}
			}
			
			
			
		}
		if(list.size()>0){
			for(int j=0;j<list.size();j++){
				Object[] obj = (Object[])list.get(j);
				if(j==0){
					year+=""+obj[1]+",";
				}else{
					Object[] obj0 = (Object[])list.get(j-1);
					if(!obj[1].toString().equals(obj0[1].toString())){
						year+=""+obj[1]+",";
					}
				}
			}
//			//y轴 [{name: 'Asia',data: []}, { name: 'Africa', data: [106, 107, 111, 133, 221, 767, 1766]}]
			String[] years = year.split(",");
			
			for(int i=0;i<years.length;i++){//循环所有的年份
				yBars.append("{name:'"+years[i]+"',data:[");
				String yBar ="";
				for(int k=0;k<listMemberX.size();k++){//循环所有的人
				 
					int num=0;
					for(int j=0;j<list.size();j++){//循环查到的数据
						Object[] obj = (Object[])list.get(j);
						//------------拼y轴---------------
						if((obj[0].toString().trim()).equals(listMemberX.get(k).toString().replaceAll(" +","")) && obj[1].toString().trim().equals(years[i])){
							yBar+=obj[2]+",";
							num++;
							break;
						}
					}
					if(num==0){
						yBar+="0,";
					}
				}
				yBar = yBar.substring(0,yBar.length()-1);
				yBars.append(yBar);
				if(i==listMember.size()-1){
					yBars.append("]}");
				}else{
					yBars.append("]},");
				}
			}
		}
		String histograms = histogram.substring(0, histogram.length()-1);
		histograms+="]";
		
		yBars.append("]");
		request.setAttribute("yBars", yBars);
		request.setAttribute("histograms", histograms);
		request.setAttribute("name","按年度统计审查人员认证次数");
		
//		request.setAttribute("name", name);
		System.out.println(yBars);
		System.out.println(histograms);
		System.out.println(year);
//		System.out.println(name);
		return SUCCESS;
	}
//	 xAxis: {
//         ['1750', '1800', '1850', '1900', '1950', '1999', '2050'],
//        tickmarkPlacement: 'on',
//        title: {
//            enabled: false
//        }
//    },
//    yAxis: {
//        title: {
//            text: 'Billions'
//        },
//        labels: {
//            formatter: function() {
//                return this.value / 1000;
//            }
//        }
//    },
//	 series: [{
//         name: 'Asia',
//         data: [502, 635, 809, 947, 1402, 3634, 5268]
//     }, {
//         name: 'Africa',
//         data: [106, 107, 111, 133, 221, 767, 1766]
//     }, {
//         name: 'Europe',
//         data: [163, 203, 276, 408, 547, 729, 628]
//     }, {
//         name: 'America',
//         data: [18, 31, 54, 156, 339, 818, 1201]
//     }, {
//         name: 'Oceania',
//         data: [2, 2, 2, 6, 13, 30, 46]
//     }]
//	public String certNumCount1() throws Exception{
//		String timeFrom = request.getParameter("timeFrom");
//		String timeTo = request.getParameter("timeTo");
//		StringBuffer sqlStr = new StringBuffer();
//		 if(StringUtils.isNotEmpty(timeFrom)){
//			 sqlStr.append(" and a.time >= '"+timeFrom+"'");
//		 }
//		 if(StringUtils.isNotEmpty(timeTo)){
//			 sqlStr.append(" and a.time <= '"+timeTo+"'");
//		 }
//		StringBuilder histogram = new StringBuilder();
//		
//		StringBuilder yBars = new StringBuilder();
//		StringBuilder name = new StringBuilder();
//		String year="";
//		
//		String sql=	" select replace(a.member_name,' ',''),a.time,count(*) from ( "+
//					" select member_name1 member_name,YEAR(passed_time) time from cert_task_info where member_name1 is not null and  member_name1 !='' union all "+
//					" select member_name2 member_name,YEAR(passed_time) from cert_task_info where member_name2 is not null and  member_name2 !='' union all "+
//					" select member_name3 member_name,YEAR(passed_time) from cert_task_info where member_name3 is not null and  member_name3 !='' union all "+
//					" select member_name4 member_name,YEAR(passed_time) from cert_task_info where member_name4 is not null and  member_name4 !='' union all "+
//					" select member_name5 member_name,YEAR(passed_time) from cert_task_info where member_name5 is not null and  member_name5 !='' union all "+
//					" select member_name6 member_name,YEAR(passed_time) from cert_task_info where member_name6 is not null and  member_name6 !='' union all "+
//					" select member_name7 member_name,YEAR(passed_time) from cert_task_info where member_name7 is not null and  member_name7 !='' union all "+
//					" select member_name8 member_name,YEAR(passed_time) from cert_task_info where member_name8 is not null and  member_name8 !='' union all "+
//					" select member_name9 member_name,YEAR(passed_time) from cert_task_info where member_name9 is not null and  member_name9 !='' union all "+
//					" select member_name10 member_name,YEAR(passed_time) from cert_task_info where member_name10 is not null and  member_name10 !='') a  "+sqlStr+" group by a.time,a.member_name order by a.time";
//		List list = new ArrayList();
//
////		//x轴年度: ['1750', '1800', '1850', '1900', '1950', '1999', '2050'],
//		yBars.append("[");
//		histogram.append("[");
//		java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam?useUnicode=true&amp;characterEncoding=utf-8", "root", "123456");  
//		Statement stmt = conn.createStatement();  
//		ResultSet rs = stmt.executeQuery(sql);  
//		while (rs.next()) {  
//			Object note[] = new Object[3];  
//			for (int i = 0; i < note.length; i++) {  
//				note[i] = rs.getObject(i + 1);  
//			}  
//			    list.add(note);  
//		}  
//		stmt.close();  
//		conn.close();  
//		
//		List listMember  =dao.findAll("from CodeDict where kindId = '017'");
//		if(list.size()>0){
//			for(int j=0;j<list.size();j++){
//				Object[] obj = (Object[])list.get(j);
//
//				//------------拼X轴---------------
//				if(j==0){
//					histogram.append("'"+obj[1]+"',");
//					year+=""+obj[1]+",";
//				}else{
//					Object[] obj0 = (Object[])list.get(j-1);
//					if(!obj[1].toString().equals(obj0[1].toString())){
//						histogram.append("'"+obj[1]+"',");
//						year+=""+obj[1]+",";
//					}
//				}
//			}
////			//y轴 [{name: 'Asia',data: []}, { name: 'Africa', data: [106, 107, 111, 133, 221, 767, 1766]}]
//			String[] years = year.split(",");
//			
//			for(int k=0;k<listMember.size();k++){//循环所有的人
//				CodeDict codeDict = (CodeDict) listMember.get(k);
//				yBars.append("{name:'"+codeDict.getCodeName()+"',data:[");
//				String yBar ="";
//				for(int i=0;i<years.length;i++){//循环所有的年份
//					int num=0;
//					for(int j=0;j<list.size();j++){//循环查到的数据
//						Object[] obj = (Object[])list.get(j);
//						//------------拼y轴---------------
//						if((obj[0].toString().trim()).equals(codeDict.getCodeName().replaceAll(" +","")) && obj[1].toString().trim().equals(years[i])){
//							yBar+=obj[2]+",";
//							num++;
//							break;
//						}
//					}
//					if(num==0){
//						yBar+="0,";
//					}
//				}
//				yBar = yBar.substring(0,yBar.length()-1);
//				yBars.append(yBar);
//				if(k==listMember.size()-1){
//					yBars.append("]}");
//				}else{
//					yBars.append("]},");
//				}
//				
//			}
//				
//		}
//
//		String histograms = histogram.substring(0, histogram.length()-1);
//		histograms+="]";
//		
//		yBars.append("]");
//		request.setAttribute("yBars", yBars);
//		request.setAttribute("histograms", histograms);
////		request.setAttribute("name", name);
//		System.out.println(yBars);
//		System.out.println(histograms);
//		System.out.println(year);
////		System.out.println(name);
//		return SUCCESS;
//	}
	public String certNumCount() throws Exception{
		String timeFrom = request.getParameter("timeFrom");
		String timeTo = request.getParameter("timeTo");
		
		StringBuffer sqlStr = new StringBuffer();
		 if(StringUtils.isNotEmpty(timeFrom)){
			 sqlStr.append(" and a.time >= '"+timeFrom+"'");
		 }
		 if(StringUtils.isNotEmpty(timeTo)){
			 sqlStr.append(" and a.time <= '"+timeTo+"'");
		 }
		StringBuilder histogram = new StringBuilder();
		
		StringBuilder yBars = new StringBuilder();
		StringBuilder name = new StringBuilder();
		String year="";
		
		String sql=	" select replace(a.member_name,' ',''),a.time,count(*) from ( "+
					" select member_name1 member_name,YEAR(passed_time) time from cert_task_info where member_name1 is not null and  member_name1 !='' union all "+
					" select member_name2 member_name,YEAR(passed_time) from cert_task_info where member_name2 is not null and  member_name2 !='' union all "+
					" select member_name3 member_name,YEAR(passed_time) from cert_task_info where member_name3 is not null and  member_name3 !='' union all "+
					" select member_name4 member_name,YEAR(passed_time) from cert_task_info where member_name4 is not null and  member_name4 !='' union all "+
					" select member_name5 member_name,YEAR(passed_time) from cert_task_info where member_name5 is not null and  member_name5 !='' union all "+
					" select member_name6 member_name,YEAR(passed_time) from cert_task_info where member_name6 is not null and  member_name6 !='' union all "+
					" select member_name7 member_name,YEAR(passed_time) from cert_task_info where member_name7 is not null and  member_name7 !='' union all "+
					" select member_name8 member_name,YEAR(passed_time) from cert_task_info where member_name8 is not null and  member_name8 !='' union all "+
					" select member_name9 member_name,YEAR(passed_time) from cert_task_info where member_name9 is not null and  member_name9 !='' union all "+
					" select member_name10 member_name,YEAR(passed_time) from cert_task_info where member_name10 is not null and  member_name10 !='') a where a.time is not null "+sqlStr+" group by a.time,a.member_name order by a.time";
		List list = new ArrayList();

//		//x轴年度: ['1750', '1800', '1850', '1900', '1950', '1999', '2050'],
		yBars.append("[");
		histogram.append("[");
		java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.101:3306/exam?useUnicode=true&amp;characterEncoding=utf-8", "root", "root");  
		Statement stmt = conn.createStatement();  
		ResultSet rs = stmt.executeQuery(sql);  
		while (rs.next()) {  
			Object note[] = new Object[3];  
			for (int i = 0; i < note.length; i++) {  
				note[i] = rs.getObject(i + 1);  
			}  
			    list.add(note);  
		}  
		stmt.close();  
		conn.close();  
		
		List listMember  =dao.findAll("from CodeDict where kindId = '017'");
		List listMemberX = new ArrayList();
		for(int k=0;k<listMember.size();k++){//循环所有的人
			CodeDict codeDict = (CodeDict) listMember.get(k);
			//------------拼X轴---------------
//			histogram.append("'"+codeDict.getCodeName()+"',");
			if(list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					Object[] obj = (Object[])list.get(i);
					if((obj[0].toString().trim()).equals(codeDict.getCodeName().replaceAll(" +",""))){
						histogram.append("'"+codeDict.getCodeName().replaceAll(" +","")+"',");
						listMemberX.add(codeDict.getCodeName().replaceAll(" +",""));
						break;
					}
				}
			}
			
			
			
		}
		if(list.size()>0){
			for(int j=0;j<list.size();j++){
				Object[] obj = (Object[])list.get(j);
				if(j==0){
					year+=""+obj[1]+",";
				}else{
					Object[] obj0 = (Object[])list.get(j-1);
					if(!obj[1].toString().equals(obj0[1].toString())){
						year+=""+obj[1]+",";
					}
				}
			}
//			//y轴 [{name: 'Asia',data: []}, { name: 'Africa', data: [106, 107, 111, 133, 221, 767, 1766]}]
			String[] years = year.split(",");
			
			for(int i=0;i<years.length;i++){//循环所有的年份
				yBars.append("{name:'"+years[i]+"',data:[");
				String yBar ="";
				for(int k=0;k<listMemberX.size();k++){//循环所有的人
				 
					int num=0;
					for(int j=0;j<list.size();j++){//循环查到的数据
						Object[] obj = (Object[])list.get(j);
						//------------拼y轴---------------
						if((obj[0].toString().trim()).equals(listMemberX.get(k).toString().replaceAll(" +","")) && obj[1].toString().trim().equals(years[i])){
							yBar+=obj[2]+",";
							num++;
							break;
						}
					}
					if(num==0){
						yBar+="0,";
					}
				}
				yBar = yBar.substring(0,yBar.length()-1);
				yBars.append(yBar);
				if(i==listMember.size()-1){
					yBars.append("]}");
				}else{
					yBars.append("]},");
				}
			}
		}
		String histograms = histogram.substring(0, histogram.length()-1);
		histograms+="]";
		
		yBars.append("]");
		request.setAttribute("yBars", yBars);
		request.setAttribute("histograms", histograms);
//		request.setAttribute("name", name);
		System.out.println(yBars);
		System.out.println(histograms);
		System.out.println(year);
//		System.out.println(name);
		return SUCCESS;
	}
	/**
	 * 2014-07-10 王爽
	 */
	public String listCheckPeople() throws Exception{
		List listPeople = dao.findAll(" from CodeDict where kindId='017' ");
		request.setAttribute("listPeople", listPeople);
		request.setAttribute("num", request.getParameter("num"));
		request.setAttribute("numS", request.getParameter("numS"));
		return SUCCESS;
	}
	public String ajaxCheckPeople() throws Exception{
		String checkp = request.getParameter("checkp");
		System.out.println(checkp+"///////////////////////////////////////////////////////");
		this.Alert_Close("选择成功", "ok");
		return toMessage();
	}
	
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
	
	public String getCodeName(String codeId) throws Exception{
		CodeDict codeDict=(CodeDict) dao.findOne(CodeDict.class, codeId);
		String codeName=codeDict.getCodeName();
		return codeName;
	}
	
	////////////////////////////////////////2015/////////////////////////////////////////
	public String listTaskTODO() throws Exception{
		List resultList = new ArrayList();
		List unitList=dao.findAll("from UnitInfo");
		for(int i=0;i<unitList.size();i++){
			UnitInfo unitInfo=(UnitInfo)unitList.get(i);
			resultList.add(unitInfo.getUnitNo()+"，"+unitInfo.getUnitName()+"，"+Common.getPinYinHeadChar(unitInfo.getUnitName()));
		}
		String unitData = Java2JSON.list2jsonSuggest(resultList);
		request.setAttribute("unitData",unitData);
		
		String unitName = request.getParameter("unitName");
		String region = request.getParameter("region");
		String unitCategory = request.getParameter("unitCategory");
		String time = request.getParameter("time");
		String check1 = request.getParameter("check1");
		String check2 = request.getParameter("check2");
		String check3 = request.getParameter("check3");
		String check4 = request.getParameter("check4");
		if(StringUtils.isEmpty(check1) && StringUtils.isEmpty(check2) && StringUtils.isEmpty(check3) && StringUtils.isEmpty(check4)){
			check4="4";
		}
		String sql="";
		if(StringUtils.isNotEmpty(unitName)){
			sql+=" and pkUnit in(select pkUnit from UnitInfo where unitName like '%"+unitName.trim()+"%')";
		}
		
		if(StringUtils.isNotEmpty(region)){
			sql+=" and pkUnit in(select pkUnit from UnitInfo where unitLoginAddress like '%"+region.trim()+"%')";
		}
		
		if(StringUtils.isNotEmpty(unitCategory)){
			sql+=" and pkUnit in(select pkUnit from UnitInfo where unitCategory = '"+unitCategory.trim()+"')";
		}
//		if(StringUtils.isNotEmpty(time)){
//			sql+=" and  passedTime= '"+time.trim()+"' ";
//		}
		String hql="and ( certStatus='' ";
		if(StringUtils.isNotEmpty(check1)){
			hql+=" or  certStatus in ('012001','012002','012003') ";
		}
		if(StringUtils.isNotEmpty(check2)){
			hql+="  or certStatus='012001' ";
		}
		if(StringUtils.isNotEmpty(check3)){
			hql+=" or certStatus='012001'  ";
		}
		if(StringUtils.isNotEmpty(check4)){
			hql+=" or certStatus in ('012002','012003')  ";
		}
		hql+=")";
		List listData=dao.findByPage("from CertTaskInfo where 1=1 "+sql+hql+"  order by pkUnit,certStatus");
		for (int i = 0; i < listData.size(); i++) {
			CertTaskInfo cti = (CertTaskInfo)listData.get(i);
			UnitInfo ui = (UnitInfo)dao.findOne(UnitInfo.class, Integer.parseInt(cti.getPkUnit().trim()));
			cti.setUnitInfo(ui);
		}
		
		request.setAttribute("unitName", unitName);
		request.setAttribute("region", region);
		request.setAttribute("unitCategory", unitCategory);
		request.setAttribute("check1", check1);
		request.setAttribute("time", time);
		request.setAttribute("check2",check2);
		request.setAttribute("check3", check3);
		request.setAttribute("check4", check4);
		request.setAttribute("listData", listData);
		return toJsp("/jsp/censor/censor/listTaskTODO.jsp");
	}
	/**
	 * 外出携带扣分比例统计 王爽2016.3.9
	 * @return 
	 * @throws Exception
	 */
	public String listCertAnalyse111() throws  Exception{
		UnitInfo unitInfo =new UnitInfo();
		StringBuilder yBar = new StringBuilder();
		StringBuilder viewStrs = new StringBuilder();
		StringBuilder name = new StringBuilder();
		
		//--------------------------------
		
		name.append("各单位在外出携带管理上的扣分比");
		yBar.append("[");
		//[['现场审查成绩在480分以上',0.2140],['现场审查不合格',0.786]]
		List certConfigRule = dao.findAll(" from CertConfigRule where ruleContent like '%外出%' or ruleContent like '%携带%'");
		
		Long koufen;
		Long zongkoufen =new Long(0);
		
		for (int i = 0; i < certConfigRule.size(); i++) {
			CertConfigRule ccr = (CertConfigRule)certConfigRule.get(i);
			Long a = dao.findCount( "select sum(scoreNumber)  from CertTaskScore where ruleId ="+ccr.getRuleId()+" ");//总扣分
			if(a!=null){
				zongkoufen+=a;
			}
		}
		
		System.out.println("总扣分/*-/*-/*-/-*/*-/*-/-*"+zongkoufen);
		for (int j = 0; j < certConfigRule.size(); j++) {
			CertConfigRule CertConfigRule1 = (CertConfigRule) certConfigRule.get(j);
			if (CertConfigRule1.getRuleNo()!=null&&CertConfigRule1.getRuleNo()!=null) {
				 koufen = dao.findCount(" select sum(scoreNumber) from CertTaskScore where ruleId ='"+CertConfigRule1.getRuleNo()+"' ");                 	
			}else {
				  koufen=0L;   
			}
			if (koufen==null) {
				 koufen=0L;      
			}
			String xx;
			System.out.println("扣分/*-/*-/*-/-*/*-/*-/-*"+(double) koufen/1000);
			if(zongkoufen==0){
				xx="0.00";
			}else{
				NumberFormat formatter = new DecimalFormat("0.00");     
				Double x=new Double(((double) koufen/1000)/((double) zongkoufen/1000));     
				System.out.println("biliqwewq eq "+x);
				xx = formatter.format(x*100);    
				System.out.println("biliqwewq eq "+xx);
			}
			
			if(j==certConfigRule.size()-1){
				yBar.append("['"+CertConfigRule1.getRuleContent()+"',"+(xx+"")+"]");
			}else{
				yBar.append("['"+CertConfigRule1.getRuleContent()+"',"+(xx+"")+"],");    
			}
		}
			
		yBar.append("]");
		
		System.out.println(yBar+"divScore");
		request.setAttribute("yBar", yBar);
		request.setAttribute("viewStrs", viewStrs);
		request.setAttribute("name", name);
		return SUCCESS;
	}
	public String listCertAnalyse112() throws  Exception{
		UnitInfo unitInfo =new UnitInfo();
		StringBuilder yBar = new StringBuilder();
		StringBuilder viewStrs = new StringBuilder();
		StringBuilder name = new StringBuilder();
		
		//--------------------------------
		
		name.append("各单位在安全保密措施管理上的扣分比");
		yBar.append("[");
		//[['现场审查成绩在480分以上',0.2140],['现场审查不合格',0.786]]
		List certConfigRule = dao.findAll(" from CertConfigRule where ruleContent like '%安全产品%' or  ruleContent like '%控制%'");
		
		Long koufen;
		Long zongkoufen =new Long(0);
		
		for (int i = 0; i < certConfigRule.size(); i++) {
			CertConfigRule ccr = (CertConfigRule)certConfigRule.get(i);
			Long a = dao.findCount( "select sum(scoreNumber)  from CertTaskScore where ruleId ="+ccr.getRuleId()+" ");//总扣分
			if(a!=null){
				zongkoufen+=a;
			}
		}
		for (int j = 0; j < certConfigRule.size(); j++) {
			CertConfigRule CertConfigRule1 = (CertConfigRule) certConfigRule.get(j);
			if (CertConfigRule1.getRuleNo()!=null&&CertConfigRule1.getRuleNo()!=null) {
				 koufen = dao.findCount(" select sum(scoreNumber) from CertTaskScore where ruleId ='"+CertConfigRule1.getRuleNo()+"' ");                 	
			}else {
				  koufen=0L;   
			}
			if (koufen==null) {
				 koufen=0L;      
			}
			String xx;
			System.out.println("扣分/*-/*-/*-/-*/*-/*-/-*"+(double) koufen/1000);
			if(zongkoufen==0){
				xx="0.00";
			}else{
				NumberFormat formatter = new DecimalFormat("0.00");     
				Double x=new Double(((double) koufen/1000)/((double) zongkoufen/1000));     
				System.out.println("biliqwewq eq "+x);
				xx = formatter.format(x*100);    
				System.out.println("biliqwewq eq "+xx);
			}
			
			if(j==certConfigRule.size()-1){
				yBar.append("['"+CertConfigRule1.getRuleContent()+"',"+(xx+"")+"]");
			}else{
				yBar.append("['"+CertConfigRule1.getRuleContent()+"',"+(xx+"")+"],");    
			}
		}
			
		yBar.append("]");
		
		System.out.println(yBar+"divScore");
		request.setAttribute("yBar", yBar);
		request.setAttribute("viewStrs", viewStrs);
		request.setAttribute("name", name);
		return SUCCESS;
	}
	/**
	 * 在0-size区间内exceptIndex区间外生成随机数
	 * @param size
	 * @param exceptIndex
	 * @return
	 */
	private int randIndexInScope(int size,int[] exceptIndex) throws Exception{
		if(exceptIndex.length==2 && exceptIndex[1]-exceptIndex[0]+1==size)
			throw new Exception("范围越界");
		Random ra = new Random();
		int num = ra.nextInt(size);
		int length = exceptIndex.length;
		boolean isInScope = false;
		while(!isInScope){
			int i=0;
			for(;i<length;i+=2)
				if(num<exceptIndex[i])
					break;
			if(i>0)
				if(num>exceptIndex[i-1])
					isInScope = true;
				else{
					num += (exceptIndex[i-1]-exceptIndex[i-2]+1);
					num %= size;
				}
			else
				isInScope = true;
		}
		return num;
	}
	private String getQuestionTitle(int i) {
		if(i==1)return "一";
		if(i==2)return "二";
		if(i==3)return "三";
		if(i==4)return "四";
		if(i==5)return "五";
		return "";
	}
	/**
	 * 通过传入的QuestionListIndex的List生成被除去的区间
	 * @param except
	 * @return
	 */
	private int[] questionListIndexToExceptIndex(List<QuestionListIndex> except){
		int size = except.size();
		int[] exceptIndex = new int[size*2];
		for(int i=0;i<size;i++){
			exceptIndex[i*2]=except.get(i).getFrom();
			exceptIndex[i*2+1]=except.get(i).getTo();
		}
		if(size>1){
			for(int i=1;i<size;i++){
				int j=i;
				int temp1 = exceptIndex[2*j];
				int temp2 = exceptIndex[2*j+1];
				while(j>0 && temp1<exceptIndex[2*j-2]){
					exceptIndex[2*j]=exceptIndex[2*j-2];
					exceptIndex[2*j+1]=exceptIndex[2*j-1];
					j--;
				}
				exceptIndex[2*j]=temp1;
				exceptIndex[2*j+1]=temp2;
			}
		}
		//若遇两个区间毗邻则合并
		int curSize = 2;
		for(int k = 2;k<exceptIndex.length;k+=2){
			if(exceptIndex[curSize-1]-exceptIndex[k]>=-1){
				if(exceptIndex[curSize-1]<exceptIndex[k+1])
					exceptIndex[curSize-1]=exceptIndex[k+1];
			}
			else{
				exceptIndex[curSize]=exceptIndex[k];
				exceptIndex[curSize+1]=exceptIndex[k+1];
				curSize+=2;
			}
		}
		if(exceptIndex.length==0)
			curSize=0;
		int[] resultExceptIndex = new int[curSize];
		for(int i=0;i<curSize;i++)
			resultExceptIndex[i]=exceptIndex[i];
		return resultExceptIndex;
	}
	public String listCertAnalyse10() throws  Exception{
		UnitInfo unitInfo =new UnitInfo();
		StringBuilder yBar = new StringBuilder();
		StringBuilder viewStrs = new StringBuilder();
		StringBuilder name = new StringBuilder();
		
		//--------------------------------
		
		name.append("上市公司统计分析");
		
//		String sql = "SELECT a.scoreRule,count(*),a.certTaskId,a.ruleId,d.ruleContent " +
//				"FROM CertTaskScore a,CertTaskInfo b,UnitInfo c,CertConfigRule d " +
//				"WHERE a.certTaskId = b.certTaskId AND b.pkUnit = c.pkUnit AND a.ruleId = d.ruleId " +
//				""+strs1+str+strDate+"  " +
//				"GROUP BY a.scoreRule,a.ruleId ";
//		
//		String sql1 = "SELECT count(*) FROM CertTaskInfo b WHERE b.pkUnit IN" +
//		"(SELECT pkUnit FROM UnitInfo c WHERE 1=1 "+strs1+") "+str+strDate+" ";

//		List list1=dao.findAll(sql);
		//['IE',       26.8],{name: 'Chrome',y: 12.8,sliced: true,selected: true},
		double a=dao.findRowCount("select count(*) from UnitInfo where listedCompanies='1' and listedCompanies is not null ");  
		double b=dao.findRowCount("select count(*) from UnitInfo where listedCompanies='2' and listedCompanies is not null ");  
		if (a!=0&&b!=0) {
			
		
		double percent=a/b;
		System.out.println("数字"+percent+"----"+a+"-----"+b+"****");
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMinimumFractionDigits(4);
		String percenta = nf.format(percent);
		System.out.println("数字"+percenta+"----"+a+"-----"+b+"****");
		String percentb =String.valueOf((1-Double.valueOf(percenta)));
		yBar.append("[['已经上市的公司',"+percenta+"],['未上市的公司',"+percentb+"]]");
		}else {
			yBar.append("[['已经上市的公司',"+0+"],['未上市的公司',"+0+"]]");                         
		}
		
//		if(list1.size()>0){
//			int a = dao.findRowCount(sql1);
//			for(int i=0;i<list1.size();i++){
//				Object[] obj = (Object[]) list1.get(i);
//				String sql3 = "SELECT count(*) FROM CertTaskInfo b WHERE b.pkUnit IN" +
//				"(SELECT pkUnit FROM UnitInfo c WHERE 1=1 "+strs1+") "+str+strDate+" " +
//				" and certTaskId in(select certTaskId from CertTaskScore where ruleId = '"+obj[3]+"')";
//				int b = dao.findRowCount(sql3);
//				yBar.append("[");
//				if(obj[1]==null){
//					obj[1]=0;
//				}
//				String percent = getPercent(b,a );
//				yBar.append("'"+obj[0].toString()+"',"+percent+"");
//				viewStrs.append(""+obj[0].toString()+":"+obj[4].toString()+"");
//				if(i==list1.size()-1){
//					yBar.append("]");
//				}else{
//					yBar.append("],");
//					viewStrs.append(",");
//				}
//			}
//		}else{
//			yBar.append("['无扣分项',0]");
//		}
		
//		String sql3 = "SELECT a.scoreRule as scoreRule,a.scoreNumber as scoreNumber,b.pkUnit as pkUnit,c.unitName as unitName " +
//		"FROM CertTaskScore a,CertTaskInfo b,UnitInfo c " +
//		"WHERE a.certTaskId = b.certTaskId " +
//		"AND b.pkUnit = c.pkUnit " +
//		""+strs1+str+strDate+"" +
//		"order by a.scoreRule";
//		StringBuilder divScore = new StringBuilder();
//		List list3 = dao.findAll(sql3);
//		for(int j=0;j<list3.size();j++){
//			Object[] obj2 = (Object[]) list3.get(j);
//			divScore.append(obj2[0]+","+obj2[1]+","+obj2[3]);
//			if(j!=list3.size()-1){
//				divScore.append(";");
//			}
//		}
//		request.setAttribute("divScore", divScore);
//		yBar.append("]");
		System.out.println(yBar+"divScore");
		request.setAttribute("yBar", yBar);
		request.setAttribute("viewStrs", viewStrs);
		request.setAttribute("name", name);
		return toJsp("/jsp/censor/censor/listCertAnalyse10.jsp");  
	}
	
	
	/**
	 * 黄嵩凯 工作统计分析 
	 * @return
	 * @throws Exception
	 */
	
	
	public String certNumAnalyse123456() throws Exception{      
		String timeFrom = request.getParameter("timeFrom");
		String timeTo = request.getParameter("timeTo");
		
		StringBuffer sqlStr = new StringBuffer();
		 if(StringUtils.isNotEmpty(timeFrom)){
			 sqlStr.append(" and a.time >= '"+timeFrom+"'");
		 }
		 if(StringUtils.isNotEmpty(timeTo)){
			 sqlStr.append(" and a.time <= '"+timeTo+"'");
		 }
		StringBuilder histogram = new StringBuilder();
		
		StringBuilder yBars = new StringBuilder();
		StringBuilder name = new StringBuilder();
		String year="";
		
		String sql=	" select replace(a.member_name,' ',''),a.time,count(*) from ( "+
					" select member_name1 member_name,YEAR(passed_time) time from cert_task_info where member_name1 is not null and  member_name1 !='' union all "+
					" select member_name2 member_name,YEAR(passed_time) from cert_task_info where member_name2 is not null and  member_name2 !='' union all "+
					" select member_name3 member_name,YEAR(passed_time) from cert_task_info where member_name3 is not null and  member_name3 !='' union all "+
					" select member_name4 member_name,YEAR(passed_time) from cert_task_info where member_name4 is not null and  member_name4 !='' union all "+
					" select member_name5 member_name,YEAR(passed_time) from cert_task_info where member_name5 is not null and  member_name5 !='' union all "+
					" select member_name6 member_name,YEAR(passed_time) from cert_task_info where member_name6 is not null and  member_name6 !='' union all "+
					" select member_name7 member_name,YEAR(passed_time) from cert_task_info where member_name7 is not null and  member_name7 !='' union all "+
					" select member_name8 member_name,YEAR(passed_time) from cert_task_info where member_name8 is not null and  member_name8 !='' union all "+
					" select member_name9 member_name,YEAR(passed_time) from cert_task_info where member_name9 is not null and  member_name9 !='' union all "+
					" select member_name10 member_name,YEAR(passed_time) from cert_task_info where member_name10 is not null and  member_name10 !='') a where a.time is not null "+sqlStr+" group by a.time,a.member_name order by a.time";
		List list = new ArrayList();

//		//x轴年度: ['1750', '1800', '1850', '1900', '1950', '1999', '2050'],
		    
		histogram.append("[");
		java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.101:3306/exam?useUnicode=true&amp;characterEncoding=utf-8", "root", "root");  
		Statement stmt = conn.createStatement();  
		ResultSet rs = stmt.executeQuery(sql);  
		while (rs.next()) {  
			Object note[] = new Object[3];  
			for (int i = 0; i < note.length; i++) {  
				note[i] = rs.getObject(i + 1);  
			}  
			    list.add(note);  
		}  
		stmt.close();  
		conn.close();  
		
		List listMember  =dao.findAll("from CodeDict where kindId = '017'");
		List listMemberX = new ArrayList();
		for(int k=0;k<listMember.size();k++){//循环所有的人
			CodeDict codeDict = (CodeDict) listMember.get(k);
			//------------拼X轴---------------
//			histogram.append("'"+codeDict.getCodeName()+"',");
			if(list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					Object[] obj = (Object[])list.get(i);
					if((obj[0].toString().trim()).equals(codeDict.getCodeName().replaceAll(" +",""))){
						histogram.append("'"+codeDict.getCodeName().replaceAll(" +","")+"',");
						listMemberX.add(codeDict.getCodeName().replaceAll(" +",""));
						break;
					}
				}
			}
			
			
			
		}
		if(list.size()>0){
			for(int j=0;j<list.size();j++){
				Object[] obj = (Object[])list.get(j);
				if(j==0){
					year+=""+obj[1]+",";
				}else{
					Object[] obj0 = (Object[])list.get(j-1);
					if(!obj[1].toString().equals(obj0[1].toString())){
						year+=""+obj[1]+",";
					}
				}
			}
//			//y轴 [{name: 'Asia',data: []}, { name: 'Africa', data: [106, 107, 111, 133, 221, 767, 1766]}]
			String[] years = year.split(",");
			
			//for(int i=0;i<years.length;i++){//循环所有的年份
				//yBars.append("{name:'"+years[i]+"',data:[");
				String yBar ="";
				String yBar1 ="";
				
				
				String histograms = histogram.substring(0, histogram.length()-1);
				String[] names = histograms.split(",");
				for(int k=0;k<listMemberX.size();k++){//循环所有的人
					int num=0;
					for(int j=0;j<list.size();j++){//循环查到的数据
						Object[] obj = (Object[])list.get(j);
						//------------拼y轴---------------
						if((obj[0].toString().trim()).equals(listMemberX.get(k).toString().replaceAll(" +","")) && obj[1].toString().trim().equals(years[0])){
							yBar1+=obj[2]+",";
							num++;
							break;
						}
					}
					if(num==0){
						yBar1+="0,";      
					}
					
					
					
					
					
				 
					
				}
				yBar1 = yBar1.substring(0,yBar1.length()-1);   
				String[] shuliang = yBar1.split(",");
				for(int k=0;k<listMemberX.size();k++){
					  
					
					if(k==listMemberX.size()-1){  
						yBar+="["+names[k]+","+(shuliang[k]+"")+"]";  
					}else{
						yBar+="["+names[k]+","+(shuliang[k]+"")+"],";      
					}   
					
					
				}
				   
				yBars.append(yBar);
				//if(i==listMember.size()-1){
				//	yBars.append("]}");
				//}else{
				//	yBars.append("]},");
				//}
			//}
		}
		//[{name:'2010',data:[1,1,1,1,1,1,1,0,0,0,0]},{name:'2014',data:[0,0,0,0,0,0,0,0,0,1,1]},{name:'2016',data:[0,0,0,0,0,0,0,1,1,0,0]},]
		//['曹蕾','仇新梁','楚健','高洪滨','葛杨','李宇明','梁学贵','测试人员1','测试人员2','测试人员4','测试人员7']
		// 2010,2014,2016,  
		String histograms = histogram.substring(0, histogram.length()-1);
		histograms+="]";
		
		yBars.append("]");
		request.setAttribute("yBars", yBars);  
		request.setAttribute("yBar", yBars);  
		request.setAttribute("histograms", histograms);
		request.setAttribute("name","工作量统计分析");  
		
//		request.setAttribute("name", name);
		System.out.println(yBars);
		System.out.println(histograms);
		System.out.println(year);
//		System.out.println(name);
		return toJsp("/jsp/censor/censor/listCertAnalyse666.jsp");    
	}
	
	
	
	
	
	
	
	/**
	 * 黄嵩凯
	 * @return
	 * @throws Exception
	 */
	
	//-----------------------------审查人员认证数统计------------------------------------------		
	public String certNumAnalysetest() throws Exception{
		String timeFrom = request.getParameter("timeFrom");
		String timeTo = request.getParameter("timeTo");
		
		StringBuffer sqlStr = new StringBuffer();
		 if(StringUtils.isNotEmpty(timeFrom)){
			 sqlStr.append(" and a.time >= '"+timeFrom+"'");
		 }
		 if(StringUtils.isNotEmpty(timeTo)){
			 sqlStr.append(" and a.time <= '"+timeTo+"'");
		 }
		StringBuilder histogram = new StringBuilder();
		
		StringBuilder yBars = new StringBuilder();
		StringBuilder name = new StringBuilder();
		String year="";
		
		String sql=	" select replace(a.member_name,' ',''),a.time,count(*) from ( "+
					" select member_name1 member_name,YEAR(passed_time) time from cert_task_info where member_name1 is not null and  member_name1 !='' union all "+
					" select member_name2 member_name,YEAR(passed_time) from cert_task_info where member_name2 is not null and  member_name2 !='' union all "+
					" select member_name3 member_name,YEAR(passed_time) from cert_task_info where member_name3 is not null and  member_name3 !='' union all "+
					" select member_name4 member_name,YEAR(passed_time) from cert_task_info where member_name4 is not null and  member_name4 !='' union all "+
					" select member_name5 member_name,YEAR(passed_time) from cert_task_info where member_name5 is not null and  member_name5 !='' union all "+
					" select member_name6 member_name,YEAR(passed_time) from cert_task_info where member_name6 is not null and  member_name6 !='' union all "+
					" select member_name7 member_name,YEAR(passed_time) from cert_task_info where member_name7 is not null and  member_name7 !='' union all "+
					" select member_name8 member_name,YEAR(passed_time) from cert_task_info where member_name8 is not null and  member_name8 !='' union all "+
					" select member_name9 member_name,YEAR(passed_time) from cert_task_info where member_name9 is not null and  member_name9 !='' union all "+
					" select member_name10 member_name,YEAR(passed_time) from cert_task_info where member_name10 is not null and  member_name10 !='') a where a.time is not null "+sqlStr+" group by a.time,a.member_name order by a.time";
		List list = new ArrayList();

//		//x轴年度: ['1750', '1800', '1850', '1900', '1950', '1999', '2050'],
		yBars.append("[");
		histogram.append("[");  
		java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.101:3306/exam?useUnicode=true&amp;characterEncoding=utf-8", "root", "root");  
		Statement stmt = conn.createStatement();  
		ResultSet rs = stmt.executeQuery(sql);  
		while (rs.next()) {  
			Object note[] = new Object[3];  
			for (int i = 0; i < note.length; i++) {  
				note[i] = rs.getObject(i + 1);  
			}  
			    list.add(note);  
		}  
		stmt.close();  
		conn.close();  
		
		List listMember  =dao.findAll("from CodeDict where kindId = '017'");
		List listMemberX = new ArrayList();
		for(int k=0;k<listMember.size();k++){//循环所有的人
			CodeDict codeDict = (CodeDict) listMember.get(k);
			//------------拼X轴---------------
//			histogram.append("'"+codeDict.getCodeName()+"',");
			if(list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					Object[] obj = (Object[])list.get(i);
					if((obj[0].toString().trim()).equals(codeDict.getCodeName().replaceAll(" +",""))){
						histogram.append("'"+codeDict.getCodeName().replaceAll(" +","")+"',");
						listMemberX.add(codeDict.getCodeName().replaceAll(" +",""));
						break;
					}
				}
			}
			
			
			
		}
		if(list.size()>0){
			for(int j=0;j<list.size();j++){
				Object[] obj = (Object[])list.get(j);
				if(j==0){
					year+=""+obj[1]+",";
				}else{
					Object[] obj0 = (Object[])list.get(j-1);
					if(!obj[1].toString().equals(obj0[1].toString())){
						year+=""+obj[1]+",";
					}
				}
			}
//			//y轴 [{name: 'Asia',data: []}, { name: 'Africa', data: [106, 107, 111, 133, 221, 767, 1766]}]
			String[] years = year.split(",");
			
			for(int i=0;i<years.length;i++){//循环所有的年份
				yBars.append("{name:'"+years[i]+"',data:[");
				String yBar ="";
				for(int k=0;k<listMemberX.size();k++){//循环所有的人
				 
					int num=0;
					for(int j=0;j<list.size();j++){//循环查到的数据
						
						String histograms1 = histogram.substring(0, histogram.length()-1);
						String[] name1 = histograms1.split(",");
//						if(j==list.size()-1){
//							yBar+="["+name1[j]+","+(xx+"")+"]";
//						}else{
//							yBar+="["+name1[j]+","+(xx+"")+"],";          
//						}
//						
						Object[] obj = (Object[])list.get(j);
						//------------拼y轴---------------
						if((obj[0].toString().trim()).equals(listMemberX.get(k).toString().replaceAll(" +","")) && obj[1].toString().trim().equals(years[i])){
							yBar+=obj[2]+",";
							num++;
							break;
						}else {
							if(j==list.size()-1){
								yBar+="["+name1[j]+","+(0+"")+"]";  
							}else{
								yBar+="["+name1[j]+","+(0+"")+"],";          
							}
						}
						
					}
					
					if(num==0){
						yBar+="0,";
					}
				}
				yBar = yBar.substring(0,yBar.length()-1);
				yBars.append(yBar);
			
			}
		}
		String histograms = histogram.substring(0, histogram.length()-1);
		
		histograms+="]";
		
		yBars.append("]");
		request.setAttribute("yBars", yBars);
		request.setAttribute("histograms", histograms);
//		request.setAttribute("name", name);
		System.out.println(yBars);
		System.out.println(histograms);
		System.out.println(year);
//		System.out.println(name);
		return toJsp("/jsp/censor/count/certNumAnalyse123456.jsp");  
	}
	
}
