package com.chinaGPS.gtmp.action.unit;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.liteframework.core.web.util.JsonExtUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.chinaGPS.gtmp.action.common.BaseAction;
import com.chinaGPS.gtmp.entity.RolePOJO;
import com.chinaGPS.gtmp.entity.UnitPOJO;
import com.chinaGPS.gtmp.entity.UnitSetUp;
import com.chinaGPS.gtmp.entity.UserPOJO;
import com.chinaGPS.gtmp.entity.VehicleWorkPOJO;
import com.chinaGPS.gtmp.service.IUnitService;
import com.chinaGPS.gtmp.util.Constants;
import com.chinaGPS.gtmp.util.DateUtil;
import com.chinaGPS.gtmp.util.OperationLog;
import com.chinaGPS.gtmp.util.page.PageSelect;
import com.chinaGPS.gtmp.util.upload.ExcelUtil;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Package:com.chinaGPS.gtmp.action.unit
 * @ClassName:UnitAction
 * @Description:处理终端测试业务响应Action类型
 * @author:lxj
 * @date:Dec 18, 2012 2:54:17 PM
 * 
 */
@Scope("prototype")
@Controller 
public class UnitAction extends BaseAction implements ModelDriven<UnitPOJO> {
	private static final long serialVersionUID = -6908832647706199052L;

	private Logger logger = Logger.getLogger(UnitAction.class);

	@Resource
	private IUnitService unitService;
	@Resource
	private UnitPOJO unit;
	private UnitSetUp usu;
	@Resource
	private PageSelect pageSelect;

	private int page;
	private int rows;

	private File upload;
	private String uploadFileName;
	private String newFileName;
	// 批量终端ID
	private List<String> idList;
	private List<String> units;// 批量还原终端的信息
	private int id;
	private int unitId;
	private int unitStatus;
	private String vehicleDef;
	private String simNo;

	/**
	 * @Title:query
	 * @Description:处理查询终端信息请求业务
	 * @return
	 * @throws
	 */
	public String search() throws Exception {
		pageSelect.setPage(page);
		pageSelect.setRows(rows);
		UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
		List<RolePOJO> roles = userPOJO.getRoles();
		boolean isSupperier = false;// 是否是终端供应商
		if (!roles.isEmpty()) {
			if (roles.size() == 1) {
				RolePOJO role = roles.get(0);
				if (role.getRoleId().intValue() == Constants.SUPPERIER_ROLE_ID
						.intValue()) {// 如果是终端供应商的话
					isSupperier = true;
				}
			}
		}
		if (isSupperier) {// 终端供应商
			unit.setUserId(userPOJO.getDepartId());
		}
		renderObject(unitService.getUnits(unit, pageSelect));
		return NONE;
	}
	
	/**
	 * @Title:query
	 * @Description:处理查询终端信息请求业务
	 * @return
	 * @throws
	 */
	public String searchUnitSetUp() throws Exception {
		if(page==0){
			page=1;
		}
		if(rows==0){
			rows=10;
		}
		pageSelect.setPage(page);
		pageSelect.setRows(rows);
		UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
		List<RolePOJO> roles = userPOJO.getRoles();
		Map mp=new HashMap();
		unitId=getParameter("unitId").equals("")?0:Integer.parseInt(getParameter("unitId"));
		mp.put("unitId", unitId);
		mp.put("unitStatus", unitStatus);
		mp.put("vehicleDef", vehicleDef);
		simNo=getParameter("simNo");
		mp.put("simNo", simNo);
		boolean isSupperier = false;// 是否是终端供应商
		if (!roles.isEmpty()) {
			if (roles.size() == 1) {
				RolePOJO role = roles.get(0);
				if (role.getRoleId().intValue() == Constants.SUPPERIER_ROLE_ID.intValue()) {// 如果是终端供应商的话
					isSupperier = true;
				}
			}
		}
		if (isSupperier) {// 终端供应商
			mp.put("departId",userPOJO.getDepartId());
		}
		renderObject(unitService.findUnitSetUp(mp, pageSelect));
		return NONE;
	}
	
	public void toExcelunitSetUp(){
		UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
		List<RolePOJO> roles = userPOJO.getRoles();
		Map mp=new HashMap();
		unitId=getParameter("unitId")==null||getParameter("unitId").equals("")?0:Integer.parseInt(getParameter("unitId"));
		mp.put("unitId", unitId);
		mp.put("unitStatus", unitStatus);
		mp.put("vehicleDef", vehicleDef);
		simNo=getParameter("simNo");
		mp.put("simNo", simNo);
		boolean isSupperier = false;// 是否是终端供应商
		if (!roles.isEmpty()) {
			if (roles.size() == 1) {
				RolePOJO role = roles.get(0);
				if (role.getRoleId().intValue() == Constants.SUPPERIER_ROLE_ID.intValue()) {// 如果是终端供应商的话
					isSupperier = true;
				}
			}
		}
		if (isSupperier) {// 终端供应商
			mp.put("departId",userPOJO.getDepartId());
		}
		List<UnitSetUp> list=null;
		try {
			list= unitService.findUnitSetUp2(mp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Map<String, Object>> values = new ArrayList<Map<String, Object>>();
		List fieldsList =new ArrayList();
		fieldsList.add("整机编号");
		fieldsList.add("终端编号");
		fieldsList.add("机械状态");
		fieldsList.add("SIM卡号");
		Map<String, Object> map=null;
		for (UnitSetUp object : list) {
			map=new HashMap<String, Object>();
			map.put("0",object.getVehicleDef());
			map.put("1",object.getUnitId());
			if(object.getUnitStatus()==-1){
				map.put("2","停机保号");
			}else if(object.getUnitStatus()==1){
				map.put("2","开通");
			}else if(object.getUnitStatus()==2){
				map.put("2","续费");
			}else if(object.getUnitStatus()==3){
				map.put("2","注销");
			}
			map.put("3",object.getSimNo());
			values.add(map);
		}
		super.renderExcel2("车载设置" + ".xls", fieldsList.toArray(), values);
		
	}


	/**
	 * @Title:getList
	 * @Description:处理获取终端信息列表请求业务
	 * @return
	 * @throws
	 */
	public String getList() throws Exception {
		renderObject(unitService.getList(unit));
		return NONE;
	}

	/**
	 * @Title:getById
	 * @Description:处理通过终端ID获取终端信息请求业务
	 * @return
	 * @throws
	 */
	public String getById() throws Exception {
		unit = unitService.getUnitById(unit.getUnitId());
		renderObject(unit);
		return NONE;
	}

	/**
	 * @Title:delete
	 * @Description:处理删除终端信息请求业务
	 * @return
	 * @throws
	 */
	@OperationLog(description = "终端删除")
	public String delete() {
		boolean result = true;
		String msg = OP_SUCCESS;
		try {
			unitService.delUnitById(unit.getUnitId());
			// logger("终端删除(unitId="+unit.getUnitId()+")");//写入操作日志
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
	 * @Description:处理保存或更新终端信息请求业务
	 * @return
	 * @throws
	 */
	@OperationLog(description = "终端保存")
	public String saveOrUpdate() {
		UserPOJO userPOJO = (UserPOJO) getSession()
				.get(Constants.USER_INFO);
		boolean result = true;
		String msg = OP_SUCCESS;
		if (unit != null) {
			try {
				// 修改终端设置终端所属机构为当前用户机构
				unit.setUserId(userPOJO.getUserId());
				
				//终端供应商
				/*unit.setSupperierSn("01");
				result = unitService.saveOrUpdate(unit);
				*/if(userPOJO.getDepartInfo()!=null&&userPOJO.getDepartInfo().getSupperierSn()!=null){
					unit.setSupperierSn(userPOJO.getDepartInfo().getSupperierSn());
					result = unitService.saveOrUpdate(unit);
				}else{
					result = false;
					msg = "抱歉，您不是终端供应商，找不到供应商编号，保存失败!";
				}
				/*
				 * if(StringUtils.isNotEmpty(unit.getUnitId())){
				 * logger("终端修改(unitSn="+unit.getUnitSn()+")");//写入操作日志 }else{
				 * logger("终端新增(unitSn="+unit.getUnitSn()+")");//写入操作日志 }
				 */

				
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result = false;
				msg = OP_FAIL;
			}
		}
		renderMsgJson(result, msg);
		return NONE;
	}
	
	
	public String saveOrUpdateUnitSetUp(){
		UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
		boolean result = true;
		String msg = OP_SUCCESS;
		Map mp=new HashMap();
		try {
			unitId=getParameter("unitId").equals("")?0:Integer.parseInt(getParameter("unitId"));
			mp.put("unitId", unitId);
			mp.put("unitStatus", unitStatus);
			mp.put("vehicleDef", vehicleDef);	
			//终端供应商
				//if(userPOJO.getDepartInfo()!=null&&userPOJO.getDepartInfo().getSupperierSn()!=null){
					if(unitService.findVehicleByvehicleDef(vehicleDef)<1){
						result = false;
						msg = "您输入的整机编号不存在，请确认后再操作！";
					}else if(unitId==0 && unitService.findUtilSetUpByvehicleDef(vehicleDef)>=1){
						result = false;
						msg = "您输入的整机编号已存在！";
					}else{
						result = unitService.saveOrUpdateUtilSetUp(mp);
					}
			//	}else{
				//	result = false;
				//	msg = "抱歉，您不是终端供应商，找不到供应商编号，保存失败!";
			//	}
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
	 * @Description:防重判断SimNo
	 * @return
	 * @throws
	 */
	public String checkSimNo() {
		Long userId = (Long) getSession().get(Constants.USER_ID);// 得到用户名
		boolean result = true;
		String msg = "";
		if (unit != null) {
			try {
				// 修改终端设置终端所属机构为当前用户机构
				// unit.setUserId(userId);
				unit.setIsValid(Constants.IS_VALID_YES);
				// 判断终端序列号和供应商是否重复并进行编辑操作
				if (!unitService.getUnitBySimNo(unit).isEmpty()) {
					result = false;
					msg = "该SIM卡号已经存在!";
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result = false;
				msg = OP_FAIL;
			}
		}
		renderMsgJson(result, msg);
		return NONE;
	}

	/**
	 * @Title:checkMaterialNo
	 * @Description:防重判断MaterialNo
	 * @return
	 * @throws
	 */
	public String checkMaterialNo() {
		Long userId = (Long) getSession().get(Constants.USER_ID);// 得到用户名
		boolean result = true;
		String msg = "";
		if (unit != null) {
			try {
				// 修改终端设置终端所属机构为当前用户机构
				// unit.setUserId(userId);
				unit.setIsValid(Constants.IS_VALID_YES);
				// 判断终端序列号和供应商是否重复并进行编辑操作
				if (!unitService.getUnitBySimNo(unit).isEmpty()) {
					result = false;
					msg = "该物料条码已经存在!";
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result = false;
				msg = OP_FAIL;
			}
		}
		renderMsgJson(result, msg);
		return NONE;
	}

	/**
	 * @Title:checkSteelNo
	 * @Description:防重判断SteelNo
	 * @return
	 * @throws
	 */
	public String checkSteelNo() {
		Long userId = (Long) getSession().get(Constants.USER_ID);// 得到用户名
		boolean result = true;
		String msg = "";
		if (unit != null) {
			try {
				// 修改终端设置终端所属机构为当前用户机构
				// unit.setUserId(userId);
				unit.setIsValid(Constants.IS_VALID_YES);
				// 判断终端序列号和供应商是否重复并进行编辑操作
				if (!unitService.getUnitBySimNo(unit).isEmpty()) {
					result = false;
					msg = "该钢号已经存在!";
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result = false;
				msg = OP_FAIL;
			}
		}
		renderMsgJson(result, msg);
		return NONE;
	}

	/**
	 * @Title:UnitSn
	 * @Description:防重判断unitSn
	 * @return
	 * @throws
	 */
	public String checkUnitSn() {
		UserPOJO userPOJO = (UserPOJO) getSession()
				.get(Constants.USER_INFO);
		boolean result = true;
		String msg = "";
		if (unit != null) {
			try {
				//终端供应商
				if(userPOJO.getDepartInfo()!=null&&userPOJO.getDepartInfo().getSupperierSn()!=null){
					unit.setSupperierSn(userPOJO.getDepartInfo().getSupperierSn());
				}
				// 用终端供应商编号和unitSn共同判断unitSn是否存在；因为可能2个供应商都是共用的一个unitSn，
				// 但是终端供应商编号不一样；但是用户没选供应商就验证,则联合当前登录用户id来验证
				if (StringUtils.isEmpty(unit.getSupperierSn())) {
					unit.setUserId(userPOJO.getUserId());
				}
				unit.setIsValid(Constants.IS_VALID_YES);
				// 判断终端序列号和供应商是否重复并进行编辑操作
				if (!unitService.getUnitBySnSID(unit).isEmpty()) {
					result = false;
					msg = "该终端序列号已经存在!";
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result = false;
				msg = "操作失败";
			}
		}
		renderMsgJson(result, msg);
		return NONE;
	}

	@SuppressWarnings("unchecked")
	@OperationLog(description = "终端注册导入")
	public void impFromExcel() {
		if (upload != null) {
			String title = "终端注册信息";
			try {
				List<String[]> list = ExcelUtil.uploadFileHandler(upload,
						uploadFileName, title);// 获得上传excel文件解析后的数据内容
				Date productDate = null;
				Long timeMills = null;
				if (list != null && list.size() != 0) {
					list.remove(0);
					list.remove(0);
					List<UnitPOJO> units = new ArrayList<UnitPOJO>();
					UnitPOJO unit = null;
					for (String[] s : list) {
						// sa, 1-201212144000, 1, v1.0, vs.010, 18627112001 ,
						// A1001, 201212144000 , 2012/4/12, 1, 赛格终端
						if(null==s[0]||"".equals(s[0].trim())){
							break;
						}
						unit = new UnitPOJO();
						unit.setSupperierSn(s[0].trim());
						unit.setUnitSn(s[1].trim());
						unit.setUnitTypeSn(s[2].trim());
						unit.setHardwareVersion(s[3].trim());
						unit.setSoftwareVersion(s[4].trim());
						unit.setSimNo(s[5].trim());
						unit.setMaterialNo(s[6].trim());
						unit.setSteelNo(s[7].trim());
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						if (s[8] != null && !s[8].equals("")) {
							productDate = dateFormat.parse(s[8]);
							timeMills = productDate.getTime();
							if (timeMills != null) {
								unit.setProductionDate(new java.sql.Date(
										timeMills));
							}
						}
						unit.setUserId(Long.valueOf(s[9].trim()));
						unit.setRemark(s[10].trim());
						if (s[11] != null && !s[11].equals("")) {
							productDate = dateFormat.parse(s[11]);
							timeMills = productDate.getTime();
							if (timeMills != null) {
								unit.setOpenTime(new java.sql.Date(
										timeMills));
							}
						}
						if (s[12] != null && !s[12].equals("")) {
							productDate = dateFormat.parse(s[12]);
							timeMills = productDate.getTime();
							if (timeMills != null) {
								unit.setEndTime(new java.sql.Date(
										timeMills));
							}
						}
						if (s[13] != null && !s[13].equals("")) {
							unit.setPayAmount(new BigDecimal(s[13]));
						}
						units.add(unit);
					}
					HashMap<String, Object> result = unitService.addUnitsAndSimServer(units);
					//HashMap<String, Object> result = unitService.addUnits(units);

					// logger("终端批量导入,结果:导入数量 "+result.get("count")+",
					// "+result.get("msg")+")");//写入操作日志

					renderObject(result);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("文件上传失败!" + e.getMessage());
			}
		}
	}

	/**
	 * 彻底删除终端信息
	 */
	@OperationLog(description = "终端物理删除")
	public void deleteInRecyle() {
		boolean result = true;
		String msg = OP_SUCCESS;
		try {
			if (idList != null && idList.size() > 0) {
				unitService.deleteUnits(idList);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = false;
			msg = OP_FAIL;
		}
		renderMsgJson(result, msg);
	}

	/**
	 * 函 数 名 :updateUnitsIsValid 功能描述：批量修改终端的有效性 输入参数:
	 * 
	 * @param
	 * @return void
	 * @throws 无异常处理
	 *             创 建 人:周峰炎 日 期:2013-7-4 修 改 人: 修 改 日 期: 修 改 原 因:
	 */
	@OperationLog(description = "终端批量还原")
	public void updateUnitsIsValid() {
		boolean result = true;
		String msg = OP_SUCCESS;
		try {
			if (units != null) {
				List<UnitPOJO> unitPOJOList = new ArrayList<UnitPOJO>();
				JsonExtUtils jeu = JsonExtUtils.create(Inclusion.ALWAYS);
				Map<String, Object> map = null;
				UnitPOJO unitPOJO2 = null;
				for (int i = 0; i < units.size(); i++) {
					map = (Map<String, Object>) jeu.fromJson(units.get(i),
							Map.class);
					unitPOJO2 = new UnitPOJO();
					try {
						BeanUtils.populate(unitPOJO2, map);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
					if (unit == null) {
						unit = new UnitPOJO();
					}
					// 判断sim卡号是否存在
					unit.setIsValid(Constants.IS_VALID_YES);
					unit.setUnitSn(unitPOJO2.getUnitSn());
					unit.setSupperierSn(unitPOJO2.getSupperierSn());
					// 判断终端序列号和供应商是否重复并进行编辑操作
					if (com.chinaGPS.gtmp.util.StringUtils.isNotBlank(unitPOJO2
							.getUnitSn())
							&& !unitService.getUnitBySnSID(unit).isEmpty()) {
						result = false;
						msg = "终端序列号为[" + unitPOJO2.getUnitSn() + "]的终端已经存在!";
						break;
					} else {
						unit.setUnitSn(null);
						unit.setSupperierSn(null);
						unit.setSimNo(unitPOJO2.getSimNo());
						// 判断sim卡是否存在
						if (com.chinaGPS.gtmp.util.StringUtils
								.isNotBlank(unitPOJO2.getSimNo())
								&& !unitService.getUnitBySimNo(unit).isEmpty()) {
							result = false;
							msg = "SIM卡号为[" + unitPOJO2.getSimNo()
									+ "]的终端已经存在!";
							break;
						} else {
							// 判断物料编号是否存在
							unit.setSimNo(null);
							unit.setMaterialNo(unitPOJO2.getMaterialNo());
							if (com.chinaGPS.gtmp.util.StringUtils
									.isNotBlank(unitPOJO2.getMaterialNo())
									&& !unitService.getUnitBySimNo(unit)
											.isEmpty()) {
								result = false;
								msg = "物料条码为[" + unitPOJO2.getMaterialNo()
										+ "]的终端已经存在!";
								break;
							} else {
								// 判断钢号是否存在
								unit.setMaterialNo(null);
								unit.setSteelNo(unitPOJO2.getSteelNo());
								if (com.chinaGPS.gtmp.util.StringUtils
										.isNotBlank(unitPOJO2.getSteelNo())
										&& !unitService.getUnitBySimNo(unit)
												.isEmpty()) {
									result = false;
									msg = "钢号为[" + unitPOJO2.getSteelNo()
											+ "]的终端已经存在!";
									break;
								}
							}
						}
					}

					unitPOJOList.add(unitPOJO2);
				}
				// 确定修改
				if (result && unitPOJOList != null && unitPOJOList.size() > 0) {
					unitService.updateUnitsIsValid(unitPOJOList);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = false;
			msg = OP_FAIL;
		}
		renderMsgJson(result, msg);
	}

	/**
	 * @Title:getUnitService
	 * @Description:提供unitService层的getter方法
	 * @return
	 * @throws
	 */
	public IUnitService getUnitService() {
		return unitService;
	}

	/**
	 * @Title:setUnitService
	 * @Description:提供unitService层的setter方法
	 * @param unitService
	 * @throws
	 */
	public void setUnitService(IUnitService unitService) {
		this.unitService = unitService;
	}

	/**
	 * @Title:getUnit
	 * @Description:提供UnitPOJO实体类的getter方法
	 * @return
	 * @throws
	 */
	public UnitPOJO getUnit() {
		return unit;
	}

	/**
	 * @Title:setUnit
	 * @Description:提供UnitPOJO实体类的setter方法
	 * @param unit
	 * @throws
	 */
	public void setUnit(UnitPOJO unit) {
		this.unit = unit;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public PageSelect getPageSelect() {
		return pageSelect;
	}

	public void setPageSelect(PageSelect pageSelect) {
		this.pageSelect = pageSelect;
	}

	public int getPage() {
		return (page == 0) ? 1 : page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return (rows == 0) ? 10 : rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getNewFileName() {
		return newFileName;
	}

	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}

	@Override
	public UnitPOJO getModel() {
		return unit;
	}

	public List<String> getIdList() {
		return idList;
	}

	public void setIdList(List<String> idList) {
		this.idList = idList;
	}

	public List<String> getUnits() {
		return units;
	}

	public void setUnits(List<String> units) {
		this.units = units;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public int getUnitStatus() {
		return unitStatus;
	}

	public void setUnitStatus(int unitStatus) {
		this.unitStatus = unitStatus;
	}

	public String getVehicleDef() {
		return vehicleDef;
	}

	public void setVehicleDef(String vehicleDef) {
		this.vehicleDef = vehicleDef;
	}

	public String getSimNo() {
		return simNo;
	}

	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}

	
	
}
