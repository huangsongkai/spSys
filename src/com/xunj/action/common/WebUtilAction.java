package com.xunj.action.common;


@SuppressWarnings("unchecked")
public class WebUtilAction extends AbstractAction {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7583255327158063634L;

	/**
	 * 获取附件列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getFileList() throws Exception {
		String beanName = request.getParameter("beanName");
		String belongId = request.getParameter("belongId");
		uploadfileList = findFileList(beanName, belongId);
		return toJsp("/jsp/common/uploadFile/lstFileWeb.jsp");
	}

}

