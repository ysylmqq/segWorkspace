package cc.chinagps.gateway.web.util;

/**
 * 运行结果代码(注意与前台js相对应)
 */
public interface ErrorCode {
	//成功
	public static final int SUCCESS = 1;
	
	//失败
	public static final int FAIL = 0;
	
	//用户没有登录,或Session失效
	public static final int SESSION_EXPIRED = -3;
	
	//登陆验证码错误
	public static final int LOGIN_CODE_WRONG = -8;
	
	//用户不存在
	public static final int LOGIN_USER_NOT_EXIST = -10;
	
	//登陆密码错误
	public static final int LOGIN_PASSWORD_WRONG = -11;
	
	//其他错误
	public static final int OTHER_ERROR = -10009;
}