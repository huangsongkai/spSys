package com.xunj.tag;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class TagUtil
{
  public static String initSplitPage(ServletContext ctx, int rowCount, HttpServletRequest request, int rows)
  {
    String submitType = request.getParameter("submitType");
    
    String pn = request.getParameter("pageNum");
	String pageSizeStr = request.getParameter("numPerPage");
	if(StringUtils.isEmpty(pn)){
		pn="1";
	}
	if(StringUtils.isEmpty(pageSizeStr)){
		pageSizeStr="20";
	}
	request.setAttribute("pageNum",pn);
	request.setAttribute("numPerPage",pageSizeStr);
    int pageSize =	Integer.valueOf(pageSizeStr);
//    	Integer.valueOf(request.getParameter("numPerPage"));
//    
    String pageSizeOption = getPageSizeOption(ctx);
    
    if (rows == -1)
    {
      if (pageSizeStr != null)
        pageSize = Integer.parseInt(pageSizeStr);
    }
    else {
      pageSize = rows;
    }
    int pageNumber = 1;
    if (pn != null)
      pageNumber = Integer.parseInt(pn);
    if ((submitType != null) && (submitType.equals("search")))
      pageNumber = 1;
    if ((pageNumber * pageSize - rowCount >= pageSize) && (rowCount != 0)) {
      --pageNumber;
    }
    int pageCount = 0;

    if (rowCount % pageSize == 0)
      pageCount = rowCount / pageSize;
    else {
      pageCount = rowCount / pageSize + 1;
    }
    TagBase tagbase = new TagBase();
    tagbase.setPageSize(pageSize);
    tagbase.setRowCount(rowCount);
    tagbase.setPageNumber(pageNumber);
    tagbase.setPageCount(pageCount);
    tagbase.setPageSizeOption(pageSizeOption);
    request.setAttribute("tagbase", tagbase);

    int skipRow = 0;
    if (pageNumber - 1 > 0)
      skipRow = pageSize * (pageNumber - 1);
    if ((submitType != null) && (submitType.equals("search")))
      skipRow = 0;
    return skipRow + "," + pageSize;
  }

  public static String initSplitPage4Flex(int rowCount, int pageNumber, int pageSize) {
    if ((pageNumber * pageSize - rowCount >= pageSize) && (rowCount != 0)) {
      --pageNumber;
    }
    int pageCount = 0;

    if (rowCount % pageSize == 0)
      pageCount = rowCount / pageSize;
    else {
      pageCount = rowCount / pageSize + 1;
    }
    TagBase tagbase = new TagBase();
    tagbase.setPageSize(pageSize);
    tagbase.setRowCount(rowCount);
    tagbase.setPageNumber(pageNumber);
    tagbase.setPageCount(pageCount);

    int skipRow = 0;
    if (pageNumber - 1 > 0) {
      skipRow = pageSize * (pageNumber - 1);
    }
    return skipRow + "," + pageSize + "," + pageCount;
  }

  public static int getPageSize(ServletContext ctx)
  {
    HashMap config = (HashMap)ctx.getAttribute("sysConfig");
    return Integer.parseInt((String)config.get("EachPageSize"));
  }
  public static String getPageSizeOption(ServletContext ctx)
  {
    HashMap config = (HashMap)ctx.getAttribute("sysConfig");
    return (String)config.get("pageSizeOption");
  }
}