package com.xunj.action.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.xunj.core.CorePo;
import com.xunj.po.UploadFile;
import com.xunj.util.Util;

@SuppressWarnings("unchecked")
public class CommonAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3876189616627978986L;
	private List datalist;
	private CorePo bean;

	public List getDatalist() {
		return datalist;
	}

	public void setDatalist(List datalist) {
		this.datalist = datalist;
	}

	public CorePo getBean() {
		return bean;
	}

	public void setBean(CorePo bean) {
		this.bean = bean;
	}

	/**
	 * 公共查询显示列表分页
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		CommonActionUtil c = new CommonActionUtil();
		// 查询条件json内容
		String[] jsonContent = request.getParameterValues("jsonContent");
		// 返回显示页面的map
		Map getValue = new HashMap();
		// 提供公共方法
		
		// 查询条件对象
		QueryBean qb = null;
		// 表单提交查询存在查询条件内容
		if (jsonContent != null) {
			for (int jsonCnt = 0; jsonCnt < jsonContent.length; jsonCnt++) {
				qb = c.getQueryBean(jsonContent[jsonCnt], request);
				List result = null;
				if (qb.getIsSplitPage() == null)
					result = dao.criteriaByPage(c.getMappingBean(qb
							.getBeanName()), qb.getParaList());
				else if (qb.getIsSplitPage().equals("false"))
					result = dao.criteriaQuery(c.getMappingBean(qb
							.getBeanName()), qb.getParaList());
				// 如果不是静态list查询，默认写入dataList中
				if (qb.getStaticCondition() == null) {
					setDatalist(result);
				} else
					request.setAttribute(qb.getListName(), result);
			}
		} else {
			// 查询bean
			qb = new QueryBean();
			qb.setBeanName(request.getParameter("beanName"));
			// 查询条件list
			List lists = new ArrayList();
			// 放入页面反写文本框内容
			request.setAttribute("getValue", getValue);
			List result = dao.criteriaByPage(
					c.getMappingBean(qb.getBeanName()), lists);
			setDatalist(result);

		}
		return SUCCESS;
	}

	/**
	 * 新增数据，进入添加页面，读取相关信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		CommonActionUtil c = new CommonActionUtil();
		//权限验证
		if(!c.verifyPowerCommon(request))
			 return toMessage();
		
		Map map = request.getParameterMap();
		Iterator its = map.entrySet().iterator();

		while (its.hasNext()) {
			Entry e = (Entry) its.next();
			String[] values = (String[]) e.getValue();
			String name = (String) e.getKey();
			for (int i = 0; i < values.length; i++) {
				request.setAttribute(name, values[i]);
			}
		}
		// 查询条件json内容
		String[] jsonContent = request.getParameterValues("jsonContent");

		// 查询条件对象
		QueryBean qb = null;
		// 表单提交查询存在查询条件内容
		if (jsonContent != null) {
			for (int jsonCnt = 0; jsonCnt < jsonContent.length; jsonCnt++) {
				qb = c.getQueryBean(jsonContent[jsonCnt], request);
				List result = null;
				if (qb.getIsSplitPage() == null)
					result = dao.criteriaByPage(c.getMappingBean(qb
							.getBeanName()), qb.getParaList());
				else if (qb.getIsSplitPage().equals("false"))
					result = dao.criteriaQuery(c.getMappingBean(qb
							.getBeanName()), qb.getParaList());
				// 如果不是静态list查询，默认写入dataList中
				if (qb.getStaticCondition() == null) {
					setDatalist(result);
				} else
					request.setAttribute(qb.getListName(), result);
			}
		}
		return SUCCESS;
	}

	/**
	 * 保存数据
	 * 
	 * @return
	 * @throws Exception
	 */

	public String save() throws Exception {

		CommonActionUtil c = new CommonActionUtil();
		//权限验证
		if(!c.verifyPowerCommon(request))
			 return toMessage();
		
		String beanName = request.getParameter("beanName");
		// 查询条件json内容
		String jsonContent = request.getParameter("jsonContent");
		// 将查询条件转换为BEAN
		JSONObject jb = JSONObject.fromObject(jsonContent);

		CorePo po = (CorePo) JSONObject.toBean(jb, c.getMappingBean(beanName));
		Util.logOut(po, false);
		String id = dao.save(po);
		ArrayList arr = super.upload(beanName, id);
		if (arr != null && arr.size() > 0) {
			dao.saveOrUpdateAll(arr);
		}

		this.Alert_Close("保存成功！", "ok");
		return toMessage();
	}
	/**
	 * 预览数据（公共信息类预览功能使用）
	 * 
	 * @return
	 * @throws Exception
	 */
	
	public String preview() throws Exception {
		
		CommonActionUtil c = new CommonActionUtil();
		//权限验证
		if(!c.verifyPowerCommon(request))
			return toMessage();
		
		String beanName = request.getParameter("beanName");
		// 查询条件json内容
		String jsonContent = request.getParameter("jsonContent");
		// 将查询条件转换为BEAN
		JSONObject jb = JSONObject.fromObject(jsonContent);
		
		bean = (CorePo) JSONObject.toBean(jb, c.getMappingBean(beanName));
		return SUCCESS;
	}

	/**
	 * 保存数据
	 * 
	 * @return
	 * @throws Exception
	 */

	public String saveWithSet() throws Exception {

		CommonActionUtil c = new CommonActionUtil();
		//权限验证
		if(!c.verifyPowerCommon(request))
			 return toMessage();
		String beanName = request.getParameter("beanName");
		// 查询条件json内容
		String jsonContent = request.getParameter("jsonContent");
		String mappingContent = request.getParameter("mappingContent");
		// 将查询条件转换为BEAN
		JSONObject jb = JSONObject.fromObject(jsonContent);
		//主表对象
		CorePo po = (CorePo) JSONObject.toBean(jb, c.getMappingBean(beanName));
		//映射类对应配置信息
		JSONObject jbmapping = JSONObject.fromObject(mappingContent);
		Map classMap = new HashMap();
		classMap.put("mappingBean", BeanFields.class);
		QueryBean qb = (QueryBean) JSONObject.toBean(jbmapping, QueryBean.class,classMap);
		//映射主表对象到从表对象中
		for(int i=0;i<qb.getMappingBean().size();i++)
		{
			BeanFields bf=(BeanFields) qb.getMappingBean().get(i);
			Set childSet = (Set) PropertyUtils.getProperty(po, bf.getSetName());
			Iterator it=childSet.iterator();
			while(it.hasNext())
			{
				CorePo o =(CorePo) it.next();
				PropertyUtils.setProperty(o, bf.getMappingBeanName(), po);
			}
		}
		
		String id = dao.save(po);
		ArrayList arr = super.upload(beanName, id);
		if (arr != null && arr.size() > 0) {
			dao.saveOrUpdateAll(arr);
		}

		this.Alert_Close("保存成功！", "ok");
		return toMessage();
	}
	/**
	 * 删除数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		CommonActionUtil c = new CommonActionUtil();
		//权限验证
		if(!c.verifyPowerCommon(request))
			 return toMessage();
		String beanName = request.getParameter("beanName");
		String idValue = request.getParameter("idValue");
		Class clazz = c.getMappingBean(beanName);
		Object value = c.getBeanIdType(beanName, idValue);
		bean = (CorePo) dao.findOne(clazz, value);
		dao.delete(bean);
		this.Alert_Close("删除成功", "ok");
		return toMessage();
	}
	/**
	 * 批量删除数据,传入主键数据需用","进行拆分
	 * @return
	 * @throws Exception
	 */
	public String deleteAll() throws Exception {

		String beanName=request.getParameter("beanName");
		String beanId=request.getParameter("beanId");
		CommonActionUtil c=new CommonActionUtil();
		String ids=request.getParameter("ids");
		String type = c.getBeanKeyType(beanName);
		String tmpStr="'";
		if(!type.equals("java.lang.String"))
			tmpStr="";
		String[] ls  = ids.split(",");
		String delStr = "delete from "+c.getMappingBean(beanName).getName()+" where "+beanId+" in (";
		for(String idValue:ls){
			if(StringUtils.isNotEmpty(idValue)&&StringUtils.isNotBlank(idValue)){
				delStr+=tmpStr + idValue +tmpStr +",";
			}
		}
		delStr = delStr.substring(0,delStr.length()-1);
		delStr+=")";
		dao.bulkUpdate(delStr);
		this.Alert_Close("删除成功", "ok");
		return toMessage();
	}
	/**
	 * 更新数据，根据表单提交方式判断操作行为。get为读取数据，post为保存所修改的数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception {
		CommonActionUtil c = new CommonActionUtil();
		//权限验证
		if(!c.verifyPowerCommon(request))
			 return toMessage();
		String beanName = request.getParameter("beanName");
		// super.upload();
		if (POST()) {
			// 查询条件json内容
			String jsonContent = request.getParameter("jsonContent");
			// 将查询条件转换为BEAN
			JSONObject jb = JSONObject.fromObject(jsonContent);

			corePo = (CorePo) JSONObject.toBean(jb, c
					.getMappingBean(beanName));
			dao.update(corePo);
			ArrayList arr = null;
			if (corePo != null && StringUtils.isNotEmpty(corePo.getTableId())) {
				arr = super.upload(beanName, corePo.getTableId());
			}
			if (arr != null && arr.size() > 0) {
				dao.saveOrUpdateAll(arr);
			}

			this.Alert_Close("更新成功！", "ok");
			return toMessage();
		} else {
			// 根据制定的id值返回表中数据po对象
			String idValue = request.getParameter("idValue");
			Class clazz = c.getMappingBean(beanName);
			Object value = c.getBeanIdType(beanName, idValue);
			bean = (CorePo) dao.findOne(clazz, value);

			// 取回辅助数据表信息
			// 查询条件json内容
			String[] jsonContent = request.getParameterValues("jsonContent");

			// 查询条件对象
			QueryBean qb = null;
			// 表单提交查询存在查询条件内容
			if (jsonContent != null) {
				for (int jsonCnt = 0; jsonCnt < jsonContent.length; jsonCnt++) {
					qb = c.getQueryBean(jsonContent[jsonCnt], request);
					List result = null;
					if (qb.getIsSplitPage() == null)
						result = dao.criteriaByPage(c.getMappingBean(qb
								.getBeanName()), qb.getParaList());
					else if (qb.getIsSplitPage().equals("false"))
						result = dao.criteriaQuery(c.getMappingBean(qb
								.getBeanName()), qb.getParaList());
					// 如果不是静态list查询，默认写入dataList中
					if (qb.getStaticCondition() == null) {
						setDatalist(result);
					} else
						request.setAttribute(qb.getListName(), result);
				}
			}
			return SUCCESS;
		}
	}

	/**
	 * 数据详细信息查看，根据传入数据的ID进行查询
	 * 
	 * @return
	 * @throws Exception
	 */
	public String detail() throws Exception {
		CommonActionUtil c = new CommonActionUtil();
		//权限验证
		if(!c.verifyPowerCommon(request))
			 return toMessage();
		String beanName = request.getParameter("beanName");

		// 根据制定的id值返回表中数据po对象
		String idValue = request.getParameter("idValue");
		Class clazz = c.getMappingBean(beanName);
		Object value = c.getBeanIdType(beanName, idValue);
		bean = (CorePo) dao.findOne(clazz, value);

		// 取回辅助数据表信息
		// 查询条件json内容
		String[] jsonContent = request.getParameterValues("jsonContent");

		// 查询条件对象
		QueryBean qb = null;
		// 表单提交查询存在查询条件内容
		if (jsonContent != null) {
			for (int jsonCnt = 0; jsonCnt < jsonContent.length; jsonCnt++) {
				qb = c.getQueryBean(jsonContent[jsonCnt], request);
				List result = null;
				if (qb.getIsSplitPage() == null)
					result = dao.criteriaByPage(c.getMappingBean(qb
							.getBeanName()), qb.getParaList());
				else if (qb.getIsSplitPage().equals("false"))
					result = dao.criteriaQuery(c.getMappingBean(qb
							.getBeanName()), qb.getParaList());
				// 如果不是静态list查询，默认写入dataList中
				if (qb.getStaticCondition() == null) {
					setDatalist(result);
				} else
					request.setAttribute(qb.getListName(), result);
			}
		}

		return SUCCESS;

	}

	/**
	 * 获取附件列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getFileList() throws Exception {
		String beanName = request.getParameter("beanName");
		String belongId = request.getParameter("belongId");
		String allowDeletions = request.getParameter("allowDeletions");
		String deleteFile = request.getParameter("deleteFile");
		request.setAttribute("allowDeletions", allowDeletions);
		request.setAttribute("deleteFile", deleteFile);
		uploadfileList = findFileList(beanName, belongId);
		return SUCCESS;
	}

	/**
	 * 删除附件
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteFile() throws Exception {
		String fileId = request.getParameter("fileId");
		String putPath = request.getParameter("putPath");
		String deleteFile = request.getParameter("deleteFile");
		HashMap<String, String> config = (HashMap<String, String>) servletContext
				.getAttribute("sysConfig");
		// 文件保存形式，网络或本地native、network
		String uploadLocation = config.get("uploadFileSavePath");

		UploadFile vo = new UploadFile();
		vo.setFileId(fileId);
		dao.delete(vo);
		if(deleteFile.equals("true"))
		{
			FileUtil fileUtil = new FileUtil(servletContext);
			if (uploadLocation.equals("native")) {
				fileUtil.deleteNativeFile(putPath);
			} else {
				fileUtil.deleteFtpFile(putPath);
			}
		}
		// this.Alert_Close("删除附件成功", "ok");
		return toResult();
	}
	public String getUploadProgress() throws Exception{
//		ResourceFileUploadStatus status = (ResourceFileUploadStatus) request.getSession().getAttribute("currentUploadStatus");
//		double  readedBytes =0;//已传
//		double  totalBytes =0; //总共
//		readedBytes = status!=null?Double.parseDouble(status.getReadedBytes()+""):0;
//		totalBytes = status!=null?Double.parseDouble(status.getTotalBytes()+""):0;
//		double num=0;
//		num = readedBytes/totalBytes*100;
//		request.setAttribute("data", num);
//		System.out.println("-----readedBytes:"+readedBytes+"-----totalBytes:"+totalBytes+"--------readedBytes/totalBytes:"+readedBytes/totalBytes+"--------:"+num);
		System.out.println("---------:"+ request.getSession().getAttribute("num"));
		request.setAttribute("data", request.getSession().getAttribute("num"));
		return this.toResult();
	}
	
	public String report() throws Exception {

		// 查询条件json内容
		String[] jsonContent = request.getParameterValues("jsonContent");
		String reportName = request.getParameter("reportName");
		// 返回显示页面的map
		Map parameter = new HashMap();
		Map map = request.getParameterMap();
		Iterator its = map.entrySet().iterator();

		while (its.hasNext()) {
			Entry e = (Entry) its.next();
			String[] values = (String[]) e.getValue();
			String name = (String) e.getKey();
			for (int i = 0; i < values.length; i++) {
				parameter.put(name, values[i]);
			}
		}
		request.setAttribute("reportName", reportName);
		request.setAttribute("parameter", parameter);
		// 提供公共方法
		CommonActionUtil c = new CommonActionUtil();
		// 查询条件对象
		QueryBean qb = null;
		// 表单提交查询存在查询条件内容
		if (jsonContent != null) {
			for (int jsonCnt = 0; jsonCnt < jsonContent.length; jsonCnt++) {
				qb = c.getQueryBean(jsonContent[jsonCnt], request);
				List result = null;
				if (qb.getIsSplitPage() == null)
					result = dao.criteriaByPage(c.getMappingBean(qb
							.getBeanName()), qb.getParaList());
				else if (qb.getIsSplitPage().equals("false"))
					result = dao.criteriaQuery(c.getMappingBean(qb
							.getBeanName()), qb.getParaList());
				// 如果不是静态list查询，默认写入dataList中
				request.setAttribute("dataList", result);
			}
		} else {
			// 查询bean
			qb = new QueryBean();
			qb.setBeanName(request.getParameter("beanName"));
			// 查询条件list
			List lists = new ArrayList();
			//放入页面反写文本框内容
			List result = dao.criteriaByPage(
					c.getMappingBean(qb.getBeanName()), lists);
			request.setAttribute("dataList", result);
		}
		return toJsp("/servlets/pdf");
	}
}

