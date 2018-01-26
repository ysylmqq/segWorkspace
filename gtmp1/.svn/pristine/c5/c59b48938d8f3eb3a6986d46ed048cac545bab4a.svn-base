package com.chinaGPS.gtmp.action.run;

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
import com.chinaGPS.gtmp.action.vehicle.AreaPointAction;
import com.chinaGPS.gtmp.entity.AreaPointPOJO;
import com.chinaGPS.gtmp.entity.AreaPointVehiclePOJO;
import com.chinaGPS.gtmp.entity.DepartPOJO;
import com.chinaGPS.gtmp.entity.UserPOJO;
import com.chinaGPS.gtmp.service.IAreaPointService;
import com.chinaGPS.gtmp.util.Constants;
import com.chinaGPS.gtmp.util.OperationLog;
import com.chinaGPS.gtmp.util.page.PageSelect;
import com.opensymphony.xwork2.ModelDriven;

@Scope("prototype")
@Controller
public class FenceAction extends BaseAction implements ModelDriven<AreaPointPOJO> {
	private static final long serialVersionUID = 2974416252574428270L;
	private static Logger logger = LoggerFactory.getLogger(AreaPointAction.class);
	
	@Resource
	private AreaPointPOJO areaPointPOJO;
	@Resource
	private AreaPointVehiclePOJO areaPointVehiclePOJO;
	@Resource
	private IAreaPointService areaPointService;
	@Resource
    private PageSelect pageSelect;

    private int page;
    private int rows;
    private String vehicleIds;
    String area_id;
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
    	areaPointPOJO.setAreapointType(2);
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
		areaPointPOJO.setAreapointType(2);
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
			areaPointVehiclePOJO.setArea_id(areaPointPOJO.getAreapointId());
			areaPointService.relieveAreaVehicle(areaPointVehiclePOJO);
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
    @OperationLog(description = "电子围栏区域保存")
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
					areaPointPOJO.setAreapointType(2);
					areaPointService.add(areaPointPOJO);
				} else {
					areaPointService.edit(areaPointPOJO);
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
    
    public String getAreaVehicels() throws Exception {
    	pageSelect.setPage(page);
		pageSelect.setRows(rows);
		HashMap<String, Object> result = new HashMap<String, Object>();
		if(areaPointPOJO.getAreapointId() == null){
			result.put("total", 0);
			result.put("rows", "");
		}else {
			areaPointVehiclePOJO.setArea_id(areaPointPOJO.getAreapointId());
			Map map = new HashMap();			
			map.put("areaPointVehicles", areaPointVehiclePOJO);
			int total = areaPointService.getAreaVehiclesCountAll(map);
			if(total>0){
			    List<AreaPointVehiclePOJO> resultList = areaPointService.getAreaVehicles(map,
					new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
			    result.put("total", total);
			    result.put("rows", resultList);
			}else{
				result.put("total", 0);
				result.put("rows", "");
			}
		}
		
		
		renderObject(result);
		return NONE;
}
    @OperationLog(description = "电子围栏区域机械绑定")
    public void saveAreaVehicles() throws Exception {
    	//区域绑定车辆
    	boolean result = false;
		String msg = OP_FAIL;
    	try {
    		 String[] vehicleId = vehicleIds.split(",");
    		 for(String vid:vehicleId){    			
    			UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
    			AreaPointVehiclePOJO entity = new AreaPointVehiclePOJO();
    			entity.setArea_id(areaPointPOJO.getAreapointId());
    			entity.setUnit_id(areaPointService.getSimNo(Long.valueOf(vid)));
    			entity.setUser_id(userPOJO.getUserId());
    			entity.setVehicle_id(Long.valueOf(vid));
    			areaPointService.saveAreaVehicles(entity);
    		 }
    		 
    		 result = true;
    		 msg = OP_SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);			
		}
		renderMsgJson(result, msg);
        }
    
    
    @OperationLog(description = "电子围栏区域机械解除绑定")
    public void relieveAreaVehicle() throws Exception{

    	//区域机械解除绑定车辆
    	boolean result = false;
		String msg = OP_FAIL;
    	try {
    		 if(getRequest().getParameter("area_id")!=null){
    			 areaPointVehiclePOJO.setArea_id(Long.valueOf(getRequest().getParameter("area_id")));
    		 }
    		 if(getRequest().getParameter("vehicle_id")!=null){
    			 areaPointVehiclePOJO.setVehicle_id(Long.valueOf(getRequest().getParameter("vehicle_id")));
    		 }
    		 areaPointService.relieveAreaVehicle(areaPointVehiclePOJO);
    		 result = true;
    		 msg = OP_SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);			
		}
		renderMsgJson(result, msg);
        
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
	
	public String getVehicleIds() {
		return vehicleIds;
	}
	public void setVehicleIds(String vehicleIds) {
		this.vehicleIds = vehicleIds;
	}
	public String getArea_id() {
		return area_id;
	}
	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	
}
