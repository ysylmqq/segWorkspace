package com.gboss.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ldap.objectClasses.CommonCompany;
import ldap.objectClasses.CommonOperator;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;
import ldap.util.Config;
import ldap.util.IOUtil;

import org.apache.commons.io.FileUtils;
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
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;
import org.springframework.web.multipart.MultipartFile;

import com.gboss.comm.SelConst;
import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.comm.WhsConst;
import com.gboss.pojo.SalesPack;
import com.gboss.pojo.SalesProduct;
import com.gboss.pojo.Salescontract;
import com.gboss.pojo.Supplycontracts;
import com.gboss.service.ChannelService;
import com.gboss.service.SalesContractService;
import com.gboss.util.DateUtil;
import com.gboss.util.JsonUtil;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.excel.CreateExcel_PDF_CSV;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.controller
 * @ClassName:SalesContractController
 * @Description:销售合同控制层
 * @author:zfy
 * @date:2013-8-27 上午9:47:25
 */
@Controller
public class SalesContractController extends BaseController{
	protected static final Logger LOGGER = LoggerFactory.getLogger(SalesContractController.class);
	
	@Autowired
	@Qualifier("salesContractService")
	private SalesContractService salesContractService;
	@Autowired
	@Qualifier("channelService")
	private ChannelService channelService;
	
	private ObjectMapper  objectMapper = new ObjectMapper();
	private JsonGenerator jsonGenerator = null;
	
	@RequestMapping(value = "/sell/addSalesContract")
	@LogOperation(description = "添加销售合同", type =  SystemConst.OPERATELOG_ADD, model_id = 2)
	public @ResponseBody HashMap<String, Object> addSalesContract(@RequestBody Salescontract salescontract,HttpServletRequest request) throws SystemException{
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("添加销售合同 开始");
		}
		HashMap<String, Object> resultMap =null;;
		boolean flag=true;
		String msg=SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(salescontract,true));
			
			if(LOGGER.isDebugEnabled()){
				jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(salescontract);
			}
	        //入库
	        String userId=(String)request.getSession().getAttribute(SystemConst.ACCOUNT_ID);
	        String userName=(String)request.getSession().getAttribute(SystemConst.ACCOUNT_USERNAME);
			String orgId=(String)request.getSession().getAttribute(SystemConst.ACCOUNT_ORGID);	
			String companyId=(String)request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
			if(salescontract!=null){
				if(StringUtils.isNotBlank(userId)){
					salescontract.setUserId(Long.valueOf(userId));
					salescontract.setUserName(userName);
				}
				if(StringUtils.isNotBlank(orgId)){
					salescontract.setOrgId(Long.valueOf(orgId));
				}
				if(StringUtils.isNotBlank(companyId)){
					salescontract.setCompanyId(Long.valueOf(companyId));
				}
			}
			//设置机构、分公司name
			OpenLdap openLdap = OpenLdapManager.getInstance();
			if(StringUtils.isNotBlank(companyId)){
				CommonCompany commonCompany=openLdap.getCompanyById(companyId);
				if(commonCompany!=null){
					salescontract.setCompanyName(commonCompany.getCompanyname());
				}
			}
			if(StringUtils.isNotBlank(orgId)){
				CommonCompany commonCompany=openLdap.getCompanyById(orgId);
				if(commonCompany!=null){
					salescontract.setOrgName(commonCompany.getCompanyname());
				}
			}
			resultMap=salesContractService.addSalesContract(salescontract);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(),e);
			flag = false;
			msg = SystemConst.OP_FAILURE;
			resultMap = new HashMap<String, Object>();
			resultMap.put(SystemConst.SUCCESS, flag);
			resultMap.put(SystemConst.MSG, msg);
			e.printStackTrace();
		}finally{
			//IOUtil.closeIS(is);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("添加销售合同 结束");
		}
		return resultMap;
	}
	@RequestMapping(value = "/sell/updateSalesContract")
	@LogOperation(description = "修改销售合同", type =  SystemConst.OPERATELOG_UPDATE, model_id = 2)
	public @ResponseBody HashMap<String, Object> updateSalesContract(@RequestBody Salescontract salescontract,HttpServletRequest request) throws SystemException{
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("修改销售合同 开始");
		}
		HashMap<String, Object> resultMap=new HashMap<String, Object>();
		boolean flag=true;
		String msg=SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(salescontract,true));
			
			int result=1;
			if(LOGGER.isDebugEnabled()){
				jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(salescontract);
			}
	        //String userId=(String)request.getSession().getAttribute(SystemConst.ACCOUNT_ID);
	        //String userName=(String)request.getSession().getAttribute(SystemConst.ACCOUNT_USERNAME);
			String orgId=(String)request.getSession().getAttribute(SystemConst.ACCOUNT_ORGID);	
			String companyId=(String)request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
			if(salescontract!=null){
				/*if(StringUtils.isNotBlank(userId)){
					salescontract.setUserId(Long.valueOf(userId));
					salescontract.setUserName(userName);
				}*/
				if(StringUtils.isNotBlank(orgId)){
					salescontract.setOrgId(Long.valueOf(orgId));
				}
				if(StringUtils.isNotBlank(companyId)){
					salescontract.setCompanyId(Long.valueOf(companyId));
				}
			}
			//设置机构、分公司name
			OpenLdap openLdap = OpenLdapManager.getInstance();
			if(StringUtils.isNotBlank(companyId)){
				CommonCompany commonCompany=openLdap.getCompanyById(companyId);
				if(commonCompany!=null){
					salescontract.setCompanyName(commonCompany.getCompanyname());
				}
			}
			if(StringUtils.isNotBlank(orgId)){
				CommonCompany commonCompany=openLdap.getCompanyById(orgId);
				if(commonCompany!=null){
					salescontract.setOrgName(commonCompany.getCompanyname());
				}
			}
			result=salesContractService.updateSalesContract(salescontract);
			if(result==-1){
				flag=false;
				msg="参数不合法";
			}else if(result==0){
				flag=false;
				msg="要操作的对象不存在";
			}else if(result==2){
				flag=false;
				msg="销售合同编号为["+salescontract.getCode()+"]的已存在";
			}else if(result==3){
				flag=false;
				msg="销售合同名称为["+salescontract.getName()+"]的已存在";
			}
		        
		} catch (IOException e) {
			LOGGER.error(e.getMessage(),e);
			flag=false;
			msg=SystemConst.OP_FAILURE;
			e.printStackTrace();
		}finally{
			//IOUtil.closeIS(is);
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("修改销售合同 结束");
		}
		return resultMap;
	}
	@RequestMapping(value = "/sell/findSalesContracts")
	public @ResponseBody Page<HashMap<String, Object>> findSalesContracts(@RequestBody PageSelect<HashMap<String, Object>> pageSelect, BindingResult bindingResult,HttpServletRequest request) throws SystemException{
		Page<HashMap<String, Object>> result=null;
		try {
			//String orgId=(String)request.getSession().getAttribute(SystemConst.ACCOUNT_ORGID);	
			String companyId=(String)request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
			if(pageSelect!=null){
				Map conditionMap=pageSelect.getFilter();
				if(conditionMap==null){
					conditionMap=new HashMap();
				}
				List<String> roleids = (List<String>)request.getSession().getAttribute(SystemConst.ACCOUNT_ROLEIDS);
				String userId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ID);
				OpenLdap openLdap = OpenLdapManager.getInstance();
				if(StringUtils.isNotNullOrEmpty(roleids)){
				// 根据用户角色ID判断 用户是销售经理、部门经理、销售总监
					if (roleids.contains(SystemConst.ROLEID_SALES_MANAGER)) {// 销售经理
						conditionMap.put("userId",userId);
					} else{// 部门经理、销售总监
						CommonOperator user=openLdap.getOperatorById(userId);
						if(user!=null){
							conditionMap.put("orgIds",user.getRolecompanynos());
						}else{
							conditionMap.put("userId",userId);
						}
					}
				}
				conditionMap.put("companyId", companyId);
				//conditionMap.put("orgId", orgId);
				
			}
			result= salesContractService.findSalesContracts(pageSelect);
		} catch (Exception e) {
			//打印
			e.printStackTrace();
			//同时把异常抛到前台
			throw new SystemException();
		}
		return result;
	}
	@RequestMapping(value = "/sell/findSalesContractProducts")
	public @ResponseBody Page<HashMap<String, Object>> findSalesContractProducts(@RequestBody PageSelect<HashMap<String, Object>> pageSelect, BindingResult bindingResult,HttpServletRequest request) throws SystemException{
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询销售合同商品价格开始");
		}
		Page<HashMap<String, Object>> result=null;
		try {
			String companyId=(String)request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
			if(pageSelect!=null){
				Map conditionMap=pageSelect.getFilter();
				if(conditionMap==null){
					conditionMap=new HashMap();
				}
				conditionMap.put("companyId", companyId);
				//conditionMap.put("orgId", orgId);
			}
			result= salesContractService.findSalesContractProducts(pageSelect);
		} catch (Exception e) {
			//打印
			e.printStackTrace();
			//同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询销售合同商品价格 结束");
		}
		return result;
	}
	@RequestMapping(value = "/sell/getSalesContract")
	public @ResponseBody Salescontract getSalesContract(@RequestBody Salescontract salescontract, BindingResult bindingResult,HttpServletRequest request) throws SystemException{
		Salescontract result=null;
		try {
			result=salesContractService.getSalesContract(salescontract.getId());
		} catch (Exception e) {
			//打印
			e.printStackTrace();
			//同时把异常抛到前台
			throw new SystemException();
		}
		return result;
	}
	@RequestMapping(value = "/sell/findSalesProducts")
	public @ResponseBody List<HashMap<String,Object>> findSalesProducts(@RequestBody SalesProduct salesProduct, BindingResult bindingResult,HttpServletRequest request) throws SystemException{
		List<HashMap<String,Object>> result=null;
		try {
			result=salesContractService.findSalesProductsByContractId(salesProduct.getContractId());
		} catch (Exception e) {
			//打印
			e.printStackTrace();
			//同时把异常抛到前台
			throw new SystemException();
		}
		return result;
	}
	@RequestMapping(value = "/sell/findSalesPacks")
	public @ResponseBody List<HashMap<String,Object>> findSalesPacks(@RequestBody SalesPack salesPack, BindingResult bindingResult,HttpServletRequest request) throws SystemException{
		List<HashMap<String,Object>> result=null;
		try {
			result=salesContractService.findSalesPacksByContractId(salesPack.getContractId());
		} catch (Exception e) {
			//打印
			e.printStackTrace();
			//同时把异常抛到前台
			throw new SystemException();
		}
		return result;
	}
	
	@RequestMapping(value = "/sell/getSalesContractNo")
	public @ResponseBody String getSupplyContractNo(HttpServletRequest request) throws SystemException {
		String results = null;
		try {
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			results=salesContractService.getSalesContractNo(companyId==null?null:Long.valueOf(companyId),userId==null?null:Long.valueOf(userId));
		} catch (Exception e) {
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return results;
	}
	
	@RequestMapping(value = "/sell/updateSalesContractDetail")
	@LogOperation(description = "修改销售合同商品明细", type = SystemConst.OPERATELOG_UPDATE, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> updateSalesContractDetail(
			@RequestBody Salescontract salesProduct,
			HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("修改销售合同商品明细开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(salesProduct,true));
			
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(salesProduct);
			}
			if (salesProduct != null) {
				int result = salesContractService
						.updateDetails(salesProduct.getId(),salesProduct.getSalesProducts());
				if (result == -1) {
					flag = false;
					msg = "参数不合法";
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
			LOGGER.debug("修改销售合同商品明细 结束");
		}
		return resultMap;
	}
	@RequestMapping(value = "/sell/updateSalesContractStatus")
	@LogOperation(description = "修改销售合同状态", type = SystemConst.OPERATELOG_UPDATE, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> updateStatus(
			@RequestBody Salescontract salescontract,
			HttpServletRequest request) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(salescontract,true));
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			salescontract.setCheckUserId(userId==null?null:Long.valueOf(userId));
			salescontract.setStatus(1);//有效
		/*	String userName = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_USERNAME);
			salescontract.setUserId(Long.valueOf(userId));
			salescontract.setUserName(userName);*/
			salesContractService.updateStatus(salescontract);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg = SystemConst.OP_FAILURE;
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		return resultMap;
	}
	@RequestMapping(value = "/sell/updateSalesContractFiling")
	@LogOperation(description = "修改销售合同归档状态", type = SystemConst.OPERATELOG_UPDATE, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> updateSalesContractFiling(
			@RequestBody Salescontract salescontract,
			HttpServletRequest request) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(salescontract,true));
			salescontract.setIsFiling(1);//已归档
			/*String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			String userName = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_USERNAME);
			salescontract.setUserId(Long.valueOf(userId));
			salescontract.setUserName(userName);*/
			salesContractService.updateFillingById(salescontract);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg = SystemConst.OP_FAILURE;
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		return resultMap;
	}
	
	@RequestMapping(value = "/sell/exportSalesContract")
	public @ResponseBody
	void exportSalesContract(
			HttpServletRequest request, HttpServletResponse response)
			throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出excel 开始");
		}
		List<HashMap<String, Object>> list = null;
		try {
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

		    List<String> roleids = (List<String>)request.getSession().getAttribute(SystemConst.ACCOUNT_ROLEIDS);
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			String companyId=(String)request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
			OpenLdap openLdap = OpenLdapManager.getInstance();
			if(StringUtils.isNotNullOrEmpty(roleids)){
				// 根据用户角色ID判断 用户是销售经理、部门经理、销售总监
				if (roleids.contains(SystemConst.ROLEID_SALES_MANAGER)) {// 销售经理
					map.put("userId",userId);
				} else{// 部门经理、销售总监
					CommonOperator user=openLdap.getOperatorById(userId);
					if(user!=null){
						map.put("orgIds",user.getRolecompanynos());
					}else{
						map.put("userId",userId);
					}
				}
			}
			map.put("companyId", companyId);
			//conditionMap.put("orgId", orgId);
			
			list = salesContractService.findAllSalesContracts(map);
			String[][] title = {{"序号","10"},{"合同名称","18"},{"代理商名称","19"},{"代理商联系电话","20"},{"签约人","15"},{"合同签订日期","20"},{"合同生效日期","20"},{"合同到期日期","20"},{"是否生效","16"},{"是否归档","16"},{"创建人","15"}};
			List valueList = new ArrayList();
			HashMap<String, Object> dataMap = null;
			String[] values = null;
			int listLenth=list.size();
			int columnIndex=0;
			int titleLength=title.length;
			for(int i=0; i<listLenth; i++){
        		values = new String[titleLength];
				dataMap = list.get(i);
				columnIndex=0;
				values[columnIndex] = String.valueOf(i+1);
				columnIndex++;
				values[columnIndex] = dataMap.get("name") == null ?"":dataMap.get("name").toString();
				columnIndex++;
				values[columnIndex]= dataMap.get("channelName") == null ?"":dataMap.get("channelName").toString();
				columnIndex++;
				values[columnIndex] = dataMap.get("phone") == null ?"":dataMap.get("phone").toString();
				columnIndex++;
				values[columnIndex] = dataMap.get("contractorName") == null ?"":dataMap.get("contractorName").toString();
				columnIndex++;
				values[columnIndex] = dataMap.get("signDate") == null ?"":dataMap.get("signDate").toString();
				columnIndex++;
				values[columnIndex] = dataMap.get("validDate") == null ?"":dataMap.get("validDate").toString();
				columnIndex++;
				values[columnIndex] = dataMap.get("matureDate") == null ?"":dataMap.get("matureDate").toString();
				columnIndex++;
				values[columnIndex] = SelConst.SALESCONTRACT_STATUS.get(dataMap.get("status") == null ?0:Integer.parseInt(dataMap.get("status").toString()));
				columnIndex++;
				values[columnIndex] = SelConst.SALESCONTRACT_IS_FILING.get(dataMap.get("isFiling") == null ?0:Integer.parseInt(dataMap.get("isFiling").toString()));
				columnIndex++;
				values[columnIndex] = dataMap.get("userName") == null ?"":dataMap.get("userName").toString();
				valueList.add(values);
			}
			//获得分公司中英文名称
        	CommonCompany commonCompany =openLdap.getCompanyById(companyId);
	
			CreateExcel_PDF_CSV.createExcel(valueList, response, "销售合同", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		} catch (Exception e) {
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出excel 结束");
		}
	}
	
	@RequestMapping(value = "/sell/deleteSalesContracts")
	@LogOperation(description = "删除销售合同", type = SystemConst.OPERATELOG_DEL, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> deleteSalesContracts(
			@RequestBody List<Long> list,
			HttpServletRequest request) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
				//将参数添加到日志中
				request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(list,true));
			
				int result = salesContractService.deleteSalesContracts(list);
				if (result == -1) {
					flag = false;
					msg = "参数不正确";
				}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg = SystemConst.OP_FAILURE;
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		return resultMap;
	}
	
	@RequestMapping(value = "/sell/getSalePriceByProductId")
	public @ResponseBody SalesProduct getSalesProductByProductId(@RequestParam(required=false) Long productId,@RequestParam(value="channelId",required=false) Long channelId,
			HttpServletRequest request) {
		SalesProduct salesProduct=null;
		try {
			String companyId=(String)request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
			//如果代理商为空,表示查询的为自身营业厅的代理商
			if(channelId==null){
				Map<String,Object> map=new HashMap<String, Object>();
				String orgId=(String)request.getSession().getAttribute(SystemConst.ACCOUNT_ORGID);	
				map.put("orgId", orgId);
				map.put("companyId", companyId);
				map.put("dictId", SystemConst.DICT_CHANNEL_SELF);
				HashMap<String, Object> channelMap=channelService.getChannel4Self(map);
				if(channelMap!=null){
					channelId=channelMap.get("id")==null?null:Long.valueOf(channelMap.get("id").toString());
				}
			}
			salesProduct = salesContractService.getSalesProductByProductId(productId,channelId,companyId==null?null:Long.valueOf(companyId));
			//再得到销售合同的名称
			if(salesProduct!=null){
				Salescontract salescontract=salesContractService.get(Salescontract.class,salesProduct.getContractId());
				if(salescontract!=null){
					salesProduct.setContractName(salescontract.getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
		}
		if(salesProduct==null){
			salesProduct=new SalesProduct();
		}
		return salesProduct;
	}
}

