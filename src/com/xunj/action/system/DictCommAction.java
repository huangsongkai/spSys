package com.xunj.action.system;


import java.util.ArrayList;
import java.util.List;

import com.xunj.action.common.AbstractAction;
import com.xunj.core.ParaUnit;
import com.xunj.po.CodeDict;
import com.xunj.po.CodeKind;
import com.xunj.service.system.CodeService;
import com.xunj.util.StateConst;

/**
 * 字典码通用引用功能
 * @author 王瑾
 *
 */
public class DictCommAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4654574303236951447L;
	private CodeDict codeDict;
	private CodeKind codeKind;

	
	/**
	 * 查询字典明细信息
	 * @return
	 * @throws Exception
	 */
	public String listDictComm() throws Exception{
		ArrayList<ParaUnit> paralist = new ArrayList<ParaUnit>();
		String kindId = request.getParameter("kindId");
		codeKind = (CodeKind) dao.findOne(CodeKind.class, kindId);
		String toJsp = request.getParameter("toJsp");
		
			
		if(kindId!=null && !"".equals(kindId)){
			paralist.add(new ParaUnit("kindId", kindId,ParaUnit.EQ));
		}

		paralist.add(new ParaUnit("state", StateConst.STATE_USE,ParaUnit.EQ));
		paralist.add(new ParaUnit("codeId", ParaUnit.ASC,ParaUnit.ORDER));
		
		List list = dao.criteriaQuery(CodeDict.class, paralist);

		request.setAttribute("datalist", list);
		request.setAttribute("kindId", kindId);
		return toJsp(toJsp);
	}

	/**
	 * 添加字典子类信息
	 * @return
	 * @throws Exception
	 */
	public String insDictComm() throws Exception{
		CodeService cdao = new CodeService(dao);
		if (POST()) {
			String kindName = request.getParameter("kindName");
			String newId = cdao.getNewCodeId(codeDict.getParentCodeId());
			codeDict.setCodeId(newId);
			//删除状态
			codeDict.setState(StateConst.STATE_USE);
			//字典码子类是否可修改,N,可修改，Y不可修改
			codeDict.setIsDefult(StateConst.CODE_STATE_NOTDEFAULT);
			dao.save(codeDict);
			Alert_GoUrl("保存成功！", "updDictComm.do?codeId="+newId+"&kindName="+kindName,"frame_right","frame_left");
			return toMessage();
			
		} else {
			String pid = request.getParameter("pid");
			String kindId = request.getParameter("kindId");
			String newId = cdao.getNewCodeId(pid);
			codeDict = new CodeDict();
			codeDict.setCodeId(newId);
			request.setAttribute("pid",pid);
			request.setAttribute("kindId",kindId);
			return toJsp("/jsp/system/dict_comm/ins_codeDict.jsp");
		}
	}
	/**
	 * 修改字典明细信息
	 * @return
	 * @throws Exception
	 */
	public String updDictComm() throws Exception{
		String codeId = request.getParameter("codeId");
		String kindName = request.getParameter("kindName");
		
		request.setAttribute("codeId", codeId);
		if (POST()) {
			dao.update(codeDict);
			Alert_GoUrl("保存成功！", "updDictComm.do?codeId="+codeDict.getCodeId()+"&kindName="+kindName,"frame_right","frame_left");
			return toMessage();
			
		} else {
			codeDict = (CodeDict)dao.findOne(CodeDict.class, codeId);
			return toJsp("/jsp/system/dict_comm/upd_codeDict.jsp");
		}
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