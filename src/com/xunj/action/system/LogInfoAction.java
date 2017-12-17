package com.xunj.action.system;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xunj.action.common.AbstractAction;
import com.xunj.core.ParaUnit;
import com.xunj.po.SysLoginfo;
import com.xunj.util.Common;

/**
 * 日志管理
 * @author 王瑾
 *
 */
public class LogInfoAction extends AbstractAction{


	/**
	 * 
	 */
	private static final long serialVersionUID = -1685747052749519609L;
	private SysLoginfo loginfo;
	public SysLoginfo getLoginfo() {
		return loginfo;
	}
	public void setLoginfo(SysLoginfo loginfo) {
		this.loginfo = loginfo;
	}
	/**
	 * 日志列表显示
	 * @return
	 * @throws Exception
	 */
	public String listLog() throws Exception{
		dao.setRecordLog(false);//不记录日志
		ArrayList<ParaUnit> paralist = new ArrayList<ParaUnit>();
		if (POST()) {
			if(!"".equals(loginfo.getUserId()))
				paralist.add(new ParaUnit("userId", loginfo.getUserId(), ParaUnit.LIKE));
			if(!"".equals(loginfo.getUserName()))
				paralist.add(new ParaUnit("userName", loginfo.getUserName(), ParaUnit.LIKE));
			if(!"".equals(loginfo.getHandleType()))
				paralist.add(new ParaUnit("handleType", loginfo.getHandleType(), ParaUnit.LIKE));
			if(!"".equals(loginfo.getIpAddress()))
				paralist.add(new ParaUnit("ipAddress", loginfo.getIpAddress(), ParaUnit.LIKE));
			
		
			String dateFrom = request.getParameter("dateFrom");
			String dateTo = request.getParameter("dateTo");
			if(!dateFrom.equals("")&&!dateTo.equals(""))
			{
				Date d1 = Common.newDate(dateFrom+" 00:00:00","yyyy-MM-dd hh:mm:ss");
				Date d2 = Common.newDate(dateTo+" 23:59:59","yyyy-MM-dd hh:mm:ss");
				paralist.add(new ParaUnit("handleTime", d1, ParaUnit.GE));
				paralist.add(new ParaUnit("handleTime", d2, ParaUnit.LE));
			}
			request.setAttribute("dateFrom", dateFrom);
			request.setAttribute("dateTo", dateTo);
		}
		paralist.add(new ParaUnit("handleTime", ParaUnit.DESC, ParaUnit.ORDER));
		List list = dao.criteriaByPage(SysLoginfo.class, paralist);
		request.setAttribute("logList", list);
		dao.setRecordLog(true);
		return toJsp("/jsp/system/log_info/list_logInfo.jsp");
	}
	/**
	 * 日志查看
	 * @return
	 * @throws Exception
	 */
	public String showLog() throws Exception{
		dao.setRecordLog(false);//不记录日志
		loginfo = (SysLoginfo)dao.findOne(SysLoginfo.class, loginfo.getLogId());
		dao.setRecordLog(true);
		return toJsp("/jsp/system/log_info/show_logInfo.jsp");
	}
	/**
	 * 导出并移除日志
	 * @return
	 * @throws Exception
	 */
	public String removeLog() throws Exception {
		
		String tmp="";
		ArrayList<ParaUnit> paralist = new ArrayList<ParaUnit>();
		paralist.add(new ParaUnit("userId", loginfo.getUserId(), ParaUnit.LIKE));
		paralist.add(new ParaUnit("userName", loginfo.getUserName(), ParaUnit.LIKE));
		paralist.add(new ParaUnit("handleType", loginfo.getHandleType(), ParaUnit.LIKE));

		paralist.add(new ParaUnit("ipAddress", loginfo.getIpAddress(), ParaUnit.LIKE));
		
		String dateFrom = request.getParameter("dateFrom");
		String dateTo = request.getParameter("dateTo");
		if(!dateFrom.equals("")&&!dateTo.equals(""))
		{
			Date d1 = Common.newDate(dateFrom+" 00:00:00","yyyy-MM-dd hh:mm:ss");
			Date d2 = Common.newDate(dateTo+" 23:59:59","yyyy-MM-dd hh:mm:ss");
			paralist.add(new ParaUnit("handleTime", d1, ParaUnit.GE));
			paralist.add(new ParaUnit("handleTime", d2, ParaUnit.LE));
			tmp+=" and handleTime >='"+dateFrom+" 00:00:00' and handleTime <= '"+dateTo+" 23:59:59";
		}
		
		paralist.add(new ParaUnit("handleTime", ParaUnit.DESC, ParaUnit.ORDER));
		List list = dao.criteriaQuery(SysLoginfo.class, paralist);

		request.setAttribute("logList", list);
		String delHql ="delete SysLoginfo where userId like '%"+loginfo.getUserId()+"%' " +
				" and userName like '%"+loginfo.getUserName()+"%' " +
				" and handleType like '%"+loginfo.getHandleType()+"%' " +
				" and ipAddress like '%"+loginfo.getIpAddress()+"%'"+tmp;
		System.out.println(delHql);
		dao.bulkUpdate(delHql);
		return toJsp("/jsp/system/log_info/export_logInfo.jsp");
	}


}