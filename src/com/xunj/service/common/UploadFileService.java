package com.xunj.service.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import com.xunj.core.CoreDao;
import com.xunj.core.ParaUnit;
import com.xunj.po.UploadFile;
import com.xunj.util.Util;

public class UploadFileService {

	private CoreDao dao;

	public UploadFileService(CoreDao dao) {
		this.dao = dao;
	}

	/**
	 * 附件复制
	 * 
	 * @return
	 * @throws Exception
	 */
	public int copyUploadFile(String belongTable, String belongId,
			String newBelongTable, String newBelongId) throws Exception {
		int fileCount = 0;
		// 查询原附件记录
		ArrayList<ParaUnit> paralist = new ArrayList<ParaUnit>();
		paralist.add(new ParaUnit("belongTable", belongTable, ParaUnit.EQ));
		paralist.add(new ParaUnit("belongId", belongId, ParaUnit.EQ));
		List list = dao.criteriaByPage(UploadFile.class, paralist);
		List newList = new ArrayList();
		if (!list.isEmpty()) {
			fileCount = list.size();
			Util u = new Util();
			ServletContext ctx = ServletActionContext.getServletContext();
			for (int i = 0; i < list.size(); i++) {
				UploadFile uploadFile = (UploadFile) list.get(i);
				UploadFile newUploadFile = new UploadFile();
				u.copySameProperties(uploadFile, newUploadFile);
				String filePath = ctx.getRealPath(uploadFile.getPutPath());
				String uid = UUID.randomUUID().toString();
				
				String newPutPath = uploadFile.getPutPath().substring(0,
						uploadFile.getPutPath().lastIndexOf("/"))
						+ uid
						+ uploadFile.getPutPath().substring(
								uploadFile.getPutPath().lastIndexOf("."),
								uploadFile.getPutPath().length() - 1);
				copyFile(filePath,ctx.getRealPath(newPutPath));
				
				newUploadFile.setPutPath(newPutPath);
				newUploadFile.setBelongId(newBelongId);
				newUploadFile.setBelongTable(newBelongTable);
				newList.add(newUploadFile);
			}
			dao.saveAll(newList);
		}
		return fileCount;
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();

		}

	}
}
