package com.xunj.action.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.xunj.core.ParaUnit;
import com.xunj.util.Util;

public class CommonActionUtil {

	public static String[][] beanMapping ={
		{"userlist", "com.xunj.po.SysUserList","java.lang.String"},
		{"codeKind", "com.xunj.po.CodeKind","java.lang.String"},
		{"codeDict", "com.xunj.po.CodeDict","java.lang.String"},
		{"funcInfo", "com.xunj.po.SysFuncInfo","java.lang.String"},
		{"funcGroup", "com.xunj.po.SysFuncGroup","java.lang.String"},
		{"infoPublish", "com.hitzd.po.SysInfoPublish","java.lang.String"}		
	};
	public Class getMappingBean(String key) throws ClassNotFoundException {
		String beanFullName = null;
		for(int i=0;i<beanMapping.length;i++)
		{
			if(beanMapping[i][0].equals(key))
			{
				beanFullName=beanMapping[i][1];
				break;
			}
		}
		Class obj;
		try {
			if (beanFullName == null)
				throw new ClassNotFoundException("映射类文件不在配置范围内或未指定操作的类，beanName="+key);
			obj = Class.forName(beanFullName);
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundException("映射类文件不在配置范围内或未指定操作的类，beanName="+key);
		}

		return obj;
	}

	/**
	 * 返回指定类型转换后的bean的主键值,map中存放表的主键类型
	 * 
	 * @param beanName
	 * @param value
	 * @return
	 * @throws ClassNotFoundException
	 */
	public Object getBeanIdType(String beanName, String value) {
		String beanIdType = null;
		for(int i=0;i<beanMapping.length;i++)
		{
			if(beanMapping[i][0].equals(beanName))
				beanIdType=beanMapping[i][2];
		}
		Object returnValue = null;

		if (beanIdType.equals("java.lang.Integer"))
			returnValue = Integer.valueOf(value);
		else if (beanIdType.equals("java.lang.Long"))
			returnValue = Long.valueOf(value);
		else
			returnValue = value;

		return returnValue;
	}
	/**
	 * 返回指定类主键类型,map中存放表的主键类型
	 * 
	 * @param beanName
	 * @param value
	 * @return
	 * @throws ClassNotFoundException
	 */
	public String getBeanKeyType(String beanName) {
		String beanKeyType = null;
		for(int i=0;i<beanMapping.length;i++)
		{
			if(beanMapping[i][0].equals(beanName))
				beanKeyType=beanMapping[i][2];
		}
		return beanKeyType;
	}

	public Object chageType(String value, String type) {
		Util u = new Util();
		if (type.equals("String"))
			return value;
		else if (type.equals("Date"))
			return u.StringtoDate(value);
		else if (type.equals("Double"))
			return u.StrToDouble(value);
		else if (type.equals("Long"))
			return u.StrToLong(value);
		else if (type.equals("Integer"))
			return u.StrToInteger(value);
		return null;
	}

	public String getValue(String key, HttpServletRequest request) {
		String value = null;
		value = request.getParameter(key);
		if (value == null)
			value = "";
		return value;
	}

	/**
	 * 根据传入的json字符串返回对应的QueryBean，并将根据指定的参数将json内容放入getValue写入request
	 * 
	 * @param jsonContent
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public QueryBean getQueryBean(String jsonContent, HttpServletRequest request) {
		// 查询条件list
		List lists = new ArrayList();
		// or条件list
		List orlists = new ArrayList();
		// 查询条件json内容
		// 返回显示页面的map
		Map getValue = new HashMap();
		Map staticCondition = new HashMap();

		QueryBean qb = null;

		if (StringUtils.isNotBlank(jsonContent)) {
			// 将查询条件转换为BEAN
			JSONObject jb = JSONObject.fromObject(jsonContent);
			qb = (QueryBean) JSONObject.toBean(jb, QueryBean.class);

			// 查询条件字段
			BeanFields[] bf = qb.getBeanFields();
			// or查询
			String or[] = qb.getOr();
			// 迭代所有字段
			if (bf != null) {
				for (BeanFields obj : bf) {
					String value = obj.getValue();
					if (!value.equals("")) {
						// 默认and,判断是否在or条件中
						boolean test = false;
						if (or != null) {
							for (String ors : or) {
								if (ors.equals(obj.getFieldName()))// 是or条件
								{
									test = true;

								}
							}
						}
						if (test) {
							orlists.add(new ParaUnit(obj.getFieldName(), this
									.chageType(value, obj.getDataType()), obj
									.getRole()));
						} else {
							lists.add(new ParaUnit(obj.getFieldName(), this
									.chageType(value, obj.getDataType()), obj
									.getRole()));
						}
					}
					if (obj.getSuffix() == null)
						obj.setSuffix("");
					// 不为静态条件查询，写入返回值
					if (qb.getStaticCondition() == null)
						getValue.put(obj.getFieldName() + obj.getSuffix(),
								value);
					else {
						// 写入返回值
						if (qb.getGetValueName() != null) {
							staticCondition.put(obj.getFieldName()
									+ obj.getSuffix(), value);
						}
					}

				}
			}
			if (orlists.size() > 0)
				lists.add(new ParaUnit("", orlists, ParaUnit.OR));
			// 查询条件字段
			BeanFields[] order = qb.getOrders();
			if (order != null) {
				for (int i = 1; i <= order.length; i++) {
					for (BeanFields obj : order) {
						if (obj.getOrderSeq().equals(String.valueOf(i)))
							lists.add(new ParaUnit(obj.getFieldName(), obj
									.getOrder(), ParaUnit.ORDER));
					}
				}
			}
		}
		// 静态查询条件组织写入页面，根据指定名称
		if (qb.getStaticCondition() != null) {
			if (qb.getGetValueName() != null)
				request.setAttribute(qb.getGetValueName(), staticCondition);
		} else {
			Map map=request.getParameterMap();
			Iterator its = map.entrySet().iterator();
			
			while (its.hasNext()) {
				Entry e = (Entry) its.next();
				String[] values = (String[]) e.getValue();
				String name = (String) e.getKey();
				for (int i = 0; i < values.length; i++) {
					getValue.put(name, values[i]);
				}
			}
			request.setAttribute("getValue", getValue);// 放入页面反写文本框内容
		}
		qb.setParaList(lists);
		return qb;
	}
	/**
	 * 权限验证方法
	 * if(!verifyPowerCommon(request))
	 * 	return toMessage();
	 * @param funcId	//功能点ID
	 * @return			//验证通过返回true，无权限返回false
	 */
	public boolean verifyPowerCommon(HttpServletRequest request)
	{
		
		String VFID = request.getParameter("VFID");
		if(VFID==null)
		{
			request.setAttribute("msgType", "3");
			request.setAttribute("msg", "请为当前功能点指定验证标志，URL方式：&VFID=${wfn:getVerifyKey(pageContext.session.id,\"005001\")}，hidden方式<input type=\"hidden\" name=\"VFID\" value=\"${wfn:getVerifyKey(pageContext.session.id,\"005001\")}\">！");
			return false;
		}
		HashMap verifyMap = (HashMap) request.getSession().getAttribute("verifyMap");
		if(verifyMap.get(VFID)==null)
		{
			request.setAttribute("msgType", "3");
			request.setAttribute("msg", "抱歉，您是无权用户！");
			return false;
		}
		else
			return true;
	}
}
