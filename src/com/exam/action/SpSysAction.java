package com.exam.action;

import com.exam.po.*;
import com.xunj.action.common.AbstractAction;
import com.xunj.core.Common;
import com.xunj.util.AESUtil;
import com.xunj.util.IOUtil;
import com.xunj.util.RSAUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.security.interfaces.RSAPrivateKey;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static com.xunj.util.AESUtil.AES_CBC_Decrypt;

//import com.sun.org.apache.commons.beanutils.PropertyUtils;


public class SpSysAction extends AbstractAction{

	private static final long serialVersionUID = 4747379774042416043L;
	
	private BaseInfo baseInfo;
	private BaseApplicationInfo baseApplicationInfo;
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

	public BaseInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(BaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public BaseApplicationInfo getBaseApplicationInfo() {
		return baseApplicationInfo;
	}

	public void setBaseApplicationInfo(BaseApplicationInfo baseApplicationInfo) {
		this.baseApplicationInfo = baseApplicationInfo;
	}

	public String listTaskInfo() throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append(" 1=1 ");
		if(POST()){
			String companyName = request.getParameter("companyName");
			request.setAttribute("companyName",companyName);
			if(StringUtils.isNotEmpty(companyName)){
				sql.append("and companyName like '%"+companyName+"%'");
			}
			String creditCode = request.getParameter("creditCode");
			request.setAttribute("creditCode",creditCode);
			if(StringUtils.isNotEmpty(creditCode)){
				sql.append("and creditCode like '%"+creditCode+"%'");
			}
						
			String appTime=request.getParameter("appTime");
			if(StringUtils.isNotEmpty(appTime)){
				sql.append("and appTime='"+appTime+"'");
			}
			request.setAttribute("appTime", appTime);
		}
		List list = dao.findByPage("from BaseApplicationInfo a where"+sql+"  order by a.appTime desc");
	
		ArrayList<BaseApplicationInfo> dataList=new ArrayList<BaseApplicationInfo>();
		for(int i=0;i<list.size();i++){
			BaseApplicationInfo bai=(BaseApplicationInfo) list.get(i);
			BaseInfo bi=(BaseInfo)dao.findFirst("from BaseInfo where creditCode='"+bai.getCreditCode()+"'");
 			if(bi!=null){
 				bai.setBaseInfo(bi);
 			}
 			dataList.add(bai);
 		}
		request.setAttribute("dataList",dataList);
 		
		List resultList = new ArrayList();
		List unitList=dao.findAll("from UnitInfo");
		for(int i=0;i<unitList.size();i++){
			UnitInfo unitInfo=(UnitInfo)unitList.get(i);
			resultList.add(unitInfo.getUnitNo()+"，"+unitInfo.getUnitName()+"，"+Common.getPinYinHeadChar(unitInfo.getUnitName()));
		}
		return SUCCESS;
	}
	
	public String addTaskInfo() throws Exception{
		String pkbaseApp = request.getParameter("taskId");
		Integer tempPkbaseApp = Integer.parseInt(pkbaseApp);
		BaseApplicationInfo bai = (BaseApplicationInfo) dao.findOne(BaseApplicationInfo.class, tempPkbaseApp);
		BaseInfo bi=(BaseInfo)dao.findFirst("from BaseInfo where creditCode='"+bai.getCreditCode()+"'");
		List listBMP=dao.findAll("from BaseMtcsolPeople where pkbaseApp="+pkbaseApp+"");
		List listBSP=dao.findAll("from BaseSecretPeople where pkbaseApp="+pkbaseApp+"");
		
		request.setAttribute("baseInfo", bi);
		request.setAttribute("baseApplicationInfo",bai);
		request.setAttribute("listBSP",listBSP);
		request.setAttribute("listBMP",listBMP);
		return SUCCESS;
	}
	public String saveCertTaskInfo() throws Exception{
		dao.update(baseInfo);
		dao.update(baseApplicationInfo);
		String[] pkSecretPeoples = request.getParameterValues("pkSecretPeople");
		String[] names = request.getParameterValues("name");
		String[] depts = request.getParameterValues("dept");
		String[] dutys = request.getParameterValues("duty");
		if(pkSecretPeoples.length>0){
			for (int i = 0; i < pkSecretPeoples.length; i++) {
				BaseSecretPeople bsp=(BaseSecretPeople)dao.findOne(BaseSecretPeople.class, Integer.parseInt(pkSecretPeoples[i]));
				bsp.setName(names[i]);
				bsp.setDept(depts[i]);
				bsp.setDuty(dutys[i]);
				dao.update(bsp);
			}
		}
		String[] name1 = request.getParameterValues("name1");
		String[] dept1 = request.getParameterValues("dept1");
		String[] duty1 = request.getParameterValues("duty1");
		if(name1.length>0){
			for (int i = 0; i < name1.length; i++) {
				if(StringUtils.isNotBlank(name1[i])){
					BaseSecretPeople bsp1=new BaseSecretPeople();
					bsp1.setDept(dept1[i]);
					bsp1.setDuty(duty1[i]);
					bsp1.setName(name1[i]);
					bsp1.setPkbaseApp(baseApplicationInfo.getPkbaseApp());
					dao.save(bsp1);
				}
			}
		}
		
		
		String[] pkMtcsol = request.getParameterValues("pkMtcsol");
		String[] mtcsolName = request.getParameterValues("mtcsolName");
		String[] mtcsolSex = request.getParameterValues("mtcsolSex");
		String[] mtcsolAge = request.getParameterValues("mtcsolAge");
		String[] mtcsolJob = request.getParameterValues("mtcsolJob");
		String[] mtcsolSchool = request.getParameterValues("mtcsolSchool");
		if(pkMtcsol.length>0){
			for (int i = 0; i < pkMtcsol.length; i++) {
				BaseMtcsolPeople bmp=(BaseMtcsolPeople)dao.findOne(BaseMtcsolPeople.class, Integer.parseInt(pkMtcsol[i]));
				bmp.setMtcsolName(mtcsolName[i]);
				bmp.setMtcsolSex(mtcsolSex[i]);
				bmp.setMtcsolAge(Integer.parseInt(mtcsolAge[i]));
				bmp.setMtcsolJob(mtcsolJob[i]);
				bmp.setMtcsolSchool(mtcsolSchool[i]);
				dao.update(bmp);
			}
		}
		
		String[] mtcsolName1 = request.getParameterValues("mtcsolName1");
		String[] mtcsolSex1 = request.getParameterValues("mtcsolSex1");
		String[] mtcsolAge1 = request.getParameterValues("mtcsolAge1");
		String[] mtcsolJob1 = request.getParameterValues("mtcsolJob1");
		String[] mtcsolSchool1 = request.getParameterValues("mtcsolSchool1");
		if(mtcsolName1.length>0){
			for (int i = 0; i < mtcsolName1.length; i++) {
				if(StringUtils.isNotBlank(mtcsolName1[i])){
					BaseMtcsolPeople bmp1=new BaseMtcsolPeople();
					bmp1.setMtcsolName(mtcsolName1[i]);
					bmp1.setMtcsolSex(mtcsolSex1[i]);
					bmp1.setMtcsolAge(Integer.parseInt(mtcsolAge1[i]));
					bmp1.setMtcsolJob(mtcsolJob1[i]);
					bmp1.setMtcsolSchool(mtcsolSchool1[i]);
					bmp1.setPkbaseApp(baseApplicationInfo.getPkbaseApp());
					dao.save(bmp1);
				}
			}
		}
		
		Alert_GoUrl("保存成功！","closeCurrent","审批流程",null);
		return toResult();
	}
	public String deleteTaskInfo() throws Exception{
		String taskId=request.getParameter("taskId");
		dao.bulkUpdate("delete from BaseApplicationInfo x where pkbaseApp='"+taskId+"'");
		
		dao.bulkUpdate("delete from BaseMtcsolPeople x where pkbaseApp='"+taskId+"'");
		dao.bulkUpdate("delete from BaseSecretPeople x where pkbaseApp='"+taskId+"'");
		dao.bulkUpdate("delete from BaseAttach x where pkbaseApp='"+taskId+"'");
		
		//this.closeDialog("删除成功");
		Alert_GoUrl("删除成功！","","审批流程",null);
		return toResult();
	}
	public String checkFlow() throws Exception{
		String pkbaseApp = request.getParameter("pkbaseApp");
		BaseApplicationInfo cti=(BaseApplicationInfo)dao.findOne(BaseApplicationInfo.class, Integer.parseInt(pkbaseApp));



		request.setAttribute("pkbaseApp", pkbaseApp);
		request.setAttribute("planState", cti.getPlanState());
		request.setAttribute("spStatus", cti.getSpStatus());
		request.setAttribute("creditCode", cti.getCreditCode());
		return SUCCESS;
	}
	public String sqlImport() throws Exception{
//		Alert_GoUrl("导入成功", "closeCurrent","数据导入导出",null);
//		return  toResult();
		//dao.update()
		//BaseApplicationInfo cti=(BaseApplicationInfo)dao.findOne(BaseApplicationInfo.class, Integer.parseInt(pkbaseApp));
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		return SUCCESS;
	}
	public String dataImpWord() throws Exception{
		String type = request.getParameter("type");
		request.setAttribute("type", type);

		if (doc!=null && !"".equals(doc)) {
			String ziptemp=request.getRealPath("ziptemp");
		    String saveRootDirectory = "/Users/huang/kegongju/";
			String sp=request.getRealPath("res");
			String temp=request.getRealPath("temp");
			System.out.println("");
			ZipFile yuanzipFile = new ZipFile(doc.getPath());
			Enumeration<ZipEntry> enu1 = (Enumeration<ZipEntry>) yuanzipFile.entries();
			while (enu1.hasMoreElements()) {
				ZipEntry zipElement1 = (ZipEntry) enu1.nextElement();
				InputStream read = yuanzipFile.getInputStream(zipElement1);
				String fileName = zipElement1.getName();
				if (fileName != null && fileName.indexOf(".") != -1) {// 是否为文件
					unZipFile1(zipElement1, read, ziptemp+"/");
				}
			}

				try {
				//获取私钥
				RSAPrivateKey privateKey = RSAUtil.getPrivateKey(sp+"/private.pem");
//				String filePath = doc.getPath();
//				String folderPath = StringUtils.substringBeforeLast(filePath, "/");
				//File file =  new File(ziptemp+"/key.secrete");
				//读取已经公钥加密的AES密钥文件--记录AES加密时候产生的随机数
				String encodedSecrete = IOUtil.getContent(ziptemp+"/key.secrete");
				//解密该随机数
				String secrete = RSAUtil.decrypt(encodedSecrete, privateKey);
				//读取加密过的申请文件

				byte[] encodedFileBytes = getBytes(ziptemp+"/files.kgj");
				//文件转码和解密,输入上面解析出的随机数
				byte[] decoded = AES_CBC_Decrypt(encodedFileBytes, AESUtil.genSkc(secrete), false);
				FileOutputStream fileOutputStream = new FileOutputStream(new File(temp+"/temp.zip"));
				fileOutputStream.write(decoded);
				fileOutputStream.flush();
					File deletefile = new File(ziptemp);
					deleteDir(deletefile);


				// 获得zip信息
	            ZipFile zipFile = new ZipFile(temp+"/temp.zip");
	            @SuppressWarnings("unchecked")  
	            Enumeration<ZipEntry> enu = (Enumeration<ZipEntry>) zipFile.entries();  
	            String a="";
	            List list = new ArrayList();
	            while (enu.hasMoreElements()) {  
	                ZipEntry zipElement = (ZipEntry) enu.nextElement();  
	                InputStream read = zipFile.getInputStream(zipElement);  
	                String fileName = zipElement.getName();  
	                
	                if(fileName.indexOf(".")>0 &&  !(".json".equals(fileName.substring(fileName.indexOf("."), fileName.length()))) ){
	                	list.add(fileName);
	                }
	                
	                if (fileName != null && fileName.indexOf(".") != -1) {// 是否为文件  
	                    unZipFile(zipElement, read, saveRootDirectory);  
	                }  
	                if(fileName.indexOf(".")>0 && ".json".equals(fileName.substring(fileName.indexOf("."), fileName.length()))){
	                	File file1=new File(saveRootDirectory +fileName );
		                String content= FileUtils.readFileToString(file1,"UTF-8").replaceAll(" +","");
		                JSONObject json_test = JSONObject.fromObject(content); 
		                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		                SimpleDateFormat sdf1=new SimpleDateFormat("yyyy");
		                BaseInfo bbbi=(BaseInfo)dao.findFirst("from BaseInfo where creditCode = '"+json_test.getString("creditCode")+"'");
		                if(bbbi==null){
		                	BaseInfo bi= new BaseInfo();
		                    bi.setCompanyName(json_test.getString("companyName"));//单位名称
							//创建单位春初文件夹
////							File saveFile = new File("/Users/huang/kegongju");
////							saveFile.mkdir();
//							copyFile(file1, saveFile);
		                    bi.setCreditCode(json_test.getString("creditCode"));//社会统一信用代码
		                    bi.setCompanyType(json_test.getString("companyType"));//单位性质
		                    bi.setLegalBody(json_test.getString("legalBody"));//法定代表人
		                    bi.setCompanyPersonCount(json_test.getInt("companyPersonCount"));//单位人数
		                    bi.setSecretPersonCount(json_test.getInt("secretPersonCount"));//涉密人员数
		                    bi.setRegAddress(json_test.getString("regAddress"));//注册地址
		                    bi.setOfficeAddress(json_test.getString("officeAddress"));//科研生产（办公）地址
		                    bi.setMailingAddress(json_test.getString("mailingAddress"));//通信地址
		                    bi.setPostalCode(json_test.getInt("postalCode"));//邮政编码
		                    bi.setPhone(json_test.getString("phone"));//联系电话
		                    bi.setCompanyCreateTime(sdf.parse(json_test.getString("companyCreateTime")));//单位创建时间
		                    bi.setIsShangshi(json_test.getString("isShangshi"));//是否为上市公司
		                    bi.setRegMoney(json_test.getString("regMoney"));//注册资金
		                    bi.setFixedAssets(json_test.getString("fixedAssets"));//固定总资产
		                    bi.setEquityStructure(json_test.getString("equityStructure"));//股权结构
		                    bi.setForeignRelations(json_test.getString("foreignRelations"));//我国国际、境外永久居留权或者长期居留许可及涉外婚姻关系情况
		                    bi.setPunishments(json_test.getString("punishments"));//证券监管机构的行政处罚情况
		                    bi.setSummaryOfCompany(json_test.getString("summaryOfCompany"));//单位概况
		                    dao.save(bi);
		                    saveRootDirectory+=json_test.getString("companyName")+"/";
		                }
		                
		                BaseApplicationInfo baii = (BaseApplicationInfo)dao.findFirst("from BaseApplicationInfo where creditCode='"+json_test.getString("creditCode")+"' and appTime='"+sdf1.format(new Date())+"' ");
		                if(baii==null){
		                	BaseApplicationInfo bai=new BaseApplicationInfo();
			                bai.setCompanyName(json_test.getString("companyName"));//单位名称
			                bai.setCreditCode(json_test.getString("creditCode"));//社会统一信用代码
			                bai.setApplyReason(json_test.getString("applyReason"));//申请理由
			                bai.setMianLegalBody(json_test.getString("mianLegalBody"));//法定代表人或主要负责人
			                bai.setChargeOfSecretLeader(json_test.getString("chargeOfSecretLeader"));//分管保密工作负责人
			                bai.setOtherLeader(json_test.getString("otherLeader"));//其他负责人
			                bai.setSecretDepartment(json_test.getString("secretDepartment"));//涉密部门或项目负责人
			                bai.setSecretPerson(json_test.getString("secretPerson"));//涉密人员
			                bai.setCentralizingMng(json_test.getString("centralizingMng"));//归口管理
			                bai.setAppTime(sdf1.format(new Date()));//申请数据导入年份
			                bai.setSpStatus("1");//审批状态1为已导入申请数据
			//-2---------------------------------------------------------------------
			                JSONArray strs1=json_test.getJSONArray("workSituation"); 
			                if(strs1.size()>0){
			                	 bai.setWorkSituation1(strs1.get(0)+"");//保密工作机构设置情况
			                	 bai.setWorkSituation2(strs1.get(1)+"");//保密工作机构负责人姓名职务以及任命文号
			                }
//			               //----------------------------------------------------------
			                bai.setSecretCommit(json_test.getString("secretCommit"));//保密委员会或保密工作领导小组
			                bai.setSecretSituation(json_test.getString("secretSituation"));//保密工作机构
			                bai.setBasicSystem(json_test.getString("basicSystem"));//基本制度
			                bai.setSpecialSystem(json_test.getString("specialSystem"));//专项制度
			//-4---------------------------------------------------------------------
			                JSONArray strs3=json_test.getJSONArray("tightMng"); //定密管理
			                if(strs3.size()>0){
			                	bai.setTightMng1(strs3.get(0)+"");//定密工作情况
			                    bai.setTightMng2(strs3.get(1)+"");//定密责任人情况
			                    bai.setTightMng3(strs3.get(2)+"");//本单位国家秘密事项范围确定情况
			                    bai.setTightMng4(strs3.get(3)+"");//确定和调整密级情况
				            }
			                
			//-5------------合计自行计算？或者直接能从得到的数组中取出---------------------------------------------------------
			                JSONObject strs4=json_test.getJSONObject("secretStaffMng"); //涉密人员管理
			                String counts = strs4.getString("counts");
			                String[] countss=(counts.substring(1, counts.length()-1)).split(",");
			                bai.setSecretStaffMng1(Integer.parseInt(countss[0]));//核心涉密人员数量
			                bai.setSecretStaffMng2(Integer.parseInt(countss[1]));//重要涉密人员数量
			                bai.setSecretStaffMng3(Integer.parseInt(countss[2]));//一般涉密人员数量
			                bai.setSecretStaffMng4(strs4.getString("content"));//上年度至本年度做的主要工作
			                //----------------------------------------------------------------------
			                bai.setCountriesSys(json_test.getString("countriesSys"));//国家秘密载体管理
			                bai.setDenseProductMng(json_test.getString("denseProductMng"));//密品管理
			                
			//-4---------------------------------------------------------------------
			                JSONArray strs5=json_test.getJSONArray("theImportSecretMng"); //保密要害部门部位管理
			                if(strs5.size()>0){
			                    bai.setTheImportSecretMng1(strs5.get(0)+"");//保密要害部门部位确定情况
			                    bai.setTheImportSecretMng2(strs5.get(1)+"");//保密防护措施落实情况
				            }
			                
			                
			//-5---------------------------------------------------------------------
			                JSONArray strs6=json_test.getJSONArray("sysAndEquiAndStorageMng"); //保密要害部门部位管理
			                
			                if(strs6.size()>0){
			                    bai.setSysAndEquiAndStorageMng1(strs6.get(0)+"");//涉密信息系统建设、防护情况
			                    bai.setSysAndEquiAndStorageMng2(strs6.get(1)+"");//涉密信息设备建设、防护情况
			                    bai.setSysAndEquiAndStorageMng3(strs6.get(2)+"");//涉密存储设备建设、防护情况
			                    bai.setSysAndEquiAndStorageMng4(strs6.get(3)+"");//涉密信息系统、涉密信息设备和涉密存储设备的管理情况
			                    bai.setSysAndEquiAndStorageMng5(strs6.get(4)+"");//非涉密信息系统、非涉密信息设备和非涉密存储设备的管理情况
			                    bai.setSysAndEquiAndStorageMng6(strs6.get(5)+"");//专用系统或者设备建设、防护和管理情况
				            }
			                
			               
			                //-----------------------------------------------------------------------
			                bai.setNewsMng(json_test.getString("newsMng"));//新闻宣传管理
			                bai.setMeetingMng(json_test.getString("MeetingMng"));//涉密会议管理
			                bai.setTestMng(json_test.getString("TestMng"));//外场试验管理
			                
			//-6---------------------------------------------------------------------
			                JSONArray strs7=json_test.getJSONArray("collaborationMng"); //协作配套管理
			                if(strs7.size()>0){
			                    bai.setCollaborationMng1(strs7.get(0)+"");//协作配套管理1采取了哪些保密管理措施
			                    bai.setCollaborationMng2(strs7.get(1)+"");//协作配套管理2采取了哪些保密管理措施
				            }
			                //-------------------------------------------------------------------------
			                bai.setForeignNationals(json_test.getString("foreignNationals"));//涉外管理
			                bai.setSecretCheck(json_test.getString("secretCheck"));//保密检查
			                bai.setKpAndRewardsAndPunishments(json_test.getString("kpAndRewardsAndPunishments"));//考核与奖惩
			                bai.setWorkFileMng(json_test.getString("workFileMng"));//工作档案管理
			//-6---------------------------------------------------------------------
			                JSONArray strs8=json_test.getJSONArray("workingFundsMng"); //协作配套管理                
			                if(strs8.size()>0){
			                	bai.setWorkingFundsMng1(strs8.get(0)+"");//上年度至本年度保密管理经费预算和支出情况
			                    bai.setWorkingFundsMng2(strs8.get(1)+"");//上年度至本年度保密管理经费预算和支出情况
				            }
							bai.setPlanState("1");
			                a = dao.save(bai);
		                }else{
		                	a=baii.getPkbaseApp()+"";
		                }
		              //-1---------得到个数组---------------------------------------------------
		                String strs=json_test.getString("secretCommittee");//保密委员会或保密工作领导小组(有从表这个表里不记录数据)
		                strs=strs.substring(2, strs.length()-2);
		                String[] strss=strs.split("\\],\\[");
		                for (int i = 0; i < strss.length; i++) {//循环json数组
		                	String ob  = strss[i];//得到json对象String  
		                	String[] strss1=ob.split(",");
		                	BaseSecretPeople b = (BaseSecretPeople)dao.findFirst("from BaseSecretPeople where name='"+strss1[0].substring(1,strss1[0].length()-1)+"' and pkbaseApp="+Integer.parseInt(a)+" ");
		                	if(b==null){
			                	BaseSecretPeople bsp=new BaseSecretPeople();
			                	if(StringUtils.isNotBlank(strss1[0]) && StringUtils.isNotBlank(strss1[0].substring(1,strss1[0].length()-1))){
			                		bsp.setName(strss1[0].substring(1,strss1[0].length()-1));
			                	}
			                	if(StringUtils.isNotBlank(strss1[1]) && StringUtils.isNotBlank(strss1[1].substring(1,strss1[1].length()-1))){
			                		bsp.setDept(strss1[1].substring(1,strss1[1].length()-1));
			                	}
								if(StringUtils.isNotBlank(strss1[2]) && StringUtils.isNotBlank(strss1[2].substring(1,strss1[2].length()-1))){
									bsp.setDuty(strss1[2].substring(1,strss1[2].length()-1));
								}
								if(StringUtils.isNotBlank(strss1[0]) && StringUtils.isNotBlank(strss1[0].substring(1,strss1[0].length()-1))){
									bsp.setPkbaseApp(Integer.parseInt(a));
									dao.save(bsp);
								}	
		                	}
		                }
		              //-3---------------------------------------------------------------------
		                String strs2=json_test.getString("mtcsol");//保密委员会或保密工作领导小组(有从表这个表里不记录数据)
		                strs2=strs2.substring(2, strs2.length()-2);
		                String[] strs2s=strs2.split("\\],\\[");
		                for (int i = 0; i < strs2s.length; i++) {//循环json数组
		                	String ob  = strs2s[i];//得到json对象String  
		                	String[] strs2s1=ob.split(",");
		                	
		                	BaseMtcsolPeople b = (BaseMtcsolPeople)dao.findFirst("from BaseMtcsolPeople where mtcsolName='"+strs2s1[0].substring(1,strs2s1[0].length()-1)+"' and pkbaseApp="+Integer.parseInt(a)+" ");
		                	if(b==null){
		                		BaseMtcsolPeople bmp=new BaseMtcsolPeople();
			                	if(StringUtils.isNotBlank(strs2s1[0]) && StringUtils.isNotBlank(strs2s1[0].substring(1,strs2s1[0].length()-1))){
			                		bmp.setMtcsolName(strs2s1[0].substring(1,strs2s1[0].length()-1));
			                	}
			                	if(StringUtils.isNotBlank(strs2s1[1]) && StringUtils.isNotBlank(strs2s1[1].substring(1,strs2s1[1].length()-1))){
			                		bmp.setMtcsolSex(strs2s1[1].substring(1,strs2s1[1].length()-1));
			                	}
								if(StringUtils.isNotBlank(strs2s1[2]) && StringUtils.isNotBlank(strs2s1[2].substring(1,strs2s1[2].length()-1))){
									bmp.setMtcsolAge(Integer.parseInt(strs2s1[2].substring(1,strs2s1[2].length()-1)));
								}
								if(StringUtils.isNotBlank(strs2s1[3]) && StringUtils.isNotBlank(strs2s1[3].substring(1,strs2s1[3].length()-1))){
			                		bmp.setMtcsolJob(strs2s1[3].substring(1,strs2s1[3].length()-1));
			                	}
			                	if(StringUtils.isNotBlank(strs2s1[4]) && StringUtils.isNotBlank(strs2s1[4].substring(1,strs2s1[4].length()-1))){
			                		bmp.setMtcsolSchool(strs2s1[4].substring(1,strs2s1[4].length()-1));
			                	}
								if(StringUtils.isNotBlank(strs2s1[5]) && StringUtils.isNotBlank(strs2s1[5].substring(1,strs2s1[5].length()-1))){
									bmp.setConfidentiality(strs2s1[5].substring(1,strs2s1[5].length()-1));
								}
								if(StringUtils.isNotBlank(strs2s1[0]) && StringUtils.isNotBlank(strs2s1[0].substring(1,strs2s1[0].length()-1))){
									bmp.setPkbaseApp(Integer.parseInt(a));
									dao.save(bmp);
								}
		                	}
		                }
	                }
	            }  
	            while (enu.hasMoreElements()) {  
	            	 ZipEntry zipElement = (ZipEntry) enu.nextElement();  
	            	 InputStream read = zipFile.getInputStream(zipElement);  
		             String fileName = zipElement.getName();  
		             if (fileName != null && fileName.indexOf(".") != -1) {// 是否为文件  
		                 unZipFile(zipElement, read, saveRootDirectory);  
		             }
	            }
	            if(list != null && list.size()>0){
	            	for (int i = 0; i < list.size(); i++) {
	            		BaseAttach b = (BaseAttach)dao.findFirst("from BaseAttach where fileName='"+list.get(i)+"' and pkbaseApp="+Integer.parseInt(a)+" ");
	            		if(b == null ){
	            			BaseAttach ba=new BaseAttach();
			 	        	ba.setFileName(list.get(i)+"");
			 	        	ba.setFilePath(saveRootDirectory);
			 	        	ba.setFileType("img");
			 	        	ba.setPkbaseApp(Integer.parseInt(a));
			 	        	dao.save(ba);
	            		}
					}
		        }
					File deletefile1 = new File(temp);
					deleteDir(deletefile1);


	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        if("1".equals(type)){
	        	 Alert_GoUrl("导入成功！", "closeCurrent","审批流程","");
	        }else{
	        	 Alert_GoUrl("导入成功！", "closeCurrent","spsysCheckFlow","");
	        }
	       
		} else {
			 if("1".equals(type)){
				 Alert_GoUrl("导入失败！", "closeCurrent","审批流程","");	
	        }else{
	        	Alert_GoUrl("导入失败！", "closeCurrent","spsysCheckFlow","");	
	        }
		}

		return toResult();
	}
	//删除文件
	public void deleteFile(String path){ 
		 File file=new File(path); 
		 if(file.exists()){ 
			 if(file.isFile()){ 
				 file.delete(); 
			 }else{ 
				 System.out.println("输入有误，不是文件！"); 
			 } 
		 }else{ 
			 System.out.println("文件不存在"); 
		 } 
	} 
	
	 /** 
     *  
     * @Description: TODO(找到文件并读取解压到指定目录) 
     * @param
     * @return void 返回类型 
     * @throws 
     */  
    public void unZipFile(ZipEntry ze, InputStream read,String saveRootDirectory) throws FileNotFoundException, IOException {  
        // 如果只读取图片，自行判断就OK.  
        String fileName = ze.getName();  
        // 判断文件是否符合要求或者是指定的某一类型  
//      if (fileName.equals("WebRoot/WEB-INF/web.xml")) {  
            // 指定要解压出来的文件格式（这些格式可抽取放置在集合或String数组通过参数传递进来，方法更通用）  
            File file = new File(saveRootDirectory + fileName);  
            if (!file.exists()) {  
                File rootDirectoryFile = new File(file.getParent());  
                // 创建目录  
                if (!rootDirectoryFile.exists()) {  
                    boolean ifSuccess = rootDirectoryFile.mkdirs();  
                    if (ifSuccess) {  
                        System.out.println("文件夹创建成功!");  
                    } else {  
                        System.out.println("文件创建失败!");  
                    }  
                }  
                // 创建文件  
                try {  
                    file.createNewFile();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
            // 写入文件  
            BufferedOutputStream write = new BufferedOutputStream(new FileOutputStream(file));  
            int cha = 0;  
            while ((cha = read.read()) != -1) {  
                write.write(cha);  
            }  
            // 要注意IO流关闭的先后顺序  
            write.flush();  
            write.close();  
            read.close();  
            // }  
//      }  
    }

	public void unZipFile1(ZipEntry ze, InputStream read,String saveRootDirectory) throws FileNotFoundException, IOException {
		// 如果只读取图片，自行判断就OK.
		String fileName = ze.getName();
		// 判断文件是否符合要求或者是指定的某一类型
//      if (fileName.equals("WebRoot/WEB-INF/web.xml")) {
		// 指定要解压出来的文件格式（这些格式可抽取放置在集合或String数组通过参数传递进来，方法更通用）
		File file = new File(saveRootDirectory + fileName);
		if (!file.exists()) {
			File rootDirectoryFile = new File(file.getParent());
			// 创建目录
			if (!rootDirectoryFile.exists()) {
				boolean ifSuccess = rootDirectoryFile.mkdirs();
				if (ifSuccess) {
					System.out.println("文件夹创建成功!");
				} else {
					System.out.println("文件创建失败!");
				}
			}
			// 创建文件
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 写入文件
		BufferedOutputStream write = new BufferedOutputStream(new FileOutputStream(file));
		int cha = 0;
		while ((cha = read.read()) != -1) {
			write.write(cha);
		}
		// 要注意IO流关闭的先后顺序
		write.flush();
		write.close();
		read.close();
		// }
//      }
	}
    
    public String listUnit() throws Exception{
	    StringBuilder sql = new StringBuilder();
		sql.append("");
		if(POST()){
			String companyName = request.getParameter("companyName");
			request.setAttribute("companyName",companyName);
			if(StringUtils.isNotEmpty(companyName)){
				sql.append("and companyName like '%"+companyName+"%'");
			}
			String creditCode = request.getParameter("creditCode");
			request.setAttribute("creditCode",creditCode);
			if(StringUtils.isNotEmpty(creditCode)){
				sql.append("and creditCode like '%"+creditCode+"%'");
			}
		}
		List dataList=dao.findByPage("from BaseInfo where 1=1 "+sql+" order by pkbase");
		request.setAttribute("dataList", dataList);
		
		return SUCCESS;
	}
    public String addUnit() throws Exception{
		String pkbase= request.getParameter("pkbase");
		baseInfo=(BaseInfo)dao.findOne(BaseInfo.class, Integer.parseInt(pkbase));
		request.setAttribute("baseInfo",baseInfo);
		return SUCCESS;
	}
	
	
	public String saveUnit() throws Exception{
		if("".equals(baseInfo.getPkbase())){
			baseInfo.setPkbase(null);
		}
		dao.update(baseInfo);
		Alert_GoUrl("保存成功","closeCurrent","基础信息",null);
		return  toResult(); 
	}
	public String deleteUnit() throws Exception{
		String pkbase=request.getParameter("pkbase");
		BaseInfo bi=(BaseInfo)dao.findOne(BaseInfo.class, Integer.parseInt(pkbase));
		
		List baiList = dao.findAll(" from BaseApplicationInfo where creditCode='"+bi.getCreditCode()+"'");
		if(baiList != null && baiList.size()>0){
			this.Alert_GoUrl("该单位已有申请数据，不能删除", "", "", "");
			return toErrorMessage();
		}else{
			dao.bulkUpdate("delete from BaseInfo where pkbase='"+pkbase+"'");
			Alert_GoUrl("删除成功","","基础信息",null);
			return  toResult();
		}
	}
	public String ajaxCertScore() throws Exception{
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		String pkbaseApp=request.getParameter("pkbaseApp");
		String certScore=request.getParameter("certScore");
		BaseApplicationInfo bai=(BaseApplicationInfo)dao.findOne(BaseApplicationInfo.class, Integer.parseInt(pkbaseApp));
		bai.setCertScore(certScore);
		dao.update(bai);
		return toData();
	}
	
	public String exportSMJY() throws Exception{
		int pkbaseApp = Integer.parseInt(request.getParameter("pkbaseApp"));
		BaseApplicationInfo bai = (BaseApplicationInfo) dao.findOne(BaseApplicationInfo.class, pkbaseApp);
		BaseInfo bi = (BaseInfo) dao.findFirst("from BaseInfo where creditCode='"+bai.getCreditCode()+"'");
		HashMap data = new HashMap();
//		Java2word j2w = new Java2word();
	    String sp=request.getRealPath("upload");
//	    j2w.toWord(sp+"\\审查意见书.doc",sp+"\\result.doc",data);
		
	    try{
	       	String x = new String((bi.getCompanyName()+"书面建议.doc").getBytes("GBK"), "ISO8859-1");
		    File file = new File(sp+"\\书面建议.doc");
		    InputStream fis = new BufferedInputStream(new FileInputStream(sp+"\\书面建议.doc"));
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
		return toResult();
	}
	public String exportSMSCJLD() throws Exception{
		int pkbaseApp = Integer.parseInt(request.getParameter("pkbaseApp"));
		BaseApplicationInfo bai = (BaseApplicationInfo) dao.findOne(BaseApplicationInfo.class, pkbaseApp);
		BaseInfo bi = (BaseInfo) dao.findFirst("from BaseInfo where creditCode='"+bai.getCreditCode()+"'");
		HashMap data = new HashMap();
//		Java2word j2w = new Java2word();
	    String sp=request.getRealPath("upload");
//	    j2w.toWord(sp+"\\审查意见书.doc",sp+"\\result.doc",data);
		
	    try{
	       	String x = new String((bi.getCompanyName()+"保密资格认定书面审查记录单.doc").getBytes("GBK"), "ISO8859-1");
		    File file = new File(sp+"\\保密资格认定书面审查记录单.doc");
		    InputStream fis = new BufferedInputStream(new FileInputStream(sp+"\\保密资格认定书面审查记录单.doc"));
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
		return toResult();
	}
	public String exportSLD() throws Exception{
		int pkbaseApp = Integer.parseInt(request.getParameter("pkbaseApp"));
		BaseApplicationInfo bai = (BaseApplicationInfo) dao.findOne(BaseApplicationInfo.class, pkbaseApp);
		BaseInfo bi = (BaseInfo) dao.findFirst("from BaseInfo where creditCode='"+bai.getCreditCode()+"'");
		HashMap data = new HashMap();
//		Java2word j2w = new Java2word();
	    String sp=request.getRealPath("upload");
//	    j2w.toWord(sp+"\\审查意见书.doc",sp+"\\result.doc",data);
		
	    try{
	       	String x = new String((bi.getCompanyName()+"受理单.doc").getBytes("GBK"), "ISO8859-1");
		    File file = new File(sp+"\\受理单.doc");
		    InputStream fis = new BufferedInputStream(new FileInputStream(sp+"\\受理单.doc"));
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
		return toResult();
	}
	public String exportBSLYJ() throws Exception{
		int pkbaseApp = Integer.parseInt(request.getParameter("pkbaseApp"));
		BaseApplicationInfo bai = (BaseApplicationInfo) dao.findOne(BaseApplicationInfo.class, pkbaseApp);
		BaseInfo bi = (BaseInfo) dao.findFirst("from BaseInfo where creditCode='"+bai.getCreditCode()+"'");
		HashMap data = new HashMap();
//		Java2word j2w = new Java2word();
	    String sp=request.getRealPath("upload");
//	    j2w.toWord(sp+"\\审查意见书.doc",sp+"\\result.doc",data);
		
	    try{
	       	String x = new String((bi.getCompanyName()+"不予受理通知书.doc").getBytes("GBK"), "ISO8859-1");
		    File file = new File(sp+"\\不予受理通知书.doc");
		    InputStream fis = new BufferedInputStream(new FileInputStream(sp+"\\不予受理通知书.doc"));
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
		return toResult();
	}
	public String exportCLBZTZS() throws Exception{
		int pkbaseApp = Integer.parseInt(request.getParameter("pkbaseApp"));
		BaseApplicationInfo bai = (BaseApplicationInfo) dao.findOne(BaseApplicationInfo.class, pkbaseApp);
		BaseInfo bi = (BaseInfo) dao.findFirst("from BaseInfo where creditCode='"+bai.getCreditCode()+"'");
		HashMap data = new HashMap();
//		Java2word j2w = new Java2word();
	    String sp=request.getRealPath("upload");
//	    j2w.toWord(sp+"\\审查意见书.doc",sp+"\\result.doc",data);
		
	    try{
	       	String x = new String((bi.getCompanyName()+"材料补正通知书.doc").getBytes("GBK"), "ISO8859-1");
		    File file = new File(sp+"\\材料补正通知书.doc");
		    InputStream fis = new BufferedInputStream(new FileInputStream(sp+"\\材料补正通知书.doc"));
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
		return toResult();
	}
	public String listAlreadyList() throws Exception{
		//List list = dao.findAll("from BaseApplicationInfo where  DATEDIFF(str_to_date(data19,'%Y-%m-%d'),sysdate())<30 and certType='013001' and certStatus !='012003'");
		List list = dao.findAll("from BaseApplicationInfo where spStatus <> 0 ");
		String data="";
		if(list != null && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				BaseApplicationInfo cti = (BaseApplicationInfo)list.get(i);
				if(cti != null && StringUtils.isNotEmpty(cti.getCreditCode())){
					BaseInfo ui = (BaseInfo)dao.findFirst("from BaseInfo where creditCode='"+cti.getCreditCode()+"'");
					data+=ui.getCompanyName()+";"+cti.getAppTime()+";"+cti.getCreditCode()+"|";
				}
			}
		}
		if(list == null || list.size() == 0){
			data="no";
		}
		request.setAttribute("data", data);
		return toData();
	}
	public String updateCertificationAffirm() throws Exception{
		String pkbaseApp=request.getParameter("pkbaseApp");
		BaseApplicationInfo bai=(BaseApplicationInfo)dao.findFirst("from BaseApplicationInfo where pkbaseApp='"+pkbaseApp+"'");
		bai.setSpStatus("0");
		dao.update(bai);
		return toData();
	}
	private byte[] getBytes(String filePath){
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}
	/**
	 * 复制文件
	 * @param fromFile
	 * @param toFile
	 * <br/>
	 * 2016年12月19日  下午3:31:50
	 * @throws IOException
	 */
	public void copyFile(File fromFile,File toFile) throws IOException{
		FileInputStream ins = new FileInputStream(fromFile);
		FileOutputStream out = new FileOutputStream(toFile);
		byte[] b = new byte[1024];
		int n=0;
		while((n=ins.read(b))!=-1){
			out.write(b, 0, n);
		}

		ins.close();
		out.close();
	}
	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 * @param dir 将要删除的文件目录
	 * @return boolean Returns "true" if all deletions were successful.
	 *                 If a deletion fails, the method stops attempting to
	 *                 delete and returns "false".
	 */
	private static void deleteDir(File dir) {
		File file = new File("");
		if (dir.isDirectory()) {
			String[] children = dir.list();
			//递归删除目录中的子目录下
			for (int i=0; i<children.length; i++) {
				 deleteDir(new File(dir, children[i]));

			}
		}
		// 目录此时为空，可以删除
	}
}
