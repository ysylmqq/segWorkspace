package com.chinaGPS.gtmp.action.run;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.liteframework.core.web.util.JsonExtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.chinaGPS.gtmp.action.common.BaseAction;
import com.chinaGPS.gtmp.business.command.SendCommandMessage;
import com.chinaGPS.gtmp.business.memcache.GatewayBack;
import com.chinaGPS.gtmp.business.memcache.MemCache;
import com.chinaGPS.gtmp.business.memcache.Position;
import com.chinaGPS.gtmp.business.memcache.UnitBack;
import com.chinaGPS.gtmp.entity.CommandPOJO;
import com.chinaGPS.gtmp.entity.CompositeQueryConditionPOJO;
import com.chinaGPS.gtmp.entity.ConditionsPOJO;
import com.chinaGPS.gtmp.entity.DealerAreaPOJO;
import com.chinaGPS.gtmp.entity.DealerVehiclePOJO;
import com.chinaGPS.gtmp.entity.DepartPOJO;
import com.chinaGPS.gtmp.entity.DicCommandType;
import com.chinaGPS.gtmp.entity.MapTagPOJO;
import com.chinaGPS.gtmp.entity.RemoteUpgradePOJO;
import com.chinaGPS.gtmp.entity.RolePOJO;
import com.chinaGPS.gtmp.entity.SystemParamPOJO;
import com.chinaGPS.gtmp.entity.TLastConditionsPOJO;
import com.chinaGPS.gtmp.entity.TestCommandPOJO;
import com.chinaGPS.gtmp.entity.TreeNode;
import com.chinaGPS.gtmp.entity.UnitPOJO;
import com.chinaGPS.gtmp.entity.UserPOJO;
import com.chinaGPS.gtmp.entity.VehiclePOJO;
import com.chinaGPS.gtmp.entity.VehicleSalePOJO;
import com.chinaGPS.gtmp.entity.VehicleUnitPOJO;
import com.chinaGPS.gtmp.service.IDealerAreaService;
import com.chinaGPS.gtmp.service.IMapTagService;
import com.chinaGPS.gtmp.service.ISendCommandService;
import com.chinaGPS.gtmp.service.IVehicleService;
import com.chinaGPS.gtmp.service.OwnerService;
import com.chinaGPS.gtmp.util.ConditionDispose;
import com.chinaGPS.gtmp.util.ConditionUnit;
import com.chinaGPS.gtmp.util.Constants;
import com.chinaGPS.gtmp.util.DateUtil;
import com.chinaGPS.gtmp.util.HttpURLConnectionUtil;
import com.chinaGPS.gtmp.util.page.PageSelect;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Package:com.chinaGPS.gtmp.action.run
 * @ClassName:RunAction
 * @Description:
 * @author:zfy
 * @date:2013-4-15 下午04:45:33
 */
@Scope("prototype")
@Controller
public class OwnerAction extends BaseAction implements ModelDriven<VehiclePOJO> {
	private static final long serialVersionUID = 6817759622535017538L;
	private static Logger logger = LoggerFactory.getLogger(OwnerAction.class);
	@Resource
	private IDealerAreaService dealerAreaService;
	@Resource
	private OwnerService ownerService;
	@Resource
	private IMapTagService mapTagService;
	@Resource
	private MemCache memCache;
	@Resource
	private ISendCommandService sendCommandService;
	@Resource
	private SendCommandMessage sendCommandMessage;
	@Resource
	private MapTagPOJO mapTagPOJO;
	@Resource
	private VehiclePOJO vehiclePOJO;
	@Resource
	private DealerAreaPOJO dealerAreaPOJO;
	@Autowired
	private VehicleSalePOJO vehicleSalePOJO;
	@Resource
	private IVehicleService vehicleService;
	@Resource
	private UnitPOJO unit;
	@Resource
	private CompositeQueryConditionPOJO condition;
	@Resource
	private PageSelect pageSelect;

	private String id;
	private String[] dealerIds;// 查询更多条件中的经销商数组
	private List<String> vehicleUnitPOJOs;// 监控列表中的vehicleunitpojo

	private int page;
	private int rows;

	private Date startTime;
	private Date endTime;

	private String startTimeStr;
	private String endTimeStr;

	/**
	 * @Title:query
	 * @Description:异步加载机械列表(包括片区和经销商)
	 * @return
	 * @throws
	 */
	public String getDealerAreasList() {
		List<Map> mapList = new ArrayList<Map>();
		try {
			Map childMap = null;
			Map attrMap = null;
			List<Map> districtChildList = new ArrayList<Map>();

			UserPOJO userPOJO = (UserPOJO) getSession()
					.get(Constants.USER_INFO);
			List<RolePOJO> roles = userPOJO.getRoles();
			boolean isDealer = false;// 是否是经销商
			boolean isLeaseHold = false; // 是否是融资租赁
			DepartPOJO departPOJO = null;
					// 要查找机械
						HashMap map = new HashMap();
						//TODO暂时写死
						map.put("ownerId", userPOJO.getUserId());  
						// 只查询出已销售、有效的机械
						map.put("vehicleStatus", Constants.VEHICLE_STATE3);
						map.put("isValid", Constants.IS_VALID_YES);
						Map mapRoot = new HashMap();
						mapRoot.put("id", Constants.ROOT_ID);
						mapRoot.put("text", Constants.ROOT_TEXT);
						mapRoot.put("state", Constants.NODE_STATE_OPEN);
						
						List<VehicleUnitPOJO> dealerAreaList = ownerService.getVehilclesInDealer(map);
						for (VehicleUnitPOJO vehiclePOJO : dealerAreaList) {
							childMap = new HashMap();
							childMap.put("id", Constants.NODE_VEHICLE_PREFIX
									+ vehiclePOJO.getVehicleId());
							childMap.put("text", vehiclePOJO.getVehicleDef());
							childMap.put("type", 4);
							childMap.put("attributes", vehiclePOJO);
							if (vehiclePOJO.getIsLogin() != null
									&& vehiclePOJO.getIsLogin().intValue() == 0) {// 在线
								childMap.put("iconCls",	Constants.NODE_ONLINE_ICON);
							} else {// 离线
								childMap.put("iconCls",	Constants.NODE_OFFLINE_ICON);
							}
							districtChildList.add(childMap);
						}
						mapRoot.put("children", districtChildList);
						mapList.add(mapRoot);// 添加父节点


			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		renderObject(mapList);
		return NONE;
	}
	
	/**
	 * @Title:query
	 * @Description:详细查询异步加载机械列表()
	 * @return
	 * @throws
	 */
	public String getDealerAreasListXx() {
		List<Map> mapList = new ArrayList<Map>();
		try {
			Map childMap = null;
			Map attrMap = null;
			List<Map> districtChildList = new ArrayList<Map>();

			UserPOJO userPOJO = (UserPOJO) getSession()
					.get(Constants.USER_INFO);
			List<RolePOJO> roles = userPOJO.getRoles();
			boolean isDealer = false;// 是否是经销商
			boolean isLeaseHold = false; // 是否是融资租赁
			DepartPOJO departPOJO = null;
			String vehicleDef = getRequest().getParameter("vehicleDef");
					// 要查找机械
						HashMap map = new HashMap();
						//TODO暂时写死
						map.put("ownerId", userPOJO.getUserId());  
						map.put("vehicleDef", vehicleDef);  
						// 只查询出已销售、有效的机械
						map.put("vehicleStatus", Constants.VEHICLE_STATE3);
						map.put("isValid", Constants.IS_VALID_YES);
						
						List<VehicleUnitPOJO> dealerAreaList = ownerService.getVehilclesInDealer(map);
						for (VehicleUnitPOJO vehiclePOJO : dealerAreaList) {
							childMap = new HashMap();
							childMap.put("id", Constants.NODE_VEHICLE_PREFIX
									+ vehiclePOJO.getVehicleId());
							childMap.put("text", vehiclePOJO.getVehicleDef());
							childMap.put("type", 4);
							if (vehiclePOJO.getIsLogin() != null
									&& vehiclePOJO.getIsLogin().intValue() == 0) {// 在线
								childMap.put("iconCls",	Constants.NODE_ONLINE_ICON);
							} else {// 离线
								childMap.put("iconCls",	Constants.NODE_OFFLINE_ICON);
							}
							childMap.put("attributes", vehiclePOJO);
							mapList.add(childMap);
						}


			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		renderObject(mapList);
		return NONE;
	}

	/**
	 * @Title:getRootNode
	 * @Description:获得根节点
	 * @throws
	 */
	public void getRootNode() {
		List<Map> mapList = new ArrayList<Map>();
		Map mapRoot = new HashMap();
		mapRoot.put("id", Constants.ROOT_ID);
		mapRoot.put("text", Constants.ROOT_TEXT);
		mapRoot.put("state", Constants.NODE_STATE_OPEN);
		mapList.add(mapRoot);
		renderObject(mapList);
	}

	/**
	 * @Title:getVehilces
	 * @Description:搜索(更多条件),并构成一棵树
	 * @throws
	 */
	public void getVehilces() {
		// 判断是否是经销商
		UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
		List<RolePOJO> roles = userPOJO.getRoles();
		boolean isDealer = false;// 是否是经销商
		boolean isLeaseHold = false; // 是否是融资租赁
		DepartPOJO departPOJO = null;
		if (!roles.isEmpty()) {
			if (roles.size() == 1) {
				RolePOJO role = roles.get(0);
				if (role.getRoleId() == Constants.DEALER_ROLE_ID) {// 如果是经销商的话
					isDealer = true;
				} else if (role.getRoleId() == Constants.LEASEHOLD_ROLE_ID) { // 如果是融资租赁角色的话
					isLeaseHold = true;
				}
			}
		}

		HashMap mapSelect = new HashMap();
		List<TreeNode> mapAreaList = new ArrayList<TreeNode>();

		try {
			if (vehiclePOJO != null) {
				if (StringUtils.isNotEmpty(vehiclePOJO.getVehicleDef())) {
					mapSelect.put("vehicleDef", vehiclePOJO.getVehicleDef());
				}
				if (dealerIds != null && dealerIds.length > 0) {
					if (isDealer) {// 经销商
						departPOJO = userPOJO.getDepartInfo();
						dealerIds[0] = String.valueOf(departPOJO.getDealerId());
					}
					mapSelect.put("dealerIds", dealerIds);
				}
				if (vehiclePOJO.getTypeId() != null) {
					mapSelect.put("typeId", vehiclePOJO.getTypeId());
				}
				if (vehiclePOJO.getModelId() != null) {
					mapSelect.put("modelId", vehiclePOJO.getModelId());
				}
				if (StringUtils.isNotEmpty(vehiclePOJO.getMaterialNo())) {
					mapSelect.put("materialNo", vehiclePOJO.getMaterialNo());
				}
				if (StringUtils.isNotEmpty(vehiclePOJO.getSteelNo())) {
					mapSelect.put("steelNo", vehiclePOJO.getSteelNo());
				}
				if (StringUtils.isNotEmpty(vehiclePOJO.getUnitSn())) {
					mapSelect.put("unitSn", vehiclePOJO.getUnitSn());
				}
				// 是否在线,用isValid属性替代
				if (vehiclePOJO.getIsValid() != null) {
					mapSelect.put("isLogin", vehiclePOJO.getIsValid());
				}
				// 是否工作,用testFlag属性替代
				if (vehiclePOJO.getTestFlag() != null) {
					mapSelect.put("isWork", vehiclePOJO.getTestFlag());
				}
				// 只查询出已销售、有效的机械
				mapSelect.put("vehicleStatus", Constants.VEHICLE_STATE3);
				mapSelect.put("isValid", Constants.IS_VALID_YES);
			}
			if (isLeaseHold) {// 融资租赁
				mapSelect.put("leaseFlag", 1);
				List<VehicleUnitPOJO> vehicleUnitList = ownerService
						.getVehilclesInDealer(mapSelect);
				List<TreeNode<VehiclePOJO>> vehicleNodeList = new ArrayList<TreeNode<VehiclePOJO>>();
				for (VehicleUnitPOJO dealerVehiclePOJO2 : vehicleUnitList) {
					TreeNode node = new TreeNode();
					node.setId(Constants.NODE_VEHICLE_PREFIX
							+ dealerVehiclePOJO2.getVehicleId());
					node.setText(dealerVehiclePOJO2.getVehicleDef());
					if (dealerVehiclePOJO2.getIsLogin() != null
							&& dealerVehiclePOJO2.getIsLogin().intValue() == 0) {// 在线
						node.setIconCls(Constants.NODE_ONLINE_ICON);
					} else {// 离线
						node.setIconCls(Constants.NODE_OFFLINE_ICON);
					}
					node.setAttributes(dealerVehiclePOJO2);
					vehicleNodeList.add(node);
				}
				if (vehicleNodeList != null && !vehicleNodeList.isEmpty()) {
					mapAreaList.addAll(vehicleNodeList);
				}
			} else {// 非融资租赁
				// 查询经销商id和机械
				List<DealerVehiclePOJO> dealerVehicleList = ownerService
						.getVehilcles(mapSelect);
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
						if (dealerVehiclePOJO2.getIsLogin() != null
								&& dealerVehiclePOJO2.getIsLogin().intValue() == 0) {// 在线
							node.setIconCls(Constants.NODE_ONLINE_ICON);
						} else {// 离线
							node.setIconCls(Constants.NODE_OFFLINE_ICON);
						}
						node.setAttributes(dealerVehiclePOJO2);
						vehicleNodeList.add(node);
					}
					vehicleMap.put(dealerVehiclePOJO.getDealerId(),
							vehicleNodeList);
				}
				if (isDealer) {// 经销商
					if (vehicleNodeList != null && !vehicleNodeList.isEmpty()) {
						mapAreaList.addAll(vehicleNodeList);
					}
				} else {
					if (!dealerVehicleList.isEmpty()) {
						dealerAreaPOJO = new DealerAreaPOJO();
						dealerAreaPOJO.setDealerVehicleList(dealerVehicleList);
						// 查询片区和经销商
						List<DealerAreaPOJO> dealerAreaList = ownerService
								.getList(dealerAreaPOJO);
						Map mapRreaMap = new HashMap();
						for (DealerAreaPOJO dealerAreaPOJO2 : dealerAreaList) {
							TreeNode node = new TreeNode();
							node.setId(dealerAreaPOJO2.getId());
							node.setText(dealerAreaPOJO2.getName());
							if (StringUtils
									.isNotEmpty(dealerAreaPOJO2.getPid())) {
								node.setParentId(dealerAreaPOJO2.getPid());
							}
							node.setAttributes(dealerAreaPOJO2);
							mapRreaMap.put(dealerAreaPOJO2.getId(), node);

						}
						for (DealerAreaPOJO dealerAreaPOJO3 : dealerAreaList) {
							if (StringUtils
									.isNotEmpty(dealerAreaPOJO3.getPid())) { // 有父节点
								TreeNode pnode = (TreeNode) mapRreaMap
										.get(dealerAreaPOJO3.getPid());
								TreeNode cnode = (TreeNode) mapRreaMap
										.get(dealerAreaPOJO3.getId());
								// 设置机械
								cnode.setChildren((List<TreeNode<VehiclePOJO>>) 
								vehicleMap.get(dealerAreaPOJO3.getId()));
								pnode.addChild(cnode);

							} else {// 无父节点
								mapAreaList.add((TreeNode) 
								mapRreaMap.get(dealerAreaPOJO3.getId()));
							}
						}
					}

				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		renderObject(mapAreaList);
	}

	/**
	 * @Title:query
	 * @Description:查询片区和经销商构成一棵树
	 * @return
	 * @throws
	 */
	public String getDealerAreas4Tree() {
		List<Map> mapList = new ArrayList<Map>();
		try {
			Map childMap = null;
			Map attrMap = null;
			List<Map> districtChildList = new ArrayList<Map>();

			UserPOJO userPOJO = (UserPOJO) getSession()
					.get(Constants.USER_INFO);
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
				departPOJO = userPOJO.getDepartInfo();
				Map mapRoot = new HashMap();

				mapRoot.put("id", "depart_" + departPOJO.getDepartId());
				mapRoot.put("text", departPOJO.getDepartName());
				mapRoot.put("state", Constants.NODE_STATE_OPEN);
				mapList.add(mapRoot);// 添加父节点

			} else {// 非经销商
				if (StringUtils.isNotEmpty(id)) {
					dealerAreaPOJO.setPid(id);
					dealerAreaPOJO.setDtype(null);
					List<DealerAreaPOJO> dealerAreaList = ownerService
							.getList(dealerAreaPOJO);
					for (DealerAreaPOJO dealerAreaPOJO : dealerAreaList) {
						childMap = new HashMap();
						childMap.put("id", dealerAreaPOJO.getId());
						childMap.put("text", dealerAreaPOJO.getName());
						childMap.put("attributes", dealerAreaPOJO);
						childMap.put("state", Constants.NODE_STATE_OPEN);
						mapList.add(childMap);
					}

				} else {

					dealerAreaPOJO.setDtype(1);// 片区
					List<DealerAreaPOJO> dealerAreaList = ownerService
							.getList(dealerAreaPOJO);
					for (DealerAreaPOJO dealerAreaPOJO : dealerAreaList) {
						childMap = new HashMap();
						childMap.put("id", dealerAreaPOJO.getId());
						childMap.put("text", dealerAreaPOJO.getName());
						childMap.put("state", Constants.NODE_STATE_CLOSE);
						childMap.put("attributes", dealerAreaPOJO);
						mapList.add(childMap);// 添加片区
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(mapList);
		return NONE;
	}

	/**
	 * @Title:addMapTag
	 * @Description:添加地图标注
	 * @throws
	 */
	public void addMapTag() {
		boolean result = true;
		String msg = OP_SUCCESS;
		try {
			int mapTagCountMax = 10;// 地图标注的数量
			if (getSession().get(Constants.SYSTEM_PARAMS) != null) {
				List<SystemParamPOJO> systemParamList = (List<SystemParamPOJO>) getSession()
						.get(Constants.SYSTEM_PARAMS);
				for (SystemParamPOJO systemParamPOJO : systemParamList) {
					if (Constants.MAP_TAG_COUNT.equals(systemParamPOJO
							.getCode())) {
						mapTagCountMax = StringUtils.isNotEmpty(systemParamPOJO
								.getValue()) ? Integer.parseInt(systemParamPOJO
								.getValue()) : 10;
					}
				}
			}

			Long userId = (Long) getSession().get(Constants.USER_ID);
			mapTagPOJO.setUserId(userId);

			// 判断是否超过系统设置的最大标注数量
			List<MapTagPOJO> mapTagList = mapTagService.getList(mapTagPOJO);
			if (mapTagList != null && !mapTagList.isEmpty()) {
				if (mapTagList.size() >= mapTagCountMax) {
					msg = "抱歉,地图标注数最多设置" + mapTagCountMax + "个!";
					result = false;
				} else {
					mapTagService.add(mapTagPOJO);
				}
			} else {
				mapTagService.add(mapTagPOJO);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = false;
			msg = OP_FAIL;
		}
		renderMsgJson(result, msg);
	}

	/**
	 * @Title:addMapTag
	 * @Description:删除地图标注
	 * @throws
	 */
	public void deleteMapTag() {
		boolean result = true;
		String msg = "";
		try {
			mapTagService.removeById(mapTagPOJO.getTagId());
			msg = OP_SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = false;
			msg = OP_FAIL;
		}
		renderMsgJson(result, msg);
	}

	/**
	 * @Title:addMapTag
	 * @Description:查询地图标注
	 * @throws
	 */
	public void queryMapTag() {
		if (getSession().get(Constants.USER_ID) != null) {
			List<MapTagPOJO> datalist = new ArrayList<MapTagPOJO>();
			try {
				Long userId = (Long) getSession().get(Constants.USER_ID);
				mapTagPOJO.setUserId(userId);
				datalist = mapTagService.getList(mapTagPOJO);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			renderObject(datalist);
		}
	}

	/**
	 * @Title:addMonitorList
	 * @Description:加入到监控列表
	 * @throws
	 */
	public void addMonitorList() {
		if (vehicleUnitPOJOs != null) {
			List<VehicleUnitPOJO> vehicleUnitPOJOList = new ArrayList<VehicleUnitPOJO>();
			JsonExtUtils jeu = JsonExtUtils.create(Inclusion.ALWAYS);
			Map<String, Object> map = null;
			VehicleUnitPOJO vehicleUnitPOJO2 = null;
			for (int i = 0; i < vehicleUnitPOJOs.size(); i++) {
				map = (Map<String, Object>) jeu.fromJson(vehicleUnitPOJOs
						.get(i), Map.class);
				vehicleUnitPOJO2 = new VehicleUnitPOJO();
				try {
					BeanUtils.populate(vehicleUnitPOJO2, map);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				vehicleUnitPOJOList.add(vehicleUnitPOJO2);
			}
			getSession().put(Constants.WATCH_VEHICLE, vehicleUnitPOJOList);
		}
	}

	/**
	 * 删除监控车辆（车台呼号）
	 * 
	 * @return
	 */
	public void delWatchUnitIds() {
		List<VehicleUnitPOJO> removeList = new ArrayList<VehicleUnitPOJO>();
		if (vehicleUnitPOJOs != null) {
			Map<String, Object> map = null;
			VehicleUnitPOJO vehicleUnitPOJO2 = null;
			JsonExtUtils jeu = JsonExtUtils.create(Inclusion.ALWAYS);
			List<VehicleUnitPOJO> gpsMonitorList = (List<VehicleUnitPOJO>) getSession()
					.get(Constants.WATCH_VEHICLE);
			for (int i = 0; i < vehicleUnitPOJOs.size(); i++) {
				map = (Map<String, Object>) jeu.fromJson(vehicleUnitPOJOs
						.get(i), Map.class);
				vehicleUnitPOJO2 = new VehicleUnitPOJO();
				try {
					BeanUtils.populate(vehicleUnitPOJO2, map);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				for (VehicleUnitPOJO vehicleUnitPOJO : gpsMonitorList) {
					if (vehicleUnitPOJO != null && vehicleUnitPOJO2 != null) {
						if (vehicleUnitPOJO.getUnitId() != null
								&& vehicleUnitPOJO.getUnitId().equals(
										vehicleUnitPOJO2.getUnitId())) {
							removeList.add(vehicleUnitPOJO);
						}
					}
				}
			}
			gpsMonitorList.removeAll(removeList);
			getSession().put(Constants.WATCH_VEHICLE, gpsMonitorList);
		}
	}

	/**
	 * 清空监控车辆（车台呼号）
	 * 
	 * @return
	 */
	public String cleanWatchVehicle() {
		getSession().remove(Constants.WATCH_VEHICLE);
		return NONE;
	}

	/**
	 * 获取监控车辆（unitId)
	 * 
	 * @return
	 */
	public String getWatchVehicle() {
		HashMap<String, Object> result = new HashMap<String, Object>();

		try {
			int total = 0;
			List<VehicleUnitPOJO> vehicleList = new ArrayList<VehicleUnitPOJO>();
			if (getSession().get(Constants.WATCH_VEHICLE) != null) {
				vehicleList = (List<VehicleUnitPOJO>) getSession().get(
						Constants.WATCH_VEHICLE);
				total = vehicleList.size();
			}

			result.put("total", total);
			result.put("rows", vehicleList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		renderObject(result);
		return NONE;
	}

	/**
	 * 获取最后位置（unitId)
	 * 
	 * @return
	 */
	public String getLastGPS() {
		try {
			// 先从memcached中取，取不到再从最后位置表中取
			Position position = memCache.getPosition(vehiclePOJO.getUnitId());
			if (position != null) {
				if(position.getLat()!=null && position.getLon()!=null&&!position.getLat().equals("0")&&!position.getLon().equals("0")){
					String ret = HttpURLConnectionUtil.getAddress(HttpURLConnectionUtil.getBaiduLonlat(""+position.getLon()+","+position.getLat()));
					if(ret!=null && !ret.equals("")){
						position.setReferencePosition(ret);
					}
				}
				renderObject(position);
			} else {
				renderObject(vehicleService.selectLastPosition(vehiclePOJO));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return NONE;
	}

	/**
	 * 获取最后工况（unitId)
	 * 
	 * @return
	 */
	public void getLastWorkInfo() {
		TLastConditionsPOJO tLastConditionsPOJO = new TLastConditionsPOJO();
		try {
			// 最后工况信息-
			tLastConditionsPOJO = vehicleService.selectLastCondition(vehiclePOJO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(tLastConditionsPOJO);
	}

	/**
	 * @Title:getMemcacheDataTest
	 * @Description:得到Memcached中的gps信息，工况信息，指令回应信息
	 * @throws
	 * @author jcb
	 */
	public void getMemcacheDataTest() {

		String unitId = "";
		Integer messageNumber = 0;
		String commandSn = "";
		HashMap resultMap = new HashMap();
		TestCommandPOJO re = new TestCommandPOJO();
		try {
			// 指令发送的commandSN信息,用于取得指令回应
			Map<Integer, String> commandSNList = null;
			if (getSession().get(Constants.COMMAND_SN_TEST) != null) {
				commandSNList = (Map<Integer, String>) getSession().get(
						Constants.COMMAND_SN_TEST);
			}

			// 指令回应
			List<GatewayBack> backList = new ArrayList<GatewayBack>();
			// GPS信息
			List<Position> positionList = new ArrayList<Position>();
			// 工况信息
			List<ConditionsPOJO> conditionList = new ArrayList<ConditionsPOJO>();
			GatewayBack back = null;
			HashMap map = new HashMap();

			List<VehicleUnitPOJO> dealerAreaList = null;
			DicCommandType dicCommandType = new DicCommandType();
			if (commandSNList != null) {
				Set<Integer> keySet = commandSNList.keySet();
				for (Integer sn : keySet) {
					back = memCache.getGatewayBack(sn);
					if (back == null) {
						//commandSNList.remove(sn);
						//getSession().put(Constants.COMMAND_SN_TEST, commandSNList);
						continue;
					}
					// 流水号
					commandSn = back.getCommandSn();
					unitId = back.getUnitId();
					messageNumber = back.getMessageNumber();
					if (back != null) {
						map.put("unitId", back.getUnitId());
						dealerAreaList = ownerService
								.getVehilclesInDealer(map);
						if (dealerAreaList != null && !dealerAreaList.isEmpty()) {
							// 整机编号
							back.setVehicleDef(dealerAreaList.get(0)
									.getVehicleDef());
						}
						dicCommandType.setCommandTypeName(back.getCommandSn());
						dicCommandType = sendCommandService
								.getCommandTypeTest(dicCommandType);
						if (dicCommandType != null) {// 指令类型
							back.setCommandType(dicCommandType
									.getCommandTypeName());
							//指令类型
							re.setCommandTypeName(dicCommandType.getCommandTypeName());
						}
						backList.add(back);
						//网关返回是00，说明成功
						if("00".equals(back.getIsSuccess())){
						re.setCommandResult(0);
						}
						re.setUnitId(back.getUnitId());
						/*
						 * if("00".equals(back.getIsSuccess())){ //测试指令记录表更新、
						 * commandMapper.updateTestCommand(back); //指令发送记录表更新
						 * //commandMapper.updateTab(back); }
						 */

						/**
						 * 如果是那种下发后没有回应的指令，网关回应成功后就可以删除掉了（如A锁下发，A锁解除）
						 * if("00".equals(back.getIsSuccess()) &&
						 * dicCommandType.getCommandTypeId()==52 ){
						 * memCache.remove(back.getCommandSn()+"gback");
						 * commandSNList.remove(Integer.valueOf(commandSn)); }
						 */
					}
					// 取得心跳设置的终端回应 add 2013-6-7
					UnitBack unitBack = null;
					if (!"".equals(commandSn)
							&& dicCommandType.getCommandTypeId() == 52) {
						unitBack = memCache.getUnitPant(commandSn);
						if (unitBack == null) {
							// 取出后就移除 add 2013-6-8
							memCache.remove(back.getCommandSn() + "gback");
							memCache.remove(back.getCommandSn() + "uback");
							commandSNList.remove(Integer.valueOf(commandSn));
							continue;
						}
						re.setUnitBack("0");
						/*
						 * if("00".equals(unitBack.getPantStatus())){
						 * back.setSucFlag("1"); commandMapper.updateTab1(back); }
						 */
						// 取出后就移除
						memCache.remove(back.getCommandSn() + "gback");
						memCache.remove(back.getCommandSn() + "uback");
						commandSNList.remove(Integer.valueOf(commandSn));
					}

					Position position = null;
					if (!"".equals(commandSn)
							&& dicCommandType.getCommandTypeId() == 32) {
						position = memCache.getPositionOrder(commandSn);
						if (position == null) {
							// 取出后就移除 add 2013-6-8
							memCache.remove(back.getCommandSn() + "gback");
							memCache.remove(back.getCommandSn() + "uback");
							commandSNList.remove(Integer.valueOf(commandSn));
							continue;
						}
						re.setUnitBack("0");
						if(position.getLat()!=null && position.getLon()!=null&&!position.getLat().equals("0")&&!position.getLon().equals("0")){
							String ret = HttpURLConnectionUtil.getAddress(HttpURLConnectionUtil.getBaiduLonlat(""+position.getLon()+","+position.getLat()));
							if(ret!=null && !ret.equals("")){
								position.setReferencePosition(ret);
							}
						}
						positionList.add(position);
						// 终端回应有数据要插表
						// back.setSucFlag("1");
						// commandMapper.updateTab1(back);
						// 取出后就移除
						memCache.remove(back.getCommandSn() + "gback");
						memCache.remove(back.getCommandSn() + "uback");
						commandSNList.remove(Integer.valueOf(commandSn));
						break;
					}

					ConditionsPOJO conditions = null;
					if (!"".equals(commandSn)
							&& dicCommandType.getCommandTypeId() == 81) {
						conditions = memCache.getCondition(commandSn);
						if (conditions == null) {
							// 取出后就移除 add 2013-6-8
							memCache.remove(back.getCommandSn() + "gback");
							memCache.remove(back.getCommandSn() + "uback");
							commandSNList.remove(Integer.valueOf(commandSn));
							continue;
						}
						re.setUnitBack("0");
						conditionList.add(conditions);
						// 终端回应有数据要插表
						// back.setSucFlag("1");
						// commandMapper.updateTab1(back);
						// 取出后就移除
						memCache.remove(back.getCommandSn() + "gback");
						memCache.remove(back.getCommandSn() + "uback");
						commandSNList.remove(Integer.valueOf(commandSn));
						break;
					}
					//Map<Integer, String> commandSNList1 = (Map<Integer, String>) getSession().get(Constants.COMMAND_SN_TEST);
					//commandSNList.remove(sn);
					//getSession().put(Constants.COMMAND_SN_TEST, commandSNList);
				}
				
				SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date time = new Date();
	            String nowTime = formater.format(time);
	            Date date = formater.parse(nowTime);
	            re.setStamp(date);
				
				resultMap.put("response", backList);
				resultMap.put("gpsInfo", positionList);
				resultMap.put("workInfo", conditionList);
				resultMap.put("testlog", re);
			
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(resultMap);
	}

	/**
	 * @throws Exception
	 * @Title:getMemcacheData
	 * @Description:得到Memcached中的gps信息，工况信息，指令回应信息
	 * @throws
	 */
	public void getMemcacheData() {
		try {
			// 一般指令发送的commandSN信息,用于取得指令回应
			Map<Integer, String> commandSNList = null;
			if (getSession().get(Constants.COMMAND_SN) != null) {
				commandSNList = (Map<Integer, String>) getSession().get(
						Constants.COMMAND_SN);
			}
			// 空中升级指令发送的commandSN信息,用于取得指令回应
			Map<Integer, String> commandSNUpgradeList = null;
			if (getSession().get(Constants.COMMAND_SN_UPGRADE) != null) {
				commandSNUpgradeList = (Map<Integer, String>) getSession().get(
						Constants.COMMAND_SN_UPGRADE);
			}

			// 指令回应的GPS信息
			Map<String, GatewayBack> commandSNGpsList = null;
			if (getSession().get(Constants.GPSINFO_SN) != null) {
				commandSNGpsList = (Map<String, GatewayBack>) getSession().get(
						Constants.GPSINFO_SN);
			}
			// 指令回应的工况信息
			Map<String, GatewayBack> commandSNWorkInfoList = null;
			if (getSession().get(Constants.WORKINFO_SN) != null) {
				commandSNWorkInfoList = (Map<String, GatewayBack>) getSession()
						.get(Constants.WORKINFO_SN);
			}

			// 一般指令回应，如参数查询
			Map<String, GatewayBack> commandSNUnitBackList = null;
			if (getSession().get(Constants.UNITBACK_SN) != null) {
				commandSNUnitBackList = (Map<String, GatewayBack>) getSession()
						.get(Constants.UNITBACK_SN);
			}

			// 一般指令回应,如果GPRS无回应，则走GSM重新发送
			List<GatewayBack> backList = new ArrayList<GatewayBack>();
			GatewayBack back = null;
			HashMap map = new HashMap();

			List<VehicleUnitPOJO> dealerAreaList = null;
			DicCommandType dicCommandType = new DicCommandType();

			// 存放需要走GSM的指令信息
			Map<Integer, String> commandSNMsgList = new HashMap<Integer, String>();// 要走短信的
			CommandPOJO command = null;
			if (commandSNList != null) {
				Set<Integer> keySet = commandSNList.keySet();
				// 要删除的key
				List<Integer> removedKeys = new ArrayList<Integer>();
				for (Integer sn : keySet) {
					back = memCache.getGatewayBack(sn);// 走GPRS
					// 从数据库t_send_command表中获得指令是走哪个通道
					command = sendCommandService.getById(Long.valueOf(sn
							.longValue()));

					if (back != null) {
						if (command != null && command.getPathFlag() != null) {
							back.setPathFlag(command.getPathFlag());
						}
						map.put("unitId", back.getUnitId());
						dealerAreaList = ownerService
								.getVehilclesInDealer(map);
						if (dealerAreaList != null && !dealerAreaList.isEmpty()) {
							// 整机编号
							back.setVehicleDef(dealerAreaList.get(0)
									.getVehicleDef());
						}
						dicCommandType.setCommandTypeName(back.getCommandSn());
						dicCommandType.setCommandParam("t_send_command");
						dicCommandType = sendCommandService
								.getCommandType(dicCommandType);
						if (dicCommandType != null) {// 指令类型
							// 如果是定位和工况采集，则需获得指令回应的gps和工况
							if (dicCommandType.getCommandTypeId().intValue() == Constants.COMMAND_TYPE_GPS
									.intValue()
									|| dicCommandType.getCommandTypeId()
											.intValue() == Constants.COMMAND_TYPE_CONDITIONS
											.intValue()) {
								if (dicCommandType.getCommandTypeId()
										.intValue() == Constants.COMMAND_TYPE_GPS
										.intValue()) {// 定位
									if (commandSNGpsList == null) {
										commandSNGpsList = new HashMap<String, GatewayBack>();
									}

									commandSNGpsList.put(back.getCommandSn(),
											back);
									getSession().put(Constants.GPSINFO_SN,
											commandSNGpsList);
								} else if (dicCommandType.getCommandTypeId()
										.intValue() == Constants.COMMAND_TYPE_CONDITIONS
										.intValue()) {// 工况数据采集
									if (commandSNWorkInfoList == null) {
										commandSNWorkInfoList = new HashMap<String, GatewayBack>();
									}

									commandSNWorkInfoList.put(back
											.getCommandSn(), back);
									getSession().put(Constants.WORKINFO_SN,
											commandSNWorkInfoList);

								}
							} else {// 其他一般指令终端回应
								back.setCommandType(dicCommandType
										.getCommandTypeName());
								if (commandSNUnitBackList == null) {
									commandSNUnitBackList = new HashMap<String, GatewayBack>();
								}
								commandSNUnitBackList.put(back.getCommandSn(),
										back);
								getSession().put(Constants.UNITBACK_SN,
										commandSNUnitBackList);
							}

							back.setCommandType(dicCommandType
									.getCommandTypeName());
						}

						// 拼接指令回应数据,显示在指令回应信息中
						StringBuffer remarkSb = new StringBuffer();
						remarkSb.append(back.getVehicleDef());
						remarkSb.append(" ").append(
								DateUtil.format(back.getStamp(),
										DateUtil.YMD_DASH_WITH_FULLTIME24));
						remarkSb.append(" ").append(back.getCommandType());
						if ("00".equals(back.getIsSuccess())) {
							remarkSb.append(" 成功");
						} else {
							remarkSb.append(" 失败");
						}
						if (back.getPathFlag().intValue() == 0) {
							remarkSb.append(" GPRS");
						} else {
							remarkSb.append(" GSM");
						}
						back.setResponseType(Constants.RESPONSE_GATEWAY);
						back.setRemark(remarkSb.toString());
						backList.add(back);

						// 网关指令回应取出后就移除
						if (dicCommandType != null) {
							// if(!(dicCommandType.getCommandTypeId().intValue()==Constants.COMMAND_TYPE_TRACK.intValue()||dicCommandType.getCommandTypeId().intValue()==Constants.COMMAND_TYPE_CONDITIONS.intValue())){
							// 从memcached中移除
							memCache.remove(back.getCommandSn() + "gback");

							// 把要移除的key加入到集合中
							removedKeys.add(Integer
									.valueOf(back.getCommandSn()));
							// }
						}
					} else {// 走短信流程gsm
						if (logger.isDebugEnabled()) {
							logger.debug("指令id[" + sn + "]取不到网关回应,走GSM");
						}
						// 工况采集不走GSM
						if (command != null
								&& command.getPathFlag() != null
								&& command.getPathFlag().intValue() != 1
								&& command.getCommandTypeId().intValue() != Constants.COMMAND_TYPE_CONDITIONS
										.intValue()) {// 如果已经走GSM发过了，就不再发送
							commandSNMsgList.put(sn, commandSNList.get(sn));
						}
						String[] rawdatas = commandSNList.get(sn).split("-");
						long step = System.currentTimeMillis()- Long.valueOf(rawdatas[1]);
						if(step>30000){//如果30秒无回应
							if((command.getUnitBack() ==null&&command.getGatewayBack() == null) || (command.getUnitBack().equals("9999")&&command.getGatewayBack().equals("9999"))){
								map.put("unitId", command.getUnitId());
								dealerAreaList = dealerAreaService.getVehilclesInDealer(map);
								if (dealerAreaList != null && !dealerAreaList.isEmpty()) {
									// 整机编号								
								back = new GatewayBack();
								back.setResponseType(Constants.RESPONSE_GATEWAY);
								back.setRemark(dealerAreaList.get(0).getVehicleDef()+"  无回应！");
								backList.add(back);
								}
								memCache.remove(back.getCommandSn() + "gback");
								removedKeys.add(Integer.valueOf(command.getCommandSn().toString()));
							}
						}
					}
				}

				// 从session中移除
				if (removedKeys != null && !removedKeys.isEmpty()) {
					for (Integer removedKey : removedKeys) {
						commandSNList.remove(removedKey);
					}
					getSession().put(Constants.COMMAND_SN, commandSNList);// result主要用于得到指令回应
				}
			}

			// 指令回应的: GPS信息,工况信息
			List<Position> positionList = new ArrayList<Position>();
			List<ConditionsPOJO> conditionList = new ArrayList<ConditionsPOJO>();
			Position position = null;
			ConditionsPOJO conditions = null;
			// 指令回应：GPS位置
			if (commandSNGpsList != null) {
				// 要删除的key
				List<String> removedKeys = new ArrayList<String>();

				for (Map.Entry<String, GatewayBack> obj : commandSNGpsList
						.entrySet()) {
					position = memCache.getPositionOrder(obj.getKey());
					if (position != null) {
						position.setVehicleDef(obj.getValue().getVehicleDef());
						if (position.getNowTime() == null) {
							position.setNowTime(DateUtil.format(new Date(),
									DateUtil.YMD_DASH_WITH_FULLTIME24));
						}
						if(position.getLat()!=null && position.getLon()!=null&&!position.getLat().equals("0")&&!position.getLon().equals("0")){
							String ret = HttpURLConnectionUtil.getAddress(HttpURLConnectionUtil.getBaiduLonlat(""+position.getLon()+","+position.getLat()));
							if(ret!=null && !ret.equals("")){
								position.setReferencePosition(ret);
							}
						}
						positionList.add(position);
						// 从memcached中移除
						memCache.remove(obj.getKey() + "uback");
						// 把要移除的key加入到集合中
						removedKeys.add(obj.getKey());
					} else {
						if (logger.isDebugEnabled()) {
							logger.debug("指令id[" + obj.getKey()
									+ "]未取到终端回应(查车)");
						}
					}
				}

				// 从session中移除
				if (removedKeys != null && !removedKeys.isEmpty()) {
					for (String removedKey : removedKeys) {
						commandSNGpsList.remove(removedKey);
					}
					getSession().put(Constants.GPSINFO_SN, commandSNGpsList);
				}
			}

			// 指令回应：工况采集
			if (commandSNWorkInfoList != null) {
				// 要删除的key
				List<String> removedKeys = new ArrayList<String>();

				for (Map.Entry<String, GatewayBack> obj : commandSNWorkInfoList
						.entrySet()) {
					conditions = memCache.getCondition(obj.getValue().getUnitId());
					if (conditions != null) {
						conditions
								.setVehicleDef(obj.getValue().getVehicleDef());
						conditionList.add(conditions);
						// 从memcached中移除
						memCache.remove(obj.getKey() + "uback");
						// 把要移除的key加入到集合中
						removedKeys.add(obj.getKey());
					} else {
						if (logger.isDebugEnabled()) {
							logger.debug("指令id[" + obj.getKey()
									+ "]未取到终端回应(工况)");
						}
					}
				}
				// 从session中移除
				if (removedKeys != null && !removedKeys.isEmpty()) {
					for (String removedKey : removedKeys) {
						commandSNWorkInfoList.remove(removedKey);
					}
					getSession().put(Constants.WORKINFO_SN,
							commandSNWorkInfoList);
				}
			}
			// 空中升级网关回应
			RemoteUpgradePOJO remoteUpgrade = null;
			if (commandSNUpgradeList != null) {
				Set<Integer> keySetUpgrade = commandSNUpgradeList.keySet();
				// 要删除的key
				List<Integer> removedKeys = new ArrayList<Integer>();
				for (Integer sn : keySetUpgrade) {
					back = memCache.getGatewayBack(sn);// 走GPRS
					// 从数据库t_send_command表中获得指令是走哪个通道
					remoteUpgrade = sendCommandService.getByIdUpgrade(Long
							.valueOf(sn.longValue()));
					if (back != null) {

						if (remoteUpgrade != null
								&& remoteUpgrade.getPathFlag() != null) {
							back.setPathFlag(remoteUpgrade.getPathFlag());
						}
						map.clear();
						map.put("unitId", back.getUnitId());
						dealerAreaList = ownerService
								.getVehilclesInDealer(map);
						if (dealerAreaList != null && !dealerAreaList.isEmpty()) {
							// 整机编号
							back.setVehicleDef(dealerAreaList.get(0)
									.getVehicleDef());
						}
						back.setCommandType(Constants.COMMAND_TYPE16_NAME);
						backList.add(back);

						// 从memcached中移除
						memCache.remove(back.getCommandSn() + "gback");
						// 把要移除的key加入到集合中
						removedKeys.add(Integer.valueOf(back.getCommandSn()));
						// 加入到终端回应监控中
						if (commandSNUnitBackList == null) {
							commandSNUnitBackList = new HashMap<String, GatewayBack>();
						}
						commandSNUnitBackList.put(back.getCommandSn(), back);
						getSession().put(Constants.UNITBACK_SN,
								commandSNUnitBackList);
					} else {// 走短信流程gsm
						if (logger.isDebugEnabled()) {
							logger.debug("指令id[" + back.getCommandSn()
									+ "]未取到终端回应(空中升级),走gsm");
						}
						if (remoteUpgrade != null
								&& remoteUpgrade.getPathFlag() != null
								&& remoteUpgrade.getPathFlag().intValue() != 1) {// 如果已经走GSM发过了，就不在发送
							commandSNMsgList.put(sn, commandSNUpgradeList
									.get(sn));
						}
					}
				}
				// 从session中移除
				if (removedKeys != null && !removedKeys.isEmpty()) {
					for (Integer removedKey : removedKeys) {
						commandSNUpgradeList.remove(removedKey);
					}
					getSession().put(Constants.COMMAND_SN_UPGRADE,
							commandSNUpgradeList);// result主要用于得到指令回应
				}
			}
			// 一般指令终端回应
			// List<UnitBack> unitBackList = new ArrayList<UnitBack>();
			UnitBack unitBack = null;
			if (commandSNUnitBackList != null) {
				// 要删除的key
				List<String> removedKeys = new ArrayList<String>();
				GatewayBack gatewayback = null;
				for (Map.Entry<String, GatewayBack> obj : commandSNUnitBackList
						.entrySet()) {
					unitBack = memCache.getUnitBack(Integer.parseInt(obj
							.getKey()));
					if (unitBack != null) {
						gatewayback = obj.getValue();
						unitBack.setVehicleDef(gatewayback.getVehicleDef());
						unitBack.setStamp(new Date());
						// unitBackList.add(unitBack);

						// 拼接指令回应数据,显示在指令回应信息中
						StringBuffer remarkSb = new StringBuffer();
						remarkSb.append(gatewayback.getVehicleDef());
						remarkSb.append(" ").append(
								gatewayback.getCommandType());
						// 如果是空中升级，终端回应单独解析
						if (Constants.COMMAND_TYPE16_NAME.equals(gatewayback
								.getCommandType())) {
							// 00-文件格式正确；01-不存在任何文件；02-供应商标识不对；03-终端型号不对；04-程序版本不对；05-升级类型不对；06-GPS终端升级成功；07-控制器升级成功；08-显示器升级成功；09-未知类型错误
							if ("00".equals(unitBack.getUnitback())) {
								remarkSb.append(" 文件格式正确");
							} else if ("01".equals(unitBack.getUnitback())) {
								remarkSb.append(" 不存在任何文件");
							} else if ("02".equals(unitBack.getUnitback())) {
								remarkSb.append(" 供应商标识不对");
							} else if ("03".equals(unitBack.getUnitback())) {
								remarkSb.append(" 终端型号不对");
							} else if ("04".equals(unitBack.getUnitback())) {
								remarkSb.append(" 程序版本不对");
							} else if ("05".equals(unitBack.getUnitback())) {
								remarkSb.append(" 升级类型不对");
							} else if ("06".equals(unitBack.getUnitback())) {
								remarkSb.append(" GPS终端升级成功");
							} else if ("07".equals(unitBack.getUnitback())) {
								remarkSb.append(" 控制器升级成功");
							} else if ("08".equals(unitBack.getUnitback())) {
								remarkSb.append(" 显示器升级成功");
							} else if ("09".equals(unitBack.getUnitback())) {
								remarkSb.append(" 未知类型错误");
							}
							remarkSb.append(" ").append(
									DateUtil.format(unitBack.getStamp(),
											DateUtil.YMD_DASH_WITH_FULLTIME24));
						} else {
							// 00-接收成功；01-校验错；02-控制器未上电；03-总线故障；04-未知传输失败类型
							if ("00".equals(unitBack.getUnitback())) {
								remarkSb.append(" 接收成功");
							} else if ("01".equals(unitBack.getUnitback())) {
								remarkSb.append(" 校验错");
							} else if ("02".equals(unitBack.getUnitback())) {
								remarkSb.append(" 控制器未上电");
							} else if ("03".equals(unitBack.getUnitback())) {
								remarkSb.append(" 总线故障");
							} else if ("04".equals(unitBack.getUnitback())) {
								remarkSb.append(" 未知传输失败类型");
							}
							remarkSb.append(" ").append(
									DateUtil.format(unitBack.getStamp(),
											DateUtil.YMD_DASH_WITH_FULLTIME24));
							if (StringUtils.isNotEmpty(unitBack.getIp())) {
								remarkSb.append(" IP:")
										.append(unitBack.getIp());
							}
							if (StringUtils.isNotEmpty(unitBack.getPort())) {
								remarkSb.append(" 端口:").append(
										unitBack.getPort());
							}
							if (StringUtils.isNotEmpty(unitBack.getApn())) {
								remarkSb.append(" APN:").append(
										unitBack.getApn());
							}
							if (StringUtils.isNotEmpty(unitBack.getIp())) {
								remarkSb.append(" 短信号码:").append(
										unitBack.getNumber());
							}
							if (StringUtils.isNotEmpty(unitBack
									.getVersionCanId())) {
								remarkSb.append(" 控制器版本及ID信息:").append(
										unitBack.getVersionCanId());
							}
						}
						back = new GatewayBack();
						back.setResponseType(Constants.RESPONSE_UNIT);
						back.setRemark(remarkSb.toString());
						backList.add(back);
						// 从memcached中移除
						memCache.remove(obj.getKey() + "uback");
						// 把要移除的key加入到集合中
						removedKeys.add(obj.getKey());

					} else {
						if (logger.isDebugEnabled()) {
							logger.debug("指令id[" + obj.getKey()
									+ "]未取到终端回应(一般指令),可能终端没回应");
						}
					}
				}

				// 从session中移除
				if (removedKeys != null && !removedKeys.isEmpty()) {
					for (String removedKey : removedKeys) {
						commandSNUnitBackList.remove(removedKey);
					}
					getSession().put(Constants.UNITBACK_SN,
							commandSNUnitBackList);
				}
			}
			/*
			 * //暂时注释 待测试 调用发短信的方法
			 * if(commandSNMsgList!=null&&commandSNMsgList.keySet().size()>0){
			 * try { sendCommandMessage.sendCommand(commandSNMsgList);
			 * commandSNMsgList.clear(); } catch (JMSException e) {
			 * e.printStackTrace(); } }
			 */

			HashMap resultMap = new HashMap();
			resultMap.put("gpsInfo", positionList);
			resultMap.put("workInfo", conditionList);
			resultMap.put("response", backList);
			renderObject(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			renderMsgJson(false, "获取指令回应信息失败");
		}
	}

	/**
	 * @Title:compositeQuery
	 * @Description:综合查询
	 * @throws
	 */
	public void compositeQuery() {
		pageSelect.setPage(page);
		pageSelect.setRows(rows);
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		
		// 只查询出有效的机械
		condition.setIsValid(Constants.IS_VALID_YES);
		// condition.setStatus(Constants.VEHICLE_STATE3);
		// 锁车需截取字符串处理
		if (com.chinaGPS.gtmp.util.StringUtils.isNotBlank(condition.getLock())) {
			String lock = condition.getLock();
			if (lock != null && lock.length() > 6) {
				condition.setLock(lock.substring(0, 6));
				condition.setAlock(Integer.parseInt(lock.substring(7)));
			}
		}
		try {
			result = ownerService.queryComposite(condition, pageSelect);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(result);
	}

	/**
	 * @Title:queryHistoryCondition
	 * @Description:历史工况查询
	 * @throws
	 */
	public void queryHistoryCondition() {
		HashMap<String, Object> result = new HashMap<String, Object>();

		try {
			pageSelect.setPage(page);
			pageSelect.setRows(rows);
			Map<String, Serializable> map = new HashMap<String, Serializable>();
			map.put("unitId", vehiclePOJO.getUnitId());
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			result = ownerService.queryHistoryCondition(map, pageSelect);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(result);
	}

	/**
	 * @Title:exportToHisConditions
	 * @Description:历史工况导出
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws
	 */
	public String exportToHisConditions() {
		List<Object[]> values = new ArrayList<Object[]>();

		try {
			pageSelect.setPage(1);
			pageSelect.setRows(Integer.MAX_VALUE);
			Map<String, Serializable> map = new HashMap<String, Serializable>();
			map.put("unitId", vehiclePOJO.getUnitId());

			// 开始时间
			map.put("startTime", DateUtil.parse(java.net.URLDecoder
					.decode(startTimeStr), DateUtil.YMD_DASH_WITH_FULLTIME));
			map.put("endTime", DateUtil.parse(java.net.URLDecoder
					.decode(endTimeStr), DateUtil.YMD_DASH_WITH_FULLTIME));
			HashMap resultMap = dealerAreaService.queryHistoryCondition(map,
					pageSelect);// 开始时间
			List<ConditionsPOJO> list = (List<ConditionsPOJO>) resultMap
					.get("rows");
			String isWork = null;
			for (ConditionsPOJO conditionsPOJO : list) {				
				values.add(new Object[] {
						conditionsPOJO.getVehicleDef(),                 //整机编号
						ConditionDispose.formatTemp(conditionsPOJO
							.getEngineCoolantTemperature()),            //发动机冷却液温度
						ConditionDispose.formatLevel(conditionsPOJO
							.getEngineCoolantLevel()),					//发动机冷却液液位
						ConditionDispose.formatOilPressure(conditionsPOJO
							.getEngineOilPressure()),					//发动机机油压力
						ConditionDispose.formatLevel(conditionsPOJO
							.getEngineFuelLevel()),						//发动机燃油油位
						ConditionDispose.formatSpeed(conditionsPOJO
							.getEngineSpeed()),							//发动机转速
						ConditionDispose.formatHydTemperature(conditionsPOJO
							.getHydraulicOilTemperature()),				//液压油温度
						ConditionDispose.formatVoltagePressure(conditionsPOJO
							.getSystemVoltage()),						//系统电压
						ConditionDispose.formatPumpPressure(conditionsPOJO
							.getBeforePumpMainPressure()),				//挖掘机前泵主压压力
						ConditionDispose.formatPumpPressure(conditionsPOJO
							.getAfterPumpMainPressure()),				//挖掘机后泵主压压力	
						ConditionDispose.formatPilotPressure(conditionsPOJO
							.getPilotPressure1()),						//挖掘机先导压力1
						ConditionDispose.formatPilotPressure(conditionsPOJO
							.getPilotPressure2()),						//挖掘机先导压力2
						ConditionDispose.formatPilotPressure(conditionsPOJO
							.getBeforePumpPressure()),					//挖掘机前泵负压压力
						ConditionDispose.formatPilotPressure(conditionsPOJO
							.getAfterPumpPressure()),					//挖掘机后泵负压压力
						ConditionDispose.formatCurrent(conditionsPOJO
							.getProportionalCurrent1()),				//挖掘机比例阀电流1
						ConditionDispose.formatCurrent(conditionsPOJO
							.getProportionalCurrent2()),				//挖掘机比例阀电流2
						ConditionDispose.formatGear(conditionsPOJO
							.getGear()),							    //挖机档位	
						conditionsPOJO.getTotalWorkingHours(),          //累计工作小时
						ConditionDispose.formatOilTemper(conditionsPOJO
							.getHighEngineCoolantTemperVal()),			//发动机冷却液温度高报警值
						ConditionDispose.formatOilPressure(conditionsPOJO
							.getLowEngineOilPressureAlarmValue()),		//发动机机油压力低报警值
						ConditionDispose.formatVoltagePressure(conditionsPOJO
							.getHighVoltageAlarmValue()),				//系统电压高报警设置值
						ConditionDispose.formatVoltagePressure(conditionsPOJO
							.getLowVoltageAlarmValue()),				//系统电压低报警设置值
						ConditionDispose.formatOilTemper(conditionsPOJO
							.getHighHydraulicOilTemperAlarmVal()),		//液压油温高报警值
						conditionsPOJO.getToothNumberValue(),           //飞轮齿数值
						conditionsPOJO.getMonitorSoftwareCode(),	    //监控器（显示器）供应商内部软件代号
						conditionsPOJO.getMonitorYcSoftwareCode(),	    //监控器（显示器）玉柴内部软件版本号
						conditionsPOJO.getControllerSoftwareCode(),		//控制器供应商内部软件代号
						conditionsPOJO.getControllerYcSoftwareCode(),   //制器玉柴内部软件代号
						ConditionDispose.formatOilTemper(conditionsPOJO
							.getEngineOilTemperature()),				//发动机机油温度		
						ConditionDispose.formatFaultCode(conditionsPOJO
							.getFaultCode()),							//故障码
						ConditionDispose.isWorking(conditionsPOJO
							.getEngineSpeed(),conditionsPOJO
							.getEngineOilPressure(),conditionsPOJO
							.getSystemVoltage()),                       //是否工作
						DateUtil.format(conditionsPOJO
							.getStamp(),DateUtil.YMD_DASH_WITH_FULLTIME24) });		//上报工况时间
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		String[] headers = new String[] { "整机编号","发动机冷却液温度", "发动机冷却液液位", "发动机机油压力",
				"发动机燃油油位", "发动机转速", "液压油温度", "系统电压", "挖掘机前泵主压压力", "挖掘机后泵主压压力",
				"挖掘机先导压力1", "挖掘机先导压力2", "挖掘机前泵负压压力", "挖掘机后泵负压压力", "挖掘机比例阀电流1",
				"挖掘机比例阀电流2", "挖机档位", "累计工作小时", "发动机冷却液温度高报警设置值",
				"发动机机油压力低报警设置值", "系统电压高报警设置值", "系统电压低报警设置值", "液压油温高报警值",
				"飞轮齿数设置值", "监控器（显示器）供应商内部软件代号", "监控器（显示器）玉柴内部软件版本号",
				"控制器供应商内部软件代号", "控制器玉柴内部软件代号", "故障代码","发动机机油温度", "是否工作", "上报工况时间" };
		super.renderExcel("历史工况导出" + ".xls", headers, values);
		return null;
	}
/*	public String exportToHisConditions() {
		List<Object[]> values = new ArrayList<Object[]>();

		try {
			pageSelect.setPage(1);
			pageSelect.setRows(Integer.MAX_VALUE);
			Map<String, Serializable> map = new HashMap<String, Serializable>();
			map.put("unitId", vehiclePOJO.getUnitId());

			// 开始时间
			map.put("startTime", DateUtil.parse(java.net.URLDecoder
					.decode(startTimeStr), DateUtil.YMD_DASH_WITH_FULLTIME));
			map.put("endTime", DateUtil.parse(java.net.URLDecoder
					.decode(endTimeStr), DateUtil.YMD_DASH_WITH_FULLTIME));
			HashMap resultMap = dealerAreaService.queryHistoryCondition(map,
					pageSelect);// 开始时间
			List<ConditionsPOJO> list = (List<ConditionsPOJO>) resultMap
					.get("rows");
			String isWork = null;
			for (ConditionsPOJO conditionsPOJO : list) {				
				values.add(new Object[] {
								conditionsPOJO.getEngineCoolantTemperature() != null ? conditionsPOJO
										.getEngineCoolantTemperature()
										+ ConditionUnit.TEMPERATURE.getValue()
										: "",
								conditionsPOJO.getEngineCoolantLevel() != null ? ConditionDispose.formatFF(conditionsPOJO
										.getEngineCoolantLevel())
										+ ConditionUnit.PERCENT.getValue()
										: "",
								conditionsPOJO.getEngineOilPressure() != null ? conditionsPOJO
										.getEngineOilPressure()
										+ ConditionUnit.PRESSURE.getValue()
										: "",
								conditionsPOJO.getEngineFuelLevel() != null ? conditionsPOJO
										.getEngineFuelLevel()
										+ ConditionUnit.PERCENT.getValue()
										: "",
								conditionsPOJO.getEngineSpeed() != null ? conditionsPOJO
										.getEngineSpeed()
										+ ConditionUnit.ROTATE_SPEED.getValue()
										: "",
								conditionsPOJO.getHydraulicOilTemperature() != null ? conditionsPOJO
										.getHydraulicOilTemperature()
										+ ConditionUnit.TEMPERATURE.getValue()
										: "",
								conditionsPOJO.getSystemVoltage() != null ? conditionsPOJO
										.getSystemVoltage()
										+ ConditionUnit.VOLTAGE_PRESSURE
												.getValue()
										: "",
								conditionsPOJO.getBeforePumpMainPressure() != null ? ConditionDispose.formatFF(conditionsPOJO
										.getBeforePumpMainPressure())
										+ ConditionUnit.PRESSURE2.getValue()
										: "",
								conditionsPOJO.getAfterPumpMainPressure() != null ? ConditionDispose.formatFF(conditionsPOJO
										.getAfterPumpMainPressure())
										+ ConditionUnit.PRESSURE2.getValue()
										: "",
								conditionsPOJO.getPilotPressure1() != null ? ConditionDispose.formatFF(conditionsPOJO
										.getPilotPressure1())
										+ ConditionUnit.PRESSURE2.getValue()
										: "",
								conditionsPOJO.getPilotPressure2() != null ? ConditionDispose.formatFF(conditionsPOJO
										.getPilotPressure2())
										+ ConditionUnit.PRESSURE2.getValue()
										: "",
								conditionsPOJO.getBeforePumpPressure() != null ? ConditionDispose.formatFF(conditionsPOJO
										.getBeforePumpPressure())
										+ ConditionUnit.PRESSURE2.getValue()
										: "",
								conditionsPOJO.getAfterPumpPressure() != null ? ConditionDispose.formatFF(conditionsPOJO
										.getAfterPumpPressure())
										+ ConditionUnit.PRESSURE2.getValue()
										: "",
								conditionsPOJO.getProportionalCurrent1() != null ? ConditionDispose.formatFFFF(conditionsPOJO
										.getProportionalCurrent1())
										+ ConditionUnit.CURRENT.getValue()
										: "",
								conditionsPOJO.getProportionalCurrent2() != null ? ConditionDispose.formatFFFF(conditionsPOJO
										.getProportionalCurrent2())
										+ ConditionUnit.CURRENT.getValue()
										: "",
								ConditionDispose.format0(conditionsPOJO.getGear()),
								conditionsPOJO.getTotalWorkingHours(),
								conditionsPOJO.getHighEngineCoolantTemperVal() != null ? conditionsPOJO
										.getHighEngineCoolantTemperVal()
										+ ConditionUnit.TEMPERATURE.getValue()
										: "",
								conditionsPOJO
										.getLowEngineOilPressureAlarmValue() != null ? ConditionDispose.format0( conditionsPOJO
										.getLowEngineOilPressureAlarmValue())
										+ ConditionUnit.PRESSURE.getValue()
										: "",
								conditionsPOJO.getHighVoltageAlarmValue() != null ? conditionsPOJO
										.getHighVoltageAlarmValue()
										+ ConditionUnit.VOLTAGE_PRESSURE
												.getValue()
										: "",
								conditionsPOJO.getLowVoltageAlarmValue() != null ? conditionsPOJO
										.getLowVoltageAlarmValue()
										+ ConditionUnit.VOLTAGE_PRESSURE
												.getValue()
										: "",
								conditionsPOJO
										.getHighHydraulicOilTemperAlarmVal() != null ? ConditionDispose.format0(conditionsPOJO
										.getHighHydraulicOilTemperAlarmVal())
										+ ConditionUnit.TEMPERATURE.getValue()
										: "",
								conditionsPOJO.getToothNumberValue(),
								conditionsPOJO.getMonitorSoftwareCode(),
								conditionsPOJO.getMonitorYcSoftwareCode(),
								conditionsPOJO.getControllerSoftwareCode(),
								conditionsPOJO.getControllerYcSoftwareCode(),
								conditionsPOJO.getProductCode(),
								ConditionDispose.formatFFFF(conditionsPOJO.getFaultCode()),
								ConditionDispose.isWorking(conditionsPOJO.getEngineSpeed(),conditionsPOJO.getEngineOilPressure(),conditionsPOJO.getSystemVoltage()),
								DateUtil.format(conditionsPOJO.getStamp(),
										DateUtil.YMD_DASH_WITH_FULLTIME24) });

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		String[] headers = new String[] { "发动机冷却液温度", "发动机冷却液液位", "发动机机油压力",
				"发动机燃油油位", "发动机转速", "液压油温度", "系统电压", "挖掘机前泵主压压力", "挖掘机后泵主压压力",
				"挖掘机先导压力1", "挖掘机先导压力2", "挖掘机前泵负压压力", "挖掘机后泵负压压力", "挖掘机比例阀电流1",
				"挖掘机比例阀电流2", "挖机档位", "累计工作小时", "发动机冷却液温度高报警设置值",
				"发动机机油压力低报警设置值", "系统电压高报警设置值", "系统电压低报警设置值", "液压油温高报警值",
				"飞轮齿数设置值", "监控器（显示器）供应商内部软件代号", "监控器（显示器）玉柴内部软件版本号",
				"控制器供应商内部软件代号", "控制器玉柴内部软件代号", "产品编号", "故障代码", "是否工作", "最后工况时间" };
		super.renderExcel("历史工况导出" + ".xls", headers, values);
		return null;
	}*/
 
	/**
	 * @Title:getDealers
	 * @Description:得到所有经销商
	 * @throws
	 */
	public void getDealers() {
		List<DealerAreaPOJO> result = new ArrayList<DealerAreaPOJO>();

		try {
			DealerAreaPOJO dealerAreaPOJO2 = new DealerAreaPOJO();
			dealerAreaPOJO2.setName("全部");
			dealerAreaPOJO2.setId("");
			result.add(dealerAreaPOJO2);

			dealerAreaPOJO.setDtype(2);
			dealerAreaPOJO.setIsValid(0);
			result.addAll(ownerService.getList(dealerAreaPOJO));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(result);
	}

	/**
	 * @Title:getVehicleSaleByPage
	 * @Description:机械销售信息查询
	 * @throws
	 */
	public void getVehicleSaleByPage() {
		HashMap<String, Object> result = new HashMap<String, Object>();

		try {
			pageSelect.setPage(page);
			pageSelect.setRows(rows);
			Map map = new HashMap<String, Serializable>();
			map.put("vehicleSale", vehicleSalePOJO);
			result = ownerService.getVehicleSaleByPage(map, pageSelect);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(result);
	}

	/**
	 * @Title:getVehicle4sale
	 * @Description:得到已销售机械
	 * @throws
	 */
	public void getVehicle4sale() {
		List<Map> result = new ArrayList<Map>();
		try {
			Map map = new HashMap<String, Serializable>();
			map.put("vehicleStatus", Constants.VEHICLE_STATE2);
			result = ownerService.getVehicle4sale(map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(result);
	}

	/**
	 * @Title:getOwner4sale
	 * @Description:机主信息查询
	 * @throws
	 */
	public void getOwner4sale() {
		List<Map> result = new ArrayList<Map>();
		try {
			Map map = new HashMap<String, Serializable>();
			result = ownerService.getOwner4sale(map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(result);
	}

	/**
	 * @Title:saveDealerVechicle
	 * @Description:新增修改机械经销商机主绑定关系信息
	 * @throws
	 */
	public void saveDealerVechicle() {
		boolean result = true;
		String msg = OP_SUCCESS;
		try {
			if (StringUtils.isBlank(id)) {
				ownerService.addDealerVechicle(vehicleSalePOJO);
			} else {
				ownerService.editDealerVechicle(vehicleSalePOJO);
			}
			vehicleSalePOJO.setStatus(Constants.VEHICLE_STATE3);
			vehicleSalePOJO.setSaleDate(new Date());
			ownerService.editVechicle(vehicleSalePOJO);
		} catch (Exception e) {
			result = false;
			msg = OP_FAIL;
			e.printStackTrace();
		}
		renderMsgJson(result, msg);
	}

	/**
	 * @Title:getVehicleDestribut
	 * @Description:机械分布
	 * @return
	 * @throws
	 */
	public String getVehicleDestribute() {
		List<VehicleUnitPOJO> vehicleUnitList = new ArrayList<VehicleUnitPOJO>();
		try {
			if (StringUtils.isNotEmpty(id)) {
				HashMap map = new HashMap();
				map.put("dealerId", id);
				// 只查询出已销售、有效的机械
				map.put("vehicleStatus", Constants.VEHICLE_STATE3);
				map.put("isValid", Constants.IS_VALID_YES);
				vehicleUnitList = ownerService.getVehilclesInDealer(map);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(vehicleUnitList);
		return NONE;
	}

	/**
	 * 函 数 名 :getGpsWorkOnLineData 功能描述：获得定时上报的GPS位置、工况、在线、不在线的数据条数 输入参数:
	 * 
	 * @param
	 * @return void
	 * @throws 无异常处理
	 *             创 建 人:周峰炎 日 期:2013-7-4 修 改 人: 修 改 日 期: 修 改 原 因:
	 */
	public void getGpsWorkOnLineData() {
		try {// 监控列表中的信息
			List<VehicleUnitPOJO> monitorGPSs = null;
			if (getSession().get(Constants.WATCH_VEHICLE) != null) {
				monitorGPSs = (List<VehicleUnitPOJO>) getSession().get(
						Constants.WATCH_VEHICLE);
			}

			// 定时上报: GPS信息,工况信息
			List<Position> positionList = new ArrayList<Position>();
			List<ConditionsPOJO> conditionList = new ArrayList<ConditionsPOJO>();
			Position position = null;
			ConditionsPOJO conditions = null;
			if (monitorGPSs != null) {
				for (VehicleUnitPOJO vehicleUnitPOJO : monitorGPSs) {
					// gps
					position = memCache
							.getPosition(vehicleUnitPOJO.getUnitId());
					if (position != null) {
						position.setVehicleDef(vehicleUnitPOJO.getVehicleDef());
						if (position.getNowTime() == null) {
							position.setNowTime(DateUtil.format(new Date(),
									DateUtil.YMD_DASH_WITH_FULLTIME24));
						}
						if(position.getLat()!=null && position.getLon()!=null&&!position.getLat().equals("0")&&!position.getLon().equals("0")){
							String ret = HttpURLConnectionUtil.getAddress(HttpURLConnectionUtil.getBaiduLonlat(""+position.getLon()+","+position.getLat()));
							if(ret!=null && !ret.equals("")){
								position.setReferencePosition(ret);
							}
						}
						positionList.add(position);
						// 取出后就移除
						// memCache.remove(vehicleUnitPOJO.getUnitId()+"gps");
					}
					// 工况
					conditions = memCache.getCondition(vehicleUnitPOJO
							.getUnitId());
					if (conditions != null) {
						conditions.setVehicleDef(vehicleUnitPOJO
								.getVehicleDef());
						conditionList.add(conditions);
						// 取出后就移除
						// memCache.remove(vehicleUnitPOJO.getUnitId()+"con");
					}
				}
			}

			// 在线、离线数量
			int onLineCount = 0;
			int offLineCount = 0;
			UserPOJO userPOJO = (UserPOJO) getSession()
					.get(Constants.USER_INFO);
			List<RolePOJO> roles = userPOJO.getRoles();
			boolean isDealer = false;// 是否是经销商
			boolean isLeaseHold = false; // 是否融资租赁
			DepartPOJO departPOJO = null;
			if (!roles.isEmpty()) {
				if (roles.size() == 1) {
					RolePOJO role = roles.get(0);
					if (role.getRoleId() == Constants.DEALER_ROLE_ID) {// 如果是经销商的话
						isDealer = true;
					} else if (role.getRoleId() == Constants.LEASEHOLD_ROLE_ID) {
						isLeaseHold = true;
					}
				}
			}
			// 查询条件
			HashMap map = new HashMap();
			if (isDealer) {// 经销商
				departPOJO = userPOJO.getDepartInfo();
				if (departPOJO != null) {
					map.put("dealerId", departPOJO.getDealerId());
				}
			}
			if (isLeaseHold) { // 融资租赁
				map.put("leaseFlag", 1);
			}
			// 只查询出已销售、有效的机械
			map.put("vehicleStatus", Constants.VEHICLE_STATE3);
			map.put("isValid", Constants.IS_VALID_YES);
			// 在线
			map.put("isLogin", Constants.IS_LOGIN_YES);
			onLineCount = ownerService.getVehilclesCount(map);
			// 离线
			map.remove("isLogin");
			map.put("isLogin", Constants.IS_LOGIN_NO);
			offLineCount = ownerService.getVehilclesCount(map);

			HashMap resultMap = new HashMap();
			resultMap.put("gpsInfo", positionList);
			resultMap.put("workInfo", conditionList);
			resultMap.put("onLineCount", onLineCount);
			resultMap.put("offLineCount", offLineCount);
			renderObject(resultMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			renderMsgJson(false, "获取定时上报回应信息失败");
		}

	}
	/**
	　　* 函 数 名 :getTrackData
	　　* 功能描述：获得实时跟踪信息
	　　* 输入参数:
	　　* @param 
	　　* @return void
	　　* @throws 无异常处理　　
	　　* 创 建 人:周峰炎
	　　* 日 期:2013-7-24
	　　* 修 改 人:
	　　* 修 改 日 期:
	　　* 修 改 原 因:
	　　
	 */
	public void getTrackData() {
		try {
			List<Position> positionList = new ArrayList<Position>();
			Position position = null;
			if(StringUtils.isNotBlank(id)){
				  String[] unitIds = id.split(",");
				  HashMap map=new HashMap();
				  List<VehicleUnitPOJO> dealerAreaList=null;
			      for(int i=0;i<unitIds.length;i++){
			    	  position = memCache
								.getPosition(unitIds[i]);
						if (position != null) {
							map.put("unitId", unitIds[i]);
							dealerAreaList = ownerService
									.getVehilclesInDealer(map);
							if(!dealerAreaList.isEmpty()){
								position.setVehicleDef(dealerAreaList.get(0)
										.getVehicleDef());
							}
							if (position.getNowTime() == null) {
								position.setNowTime(DateUtil.format(new Date(),
										DateUtil.YMD_DASH_WITH_FULLTIME24));
							}
							if(position.getLat()!=null && position.getLon()!=null&&!position.getLat().equals("0")&&!position.getLon().equals("0")){
								String ret = HttpURLConnectionUtil.getAddress(HttpURLConnectionUtil.getBaiduLonlat(""+position.getLon()+","+position.getLat()));
								if(ret!=null && !ret.equals("")){
									position.setReferencePosition(ret);
								}
							}
							positionList.add(position);
							// 取出后就移除
							// memCache.remove(vehicleUnitPOJO.getUnitId()+"gps");
						}
			      }
			}

			HashMap resultMap = new HashMap();
			resultMap.put("gpsInfo", positionList);
			renderObject(resultMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			renderMsgJson(false, "获取实时跟踪回应信息失败");
		}

	}
	/**
	 * 函 数 名 :getOnOffLineVechile 功能描述：获得在线、不在线机械 输入参数:
	 * 
	 * @param
	 * @return void
	 * @throws 无异常处理
	 *             创 建 人:周峰炎 日 期:2013-7-5 修 改 人: 修 改 日 期: 修 改 原 因:
	 * 
	 */
	public void getOnOffLineVechile() {
		HashMap<String, Object> result = new HashMap<String, Object>();

		try {
			List<VehicleUnitPOJO> vehicleUnitList = null;
			// 在线、离线数量
			int onLineCount = 0;
			int offLineCount = 0;
			UserPOJO userPOJO = (UserPOJO) getSession()
					.get(Constants.USER_INFO);
			List<RolePOJO> roles = userPOJO.getRoles();
			boolean isDealer = false;// 是否是经销商
			boolean isLeaseHold = false; // 是否融资租赁
			DepartPOJO departPOJO = null;
			if (!roles.isEmpty()) {
				if (roles.size() == 1) {
					RolePOJO role = roles.get(0);
					if (role.getRoleId() == Constants.DEALER_ROLE_ID) {// 如果是经销商的话
						isDealer = true;
					} else if (role.getRoleId() == Constants.LEASEHOLD_ROLE_ID) {
						isLeaseHold = true;
					}
				}
			}
			// 查询条件
			HashMap map = new HashMap();
			if (isDealer) {// 经销商
				departPOJO = userPOJO.getDepartInfo();
				if (departPOJO != null) {
					map.put("dealerId", departPOJO.getDealerId());
				}
			}
			if (isLeaseHold) { // 融资租赁
				map.put("leaseFlag", 1);
			}

			// 只查询出已销售、有效的机械
			map.put("vehicleStatus", Constants.VEHICLE_STATE3);
			map.put("isValid", Constants.IS_VALID_YES);
			// 整机编号
			if (StringUtils.isNotEmpty(vehiclePOJO.getVehicleDef())) {
				map.put("vehicleDef", vehiclePOJO.getVehicleDef());
			}
			// 在线、离线条件
			if (vehiclePOJO.getIsValid() != null) {
				map.put("isLogin", vehiclePOJO.getIsValid());
			}
			onLineCount = ownerService.getVehilclesCount(map);
			vehicleUnitList = ownerService.getVehilclesInDealer(map);
			result.put("total", onLineCount);
			result.put("rows", vehicleUnitList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(result);
	}

	/**
	 * 函 数 名 :getVechilesInArea 功能描述：用于拉框搜索 输入参数:
	 * 
	 * @param
	 * @return void
	 * @throws 无异常处理
	 *             创 建 人:周峰炎 日 期:2013-7-12 修 改 人: 修 改 日 期: 修 改 原 因:
	 */
	public void getVechilesInArea() {
		// List<VehicleUnitPOJO> vehicleUnitList=null;
		UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
		List<RolePOJO> roles = userPOJO.getRoles();
		boolean isDealer = false;// 是否是经销商
		boolean isLeaseHold = false; // 是否是融资租赁
		DepartPOJO departPOJO = null;
		if (!roles.isEmpty()) {
			if (roles.size() == 1) {
				RolePOJO role = roles.get(0);
				if (role.getRoleId() == Constants.DEALER_ROLE_ID) {// 如果是经销商的话
					isDealer = true;
				} else if (role.getRoleId() == Constants.LEASEHOLD_ROLE_ID) { // 如果是融资租赁角色的话
					isLeaseHold = true;
				}
			}
		}
		// 查询条件
		HashMap map = new HashMap();
		if (isDealer) {// 经销商
			departPOJO = userPOJO.getDepartInfo();
			if (departPOJO != null) {
				map.put("dealerId", departPOJO.getDealerId());
			}
		}
		if (isLeaseHold) { // 融资租赁公司
			map.put("leaseFlag", 1);
		}
		// 只查询出已销售、有效的机械
		// map.put("vehicleStatus", Constants.VEHICLE_STATE3);
		// 机械分组
		if (getSession().get(Constants.VEHICLE_STATUS) != null) {
			List<Integer> vehicleStatus = (List<Integer>) getSession().get(
					Constants.VEHICLE_STATUS);
			if (vehicleStatus != null && !vehicleStatus.isEmpty()) {
				StringBuffer sb = new StringBuffer();
				for (Integer status : vehicleStatus) {
					sb.append(status).append(",");
				}
				if (sb.length() > 0) {
					map.put("vehicleStatusList", sb.deleteCharAt(
							sb.length() - 1).toString());
				}
			} else {
				if (isDealer) {// 经销商
					vehicleStatus.add(Constants.VEHICLE_STATE3);
				} else {
					// 如果没有设置数据权限，则默认查一个组为0，实际上没这个组，即不查询出来
					vehicleStatus.add(0);
				}
				map.put("vehicleStatus", vehicleStatus);
			}
		}
		map.put("isValid", Constants.IS_VALID_YES);
		// 左上角的经纬度
		if (condition.getLon() != null) {
			map.put("lon", condition.getLon());
		}
		if (condition.getLat() != null) {
			map.put("lat", condition.getLat());
		}
		// 右下角的经纬度
		if (condition.getLon2() != null) {
			map.put("lon2", condition.getLon2());
		}
		if (condition.getLat2() != null) {
			map.put("lat2", condition.getLat2());
		}
		List<VehicleUnitPOJO> resultList = new ArrayList<VehicleUnitPOJO>();
		// vehicleUnitList=ownerService.getVehilclesInDealer(map);
		try {
			resultList = ownerService.getVehilclesInDealer(map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(resultList);
	}

	@Override
	public VehiclePOJO getModel() {
		return vehiclePOJO;
	}

	public DealerAreaPOJO getDealerAreaPOJO() {
		return dealerAreaPOJO;
	}

	public void setDealerAreaPOJO(DealerAreaPOJO dealerAreaPOJO) {
		this.dealerAreaPOJO = dealerAreaPOJO;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String[] getDealerIds() {
		return dealerIds;
	}

	public void setDealerIds(String[] dealerIds) {
		this.dealerIds = dealerIds;
	}

	public UnitPOJO getUnit() {
		return unit;
	}

	public void setUnit(UnitPOJO unit) {
		this.unit = unit;
	}

	public IMapTagService getMapTagService() {
		return mapTagService;
	}

	public void setMapTagService(IMapTagService mapTagService) {
		this.mapTagService = mapTagService;
	}

	public MapTagPOJO getMapTagPOJO() {
		return mapTagPOJO;
	}

	public void setMapTagPOJO(MapTagPOJO mapTagPOJO) {
		this.mapTagPOJO = mapTagPOJO;
	}

	public List<String> getVehicleUnitPOJOs() {
		return vehicleUnitPOJOs;
	}

	public void setVehicleUnitPOJOs(List<String> vehicleUnitPOJOs) {
		this.vehicleUnitPOJOs = vehicleUnitPOJOs;
	}

	public MemCache getMemCache() {
		return memCache;
	}

	public void setMemCache(MemCache memCache) {
		this.memCache = memCache;
	}

	public CompositeQueryConditionPOJO getCondition() {
		return condition;
	}

	public void setCondition(CompositeQueryConditionPOJO condition) {
		this.condition = condition;
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public SendCommandMessage getSendCommandMessage() {
		return sendCommandMessage;
	}

	public void setSendCommandMessage(SendCommandMessage sendCommandMessage) {
		this.sendCommandMessage = sendCommandMessage;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public VehicleSalePOJO getVehicleSalePOJO() {
		return vehicleSalePOJO;
	}

	public void setVehicleSalePOJO(VehicleSalePOJO vehicleSalePOJO) {
		this.vehicleSalePOJO = vehicleSalePOJO;
	}

	public OwnerService getOwnerService() {
		return ownerService;
	}

	public void setOwnerService(OwnerService ownerService) {
		this.ownerService = ownerService;
	}

}
