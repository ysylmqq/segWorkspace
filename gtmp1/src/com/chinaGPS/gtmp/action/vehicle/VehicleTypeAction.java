package com.chinaGPS.gtmp.action.vehicle;

import java.util.ArrayList;
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
import com.chinaGPS.gtmp.entity.VehicleTypePOJO;
import com.chinaGPS.gtmp.service.IVehicleTypeService;
import com.chinaGPS.gtmp.util.OperationLog;
import com.chinaGPS.gtmp.util.StringUtils;
import com.chinaGPS.gtmp.util.page.PageSelect;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 文 件 名 :VehicleTypeAction.java
 * CopyRright (c) 2012:赛格导航
 * 文件编号：0000001
 * 创 建 人：肖克
 * 创 建 日 期：2012-12-12
 * 描 述： 机械类型管理控制器
 * 修 改 人：
 * 修 改 日 期：
 * 修 改 原 因:
 * 版 本 号：1.0
 */
@Scope("prototype")
@Controller  
public class VehicleTypeAction extends BaseAction implements ModelDriven<VehicleTypePOJO> {
	private static final long serialVersionUID = 4730454699842033531L;
	private static Logger logger = LoggerFactory.getLogger(VehicleTypeAction.class);
	
	@Resource
    private IVehicleTypeService vehicleTypeService;
    @Resource
	private VehicleTypePOJO vehicleTypePOJO;	
    @Resource
    private PageSelect pageSelect;

    private int page;
    private int rows;
    
	/**
	 * 查询机械类型信息，返回list
	 */
	public String getList() {
		List<VehicleTypePOJO> result = new ArrayList<VehicleTypePOJO>();

		try {
			VehicleTypePOJO vehicleTypePOJO2 = new VehicleTypePOJO();
			vehicleTypePOJO2.setTypeName("全部");
			vehicleTypePOJO2.setTypeId("");

			result.add(vehicleTypePOJO2);

			vehicleTypePOJO.setIsValid(0);// 有效
			result.addAll(vehicleTypeService.getList(vehicleTypePOJO));
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
		map.put("vehicleType", vehicleTypePOJO);
		int total = vehicleTypeService.countAll(map);
		List<VehicleTypePOJO> resultList = vehicleTypeService.getByPage(map,
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
    @OperationLog(description = "机械类型删除")
    public String delete() throws Exception {
		boolean result = true;
		String msg = OP_SUCCESS;
		try {
			vehicleTypeService.remove(vehicleTypePOJO);
			// logger("机械类型删除(typeId="+vehicleTypePOJO.getTypeId()+")");//写入操作日志
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = false;
			msg = OP_FAIL;
		}
		renderMsgJson(result, msg);
		return NONE;
	}
    
    
    
    /**
     * @Title:seachEdit
     * @Description:编辑
     * @return
     * @throws Exception 
     * @throws
     */
    @OperationLog(description = "机械类型编辑")
    public String seachEdit() throws Exception {
    	HashMap<String, Object> result = new HashMap<String, Object>();
    	VehicleTypePOJO obj = vehicleTypeService.get(vehicleTypePOJO);
    	result.put("rows", obj);
    	renderObject(result);
		return NONE;
	}

    /**
     * @Title:saveOrUpdate
     * @Description:处理保存或更新终端信息请求业务
     * @return
     * @throws
     */
    @OperationLog(description = "机械类型保存")
    public String saveOrUpdate() {
		boolean result = true;
		String msg = OP_SUCCESS;
		try {
			// 先判断机械类型是否存在
			// 由于字典项和判断机械类型是否存在公用一个方法，只是条件不一样,所以先设置是否有效为空
			Integer isVal = vehicleTypePOJO.getIsValid();
			vehicleTypePOJO.setIsValid(null);
			//List alreadyList = vehicleTypeService.getList(vehicleTypePOJO);
			//if (alreadyList.isEmpty()) {
				vehicleTypePOJO.setIsValid(isVal);
				if ((vehicleTypePOJO.getIndexId()==null)) {
					List alreadyList = vehicleTypeService.getList(vehicleTypePOJO);
					if (!alreadyList.isEmpty()){
						result = false;
						msg = "该机械类型已存在";
						renderMsgJson(result, msg);
						return NONE;
					}
					vehicleTypeService.add(vehicleTypePOJO);
				} else {
					vehicleTypeService.edit(vehicleTypePOJO);
				}
			/*} else {
				result = false;
				msg = "该机械类型已存在";
			}*/
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = false;
			msg = OP_FAIL;
		}
		renderMsgJson(result, msg);
		return NONE;
	}
    
	public IVehicleTypeService getVehicleTypeService() {
		return vehicleTypeService;
	}

	public void setVehicleTypeService(IVehicleTypeService vehicleTypeService) {
		this.vehicleTypeService = vehicleTypeService;
	}

	public VehicleTypePOJO getVehicleTypePOJO() {
		return vehicleTypePOJO;
	}

	public void setVehicleTypePOJO(VehicleTypePOJO vehicleTypePOJO) {
		this.vehicleTypePOJO = vehicleTypePOJO;
	}

	@Override
	public VehicleTypePOJO getModel() {
	    return vehicleTypePOJO;
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