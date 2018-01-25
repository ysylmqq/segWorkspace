package com.org.netty.myselfcode.modal;

public class Constant {

	/**
	 * 数据包的头标示
	 */
	public static int HEAD = 0x01;
	
	/**
	 * 登录CMD
	 */
	public static short LOGINCMD = 0x01;
	
	/**
	 * 登录回应CMD
	 */
	public static short LOGINACKCMD = 0x02;
	
	/**
	 * 查车CMD
	 */
	public static short QUERYCMD = 0x03;
	
	/**
	 * 查车ACKCMD
	 */
	public static short QUERYACKCMD = 0x04;
	
	/**
	 * 关闭省流量CMD
	 */
	public static short CLOSECMD = 0x05;
	
	/**
	 * 关闭省流量ACKCMD
	 */
	public static short CLOSEACKCMD = 0x06;
	
}
