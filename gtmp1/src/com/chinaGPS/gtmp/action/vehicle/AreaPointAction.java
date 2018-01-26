package com.chinaGPS.gtmp.action.vehicle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.chinaGPS.gtmp.action.common.BaseAction;
import com.chinaGPS.gtmp.entity.AreaPointPOJO;
import com.chinaGPS.gtmp.entity.DepartPOJO;
import com.chinaGPS.gtmp.entity.UserPOJO;
import com.chinaGPS.gtmp.service.IAreaPointService;
import com.chinaGPS.gtmp.util.Constants;
import com.chinaGPS.gtmp.util.OperationLog;
import com.chinaGPS.gtmp.util.page.PageSelect;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 文 件 名 :AreaPointAction.java
 * CopyRright (c) 2012:赛格导航
 * 文件编号：0000001
 * 创 建 人：周峰炎
 * 创 建 日 期：2013-7-15
 * 描 述： 库存区域Action
 * 修 改 人：
 * 修 改 日 期：
 * 修 改 原 因:
 * 版 本 号：1.0
 */
@Scope("prototype")
@Controller
public class AreaPointAction  extends BaseAction implements ModelDriven<AreaPointPOJO> {
	private static final long serialVersionUID = 2974416252574428270L;
	private static Logger logger = LoggerFactory.getLogger(AreaPointAction.class);
	
	@Resource
	private AreaPointPOJO areaPointPOJO;
		
	@Resource
	private IAreaPointService areaPointService;
	 @Resource
    private PageSelect pageSelect;

    private int page;
    private int rows;
    public void getList() throws Exception {
    	//获得用户所属机构
    	UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
    	if(userPOJO!=null){
    		DepartPOJO departPOJO=userPOJO.getDepartInfo();
    		if(departPOJO!=null&&departPOJO.getDepartId()!=null){
    			if(areaPointPOJO==null){
    				areaPointPOJO=new AreaPointPOJO();
    			}
    			areaPointPOJO.setDepartId(departPOJO.getDepartId());
    		}
    	}
    	areaPointPOJO.setAreapointType(1);
    	List<AreaPointPOJO> resultList = areaPointService.getList(areaPointPOJO);
    	renderObject(resultList);
        }
    /**
     * @Title:search
     * @Description:处理查询库存区域
     * @return
     * @throws Exception 
     * @throws
     */
    public String search() throws Exception {
		pageSelect.setPage(page);
		pageSelect.setRows(rows);
		Map map = new HashMap();
		areaPointPOJO.setAreapointType(1);
		map.put("areaPoint", areaPointPOJO);
		int total = areaPointService.countAll(map);
		List<AreaPointPOJO> resultList = areaPointService.getByPage(map,
				new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		renderObject(result);
		return NONE;
	}


    /**
     * @Title:getById
     * @Description:处理通过库存区域ID获取库存区域请求业务
     * @return
     * @throws Exception 
     * @throws
     */
    public String getById() {
    	try {
    		areaPointPOJO = areaPointService.getById(areaPointPOJO.getAreapointId());
    	} catch (Exception e) {
    		logger.error(e.getMessage(), e);
    	}
		renderObject(areaPointPOJO);
		return NONE;
	}

    /**
     * @Title:delete
     * @Description:处理删除库存区域请求业务
     * @return
     * @throws Exception 
     * @throws
     */
    @OperationLog(description = "库存区域删除")
    public String delete() {
		boolean result = true;
		String msg = OP_SUCCESS;
		try {
			areaPointService.removeById(areaPointPOJO.getAreapointId());
			// logger("库存区域删除(paramId="+areaPointPOJO.getAreapointId()+")");//写入操作日志
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
     * @Description:处理保存或更新库存区域请求业务
     * @return
     * @throws
     */
    @OperationLog(description = "库存区域保存")
    public String saveOrUpdate() {
		boolean result = true;
		String msg = OP_SUCCESS;
		// 获得用户所属机构
		UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
		if (userPOJO != null) {
			DepartPOJO departPOJO = userPOJO.getDepartInfo();
			if (departPOJO != null && departPOJO.getDepartId() != null) {
				areaPointPOJO.setDepartId(departPOJO.getDepartId());
			}
		}
		try {
			if (areaPointService.checkAreaPointName(areaPointPOJO) > 0) {
				msg = "该区域名称已存在!";
				result = false;
			} else {
				areaPointPOJO.setUserId(userPOJO.getUserId());
				if (areaPointPOJO.getAreapointId() == null) {
					areaPointPOJO.setAreapointType(1);
					areaPointService.add(areaPointPOJO);
					// /
					// logger("库存区域新增(code="+areaPointPOJO.getAreapointName()+")");//写入操作日志
				} else {
					areaPointService.edit(areaPointPOJO);
					// logger("库存区域修改(code="+areaPointPOJO.getAreapointName()+")");//写入操作日志
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			msg = OP_FAIL;
			result = false;
		}
		renderMsgJson(result, msg);
		return NONE;
	}
    
	@Override
	public AreaPointPOJO getModel() {
		return areaPointPOJO;
	}

	public IAreaPointService getAreaPointService() {
		return areaPointService;
	}

	public void setAreaPointService(IAreaPointService areaPointService) {
		this.areaPointService = areaPointService;
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
