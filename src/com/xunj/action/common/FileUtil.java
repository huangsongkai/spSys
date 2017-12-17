package com.xunj.action.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import javax.servlet.ServletContext;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

public class FileUtil {

	private FTPClient ftp;
	private String server;
	private int port;
	private String username;

	private String password;

	private final static Logger log = Logger.getLogger(FileUtil.class);
	private boolean binaryTransfer = true;
	private int BUFFER_SIZE = 16 * 1024;
	private ServletContext servletContext;
	HashMap<String, String> config = null;

	public FileUtil(ServletContext servletContext) {
		this.servletContext = servletContext;
		config = (HashMap<String, String>) servletContext
				.getAttribute("sysConfig");
		server = config.get("ftpServerIP");
		port = Integer.parseInt(config.get("ftpServerPort"));
		username = config.get("ftpAdminUser");
		password = config.get("ftpAdminPassword");
	}

	/**
	 * 进行ftp连接
	 * 
	 * @return
	 */
	public boolean connect() {
		try {
			int reply;
			ftp = new FTPClient();
			ftp.connect(server, port);
			// 连接后检测返回码来校验连接是否成功
			reply = ftp.getReplyCode();

			if (FTPReply.isPositiveCompletion(reply)) {
				if (ftp.login(username, password)) {
					// 设置为passive模式
					ftp.enterLocalPassiveMode();
					return true;
				}
			} else {
				ftp.disconnect();
				log.error("FTP server refused connection.");
			}
		} catch (IOException e) {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
				}
			}
			log.error("Could not connect to server.", e);
		}
		return false;
	}

	/**
	 * 断开ftp连接
	 */
	public void disconnect() {
		try {
			ftp.logout();
			if (ftp.isConnected()) {
				ftp.disconnect();
			}
		} catch (IOException e) {
			log.error("Could not disconnect from server.", e);
		}
	}

	/**
	 * 上传一个本地文件到远程指定文件
	 * 
	 * @param remoteAbsoluteFile
	 *            远程文件名(包括完整路径)
	 * @param localAbsoluteFile
	 *            本地文件名(包括完整路径)
	 * @return 成功时，返回true，失败返回false
	 */
	public boolean uploadToFtp(String remoteAbsoluteFile, File file,
			String folder) {
		InputStream input = null;
		try {
			// //设置文件传输类型
			if (binaryTransfer) {
				ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			} else {
				ftp.setFileType(FTPClient.ASCII_FILE_TYPE);
			}
			// 处理传输
			input = new FileInputStream(file);
			ftp.makeDirectory(folder);
			ftp.storeFile(folder + "/" + remoteAbsoluteFile, input);

			input.close();

			return true;
		} catch (FileNotFoundException e) {
			log.error("local file not found.", e);
		} catch (IOException e1) {
			log.error("Could put file to server.", e1);
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (Exception e2) {
			}
		}

		return false;
	}

	/**
	 * 下载一个远程文件到本地的指定文件
	 * 
	 * @param remoteAbsoluteFile
	 *            远程文件名(包括完整路径)
	 * @param localAbsoluteFile
	 *            本地文件名(包括完整路径)
	 * @return 成功时，返回true，失败返回false
	 */
	public boolean getFtpFile(String remoteAbsoluteFile,String localAbsoluteFile) {
		OutputStream output = null;
		try {
			// 设置文件传输类型
			if (binaryTransfer) {
				ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			} else {
				ftp.setFileType(FTPClient.ASCII_FILE_TYPE);
			}
			// 处理传输
			output = new FileOutputStream(localAbsoluteFile);
			ftp.retrieveFile(remoteAbsoluteFile, output);
			output.close();
			return true;
		} catch (FileNotFoundException e) {
			log.error("local file not found.", e);
		} catch (IOException e1) {
			log.error("Could get file from server.", e1);
		} finally {
			try {
				if (output != null) {
					output.close();
				}
			} catch (IOException e2) {
			}
		}
		return false;
	}

	/**
	 * 删除远程文件
	 * 
	 * @param remoteAbsoluteFile
	 *            远程文件名(包括完整路径)
	 * @return 成功时，返回true，失败返回false
	 */
	public boolean deleteFtpFile(String remoteAbsoluteFile) {
		try {
			connect();
			// 删除远程文件
			ftp.deleteFile(remoteAbsoluteFile);
		} catch (IOException e1) {
			log.error("Could get file from server.", e1);
		} finally {
			disconnect();
		}
		return false;
	}

	/**
	 * 删除远程文件
	 * 
	 * @param remoteAbsoluteFile
	 *            远程文件名(包括完整路径)
	 * @return 成功时，返回true，失败返回false
	 */
	public boolean deleteNativeFile(String putPath) {
		try {

			File file = new File(servletContext.getRealPath(putPath));
			if (file.exists())
				file.delete();
			return true;
		} catch (Exception e1) {
			log.error("Could get file from server.", e1);
		}
		return false;
	}

	/**
	 * 上传附件到本地文件
	 * 
	 * @param src
	 * @param dst
	 */
	public void uploadToNative(File src, File dst) {

		// System.out.println("2-->:"+dst.getPath());
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(src),
						BUFFER_SIZE);
				out = new BufferedOutputStream(new FileOutputStream(dst));
				byte[] buffer = new byte[BUFFER_SIZE];
				int length = 0;
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 上传附件
	 */
	public void uploadFile(String newName, File file, String folder) {

		// 文件保存形式，网络或本地native、network
		String uploadFileSavePath = config.get("uploadFileSavePath");
		// 将附件保存在本地
		if (uploadFileSavePath.equals("native")) {
			String filePath = servletContext.getRealPath(config
					.get("nativeUpLoadPath"));
			File newfilefolder = new File(filePath + "/" + folder);
			if (!newfilefolder.exists())
				newfilefolder.mkdirs();
			File newfile = new File(filePath + folder + "/" + newName);
			uploadToNative(file, newfile);
		} else {
			try {
				connect();
				uploadToFtp(newName, file, folder);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				disconnect();
			}
		}

	}
}
