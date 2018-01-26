package com.chinaGPS.gtmp.action.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.chinaGPS.gtmp.action.common.BaseAction;
import com.chinaGPS.gtmp.entity.SystemParamPOJO;
import com.chinaGPS.gtmp.service.ISystemParamService;
import com.chinaGPS.gtmp.util.Constants;
import com.chinaGPS.gtmp.util.OperationLog;
import com.chinaGPS.gtmp.util.page.PageSelect;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Package:com.chinaGPS.gtmp.action.system
 * @ClassName:SystemParamAction
 * @Description:系统参数配置接口
 * @author:zfy
 * @date:2013-5-22 下午05:31:54
 * 
 */
@Scope("prototype")
@Controller
public class SystemParamAction extends BaseAction implements ModelDriven<SystemParamPOJO> {
	private static final long serialVersionUID = -3628830040926824485L;
	private Logger logger = Logger.getLogger(SystemParamAction.class);

	@Resource
	private ISystemParamService systemParamService;
	@Resource
	private SystemParamPOJO param;
	@Resource
	private PageSelect pageSelect;

	private int page;
	private int rows;

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
		map.put("param", param);
		int total = systemParamService.countAll(map);
		List<SystemParamPOJO> resultList = systemParamService.getByPage(map,
				new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		renderObject(result);
		return NONE;
	}

	/**
	 * @Title:getList
	 * @return
	 * @throws
	 */
	public String getList() throws Exception {
		renderObject(systemParamService.getList(param));
		return NONE;
	}

	/**
	 * @Title:getById
	 * @Description:处理通过参数ID获取参数信息请求业务
	 * @return
	 * @throws Exception
	 * @throws
	 */
	public String getById() throws Exception {
		param = systemParamService.getById(param.getParamId());
		renderObject(param);
		return NONE;
	}

	/**
	 * @Title:delete
	 * @Description:处理删除参数配置信息请求业务
	 * @return
	 * @throws Exception
	 * @throws
	 */
	@OperationLog(description = "系统参数删除")
	public String delete() throws Exception {
		boolean result = true;
		String msg = OP_SUCCESS;
		try {
			systemParamService.removeById(param.getParamId());
			// logger("系统参数删除(paramId="+param.getParamId()+")");//写入操作日志
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
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
	@OperationLog(description = "系统参数保存")
	public String saveOrUpdate() {
		Long userId = (Long) getSession().get(Constants.USER_ID);// 得到用户名
		boolean result = true;
		String msg = OP_SUCCESS;
		try {
			// 先判断code是否存在
			List alreadyList = systemParamService.getList(param);
			if (alreadyList.isEmpty()) {
				if (param.getParamId() == null) {
					systemParamService.add(param);
					// logger("系统参数新增(code="+param.getCode()+")");//写入操作日志
				} else {
					systemParamService.edit(param);
					// logger("系统参数修改(code="+param.getCode()+")");//写入操作日志
				}
			} else {
				result = false;
				msg = "该参数编码已存在";
			}
		} catch (Exception e) {
			result = false;
			msg = OP_FAIL;
			logger.error(e.getMessage(), e);
		}
		renderMsgJson(result, msg);
		return NONE;
	}

	public PageSelect getPageSelect() {
		return pageSelect;
	}

	public void setPageSelect(PageSelect pageSelect) {
		this.pageSelect = pageSelect;
	}

	public int getPage() {
		return (page == 0) ? 1 : page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return (rows == 0) ? 10 : rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	@Override
	public SystemParamPOJO getModel() {
		return param;
	}

	public ISystemParamService getSystemParamService() {
		return systemParamService;
	}

	public void setSystemParamService(ISystemParamService systemParamService) {
		this.systemParamService = systemParamService;
	}

	public SystemParamPOJO getParam() {
		return param;
	}

	public void setParam(SystemParamPOJO param) {
		this.param = param;
	}

}
