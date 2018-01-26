package cc.chinagps.gateway.seat.cmd;

public class CmdUtil {
	// 查车
	public static final int CMD_ID_POSITION = 0x0001;
	// 跟踪
	public static final int CMD_ID_TRACE = 0x0015;
	// 停止跟踪
	public static final int CMD_ID_STOP_TRACE = 0x0016;
	// 开启定时报告
	public static final int CMD_ID_START_UPLOAD_BY_INTERVAL = 0x0038;
	// 关闭定时报告
	public static final int CMD_ID_STOP_UPLOAD_BY_INTERVAL = 0x0039;
	
	//开启点火定时报告
	//public static final int CMD_ID_START_ACC_ON_DELIVERY = 0x0040;
	//关闭点火定时报告
	//public static final int CMD_ID_STOP_ACC_ON_DELIVERY = 0x0041;
	
	//开启熄火定时报告
	public static final int CMD_ID_START_ACC_OFF_DELIVERY = 0x0040;
	//关闭熄火定时报告
	public static final int CMD_ID_STOP_ACC_OFF_DELIVERY = 0x0041;
	
	//开启定距上传
	public static final int CMD_ID_START_UPLOAD_BY_DISTANCE = 0x0044;
	//关闭定距上传
	public static final int CMD_ID_STOP_UPLOAD_BY_DISTANCE = 0x0045;
	// 断油电
	public static final int CMD_ID_CUT_OFF_OIL = 0x0006;
	// 恢复油电
	public static final int CMD_ID_RESUME_OIL = 0x0007;
	// 终端复位
	public static final int CMD_ID_RESET = 0x0002;
	// 终端重启
	public static final int CMD_ID_RESTART = 0x0075;
	// 设置心跳间隔
	public static final int CMD_ID_SET_HEARTBEAT_INTERVAL = 0x0076;
	// 设置终端休眠定时唤醒时间间隔
	public static final int CMD_ID_SET_SLEEP_WAKEUP_INTERVAL = 0x0077;
	// 设置总里程油耗
	public static final int CMD_ID_SET_TOTAL_DISTANCE_OIL = 0x0078;
	// 设置GPS回传参数
	public static final int CMD_ID_SET_GPS_DELIVER_PARAM = 0x0079;
	// 设置GPS拐点补传参数
	public static final int CMD_ID_SET_INFLECTION_PARAM = 0x007A;
	// 设置超速报警参数
	public static final int CMD_ID_SET_OVERSPEED_PARAM = 0x007B;
	// 设置OBD适配参数
	public static final int CMD_ID_SET_OBD_ADAPT_PARAM = 0x007C;
	// 查询OBD适配参数
	public static final int CMD_ID_QUERY_OBD_ADAPT_PARAM = 0x007D;
	// 禁止电话打入
	public static final int CMD_ID_FORBID_TEL_IN = 0x000A;
	// 禁止电话打出
	public static final int CMD_ID_FORBID_TEL_OUT = 0x000B;
	// 集群通话
	public static final int CMD_ID_GROUP_TEL = 0x001A;
	// 恢复电话功能
	public static final int CMD_ID_RESUME_TEL = 0x000C;
	// 监听
	public static final int CMD_ID_MONITOR = 0x0017;
	// 设置监听号码
	public static final int CMD_ID_SET_MONITOR_TEL = 0x0019;
	// 限速
	public static final int CMD_ID_LIMIT_SPEED = 0x001D;
	// 取消限速
	public static final int CMD_ID_CANCEL_LIMIT_SPEED = 0x001E;
	// 下发短信
	public static final int CMD_ID_SEND_SM = 0x0035;
	// 上传行车记录
	public static final int CMD_ID_UPLOAD_HISTORY = 0x0012;
	// 设置TCP/UDP通信参数
	public static final int CMD_ID_SET_TCP_UDP_PARAMS = 0x0057;
	// 查询TCP/UDP通信参数
	public static final int CMD_ID_QUERY_TCP_UDP_PARAMS = 0x0058;
	// 设置APN
	public static final int CMD_ID_SET_APN = 0x0051;
	// 设置TCP/UDP + APN通信参数
	public static final int CMD_ID_SET_TCP_UDP_APN_PARAMS = 0xF052;
	// 更改服务电话
	public static final int CMD_ID_SET_SERRVICE_TEL = 0x0025;
	// 设置短消息服务中心号码
	public static final int CMD_ID_SET_SM_CENTER_NUM = 0x0026;
	// 设置短信呼入特服号
	public static final int CMD_ID_SET_SM_IN_NUM = 0x002F;
	// 设置短信呼出特服号
	public static final int CMD_ID_SET_SM_OUT_NUM = 0xF056;
	// 设置GPRS重连时间间隔
	public static final int CMD_ID_SET_RECONNECT_INTERVAL = 0x005B;
	// (TN)设置导航仪目的查询点
	public static final int CMD_ID_SET_TARGET_QUERY_POINT = 0x0292;
	// 锁车门
	public static final int CMD_ID_LOCK_DOOR = 0x0004;
	// 开车门
	public static final int CMD_ID_OPEN_DOOR = 0x0005;
	
	// 找车
	public static final int CMD_ID_SEARCH_VEHICLE = 0x0013;
	// 关车窗
	public static final int CMD_ID_CLOSE_WINDOW = 0x000D;
	// 开车窗
	public static final int CMD_ID_OPEN_WINDOW = 0x000E;
	// 读取车型
	public static final int CMD_ID_READ_DEFINED_TYPE = 0x00A0;
	// 标定车型
	public static final int CMD_ID_DEFINED_TYPE = 0x00A1;
	// 清除故障码
	public static final int CMD_ID_CLEAR_FAULT_CODE = 0x00A2;
	// 透传指令
	public static final int CMD_ID_PASS_THROUGH = 0xFFFF;
	
	//海马增加
	//设置故障/救援服务号码
	public static final int CMD_ID_SET_HELP_TEL = 0x00A6;
	//查询故障/救援服务号码
	public static final int CMD_ID_QUERY_HELP_TEL = 0x00A7;
	
	
	public static final int CMD_ID_UNIT_UPDATE_FTP = 0x00B8;
	
	//FTP升级（多文件）
	public static final int CMD_ID_UPGRADE_FTP_MULTIFILE = 0x00B9;
	
	//设置故障上报
	public static final int CMD_ID_SET_UPLOAD_FAULT = 0x00AA;
	//设置故障不上报
	public static final int CMD_ID_SET_NOT_UPLOAD_FAULT = 0x00AB;
	//查询故障是否上报
	public static final int CMD_ID_QUERY_UPLOAD_FAULT_OR_NOT = 0x00AC;
	
	//开启点火熄火报告
	public static final int CMD_ID_START_UPLOAD_WHEN_ACC_ON_OFF = 0x0042;
	//关闭点火熄火报告
	public static final int CMD_ID_STOP_UPLOAD_WHEN_ACC_ON_OFF = 0x0043;
	
	//开启车身状态变化报告
	public static final int CMD_ID_START_UPLOAD_WHEN_VEHICLE_STATUS_CHANGED = 0x0048;
	//关闭车身状态变化报告
	public static final int CMD_ID_STOP_UPLOAD_WHEN_VEHICLE_STATUS_CHANGED = 0x0049;
	
	//开启关机报告
	public static final int CMD_ID_START_UPLOAD_WHEN_POWER_OFF = 0x004A;
	//关闭关机报告
	public static final int CMD_ID_STOP_UPLOAD_WHEN_POWER_OFF = 0x004B;
	
	//开启休眠报告
	public static final int CMD_ID_START_UPLOAD_WHEN_SLEEP = 0x004C;
	//关闭休眠报告
	public static final int CMD_ID_STOP_UPLOAD_WHEN_SLEEP = 0x004D;
	//设置电控单元配置
	public static final int CMD_ID_SET_ECU_CONFIG = 0x00B3;
	
	//查询电控单元配置
	public static final int CMD_ID_QUERY_ECU_CONFIG = 0x00B4;
	
	//新增查询
	//呼叫中心
	public static final int CMD_ID_QUERY_CALL_CENTER = 0x0027;
	//短信业务中心号码
	public static final int CMD_ID_QUERY_SM_CENTER = 0x0030;
	//定时上报参数
	public static final int CMD_ID_QUERY_UPLOAD_PARAM = 0x0070;
	//ACC变化上报参数
	public static final int CMD_ID_QUERY_ACC_CHANFE_PARAM = 0x0071;
	//休眼上报参数
	public static final int CMD_ID_QUERY_SLEEP_PARAM = 0x0072;
	//关机上报参数
	public static final int CMD_ID_QUERY_POWEROFF_PARAM = 0x0073;
	//车身状态变化上报参数
	public static final int CMD_ID_QUERY_CAR_BODY_CHANGE_PARAM = 0x0074;
	//OBD故障
	public static final int CMD_ID_QUERY_FAULT = 0x00A3;
	//查询呼入呼出限制
	public static final int CMD_ID_QUERY_CALL_LIMIT = 0x00A4;
	//通知终端升级
	public static final int CMD_ID_UPDATE_UNIT = 0x00A5;
	//查询升级状态
	public static final int CMD_ID_QUERY_UPDATE_STATUS = 0x00B2;
	//新增查询end
	
	//透传给硬件
	public static final int CMD_ID_TO_HARDWARE = 0xFF;
	
	//TBOX控制指令
	//关灯
	public static final int CMD_ID_BOX_CLOSE_LIGHT = 0x0062;
	//开灯
	public static final int CMD_ID_BOX_OPEN_LIGHT = 0x0061;
	//开后尾箱
	public static final int CMD_ID_BOX_OEPN_TAIL_DOOR = 0x0063;
	//关空调
	public static final int CMD_ID_BOX_CLOSE_AIR_CONDITIONING = 0x0066;
	//开空调
	public static final int CMD_ID_BOX_OEPN_AIR_CONDITIONING = 0x0065;
	//设置空调
	//public static final int CMD_ID_BOX_SET_AIR_CONDITIONING = 0x0064;
	
	//开启发动机
	public static final int CMD_ID_BOX_START_ENGINE = 0x0069;
	//关闭发动机
	public static final int CMD_ID_BOX_STOP_ENGINE = 0x006A;
	//TBOX控制指令end
	
	//打开维修模式
	public static final int CMD_ID_OPEN_REPAIR_MODE = 0x0067;
	//关闭维修模式
	public static final int CMD_ID_CLOSE_REPAIR_MODE = 0x0068;
	
	//一动网增加指令
	//设置用户密码
	public static final int CMD_ID_SET_USER_PASSWORD = 0x00AE;
	//设置报警手机号码
	public static final int CMD_ID_SET_ALARM_TEL = 0x00B0;
	//设置一动网报警参数
	public static final int CMD_ID_SET_ALARM_PARAM = 0x0300;
	
	//查询超速参数
	public static final int CMD_ID_QUERY_LIMIT_SPEED_PARAM = 0x00AD;
	//查询用户密码
	public static final int CMD_ID_QUERY_USER_PASSWORD = 0x00AF;
	//查询报警手机号码
	public static final int CMD_ID_QUERY_ALARM_TEL = 0x00B1;
	//查询一动网报警参数
	public static final int CMD_ID_QUERY_ALARM_PARAM = 0x0301;
	
	// wifi认证
	public static final int CMD_ID_WIFI_AUTH = 0x0100;
	
	//查询子机编号
	public static final int CMD_ID_QUERY_TRG = 0x00E1;
	
	//删除子机编号
	public static final int CMD_ID_DEL_TRG = 0x00E2;
	
	//设置蓝牙断开时子机上传间隔
	public static final int CMD_ID_SET_TRG_INTERVAL = 0x00E3;
	
	//打开基站数据上报 
	public static final int CMD_ID_OPEN_BASESTATION_UPLOAD = 0x004E;
	
	//关闭基站数据上报 
	public static final int CMD_ID_CLOSE_BASESTATION_UPLOAD = 0x004F;
	
	//读取终端（TBOX）信息
	public static final int CMD_ID_QUERY_TBOX_INFO = 0x00B5;
	
	//设置侧翻校正参数
	public static final int CMD_ID_SET_ROLL_PARAM = 0x00B6;
	
	//查询侧翻校正参数
	public static final int CMD_ID_QUERY_ROLL_PARAM = 0x00B7;
	
	//查询空调设置参数
	public static final int CMD_ID_QUERY_AIR_CONDITIONING_PRARM = 0x0064;
	
	//北汽下发订单
	public static final int CMD_ID_DELIVER_ORDER = 0x0311;
	
	//北汽结束订单
	public static final int CMD_ID_FINISH_ORDER = 0x0312;
	
	//北汽远程控制车辆
	public static final int CMD_ID_REMOTE_CONTROL_VEHICLE = 0x0313;
	
	public static final int CMD_ID_PICK_UP_CAR = 0x0314;
	
	public static final int CMD_ID_RETURN_CAR = 0x0315;
	
	//允许音响主机连接3G模块
	public static final int CMD_ID_OPEN_SOUND_CONNECT_3G = 0x00F0;
	
	//禁止音响主机连接3G模块
	public static final int CMD_ID_CLOSE_SOUND_CONNECT_3G = 0x00F1;
	
	//查询音响主机连接3G模块状态
	public static final int CMD_ID_QUERY_SOUND_CONNECT_3G = 0x00F2;
	
	
	public static final int CMD_ID_OPEN_TBOX_SAVE_TRAFFIC = 0x00D3;
	
	
	public static final int CMD_ID_CLOSE_TBOX_SAVE_TRAFFIC = 0x00D4;
	
	//打开前雨刷
	public static final int CMD_ID_OPEN_FRONT_WINDSHIELD_WIPER = 0x00F3;
	
	//关闭前雨刷
	public static final int CMD_ID_CLOSE_FRONT_WINDSHIELD_WIPER = 0x00F4;
	
	//打开后雨刷
	public static final int CMD_ID_OPEN_BACK_WINDSHIELD_WIPER = 0x00F5;
		
	//关闭后雨刷
	public static final int CMD_ID_CLOSE_BACK_WINDSHIELD_WIPER = 0x00F6;
	
	//展开后视镜
	public static final int CMD_ID_EXPAND_BACK_MIRROR = 0x00F7;
	
	//收缩后视镜
	public static final int CMD_ID_SHRINK_BACK_MIRROR = 0x00F8;
	
	/**
	 * 整车远程升级 
	 */
	public static final int CMD_ID_ENTIRE_CAR_OTA_UPGRADE = 0x00D5;
	
	/**
	 *  网关向终端查询升级状态
	 */
	public static final int CMD_ID_ENTIRE_CAR_OTA_UPGRADE_STATUS = 0x00D6;
	
	/**====================长丰start==========================*/
	//查询指令
	public static final int CMD_ID_LEOPAARD_QUERY_CMD = 0x0320;
		
	//设置指令
	public static final int CMD_ID_LEOPAARD_SET_CMD = 0x0321;
	
	//开天窗
	public static final int CMD_ID_LEOPAARD_OPEN_SUNROOF = 0x0322;
	
	//关天窗
	public static final int CMD_ID_LEOPAARD_CLOSE_SUNROOF = 0x0323;
	
	//开窗
	public static final int CMD_ID_LEOPAARD_OPEN_WINDOW = 0x0324;
		
	//关窗
	public static final int CMD_ID_LEOPAARD_CLOSE_WINDOW = 0x0325;
	
	public static final int CMD_ID_LEOPAARD_HOT_AIRCONDITION = 0x0326;
	
	public static final int CMD_ID_LEOPAARD_COLD_AIRCONDITION = 0x0327;
	
	public static final int CMD_ID_LEOPAARD_FAULT_DIAGNOSIS = 0x0328;
	
	
	/**====================长丰end==========================*/
	/**
	 * 构建指令缓存sn (车台回应sn与下发sn不对应)
	 */
	public static String getCmdCacheSn(String call, int cmdId) {
		return call + "_" + cmdId;
	}
}