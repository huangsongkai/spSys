package com.xunj.action.system;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import org.apache.commons.lang.StringUtils;
import com.xunj.action.common.AbstractAction;
import com.xunj.core.MD5;
import com.xunj.core.SessionBean;
import com.xunj.po.SysFuncGroup;
import com.xunj.po.SysFuncGroupUser;
import com.xunj.po.SysFuncInfo;
import com.xunj.po.SysOperationRole;
import com.xunj.po.SysUserList;
import com.xunj.util.StateConst;

@SuppressWarnings("unchecked")
public class phoneLoginAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6844976573603746218L;
	//用户信息
	private SysUserList userList;
	
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
		return toJsp("/phoneLogin.jsp");
	}  
	return SUCCESS;
    }

	
	public String sj() throws Exception {
		return toJsp("phoneLogin.jsp");
	}
	
	public String phoneLogin() throws Exception {
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
								"and fgu.userId='"+userlist.getUserId()+"' and fi.funcId = '007001010'";
						
						//用户功能点对应表
//						List userfuncs=dao.findAll(hql_userfunc);
//						//用户功能组对应表
						List userfuncs1=dao.findAll(hql_userfuncGroup);
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
							if(funcinfo.getFuncParentId().length()==3){
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
			
			Long customerQualificationSize = (Long) dao.findCount("select count(distinct t.customer) from CustomerQualification" +
				" t where sysdate > t.expriationDate and t.customer in (select a.pkCustomer from Customer a where a.useState = 'A')");
			Long clientNoSellSize = (Long) dao.findCount("select count(clientId) from SellBill group by clientId having sysdate-MAX(billDate)>100");
			Long reviewStateSaleSize = (Long) dao.findCount("select count(*) from SellBill where billState ='D'");
			Long overTimeSize = Long.valueOf(0);//(Long) dao.findCount("select count(*) from StorageBillsDetail where effectiveDate is not null and to_date(effectiveDate,'yyyyMMdd')-sysdate<180");
			request.getSession().setAttribute("customerQualificationSize", customerQualificationSize);
			request.getSession().setAttribute("clientNoSellSize", clientNoSellSize);
			request.getSession().setAttribute("reviewStateSaleSize", reviewStateSaleSize);
			request.getSession().setAttribute("overTimeSize", overTimeSize);
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
		SysOperationRole role=(SysOperationRole) dao.findOne(SysOperationRole.class, userlist.getDeptId());
		sessionbean.setDeptName(role.getRoleName());
		request.getSession().setAttribute("sessionbean", sessionbean);
	}
//	public String mainWork() throws Exception {
//		Long customerQualificationSize = (Long) dao.findCount("select count(distinct t.customer) from CustomerQualification" +
//				" t where sysdate > t.expriationDate and t.customer in (select a.pkCustomer from Customer a where a.useState = 'A')");
//		Long clientNoSellSize = (Long) dao.findCount("select count(clientId) from SellBill group by clientId having sysdate-MAX(billDate)>100");
//		Long reviewStateSaleSize = (Long) dao.findCount("select count(*) from SellBill where billState ='D'");
//		Long overTimeSize = (Long) dao.findCount("select count(*) from StorageBillsDetail where to_date(effectiveDate,'yyyyMMdd')-sysdate<180");
//		request.setAttribute("customerQualificationSize", customerQualificationSize);
//		request.setAttribute("clientNoSellSize", clientNoSellSize);
//		request.setAttribute("reviewStateSaleSize", reviewStateSaleSize);
//		request.setAttribute("overTimeSize", overTimeSize);
//		return toJsp("/jsp/sale/old/addSaleBill.jsp");
//	}

}
