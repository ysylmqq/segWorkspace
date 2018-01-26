/*
********************************************************************************************
Discription:  内部协议常量
			  
Written By:   Arvin
Date:         2011-12-7
Version:      1.0

Modified by:  ZXZ
Modified Date:2014-04-22
Version:
********************************************************************************************
*/
package cc.chinagps.interprotocol;

/**
 * 常量
 */
public class Constant {
	/**
	 * 消息的stx
	 */
	public static final byte STX = (byte) 0xfe;
	/**
	 * 登录的消息Id
	 */
	public static final int MESSAGE_ID_LOGIN = 0x1;
	
	/**
	 * 登录回应的消息Id
	 */
	public static final int MESSAGE_ID_LOGIN_ACK = 0x80000001;
	
	/**
	 * 退录的消息Id
	 */
	public static final int MESSAGE_ID_LOGOUT = 0x2;
	
	/**
	 * 退录回应的消息Id
	 */
	public static final int MESSAGE_ID_LOGOUT_ACK = 0x80000002;
	
	/**
	 * 心跳的消息Id
	 */
	public static final int MESSAGE_ID_ACTIVETEST = 0x3;
	
	/**
	 * 心跳回应的消息Id
	 */
	public static final int MESSAGE_ID_ACTIVETEST_ACK = 0x80000003;
	
	/**
	 * 最大包长度
	 */
	public static final int MAX_PACKAGE_LENGTH = 16777216;

	/*
	 * 通信中心报文协议(内容为PROTOBUF格式)
	 */
	public static final int MESSAGE_ID_COMCENTER_PROTOBUF = 0x00000010;

}