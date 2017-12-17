package com.xunj.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

public class ZipTools {

	/**
	 * 功能：解压 zip 文件，只能解压 zip 文件
	 * 
	 * @param zipfile
	 * @param destDir
	 */

	public static void unZip(String zipfile, String destDir) {

		destDir = destDir.endsWith("\\") ? destDir : destDir + "\\";

		byte b[] = new byte[1024];

		int length;

		ZipFile zipFile;

		try {

			zipFile = new ZipFile(new File(zipfile));

			Enumeration enumeration = zipFile.getEntries();

			ZipEntry zipEntry = null;

			while (enumeration.hasMoreElements()) {

				zipEntry = (ZipEntry) enumeration.nextElement();

				File loadFile = new File(destDir + zipEntry.getName());

				if (zipEntry.isDirectory()) {

					loadFile.mkdirs();

				} else {

					if (!loadFile.getParentFile().exists()) {
						loadFile.getParentFile().mkdirs();

					}
					OutputStream outputStream = new FileOutputStream(loadFile);

					InputStream inputStream = zipFile.getInputStream(zipEntry);

					while ((length = inputStream.read(b)) > 0)
						outputStream.write(b, 0, length);
					outputStream.close();
					inputStream.close();
				}
			}
			zipFile.close();
		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	/**
	 * 压缩文件
	 */
	public static void zip(String srcPathName, String zipName) {
		File zipFile = new File(zipName);
		File srcdir = new File(srcPathName);
		if (!srcdir.exists())
			throw new RuntimeException(srcPathName + "不存在！");

		Project prj = new Project();
		Zip zip = new Zip();
		zip.setProject(prj);
		zip.setDestFile(zipFile);
		FileSet fileSet = new FileSet();
		fileSet.setProject(prj);
		fileSet.setDir(srcdir);
		// fileSet.setIncludes("**/*.java"); 包括哪些文件或文件夹
		// eg:zip.setIncludes("*.java");
		// fileSet.setExcludes(...); 排除哪些文件或文件夹
		zip.addFileset(fileSet);

		zip.execute();
	}

	public static void main(String[] args) {
		try {
//			zip("d://tmp", "d://tt.zip");
			unZip("d://test.zip", "d://tttttt/");
			System.out.println("sucess!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
