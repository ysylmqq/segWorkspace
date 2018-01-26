package com.chinaGPS.gtmp.action.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.chinaGPS.gtmp.action.common.BaseAction;
import com.chinaGPS.gtmp.entity.DicUnitTypePOJO;
import com.chinaGPS.gtmp.entity.RolePOJO;
import com.chinaGPS.gtmp.entity.UserPOJO;
import com.chinaGPS.gtmp.service.IDicUnitTypeService;
import com.chinaGPS.gtmp.util.Constants;
import com.chinaGPS.gtmp.util.OperationLog;
import com.chinaGPS.gtmp.util.StringUtils;
import com.chinaGPS.gtmp.util.page.PageSelect;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Package:com.chinaGPS.gtmp.action.system
 * @ClassName:DicUnitTypetAction
 * @Description:终端类型字典Action
 * @author:zfy
 * @date:2013-4-9 上午10:24:58
 */
@Scope("prototype")
@Controller
public class DicUnitTypeAction extends BaseAction implements ModelDriven<DicUnitTypePOJO> {
	private static final long serialVersionUID = -3513041228851799889L;
	private static Logger logger = Logger.getLogger(DicUnitTypeAction.class);

	@Resource
	private IDicUnitTypeService dicUnitTypeService;
	@Resource
	private DicUnitTypePOJO dicUnitTypePOJO;

	@Resource
	private PageSelect pageSelect;

	private int page;
	private int rows;

	/**
	 * @Title:getList
	 * @Description:处理获取终端类型列表
	 * @return
	 * @throws
	 */
	public String getList() {
		List<DicUnitTypePOJO> result = new ArrayList<DicUnitTypePOJO>();

		try {
			// 非终端注册
			if (!(dicUnitTypePOJO.getIsValid() != null && dicUnitTypePOJO
					.getIsValid().intValue() == 0)) {
				DicUnitTypePOJO dicUnitTypePOJO2 = new DicUnitTypePOJO();
				dicUnitTypePOJO2.setUnitType("全部");
				dicUnitTypePOJO2.setUnitTypeSn("");
				result.add(dicUnitTypePOJO2);
			}
			dicUnitTypePOJO.setIsValid(0);// 有效

			UserPOJO userPOJO = (UserPOJO) getSession()
					.get(Constants.USER_INFO);
			List<RolePOJO> roles = userPOJO.getRoles();
			boolean isSupperier = false;// 是否是终端供应商
			if (!roles.isEmpty()) {
				if (roles.size() == 1) {
					RolePOJO role = roles.get(0);
					if (role.getRoleId().intValue() == Constants.SUPPERIER_ROLE_ID
							.intValue()) {// 如果是终端供应商的话
						isSupperier = true;
					}
				}
			}
			if (isSupperier) {// 终端供应商
				dicUnitTypePOJO.setUserId(userPOJO.getDepartId());
			}

			result.addAll(dicUnitTypeService.getList(dicUnitTypePOJO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(result);

		return NONE;
	}

	/**
	 * @Title:query
	 * @Description:处理查询终端信息请求业务
	 * @return
	 * @throws Exception
	 * @throws
	 */
	public String search() throws Exception {
		pageSelect.setPage(page);
		pageSelect.setRows(rows);
		Map map = new HashMap();

		UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
		List<RolePOJO> roles = userPOJO.getRoles();
		boolean isSupperier = false;// 是否是终端供应商
		if (!roles.isEmpty()) {
			if (roles.size() == 1) {
				RolePOJO role = roles.get(0);
				if (role.getRoleId().intValue() == Constants.SUPPERIER_ROLE_ID
						.intValue()) {// 如果是终端供应商的话
					isSupperier = true;
				}
			}
		}
		if (isSupperier) {// 终端供应商
			dicUnitTypePOJO.setUserId(userPOJO.getDepartId());
		}

		map.put("unitType", dicUnitTypePOJO);
		int total = dicUnitTypeService.countAll(map);
		List<DicUnitTypePOJO> resultList = dicUnitTypeService.getByPage(map,
				new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		renderObject(result);
		return NONE;
	}

	/**
	 * @Title:delete
	 * @Description:处理删除参数配置信息请求业务
	 * @return
	 * @throws Exception
	 * @throws
	 */
	@OperationLog(description = "终端类型删除")
	public String delete() throws Exception {
		boolean result = true;
		String msg = OP_SUCCESS;
		try {
			dicUnitTypeService.remove(dicUnitTypePOJO);
			// logger("终端类型删除(unitTypeSn="+dicUnitTypePOJO.getUnitTypeSn()+")");//写入操作日志
		} catch (RuntimeException e) {
			e.printStackTrace();
			result = false;
			msg = OP_FAIL;
		}
		renderMsgJson(result, msg);
		return NONE;
	}

	/**
	 * @Title:saveOrUpdate
	 * @Description:处理保存或更新终端信息请求业务
	 * @return
	 * @throws
	 */
	@OperationLog(description = "终端类型保存")
	public String saveOrUpdate() {
		boolean result = true;
		String msg = OP_SUCCESS;
		try {
			Long userId = (Long) getSession().get(Constants.USER_ID);// 得到用户名
			dicUnitTypePOJO.setUserId(userId);
			if (StringUtils.isBlank(dicUnitTypePOJO.getUpdateId())) {
				// 先判断unitTypeSn是否存在
				// 由于字典项和判断unitTypeSn是否存在公用一个方法，只是条件不一样,所以先设置是否有效为空
				Integer isVal = dicUnitTypePOJO.getIsValid();
				dicUnitTypePOJO.setIsValid(null);

				List alreadyList = dicUnitTypeService.getList(dicUnitTypePOJO);
				dicUnitTypePOJO.setIsValid(isVal);
				if (!alreadyList.isEmpty()) {
					result = false;
					msg = "该终端类型编号已存在";
				} else {
					dicUnitTypeService.add(dicUnitTypePOJO);
					// logger("终端类型新增(unitTypeSn="+dicUnitTypePOJO.getUnitTypeSn()+")");//写入操作日志
				}
			} else {
				dicUnitTypeService.edit(dicUnitTypePOJO);
				// logger("终端类型修改(unitTypeSn="+dicUnitTypePOJO.getUnitTypeSn()+")");//写入操作日志

			}
		} catch (Exception e) {
			result = false;
			msg = OP_FAIL;
			e.printStackTrace();
		}

		renderMsgJson(result, msg);
		return NONE;
	}

	public IDicUnitTypeService getDicUnitTypeService() {
		return dicUnitTypeService;
	}

	public void setDicUnitTypeService(IDicUnitTypeService dicUnitTypeService) {
		this.dicUnitTypeService = dicUnitTypeService;
	}

	public DicUnitTypePOJO getDicUnitTypePOJO() {
		return dicUnitTypePOJO;
	}

	public void setDicUnitTypePOJO(DicUnitTypePOJO dicUnitTypePOJO) {
		this.dicUnitTypePOJO = dicUnitTypePOJO;
	}

	@Override
	public DicUnitTypePOJO getModel() {
		return dicUnitTypePOJO;
	}

	public PageSelect getPageSelect() {
		return pageSelect;
	}

	public void setPageSelect(PageSelect pageSelect) {
		this.pageSelect = pageSelect;
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
