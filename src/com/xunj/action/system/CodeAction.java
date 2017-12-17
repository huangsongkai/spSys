package com.xunj.action.system;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.record.formula.functions.T;

import com.xunj.action.common.AbstractAction;
import com.xunj.core.ParaUnit;
import com.xunj.po.CodeDict;
import com.xunj.po.CodeKind;
import com.xunj.service.system.CodeService;
import com.xunj.util.StateConst;

/**
 * 数据字典管理
 *
 */
public class CodeAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4654574303236951447L;
	private CodeDict codeDict;
	private CodeKind codeKind;
	/**
	 * 查询字典listCodeKind()
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String listCodeKind() throws Exception{
		ArrayList<ParaUnit> paralist = new ArrayList<ParaUnit>();
		if(POST()){
			if(codeKind.getKindId()!=null && !"".equals(codeKind.getKindId()) ){
				paralist.add(new ParaUnit("kindId", codeKind.getKindId(),ParaUnit.LIKE));
			}
			if(codeKind.getKindName()!=null && !"".equals(codeKind.getKindName()) ){
				paralist.add(new ParaUnit("kindName", codeKind.getKindName(),ParaUnit.LIKE));
			}	
		}
		List list = dao.criteriaByPage(CodeKind.class, paralist);	
		request.setAttribute("kindList", list);
		return toJsp("/jsp/system/code/list_codeKind.jsp");	
	}	
	/**
	 * 删除字典明细信息
	 * @return
	 * @throws Exception
	 */
	public String deleteCodeKind() throws Exception {
		String kindId=request.getParameter("kindId");
		dao.bulkUpdate("delete from CodeDict where kindId='"+kindId+"'");	
		dao.bulkUpdate("delete from CodeKind where kindId='"+kindId+"'");	
		this.Alert_GoUrl("删除成功!", "","数据字典管理","");
		return toResult();
	}
	
	
	/**
	 * 添加字典大类信息
	 * @return
	 * @throws Exception
	 */
	public String addCodeKind() throws Exception{
		CodeService cdao = new CodeService(dao);
		String newId = cdao.getNewKindId();
		CodeKind codeKind=new CodeKind();
		codeKind.setKindId(newId);
		//删除状态
		codeKind.setState(StateConst.STATE_USE);
		request.setAttribute("codeKind", codeKind);
		return toJsp("/jsp/system/code/add_codeKind.jsp");
	}
	
	
	public String saveCodeKind() throws Exception{
		dao.saveOrUpdate(codeKind);
		Alert_GoUrl("保存成功！", "closeCurrent","数据字典管理","");
		return toResult();
	}
	
	/**
	 * 修改字典大类信息
	 * @return
	 * @throws Exception
	 */
	public String updateCodeKind() throws Exception{
		String kindId = request.getParameter("kindId");
		request.setAttribute("kindId", kindId);
		codeKind = (CodeKind)dao.findOne(CodeKind.class, kindId);
		return toJsp("/jsp/system/code/update_codeKind.jsp");
	}
	

	/**
	 * 查询字典明细信息
	 * @return
	 * @throws Exception
	 */
	public String listCodeDict() throws Exception{
		ArrayList<ParaUnit> paralist = new ArrayList<ParaUnit>();
		String kindId = request.getParameter("kindId");
			
		if(kindId!=null && !"".equals(kindId)){
			paralist.add(new ParaUnit("kindId", kindId,ParaUnit.EQ));
		}

		paralist.add(new ParaUnit("state", StateConst.STATE_USE,ParaUnit.EQ));
		paralist.add(new ParaUnit("codeId", ParaUnit.ASC,ParaUnit.ORDER));
		
		List list = dao.criteriaQuery(CodeDict.class, paralist);

		request.setAttribute("codeDictList", list);
		request.setAttribute("kindId", kindId);
		return toJsp("/jsp/system/code/list_codeDict.jsp");
	}  
	
	
	public String saveCodeDict() throws Exception{
		String kindId = request.getParameter("codeDict.parentCodeId");
		String[] codeIds = request.getParameterValues("codeDict.codeId");
		String[] codeCodes = request.getParameterValues("codeDict.codeCode");
		String[] codeNames = request.getParameterValues("codeDict.codeName");
		dao.bulkUpdate("update CodeDict set state='P' where parentCodeId='"+kindId+"'");
		CodeService cdao = new CodeService(dao);
		String oldId="";
		for(int i=0;i<codeIds.length;i++){
			if(StringUtils.isEmpty(codeIds[i])){
				String newId = cdao.getNewCodeId(kindId);
				CodeDict temp=new CodeDict(); 
				temp.setCodeId(newId);
				temp.setParentCodeId(kindId);
				temp.setKindId(kindId);
				temp.setCodeCode(codeCodes[i]);
				temp.setCodeName(codeNames[i]);
				temp.setState(StateConst.STATE_USE);
				CodeDict instance = (CodeDict) dao.getHtemplate().get(CodeDict.class, oldId);
				dao.getHtemplate().evict(instance);
				dao.save(temp);
			}else{
				CodeDict temp=(CodeDict) dao.findOne(CodeDict.class, codeIds[i]);
				temp.setCodeCode(codeCodes[i]);
				temp.setCodeName(codeNames[i]);
				temp.setState(StateConst.STATE_USE);
				dao.update(temp);
				oldId=temp.getCodeId();
			}
		}
		
		Alert_GoUrl("保存成功！", "closeCurrent","数据字典管理","");
		return toResult();
	}
	/************************以下为旧代码不适用*************************************/
	/**
	 * 查询字典listTree()
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String listTree() throws Exception{
		ArrayList<ParaUnit> paralist = new ArrayList<ParaUnit>();
//		List list = dao.criteriaQuery(CodeKind.class, paralist);
		String levelTal = "2";	
		List kindList=dao.findAll("select t.kindId as codeId,t.kindName as  codeName ,'0' as parentCodeId from CodeKind t ");
		List dictList=dao.findAll("select d.codeId,d.codeName,d.parentCodeId from CodeDict d");
		dictList.addAll(kindList);
		List list=new ArrayList();
		for(int i=0;i<dictList.size();i++){
			Object[] obj = (Object[]) dictList.get(i);
			CodeDict dict=new CodeDict();
			dict.setCodeId((String) obj[0]);
			dict.setCodeName((String) obj[1]);
			dict.setParentCodeId((String) obj[2]);
			list.add(dict);
		}
		request.setAttribute("dataList", list); 		
		request.setAttribute("kindId", "0");
		request.setAttribute("levelTal", levelTal);
		request.setAttribute("kindName", "数据字典管理");
		return toJsp("/jsp/system/code/list_codeTree.jsp");
	}

	
	
	
	

	/**
	 * 查看字典大类信息
	 * @return
	 * @throws Exception
	 */
	public String showKind() throws Exception{
		String kindId = request.getParameter("kindId");
		request.setAttribute("kindId", kindId);
		codeKind = (CodeKind)dao.findOne(CodeKind.class, kindId);
		return toJsp("/jsp/system/code/show_codeKind.jsp");
	}
	/**
	 * 添加字典子类信息
	 * @return
	 * @throws Exception
	 */
	public String insDict() throws Exception{
		CodeService cdao = new CodeService(dao);
		if (POST()) {
			String newId = cdao.getNewCodeId(codeDict.getParentCodeId());
			codeDict.setCodeId(newId);
			codeDict.setKindId(codeDict.getParentCodeId());
			//删除状态
			codeDict.setState(StateConst.STATE_USE);
			//字典码子类是否可修改,N,可修改，Y不可修改
			codeDict.setIsDefult(StateConst.CODE_STATE_NOTDEFAULT);
			dao.save(codeDict);
			Alert_GoUrl("保存成功！", "list_CodeList.do","frame_right","frame_left");
			return toMessage();
			
		} else {
			String pid = request.getParameter("pid");
		//	String codeName = request.getParameter("codeName");
			
			String kindId = request.getParameter("kindId");
			String newId = cdao.getNewCodeId(pid);
			String no = newId.substring(4, 6);
			CodeKind sysCodeKind=(CodeKind) dao.findOne(CodeKind.class, pid);
			String codeName = sysCodeKind.getKindName();
			codeDict = new CodeDict();
			codeDict.setCodeId(newId);
			codeDict.setCodeCode(no);
			request.setAttribute("pid",pid);
			request.setAttribute("codeName",codeName);
			request.setAttribute("kindId",kindId);
			return toJsp("/jsp/system/code/ins_codeDict.jsp");
		}
	}
	/**
	 * 修改字典明细信息
	 * @return
	 * @throws Exception
	 */
	public String updDict() throws Exception{
		String codeId = request.getParameter("codeId");
		request.setAttribute("codeId", codeId);
		if (POST()) {
			dao.update(codeDict);
			Alert_GoUrl("保存成功！", "list_CodeList.do?codeId="+codeDict.getCodeId(),"frame_right","frame_left");
			return toMessage();
			
		} else {
			codeDict = (CodeDict)dao.findOne(CodeDict.class, codeId);
			return toJsp("/jsp/system/code/upd_codeDict.jsp");
		}
	}
	/**
	 * 查看字典明细信息
	 * @return
	 * @throws Exception
	 */
	public String showDict() throws Exception{
		String codeId = request.getParameter("codeId");
		request.setAttribute("codeId", codeId);
		codeDict = (CodeDict)dao.findOne(CodeDict.class, codeId);
		
		return toJsp("/jsp/system/code/show_codeDict.jsp");
	}


	/**
	 * 更新application中的字典码缓存
	 * @return
	 * @throws Exception
	 */
	public String updApplicationDict() throws Exception {
		CodeDict po = new CodeDict();
		po.setState(StateConst.STATE_USE);
		this.getServletContext().setAttribute("CodeDict", dao.findByExample(po));
		request.setAttribute("data", "ok");
		return toResult();
	}
	
	public CodeDict getCodeDict() {
		return codeDict;
	}

	public void setCodeDict(CodeDict codeDict) {
		this.codeDict = codeDict;
	}

	public CodeKind getCodeKind() {
		return codeKind;
	}

	public void setCodeKind(CodeKind codeKind) {
		this.codeKind = codeKind;
	}
	
}