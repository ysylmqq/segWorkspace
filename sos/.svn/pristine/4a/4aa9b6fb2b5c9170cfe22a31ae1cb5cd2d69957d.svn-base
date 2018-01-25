package com.gboss.controller;

import java.util.HashMap;
import java.util.List;
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
import com.gboss.pojo.SysValue;
import com.gboss.pojo.UnitType;
import com.gboss.pojo.web.VerifyPOJO;
import com.gboss.service.UnitTypeService;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.controller
 * @ClassName:UnitTypeController
 * @Description:TODO
 * @author:bzhang
 * @date:2014-7-6 下午2:54:04
 */
@Controller
public class UnitTypeController extends BaseController {
	
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(UnitTypeController.class);

	@Autowired
	private UnitTypeService unitTypeService;
	
	
	@RequestMapping(value = "/unitType/add", method = RequestMethod.POST)
	@LogOperation(description = "车台类型新增", type = SystemConst.OPERATELOG_ADD, model_id =20071)
	public @ResponseBody Map<String, Object> add(@RequestBody UnitType unitType, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		Boolean isExist = unitTypeService.isExist(unitType.getUnittype());
		String msg = isExist==true?SystemConst.OP_FAILURE:SystemConst.OP_SUCCESS;
		map.put("success", !isExist);
		map.put(SystemConst.MSG, msg);
		if(!isExist){
			unitTypeService.save(unitType);
		}
		return map;
	}
	
	
	
	@RequestMapping(value = "/unitType/update", method = RequestMethod.POST)
	@LogOperation(description = "车台类型修改", type = SystemConst.OPERATELOG_UPDATE, model_id =20071)
	public @ResponseBody Map<String, Object> update(@RequestBody UnitType unitType, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		Boolean isExist = unitTypeService.isExist(unitType.getUnittype(), unitType.getUnittype_id());
		String msg = isExist==true?SystemConst.OP_FAILURE:SystemConst.OP_SUCCESS;
		map.put("success", !isExist);
		map.put(SystemConst.MSG, msg);
		if(!isExist){
			UnitType unitType_old = unitTypeService.get(UnitType.class, unitType.getUnittype_id());
			unitType_old.setUnittype(unitType.getUnittype());
			unitType_old.setProtocol_id(unitType.getProtocol_id());
			unitType_old.setTypeclass(unitType.getTypeclass());
			unitType_old.setMemo(unitType.getMemo());
			unitTypeService.saveOrUpdate(unitType_old);	
		}
		return map;
	}
	
	@RequestMapping(value = "/unitType/getSysValue", method = RequestMethod.POST)
	public @ResponseBody List<SysValue> getSysValue
			(@RequestBody VerifyPOJO verifyPOJO, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		Integer typeId = SystemConst.CAR_TYPE;
		List<SysValue> list = null;
		try {
			list = unitTypeService.getSysValueList(typeId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/unitType/getUnitType", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getUnitType
			(@RequestBody VerifyPOJO verifyPOJO, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		Long unittype_id = Long.valueOf(verifyPOJO.getParameter());
		String unitType = unitTypeService.getUnittypeByid(unittype_id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		map.put("unittype", unitType);
		return map;
	}
	
	@RequestMapping(value = "/unitType/findUnitTypesByPage")
	@LogOperation(description = "车台类型分页列表", type = SystemConst.OPERATELOG_SEARCHKEY, model_id =20071)
	public @ResponseBody
	Page<HashMap<String, Object>> findUnitTypesByPage(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询车台类型开始");
		}
		Page<HashMap<String, Object>> result = null;
		try {
			if (pageSelect != null) {
				Map map = pageSelect.getFilter();
				if (map == null) {
					map = new HashMap<String, Object>();
				}
				pageSelect.setFilter(map);
			}
			result = unitTypeService.findUnitTypes(pageSelect);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询车台类型结束");
		}
		return result;
	}
	
	
	
	
	

}

