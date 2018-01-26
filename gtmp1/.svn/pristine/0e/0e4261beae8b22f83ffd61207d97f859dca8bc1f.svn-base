package com.chinaGPS.gtmp.action.run;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.chinaGPS.gtmp.action.common.BaseAction;
import com.chinaGPS.gtmp.entity.DicMalfunctionCode;
import com.chinaGPS.gtmp.entity.DynamicColumn;
import com.chinaGPS.gtmp.entity.DynamicMalfunctionPOJO;
import com.chinaGPS.gtmp.entity.MalfunctionPOJO;
import com.chinaGPS.gtmp.service.IMalfunctionService;
import com.chinaGPS.gtmp.util.DateUtil;
import com.chinaGPS.gtmp.util.StringUtils;
import com.chinaGPS.gtmp.util.page.PageSelect;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Package:com.chinaGPS.gtmp.action.run
 * @ClassName:MalfunctionAction
 * @Description:故障诊断Action
 * @author:zfy
 * @date:2013-5-3 下午03:18:01
 */
@Scope("prototype")
@Controller
public class MalfunctionAction extends BaseAction implements ModelDriven<MalfunctionPOJO> {
	private static final long serialVersionUID = -1255980836213567691L;
	private static Logger logger = LoggerFactory.getLogger(MalfunctionAction.class);

	@Resource
	private MalfunctionPOJO malfunctionPOJO;
	@Resource
	private DicMalfunctionCode dicMalfunctionCode;
	@Resource
	private IMalfunctionService malfunctionService;
	private int page;
	private int rows;
	@Resource
	private PageSelect pageSelect;

	private String startDateStr;
	private String endDateStr;
	private JFreeChart chart;

	/**
	 * @Title:getAlarmType
	 * @Description:得到故障码
	 * @throws Exception
	 * @throws
	 */
	public void getDicMalfunctionCodeInfo() throws Exception {

		DicMalfunctionCode dicMalfunctionCode2 = new DicMalfunctionCode();
		dicMalfunctionCode2.setMalfunction("全部");
		dicMalfunctionCode2.setMalfunctionCode("");

		List<DicMalfunctionCode> result = new ArrayList<DicMalfunctionCode>();

		// 如果不是故障诊断查询解决方案
		if (dicMalfunctionCode != null) {
			if (StringUtils.isBlank(dicMalfunctionCode.getMalfunctionCode())) {
				result.add(dicMalfunctionCode2);
			}
		}
		result.addAll(malfunctionService
				.getDicMalfunctionCode(dicMalfunctionCode));

		renderObject(result);
	}

	public void search() throws Exception {
		pageSelect.setPage(page);
		pageSelect.setRows(rows);
		Map map = new HashMap();
		map.put("malfunction", malfunctionPOJO);
		int total = malfunctionService.countAll(map);
		List<MalfunctionPOJO> resultList = malfunctionService.getByPage(map, new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		/*for(MalfunctionPOJO malfunctionPOJO:resultList){
			String temp = malfunctionPOJO.getVehicleState();
			if(null!=temp && !"".equals(temp))
				malfunctionPOJO.setVehicleState(temp.replace("熄火", "ACC关").replace("点火", "ACC开"));	
		}*/
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		renderObject(result);
	}

	/**
	 * @Title:statistic_search
	 * @Description:故障统计
	 * @throws Exception
	 * @throws
	 */
	public void statistic_search() throws Exception {
		// 条件
		HashMap<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("reportType", malfunctionPOJO.getReportType());
		conditionMap.put("modelTypeId", malfunctionPOJO.getModelName());
		conditionMap.put("malfunctionCode", malfunctionPOJO.getMalfunctionCode());
		conditionMap.put("vehicleCode", malfunctionPOJO.getVehicleCode());
		conditionMap.put("vehicleArg", malfunctionPOJO.getVehicleArg());
		conditionMap.put("dealerIds", malfunctionPOJO.getDealerIds());

		LinkedHashMap<String, Object> propertyMap = new LinkedHashMap<String, Object>();
		propertyMap.put("typeId", Class.forName("java.lang.String"));
		String[] columnsNameArray = null;
		int count = 0;
		String columnName = null;
		if (StringUtils.isNotBlank(endDateStr) && StringUtils.isNotBlank(startDateStr)) {// 只出现这几个月的信息
			conditionMap.put("startTime", DateUtil.parse(startDateStr + "-01", DateUtil.YMD_DASH));
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Integer.parseInt(endDateStr.split("-")[0]));
			cal.set(Calendar.MONTH, Integer.parseInt(endDateStr.split("-")[1]) - 1);
			cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			cal.set(Calendar.HOUR, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			conditionMap.put("endTime", cal.getTime());
			HashSet<String> monthSet = DateUtil.getAllYearMonthList(startDateStr, endDateStr);
			count = monthSet.size();
			columnsNameArray = new String[count];
			int i = 1;
			for (String item : monthSet) {
				columnName = "m" + item;
				columnsNameArray[i - 1] = columnName;
				i++;
				propertyMap.put(columnName, Class.forName("java.lang.String"));
			}
		} else {
			count = 12;
			int year = Calendar.getInstance().get(Calendar.YEAR);
			columnsNameArray = new String[count];
			for (int i = 1; i <= count; i++) {
				columnName = "m" + year + String.format("%02d", i);
				columnsNameArray[i - 1] = columnName;
				propertyMap.put(columnName, Class.forName("java.lang.String"));
			}
		}

		Set<String> set = propertyMap.keySet();
		Iterator<String> it = set.iterator();
		List<DynamicColumn> fieldsList = new ArrayList<DynamicColumn>();
		DynamicColumn dynamicCcolumn = null;
		String column = null;
		while (it.hasNext()) {
			column = it.next();
			dynamicCcolumn = new DynamicColumn();
			dynamicCcolumn.setField(column);
			dynamicCcolumn.setWidth(100);
			if (column.startsWith("m2")) {
				dynamicCcolumn.setTitle(column.replace("m", "").substring(0, 4)+ "年" + column.replace("m", "").substring(4) + "月");
			} else {
				dynamicCcolumn.setHidden(true);
				dynamicCcolumn.setTitle("typeId");
			}
			fieldsList.add(dynamicCcolumn);
		}
		
		DynamicColumn dc = new DynamicColumn();
		if(malfunctionPOJO.getReportType() == 1) { // 如果是机型
			if(malfunctionPOJO.getVehicleCode() != null && !"".equals(malfunctionPOJO.getVehicleCode())) { // 如果机械代号不为空
				dc.setTitle("机械代号");
				dc.setField("vehicleCode");
				fieldsList.add(0, dc);
				propertyMap.put("vehicleCode", Class.forName("java.lang.String"));
				if(malfunctionPOJO.getVehicleArg() != null && !"".equals(malfunctionPOJO.getVehicleArg())) { // 如果配置号不为空
					dc = new DynamicColumn();
					dc.setTitle("配置号");
					dc.setField("vehicleArg");
					fieldsList.add(1, dc);
					propertyMap.put("vehicleArg", Class.forName("java.lang.String"));
				}
			} else {
				dc.setTitle("机械型号");
				dc.setField("modelName");
				fieldsList.add(0, dc);
				propertyMap.put("modelName", Class.forName("java.lang.String"));
			}
		} else if (malfunctionPOJO.getReportType() == 2) { // 如果是故障类型
			dc.setTitle("故障类型");
			dc.setField("malfunction");
			fieldsList.add(0, dc);
			propertyMap.put("malfunction", Class.forName("java.lang.String"));
		} else {
			dc.setTitle("区域/经销商");
			dc.setField("dealerName");
			fieldsList.add(0, dc);
			propertyMap.put("dealerName", Class.forName("java.lang.String"));
		}
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		conditionMap.put("columns", columnsNameArray);
		List<Object> list = malfunctionService.statisticMalfunction(conditionMap, propertyMap);
		result.put("rows", list);
		result.put("total", 0);
		List<List<DynamicColumn>> columnsListWrap = new ArrayList<List<DynamicColumn>>();
		columnsListWrap.add(fieldsList);
		result.put("columns", columnsListWrap);

		renderObject(result);

	}

	public void exportToExcelStatistic() throws Exception {
		List<String> fieldsList2 = new ArrayList<String>();
		
		// 条件
		HashMap<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("reportType", malfunctionPOJO.getReportType());
		conditionMap.put("modelTypeId", malfunctionPOJO.getModelName());
		conditionMap.put("malfunctionCode", malfunctionPOJO.getMalfunctionCode());
		conditionMap.put("vehicleCode", malfunctionPOJO.getVehicleCode());
		conditionMap.put("vehicleArg", malfunctionPOJO.getVehicleArg());
		conditionMap.put("dealerIds", malfunctionPOJO.getDealerIds());

		LinkedHashMap<String, Object> propertyMap = new LinkedHashMap<String, Object>();
		
		if(malfunctionPOJO.getReportType() == 1) { // 如果是机型
			if(malfunctionPOJO.getVehicleCode() != null && !"".equals(malfunctionPOJO.getVehicleCode())) { // 如果机械代号不为空
				fieldsList2.add(0, "机械代号");
				propertyMap.put("vehicleCode", Class.forName("java.lang.String"));
				if(malfunctionPOJO.getVehicleArg() != null && !"".equals(malfunctionPOJO.getVehicleArg())) { // 如果配置号不为空
					fieldsList2.add(1, "配置号");
					propertyMap.put("vehicleArg", Class.forName("java.lang.String"));
				}
			} else {
				fieldsList2.add(0, "机械型号");
				propertyMap.put("modelName", Class.forName("java.lang.String"));
			}
		} else if (malfunctionPOJO.getReportType() == 2) { // 如果是故障类型
			fieldsList2.add(0, "故障类型");
			propertyMap.put("malfunction", Class.forName("java.lang.String"));
		} else {
			fieldsList2.add(0, "区域/经销商");
			propertyMap.put("dealerName", Class.forName("java.lang.String"));
		}
		
		String[] columnsNameArray = null;
		int count = 0;
		String columnName = null;
		if (StringUtils.isNotBlank(endDateStr) && StringUtils.isNotBlank(startDateStr)) {// 只出现这几个月的信息
			HashSet<String> monthSet = DateUtil.getAllYearMonthList(startDateStr, endDateStr);
			conditionMap.put("startTime", DateUtil.parse(startDateStr + "-01", DateUtil.YMD_DASH));
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Integer.parseInt(endDateStr.split("-")[0]));
			cal.set(Calendar.MONTH, Integer.parseInt(endDateStr.split("-")[1]) - 1);
			cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			cal.set(Calendar.HOUR, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			conditionMap.put("endTime", cal.getTime());
			count = monthSet.size();
			columnsNameArray = new String[count];
			int i = 1;
			for (String item : monthSet) {
				columnName = "m" + item;
				columnsNameArray[i - 1] = columnName;
				i++;
				propertyMap.put(columnName, Class.forName("java.lang.String"));
			}
		} else {
			count = 12;
			int year = Calendar.getInstance().get(Calendar.YEAR);
			columnsNameArray = new String[count];
			for (int i = 1; i <= count; i++) {
				columnName = "m" + year + String.format("%02d", i);
				columnsNameArray[i - 1] = columnName;
				propertyMap.put(columnName, Class.forName("java.lang.String"));
			}
		}

		Set<String> set = propertyMap.keySet();
		Iterator<String> it = set.iterator();
		String[] columnSqlArray = new String[set.size()];
		String column = null;
		int columnIndex = 0;
		String title = "";
		while (it.hasNext()) {
			column = it.next();
			columnSqlArray[columnIndex] = column;
			columnIndex++;
			if(column.startsWith("m2")) {
				title = column.replace("m", "").substring(0, 4)+ "年" + column.replace("m", "").substring(4) + "月";
				fieldsList2.add(title);
			}
		}
		conditionMap.put("columns", columnsNameArray);
		
		List<DynamicMalfunctionPOJO> list = malfunctionService.statisticMalfunctionToPOJO(conditionMap, propertyMap);
		List<Object[]> values = new ArrayList<Object[]>();
		for (DynamicMalfunctionPOJO object : list) {
			String[] row = new String[columnSqlArray.length];
			for (int j = 0; j < columnSqlArray.length; j++) {
				row[j] = String.valueOf(object.getValue(columnSqlArray[j]));
			}
			values.add(row);
		}
		super.renderExcel("故障统计" + ".xls", fieldsList2.toArray(), values);
	}

	/**
	 * @Title:drawChart
	 * @Description:图表
	 * @return
	 * @throws
	 */
	public String drawChart() {
		try {
			// chart = createChart(createDataset());
			chart = createMalfunctionChart(createDataSet4Malfunction());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}
	
	public void downloadChart() {
		try {
			chart = createMalfunctionChart(createDataSet4Malfunction());
			HttpServletResponse resp = BaseAction.getResponse();
			resp.setContentType("application/octet-stream");
			resp.setHeader("Content-Disposition", "attachment; filename=alarm_chart.png");
			ChartUtilities.writeChartAsPNG(resp.getOutputStream(), chart, 1024, 768);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public CategoryDataset createDataSet4Malfunction() throws Exception {
		// 条件
		HashMap<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("reportType", malfunctionPOJO.getReportType());
		conditionMap.put("modelTypeId", malfunctionPOJO.getModelName());
		conditionMap.put("malfunctionCode", malfunctionPOJO.getMalfunctionCode());
		conditionMap.put("vehicleCode", malfunctionPOJO.getVehicleCode());
		conditionMap.put("vehicleArg", malfunctionPOJO.getVehicleArg());
		conditionMap.put("dealerIds", malfunctionPOJO.getDealerIds());

		LinkedHashMap<String, Object> propertyMap = new LinkedHashMap<String, Object>();
		if(malfunctionPOJO.getReportType() == 1) { // 如果是机型
			if(malfunctionPOJO.getVehicleCode() != null && !"".equals(malfunctionPOJO.getVehicleCode())) { // 如果机械代号不为空
				propertyMap.put("vehicleCode", Class.forName("java.lang.String"));
				if(malfunctionPOJO.getVehicleArg() != null && !"".equals(malfunctionPOJO.getVehicleArg())) { // 如果配置号不为空
					propertyMap.put("vehicleArg", Class.forName("java.lang.String"));
				}
			} else {
				propertyMap.put("modelName", Class.forName("java.lang.String"));
			}
		} else if (malfunctionPOJO.getReportType() == 2) { // 如果是故障类型
			propertyMap.put("malfunction", Class.forName("java.lang.String"));
		} else {
			propertyMap.put("dealerName", Class.forName("java.lang.String"));
		}
		
		String[] columnsNameArray = null;
		int count = 0;
		String columnName = null;
		if (StringUtils.isNotBlank(endDateStr) && StringUtils.isNotBlank(startDateStr)) {// 只出现这几个月的信息
			HashSet<String> monthSet = DateUtil.getAllYearMonthList(startDateStr, endDateStr);
			conditionMap.put("startTime", DateUtil.parse(startDateStr + "-01", DateUtil.YMD_DASH));
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Integer.parseInt(endDateStr.split("-")[0]));
			cal.set(Calendar.MONTH, Integer.parseInt(endDateStr.split("-")[1]) - 1);
			cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			cal.set(Calendar.HOUR, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			conditionMap.put("endTime", cal.getTime());
			count = monthSet.size();
			columnsNameArray = new String[count];
			int i = 1;
			for (String item : monthSet) {
				columnName = "m" + item;
				columnsNameArray[i - 1] = columnName;
				i++;
				propertyMap.put(columnName, Class.forName("java.lang.String"));
			}
		} else {
			count = 12;
			int year = Calendar.getInstance().get(Calendar.YEAR);
			columnsNameArray = new String[count];
			for (int i = 1; i <= count; i++) {
				columnName = "m" + year + String.format("%02d", i);
				columnsNameArray[i - 1] = columnName;
				propertyMap.put(columnName, Class.forName("java.lang.String"));
			}
		}
		conditionMap.put("columns", columnsNameArray);
		
		List<DynamicMalfunctionPOJO> list = malfunctionService.statisticMalfunctionToPOJO(conditionMap, propertyMap);
		String typeName = null;// 类型
		String ccount = null;// 数量
		String yyyymm = null;// 年月
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();

		for (DynamicMalfunctionPOJO object : list) {
			for (int j = 0; j < columnsNameArray.length; j++) {
				ccount = String.valueOf(object.getValue(columnsNameArray[j]));
				yyyymm = columnsNameArray[j].replace("m", "").substring(0, 4) + "-" + columnsNameArray[j].replace("m", "").substring(4);
				typeName = String.valueOf(object.getValue("malfunction"));
				
				if("null".equals(typeName)) {
					if ("null".equals(String.valueOf(object.getValue("dealerName")))) {
						if(object.getValue("vehicleCode") != null) {
							if (object.getValue("vehicleArg") != null) {
								typeName = String.valueOf(object.getValue("vehicleCode")) + String.valueOf(object.getValue("vehicleArg"));
							} else {
								typeName = String.valueOf(object.getValue("vehicleCode"));
							}
						} else {
							typeName = String.valueOf(object.getValue("modelName"));
						}
					} else {
						typeName = String.valueOf(object.getValue("dealerName"));
					}
				}
				defaultcategorydataset.addValue(Integer.parseInt(ccount), typeName, yyyymm);
			}
		}
		return defaultcategorydataset;
	}

	/**
	 * @Title:createMalfunctionChart
	 * @Description:创建故障统计图表对象
	 * @param categorydataset
	 * @return
	 * @throws
	 */
	private JFreeChart createMalfunctionChart(CategoryDataset categorydataset) {
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

		JFreeChart jfreechart = ChartFactory.createLineChart("故障统计图表",
				"年月", "数量（次）", categorydataset, PlotOrientation.VERTICAL, true,
				true, false);
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

	@Override
	public MalfunctionPOJO getModel() {
		return malfunctionPOJO;
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

	public IMalfunctionService getMalfunctionService() {
		return malfunctionService;
	}

	public void setMalfunctionService(IMalfunctionService malfunctionService) {
		this.malfunctionService = malfunctionService;
	}

	public void setDicMalfunctionCode(DicMalfunctionCode dicMalfunctionCode) {
		this.dicMalfunctionCode = dicMalfunctionCode;
	}

	public DicMalfunctionCode getDicMalfunctionCode() {
		return dicMalfunctionCode;
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

	public JFreeChart getChart() {
		return chart;
	}

	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}
}
