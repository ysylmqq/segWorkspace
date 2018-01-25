package com.gboss.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Constants {

	public static String SYS_CONFIG_FILE = "fileupload.properties";
	public final static int IMAGE_UPLOAD = 1;
	public static String ADMIN_SESSION_KEY = "admin";
	public static String ADMIN_NAME_SESSION_KEY = "admin_name";
	public final static int AD_LIST_UPLOAD = 2;
	public final static int AD_DETAIL_UPLOAD = 3;
	public final static int BIZ_LIST_UPLOAD = 4;
	public final static int BIZ_DETAIL_UPLOAD = 5;
	public final static int DISCOUNT_LIST_UPLOAD = 6;
	public final static int DISCOUNT_DETAIL_UPLOAD = 7;
	
	 //资讯正常
	 public final static int NEWS_NORMAL = 0;
	 //资讯已删除
	 public final static int NEWS_DELETELED = 1;

    public final static int UPLOAD_LARGE_IMG = 2001;
    public final static int UPLOAD_LITTLE_IMG = 2002;
    public final static int UPLOAD_CONTENT_IMG = 2003;
	// 文件服务器上传服务端action标识
	public static final String FILE_UPLOAD_ACTION = "/upload.action";
	// 文件服务器文件下载action标识
	public static final String FILE_DOWNLOAD_ACTION = "/download.action";
	public static final String FILE_DELETE_ACTION = "/delete.action";

	// 文件服务器目录结构标识
	public static final String RDS_SYSTEM_FLAG = "rds";
	// 打包文件后缀名
	public static final String FILE_SUFFIX = ".zip";

	// 广告列表存储路径
	public static final String ADLIST_SAVE_PATH = "/adList";
	// 优惠信息列表存储路径
	public static final String DISCOUNTLIST_SAVE_PATH = "/discountList";
	// 广告存储路径
	public static final String AD_SAVE_PATH = "/ad";
	// 优惠信息存储路径
	public static final String DISCOUNT_SAVE_PATH = "/discount";
	// 广告包名前缀
	public static final String AD_PREFIX_NAME = "";
	// 优惠包名前缀
	public static final String DISCOUNT_PREFIX_NAME = "";
	// 商户列表存储路径
	public static final String BIZLIST_SAVE_PATH = "/bizList";
	// 商户信息存储路径
	public static final String BIZ_SAVE_PATH = "/biz";
	// 商户信息包名前缀
	public static final String BIZ_PREFIX_NAME = "";

	// 图片文件存储路径
	public static final String IMAGES_SAVE_PATH = "/images";
	
	
	public static final String IMAGES_URL = "/fileSys/";
	// 广告列表数据包名
	public static final String AD_LIST_FILE_NAME = "adlist.xml";
	// 广告分类包名
	public static final String AD_TYPE_FILE_NAME = "admenu.xml";
	//优惠信息列表包名
    public static final String DISCOUNT_LIST_FILE_NAME = "discountlist.xml";
	// 用户登录时验证常量
	public static final String SESSION_USER_NAME = "userName";
	public static final String SESSION_USER_PASSWORD = "userPassword";
	// 头像地址
	public static final String SESSION_IMGPATH = "imgPath";
	// 跳转的url
	public static final String URLREF = "urlRef";

	private static Properties properties = new Properties();

	static {
		try {
			// 读取.properties文件
			InputStream in = Constants.class.getClassLoader()
					.getResourceAsStream(SYS_CONFIG_FILE);
			// 将流中读取键值对，放到properties中！
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String strSysConfig(String key) {
		return properties.getProperty(key);
	}

	public static int intSysConfig(String key, int defValue) {
		try {
			return Integer.parseInt(strSysConfig(key));
		} catch (Exception e) {
			return defValue;
		}
	}

	public static int intSysConfig(String key) {
		return intSysConfig(key, 0);
	}

	public static float floatSysConfig(String key, float defValue) {
		try {
			return Float.parseFloat(strSysConfig(key));
		} catch (Exception e) {
			return defValue;
		}
	}

	public static float floatSysConfig(String key) {
		return floatSysConfig(key, 0);
	}

}
