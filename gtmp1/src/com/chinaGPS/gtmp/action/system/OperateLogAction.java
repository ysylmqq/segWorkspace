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
import com.chinaGPS.gtmp.entity.OperateLogPOJO;
import com.chinaGPS.gtmp.service.IOperateLogService;
import com.chinaGPS.gtmp.util.page.PageSelect;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Package:com.chinaGPS.gtmp.action.system
 * @ClassName:OperateLogAction
 * @Description:操作日志Action
 * @author:zfy
 * @date:2013-4-9 上午10:24:58
 */
@Scope("prototype")
@Controller
public class OperateLogAction extends BaseAction implements ModelDriven<OperateLogPOJO> {
	private static final long serialVersionUID = 2095400991627539706L;
	private Logger logger = Logger.getLogger(OperateLogAction.class);

	@Resource
	private IOperateLogService operateLogService;
	@Resource
	private OperateLogPOJO operateLogPOJO;
	@Resource
	private PageSelect pageSelect;

	private int page;
	private int rows;

	/**
	 * @Title:getPage
	 * @Description:获取操作日誌列表
	 * @return
	 * @throws Exception
	 * @throws
	 */
	public void getPage() throws Exception {
		pageSelect.setPage(page);
		pageSelect.setRows(rows);
		// 分角色,如果是超级管理员，可以看到其他人的操作日志；否则只能看到自己的日志
		// operateLogPOJO.setUserId((Long)getSession().get(Constants.USER_ID));
		Map map = new HashMap();
		map.put("opl", operateLogPOJO);
		int total = operateLogService.countAll(map);
		List<OperateLogPOJO> resultList = operateLogService.getByPage(map,
				new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		renderObject(result);
	}
	
	public void getSelectData() {
		List<Map<String, Object>> datalist = new ArrayList<Map<String,Object>>();
		try {
			datalist = operateLogService.getSelectData();
			Map<String, Object> allSelect = new HashMap<String, Object>();
			allSelect.put("LOGTYPE", "全部");
			datalist.add(0, allSelect);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(datalist);
	}

	@Override
	public OperateLogPOJO getModel() {
		return operateLogPOJO;
	}

	public IOperateLogService getOperateLogService() {
		return operateLogService;
	}

	public void setOperateLogService(IOperateLogService operateLogService) {
		this.operateLogService = operateLogService;
	}

	public PageSelect getPageSelect() {
		return pageSelect;
	}

	public void setPageSelect(PageSelect pageSelect) {
		this.pageSelect = pageSelect;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setPage(int page) {
		this.page = page;
	}

}
