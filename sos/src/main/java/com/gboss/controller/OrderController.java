package com.gboss.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import ldap.objectClasses.CommonCompany;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.comm.WhsConst;
import com.gboss.pojo.CustomerAddress;
import com.gboss.pojo.Order;
import com.gboss.pojo.OrderDetails;
import com.gboss.pojo.Product;
import com.gboss.pojo.Supplycontracts;
import com.gboss.service.OrderService;
import com.gboss.util.DateUtil;
import com.gboss.util.JsonUtil;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.excel.CreateExcel_PDF_CSV;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.controller
 * @ClassName:OrderController
 * @Description:订单控制类
 * @author:zfy
 * @date:2013-11-5 下午5:27:47
 */
@Controller
public class OrderController extends BaseController {
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(OrderController.class);

	@Autowired
	@Qualifier("orderService")
	private OrderService orderService;

	@RequestMapping(value = "/order/findOrdersByPage")
	public @ResponseBody Page<HashMap<String, Object>> findOrdersByPage(@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询订单 开始");
		}
		Page<HashMap<String, Object>> result = null;
		try {
			if (pageSelect != null) {
				if (pageSelect.getFilter() == null) {
					pageSelect.setFilter(new HashMap<String, Object>());
				}
				OpenLdap openLdap = OpenLdapManager.getInstance();
				String userId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ID);
				String companyId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_COMPANYID);
				//判断是否是分公司
				int level=openLdap.getUserCompanyLevel( (String) request.getSession().getAttribute(
							SystemConst.ACCOUNT_ORGID));
				if(level==0){//总部
					if (StringUtils.isNotBlank(userId)) {
						pageSelect.getFilter().put("receiptId",userId);
					}
				}else if(level==1){//分公司
					if (StringUtils.isNotBlank(companyId)) {
						pageSelect.getFilter().put("companyId", companyId);
					}
					
				}else{//营业处
					//根据分公司或者营业处id得到下面的所有仓库列表
					if(StringUtils.isNullOrEmpty(pageSelect.getFilter().get("whsId"))){
						List<Long> wareHouseIds = new ArrayList<Long>();
						/*String orgId = (String) request.getSession().getAttribute(
								SystemConst.ACCOUNT_ORGID);*/
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
			result = orderService.findOrdersByPage(pageSelect);
		
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询订单 结束");
		}
		return result;
	}
	
	@RequestMapping(value = "/order/addOrder")
	@LogOperation(description = "添加订单", type = SystemConst.OPERATELOG_ADD, model_id = 2)
	public @ResponseBody
	 HashMap<String, Object> addOrder(
			@RequestBody Order order,
			final HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("添加订 开始");
		}
		HashMap<String, Object> resultMap = null;
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(order,true));
		
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			String userName = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_USERNAME);
			String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			OpenLdap openLdap = OpenLdapManager.getInstance();
			if (StringUtils.isNotBlank(userId)) {
				order.setUserId(Long.valueOf(userId));
				order.setUserName(userName);
				//获得当前登录人==仓管员所管理的仓库
				if(StringUtils.isNullOrEmpty(order.getWhsId())){
					/*CommonCompany commonCompany = openLdap.getWarehouseByUserId(userId);
					if(commonCompany!=null) {
						if(StringUtils.isNotBlank(commonCompany.getCompanyno())){
							order.setWhsId(Long.valueOf(commonCompany.getCompanyno()));
						}
						order.setWhsName(commonCompany.getCompanyname());
					}*/
					List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
					for (CommonCompany commonCompany2 : wareHouseList) {
						order.setWhsId(StringUtils.isNullOrEmpty(commonCompany2.getCompanyno())?null:Long.valueOf(commonCompany2.getCompanyno()));
						order.setWhsName(StringUtils.isNullOrEmpty(commonCompany2.getCompanyname())?null:commonCompany2.getCompanyname());
						break;
					}
				}
			}
			if (StringUtils.isNotBlank(companyId)) {
				order.setCompanyId(Long.valueOf(companyId));
				//设置机构、分公司name
				CommonCompany commonCompany=openLdap.getCompanyById(companyId);
				if(commonCompany!=null){
					order.setCompanyName(commonCompany.getCompanyname());
				}
			}
			//部门
			if(StringUtils.isNotBlank(orgId)){
				order.setOrgId(Long.valueOf(orgId));
				CommonCompany commonCompany=openLdap.getCompanyById(orgId);
				if(commonCompany!=null){
					order.setOrgName(commonCompany.getCompanyname());
				}
			}
			resultMap = orderService.addOrder(order);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg = e.getMessage();
			resultMap = new HashMap<String, Object>();
			resultMap.put(SystemConst.SUCCESS, flag);
			resultMap.put(SystemConst.MSG, msg);
			e.printStackTrace();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("添加订 开始");
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/order/updateOrder")
	@LogOperation(description = "修改订单", type = SystemConst.OPERATELOG_UPDATE, model_id = 2)
	public @ResponseBody
	 HashMap<String, Object> updateOrder(
			@RequestBody Order order,
			final HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("修改订单 开始");
		}
		HashMap<String, Object> resultMap = null;
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(order,true));
		
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			String userName = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_USERNAME);
			String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			OpenLdap openLdap = OpenLdapManager.getInstance();
			if (StringUtils.isNotBlank(userId)) {
				order.setUserId(Long.valueOf(userId));
				order.setUserName(userName);
				//获得当前登录人==仓管员所管理的仓库
				if(StringUtils.isNullOrEmpty(order.getWhsId())){
					/*CommonCompany commonCompany = openLdap.getWarehouseByUserId(userId);
					if(commonCompany!=null) {
						if(StringUtils.isNotBlank(commonCompany.getCompanyno())){
							order.setWhsId(Long.valueOf(commonCompany.getCompanyno()));
						}
						order.setWhsName(commonCompany.getCompanyname());
					}*/
					List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
					for (CommonCompany commonCompany2 : wareHouseList) {
						order.setWhsId(StringUtils.isNullOrEmpty(commonCompany2.getCompanyno())?null:Long.valueOf(commonCompany2.getCompanyno()));
						order.setWhsName(StringUtils.isNullOrEmpty(commonCompany2.getCompanyname())?null:commonCompany2.getCompanyname());
						break;
					}
				}
			}
			if (StringUtils.isNotBlank(companyId)) {
				order.setCompanyId(Long.valueOf(companyId));
				//设置机构、分公司name
				CommonCompany commonCompany=openLdap.getCompanyById(companyId);
				if(commonCompany!=null){
					order.setCompanyName(commonCompany.getCompanyname());
				}
			}
			//部门
			if(StringUtils.isNotBlank(orgId)){
				order.setOrgId(Long.valueOf(orgId));
				CommonCompany commonCompany=openLdap.getCompanyById(orgId);
				if(commonCompany!=null){
					order.setOrgName(commonCompany.getCompanyname());
				}
			}
			resultMap = orderService.updateOrder(order);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg = e.getMessage();
			resultMap = new HashMap<String, Object>();
			resultMap.put(SystemConst.SUCCESS, flag);
			resultMap.put(SystemConst.MSG, msg);
			e.printStackTrace();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("修改订单 开始");
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/order/addCustomerAddress")
	@LogOperation(description = "添加客户联系地址", type = SystemConst.OPERATELOG_ADD, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> addCustomerAddress(
			@RequestBody CustomerAddress customerAddress, HttpServletRequest request)
			throws SystemException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(customerAddress,true));
			
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			//设置机构、分公司name
			OpenLdap openLdap = OpenLdapManager.getInstance();
			CommonCompany commonCompany=openLdap.getCompanyById(companyId);
			if(commonCompany!=null){
				customerAddress.setCompanyName(commonCompany.getCompanyname());
			}
			customerAddress.setCompanyId(companyId==null?null:Long.valueOf(companyId));
			int result  = orderService.addCustomerAddress(customerAddress);
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
		return resultMap;
	}

	@RequestMapping(value = "/order/updateCustomerAddress")
	@LogOperation(description = "修改用户联系地址 ", type = SystemConst.OPERATELOG_ADD, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> updateCustomerAddress(
			@RequestBody CustomerAddress customerAddress, HttpServletRequest request)
			throws SystemException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(customerAddress,true));
			
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			//设置机构、分公司name
			OpenLdap openLdap = OpenLdapManager.getInstance();
			CommonCompany commonCompany=openLdap.getCompanyById(companyId);
			if(commonCompany!=null){
				customerAddress.setCompanyName(commonCompany.getCompanyname());
			}
			customerAddress.setCompanyId(companyId==null?null:Long.valueOf(companyId));
			int result  = orderService.updateCustomerAddress(customerAddress);
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
		return resultMap;
	}
	
	@RequestMapping(value = "/order/updateCustomAddressIsDefault")
	@LogOperation(description = "设置默认地址 ", type = SystemConst.OPERATELOG_UPDATE, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> updateCustomAddressIsDefault(
			@RequestBody CustomerAddress customerAddress, HttpServletRequest request)
			throws SystemException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(customerAddress,true));
			
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			customerAddress.setCompanyId(companyId==null?null:Long.valueOf(companyId));
			int result  = orderService.updateCustomAddressIsDefault(customerAddress.getId(),companyId==null?null:Long.valueOf(companyId));
			if (result == -1) {
				flag = false;
				msg = "参数不合法!";
			} else if (result == 0) {
				flag = false;
				msg = "该地址不存在,操作失败!";
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
	@RequestMapping(value = "/order/deleteCustomerAddresss")
	@LogOperation(description = "批量删除用户联系地址 ", type =  SystemConst.OPERATELOG_DEL, model_id = 2)
	public @ResponseBody HashMap<String, Object> deleteCustomerAddresss(@RequestBody List<Long> list,HttpServletRequest request) throws SystemException{
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(list,true));
			
			int result = orderService.deleteCustomerAddresss(list);
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
		return resultMap;
	}
	
	@RequestMapping(value = "/order/findCustomerAddress")
	public @ResponseBody
	List<HashMap<String, Object>> findCustomerAddress(@RequestBody Map<String,Object> conditionMap,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询用户联系地址 开始");
		}
		List<HashMap<String, Object>> result = null;
		try {
			if (conditionMap == null) {
				conditionMap = new HashMap();
			}
			OpenLdap openLdap = OpenLdapManager.getInstance();
			//判断是否是分公司
			int level=openLdap.getUserCompanyLevel( (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ORGID));
			if(level==0){//总部
				
			}else{//分公司或者营业处
				String companyId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_COMPANYID);
				if (StringUtils.isNotBlank(companyId)) {
					conditionMap.put("companyId",companyId);
				}
			}
			
			result = orderService.findCustomerAddress(conditionMap);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询用户联系地址 结束");
		}
		return result;
	}
	
	@RequestMapping(value = "/order/findOrderDetailsByOrderId")
	public @ResponseBody
	List<HashMap<String,Object>> findOrderDetailsByOrderId(@RequestBody OrderDetails orderDetails,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("根据订单id查询订单明细 开始");
		}
		List<HashMap<String,Object>> result = null;
		try {
			result = orderService.findOrderDetailsByOrderId(orderDetails.getOrderId());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("根据订单id查询订单明细 结束");
		}
		return result;
	}
	
	@RequestMapping(value = "/order/deleteOrders")
	@LogOperation(description = "批量删除订单 ", type =  SystemConst.OPERATELOG_DEL, model_id = 2)
	public @ResponseBody HashMap<String, Object> deleteOrders(@RequestBody List<Long> list,HttpServletRequest request) throws SystemException{
		HashMap<String, Object> resultMap = null;;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(list,true));
			
			resultMap= orderService.deleteOrders(list);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			resultMap = new HashMap<String, Object>();
			resultMap.put(SystemConst.SUCCESS, false);
			resultMap.put(SystemConst.MSG, StringUtils.isBlank(e.getMessage())?SystemConst.OP_FAILURE:e.getMessage());
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/order/getOrderNo")
	public @ResponseBody String getOrderNo(HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得订单号 开始");
		}
		String results = null;
		try {
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			results=orderService.getOrderNo(companyId==null?null:Long.valueOf(companyId),userId==null?null:Long.valueOf(userId));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得订单号 结束");
		}
		return results;
	}
	
	@RequestMapping(value = "/order/getPriceByProductId")
	public @ResponseBody Float getPriceByProductId(Long productId,HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得商品采购合同中的价格 开始");
		}
		Float results = null;
		try {
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			results=orderService.getPriceByProductId(companyId==null?null:Long.valueOf(companyId), productId,DateUtil.formatToday(),1);
		} catch (Exception e) {
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得商品采购合同中的价格  结束");
		}
		return results;
	}
	

	@RequestMapping(value = "/order/updateStatus")
	@LogOperation(description = "修改订单状态", type = SystemConst.OPERATELOG_UPDATE, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> updateStatus(
			@RequestBody Order order,
			HttpServletRequest request) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(order,true));
			//设置为有效
			order.setStatus(1);
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			order.setCheckUserId(userId==null?null:Long.valueOf(userId));
			orderService.updateStatus(order);
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
	
	@RequestMapping(value = "/order/exportOrders")
	public @ResponseBody
	void exportOrders(
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

		    OpenLdap openLdap = OpenLdapManager.getInstance();
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			//判断是否是分公司
			int level=openLdap.getUserCompanyLevel( (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ORGID));
			if(level==0){//总部
				String userId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ID);
				if (StringUtils.isNotBlank(userId)) {
					map.put("receiptId",userId);
				}
			}else{//分公司或者营业处
				if (StringUtils.isNotBlank(companyId)) {
					map.put("companyId", companyId);
				}
			}
			list = orderService.findAllOrders(map);
			/*String[] title = new String[7];
			title[0] = "序号";
			title[1] = "订单号";//orderNo
			title[2] = "订单接收人";//receiptName
			title[3] = "总金额(元)";//totalPrice
			title[4] = "订单日期";//stamp
			title[5] = "备注";//remark
			title[6] = "是否生效";//status
*/			String[][] title = {{"序号","10"},{"订单号","20"},{"订单接收人","20"},{"总金额(元)","20"},{"订单日期","20"},{"备注","30"},{"是否生效","16"},{"是否完成","16"}};
			if(level==0){//总部
				title=null;
				title= new String[7][];
				title[0]=new String[3];
				title[0][0]="序号";
				title[0][1]="10";
				title[0][2]="0";
				title[1]=new String[3];
				title[1][0]="订单号";
				title[1][1]="20";
				title[1][2]="0";
				title[2]=new String[3];
				title[2][0]="订单接收人";
				title[2][1]="20";
				title[2][2]="0";
				title[3]=new String[3];
				title[3][0]="总金额(元)";
				title[3][1]="20";
				title[3][2]="0";
				title[4]=new String[3];
				title[4][0]="订单日期";
				title[4][1]="20";
				title[4][2]="0";
				title[5]=new String[3];
				title[5][0]="备注";
				title[5][1]="30";
				title[5][2]="1";
				title[6]=new String[3];
				title[6][0]="是否完成";
				title[6][1]="16";
				title[6][2]="1";
			}
			List valueList = new ArrayList();
			HashMap<String, Object> dataMap = null;
			String[] values = null;
			int listLenth=list.size();
			for(int i=0; i<listLenth; i++){
				if(level==0){//总部
	        		values = new String[7];
					dataMap = list.get(i);
					values[0] = String.valueOf(i+1);
					values[1] = dataMap.get("orderNo") == null ?"":dataMap.get("orderNo").toString();
					values[2] = dataMap.get("receiptName") == null ?"":dataMap.get("receiptName").toString();
					values[3] = dataMap.get("totalPrice") == null ?"":dataMap.get("totalPrice").toString();
					values[4] = dataMap.get("stamp") == null ?"":dataMap.get("stamp").toString();
					values[5] = dataMap.get("remark") == null ?"":dataMap.get("remark").toString();
					values[6] = WhsConst.ORDER_IS_COMPLETED.get(dataMap.get("isCompleted") == null ?0:Integer.parseInt(dataMap.get("isCompleted").toString()));
				}else{
					values = new String[8];
					dataMap = list.get(i);
					values[0] = String.valueOf(i+1);
					values[1] = dataMap.get("orderNo") == null ?"":dataMap.get("orderNo").toString();
					values[2] = dataMap.get("receiptName") == null ?"":dataMap.get("receiptName").toString();
					values[3] = dataMap.get("totalPrice") == null ?"":dataMap.get("totalPrice").toString();
					values[4] = dataMap.get("stamp") == null ?"":dataMap.get("stamp").toString();
					values[6] = WhsConst.ORDER_STATUS.get(dataMap.get("status") == null ?0:Integer.parseInt(dataMap.get("status").toString()));
					values[7] = WhsConst.ORDER_IS_COMPLETED.get(dataMap.get("isCompleted") == null ?0:Integer.parseInt(dataMap.get("isCompleted").toString()));
				}
				valueList.add(values);
			}
			//获得分公司中英文名称
        	CommonCompany commonCompany =openLdap.getCompanyById(companyId);
	
			CreateExcel_PDF_CSV.createExcel(valueList, response, "订单列表", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出excel 结束");
		}
	}
	
	@RequestMapping(value = "/order/exportExcel4OrderDetail")
	public void exportExcel4ProductDetail(HttpServletRequest request,HttpServletResponse response) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出订单明细 开始");
		}
		OutputStream os = null;
		WritableWorkbook wb = null;
		try {
			WritableFont wf_key = new jxl.write.WritableFont(WritableFont.createFont("宋体"), 10,WritableFont.BOLD); 
	        WritableFont wf_value = new jxl.write.WritableFont(WritableFont.createFont("宋体"), 10,WritableFont.NO_BOLD); 
            
	        WritableCellFormat wcf_value = new WritableCellFormat(wf_value); 
            wcf_value.setAlignment(jxl.format.Alignment.CENTRE); 
            wcf_value.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
            wcf_value.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN); 
             
            WritableCellFormat wcf_name_center = new WritableCellFormat(wf_key); 
            wcf_name_center.setAlignment(Alignment.CENTRE); 
            wcf_name_center.setVerticalAlignment(VerticalAlignment.CENTRE); 
            wcf_name_center.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN); 
             
            //WritableFont wf_title = new WritableFont(WritableFont.ARIAL,14,WritableFont.BOLD); 
            WritableFont wf_title = new jxl.write.WritableFont(WritableFont.createFont("宋体"), 14,WritableFont.BOLD); 
            WritableCellFormat  wcf_title = new WritableCellFormat(wf_title); 
            wcf_title.setAlignment(Alignment.CENTRE); 
            wcf_title.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
            
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
		    Long orderId=map.get("orderId")==null?null:Long.valueOf(map.get("orderId").toString());
		    //获得订单信息
		    Order orderResult=orderService.get(Order.class, orderId);
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			//获得分公司中英文名称
			OpenLdap openLdap = OpenLdapManager.getInstance();
			CommonCompany commonCompany =  openLdap.getCompanyById(companyId);

		    
			String fileName=orderResult==null?"订单明细":("订单"+orderResult.getOrderNo());
			
			os = response.getOutputStream();
			response.setHeader("Content-disposition", "attachment; filename="+ java.net.URLEncoder.encode(fileName,"UTF-8")+ ".xls"); //设定输出文件头
			response.setContentType("application/excel"); //定义输出类型
			wb = Workbook.createWorkbook(os);
	        WritableSheet ws = wb.createSheet(fileName,0); 
	         
	        int startRowNum=0;//起始行 
	        int startColNum=0;//起始列 
	         
	        //设置列宽 
	        ws.setColumnView(0, 12); //序号
	        ws.setColumnView(1, 14); //编码
	        ws.setColumnView(2, 18); //名称
	        ws.setColumnView(3, 25); //规格
	        ws.setColumnView(4, 12); //价格
	        ws.setColumnView(5, 13); //采购数量
	        ws.setColumnView(6, 13); //已采购数量
	        ws.setColumnView(8, 14); //备注
	        
	       //去掉整个sheet中的网格线  
	        ws.getSettings().setShowGridLines(false);  
         
			//页眉:第一行分公司中文全称，第二行分公司英文名,第三行文件名
	        ws.addCell(new Label(0, 0, commonCompany.getCnfullname(),wcf_title));
	        ws.mergeCells(0,0, 7,0); 
			
	        ws.addCell(new Label(0, 1, commonCompany.getEnfullname(),wcf_title));
	        ws.mergeCells(0,1, 7,1); 
			
	        ws.addCell(new Label(0, 2, fileName,wcf_title));
	        ws.mergeCells(0,2, 7,2); 
			
	        startRowNum=3;
	        //第四行
	        ws.addCell(new Label(startColNum,startRowNum,"订单号：",wcf_value)); 
	        
	        ws.addCell(new Label(1,startRowNum,orderResult==null?"":orderResult.getOrderNo(),wcf_value)); 
	        ws.mergeCells(1,startRowNum, 3,startRowNum);
	       
	        ws.addCell(new Label(4,startRowNum,"日期：",wcf_value)); 
	        
	        ws.addCell(new Label(5,startRowNum,orderResult==null?"":orderResult.getStamp()+"",wcf_value)); 
	        ws.mergeCells(5,startRowNum, 7,startRowNum);
	       
	        startColNum=0; 
	        startRowNum=4;       
	        
	        //第五行
	        ws.addCell(new Label(startColNum,startRowNum,"客户名称：",wcf_value)); 
	        
	        ws.addCell(new Label(1,startRowNum,"",wcf_value)); 
	        ws.mergeCells(1,startRowNum, 3,startRowNum);
	       
	        ws.addCell(new Label(4,startRowNum,"订单接收人：",wcf_value)); 
	        
	        ws.addCell(new Label(5,startRowNum,orderResult==null?"":orderResult.getReceiptName(),wcf_value)); 
	        ws.mergeCells(5,startRowNum, 7,startRowNum);
	       
	        startColNum=0; 
	        startRowNum=5;      
	        
	        //第六行
	        ws.addCell(new Label(startColNum,startRowNum,"总金额：",wcf_value)); 
	        
	        ws.addCell(new Label(1,startRowNum,orderResult==null?"":orderResult.getTotalPrice()+"",wcf_value)); 
	        ws.mergeCells(1,startRowNum, 3,startRowNum);
	       
	        ws.addCell(new Label(4,startRowNum,"",wcf_value)); 
	        ws.mergeCells(4,startRowNum, 7,startRowNum);
	       
	        startColNum=0; 
	        startRowNum=6; 
	        
	        //第7行
	        ws.addCell(new Label(0,startRowNum,"备注：",wcf_value)); 
	        
	        ws.addCell(new Label(1,startRowNum,orderResult==null?"":orderResult.getRemark(),wcf_value)); 
	        ws.mergeCells(1,startRowNum, 7,startRowNum);
	       
	        startColNum=0; 
	        startRowNum=7; 
	        
	        //第八行 空行
	        ws.addCell(new Label(0,startRowNum,"",wcf_value));
	        ws.mergeCells(0,startRowNum, 7,startRowNum);
	        startColNum=0; 
	        startRowNum=8; 
	        
	        //第九行 商品表头
	    	ws.addCell(new Label(startColNum++,startRowNum,"序号",wcf_value));
	    	ws.addCell(new Label(startColNum++,startRowNum,"商品编码",wcf_value));
	    	ws.addCell(new Label(startColNum++,startRowNum,"商品名称",wcf_value));
			ws.addCell(new Label(startColNum++,startRowNum,"商品规格",wcf_value)); 
			ws.addCell(new Label(startColNum++,startRowNum,"价格",wcf_value));
			ws.addCell(new Label(startColNum++,startRowNum,"采购数量",wcf_value));
			ws.addCell(new Label(startColNum++,startRowNum,"已采购数量",wcf_value));
			ws.addCell(new Label(startColNum++,startRowNum,"备注",wcf_value));
	
			startColNum=0; 
	        startRowNum=9; 
	        int rowIndex=0;
	         if (orderResult != null) {
	        	//获得订单明细
	 		    List<HashMap<String, Object>> details=orderService.findOrderDetailsByOrderId(orderId);
	 		   
	        	HashMap<String, Object> orderDetail=null;
	        	if (details != null && !details.isEmpty()) {
	        		int listLenth=details.size();
	        		for (int j = 0; j < listLenth; j++) {
	        			orderDetail = details.get(j);
						rowIndex = startRowNum+j; 
						ws.addCell(new Label(startColNum++,rowIndex,String.valueOf(j+1),wcf_value));
						ws.addCell(new Label(startColNum++,rowIndex,orderDetail.get("productCode") == null ?"":orderDetail.get("productCode").toString(),wcf_value));
						ws.addCell(new Label(startColNum++,rowIndex,orderDetail.get("productName") == null ?"":orderDetail.get("productName").toString(),wcf_value));
						ws.addCell(new Label(startColNum++,rowIndex,orderDetail.get("norm") == null ?"":orderDetail.get("norm").toString(),wcf_value));
						ws.addCell(new Label(startColNum++,rowIndex,orderDetail.get("price") == null ?"":orderDetail.get("price").toString(),wcf_value));
						ws.addCell(new Label(startColNum++,rowIndex,orderDetail.get("num") == null ?"":orderDetail.get("num")+"",wcf_value)); 
						ws.addCell(new Label(startColNum++,rowIndex,(orderDetail.get("inNum") == null ?"":orderDetail.get("inNum"))+"",wcf_value));
						ws.addCell(new Label(startColNum++,rowIndex,orderDetail.get("remark")==null?"":orderDetail.get("remark").toString(),wcf_value));
						startColNum=0;
					}
	        		startRowNum=startRowNum+details.size();
	        	}
	        	 //获得地址信息
	 		    List<HashMap<String, Object>> customerAddressResult=orderService.findCustomerAddress(map);
	 		   if (customerAddressResult != null && !customerAddressResult.isEmpty()) {
	 			  //与地址信息之间 空行
	    	        ws.addCell(new Label(0,startRowNum,"",wcf_value));
	    	        ws.mergeCells(0,startRowNum, 7,startRowNum);
	    	        startColNum=0; 
	    	        startRowNum++; 
	 			   String orderTransportType=null;//运输方式
	        		for (int j = 0; j < customerAddressResult.size(); j++) {
	        			orderDetail = customerAddressResult.get(j);
						rowIndex = startRowNum+j; 
						
						ws.addCell(new Label(startColNum,startRowNum,"运输方式：",wcf_value)); 
						orderTransportType=orderDetail.get("transportType") == null ?"1":orderDetail.get("transportType").toString();
						if("4".equals(orderTransportType)){//指定货运
							orderTransportType=WhsConst.ORDER_TRANSPORT_TYPE.get(Integer.parseInt(orderTransportType))+orderDetail.get("specifyFreight");
						}else{
							orderTransportType=WhsConst.ORDER_TRANSPORT_TYPE.get(Integer.parseInt(orderTransportType));
						}
				        ws.addCell(new Label(1,startRowNum,orderTransportType,wcf_value)); 
				        ws.mergeCells(1,startRowNum, 3,startRowNum);
				       
				        ws.addCell(new Label(4,startRowNum,"运费承接方：",wcf_value)); 
				        
				        ws.addCell(new Label(5,startRowNum,WhsConst.ORDER_PAY_TYPE.get(Integer.parseInt(orderDetail.get("payType") == null ?"1":orderDetail.get("payType").toString())),wcf_value)); 
				        ws.mergeCells(5,startRowNum, 7,startRowNum);
				        
				        startRowNum++;
				        //收货地址
				        ws.addCell(new Label(0,startRowNum,"收货地址：",wcf_value)); 
				        
				        ws.addCell(new Label(1,startRowNum,orderDetail.get("address") == null ?"":orderDetail.get("address").toString(),wcf_value)); 
				        ws.mergeCells(1,startRowNum, 7,startRowNum);
				        
				        startRowNum++;
				        
				        //收货人、手机号码
				        ws.addCell(new Label(startColNum,startRowNum,"收货人：",wcf_value)); 
				        ws.addCell(new Label(1,startRowNum,orderDetail.get("name") == null ?"":orderDetail.get("name").toString(),wcf_value)); 
				        ws.mergeCells(1,startRowNum, 3,startRowNum);
				       
				        ws.addCell(new Label(4,startRowNum,"手机号码：",wcf_value)); 
				        
				        ws.addCell(new Label(5,startRowNum,orderDetail.get("phone") == null ?"":orderDetail.get("phone").toString(),wcf_value)); 
				        ws.mergeCells(5,startRowNum, 7,startRowNum);
				        
				        startRowNum++;
				        //固话、邮箱
				        ws.addCell(new Label(startColNum,startRowNum,"固话：",wcf_value)); 
				        ws.addCell(new Label(1,startRowNum,orderDetail.get("tel") == null ?"":orderDetail.get("tel").toString(),wcf_value)); 
				        ws.mergeCells(1,startRowNum, 3,startRowNum);
				       
				        ws.addCell(new Label(4,startRowNum,"邮箱：",wcf_value)); 
				        
				        ws.addCell(new Label(5,startRowNum,orderDetail.get("email") == null ?"":orderDetail.get("email").toString(),wcf_value)); 
				        ws.mergeCells(5,startRowNum, 7,startRowNum);
				        
				        startRowNum++;
				        
				        //邮编
				        ws.addCell(new Label(0,startRowNum,"邮编：",wcf_value)); 
				        
				        ws.addCell(new Label(1,startRowNum,orderDetail.get("postcode") == null ?"":orderDetail.get("postcode").toString(),wcf_value)); 
				        ws.mergeCells(1,startRowNum, 7,startRowNum);
				      
				        startRowNum++;
						startColNum=0;
					}
	        		startRowNum=startRowNum+customerAddressResult.size();
	        	}
	         }
	        wb.write();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}  finally {
			try {
				wb.close();
				//os.flush();    
				os.close();
			} catch (WriteException e) {
				LOGGER.error(e.getMessage(), e);
				e.printStackTrace();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
				e.printStackTrace();
			}
		} 
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出订单明细 结束");
		}
	}
}
