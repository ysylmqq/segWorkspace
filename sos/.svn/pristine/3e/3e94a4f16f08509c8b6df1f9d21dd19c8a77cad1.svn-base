package com.gboss.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ldap.mysql.IdCreater;
import ldap.objectClasses.CommonCompany;
import ldap.objectClasses.CommonOperator;
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
import org.springframework.web.multipart.MultipartFile;

import com.gboss.comm.SelConst;
import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.Channel;
import com.gboss.pojo.ChannelGroup;
import com.gboss.pojo.ChannelSalesmanager;
import com.gboss.pojo.Channelcontacts;
import com.gboss.pojo.web.TreeNode;
import com.gboss.service.ChannelService;
import com.gboss.util.GetPinyin;
import com.gboss.util.JsonUtil;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.excel.CreateExcel_PDF_CSV;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.controller
 * @ClassName:ChannelController
 * @Description:代理商管理控制层
 * @author:zfy
 * @date:2013-8-26 下午4:35:13
 */
@Controller
public class ChannelController extends BaseController {
	protected static final Logger LOGGER = LoggerFactory.getLogger(ChannelController.class);

	@Autowired
	@Qualifier("channelService")
	private ChannelService channelService;

	private ObjectMapper objectMapper = new ObjectMapper();
	private JsonGenerator jsonGenerator = null;

	@RequestMapping(value = "/sell/addChannelAndContracts")
	@LogOperation(description = "添加代理商", type = SystemConst.OPERATELOG_ADD, model_id = 20072)
	public @ResponseBody HashMap<String, Object> addChannelAndContracts(@RequestBody Channel channel, HttpServletRequest request)
		throws SystemException{
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("添加代理商 开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			// 将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(channel, true));

			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(channel);
			}
			String companyId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
			/*
			 String orgId = (String) request.getSession().getAttribute(
			 SystemConst.ACCOUNT_ORGID);
			 */
			if (channel != null) {
				// 设置机构、分公司name
				OpenLdap openLdap = OpenLdapManager.getInstance();
				if (StringUtils.isNotBlank(companyId)) {
					CommonCompany commonCompany = openLdap.getCompanyById(companyId);
					if (commonCompany != null) {
						channel.setCompanyName(commonCompany.getCompanyname());
					}
					channel.setCompanyId(Long.valueOf(companyId));
				}
				// 如果不是营业厅，机构就为空
				/*
				 if(StringUtils.isBlank(channel.getOrgId())){ if
				 (StringUtils.isNotBlank(orgId)) { CommonCompany
				 commonCompany=openLdap.getCompanyById(orgId);
				 if(commonCompany!=null){
				 channel.setOrgName(commonCompany.getCompanyname()); }
				 channel.setOrgId(orgId); } }
				 */

				int result = channelService.addChannelAndContracts(channel);
				if (result == -1) {
					flag = false;
					msg = "操作对象为空";
				} else if (result == 2) {
					flag = false;
					msg = "代理商名称已存在";
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
			LOGGER.debug("添加代理商 结束");
		}
		return resultMap;
	}

	@RequestMapping(value = "/sell/updateChannelAndContracts")
	@LogOperation(description = "修改代理商", type = SystemConst.OPERATELOG_UPDATE, model_id = 20072)
	public @ResponseBody HashMap<String, Object> updateChannelAndContracts(
			@RequestBody Channel channel, HttpServletRequest request)throws SystemException{
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("修改代理商 开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			// 将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(channel, true));

			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(channel);
			}
			String companyId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
			/*
			String orgId = (String) request.getSession().getAttribute(
	 		SystemConst.ACCOUNT_ORGID);
			 */
			if (channel != null) {
				// 设置机构、分公司name
				OpenLdap openLdap = OpenLdapManager.getInstance();
				if (StringUtils.isNotBlank(companyId)) {
					CommonCompany commonCompany = openLdap.getCompanyById(companyId);
					if (commonCompany != null) {
						channel.setCompanyName(commonCompany.getCompanyname());
					}
					channel.setCompanyId(Long.valueOf(companyId));
				}
				/*
				 if (StringUtils.isNotBlank(orgId)) { CommonCompany
				 commonCompany=openLdap.getCompanyById(orgId);
				 if(commonCompany!=null){
				 channel.setOrgName(commonCompany.getCompanyname()); }
				 channel.setOrgId(orgId); }
				 */
				int result = channelService.updateChannelAndContracts(channel);
				if (result == -1) {
					flag = false;
					msg = "操作对象为空";
				} else if (result == 0) {
					flag = false;
					msg = "要操作的对象不存在";
				} else if (result == 2) {
					flag = false;
					msg = "代理商名称已存在";
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
			LOGGER.debug("修改代理商 结束");
		}
		return resultMap;
	}

	@RequestMapping(value = "/sell/deleteChannelAndcontracts")
	@LogOperation(description = "删除代理商", type = SystemConst.OPERATELOG_DEL, model_id = 20072)
	public @ResponseBody HashMap<String, Object> deleteChannelAndcontracts(Long id, HttpServletRequest request)throws SystemException{
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("删除代理商 开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			// 将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(id, true));

			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(id);
			}
			if (id != null) {
				int result = channelService.deleteChannelAndcontracts(id);
				if (result == -1) {
					flag = false;
					msg = "操作对象为空";
				} else if (result == 0) {
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
			LOGGER.debug("删除代理商 结束");
		}
		return resultMap;
	}

	@RequestMapping(value = "/sell/findChannels")
	public @ResponseBody Page<HashMap<String, Object>> findChannels(@RequestBody PageSelect<Channel> pageSelect,
			HttpServletRequest request) throws SystemException{
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询代理商 开始");
		}
		Page<HashMap<String, Object>> result = null;
		try {
			if (pageSelect.getFilter() == null) {
				Map map = new HashMap<String, Object>();
				pageSelect.setFilter(map);
			}

			String companyId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
			if (StringUtils.isNotBlank(companyId)) {
				if (pageSelect.getFilter() == null) {
					pageSelect.setFilter(new HashMap<String, Object>());
				}
				pageSelect.getFilter().put("companyId", companyId);
			}
			result = channelService.findChannels(pageSelect);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询代理商 结束");
		}
		return result;
	}

	@RequestMapping(value = "/sell/addSalesManager")
	@LogOperation(description = "添加销售经理与代理商关系", type = SystemConst.OPERATELOG_ADD, model_id = 20072)
	public @ResponseBody HashMap<String, Object> addSalesManager( @RequestBody ChannelSalesmanager channelSalesmanager,
			HttpServletRequest request) throws SystemException{
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("添加销售经理与代理商关系 开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			// 将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(channelSalesmanager, true));

			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(channelSalesmanager);
			}
			int result = channelService.addSalesManager(channelSalesmanager);
			if (result == -1) {
				flag = false;
				msg = "操作对象为空";
			} else if (result == 0) {
				flag = false;
				msg = "该销售经理与代理商的关系已存在!";
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
			LOGGER.debug("添加销售经理与代理商关系 结束");
		}
		return resultMap;
	}

	@RequestMapping(value = "/sell/updateSalesManager")
	@LogOperation(description = "修改销售经理与代理商关系", type = SystemConst.OPERATELOG_ADD, model_id = 20072)
	public @ResponseBody HashMap<String, Object> updateSalesManager(@RequestBody ChannelSalesmanager channelSalesmanager,
			HttpServletRequest request)throws SystemException{
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("修改销售经理与代理商关系 开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			// 将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(channelSalesmanager, true));

			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(channelSalesmanager);
			}
			int result = channelService.updateSalesManager(channelSalesmanager);
			if (result == -1 || result == 0) {
				flag = false;
				msg = "操作对象为空";
			} else if (result == 2) {
				flag = false;
				msg = "该销售经理与代理商的关系已存在!";
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
			LOGGER.debug("修改销售经理与代理商关系 结束");
		}
		return resultMap;
	}

	@RequestMapping(value = "/sell/deleteSalesManagers")
	@LogOperation(description = "销售经理与代理商关系", type = SystemConst.OPERATELOG_DEL, model_id = 20072)
	public @ResponseBody HashMap<String, Object> deleteSalesManagers(@RequestBody List<Long> ids,
			HttpServletRequest request)throws SystemException{
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("删除销售经理与代理商关系 开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			// 将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(ids, true));

			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(ids);
			}
			int result = channelService.deleteSalesManagers(ids);
			if (result == -1) {
				flag = false;
				msg = "操作对象为空";
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
			LOGGER.debug("删除销售经理与代理商关系 结束");
		}
		return resultMap;
	}

	@RequestMapping(value = "/sell/getChannel")
	public @ResponseBody HashMap<String, Object> getChannel(@RequestBody Channel channel,
			HttpServletRequest request)throws SystemException{
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查找代理商 开始");
		}
		HashMap<String, Object> result = null;
		try {
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(channel);
			}
			result = channelService.getChannelAndContacts(channel.getId());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查找代理商 结束");
		}
		return result;
	}

	@RequestMapping(value = "/sell/findAllChannels")
	public @ResponseBody List<HashMap<String, Object>> findAllChannels(
			@RequestBody Map<String, Object> map, HttpServletRequest request)throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询代理商 开始");
		}
		List<HashMap<String, Object>> result = null;
		try {

			String companyId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
			if (StringUtils.isNotBlank(companyId)) {
				map.put("companyId", companyId);
			}
			result = channelService.findChannels(map);
		} catch (SystemException e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询代理商 结束");
		}
		return result;
	}

	/**
	 * @Title:exportChannel
	 * @Description:导出代理商
	 * @param request
	 * @param response
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/sell/exportExcel4Channel")
	public @ResponseBody void exportExcel4Channel(HttpServletRequest request,
			HttpServletResponse response) throws SystemException {
		try {
			Map returnMap = request.getParameterMap();
			// 将request.getParameterMap()转换成可操作的普通Map
			// 返回值Map
			Map map = new HashMap();
			Iterator entries = returnMap.entrySet().iterator();
			Map.Entry entry = null;
			String name = "";
			String value = "";
			while (entries.hasNext()) {
				entry = (Map.Entry) entries.next();
				name = (String) entry.getKey();
				Object valueObj = entry.getValue();
				if (null == valueObj) {
					value = "";
				} else if (valueObj instanceof String[]) {
					String[] values = (String[]) valueObj;
					for (int i = 0; i < values.length; i++) {
						value = values[i] + ",";
					}
					value = value.substring(0, value.length() - 1);
				} else {
					value = valueObj.toString();
				}
				if ("null".equals(value)) {
					value = "";
				}
				if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(value)) {
					map.put(name, value);
				}
			}
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			if (StringUtils.isNotBlank(companyId)) {
				map.put("companyId", companyId);
			}
			/*
			 String[] title = new String[7]; title[0] = "序号"; title[1] =
			 "代理商名称"; title[2] = "代理商地址"; title[3] = "代理商型"; title[4] =
			 "所属集团"; title[5] = "月销售量"; title[6] = "备注";
			 */
			String[][] title = { { "序号", "10" }, { "代理商名称", "20" },
					{ "代理商地址", "30" }, { "代理商型", "14" }, { "所属集团", "20" }, { "月销售量", "16" }, { "备注", "20" } };
			List<HashMap<String, Object>> list = channelService.findChannels(map);
			List valueList = new ArrayList();
			HashMap<String, Object> channel = null;
			String[] values = null;
			int listLenth = list.size();
			for (int i = 0; i < listLenth; i++) {
				values = new String[7];
				channel = list.get(i);
				values[0] = String.valueOf(i + 1);
				values[1] = channel.get("name") == null ? "" : channel.get("name").toString();
				values[2] = channel.get("address") == null ? "" : channel.get("address").toString();
				values[3] = channel.get("dictName") == null ? "" : channel.get("dictName").toString();
				values[4] = channel.get("groupName") == null ? "" : channel.get("groupName").toString();
				values[5] = channel.get("monthSell") == null ? "" : channel.get("monthSell").toString();
				values[6] = channel.get("remark") == null ? "" : channel.get("remark").toString();
				valueList.add(values);
			}
			// 获得分公司中英文名称
			OpenLdap openLdap = OpenLdapManager.getInstance();
			CommonCompany commonCompany = openLdap.getCompanyById(companyId);

			CreateExcel_PDF_CSV.createExcel(valueList, response, "代理商", title, commonCompany.getCnfullname(), commonCompany.getEnfullname(), false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
	}

	@RequestMapping(value = "/sell/findOrgsAndManagersByCompanyId")
	public @ResponseBody List<TreeNode> findOrgsAndManagersByCompanyId(
			@RequestBody Map<String, Object> map, HttpServletRequest request)throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询分公司下的机构和销售经理 开始");
		}
		List<TreeNode> result = new ArrayList<TreeNode>();
		try {
			OpenLdap openLdap = OpenLdapManager.getInstance();

			// 查询机构
			String companyId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
			if (StringUtils.isNotBlank(companyId)) {
				List<CommonCompany> commonCompanies = openLdap.getOrgIdsByCompanyId(companyId);
				if (commonCompanies != null && !commonCompanies.isEmpty()) {
					TreeNode treeNode = null;
					TreeNode treeNode2 = null;
					List<TreeNode> items = null;
					List<CommonOperator> commonOperators = null;
					for (CommonCompany commonCompany : commonCompanies) {
						treeNode = new TreeNode();
						treeNode.setId(commonCompany.getCompanyno());
						treeNode.setName(commonCompany.getCompanyname());
						treeNode.setUrl("1");// 表示机构
						// 查询机构下的销售经理
						commonOperators = openLdap.getManagersByOrgId(commonCompany.getCompanyno());
						if (commonOperators != null && !commonOperators.isEmpty()) {
							for (CommonOperator commonOperator : commonOperators) {
								treeNode2 = new TreeNode();
								treeNode2.setId(commonOperator.getOpid());
								treeNode2.setName(commonOperator.getOpname());
								treeNode2.setUrl("2");// 表示销售经理
								items = treeNode.getItems();
								if (items == null) {
									items = new ArrayList<TreeNode>();
									treeNode.setItems(items);
								}
								treeNode.getItems().add(treeNode2);
							}
						}
						result.add(treeNode);
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询分公司下的机构和销售经理 结束");
		}
		return result;
	}

	@RequestMapping(value = "/sell/findChannelManagers")
	public @ResponseBody Page<HashMap<String, Object>> findChannelManagers(
			@RequestBody PageSelect<Map<String, Object>> pageSelect, HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询代理商和销售经理关系开始");
		}
		Page<HashMap<String, Object>> result = null;
		try {
			String companyId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
			if (StringUtils.isNotBlank(companyId)) {
				if (pageSelect.getFilter() == null) {
					pageSelect.setFilter(new HashMap<String, Object>());
				}
				pageSelect.getFilter().put("companyId", companyId);
			}
			result = channelService.findChannelManagers(pageSelect);
		} catch (SystemException e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询代理商和销售经理关系 结束");
		}
		return result;
	}

	@RequestMapping(value = "/sell/findAllChannelManagers")
	public @ResponseBody List<HashMap<String, Object>> findAllChannelManagers(
			@RequestBody Map<String, Object> map, HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询代理商和销售经理关系开始");
		}
		List<HashMap<String, Object>> result = null;
		try {
			String companyId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
			if (StringUtils.isNotBlank(companyId)) {
				map.put("companyId", companyId);
			}
			result = channelService.findAllChannelManagers(map);
		} catch (SystemException e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询代理商和销售经理关系 结束");
		}
		return result;
	}

	/**
	 * @Title:exportExcel4SaleManagers
	 * @Description:导出crm
	 * @param request
	 * @param response
	 * @throws SystemException
	 */
	@RequestMapping(value = "/sell/exportExcel4SaleManagers")
	public @ResponseBody
	void exportExcel4SaleManagers(HttpServletRequest request, HttpServletResponse response) throws SystemException {
		try {
			Map returnMap = request.getParameterMap();
			// 将request.getParameterMap()转换成可操作的普通Map
			// 返回值Map
			Map map = new HashMap();
			Iterator entries = returnMap.entrySet().iterator();
			Map.Entry entry = null;
			String name = "";
			String value = "";
			while (entries.hasNext()) {
				entry = (Map.Entry) entries.next();
				name = (String) entry.getKey();
				Object valueObj = entry.getValue();
				if (null == valueObj) {
					value = "";
				} else if (valueObj instanceof String[]) {
					String[] values = (String[]) valueObj;
					for (int i = 0; i < values.length; i++) {
						value = values[i] + ",";
					}
					value = value.substring(0, value.length() - 1);
				} else {
					value = valueObj.toString();
				}
				if ("null".equals(value)) {
					value = "";
				}
				if (StringUtils.isNotBlank(name)
						&& StringUtils.isNotBlank(value)) {
					map.put(name, value);
				}
			}
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			if (StringUtils.isNotBlank(companyId)) {
				map.put("companyId", companyId);
			}
			String[][] title = { { "序号", "10" }, { "代理商名称", "30" },
					{ "销售经理", "15" }, { "是否过时", "14" }, { "备注", "30" } };
			List<HashMap<String, Object>> list = channelService.findAllChannelManagers(map);
			List valueList = new ArrayList();
			HashMap<String, Object> channel = null;
			String[] values = null;
			int listLenth = list.size();
			for (int i = 0; i < listLenth; i++) {
				values = new String[5];
				channel = list.get(i);
				values[0] = String.valueOf(i + 1);
				values[1] = channel.get("channelName") == null ? "" : channel.get("channelName").toString();
				values[2] = channel.get("managerName") == null ? "" : channel.get("managerName").toString();
				values[3] = SelConst.SALESMANAGER_ISDEL.get(channel.get("isdel") == null ? 1 : channel.get("isdel"));
				values[4] = channel.get("remark") == null ? "" : channel.get("remark").toString();
				valueList.add(values);
			}
			// 获得分公司中英文名称
			OpenLdap openLdap = OpenLdapManager.getInstance();
			CommonCompany commonCompany = openLdap.getCompanyById(companyId);

			CreateExcel_PDF_CSV.createExcel(valueList, response, "代理商与销售经理关系", title, commonCompany.getCnfullname(), commonCompany.getEnfullname(), false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
	}

	@RequestMapping(value = "/sell/getChannelManager")
	public @ResponseBody HashMap<String, Object> getChannelManager(
			@RequestBody Map<String, Object> map, HttpServletRequest request)throws SystemException{
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询未过时的销售经理 开始");
		}
		HashMap<String, Object> result = null;
		try {
			result = channelService.getChannelManager(map);
		} catch (SystemException e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询未过时的销售经理  结束");
		}
		if (result == null) {
			result = new HashMap<String, Object>();
		}
		return result;
	}

	@RequestMapping(value = "/sell/findAllChannelGroups")
	public @ResponseBody List<HashMap<String, Object>> findAllChannelGroups(
			@RequestBody Map<String, Object> map, HttpServletRequest request) throws SystemException{
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询代理商所属集团 开始");
		}
		List<HashMap<String, Object>> result = null;
		try {
			String companyId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
			if (StringUtils.isNotBlank(companyId)) {
				map.put("companyId", companyId);
			}
			result = channelService.findChannelGroups(map);
		} catch (SystemException e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询代理商所属集团 结束");
		}
		return result;
	}

	@RequestMapping(value = "/sell/saveChannelGroup")
	@LogOperation(description = "保存代理商所属集团", type = SystemConst.OPERATELOG_ADD, model_id = 20072)
	public @ResponseBody HashMap<String, Object> saveChannelGroup(
			@RequestBody ChannelGroup channelGroup, HttpServletRequest request)throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("保存代理商所属集团 开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			// 将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(channelGroup, true));

			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(channelGroup);
			}
			String companyId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
			String orgId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_ORGID);
			String userId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_ID);
			if (channelGroup != null) {
				// 设置机构、分公司name
				OpenLdap openLdap = OpenLdapManager.getInstance();
				if (StringUtils.isNotBlank(companyId)) {
					CommonCompany commonCompany = openLdap.getCompanyById(companyId);
					if (commonCompany != null) {
						channelGroup.setCompanyName(commonCompany.getCompanyname());
					}
					channelGroup.setCompanyId(Long.valueOf(companyId));
				}
				if (StringUtils.isNotBlank(orgId)) {
					CommonCompany commonCompany = openLdap.getCompanyById(orgId);
					if (commonCompany != null) {
						channelGroup.setOrgName(commonCompany.getCompanyname());
					}
					channelGroup.setOrgId(Long.valueOf(orgId));
				}
				if (StringUtils.isNotBlank(userId)) {
					channelGroup.setUserId(Long.valueOf(userId));
				}
			}
			int result = channelService.saveChannelGroup(channelGroup);
			if (result == -1) {
				flag = false;
				msg = "操作对象为空";
			}
			if (result == 0) {
				flag = false;
				msg = "操作对象不存在！";
			} else if (result == 2) {
				flag = false;
				msg = "集团英文名称已存在,操作失败！";
			} else if (result == 3) {
				flag = false;
				msg = "集团名称已存在,操作失败！";
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
			LOGGER.debug("保存代理商所属集团 结束");
		}
		return resultMap;
	}

	@RequestMapping(value = "/sell/deleteChannelGroups")
	@LogOperation(description = "删除代理商所属集团", type = SystemConst.OPERATELOG_ADD, model_id = 20072)
	public @ResponseBody HashMap<String, Object> deleteChannelGroups(@RequestBody List<Long> list,
			HttpServletRequest request) throws SystemException{
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("保存代理商所属集团 开始");
		}
		HashMap<String, Object> resultMap = null;
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			// 将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(list, true));

			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(list);
			}
			resultMap = channelService.deleteChannelGroups(list);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg = StringUtils.isBlank(e.getMessage()) ? SystemConst.OP_FAILURE : e.getMessage();
			resultMap = new HashMap<String, Object>();
			resultMap.put(SystemConst.SUCCESS, flag);
			resultMap.put(SystemConst.MSG, msg);
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("保存代理商所属集团 结束");
		}
		return resultMap;
	}

	@RequestMapping(value = "/sell/impChannel")
	public @ResponseBody void impChannel(@RequestParam("channelFile") List<MultipartFile> channelFile,
			HttpServletRequest request, HttpServletResponse response) throws SystemException{
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导入代理商 开始");
		}
		OutputStream out = null;
		try {
			String companyId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
			String orgId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_ORGID);

			String companyName = null;
			// 设置机构、分公司name
			OpenLdap openLdap = OpenLdapManager.getInstance();
			if (StringUtils.isNotBlank(companyId)) {
				CommonCompany commonCompany = openLdap.getCompanyById(companyId);
				if (commonCompany != null) {
					companyName = commonCompany.getCompanyname();
				}
			}
			CommonCompany company2 = openLdap.getCompanyById(orgId);
			String orgName = company2.getCompanyname();
			if (channelFile != null) {
				for (MultipartFile channelFile1 : channelFile) {
					List<String[]> dataList = CreateExcel_PDF_CSV.getData(channelFile1.getInputStream());
					if (dataList != null) {
						String[] array = null;
						Channel channel = null;
						CommonOperator saleOp = null;
						CommonOperator saleOp2 = null;
						List<String> rolesId = new ArrayList<String>();
						rolesId.add(SystemConst.ROLEID_SALES_MANAGER);

						List<String> companynos = new ArrayList<String>();
						// companynos.add(companyId);
						companynos.add(orgId);

						List<Channelcontacts> channelcontacts = null;

						for (int i = 2; i < dataList.size(); i++) {
							channelcontacts = new ArrayList<Channelcontacts>();
							array = dataList.get(i);
							if (StringUtils.isNotBlank(array[0])) {
								// 先在ldap中添加销售经理
								// 判断是否存在
								// saleOp2=openLdap.getOperatorByopname(array[4].trim());
								saleOp2 = openLdap.isExistSaleManagers(companyId, array[4].trim());

								String opid = null;
								if (saleOp2 == null) {
									saleOp = new CommonOperator();
									opid = IdCreater.getOperatorId();
									saleOp.setOpid(opid);
									saleOp.setLoginname(GetPinyin.getPingYin(array[4].trim()));
									saleOp.setOpname(array[4]);
									saleOp.setUserPassword("123456");
									saleOp.setStatus("1");

									saleOp.setRoleid(rolesId);
									saleOp.setRolename("销售经理");
									saleOp.setOrder("0");
									saleOp.setCompanynos(companynos);
									// saleOp.setCompanyname(companyName);
									saleOp.setCompanyname(orgName);
									openLdap.add(saleOp);
								} else {
									opid = saleOp2.getOpid();
								}
								// 代理商
								channel = new Channel();
								channel.setAddress(array[3]);
								channel.setCompanyId(Long.valueOf(companyId));
								channel.setCompanyName(companyName);
								channel.setOrgId(Long.valueOf(orgId));
								channel.setOrgName(orgName);
								channel.setDictId(1l);// 4s
								channel.setName(array[0]);
								// 渠道联系人表
								if (StringUtils.isNotBlank(array[1])) {
									Channelcontacts channelcontacts2 = new Channelcontacts();
									channelcontacts2.setName(array[1]);
									channelcontacts2.setPhone(array[2]);
									channelcontacts2.setQq("");
									channelcontacts2.setEmail("");
									channelcontacts.add(channelcontacts2);
									channel.setChannelcontacts(channelcontacts);
								}
								// 销售经理I渠道关系表
								if (StringUtils.isNotBlank(array[4])) {
									channel.setSalesManagerId(Long.valueOf(opid));
									channel.setSalesManagerName(array[4]);
								}
								channelService.addChannelAndContracts(channel);
							}
						}
					}
				}
			}
			out = response.getOutputStream();
			String str = "<script>parent.callback('导入完毕','true');</script>";
			out.write(str.getBytes());
			out.flush();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
