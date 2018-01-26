package com.chinaGPS.gtmp.action.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.chinaGPS.gtmp.action.common.BaseAction;
import com.chinaGPS.gtmp.entity.VehicleWorkPOJO;
import com.chinaGPS.gtmp.service.IWorkhourService;
import com.chinaGPS.gtmp.util.StringUtils;
import com.chinaGPS.gtmp.util.page.PageSelect;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 工作时间统计报表Action
 * @author Ben
 *
 */
@Controller @Scope("prototype")
public class WorkhourReportAction extends BaseAction implements ModelDriven<VehicleWorkPOJO> {
	private static final long serialVersionUID = 5795377645134741566L;
	private static Logger logger = LoggerFactory.getLogger(WorkhourReportAction.class);
	
	@Resource
	private VehicleWorkPOJO vehicleWorkPOJO;
	
	@Resource
	private IWorkhourService workhourService;
	
	private int page;
	private int rows;
	@Resource
	private PageSelect pageSelect;
	
	/**
	 * 累计工作时间汇总统计
	 * @return
	 */
	public String totalWorkhour() {
		/*List<Map<String, String>> datalist = new ArrayList<Map<String,String>>();
		
		try {
			if (org.apache.commons.lang.StringUtils.isNotEmpty(vehicleWorkPOJO.getDealerId())) {
				vehicleWorkPOJO.setDealerIds(StringUtils.split(vehicleWorkPOJO.getDealerId(), ','));
			}
			datalist = workhourService.totalWorkhour(vehicleWorkPOJO);			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(datalist);*/
		Map<String, Object> data = new HashMap<String,Object>();
		pageSelect.setPage(page);
		pageSelect.setRows(rows);
		try {
			if (org.apache.commons.lang.StringUtils.isNotEmpty(vehicleWorkPOJO.getDealerId())) {
				vehicleWorkPOJO.setDealerIds(StringUtils.split(vehicleWorkPOJO.getDealerId(), ','));
			}
			data = workhourService.totalWorkhourPage(vehicleWorkPOJO, pageSelect);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		renderObject(data);
		return NONE;
	}
	
	/**
	 * 累计工作时间详情
	 * @return
	 */
	public String totalWorkhourDetail() {
		Map<String, Object> data = new HashMap<String,Object>();
		pageSelect.setPage(page);
		pageSelect.setRows(rows);
		try {
			if (org.apache.commons.lang.StringUtils.isNotEmpty(vehicleWorkPOJO.getDealerId())) {
				vehicleWorkPOJO.setDealerIds(StringUtils.split(vehicleWorkPOJO.getDealerId(), ','));
			}
			data = workhourService.totalWorkhourDetail(vehicleWorkPOJO, pageSelect);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		renderObject(data);
		return NONE;
	}
	
	/**
	 * 每日工作时间汇总统计
	 * @return
	 */
	public String dailyWorkhour() {
		List<Map<String, String>> datalist = new ArrayList<Map<String,String>>();
		
		try {
			if (org.apache.commons.lang.StringUtils.isNotEmpty(vehicleWorkPOJO.getDealerId())) {
				vehicleWorkPOJO.setDealerIds(StringUtils.split(vehicleWorkPOJO.getDealerId(), ','));
			}
			datalist = workhourService.dailyWorkhour(vehicleWorkPOJO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(datalist);
		
		return NONE;
	}
	
	/**
	 * 每日工作时间详情
	 * @return
	 */
	public String dailyWorkhourDetail() {
		pageSelect.setPage(page);
		pageSelect.setRows(rows);
		Map<String, Object> data = new HashMap<String,Object>();
		
		try {
			data = workhourService.dailyWorkhourDetail(vehicleWorkPOJO, pageSelect);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		renderObject(data);
		return NONE;
	}
	
	/**
	 * 查询机械代号，返回list
	 */
	public String modelNameSearch() {
		String obj = getRequest().getParameter("obj");
		vehicleWorkPOJO.setModelId(obj);
		HashMap<String, Object> result = new HashMap<String, Object>();
		List<VehicleWorkPOJO> resulttemp = new ArrayList<VehicleWorkPOJO>();
		try {
			List<VehicleWorkPOJO> reList = workhourService.getListCode(vehicleWorkPOJO);
			VehicleWorkPOJO tempPojo=new VehicleWorkPOJO();
			tempPojo.setVehicleCode("全部");
			tempPojo.setModelId("");
			resulttemp.add(tempPojo);
			resulttemp.addAll(reList);
			result.put("code",resulttemp);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(result);
		return NONE;
	}
	
	/**
	 * 查询机械代号，返回list
	 */
	public String codeSearch() {
		String obj = getRequest().getParameter("obj");
		vehicleWorkPOJO.setVehicleCode(obj);
		HashMap<String, Object> result = new HashMap<String, Object>();
		List<VehicleWorkPOJO> resulttemp = new ArrayList<VehicleWorkPOJO>();
		try {
			List<VehicleWorkPOJO> reList = workhourService.getListArg(vehicleWorkPOJO);
			VehicleWorkPOJO tempPojo=new VehicleWorkPOJO();
			tempPojo.setVehicleArg("全部");
			tempPojo.setModelId("");
			resulttemp.add(tempPojo);
			resulttemp.addAll(reList);
			result.put("arg",resulttemp);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(result);
		return NONE;
	}
	
	/**
	 * 累计工作时间汇总统计导出
	 * @return
	 */
	public String summaryDownExcel() {
		List<Object[]> values = new ArrayList<Object[]>();
		try {
			if(null != vehicleWorkPOJO.getDealerId()&& !("").equals(vehicleWorkPOJO.getDealerId())){
				vehicleWorkPOJO.setDealerIds(vehicleWorkPOJO.getDealerId().split(","));
			}
			List<Map<String, String>> datalist = workhourService.totalWorkhour(vehicleWorkPOJO);
			for(Map<String, String> a:datalist){
				values.add(new Object[] {
						a.get("DEALERNAME"),
						a.get("AREANAME"),
						a.get("MODELNAME"),
						a.get("VEHICLECODE"),
						a.get("VEHICLEARG"),
						a.get("VEHICLECOUNT"),
						a.get("TOTALWORKHOUR"),
						a.get("AVGWORKHOUR")
				});
			}
			String[] headers = new String[] { "经销商", "区域","机械型号", "机械代号", "机械配置", "机械数量", "累计工作时间(小时)", "平均工作时间(小时)"};
			super.renderExcel("累计工作时间汇总统计" + ".xls", headers, values);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return NONE;
	}
	/**
	 * 累计工作时间详情导出
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String detailedDownExcel() {
		List<Object[]> values = new ArrayList<Object[]>();
		pageSelect.setPage(1);
		//pageSelect.setRows(Integer.MAX_VALUE);
		pageSelect.setRows(Integer.MAX_VALUE);
		try {
			if(null != vehicleWorkPOJO.getDealerId()&& !("").equals(vehicleWorkPOJO.getDealerId())){
				vehicleWorkPOJO.setDealerIds(vehicleWorkPOJO.getDealerId().split(","));
			}
			Map<String, Object> datalist = workhourService.totalWorkhourDetail(vehicleWorkPOJO, pageSelect);
			List<Map<String, String>> list =(List<Map<String, String>>) datalist.get("rows");
			for(Map<String, String> a:list){
				values.add(new Object[] {
				a.get("VEHICLEDEF"),
				a.get("DEALERNAME"),
				a.get("AREANAME"),
				a.get("VEHICLESTATUS"),
				a.get("MODELNAME"),				
				a.get("VEHICLECODE"),
				a.get("VEHICLEARG"),
				a.get("TOTALWORK")
				});
			}
			String[] headers = new String[] { "整机编号","经销商","区域","机械状态", "机械型号", "机械代号", "机械配置", "累计工作时间(小时)"};
			super.renderExcel("累计工作时间详情" + ".xls", headers, values);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return NONE;
	}
	/**
	 * 每日工作时间汇总统计导出
	 * @return
	 */
	public String dayDownExcel() {
		List<Object[]> values = new ArrayList<Object[]>();
		try {
			if(null != vehicleWorkPOJO.getDealerId()&& !("").equals(vehicleWorkPOJO.getDealerId())){
				vehicleWorkPOJO.setDealerIds(vehicleWorkPOJO.getDealerId().split(","));
			}
			List<Map<String, String>> datalist = workhourService.dailyWorkhour(vehicleWorkPOJO);
			for(Map<String, String> a:datalist){
				values.add(new Object[] {
						a.get("VEHICLEDEF"),
						a.get("DEALERNAME"),
						a.get("AREANAME"),
						a.get("VEHICLESTATUS"),
						a.get("MODELNAME"),				
						a.get("VEHICLECODE"),
						a.get("VEHICLEARG"),
						a.get("TOTALWORK")
				});
			}
			String[] headers = new String[] { "整机编号","经销商","区域","机械状态", "机械型号", "机械代号", "机械配置","阶段工作时间(小时)"};
			super.renderExcel("每日工作时间汇总统计导出" + ".xls", headers, values);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}		
		return NONE;
	}
	
	/**
	 * 每日工作时间详情导出
	 * @return
	 */
	public String dayDetailDownExcel() {
		List<Object[]> values = new ArrayList<Object[]>();
		pageSelect.setPage(1);
		pageSelect.setRows(Integer.MAX_VALUE);
		Map<String, Object> data = new HashMap<String,Object>();		
		try {
			if(null != vehicleWorkPOJO.getDealerId()&& !("").equals(vehicleWorkPOJO.getDealerId())){
				vehicleWorkPOJO.setDealerIds(vehicleWorkPOJO.getDealerId().split(","));
			}
			data = workhourService.dailyWorkhourDetail(vehicleWorkPOJO, pageSelect);
			@SuppressWarnings("unchecked")
			List<Map<String, String>> list =(List<Map<String, String>>) data.get("rows");
			for(Map<String, String> a:list){
				values.add(new Object[] {
				a.get("VEHICLEDEF"),
				a.get("DATE_YMD"),
				a.get("DEALERNAME"),
				a.get("MODELNAME"),
				a.get("WORKED_HOURS")
				});
			}
			String[] headers = new String[] { "整机编号","日期","经销商","机械型号","当日工作时间(小时)"};
			super.renderExcel("累计工作时间详情" + ".xls", headers, values);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		renderObject(data);
		return NONE;
	}
	@Override
	public VehicleWorkPOJO getModel() {
		return vehicleWorkPOJO;
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

	public PageSelect getPageSelect() {
		return pageSelect;
	}

	public void setPageSelect(PageSelect pageSelect) {
		this.pageSelect = pageSelect;
	}

}
