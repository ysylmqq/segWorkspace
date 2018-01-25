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
import com.gboss.pojo.Record;
import com.gboss.service.RecordService;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.controller
 * @ClassName:RecordController
 * @Description:TODO
 * @author:bzhang
 * @date:2014-8-13 下午4:14:14
 */
@Controller
public class RecordController extends BaseController {
	
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(RecordController.class);
	
	@Autowired
	private RecordService recordService;
	
	@RequestMapping(value = "/record/findRecordByPage")
	public @ResponseBody
	Page<HashMap<String, Object>> findRecordByPage(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询档案开始");
		}
		Page<HashMap<String, Object>> result = null;
		try {
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			Long company_id = companyId == null ? null : Long.valueOf(companyId);
			if (pageSelect != null) {
				Map map = pageSelect.getFilter();
				if (map == null) {
					map = new HashMap<String, Object>();
				}
				pageSelect.setFilter(map);
			}
			result =recordService.findRecord(company_id, pageSelect);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询档案结束");
		}
		return result;
	}
	
	

	@RequestMapping(value = "/record/add", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> add(@RequestBody Record record, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		Boolean isExist = recordService.is_exist(record);
		String companyId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		Long company_id = companyId == null ? null : Long.valueOf(companyId);
		
		String user_id = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ID);
		Long userId = user_id == null ? null : Long.valueOf(user_id);
		
		String msg = isExist==true?"位置编号已存在！":SystemConst.OP_SUCCESS;
		map.put("success", !isExist);
		map.put(SystemConst.MSG, msg);
		if(!isExist){
			record.setOp_id(userId);
			record.setSubco_no(company_id);
			record.setFlag(0);
			recordService.save(record);
		}
		return map;
	}
	
	
	
	@RequestMapping(value = "/record/update", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> update(@RequestBody Record record, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = false;
		String msg = SystemConst.OP_FAILURE;
		try {
			if(record!= null && record.getLoc_id() != null){
				Record old_record =  recordService.get(Record.class, record.getLoc_id());
				Boolean isExist = recordService.is_exist(record);
				if(isExist){
					msg = "位置编号已存在！";
				}else{
					flag = true;
					msg = SystemConst.OP_SUCCESS;
					old_record.setLoc_name(record.getLoc_name());
					old_record.setLocation(record.getLocation());
					old_record.setB_type(record.getB_type());
					old_record.setRemark(record.getRemark());
					recordService.saveOrUpdate(old_record);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;
			msg = SystemConst.OP_FAILURE;
			e.printStackTrace();
		}
		map.put("success", flag);
		map.put(SystemConst.MSG, msg);
		return map;
	}
	
	

	@RequestMapping(value = "/record/delete")
	@LogOperation(description = "删除档案", type = SystemConst.OPERATELOG_DEL, model_id =2)
	public @ResponseBody
	HashMap<String, Object> deleteTasks(@RequestBody List<Long> ids,
			HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("档案删除开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			int result = recordService.delete(ids);
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
			LOGGER.debug("任务单删除结束");
		}
		return resultMap;
	}
	

}

