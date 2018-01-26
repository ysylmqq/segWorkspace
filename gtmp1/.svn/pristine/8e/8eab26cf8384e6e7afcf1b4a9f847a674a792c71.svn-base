package com.chinaGPS.gtmp.action.permission;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.chinaGPS.gtmp.action.common.BaseAction;
import com.chinaGPS.gtmp.entity.OwnerPOJO;
import com.chinaGPS.gtmp.entity.UserRolePOJO;
import com.chinaGPS.gtmp.service.IOwnerUserService;
import com.chinaGPS.gtmp.util.MD5Enctype;
import com.chinaGPS.gtmp.util.OperationLog;
import com.chinaGPS.gtmp.util.page.PageSelect;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 机主用户Action
 * 
 * @author Ben
 * 
 */
@Scope("prototype")
@Controller
public class OwnerUserAction extends BaseAction implements
		ModelDriven<OwnerPOJO> {
	private static final long serialVersionUID = -2469165500712135047L;
	private static Logger logger = LoggerFactory.getLogger(OwnerUserAction.class);

	@Autowired
	private IOwnerUserService ownerUserService;
	@Autowired
	private OwnerPOJO ownerPOJO;
	@Autowired
	private PageSelect pageSelect;
	
	private UserRolePOJO userRolePOJO;

	private int page;
	private int rows;
	
	@OperationLog(description = "查询机主用户")
	public void search() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			pageSelect.setPage(page);
			pageSelect.setRows(rows);
			HashMap<String, Serializable> map = new HashMap<String, Serializable>();
			map.put("owner", ownerPOJO);
			int total = ownerUserService.countAll(map);
			List<OwnerPOJO> datalist = ownerUserService.getByPage(map, new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
			resultMap.put("total", total);
			resultMap.put("rows", datalist);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(resultMap);
	}
	
	 /**
     * 用户信息导出
     * 2015年12月21日11:17:39添加byHHF
     */
    public String exportToExcel() throws UnsupportedEncodingException {
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	try {
            List<Object[]> values = new ArrayList<Object[]>();
            Map map = new HashMap();
            if(pageSelect.getPage() == 0)
            pageSelect.setPage(1);
            if(pageSelect.getRows() ==0)
            pageSelect.setRows(Integer.MAX_VALUE);
           
            map.put("owner", ownerPOJO);
            int total = ownerUserService.countAll(map);
			List<OwnerPOJO> datalist = ownerUserService.getByPage(map, new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
			resultMap.put("total", total);
			resultMap.put("rows", datalist);
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
            for (OwnerPOJO owner : datalist) {
                if (org.apache.commons.lang.StringUtils.isNotEmpty(owner
                        .getIsValid())) {
                    if ("0".equals(owner.getIsValid())) {
                    	owner.setIsValid("有效");
                    } else {
                    	owner.setIsValid("无效");
                    }
                }
                values.add(new Object[] { 
                		owner.getLoginName(),
                		owner.getOwnerName(),
                		owner.getSex(),
                		owner.getOwnerPhoneNumber(),
                		owner.getIsValid(), 
                		owner.getStamp()==null?"":simple.format(owner.getStamp())});
            }
            String[] headers = new String[] { "登陆名称","用户名称", "性别", "电话",
                    "是否有效", "创建时间"};
            super.renderExcel("用户信息查询" + ".xls", headers, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
	@OperationLog(description = "机主用户保存")
	public void saveOrUpdate() {
		boolean result = false;
		String msg = OP_FAIL;
		try {
			ownerPOJO.setLoginName(ownerPOJO.getOwnerPhoneNumber()); // 将登录名设置为手机号码
			OwnerPOJO owner = ownerUserService.get(ownerPOJO);
			if (owner == null) {
				if (ownerPOJO.getOwnerId() == null || "".equals(ownerPOJO.getOwnerId())) { // 新増
					ownerPOJO.setPassword(MD5Enctype.createPassword("123456"));
					result = ownerUserService.add(ownerPOJO);
					msg = OP_SUCCESS;
				} else { // 修改
					result = ownerUserService.edit(ownerPOJO);
					msg = OP_SUCCESS;
				}
			} else {
				result = false;
				msg = "该登录名已存在";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderMsgJson(result, msg);
	}
	
	@OperationLog(description = "查看机主用户")
	public void show() {
		try {
			ownerPOJO = ownerUserService.getById(Long.valueOf(ownerPOJO.getOwnerId()));
		} catch (Exception e)  {
			logger.error(e.getMessage(), e);
		}
		renderObject(ownerPOJO);
	}
	
	@OperationLog(description = "重置机主密码")
	public void resetPwd() {
		boolean flag = false;
		String msg = OP_FAIL;
		try {
			ownerPOJO.setPassword(MD5Enctype.createPassword("123456"));
			flag = ownerUserService.edit(ownerPOJO);
			msg = OP_SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderMsgJson(flag, msg);
	}
	
	@OperationLog(description = "删除机主用户")
	public void delete() {
		boolean flag = false;
		String msg = OP_FAIL;
		try {
			ownerPOJO.setIsValid("1");
			flag = ownerUserService.edit(ownerPOJO);
			msg = OP_SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderMsgJson(flag, msg);
	}
	
	public void getOwnerRoles() {
		String roleIdsStr = "";
		try {
			List<UserRolePOJO> userRolesList = ownerUserService.getOwnerRoles(userRolePOJO);
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
		Map<String, String> map = new HashMap<String, String>();
		map.put("data", roleIdsStr);
		renderObject(map);
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

	@Override
	public OwnerPOJO getModel() {
		return this.ownerPOJO;
	}

	public UserRolePOJO getUserRolePOJO() {
		return userRolePOJO;
	}

	public void setUserRolePOJO(UserRolePOJO userRolePOJO) {
		this.userRolePOJO = userRolePOJO;
	}

}
