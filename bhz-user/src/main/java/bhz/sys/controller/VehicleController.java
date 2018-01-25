package bhz.sys.controller;

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

import bhz.com.constant.SystemConst;
import bhz.com.util.CreateExcel_PDF_CSV;
import bhz.com.util.Page;
import bhz.com.util.PageSelect;
import bhz.com.util.ResultJson;
import bhz.com.util.Utils;
import bhz.sys.entity.Vehicle;
import bhz.sys.facade.VehicleService;

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
		Page<HashMap<String, Object>> result = null;
		try {
			result = vehicleService.search(pageSelect, 201L);
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
	
			CreateExcel_PDF_CSV.createExcel(valueList, response, "车辆信息报表", title,"","",false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

}

