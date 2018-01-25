package com.gboss.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ldap.objectClasses.CommonSystem;

import org.apache.commons.lang.StringUtils;
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
import com.gboss.service.BrandService;
import com.gboss.util.LogOperation;

/**
 * @Package:com.gboss.controller
 * @ClassName:BrandController
 * @Description:TODO
 * @author:bzhang
 * @date:2014-6-24 下午3:35:17
 */
@Controller
public class BrandController extends BaseController {

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(BrandController.class);

	@Autowired
	private BrandService brandService;

	@RequestMapping(value = "/brand/brandTree")
	@LogOperation(description = "车型树", type = SystemConst.OPERATELOG_SEARCHKEY,model_id = 20070)
	public @ResponseBody
	List<HashMap<String, Object>> brandTree(@RequestBody Brand brand, BindingResult bindingResult,HttpServletRequest request) throws Exception {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		String keyword = brand.getName();
		try {
			if (StringUtils.isNotBlank(keyword))
				list = brandService.brandTree(keyword);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping(value = "/brand/update", method = RequestMethod.POST)
	@LogOperation(description = "修改品牌", type = SystemConst.OPERATELOG_UPDATE,model_id = 20070)
	public @ResponseBody
	Map<String, Object> update(@RequestBody Brand brand,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		Boolean isExist = brandService.isExist(brand.getName(), brand.getId());
		String msg = isExist == true ? "该品牌名称已存在！" : SystemConst.OP_SUCCESS;
		map.put("success", !isExist);
		map.put(SystemConst.MSG, msg);
		if (!isExist) {
			Brand oldBrand = brandService.get(Brand.class, brand.getId());
			oldBrand.setName(brand.getName());
			brandService.saveOrUpdate(oldBrand);
		}
		return map;
	}

	@RequestMapping(value = "/brand/add", method = RequestMethod.POST)
	@LogOperation(description = "添加品牌", type = SystemConst.OPERATELOG_ADD,model_id = 20070)
	public @ResponseBody
	Map<String, Object> add(@RequestBody Brand brand,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		Boolean isExist = brandService.isExist(brand.getName());
		String msg = isExist == true ? "该品牌名称已存在！" : SystemConst.OP_SUCCESS;
		map.put("success", !isExist);
		map.put(SystemConst.MSG, msg);
		if (!isExist) {
			brand.setIs_valid(1);
			brandService.save(brand);
		}
		return map;
	}

	@RequestMapping(value = "/brand/delete")
	@LogOperation(description = "删除品牌", type = SystemConst.OPERATELOG_DEL,model_id = 20070)
	public @ResponseBody
	HashMap<String, Object> delete(Long id, HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("删除品牌开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			int result = brandService.delete(id);
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
			LOGGER.debug("删除品牌结束");
		}
		return resultMap;
	}

}
