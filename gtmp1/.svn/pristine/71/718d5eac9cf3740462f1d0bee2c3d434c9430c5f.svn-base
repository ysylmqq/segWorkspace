package com.chinaGPS.gtmp.action.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.chinaGPS.gtmp.entity.ModulePOJO;
import com.chinaGPS.gtmp.entity.RolePOJO;
import com.chinaGPS.gtmp.entity.SystemParamPOJO;
import com.chinaGPS.gtmp.entity.UserPOJO;
import com.chinaGPS.gtmp.service.IOwnerUserService;
import com.chinaGPS.gtmp.service.ISystemParamService;
import com.chinaGPS.gtmp.service.IUserService;
import com.chinaGPS.gtmp.util.Constants;
import com.chinaGPS.gtmp.util.MD5Enctype;
import com.chinaGPS.gtmp.util.OperationLog;
import com.chinaGPS.gtmp.util.StringUtils;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Package:com.chinaGPS.gtmp.action.common
 * @ClassName:LoginAction
 * @Description:登录控制器
 * @author:zfy
 * @date:2012-12-17 下午04:02:40
 */

@Scope("prototype")
@Controller
public class LoginAction extends BaseAction implements ModelDriven<UserPOJO> {
	private static final long serialVersionUID = -2221033898722030366L;
	private static Logger logger = LoggerFactory.getLogger(LoginAction.class);

	/** 用户Service */
	@Resource
	private IUserService userService;
	
	@Resource
	private IOwnerUserService ownerUserService;

	@Resource
	private ISystemParamService systemParamService;

	/** 用户信息 */
	@Resource
	private UserPOJO userPOJO;

	/** 验证码 */
	private String checkCode;

	/**
	 * @Title:login
	 * @Description: 用户登录系统
	 * @return
	 * @throws
	 */
	@OperationLog(description = "用户登录")
	public String login() {
		if (userPOJO == null) {
			return LOGIN;
		}
		
		if (StringUtils.isBlank(userPOJO.getLoginName()) || StringUtils.isBlank(userPOJO.getPassword()))
			return LOGIN;
		
		boolean flag = true;
		String msg = "";
		String firstPageURL = "index.jsp";

		try {
			if (StringUtils.isNotBlank(checkCode)) {
				if (!checkCode.equalsIgnoreCase(String.valueOf(getSession().get("checkCode")))) {
					msg = "验证码错误!";
					flag = false;
				} else {
					Pattern p = Pattern.compile("^[1][\\d]{10}");
					Matcher m = p.matcher(userPOJO.getLoginName());
					if (m.matches()) {
						UserPOJO ownerUsr = ownerUserService.getUserByLoginNamePwd(userPOJO);
						if (ownerUsr != null) {
							if (MD5Enctype.createPassword(userPOJO.getPassword()).equals(ownerUsr.getPassword())) {
								getSession().put(Constants.USER_ID, ownerUsr.getUserId());
								getSession().put(Constants.USER_NAME, ownerUsr.getUserName());
								getSession().put(Constants.USER_INFO, ownerUsr);
								getSession().put(Constants.USER_TYPE, 1);// 用户类别：0-玉柴用户 1-机主用户
								getSession().put(Constants.REMOTE_IP, getIpAddr(getRequest()));
								
								List<RolePOJO> roleList = ownerUsr.getRoles();
								if (roleList != null && roleList.size() > 0) {
									for (RolePOJO role : roleList) {
										if (role == null || role.getRoleId() == null) {
											flag = false;
											msg = "您暂无访问权限，请联系系统管理员!";
											HashMap<String, Object> map = new HashMap<String, Object>();
											map.put("success", flag);
											map.put("msg", msg);
											renderObject(map);
											return NONE;
										}
									}
									// 可访问的模块
									List<ModulePOJO> moduleList = userService.getAccessibleModues(roleList);
	
									if (moduleList == null || moduleList.isEmpty()) {
										flag = false;
										msg = "您暂无访问权限，请联系系统管理员!";
									} else {
										getSession().put(Constants.MODULES, moduleList);
										getSession().put(Constants.VEHICLE_STATUS, getVehicleStatus(moduleList));
										// 首页:如果是多角色，则取第一个角色的首页
										for (ModulePOJO module : moduleList) {
											if (StringUtils.isNotBlank(module.getControl3())) {
												firstPageURL = module.getControl3();
												break;
											}
										}
	
										getSession().put(Constants.FIRST_PAGE, firstPageURL);
	
										// 系统参数
										SystemParamPOJO systemParamPOJO = new SystemParamPOJO();
										List<SystemParamPOJO> systemParamList = systemParamService.getList(systemParamPOJO);
										getSession().put(Constants.SYSTEM_PARAMS, systemParamList);
	
										flag = true;
										msg = "登录系统";
									}
								}
							} else {
								flag = false;
								msg = "用户登录不成功！请核对密码！";
							}
						} else {
							flag = false;
							msg = "用户登录不成功！请核对用户名！";
						}
					} else {
						// 内部用户BEGIN
						UserPOJO userPOJO2 = userService.getUserByLoginNamePwd(userPOJO);
						if (userPOJO2 != null) {
							if (MD5Enctype.createPassword(userPOJO.getPassword()).equals(userPOJO2.getPassword())) { // 验证密码
								userPOJO2.getDepartInfo().setDealerId(userPOJO2.getDepartInfo().getDepartId());
								getSession().put(Constants.USER_ID, userPOJO2.getUserId());// 用户id
								getSession().put(Constants.USER_NAME, userPOJO2.getUserName());// 用户名称
								getSession().put(Constants.USER_TYPE, 0); // 用户类别：0-玉柴用户 1-机主用户
								getSession().put(Constants.USER_INFO, userPOJO2);// 用户信息:包括用户机构和角色的信息
								getSession().put(Constants.REMOTE_IP, getIpAddr(getRequest()));
	
								List<RolePOJO> roleList = userPOJO2.getRoles();
								if (roleList != null && roleList.size() > 0) {
									for (RolePOJO role : roleList) {
										if (role == null || role.getRoleId() == null) {
											flag = false;
											msg = "您暂无访问权限，请联系系统管理员!";
											HashMap<String, Object> map = new HashMap<String, Object>();
											map.put("success", flag);
											map.put("msg", msg);
											renderObject(map);
											return NONE;
										}
									}
									// 可访问的模块
									List<ModulePOJO> moduleList = userService.getAccessibleModues(roleList);
	
									if (moduleList == null || moduleList.isEmpty()) {
										flag = false;
										msg = "您暂无访问权限，请联系系统管理员!";
									} else {
										getSession().put(Constants.MODULES, moduleList);
										getSession().put(Constants.VEHICLE_STATUS, getVehicleStatus(moduleList));
										/*// 首页:如果是多角色，则取第一个角色的首页
										boolean hasCommand = false;
										boolean hasVehicleMain = false;
										for (ModulePOJO module : moduleList) {
											if (StringUtils.isNotBlank(module.getControl3())) {
												if(module.getModuleType() == 2){
													hasCommand = true;
												}
												if(module.getModuleName().equals("运营监管")){
													hasVehicleMain = true;
												}
											}
										}
										if(hasVehicleMain){
											firstPageURL = "jsp/run/run_main.jsp";
										}else
	                                    if(hasCommand && !hasVehicleMain){
	                                    	firstPageURL = "jsp/vehicle/vehicle_main.jsp";
	                                    }else{
	                                    	for (ModulePOJO module : moduleList) {
	                                    		firstPageURL = module.getControl3();
	                                    		break;
	                                    	}
	                                    }*/
										firstPageURL = userPOJO2.getDepartInfo().getFirstPageURL();
										getSession().put(Constants.FIRST_PAGE, firstPageURL);
	
										// 系统参数
										SystemParamPOJO systemParamPOJO = new SystemParamPOJO();
										List<SystemParamPOJO> systemParamList = systemParamService.getList(systemParamPOJO);
										getSession().put(Constants.SYSTEM_PARAMS, systemParamList);
	
										flag = true;
										msg = "登录系统";
										// logger("登录");//写入操作日志
									}
								} else {
									flag = false;
									msg = "您暂无访问权限，请联系系统管理员!";
								}
							} else {
								flag = false;
								msg = "用户登录不成功！请核对密码！";
							}
	
						} else {
							flag = false;
							msg = "用户登录不成功！请核对用户名！";
						}
						// 内部用户END
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("success", flag);
		map.put("msg", msg);
		renderObject(map);
		return NONE;
	}

	/**
	 * 得到数据权限,目前只有机械状态
	 * 
	 * @param moduleList
	 * @return
	 */
	public List<Integer> getVehicleStatus(List<ModulePOJO> moduleList) {
		List<Integer> statusList = new ArrayList<Integer>();
		ModulePOJO module = null;
		for (int i = 0; i < moduleList.size(); i++) {
			module = moduleList.get(i);
			if (module.getModuleType() != null && module.getModuleType().intValue() == Constants.MODULE_TYPE_DATA.intValue()) {
				if (StringUtils.isNotBlank(module.getUrl())) {
					statusList.add(Integer.parseInt(module.getUrl()));
				}
			}
		}
		return statusList;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	@Override
	public UserPOJO getModel() {
		return userPOJO;
	}

}