package com.gboss.comm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cc.chinagps.gboss.comcenter.interprotocol.clienttest.CenterClientHandler;
import cc.chinagps.lib.util.Config;

import com.gboss.pojo.EquipCode;
import com.gboss.pojo.FlowCtrolCmd;
import com.gboss.pojo.Preload;
import com.gboss.pojo.Upgrade;
import com.gboss.pojo.VehicleConf;
import com.gboss.util.StringUtils;
import com.google.common.base.Strings;

public class SystemConst {

	public final static String ACCOUNT_USERNAME = "username";
	public final static String ACCOUNT_DEPARTID = "departid";
	public final static String ACCOUNT_ID = "userid";
	public final static String ACCOUNT_ROLEIDS = "roleid";
	public final static String ACCOUNT_TYPE = "usertype";// 登录帐号类型
	public final static String ACCOUNT_GROUPID = "groupid";
	public final static String ACCOUNT_USER = "user";
	public final static String ACCOUNT_ORGID = "orgid";
	public final static String ACCOUNT_COMPANYID = "companyid";
	public final static String ACCOUNT_COMPANYCODE = "companycode";
	public final static String ACCOUNT_COMPANYNAME = "companyname";
	public final static String ACCOUNT_ISHQ = "ishq";// 是否是总部，特殊处理

	public final static int DEFAULT_PAGE_SIZE = 10; // 每页行数

	public final static String SYSTEMID = "c85751af0f8b4984844d9413c288e30c";// 海马流量控制系统

	public static final String LOGID = "f233580102da4553b1a118a449c23774";
	public final static String TOPCOMPANYNO = "3";// 用于判断是否属于分公司
	public final static String HEADQUARTERS = "2";// 用于判断是否属于总部
	public final static String GROUPCOMPANYNO = "4";// 用于判断是否属于集团机构

	public final static String ROLEID_SALES_MANAGER = "3b91ee9136a841a8ac4a912133ca4a64";// 销售经理的角色ID
	public final static String ROLEID_ORG_MANAGER = "6c4209011b384bfc8c01eb8e5f079c42";// 部门经理的角色ID
	public final static String ROLEID_SALES_DIRECTOR = "fa98735655e34dffae93f4d5fd1836b7";// 销售总监的角色ID
	public final static String ROLEID_ELCTRICIAN = "af0f3e9e83ec4c6ba623a6b2f6b409c9";// 营业处电工
	public final static String ROLEID_ORDERS_RECIPIENT = "b89b54165cc249c69fb9b90bff64ee62";// 订单接收人
	public final static String ROLEID_UPDATE = "4739edacf0e94261887f370c21a4be08"; // 空中升级权限ID

	public final static String SEARCH_UNITID = "unit_id";// 原始单据那儿的查询客户ID
	public final static String CUST_INFO = "cust_info";// 查询的客户信息
	public final static String ERROR_MSG = "reeor_msg";// 提示信息

	// 操作结果描述
	public final static String OP_SUCCESS = "操作成功";
	public final static String OP_FAILURE = "操作失败";
	public final static String SUCCESS = "success";
	public final static String MSG = "msg";

	// 操作日志表-操作类型
	public final static int OPERATELOG_LOGIN = 2;
	public final static int OPERATELOG_ADD = 2;
	public final static int OPERATELOG_DEL = 4;
	public final static int OPERATELOG_UPDATE = 3;
	public final static int OPERATELOG_SEARCHKEY = 1;
	public final static int OPERATELOG_LOGOUT = 5;
	public final static int OPERATELOG_SAVE = 10007;
	public final static int OPERATELOG_LOGIN_VALUE = 20000;
	public final static int OPERATELOG_LOGOUT_VALUE = 20001;

	// 任务单状态
	public static final Integer DELETEED_TASK = -1;// 删除
	public static final Integer WAIT_RES_TASK = 1; // 待预约
	public static final Integer HAS_RES_TASK = 2; // 已预约
	public static final Integer HAS_DISPATCH_TASK = 3; // 已派工
	public static final Integer DELING_TASK = 4; // 处理中
	public static final Integer OVER_TASK = 5; // 结束

	// 工单状态
	public static final Integer DELETEED_TASK_STATUS = 0;// 删除
	public static final Integer NORMAL_RES_TASK_STATUS = 1; // 正常
	public static final Integer END_RES_TASK_STATUS = 2; // 已结束

	public static final Integer DISPATCHTASK_DELETED = -1; // 派工删除
	public static final Integer DISPATCHTASK_NORMAL = 1; // 派工正常

	public static final Integer POLICY_DELETEED = 9; // 保单作废删除状态
	public static final Integer POLICY_STOPED = 0; // 保单办停状态
	public static final Integer POLICY_NORMAL = 2; // 保单正常状态

	public static final Integer SUBMIT_DATE = 3; // 交单及时天数

	public static final Integer CAR_TYPE = 1020;

	// 资讯状态
	public static final Integer NEWS_UNCHENKED = 0;
	public static final Integer NEWS_CHENKED = 1;

	// SIM卡状态
	public static final Integer SIM_NOOTSENSITIZE = 0; // 未激活
	public static final Integer SIM_NORM = 1;// 激活
	public static final Integer SIM_STOP = 2;// 停机
	public static final Integer SIM_OUTLINE = 3;// 销号
	public static final Integer SIM_DELETE = 9;// 删除

	// SIM卡套餐状态
	public static final Integer SIM_COMBO_UNNORMAL = 0;// 未生效状态，待生效
	public static final Integer SIM_COMBO_NORMAL = 1;// 生效状态
	public static final Integer SIM_COMBO_APPALY = 2;// 更换套餐申请
	public static final Integer SIM_COMBO_FAIL = 3;// 更换套餐失败
	public static final Integer SIM_COMBO_DELETE = 9;// 删除套餐

	// 故障码集合
	public static Map<String, String> errorMap = new HashMap<String, String>();
	static {
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

	// 故障码集合
	public static Map<String, String> stopReasonMap = new HashMap<String, String>();
	static {
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

	// 空中升级等待队列--数据据中取flag为1、3
	public static ConcurrentHashMap<String, Upgrade> hm_call_letter_upgrade = new ConcurrentHashMap<String, Upgrade>();
	static {

	}
	public static Hashtable<String, Upgrade> hm_call_letter_cancel = new Hashtable<String, Upgrade>();
	static {

	}

	// 空中升级表中所有数据对列
	public static ConcurrentHashMap<String, Upgrade> hm_call_letter_upgradeAll = new ConcurrentHashMap<String, Upgrade>();
	static {

	}
	// 空中升级补发对列
	public static Hashtable<String, Upgrade> hm__upgrade_Reissue = new Hashtable<String, Upgrade>();
	static {

	}

	/***************************************************** 流量控制指令系统缓存 *************************************************/
	public static ConcurrentHashMap<String, ArrayList<FlowCtrolCmd>> hm_send_search_cmd = new ConcurrentHashMap<String, ArrayList<FlowCtrolCmd>>();
	/******** 1.导航主机打开指令 ******/
	// 流量控制指令等待队列；取数据库中flag为：
	public static ConcurrentHashMap<String, FlowCtrolCmd> hm_navi_host_open_cmd = new ConcurrentHashMap<String, FlowCtrolCmd>();
	// 流量控制指令取消队列
	public static Hashtable<String, FlowCtrolCmd> hm_navi_host_cancle_open_cmd = new Hashtable<String, FlowCtrolCmd>();
	// //流量控制指令表所有数据队列
	// public static ConcurrentHashMap <String, FlowCtrolCmd>
	// hm_navi_host_open_cmdAll = new ConcurrentHashMap <String,
	// FlowCtrolCmd>();
	// //流量控制补发队列
	// public static Hashtable <String, FlowCtrolCmd>
	// hm_navi_host_open_cmdReissue = new Hashtable <String, FlowCtrolCmd>();

	/******** 2.导航主机关闭指令 ******/
	// 流量控制指令等待队列；取数据库中flag为：
	public static ConcurrentHashMap<String, FlowCtrolCmd> hm_navi_host_close_cmd = new ConcurrentHashMap<String, FlowCtrolCmd>();
	// 流量控制指令取消队列
	public static Hashtable<String, FlowCtrolCmd> hm_navi_host_cancle_close_cmd = new Hashtable<String, FlowCtrolCmd>();
	// //流量控制指令表所有数据队列
	// public static ConcurrentHashMap <String, FlowCtrolCmd>
	// hm_navi_host_close_cmdAll = new ConcurrentHashMap <String,
	// FlowCtrolCmd>();
	// //流量控制补发队列
	// public static Hashtable <String, FlowCtrolCmd>
	// hm_navi_host_close_cmdReissue = new Hashtable <String, FlowCtrolCmd>();

	/******** 3.省流量打开指令 ******/
	// 省流量指令等待队列；取数据库中flag为：
	public static ConcurrentHashMap<String, FlowCtrolCmd> hm_save_flow_open_cmd = new ConcurrentHashMap<String, FlowCtrolCmd>();
	// 省流量指令取消队列
	public static Hashtable<String, FlowCtrolCmd> hm_save_flow_cancle_open_cmd = new Hashtable<String, FlowCtrolCmd>();
	// //省流量指令表所有数据队列
	// public static ConcurrentHashMap <String, FlowCtrolCmd>
	// hm_save_flow_open_cmdAll = new ConcurrentHashMap <String,
	// FlowCtrolCmd>();
	// //省流量补发队列
	// public static Hashtable <String, FlowCtrolCmd>
	// hm_save_flow_open_cmdReissue = new Hashtable <String, FlowCtrolCmd>();

	/******** 4.省流量打开指令 ******/
	// 省流量指令等待队列；取数据库中flag为：
	public static ConcurrentHashMap<String, FlowCtrolCmd> hm_save_flow_close_cmd = new ConcurrentHashMap<String, FlowCtrolCmd>();
	// 省流量指令取消队列
	public static Hashtable<String, FlowCtrolCmd> hm_save_flow_cancle_close_cmd = new Hashtable<String, FlowCtrolCmd>();
	// //省流量指令表所有数据队列
	// public static ConcurrentHashMap <String, FlowCtrolCmd>
	// hm_save_flow_close_cmdAll = new ConcurrentHashMap <String,
	// FlowCtrolCmd>();
	// //省流量补发队列
	// public static Hashtable <String, FlowCtrolCmd>
	// hm_save_flow_close_cmdReissue = new Hashtable <String, FlowCtrolCmd>();

	/*****************************************************
	 * 流量控制指令系统 缓存end
	 *************************************************/

	// 配置指令所有待下发指令call_letter缓存
	public static ConcurrentHashMap<String, VehicleConf> hm_vehicleconf_cache = new ConcurrentHashMap<String, VehicleConf>();
	// 简码和code1的映射关系缓存
	public static ConcurrentHashMap<String, EquipCode> hm_equipcode_cache = new ConcurrentHashMap<String, EquipCode>();
	// 所有海马绑定接口过来的有EquipCode的sim信息缓存
	public static ConcurrentHashMap<String, Preload> hm_sim_cache = new ConcurrentHashMap<String, Preload>();

	public static final int QUERYCONF_CMD = 0;// 查询配置指令
	public static final int SETCONF_CMD = 1; // 设置配置指令

	public static CenterClientHandler clienthandler = null;

	// 操作日志参数key
	public final static String OPLOG_PARAMS_KEY = "oplog_params_key";

	/**
	 * 海马的subcoNO
	 */
	public final static String HM_SUBCO_NO = "201";
	//流量控制相关指令的指令码
	public static int NAVI_HOST_OPEN_CMD = 240;
	public static int NAVI_HOST_CLOSE_CMD = 241;
	public static int SAVE_FLOW_OPEN_CMD = 211;
	public static int SAVE_FLOW_CLOSE_CMD = 212;
}
