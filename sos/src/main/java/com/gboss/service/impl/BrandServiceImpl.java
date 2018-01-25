package com.gboss.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.BrandDao;
import com.gboss.pojo.Brand;
import com.gboss.pojo.MaintainRule;
import com.gboss.pojo.Model;
import com.gboss.pojo.Series;
import com.gboss.service.BrandService;
import com.gboss.service.MaintainRuleService;
import com.gboss.service.ModelService;
import com.gboss.service.SeriesService;
import com.gboss.util.Constants;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:BrandServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-5-9 下午3:11:29
 */
@Service("BrandService")
@Transactional
public class BrandServiceImpl extends BaseServiceImpl implements BrandService {

	@Autowired
	@Qualifier("BrandDao")
	private BrandDao brandDao;

	@Autowired
	private SeriesService seriesService;
	
	@Autowired
	private MaintainRuleService maintainRuleService;

	@Autowired
	private ModelService modelService;

	@Override
	public List<Brand> getBrandList() throws SystemException {
		return brandDao.getBrandList();
	}

	@Override
	public List<HashMap<String, Object>> brandTree(String keyword)throws SystemException {

		List<Brand> list = brandDao.serachBrandList(keyword);
		List<HashMap<String, Object>> msg = new ArrayList<HashMap<String, Object>>();
		if (list.size() == 0) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("name","没有数据");
			msg.add(map);
			return msg;
		}
		for (int i = 0; i < list.size(); i++) {
			Brand brand = list.get(i);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", brand.getId());
			map.put("name", brand.getName());
			map.put("level", "0");
			List<HashMap<String, Object>> series_items = new ArrayList<HashMap<String, Object>>();
			List<Series> seriesList = seriesService
					.getSeriesList(brand.getId());
			if (null != seriesList && seriesList.size() > 0) {
				for (Series series : seriesList) {
					HashMap<String, Object> series_map = new HashMap<String, Object>();
					List<Model> modelList = modelService.getModelList(series
							.getId());
					if (null != modelList && modelList.size() > 0) {
						List<HashMap<String, Object>> model_items = new ArrayList<HashMap<String, Object>>();
						for (Model model : modelList) {
							MaintainRule rule = maintainRuleService.getMaintainRuleByModelId(model.getId());
							HashMap<String, Object> model_map = new HashMap<String, Object>();
							Series series1 = seriesService.get(Series.class,
									model.getSeriesId());
							String series_name = series1 == null ? "" : series1
									.getName();
							if(model.getImg()!=null && !model.getImg().equals("")){
								model_map.put("img", model.getImg());
							}else{
								model_map.put("img",null);
							}
							model_map.put("series_name_model", series_name);
							model_map.put("id", model.getId());
							model_map.put("series_id", model.getSeriesId());
							model_map.put("name", model.getName());
							model_map.put("level", "2");
							//保养信息
							if(null != rule){
								model_map.put("standart_oil", model.getStandart_oil());
								model_map.put("displacement", model.getDisplacement());
								model_map.put("center_control", model.getCenter_control());
								model_map.put("horn_control", model.getHorn_control());
								model_map.put("window_control", model.getWindow_control());
								model_map.put("first_service_mileage", rule.getFirst_service_mileage());
								model_map.put("first_service_time", rule.getFirst_service_time());
								model_map.put("second_service_time", rule.getSecond_service_time());
								model_map.put("second_service_mileage", rule.getSecond_service_mileage());
								model_map.put("interval_time", rule.getInterval_time());
								model_map.put("interval_mileage", rule.getInterval_mileage());
								model_map.put("oil_volume", model.getOil_volume());
							}
							
							
							model_items.add(model_map);
						}
						series_map.put("items", model_items);
					}

					Brand brand1 = brandDao.get(Brand.class,
							series.getBrand_id());
					String brand_name = brand1 == null ? "" : brand1.getName();
					series_map.put("brand_name_series", brand_name);
					series_map.put("id", series.getId());
					series_map.put("producer", series.getProducer());
					series_map.put("remark", series.getRemark());
					
					series_map.put("brand_id", series.getBrand_id());
					series_map.put("name", series.getName());
					series_map.put("level", "1");
					series_items.add(series_map);
				}
			}

			map.put("items", series_items);
			msg.add(map);
		}
		return msg;
	}

	@Override
	public Boolean isExist(String name) throws SystemException{
		return brandDao.isExist(name);
	}

	@Override
	public int delete(Long id) throws SystemException {
		return brandDao.delete(id);
	}

	@Override
	public Boolean isExist(String name, Long id) throws SystemException{
		return brandDao.isExist(name, id);
	}

}
