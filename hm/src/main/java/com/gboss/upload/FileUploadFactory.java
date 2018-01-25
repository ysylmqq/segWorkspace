package com.gboss.upload;

import java.io.InputStream;
import java.util.Date;

import com.gboss.util.Constants;

import com.gboss.util.RandomFileName;
import com.gboss.util.DateUtil;

/*
 * 上传类工厂
 */
public class FileUploadFactory {
	/*
	 * 根据不同参数，返回上传类实例
	 */
	public static FileUpload getInstance(int uploadFileType) {
		FileUpload fileUpload = null;

		switch (uploadFileType) {
		case Constants.IMAGE_UPLOAD:
			/*
			 * 图片文件上传方法 return 文件下载地址，上传失败，返回空字符串
			 */
			fileUpload = new FileUpload() {
				@Override
				public String fileUpload(InputStream input, String fileName) {
					// 转换成不重复随机文件名
					fileName = RandomFileName.randomFileName(fileName);
					String s = DateUtil.format(new Date(), DateUtil.YMD_DASH);
					String path = Constants.IMAGES_SAVE_PATH + "/" + s;
					// 拼装上传url
					String urlStr = FilePathBuild
							.fileUploadPath(path, fileName);

					if (FileUploadClient.fileUpload(urlStr, input)) {
						return FilePathBuild.fileDownloadPath(fileName);
					}
					return "";
				}
			};
			break;
		case Constants.AD_LIST_UPLOAD:
			fileUpload = new FileUpload() {
				/*
				 * 广告列表zip包上传方法 return 文件下载地址，上传失败，返回空字符串
				 */
				@Override
				public String fileUpload(InputStream input, String fileName) {
					// 拼装上传url
					String urlStr = FilePathBuild.fileUploadPath(
							Constants.ADLIST_SAVE_PATH, fileName);

					if (FileUploadClient.fileUpload(urlStr, input)) {
						return FilePathBuild
								.fileDownloadPath(Constants.ADLIST_SAVE_PATH
										+ "/" + fileName);
					}
					return "";
				}
			};
			break;
		case Constants.AD_DETAIL_UPLOAD:
			fileUpload = new FileUpload() {
				/*
				 * 广告详情zip包上传方法 return 文件下载地址，上传失败，返回空字符串
				 */
				@Override
				public String fileUpload(InputStream input, String fileName) {
					// 拼装上传url
					String urlStr = FilePathBuild.fileUploadPath(
							Constants.AD_SAVE_PATH, fileName);

					if (FileUploadClient.fileUpload(urlStr, input)) {
						return FilePathBuild
								.fileDownloadPath(Constants.AD_SAVE_PATH + "/"
										+ fileName);
					}
					return "";
				}
			};
			break;
		case Constants.BIZ_LIST_UPLOAD:
			fileUpload = new FileUpload() {
				@Override
				public String fileUpload(InputStream input, String fileName) {
					return null;
				}
			};
			break;
		case Constants.BIZ_DETAIL_UPLOAD:
			fileUpload = new FileUpload() {
				@Override
				public String fileUpload(InputStream input, String fileName) {
					return null;
				}
			};
			break;
		case Constants.DISCOUNT_LIST_UPLOAD:
			fileUpload = new FileUpload() {
				/*
				 * 优惠信息列表zip包上传方法 return 文件下载地址，上传失败，返回空字符串
				 */
				@Override
				public String fileUpload(InputStream input, String fileName) {
					// 拼装上传url
					String urlStr = FilePathBuild.fileUploadPath(
							Constants.DISCOUNTLIST_SAVE_PATH, fileName);

					if (FileUploadClient.fileUpload(urlStr, input)) {
						return FilePathBuild
								.fileDownloadPath(Constants.DISCOUNTLIST_SAVE_PATH
										+ "/" + fileName);
					}
					return "";
				}
			};
			break;
		case Constants.DISCOUNT_DETAIL_UPLOAD :
			fileUpload = new FileUpload() {
				/*
				 * 优惠信息详情zip包上传方法 return 文件下载地址，上传失败，返回空字符串
				 */
				@Override
				public String fileUpload(InputStream input, String fileName) {
					// 拼装上传url
					String urlStr = FilePathBuild.fileUploadPath(
							Constants.DISCOUNT_SAVE_PATH, fileName);

					if (FileUploadClient.fileUpload(urlStr, input)) {
						return FilePathBuild
								.fileDownloadPath(Constants.DISCOUNT_SAVE_PATH
										+ "/" + fileName);
					}
					return "";
				}
			};
			break;
		default:
			fileUpload = new FileUpload() {
				@Override
				public String fileUpload(InputStream input, String fileName) {
					return null;
				}
			};
			break;
		}
		return fileUpload;
	}
}