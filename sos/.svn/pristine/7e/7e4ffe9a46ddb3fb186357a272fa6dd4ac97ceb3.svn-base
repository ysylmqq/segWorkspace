package com.gboss.upload;

import com.gboss.util.Constants;
import com.gboss.util.WebFileUtils;


public class FilePathBuild {
	/*
	 * 拼装上传文件服务器路径 String sysFlag:系统标识，确认文件服务器上本系统文件存放的具体路径 String
	 * filePath:文件服务器相对路径 example:/d/d String fileName:文件名称 example:test.zip
	 */
	public static String fileUploadPath(String filePath, String fileName) {
	/*	return Constants.strSysConfig("documentfileUrl")
				+ Constants.FILE_UPLOAD_ACTION + "?sysFlag="
				+ Constants.RDS_SYSTEM_FLAG + "&filePath=" + filePath
				+ "&fileName=" + fileName;*/
		return Constants.strSysConfig("documentfileUrl")
				+ Constants.FILE_UPLOAD_ACTION + "?&fileName=" + fileName;
	}

	/*
	 * 拼装文件下载相对路径 String sysFlag:系统标识，确认文件服务器上本系统文件存放的具体路径 String
	 * path:文件服务器相对路径，包含文件名 example:/d/d/test.zip
	 */
	public static String fileDownloadPath(String fileName) {
		return Constants.strSysConfig("documentfileUrl") + Constants.IMAGES_URL + fileName;
	}

	/*
	 * 拼装文件下载全路径 String sysFlag:系统标识，确认文件服务器上本系统文件存放的具体路径 String
	 * path:文件服务器相对路径，包含文件名 example:/d/d/test.zip
	 */
	public static String fileDownloadFullPath(String path) {
		return Constants.strSysConfig("documentfileUrl")
				+ Constants.FILE_DOWNLOAD_ACTION + "?sysFlag="
				+ Constants.RDS_SYSTEM_FLAG + "&filePath=" + path;
	}

	/*
	 * 拼装文件下载全路径 String sysFlag:系统标识，确认文件服务器上本系统文件存放的具体路径 String
	 * relativePath:文件服务器相对路径，包含文件名
	 * example:/download.action?sysFlag=lbass&path=/images/1322818296578.JPG
	 */
	public static String fileDownloadRealPath(String relativePath) {
		if (relativePath == null || "".equals(relativePath)) {
			return "";
		}

		if (WebFileUtils.isOuterUrl(relativePath)) {
			return relativePath;
		}
		return Constants.strSysConfig("documentfileUrl") + relativePath;
	}

	/**
	 * 从下载路径获取删除全路径
	 * 
	 * @param downPath
	 *            下载路径
	 */
	public static String fileDeleteFullPath(String downPath) {
		if (downPath == null || "".equals(downPath)) {
			return null;
		}
		return fileDownloadRealPath(downPath.replaceFirst(
				Constants.FILE_DOWNLOAD_ACTION, Constants.FILE_DELETE_ACTION));
	}

}