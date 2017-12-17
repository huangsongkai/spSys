
package com.xunj.report;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;


/**
 * html报表导出
 * wangjin
 */
public class HtmlServlet extends HttpServlet
{


	/**
	 *
	 */
	public void service(
		HttpServletRequest request,
		HttpServletResponse response
		) throws IOException, ServletException
	{
		ServletContext context = this.getServletConfig().getServletContext();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		try
		{
			String htmlHeader = "";
			
			htmlHeader+="<html>\n";
			htmlHeader+="<head>\n";
			htmlHeader+="  <title></title>\n";
			htmlHeader+="  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\n";
			htmlHeader+="  <style type=\"text/css\">\n";
			htmlHeader+="    a {text-decoration: none}\n";
			htmlHeader+="    div {font-size: 12px;}\n";
			htmlHeader+="  </style>\n";
			htmlHeader+="</head>\n";
			htmlHeader+="<body text=\"#000000\" link=\"#000000\" alink=\"#000000\" vlink=\"#000000\">\n";
			
			String ctxPath = getServletContext().getContextPath();
			String urlParam = (String) request.getAttribute("urlParam");
			
			htmlHeader+="<div align=\"center\">\n<img src=\""+ctxPath+"/images/ico/ico.pdf.png\">\n";
			htmlHeader+="<a href=\""+urlParam+"&out=pdf\" target=\"_blank\">PDF输出打印</a>\n";
			
			htmlHeader+="<img src=\""+ctxPath+"/images/ico/ico.doc.png\">\n";
			htmlHeader+="<a href=\""+urlParam+"&out=rtf\" target=\"_blank\">RTF输出打印</a>\n";
			
			htmlHeader+="<img src=\""+ctxPath+"/images/ico/ico.xls.png\">\n";
			htmlHeader+="<a href=\""+urlParam+"&out=execl\" target=\"_blank\">EXCEL输出打印</a>\n";
			htmlHeader+="</div>\n";
			
			htmlHeader+="<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n";
			htmlHeader+="<tr><td width=\"50%\">&nbsp;</td><td align=\"center\">\n";
			htmlHeader+="\n";
			
			
			String reportFileName = context
			.getRealPath("/reports/"+request.getAttribute("reportName")+".jasper");
			List dataList = (List) request.getAttribute("dataList");
			File reportFile = new File(reportFileName);
			if (!reportFile.exists())
				throw new JRRuntimeException(
						"指定报表文件不存在。");
			JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
		
			Map parameters = (Map) request.getAttribute("parameter");
			JasperPrint jasperPrint = 
				JasperFillManager.fillReport(
					jasperReport, 
					parameters, 
					new ReportDataSource(dataList)
					);
						
			JRHtmlExporter exporter = new JRHtmlExporter();
		
			request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
			
			exporter.setParameter(JRHtmlExporterParameter.HTML_HEADER, htmlHeader);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
			exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, ctxPath+"/servlets/image?image=");
			
			exporter.exportReport();
			
		}
		catch (JRException e)
		{
			out.println("<html>");
			out.println("<head>");
			out.println("<title>JasperReports - Web Application Sample</title>");
			out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"../stylesheet.css\" title=\"Style\">");
			out.println("</head>");
			
			out.println("<body bgcolor=\"white\">");

			out.println("<span class=\"bnew\">JasperReports encountered this error :</span>");
			out.println("<pre>");

			e.printStackTrace(out);

			out.println("</pre>");

			out.println("</body>");
			out.println("</html>");
		}
	}


}
