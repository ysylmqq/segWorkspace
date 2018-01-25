package com.org.netty.pkg;


import com.google.protobuf.InvalidProtocolBufferException;

public class MainTest {

	/**
	 * 编码
	 * @param req
	 * @return
	 */
	public static byte[] encode(UserDataBuff.UserInfo req){
		return req.toByteArray();
	}
	
	/**
	 * 解码
	 * @param body
	 * @return
	 * @throws InvalidProtocolBufferException
	 */
	public static  UserDataBuff.UserInfo decode(byte [] body) throws InvalidProtocolBufferException{
		return UserDataBuff.UserInfo.parseFrom(body);
	}
	
	public static UserDataBuff.UserInfo createUserInfo(){
		UserDataBuff.UserInfo.Builder builder = UserDataBuff.UserInfo.newBuilder();
		builder.setId(1);
		builder.setDesc("你好");
		builder.setRespCode(100);
		return builder.build();
	}
	
	public static void main(String[] args) throws InvalidProtocolBufferException {
		UserDataBuff.UserInfo userInfo = MainTest.createUserInfo();
		System.err.println("encode before "+userInfo.toString()+"  "+userInfo.getDesc());
		byte [] encode = MainTest.encode(userInfo);
		System.err.println("encode length "+encode.length);
		
		UserDataBuff.UserInfo userInfo2 = MainTest.decode(encode);
		System.err.println("decode "+userInfo2.toString());
		System.err.println(" equal "+userInfo2.equals(userInfo));
	}
}
