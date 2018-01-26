package com.chinaGPS.gtmp.action.common;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.chinaGPS.gtmp.util.Constants;
import com.chinaGPS.gtmp.util.OperationLog;

/**
 * 登出action
 * @author zfy
 *
 */
@Scope("prototype")
@Controller 
public class LoginOutAction extends BaseAction{
	private static final long serialVersionUID = -6577258565250706674L;

	/**
	 * 用户登出系统
	 * @return 登录界面
	 */
	@OperationLog(description = "用户退录")
	public String loginOut(){
		getSession().remove(Constants.USER_INFO);
		getSession().remove(Constants.MODULES);
		getSession().remove(Constants.REMOTE_IP);
		getSession().remove(Constants.USER_ID);
		getSession().remove(Constants.USER_NAME);
		renderMsgJson(true,"用户退出成功!");
		return NONE;
	}
}