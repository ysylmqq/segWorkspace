package com.gboss.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.MaintainItems;
import com.gboss.service.MaintainRuleService;
import com.gboss.util.LogOperation;

/**
 * @Package:com.gboss.controller
 * @ClassName:MaintainItemsController
 * @Description:TODO
 * @author:bzhang
 * @date:2014-6-27 上午10:31:15
 */
@Controller
public class MaintainItemsController extends BaseController {

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(MaintainItemsController.class);

	@Autowired
	private MaintainRuleService maintainRuleService;

	@RequestMapping(value = "/maintainItems/getDataList")
	@LogOperation(description = "获取保养信息", type = SystemConst.OPERATELOG_SEARCHKEY,model_id=20070)
	public @ResponseBody
	List<HashMap<String, Object>> getDataList(Long id,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得保养初始化数据开始");
		}
		List<HashMap<String, Object>> results = new ArrayList<HashMap<String, Object>>();
		try {

			String[] names = { "空调滤清器", "空气滤清器", "正时皮带", "整车制动液", "前制动器",
					"后制动器", "冷却液", "发动机机油", "燃油滤清器", "油路", "机油滤清器", "转向助力液",
					"火花塞", "变速箱油" };
			String[] values = { "ac_system", "air_filter", "belt",
					"break_fluid", "break_front", "break_rear",
					"coolant_fluid", "engine_oil", "fuel_filter",
					"oil_channels", "oil_filter", "power_steering",
					"spark_plugs", "transmission_fluid" };
			String colNames[] = { "a", "b", "c", "d", "e", "f", "g", "h", "i",
					"j", "k", "m" };

			MaintainItems item = maintainRuleService
					.getMaintainItemsByModelId(id);
			if (item != null) {
				Long main_id = item.getId();
				String data[] = { item.getAc_system(), item.getAir_filter(),
						item.getBelt(), item.getBreak_fluid(),
						item.getBreak_front(), item.getBreak_rear(),
						item.getCoolant_fluid(), item.getEngine_oil(),
						item.getFuel_filter(), item.getOil_channels(),
						item.getOil_filter(), item.getPower_steering(),
						item.getSpark_plugs(), item.getTransmission_fluid() };

				for (int i = 1; i < 15; i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					String value = values[i - 1];
					String name = names[i - 1];
					String dataValue = data[i-1];
					int a = -1;
					int b =0;
					if(null != dataValue && dataValue.contains(",")){
						String[] str = dataValue.split(",");
						a = Integer.valueOf(str[0]);
						b = Integer.valueOf(str[1]) - a;
					}
					for (int j = 0; j < 13; j++) {
						if (j == 0) {
							map.put("name", name);
						} else {
							int k = j - 1;
							int temp = k - a;
							String html = "<input type='checkbox'  name='"
									+ value + "' mark='" + k + "'  id='" + main_id
									+ "'>";
							if(b!=0 && (k==a || temp >0 && temp % b == 0 )){
								 html = "<input type='checkbox'  checked='checked' name='"
											+ value + "' mark='" + k + "'  id='" + main_id
											+ "'>";
							}
							map.put(colNames[k], html);
						}
					}
					results.add(map);
				}
			}else{
				for (int i = 1; i < 15; i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					String value = values[i - 1];
					String name = names[i - 1];
					for (int j = 0; j < 13; j++) {
						if (j == 0) {
							map.put("name", name);
						} else {
							int k = j - 1;
							String html = "<input type='checkbox' id='0'  name='" + value
									+ "' mark='" + k + "' >";
							map.put(colNames[j - 1], html);
						}
					}
					results.add(map);
				}
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得保养初始化数据结束");
		}
		return results;
	}

	@RequestMapping(value = "/maintainItems/save", method = RequestMethod.POST)
	@LogOperation(description = "添加保养", type = SystemConst.OPERATELOG_ADD,model_id=20070)
	public @ResponseBody
	HashMap save(@RequestBody MaintainItems main, BindingResult bindingResult,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("车型保养关联开始");
		}
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (main != null) {
				if (main.getEngine_oil_ids() != null
						&& main.getEngine_oil_ids().size() > 1) {
					main.setEngine_oil(main.getEngine_oil_ids().get(0) + ","
							+ main.getEngine_oil_ids().get(1));
				}

				if (main.getOil_filter_ids() != null
						&& main.getOil_filter_ids().size() > 1) {
					main.setOil_filter(main.getOil_filter_ids().get(0) + ","
							+ main.getOil_filter_ids().get(1));
				}

				if (main.getFuel_filter_ids() != null
						&& main.getFuel_filter_ids().size() > 1) {
					main.setFuel_filter(main.getFuel_filter_ids().get(0) + ","
							+ main.getFuel_filter_ids().get(1));
				}

				if (main.getSpark_plugs_ids() != null
						&& main.getSpark_plugs_ids().size() > 1) {
					main.setSpark_plugs(main.getSpark_plugs_ids().get(0) + ","
							+ main.getSpark_plugs_ids().get(1));
				}
				if (main.getOil_channels_ids() != null
						&& main.getOil_channels_ids().size() > 1) {
					main.setOil_channels(main.getOil_channels_ids().get(0)
							+ "," + main.getOil_channels_ids().get(1));
				}
				if (main.getCoolant_fluid_ids() != null
						&& main.getCoolant_fluid_ids().size() > 1) {
					main.setCoolant_fluid(main.getCoolant_fluid_ids().get(0)
							+ "," + main.getCoolant_fluid_ids().get(1));
				}
				if (main.getAc_system_ids() != null
						&& main.getAc_system_ids().size() > 1) {
					main.setAc_system(main.getAc_system_ids().get(0) + ","
							+ main.getAc_system_ids().get(1));
				}
				if (main.getTransmission_fluid_ids() != null
						&& main.getTransmission_fluid_ids().size() > 1) {
					main.setTransmission_fluid(main.getTransmission_fluid_ids()
							.get(0)
							+ ","
							+ main.getTransmission_fluid_ids().get(1));
				}
				if (main.getBreak_fluid_ids() != null
						&& main.getBreak_fluid_ids().size() > 1) {
					main.setBreak_fluid(main.getBreak_fluid_ids().get(0) + ","
							+ main.getBreak_fluid_ids().get(1));
				}
				if (main.getBreak_front_ids() != null
						&& main.getBreak_front_ids().size() > 1) {
					main.setBreak_front(main.getBreak_front_ids().get(0) + ","
							+ main.getBreak_front_ids().get(1));
				}
				if (main.getBreak_rear_ids() != null
						&& main.getBreak_rear_ids().size() > 1) {
					main.setBreak_rear(main.getBreak_rear_ids().get(0) + ","
							+ main.getBreak_rear_ids().get(1));
				}

				if (main.getBelt_ids() != null && main.getBelt_ids().size() > 1) {
					main.setBelt(main.getBelt_ids().get(0) + ","
							+ main.getBelt_ids().get(1));
				}

				if (main.getPower_steering_ids() != null
						&& main.getPower_steering_ids().size() > 1) {
					main.setPower_steering(main.getPower_steering_ids().get(0)
							+ "," + main.getPower_steering_ids().get(1));
				}

				if (main.getOil_filter_ids() != null
						&& main.getOil_filter_ids().size() > 1) {
					main.setOil_filter(main.getOil_filter_ids().get(0) + ","
							+ main.getOil_filter_ids().get(1));
				}
				if(main.getId() == null || main.getId() == 0){
					maintainRuleService.save(main);
				}else{
					maintainRuleService.update(main);
				}
				
				if (main.getId() == null) {
					flag = false;
					msg = SystemConst.OP_FAILURE;
				}

			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			flag = false;
			msg = SystemConst.OP_FAILURE;
			// 同时把异常抛到前台
			throw new SystemException();
		}
		resultMap.put("success", flag);
		resultMap.put("msg", msg);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("车型保养关联结束");
		}
		return resultMap;
	}

}
