package com.chinaGPS.gtmp.util;

/**
 * 静态常量类
 * 
 * @author xk
 * 
 */
public class Constants {
	// 用户session的key
	public final static String USER_ID = "userId"; // 用户id
	public final static String USER_NAME = "userName"; // 用户名称
	public final static String USER_INFO = "userInfo"; // 用户信息
	public final static String USER_TYPE = "userType"; // 用户类别：0-玉柴用户 1-机主用户
	public final static String MODULES = "modules"; // 用户可访问功能权限列表
	public final static String VEHICLE_STATUS = "vehicleStatus"; // 用户可访问的机械状态组
	public final static String REMOTE_IP = "remoteIp"; // 用户访问ip
	public final static String FIRST_PAGE = "firstPage"; // 首页地址

	// 运营监管，指令相关的session
	public final static String WATCH_VEHICLE = "watchVehicle"; // 监控车辆
	public final static String COMMAND_SN = "commandSn"; // 指令SN
	public final static String COMMAND_SN_UPGRADE = "commandSnUpgrade";// 指令SN,存放空中升级的sn
	public final static String GPSINFO_SN = "gpsInfoSn"; // 位置的key
	public final static String WORKINFO_SN = "workInfoSn"; // 工况的key
	public final static String UNITBACK_SN = "unitBackSn"; // 一般指令终端回应的key

	public final static Integer UNIT_STATE = 0; // 注册，待测试

	public final static Integer VEHICLE_STATE1 = 1; // 测试组
	public final static Integer VEHICLE_STATE2 = 2; // 已测组
	public final static Integer VEHICLE_STATE3 = 3; // 销售组
	public final static Integer VEHICLE_STATE8 = 8; // 法务组
	public final static Integer VEHICLE_STATE9 = 9; // 停用组

	public final static Integer IS_TEST_NO = 0; // 未测试
	public final static Integer IS_TEST_YES = 1; // 已测试

	public final static Integer IS_VALID_YES = 0; // 有效
	public final static Integer IS_VALID_NO = 1; // 无效

	public final static String ROOT_ID = "root_id"; // 机械列表树根节点id
	public final static String ROOT_TEXT = "机械列表"; // 机械列表树根节点text

	public final static String NODE_STATE_OPEN = "open"; // 树节点state开
	public final static String NODE_STATE_CLOSE = "closed"; // 树节点state关

	public final static String NODE_ONLINE_ICON = "icon-online";// 树的叶子节点图标_在线
	public final static String NODE_OFFLINE_ICON = "icon-offline";// 树的叶子节点图标_离线
	public final static String NODE_VEHICLE_PREFIX = "vehicle_";// 树的叶子节点图标

	public final static Long DEALER_ROLE_ID = 3l; // 经销商角色ID
	public final static Long SUPPERIER_ROLE_ID = 4l; // 终端供应商角色ID
	public final static Long LEASEHOLD_ROLE_ID = 84l; // 融资租赁角色ID

	public final static Long SUPPERIER_DEPART_ID = 4l; // 终端供应商机构树根节点ID

	public final static Integer COMMAND_TYPE_GPS = 32; // 指令类型为定位的commandTypID
	public final static Integer COMMAND_TYPE_TRACK = 33; // 指令类型为跟踪的commandTypID
	public final static Integer COMMAND_TYPE_CONDITIONS = 81;// 指令类型为工况采集的commandTypID
	public final static Integer COMMAND_TYPE_UPGRADE = 16;// 指令类型为空中升级的commandTypID

	public final static String SYSTEM_PARAMS = "systemParams";// 系统参数集合
	public final static String MAP_TAG_COUNT = "MapTagNum";// 系统参数：地图标注的数量

	public final static Integer MODULE_TYPE_DATA = 4; // 模块表中MODULE_TYPE=4为数据权限
	public final static Integer MODULE_TYPE_MENU = 1; // 模块表中MODULE_TYPE=1为菜单

	public final static String VEHICLE_STATE1_TEXT = "测试组"; // 测试组
	public final static String VEHICLE_STATE2_TEXT = "已测组"; // 已测组

	// 机械巡检，指令相关的session
	public final static String WATCH_VEHICLE_POLLING = "watchVehiclePolling"; // 监控车辆
	public final static String COMMAND_SN_POLLING = "commandSnPolling"; // 指令SN
	public final static String COMMAND_SN_UPGRADE_POLLING = "commandSnUpgradePolling";// 指令SN,存放空中升级的sn
	public final static String GPSINFO_SN_POLLING = "gpsInfoSnPolling"; // 位置的key
	public final static String WORKINFO_SN_POLLING = "workInfoSnPolling"; // 工况的key
	public final static String UNITBACK_SN_POLLING = "unitBackSnPolling"; // 一般指令终端回应的key

	public final static Integer IS_LOGIN_YES = 0; // 在线
	public final static Integer IS_LOGIN_NO = 1; // 离线

	public final static String RESPONSE_GATEWAY = "网关回应"; // 网关回应
	public final static String RESPONSE_UNIT = "终端回应"; // 终端回应

	public final static String COMMAND_TYPE16_NAME = "空中升级"; // 空中升级

	// 机械测试
	public final static String COMMAND_SN_TEST = "commandSnTest"; // 指令SN
}
