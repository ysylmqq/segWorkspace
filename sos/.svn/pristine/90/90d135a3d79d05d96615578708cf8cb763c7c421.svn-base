
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.comm.WhsConst;
import com.gboss.service.StockService;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.excel.CreateExcel_PDF_CSV;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.controller
 * @ClassName:StockReportController
 * @Description:库存报表控制类
 * @author:zfy
 * @date:2013-8-30 下午3:52:35
 */
@Controller
public class StockReportController extends BaseController {
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(StockReportController.class);

	@Autowired
	@Qualifier("stockService")
	private StockService stockService;

	@RequestMapping(value = "/stock/exportExcel4StockIn")
	public void exportExcel4StockIn(HttpServletRequest request,HttpServletResponse response) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出新产品入库表 开始");
		}
		
		try {
			/*String[] title = new String[9];
			title[columnIndex]  = "序号";
			title[columnIndex]  = "日期";
			title[columnIndex]  = "商品编码";
			title[columnIndex]  = "商品名称";
			title[columnIndex]  = "商品规格";
			title[columnIndex]  = "单位";
			title[columnIndex]  = "数量";
			title[columnIndex]  = "送货单号";
			title[columnIndex]  = "备注";
			*/
			
			String[][] title = {{"序号","10"},{"日期","20"},{"商品编码","20"},{"商品名称","20"},{"商品规格","40"},{"代理商","16"},{"数量","16","1"},{"单价","16"},{"送货单号","26"},{"仓库","13"},{"备注","20"}};
			
			//条件
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
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			if (StringUtils.isNotBlank(companyId)) {
				map.put("companyId", companyId);
			}
			List<Long> wareHouseIds = new ArrayList<Long>();
			OpenLdap openLdap = OpenLdapManager.getInstance();
			if(StringUtils.isNullOrEmpty(map.get("whsId"))){
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
			map.put("type", 0);//入库类型：采购

			
			List<HashMap<String, Object>> results = stockService.findAllStockInDetails3(map);
			List valueList = new ArrayList();
			HashMap<String, Object> valueData = null;
			String[] values = null;
			int listLenth=results.size();
			int columnIndex=0;
			for(int i=0; i<listLenth; i++){
				values = new String[11];
				valueData = results.get(i);
				columnIndex=0;
				values[columnIndex]  = (i+1)+"";
				columnIndex++;
				values[columnIndex] = valueData.get("stamp") == null ? "" : valueData.get("stamp").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("productCode") == null ? "" : valueData.get("productCode").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("productName") == null ? "" : valueData.get("productName").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("norm") == null ? "" : valueData.get("norm").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("unit") == null ? "" : valueData.get("unit").toString();
				columnIndex++;
				values[columnIndex]  = (valueData.get("inNum") == null ? "" : valueData.get("inNum"))+"";
				columnIndex++;
				values[columnIndex]  = (valueData.get("price") == null ? "" : valueData.get("price"))+"";
				columnIndex++;
				values[columnIndex]  = valueData.get("code") == null ? "" : valueData.get("code").toString();
				columnIndex++;
				values[columnIndex] = valueData.get("whsName") == null ? "" : valueData.get("whsName").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("remark") == null ? "" : valueData.get("remark").toString();
				valueList.add(values);
			}
			//获得分公司中英文名称
			CommonCompany commonCompany =  openLdap.getCompanyById(companyId);
	
			CreateExcel_PDF_CSV.createExcel(valueList, response, "新产品入库表", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),true);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出新产品入库表 结束");
		}
	}
	
	@RequestMapping(value = "/stock/findStockIn4Report")
	public @ResponseBody Page<HashMap<String, Object>> findStockIn4Report(@RequestBody PageSelect<Map<String, Object>> pageSelect,HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询新产品入库表 开始");
		}
		Page<HashMap<String, Object>> results = null;
		try {
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
				
			}else if(level==1){//分公司
				pageSelect.getFilter().put("companyId", companyId);
			}else{
				//根据分公司或者营业处id得到下面的所有仓库列表
				List<Long> wareHouseIds = new ArrayList<Long>();
				if(StringUtils.isNullOrEmpty(pageSelect.getFilter().get("whsId"))){
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
			//pageSelect.getFilter().put("type", 0);//入库类型：采购

			results = stockService.findStockInDetails3ByPage(pageSelect);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询新产品入库表 结束");
		}
		return results;
	}
	
	@RequestMapping(value = "/stock/findAllStockIn4Report")
	public @ResponseBody List<HashMap<String, Object>> findAllStockIn4Report(@RequestBody Map<String, Object> map,HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询所有新产品入库表 开始");
		}
		List<HashMap<String, Object>> results = null;
		try {
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
				
			}else if(level==1){//分公司
				map.put("companyId", companyId);
			}else{
				//根据分公司或者营业处id得到下面的所有仓库列表
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
			//map.put("type", 0);//入库类型：采购

			results = stockService.findAllStockInDetails3(map);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询所有新产品入库表 结束");
		}
		return results;
	}
	
	@RequestMapping(value = "/stock/findWriteoffsAndDetails")
	public @ResponseBody List<HashMap<String, Object>> findWriteoffsOperates(@RequestBody Map<String, Object> map,HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询核销明细 开始(主要为进销存明细) 开始");
		}
		List<HashMap<String, Object>> results = null;
		try {
			results = stockService.findWriteoffsAndDetails(map);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询核销明细 开始(主要为进销存明细) 结束");
		}
		return results;
	}
	
	@RequestMapping(value = "/stock/findWriteoffsAndDetails4Xz")
	public @ResponseBody List<HashMap<String, Object>> findWriteoffsAndDetails4Xz(@RequestBody Map<String, Object> map,HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询销账明细 开始(主要为进销存明细) 开始");
		}
		List<HashMap<String, Object>> results = null;
		try {
			results = stockService.findWriteoffsAndDetails4Xz(map);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询销账明细 开始(主要为进销存明细) 结束");
		}
		return results;
	}
	
	@RequestMapping(value = "/stock/exportExcel4BorrowDetails")
	public void exportExcel4BorrowDetails(HttpServletRequest request,HttpServletResponse response) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出个人、代理商进销存明细表 开始");
		}
		try {
			//条件
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
			/*String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			if (StringUtils.isNotBlank(orgId)) {
				map.put("orgId", orgId);
			}*/
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
				
				//如果登录用户是销售经理，只能查看自己的借用信息
				List<String> roleids = (List<String>)request.getSession().getAttribute(SystemConst.ACCOUNT_ROLEIDS);
				if(StringUtils.isNotNullOrEmpty(roleids)){
				// 根据用户角色ID判断 用户是销售经理、部门经理、销售总监
					if (roleids.contains(SystemConst.ROLEID_SALES_MANAGER)) {// 销售经理
						map.put("borrowerId", userId);
					}
				}
			}
			List<Map<String, Object>> result = stockService.findBorrows(map);
			
			String fileName= null;
			boolean isPerson=true;//是否是个人进销存明细
			if(map.get("type2") != null && "1".equals(map.get("type2").toString())) {//个人进销存明细
				fileName="个人进销存明细表";
				isPerson=true;
	        } else {//代理商进销存明细
	        	fileName="代理商进销存明细表";
	        	isPerson=false;
	        }
			
		/*String[] title = new String[10];
		  title[columnIndex]  = "序号";
		    if(map.get("type2") != null && "1".equals(map.get("type2").toString())) {//个人进销存明细
			   title[columnIndex]  = "借用人";//单位或者借用人
		    }else{//代理商
		       title[columnIndex]  = "单位";//单位或者借用人
		    }
			title[columnIndex]  = "商品编码";
			title[columnIndex]  = "商品名称";
			title[columnIndex]  = "商品规格";
			title[columnIndex]  = "数量";
			title[columnIndex]  = "已核销数量";
			title[columnIndex]  = "已还库数量";
			title[columnIndex]  = "未还库数量";
			title[columnIndex]  = "时间";*/
			String[][] title = {{"序号","10","0"},{"借用人","20","0"},{"代理商","20","0"},{"最终客户","14","0"},{"时间","20","0"},{"商品编码","20","0"},{"商品名称","20","0"},{"商品规格","40","0"},{"数量","10","1"},{"已销账数量","20","1"},{"已核销数量","20","1"},{"已还库数量","20","1"},{"未还库数量","20","1"},{"仓库","10","0"}};
			int columnIndex=0;
			if(!isPerson){//代理商进销存明细
				title=null;
				title= new String[12][];
				columnIndex=0;
				title[columnIndex]=new String[3];
				title[columnIndex][0]="序号";
				title[columnIndex][1]="10";
				title[columnIndex][2]="0";
				columnIndex++;
				title[columnIndex]=new String[3];
				title[columnIndex][0]="代理商";
				title[columnIndex][1]="20";
				title[columnIndex][2]="0";
				columnIndex++;
				title[columnIndex]=new String[3];
				title[columnIndex][0]="时间";
				title[columnIndex][1]="20";
				title[columnIndex][2]="0";
				columnIndex++;
				title[columnIndex]=new String[3];
				title[columnIndex][0]="商品编码";
				title[columnIndex][1]="20";
				title[columnIndex][2]="0";
				columnIndex++;
				title[columnIndex]=new String[3];
				title[columnIndex][0]="商品名称";
				title[columnIndex][1]="20";
				title[columnIndex][2]="0";
				columnIndex++;
				title[columnIndex]=new String[3];
				title[columnIndex][0]="商品规格";
				title[columnIndex][1]="40";
				title[columnIndex][2]="0";
				columnIndex++;
				title[columnIndex]=new String[3];
				title[columnIndex][0]="数量";
				title[columnIndex][1]="10";
				title[columnIndex][2]="1";
				columnIndex++;
				title[columnIndex]=new String[3];
				title[columnIndex][0]="已销账数量";
				title[columnIndex][1]="20";
				title[columnIndex][2]="1";
				columnIndex++;
				title[columnIndex]=new String[3];
				title[columnIndex][0]="已核销数量";
				title[columnIndex][1]="20";
				title[columnIndex][2]="1";
				columnIndex++;
				title[columnIndex]=new String[3];
				title[columnIndex][0]="已还库数量";
				title[columnIndex][1]="20";
				title[columnIndex][2]="1";
				columnIndex++;
				title[columnIndex]=new String[3];
				title[columnIndex][0]="未还库数量";
				title[columnIndex][1]="20";
				title[columnIndex][2]="1";
				columnIndex++;
				title[columnIndex]=new String[3];
				title[columnIndex][0]="仓库";
				title[columnIndex][1]="10";
				title[columnIndex][2]="0";
			}
			
			List valueList = new ArrayList();
			Map<String, Object> dataValue = null;
			String[] values = null;
			int listLenth=result.size();
			for(int i=0; i<listLenth; i++){
				
				if(isPerson){//个人进销存明细
					values = new String[14];
					dataValue = result.get(i);
					columnIndex=0;
					values[columnIndex] = String.valueOf(i+1);
					columnIndex++;
					values[columnIndex] = dataValue.get("borrowerName")==null ? "" :dataValue.get("borrowerName").toString();
					columnIndex++;
					values[columnIndex] =dataValue.get("channelName")==null ? "" :dataValue.get("channelName").toString();
					columnIndex++;
					values[columnIndex] = dataValue.get("customerName")==null ? "" :dataValue.get("customerName").toString();
				    columnIndex++;
					values[columnIndex] =dataValue.get("stamp")==null ? "" :dataValue.get("stamp").toString();
					columnIndex++;
					values[columnIndex] = dataValue.get("productCode")==null ? "" :dataValue.get("productCode").toString();
					columnIndex++;
					values[columnIndex] = dataValue.get("productName")==null ? "" :dataValue.get("productName").toString();
					columnIndex++;
					values[columnIndex] = dataValue.get("norm")==null ? "" :dataValue.get("norm").toString();
					columnIndex++;
					values[columnIndex] = dataValue.get("borrowNum")==null ? "" :dataValue.get("borrowNum").toString();
					columnIndex++;
					values[columnIndex] = dataValue.get("writeoffsNum")==null ? "" :dataValue.get("writeoffsNum").toString();
					columnIndex++;
					values[columnIndex] = dataValue.get("writeoffsNum2")==null ? "" :dataValue.get("writeoffsNum2").toString();
					columnIndex++;
					values[columnIndex] = dataValue.get("returnNum")==null ? "" :dataValue.get("returnNum").toString();
					columnIndex++;
					values[columnIndex] = dataValue.get("num")==null ? "" :dataValue.get("num").toString();
					columnIndex++;
					values[columnIndex] = dataValue.get("whsName")==null ? "" :dataValue.get("whsName").toString();

				}else{//代理商进销存明细
					values = new String[12];
					dataValue = result.get(i);
					columnIndex=0;
					values[columnIndex] = String.valueOf(i+1);
					columnIndex++;
					values[columnIndex] = dataValue.get("channelName")==null ? "" :dataValue.get("channelName").toString();
					columnIndex++;
					values[columnIndex] =dataValue.get("stamp")==null ? "" :dataValue.get("stamp").toString();
					columnIndex++;
					values[columnIndex] = dataValue.get("productCode")==null ? "" :dataValue.get("productCode").toString();
					columnIndex++;
					values[columnIndex] = dataValue.get("productName")==null ? "" :dataValue.get("productName").toString();
					columnIndex++;
					values[columnIndex] = dataValue.get("norm")==null ? "" :dataValue.get("norm").toString();
					columnIndex++;
					values[columnIndex] = dataValue.get("borrowNum")==null ? "" :dataValue.get("borrowNum").toString();
					columnIndex++;
					values[columnIndex] = dataValue.get("writeoffsNum")==null ? "" :dataValue.get("writeoffsNum").toString();
					columnIndex++;
					values[columnIndex] = dataValue.get("writeoffsNum2")==null ? "" :dataValue.get("writeoffsNum2").toString();
					columnIndex++;
					values[columnIndex] = dataValue.get("returnNum")==null ? "" :dataValue.get("returnNum").toString();
					columnIndex++;
					values[columnIndex] = dataValue.get("num")==null ? "" :dataValue.get("num").toString();
					columnIndex++;
					values[columnIndex] = dataValue.get("whsName")==null ? "" :dataValue.get("whsName").toString();

				}
				valueList.add(values);
			}
			//合计
			//获得分公司中英文名称
			CommonCompany commonCompany =  openLdap.getCompanyById(companyId);
			String sheetTitle="代理商进销存明细";
			if(map.get("type2") != null && "1".equals(map.get("type2").toString())) {//个人进销存明细
			  sheetTitle="个人进销存明细";
			}
			 CreateExcel_PDF_CSV.createExcel(valueList, response, sheetTitle, title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),true);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}  finally {
		} 
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出个人、代理商进销存明细表 结束");
		}
	}
	
	@RequestMapping(value = "/stock/exportExcel4InOutDetails")
	public void exportExcel4InOutDetails(HttpServletRequest request,HttpServletResponse response) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出出入库明细 开始");
		}
		try {
			//条件
			Map returnMap =   request.getParameterMap();
			//将request.getParameterMap()转换成可操作的普通Map
			// 返回值Map
		    HashMap map = new HashMap();
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
			OpenLdap openLdap = OpenLdapManager.getInstance();
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			String companyId=(String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			String orgId=(String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			if (StringUtils.isNotBlank(userId)) {
				//判断是否是分公司
				int level=openLdap.getUserCompanyLevel(orgId);
				if(level==0){//总部
					
				}else if(level==1){//分公司
					map.put("companyId", companyId);
				}else{
					//根据分公司或者营业处id得到下面的所有仓库列表
					List<Long> wareHouseIds = new ArrayList<Long>();
					if(StringUtils.isNullOrEmpty(map.get("whsId"))){
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
			//只查询出入库明细的
			Object isBorrowObj=map.get("isBorrow");
			if(isBorrowObj != null && Boolean.parseBoolean(isBorrowObj.toString())){
				map.put("otype", "0,3");// 出库类型(代理销售、出入库明细)
				map.put("itype", 2);// 入库类型(出入库明细)
			}
			
			
			List<HashMap<String, Object>> results = stockService.findStockInOutDetails(map);
			/*String[] title = new String[6];
			title[columnIndex]  = "序号";
			title[columnIndex]  = "商品名称";//productName
			title[columnIndex]  = "商品规格";//norm
			title[columnIndex]  = "出入库数量";//inOutNum
			title[columnIndex]  = "结余数量";//surplusNum
			title[columnIndex]  = "日期";//stamp
*/			String[][] title = {{"序号","10"},{"商品编码","20"},{"商品名称","20"},{"商品规格","20"},{"出入库单号","20"},{"操作类型","16"},{"出入库数量","20"},{"结余数量","20"},{"经办人","16"},{"客户","20"},{"日期","20"},{"仓库","10"}};
			
			List valueList = new ArrayList();
			HashMap<String, Object> valueData = null;
			String[] values = null;
			int listLenth=results.size();
			int columnIndex=0;
			for(int i=0; i<listLenth; i++){
				values = new String[12];
				valueData = results.get(i);
				columnIndex=0;
				values[columnIndex]  = (i+1)+"";
				columnIndex++;
				values[columnIndex] = valueData.get("productCode") == null ? "" : valueData.get("productCode").toString();
				columnIndex++;
				values[columnIndex] = valueData.get("productName") == null ? "" : valueData.get("productName").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("norm") == null ? "" : valueData.get("norm").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("stockCode") == null ? "" : valueData.get("stockCode").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("type") == null ? "" : valueData.get("type").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("inOutNum") == null ? "" : valueData.get("inOutNum").toString();
				columnIndex++;
				values[columnIndex]  = (valueData.get("surplusNum") == null ? "" : valueData.get("surplusNum"))+"";
				columnIndex++;
				values[columnIndex]  = valueData.get("managersName") == null ? "" : valueData.get("managersName").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("channelName") == null ? "" : valueData.get("channelName").toString();
				columnIndex++;
				values[columnIndex] = valueData.get("stamp") == null ? "" : valueData.get("stamp").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("whsName") == null ? "" : valueData.get("whsName").toString();
				valueList.add(values);
			}
			//获得分公司中英文名称
			CommonCompany commonCompany =  openLdap.getCompanyById(companyId);
			CreateExcel_PDF_CSV.createExcel(valueList, response, "出入库明细列表", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出出入库明细 结束");
		}
	}
	
	@RequestMapping(value = "/stock/exportExcel4CurrentStocks")
	public void exportExcel4CurrentStocks(HttpServletRequest request,HttpServletResponse response) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出当前库存状态 开始");
		}
		try {
			//条件
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
				
			}else{//分公司或者营业处
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
			//是否只查询库存
			boolean isOnlyStock=false;
			if(map.get("onlyStock")!=null){
				isOnlyStock=true;
			}
			
			List<HashMap<String, Object>> results = stockService.findAllCurrentStocks(map,isOnlyStock);
			/*String[] title = new String[6];
			title[columnIndex]  = "序号";
			title[columnIndex]  = "商品编码";//productCode
			title[columnIndex]  = "商品名称";//productName
			title[columnIndex]  = "商品规格";//norm
			title[columnIndex]  = "单位";//unit
			title[columnIndex]  = "数量";//num
*/			
			String[][] title = {{"序号","10"},{"商品编码","20"},{"商品名称","20"},{"商品规格","40"},{"库存数量","20","1"},{"借用数量","20","1"},{"销售未还款数量","20","1"},{"仓库","10"}};
			int columnIndex=0;
			if(isOnlyStock){//如果只有库存
				title=null;
				title= new String[7][];
				columnIndex=0;
				title[columnIndex] =new String[3];
				title[columnIndex] [0]="序号";
				title[columnIndex] [1]="10";
				title[columnIndex] [2]="0";
				columnIndex++;
				title[columnIndex] =new String[3];
				title[columnIndex] [0]="仓库";
				title[columnIndex] [1]="10";
				title[columnIndex] [2]="0";
				columnIndex++;
				title[columnIndex] =new String[3];
				title[columnIndex] [0]="商品编码";
				title[columnIndex] [1]="20";
				title[columnIndex] [2]="0";
				columnIndex++;
				title[columnIndex] =new String[3];
				title[columnIndex] [0]="商品名称";
				title[columnIndex] [1]="20";
				title[columnIndex] [2]="0";
				columnIndex++;
				title[columnIndex] =new String[3];
				title[columnIndex] [0]="商品规格";
				title[columnIndex] [1]="40";
				title[columnIndex] [2]="0";
				columnIndex++;
				title[columnIndex] =new String[3];
				title[columnIndex] [0]="数量";
				title[columnIndex] [1]="10";
				title[columnIndex] [2]="1";
				columnIndex++;
				title[columnIndex] =new String[3];
				title[columnIndex] [0]="备注";
				title[columnIndex] [1]="10";
				title[columnIndex] [2]="0";
			}
			List valueList = new ArrayList();
			HashMap<String, Object> valueData = null;
			String[] values = null;
			int listLenth=results.size();
			
			for(int i=0; i<listLenth; i++){
				if(isOnlyStock){//如果只有库存
					values = new String[7];
					valueData = results.get(i);
					columnIndex=0;
					values[columnIndex]  = (i+1)+"";
					columnIndex++;
					values[columnIndex] = valueData.get("whsName") == null ? "" : valueData.get("whsName").toString();
					columnIndex++;
					values[columnIndex]  = valueData.get("productCode") == null ? "" : valueData.get("productCode").toString();
					columnIndex++;
					values[columnIndex]  = valueData.get("productName") == null ? "" : valueData.get("productName").toString();
					columnIndex++;
					values[columnIndex]  = valueData.get("norm") == null ? "" : valueData.get("norm").toString();
					columnIndex++;
					values[columnIndex]  = (valueData.get("num") == null ? "0" : valueData.get("num"))+"";
					columnIndex++;
					values[columnIndex]  = valueData.get("remark") == null ?"" : valueData.get("remark")+"";
				}else{
					values = new String[8];
					valueData = results.get(i);
					columnIndex=0;
					values[columnIndex]  = (i+1)+"";
					columnIndex++;
					values[columnIndex] = valueData.get("productCode") == null ? "" : valueData.get("productName").toString();
					columnIndex++;
					values[columnIndex] = valueData.get("productName") == null ? "" : valueData.get("productName").toString();
					columnIndex++;
					values[columnIndex]  = valueData.get("norm") == null ? "" : valueData.get("norm").toString();
					columnIndex++;
					values[columnIndex]  = (valueData.get("num") == null ? "0" : valueData.get("num"))+"";
					columnIndex++;
					values[columnIndex]  = valueData.get("borrowNum") == null || StringUtils.isBlank(valueData.get("borrowNum").toString()) ? "0" : valueData.get("borrowNum").toString();
					columnIndex++;
					values[columnIndex]  = valueData.get("sellUnNum") == null || StringUtils.isBlank(valueData.get("sellUnNum").toString()) ? "0" : valueData.get("sellUnNum").toString();
					columnIndex++;
					values[columnIndex]  = valueData.get("whsName") == null ? "" : valueData.get("whsName").toString();
			
				}
				valueList.add(values);
			}
			//获得分公司中英文名称
			CommonCompany commonCompany =  openLdap.getCompanyById(companyId);
	
			CreateExcel_PDF_CSV.createExcel(valueList, response, "当前库存状态列表", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),true);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出当前库存状态 结束");
		}
	}
	
	@RequestMapping(value = "/stock/exportExcel4Borrow")
	public void exportExcel4Borrow(HttpServletRequest request,HttpServletResponse response) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出借用列表 开始");
		}
		try {
			//条件
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
		    List<Long> wareHouseIds = new ArrayList<Long>();
			String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			OpenLdap openLdap = OpenLdapManager.getInstance();
			if(StringUtils.isNullOrEmpty(map.get("whsId"))){
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
			
			List<Map<String, Object>> results = stockService.findBorrows(map);
/*			String[] title = new String[5];
			title[columnIndex]  = "序号";
			title[columnIndex]  = "商品名称";//productName
			title[columnIndex]  = "数量";//num
			title[columnIndex]  = "借用人名称";//borrowerName{"商品编码","20"},{"商品规格","40"}
*/			
			String[][] title = {{"序号","10"},{"商品编码","20"},{"商品名称","20"},{"商品规格","40"},{"数量","20","1"},{"借用人名称","26"},{"仓库","10"}};
			List valueList = new ArrayList();
			Map<String, Object> valueData = null;
			String[] values = null;
			int listLenth=results.size();
			int columnIndex=0;
			for(int i=0; i<listLenth; i++){
				values = new String[7];
				valueData = results.get(i);
				columnIndex=0;
				values[columnIndex]  = (i+1)+"";
				columnIndex++;
				values[columnIndex] = valueData.get("productCode") == null ? "" : valueData.get("productCode").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("productName") == null ? "" : valueData.get("productName").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("norm") == null ? "" : valueData.get("norm").toString();
				columnIndex++;
				values[columnIndex]  = (valueData.get("num") == null ? "" : valueData.get("num"))+"";
				columnIndex++;
				values[columnIndex]  = valueData.get("borrowerName") == null ? "" : valueData.get("borrowerName").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("whsName") == null ? "" : valueData.get("whsName").toString();
				valueList.add(values);
			}
			//获得分公司中英文名称
			CommonCompany commonCompany =  openLdap.getCompanyById(companyId);

			CreateExcel_PDF_CSV.createExcel(valueList, response, "挂账列表", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),true);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出借用列表 结束");
		}
	}
	
	@RequestMapping(value = "/stock/exportExcel4BorrowDetailsInit")
	public void exportExcel4BorrowDetailsInit(HttpServletRequest request,HttpServletResponse response) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出挂账表(初始化) 开始");
		}
		try {
			//条件
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
		    List<Long> wareHouseIds = new ArrayList<Long>();
			String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			OpenLdap openLdap = OpenLdapManager.getInstance();
			if(StringUtils.isNullOrEmpty(map.get("whsId"))){
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
			List<Map<String, Object>> result = stockService.findBorrows(map);
			
			String fileName= "借用挂账明细表";
			String borrowTitle=null;
			String[][] title = {{"序号","10"},{"营业处仓库","15"},{"挂账类型","10"},{"借用人","15"},{"代理商","20"},{"最终客户","20"},{"商品编码","20"},{"商品名称","20"},{"商品规格","40"},{"合同名称","20"},{"单价","14"},{"借用数量","10"},{"已还库数量","20"},{"已核销数量","20"},{"未还库数量","20"},{"日期","20"},{"备注","20"}};
			List valueList = new ArrayList();
			Map<String, Object> dataValue = null;
			String[] values = null;
			int columnIndex=0;
			int listLenth=result.size();
			for(int i=0; i<listLenth; i++){
				values = new String[17];
				dataValue = result.get(i);
				columnIndex=0;
				values[columnIndex] = String.valueOf(i+1);
				columnIndex++;
				values[columnIndex] = dataValue.get("whsName")==null ? "" :dataValue.get("whsName").toString();
				columnIndex++;
				values[columnIndex] = WhsConst.BORROW_DETAILS_TYPE.get(Integer.parseInt(dataValue.get("type")==null ? "5" :dataValue.get("type").toString()));
				columnIndex++;
				values[columnIndex] = dataValue.get("borrowerName")==null ? "" :dataValue.get("borrowerName").toString();
				columnIndex++;
				values[columnIndex] = dataValue.get("channelName")==null ? "" :dataValue.get("channelName").toString();
				columnIndex++;
				values[columnIndex] = dataValue.get("customerName")==null ? "" :dataValue.get("customerName").toString();
				columnIndex++;
				values[columnIndex] = dataValue.get("productCode")==null ? "" :dataValue.get("productCode").toString();
				columnIndex++;
				values[columnIndex] = dataValue.get("productName")==null ? "" :dataValue.get("productName").toString();
				columnIndex++;
				values[columnIndex] = dataValue.get("norm")==null ? "" :dataValue.get("norm").toString();
				columnIndex++;
				values[columnIndex] = dataValue.get("contractName")==null ? "" :dataValue.get("contractName").toString();
				columnIndex++;
				values[columnIndex] = dataValue.get("price")==null ? "" :dataValue.get("price").toString();
				columnIndex++;
				values[columnIndex] = dataValue.get("borrowNum")==null ? "" :dataValue.get("borrowNum").toString();
				columnIndex++;
				values[columnIndex] = dataValue.get("returnNum")==null ? "" :dataValue.get("returnNum").toString();
				columnIndex++;
				values[columnIndex] = dataValue.get("writeoffsNum")==null ? "" :dataValue.get("writeoffsNum").toString();
				columnIndex++;
				values[columnIndex] = dataValue.get("num")==null ? "" :dataValue.get("num").toString();
				columnIndex++;
				values[columnIndex] = dataValue.get("stamp")==null ? "" :dataValue.get("stamp").toString();
				columnIndex++;
				values[columnIndex] =dataValue.get("remark")==null ? "" :dataValue.get("remark").toString();				
				valueList.add(values);
			}
			//获得分公司中英文名称
			CommonCompany commonCompany =  openLdap.getCompanyById(companyId);
			 CreateExcel_PDF_CSV.createExcel(valueList, response, fileName, title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}  finally {
		} 
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出挂账表(初始化) 结束");
		}
	}
	
	@RequestMapping(value = "/stock/findProductStock")
	public @ResponseBody
	List<HashMap<String, Object>> findProductStock(@RequestBody Map<String, Object> map,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询商品库存(报表) 结束");
		}
		List<HashMap<String, Object>> results = null;
		try {
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
				map.put("companyId", companyId);
			//}else{
				//根据分公司或者营业处id得到下面的所有仓库列表
				List<Long> wareHouseIds = new ArrayList<Long>();
				if(StringUtils.isNullOrEmpty(map.get("whsId"))){
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
			map.put("level", level+"");
			results = stockService.findProductStock(map);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印日志
			e.printStackTrace();
			// 同时把异常抛到前台
			//throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询商品库存(报表) 结束");
		}
		return results;
	}
	
	@RequestMapping(value = "/stock/exportExcel4Stock")
	public void exportExcel4Stock(HttpServletRequest request,HttpServletResponse response) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出库存报表 开始");
		}
		try {
			//条件
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
			/*String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			if (StringUtils.isNotBlank(orgId)) {
				map.put("orgId", orgId);
			}*/
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
				
			}else if(level==1){//分公司
				map.put("companyId", companyId);
			}else{
				//根据分公司或者营业处id得到下面的所有仓库列表
				List<Long> wareHouseIds = new ArrayList<Long>();
				if(StringUtils.isNullOrEmpty(map.get("whsId"))){
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
			map.put("level", level+"");
				
			List<HashMap<String, Object>> result = stockService.findProductStock(map);
			
			String fileName= "库存报表";
			String type=map.get("type")==null?"0":map.get("type").toString();
			String[][] title = {{"序号","10","0"},{"商品编码","20","0"},{"商品名称","20","0"},{"商品规格","40","0"},{"数量","10","1"}};
			int columnIndex=0;
			if(level==0){//总部
				if("1".equals(type)){//明细
					title=null;
					title= new String[6][];
					columnIndex=0;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="序号";
					title[columnIndex] [1]="10";
					title[columnIndex] [2]="0";
					columnIndex++;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="商品编码";
					title[columnIndex] [1]="20";
					title[columnIndex] [2]="0";
					columnIndex++;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="商品名称";
					title[columnIndex] [1]="20";
					title[columnIndex] [2]="0";
					columnIndex++;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="商品规格";
					title[columnIndex] [1]="40";
					title[columnIndex] [2]="0";
					columnIndex++;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="数量";
					title[columnIndex] [1]="10";
					title[columnIndex] [2]="1";
					columnIndex++;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="分公司名称";
					title[columnIndex] [1]="20";
					title[columnIndex] [2]="0";
				}
			
			}else if(level==1){//分公司
				if("1".equals(type)){//明细
					title=null;
					title= new String[6][];
					columnIndex=0;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="序号";
					title[columnIndex] [1]="10";
					title[columnIndex] [2]="0";
					columnIndex++;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="商品编码";
					title[columnIndex] [1]="20";
					title[columnIndex] [2]="0";
					columnIndex++;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="商品名称";
					title[columnIndex] [1]="20";
					title[columnIndex] [2]="0";
					columnIndex++;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="商品规格";
					title[columnIndex] [1]="40";
					title[columnIndex] [2]="0";
					columnIndex++;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="数量";
					title[columnIndex] [1]="10";
					title[columnIndex] [2]="1";
					columnIndex++;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="营业处仓库";
					title[columnIndex] [1]="20";
					title[columnIndex] [2]="0";
				}
			}
			List valueList = new ArrayList();
			Map<String, Object> dataValue = null;
			String[] values = null;
			int listLenth=result.size();
			for(int i=0; i<listLenth; i++){
				values = new String[5];
				dataValue = result.get(i);
				columnIndex=0;
				values[columnIndex]  = String.valueOf(i+1);
				columnIndex++;
				values[columnIndex] = dataValue.get("productCode")==null ? "" :dataValue.get("productCode").toString();
				columnIndex++;
				values[columnIndex] = dataValue.get("productName")==null ? "" :dataValue.get("productName").toString();
				columnIndex++;
				values[columnIndex]  = dataValue.get("norm")==null ? "" :dataValue.get("norm").toString();
				columnIndex++;
				values[columnIndex]  = dataValue.get("num")==null ? "" :dataValue.get("num").toString();
		
				if(level==0){//总部
					if("1".equals(type)){//明细
						values = new String[6];
						columnIndex=0;
						values[columnIndex]  = String.valueOf(i+1);
						columnIndex++;
						values[columnIndex] = dataValue.get("productCode")==null ? "" :dataValue.get("productCode").toString();
						columnIndex++;
						values[columnIndex] = dataValue.get("productName")==null ? "" :dataValue.get("productName").toString();
						columnIndex++;
						values[columnIndex]  = dataValue.get("norm")==null ? "" :dataValue.get("norm").toString();
						columnIndex++;
						values[columnIndex]  = dataValue.get("num")==null ? "" :dataValue.get("num").toString();
						columnIndex++;
						values[columnIndex]  = dataValue.get("companyName")==null ? "" :dataValue.get("companyName").toString();
					}
				}else if(level==1){//分公司
					if("1".equals(type)){//明细
						values = new String[6];
						columnIndex=0;
						values[columnIndex]  = String.valueOf(i+1);
						columnIndex++;
						values[columnIndex] = dataValue.get("productCode")==null ? "" :dataValue.get("productCode").toString();
						columnIndex++;
						values[columnIndex] = dataValue.get("productName")==null ? "" :dataValue.get("productName").toString();
						columnIndex++;
						values[columnIndex]  = dataValue.get("norm")==null ? "" :dataValue.get("norm").toString();
						columnIndex++;
						values[columnIndex]  = dataValue.get("num")==null ? "" :dataValue.get("num").toString();
						columnIndex++;
						values[columnIndex]  = dataValue.get("whsName")==null ? "" :dataValue.get("whsName").toString();
		
					}
				}
				
				valueList.add(values);
			}
			//合计
			//获得分公司中英文名称
			CommonCompany commonCompany =  openLdap.getCompanyById(companyId);
			 CreateExcel_PDF_CSV.createExcel(valueList, response, fileName, title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),true);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}  finally {
		} 
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出库存报表 结束");
		}
	}
	
	@RequestMapping(value = "/stock/exportExcel4Writeoffs")
	public void exportExcel4Writeoffs(HttpServletRequest request,HttpServletResponse response) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出核销信息 开始");
		}
		
		try {
			String[][] title = {{"序号","10"},{"核销单号","20"},{"经办人","15"},{"收据编号","20"},{"人工费","16"},{"日期","26"}};
			
			//条件
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
				
			}else if(level==1){//分公司
				map.put("companyId", companyId);
			}else{
				//根据分公司或者营业处id得到下面的所有仓库列表
				List<Long> wareHouseIds = new ArrayList<Long>();
				if(StringUtils.isNullOrEmpty(map.get("whsId"))){
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
			List<HashMap<String, Object>> results = stockService.findAllWriteoffs(map);
			List valueList = new ArrayList();
			HashMap<String, Object> valueData = null;
			String[] values = null;
			int listLenth=results.size();
			int columnIndex=0;
			for(int i=0; i<listLenth; i++){
				values = new String[6];
				valueData = results.get(i);
				columnIndex=0;
				values[columnIndex]  = (i+1)+"";
				columnIndex++;
				values[columnIndex] = valueData.get("writeoffsNo") == null ? "" : valueData.get("writeoffsNo").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("managersName") == null ? "" : valueData.get("managersName").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("receiptNo") == null ? "" : valueData.get("receiptNo").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("peopleMoney") == null ? "" : valueData.get("peopleMoney").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("stamp") == null ? "" : valueData.get("stamp").toString();
				valueList.add(values);
			}
			//获得分公司中英文名称
			CommonCompany commonCompany =  openLdap.getCompanyById(companyId);
	
			CreateExcel_PDF_CSV.createExcel(valueList, response, "核销信息", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出核销信息 结束");
		}
	}
	@RequestMapping(value = "/stock/exportExcel4WriteoffsXz")
	public void exportExcel4WriteoffsXz(HttpServletRequest request,HttpServletResponse response) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出销账信息 开始");
		}
		try {
			String[][] title = {{"序号","10"},{"销账单号","20"},{"代理商","16"},{"经办人","20"},
					{"转账票号","20"},{"本次销账金额","16","1"},
					{"本次结余金额","16","1"},{"日期","20"},{"备注","20"}};
			
			//条件
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
				
			}else if(level==1){//分公司
				map.put("companyId", companyId);
			}else{
				//根据分公司或者营业处id得到下面的所有仓库列表
				List<Long> wareHouseIds = new ArrayList<Long>();
				if(StringUtils.isNullOrEmpty(map.get("whsId"))){
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
			List<HashMap<String, Object>> results = stockService.findAllWriteoffsXz(map);
			List valueList = new ArrayList();
			HashMap<String, Object> valueData = null;
			String[] values = null;
			int listLenth=results.size();
			int columnIndex=0;
			for(int i=0; i<listLenth; i++){
				values = new String[9];
				valueData = results.get(i);
				columnIndex=0;
				values[columnIndex]  = (i+1)+"";
				columnIndex++;
				values[columnIndex] = valueData.get("writeoffNo") == null ? "" : valueData.get("writeoffNo").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("channelName") == null ? "" : valueData.get("channelName").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("managerName") == null ? "" : valueData.get("managerName").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("ticketno") == null ? "" : valueData.get("ticketno").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("offAmount") == null ? "" : valueData.get("offAmount").toString();
				columnIndex++;
				values[columnIndex]  = (valueData.get("balance") == null ? "" : valueData.get("balance"))+"";
				columnIndex++;
				values[columnIndex]  = valueData.get("stamp") == null ? "" : valueData.get("stamp").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("remark") == null ? "" : valueData.get("remark").toString();
				valueList.add(values);
			}
			//获得分公司中英文名称
			CommonCompany commonCompany =  openLdap.getCompanyById(companyId);
	
			CreateExcel_PDF_CSV.createExcel(valueList, response, "销账信息", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),true);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出销账信息 结束");
		}
	}
	
	@RequestMapping(value = "/stock/findSaleOutDetails")
	public @ResponseBody Page<HashMap<String, Object>> findSaleOutDetails(@RequestBody PageSelect<Map<String, Object>> pageSelect,HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询销售报表 开始");
		}
		Page<HashMap<String, Object>> results = null;
		try {
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
				List<Long> wareHouseIds = new ArrayList<Long>();
				if(StringUtils.isNullOrEmpty(pageSelect.getFilter().get("whsId"))){
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
			pageSelect.getFilter().put("level", level+"");
			pageSelect.getFilter().put("otype", "0,1,2");//代理销售、直销、升级
			results = stockService.findSaleOutDetails(pageSelect);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询销售报表 结束");
		}
		return results;
	}
	
	@RequestMapping(value = "/stock/findAllSaleOutDetails")
	public @ResponseBody List<HashMap<String, Object>> findAllSaleOutDetails(@RequestBody Map<String, Object> map,HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询所有销售报表 开始");
		}
		List<HashMap<String, Object>> results = null;
		try {
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
				map.put("companyId", companyId);
			//}else{
				//根据分公司或者营业处id得到下面的所有仓库列表
				List<Long> wareHouseIds = new ArrayList<Long>();
				if(StringUtils.isNullOrEmpty(map.get("whsId"))){
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
			map.put("level", level+"");
			map.put("otype", "0,1,2");//代理销售、直销、升级	
			results = stockService.findAllSaleOutDetails(map);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询所有销售报表 结束");
		}
		return results;
	}
	@RequestMapping(value = "/stock/exportExcel4SaleOutDetails")
	public void exportExcel4SaleOutDetails(HttpServletRequest request,HttpServletResponse response) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出销售报表 开始");
		}
		try {
			//条件
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
			/*String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			if (StringUtils.isNotBlank(orgId)) {
				map.put("orgId", orgId);
			}*/
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
				map.put("companyId", companyId);
			//}else{
				//根据分公司或者营业处id得到下面的所有仓库列表
				List<Long> wareHouseIds = new ArrayList<Long>();
				if(StringUtils.isNullOrEmpty(map.get("whsId"))){
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
			map.put("level", level+"");
			map.put("otype", "0,1,2");//代理销售、直销、升级	
			
			List<HashMap<String, Object>> result = stockService.findAllSaleOutDetails(map);
			
			String fileName= "销售报表";
			String type=map.get("type")==null?"0":map.get("type").toString();
			String[][] title = {{"序号","10","0"},{"商品编码","20","0"},{"商品名称","20","0"},{"商品规格","40","0"},{"数量","10","1"}};
			int columnIndex=0;
			if(level==0){//总部
				if("1".equals(type)){//明细
					title=null;
					title= new String[6][];
					columnIndex=0;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="序号";
					title[columnIndex] [1]="10";
					title[columnIndex] [2]="0";
					columnIndex++;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="商品编码";
					title[columnIndex] [1]="20";
					title[columnIndex] [2]="0";
					columnIndex++;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="商品名称";
					title[columnIndex] [1]="20";
					title[columnIndex] [2]="0";
					columnIndex++;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="商品规格";
					title[columnIndex] [1]="40";
					title[columnIndex] [2]="0";
					columnIndex++;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="数量";
					title[columnIndex] [1]="10";
					title[columnIndex] [2]="1";
					columnIndex++;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="分公司名称";
					title[columnIndex] [1]="20";
					title[columnIndex] [2]="0";
				}
			
			}else if(level==1){//分公司
				if("1".equals(type)){//明细
					title=null;
					title= new String[6][];
					columnIndex=0;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="序号";
					title[columnIndex] [1]="10";
					title[columnIndex] [2]="0";
					columnIndex++;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="商品编码";
					title[columnIndex] [1]="20";
					title[columnIndex] [2]="0";
					columnIndex++;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="商品名称";
					title[columnIndex] [1]="20";
					title[columnIndex] [2]="0";
					columnIndex++;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="商品规格";
					title[columnIndex] [1]="40";
					title[columnIndex] [2]="0";
					columnIndex++;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="数量";
					title[columnIndex] [1]="10";
					title[columnIndex] [2]="1";
					columnIndex++;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="营业处仓库";
					title[columnIndex] [1]="20";
					title[columnIndex] [2]="0";
				}
			}else {//营业处
				if("1".equals(type)){//明细
					title=null;
					title= new String[11][];
					columnIndex=0;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="序号";
					title[columnIndex] [1]="10";
					title[columnIndex] [2]="0";
					columnIndex++;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="商品编码";
					title[columnIndex] [1]="20";
					title[columnIndex] [2]="0";
					columnIndex++;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="商品名称";
					title[columnIndex] [1]="20";
					title[columnIndex] [2]="0";
					columnIndex++;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="商品规格";
					title[columnIndex] [1]="40";
					title[columnIndex] [2]="0";
					columnIndex++;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="数量";
					title[columnIndex] [1]="10";
					title[columnIndex] [2]="1";
					columnIndex++;
		         	title[columnIndex] =new String[3];
					title[columnIndex] [0]="操作类型";
					title[columnIndex] [1]="20";
					title[columnIndex] [2]="0";
					columnIndex++;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="单号";
					title[columnIndex] [1]="20";
					title[columnIndex] [2]="0";
					columnIndex++;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="经办人";
					title[columnIndex] [1]="20";
					title[columnIndex] [2]="0";
					columnIndex++;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="客户";
					title[columnIndex] [1]="20";
					title[columnIndex] [2]="0";
					columnIndex++;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="备注";
					title[columnIndex] [1]="20";
					title[columnIndex] [2]="0";
					columnIndex++;
					title[columnIndex] =new String[3];
					title[columnIndex] [0]="日期";
					title[columnIndex] [1]="20";
					title[columnIndex] [2]="0";
				}
			}
			List valueList = new ArrayList();
			Map<String, Object> dataValue = null;
			String[] values = null;
			int listLenth=result.size();
			
			for(int i=0; i<listLenth; i++){
				values = new String[5];
				dataValue = result.get(i);
				columnIndex=0;
				values[columnIndex]  = String.valueOf(i+1);
				columnIndex++;
				values[columnIndex] = dataValue.get("productCode")==null ? "" :dataValue.get("productCode").toString();
				columnIndex++;
				values[columnIndex] = dataValue.get("productName")==null ? "" :dataValue.get("productName").toString();
				columnIndex++;
				values[columnIndex]  = dataValue.get("norm")==null ? "" :dataValue.get("norm").toString();
				columnIndex++;
				values[columnIndex]  = dataValue.get("num")==null ? "" :dataValue.get("num").toString();

				if(level==0){//总部
					if("1".equals(type)){//明细
						values = new String[6];
						columnIndex=0;
						values[columnIndex]  = String.valueOf(i+1);
						columnIndex++;
						values[columnIndex] = dataValue.get("productCode")==null ? "" :dataValue.get("productCode").toString();
						columnIndex++;
						values[columnIndex] = dataValue.get("productName")==null ? "" :dataValue.get("productName").toString();
						columnIndex++;
						values[columnIndex]  = dataValue.get("norm")==null ? "" :dataValue.get("norm").toString();
						columnIndex++;
						values[columnIndex]  = dataValue.get("num")==null ? "" :dataValue.get("num").toString();
						columnIndex++;
						values[columnIndex]  = dataValue.get("companyName")==null ? "" :dataValue.get("companyName").toString();
					}
				}else if(level==1){//分公司
					if("1".equals(type)){//明细
						values = new String[6];
						columnIndex=0;
						values[columnIndex]  = String.valueOf(i+1);
						columnIndex++;
						values[columnIndex] = dataValue.get("productCode")==null ? "" :dataValue.get("productCode").toString();
						columnIndex++;
						values[columnIndex] = dataValue.get("productName")==null ? "" :dataValue.get("productName").toString();
						columnIndex++;
						values[columnIndex]  = dataValue.get("norm")==null ? "" :dataValue.get("norm").toString();
						columnIndex++;
						values[columnIndex]  = dataValue.get("num")==null ? "" :dataValue.get("num").toString();
						columnIndex++;
						values[columnIndex]  = dataValue.get("whsName")==null ? "" :dataValue.get("whsName").toString();
		
					}
				}else{//营业处
					if("1".equals(type)){//明细
						values = new String[11];
						columnIndex=0;
						values[columnIndex]  = String.valueOf(i+1);
						columnIndex++;
						values[columnIndex] = dataValue.get("productCode")==null ? "" :dataValue.get("productCode").toString();
						columnIndex++;
						values[columnIndex] = dataValue.get("productName")==null ? "" :dataValue.get("productName").toString();
						columnIndex++;
						values[columnIndex]  = dataValue.get("norm")==null ? "" :dataValue.get("norm").toString();
						columnIndex++;
						values[columnIndex]  = dataValue.get("num")==null ? "" :dataValue.get("num").toString();
						columnIndex++;
						values[columnIndex]  = dataValue.get("type")==null ? "" :dataValue.get("type").toString();
						columnIndex++;
						values[columnIndex]  = dataValue.get("stockCode")==null ? "" :dataValue.get("stockCode").toString();
						columnIndex++;
						values[columnIndex]  = dataValue.get("managersName")==null ? "" :dataValue.get("managersName").toString();
						columnIndex++;
						values[columnIndex]  = dataValue.get("channelName")==null ? "" :dataValue.get("channelName").toString();
						columnIndex++;
						values[columnIndex]  = dataValue.get("remark")==null ? "" :dataValue.get("remark").toString();
						columnIndex++;
						values[columnIndex] = dataValue.get("stamp")==null ? "" :dataValue.get("stamp").toString();
		
					}
				}
				
				valueList.add(values);
			}
			//合计
			//获得分公司中英文名称
			CommonCompany commonCompany =  openLdap.getCompanyById(companyId);
			 CreateExcel_PDF_CSV.createExcel(valueList, response, fileName, title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),true);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}  finally {
		} 
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出销售报表 结束");
		}
	}
	@RequestMapping(value = "/stock/exportExcel4Stockout")
	public void exportExcel4Stockout(HttpServletRequest request,HttpServletResponse response) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出出库信息 开始");
		}
		
		try {
			String[][] title = {{"序号","10"},{"出库单号","20"},{"类型","15"},{"出库仓库","15"},{"经办人","20"},{"客户","16"},{"入库仓库","26"},{"出库日期","16"},{"调拨是否完成","16"},{"费用","10"},{"收据票号","16"},{"备注","26"}};
			
			//条件
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
				
			}else if(level==1){//分公司
				map.put("companyId", companyId);
			}else{
				//根据分公司或者营业处id得到下面的所有仓库列表
				List<Long> wareHouseIds = new ArrayList<Long>();
				if(StringUtils.isNullOrEmpty(map.get("whsId"))){
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
			List<HashMap<String, Object>> results = stockService.findAllStockOuts(map);
			List valueList = new ArrayList();
			HashMap<String, Object> valueData = null;
			String[] values = null;
			int listLenth=results.size();
			int columnIndex=0;
			for(int i=0; i<listLenth; i++){
				values = new String[12];
				valueData = results.get(i);
				columnIndex=0;
				values[columnIndex]  = (i+1)+"";
				columnIndex++;
				values[columnIndex] = valueData.get("code") == null ? "" : valueData.get("code").toString();
				columnIndex++;
				values[columnIndex]  = WhsConst.STOCKOUT_TYPE.get(Integer.parseInt(valueData.get("type") == null ? "0" : valueData.get("type").toString()));
				columnIndex++;
				values[columnIndex]  = valueData.get("whsName") == null ? "" : valueData.get("whsName").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("managersName") == null ? "" : valueData.get("managersName").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("channelName") == null ? "" : valueData.get("channelName").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("inWhsName") == null ? "" : valueData.get("inWhsName").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("stamp") == null ? "" : valueData.get("stamp").toString();
				columnIndex++;
				values[columnIndex]  = WhsConst.STOCKOUT_ISCOMPLETED.get(Integer.parseInt(valueData.get("isCompleted") == null ? "0" : valueData.get("isCompleted").toString()));
				columnIndex++;
				values[columnIndex]  = valueData.get("money") == null ? "" : valueData.get("money").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("receiptNo") == null ? "" : valueData.get("receiptNo").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("remark") == null ? "" : valueData.get("remark").toString();
				valueList.add(values);
			}
			//获得分公司中英文名称
			CommonCompany commonCompany =  openLdap.getCompanyById(companyId);
	
			CreateExcel_PDF_CSV.createExcel(valueList, response, "出库信息", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出出库信息 结束");
		}
	}
	@RequestMapping(value = "/stock/exportExcel4Stockin")
	public void exportExcel4Stockin(HttpServletRequest request,HttpServletResponse response) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出入库信息 开始");
		}
		
		try {
			String[][] title = {{"序号","10"},{"入库单号","20"},{"类型","15"},{"订单号","18"},{"入库仓库","15"},{"经办人","20"},{"客户","16"},{"出库仓库","26"},{"人库日期","16"},{"备注","26"}};
			
			//条件
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
				
			}else if(level==1){//分公司
				map.put("companyId", companyId);
			}else{
				//根据分公司或者营业处id得到下面的所有仓库列表
				List<Long> wareHouseIds = new ArrayList<Long>();
				if(StringUtils.isNullOrEmpty(map.get("whsId"))){
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
			List<HashMap<String, Object>> results = stockService.findAllStockIns(map);
			List valueList = new ArrayList();
			HashMap<String, Object> valueData = null;
			String[] values = null;
			int columnIndex=0;
			int listLenth=results.size();
			for(int i=0; i<listLenth; i++){
				values = new String[10];
				valueData = results.get(i);
				columnIndex=0;
				values[columnIndex]  = (i+1)+"";
				columnIndex++;
				values[columnIndex] = valueData.get("code") == null ? "" : valueData.get("code").toString();
				columnIndex++;
				values[columnIndex]  = WhsConst.STOCKIN_TYPE.get(Integer.parseInt(valueData.get("type") == null ? "0" : valueData.get("type").toString()));
				columnIndex++;
				values[columnIndex]  = valueData.get("orderNo") == null ? "" : valueData.get("orderNo").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("whsName") == null ? "" : valueData.get("whsName").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("managersName") == null ? "" : valueData.get("managersName").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("channelName") == null ? "" : valueData.get("channelName").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("outWhsName") == null ? "" : valueData.get("outWhsName").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("stamp") == null ? "" : valueData.get("stamp").toString();
				columnIndex++;
				values[columnIndex] = valueData.get("remark") == null ? "" : valueData.get("remark").toString();
				valueList.add(values);
			}
			//获得分公司中英文名称
			CommonCompany commonCompany =  openLdap.getCompanyById(companyId);
	
			CreateExcel_PDF_CSV.createExcel(valueList, response, "入库信息", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出入库信息 结束");
		}
	}
	
	@RequestMapping(value = "/stock/exportExcel4StockAlarm")
	public void exportExcel4StockAlarm(HttpServletRequest request,HttpServletResponse response) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出库存预警信息 开始");
		}
		
		try {
			String[][] title = {{"序号","10"},{"商品编码","20"},{"商品名称","20"},{"商品规格","40"},{"商品编码","20"},{"库存数量","18"},{"最低库存","15"},{"最长积压时间(月)","20"},{"最后出库时间","20"},{"仓库","20"}};
			
			//条件
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
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			OpenLdap openLdap = OpenLdapManager.getInstance();
			if (StringUtils.isNotBlank(userId)) {
				//判断是否是分公司
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
			List<HashMap<String, Object>> results = stockService.findStockLtSetup(map);
			List valueList = new ArrayList();
			HashMap<String, Object> valueData = null;
			String[] values = null;
			int listLenth=results.size();
			int columnIndex=0;
			for(int i=0; i<listLenth; i++){
				values = new String[10];
				valueData = results.get(i);
				columnIndex=0;
				values[columnIndex]  = (i+1)+"";
				columnIndex++;
				values[columnIndex] = valueData.get("productCode") == null ? "" : valueData.get("productCode").toString();
				columnIndex++;
				values[columnIndex] = valueData.get("productName") == null ? "" : valueData.get("productName").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("norm") == null ? "" : valueData.get("norm").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("productCode") == null ? "" : valueData.get("productCode").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("num") == null ? "" : valueData.get("num").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("minStock") == null ? "" : valueData.get("minStock").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("overstockTime") == null ? "" : valueData.get("overstockTime").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("stamp") == null ? "" : valueData.get("stamp").toString();
				columnIndex++;
				values[columnIndex]  = valueData.get("whsName") == null ? "" : valueData.get("whsName").toString();
				valueList.add(values);
			}
			//获得分公司中英文名称
			CommonCompany commonCompany =  openLdap.getCompanyById(companyId);
	
			CreateExcel_PDF_CSV.createExcel(valueList, response, "库存预警信息", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出库存预警信息 结束");
		}
	}
	@RequestMapping(value = "/stock/exportExcel4BorrowDetailsTurn")
	public void exportExcel4BorrowDetailsTurn(HttpServletRequest request,HttpServletResponse response) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出挂账表(转移) 开始");
		}
		try {
			//条件
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
		    List<Long> wareHouseIds = new ArrayList<Long>();
			String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			OpenLdap openLdap = OpenLdapManager.getInstance();
			if(StringUtils.isNullOrEmpty(map.get("whsId"))){
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
			List<HashMap<String, Object>> result = stockService.findAllBorrowDetails(map);
			
			String fileName= "借用挂账转移表";
			String borrowTitle=null;
			String[][] title = {{"序号","10"},{"营业处仓库","15"},{"借用人","15"},{"代理商","20"},{"最终客户","20"},{"商品编码","20"},{"商品名称","20"},{"商品规格","40"},{"借用数量","10"},{"已还库数量","20"},{"已核销数量","20"},{"未还库数量","20"},{"日期","20"},{"目的经办人","16"},{"备注","20"}};
			List valueList = new ArrayList();
			Map<String, Object> dataValue = null;
			String[] values = null;
			int columnIndex=0;
			int listLenth=result.size();
			for(int i=0; i<listLenth; i++){
				values = new String[15];
				dataValue = result.get(i);
				columnIndex=0;
				values[columnIndex] = String.valueOf(i+1);
				columnIndex++;
				values[columnIndex] = dataValue.get("whsName")==null ? "" :dataValue.get("whsName").toString();
				columnIndex++;
				values[columnIndex] = dataValue.get("borrowerName")==null ? "" :dataValue.get("borrowerName").toString();
				columnIndex++;
				values[columnIndex] = dataValue.get("channelName")==null ? "" :dataValue.get("channelName").toString();
				columnIndex++;
				values[columnIndex] = dataValue.get("customerName")==null ? "" :dataValue.get("customerName").toString();
				columnIndex++;
				values[columnIndex] = dataValue.get("productCode")==null ? "" :dataValue.get("productCode").toString();
				columnIndex++;
				values[columnIndex] = dataValue.get("productName")==null ? "" :dataValue.get("productName").toString();
				columnIndex++;
				values[columnIndex] = dataValue.get("norm")==null ? "" :dataValue.get("norm").toString();
				columnIndex++;
				values[columnIndex] = dataValue.get("borrowNum")==null ? "" :dataValue.get("borrowNum").toString();
				columnIndex++;
				values[columnIndex] = dataValue.get("returnNum")==null ? "" :dataValue.get("returnNum").toString();
				columnIndex++;
				values[columnIndex] = dataValue.get("writeoffsNum")==null ? "" :dataValue.get("writeoffsNum").toString();
				columnIndex++;
				values[columnIndex] = dataValue.get("num")==null ? "" :dataValue.get("num").toString();
				columnIndex++;
				values[columnIndex] = dataValue.get("stamp")==null ? "" :dataValue.get("stamp").toString();
				columnIndex++;
				values[columnIndex] = dataValue.get("toBorrowerName")==null ? "" :dataValue.get("toBorrowerName").toString();
				columnIndex++;
				values[columnIndex] =dataValue.get("remark")==null ? "" :dataValue.get("remark").toString();				
				valueList.add(values);
			}
			//获得分公司中英文名称
			CommonCompany commonCompany =  openLdap.getCompanyById(companyId);
			 CreateExcel_PDF_CSV.createExcel(valueList, response, fileName, title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}  finally {
		} 
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出挂账表(转移) 结束");
		}
	}
}
