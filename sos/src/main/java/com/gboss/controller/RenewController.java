package com.gboss.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ldap.objectClasses.CommonCompany;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gboss.comm.SystemConfig;
import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.UnitType;
import com.gboss.service.BoundService;
import com.gboss.service.RenewService;
import com.gboss.service.UnitService;
import com.gboss.service.UnitTypeService;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.Utils;
import com.gboss.util.excel.CreateExcel_PDF_CSV;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.controller
 * @ClassName:RenewController
 * @author:hansm
 * @date:2016-6-12 下午2:51:12
 */
@Controller
public class RenewController extends BaseController {
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(RenewController.class);

	@Autowired
	private RenewService renewService;
	
	@Autowired
	private SystemConfig systemconfig;

	@RequestMapping(value = "/renew/findHmTBoxRenew")
	@LogOperation(description = "海马TBox续费列表查询", type = SystemConst.OPERATELOG_SEARCHKEY, model_id =20082)
	public @ResponseBody
	Page<HashMap<String, Object>> findHmTBoxRenew(@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		Page<HashMap<String, Object>> results = null;
		try {
			//subcoNo参数暂时固定为海马201，通过界面传递
			results = renewService.findHmTBoxRenewBypage(pageSelect);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印日志
			e.printStackTrace();
			// 同时把异常抛到前台
			//throw new SystemException();
		}
		return results;
	}
	
	/**
	 * 海马TBOX续费表单导入
	 * @param tbox_file
	 * @param request
	 * @param response
	 * @throws SystemException
	 * @throws IOException
	 */
	@RequestMapping(value = "/renew/importTBOXFile")
	@LogOperation(description = "海马TBOX续费表单导入", type = SystemConst.OPERATELOG_ADD, model_id =20100)
	public @ResponseBody Map<String, Object> importTBOXFile(@RequestParam("cv_file") MultipartFile cv_file, HttpServletRequest request, 
			HttpServletResponse response) throws SystemException, IOException {
		String compId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		Long compannyId = compId == null ? null : Long.valueOf(compId);
		String uid = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_ID);
		String companyCode = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYCODE);
		String companyName = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYNAME);
		Long userId = Long.valueOf(uid);
		long size = cv_file.getSize();
		Map<String, Object> map = new HashMap<String, Object>();
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
			File descFile = new File(fileDir, cv_file.getOriginalFilename());
			FileUtils.copyInputStreamToFile(cv_file.getInputStream(), descFile);
			List<String[]> dataList = CreateExcel_PDF_CSV.getData(descFile, 13);
			map = renewService.importHMTBOX(dataList, compannyId, userId, companyName, companyCode);
			// 删除上传文件
			descFile.delete();
		}
		return map;
	}
	
	/**
	 * 海马TBOX续费资料导入查询
	 * @param pageSelect
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/renew/findTBOXByPage", method = RequestMethod.POST)
	public @ResponseBody Page<Map<String, Object>> findTBOXByPage(@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		Page<Map<String, Object>> result = null;
		try{
			String compannyId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
			if (pageSelect != null) {
				Map<String, Object> map = pageSelect.getFilter();
				if (map == null) {
					map = new HashMap<String, Object>();
				}
				map.put("subco_no", compannyId);
				pageSelect.setFilter(map);
			}
			result = renewService.findTBOXByPage(pageSelect);
		}catch(Exception e){
			LOGGER.info("查询海马TBOX续费表单错误，原因:"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 海马TBOX续费历史资料查询
	 * @param pageSelect
	 * @param request
	 * @return
	 * @throws SystemException
	 */
	@RequestMapping(value = "/renew/findHistoryRenew", method = RequestMethod.POST)
	@LogOperation(description = "海马TBox续费列表查询", type = SystemConst.OPERATELOG_SEARCHKEY, model_id =20110)
	public @ResponseBody
	Page<HashMap<String, Object>> findHistoryRenew(@RequestBody PageSelect<Map<String, Object>> pageSelect,
			String vin,HttpServletRequest request) throws SystemException {
		Page<HashMap<String, Object>> results = null;
		try {
			
			results = renewService.findHistoryRenew(pageSelect,vin);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印日志
			e.printStackTrace();
			// 同时把异常抛到前台
			//throw new SystemException();
		}
		return results;
	}
}
