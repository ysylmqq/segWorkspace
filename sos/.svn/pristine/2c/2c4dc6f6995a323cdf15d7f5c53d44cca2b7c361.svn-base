package com.gboss.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Brand;
import com.gboss.pojo.Model;
import com.gboss.pojo.Series;
import com.gboss.pojo.web.VerifyPOJO;
import com.gboss.service.BrandService;
import com.gboss.service.CarTypeService;
import com.gboss.service.SeriesService;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.page.Page;

@Controller
public class CarTypeController extends BaseController {
	
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(CarTypeController.class);

	@Autowired
	@Qualifier("carTypeService")
	private CarTypeService carTypeService;
	
	@Autowired
	@Qualifier("BrandService")
	private BrandService brandService;
	
	@Autowired
	@Qualifier("SeriesService")
	private SeriesService seriesService;
	
	@RequestMapping(value = "careType/getCareType", method = RequestMethod.POST)
	public @ResponseBody List<Model> getCareType(HttpServletRequest request) throws SystemException {
		List<Model> list = carTypeService.getCarTypeList();
		return list;
	}
	
	@RequestMapping(value = "/careType/findCareType", method = RequestMethod.POST)
	public @ResponseBody Page<Model> findCareType(@RequestBody PageSelect<Model> pageSelect,
			HttpServletRequest request) throws SystemException {
		Page<Model> result = carTypeService.findCarType(pageSelect);
		return result;
	}
	
	@RequestMapping(value = "/careType/getBrand", method = RequestMethod.POST)
	public @ResponseBody List<Brand> getBrand
			(@RequestBody VerifyPOJO verifyPOJO, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		List<Brand> list = brandService.getBrandList();
		return list;
	}
	
	@RequestMapping(value = "/careType/getSeries", method = RequestMethod.POST)
	public @ResponseBody List<Series> getSeries
			(@RequestBody VerifyPOJO verifyPOJO, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		String brand_id = verifyPOJO.getParameter();
		List<Series> list = seriesService.getSeriesList(Long.valueOf(brand_id));
		return list;
	}
	
	@RequestMapping(value = "/careType/getCareTypeByid", method = RequestMethod.POST)
	public @ResponseBody List<Model> getCareTypeByid
			(@RequestBody VerifyPOJO verifyPOJO, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		String series_id = verifyPOJO.getParameter();
		List<Model> list = new ArrayList<Model>();
		if(StringUtils.isBlank(series_id)){
			list = carTypeService.getCarTypeList();
		}else{
			list = carTypeService.getCarTypeList(Long.valueOf(series_id));
		}
		return list;
	}	

}

