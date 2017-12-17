/*
 * ============================================================================
 * GNU Lesser General Public License
 * ============================================================================
 *
 * JasperReports - Free Java report-generating library.
 * Copyright (C) 2001-2006 JasperSoft Corporation http://www.jaspersoft.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307, USA.
 * 
 * JasperSoft Corporation
 * 303 Second Street, Suite 450 North
 * San Francisco, CA 94107
 * http://www.jaspersoft.com
 */
package com.xunj.report;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.FileBufferedOutputStream;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: PdfServlet.java 1989 2007-12-04 16:19:11Z teodord $
 */
public class PdfServlet extends BaseHttpServlet {
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	/**
	 * 
	 */
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		ServletContext context = this.getServletConfig().getServletContext();
		String reportFileName = context
				.getRealPath("/reports/"+request.getAttribute("reportName")+".jasper");
		List dataList = (List) request.getAttribute("dataList");
		File reportFile = new File(reportFileName);
		if (!reportFile.exists())
			throw new JRRuntimeException(
					"ָ�������ļ������ڡ�");

		Map parameters = (Map) request.getAttribute("parameter");

		JasperPrint jasperPrint = null;
		try {
			jasperPrint = JasperFillManager.fillReport(reportFileName,
					parameters, new ReportDataSource(dataList));
		} catch (JRException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		Boolean isBuffered = Boolean
				.valueOf(request
						.getParameter(BaseHttpServlet.BUFFERED_OUTPUT_REQUEST_PARAMETER));
		if (isBuffered.booleanValue()) {
			FileBufferedOutputStream fbos = new FileBufferedOutputStream();
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,
					getJasperPrintList(jasperPrint));
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, fbos);

			try {
				exporter.exportReport();
				fbos.close();

				if (fbos.size() > 0) {
					response.setContentType("application/pdf");
					response.setContentLength(fbos.size());
					ServletOutputStream ouputStream = response
							.getOutputStream();

					try {
						fbos.writeData(ouputStream);
						fbos.dispose();
						ouputStream.flush();
					} finally {
						if (ouputStream != null) {
							try {
								ouputStream.close();
							} catch (IOException ex) {
							}
						}
					}
				}
			} catch (JRException e) {
				throw new ServletException(e);
			} finally {
				fbos.close();
				fbos.dispose();
			}

			// else
			// {
			// response.setContentType("text/html");
			// PrintWriter out = response.getWriter();
			// out.println("<html>");
			// out.println("<body bgcolor=\"white\">");
			// out.println("<span class=\"bold\">Empty response.</span>");
			// out.println("</body>");
			// out.println("</html>");
			// }
		} else {
			response.setContentType("application/pdf");

			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,
					getJasperPrintList(jasperPrint));

			OutputStream ouputStream = response.getOutputStream();
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					ouputStream);

			try {
				exporter.exportReport();
			} catch (JRException e) {
				throw new ServletException(e);
			} finally {
				if (ouputStream != null) {
					try {
						ouputStream.close();
					} catch (IOException ex) {
					}
				}
			}
		}
	}
	private List getJasperPrintList(JasperPrint jasperPrint)
	{

		List jasperPrintList = new ArrayList();
		jasperPrintList.add(jasperPrint);
	
		return jasperPrintList;
	}
}