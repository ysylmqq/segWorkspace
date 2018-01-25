package com.hm.bigdata.comm;

import java.util.HashMap;
import java.util.Map;

public class SystemConst {

	public final static String ACCOUNT_USERNAME="username";
    public final static String ACCOUNT_DEPARTID="departid";
    public final static String ACCOUNT_ID="userid";
    public final static String ACCOUNT_ROLEID="roleid";
    public final static String ACCOUNT_ROLEIDS="roleid";
    public final static String ACCOUNT_TYPE="usertype";//登录帐号类型
    public final static String ACCOUNT_GROUPID="groupid";
    public final static String ACCOUNT_USER="user";
    public final static String ACCOUNT_ORGID="orgid";
    public final static String ACCOUNT_COMPANYID="companyid";
    public final static String ACCOUNT_COMPANYCODE="companycode";
    public final static String ACCOUNT_COMPANYNAME="companyname";
    public final static String ACCOUNT_ISHQ="ishq";//是否是总部，特殊处理

	
	public final static int DEFAULT_PAGE_SIZE = 10; // 每页行数
	
	public final static String SYSTEMID="658fb6d3f0ca4a3d8545b4ea35a4ee85";//LDAP中本系统的systemid  fee  a9182f1b90864facb42f5cd32bfd4978 
	public static final String LOGID = "f233580102da4553b1a118a449c23774";
	public final static String TOPCOMPANYNO="3";//用于判断是否属于分公司
	public final static String HEADQUARTERS="2";//用于判断是否属于总部
	
	//商品是否在销，1：是，0：否
	public final static int ISSELL_YES=1;
	public final static int ISSELL_NO=0;

	//商品类别,1:成品，2：配件
	public final static int PRODUCT_TYPE_FULL=1;
	public final static int PRODUCT_TYPE_PART=0;
	
	//商品状态，1：已生效，0：未生效
	public final static int STATUS_VALID=1;
	public final static int STATUS_NOT_VALID=0;
	
	//操作结果描述
	public final static String OP_SUCCESS="操作成功";
	public final static String OP_FAILURE="操作失败";
	public final static String SUCCESS="success";
	public final static String MSG="msg";
	
	//操作日志表-操作类型
	public final static int OPERATELOG_LOGIN=2;
	public final static int OPERATELOG_ADD=2;
	public final static int OPERATELOG_DEL=4;
	public final static int OPERATELOG_UPDATE=3;
	public final static int OPERATELOG_SEARCHKEY=10005;
	public final static int OPERATELOG_LOGOUT=5;
	public final static int OPERATELOG_SAVE=10007;
	public final static int OPERATELOG_LOGIN_VALUE=30041;
	public final static int OPERATELOG_LOGOUT_VALUE=30042;
	
	public final static String ROLEID_SALES_MANAGER="3b91ee9136a841a8ac4a912133ca4a64";//销售经理的角色ID
	public final static String ROLEID_ELCTRICIAN="af0f3e9e83ec4c6ba623a6b2f6b409c9";//营业处电工
	
	//托收文件-类型
	public final static Map<Integer, String> PAY_TXT_TYPE = new HashMap<Integer, String>();
	//收费方式
	//public final static Map<Integer, String> PAY_MODEL = new HashMap<Integer, String>();
	//托收机构
	public final static Map<Integer, String> AGENCY = new HashMap<Integer, String>();
	//银行账号类型
	public final static Map<Integer, String> BACCOUNT_TYPE = new HashMap<Integer, String>();

	static{
		//托收下载文件-类型 1：金融中心交易文件、2：金融中心总结文件、3：银盛交易文件、4：银盛总结文件、5：中国银联交易、总结文件
		PAY_TXT_TYPE.put(1, ".T30");
		PAY_TXT_TYPE.put(2, ".T40");
		PAY_TXT_TYPE.put(3, ".T30");
		PAY_TXT_TYPE.put(4, ".T40");
		PAY_TXT_TYPE.put(5, ".txt");
		
		//托收上传文件-类型 6：金融中心交易文件、7：金融中心总结文件、8：银盛交易文件、9：银盛总结文件、10：中国银联交易、总结文件
		PAY_TXT_TYPE.put(6, ".T10");
		PAY_TXT_TYPE.put(7, ".T20");
		PAY_TXT_TYPE.put(8, ".T10");
		PAY_TXT_TYPE.put(9, ".T20");
		PAY_TXT_TYPE.put(10, ".rnt");
		
		/*//收费方式 
		PAY_MODEL.put(0, "托收");
		PAY_MODEL.put(1, "现金");
		PAY_MODEL.put(2, "转账");
		PAY_MODEL.put(3, "刷卡");
		*/
		
		//托收机构
		AGENCY.put(1, "银盛");
		AGENCY.put(2, "金融中心");
		AGENCY.put(3, "银联");
		
		//银行账号类型
		BACCOUNT_TYPE.put(0, "借记卡");
		BACCOUNT_TYPE.put(1, "存折");
		BACCOUNT_TYPE.put(2, "信用卡");
}

	public static String ZERO1 = "0000";//2
	public static String ZERO2 = "00";//2
	public static String RMB0 = "0";//币种
	public static String PAY_TYPE1 = "1";//收付标志 1-收；0-付
	public static String BLANK = "";//空字符
	public static String PAY_Q = "Q";//“Q”为扣费请求文件，“P”为扣费响应文件
	public static String PAY_S = "S";	// S为代收请求， F为代付请求
	public static String PAY_F = "F";
	public static String PAY__ = "_";//“_”-分隔符
	public static String PAY_VERSION02 = "02";	//代收协议版本 02版
	public static String PAY_VERSION03 = "03";	//代收协议版本 02版
	public static String SERVICE_FEE = "服务费";
	public static String PAY_Y1 = "Y";//“Y”-成功扣款
	public static String PAY_TAG_DEFAULT = "X";//托收文件记录表 托收标记 默认值
	public static String PAY_CODE = "10800";	//代收业务代码
	
	public final static int MAX_SIZE=1000;//sql in 最大个数
	public final static int WORD_MAX_PAGE=1000;//word最大页数
	public final static int COLL_MAX_MAX=5000;//集合中最大对象数
	public final static int TX_COMMIT_SIZE=100;//100条提交一次,在Hibernate的配置文件中，应该把hibernate.jdbc.batch_size属性也设为50。
	
	public final static String FEE_FREEMARK_URL="/com/chinagps/fee/util";//发票打印文件模板url
	public final static String FEE_FREEMARK_NAME="fee_print.ftl";//发票打印文件模板文件名称
	
	public final static String FEE_EMAIL_TITLE="赛格导航计费托收电子对账单";//赛格导航计费托收电子对账单 的邮件标题
	
	//计费项目类型在系统参数中的类型ＩＤ
	public final static int FEE_TYPE_ID=3100;
	//缴费方式在系统参数中的类型ＩＤ
	public final static int PAY_MODEL_ID=3050;
	//投递方式在系统参数中的类型ＩＤ
	public final static int DELIVERY_ID=3110;
	//账号类型在系统参数中的类型ＩＤ
	public final static int ACCOUNT_TYPE_ID=3110;
}
