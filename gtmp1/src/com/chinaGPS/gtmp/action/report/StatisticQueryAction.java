package com.chinaGPS.gtmp.action.report;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.cglib.beans.BeanMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.TextAnchor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.chinaGPS.gtmp.action.common.BaseAction;
import com.chinaGPS.gtmp.entity.AvgWorkTime;
import com.chinaGPS.gtmp.entity.DepartPOJO;
import com.chinaGPS.gtmp.entity.DynamicColumn;
import com.chinaGPS.gtmp.entity.DynamicMalfunctionPOJO;
import com.chinaGPS.gtmp.entity.RolePOJO;
import com.chinaGPS.gtmp.entity.UserPOJO;
import com.chinaGPS.gtmp.entity.VehicleUnitPOJO;
import com.chinaGPS.gtmp.entity.VehicleWorkPOJO;
import com.chinaGPS.gtmp.service.IDealerAreaService;
import com.chinaGPS.gtmp.service.IStatisticQueryService;
import com.chinaGPS.gtmp.util.Constants;
import com.chinaGPS.gtmp.util.DateUtil;
import com.chinaGPS.gtmp.util.StringUtils;
import com.chinaGPS.gtmp.util.page.PageSelect;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Package:com.chinaGPS.gtmp.action.report
 * @ClassName:StatisticQueryAction
 * @Description:
 * @author:zfy
 * @date:2013-5-10 下午05:24:00
 */
@Scope("prototype")
@Controller
public class StatisticQueryAction extends BaseAction implements ModelDriven<VehicleWorkPOJO> {
	private static final long serialVersionUID = 7918012024727537369L;
	private static Logger logger = LoggerFactory.getLogger(StatisticQueryAction.class);
	
	@Resource
    private VehicleWorkPOJO vehicleWorkPOJO;
    @Resource
    private IStatisticQueryService statisticQueryService;
    @Resource
    private IDealerAreaService dealerAreaService;
    @Resource
    private PageSelect pageSelect;
    
    private int page;
    private int rows;
    //图表
    private JFreeChart chart;
    private String startDateStr;
    private String endDateStr;
    
    /**
     * @Title:getUnWorkByPage
     * @Description:未工作统计
     * @throws
     */
    public void getUnWorkByPage() {
    	HashMap<String, Object> result = new HashMap<String, Object>();
		try {
			pageSelect.setPage(page);
			pageSelect.setRows(rows);
			if (org.apache.commons.lang.StringUtils.isNotEmpty(vehicleWorkPOJO.getDealerId())) {
				vehicleWorkPOJO.setDealerIds(StringUtils.split(vehicleWorkPOJO.getDealerId(), ','));
			}

			result = statisticQueryService.getUnWorkByPage(vehicleWorkPOJO, pageSelect);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(result);
	}
    
    /**
     * @Title:getUnUploadByPage
     * @Description:未上报统计
     * @throws
     */
    public void getUnUploadByPage() {
		HashMap<String, Object> result = new HashMap<String, Object>();

		try {
			pageSelect.setPage(page);
			pageSelect.setRows(rows);
			if (org.apache.commons.lang.StringUtils.isNotEmpty(vehicleWorkPOJO.getDealerId())) {
				vehicleWorkPOJO.setDealerIds(StringUtils.split(vehicleWorkPOJO.getDealerId(), ','));
			}
			result = statisticQueryService.getUnUploadByPage(vehicleWorkPOJO,
					pageSelect);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(result);
	}
    
    /**
	 * @Title:exportToExcelUnWork
	 * @Description:未工作机械导出
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws
	 */
    public String exportToExcelUnWork() {
		List<Object[]> values = new ArrayList<Object[]>();
		try {
			vehicleWorkPOJO.setVehicleDef(URLDecoder.decode(vehicleWorkPOJO.getVehicleDef()));
			if (org.apache.commons.lang.StringUtils.isNotEmpty(vehicleWorkPOJO.getDealerId())) {
				vehicleWorkPOJO.setDealerIds(StringUtils.split(vehicleWorkPOJO.getDealerId(), ','));
			}
			pageSelect.setPage(1);
			pageSelect.setRows(Integer.MAX_VALUE);
			Map map = statisticQueryService.getUnWorkByPage(vehicleWorkPOJO, pageSelect);
			List<VehicleWorkPOJO> list = (List<VehicleWorkPOJO>) map.get("rows");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			int i=0;
			for (VehicleWorkPOJO vehicleWorkPOJO1 : list) {
				i++;
				String lastworkdate =vehicleWorkPOJO1.getLastWorkDate()==null?"":sdf.format(vehicleWorkPOJO1.getLastWorkDate());
				values.add(new Object[] {
						i,
						vehicleWorkPOJO1.getVehicleDef(),
						vehicleWorkPOJO1.getDealerName(),
						vehicleWorkPOJO1.getAreaName(),
						vehicleWorkPOJO1.getVehicleStatus(),
						vehicleWorkPOJO1.getModelName(),
						vehicleWorkPOJO1.getVehicleCode(),
						vehicleWorkPOJO1.getVehicleArg(),
						vehicleWorkPOJO1.getOwnerName(),
						vehicleWorkPOJO1.getOwnerPhoneNumber(),
						lastworkdate,
						vehicleWorkPOJO1.getLon(), 
						vehicleWorkPOJO1.getLat(),
						vehicleWorkPOJO1.getSpeed(),
						vehicleWorkPOJO1.getReferencePosition() });
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		String[] headers = new String[] {"序号", "整机编号", "经销商","区域","机械状态","机型","机器代号", "配置号", "客户姓名", "客户联系电话", "最后工作时间", "经度", "纬度", "速度", "参考位置" };
		
		super.renderExcel("未工作机械查询" + ".xls", headers, values);
		return null;
	}
    
    /**
     * @Title:exportToExcelUnWork
     * @Description:未上报机械导出
     * @return
     * @throws UnsupportedEncodingException
     * @throws
     */
    public String exportToExcelUnUpload() {
		List<Object[]> values = new ArrayList<Object[]>();
		try {
			vehicleWorkPOJO.setVehicleDef(URLDecoder.decode(vehicleWorkPOJO.getVehicleDef()));
			if (org.apache.commons.lang.StringUtils.isNotEmpty(vehicleWorkPOJO.getDealerId())) {
				vehicleWorkPOJO.setDealerIds(StringUtils.split(vehicleWorkPOJO.getDealerId(), ','));
			}
			pageSelect.setPage(1);
			pageSelect.setRows(Integer.MAX_VALUE);
			Map map = statisticQueryService.getUnUploadByPage(vehicleWorkPOJO, pageSelect);
			List<VehicleWorkPOJO> list = (List<VehicleWorkPOJO>) map.get("rows");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int i = 0;
			for (VehicleWorkPOJO vehicleWorkPOJO1 : list) {
				i++;
				String gpstime =vehicleWorkPOJO1.getGpsTime()==null?"":sdf.format(vehicleWorkPOJO1.getGpsTime());
				values.add(new Object[] {
						i,
						vehicleWorkPOJO1.getVehicleDef(),
						vehicleWorkPOJO1.getDealerName(),
						vehicleWorkPOJO1.getAreaName(),
						vehicleWorkPOJO1.getVehicleStatus(),
						vehicleWorkPOJO1.getModelName(),
						vehicleWorkPOJO1.getVehicleCode(),
						vehicleWorkPOJO1.getVehicleArg(),
						vehicleWorkPOJO1.getOwnerName(),
						vehicleWorkPOJO1.getOwnerPhoneNumber(),
						gpstime,
						vehicleWorkPOJO1.getLon(), 
						vehicleWorkPOJO1.getLat(),
						vehicleWorkPOJO1.getSpeed(),
						vehicleWorkPOJO1.getReferencePosition() });
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		String[] headers = new String[] { "序号","整机编号", "经销商","区域","机械状态","机型","机器代号", "配置号", "客户姓名", "客户联系电话", "定位时间", "经度", "纬度", "速度", "参考位置" };
		super.renderExcel("未上报机械查询" + ".xls", headers, values);
		return null;
	}
    /**
     * @throws Exception 
     * @Title:statisticTimeQuantum
     * @Description:机械工作时间段统计
     * @throws
     */
    public String statisticTimeQuantum() throws Exception {
    	List<AvgWorkTime> resultList = new ArrayList<AvgWorkTime>();    	
    	HashMap map = new HashMap();
		setConditionMap(map, vehicleWorkPOJO);
		map.put("modelNameId", vehicleWorkPOJO.getModelNameId());
		map.put("vehicleCode", vehicleWorkPOJO.getVehicleCode());
		map.put("vehicleArg", vehicleWorkPOJO.getVehicleArg());
		if(vehicleWorkPOJO.getVehicleStatus()!=null && !"".equals(vehicleWorkPOJO.getVehicleStatus())){
			map.put("vehicleStatus", vehicleWorkPOJO.getVehicleStatus());
		}
		resultList = statisticQueryService.statisticTimeQuantum(map);
		renderObject(resultList);
		
		
		return NONE;
    }
/*    public void statisticTimeQuantum() {
		List<WorkTimeHours> resultList = new ArrayList<WorkTimeHours>();
		WorkTimeHours workTimeHours = new WorkTimeHours();
		try {
			List<HashMap> resultList1 = new ArrayList<HashMap>();
			List<HashMap> resultList2 = new ArrayList<HashMap>();
			List<HashMap> resultList3 = new ArrayList<HashMap>();
			List<HashMap> resultList4 = new ArrayList<HashMap>();
			List<HashMap> resultList5 = new ArrayList<HashMap>();
			List<HashMap> resultList6 = new ArrayList<HashMap>();
			List<HashMap> resultList7 = new ArrayList<HashMap>();
			Object vountToInt1 = null;
			Object vountToInt2 = null;
			Object vountToInt3 = null;
			Object vountToInt4 = null;
			Object vountToInt5 = null;
			Object vountToInt6 = null;
			Object vountToInt7 = null;
			HashMap map = new HashMap();
			if(vehicleWorkPOJO.getVehicleCode()==null || "null".equals(vehicleWorkPOJO.getVehicleCode())){
				vehicleWorkPOJO.setVehicleCode("");
			}
			if(vehicleWorkPOJO.getVehicleArg()==null || "null".equals(vehicleWorkPOJO.getVehicleArg())){
				vehicleWorkPOJO.setVehicleArg("");
			}
			map.put("dealerId", vehicleWorkPOJO.getDealerId());
			map.put("modelName", vehicleWorkPOJO.getModelName());
			map.put("vehicleCode", vehicleWorkPOJO.getVehicleCode());
			map.put("vehicleArg", vehicleWorkPOJO.getVehicleArg());
			//有7种时间区分 100 200 500 1000 2000 3000 3000以一
			map.put("workTime", 100);
			 resultList1 = statisticQueryService.statisticTimeQuantum(map);
			 for (HashMap map1 : resultList1) {
				  vountToInt1 = map1.get("VCOUNT"); 
				  workTimeHours = new WorkTimeHours();
				  workTimeHours.setTimeQuantum("100");
				  workTimeHours.setVcount(vountToInt1.toString());
				  resultList.add(workTimeHours);
			 }
			map.put("workTime", 200);
			 resultList2 = statisticQueryService.statisticTimeQuantum(map);
			 for (HashMap map2 : resultList2) {
				  vountToInt2 =  map2.get("VCOUNT"); 
				  workTimeHours = new WorkTimeHours();
				  workTimeHours.setTimeQuantum("100-200");
				  workTimeHours.setVcount(vountToInt2.toString());
				  resultList.add(workTimeHours);
			 }
			map.put("workTime", 500);
			 resultList3 = statisticQueryService.statisticTimeQuantum(map);
			 for (HashMap map3 : resultList3) {
				  vountToInt3 = (Object) map3.get("VCOUNT"); 
				  workTimeHours = new WorkTimeHours();
				  workTimeHours.setTimeQuantum("200-500");
				  workTimeHours.setVcount(vountToInt3.toString());
				  resultList.add(workTimeHours);
			 }
			map.put("workTime", 1000);
			 resultList4 = statisticQueryService.statisticTimeQuantum(map);
			 for (HashMap map4 : resultList4) {
				  vountToInt4 =  map4.get("VCOUNT"); 
				  workTimeHours = new WorkTimeHours();
				  workTimeHours.setTimeQuantum("500-1000");
				  workTimeHours.setVcount(vountToInt4.toString());
				  resultList.add(workTimeHours);
			 }
			map.put("workTime", 2000);
			 resultList5 = statisticQueryService.statisticTimeQuantum(map);
			 for (HashMap map5 : resultList5) {
				  vountToInt5 =  map5.get("VCOUNT"); 
				  workTimeHours = new WorkTimeHours();
				  workTimeHours.setTimeQuantum("1000-2000");
				  workTimeHours.setVcount(vountToInt5.toString());
				  resultList.add(workTimeHours);
			 }
			map.put("workTime", 3000);
			 resultList6 = statisticQueryService.statisticTimeQuantum(map);
			 for (HashMap map6 : resultList6) {
				  vountToInt6 =  map6.get("VCOUNT"); 
				  workTimeHours = new WorkTimeHours();
				  workTimeHours.setTimeQuantum("2000-3000");
				  workTimeHours.setVcount(vountToInt6.toString());
				  resultList.add(workTimeHours);
			 }
			map.put("workTime", 3001);
			 resultList7 = statisticQueryService.statisticTimeQuantum(map);
			 for (HashMap map7 : resultList7) {
				  vountToInt7 =  map7.get("VCOUNT"); 
				  workTimeHours = new WorkTimeHours();
				  workTimeHours.setTimeQuantum("3000以上");
				  workTimeHours.setVcount(vountToInt7.toString());
				  resultList.add(workTimeHours);
			 }
			//map.put("workTime", vehicleWorkPOJO.getWorkTime());
			//resultList = statisticQueryService.statisticTimeQuantum(map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(resultList);
	}*/
    
    /**
     * @throws Exception 
	 * @Title:exportToExcelUnWork
	 * @Description:机械工作时间段导出
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws
	 */
    public String exportToExcelTimeQuantum() throws Exception {
    	List<Object[]> values = new ArrayList<Object[]>();
    	List<AvgWorkTime> resultList = new ArrayList<AvgWorkTime>();    
    	HashMap map = new HashMap();
    	setConditionMap(map, vehicleWorkPOJO);
		map.put("modelNameId", vehicleWorkPOJO.getModelNameId());
		map.put("vehicleCode", vehicleWorkPOJO.getVehicleCode());
		map.put("vehicleArg", vehicleWorkPOJO.getVehicleArg());
		if(vehicleWorkPOJO.getVehicleStatus()!=null && !"".equals(vehicleWorkPOJO.getVehicleArg())){
			map.put("vehicleStatus", vehicleWorkPOJO.getVehicleStatus());
		}
		resultList = statisticQueryService.statisticTimeQuantum(map);		            
		int i = 0;
		for (AvgWorkTime pojo : resultList) {
			i++;
			values.add(new Object[] { 
					i,
					pojo.getModelName(),
					pojo.getaCount(),
					pojo.getbCount(),
					pojo.getcCount(),
					pojo.getdCount(),
					pojo.geteCount(),
					pojo.getfCount(),
					pojo.getgCount()
					});
		 
	 }
		String[] headers = new String[] {"序号", "机械类型","0-100(小时)","100-200(小时)","200-500(小时)","500-1000(小时)","1000-2000(小时)","2000-3000(小时)","3000以上(小时)" };
		super.renderExcel("机械工作时间段统计" + ".xls", headers, values);
		return null;
    	
    	
    	/*
		List<Object[]> values = new ArrayList<Object[]>();
		List<WorkTimeHours> resultList = new ArrayList<WorkTimeHours>();
		WorkTimeHours workTimeHours = new WorkTimeHours();
		try {
			List<HashMap> resultList1 = new ArrayList<HashMap>();
			List<HashMap> resultList2 = new ArrayList<HashMap>();
			List<HashMap> resultList3 = new ArrayList<HashMap>();
			List<HashMap> resultList4 = new ArrayList<HashMap>();
			List<HashMap> resultList5 = new ArrayList<HashMap>();
			List<HashMap> resultList6 = new ArrayList<HashMap>();
			List<HashMap> resultList7 = new ArrayList<HashMap>();
			Object vountToInt1 = null;
			Object vountToInt2 = null;
			Object vountToInt3 = null;
			Object vountToInt4 = null;
			Object vountToInt5 = null;
			Object vountToInt6 = null;
			Object vountToInt7 = null;
			HashMap map = new HashMap();
			if(vehicleWorkPOJO.getVehicleCode()==null || "null".equals(vehicleWorkPOJO.getVehicleCode())){
				vehicleWorkPOJO.setVehicleCode("");
			}
			if(vehicleWorkPOJO.getVehicleArg()==null || "null".equals(vehicleWorkPOJO.getVehicleArg())){
				vehicleWorkPOJO.setVehicleArg("");
			}
			map.put("dealerId", vehicleWorkPOJO.getDealerId());
			map.put("modelName", vehicleWorkPOJO.getModelName());
			map.put("vehicleCode", vehicleWorkPOJO.getVehicleCode());
			map.put("vehicleArg", vehicleWorkPOJO.getVehicleArg());
			//有7种时间区分 100 200 500 1000 2000 3000 3000以一
			map.put("workTime", 100);
			 resultList1 = statisticQueryService.statisticTimeQuantum(map);
			 for (HashMap map1 : resultList1) {
				  vountToInt1 = map1.get("VCOUNT"); 
				  workTimeHours = new WorkTimeHours();
				  workTimeHours.setTimeQuantum("100");
				  workTimeHours.setVcount(vountToInt1.toString());
				  resultList.add(workTimeHours);
			 }
			map.put("workTime", 200);
			 resultList2 = statisticQueryService.statisticTimeQuantum(map);
			 for (HashMap map2 : resultList2) {
				  vountToInt2 =  map2.get("VCOUNT"); 
				  workTimeHours = new WorkTimeHours();
				  workTimeHours.setTimeQuantum("100-200");
				  workTimeHours.setVcount(vountToInt2.toString());
				  resultList.add(workTimeHours);
			 }
			map.put("workTime", 500);
			 resultList3 = statisticQueryService.statisticTimeQuantum(map);
			 for (HashMap map3 : resultList3) {
				  vountToInt3 = (Object) map3.get("VCOUNT"); 
				  workTimeHours = new WorkTimeHours();
				  workTimeHours.setTimeQuantum("200-500");
				  workTimeHours.setVcount(vountToInt3.toString());
				  resultList.add(workTimeHours);
			 }
			map.put("workTime", 1000);
			 resultList4 = statisticQueryService.statisticTimeQuantum(map);
			 for (HashMap map4 : resultList4) {
				  vountToInt4 =  map4.get("VCOUNT"); 
				  workTimeHours = new WorkTimeHours();
				  workTimeHours.setTimeQuantum("500-1000");
				  workTimeHours.setVcount(vountToInt4.toString());
				  resultList.add(workTimeHours);
			 }
			map.put("workTime", 2000);
			 resultList5 = statisticQueryService.statisticTimeQuantum(map);
			 for (HashMap map5 : resultList5) {
				  vountToInt5 =  map5.get("VCOUNT"); 
				  workTimeHours = new WorkTimeHours();
				  workTimeHours.setTimeQuantum("1000-2000");
				  workTimeHours.setVcount(vountToInt5.toString());
				  resultList.add(workTimeHours);
			 }
			map.put("workTime", 3000);
			 resultList6 = statisticQueryService.statisticTimeQuantum(map);
			 for (HashMap map6 : resultList6) {
				  vountToInt6 =  map6.get("VCOUNT"); 
				  workTimeHours = new WorkTimeHours();
				  workTimeHours.setTimeQuantum("2000-3000");
				  workTimeHours.setVcount(vountToInt6.toString());
				  resultList.add(workTimeHours);
			 }
			map.put("workTime", 3001);
			 resultList7 = statisticQueryService.statisticTimeQuantum(map);
			 for (HashMap map7 : resultList7) {
				  vountToInt7 =  map7.get("VCOUNT"); 
				  workTimeHours = new WorkTimeHours();
				  workTimeHours.setTimeQuantum("3000以上");
				  workTimeHours.setVcount(vountToInt7.toString());
				  resultList.add(workTimeHours);
			 }
			 for (WorkTimeHours pojo : resultList) {
					values.add(new Object[] { 
							pojo.getTimeQuantum(),
							pojo.getVcount()
							});
				 
			 }
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		String[] headers = new String[] { "时间段(小时)", "机械数量" };
		super.renderExcel("机械工作时间段统计" + ".xls", headers, values);*/
	}
    
    /**
	   * @Title:drawChart
	   * @Description:图表
	   * @return
	   * @throws
	   */
	public String drawChart4TimeQuantum() {
		try {
			chart = createChart4TimeQuantum(createDataset4TimeQuantum());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	//机械工作时间段图片导出
	public void downloadChart() {
		try {
			chart = createChart4TimeQuantum(createDataset4TimeQuantum());
			HttpServletResponse resp = BaseAction.getResponse();
			resp.setContentType("application/octet-stream");
			resp.setHeader("Content-Disposition", "attachment; filename=vehicle_workType_time.png");
			ChartUtilities.writeChartAsPNG(resp.getOutputStream(), chart, 1024, 768);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	//平均工作时间统计图片导出
	public void downloadChart1() {
		try {
			chart = createChart4AvgWorkHours(createDataSet4AvgWorkHours());
			HttpServletResponse resp = BaseAction.getResponse();
			resp.setContentType("application/octet-stream");
			resp.setHeader("Content-Disposition", "attachment; filename=avg_workTime.png");
			ChartUtilities.writeChartAsPNG(resp.getOutputStream(), chart, 1024, 768);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	//机械工作时间统计图片导出
	public void downloadChart2() {
		try {
			chart = createChart4WorkHours(createDataSet4WorkHours());
			HttpServletResponse resp = BaseAction.getResponse();
			resp.setContentType("application/octet-stream");
			resp.setHeader("Content-Disposition", "attachment; filename=avg_workTime.png");
			ChartUtilities.writeChartAsPNG(resp.getOutputStream(), chart, 1024, 768);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	
	
	private CategoryDataset createDataset4TimeQuantum() throws Exception {
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		List<Object[]> values = new ArrayList<Object[]>();
    	List<AvgWorkTime> resultList = new ArrayList<AvgWorkTime>();    
    	HashMap map = new HashMap();
    	setConditionMap(map, vehicleWorkPOJO);
		map.put("modelNameId", vehicleWorkPOJO.getModelNameId());
		map.put("vehicleCode", vehicleWorkPOJO.getVehicleCode());
		map.put("vehicleArg", vehicleWorkPOJO.getVehicleArg());
		if(vehicleWorkPOJO.getVehicleStatus()!=null && !"".equals(vehicleWorkPOJO.getVehicleArg())){
			map.put("vehicleStatus", vehicleWorkPOJO.getVehicleStatus());
		}
		resultList = statisticQueryService.statisticTimeQuantum(map);
		for (AvgWorkTime object : resultList) {
			String acount = object.getaCount();
			String bcount = object.getbCount();
			String ccount = object.getcCount();
			String dcount = object.getdCount();
			String ecount = object.geteCount();
			String fcount = object.getfCount();
			String gcount = object.getgCount();
			String typeName = object.getModelName();
			
		    defaultcategorydataset.addValue(Integer.parseInt(acount), typeName, "0-100");
		    defaultcategorydataset.addValue(Integer.parseInt(bcount), typeName, "100-200");
		    defaultcategorydataset.addValue(Integer.parseInt(ccount), typeName, "200-500");
		    defaultcategorydataset.addValue(Integer.parseInt(dcount), typeName, "500-1000");
		    defaultcategorydataset.addValue(Integer.parseInt(ecount), typeName, "1000-2000");
		    defaultcategorydataset.addValue(Integer.parseInt(fcount), typeName, "2000-3000");
		    defaultcategorydataset.addValue(Integer.parseInt(gcount), typeName, "3000以上");
		}
		
		
		
		return defaultcategorydataset;
		/*DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		List<Object[]> values = new ArrayList<Object[]>();
		List<HashMap> resultList1 = new ArrayList<HashMap>();
		List<HashMap> resultList2 = new ArrayList<HashMap>();
		List<HashMap> resultList3 = new ArrayList<HashMap>();
		List<HashMap> resultList4 = new ArrayList<HashMap>();
		List<HashMap> resultList5 = new ArrayList<HashMap>();
		List<HashMap> resultList6 = new ArrayList<HashMap>();
		List<HashMap> resultList7 = new ArrayList<HashMap>();
		Object vountToInt1 = null;
		Object vountToInt2 = null;
		Object vountToInt3 = null;
		Object vountToInt4 = null;
		Object vountToInt5 = null;
		Object vountToInt6 = null;
		Object vountToInt7 = null;
		HashMap map = new HashMap();
		if(vehicleWorkPOJO.getVehicleCode()==null || "null".equals(vehicleWorkPOJO.getVehicleCode())){
			vehicleWorkPOJO.setVehicleCode("");
		}
		if(vehicleWorkPOJO.getVehicleArg()==null || "null".equals(vehicleWorkPOJO.getVehicleArg())){
			vehicleWorkPOJO.setVehicleArg("");
		}
		map.put("dealerId", vehicleWorkPOJO.getDealerId());
		map.put("modelName", vehicleWorkPOJO.getModelName());
		map.put("vehicleCode", vehicleWorkPOJO.getVehicleCode());
		map.put("vehicleArg", vehicleWorkPOJO.getVehicleArg());
		//有7种时间区分 100 200 500 1000 2000 3000 3000以一
		map.put("workTime", 100);
		 resultList1 = statisticQueryService.statisticTimeQuantum(map);
		 for (HashMap map1 : resultList1) {
			  vountToInt1 = map1.get("VCOUNT"); 
		 }
		map.put("workTime", 200);
		 resultList2 = statisticQueryService.statisticTimeQuantum(map);
		 for (HashMap map2 : resultList2) {
			  vountToInt2 =  map2.get("VCOUNT"); 
		 }
		map.put("workTime", 500);
		 resultList3 = statisticQueryService.statisticTimeQuantum(map);
		 for (HashMap map3 : resultList3) {
			  vountToInt3 = (Object) map3.get("VCOUNT"); 
		 }
		map.put("workTime", 1000);
		 resultList4 = statisticQueryService.statisticTimeQuantum(map);
		 for (HashMap map4 : resultList4) {
			  vountToInt4 =  map4.get("VCOUNT"); 
		 }
		map.put("workTime", 2000);
		 resultList5 = statisticQueryService.statisticTimeQuantum(map);
		 for (HashMap map5 : resultList5) {
			  vountToInt5 =  map5.get("VCOUNT"); 
		 }
		map.put("workTime", 3000);
		 resultList6 = statisticQueryService.statisticTimeQuantum(map);
		 for (HashMap map6 : resultList6) {
			  vountToInt6 =  map6.get("VCOUNT"); 
		 }
		map.put("workTime", 3001);
		 resultList7 = statisticQueryService.statisticTimeQuantum(map);
		 for (HashMap map7 : resultList7) {
			  vountToInt7 =  map7.get("VCOUNT"); 
		 }
		defaultcategorydataset.addValue((Number)vountToInt1, "Classes", "0-100");
		defaultcategorydataset.addValue((Number)vountToInt2, "Classes", "100-200");
		defaultcategorydataset.addValue((Number)vountToInt3, "Classes", "200-500");
		defaultcategorydataset.addValue((Number)vountToInt4, "Classes", "500-1000");
		defaultcategorydataset.addValue((Number)vountToInt5, "Classes", "1000-2000");
		defaultcategorydataset.addValue((Number)vountToInt6, "Classes", "2000-3000");
		defaultcategorydataset.addValue((Number)vountToInt7, "Classes", "3000以上");
		return defaultcategorydataset;*/
		

	}

	private JFreeChart createChart4TimeQuantum(CategoryDataset categorydataset) {
		// 解决中文乱码问题
		// 创建主题样式
	 	StandardChartTheme mChartTheme = new StandardChartTheme("CN");
	 	// 设置标题字体
	 	mChartTheme.setExtraLargeFont(new Font("黑体", Font.BOLD, 14));
	 	// 设置轴向字体
	 	mChartTheme.setLargeFont(new Font("宋体", Font.CENTER_BASELINE, 12));
	 	// 设置图例字体
	 	mChartTheme.setRegularFont(new Font("宋体", Font.CENTER_BASELINE, 12));
	 	// 应用主题样式
	 	ChartFactory.setChartTheme(mChartTheme);
		

		//JFreeChart jfreechart = ChartFactory.createStackedBarChart("机械工作时间段统计",
	 	JFreeChart jfreechart = ChartFactory.createLineChart("机械工作时间段统计",
				"时间段(小时)", "机械数量", categorydataset, PlotOrientation.VERTICAL,
				true, true, false);
	 	jfreechart.setBackgroundPaint(Color.white);
		CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
		categoryplot.setBackgroundPaint(Color.lightGray);
		categoryplot.setRangeGridlinePaint(Color.white);
		// 年月日纵向排列
		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
		categoryaxis.setLowerMargin(0.02D);
		categoryaxis.setUpperMargin(0.02D);
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);

		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		// 设置整数
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); 
	 	  /*CategoryPlot plot = jfreechart.getCategoryPlot();
		  NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		  rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		  rangeAxis.setAutoRangeIncludesZero(true);
		  rangeAxis.setUpperMargin(0.20);
		  rangeAxis.setLabelAngle(Math.PI / 2.0);
		  //加上这个就可以变窄
		  BarRenderer barrenderer = new BarRenderer();
		  barrenderer.setMaximumBarWidth(0.1);
		  barrenderer.setMinimumBarLength(0.1);
		  plot.setRenderer(barrenderer);*/
		  return jfreechart;
		
		
		
		/*jfreechart.setBackgroundPaint(Color.white);
		CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
		categoryplot.setBackgroundPaint(Color.lightGray);
		categoryplot.setRangeGridlinesVisible(false);
		// 年月日纵向排列
		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
		categoryaxis.setLowerMargin(0.02D);
		categoryaxis.setUpperMargin(0.02D);
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);

		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot.getRenderer();
		// lineandshaperenderer.setShapesVisible(true);
		lineandshaperenderer.setDrawOutlines(true);
		lineandshaperenderer.setUseFillPaint(true);
		lineandshaperenderer.setBaseFillPaint(Color.white);
		lineandshaperenderer.setSeriesStroke(0, new BasicStroke(3F));
		lineandshaperenderer.setSeriesOutlineStroke(0, new BasicStroke(2.0F));
		lineandshaperenderer.setSeriesShape(0,new java.awt.geom.Ellipse2D.Double(-5D, -5D, 10D, 10D));
		return jfreechart;*/
	}
	
    /**
	 * @Title:getTimeQuanDetailPage
	 * @Description:获得工作时间段统计详细信息
	 * @throws
	 */
	public void getTimeQuanDetailPage() {
		HashMap<String, Object> result = new HashMap<String, Object>();

		try {
			pageSelect.setPage(page);
			pageSelect.setRows(rows);
			HashMap map = new HashMap();
			setConditionMap(map, vehicleWorkPOJO);
			result = statisticQueryService.getTimeQuanDetailPage(vehicleWorkPOJO, pageSelect);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(result);
	}

	/**
	 * @Title:getWorkedYearsDetailPage
	 * @Description:获得使用年数详细信息
	 * @throws
	 */
	public void getWorkedYearsDetailPage() {
		HashMap<String, Object> result = new HashMap<String, Object>();

		try {
			pageSelect.setPage(page);
			pageSelect.setRows(rows);
			result = statisticQueryService.getWorkedYearsDetailPage(vehicleWorkPOJO, pageSelect);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(result);
	} 
	    
	/**
	 * @Title:statisticAvgWorkHours
	 * @Description:平均工作时间统计
	 * @throws Exception
	 * @throws
	 */
	public void statisticAvgWorkHours() throws Exception {
		// 动态列
		// 条件
		HashMap conditionMap = new HashMap();
		setConditionMap(conditionMap,vehicleWorkPOJO);
		conditionMap.put("modelTypeId", vehicleWorkPOJO.getModelName());
		if(vehicleWorkPOJO.getVehicleStatus()!=null && !"".equals(vehicleWorkPOJO.getVehicleArg())){
			conditionMap.put("vehicleStatus", vehicleWorkPOJO.getVehicleStatus());
		}
		/*if("全部".equals(vehicleWorkPOJO.getVehicleCode())){
			vehicleWorkPOJO.setVehicleCode("");
		}if("全部".equals(vehicleWorkPOJO.getVehicleArg())){
			vehicleWorkPOJO.setVehicleArg("");
		}*/
		LinkedHashMap propertyMap = new LinkedHashMap();
		propertyMap.put("typeId", Class.forName("java.lang.String"));
		propertyMap.put("typeName", Class.forName("java.lang.String"));
		/*propertyMap.put("vehicleCode", Class.forName("java.lang.String"));
		propertyMap.put("vehicleArg", Class.forName("java.lang.String"));*/
		propertyMap.put("dateLabel", Class.forName("java.lang.String"));
		String beginDateStr = null;
		String[] columnsNameArray = null;
		int count = 0;
		String columnName = null;
		if (StringUtils.isNotBlank(endDateStr) && StringUtils.isNotBlank(startDateStr)) {// 只出现这几个月的信息
			HashSet<String> monthSet = com.chinaGPS.gtmp.util.DateUtil.getAllMonthList(startDateStr, endDateStr);
			conditionMap.put("startTime", DateUtil.parse(startDateStr + "-01",DateUtil.YMD_DASH));
			Date date = new Date();
			date.setYear(Integer.parseInt(endDateStr.split("-")[0]) - 1900);
			date.setMonth(Integer.parseInt(endDateStr.split("-")[1]) - 1);
			date.setHours(23);
			date.setMinutes(59);
			date.setSeconds(59);
			conditionMap.put("endTime", date);
			count = monthSet.size();
			columnsNameArray = new String[count];
			int i = 1;
			for (String item : monthSet) {
				columnName = "m"+ String.format("%02d", Integer.parseInt(item));
				columnsNameArray[i - 1] = columnName;
				i++;
				propertyMap.put(columnName, Class.forName("java.lang.String"));
			}
		} else {
			count = 12;
			columnsNameArray = new String[count];
			for (int i = 1; i <= count; i++) {
				columnName = "m" + String.format("%02d", i);
				columnsNameArray[i - 1] = columnName;
				propertyMap.put(columnName, Class.forName("java.lang.String"));
			}
		}

		Set set = propertyMap.keySet();
		Iterator it = set.iterator();
		DynamicColumn columnMap = null;
		List<DynamicColumn> fieldsList = new ArrayList<DynamicColumn>();
		DynamicColumn dynamicCcolumn = null;
		String column = null;
		String type = "机械类型";
		//String vehicleCode = "机械代号";
		//String vehicleArg = "机械配置";
		String dateLabel = "年";
		while (it.hasNext()) {
			column = String.valueOf(it.next());
			dynamicCcolumn = new DynamicColumn();
			dynamicCcolumn.setField(column);
			dynamicCcolumn.setWidth(100);
			if ("typeId".equals(column)) {
				dynamicCcolumn.setHidden(true);
				// columnMap.put("header", vehicleDef);
			} else if ("typeName".equals(column)) {
				dynamicCcolumn.setTitle(type);
				// columnMap.put("header", vehicleDef);
			}/*else if ("vehicleCode".equals(column)) {
				dynamicCcolumn.setTitle(vehicleCode);
				// columnMap.put("header", vehicleDef);
			}else if ("vehicleArg".equals(column)) {
				dynamicCcolumn.setTitle(vehicleArg);
				// columnMap.put("header", vehicleDef);
			}*/else if ("dateLabel".equals(column)) {
				dynamicCcolumn.setTitle(dateLabel);
			} else {
				dynamicCcolumn.setTitle(column.substring(1) + "月");
			}
			fieldsList.add(dynamicCcolumn);
		}
		HashMap<String, Object> result = new HashMap<String, Object>();
		conditionMap.put("columns", columnsNameArray);
		conditionMap.put("modelTypeId", vehicleWorkPOJO.getModelName());
		//conditionMap.put("vehicleCode", vehicleWorkPOJO.getVehicleCode());
		//conditionMap.put("vehicleArg", vehicleWorkPOJO.getVehicleArg());
		conditionMap.put("dealerIds", vehicleWorkPOJO.getDealerIds());
		
		List<Object> list = statisticQueryService.statisticAvgWorkHours(conditionMap, propertyMap);
		result.put("rows", list);
		result.put("total", 0);
		List columnsListWrap = new ArrayList();
		columnsListWrap.add(fieldsList);
		result.put("columns", columnsListWrap);
		renderObject(result);
	}
	
	public void exportToExcelAvgWorkHours() throws Exception {

		// 条件
		HashMap conditionMap = new HashMap();
		conditionMap.put("modelTypeId", vehicleWorkPOJO.getModelName());
		setConditionMap(conditionMap,vehicleWorkPOJO);
		if(vehicleWorkPOJO.getVehicleStatus()!=null && !"".equals(vehicleWorkPOJO.getVehicleArg())){
			conditionMap.put("vehicleStatus", vehicleWorkPOJO.getVehicleStatus());
		}
		LinkedHashMap propertyMap = new LinkedHashMap();
		propertyMap.put("typeName", Class.forName("java.lang.String"));
		//propertyMap.put("vehicleCode", Class.forName("java.lang.String"));
		//propertyMap.put("vehicleArg", Class.forName("java.lang.String"));
		propertyMap.put("dateLabel", Class.forName("java.lang.String"));
		String beginDateStr = null;
		String[] columnsNameArray = null;
		int count = 0;
		String columnName = null;
		if (StringUtils.isNotBlank(endDateStr)
				&& StringUtils.isNotBlank(startDateStr)) {// 只出现这几个月的信息
			HashSet<String> monthSet = com.chinaGPS.gtmp.util.DateUtil
					.getAllMonthList(startDateStr, endDateStr);
			conditionMap.put("startTime", DateUtil.parse(startDateStr + "-01",
					DateUtil.YMD_DASH));
			Date date = new Date();
			date.setYear(Integer.parseInt(endDateStr.split("-")[0]) - 1900);
			date.setMonth(Integer.parseInt(endDateStr.split("-")[1]) - 1);
			date.setHours(23);
			date.setMinutes(59);
			date.setSeconds(59);
			conditionMap.put("endTime", date);
			count = monthSet.size();
			columnsNameArray = new String[count];
			int i = 1;
			for (String item : monthSet) {
				columnName = "m"
						+ String.format("%02d", Integer.parseInt(item));
				columnsNameArray[i - 1] = columnName;
				i++;
				propertyMap.put(columnName, Class.forName("java.lang.String"));
			}
		} else {
			count = 12;
			columnsNameArray = new String[count];
			for (int i = 1; i <= count; i++) {
				columnName = "m" + String.format("%02d", i);
				columnsNameArray[i - 1] = columnName;
				propertyMap.put(columnName, Class.forName("java.lang.String"));
			}
		}

		Set set = propertyMap.keySet();
		Iterator it = set.iterator();
		DynamicColumn columnMap = null;
		String[] columnSqlArray = new String[set.size()];
		List<DynamicColumn> fieldsList = new ArrayList<DynamicColumn>();
		List<String> fieldsList2 = new ArrayList<String>();
		fieldsList2.add("序号");
		String column = null;
		String type = "机械类型";
		String dateLabel = "年";
		//String vehicleCode = "机械代号";
		//String vehicleArg = "机械配置";
		int columnIndex = 0;
		String title = "";
		while (it.hasNext()) {
			column = String.valueOf(it.next());
			columnSqlArray[columnIndex] = column;
			columnIndex++;
			if (!"typeId".equals(column)) {
				// columnMap.put("header", vehicleDef);
				if ("typeName".equals(column)) {
					title = type;
					// columnMap.put("header", vehicleDef);
				}/* else if ("vehicleCode".equals(column)) {
					title = vehicleCode;
					// columnMap.put("header", vehicleDef);
				}else if ("vehicleArg".equals(column)) {
					title = vehicleArg;
					// columnMap.put("header", vehicleDef);
				}*/else if ("dateLabel".equals(column)) {
					title = dateLabel;
				} else {
					title = column.substring(1) + "月";
				}
				fieldsList2.add(title);
			}
		}
		HashMap<String, Object> result = new HashMap<String, Object>();
		conditionMap.put("columns", columnsNameArray);
		List<DynamicMalfunctionPOJO> list = statisticQueryService
				.statisticAvgWorkHoursToPOJO(conditionMap, propertyMap);
		List<Object[]> values = new ArrayList<Object[]>();
		int i = 0;
		for (DynamicMalfunctionPOJO object : list) {
			i++;
			String[] row = new String[columnSqlArray.length+1];
			row[0] = i+"";
			for (int j = 0; j < columnSqlArray.length; j++) {
				row[j+1] = String.valueOf(object.getValue(columnSqlArray[j]));
			}
			values.add(row);
		}

		super.renderExcel("平均工作时间统计" + ".xls", fieldsList2.toArray(), values);

	}
	
	/**
	 * @Title:drawChart4AvgWorkHours
	 * @Description:图表
	 * @return
	 * @throws
	 */
	public String drawChart4AvgWorkHours() {
		try {
			chart = createChart4AvgWorkHours(createDataSet4AvgWorkHours());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}


    public CategoryDataset createDataSet4AvgWorkHours() throws Exception {
		// 条件
		HashMap conditionMap = new HashMap();
		conditionMap.put("modelTypeId", vehicleWorkPOJO.getModelName());
		setConditionMap(conditionMap,vehicleWorkPOJO);
		if(vehicleWorkPOJO.getVehicleStatus()!=null && !"".equals(vehicleWorkPOJO.getVehicleArg())){
			conditionMap.put("vehicleStatus", vehicleWorkPOJO.getVehicleStatus());
		}
		LinkedHashMap propertyMap = new LinkedHashMap();
		propertyMap.put("typeId", Class.forName("java.lang.String"));
		propertyMap.put("typeName", Class.forName("java.lang.String"));
		propertyMap.put("dateLabel", Class.forName("java.lang.String"));
		String beginDateStr = null;
		String[] columnsNameArray = null;
		int count = 0;
		String columnName = null;
		if (StringUtils.isNotBlank(endDateStr)
				&& StringUtils.isNotBlank(startDateStr)) {// 只出现这几个月的信息
			HashSet<String> monthSet = com.chinaGPS.gtmp.util.DateUtil
					.getAllMonthList(startDateStr, endDateStr);
			conditionMap.put("startTime", DateUtil.parse(startDateStr + "-01",
					DateUtil.YMD_DASH));
			Date date = new Date();
			date.setYear(Integer.parseInt(endDateStr.split("-")[0]) - 1900);
			date.setMonth(Integer.parseInt(endDateStr.split("-")[1]) - 1);
			date.setHours(23);
			date.setMinutes(59);
			date.setSeconds(59);
			conditionMap.put("endTime", date);
			count = monthSet.size();
			columnsNameArray = new String[count];
			int i = 1;
			for (String item : monthSet) {
				columnName = "m"
						+ String.format("%02d", Integer.parseInt(item));
				columnsNameArray[i - 1] = columnName;
				i++;
				propertyMap.put(columnName, Class.forName("java.lang.String"));
			}
		} else {
			count = 12;
			columnsNameArray = new String[count];
			for (int i = 1; i <= count; i++) {
				columnName = "m" + String.format("%02d", i);
				columnsNameArray[i - 1] = columnName;
				propertyMap.put(columnName, Class.forName("java.lang.String"));
			}
		}
		HashMap<String, Object> result = new HashMap<String, Object>();
		conditionMap.put("columns", columnsNameArray);
		List<DynamicMalfunctionPOJO> list = statisticQueryService
				.statisticAvgWorkHoursToPOJO(conditionMap, propertyMap);
		String typeName = null;// 类型
		String ccount = null;// 数量
		String yyyymm = null;// 年月
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();

		for (DynamicMalfunctionPOJO object : list) {
			for (int j = 0; j < columnsNameArray.length; j++) {
				ccount = String.valueOf(object.getValue(columnsNameArray[j]));
				yyyymm = String.valueOf(object.getValue("dateLabel")) + "-"
						+ columnsNameArray[j].replace("m", "");
				typeName = String.valueOf(object.getValue("typeName"));
				defaultcategorydataset.addValue(Float.parseFloat(ccount),typeName, yyyymm);
			}
		}

		return defaultcategorydataset;

	}

    /**
     * @Title:createMalfunctionChart
     * @Description:创建平均工作时间统计图表对象
     * @param categorydataset
     * @return
     * @throws
     */
    private JFreeChart createChart4AvgWorkHours(CategoryDataset categorydataset) {
		// 解决中文乱码问题
		// 创建主题样式
		StandardChartTheme mChartTheme = new StandardChartTheme("CN");
		// 设置标题字体
		mChartTheme.setExtraLargeFont(new Font("黑体", Font.BOLD, 14));
		// 设置轴向字体
		mChartTheme.setLargeFont(new Font("宋体", Font.CENTER_BASELINE, 12));
		// 设置图例字体
		mChartTheme.setRegularFont(new Font("宋体", Font.CENTER_BASELINE, 12));
		// 应用主题样式
		ChartFactory.setChartTheme(mChartTheme);

		JFreeChart jfreechart = ChartFactory.createStackedBarChart(
				"平均工作时间统计图表", "年月", "小时数", categorydataset,
				PlotOrientation.VERTICAL, true, true, false);
		jfreechart.setBackgroundPaint(Color.white);
		CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
		categoryplot.setBackgroundPaint(Color.lightGray);
		categoryplot.setRangeGridlinePaint(Color.white);
		// 年月日纵向排列
		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
		categoryaxis.setLowerMargin(0.02D);
		categoryaxis.setUpperMargin(0.02D);
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);

		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		// 设置整数
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); // 关键就是这句
		// 设置纵坐标的标题字体和大小
		// numberaxis.setLabelFont(new Font("黑体", Font.CENTER_BASELINE, 24));
		// 设置丛坐标的坐标值的字体颜色
		// numberaxis.setLabelPaint(Color.BLACK);
		// 设置丛坐标的坐标轴标尺颜色
		// numberaxis.setTickLabelPaint(Color.RED);
		// 坐标轴标尺颜色
		// numberaxis.setTickMarkPaint(Color.BLUE);
		// 丛坐标的默认间距值
		// numberaxis.setAutoTickUnitSelection(true);
		// 设置丛坐标间距值
		// numberaxis.setAutoTickUnitSelection(false);
		// numberaxis.setTickUnit(new NumberTickUnit(20));

		StackedBarRenderer stackedbarrenderer = (StackedBarRenderer) categoryplot.getRenderer();
		stackedbarrenderer.setRenderAsPercentages(false);// 不用百分比
		stackedbarrenderer.setDrawBarOutline(false);
		stackedbarrenderer.setBaseItemLabelsVisible(true);
		stackedbarrenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());

		// 数值太大时，柱子上也能显示数字
		stackedbarrenderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
		stackedbarrenderer.setItemLabelAnchorOffset(5D);// 设置柱形图上的文字偏离值
		
		CategoryPlot plot = jfreechart.getCategoryPlot();
		//加上这个就可以变窄
		  BarRenderer barrenderer = new BarRenderer();
		  barrenderer.setMaximumBarWidth(0.1);
		  barrenderer.setMinimumBarLength(0.1);
		  plot.setRenderer(barrenderer);
		
		return jfreechart;
	}
    
    /**
	 * @Title:statisticWorkedYears
	 * @Description:机械使用年数统计
	 * @throws
	 */
    public void statisticWorkedYears() {
    	HashMap<String, Object> result = new HashMap<String, Object>();
    	
    	try {
    		// 查询条件
			HashMap<String, Object> conditionMap = new HashMap<String, Object>();
			if (org.apache.commons.lang.StringUtils.isNotEmpty(vehicleWorkPOJO.getDealerId())) {
				vehicleWorkPOJO.setDealerIds(StringUtils.split(vehicleWorkPOJO.getDealerId(), ','));
				conditionMap.put("dealerIds", vehicleWorkPOJO.getDealerIds());
				conditionMap.put("vehicleStatus", 3);
				
			}
    		
    		conditionMap.put("vehicleModel", vehicleWorkPOJO.getModelName());
    		conditionMap.put("vehicleCode", vehicleWorkPOJO.getVehicleCode());
    		conditionMap.put("vehicleArg", vehicleWorkPOJO.getVehicleArg());
    		
    		// Grid表头
			LinkedHashMap<String, Object> propertyMap = new LinkedHashMap<String, Object>();
			propertyMap.put("typeId", Class.forName("java.lang.String"));
			propertyMap.put("modelName", Class.forName("java.lang.String"));
			propertyMap.put("one", Class.forName("java.lang.String"));
			propertyMap.put("two", Class.forName("java.lang.String"));
			propertyMap.put("three", Class.forName("java.lang.String"));
			propertyMap.put("four", Class.forName("java.lang.String"));
			propertyMap.put("five", Class.forName("java.lang.String"));
			propertyMap.put("six", Class.forName("java.lang.String"));
			
			Set<String> set = propertyMap.keySet();
			Iterator<String> it = set.iterator();
			List<DynamicColumn> fieldsList = new ArrayList<DynamicColumn>();
			DynamicColumn dynamicColumn = null;
			String column = null;
			while (it.hasNext()) {
				column = it.next();
				dynamicColumn = new DynamicColumn();
				dynamicColumn.setField(column);
				dynamicColumn.setWidth(150);
				if("typeId".equals(column)) {
					dynamicColumn.setHidden(true);
					dynamicColumn.setTitle("typeId");
				}else if ("modelName".equals(column)){
					dynamicColumn.setTitle("机型");
				} else if ("one".equals(column)){
					dynamicColumn.setTitle("1年以下");
				} else if ("two".equals(column)){
					dynamicColumn.setTitle("1-2年");
				} else if ("three".equals(column)){
					dynamicColumn.setTitle("2-3年");
				} else if ("four".equals(column)){
					dynamicColumn.setTitle("3-4年");
				} else if ("five".equals(column)){
					dynamicColumn.setTitle("4-5年");
				} else if ("six".equals(column)){
					dynamicColumn.setTitle("5年以上");
				}
				fieldsList.add(dynamicColumn);
			}
    		
			List<Object> resultList = statisticQueryService.statisticWorkedYears(conditionMap, propertyMap);
			result.put("rows", resultList);
			result.put("total", 0);
			result.put("columns", fieldsList);
			
    		renderObject(result);
    	} catch (Exception e) {
    		logger.error(e.getMessage(), e);
    	}
	}
    
    /**
     * @Title:exportToExcelWorkedYears
     * @Description:机械工作使用年数导出
     * @return
     * @throws UnsupportedEncodingException
     * @throws
     */
    public void exportToExcelWorkedYears() {
    	List<Object[]> values = new ArrayList<Object[]>();
    	
		try {
			// 查询条件
			HashMap<String, Object> conditionMap = new HashMap<String, Object>();
			String dealerIds = vehicleWorkPOJO.getDealerId();
			if(org.apache.commons.lang.StringUtils.isNotEmpty(dealerIds)){
	    		conditionMap.put("dealerIds",dealerIds.split(","));
			}
    		conditionMap.put("vehicleModel", vehicleWorkPOJO.getModelName());
    		conditionMap.put("vehicleCode", vehicleWorkPOJO.getVehicleCode());
    		conditionMap.put("vehicleArg", vehicleWorkPOJO.getVehicleArg());
    		
    		// Grid表头
			LinkedHashMap<String, Object> propertyMap = new LinkedHashMap<String, Object>();
			propertyMap.put("typeId", Class.forName("java.lang.String"));
			propertyMap.put("modelName", Class.forName("java.lang.String"));
			propertyMap.put("one", Class.forName("java.lang.String"));
			propertyMap.put("two", Class.forName("java.lang.String"));
			propertyMap.put("three", Class.forName("java.lang.String"));
			propertyMap.put("four", Class.forName("java.lang.String"));
			propertyMap.put("five", Class.forName("java.lang.String"));
			propertyMap.put("six", Class.forName("java.lang.String"));
			
			List<Object> resultList = statisticQueryService.statisticWorkedYears(conditionMap, propertyMap);
			
			Set<String> set = propertyMap.keySet();
			String[] columnArray = new String[set.size()];
			Iterator<String> it = set.iterator();
			int idx = 0;
			while(it.hasNext()) {
				columnArray[idx] = it.next();
				idx++;
			}
			
			for (Object object : resultList) {
				BeanMap bm = BeanMap.create(object);
				String[] row = new String[columnArray.length-1];
				for (int j = 1; j < columnArray.length; j++) {
					row[j-1] = String.valueOf(bm.get(columnArray[j]));
				}
				values.add(row);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		String[] headers = new String[] {"机型", "1年以下", "1-2年", "2-3年", "3-4年", "4-5年", "5年以上"};
		renderExcel("机械使用年数统计" + ".xls", headers, values);
	}
    
    /**
	 * @Title:drawChart
	 * @Description:图表
	 * @return
	 * @throws
	 */
	public String drawChart4WorkedYears() {
		try {
			chart = createChart4WorkedYears(createDataset4WorkedYears());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}
	
	public void downloadChart4WorkedYears() {
		try {
			chart = createChart4WorkedYears(createDataset4WorkedYears());
			HttpServletResponse resp = BaseAction.getResponse();
			resp.setContentType("application/octet-stream");
			resp.setHeader("Content-Disposition", "attachment; filename=alarm_chart.png");
			ChartUtilities.writeChartAsPNG(resp.getOutputStream(), chart, 1024, 768);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private CategoryDataset createDataset4WorkedYears() throws Exception {
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		List<Object[]> values = new ArrayList<Object[]>();

		// 查询条件
		HashMap<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("dealerId", vehicleWorkPOJO.getDealerId());
		conditionMap.put("vehicleModel", vehicleWorkPOJO.getModelName());
		conditionMap.put("vehicleCode", vehicleWorkPOJO.getVehicleCode());
		conditionMap.put("vehicleArg", vehicleWorkPOJO.getVehicleArg());
		
		// Grid表头
		LinkedHashMap<String, Object> propertyMap = new LinkedHashMap<String, Object>();
		propertyMap.put("typeId", Class.forName("java.lang.String"));
		propertyMap.put("modelName", Class.forName("java.lang.String"));
		propertyMap.put("one", Class.forName("java.lang.String"));
		propertyMap.put("two", Class.forName("java.lang.String"));
		propertyMap.put("three", Class.forName("java.lang.String"));
		propertyMap.put("four", Class.forName("java.lang.String"));
		propertyMap.put("five", Class.forName("java.lang.String"));
		propertyMap.put("six", Class.forName("java.lang.String"));

		Set<String> set = propertyMap.keySet();
		String[] columnArray = new String[set.size()];
		Iterator<String> it = set.iterator();
		int idx = 0;
		while(it.hasNext()) {
			columnArray[idx] = it.next();
			idx++;
		}
		
		List<Object> resultList = statisticQueryService.statisticWorkedYears(conditionMap, propertyMap);
		String vcount = null;
		String modelName = null;
		String yearStr = null;
		for (Object object : resultList) {
			BeanMap bm = BeanMap.create(object);
			modelName = String.valueOf(bm.get("modelName"));
			
			for (int i = 2; i < columnArray.length; i++) {
				if("one".equals(columnArray[i])) {
					yearStr = "1年以下";
				} else if ("two".equals(columnArray[i])) {
					yearStr = "1-2年";
				} else if ("three".equals(columnArray[i])) {
					yearStr = "2-3年";
				} else if ("four".equals(columnArray[i])) {
					yearStr = "3-4年";
				} else if ("five".equals(columnArray[i])) {
					yearStr = "4-5年";
				} else if ("six".equals(columnArray[i])) {
					yearStr = "5年以上";
				}
				vcount = String.valueOf(bm.get(columnArray[i]));
				defaultcategorydataset.addValue(Integer.parseInt(vcount), modelName, yearStr);
			}
		}
		return defaultcategorydataset;
	}

	private JFreeChart createChart4WorkedYears(CategoryDataset categorydataset) {
		// 解决中文乱码问题
		// 创建主题样式
		StandardChartTheme mChartTheme = new StandardChartTheme("CN");
		// 设置标题字体
		mChartTheme.setExtraLargeFont(new Font("黑体", Font.BOLD, 14));
		// 设置轴向字体
		mChartTheme.setLargeFont(new Font("宋体", Font.CENTER_BASELINE, 12));
		// 设置图例字体
		mChartTheme.setRegularFont(new Font("宋体", Font.CENTER_BASELINE, 12));
		// 应用主题样式
		ChartFactory.setChartTheme(mChartTheme);

		JFreeChart jfreechart = ChartFactory.createLineChart("机械使用年数统计",
				"机械使用年数", "机械数量", categorydataset, PlotOrientation.VERTICAL,
				true, true, false);
		jfreechart.setBackgroundPaint(Color.white);
		CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
		categoryplot.setBackgroundPaint(Color.lightGray);
		categoryplot.setRangeGridlinesVisible(false);
		// 年月日纵向排列
		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
		categoryaxis.setLowerMargin(0.02D);
		categoryaxis.setUpperMargin(0.02D);
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);

		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		
//		// 如果使用柱状图，使用下列代码
//		numberaxis.setAutoRangeIncludesZero(true);
//		numberaxis.setUpperMargin(0.20);
//		numberaxis.setLabelAngle(Math.PI / 2.0);
//		BarRenderer barrenderer = new BarRenderer();
//		barrenderer.setMaximumBarWidth(0.1);
//		barrenderer.setMinimumBarLength(0.1);
//		categoryplot.setRenderer(barrenderer);
		
		// 如果使用拆线图，使用下列代码
		LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot.getRenderer();
		lineandshaperenderer.setShapesVisible(true);
		lineandshaperenderer.setDrawOutlines(true);
		lineandshaperenderer.setUseFillPaint(true);
		lineandshaperenderer.setBaseFillPaint(Color.white);
		lineandshaperenderer.setSeriesStroke(0, new BasicStroke(3F));
		lineandshaperenderer.setSeriesOutlineStroke(0, new BasicStroke(2.0F));
		lineandshaperenderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-5D, -5D, 10D, 10D));
		
		return jfreechart;
	}
	
	    /**
		 * @Title:statisticDistribute
		 * @Description:机械分布统计
		 * @throws
		 */
	public void statisticDistribute() {
		List<VehicleUnitPOJO> resultList = new ArrayList<VehicleUnitPOJO>();

		try {
			HashMap map = new HashMap();
			if(vehicleWorkPOJO.getVehicleCode()!=null && !"null".equals(vehicleWorkPOJO.getVehicleCode())&& !"全部".equals(vehicleWorkPOJO.getVehicleCode())){
				map.put("vehicleCode", vehicleWorkPOJO.getVehicleCode());
			}
			if(vehicleWorkPOJO.getVehicleArg()!=null && !"null".equals(vehicleWorkPOJO.getVehicleArg()) && !"全部".equals(vehicleWorkPOJO.getVehicleArg())){
				map.put("vehicleArg", vehicleWorkPOJO.getVehicleArg());
			}
			map.put("modelTypeId", vehicleWorkPOJO.getModelName());	
			map.put("dealerIds", vehicleWorkPOJO.getDealerIds());
			map.put("province", vehicleWorkPOJO.getProvince());
			map.put("city", vehicleWorkPOJO.getCity());
			// 机械状态组
			map.put("status", vehicleWorkPOJO.getDirection());
			// 省
			if (vehicleWorkPOJO.getLocationState() == 1) {
				map.put("area", "lp.province");
			} else if (vehicleWorkPOJO.getLocationState() == 2) {// 市
				map.put("area", "lp.city");
			}
			
			resultList = statisticQueryService.statisticDistribute(map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(resultList);
	}
	
	    /**
		 * @Title:exportToExcelWorkedYears
		 * @Description:机械分布导出
		 * @return
		 * @throws UnsupportedEncodingException
		 * @throws
		 */
	public String exportToExcelDistribute() {
		List<Object[]> values = new ArrayList<Object[]>();

		try {
			HashMap map = new HashMap();
			if(null != vehicleWorkPOJO.getDealerId()&& !("").equals(vehicleWorkPOJO.getDealerId())){
				vehicleWorkPOJO.setDealerIds(vehicleWorkPOJO.getDealerId().split(","));
			}
			if(vehicleWorkPOJO.getVehicleCode()!=null && !"null".equals(vehicleWorkPOJO.getVehicleCode())&& !"全部".equals(vehicleWorkPOJO.getVehicleCode())){
				map.put("vehicleCode", vehicleWorkPOJO.getVehicleCode());
			}
			if(vehicleWorkPOJO.getVehicleArg()!=null && !"null".equals(vehicleWorkPOJO.getVehicleArg()) && !"全部".equals(vehicleWorkPOJO.getVehicleArg())){
				map.put("vehicleArg", vehicleWorkPOJO.getVehicleArg());
			}
			map.put("modelTypeId", vehicleWorkPOJO.getModelName());	
			map.put("dealerIds", vehicleWorkPOJO.getDealerIds());
			map.put("province", vehicleWorkPOJO.getProvince());
			map.put("city", vehicleWorkPOJO.getCity());
			// 机械状态组
			map.put("status", vehicleWorkPOJO.getDirection());
			// 省
            map.put("area", "lp.province");
				
			List<VehicleUnitPOJO> resultList = statisticQueryService.statisticDistribute(map);//statisticQueryService.statisticDistribute4Export(map);
			int i = 1;
			for (VehicleUnitPOJO map2 : resultList) {				
				values.add(new Object[] { i++,map2.getVehicleDef(), map2.getTypeId()});
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		String[] headers = new String[] { "序号","省", 
				"机械数量" };
		super.renderExcel("机械分布统计" + ".xls", headers, values);
		return null;
	}

	/**
	 * @Title:statisticWorkHours
	 * @Description:机械工作时间统计
	 * @throws Exception
	 * @throws
	 */
	public void statisticWorkHours() throws Exception {
		// 动态列
		// 条件
		HashMap conditionMap = new HashMap();
		setConditionMap(conditionMap,vehicleWorkPOJO);
		conditionMap.put("typeId", vehicleWorkPOJO.getDirection());
		conditionMap.put("vehicleDef", vehicleWorkPOJO.getVehicleDef());

		LinkedHashMap propertyMap = new LinkedHashMap();
		propertyMap.put("typeName", Class.forName("java.lang.String"));
		propertyMap.put("dealerName", Class.forName("java.lang.String"));
		propertyMap.put("areaName", Class.forName("java.lang.String"));
		propertyMap.put("vehicleStatus",  Class.forName("java.lang.String"));
		propertyMap.put("vehicleType", Class.forName("java.lang.String"));
		propertyMap.put("vehicleCode", Class.forName("java.lang.String"));
		propertyMap.put("vehicleArg", Class.forName("java.lang.String"));
		propertyMap.put("dateLabel", Class.forName("java.lang.String"));
		propertyMap.put("addHours", Class.forName("java.lang.String"));
		propertyMap.put("totalHours", Class.forName("java.lang.String"));
		propertyMap.put("avgHours", Class.forName("java.lang.String"));
		
		String[] columnsNameArray = null;
		int count = 0;
		String columnName = null;
		// 月报
		if (vehicleWorkPOJO.getDirection() == 1) {
			if (StringUtils.isNotBlank(endDateStr) && StringUtils.isNotBlank(startDateStr)) {// 只出现这几天的信息
				// beginDateStr=DateUtil.format(workTimePOJO.getStartDate(),"yyyy-MM-dd");
				// endDateStr=DateUtil.format(workTimePOJO.getEndDate(),"yyyy-MM-dd");
				HashSet<String> monthSet = DateUtil.getAllDays(startDateStr,
						endDateStr);
				conditionMap.put("startTime", DateUtil.parse(startDateStr,
						DateUtil.YMD_DASH));
				Date date = DateUtil.parse(endDateStr, DateUtil.YMD_DASH);
				date.setHours(23);
				date.setMinutes(59);
				date.setSeconds(59);
				conditionMap.put("endTime", date);
				count = monthSet.size();
				columnsNameArray = new String[count];
				int i = 1;
				for (String item : monthSet) {
					columnName = "d"
							+ String.format("%02d", Integer.parseInt(item));
					columnsNameArray[i - 1] = columnName;
					i++;
					propertyMap.put(columnName, Class
							.forName("java.lang.String"));
				}
			} else {
				count = 31;
				columnsNameArray = new String[count];
				for (int i = 1; i <= count; i++) {
					columnName = "d" + String.format("%02d", i);
					columnsNameArray[i - 1] = columnName;
					propertyMap.put(columnName, Class
							.forName("java.lang.String"));
				}
			}
		} else if (vehicleWorkPOJO.getDirection() == 2) {// 年报
			if (StringUtils.isNotBlank(endDateStr)
					&& StringUtils.isNotBlank(startDateStr)) {// 只出现这几个月的信息
				HashSet<String> monthSet = com.chinaGPS.gtmp.util.DateUtil
						.getAllMonthList(startDateStr, endDateStr);
				conditionMap.put("startTime", DateUtil.parse(startDateStr
						+ "-01", DateUtil.YMD_DASH));
				Date date = new Date();
				date.setYear(Integer.parseInt(endDateStr.split("-")[0]) - 1900);
				date.setMonth(Integer.parseInt(endDateStr.split("-")[1]) - 1);
				date.setHours(23);
				date.setMinutes(59);
				date.setSeconds(59);
				conditionMap.put("endTime", date);
				count = monthSet.size();
				columnsNameArray = new String[count];
				int i = 1;
				for (String item : monthSet) {
					columnName = "m"
							+ String.format("%02d", Integer.parseInt(item));
					columnsNameArray[i - 1] = columnName;
					i++;
					propertyMap.put(columnName, Class
							.forName("java.lang.String"));
				}
			} else {
				count = 12;
				columnsNameArray = new String[count];
				for (int i = 1; i <= count; i++) {
					columnName = "m" + String.format("%02d", i);
					columnsNameArray[i - 1] = columnName;
					propertyMap.put(columnName, Class
							.forName("java.lang.String"));
				}
			}
		}

		Set set = propertyMap.keySet();
		Iterator it = set.iterator();
		DynamicColumn columnMap = null;
		List<DynamicColumn> fieldsList = new ArrayList<DynamicColumn>();
		DynamicColumn dynamicCcolumn = null;
		String column = null;
		String type = "整机编号";
		String vehicleType = "机械类型";
		String vehicleCode = "机械代号";
		String vehicleArg = "机械配置";
		String dateLabel = "年份";
		String dealerName = "经销商";
		String areaName = "区域";
		String vehicleStatus="机械状态";
		String addHours = "合计工作时间";
		String totalHours = "累计工作时间";
		String avgHours = "平均工作时间";
		while (it.hasNext()) {
			column = String.valueOf(it.next());
			dynamicCcolumn = new DynamicColumn();
			dynamicCcolumn.setField(column);
			dynamicCcolumn.setWidth(100);
			if (vehicleWorkPOJO.getDirection() == 1) {// 31日				
				if ("typeId".equals(column)) {
					dynamicCcolumn.setHidden(true);
					// columnMap.put("header", vehicleDef);
				} else if ("typeName".equals(column)) {
					dynamicCcolumn.setTitle(type);
					// columnMap.put("header", vehicleDef);
				} else if ("vehicleType".equals(column)) {
					dynamicCcolumn.setTitle(vehicleType);
					// columnMap.put("header", vehicleDef);
				} else if ("vehicleCode".equals(column)) {
					dynamicCcolumn.setTitle(vehicleCode);
					// columnMap.put("header", vehicleDef);
				} else if ("vehicleArg".equals(column)) {
					dynamicCcolumn.setTitle(vehicleArg);
				} else if ("dateLabel".equals(column)) {
					dynamicCcolumn.setTitle(dateLabel);
				}else if("dealerName".equals(column)){
					dynamicCcolumn.setTitle(dealerName);
				}else if("areaName".equals(column)){
					dynamicCcolumn.setTitle(areaName);
				}else if("vehicleStatus".equals(column)){
					dynamicCcolumn.setTitle(vehicleStatus);
				}else if("addHours".equals(column)){
					dynamicCcolumn.setTitle(addHours);
				}else if("totalHours".equals(column)){
					dynamicCcolumn.setTitle(totalHours);
				}else if("avgHours".equals(column)){
					dynamicCcolumn.setTitle(avgHours);
				}else{
					dynamicCcolumn.setTitle(column.substring(1) + "日");
				}

			} else if (vehicleWorkPOJO.getDirection() == 2) {
				if ("typeId".equals(column)) {
					dynamicCcolumn.setHidden(true);
					// columnMap.put("header", vehicleDef);
				} else if ("typeName".equals(column)) {
					dynamicCcolumn.setTitle(type);
					// columnMap.put("header", vehicleDef);
				} else if ("vehicleType".equals(column)) {
					dynamicCcolumn.setTitle(vehicleType);
					// columnMap.put("header", vehicleDef);
				} else if ("vehicleCode".equals(column)) {
					dynamicCcolumn.setTitle(vehicleCode);
					// columnMap.put("header", vehicleDef);
				} else if ("vehicleArg".equals(column)) {
					dynamicCcolumn.setTitle(vehicleArg);
				} else if ("dateLabel".equals(column)) {
					dynamicCcolumn.setTitle(dateLabel);
				}else if("dealerName".equals(column)){
					dynamicCcolumn.setTitle(dealerName);
				}else if("areaName".equals(column)){
					dynamicCcolumn.setTitle(areaName);
				}else if("vehicleStatus".equals(column)){
					dynamicCcolumn.setTitle(vehicleStatus);
				}else if("addHours".equals(column)){
					dynamicCcolumn.setTitle(addHours);
				}else if("totalHours".equals(column)){
					dynamicCcolumn.setTitle(totalHours);
				}else if("avgHours".equals(column)){
					dynamicCcolumn.setTitle(avgHours);
				} else {
					dynamicCcolumn.setTitle(column.substring(1) + "月");
				}
			}
			fieldsList.add(dynamicCcolumn);
		}
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		conditionMap.put("columns", columnsNameArray);
		String vehicleTypeP = getRequest().getParameter("vehicleType");
		String vehicleCodeP = getRequest().getParameter("vehicleCode");
		String vehicleArgP = getRequest().getParameter("vehicleArg");
		String vehicleStatusP = getRequest().getParameter("vehicleStatus");
		if(vehicleCodeP==null ||"null".equals(vehicleCodeP) || "全部".equals(vehicleCodeP)){
			vehicleCodeP="";
		}
		if(vehicleArgP==null ||"null".equals(vehicleArgP)|| "全部".equals(vehicleArgP)){
			vehicleArgP="";
		}
		conditionMap.put("vehicleType", vehicleTypeP);
		conditionMap.put("vehicleCode", vehicleCodeP);
		conditionMap.put("vehicleArg", vehicleArgP);
		conditionMap.put("count",count);
		if(vehicleStatusP!=null || !"".equals(vehicleStatusP)){
			conditionMap.put("vehicleStatus", vehicleStatusP);
		}
		List<Object> list = statisticQueryService.statisticWorkHours(conditionMap, propertyMap);
		result.put("rows", list);
		result.put("total", 0);
		List columnsListWrap = new ArrayList();
		columnsListWrap.add(fieldsList);
		result.put("columns", columnsListWrap);
		renderObject(result);
	}
	

	/**
	 * @Title:statisticWorkHours
	 * @Description:机械年工作时间统计
	 * @throws Exception
	 * @author zhouwei
	 * @throws
	 */
	public void statisticWorkHoursYear() throws Exception {
		// 动态列
		// 条件
		HashMap conditionMap = new HashMap();
		HashMap<String, Object> result = new HashMap<String, Object>();
		conditionMap.put("vehicleDef", vehicleWorkPOJO.getVehicleDef());
		setConditionMap(conditionMap,vehicleWorkPOJO);
		String yearTime = getRequest().getParameter("yearTime")==null?"":getRequest().getParameter("yearTime");
		String vehicleTypeP = getRequest().getParameter("vehicleType");
		String vehicleCodeP = getRequest().getParameter("vehicleCode");
		String vehicleArgP = getRequest().getParameter("vehicleArg");
		String vehicleStatusP = getRequest().getParameter("vehicleStatus");
		if(vehicleCodeP==null ||"null".equals(vehicleCodeP) || "全部".equals(vehicleCodeP)){
			vehicleCodeP="";
		}
		if(vehicleArgP==null ||"null".equals(vehicleArgP)|| "全部".equals(vehicleArgP)){
			vehicleArgP="";
		}
		conditionMap.put("yearTime", yearTime);
		conditionMap.put("vehicleType", vehicleTypeP);
		conditionMap.put("vehicleCode", vehicleCodeP);
		conditionMap.put("vehicleArg", vehicleArgP);
		if(vehicleStatusP!=null || !"".equals(vehicleStatusP)){
			conditionMap.put("vehicleStatus", vehicleStatusP);
		}
		List<VehicleWorkPOJO> list=null;
		try {
			list= statisticQueryService.statisticWorkHoursYear(conditionMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("rows", list);
		renderObject(result);
	}
	
	public void exportToExcelWorkHoursYear() throws Exception {
		HashMap conditionMap = new HashMap();
		HashMap<String, Object> result = new HashMap<String, Object>();
		conditionMap.put("vehicleDef", vehicleWorkPOJO.getVehicleDef());
		setConditionMap(conditionMap,vehicleWorkPOJO);
		String yearTime = getRequest().getParameter("yearTime")==null?"":getRequest().getParameter("yearTime");
		String vehicleTypeP = getRequest().getParameter("vehicleType");
		String vehicleCodeP = getRequest().getParameter("vehicleCode");
		String vehicleArgP = getRequest().getParameter("vehicleArg");
		String vehicleStatusP = getRequest().getParameter("vehicleStatus");
		if(vehicleCodeP==null ||"null".equals(vehicleCodeP) || "全部".equals(vehicleCodeP)){
			vehicleCodeP="";
		}
		if(vehicleArgP==null ||"null".equals(vehicleArgP)|| "全部".equals(vehicleArgP)){
			vehicleArgP="";
		}
		conditionMap.put("yearTime", yearTime);
		conditionMap.put("vehicleType", vehicleTypeP);
		conditionMap.put("vehicleCode", vehicleCodeP);
		conditionMap.put("vehicleArg", vehicleArgP);
		if(vehicleStatusP!=null || !"".equals(vehicleStatusP)){
			conditionMap.put("vehicleStatus", vehicleStatusP);
		}
		List<VehicleWorkPOJO> list=null;
		try {
			list= statisticQueryService.statisticWorkHoursYear(conditionMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Map<String, Object>> values = new ArrayList<Map<String, Object>>();
		Map<String, Object> map=null;
		List fieldsList =new ArrayList();
		fieldsList.add("整机编号");
		fieldsList.add("经销商");
		fieldsList.add("区域");
		fieldsList.add("机械状态");
		fieldsList.add("机械类型");
		fieldsList.add("机械代号");
		fieldsList.add("机械配置");
		fieldsList.add("年份");
		fieldsList.add("合计工作时间");
		fieldsList.add("累计工作时间");
		for (VehicleWorkPOJO object : list) {
			map=new HashMap<String, Object>();
			map.put("0",object.getTypeName());
			map.put("1",object.getDealerName());
			map.put("2",object.getAreaName());
			map.put("3",object.getVehicleStatus());
			map.put("4",object.getModelName());
			map.put("5",object.getVehicleCode());
			map.put("6",object.getVehicleArg());
			map.put("7",object.getEndYears());
			map.put("8",object.getTotalTime());
			map.put("9",object.getAddTime());
			values.add(map);
		}
		super.renderExcel2("机械年工作时间统计" + ".xls", fieldsList.toArray(), values);
	}
	
	public void exportToExcelWorkHours() throws Exception {
		// 条件
		HashMap conditionMap = new HashMap();
		setConditionMap(conditionMap,vehicleWorkPOJO);
		conditionMap.put("typeId", vehicleWorkPOJO.getDirection());
		conditionMap.put("vehicleDef", vehicleWorkPOJO.getVehicleDef());

		LinkedHashMap propertyMap = new LinkedHashMap();
		propertyMap.put("rownum", Class.forName("java.lang.String"));
		propertyMap.put("typeName", Class.forName("java.lang.String"));
		propertyMap.put("dealerName", Class.forName("java.lang.String"));
		propertyMap.put("areaName", Class.forName("java.lang.String"));
		propertyMap.put("vehicleStatus",  Class.forName("java.lang.String"));
		propertyMap.put("vehicleType", Class.forName("java.lang.String"));
		propertyMap.put("vehicleCode", Class.forName("java.lang.String"));
		propertyMap.put("vehicleArg", Class.forName("java.lang.String"));
		propertyMap.put("dateLabel", Class.forName("java.lang.String"));
		propertyMap.put("addHours", Class.forName("java.lang.String"));
		propertyMap.put("totalHours", Class.forName("java.lang.String"));
		propertyMap.put("avgHours", Class.forName("java.lang.String"));
		String beginDateStr = null;
		String[] columnsNameArray = null;
		int count = 0;
		String columnName = null;
		// 月报
		if (vehicleWorkPOJO.getDirection() == 1) {
			if (StringUtils.isNotBlank(endDateStr)
					&& StringUtils.isNotBlank(startDateStr)) {// 只出现这几天的信息
				// beginDateStr=DateUtil.format(workTimePOJO.getStartDate(),"yyyy-MM-dd");
				// endDateStr=DateUtil.format(workTimePOJO.getEndDate(),"yyyy-MM-dd");
				HashSet<String> monthSet = DateUtil.getAllDays(startDateStr,
						endDateStr);
				conditionMap.put("startTime", DateUtil.parse(startDateStr,
						DateUtil.YMD_DASH));
				Date date = DateUtil.parse(endDateStr, DateUtil.YMD_DASH);
				date.setHours(23);
				date.setMinutes(59);
				date.setSeconds(59);
				conditionMap.put("endTime", date);
				count = monthSet.size();
				columnsNameArray = new String[count];
				int i = 1;
				for (String item : monthSet) {
					columnName = "d"
							+ String.format("%02d", Integer.parseInt(item));
					columnsNameArray[i - 1] = columnName;
					i++;
					propertyMap.put(columnName, Class.forName("java.lang.String"));
				}
			} else {
				count = 31;
				columnsNameArray = new String[count];
				for (int i = 1; i <= count; i++) {
					columnName = "d" + String.format("%02d", i);
					columnsNameArray[i - 1] = columnName;
					propertyMap.put(columnName, Class.forName("java.lang.String"));
				}
			}
		} else if (vehicleWorkPOJO.getDirection() == 2) {// 年报
			if (StringUtils.isNotBlank(endDateStr)
					&& StringUtils.isNotBlank(startDateStr)) {// 只出现这几个月的信息
				HashSet<String> monthSet = com.chinaGPS.gtmp.util.DateUtil
						.getAllMonthList(startDateStr, endDateStr);
				conditionMap.put("startTime", DateUtil.parse(startDateStr
						+ "-01", DateUtil.YMD_DASH));
				Date date = new Date();
				date.setYear(Integer.parseInt(endDateStr.split("-")[0]) - 1900);
				date.setMonth(Integer.parseInt(endDateStr.split("-")[1]) - 1);
				date.setHours(23);
				date.setMinutes(59);
				date.setSeconds(59);
				conditionMap.put("endTime", date);
				count = monthSet.size();
				columnsNameArray = new String[count];
				int i = 1;
				for (String item : monthSet) {
					columnName = "m"
							+ String.format("%02d", Integer.parseInt(item));
					columnsNameArray[i - 1] = columnName;
					i++;
					propertyMap.put(columnName, Class
							.forName("java.lang.String"));
				}
			} else {
				count = 12;
				columnsNameArray = new String[count];
				for (int i = 1; i <= count; i++) {
					columnName = "m" + String.format("%02d", i);
					columnsNameArray[i - 1] = columnName;
					propertyMap.put(columnName, Class
							.forName("java.lang.String"));
				}
			}
		}
		Set set = propertyMap.keySet();
		Iterator it = set.iterator();
		DynamicColumn columnMap = null;
		String[] columnSqlArray = new String[set.size()];
		List<DynamicColumn> fieldsList = new ArrayList<DynamicColumn>();
		List<String> fieldsList2 = new ArrayList<String>();
		String column = null;
		String type = "整机编号";
		String vehicleType = "机械类型";
		String vehicleCode = "机械代号";
		String vehicleArg = "机械配置";
		String dateLabel = "年份";
		String dealerName = "经销商";
		String areaName = "区域";
		String vehicleStatus="机械状态";
		String addHours = "合计工作时间";
		String totalHours = "累计工作时间";
		String avgHours = "平均工作时间";
		int columnIndex = 0;
		String title = "";
		while (it.hasNext()) {
			column = String.valueOf(it.next());
			columnSqlArray[columnIndex] = column;
			columnIndex++;
			if (!"typeId".equals(column)) {
				if (vehicleWorkPOJO.getDirection() == 1) {// 31日
					if("rownum".equals(column)){
						title = "序号";
					}else if ("typeName".equals(column)) {
						title =type;
						// columnMap.put("header", vehicleDef);
					} else if ("vehicleType".equals(column)) {
						title =vehicleType;
						// columnMap.put("header", vehicleDef);
					} else if ("vehicleCode".equals(column)) {
						title =vehicleCode;
						// columnMap.put("header", vehicleDef);
					} else if ("vehicleArg".equals(column)) {
						title =vehicleArg;
					} else if ("dateLabel".equals(column)) {
						title =dateLabel;
					}else if("dealerName".equals(column)){
						title =dealerName;
					}else if("areaName".equals(column)){
						title =areaName;
					}else if("vehicleStatus".equals(column)){
						title =vehicleStatus;
					} else {
						title = column.substring(1) + "日";
					}
				} else if (vehicleWorkPOJO.getDirection() == 2) {
					if("rownum".equals(column)){
						title = "序号";
					}else if ("typeName".equals(column)) {
						title =type;
						// columnMap.put("header", vehicleDef);
					} else if ("vehicleType".equals(column)) {
						title =vehicleType;
						// columnMap.put("header", vehicleDef);
					} else if ("vehicleCode".equals(column)) {
						title =vehicleCode;
						// columnMap.put("header", vehicleDef);
					} else if ("vehicleArg".equals(column)) {
						title =vehicleArg;
					} else if ("dateLabel".equals(column)) {
						title =dateLabel;
					}else if("dealerName".equals(column)){
						title =dealerName;
					}else if("areaName".equals(column)){
						title =areaName;
					}else if("vehicleStatus".equals(column)){
						title =vehicleStatus;
					}else if("addHours".equals(column)){
						title=addHours;
					}else if("totalHours".equals(column)){
						title=totalHours;
					}else if("avgHours".equals(column)){
						title=avgHours;
					}  else {
						title = column.substring(1) + "月";
					}
				}
				fieldsList2.add(title);
			}
		}
		HashMap<String, Object> result = new HashMap<String, Object>();
		String vehicleTypeP = getRequest().getParameter("vehicleType");
		String vehicleCodeP = getRequest().getParameter("vehicleCode");
		String vehicleArgP = getRequest().getParameter("vehicleArg");
		String vehicleStatusP = getRequest().getParameter("vehicleStatus");
		if(vehicleCodeP==null ||"null".equals(vehicleCodeP)||"全部".equals(vehicleCodeP) ){
			vehicleCodeP="";
		}
		if(vehicleArgP==null ||"null".equals(vehicleArgP)||"全部".equals(vehicleArgP)){
			vehicleArgP="";
		}
		conditionMap.put("vehicleType", vehicleTypeP);
		conditionMap.put("vehicleCode", vehicleCodeP);
		conditionMap.put("vehicleArg", vehicleArgP);
		conditionMap.put("count",count);
		if(vehicleStatusP != null && !vehicleStatusP.equals("") )
		    conditionMap.put("vehicleStatus", vehicleStatusP);
		conditionMap.put("columns", columnsNameArray);
		List<DynamicMalfunctionPOJO> list = statisticQueryService
				.statisticWorkHoursToPOJO(conditionMap, propertyMap);
		List<Object[]> values = new ArrayList<Object[]>();
		for (DynamicMalfunctionPOJO object : list) {
			String[] row = new String[columnSqlArray.length];
			for (int j = 0; j < columnSqlArray.length; j++) {
				row[j] = String.valueOf(object.getValue(columnSqlArray[j]));
			}
			values.add(row);
		}
		super.renderExcel("机械工作时间统计" + ".xls", fieldsList2.toArray(), values);
	}
		    
   /**
	 * @Title:drawChart4WorkHours
	 * @Description:图表
	 * @return
	 * @throws
	 */
	public String drawChart4WorkHours() {
		try {
			chart = createChart4WorkHours(createDataSet4WorkHours());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	public XYDataset createDataSet4WorkHours() throws Exception {
		// 条件
		// 条件
		HashMap conditionMap = new HashMap();
		setConditionMap(conditionMap,vehicleWorkPOJO);
		conditionMap.put("typeId", vehicleWorkPOJO.getDirection());
		conditionMap.put("vehicleDef", vehicleWorkPOJO.getVehicleDef());

		LinkedHashMap propertyMap = new LinkedHashMap();
		propertyMap.put("typeId", Class.forName("java.lang.String"));
		propertyMap.put("typeName", Class.forName("java.lang.String"));
		propertyMap.put("dateLabel", Class.forName("java.lang.String"));
		String beginDateStr = null;
		String[] columnsNameArray = null;
		int count = 0;
		String columnName = null;
		// 月报
		if (vehicleWorkPOJO.getDirection() == 1) {
			if (StringUtils.isNotBlank(endDateStr)
					&& StringUtils.isNotBlank(startDateStr)) {// 只出现这几天的信息
				// beginDateStr=DateUtil.format(workTimePOJO.getStartDate(),"yyyy-MM-dd");
				// endDateStr=DateUtil.format(workTimePOJO.getEndDate(),"yyyy-MM-dd");
				HashSet<String> monthSet = DateUtil.getAllDays(startDateStr,
						endDateStr);
				conditionMap.put("startTime", DateUtil.parse(startDateStr,
						DateUtil.YMD_DASH));
				Date date = DateUtil.parse(endDateStr, DateUtil.YMD_DASH);
				date.setHours(23);
				date.setMinutes(59);
				date.setSeconds(59);
				conditionMap.put("endTime", date);
				count = monthSet.size();
				columnsNameArray = new String[count];
				int i = 1;
				for (String item : monthSet) {
					columnName = "d"
							+ String.format("%02d", Integer.parseInt(item));
					columnsNameArray[i - 1] = columnName;
					i++;
					propertyMap.put(columnName, Class
							.forName("java.lang.String"));
				}
			} else {
				count = 31;
				columnsNameArray = new String[count];
				for (int i = 1; i <= count; i++) {
					columnName = "d" + String.format("%02d", i);
					columnsNameArray[i - 1] = columnName;
					propertyMap.put(columnName, Class
							.forName("java.lang.String"));
				}
			}
		} else if (vehicleWorkPOJO.getDirection() == 2) {// 年报
			if (StringUtils.isNotBlank(endDateStr)
					&& StringUtils.isNotBlank(startDateStr)) {// 只出现这几个月的信息
				HashSet<String> monthSet = com.chinaGPS.gtmp.util.DateUtil
						.getAllMonthList(startDateStr, endDateStr);
				conditionMap.put("startTime", DateUtil.parse(startDateStr
						+ "-01", DateUtil.YMD_DASH));
				Date date = new Date();
				date.setYear(Integer.parseInt(endDateStr.split("-")[0]) - 1900);
				date.setMonth(Integer.parseInt(endDateStr.split("-")[1]) - 1);
				date.setHours(23);
				date.setMinutes(59);
				date.setSeconds(59);
				conditionMap.put("endTime", date);
				count = monthSet.size();
				columnsNameArray = new String[count];
				int i = 1;
				for (String item : monthSet) {
					columnName = "m"
							+ String.format("%02d", Integer.parseInt(item));
					columnsNameArray[i - 1] = columnName;
					i++;
					propertyMap.put(columnName, Class
							.forName("java.lang.String"));
				}
			} else {
				count = 12;
				columnsNameArray = new String[count];
				for (int i = 1; i <= count; i++) {
					columnName = "m" + String.format("%02d", i);
					columnsNameArray[i - 1] = columnName;
					propertyMap.put(columnName, Class
							.forName("java.lang.String"));
				}
			}
		}
		HashMap<String, Object> result = new HashMap<String, Object>();
		String vehicleTypeP = getRequest().getParameter("vehicleType");
		String vehicleCodeP = getRequest().getParameter("vehicleCode");
		String vehicleArgP = getRequest().getParameter("vehicleArg");
		String vehicleStatusP = getRequest().getParameter("vehicleStatus");
		if(vehicleCodeP==null ||"null".equals(vehicleCodeP)||"全部".equals(vehicleCodeP)){
			vehicleCodeP="";
		}
		if(vehicleArgP==null ||"null".equals(vehicleArgP) ||"全部".equals(vehicleArgP)){
			vehicleArgP="";
		}
		conditionMap.put("vehicleType", vehicleTypeP);
		conditionMap.put("vehicleCode", vehicleCodeP);
		conditionMap.put("vehicleArg", vehicleArgP);
		if(vehicleStatusP!=null && !vehicleStatusP.equals(""))
		conditionMap.put("vehicleStatus", vehicleStatusP);
		conditionMap.put("columns", columnsNameArray);
		List<DynamicMalfunctionPOJO> list = statisticQueryService.statisticWorkHoursToPOJO(conditionMap, propertyMap);
		String typeName = null;// 类型
		String ccount = null;// 数量
		String yyyymm = null;// 年月
		DefaultXYDataset defaultXYDataset = new DefaultXYDataset();

		for (DynamicMalfunctionPOJO object : list) {
			double[][] datas=new double[2][columnsNameArray.length];
			for (int j = 0; j < columnsNameArray.length; j++) {
				yyyymm = String.valueOf(columnsNameArray[j].replace("m", ""));
				yyyymm = yyyymm.replace("d", "");
				datas[0][j] = Double.valueOf(yyyymm).doubleValue();
				ccount = String.valueOf(object.getValue(columnsNameArray[j]));
				datas[1][j] = Double.valueOf(ccount).doubleValue();
				typeName = String.valueOf(object.getValue("typeName"));
			}
			defaultXYDataset.addSeries(typeName, datas);
		}
		return defaultXYDataset;
	}


	
	/**
	 * @Title:createMalfunctionChart
	 * @Description:创建机械工作时间统计图表对象
	 * @param categorydataset
	 * @return
	 * @throws
	 */
	private JFreeChart createChart4WorkHours(XYDataset xyDataset) {
		// 解决中文乱码问题
		// 创建主题样式
		StandardChartTheme mChartTheme = new StandardChartTheme("CN");
		// 设置标题字体
		mChartTheme.setExtraLargeFont(new Font("黑体", Font.BOLD, 14));
		// 设置轴向字体
		mChartTheme.setLargeFont(new Font("宋体", Font.CENTER_BASELINE, 12));
		// 设置图例字体
		mChartTheme.setRegularFont(new Font("宋体", Font.CENTER_BASELINE, 12));
		// 应用主题样式
		ChartFactory.setChartTheme(mChartTheme);

		JFreeChart jfreechart = ChartFactory.createScatterPlot("机械工作时间统计图表", "时间", "工作小时数", xyDataset, PlotOrientation.VERTICAL, true, true, false);
		jfreechart.setBackgroundPaint(Color.white);
		jfreechart.setBorderPaint(Color.GREEN);  
        jfreechart.setBorderStroke(new BasicStroke(1.5f));
        
		XYPlot xyPlot = (XYPlot) jfreechart.getPlot();
		xyPlot.setBackgroundPaint(new Color(255, 253, 246));  
        ValueAxis vaaxis = xyPlot.getDomainAxis();  
        vaaxis.setAxisLineStroke(new BasicStroke(1.5f)); 
        
        ValueAxis va = xyPlot.getDomainAxis(0);  
        va.setAxisLineStroke(new BasicStroke(1.5f)); // 坐标轴粗细  
        va.setAxisLinePaint(new Color(215, 215, 215)); // 坐标轴颜色  
        xyPlot.setOutlineStroke(new BasicStroke(1.5f)); // 边框粗细  
        va.setLabelPaint(new Color(10, 10, 10)); // 坐标轴标题颜色  
        va.setTickLabelPaint(new Color(102, 102, 102)); // 坐标轴标尺值颜色  
        ValueAxis axis = xyPlot.getRangeAxis();  
        axis.setAxisLineStroke(new BasicStroke(1.5f)); 
        
		XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) xyPlot.getRenderer();
		xylineandshaperenderer.setSeriesOutlinePaint(0, Color.WHITE);  
        xylineandshaperenderer.setUseOutlinePaint(true);  
        NumberAxis numberaxis = (NumberAxis) xyPlot.getDomainAxis();  
        numberaxis.setAutoRangeIncludesZero(false);  
        numberaxis.setTickMarkInsideLength(2.0F);  
        numberaxis.setTickMarkOutsideLength(0.0F);
        if (vehicleWorkPOJO.getDirection() == 1) { // 月报
        	numberaxis.setLowerBound(1);
        	numberaxis.setUpperBound(31);
        } else { // 年报
        	numberaxis.setLowerBound(1);
        	numberaxis.setUpperBound(12);
        }
        //设置坐标数值为整数
        numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        numberaxis.setAxisLineStroke(new BasicStroke(1.5f));  
        NumberAxis numberaxis1 = (NumberAxis) xyPlot.getRangeAxis();  
        numberaxis1.setTickMarkInsideLength(2.0F);  
        numberaxis1.setTickMarkOutsideLength(0.0F);  
        numberaxis1.setAxisLineStroke(new BasicStroke(1.5f));

		return jfreechart;
	}
	
	private HashMap setConditionMap(HashMap conditionMap,VehicleWorkPOJO vehicleWorkPOJO) throws Exception{
		UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
		List<RolePOJO> roles = userPOJO.getRoles();
		boolean isDealer = false;// 是否是经销商
		DepartPOJO departPOJO = null;
		if (!roles.isEmpty()) {
			if (roles.size() == 1) {
				RolePOJO role = roles.get(0);
				if (role.getRoleId() == Constants.DEALER_ROLE_ID) {// 如果是经销商的话
					isDealer = true;
				}
			}
		}
		if (isDealer) {// 经销商
			String[] dealerIds = new String[1];// 查询更多条件中的经销商数组
			departPOJO = userPOJO.getDepartInfo();
			dealerIds[0] = String.valueOf(departPOJO.getDealerId());
			conditionMap.put("dealerIds", dealerIds);
			vehicleWorkPOJO.setDealerIds(dealerIds);
		}else{
			if(vehicleWorkPOJO.getDealerId()!= null && !"".equals(vehicleWorkPOJO.getDealerId())){
				String[] dealerIdsParam = vehicleWorkPOJO.getDealerId().split(",");
				conditionMap.put("dealerIds", dealerIdsParam);
				vehicleWorkPOJO.setDealerIds(dealerIdsParam);
				//conditionMap.put("vehicleStatus", 3);
			}
		}		
		return conditionMap;
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

	public JFreeChart getChart() {
		return chart;
	}

	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}

	public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}


    
}

