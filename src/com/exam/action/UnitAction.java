package com.exam.action;

import com.exam.po.*;
import com.xunj.action.common.AbstractAction;
import com.xunj.core.Common;
import com.xunj.core.Java2JSON;
import com.xunj.po.UploadFile;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

//import com.exam.po.StaffInfo;
//import com.sun.org.apache.commons.beanutils.PropertyUtils;


public class UnitAction extends AbstractAction{

	private static final long serialVersionUID = 4747379774042416043L;
	
	private UnitInfo unitInfo;
	//private StaffInfo staffInfo; 
	private File doc;
	private String docFilePath;
	private String docContentType;
	private String docFileName;
	
	
	public String getDocFilePath() {
		return docFilePath;
	}

	public void setDocFilePath(String docFilePath) {
		this.docFilePath = docFilePath;
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

	public File getDoc() {
		return doc;
	}

	public void setDoc(File doc) {
		this.doc = doc;
	}
	public UnitInfo getUnitInfo() {
		return unitInfo;
	}

	public void setUnitInfo(UnitInfo unitInfo) {
		this.unitInfo = unitInfo;
	}

//	public StaffInfo getStaffInfo() {
//		return staffInfo;
//	}
//
//	public void setStaffInfo(StaffInfo staffInfo) {
//		this.staffInfo = staffInfo;
//	}
	public String certificationProcess() throws Exception{
		String certTaskId=request.getParameter("certTaskId");
		String fileType=request.getParameter("fileType");
		System.out.println("certTaskId:"+certTaskId);
		request.setAttribute("certTaskId", certTaskId);
		request.setAttribute("fileType", fileType);
		if(StringUtils.isNotEmpty(certTaskId)){
			CertTaskInfo cti = (CertTaskInfo)dao.findOne(CertTaskInfo.class,Integer.parseInt(certTaskId));
			request.setAttribute("applicationStatus", cti.getApplicationStatus());
			request.setAttribute("certStatus", cti.getCertStatus());
			request.setAttribute("passedTime", cti.getPassedTime());
		}
		
		return SUCCESS;
	}
	public String certificationAffirm() throws Exception{
		String certTaskId=request.getParameter("certTaskId");
		System.out.println("certTaskId:"+certTaskId);
		CertTaskInfo certTaskInfo=(CertTaskInfo)dao.findFirst("from CertTaskInfo where certTaskId='"+certTaskId+"'");
		request.setAttribute("certTaskInfo", certTaskInfo);
		request.setAttribute("certTaskId", certTaskId);
		return SUCCESS;
	}
	public String updateCertificationAffirm() throws Exception{
		String certTaskId=request.getParameter("certTaskId");
		String certStatus=request.getParameter("certStatus");
		CertTaskInfo certTaskInfo=(CertTaskInfo)dao.findFirst("from CertTaskInfo where certTaskId='"+certTaskId+"'");
		certTaskInfo.setCertStatus(certStatus);
		dao.update(certTaskInfo);
		Alert_GoUrl("��֤���","closeCurrent","certificationProcess","");
		return this.toResult();
	}
	public String checkFlow() throws Exception{
		String taskId = request.getParameter("taskId");
		CheckTaskInfo cti=(CheckTaskInfo)dao.findOne(CheckTaskInfo.class, Integer.parseInt(taskId));
		request.setAttribute("taskId", taskId);
		request.setAttribute("pkUnit", cti.getPkUnit());
		request.setAttribute("checkResult", cti.getCheckResult());
		return SUCCESS;
	}
	public String listUnit() throws Exception{
	    StringBuilder sql = new StringBuilder();
		sql.append("");
		if(POST()) {
			String unitGroup=request.getParameter("unitGroup");
			System.out.println("���֣�"+unitGroup);
			if(StringUtils.isNotEmpty(unitGroup)){
				sql.append(" and unitGroup = '"+unitGroup+"'");
			}
		    request.setAttribute("unitGroup", unitGroup);
			String unitName=request.getParameter("unitName");
			if(StringUtils.isNotEmpty(unitName)){
				sql.append(" and unitName like '%"+unitName+"%'");
			}
			request.setAttribute("unitName", unitName);
			String unitLoginAddress=request.getParameter("unitLoginAddress");
			if(StringUtils.isNotEmpty(unitLoginAddress)){
				sql.append(" and unitLoginAddress like '%"+unitLoginAddress+"%'");
			}
			request.setAttribute("unitLoginAddress", unitLoginAddress);
		}
		ArrayList<UnitInfo> unitInfoList=new ArrayList<UnitInfo>();
		List dataList=dao.findByPage("from UnitInfo where 1=1 "+sql+" order by pkUnit");
		System.out.println(dataList);
		List dataList1=dao.findAll("from ManageUnit where status='A'");
		for(int i=0;i<dataList.size();i++){
			unitInfo=(UnitInfo) dataList.get(i);
			StringBuffer dataTable = new StringBuffer();
			for(int j=0;j<dataList1.size();j++){
//				manageUnit=(ManageUnit) dataList1.get(j);
				dataTable.append("<td>");
				String dataValue = "";
//				if(PropertyUtils.getProperty(unitInfo, manageUnit.getUnitData()) != null)
//				{
//				   dataValue = PropertyUtils.getProperty(unitInfo, manageUnit.getUnitData()).toString();
//				}
				dataTable.append(""+dataValue+"");
				dataTable.append("</td>");
			}
			unitInfo.setDataValue(dataTable.toString());
			unitInfoList.add(unitInfo);
		}
		request.setAttribute("unitInfoList", unitInfoList);
		request.setAttribute("dataList1", dataList1);
		
		List resultList = new ArrayList();
		List unitList=dao.findAll("from UnitInfo");
		for(int i=0;i<unitList.size();i++){
			UnitInfo unitInfo=(UnitInfo)unitList.get(i);
			resultList.add(unitInfo.getUnitNo()+"��"+unitInfo.getUnitName()+"��"+Common.getPinYinHeadChar(unitInfo.getUnitName()));
		}
		String unitData = Java2JSON.list2jsonSuggest(resultList);
		request.setAttribute("unitData",unitData);
		return SUCCESS;
	}

	public String showUnit() throws Exception{
		String unitId=request.getParameter("unitId");
		unitInfo=(UnitInfo)dao.findOne(UnitInfo.class, Integer.parseInt(unitId));
		Date date=unitInfo.getCensorDate();
		String data=unitInfo.getPkUnit()+"&"+unitInfo.getUnitName()+"&"+unitInfo.getUnitGroup()+"&"+unitInfo.getSuperiorInfo()+"&"+unitInfo.getUnitCategory()+"&"+unitInfo.getUnitCommunicationAddress()+"&"+unitInfo.getLicenseLevel()+"&"+unitInfo.getClassifiedPeople()+"&"+unitInfo.getCensorScore();
		//����+����+����+�ϼ�+����+ͨѶ��ַ+�ȼ�+ʱ��+����
		request.setAttribute("data", data);
		
	   return toData();
	}
	
	
	public String addUnit() throws Exception{
		String pkUnit= request.getParameter("pkUnit");
		if(StringUtils.isNotEmpty(pkUnit)){
			unitInfo=(UnitInfo)dao.findOne(UnitInfo.class, Integer.parseInt(pkUnit));
			List dataList=dao.findAll("from ManageUnit where status='A'");
			StringBuffer dataTable = new StringBuffer();
			for(int i=0;i<dataList.size();i++){
//				manageUnit=(ManageUnit) dataList.get(i);
			    dataTable.append("<tr>");
			    dataTable.append("<td>");
//			    dataTable.append(""+manageUnit.getManageData()+"");
			    dataTable.append("</td>");
			    dataTable.append("<td class=\"left_align\">");
			    String dataValue = "";
//			    if(PropertyUtils.getProperty(unitInfo, manageUnit.getUnitData()) != null)
//			    {
//			    	dataValue = PropertyUtils.getProperty(unitInfo, manageUnit.getUnitData()).toString();
//			    }
//			    dataTable.append("&nbsp;&nbsp;<input type=\"text\" class=\"inputLength\"  name=\"unitInfo."+manageUnit.getUnitData()+"\" value=\""+dataValue+"\">");
			    dataTable.append("</td>");
				dataTable.append("</tr>");
			}
			request.setAttribute("dataTable", dataTable);
		
		}else{
			List list =dao.findAll("from UnitInfo where 1=1 ");
			String unitNo="";
			if(list.size()>0){
				    list =dao.findAll("select max(unitNo) from UnitInfo ");
					String maxId = list.get(0).toString();
					BigDecimal bd = new BigDecimal(maxId);
					BigDecimal sum = bd.add(new BigDecimal("1"));
					String num ="";
					for(int i=sum.toString().length();i<4 ;i++) 
					{
						num +="0";
					}
					unitNo =num+sum.toString();
			}else{
				unitNo = "0001";	
			}
			unitInfo = new UnitInfo();
			unitInfo.setUnitNo(unitNo);
			List dataList=dao.findAll("from ManageUnit where status='A'");
			request.setAttribute("dataList", dataList);
		}
		request.setAttribute("unitInfo",unitInfo);
		List unitInfoList=dao.findAll("from UnitInfo order by pkUnit");
		request.setAttribute("unitInfoList", unitInfoList);
		return SUCCESS;
	}
	
	
	public String saveUnit() throws Exception{
		if("".equals(unitInfo.getPkUnit())){
			unitInfo.setPkUnit(null);
		}
		dao.saveOrUpdate(unitInfo);
		Alert_GoUrl("����ɹ�","closeCurrent","������Ϣ",null);
		return  toResult(); 
	}
	
	public String deleteUnit() throws Exception{
		String pkUnit=request.getParameter("pkUnit");
		dao.bulkUpdate("delete from UnitInfo where pkUnit='"+pkUnit+"'");
		Alert_GoUrl("ɾ���ɹ�","","������Ϣ",null);
		return  toResult();
	}
	
	public String manageUnit() throws Exception{
		List dataList=dao.findAll("from ManageUnit where status='A'");
		request.setAttribute("dataList", dataList);
		return  SUCCESS;
	}
	
	public String saveManageUnit() throws Exception{
		String[] manageUnitData=request.getParameterValues("manageUnitData");
		List manageUnitList=dao.findAll("from ManageUnit");
		for(int i=0;i<manageUnitData.length;i++){
			if(StringUtils.isNotEmpty(manageUnitData[i])){
				int a= i+1;
//				if(manageUnitList.size()==0){
//					manageUnit = new ManageUnit();
//					manageUnit.setManageData(manageUnitData[i]);
//					manageUnit.setUnitData("data"+a);
//					manageUnit.setStatus("A");
//					dao.save(manageUnit);
//				}else{
//					manageUnit = new ManageUnit();
//					manageUnit.setManageData(manageUnitData[i]);
//					manageUnit.setUnitData("data"+(manageUnitList.size()+a));
//					manageUnit.setStatus("A");
//					dao.save(manageUnit);	
//				}
			}
		}
//		this.Alert_GoUrl("����ɹ���","manageUnit.do");
		Alert_GoUrl("����ɹ�", "closeCurrent","������Ϣ",null);
		return  toResult(); 
	}
	
	public String editManageUnit() throws Exception{
		String pkManageUnit=request.getParameter("pkManageUnit");
//		manageUnit=(ManageUnit) dao.findOne(ManageUnit.class, Integer.parseInt(pkManageUnit));
//		request.setAttribute("manageUnit", manageUnit);
		return SUCCESS;
	}
	
	public String saveManageUnitData() throws Exception{
		String manageData=request.getParameter("manageData");
		String pkManageUnit=request.getParameter("pkManageUnit");
		dao.bulkUpdate("update ManageUnit set manageData='"+manageData+"' where pkManageUnit= '"+pkManageUnit+"'");
		this.Alert_Close("����ɹ�","ok");
		return  toMessage(); 
	}
	
	public String delManageUnit() throws Exception{
		String pkManageUnit=request.getParameter("pkManageUnit");
//		manageUnit=(ManageUnit) dao.findOne(ManageUnit.class, Integer.parseInt(pkManageUnit));
//		manageUnit.setStatus("P");
//		dao.update(manageUnit);
		this.Alert_Close("ɾ���ɹ�","ok");
		return  toMessage(); 
	}
	
	public String listOneUnitInfo() throws Exception{
		String unitName=request.getParameter("unitName");
		List unitInfoList=dao.findAll("from UnitInfo where unitName='"+unitName+"'");
		if(unitInfoList.size() > 0){
			unitInfo=(UnitInfo)unitInfoList.get(0);
			request.setAttribute("unitInfo", unitInfo);
			String unitNo=unitInfo.getUnitNo();
			int pkUnit=unitInfo.getPkUnit();
			String superiorInfo=unitInfo.getSuperiorInfo();
			//������ϵ���ṹ
			List unitList=dao.findAll("from UnitInfo");
			request.setAttribute("datalist",unitList);
			if("0".equals(unitInfo.getSuperiorInfo())){
			request.setAttribute("headDisplayValue","�����ƹ���");
			request.setAttribute("headValue","0");	
			}else{
				//UnitInfo superUnit=(UnitInfo) dao.findFirst("from UnitInfo where unitNo='"+unitInfo.getSuperiorInfo()+"'");
				String headValue=getUnitNo(superiorInfo);
				UnitInfo superUnit=(UnitInfo) dao.findFirst("from UnitInfo where unitNo='"+headValue+"'");
				request.setAttribute("headDisplayValue",superUnit.getUnitName());
				request.setAttribute("headValue",superUnit.getUnitNo());	
			}

			//���ܼ��
			ArrayList<CheckTaskInfo> checkTaskInfoList=new ArrayList<CheckTaskInfo>();
			List checkTaskInfo=dao.findAll("from CheckTaskInfo where pkUnit = '"+pkUnit+"' order by passedTime desc");
			for(int i=0;i<checkTaskInfo.size();i++){
				CheckTaskInfo checkTask=(CheckTaskInfo) checkTaskInfo.get(i);
				CheckConfig checkConfig=(CheckConfig) dao.findOne(CheckConfig.class,Integer.parseInt(checkTask.getTaskName()));
				checkTask.setCheckTaskName(checkConfig.getCheckName());
				checkTaskInfoList.add(checkTask);
			}
			request.setAttribute("checkTaskInfoList", checkTaskInfoList);
			
			List certTaskInfoList=dao.findAll("from CertTaskInfo where pkUnit = '"+pkUnit+"' order by passedTime desc");
			request.setAttribute("certTaskInfoList",certTaskInfoList);
		}
		return SUCCESS;
	}
	
	public String getUnitNo(String unitNo) throws Exception{
		UnitInfo superUnit=(UnitInfo) dao.findFirst("from UnitInfo where unitNo='"+unitNo+"'");
		String superNo=superUnit.getSuperiorInfo();
		unitNo =superUnit.getUnitNo();
		System.out.println("unitNo"+unitNo);
		while(superNo == "0"){
			getUnitNo(unitNo);
		}
		return unitNo;
	}
	public String listTrainInfo() throws Exception{
		StringBuilder sql = new StringBuilder();
		sql.append("");
		if(POST()) {
			String trainName=request.getParameter("trainName");
			if(StringUtils.isNotEmpty(trainName)){
				sql.append(" and trainName like '%"+trainName+"'");
			}
		    request.setAttribute("trainName", trainName);
		    String endTimeFrom = request.getParameter("endTimeFrom");
		    if(StringUtils.isNotEmpty(endTimeFrom)){
		    	sql.append(" and endTime >= '"+endTimeFrom+"'");
		    }
		    String endTimeTo = request.getParameter("endTimeTo");
		    if(StringUtils.isNotEmpty(endTimeTo)){
		    	sql.append(" and endTime <= '"+endTimeTo+"'");
		    }
		}
		List trainInfoList=dao.findByPage("from TrainInfo where 1=1  "+sql+"  order by endTime desc");
		request.setAttribute("trainInfoList", trainInfoList);
		return SUCCESS;
	}

	public String addTrainInfo() throws Exception{
		String pkTrain=request.getParameter("pkTrain");
		if(StringUtils.isNotEmpty(pkTrain)){
//			trainInfo=(TrainInfo) dao.findOne(TrainInfo.class,Integer.parseInt(pkTrain));	
		}else{
			List list =dao.findAll("from TrainInfo where 1=1 ");
			String trainNo="";
			if(list.size()>0){
				    list =dao.findAll("select max(trainNo) from TrainInfo ");
					String maxId = list.get(0).toString();
					BigDecimal bd = new BigDecimal(maxId);
					BigDecimal sum = bd.add(new BigDecimal("1"));
					String num ="";
					for(int i=sum.toString().length();i<4 ;i++) 
					{
						num +="0";
					}
					trainNo =num+sum.toString();
			}else{
				trainNo = "0001";	
			}
//			trainInfo = new TrainInfo();
//			trainInfo.setTrainNo(trainNo);
		}
//		request.setAttribute("trainInfo", trainInfo);
		return SUCCESS;
	}
	
	public String saveTrainInfo() throws Exception{
//		if("".equals(trainInfo.getPkTrain())){
//			trainInfo.setPkTrain(null);
//		}
//		dao.saveOrUpdate(trainInfo);
		this.Alert_Close("����ɹ�","ok");
		return  toMessage();	
	}
	
	public String deleteTrainInfo() throws Exception{
		String pkTrain=request.getParameter("pkTrain");
		dao.bulkUpdate("delete from TrainInfo where pkTrain='"+pkTrain+"'");
		this.Alert_Close("ɾ���ɹ�","ok");
		return  toMessage(); 	
	}
	public String listStaffInfo() throws Exception{
		StringBuilder sql = new StringBuilder();
		sql.append("");
		if(POST()) {
			String staffName=request.getParameter("staffName");
			if(StringUtils.isNotEmpty(staffName)){
				sql.append(" and staffName like '%"+staffName+"'");
			}
		    request.setAttribute("staffName", staffName);
		    String staffUnit=request.getParameter("staffUnit");
			if(StringUtils.isNotEmpty(staffUnit)){
				sql.append(" and staffUnit ='"+staffUnit+"'");
			}
		}
//		ArrayList<StaffInfo> staffInfoList=new ArrayList<StaffInfo>();
//		List staffInfoList1=dao.findByPage("from StaffInfo where 1=1  "+sql+" ");
//		for(int i=0;i<staffInfoList1.size();i++){
//			staffInfo=(StaffInfo) staffInfoList1.get(i);
////			trainInfo=(TrainInfo) dao.findOne(TrainInfo.class,Integer.parseInt(staffInfo.getPkTrain()));
//			List a = dao.findAll("from UnitInfo where pkUnit='"+Integer.parseInt(staffInfo.getStaffUnit())+"' ");
//			for (int j = 0; j < a.size(); j++) {
//				unitInfo=(UnitInfo) a.get(j);
//				String b = unitInfo.getUnitName();
//				staffInfo.setUnitName(b);
//			}
//			staffInfoList.add(staffInfo);
//		}
//		request.setAttribute("staffInfoList", staffInfoList);
		
		List resultList = new ArrayList();
		List unitList=dao.findAll("from UnitInfo");
		for(int i=0;i<unitList.size();i++){
			UnitInfo unitInfo=(UnitInfo)unitList.get(i);
			resultList.add(unitInfo.getPkUnit()+"��"+unitInfo.getUnitNo()+"��"+unitInfo.getUnitName()+"��"+Common.getPinYinHeadChar(unitInfo.getUnitName()));
		}
		String unitData = Java2JSON.list2jsonSuggest(resultList);
		request.setAttribute("unitData", unitData);
		return SUCCESS;
	}

//	public String addStaffInfo() throws Exception{
//		String pkStaff=request.getParameter("pkStaff");
//		if(StringUtils.isNotEmpty(pkStaff)){
//			staffInfo=(StaffInfo) dao.findOne(StaffInfo.class,Integer.parseInt(pkStaff));
//		}
//		List unitInfoList=dao.findAll("from UnitInfo where 1=1 order by unitNo");
//		List trainInfoList=dao.findAll("from TrainInfo where 1=1 order by endTime desc");
//		request.setAttribute("unitInfoList", unitInfoList);
//		request.setAttribute("trainInfoList", trainInfoList);
//		request.setAttribute("staffInfo", staffInfo);
//		return SUCCESS;
//	}
//
//	public String saveStaffInfo() throws Exception{
//		if("".equals(staffInfo.getPkStaff())){
//			staffInfo.setPkStaff(null);
//		}
//		dao.saveOrUpdate(staffInfo);
//		this.Alert_Close("����ɹ�","ok");
//		return  toMessage();
//	}
//
	public String deleteStaffInfo() throws Exception{
		String pkStaff=request.getParameter("pkStaff");
		dao.bulkUpdate("delete from StaffInfo where pkStaff='"+pkStaff+"'");
		this.Alert_Close("ɾ���ɹ�","ok");
		return  toMessage(); 	
	}
	
	public String listCertificateValid() throws Exception{
		String pkTrain=request.getParameter("pkTrain");
		SimpleDateFormat sf= new SimpleDateFormat("yyyy-MM-dd");
//		trainInfo=(TrainInfo) dao.findOne(TrainInfo.class,Integer.parseInt(pkTrain));
//		String data=sf.format(DateUtils.addYears(trainInfo.getEndTime(), Integer.parseInt(trainInfo.getEffectivePeriod())));
//		request.setAttribute("data", data);
		return toResult();
	}
	
	public String ajaxSuperiorInfo() throws Exception{
		String id = request.getParameter("unitGroup");
		List list=dao.findAll("from UnitInfo where unitGroup = '"+id+"'");
		String data = JSONArray.fromObject(list).toString();
		request.setAttribute("data",data);
		return toResult();
	}
	
	public String unitNumAnalyse() throws Exception{
		List listGroup = dao.findAll("from UnitInfo where superiorInfo = '0'");
		request.setAttribute("listGroup", listGroup);  
		String pkUnita=request.getParameter("pkUnita");
		String pkUnitb=request.getParameter("pkUnitb");
		String pkUnitc=request.getParameter("pkUnitc");
		String pkUnitd=request.getParameter("pkUnitd");
		
		UnitInfo unitInfo =new UnitInfo();
		StringBuilder yBar = new StringBuilder();   
		StringBuilder name = new StringBuilder();
		StringBuilder histogram = new StringBuilder();
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy");
		String nowYear=sf.format(new Date());
		String strs1="";
		
		if(StringUtils.isNotEmpty(pkUnita) || StringUtils.isNotEmpty(pkUnitb) || StringUtils.isNotEmpty(pkUnitc) || StringUtils.isNotEmpty(pkUnitd)){
			if (StringUtils.isNotEmpty(pkUnita) && StringUtils.isEmpty(pkUnitb) && StringUtils.isEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)) {
				strs1+=pkUnita;
				unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+strs1+"'");
				
			}else if(StringUtils.isNotEmpty(pkUnita) && StringUtils.isNotEmpty(pkUnitb) && StringUtils.isEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)){
				strs1+=pkUnitb;
				unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+strs1+"'");
				
			}else if(StringUtils.isNotEmpty(pkUnita) && StringUtils.isNotEmpty(pkUnitb) && StringUtils.isNotEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)){
				strs1+=pkUnitc;
				unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+strs1+"'");
				
			}else if(StringUtils.isNotEmpty(pkUnita) && StringUtils.isNotEmpty(pkUnitb) && StringUtils.isNotEmpty(pkUnitc) && StringUtils.isNotEmpty(pkUnitd)){
				strs1+=pkUnitd;
				unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+strs1+"'");
				
			}
			name.append(""+unitInfo.getUnitName()+"������������λ����ͳ��");

			
		
			//y��[{ name: 'John',data: [5, 3, 4, 7, 2]}, {name: 'Jane',data: [2, -2, -3, 2, 1]}]
			//x��['Apples', 'Oranges', 'Pears', 'Grapes', 'Bananas']
//			String sql = "SELECT tab.a-tab.b ,tab.b FROM (SELECT SUM(unitPeople) as a,unitPeople as b FROM UnitInfo WHERE unitNo LIKE '%0001%' OR superiorInfo LIKE '%0001%') as tab";
//			dao.findFirst(sql);
			String sql1="SELECT pkUnit,unitName,SUM(unitPeople) FROM UnitInfo WHERE superiorInfo like '%"+strs1+"%' or unitNo like '%"+strs1+"%' group by pkUnit,unitName ";
			List list1 = dao.findAll(sql1);
			
			String sql2="SELECT SUM(unitPeople) FROM UnitInfo WHERE unitNo like '%"+strs1+"%'";
			List list2 = dao.findAll(sql2);
			yBar.append("[{ name: '����',data: [");
			histogram.append("['"+unitInfo.getUnitName()+"',");
			for(int i=0;i<list1.size();i++){
				Object[] obj1 = (Object[]) list1.get(i);
				
				if(i==list1.size()-1){
					yBar.append(""+obj1[2]+"]}]");
					histogram.append("'"+obj1[1]+"']");
				}else{
					yBar.append(""+obj1[2]+",");
					histogram.append("'"+obj1[1]+"',");
				}
			}
//			for(int j=0;j<list2.size();j++){
//				String obj2 = (String) list2.get(j);
//				System.out.println(obj2);
//				yBar.append("{ name: '����λ����',data: ["+obj2+"]}]");
//			}
			
			
			//histogram.append("['"+unitInfo.getUnitName()+"']");
		}
		else if(StringUtils.isEmpty(pkUnita) && StringUtils.isEmpty(pkUnitb) && StringUtils.isEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)){
			String sql1="SELECT SUM(unitPeople) FROM UnitInfo ) ";
			List list1 = dao.findAll(sql1);
			
			for(int i=0;i<list1.size();i++){
				String obj1 = (String) list1.get(i);
				System.out.println(obj1);
				yBar.append("[{ name: '������λ����',data: ["+obj1+"]}]");
			}
			
			name.append("���е�λ����ͳ��");
			histogram.append("['���е�λ']");
		}


		
		
		System.out.println(yBar);
		System.out.println(histogram);
		
		request.setAttribute("yBar", yBar);
		request.setAttribute("histogram", histogram);
		request.setAttribute("name", name);
		return SUCCESS;
	
		
		
		
	}
	public String unitNumCount() throws Exception{
		String pkUnita=request.getParameter("pkUnita");
		String pkUnitb=request.getParameter("pkUnitb");
		String pkUnitc=request.getParameter("pkUnitc");
		String pkUnitd=request.getParameter("pkUnitd");
		
		UnitInfo unitInfo =new UnitInfo();
		StringBuilder yBar = new StringBuilder();
		StringBuilder name = new StringBuilder();
		StringBuilder histogram = new StringBuilder();
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy");
		String nowYear=sf.format(new Date());
		String strs1="";
		
		if(StringUtils.isNotEmpty(pkUnita) || StringUtils.isNotEmpty(pkUnitb) || StringUtils.isNotEmpty(pkUnitc) || StringUtils.isNotEmpty(pkUnitd)){
			if (StringUtils.isNotEmpty(pkUnita) && StringUtils.isEmpty(pkUnitb) && StringUtils.isEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)) {
				strs1+=pkUnita;
				unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+strs1+"'");
				
			}else if(StringUtils.isNotEmpty(pkUnita) && StringUtils.isNotEmpty(pkUnitb) && StringUtils.isEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)){
				strs1+=pkUnitb;
				unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+strs1+"'");
				
			}else if(StringUtils.isNotEmpty(pkUnita) && StringUtils.isNotEmpty(pkUnitb) && StringUtils.isNotEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)){
				strs1+=pkUnitc;
				unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+strs1+"'");
				
			}else if(StringUtils.isNotEmpty(pkUnita) && StringUtils.isNotEmpty(pkUnitb) && StringUtils.isNotEmpty(pkUnitc) && StringUtils.isNotEmpty(pkUnitd)){
				strs1+=pkUnitd;
				unitInfo = (UnitInfo) dao.findFirst(" from UnitInfo where unitNo = '"+strs1+"'");
				
			}
			name.append(""+unitInfo.getUnitName()+"������������λ����ͳ��");

			
		
			//y��[{ name: 'John',data: [5, 3, 4, 7, 2]}, {name: 'Jane',data: [2, -2, -3, 2, 1]}]
			//x��['Apples', 'Oranges', 'Pears', 'Grapes', 'Bananas']
//			String sql = "SELECT tab.a-tab.b ,tab.b FROM (SELECT SUM(unitPeople) as a,unitPeople as b FROM UnitInfo WHERE unitNo LIKE '%0001%' OR superiorInfo LIKE '%0001%') as tab";
//			dao.findFirst(sql);
			String sql1="SELECT pkUnit,unitName,SUM(unitPeople) FROM UnitInfo WHERE superiorInfo like '%"+strs1+"%' or unitNo like '%"+strs1+"%' group by pkUnit,unitName ";
			List list1 = dao.findAll(sql1);
			
			String sql2="SELECT SUM(unitPeople) FROM UnitInfo WHERE unitNo like '%"+strs1+"%'";
			List list2 = dao.findAll(sql2);
			yBar.append("[{ name: '����',data: [");
			histogram.append("['"+unitInfo.getUnitName()+"',");
			for(int i=0;i<list1.size();i++){
				Object[] obj1 = (Object[]) list1.get(i);
				
				if(i==list1.size()-1){
					yBar.append(""+obj1[2]+"]}]");
					histogram.append("'"+obj1[1]+"']");
				}else{
					yBar.append(""+obj1[2]+",");
					histogram.append("'"+obj1[1]+"',");
				}
			}
//			for(int j=0;j<list2.size();j++){
//				String obj2 = (String) list2.get(j);
//				System.out.println(obj2);
//				yBar.append("{ name: '����λ����',data: ["+obj2+"]}]");
//			}
			
			
			//histogram.append("['"+unitInfo.getUnitName()+"']");
		}
		else if(StringUtils.isEmpty(pkUnita) && StringUtils.isEmpty(pkUnitb) && StringUtils.isEmpty(pkUnitc) && StringUtils.isEmpty(pkUnitd)){
			String sql1="SELECT SUM(unitPeople) FROM UnitInfo ) ";
			List list1 = dao.findAll(sql1);
			
			for(int i=0;i<list1.size();i++){
				String obj1 = (String) list1.get(i);
				System.out.println(obj1);
				yBar.append("[{ name: '������λ����',data: ["+obj1+"]}]");
			}
			
			name.append("���е�λ����ͳ��");
			histogram.append("['���е�λ']");
		}


		
		
		System.out.println(yBar);
		System.out.println(histogram);
		
		request.setAttribute("yBar", yBar);
		request.setAttribute("histogram", histogram);
		request.setAttribute("name", name);
		return SUCCESS;
	}
	
	public String DataImpExp() throws Exception{
		List resultList = new ArrayList();
		List unitList=dao.findAll("from UnitInfo");
		for(int i=0;i<unitList.size();i++){
			UnitInfo unitInfo=(UnitInfo)unitList.get(i);
			resultList.add(unitInfo.getPkUnit()+"��"+unitInfo.getUnitNo()+"��"+unitInfo.getUnitName()+"��"+Common.getPinYinHeadChar(unitInfo.getUnitName()));
		}
		String unitData = Java2JSON.list2jsonSuggest(resultList);
		request.setAttribute("unitData", unitData);
		
		return SUCCESS;
	}
	public String dataExpWord() throws Exception{
		String pkUnit=request.getParameter("pkUnit");
		if(StringUtils.isNotEmpty(pkUnit)){
			unitInfo = (UnitInfo) dao.findOne(UnitInfo.class,Integer.parseInt(pkUnit.trim()) );
		}
		StringBuilder sql = new StringBuilder();
		HashMap data = new HashMap();
		
		List listCertTaskInfo = dao.findAll("from CertTaskInfo where pkUnit = '"+pkUnit+"' ");
		if(listCertTaskInfo.size()>0){
			for(int i=0;i<listCertTaskInfo.size();i++){
				CertTaskInfo certTaskInfo = (CertTaskInfo) listCertTaskInfo.get(i);
				
				sql.append(" insert into cert_Task_Info ( production_Permit_Registration, confidentiality_Level_Qualifications, "+
						" passed_Time,  passed_Score ,  cert_Unit_Group ,  cert_Status ,  remark ,  cert_Type ,  "+
						" plan_File_Id ,  selfexam_File_Id ,  member_Name1 ,  member_Name2 ,  member_Name3 ,  "+
						" member_Name4 ,  member_Name5 ,  member_Name6 ,  member_Name7 ,  member_Name8 ,  "+
						" member_Unit1 ,  member_Unit2 ,  member_Unit3 ,  member_Unit4 ,  member_Unit5 ,  "+
						" member_Unit6 ,  member_Unit7 ,  member_Unit8 ,  member_Position1 , "+
						" member_Position2 ,  member_Position3 ,  member_Position4 ,  member_Position5 , "+
						" member_Position6 ,  member_Position7 ,  member_Position8 ,  data1 ,  data2 ,  data3 , "+
						" data4 ,  data5 ,  data6 ,  data7 ,  data8 ,  data9 ,  data10 ,  data11 ,  data12 ,  data13 , "+
						" data14 ,  data15 ,  data16 ,  data17 ,  data18 ,  data19 ,  data20 ,  textarea1 ,  textarea2 ,  "+
						" application ,  review_Info ,  num_People ,  highest_Score ,  average ,  pk_Unit ,  "+
						" application_Status ,  review_Info_Status ,  submission_Status ,  textarea3 ,  textarea4 , "+ 
						" textarea5 ,  textarea6 ,  other_File ,  other_File_Status ,  member_Unit9 ,  member_Unit10 , "+ 
						" member_Name9 ,  member_Name10 ,  member_Position9 ,  member_Position10 )  "+
						" values('"+certTaskInfo.getProductionPermitRegistration()+"','"+certTaskInfo.getConfidentialityLevelQualifications()+"', "+
						" '"+certTaskInfo.getPassedTime()+"',"+certTaskInfo.getPassedScore()+",'"+certTaskInfo.getCertUnitGroup()+"','"+certTaskInfo.getCertStatus()+"', "+
						" '"+certTaskInfo.getRemark()+"','"+certTaskInfo.getCertType()+"','"+certTaskInfo.getPlanFileId()+"','"+certTaskInfo.getSelfexamFileId()+"', "+
						" '"+certTaskInfo.getMemberName1()+"','"+certTaskInfo.getMemberName2()+"','"+certTaskInfo.getMemberName3()+"','"+certTaskInfo.getMemberName4()+"', "+
						" '"+certTaskInfo.getMemberName5()+"','"+certTaskInfo.getMemberName6()+"','"+certTaskInfo.getMemberName7()+"','"+certTaskInfo.getMemberName8()+"', "+
						" '"+certTaskInfo.getMemberUnit1()+"','"+certTaskInfo.getMemberUnit2()+"','"+certTaskInfo.getMemberUnit3()+"','"+certTaskInfo.getMemberUnit4()+"', "+
						" '"+certTaskInfo.getMemberUnit5()+"','"+certTaskInfo.getMemberUnit6()+"','"+certTaskInfo.getMemberUnit7()+"','"+certTaskInfo.getMemberUnit8()+"', "+
						" '"+certTaskInfo.getMemberPosition1()+"','"+certTaskInfo.getMemberPosition2()+"','"+certTaskInfo.getMemberPosition3()+"', "+
						" '"+certTaskInfo.getMemberPosition4()+"','"+certTaskInfo.getMemberPosition5()+"', "+
						" '"+certTaskInfo.getMemberPosition6()+"','"+certTaskInfo.getMemberPosition7()+"','"+certTaskInfo.getMemberPosition8()+"', "+
						" '"+certTaskInfo.getData1()+"','"+certTaskInfo.getData2()+"','"+certTaskInfo.getData3()+"','"+certTaskInfo.getData4()+"','"+certTaskInfo.getData5()+"', "+
						" '"+certTaskInfo.getData6()+"','"+certTaskInfo.getData7()+"','"+certTaskInfo.getData8()+"','"+certTaskInfo.getData9()+"','"+certTaskInfo.getData10()+"', "+
						" '"+certTaskInfo.getData11()+"','"+certTaskInfo.getData12()+"','"+certTaskInfo.getData13()+"','"+certTaskInfo.getData14()+"','"+certTaskInfo.getData15()+"', "+
						" '"+certTaskInfo.getData16()+"','"+certTaskInfo.getData17()+"','"+certTaskInfo.getData18()+"','"+certTaskInfo.getData19()+"','"+certTaskInfo.getData20()+"', "+
						" '"+certTaskInfo.getTextarea1()+"','"+certTaskInfo.getTextarea2()+"','"+certTaskInfo.getApplication()+"','"+certTaskInfo.getReviewInfo()+"', "+
						" "+certTaskInfo.getNumPeople()+","+certTaskInfo.getHighestScore()+","+certTaskInfo.getAverage()+",'"+certTaskInfo.getPkUnit()+"', "+
						" '"+certTaskInfo.getApplicationStatus()+"','"+certTaskInfo.getReviewInfoStatus()+"','"+certTaskInfo.getSubmissionStatus()+"','"+certTaskInfo.getTextarea3()+"', "+
						" '"+certTaskInfo.getTextarea4()+"','"+certTaskInfo.getTextarea5()+"','"+certTaskInfo.getTextarea6()+"','"+certTaskInfo.getOtherFile()+"', "+
						" '"+certTaskInfo.getOtherFileStatus()+"','"+certTaskInfo.getMemberUnit9()+"','"+certTaskInfo.getMemberUnit10()+"','"+certTaskInfo.getMemberName9()+"', "+
						" '"+certTaskInfo.getMemberName10()+"','"+certTaskInfo.getMemberPosition9()+"','"+certTaskInfo.getMemberPosition10()+"')") ;
				sql.append("####");
				List listCertTaskScore = dao.findAll("from CertTaskScore where certTaskId ="+certTaskInfo.getCertTaskId()+"");
				if(listCertTaskScore.size()>0){
					for(int j=0;j<listCertTaskScore.size();j++){
						CertTaskScore certTaskScore = (CertTaskScore) listCertTaskScore.get(j);
						sql.append(" insert into cert_task_score " +
								"(score_rule, score_number, cert_task_id, cert_id, rule_id) " +
								" values('"+certTaskScore.getScoreRule()+"',"+certTaskScore.getScoreNumber()+"," +
										" "+certTaskScore.getCertTaskId()+","+certTaskScore.getCertId()+","+certTaskScore.getRuleId()+") ");
						if(j!=listCertTaskScore.size()-1){
							sql.append("####");
						}
					}
					
				}
				if(i!=listCertTaskInfo.size()-1&&listCertTaskScore.size()>0){
					sql.append("####");
				}
				
			}
			
			
		}
		
		
		ArrayList table9 = new ArrayList(1);
		String[] fieldName9 = {"1"};
		table9.add(fieldName9);
		String[] field19 = {sql.toString()};
		table9.add(field19);
		data.put("table$1@1",table9);

		Java2word j2w = new Java2word();
	    String sp=request.getRealPath("upload");
	    j2w.toWord(sp+"\\sql����.doc",sp+"\\sqlExp.doc",data,"pxqs");
		
	    try{
	        String x = new String((unitInfo.getUnitName()+"sql����.doc").getBytes("GBK"), "ISO8859-1");
		    File file = new File(sp+"\\sqlExp.doc");
		    InputStream fis = new BufferedInputStream(new FileInputStream(sp+"\\sqlExp.doc"));
		    byte[] buffer = new byte[fis.available()];
		    fis.read(buffer);
		    fis.close();
		    response.reset();
		    response.addHeader("Content-Disposition", "attachment;filename="+x);
		    response.addHeader("Content-Length", ""+file.length());		  
		    OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		    response.setContentType("application/octet-stream");
		    toClient.write(buffer);
		    toClient.flush();
		    toClient.close();
	    } catch (IOException ex) {
			// TODO: handle exception
	        ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String sqlImport() throws Exception{
//		Alert_GoUrl("����ɹ�", "closeCurrent","���ݵ��뵼��",null);
//		return  toResult(); 
		return SUCCESS;
	}
	
	public String dataImpWord() throws Exception{
		if (doc!=null && !"".equals(doc)) {
			this.getUploadFileName().add(docFileName);
			this.getUpload().add(doc);
			this.getUploadContentType().add(docContentType);
			ArrayList<UploadFile> list = this.upload("word_to_cert_task","11");
			String sp=request.getRealPath("upload");
			String docName = sp+	list.get(0).getPutPath().substring(7);
	        /** 1. ��ȡWORD������� */
	        HWPFDocument doc = null;
	        try {
	            doc = new HWPFDocument(new FileInputStream(docName));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        String text = doc.getRange().text();
	        
	        char[] charArray = text.toCharArray();
	        for(int i = 0; i < charArray.length; i++)
	        {
	            if (charArray[i] == 7)
	            {
	                charArray[i] = 35;
	            }
	        }  
	        text = String.valueOf(charArray);
	        String[] insertSqls = text.trim().split("####");
	        for(int i=0;i<insertSqls.length;i++){
	        	Connection conn = null;
		        Statement stmt = null;
		           try {
		             //��һ��������MySQL��JDBC������
		                Class.forName("com.mysql.jdbc.Driver"); 
		              //�ڶ�������������
		                conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/exam?user=root&password=root&useUnicode=true&characterEncoding=utf-8");
		              //����������conn����Statement������ʵ�� stmt
		                stmt = conn.createStatement();
		                String sql= insertSqls[i].replace("#", " ").replace("'null'", "null");
		                if(StringUtils.isNotEmpty(sql.trim())){
		                	 stmt.executeUpdate(insertSqls[i].replace("#", " ").replace("'null'", "null"));
		                }
		           } catch (ClassNotFoundException e) {  
			           //����JDBC����,��Ҫ�õ�����û���ҵ�
			            System.out.println("�������ش���");
			        }catch (SQLException ex) {
			          //��ʾ���ݿ����Ӵ�����ѯ����
			          System.err.println("SQLException:"+ex.getMessage());
			        }finally {
			           try{
			            
			               if(stmt != null) {
			            	   stmt.close();
			            	   stmt = null;
			               }
			               if(conn != null) {
			            	   conn.close();
			            	   conn = null;
			               }
			           }catch(SQLException e) {
			            System.err.println("SQLException:"+e.getMessage());
			           }
		        }
	        }
	        //��Ҫ���ϴ����������ϵ��ļ�ɾ��
	        deleteFile(docName);
	        
	        Alert_GoUrl("����ɹ�", "","",null);
	        
		} else {
			Alert_GoUrl("����ʧ��", "","",null);	
		}		
		return toMessage();
	}
	 
	 
	 
	//ɾ���ļ�
	public void deleteFile(String path){ 
		 File file=new File(path); 
		 if(file.exists()){ 
			 if(file.isFile()){ 
				 file.delete(); 
			 }else{ 
				 System.out.println("�������󣬲����ļ���"); 
			 } 
		 }else{ 
			 System.out.println("�ļ�������"); 
		 } 
	} 

}
