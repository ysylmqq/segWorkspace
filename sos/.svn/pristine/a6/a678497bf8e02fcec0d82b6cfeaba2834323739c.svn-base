package com.gboss.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ldap.objectClasses.CommonCompany;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.apache.commons.lang.StringUtils;
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
import com.gboss.pojo.Operatelog;
import com.gboss.pojo.SysNode;
import com.gboss.pojo.SysValue;
import com.gboss.service.SysNodeService;
import com.gboss.service.SysService;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.excel.CreateExcel_PDF_CSV;
import com.gboss.util.page.Page;

/**
 * @Package:com.chinagps.fee.controller
 * @ClassName:SysController
 * @Description:系统参数类型、值 控制层
 * @author:zfy
 * @date:2014-6-11 上午9:39:21
 */
@Controller
@RequestMapping(value="/sys")
public class SysController extends BaseController {
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(SysController.class);

	@Autowired
	@Qualifier("sysService")
	private SysService sysService;
	
	@Autowired
	@Qualifier("SysNodeService")
	private SysNodeService sysNodeService; 

	/**
	 * @Title:findSysValue
	 * @Description:查询系统参数值
	 * @param sysValue
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "findSysValue")
	public @ResponseBody
	 List<SysValue> findSysValue(
			@RequestBody SysValue sysValue,
			 HttpServletRequest request) throws SystemException {
		List<SysValue> result = null;
		try {
			if(sysValue==null){
				sysValue=new SysValue();
			}
			sysValue.setIsDel(0);
			result = sysService.findSysValue(sysValue);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return result;
	}
	
	@RequestMapping(value = "findSysNode")
	public @ResponseBody
	 List<SysNode> findSysNode(
			@RequestBody SysNode sysNode,
			 HttpServletRequest request) {
		if(sysNode==null){
			sysNode = new SysNode();
			sysNode.setFlag(1);
		}
		String companyid = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		sysNode.setSubco_id(Long.valueOf(companyid));
		List<SysNode> result = sysNodeService.findSysNode(sysNode);
		return result;
	}
	
	@RequestMapping(value = "findAllBank")
	public @ResponseBody
	 List<SysValue> findAllBank(@RequestBody SysValue sysValue,HttpServletRequest request) {
		List<SysValue> result = sysService.findAllBank();
		return result;
	}
	
	/**
	 * @Title:findOperatelogPage
	 * @Description:分页查询操作日志
	 */
	@RequestMapping(value = "findOperatelogPage")
	public @ResponseBody
	Page<Map<String,Object>> findOperatelogPage(
			@RequestBody PageSelect<Operatelog> pageSelect,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		Page<Map<String,Object>> results = null;
		try {
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			if (pageSelect != null) {
				Map conditionMap = pageSelect.getFilter();
				if (conditionMap == null) {
					conditionMap = new HashMap();
				}
				conditionMap.put("subcoNo", companyId);
			}
			results = sysService.findOperatelogPage(pageSelect);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印日志
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return results;
	}
	
	@RequestMapping(value = "exportExcel4Operatelog")
	@LogOperation(description = "操作日志记录导出", type = SystemConst.OPERATELOG_SEARCHKEY, model_id =20063)
	public void exportExcel4Operatelog(HttpServletRequest request,HttpServletResponse response) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("操作日志记录导出 开始");
		}
		try {
			//条件
			Map returnMap =   parseReqParam2(request);
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			if(returnMap!=null){
				returnMap.put("subcoNo", companyId);
			}
			List<Map<String, Object>> results = sysService.findOperatelog(returnMap);
			String[][] title = {{"序号","10"},{"操作人","20"},{"模块名称","20"},{"操作类型","20"},{"具体操作内容","100"},{"时间","30"}};
			int columnIndex=0;
			List valueList = new ArrayList();
			Map<String, Object> valueData = null;
			String[] values = null;
			int listLenth=results.size();
			int titleLength=title.length;
			for(int i=0; i<listLenth; i++){
				values = new String[titleLength];
				valueData = results.get(i);
				columnIndex=0;
				values[columnIndex]  = (i+1)+"";
				columnIndex++;
				values[columnIndex] = com.gboss.util.StringUtils.clearNull(valueData.get("user_name"));
				columnIndex++;
				values[columnIndex] = com.gboss.util.StringUtils.clearNull(valueData.get("sname"));
				columnIndex++;
				String op_type = com.gboss.util.StringUtils.clearNull(valueData.get("op_type"));
				if("1".equals(op_type)){
					op_type = "查询";
				}else if("2".equals(op_type)){
					op_type = "增加";
				}else if("3".equals(op_type)){
					op_type = "修改";
				}else if("4".equals(op_type)){
					op_type = "删除";
				}
				values[columnIndex] = op_type;
				columnIndex++;
				values[columnIndex] = com.gboss.util.StringUtils.clearNull(valueData.get("remark"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("stamp"));
				valueList.add(values);
			}
			//获得分公司中英文名称
			OpenLdap openLdap = OpenLdapManager.getInstance();
			CommonCompany commonCompany =  openLdap.getCompanyById(companyId);
			CreateExcel_PDF_CSV.createExcel(valueList, response, "操作日志记录报表", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出操作日志记录报表 结束");
		}
	}
			 
}
