package com.hm.bigdata.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hm.bigdata.comm.SystemException;
import com.hm.bigdata.util.CreateExcel_PDF_CSV;
import com.hm.bigdata.util.Utils;
import com.hm.bigdata.comm.SystemConst;
import com.hm.bigdata.entity.po.Vehicle;
import com.hm.bigdata.service.VehicleService;
import com.hm.bigdata.util.PageSelect;
import com.hm.bigdata.util.StringUtils;
import com.hm.bigdata.util.page.Page;
import com.hm.bigdata.util.page.ResultJson;

import ldap.objectClasses.CommonCompany;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

/**
 * @Package:com.gboss.controller
 * @ClassName:VehicleController
 * @Description:从门店copy过来，以后可能会改成静态数据（搜索引擎）
 * @author:bzhang
 * @date:2014-6-5 下午3:39:01
 */
@Controller
@RequestMapping(value="/vehicle")
public class VehicleController extends BaseController {
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(VehicleController.class);

	private static final int Vehicle = 0;
	
	@Autowired
	@Qualifier("vehicleService")
	private VehicleService vehicleService;

	@RequestMapping(value = "/getVehicles.page", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getVehicles( HttpServletRequest request) {
		PageSelect<Vehicle> pageSelect = (PageSelect<Vehicle>) request.getAttribute("pageSelect");
		String companyid = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		Long subco=null;
		Page<HashMap<String, Object>> result = null;
		try {
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			if (StringUtils.isNotBlank(companyId)) {
				OpenLdap openLdap = OpenLdapManager.getInstance();
				//判断是否是分公司
				int level=openLdap.getUserCompanyLevel(companyId);
				if(level==0){//总部
					subco = null;
				}else{
					subco=Long.valueOf(companyid);
				}
			}
			subco = 201L;
			result = vehicleService.search(pageSelect, subco);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return ResultJson.converToMap(result);
	}
	
	
	@RequestMapping(value = "exportVehicleInfos")
	public void exportArrearageInfos(HttpServletRequest request,HttpServletResponse response){
		try {
			String[][] title = {{"序号","10"},{"客户名称","20"},{"车牌号码","14"},{"呼号","14"},{"barcode","20"},{"vin","20"}
								};
			//条件
			Map map=parseReqParam2(request);
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			OpenLdap openLdap = OpenLdapManager.getInstance();
			if (StringUtils.isNotBlank(userId)) {
				//判断是否是分公司
				int level=openLdap.getUserCompanyLevel(companyId);
				//是否是总部，特殊处理
				String ishead = Utils.isNullOrEmpty((request.getSession().getAttribute(
						SystemConst.ACCOUNT_ISHQ)))? "false":request.getSession().getAttribute(
								SystemConst.ACCOUNT_ISHQ).toString();
				boolean ishq = Boolean.valueOf(ishead);
				if(level==0){//总部
					
				}else if(level==1){//分公司
					if(!ishq){
						map.put("subcoNo", Long.valueOf(companyId));
					}
				}else if(level==2){//营业处
					if(!ishq){
						map.put("subcoNo", Long.valueOf(companyId));
					}
				}
			}
			map.put("subcoNo",201);
			
			List<Map<String, Object>> results = vehicleService.findAllVehicles(map);
			List valueList = new ArrayList();
			Map<String, Object> valueData = null;
			String[] values = null;
			int listLenth=results.size();
			int titleLength=title.length;
			int columnIndex=0;
			
			for(int i=0; i<listLenth; i++){
				values = new String[titleLength];
				valueData = results.get(i);
				columnIndex=0;
				values[columnIndex]  = (i+1)+"";
				columnIndex++;
				values[columnIndex] = Utils.clearNull(valueData.get("customer_name"));
				columnIndex++;
				values[columnIndex]  = Utils.clearNull(valueData.get("plate_no"));
				columnIndex++;
				values[columnIndex]  = Utils.clearNull(valueData.get("call_letter"))+" ";
				columnIndex++;
				values[columnIndex]  = Utils.clearNull(valueData.get("barcode"));
				columnIndex++;
				values[columnIndex]  = Utils.clearNull(valueData.get("vin"));
				valueList.add(values);
			}
			//获得分公司中英文名称
			CommonCompany commonCompany =  openLdap.getCompanyById(companyId);
	
			CreateExcel_PDF_CSV.createExcel(valueList, response, "车辆信息报表", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

}

