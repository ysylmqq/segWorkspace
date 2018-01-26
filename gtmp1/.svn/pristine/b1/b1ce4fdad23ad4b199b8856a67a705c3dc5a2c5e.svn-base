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
import com.chinaGPS.gtmp.entity.DealerAreaPOJO;
import com.chinaGPS.gtmp.entity.VehicleModelPOJO;
import com.chinaGPS.gtmp.service.IDealerAreaService;
import com.chinaGPS.gtmp.service.IVehicleModelService;
import com.chinaGPS.gtmp.util.OperationLog;
import com.chinaGPS.gtmp.util.StringUtils;
import com.chinaGPS.gtmp.util.page.PageSelect;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 文 件 名 :VehicleModelAction.java
 * CopyRright (c) 2012:赛格导航
 * 文件编号：0000001
 * 创 建 人：肖克
 * 创 建 日 期：2012-12-12
 * 描 述： 机械型号管理控制器
 * 修 改 人：
 * 修 改 日 期：
 * 修 改 原 因:
 * 版 本 号：1.0
 */

@Scope("prototype")
@Controller 
public class VehicleModelAction extends BaseAction implements ModelDriven<VehicleModelPOJO> {
	private static final long serialVersionUID = -8003514902452443L;
	private static Logger logger = LoggerFactory.getLogger(VehicleModelAction.class);
	
	@Resource
    private IVehicleModelService vehicleModelService;
    @Resource
	private VehicleModelPOJO vehicleModelPOJO;	
    @Resource
    private PageSelect pageSelect;
    @Resource
	private IDealerAreaService dealerAreaService;
    @Resource
    private DealerAreaPOJO dealerAreaPOJO;
    private int page;
    private int rows;
    
	/**
	 * 查询机械型号信息，返回list
	 */
	public String getList() {
		List<VehicleModelPOJO> result = new ArrayList<VehicleModelPOJO>();

		try {
			VehicleModelPOJO vehicleModelPOJO2 = new VehicleModelPOJO();
			vehicleModelPOJO2.setModelName("全部");
			vehicleModelPOJO2.setModelId("");

			result.add(vehicleModelPOJO2);
			vehicleModelPOJO.setIsValid(0);// 有效
			result.addAll(vehicleModelService.getList(vehicleModelPOJO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(result);
		return NONE;
	}
	
	/**
	 * 查询机械代号信息，返回list
	 */
	public String getList1() {
		List<VehicleModelPOJO> result = new ArrayList<VehicleModelPOJO>();
		try {
			VehicleModelPOJO vehicleModelPOJO3 = new VehicleModelPOJO();
			vehicleModelPOJO3.setVehicleCode("全部");
			vehicleModelPOJO3.setModelId("");
			result.add(vehicleModelPOJO3);
			List<VehicleModelPOJO> vemList= vehicleModelService.getListCode(vehicleModelPOJO);
			result.addAll(vemList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(result);
		return NONE;
	}
	
	/**
	 * 查询机械代号配置，返回list
	 */
	public String getList2() {
		String obj = getRequest().getParameter("obj");
		vehicleModelPOJO.setVehicleCode(obj);
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
			List<VehicleModelPOJO> reList = vehicleModelService.getListArg(vehicleModelPOJO);
			result.put("arg",reList);
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
		map.put("vehicleModel", vehicleModelPOJO);
		int total = vehicleModelService.countAll(map);
		List<VehicleModelPOJO> resultList = vehicleModelService.getByPage(map,
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
    @OperationLog(description = "机械型号删除")
    public String delete() throws Exception {
	boolean result = true;
	String msg = OP_SUCCESS;
	try {
		vehicleModelService.remove(vehicleModelPOJO);
	    //logger("机械型号删除(modelId="+vehicleModelPOJO.getTypeId()+")");//写入操作日志
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
    @OperationLog(description = "机械型号保存")
    public String saveOrUpdate() {
		boolean result = true;
		String msg = OP_SUCCESS;
		try {
			// 先判断机械型号是否存在
			// 由于字典项和判断机械型号是否存在公用一个方法，只是条件不一样,所以先设置是否有效为空
			Integer isVal = vehicleModelPOJO.getIsValid();
			vehicleModelPOJO.setIsValid(null);
			List alreadyList = vehicleModelService.getList(vehicleModelPOJO);
			if (alreadyList.isEmpty()) {
				vehicleModelPOJO.setIsValid(isVal);
				if (StringUtils.isBlank(vehicleModelPOJO.getModelId())) {
					vehicleModelService.add(vehicleModelPOJO);
					// logger("机械型号新增(modelName="+vehicleModelPOJO.getModelName()+")");//写入操作日志
				} else {
					vehicleModelService.edit(vehicleModelPOJO);
					// logger("机械型号修改(modelName="+vehicleModelPOJO.getModelName()+")");//写入操作日志
				}
			} else {
				result = false;
				msg = "该机械型号已存在";
			}
		} catch (Exception e) {
			result = false;
			msg = OP_FAIL;
			logger.error(e.getMessage(), e);
		}
		renderMsgJson(result, msg);
		return NONE;
	}
    public String getAreaOrDealer(){
		List<DealerAreaPOJO> result = new ArrayList<DealerAreaPOJO>();

		try {
			DealerAreaPOJO dealerArea = new DealerAreaPOJO();
			dealerArea.setName("全部");
			dealerArea.setId("");

			result.add(dealerArea);
			dealerAreaPOJO.setIsValid(0);// 有效
			result.addAll(dealerAreaService.getList(dealerAreaPOJO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(result);
		return NONE;
	
    }
	public IVehicleModelService getVehicleModelService() {
		return vehicleModelService;
	}

	public void setVehicleModelService(IVehicleModelService vehicleModelService) {
		this.vehicleModelService = vehicleModelService;
	}

	public VehicleModelPOJO getVehicleModelPOJO() {
		return vehicleModelPOJO;
	}

	public void setVehicleModelPOJO(VehicleModelPOJO vehicleModelPOJO) {
		this.vehicleModelPOJO = vehicleModelPOJO;
	}

	@Override
	public VehicleModelPOJO getModel() {
	    return vehicleModelPOJO;
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

	public DealerAreaPOJO getDealerAreaPOJO() {
		return dealerAreaPOJO;
	}

	public void setDealerAreaPOJO(DealerAreaPOJO dealerAreaPOJO) {
		this.dealerAreaPOJO = dealerAreaPOJO;
	}	
	
	
}