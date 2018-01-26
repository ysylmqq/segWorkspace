package com.chinaGPS.gtmp.action.servicemanage;

import java.io.Serializable;
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
import com.chinaGPS.gtmp.entity.RolePOJO;
import com.chinaGPS.gtmp.entity.ServiceStationPOJO;
import com.chinaGPS.gtmp.entity.UserPOJO;
import com.chinaGPS.gtmp.service.IServiceStationService;
import com.chinaGPS.gtmp.util.Constants;
import com.chinaGPS.gtmp.util.page.PageSelect;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 服务站管理Action 
 * @author Ben
 *
 */
@Controller @Scope("prototype")
public class ServiceStationAction extends BaseAction implements ModelDriven<ServiceStationPOJO> {
	private static final long serialVersionUID = -697946161557887442L;
	private static Logger logger = LoggerFactory.getLogger(ServiceStationAction.class);
	
	@Autowired
	private ServiceStationPOJO serviceStationPOJO;
	@Autowired
	private IServiceStationService serviceStationService;
	@Autowired
	private PageSelect pageSelect;
	private int page;
	private int rows;
	
	public void index() {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
			List<RolePOJO> roles = userPOJO.getRoles();
			boolean isDealer = false;// 是否是终端供应商
			if (!roles.isEmpty()) {
				if (roles.size() == 1) {
					RolePOJO role = roles.get(0);
					if (role.getRoleId().intValue() == Constants.DEALER_ROLE_ID
							.intValue()) {// 如果是终端供应商的话
						isDealer = true;
					}
				}
			}
			if (isDealer) {
				serviceStationPOJO.setDepartId(userPOJO.getDepartId());
			}
			
			pageSelect.setPage(page);
			pageSelect.setRows(rows);
			Map<String, Serializable> params = new HashMap<String, Serializable>();
			params.put("serviceStation", serviceStationPOJO);
			int total = serviceStationService.countAll(params);
			List<ServiceStationPOJO> resultList = serviceStationService.getByPage(params, new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
			result.put("total", total);
			result.put("rows", resultList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(result);
	}
	
	public void addOrEdit() {
		boolean result = false;
		String msg = OP_FAIL;
		try {
			if(serviceStationPOJO.getId() == null || "".equals(serviceStationPOJO.getId())) {
				Long userId = (Long) getSession().get(Constants.USER_ID);
				serviceStationPOJO.setCreateMan(userId);
				result = serviceStationService.add(serviceStationPOJO);
				msg = OP_SUCCESS;
			} else {
				result = serviceStationService.edit(serviceStationPOJO);
				msg = OP_SUCCESS;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderMsgJson(result, msg);
	}
	
	public void show() {
		try {
			serviceStationPOJO = serviceStationService.getById(serviceStationPOJO.getId());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(serviceStationPOJO);
	}
	
	public void delete() {
		boolean result = false;
		String msg = OP_FAIL;
		try {
			serviceStationPOJO.setIsValid("0"); // 设置为无效，进行逻辑删除
			result = serviceStationService.edit(serviceStationPOJO);
			msg = OP_SUCCESS;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderMsgJson(result, msg);
	}
	
	@Override
	public ServiceStationPOJO getModel() {
		return this.serviceStationPOJO;
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

}
