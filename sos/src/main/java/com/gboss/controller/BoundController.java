package com.gboss.controller;

import java.io.File;
import java.io.IOException;
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

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gboss.comm.SystemConfig;
import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.UnitType;
import com.gboss.service.BoundService;
import com.gboss.service.UnitService;
import com.gboss.service.UnitTypeService;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.Utils;
import com.gboss.util.excel.CreateExcel_PDF_CSV;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.controller
 * @ClassName:BoundController
 * @author:hansm
 * @date:2016-6-12 下午2:51:12
 */
@Controller
public class BoundController extends BaseController {
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(BoundController.class);

	@Autowired
	private BoundService boundService;
	
	@Autowired
	private SystemConfig systemconfig;

	@RequestMapping(value = "/bound/findHmBoundInNets")
	@LogOperation(description = "海马绑定列表查询", type = SystemConst.OPERATELOG_SEARCHKEY, model_id =20082)
	public @ResponseBody
	Page<HashMap<String, Object>> findHmBoundInNets(@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		Page<HashMap<String, Object>> results = null;
		try {
			//subcoNo参数暂时固定为海马201，通过界面传递
			results = boundService.findBoundInNetsBypage(pageSelect);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印日志
			e.printStackTrace();
			// 同时把异常抛到前台
			//throw new SystemException();
		}
		return results;
	}
	
	@RequestMapping(value = "/bound/exportExcelHmBoundInNets")
	@LogOperation(description = "海马绑定列表导出", type = SystemConst.OPERATELOG_SEARCHKEY, model_id =20082)
	public void exportExcelHmBoundInNets(HttpServletRequest request,HttpServletResponse response) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出海马绑定列表 开始");
		}
		try {
			//条件
			Map returnMap =   parseReqParam2(request);
			String companyId = Utils.isNullOrEmpty(returnMap.get("subcoNo"))?"201":returnMap.get("subcoNo").toString();
			OpenLdap openLdap = OpenLdapManager.getInstance();
			//判断是否是分公司
			int level=openLdap.getUserCompanyLevel(companyId);
			List<HashMap<String, Object>> results = boundService.findAllBoundInNets(returnMap);
			String[][] title = {{"序号","10"},{"呼号","10"},{"IMEI","20"},{"条码","20"},{"VIN码","20"},{"车型","20"},{"工厂扫描时间","20"},
					{"同步时间","20"}};
			int columnIndex=0;
			List valueList = new ArrayList();
			HashMap<String, Object> valueData = null;
			String[] values = null;
			int listLenth=results.size();
			int titleLength=title.length;
			for(int i=0; i<listLenth; i++){
				values = new String[titleLength];
				valueData = results.get(i);
				columnIndex=0;
				values[columnIndex]  = (i+1)+"";
				columnIndex++;
				values[columnIndex] = com.gboss.util.StringUtils.clearNull(valueData.get("call_letter"));
				columnIndex++;
				values[columnIndex] = com.gboss.util.StringUtils.clearNull(valueData.get("imei"));
				columnIndex++;
				values[columnIndex] = com.gboss.util.StringUtils.clearNull(valueData.get("barcode"));
				columnIndex++;
				values[columnIndex] = com.gboss.util.StringUtils.clearNull(valueData.get("vin"));
				columnIndex++;
				values[columnIndex] = com.gboss.util.StringUtils.clearNull(valueData.get("vehicle_type"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("scan_time"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("stamp"));
				valueList.add(values);
			}
			CommonCompany commonCompany =  openLdap.getCompanyById(companyId);
			CreateExcel_PDF_CSV.createExcel(valueList, response, "海马绑定列表", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出海马绑定列表 结束");
		}
	}

	@RequestMapping(value = "/bound/exportExcelHmTBoxServerExpire")
	@LogOperation(description = "导出海马TBOX服务期满的客户", type = SystemConst.OPERATELOG_SEARCHKEY, model_id =20082)
	public void exportExcelHmTBoxServerExpire(HttpServletRequest request,HttpServletResponse response) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出海马TBOX服务期满的客户列表 开始");
		}
		try {
			//条件
			Map returnMap =   parseReqParam2(request);
			String companyId = Utils.isNullOrEmpty(returnMap.get("subcoNo"))?"201":returnMap.get("subcoNo").toString();
			OpenLdap openLdap = OpenLdapManager.getInstance();
			//判断是否是分公司
			int level=openLdap.getUserCompanyLevel(companyId);
			List<HashMap<String, Object>> results = boundService.findTBoxServerExpire(returnMap);
			String[][] title = {{"序号","10"},{"客户名称","20"},{"车牌号码","20"},{"车载号码","20"},{"vin码","20"},{"计费类型","20"},{"服务开始日期","20"},{"服务截止日期","20"}};
			int columnIndex=0;
			List valueList = new ArrayList();
			HashMap<String, Object> valueData = null;
			String[] values = null;
			int listLenth=results.size();
			int titleLength=title.length;
			for(int i=0; i<listLenth; i++){
				values = new String[titleLength];
				valueData = results.get(i);
				columnIndex=0;
				values[columnIndex]  = (i+1)+"";
				columnIndex++;
				values[columnIndex] = com.gboss.util.StringUtils.clearNull(valueData.get("customer_name"));
				columnIndex++;
				values[columnIndex] = com.gboss.util.StringUtils.clearNull(valueData.get("plate_no"));
				columnIndex++;
				values[columnIndex] = com.gboss.util.StringUtils.clearNull(valueData.get("call_letter"));
				columnIndex++;
				values[columnIndex] = com.gboss.util.StringUtils.clearNull(valueData.get("vin"));
				columnIndex++;
				values[columnIndex] = com.gboss.util.StringUtils.clearNull(valueData.get("feetype_name"));
				columnIndex++;
				values[columnIndex] = com.gboss.util.StringUtils.clearNull(valueData.get("fee_date"));
				columnIndex++;
				values[columnIndex]  = com.gboss.util.StringUtils.clearNull(valueData.get("fee_sedate"));
				valueList.add(values);
			}
			CommonCompany commonCompany =  openLdap.getCompanyById(companyId);
			CreateExcel_PDF_CSV.createExcel(valueList, response, "导出海马TBOX服务期满的客户列表", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出海马TBOX服务期满的客户列表 结束");
		}
	}
	
	
}
