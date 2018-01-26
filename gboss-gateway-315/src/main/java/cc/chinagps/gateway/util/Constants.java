package cc.chinagps.gateway.util;

public class Constants {
	public static final byte[] ZERO_BYTES_DATA = new byte[0];
	
	public static final String CHAR_ENCODING_ASCII = "ascii";
	
	public static final String VERSION = "v1.5.0(00006y)_20150928";		//版本
	
	public static final short INNER_CMD_ID_CMD = 111;					//下发指令
	
	public static final short INNER_CMD_ID_RESPONSE = 112;				//指令回应
	
	public static final short INNER_CMD_ID_LOAD_VEHICLE = 113;			//加载车辆列表
	
	public static final short INNER_CMD_ID_LOAD_VEHICLE_ACK = 114;		//加载车辆列表回应
	
	public static final short INNER_CMD_ID_VEHICLE_ONLINE_NOTIFY = 115;	//车辆上线通知
	
	public static final short INNER_CMD_ID_VEHICLE_OFFLINE_NOTIFY = 116;//车辆离线通知
	
	public static final short INNER_CMD_ID_SHOW_STATUS = 117;			//显示状态
	
	public static final short INNER_CMD_ID_SHOW_STATUS_ACK = 118;		//显示状态回应
	
	public static final short INNER_CMD_ID_ADD_TRACE = 119;				//添加Trace
	
	public static final short INNER_CMD_ID_ADD_TRACE_ACK = 120;			//添加Trace回应
	
	public static final short INNER_CMD_ID_SEND_TRACE = 121;			//发送Trace数据
	
	public static final short INNER_CMD_ID_REMOVE_TRACE = 122;			//移除Trace
	
	public static final short INNER_CMD_ID_REMOVE_TRACE_ACK = 123;		//移除Trace回应
	
	/**
	 * 指令回应
	 */
	public static final int RESULT_SUCCESS = 0;				//成功
	public static final int RESULT_FAIL_OTHERS = -1;		//失败
	public static final int RESULT_UNIT_OFFLINE = 10;		//车台不在线
	public static final int RESULT_SEND_DATA_FAIL = 17;		//发送数据失败
	public static final int RESULT_WRONG_FORMAT = 16;		//数据格式错误
	public static final int RESULT_TIMEOUT = 18;			//等待车台回应超时
	public static final int RESULT_UNIT_ACK_FAIL = 6;		//车台主动回复失败
	public static final int RESULT_UNIT_EXE_FAIL = 32;		//车台执行失败
	
	//seat session
	public static final String SESSION_KEY_RECEIVE_UNIT_LOGIN_CHANGE = "unit_login_change";
	
	public static final String SESSION_KEY_TRACE_UNIT = "trace_unit";
	
	//系统编号
	public static final String SYSTEM_ID;
	public static final int SYSTEM_ID_INT;
	public static final boolean IS_UPDATE_SERVER;
	
	static{
		SYSTEM_ID = SystemConfig.getSystemProperty("system_id");
		SYSTEM_ID_INT = Integer.valueOf(SYSTEM_ID);
		
		IS_UPDATE_SERVER = Boolean.valueOf(SystemConfig.getSystemProperty("is_update_server"));
	}
}