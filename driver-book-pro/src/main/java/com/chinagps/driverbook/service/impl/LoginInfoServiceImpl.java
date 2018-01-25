package com.chinagps.driverbook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.chinagps.driverbook.dao.LoginInfoMapper;
import com.chinagps.driverbook.pojo.LoginInfo;
import com.chinagps.driverbook.service.ILoginInfoService;

/**
 * 登录日志Service
 * @author Ben
 *
 */
@Service
@Scope("prototype")
public class LoginInfoServiceImpl extends BaseServiceImpl<LoginInfo> implements ILoginInfoService {
	@Autowired
	private LoginInfoMapper loginInfoMapper;
	
}
