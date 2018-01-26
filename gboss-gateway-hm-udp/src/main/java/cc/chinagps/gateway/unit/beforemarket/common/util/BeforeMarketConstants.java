package cc.chinagps.gateway.unit.beforemarket.common.util;

public class BeforeMarketConstants {
	public static final String BM_SESSION_KEY_ENCRYPT_C1 = "bm_encrypt_c1";
	public static final String BM_SESSION_KEY_ENCRYPT_D1 = "bm_encrypt_d1";
	
	//文件升级,缓存请求的文件信息
	public static final String BM_SESSION_KEY_REQUEST_FILE_INFO = "bm_request_file_info";
	
	/*
	 * 缓存海马OTA升级文件信息
	 */
	public static final String HM_OTA_SESSION_KEY_REQUEST_FILE_INFO = "hm_ota_request_file_info";
	
	/**
	 * 缓存Canid
	 */
	public static final String HM_OTA_SESSION_CAN_ID_KEY = "hm_ota_session_can_id_key";

	
	public static final String BM_SESSION_KEY_SN = "bm_sn";
	
	//协议中的终端类型 1:海马  2:一动网  3:凯翼
	public static final byte PROTOCOL_TERMINAL_TYPE_HM = 1;
	
	public static final byte PROTOCOL_TERMINAL_TYPE_YDW = 2;
	
	public static final byte PROTOCOL_TERMINAL_TYPE_KAIYI = 3;
	
	//协议中的版本
	public static final byte PROTOCOL_UNIT_VERSION = 0x10;
}