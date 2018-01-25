package com.gboss.controller;

import java.util.HashMap;
import java.util.Map;

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
import com.gboss.pojo.Brand;
import com.gboss.pojo.Series;
import com.gboss.service.SeriesService;
import com.gboss.util.LogOperation;

/**
 * @Package:com.gboss.controller
 * @ClassName:SeriesController
 * @Description:TODO
 * @author:bzhang
 * @date:2014-6-25 上午10:15:39
 */
@Controller
public class SeriesController extends BaseController {
	
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(SeriesController.class);
	
	@Autowired
	private SeriesService seriesService;
	

	@RequestMapping(value = "/series/update", method = RequestMethod.POST)
	@LogOperation(description = "修改车系", type = SystemConst.OPERATELOG_UPDATE, model_id =20070)
	public @ResponseBody Map<String, Object> update(@RequestBody Series series, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		Boolean isExist = seriesService.isExist(series.getName(),series.getId());
		String msg = isExist==true?"该车系名称已存在！":SystemConst.OP_SUCCESS;
		map.put("success", !isExist);
		map.put(SystemConst.MSG, msg);
		if(!isExist){
			Series oldSeries = seriesService.get(Series.class, series.getId());
			oldSeries.setName(series.getName());
			oldSeries.setProducer(series.getProducer());
			oldSeries.setRemark(series.getRemark());
			seriesService.saveOrUpdate(oldSeries);
		}
		return map;
	}
	
	
	@RequestMapping(value = "/series/add", method = RequestMethod.POST)
	@LogOperation(description = "新增车系", type = SystemConst.OPERATELOG_ADD, model_id =20070)
	public @ResponseBody Map<String, Object> add(@RequestBody Series series, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		Boolean isExist = seriesService.isExist(series.getName());
		String msg = isExist==true?"该车系名称已存在！":SystemConst.OP_SUCCESS;
		map.put("success", !isExist);
		map.put(SystemConst.MSG, msg);
		if(!isExist){
			series.setIs_valid(1);
			seriesService.save(series);
		}
		return map;
	}
	
	

	@RequestMapping(value = "/series/delete")
	@LogOperation(description = "删除车系", type = SystemConst.OPERATELOG_DEL, model_id =20070)
	public @ResponseBody
	HashMap<String, Object> delete(Long id,
			HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("删除车系开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			int result = seriesService.delete(id);
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
			LOGGER.debug("删除车系结束");
		}
		return resultMap;
	}
	

}

