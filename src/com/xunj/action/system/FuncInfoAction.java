package com.xunj.action.system;


import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.xunj.action.common.AbstractAction;
import com.xunj.action.common.BusinessException;
import com.xunj.core.ParaUnit;
import com.xunj.po.SysFuncInfo;
import com.xunj.service.system.FuncInfoService;
import com.xunj.util.StateConst;

/**
 * 菜单管理
 * @author 王瑾
 *
 */
public class FuncInfoAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4654574303236951447L;
	private SysFuncInfo funcInfo;

	/**
	 * 显示菜单列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String listFuncInfo() throws Exception {
		
		
		ArrayList<ParaUnit> paralist = new ArrayList<ParaUnit>();
		
		paralist.add(new ParaUnit("state", "A", ParaUnit.EQ));
		paralist.add(new ParaUnit("orderCol", ParaUnit.ASC,ParaUnit.ORDER));
		paralist.add(new ParaUnit("funcId", ParaUnit.ASC,ParaUnit.ORDER));
		
		List list = dao.criteriaQuery(SysFuncInfo.class, paralist);

		request.setAttribute("datalist", list);
		return toJsp("/jsp/system/funcInfo/list_funcInfo.jsp");
	}

	public String saveFuncInfo()throws Exception{
		funcInfo.setState(StateConst.STATE_USE);
		dao.save(funcInfo);
		Alert_GoUrl("保存成功！", "","","");
		return toResult();
	}
	/**
	 * 添加菜单信息
	 * @return
	 * @throws Exception
	 */
	public String addFuncInfo() throws Exception{

		FuncInfoService fdao = new FuncInfoService(dao);
		String parentId = request.getParameter("parentId");
		String newId = fdao.getNewFuncInfoId(parentId);
		request.setAttribute("newId",newId);
		request.setAttribute("parentId",parentId);
		return toJsp("/jsp/system/funcInfo/add_funcInfo.jsp");
	}
	/**
	 * 修改菜单信息
	 * @return
	 * @throws Exception
	 */
	public String updateFuncInfo()  throws BusinessException{
			String funcId = request.getParameter("funcId");
			funcInfo = (SysFuncInfo) dao.findOne(SysFuncInfo.class, funcId);
			return toJsp("/jsp/system/funcInfo/upd_funcInfo.jsp");
	}
	public String saveUpdateFuncInfo() throws Exception{
		String funcId=request.getParameter("funcInfo.funcId");
		String funcParentId=request.getParameter("funcInfo.funcParentId");
		funcInfo.setFuncId(funcId);
		funcInfo.setFuncParentId(funcParentId);
		funcInfo.setState("A");
		dao.update(funcInfo);
		Alert_GoUrl("修改成功！","","菜单管理",null);
		return toResult();
		
		
	}


	public String delFuncInfo() throws Exception {
		
		String funcId=request.getParameter("funcInfo.funcId");
		funcInfo=(SysFuncInfo) dao.findOne(SysFuncInfo.class, funcId);
		funcInfo.setState(StateConst.STATE_DELETE);
		dao.update(funcInfo);
		this.Alert_GoUrl("删除成功!", "","菜单管理","");
		return toResult();
	}
	/**
	 * 选择功能点图标
	 * @return
	 * @throws Exception
	 */
	public String lstIcoFuncInfo() throws Exception {
		String filePath = "images/top_ico";
		String path=this.getServletContext().getRealPath("/"+filePath);
		File folder = new File(path);
		if(folder.exists())
		{
			File[] icos = folder.listFiles();
			List s = new ArrayList();
			for(int i=0;i<icos.length;i++)
			{
				s.add(icos[i].getName());
			}
			request.setAttribute("folder", filePath+"/");
			request.setAttribute("icos", s);
		}
		return toJsp("/jsp/system/funcInfo/lst_icos.jsp");
	}
	
	public SysFuncInfo getFuncInfo() {
		return funcInfo;
	}
	public void setFuncInfo(SysFuncInfo funcInfo) {
		this.funcInfo = funcInfo;
	}
	
	//导出excel
	public String listFuncInfoExportExcel() throws Exception{
		ArrayList<ParaUnit> paralist = new ArrayList<ParaUnit>();
		
		paralist.add(new ParaUnit("state", "A", ParaUnit.EQ));
		paralist.add(new ParaUnit("orderCol", ParaUnit.ASC,ParaUnit.ORDER));
		paralist.add(new ParaUnit("funcId", ParaUnit.ASC,ParaUnit.ORDER));
		
		List<SysFuncInfo> list = dao.criteriaAll(SysFuncInfo.class, paralist);
		
		HSSFWorkbook hssfworkbook = new HSSFWorkbook();
		HSSFSheet hssfsheet = hssfworkbook.createSheet();
		HSSFRow row = null;
		HSSFCell cell = null;
		row = hssfsheet.createRow(0);
//		cell= row.createCell(3);
		HSSFFont titleFont = hssfworkbook.createFont();  //设置字体
        titleFont.setFontName("黑体"); 
        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        titleFont.setFontHeightInPoints((short) 20);  
        HSSFCellStyle titleStyle = hssfworkbook.createCellStyle(); 
        titleStyle.setFont(titleFont);  
//		row=hssfsheet.createRow(1);
		cell=row.createCell(0);
		cell.setCellValue("主功能");
		hssfsheet.setColumnWidth(0, 8000);
		cell=row.createCell(1);
		cell.setCellValue("子功能");
		hssfsheet.setColumnWidth(1, 8000);
		int rowNum=1;
		for(int i=0;i<list.size();i++){
			SysFuncInfo parentFuncInfo=(SysFuncInfo) list.get(i);
			
			if (parentFuncInfo.getFuncParentId().trim().length()==3) {
				row=hssfsheet.createRow(rowNum);
				rowNum++;
				cell=row.createCell(0);
				cell.setCellValue(parentFuncInfo.getFuncName());
				String parentId = parentFuncInfo.getFuncId().trim();
				for(int j=0;j<list.size();j++){
					SysFuncInfo childFuncInfo=(SysFuncInfo) list.get(j);
					
					if(childFuncInfo.getFuncParentId().trim().equals(parentId)){
						row=hssfsheet.createRow(rowNum);
						rowNum++;
						cell=row.createCell(1);
						cell.setCellValue(childFuncInfo.getFuncName());
					}
				}
			}			
		}
		String x = new String(("功能菜单信息").getBytes("GBK"), "ISO8859-1");
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

}