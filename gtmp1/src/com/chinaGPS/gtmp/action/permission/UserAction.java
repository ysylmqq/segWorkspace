package com.chinaGPS.gtmp.action.permission;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.chinaGPS.gtmp.action.common.BaseAction;
import com.chinaGPS.gtmp.entity.ModulePOJO;
import com.chinaGPS.gtmp.entity.RolePOJO;
import com.chinaGPS.gtmp.entity.TreeNode;
import com.chinaGPS.gtmp.entity.UserPOJO;
import com.chinaGPS.gtmp.entity.UserRolePOJO;
import com.chinaGPS.gtmp.service.IUserService;
import com.chinaGPS.gtmp.util.Constants;
import com.chinaGPS.gtmp.util.MD5Enctype;
import com.chinaGPS.gtmp.util.OperationLog;
import com.chinaGPS.gtmp.util.page.PageSelect;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 文 件 名 :UserAction.java
 * CopyRright (c) 2012:赛格导航
 * 文件编号：0000001
 * 创 建 人：周峰炎
 * 创 建 日 期：2012-12-7
 * 描 述： 用户管理控制器
 * 修 改 人：
 * 修 改 日 期：
 * 修 改 原 因:
 * 版 本 号：1.0
 */

@Scope("prototype")
@Controller 
public class UserAction extends BaseAction implements ModelDriven<UserPOJO> {
	private static final long serialVersionUID = 3429355749585165297L;
	private static Logger logger = LoggerFactory.getLogger(UserAction.class);

	/**
	 * 用户业务层
	 */
    @Resource
	private IUserService userService;
	
	/**
	 * 用户信息POJO
	 */
    @Resource
	private UserPOJO userPOJO;
	
	/**
	 * 用户和角色的绑定关系POJO
	 */
    @Resource
	private UserRolePOJO userRolePOJO;
	
	/**
	 * 用户拥有哪些角色,string数组
	 */
	private String[] roleIds;
	
	/** 用户类别 */
	private String userType;
	
	/**
	 * 用户拥有哪些角色,字符串
	 */
	private String roleIdsStr;
	
	/**
	 * 分页公共类
	 */
	@Resource
	private PageSelect pageSelect;
	
	private int page;
	private int rows;
	private String oldPassword;
	/**
	 * 
	　　* 函 数 名 :search
	　　* 功能描述：查询用户信息，返回分页数据
	　　* 输入参数:
	　　* @param 
	　　* @return String
	　　* @throws 无异常处理　　
	　　* 创 建 人:周峰炎
	　　* 日 期:2012-12-7
	　　* 修 改 人:
	　　* 修 改 日 期:
	　　* 修 改 原 因:
	 */
	public String search() {
		try {
			pageSelect.setPage(page);
			pageSelect.setRows(rows);
			renderObject(userService.getUsers(userPOJO, pageSelect));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return NONE;
	}
	
	/**
	 * 根据用户ID查询出用户信息
	 * @return
	 */
	public String getById() {
		try {
			userPOJO = userService.getById(userPOJO.getUserId());
			renderObject(userPOJO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return NONE;
	}
	
	/**
	 * 根据用户ID，删除用户信息
	 * @return
	 */
	@OperationLog(description = "用户删除")
	public String deleteUserById() {
		boolean flag = true;
		String msg = OP_SUCCESS;
		try {
			userService.deleteUserById(userPOJO.getUserId());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			flag = false;
			msg = OP_FAIL;
		}
		renderMsgJson(flag, msg);
		return NONE;
	}
	
	/**
	 * 保存用户信息，先进行排他处理
	 * @return
	 */
	@OperationLog(description = "用户保存")
	public String saveOrUpdate() {
		boolean flag = true;
		String msg = OP_SUCCESS;
		try {
			if (userService.checkLoginNameRepeat(userPOJO) == null) {
				if (StringUtils.isNotEmpty(userPOJO.getPassword())) {
					userPOJO.setPassword(MD5Enctype.createPassword(userPOJO.getPassword()));
				}
				/*
				 * if(userPOJO.getUserId()!=null){
				 * logger("用户修改(userName="+userPOJO.getUserName()+")");//写入操作日志
				 * }else{
				 * logger("用户新增(userName="+userPOJO.getUserName()+")");//写入操作日志 }
				 */
				userService.saveOrUpdate(userPOJO);
				flag = true;
				msg = OP_SUCCESS;
			} else {
				flag = false;
				msg = "该登录名已存在";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			flag = false;
			msg = OP_FAIL;
		}
		renderMsgJson(flag, msg);
		return NONE;
	}
	
	/**
	 * 更新用户信息
	 * @return
	 */
	@OperationLog(description = "用户更新")
	public String update() {
		boolean flag = true;
		String msg = OP_SUCCESS;
		try {
			if (StringUtils.isNotEmpty(userPOJO.getPassword())) {
				userPOJO.setPassword(MD5Enctype.createPassword(userPOJO.getPassword()));
			}
			// logger("用户修改(userId="+userPOJO.getUserId()+")");//写入操作日志
			userService.saveOrUpdate(userPOJO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			flag = false;
			msg = OP_FAIL;
		}
		renderMsgJson(flag, msg);
		return NONE;
	}
	
	/**
	 * 更新用户信息
	 * @return
	 */
	@OperationLog(description = "用户修改密码")
	public String updatePwd(){
		boolean flag=true;
		String msg=OP_SUCCESS;
		try {
			Long userId=getSession().get(Constants.USER_ID)==null?0:Long.valueOf(getSession().get(Constants.USER_ID).toString());
			UserPOJO userPOJO2=userService.getById(userId);
			//判断原密码是否正确
			if(MD5Enctype.createPassword(oldPassword).equals(userPOJO2.getPassword())){
			    userPOJO.setPassword(MD5Enctype.createPassword(userPOJO.getPassword()));
			    userService.saveOrUpdate((userPOJO));
			    
			   // logger("用户修改密码(userName="+userPOJO2.getUserName()+")");//写入操作日志
			  
			    //重新设置session中的值
			   if(getSession().get(Constants.USER_INFO)!=null){
				   UserPOJO  user=(UserPOJO)getSession().get(Constants.USER_INFO);
				   user.setPassword(user.getPassword());
				   getSession().put(Constants.USER_INFO,user);
			    }
			}else{
			    msg="原密码不正确!";
			    flag=false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			flag=false;
			msg=OP_FAIL;
		}
		renderMsgJson(flag, msg);
		return NONE;
	}
	
	/**
	 * 设置用户角色
	 * @return
	 */
	@OperationLog(description = "设置用户角色")
	public String setUserRoles() {
		boolean flag = true;
		String msg = OP_SUCCESS;
		try {
			// 批量插入
			List<UserRolePOJO> list = new ArrayList<UserRolePOJO>();
			UserRolePOJO userRolePOJO = null;
			if (roleIds != null) {
				for (int i = 0; i < roleIds.length; i++) {
					userRolePOJO = new UserRolePOJO(userPOJO.getUserId(), Long.valueOf(roleIds[i]), Integer.valueOf(userType));
					list.add(userRolePOJO);
				}
			}

			userService.setUserRoles(list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			flag = false;
			msg = OP_FAIL;
		}

		renderMsgJson(flag, msg);
		return NONE;
	}
	
	/**
	 * 得到用户有哪些角色
	 * @return
	 * @throws JSONException
	 */
	public void getUserRoles() {
		try {
			List<UserRolePOJO> userRolesList = userService.getUserRoles(userRolePOJO);
			StringBuffer sBuffer = new StringBuffer();// 用户所有的角色id拼成字符串
			for (int i = 0; i < userRolesList.size(); i++) {
				userRolePOJO = userRolesList.get(i);
				sBuffer.append(userRolePOJO.getRoleId()).append(",");
			}
			if (sBuffer.length() > 0) {
				if (sBuffer.lastIndexOf(",") == (sBuffer.length() - 1)) {
					// 去掉最后一个","
					roleIdsStr = sBuffer.deleteCharAt(sBuffer.lastIndexOf(",")).toString();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		Map map = new HashMap();
		map.put("data", roleIdsStr);
		renderObject(map);
	}
	
	/**
	 * @Title:getAccessibleModues
	 * @Description:可访问模块
	 * @throws
	 */
	public void getAccessibleModues() {
		List<ModulePOJO> moduleList = new ArrayList<ModulePOJO>();
		moduleList = (List<ModulePOJO>) getSession().get(Constants.MODULES);
		List<TreeNode> mapAreaList = new ArrayList<TreeNode>();
		Map mapRreaMap = new HashMap();
		if (moduleList != null) {
			for (ModulePOJO module1 : moduleList) {
				if (module1.getModuleType() == 1 && module1.getModuleId() != 0) {
					TreeNode node = new TreeNode();
					node.setId(module1.getModuleId().toString());
					node.setText(module1.getModuleName());
					if (module1.getParentId() != null) {
						node.setParentId(module1.getParentId().toString());
					}
					node.setAttributes(module1);
					mapRreaMap.put(module1.getModuleId(), node);
				}

			}
			for (ModulePOJO module2 : moduleList) {
				if (module2.getModuleType() == 1 && module2.getModuleId() != 0) {
					if (module2.getParentId() != 0) { // 有父节点
						TreeNode pnode = (TreeNode) mapRreaMap.get(module2.getParentId());
						TreeNode cnode = (TreeNode) mapRreaMap.get(module2.getModuleId());
						if (pnode != null && cnode != null) {
							pnode.addChild(cnode);
						}

					} else {// 无父节点
						mapAreaList.add((TreeNode) mapRreaMap.get(module2.getModuleId()));
					}
				}
			}
		}		
		renderObject(mapAreaList);
	}
	
	/**
	 * 判断是否是终端供应商
	 */
	public void isSupperier() {
		// 判断是否是终端供应商
		UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
		List<RolePOJO> roles = userPOJO.getRoles();
		boolean isSupperier = false;// 是否是经销商
		if (!roles.isEmpty()) {
			if (roles.size() == 1) {
				RolePOJO role = roles.get(0);
				if (role.getRoleId() == Constants.SUPPERIER_ROLE_ID) {// 终端供应商
					isSupperier = true;
				}
			}
		}
		renderMsgJson(isSupperier, "是否是终端供应商");
	}

	/**
	 * 用户信息导出
	 * 2015年12月18日16:00:47添加byHHF
	 */
	public String exportToExcel() throws UnsupportedEncodingException {
		try {
			
			List<Object[]> values = new ArrayList<Object[]>();
			Map map = new HashMap();
			pageSelect.setPage(1);
			pageSelect.setRows(Integer.MAX_VALUE);
		
			List<UserPOJO> resultList = null;
			
			HashMap<String,Object> result = userService.getUsers(userPOJO, pageSelect);
			if(result != null && result.containsKey("rows")){
				resultList = (List<UserPOJO>) result.get("rows");
			}
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (UserPOJO user : resultList) {				
				values.add(new Object[] { 
						user.getLoginName(),
						user.getUserName(),
						user.getSex(),
						user.getTel(), 
						user.getEmail(),
						user.getDepartName(),
						user.getIsValid()==0?"有效":"无效", 
						user.getStamp()==null?"":simple.format(user.getStamp())});

			}
			String[] headers = new String[] { "登陆名称","用户名称", "性别", "电话", "邮箱地址","所属机构",
					"是否有效", "创建时间"};
			super.renderExcel("用户信息查询" + ".xls", headers, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
			
		
	/**
	 * * 函 数 名 :checkPwd * 功能描述：判断密码是否正确 * 输入参数: *
	 * 
	 * @param 
	 * @return  
	 * @throws 无异常处理
	 * 创 建 人:周峰炎 
	 * 日 期:2013-7-12
	 * 修 改 人:
	 * 修 改 日 期:
	 * 修 改 原 因:
	 */
	public void checkPwd() {
		boolean result = true;
		String msg = OP_SUCCESS;
		try {
			Long userId = getSession().get(Constants.USER_ID) == null ? 0
					: Long.valueOf(getSession().get(Constants.USER_ID)
							.toString());
			UserPOJO userPOJO2 = userService.getById(userId);
			// 判断原密码是否正确
			if (!MD5Enctype.createPassword(oldPassword).equals(
					userPOJO2.getPassword())) {
				result = false;
				msg = "抱歉，您输入的密码错误!";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = false;
			msg = OP_FAIL;
		}
		renderMsgJson(result, msg);
	}	   

	public String[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}

	public String getRoleIdsStr() {
		return roleIdsStr;
	}

	public void setRoleIdsStr(String roleIdsStr) {
		this.roleIdsStr = roleIdsStr;
	}

	@Override
	public UserPOJO getModel() {
	    return userPOJO;
	}

	public int getPage() {
	    return page;
	}

	public void setPage(int page) {
	    this.page = page;
	}

	public int getRows() {
	    return rows;
	}

	public void setRows(int rows) {
	    this.rows = rows;
	}

	public String getOldPassword() {
	    return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
	    this.oldPassword = oldPassword;
	}

	public UserRolePOJO getUserRolePOJO() {
		return userRolePOJO;
	}

	public void setUserRolePOJO(UserRolePOJO userRolePOJO) {
		this.userRolePOJO = userRolePOJO;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
}