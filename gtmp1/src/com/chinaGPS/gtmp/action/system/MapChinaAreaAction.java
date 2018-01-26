package com.chinaGPS.gtmp.action.system;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.chinaGPS.gtmp.action.common.BaseAction;
import com.chinaGPS.gtmp.entity.MapChinaAreaPOJO;
import com.chinaGPS.gtmp.service.IMapChinaAreaService;
import com.chinaGPS.gtmp.util.StringUtils;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Package:com.chinaGPS.gtmp.action.system
 * @ClassName:MapChinaAreaAction
 * @Description:行政区域及地图中心点类 包括省、市Action
 * @author:zfy
 * @date:2013-7-5 上午10:24:58
 */
@Scope("prototype")
@Controller
public class MapChinaAreaAction extends BaseAction implements ModelDriven<MapChinaAreaPOJO> {
	private static final long serialVersionUID = -1401422994447310379L;
	private static Logger logger = Logger.getLogger(MapChinaAreaAction.class);

	@Resource
	private IMapChinaAreaService mapChinaAreaService;
	@Resource
	private MapChinaAreaPOJO mapChinaAreaPOJO;

	/**
	 * @Title:getProvinceList
	 * @Description:获得省列表
	 * @return
	 * @throws
	 */
	public String getProvinceList() throws Exception {
		List<MapChinaAreaPOJO> result = new ArrayList<MapChinaAreaPOJO>();
		MapChinaAreaPOJO mapChinaAreaPOJO2 = new MapChinaAreaPOJO();
		mapChinaAreaPOJO2.setMapId("");
		mapChinaAreaPOJO2.setName("");
		result.add(mapChinaAreaPOJO2);
		result.addAll(mapChinaAreaService.getProvinceList(mapChinaAreaPOJO2));
		renderObject(result);
		return NONE;
	}

	/**
	 * @throws UnsupportedEncodingException
	 * @Title:getCityList
	 * @Description:获得市列表
	 * @return
	 * @throws
	 */
	public String getCityList() throws Exception {
		List<MapChinaAreaPOJO> result = new ArrayList<MapChinaAreaPOJO>();
		MapChinaAreaPOJO mapChinaAreaPOJO2 = new MapChinaAreaPOJO();
		mapChinaAreaPOJO2.setMapId("");
		mapChinaAreaPOJO2.setName("");
		result.add(mapChinaAreaPOJO2);

		// 省
		if (StringUtils.isNotBlank(mapChinaAreaPOJO.getProvinceName())) {
			mapChinaAreaPOJO.setProvinceName(java.net.URLDecoder.decode(
					mapChinaAreaPOJO.getProvinceName(), "UTF-8"));
		}
		result.addAll(mapChinaAreaService.getCityList(mapChinaAreaPOJO));
		renderObject(result);
		return NONE;
	}
	
	public void getProvinceListForPoint() {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		try {
			result = mapChinaAreaService.getProvinceListForPoint();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(result);
	}
	
	public void getCityListForPoint() throws Exception {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("provinceName", URLDecoder.decode(mapChinaAreaPOJO.getProvinceName(), "UTF-8"));
			result = mapChinaAreaService.getCityListForPoint(params);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(result);
	}

	@Override
	public MapChinaAreaPOJO getModel() {
		return mapChinaAreaPOJO;
	}

}
