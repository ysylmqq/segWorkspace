package com.gboss.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
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
import org.springframework.web.multipart.MultipartFile;

import com.gboss.comm.InitHelper;
import com.gboss.comm.SystemConfig;
import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.Preload;
import com.gboss.service.PreloadService;
import com.gboss.util.DateUtil;
import com.gboss.util.JsonUtil;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.excel.CreateExcel_PDF_CSV;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.controller
 * @ClassName:PreloadController
 * @Description:TODO
 * @author:bzhang
 * @date:2014-10-9 下午3:29:24
 */
@Controller
public class PreloadController extends BaseController {

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(PreloadController.class);

	@Autowired
	@Qualifier("preloadService")
	private PreloadService preloadService;

	@Autowired
	private SystemConfig systemconfig;

	@RequestMapping(value = "/preload/add")
	@LogOperation(description = "添加SIM卡", type = SystemConst.OPERATELOG_ADD, model_id = 20100)
	public @ResponseBody
	HashMap<String, Object> add(@RequestBody Preload preload,
			BindingResult bindingResult, HttpServletRequest request) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			// 将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY,
					JsonUtil.oToJ(preload, true));
			String compannyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			Long id = compannyId == null ? null : Long.valueOf(compannyId);
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			if (StringUtils.isNotBlank(userId)) {
				preload.setOp_id(Long.valueOf(userId));
			}
			// 新增SIM卡默认截止时间等于开始时间
			preload.setE_date(preload.getS_date());
			preload.setSubco_no(id);
			int result = preloadService.addSim(preload);
			if (result == 0) {
				preload.setCombo_id(1L);
				preloadService.save(preload);
			} else if (result == 1) {
				flag = false;
				msg = "呼号已存在！";
			} else if (result == 2) {
				flag = false;
				msg = "TBOX条码已存在！";
			} else if (result == 3) {
				flag = false;
				msg = "车架号已存在！";
			} else if (result == 4) {
				flag = false;
				msg = "IMEI/MEID已存在！";
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

	@RequestMapping(value = "/preload/modifyCard")
	@LogOperation(description = "补卡", type = SystemConst.OPERATELOG_UPDATE, model_id = 200101)
	public @ResponseBody
	HashMap<String, Object> modifyCard(@RequestBody Preload preload,
			BindingResult bindingResult, HttpServletRequest request) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			// 将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY,
					JsonUtil.oToJ(preload, true));
			int result = preloadService.addSim(preload);
			if (result == 1) {
				flag = false;
				msg = "呼号已存在！";
			} else if (result == 2) {
				flag = false;
				msg = "TBOX条码已存在！";
			} else if (result == 3) {
				flag = false;
				msg = "车架号已存在！";
			} else if (result == 4) {
				flag = false;
				msg = "IMEI/MEID已存在！";
			} else if (result == 0) {
				Preload sm = preloadService.get(Preload.class,
						preload.getSim_id());
				if (sm != null) {
					sm.setAkey(preload.getAkey());
					sm.setTelco(preload.getTelco());
					sm.setW_user(preload.getW_user());
					sm.setW_password(preload.getW_password());
					sm.setEsn(preload.getEsn());
					sm.setIccid(preload.getIccid());
					sm.setImsi(preload.getImsi());
					sm.setCall_letter(preload.getCall_letter());
					sm.setImei(preload.getImei());
					sm.setS_date(preload.getS_date());
					sm.setRemark(preload.getRemark());
					preloadService.update(sm);
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

	@RequestMapping(value = "/preload/update")
	@LogOperation(description = "修改SIM卡", type = SystemConst.OPERATELOG_UPDATE, model_id = 200101)
	public @ResponseBody
	HashMap<String, Object> update(@RequestBody Preload preload,
			BindingResult bindingResult, HttpServletRequest request) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			// 将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY,
					JsonUtil.oToJ(preload, true));
			int result = preloadService.addSim(preload);
			if (result == 1) {
				flag = false;
				msg = "呼号已存在！";
			} else if (result == 2) {
				flag = false;
				msg = "TBOX条码已存在！";
			} else if (result == 3) {
				flag = false;
				msg = "车架号已存在！";
			} else if (result == 4) {
				flag = false;
				msg = "IMEI/MEID已存在！";
			} else if (result == 0) {
				Preload sm = preloadService.get(Preload.class,
						preload.getSim_id());
				if (sm != null) {
					sm.setAkey(preload.getAkey());
					sm.setTelco(preload.getTelco());
					sm.setW_user(preload.getW_user());
					sm.setW_password(preload.getW_password());
					sm.setEsn(preload.getEsn());
					sm.setIccid(preload.getIccid());
					sm.setImsi(preload.getImsi());
					sm.setCall_letter(preload.getCall_letter());
					sm.setImei(preload.getImei());
					sm.setS_date(preload.getS_date());
					// 默认缴费开始时间等于结束时间
					sm.setE_date(preload.getS_date());
					// 终端
					sm.setBarcode(preload.getBarcode());
					sm.setUnittype_id(preload.getUnittype_id());
					// 车辆
					sm.setVin(preload.getVin());
					sm.setEngine_no(preload.getEngine_no());
					sm.setColor(preload.getColor());
					sm.setProduction_date(preload.getProduction_date());
					sm.setRemark(preload.getRemark());
					preloadService.update(sm);
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

	@RequestMapping(value = "/preload/findPreloadByPage")
	public @ResponseBody
	Page<HashMap<String, Object>> findPreloadByPage(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询SIM卡开始");
		}
		Page<HashMap<String, Object>> result = null;
		try {
			String compannyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			Long id = compannyId == null ? null : Long.valueOf(compannyId);
			if (pageSelect != null) {
				Map map = pageSelect.getFilter();
				if (map == null) {
					map = new HashMap<String, Object>();
				}
				pageSelect.setFilter(map);
			}
			result = preloadService.findSimByPage(id, pageSelect);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询SIM卡结束");
		}
		return result;
	}

	@RequestMapping(value = "/preload/operate")
	@LogOperation(description = "SIM卡状态更改", type = SystemConst.OPERATELOG_UPDATE, model_id = 200101)
	public @ResponseBody
	HashMap<String, Object> operate(
			@RequestParam(value = "ids[]", required = false) List<Long> ids,
			Integer type, HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("SIM卡状态更改卡开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			int result = preloadService.operate(ids, type);
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
			LOGGER.debug("SIM卡状态更改结束");
		}
		return resultMap;
	}

	@RequestMapping(value = "/preload/operateCombo")
	@LogOperation(description = "SIM卡套餐状态更改", type = SystemConst.OPERATELOG_UPDATE, model_id = 200101)
	public @ResponseBody
	HashMap<String, Object> operateCombo(
			@RequestParam(value = "ids[]", required = false) List<Long> ids,
			Integer type, HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("SIM卡状态更改卡开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			int result = preloadService.operateCombo(ids, type);
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
			LOGGER.debug("SIM卡状态更改结束");
		}
		return resultMap;
	}

	/**
	 * 车辆信息excel上传处理
	 * 
	 * @param paytxtFiles
	 * @param vehicle
	 * @param bindResult
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/preload/importSim")
	public void importSim(@RequestParam("sim_file") MultipartFile sim_file,
			@RequestParam(required = false) Boolean isOverride, Integer type,
			Integer telco, HttpServletRequest request,
			HttpServletResponse response) throws SystemException, IOException {
		String compId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		Long compannyId = compId == null ? null : Long.valueOf(compId);
		String uid = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ID);
		Long userId = Long.valueOf(uid);
		String compCode = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYCODE);
		String compName = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYNAME);
		Map<String, Object> map = new HashMap<String, Object>();
		long size = sim_file.getSize();
		if (size > 10000000) {
			map.put("success", false);
			map.put("msg", "上传文件不能超过10M");
		} else {
			// excel上传保存路径
			String path = systemconfig.getExcelUploadPath();
			File fileDir = new File(path);
			if (!fileDir.exists()) { // 判断路径是否存在，不存在则创建
				fileDir.mkdirs();
			}
			// 保存文件和数据
			File descFile = new File(fileDir, sim_file.getOriginalFilename());
			FileUtils
					.copyInputStreamToFile(sim_file.getInputStream(), descFile);
			boolean remark = CreateExcel_PDF_CSV.checkExcel(descFile);
			if (remark) {
				List<String[]> dataList = CreateExcel_PDF_CSV.getData(descFile);
				// 解析并保存用户数据
				map = preloadService.importSim(dataList, compannyId, userId,
						type, telco);
			} else {
				map.put("success", false);
				map.put("msg", "请导入相应格式的excel文件！");
			}
			OutputStream out = response.getOutputStream();
			InitHelper.initConfCache();
			String str = "<script>parent.callback('" + map.get("msg") + "',"
					+ map.get("success") + ");</script>";
			out.write(str.getBytes("utf-8"));
			out.flush();
			out.close();
			// 删除上传文件
			descFile.delete();
		}
	}

	@RequestMapping(value = "/preload/updateCombo", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> updateCombo(@RequestBody Preload preload,
			BindingResult bindingResult, HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Preload sim = preloadService.get(Preload.class, preload.getSim_id());
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		if (sim != null) {
			// sim.setNew_combo_id(preload.getCombo_id());
			sim.setCombo_id(preload.getCombo_id());
			sim.setCombo_status(SystemConst.SIM_COMBO_UNNORMAL);
			preloadService.update(sim);
		} else {
			flag = false;
			msg = SystemConst.OP_FAILURE;
		}
		map.put("success", flag);
		map.put("msg", msg);
		return map;
	}

	@RequestMapping(value = "/preload/getSimMsg")
	public @ResponseBody
	HashMap<String, Object> getComboMsg(Long sim_id, HttpServletRequest request)
			throws SystemException {
		HashMap<String, Object> results = new HashMap<String, Object>();
		try {
			Preload sim = preloadService.get(Preload.class, sim_id);
			results.put("s_date",
					DateUtil.format(sim.getS_date(), DateUtil.YMD_DASH));
			results.put("e_date",
					DateUtil.format(sim.getE_date(), DateUtil.YMD_DASH));
		} catch (Exception e) {
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return results;
	}

}
