package com.gboss.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.Pack;
import com.gboss.service.PackService;
import com.gboss.util.JsonUtil;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.controller
 * @ClassName:PackController
 * @Description:TODO
 * @author:bzhang
 * @date:2014-11-5 下午4:01:23
 */
@Controller
public class PackController extends BaseController {

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(PackController.class);

	@Autowired
	@Qualifier("packService")
	private PackService packService;

	/**
	 * 分页查询服务包
	 * 
	 * @param pageSelect
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping("/pack/findPackPage")
	public @ResponseBody
	Page<HashMap<String, Object>> findPackPage(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询服务包开始");
		}
		Page<HashMap<String, Object>> result = null;
		try {
			String compannyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			Long sub_co = compannyId == null ? null : Long.valueOf(compannyId);
			if (pageSelect != null) {
				if (pageSelect.getFilter() == null) {
					pageSelect.setFilter(new HashMap<String, Object>());
				}
			}
			result = packService.findPackPage(sub_co, pageSelect);
		} catch (Exception e) {
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询保单结束");
		}
		return result;
	}

	@RequestMapping(value = "/pack/add", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> add(@RequestBody Pack pack,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {

			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			if (StringUtils.isNotBlank(userId)) {
				pack.setOp_id(Long.valueOf(userId));
			}
			String compannyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			Long id = compannyId == null ? null : Long.valueOf(compannyId);
			pack.setSubco_no(id);
			int result = packService.addPack(pack);
			if (result == 2) {
				flag = false;
				msg = "服务包名称为[" + pack.getPack_name() + "]的已存在";
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg = SystemConst.OP_FAILURE;
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		return resultMap;
	}

	@RequestMapping(value = "/pack/update")
	@LogOperation(description = "修改服务包", type = SystemConst.OPERATELOG_UPDATE,model_id=20091)
	public @ResponseBody
	HashMap<String, Object> updateCombo(@RequestBody Pack pack,
			BindingResult bindingResult, HttpServletRequest request) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			// 将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY,
					JsonUtil.oToJ(pack, true));
			if (packService.isExist(pack)) {
				flag = false;
				msg = "服务包名称为[" + pack.getPack_name() + "]的已存在";
			} else {
				Pack pk = packService.get(Pack.class, pack.getPack_id());
				if (pk != null) {
					pk.setFee_cycle(pack.getFee_cycle());
					pk.setPack_name(pack.getPack_name());
					pk.setPrice(pack.getPrice());
					pk.setCombo_id(pack.getCombo_id());
					packService.update(pk);
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg = SystemConst.OP_FAILURE;
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		return resultMap;
	}

	@RequestMapping(value = "/pack/delete")
	@LogOperation(description = "删除服务包", type = SystemConst.OPERATELOG_DEL,model_id=20092)
	public @ResponseBody
	HashMap<String, Object> delete(
			@RequestParam(value = "ids[]", required = false) List<Long> ids,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("删除服务包开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			int result = packService.delete(ids);
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
			LOGGER.debug("删除服务包结束");
		}
		return resultMap;
	}

}
