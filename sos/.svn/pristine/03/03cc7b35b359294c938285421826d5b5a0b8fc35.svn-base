package com.gboss.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ldap.objectClasses.CommonCompany;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.comm.WhsConst;
import com.gboss.pojo.BorrowDetails;
import com.gboss.pojo.Stock;
import com.gboss.pojo.Stockin;
import com.gboss.pojo.Stockout;
import com.gboss.pojo.Writeoffs;
import com.gboss.service.CheckService;
import com.gboss.service.StockService;
import com.gboss.util.JsonUtil;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.controller
 * @ClassName:StockController
 * @Description:库存控制类
 * @author:zfy
 * @date:2013-8-30 下午3:52:35
 */
@Controller
public class StockController extends BaseController {
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(StockController.class);

	@Autowired
	@Qualifier("stockService")
	private StockService stockService;
	
	@Autowired
	@Qualifier("checkService")
	private CheckService checkService;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	private JsonGenerator jsonGenerator = null;

	/**
	 * @Title:findCurrentStocks
	 * @Description:分页查询当前库存
	 * @param stock
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/stock/findCurrentStocks")
	public @ResponseBody
	Page<HashMap<String, Object>> findCurrentStocks(@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		Page<HashMap<String, Object>> results = null;
		try {
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(pageSelect);
			}
			if (pageSelect != null) {
				Map map = pageSelect.getFilter();
				if (map == null) {
					map = new HashMap<String, Object>();
				}
				String userId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ID);
				String companyId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_COMPANYID);
				if (StringUtils.isNotBlank(userId)) {
					OpenLdap openLdap = OpenLdapManager.getInstance();
					//判断是否是分公司
					int level=openLdap.getUserCompanyLevel( (String) request.getSession().getAttribute(
								SystemConst.ACCOUNT_ORGID));
					if(level==0){//总部
						
					}else{//分公司或者营业处
						//根据分公司或者营业处id得到下面的所有仓库列表
						if(StringUtils.isNullOrEmpty(map.get("whsId"))){
							List<Long> wareHouseIds = new ArrayList<Long>();
							/*String orgId = (String) request.getSession().getAttribute(
									SystemConst.ACCOUNT_ORGID);*/
							List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
							for (CommonCompany commonCompany2 : wareHouseList) {
								wareHouseIds.add(commonCompany2.getCompanyno()==null?null:Long.valueOf(commonCompany2.getCompanyno()));
							}
							if (!wareHouseIds.isEmpty()) {
								map.put("wareHouseIds", wareHouseIds);
							}else{//如果下面没有仓库，则不能看到任何数据
								map.put("whsId", "00");
							}
						}
						
					}
				}
				pageSelect.setFilter(map);
			}
			//是否只查询库存
			boolean isOnlyStock=false;
			if(pageSelect!=null && pageSelect.getFilter()!=null && pageSelect.getFilter().get("onlyStock")!=null){
				isOnlyStock=true;
			}
			results = stockService.findCurrentStocks(pageSelect,isOnlyStock);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印日志
			e.printStackTrace();
			// 同时把异常抛到前台
			//throw new SystemException();
		}
		return results;
	}
	/**
	 * @Title:findAllCurrentStocks
	 * @Description:查询所有当前库存
	 * @param stock
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/stock/findAllCurrentStocks")
	public @ResponseBody
	List<HashMap<String, Object>> findAllCurrentStocks(@RequestBody Map<String, Object> map,
			HttpServletRequest request) throws SystemException {
		List<HashMap<String, Object>> results = null;
		try {
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(map);
			}
			OpenLdap openLdap = OpenLdapManager.getInstance();
		/*	//仓管员
			CommonCompany commonCompany = openLdap.getWarehouseByUserId(userId);
			if(commonCompany!=null) {
				map.put("whsId", commonCompany.getCompanyno());
			}else{*/
			if(StringUtils.isNullOrEmpty(map.get("whsId"))){
				List<Long> wareHouseIds = new ArrayList<Long>();
				/*String orgId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ORGID);*/
				String userId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ID);
				String companyId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_COMPANYID);
				List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
				for (CommonCompany commonCompany2 : wareHouseList) {
					wareHouseIds.add(commonCompany2.getCompanyno()==null?null:Long.valueOf(commonCompany2.getCompanyno()));
				}
				if (!wareHouseIds.isEmpty()) {
					map.put("wareHouseIds", wareHouseIds);
				}else{//如果下面没有仓库，则不能看到任何数据
					map.put("whsId", "00");
				}
			}
			
			results = stockService.findAllCurrentStocks(map,true);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印日志
			e.printStackTrace();
			// 同时把异常抛到前台
			//throw new SystemException();
		}
		return results;
	}
	/**
	 * @Title:findStockLtSetup
	 * @Description:库存预警，查询出库存数量小于库存设置的商品
	 * @param map
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/stock/findStockLtSetup")
	public @ResponseBody
	List<HashMap<String, Object>> findStockLtSetup(
			@RequestBody Map<String, Object> map, HttpServletRequest request)
			throws SystemException {
		List<HashMap<String, Object>> results = null;
		try {
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(map);
			}
			if (map == null) {
				map = new HashMap<String, Object>();
			}
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			if (StringUtils.isNotBlank(userId)) {
				OpenLdap openLdap = OpenLdapManager.getInstance();
				//判断是否是分公司
				String orgId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ORGID);
				int level=openLdap.getUserCompanyLevel( orgId);
				if(level==0){//总部
					
				}else{//分公司或者营业处
					//根据分公司或者营业处id得到下面的所有仓库列表
					if(StringUtils.isNullOrEmpty(map.get("whsId"))){
						List<Long> wareHouseIds = new ArrayList<Long>();
						List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
						for (CommonCompany commonCompany2 : wareHouseList) {
							wareHouseIds.add(commonCompany2.getCompanyno()==null?null:Long.valueOf(commonCompany2.getCompanyno()));
						}
						if (!wareHouseIds.isEmpty()) {
							map.put("wareHouseIds", wareHouseIds);
						}else{//如果下面没有仓库，则不能看到任何数据
							map.put("whsId", "00");
						}
					}
				}
			}
			results = stockService.findStockLtSetup(map);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印日志
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return results;
	}

	@RequestMapping(value = "/stock/addStockIn")
	@LogOperation(description = "添加入库信息", type = SystemConst.OPERATELOG_ADD, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> addStockIn(@RequestBody Stockin stockin,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("添加入库信息 开始");
		}
		HashMap<String, Object> resultMap = null;
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			/**
			 * 把对象转化成一个字符串JsonUtil.oToJ(stockin)
			 * 并写到request里，以便日志处理得到处理数据资料。
			 * by tanxiang  2014/01/26
			 */
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(stockin,true));
			
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(stockin);
			}
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			String userName= (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_USERNAME);
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			String currentWhsCode=null;
			OpenLdap openLdap = OpenLdapManager.getInstance();
			if (stockin != null) {
				if (StringUtils.isNotBlank(userId)) {
					stockin.setUserId(Long.valueOf(userId));
					stockin.setUserName(userName);
					/*//获得当前登录人==仓管员所管理的仓库
					CommonCompany commonCompany = openLdap.getWarehouseByUserId(userId);
					if(commonCompany!=null) {
						stockin.setWhsId(Long.valueOf(commonCompany.getCompanyno()));
						stockin.setWhsName(commonCompany.getCompanyname());
						currentWhsCode=commonCompany.getCompanycode();
					}*/
					//设置仓库，机构下的仓库
					List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
					for (CommonCompany commonCompany2 : wareHouseList) {
						stockin.setWhsId(Long.valueOf(commonCompany2.getCompanyno()));
						stockin.setWhsName(commonCompany2.getCompanyname());
						currentWhsCode=commonCompany2.getCompanycode();
						break;
					}
				}
			}
			//设置机构、分公司name
			if(StringUtils.isNotBlank(companyId)){
				stockin.setCompanyId(Long.valueOf(companyId));
				CommonCompany commonCompany=openLdap.getCompanyById(companyId);
				if(commonCompany!=null){
					stockin.setCompanyName(commonCompany.getCompanyname());
				}
			}
			resultMap = stockService.addStockIn(stockin,currentWhsCode);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg = StringUtils.isBlank(e.getMessage())?SystemConst.OP_FAILURE:e.getMessage();
			resultMap = new HashMap<String, Object>();
			resultMap.put(SystemConst.SUCCESS, flag);
			resultMap.put(SystemConst.MSG, msg);
			e.printStackTrace();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("添加入库信息 结束");
		}
		return resultMap;
	}

	@RequestMapping(value = "/stock/addStockOut")
	@LogOperation(description = "添加出库信息", type = SystemConst.OPERATELOG_ADD, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> addStockOut(@RequestBody Stockout stockout,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("添加出库信息 开始");
		}
		HashMap<String, Object> resultMap = null;
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(stockout,true));
		
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(stockout);
			}
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			String userName= (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_USERNAME);
			String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			String currentWhsCode=null;
			OpenLdap openLdap = OpenLdapManager.getInstance();
			if (stockout != null) {
				if (StringUtils.isNotBlank(userId)) {
					stockout.setUserId(Long.valueOf(userId));
					stockout.setUserName(userName);
					/*//获得当前登录人==仓管员所管理的仓库
					CommonCompany commonCompany = openLdap.getWarehouseByUserId(userId);
					if(commonCompany!=null) {
						stockout.setWhsId(Long.valueOf(commonCompany.getCompanyno()));
						stockout.setWhsName(commonCompany.getCompanyname());
						currentWhsCode=commonCompany.getCompanycode();
					}*/
					//设置仓库，机构下的仓库
					List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
					for (CommonCompany commonCompany2 : wareHouseList) {
						stockout.setWhsId(Long.valueOf(commonCompany2.getCompanyno()));
						stockout.setWhsName(commonCompany2.getCompanyname());
						currentWhsCode=commonCompany2.getCompanycode();
						break;
					}
				}
			}
			//设置分公司name
			if(StringUtils.isNotBlank(companyId)){
				stockout.setCompanyId(Long.valueOf(companyId));
				CommonCompany commonCompany=openLdap.getCompanyById(companyId);
				if(commonCompany!=null){
					stockout.setCompanyName(commonCompany.getCompanyname());
				}
				stockout.setOrgId(orgId==null?null:Long.valueOf(orgId));
			}
			resultMap = stockService.addStockOut(stockout,currentWhsCode,true);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg = StringUtils.isBlank(e.getMessage())?SystemConst.OP_FAILURE:e.getMessage();
			resultMap = new HashMap<String, Object>();
			resultMap.put(SystemConst.SUCCESS, flag);
			resultMap.put(SystemConst.MSG, msg);
			e.printStackTrace();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("添加出库信息 结束");
		}
		return resultMap;
	}

	/**
	 * @Title:findBorrows
	 * @Description:仓库查询(员工借用)
	 * @param borrowerId
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/stock/findBorrows")
	public @ResponseBody
	List<Map<String, Object>> findBorrows(
			@RequestBody Map<String, Object> map,
			HttpServletRequest request) throws SystemException {
		List<Map<String, Object>> results = null;
		try {
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(map);
			}
			//根据分公司或者营业处id得到下面的所有仓库列表
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			OpenLdap openLdap = OpenLdapManager.getInstance();
			//判断是否是分公司
			int level=openLdap.getUserCompanyLevel(orgId);
			if(level==0){//总部
				
			}else{//分公司 或者营业处
				//根据分公司或者营业处id得到下面的所有仓库列表
				//没有选择仓库，则查询机构下的所有仓库
				if(StringUtils.isNullOrEmpty(map.get("whsId"))){
					List<Long> wareHouseIds = new ArrayList<Long>();
					List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
					for (CommonCompany commonCompany2 : wareHouseList) {
						wareHouseIds.add(commonCompany2.getCompanyno()==null?null:Long.valueOf(commonCompany2.getCompanyno()));
					}
					if (!wareHouseIds.isEmpty()) {
						map.put("wareHouseIds", wareHouseIds);
					}else{//如果下面没有仓库，则不能看到任何数据
						map.put("whsId", "00");
					}
				}
				
				//如果登录用户是销售经理，只能查看自己的借用信息
				List<String> roleids = (List<String>)request.getSession().getAttribute(SystemConst.ACCOUNT_ROLEIDS);
				if(StringUtils.isNotNullOrEmpty(roleids)){
				// 根据用户角色ID判断 用户是销售经理、部门经理、销售总监
					if (roleids.contains(SystemConst.ROLEID_SALES_MANAGER)) {// 销售经理
						map.put("borrowerId", userId);
					}
				}
			}
			results = stockService.findBorrows(map);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印日志
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return results;
	}
	
	@RequestMapping(value = "/stock/findOperates")
	public @ResponseBody
	List<HashMap<String, Object>> findOperates(
			@RequestBody Map<String, Object> map,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("员工借用-》查询所有操作(出入库、核销、销账) 开始");
		}
		List<HashMap<String, Object>> results = null;
		try {
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(map);
			}
			/*//根据分公司或者营业处id得到下面的所有仓库列表
			String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			OpenLdap openLdap = OpenLdapManager.getInstance();
			//判断是否是分公司
			int level=openLdap.getUserCompanyLevel( (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_COMPANYID));
			if(level==0){//总部
				
			}else if(level==1){//分公司
				//根据分公司或者营业处id得到下面的所有仓库列表
				List<Long> wareHouseIds = new ArrayList<Long>();
				List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(orgId);
				for (CommonCompany commonCompany2 : wareHouseList) {
					wareHouseIds.add(commonCompany2.getCompanyno());
				}
				if (!wareHouseIds.isEmpty()) {
					map.put("wareHouseIds", wareHouseIds);
				}else{//如果下面没有仓库，则不能看到任何数据
					map.put("whsId", "00");
				}
			}*/
			results = stockService.findOperates(map);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印日志
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("员工借用-》查询所有操作(出入库、核销、销账) 结束");
		}
		return results;
	}
	/**
	 * @Title:findStockInOutDetailsPage
	 * @Description:出入库明细查询
	 * @param pageSelect
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/stock/findStockInOutDetailsPage")
	public @ResponseBody
	Page<HashMap<String, Object>> findStockInOutDetailsPage(
			@RequestBody PageSelect<HashMap<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		Page<HashMap<String, Object>> results = null;
		try {
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(pageSelect);
			}

			if (pageSelect != null) {
				Map map = pageSelect.getFilter();
				if (map == null) {
					map = new HashMap<String, Object>();
				}
				String userId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ID);
				String companyId=(String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_COMPANYID);
				String orgId=(String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ORGID);
				if (StringUtils.isNotBlank(userId)) {
					OpenLdap openLdap = OpenLdapManager.getInstance();
					//判断是否是分公司
					int level=openLdap.getUserCompanyLevel(orgId);
					if(level==0){//总部
						
					}else{//分公司
						pageSelect.getFilter().put("companyId", companyId);
					//}else{
						//根据分公司或者营业处id得到下面的所有仓库列表
						if(StringUtils.isNullOrEmpty(map.get("whsId"))){
							List<Long> wareHouseIds = new ArrayList<Long>();
							List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
							for (CommonCompany commonCompany2 : wareHouseList) {
								wareHouseIds.add(commonCompany2.getCompanyno()==null?null:Long.valueOf(commonCompany2.getCompanyno()));
							}
							if (!wareHouseIds.isEmpty()) {
								pageSelect.getFilter().put("wareHouseIds", wareHouseIds);
							}else{//如果下面没有仓库，则不能看到任何数据
								pageSelect.getFilter().put("whsId", "00");
							}
						}
					}
				}
				//只查询借用的
				Object isBorrowObj=map.get("isBorrow");
				if(isBorrowObj != null && Boolean.parseBoolean(isBorrowObj.toString())){
					map.put("otype", "0,3");// 出库类型(代理销售、借用)
					map.put("itype", 2);// 入库类型(借用)
				}
			}

			results = stockService.findStockInOutDetailsPage(pageSelect);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印日志
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return results;
	}


	@RequestMapping(value = "/stock/addWriteoffInfo")
	@LogOperation(description = "核销", type = SystemConst.OPERATELOG_ADD, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> addWriteoffInfo(@RequestBody Writeoffs writeoffs,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("核销 开始");
		}
		HashMap<String, Object> resultMap = null;
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(writeoffs,true));
		
			 if(LOGGER.isDebugEnabled()){
			jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(
					System.out, JsonEncoding.UTF8);
			System.out.println("参数:");
			jsonGenerator.writeObject(writeoffs);
			 }

			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			String userName= (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_USERNAME);
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			String currentWhsCode=null;
			String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			OpenLdap openLdap = OpenLdapManager.getInstance();
			if (StringUtils.isNotBlank(userId)) {
				writeoffs.setUserId(Long.valueOf(userId));
				writeoffs.setUserName(userName);
				writeoffs.setOrgId(orgId==null?null:Long.valueOf(orgId));
				writeoffs.setCompanyId(companyId==null?null:Long.valueOf(companyId));
				
				/*//获得当前登录人==仓管员所管理的仓库
				CommonCompany commonCompany = openLdap.getWarehouseByUserId(userId);
				if(commonCompany!=null) {
					writeoffs.setWhsId(commonCompany.getCompanyno());
					currentWhsCode=commonCompany.getCompanycode();
				}*/
				//设置仓库，机构下的仓库
				
				List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
				for (CommonCompany commonCompany2 : wareHouseList) {
					writeoffs.setWhsId(Long.valueOf(commonCompany2.getCompanyno()));
					currentWhsCode=commonCompany2.getCompanycode();
					break;
				}
			}
			if(StringUtils.isBlank(currentWhsCode)){
				resultMap = new HashMap<String, Object>();
				resultMap.put(SystemConst.SUCCESS, flag);
				resultMap.put(SystemConst.MSG, "该机构下没有仓库,无法销账!");
			}else{
				resultMap = stockService.addWriteoffInfo(writeoffs,currentWhsCode);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg = SystemConst.OP_FAILURE;
			resultMap = new HashMap<String, Object>();
			resultMap.put(SystemConst.SUCCESS, flag);
			resultMap.put(SystemConst.MSG, msg);
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("核销 结束");
		}
		return resultMap;
	}

	@RequestMapping(value = "/stock/findAllProduct")
	public @ResponseBody List<HashMap<String, Object>> findAllProduct(@RequestBody Map<String, Object> map,HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询库存商品 开始");
		}
		List<HashMap<String, Object>> results = null;
		try {
			String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			if (map == null) {
				map = new HashMap<String, Object>();
			}
			OpenLdap openLdap = OpenLdapManager.getInstance();
			/*Object isContainsOrgObj = map.get("isContainsOrg");//是否查询本机构的所有仓库 true:表示查询，false: 表示不查询;
			if (StringUtils.isNotBlank(orgId) && !(isContainsOrgObj !=null && !Boolean.parseBoolean(isContainsOrgObj.toString()))) {
				//根据分公司或者营业处id得到下面的所有仓库列表
				List<Long> wareHouseIds = new ArrayList<Long>();
				List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(orgId);
				for (CommonCompany commonCompany : wareHouseList) {
					wareHouseIds.add(commonCompany.getCompanyno());
				}
				if (!wareHouseIds.isEmpty()) {
					map.put("wareHouseIds", wareHouseIds);
				}
			}*/
			
			/*//获得当前登录人==仓管员所管理的仓库
			if (StringUtils.isNotBlank(userId)) {
				CommonCompany commonCompany = openLdap.getWarehouseByUserId(userId);
				if(commonCompany!=null) {
					map.put("whsId", commonCompany.getCompanyno());
				}
			}*/
			//设置仓库，机构下的仓库
			List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
			for (CommonCompany commonCompany2 : wareHouseList) {
				map.put("whsId", commonCompany2.getCompanyno());
				break;
			}
			results = stockService.findAllProducts(map);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询库存商品 结束");
		}
		return results;
	}
	
	/**
	 * @Title:findStockInsPage
	 * @Description:分页查询入库信息
	 * @param pageSelect
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/stock/findStockInsPage")
	public @ResponseBody
	Page<HashMap<String, Object>> findStockInsPage(
			@RequestBody PageSelect<HashMap<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		Page<HashMap<String, Object>> results = null;
		try {
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(pageSelect);
			}

			if(pageSelect.getFilter()==null){
				pageSelect.setFilter(new HashMap<String, Object>());
			}
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			OpenLdap openLdap = OpenLdapManager.getInstance();
			//判断是否是分公司
			int level=openLdap.getUserCompanyLevel(orgId);
			if(level==0){//总部
				
			}else{//分公司
				pageSelect.getFilter().put("companyId", companyId);
			//}else{
				//根据分公司或者营业处id得到下面的所有仓库列表
				if(StringUtils.isNullOrEmpty(pageSelect.getFilter().get("whsId"))){
					List<Long> wareHouseIds = new ArrayList<Long>();
					List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
					for (CommonCompany commonCompany2 : wareHouseList) {
						wareHouseIds.add(commonCompany2.getCompanyno()==null?null:Long.valueOf(commonCompany2.getCompanyno()));
					}
					if (!wareHouseIds.isEmpty()) {
						pageSelect.getFilter().put("wareHouseIds", wareHouseIds);
					}
				}
			}
			results = stockService.findStockInsPage(pageSelect);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印日志
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return results;
	}
	/**
	 * @Title:findStockOutsPage
	 * @Description:分页查询出库信息
	 * @param pageSelect
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/stock/findStockOutsPage")
	public @ResponseBody
	Page<HashMap<String, Object>> findStockOutsPage(
			@RequestBody PageSelect<HashMap<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		Page<HashMap<String, Object>> results = null;
		try {
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(pageSelect);
			}

			if(pageSelect.getFilter()==null){
				pageSelect.setFilter(new HashMap<String, Object>());
			}
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			OpenLdap openLdap = OpenLdapManager.getInstance();
			//判断是否是分公司
			int level=openLdap.getUserCompanyLevel(orgId);
			if(level==0){//总部
				
			}else{//分公司、营业处
				pageSelect.getFilter().put("companyId", companyId);
			//}else{
				//根据分公司或者营业处id得到下面的所有仓库列表
				List<Long> wareHouseIds = new ArrayList<Long>();
				List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
				for (CommonCompany commonCompany2 : wareHouseList) {
					wareHouseIds.add(commonCompany2.getCompanyno()==null?null:Long.valueOf(commonCompany2.getCompanyno()));
				}
				if(StringUtils.isNullOrEmpty(pageSelect.getFilter().get("whsId"))){
					if (!wareHouseIds.isEmpty()) {
						pageSelect.getFilter().put("wareHouseIds", wareHouseIds);
					}
				}
				
				if(pageSelect.getFilter().get("isAllocated")!=null){//调拨入库,页面选择调拨出仓库
					if(wareHouseIds!=null && !wareHouseIds.isEmpty()){
						//本仓库为入仓库
						pageSelect.getFilter().put("inWhsId", wareHouseIds.get(0));
					}
				}
			}
			//pageSelect.getFilter().put("level", level+"");

			results = stockService.findStockOutsPage(pageSelect);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印日志
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return results;
	}
	
	@RequestMapping(value = "/stock/findStockOutDetails")
	public @ResponseBody List<HashMap<String, Object>> findStockOutDetails(@RequestBody Map<String, Object> map,HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询某个出库的明细 开始");
		}
		List<HashMap<String, Object>> results = null;
		try {
			results = stockService.findStockOutDetails4(map);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			//throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询某个出库的明细  结束");
		}
		return results;
	}
	@RequestMapping(value = "/stock/findStockInDetails")
	public @ResponseBody List<HashMap<String, Object>> findStockInDetails(@RequestBody Map<String, Object> map,HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询某个入库的明细 开始");
		}
		List<HashMap<String, Object>> results = null;
		try {
			results = stockService.findStockInDetails4(map);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			//throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询某个入库的明细  结束");
		}
		return results;
	}
	
	@RequestMapping(value = "/stock/findWarehouses")
	public @ResponseBody List<CommonCompany> findWarehouses(@RequestBody Map<String, Object> map,HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询用户所管理的仓库 开始");
		}
		List<CommonCompany> results = null;
		try {
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			OpenLdap openLdap = OpenLdapManager.getInstance();
			
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			//获得用户所管理的仓库
			if (StringUtils.isNotBlank(companyId)) {
				results = openLdap.getWarehouseByOrgId(companyId,userId);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询用户所管理的仓库   结束");
		}
		return results;
	}
	
	@RequestMapping(value = "/stock/findWarehousesInCompany")
	public @ResponseBody List<CommonCompany> findWarehousesInCompany(@RequestBody Map<String, Object> map,HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询某分公司下的所有仓库 开始");
		}
		List<CommonCompany> results = null;
		try {
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			OpenLdap openLdap = OpenLdapManager.getInstance();
			String currentWhsId=null;//当前登录人==仓管员所管理的仓库
			/*if (StringUtils.isNotBlank(userId)) {
				CommonCompany commonCompany = openLdap.getWarehouseByUserId(userId);
				if(commonCompany!=null) {
					currentWhsId=commonCompany.getCompanyno();
				}
			}*/
			//设置仓库，机构下的仓库
			List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
			for (CommonCompany commonCompany2 : wareHouseList) {
				currentWhsId= commonCompany2.getCompanyno();
				break;
			}
			
			//获得当前分公司下的所有的仓库
			if (StringUtils.isNotBlank(companyId)) {
				List<CommonCompany> commonCompanys = openLdap.getWarehouseByOrgId(companyId);
				if(commonCompanys!=null) {
					//如果要除开本仓库,用于调拨
					Object isExceptMeObj = map.get("isExceptMe");//是否除开本仓库
					if (isExceptMeObj !=null && Boolean.parseBoolean(isExceptMeObj.toString())) {
						CommonCompany myCommonCompany = null;
						for (CommonCompany commonCompany : commonCompanys) {
							if (commonCompany!=null && commonCompany.getCompanyno().equals(currentWhsId) ) {
								myCommonCompany = commonCompany;
								break;
							}
						}
						commonCompanys.remove(myCommonCompany);
					}
				}
				results=commonCompanys;
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询某分公司下的所有  结束");
		}
		return results;
	}
	@RequestMapping(value = "/stock/getStockInOutNo")
	public @ResponseBody HashMap<String, Object> getStockInOutNo(Boolean isIn,HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得出、入库单号 开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		String code = null;
		try {
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			/*String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);*/
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			String currentWhsId=null;//当前登录人==仓管员所管理的仓库
			String currentWhsCode=null;
			List<Long> wareHouseIds = new ArrayList<Long>();
			OpenLdap openLdap = OpenLdapManager.getInstance();
			if (StringUtils.isNotBlank(userId)) {
				//设置仓库，机构下的仓库
				List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
				for (CommonCompany commonCompany2 : wareHouseList) {
					currentWhsId=commonCompany2.getCompanyno();
					currentWhsCode=commonCompany2.getCompanycode();
					wareHouseIds.add(currentWhsId==null?null:Long.valueOf(currentWhsId));
				}
				
			}
			Boolean canStock=!checkService.checkStatus(companyId==null?null:Long.valueOf(companyId), wareHouseIds, 4, false);//判断是否还有盘点未完成,有:true，没有：false
			if(canStock){
				if(StringUtils.isNotBlank(currentWhsCode)){
					code=stockService.getStockInOutNo(companyId==null?null:Long.valueOf(companyId), currentWhsId==null?null:Long.valueOf(currentWhsId), currentWhsCode, isIn,null);
				    if("1".equals(code)){
				    	flag=false;
						msg="所属分公司未设置自定义编号，出、入库单号无法自动生成!";
				    }else if("2".equals(code)){
				    	flag=false;
						msg="仓库未设置自定义编号，出、入库单号无法自动生成!";
				    }
				}else{
					flag=false;
					msg="仓库未设置自定义编号，出、入库单号无法自动生成!";
				}
			}else{
				flag=false;
				msg="仓库正在盘点，不能进行出入库操作!";
			}
		} catch (Exception e) {
			flag=false;
			msg=SystemConst.OP_FAILURE;
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得出、入库单号 开始  结束");
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		resultMap.put("code", code);//出、入库单号
		return resultMap;
	}
	
	@RequestMapping(value = "/stock/getWriteOffsNo")
	public @ResponseBody HashMap<String, Object> getWriteOffsNo(Boolean isHx,HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得核销、销账单号 开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		String code = null;
		try {
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			String currentWhsId=null;//当前登录人==仓管员所管理的仓库
			String currentWhsCode=null;
			OpenLdap openLdap = OpenLdapManager.getInstance();
			List<Long> wareHouseIds = new ArrayList<Long>();
			//判断是否是分公司
			int level=openLdap.getUserCompanyLevel(orgId);
			if(level==0){//总部
				
			}else{//分公司、营业处
		    	if (StringUtils.isNotBlank(userId)) {
					//设置仓库，机构下的仓库
					List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
					for (CommonCompany commonCompany2 : wareHouseList) {
						currentWhsId=commonCompany2.getCompanyno();
						currentWhsCode=commonCompany2.getCompanycode();
						wareHouseIds.add(currentWhsId==null?null:Long.valueOf(currentWhsId));
					}
				
			    }
		    }
			/*if(StringUtils.isBlank(currentWhsCode)){
				resultMap = new HashMap<String, Object>();
				resultMap.put(SystemConst.SUCCESS, flag);
				resultMap.put(SystemConst.MSG, "该机构下没有仓库,无法操作!");
			}else{*/
				Boolean canStock=!checkService.checkStatus(StringUtils.isNullOrEmpty(companyId)?null:Long.valueOf(companyId), wareHouseIds, 4, false);//判断是否还有盘点未完成,有:true，没有：false
				if(canStock){
					if(StringUtils.isNotBlank(currentWhsCode)){
						code=stockService.getWriteOffsNo(StringUtils.isNullOrEmpty(companyId)?null:Long.valueOf(companyId),StringUtils.isNullOrEmpty(orgId)?null:Long.valueOf(orgId), StringUtils.isNullOrEmpty(currentWhsId)?null:Long.valueOf(currentWhsId), currentWhsCode, isHx);
						if("1".equals(code)){
					    	flag=false;
							msg="所属分公司未设置自定义编号，核销、销账单号无法自动生成!";
					    }else if("2".equals(code)){
					    	flag=false;
							msg="仓库未设置自定义编号，核销、销账单号无法自动生成!";
					    }
					}else{
						flag=false;
						msg="仓库未设置自定义编号，核销、销账单号无法自动生成!";
					}
				}else{
					flag=false;
					msg="仓库正在盘点，不能进行核销、销账操作!";
				}
			//}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag=false;
			msg=SystemConst.OP_FAILURE;
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		resultMap.put("code", code);//核销、销账单号
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得核销、销账单号 开始  结束");
		}
		
		return resultMap;
	}
	@RequestMapping(value = "/stock/getWarehouseName")
	public @ResponseBody HashMap<String, Object> getWarehouseName(HttpServletRequest request){
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得当前仓库名称 开始");
		}
		HashMap<String, Object> result=new HashMap<String, Object>();
		String name = null;
		try {
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			//分公司、营业处获得所管的仓库
			OpenLdap openLdap = OpenLdapManager.getInstance();
			/*String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);*/
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
			//如果只管一个仓库，则只查询出这个仓库名称，如果有多个仓库，则不返回仓库名称
			if(wareHouseList!=null && wareHouseList.size()==1){
				for (CommonCompany commonCompany2 : wareHouseList) {
					name=commonCompany2.getCompanyname();
					break;
				}
			}
			/*if(StringUtils.isBlank(name)){
				//获得当前登录人==仓管员所管理的仓库
				CommonCompany commonCompany = openLdap.getWarehouseByUserId(userId);
				if(commonCompany!=null) {
					name=commonCompany.getCompanyname();
				}
			}*/
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得当前仓库名称 开始");
		}
		result.put("name", name);
		return result;
	}
	@RequestMapping(value = "/stock/getBorrowTotalNum")
	public @ResponseBody Long getBorrowTotalNum(String borrowerId,String channelId,String whsId,HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得用户借用商品总数量 开始");
		}
		Long result=null;
		try {
			//仓库id
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			OpenLdap openLdap = OpenLdapManager.getInstance();
			//根据分公司或者营业处id得到下面的所有仓库列表
			String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			//判断是否是分公司
			int level=openLdap.getUserCompanyLevel(orgId);
			List<Long> wareHouseIds = new ArrayList<Long>();
			if(level==0){//总部
				
			}else{//分公司 或者营业处
				//根据分公司或者营业处id得到下面的所有仓库列表
				//没有选择仓库，则查询机构下的所有仓库
				if(StringUtils.isNullOrEmpty(whsId)){
					List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
					for (CommonCompany commonCompany2 : wareHouseList) {
						wareHouseIds.add(commonCompany2.getCompanyno()==null?null:Long.valueOf(commonCompany2.getCompanyno()));
					}
				}
			}
			result=stockService.getBorrowTotalNum(StringUtils.isNullOrEmpty(borrowerId)?null:Long.valueOf(borrowerId),StringUtils.isNullOrEmpty(channelId)?null:Long.valueOf(channelId),wareHouseIds);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得用户借用商品总数量 结束");
		}
		return result;
	}
	
	@RequestMapping(value = "/stock/saveBorrows")
	@LogOperation(description = "初始化挂账信息", type = SystemConst.OPERATELOG_ADD, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> saveBorrows(@RequestBody BorrowDetails borrowDetails,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("初始化挂账信息 开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(borrowDetails,true));
		
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(borrowDetails);
			}
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			//String currentWhsCode=null;
			String userName= (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_USERNAME);
			OpenLdap openLdap = OpenLdapManager.getInstance();
			if (borrowDetails != null) {
				if (StringUtils.isNotBlank(userId)) {
					borrowDetails.setUserId(Long.valueOf(userId));
					borrowDetails.setUserName(userName);
					//获得当前登录人==仓管员所管理的仓库
					if(StringUtils.isNullOrEmpty(borrowDetails.getWhsId())){
						/*CommonCompany commonCompany = openLdap.getWarehouseByUserId(userId);
						if(commonCompany!=null) {
							if(StringUtils.isNotBlank(commonCompany.getCompanyno())){
								borrowDetails.setWhsId(Long.valueOf(commonCompany.getCompanyno()));
							}
							borrowDetails.setWhsName(commonCompany.getCompanyname());
							//currentWhsCode=commonCompany.getCompanycode();
						}*/
						//设置仓库，机构下的仓库
						List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
						for (CommonCompany commonCompany2 : wareHouseList) {
							borrowDetails.setWhsId(Long.valueOf(commonCompany2.getCompanyno()));
							borrowDetails.setWhsName(commonCompany2.getCompanyname());
							break;
						}
					}
				}
				//设置机构、分公司name
				if(StringUtils.isNotBlank(companyId)){
					CommonCompany commonCompany=openLdap.getCompanyById(companyId);
					if(commonCompany!=null){
						borrowDetails.setCompanyName(commonCompany.getCompanyname());
					}
				}
				if(StringUtils.isNotBlank(orgId)){
					CommonCompany commonCompany=openLdap.getCompanyById(orgId);
					if(commonCompany!=null){
						borrowDetails.setOrgName(commonCompany.getCompanyname());
					}
				}
				borrowDetails.setOrgId(orgId==null?null:Long.valueOf(orgId));
				borrowDetails.setCompanyId(companyId==null?null:Long.valueOf(companyId));
			}
			int result = stockService.saveBorrows(borrowDetails);
			if (result == -1) {
				flag = false;
				msg = "参数不合法";
			} else if (result == 0) {
				flag = false;
				msg = "要操作的对象不存在";
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg = StringUtils.isBlank(e.getMessage())?SystemConst.OP_FAILURE:e.getMessage();
			e.printStackTrace();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("初始化挂账信息 结束");
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		return resultMap;
	}
	
	@RequestMapping(value = "/stock/deleteBorrows")
	@LogOperation(description = "批量删除挂账信息", type =  SystemConst.OPERATELOG_DEL, model_id = 2)
	public @ResponseBody HashMap<String, Object> deleteBorrows(@RequestBody List<Long> list,HttpServletRequest request) throws SystemException{
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("批量删除挂账信息 开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(list,true));
		
			if(LOGGER.isDebugEnabled()){
				jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(list);
			}
			int result = stockService.deleteBorrows(list);
			if (result == -1) {
				flag = false;
				msg = "参数不合法";
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("批量删除挂账信息 结束");
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/stock/saveStock")
	@LogOperation(description = "初始化库存", type = SystemConst.OPERATELOG_ADD, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> saveStock(@RequestBody Stock stock,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("初始化库存 开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(stock,true));
		
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(stock);
			}
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			String userName= (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_USERNAME);
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			//String currentWhsCode=null;
			OpenLdap openLdap = OpenLdapManager.getInstance();
			if (stock != null) {
				if (StringUtils.isNotBlank(userId)) {
					stock.setUserId(Long.valueOf(userId));
					stock.setUserName(userName);
					//获得当前登录人==仓管员所管理的仓库
					if(StringUtils.isNullOrEmpty(stock.getWhsId())){
						/*CommonCompany commonCompany = openLdap.getWarehouseByUserId(userId);
						if(commonCompany!=null) {
							if(StringUtils.isNotBlank(commonCompany.getCompanyno())){
								stock.setWhsId(Long.valueOf(commonCompany.getCompanyno()));
							}
							stock.setWhsName(commonCompany.getCompanyname());
							//currentWhsCode=commonCompany.getCompanycode();
						}*/
						//设置仓库，机构下的仓库
						List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
						for (CommonCompany commonCompany2 : wareHouseList) {
							stock.setWhsId(Long.valueOf(commonCompany2.getCompanyno()));
							stock.setWhsName(commonCompany2.getCompanyname());
							break;
						}
					}
				}
				//设置分公司name
				if(StringUtils.isNotBlank(companyId)){
					stock.setCompanyId(Long.valueOf(companyId));
					CommonCompany commonCompany=openLdap.getCompanyById(companyId);
					if(commonCompany!=null){
						stock.setCompanyName(commonCompany.getCompanyname());
					}
				}
			}
			int result = stockService.saveStock(stock);
			if (result == -1) {
				flag = false;
				msg = "参数不合法";
			} else if (result == 2) {
				flag = false;
				msg = "名称为["+stock.getProductName()+"]的商品已初始化库存！";
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg = StringUtils.isBlank(e.getMessage())?SystemConst.OP_FAILURE:e.getMessage();
			e.printStackTrace();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("初始化库存 结束");
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		return resultMap;
	}
	/**
	 * @Title:findBorrowDetailsByPage
	 * @Description:分页查询借用挂账信息
	 * @param pageSelect
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/stock/findBorrowDetailsByPage")
	public @ResponseBody
	Page<HashMap<String, Object>> findBorrowDetailsByPage(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		Page<HashMap<String, Object>> results = null;
		try {
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(pageSelect);
			}

			if(pageSelect.getFilter()==null){
				pageSelect.setFilter(new HashMap<String, Object>());
			}
			String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			OpenLdap openLdap = OpenLdapManager.getInstance();
			//判断是否是分公司
			int level=openLdap.getUserCompanyLevel(orgId);
			if(level==0){//总部
				
			}else{//分公司 或者营业处
				//根据分公司或者营业处id得到下面的所有仓库列表
				//没有选择仓库，则查询机构下的所有仓库
				if(StringUtils.isNullOrEmpty(pageSelect.getFilter().get("whsId"))){
					List<Long> wareHouseIds = new ArrayList<Long>();
					List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
					for (CommonCompany commonCompany2 : wareHouseList) {
						wareHouseIds.add(commonCompany2.getCompanyno()==null?null:Long.valueOf(commonCompany2.getCompanyno()));
					}
					if (!wareHouseIds.isEmpty()) {
						pageSelect.getFilter().put("wareHouseIds", wareHouseIds);
					}else{//如果下面没有仓库，则不能看到任何数据
						pageSelect.getFilter().put("whsId", "00");
					}
				}
			}
			results = stockService.findBorrowDetailsByPage(pageSelect);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印日志
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return results;
	}
	@RequestMapping(value = "/stock/saveBorrowTo")
	@LogOperation(description = "修改挂账转移", type = SystemConst.OPERATELOG_UPDATE, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> saveBorrowTo(@RequestParam(value="ids[]",required=false) List<Long> ids,Long borrowerId, String borrowerName,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("修改挂账转移 开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(ids,true));
		
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(ids);
			}
			int result = stockService.saveBorrowTo(ids,borrowerId,borrowerName);
			if (result == -1) {
				flag = false;
				msg = "参数不合法";
			}  else if (result == 0) {
				flag = false;
				msg = "要操作的对象不存在";
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg = StringUtils.isBlank(e.getMessage())?SystemConst.OP_FAILURE:e.getMessage();
			e.printStackTrace();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("修改挂账转移 结束");
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		return resultMap;
	}
	
	@RequestMapping(value = "/stock/deleteStocks")
	@LogOperation(description = "批量删除库存信息", type =  SystemConst.OPERATELOG_DEL, model_id = 2)
	public @ResponseBody HashMap<String, Object> deleteStocks(@RequestBody List<Long> list,HttpServletRequest request) throws SystemException{
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("批量删除库存信息 开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(list,true));
		
			if(LOGGER.isDebugEnabled()){
				jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(list);
			}
			int result = stockService.deleteStocks(list);
			if (result == -1) {
				flag = false;
				msg = "参数不合法";
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("批量删除库存信息 结束");
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/stock/findWriteoffs")
	public @ResponseBody
	Page<HashMap<String, Object>> findWriteoffs(@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询核销记录结束");
		}
		Page<HashMap<String, Object>> results = null;
		try {
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(pageSelect);
			}
			if (pageSelect != null) {
				Map map = pageSelect.getFilter();
				if (map == null) {
					map = new HashMap<String, Object>();
				}
				String userId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ID);
				if (StringUtils.isNotBlank(userId)) {
					OpenLdap openLdap = OpenLdapManager.getInstance();
					String companyId = (String) request.getSession().getAttribute(
							SystemConst.ACCOUNT_COMPANYID);
					String orgId = (String) request.getSession().getAttribute(
							SystemConst.ACCOUNT_ORGID);
					//判断是否是分公司
					int level=openLdap.getUserCompanyLevel(orgId);
					if(level==0){//总部
						
					}else{//分公司
						pageSelect.getFilter().put("companyId", companyId);
					//}else{
						//根据分公司或者营业处id得到下面的所有仓库列表
						if(StringUtils.isNullOrEmpty(pageSelect.getFilter().get("whsId"))){
							List<Long> wareHouseIds = new ArrayList<Long>();
							List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
							for (CommonCompany commonCompany2 : wareHouseList) {
								wareHouseIds.add(commonCompany2.getCompanyno()==null?null:Long.valueOf(commonCompany2.getCompanyno()));
							}
							if (!wareHouseIds.isEmpty()) {
								pageSelect.getFilter().put("wareHouseIds", wareHouseIds);
							}
						}
					}
				}
				pageSelect.setFilter(map);
			}
			
			results = stockService.findWriteoffs(pageSelect);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印日志
			e.printStackTrace();
			// 同时把异常抛到前台
			//throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询核销记录 结束");
		}
		return results;
	}
	
	@RequestMapping(value = "/stock/findWriteoffsXz")
	public @ResponseBody
	Page<HashMap<String, Object>> findWriteoffsXz(@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询销账记录结束");
		}
		Page<HashMap<String, Object>> results = null;
		try {
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(pageSelect);
			}
			if (pageSelect != null) {
				Map map = pageSelect.getFilter();
				if (map == null) {
					map = new HashMap<String, Object>();
				}
				String userId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ID);
				if (StringUtils.isNotBlank(userId)) {
					OpenLdap openLdap = OpenLdapManager.getInstance();
					String companyId = (String) request.getSession().getAttribute(
							SystemConst.ACCOUNT_COMPANYID);
					String orgId = (String) request.getSession().getAttribute(
							SystemConst.ACCOUNT_ORGID);
					//判断是否是分公司
					int level=openLdap.getUserCompanyLevel(orgId);
					if(level==0){//总部
						
					}else {//分公司
						pageSelect.getFilter().put("companyId", companyId);
					//}else{
						//根据分公司或者营业处id得到下面的所有仓库列表
						if(StringUtils.isNullOrEmpty(pageSelect.getFilter().get("whsId"))){
							List<Long> wareHouseIds = new ArrayList<Long>();
							List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
							for (CommonCompany commonCompany2 : wareHouseList) {
								wareHouseIds.add(commonCompany2.getCompanyno()==null?null:Long.valueOf(commonCompany2.getCompanyno()));
							}
							if (!wareHouseIds.isEmpty()) {
								pageSelect.getFilter().put("wareHouseIds", wareHouseIds);
							}
						}
					}
				}
				pageSelect.setFilter(map);
			}
			
			results = stockService.findWriteoffsXz(pageSelect);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印日志
			e.printStackTrace();
			// 同时把异常抛到前台
			//throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询销账记录 结束");
		}
		return results;
	}
	
	@RequestMapping(value = "/stock/findWriteoffsDetails")
	public @ResponseBody List<HashMap<String, Object>> findWriteoffsDetails(@RequestBody Map<String, Object> map,HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询某个核销的明细 开始");
		}
		List<HashMap<String, Object>> results = null;
		try {
			results = stockService.findWriteoffsDetails(map);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			//throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询某个核销的明细  结束");
		}
		return results;
	}
	@RequestMapping(value = "/stock/findWriteoffsDetailsXz")
	public @ResponseBody List<HashMap<String, Object>> findWriteoffsDetailsXz(@RequestBody Map<String, Object> map,HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询某个销账的明细 开始");
		}
		List<HashMap<String, Object>> results = null;
		try {
			results = stockService.findWriteoffsDetailsXz(map);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			//throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询某个销账的明细  结束");
		}
		return results;
	}
}
