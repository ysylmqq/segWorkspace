package com.gboss.comm;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;

import com.gboss.pojo.Equipcode;

public class SystemConst {
	
	/**日志*/
	public static final String LOGGER_NAME_EXCEPTION 	= "nameException";
	public static final String LOGGER_NAME_OTHERS 		= "nameOthers";
	public static final String LOGGER_NAME_DEBUG 		= "nameDebug";
	public static final String LOGGER_NAME_SYNCDATA     = "nameSyncdata";
	
	public final static Logger E_LOG 		= LogManager.getLogger(SystemConst.LOGGER_NAME_EXCEPTION);
	public final static Logger D_LOG		= LogManager.getLogger(SystemConst.LOGGER_NAME_DEBUG);
	public final static Logger SYNCDATA_LOG		= LogManager.getLogger(SystemConst.LOGGER_NAME_SYNCDATA);
	/**日志*/

	public final static String ACCOUNT_USERNAME="username";
    public final static String ACCOUNT_DEPARTID="departid";
    public final static String ACCOUNT_ID="userid";
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
	
	//public final static String SYSTEMID="9c7fc19936a34a26ba6af3d3aae2472f";//LDAP中本系统的systemid
	public final static String SYSTEMID="2da1a64cea404c0eacf58c5c4a883564";//二版门店子系统
	public static final String LOGID = "f233580102da4553b1a118a449c23774";
	public final static String TOPCOMPANYNO="3";//用于判断是否属于分公司
	public final static String HEADQUARTERS="2";//用于判断是否属于总部
	public final static String GROUPCOMPANYNO="4";//用于判断是否属于集团机构
	
	public final static String ROLEID_SALES_MANAGER="3b91ee9136a841a8ac4a912133ca4a64";//销售经理的角色ID
	public final static String ROLEID_ORG_MANAGER="6c4209011b384bfc8c01eb8e5f079c42";//部门经理的角色ID
	public final static String ROLEID_SALES_DIRECTOR="fa98735655e34dffae93f4d5fd1836b7";//销售总监的角色ID
	public final static String ROLEID_ELCTRICIAN="af0f3e9e83ec4c6ba623a6b2f6b409c9";//营业处电工
	public final static String ROLEID_ORDERS_RECIPIENT="b89b54165cc249c69fb9b90bff64ee62";//订单接收人
	
	public final static String SEARCH_UNITID="unit_id";//原始单据那儿的查询客户ID
	public final static String CUST_INFO="cust_info";//查询的客户信息
	public final static String ERROR_MSG="reeor_msg";//提示信息
	
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
	public final static String CODE="code";
	
	
	//操作日志表-操作类型
	public final static int OPERATELOG_LOGIN=2;
	public final static int OPERATELOG_ADD=2;
	public final static int OPERATELOG_DEL=4;
	public final static int OPERATELOG_UPDATE=3;
	public final static int OPERATELOG_SEARCHKEY=1;
	public final static int OPERATELOG_LOGOUT=5;
	public final static int OPERATELOG_SAVE=10007;
	public final static int OPERATELOG_LOGIN_VALUE=20000;
	public final static int OPERATELOG_LOGOUT_VALUE=20001;
	
	//单号前缀
	public final static String STOCKIN_NO_PREFIX="KA";//入库单号
	public final static String STOCKOUT_NO_PREFIX="SO";//出库单号
	public final static String WRITEOFFS_NO_PREFIX="HX";//核销单号
	public final static String WRITEOFF_NO_PREFIX="XZ";//销账单号
	public final static String ORDER_NO_PREFIX="PO";//订单单号
	public final static String SALES_NO_PREFIX="SC";//销售合同号
	public final static String SUPPLY_NO_PREFIX="PC";//采购合同号
	public final static String TASK_NO_PREFIX = "TA";//装单单号
	public final static String POLICY_NO_PREFIX="PO";//保险单号
	
	//任务单类型
	public static final Integer INSTALL_TASK = 1;//新装接单
	public static final Integer REPAIR_TASK = 2;//维修接单
	public static final Integer POLICE_TASK = 3;//出警接单
	public static final Integer OTHER_TASK = 4;//其他接单
	
	//电工状态标记字段
	public static final Integer ONDUTY_FREE = 0;//闲
	public static final Integer ONDUTY_BUSY = 1;//忙
	
	//电工状态
	public static final String ONDUTY_FREE_STR = "空闲";
    public static final String ONDUTY_BUSY_STR = "繁忙";
    public static final String ONDUTY_REST_STR = "休息";
    
    //任务单状态
  	public static final Integer DELETEED_TASK = -1;//删除
  	public static final Integer WAIT_RES_TASK = 1; //待预约
  	public static final Integer HAS_RES_TASK = 2; //已预约
	public static final Integer HAS_DISPATCH_TASK = 3; //已派工
  	public static final Integer DELING_TASK = 4; //处理中
  	public static final Integer OVER_TASK = 5; //结束
  	
  	//工单状态
  	public static final Integer DELETEED_TASK_STATUS = 0;//删除
  	public static final Integer NORMAL_RES_TASK_STATUS = 1; //正常
  	public static final Integer END_RES_TASK_STATUS = 2; //已结束
  	
  	public static final Integer DISPATCHTASK_DELETED = -1; //派工删除
  	public static final Integer DISPATCHTASK_NORMAL = 1; //派工正常
  	
  	
  	public static final Integer POLICY_DELETEED = 9; //保单作废删除状态
	public static final Integer POLICY_STOPED = 0; //保单办停状态
  	public static final Integer POLICY_NORMAL = 2; //保单正常状态
  	
  	
  	public static final Integer SUBMIT_DATE = 3; //交单及时天数
  	
  	
  	public static final Integer CAR_TYPE = 1020;
 	
  	//资讯状态
  	public static final Integer NEWS_UNCHENKED = 0;
	public static final Integer NEWS_CHENKED = 1;
	
	//SIM卡状态
	public static final Integer SIM_NOOTSENSITIZE = 0; //未激活
	public static final Integer SIM_NORM = 1;//激活
	public static final Integer SIM_STOP = 2;//停机
	public static final Integer SIM_OUTLINE = 3;//销号
	public static final Integer SIM_DELETE = 9;//删除
	
	//SIM阶段状态
	public static final Integer SIM_PERIOD_ONE 		= 1; //1.入网前(SIM开通激活到4s店入网) 
	public static final Integer SIM_PERIOD_TWO 		= 2; //2.销售前(4s店入网到客户开通) 
	public static final Integer SIM_PERIOD_THREE 	= 3; //3.客户使用(客户开通到客户停用)
	public static final Integer SIM_PERIOD_FOUR 	= 4; //4.客户停用后 
	public static final Integer SIM_PERIOD_FIVE 	= 5; //注销  车辆报废
	
		
	//SIM卡套餐状态
	public static final Integer SIM_COMBO_UNNORMAL =0;//未生效状态，待生效
	public static final Integer SIM_COMBO_NORMAL =1;//生效状态	
	public static final Integer SIM_COMBO_APPALY =2;//更换套餐申请
	public static final Integer SIM_COMBO_FAIL =3;//更换套餐失败
	public static final Integer SIM_COMBO_DELETE = 9;//删除套餐
	
	//故障码集合
	public static  Map<String, String> errorMap = new HashMap<String, String>();
	static{
		errorMap.put("1", "ABS");
		errorMap.put("2", "ESP");
		errorMap.put("3", "SRS");
		errorMap.put("4", "EMS");
		errorMap.put("5", "IMMO");
		errorMap.put("6", "PEPS");
		errorMap.put("7", "BCM");
		errorMap.put("8", "TCU");
		errorMap.put("9", "TPMS");
		errorMap.put("10", "TBOX");
		errorMap.put("11", "APM");
		errorMap.put("12", "ICM");
	}
	
	

	//故障码集合
	public static  Map<String, String> stopReasonMap = new HashMap<String, String>();
	static{
		stopReasonMap.put("1", "车子将/已转卖/报废");
		stopReasonMap.put("2", "产品质量问题");
		stopReasonMap.put("3", "4S店服务品质问题");
		stopReasonMap.put("4", "服务中心服务质量问题");
		stopReasonMap.put("5", "营业处服务质量问题");
		stopReasonMap.put("6", "使用价值不大");
		stopReasonMap.put("7", "个人原因");
		stopReasonMap.put("8", "其他原因");
		stopReasonMap.put("9", "暂时停止");
	}
	
	//升级队列
	public static  Hashtable<String, Object> is_upgradeing = new Hashtable<String, Object>();
	static{
			
		}
		
	//失败呼号集合
	public static  Hashtable<String, Object> upgrade_failed = new Hashtable<String, Object>();
	static{
		
	  }	
		
	
	//空中升级等待队列
	public static Hashtable<String, Object> hm_call_letter_upgrade = new Hashtable<String, Object>();
	static{
		
	}
	
		
		
	//字典表常量
	public final static Long DICT_CHANNEL_SELF=5l;//代理商_5:营业厅
	
	//操作日志参数key
	public final static String OPLOG_PARAMS_KEY="oplog_params_key";
	
	//商品级别,0:主料，1:替代料
	public final static String PRODUCT_LEVEL0="主料";
	public final static String PRODUCT_LEVEL1="替代料";
	
	
	public final static String HM_MODEL= "model";
	
	public final static String HM_SERIES= "series";
	
	public final static String HM_4S= "4s";
	
	public final static String HM_INSURANCE= "insurance";
	
	public final static String HM_COMBO= "combo";
	
	public final static String HM_ACCOUNT= "account";
	
	public final static String HM_BASEDASTA= "basedata";
	
	public final static String HM_BANDINFO= "bandinfo";
	
	public final static long HM_SUBCO_NO= 201L;
	
	public final static String HM_SUBCO_NAME= "海马汽车";
	
	//是否高配
	public final static ConcurrentMap<String, Equipcode> equipcode_Cache = new ConcurrentHashMap<String, Equipcode>();
	
	public final static double FEE_MON_HIGH = 7.0;
	public final static double FEE_MON_LOW 	= 2.7;
	
	
}
