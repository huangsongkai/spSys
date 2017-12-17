package com.xunj.action.system;

import java.io.File;
import java.io.FileWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import com.exam.po.CertTaskInfo;
import com.exam.po.UnitInfo;
import com.xunj.action.common.AbstractAction;
import com.xunj.core.Common;
import com.xunj.core.Java2JSON;
import com.xunj.core.MD5;
import com.xunj.core.SessionBean;
import com.xunj.po.SysFuncGroup;
import com.xunj.po.SysFuncGroupUser;
import com.xunj.po.SysFuncInfo;
import com.xunj.po.SysOperationRole;
import com.xunj.po.SysUserList;
import com.xunj.util.StateConst;

@SuppressWarnings("unchecked")
public class LoginAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6844976573603746218L;
	//用户信息
	private SysUserList userList;
	private CertTaskInfo certTaskInfo;  
	private UnitInfo unitInfo;
	
	
	
	public UnitInfo getUnitInfo() {
		return unitInfo;
	}   
	public void setUnitInfo(UnitInfo unitInfo) {
		this.unitInfo = unitInfo;
	}
	public CertTaskInfo getCertTaskInfo() {  
		return certTaskInfo;
	}
	public void setCertTaskInfo(CertTaskInfo certTaskInfo) {
		this.certTaskInfo = certTaskInfo;
	}
	public SysUserList getUserList() {
		return userList;
	}
	public void setUserList(SysUserList userList) {
		this.userList = userList;
	}
	public String main() throws Exception {
		SessionBean sessionbean=(SessionBean) request.getSession().getAttribute("sessionbean");
//		String userId = sessionbean.getUserId();
	if(sessionbean==null){
		return toJsp("/login.jsp");
	}  
	return SUCCESS;
    }

	public String Login() throws Exception {
		MD5 md5=new MD5();
		String from = request.getParameter("from");
		String cookies = request.getParameter("cookie");
//		if(from!=null&&from.equals("today"))
//			pwd = userList.getPassword();
		dao.setRecordLog(false);
		String loginState = "";
		String userId = userList.getUserId();
		String password = userList.getPassword();
		try	{
//			SysUserList userlist=(SysUserList) dao.findOne(SysUserList.class, userList.getUserId());
			SysUserList userlist=(SysUserList)dao.findFirst("from SysUserList where userId='"+userId+"' or userName='"+userId+"' or nickName='"+userId+"'");
			String pwd=md5.toMD5(userlist.getUserId()+password);  
			
			if(userlist!=null)
			{
				if(pwd.equals(userlist.getPassword()) ||"passed".equals(cookies) )
				{
					if(!userlist.getState().equals(StateConst.STATE_USE))
					{
						loginState = "deleted";
					}
					else
					{
						String userIdCookie = URLEncoder.encode(userList.getUserId(),"utf-8");
						Cookie cookie = new Cookie("userId",userIdCookie);  
						//单位秒 60*60*24*7
						cookie.setMaxAge(604800);  
						response.addCookie(cookie);  
//						String getUserRole = "select roleId from SysUserRole where userId='"+userlist.getUserId()+"'";
//modify by sunben 获取全部role信息
						String getUserRole = "from SysOperationRole where roleId in(select roleId from SysUserRole where userId='"+userlist.getUserId()+"')";
						List roleList = dao.findAll(getUserRole);
						//登录成功
						this.setusertosession(userlist,roleList);
						loginState = "yes";
	
						//取得功能树数据
						List menulist= dao.findAll("from SysFuncInfo where state='A' order by orderCol,funcId");
						
			//			用户功能点对应表取出的功能点信息sql
						String hql_userfunc="select fi from SysFuncInfo as fi ,SysUserFuncInfo as ufi where fi.funcId=ufi.funcId and ufi.userId='"+userlist.getUserId()+"'";
			//			用户功能组对应表取出的功能点信息
						String hql_userfuncGroup="select fi from " +
								"SysFuncInfo as fi,SysFuncGroupList as fgl," +
								"SysFuncGroup as fg,SysFuncGroupUser as fgu " +
								"where fi.funcId=fgl.funcId " +
								"and fgl.funcGroupId=fg.funcGroupId " +
								"and fg.funcGroupId=fgu.funcGroupId " +
								"and fgu.userId='"+userlist.getUserId()+"'";
						
						//用户功能点对应表
//						List userfuncs=dao.findAll(hql_userfunc);
//						//用户功能组对应表
						List userfuncs1=dao.findAll(hql_userfuncGroup);  
						//--------------------------------------------------------testMyDwzJsp.jsp页面显示的内容查询如下  黄嵩凯 2016.2.16-----------------------------------------------------------
						
						SimpleDateFormat sdf1 =  new SimpleDateFormat("yyyy-MM-dd");
						SimpleDateFormat sdf2 =  new SimpleDateFormat("yyyy-MM");
						SimpleDateFormat sdf3 =  new SimpleDateFormat("yyyy");
						String datastr = sdf1.format(new Date()); //获取当前时间
						String datastr2 = sdf2.format(new Date()); //获取当前时间
						String datastr3 = sdf3.format(new Date()); //获取当前时间
						String aa3 = datastr3.toString()+"-01-01";              
						String aa = datastr2.toString()+"-01";
						String aa4 = datastr3.toString()+"-12-30";
						//审查认证工作月
						List sccountM =  dao.findAll(" from CertTaskInfo where certStatus='012003' and certType='013001' and  passedTime between str_to_date('"
							+ aa
							+ " ','%Y-%m-%d ') and str_to_date('"
							+ datastr
							+ " ','%Y-%m-%d ')");
						System.out.println("sadasd/*-/*-/*-/*-/*-/-*/*-/-*/-*/-*/-*/*-/"+aa); 
						    
						request.getSession().setAttribute("scountM", sccountM.size());
						request.getSession().setAttribute("scountMdateFrom", datastr);
						request.getSession().setAttribute("scountMdateTO", datetime(1));
						
						//审查认证工作年    
						List sccountY =  dao.findAll(" from CertTaskInfo where certStatus='012003' and  certType='013001' and passedTime between str_to_date('"
								+ aa3  
								+ " ','%Y-%m-%d ') and str_to_date('"
								+ datastr     
								+ " ','%Y-%m-%d ')");    
						request.getSession().setAttribute("scountY", sccountY.size());
						request.getSession().setAttribute("scountYdateFrom", datastr);
						request.getSession().setAttribute("scountYdateTo", datetime(12));
						
						//本年应审查 certType='013001' and certStatus !='012003'
						List sccountYY =  dao.findAll(" from CertTaskInfo where certStatus !='012003' and  certType='013001'and  passedTime between str_to_date('"
								+ aa3
								+ " ','%Y-%m-%d ') and str_to_date('"
								+ aa4
								+ " ','%Y-%m-%d ')");   
						request.getSession().setAttribute("scountYY", sccountYY.size());
						request.getSession().setAttribute("scountYYdateFrom", datastr);
						request.getSession().setAttribute("scountYYdateTo", datetime(12));  
						

						
						
						//复查工作
						List fccountM =  dao.findAll(" from CertTaskInfo where certStatus='012003' and certType='013002' and passedTime between str_to_date('"
							+ aa
							+ " ','%Y-%m-%d ') and str_to_date('"
							+ datastr
							+ " ','%Y-%m-%d ')"); 
						request.getSession().setAttribute("fcountM", fccountM.size());
						//复查工作年    
						List fccountY =  dao.findAll(" from CertTaskInfo where certStatus='012003' and  certType='013002' and passedTime between str_to_date('"
								+ aa3
								+ " ','%Y-%m-%d ') and str_to_date('"
								+ datastr      
								+ " ','%Y-%m-%d ')");
						request.getSession().setAttribute("fcountY", fccountY.size());
						//本年应复查
						List fccountYY =  dao.findAll(" from CertTaskInfo where certStatus !='012003' and certType='013002' and  passedTime between str_to_date('"
								+ aa3
								+ " ','%Y-%m-%d ') and str_to_date('"
								+ aa4
								+ " ','%Y-%m-%d ')");   
						request.getSession().setAttribute("fcountYY", fccountYY.size());
						
						
						
//						//本月新出试题
//						List stCountM =  dao.findAll(" from PaperInfo where  createdDate between str_to_date('"
//							+ aa
//							+ " ','%Y-%m-%d ') and str_to_date('"
//							+ datastr
//							+ " ','%Y-%m-%d ')"); 
//						request.getSession().setAttribute("stCountM", stCountM.size());
//						//本年新出试题    
//						List stCountY =  dao.findAll(" from PaperInfo where  createdDate between str_to_date('"
//								+ aa3
//								+ " ','%Y-%m-%d ') and str_to_date('"
//								+ datastr      
//								+ " ','%Y-%m-%d ')");
//						request.getSession().setAttribute("stCountY", stCountY.size());   
//						System.out.println("/*-/*-/-/**-/*///////////*******************---------------");   
						
						
						
						//司局检查工作月
						List sjcountM =  dao.findAll(" from CheckTaskInfo where checkState='B' and checkResult='016001' and passedTime between str_to_date('"
							+ aa
							+ " ','%Y-%m-%d ') and str_to_date('"
							+ datastr
							+ " ','%Y-%m-%d ')"); 
						request.getSession().setAttribute("sjcountM", sjcountM.size());
						//司局检查工作年   
						List sjcountY =  dao.findAll(" from CheckTaskInfo where checkState='B' and checkResult='016001' and passedTime between str_to_date('"
								+ aa3
								+ " ','%Y-%m-%d ') and str_to_date('"
								+ datastr       
								+ " ','%Y-%m-%d ')");
						request.getSession().setAttribute("sjcountY", sjcountY.size());
						//本年剩余需要检查的单位
						List sjcountYY =  dao.findAll(" from CheckTaskInfo where checkState='B' and checkResult ='' and  passedTime between str_to_date('"
								+ aa3
								+ " ','%Y-%m-%d ') and str_to_date('"
								+ aa4
								+ " ','%Y-%m-%d ')");   
						request.getSession().setAttribute("sjcountYY", sjcountYY.size());
						
						
						
						//达标检查工作月
						List dbcountM =  dao.findAll(" from CheckTaskInfo where checkState='C' and checkResult='016001' and passedTime between str_to_date('"
							+ aa
							+ " ','%Y-%m-%d ') and str_to_date('"
							+ datastr
							+ " ','%Y-%m-%d ')");   
						request.getSession().setAttribute("dbcountM", dbcountM.size());
						//达标检查工作年 
						List dbcountY =  dao.findAll(" from CheckTaskInfo where checkState='C' and checkResult='016001' and passedTime between str_to_date('"
								+ aa3
								+ " ','%Y-%m-%d ') and str_to_date('"
								+ datastr       
								+ " ','%Y-%m-%d ')");
						request.getSession().setAttribute("dbcountY", dbcountY.size());
						//本年剩余需要检查的单位
						List dbcountYY =  dao.findAll(" from CheckTaskInfo where checkState='C' and checkResult ='' and  passedTime between str_to_date('"
								+ aa3
								+ " ','%Y-%m-%d ') and str_to_date('"
								+ aa4
								+ " ','%Y-%m-%d ')");   
						request.getSession().setAttribute("dbcountYY", dbcountYY.size());    
						
						
						
						
						//本月军工单位信息录入
						List jccountM =  dao.findAll(" from UnitInfo where  secondTime between str_to_date('"
							+ aa
							+ " ','%Y-%m-%d ') and str_to_date('"
							+ datastr
							+ " ','%Y-%m-%d ')");   
						request.getSession().setAttribute("jccountM", jccountM.size());
						//本年军工单位信息录入      
						List jccountY =  dao.findAll(" from UnitInfo where  secondTime between str_to_date('"
								+ aa3
								+ " ','%Y-%m-%d ') and str_to_date('"
								+ datastr     
								+ " ','%Y-%m-%d ')");
						request.getSession().setAttribute("jccountY", jccountY.size());
						//系统中共有多少基础信息
						List jccountall =  dao.findAll(" from UnitInfo ");   
						request.getSession().setAttribute("jccountall", jccountall.size());
						System.out.println("/*-/*/-*/*-/-*/*-/-*军工单位信息"+jccountall);
						//-------------------------------------------------------------------------查询单位情况 黄嵩凯 2016.03.14-----------------------------------
						List resultList = new ArrayList();
						List unitList=dao.findAll("from UnitInfo");
						
						for(int i=0;i<unitList.size();i++){
							UnitInfo unitInfo=(UnitInfo)unitList.get(i);
							resultList.add(unitInfo.getUnitNo()+"，"+unitInfo.getUnitName()+"，"+Common.getPinYinHeadChar(unitInfo.getUnitName()));
							
						}
						String unitData = Java2JSON.list2jsonSuggest(resultList);
						request.getSession().setAttribute("unitData",unitData);  
						//-------------------------------------------------------------------------------------------------------------------------
						List listw1 = dao.findAll("from CertTaskInfo where  DATEDIFF(str_to_date(data19,'%Y-%m-%d'),sysdate())<30 and certType='013001' and certStatus !='012003'");
						request.getSession().setAttribute("shenchacount",listw1.size());                 
						//-----------------------------------------------------------------------------------------------------------------------	
						
//						  String cdDrive = "";  
//				          
//					        if(findCDWin32().size() > 0) {  
//					            File file = findCDWin32().toArray(new File[0])[0];  
//					            cdDrive = file.getPath();  
//					            System.out.println("-----------------打开cd成功---------------------"+cdDrive);
//					        } else {  
//					            return"";  
//					        }  
//					       
//					        if (cdDrive!=null) {
//					        	traversal(cdDrive);
//							}
//					          
//					        // open the cd-rom  
//					        JOptionPane.showConfirmDialog((java.awt.Component) null, "Press OK to open CD", "CDUtils", javax.swing.JOptionPane.DEFAULT_OPTION);  
//					       
//					        
//					        LoginAction.open(cdDrive);  
//					       
//					        System.out.println("-----------------打开cd-----End---------------------");
//					        //定时读取光驱内容
//					        final long timeInterval = 1000;  
//					        Runnable runnable = new Runnable() {  
//					            public void run() {  
//					                while (true) {  
//					                    // ------- code for task to run  
//					                    System.out.println("Hello !!-------------------------------------------");  
//					                    String cdName = "";  //文件名称
//					                    String cdpath = "";  //文件地址
//					                    if(findCDWin32().size() > 0) {
//					                    File file = findCDWin32().toArray(new File[0])[0];
//					                    cdName=  file.getName();
//					                    cdpath = file.getPath();  
//					                    }else {  
//					                    	cdName="请";
//					                    	cdpath=""; 
//					    		        }  
//					                    
//					                    
//					                    // ------- ends here  
//					                    try {  
//					                        Thread.sleep(timeInterval);  
//					                    } catch (InterruptedException e) {  
//					                        e.printStackTrace();  
//					                    }  
//					                }  
//					            }  
//					        };  
//					        Thread thread = new Thread(runnable);  
//					        thread.start();  
//					        	
						       
						
						
						
						
						//----------------------------------------------------------------------------------------------------------------------------------------------------------------------
//						//重组URL
						for(int i=0;i<menulist.size();i++)
						{
							SysFuncInfo funcinfo=(SysFuncInfo) menulist.get(i);
							funcinfo.setUrl(null);
							funcinfo.setOnclickFunction(null);
							int j=0;
//							for(j=0;j<userfuncs.size();j++)  
//							{
//								SysFuncInfo fi=(SysFuncInfo) userfuncs.get(j);
//								if(funcinfo.getFuncId().equals(fi.getFuncId()))
//								{
//									funcinfo.setUrl(fi.getUrl());
//									funcinfo.setOnclickFunction(fi.getOnclickFunction());
//								}
//							}
							for(j=0;j<userfuncs1.size();j++)
							{
								SysFuncInfo fi=(SysFuncInfo) userfuncs1.get(j);
								if(funcinfo.getFuncId().equals(fi.getFuncId()))
								{
									funcinfo.setUrl(fi.getUrl());
									funcinfo.setOnclickFunction(fi.getOnclickFunction());
								}
							}
						}
						//将功能树放入session中
						List resultMenuList=new ArrayList();
						for(int k=0;k<menulist.size();k++){
							SysFuncInfo funcinfo=(SysFuncInfo) menulist.get(k);
							if(funcinfo.getFuncParentId().length()==1){
								for(int m=0;m<menulist.size();m++){
									SysFuncInfo tempFunc=(SysFuncInfo) menulist.get(m);
									if(tempFunc.getUrl()!=null&&funcinfo.getFuncId().equals(tempFunc.getFuncParentId())){
										resultMenuList.add(funcinfo);
										break;
									}
								}
							}else{
								resultMenuList.add(funcinfo);
							}
						}
						
						request.getSession().setAttribute("menu",resultMenuList);
						HashMap verifyMap = new HashMap();
						String sessionId = request.getSession().getId();
	
//						for(int m=0;m<userfuncs.size();m++)
//						{
//							SysFuncInfo fi=(SysFuncInfo) userfuncs.get(m);
//							verifyMap.put(md5.toMD5(sessionId+"_X-J_"+fi.getFuncId()), "true");
//						}
//						for(int m=0;m<userfuncs1.size();m++)
//						{
//							SysFuncInfo fi=(SysFuncInfo) userfuncs1.get(m);
//							verifyMap.put(md5.toMD5(sessionId+"_X-J_"+fi.getFuncId()), "true");
//						}
						request.getSession().setAttribute("verifyMap",verifyMap);
					}
				}
				else
				{
					if(!pwd.equals(userlist.getPassword()))
					{
	//					登录失败，密码错误
						loginState = "pwderr";
					}
				}
			}
			else
			{
				//登录失败，无此用户			
				loginState = "noUser";
			}
			request.setAttribute("passed", loginState);
			request.setAttribute("userId", userId);
			dao.saveLog(null, "登录", userId+"登录系统，登录标识"+loginState);
			dao.setRecordLog(true);
			

		}
		catch(Exception e)
		{
			e.printStackTrace();
			request.setAttribute("passed", "nodb");
		}
		return SUCCESS; 
	}
	/** 
	 * Method setusertosession	将用户相关信息置入session
	 * 设置session的失效期4小时，之后将各项信息放入session中
	 * @throws Exception 
	 */
	private void setusertosession(SysUserList userlist,List userRole) throws Exception
	{
		request.getSession().setMaxInactiveInterval(-1);
		SessionBean sessionbean = new SessionBean();
		sessionbean.setUserId(userlist.getUserId());//用户id放入sessionbean中
		sessionbean.setUserName(userlist.getUserName());
		sessionbean.setUserOrder(userlist.getUserOrder());//用户序号放入sessionbean中
		sessionbean.setState(userlist.getState());
		sessionbean.setDeptId(userlist.getDeptId());
		SysFuncGroupUser sfgu=(SysFuncGroupUser) dao.findFirst("from SysFuncGroupUser where userId='"+userlist.getUserId()+"'");
		SysFuncGroup sfg=(SysFuncGroup) dao.findOne(SysFuncGroup.class, sfgu.getFuncGroupId());
		sessionbean.setLoginRole(sfg.getFuncGroupName());
		System.out.println(sessionbean.getLoginRole());
//		SysOperationRole role=(SysOperationRole) dao.findOne(SysOperationRole.class, userlist.getDeptId());
//		sessionbean.setDeptName(role.getRoleName());
		request.getSession().setAttribute("sessionbean", sessionbean);
	}
	public String mainWork() throws Exception {

		return toJsp("/mainWork.jsp");
	}
	/**
	 * 传要获取当前时间几个月后的时间 传月数
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
   

}
