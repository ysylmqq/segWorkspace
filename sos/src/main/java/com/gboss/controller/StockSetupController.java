package com.gboss.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gboss.comm.SelConst;
import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.Product;
import com.gboss.pojo.Setup;
import com.gboss.service.StockSetupService;
import com.gboss.util.JsonUtil;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.excel.CreateExcel_PDF_CSV;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.controller
 * @ClassName:StockSetupController
 * @Description:库存设置控制类
 * @author:zfy
 * @date:2013-8-29 下午4:55:12
 */
@Controller
public class StockSetupController extends BaseController {
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(StockSetupController.class);

	@Autowired
	@Qualifier("stockSetupService")
	private StockSetupService stockSetupService;

	private ObjectMapper objectMapper = new ObjectMapper();
	private JsonGenerator jsonGenerator = null;

	@RequestMapping(value = "/stock/addSetups")
	@LogOperation(description = "添加库存设置", type = SystemConst.OPERATELOG_ADD, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> addSetups(@RequestParam(value="ids[]",required=false) List<Long> ids,Integer minStock, Integer overstockTime,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("添加库存设置 开始");
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
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			String userName = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_USERNAME);
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			String whsId=null;
			String whsName=null;
			//获得当前登录人==仓管员所管理的仓库
			if (StringUtils.isNotBlank(userId)) {
				OpenLdap openLdap = OpenLdapManager.getInstance();
			/*	CommonCompany commonCompany = openLdap.getWarehouseByUserId(userId);
				if(commonCompany!=null) {
					whsId=commonCompany.getCompanyno();
					whsName=commonCompany.getCompanyname();
				}*/
				//设置仓库，机构下的仓库
				List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
				for (CommonCompany commonCompany2 : wareHouseList) {
					whsId=commonCompany2.getCompanyno();
					whsName=commonCompany2.getCompanyname();
					break;
				}
			}
			int result = stockSetupService.addSetups(ids,StringUtils.isNullOrEmpty(whsId)?null:Long.valueOf(whsId),whsName,minStock,overstockTime,StringUtils.isNullOrEmpty(userId)?null:Long.valueOf(userId),userName);
			if (result == -1) {
				flag = false;
				msg = "参数不合法";
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg = SystemConst.OP_FAILURE;
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("添加库存设置 结束");
		}
		return resultMap;
	}

	@RequestMapping(value = "/stock/updateSetups")
	@LogOperation(description = "修改库存设置", type = SystemConst.OPERATELOG_UPDATE, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> updateSetups(@RequestParam(value="ids[]",required=false) List<Long> ids,Integer minStock, Integer overstockTime,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("修改库存设置 开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(ids,true));
			
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			int result = stockSetupService.updateSetups(ids,minStock,overstockTime,StringUtils.isNullOrEmpty(userId)?null:Long.valueOf(userId));
			if (result == -1) {
				flag = false;
				msg = "参数不合法";
			} else if (result == 0) {
				flag = false;
				msg = "要操作的对象不存在";
			} else if (result == 2) {
				flag = false;
				msg = "该商品在此仓库中已设置";
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg = SystemConst.OP_FAILURE;
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("修改库存设置 结束");
		}
		return resultMap;
	}

	@RequestMapping(value = "/stock/findStockSetup")
	public @ResponseBody
	Page<HashMap<String, Object>> findStockSetup(
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
				if (pageSelect.getFilter() == null) {
					pageSelect.setFilter(new HashMap());
				}
				String userId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ID);
				String companyId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_COMPANYID);
				//获得当前登录人==仓管员所管理的仓库
				if (StringUtils.isNotBlank(userId)) {
					OpenLdap openLdap = OpenLdapManager.getInstance();
					/*CommonCompany commonCompany = openLdap.getWarehouseByUserId(userId);
					if(commonCompany!=null) {
						pageSelect.getFilter().put("whsId", commonCompany.getCompanyno());
					}*/
					//设置仓库，机构下的仓库
					List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
					for (CommonCompany commonCompany2 : wareHouseList) {
						pageSelect.getFilter().put("whsId", commonCompany2.getCompanyno());
						break;
					}
				}
			}
			results = stockSetupService.findStocks(pageSelect);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印日志
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return results;
	}

	@RequestMapping(value = "/stock/deleteSetup")
	@LogOperation(description = "删除库存设置", type = SystemConst.OPERATELOG_DEL, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> deleteSetup(@RequestBody Setup setup,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("删除库存设置 开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(setup,true));
			
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(setup);
			}
			if (setup == null || setup.getId() == null) {
				flag = false;
				msg = "参数不合法";
			} else {
				int result = stockSetupService.deleteSetup(setup.getId());
				if (result == 0) {
					flag = false;
					msg = "要操作的对象不存在";
				}
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg = SystemConst.OP_FAILURE;
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("删除库存设置 结束");
		}
		return resultMap;
	}

	@RequestMapping(value = "/stock/getSetup")
	public @ResponseBody
	Setup getSetup(@RequestBody Setup setup, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		if (setup == null || setup.getId() == null) {
			return null;
		} else {
			return stockSetupService.get(Setup.class, setup.getId());
		}
	}
	
	@RequestMapping(value = "/stock/deleteSetups")
	@LogOperation(description = "批量删除库存设置", type =  SystemConst.OPERATELOG_DEL, model_id = 2)
	public @ResponseBody HashMap<String, Object> deleteSetups(@RequestBody List<Long> list,HttpServletRequest request) throws SystemException{
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("批量删除库存设置 开始");
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
			int result = stockSetupService.deleteSetups(list);
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
			LOGGER.debug("批量删除库存设置 结束");
		}
		return resultMap;
	}
	
	/**
	 * @Title:exportSetups
	 * @Description:导出库存设置
	 * @param request
	 * @param response
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/product/exportSetups")
	public @ResponseBody
	void exportSetups(
			HttpServletRequest request, HttpServletResponse response)
			throws SystemException {
		List<HashMap<String, Object>> list = null;
		try {
			Map returnMap =   request.getParameterMap();
			//将request.getParameterMap()转换成可操作的普通Map
			// 返回值Map
		    Map map = new HashMap();
		    Iterator entries =returnMap.entrySet().iterator();
		    Map.Entry entry = null;
		    String name = "";
		    String value = "";
		    while (entries.hasNext()) {
		        entry = (Map.Entry) entries.next();
		        name = (String) entry.getKey();
		        Object valueObj = entry.getValue();
		        if(null == valueObj){
		            value = "";
		        }else if(valueObj instanceof String[]){
		            String[] values = (String[])valueObj;
		            for(int i=0;i<values.length;i++){
		                value = values[i] + ",";
		            }
		            value = value.substring(0, value.length()-1);
		        }else{
		            value = valueObj.toString();
		        }
		        if("null".equals(value)){
		        	value="";
		        }
		        if(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(value)){
		        	map.put(name, value);
		        }
		    }

			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			//获得当前登录人==仓管员所管理的仓库
			if (StringUtils.isNotBlank(userId)) {
				OpenLdap openLdap = OpenLdapManager.getInstance();
				/*CommonCompany commonCompany = openLdap.getWarehouseByUserId(userId);
				if(commonCompany!=null) {
					map.put("whsId", commonCompany.getCompanyno());
				}*/
				//设置仓库，机构下的仓库
				List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
				for (CommonCompany commonCompany2 : wareHouseList) {
					map.put("whsId",  commonCompany2.getCompanyno());
					break;
				}
			}
			list = stockSetupService.findSetups(map);
			/*String[] title = new String[6];
			title[0] = "序号";
			title[1] = "仓库名称";
			title[2] = "商品名称";{"商品编码","20"},
			title[3] = "商品规格";
			title[4] = "最小库存";
			title[5] = "积压时长(月)";*/
			
			String[][] title = {{"序号","10"},{"仓库名称","20"},{"商品编码","20"},{"商品名称","20"},{"商品规格","40"},{"最小库存","20"},{"积压时长(月)","24"}};
		
			List valueList = new ArrayList();
			HashMap<String, Object> dataMap = null;
			String[] values = null;
			//获得仓库名称
			OpenLdap openLdap = OpenLdapManager.getInstance();
        	HashMap<String, String> wareHouseMap = new HashMap<String, String>();
        	CommonCompany commonCompany = null;
        	String whsId = null;
        	String whsName= null;
        	int listLenth=list.size();
        	int columnIndex=0;
			for(int i=0; i<listLenth; i++){
				whsId = null;
        		whsName = null;
        		values = new String[7];
				dataMap = list.get(i);
				if (dataMap.get("whsId") != null) {
					whsId=dataMap.get("whsId").toString();
					if (wareHouseMap.get(whsId) != null) {
						whsName = wareHouseMap.get(whsId) ;
					} else {
						commonCompany = openLdap.getCompanyById(dataMap.get("whsId").toString());
						if (commonCompany != null) {
							wareHouseMap.put(dataMap.get("whsId").toString(), commonCompany.getCompanyname());
							whsName = commonCompany.getCompanyname() ;
						}
					}
					
				}
				
				columnIndex=0;
				values[columnIndex] = String.valueOf(i+1);
				columnIndex++;
				values[columnIndex] = whsName == null ?"":whsName.toString();
				columnIndex++;
				values[columnIndex] = dataMap.get("productCode") == null ?"":dataMap.get("productCode").toString();
				columnIndex++;
				values[columnIndex] = dataMap.get("productName") == null ?"":dataMap.get("productName").toString();
				columnIndex++;
				values[columnIndex] = dataMap.get("norm") == null ?"":dataMap.get("norm").toString();
				columnIndex++;
				values[columnIndex] = dataMap.get("minStock") == null ?"":dataMap.get("minStock").toString();
				columnIndex++;
				values[columnIndex]= dataMap.get("overstockTime") == null ?"":dataMap.get("overstockTime").toString();
				valueList.add(values);
			}
			//获得分公司中英文名称
			commonCompany =  openLdap.getCompanyById(companyId);
	
			CreateExcel_PDF_CSV.createExcel(valueList, response, "库存设置", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
	}
}
