package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.chinaGPS.gtmp.entity.CompositeQueryConditionPOJO;
import com.chinaGPS.gtmp.entity.DealerAreaPOJO;
import com.chinaGPS.gtmp.entity.DealerVehiclePOJO;
import com.chinaGPS.gtmp.entity.TreeNode;
import com.chinaGPS.gtmp.entity.VehiclePOJO;
import com.chinaGPS.gtmp.entity.VehicleUnitPOJO;
import com.chinaGPS.gtmp.service.IAlarmService;
import com.chinaGPS.gtmp.service.IDealerAreaService;
import com.chinaGPS.gtmp.service.IHistoryService;
import com.chinaGPS.gtmp.service.ISendCommandService;
import com.chinaGPS.gtmp.service.impl.UnitServiceImpl;
import com.chinaGPS.gtmp.util.Constants;
import com.chinaGPS.gtmp.util.page.PageSelect;

/**
 * @Package:test
 * @ClassName:UnitServiceImpleTest
 * @Description:批量插入service层测试类
 * @author:lxj
 * @date:Dec 14, 2012 2:50:32 PM
 * 
 */
public class DealerVehicleServiceImpleTest {
	private static Logger logger = Logger.getLogger("OPERATION");
	private static Logger exceptionLogger = Logger.getLogger("EXCEPTION");
	private static IDealerAreaService service;
	private static IHistoryService historyService;
	private static ISendCommandService sendCommandService;
	private static IAlarmService alarmService;

	private static Logger log = Logger.getLogger(UnitServiceImpl.class);

	@BeforeClass
	public static void setUpBeforeClass() {
		try {
			ApplicationContext ctx = new FileSystemXmlApplicationContext(
					"classpath:spring/applicationContext-*.xml");
			service = (IDealerAreaService) ctx.getBean("dealerAreaServiceImpl");

			historyService = (IHistoryService) ctx
					.getBean("historyServiceImpl");
			sendCommandService = (ISendCommandService) ctx
					.getBean("sendCommandServiceImpl");
			alarmService = (IAlarmService) ctx.getBean("alarmServiceImpl");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * @Test public void testsaveOrUpdate() throws Exception { UnitPOJO unit =
	 * new UnitPOJO(); unit.setUnitId("1001TGL30-201212111006");
	 * unit.setMaterialNo("A1"); unit.setGpsSn("TGL30-201212111006");
	 * unit.setGpsType("TGL30"); unit.setSimNo("15921867006");
	 * unit.setSteelNo("20121211700006"); unit.setSoftwareVersion("v3.0");
	 * unit.setSupplier("1001");
	 * 
	 * unitService.saveOrUpdate(unit); }
	 */
	/**
	 * @Title:getVehilces
	 * @Description:搜索
	 * @throws
	 */
	public void getVehilces(VehiclePOJO vehiclePOJO) throws Exception {
		HashMap mapSelect = new HashMap();
		if (vehiclePOJO != null) {
			if (StringUtils.isNotEmpty(vehiclePOJO.getVehicleDef())) {
				mapSelect.put("vehicleDef", vehiclePOJO.getVehicleDef());
			}
		}
		// 查询经销商id和机械
		List<DealerVehiclePOJO> dealerVehicleList = service.getVehilcles(mapSelect);
		Map vehicleMap = new HashMap();
		List<TreeNode<VehiclePOJO>> vehicleNodeList = null;
		for (DealerVehiclePOJO dealerVehiclePOJO : dealerVehicleList) {
			vehicleNodeList = new ArrayList<TreeNode<VehiclePOJO>>();
			for (VehicleUnitPOJO dealerVehiclePOJO2 : dealerVehiclePOJO
					.getVehicleList()) {
				TreeNode node = new TreeNode();
				node.setId(Constants.NODE_VEHICLE_PREFIX
						+ dealerVehiclePOJO2.getVehicleId());
				node.setText(dealerVehiclePOJO2.getVehicleDef());
				node.setIconCls(Constants.NODE_ONLINE_ICON);
				node.setAttributes(vehiclePOJO);
				vehicleNodeList.add(node);
			}
			vehicleMap.put(dealerVehiclePOJO.getDealerId(), vehicleNodeList);
		}
		DealerAreaPOJO dealerAreaPOJO = new DealerAreaPOJO();
		dealerAreaPOJO.setDealerVehicleList(dealerVehicleList);
		// 查询片区和经销商
		List<DealerAreaPOJO> dealerAreaList = service.getList(dealerAreaPOJO);
		List<TreeNode> mapAreaList = new ArrayList<TreeNode>();
		Map mapRreaMap = new HashMap();
		for (DealerAreaPOJO dealerAreaPOJO2 : dealerAreaList) {
			TreeNode node = new TreeNode();
			node.setId(dealerAreaPOJO2.getId());
			node.setText(dealerAreaPOJO2.getName());
			if (StringUtils.isNotEmpty(dealerAreaPOJO2.getPid())) {
				node.setParentId(dealerAreaPOJO2.getPid());
			}
			mapRreaMap.put(dealerAreaPOJO2.getId(), node);

		}
		for (DealerAreaPOJO dealerAreaPOJO3 : dealerAreaList) {
			if (StringUtils.isNotEmpty(dealerAreaPOJO3.getPid())) { // 有父节点
				TreeNode pnode = (TreeNode) mapRreaMap.get(dealerAreaPOJO3
						.getPid());
				TreeNode cnode = (TreeNode) mapRreaMap.get(dealerAreaPOJO3
						.getId());
				// 设置机械
				cnode.setChildren((List<TreeNode<VehiclePOJO>>) vehicleMap
						.get(dealerAreaPOJO3.getId()));
				pnode.addChild(cnode);

			} else {// 无父节点
				mapAreaList.add((TreeNode) mapRreaMap.get(dealerAreaPOJO3
						.getId()));
			}
		}
		System.out.println(mapAreaList.toString());
	}

	public static void main(String[] args) throws Exception {
		setUpBeforeClass();
		DealerVehicleServiceImpleTest test = new DealerVehicleServiceImpleTest();
		// test.testadd();
		// test.testaddUnitList();
		HashMap map = new HashMap();
		map.put("dealerId", "25");
		List<DealerVehiclePOJO> list = service.getVehilcles(map);
		// System.out.println(list.get(0).getVehicleList().size());
		// System.out.println(service.getVehilclesInDealer(map).size());
		VehiclePOJO vehiclePOJO = new VehiclePOJO();
		vehiclePOJO.setVehicleDef("A001");
		// test.getVehilces(vehiclePOJO);
		// System.out.println("42afa9671c374688b588007643c9766f ".length());

		// VehicleUnitPOJO vehicleUnitPOJO=(VehicleUnitPOJO)
		// jeu.fromJson(vehicleUnitPOJOs.get(i), VehicleUnitPOJO.class);
		/*
		 * HistoryPOJO history=new HistoryPOJO();
		 * history.setUnitId("083245416");
		 * history.setStartTime(DateUtil.parse("2013-04-18 8:19:26",
		 * DateUtil.YMD_DASH_WITH_FULLTIME24));
		 * history.setEndTime(DateUtil.parse("2013-04-20 8:19:26",
		 * DateUtil.YMD_DASH_WITH_FULLTIME24));
		 * System.out.println(historyService.getList(history).size());
		 */

		// 综合查询
		// test.getComposite();
		// 写入操作日志
		/*
		 * MDC.put("logId",UUID.randomUUID().toString()); MDC.put("userId",1);
		 * String ip="192.168.3.171"; //logger.info("test");
		 * exceptionLogger.error("test");
		 */

		// 警情查询
		/*
		 * PageSelect pageSelect=new PageSelect(); pageSelect.setPage(1);
		 * pageSelect.setRows(10); AlarmPOJO condition=new AlarmPOJO();
		 * condition.setIsRead(0); HashMap alarmSelectMap=new HashMap();
		 * alarmSelectMap.put("alarm", condition);
		 * System.out.println(alarmService.countAll(alarmSelectMap));
		 * alarmService.getByPage(map, new RowBounds(0,10));
		 * System.out.println("over");
		 */
		System.out.println(alarmService.getAllAlarmType(null).size());

	}

	public void getComposite() throws Exception {
		PageSelect pageSelect = new PageSelect();
		pageSelect.setPage(1);
		pageSelect.setRows(10);
		CompositeQueryConditionPOJO condition = new CompositeQueryConditionPOJO();
		condition.setSimNo("18627110001");
		HashMap map = service.queryComposite(condition, pageSelect);
		System.out.println(map.get("total"));
	}

}
