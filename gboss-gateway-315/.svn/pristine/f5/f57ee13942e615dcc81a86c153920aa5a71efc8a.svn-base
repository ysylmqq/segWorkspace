var ErrorCode = {
	//用户没有登录,或Session失效
	SESSION_EXPIRED : -3,
	
	//登陆验证码错误
	LOGIN_CODE_WRONG : -8,
	
	//用户不存在
	LOGIN_USER_NOT_EXIST : -10,
	
	//登陆密码错误
	LOGIN_PASSWORD_WRONG : -11,
	
	//其他错误
	OTHER_ERROR : -10008
};

function getReasonInfo(code){
	switch(code){
		case ErrorCode.SESSION_EXPIRED:
			return "会话已失效";
		case ErrorCode.LOGIN_CODE_WRONG:
			return "验证码错误";
		case ErrorCode.LOGIN_USER_NOT_EXIST:
			return "用户不存在";
		case ErrorCode.LOGIN_PASSWORD_WRONG:
			return "用户名或密码错误";
		case ErrorCode.MEMBER_SYSTEM_OTHER_ERROR:
			return "其他错误";
		default:
			return "";
	}
}