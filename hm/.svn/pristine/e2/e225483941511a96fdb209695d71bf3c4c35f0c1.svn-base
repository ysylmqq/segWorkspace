package com.gboss.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTPClient;
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

import cc.chinagps.lib.util.Config;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.Upgrade;
import com.gboss.pojo.UpgradeFile;
import com.gboss.service.UpgradeFileService;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.controller
 * @ClassName:UpgradeFileController
 * @Description:TODO
 * @author:bzhang
 * @date:2015-1-27 上午11:56:52
 */
@Controller
public class UpgradeFileController extends BaseController {

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(UpgradeFileController.class);

	@Autowired
	@Qualifier("upgradeFileService")
	private UpgradeFileService upgradeFileService;

	@RequestMapping(value = "/upgradeFile/findUpgradeFileByPage")
	public @ResponseBody
	Page<HashMap<String, Object>> findUpgradeFileByPage(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
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
			result = upgradeFileService.getUpgradeFilePage(request, id,
					pageSelect);
		} catch (Exception e) {
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return result;
	}

	@RequestMapping(value = "/upgradeFile/upload", method = RequestMethod.POST)
	@LogOperation(description = "上传升级文件", type = SystemConst.OPERATELOG_ADD, model_id = 20070)
	public void upload(
			@RequestParam(value = "ip", required = false) String ip,
			@RequestParam(value = "port", required = false) Integer port,
			@RequestParam(value = "upgradeFile", required = false) MultipartFile upgradeFile,
			HttpServletRequest request, HttpServletResponse response)
			throws SystemException, IOException {
		String compId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		Long compannyId = compId == null ? null : Long.valueOf(compId);
		String uid = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ID);
		String file_path = Config.getCmdProperties().getProperty("file_path");
		File destFile = new File(file_path + upgradeFile.getOriginalFilename());
		//upgradeFile.transferTo(destFile);
		
		Long userId = Long.valueOf(uid);
		TestFtp testFtp = new TestFtp();
		boolean flag = false;
		String url = Config.getCmdProperties().getProperty("ftp_url");
		String path = Config.getCmdProperties().getProperty("ftp_path");
		String userName = Config.getCmdProperties().getProperty("ftp_username");
		String password = Config.getCmdProperties().getProperty("ftp_password");
		FTPClient ftp = testFtp.getConnectionFTP(url, 21, userName,password);
		if (ftp.isConnected()) {
			testFtp.uploadFile(ftp, path, upgradeFile);
			flag = true;
			// 关闭ftp连接
			testFtp.closeFTP(ftp);
		}
		if (flag) {
			String op_ip = request.getLocalAddr();
			UpgradeFile upgradefile = upgradeFileService.getUpgradeFileByname(upgradeFile.getOriginalFilename());
			if(upgradefile == null){
				upgradefile = new UpgradeFile();
				upgradefile.setFilename(upgradeFile.getOriginalFilename());
				upgradefile.setPort(port);
				upgradefile.setIp(ip);
				upgradefile.setOp_ip(op_ip);
				upgradefile.setSubco_no(compannyId);
				upgradefile.setOp_id(userId);
				upgradeFileService.save(upgradefile);
			}else{
				upgradefile.setFilename(upgradeFile.getOriginalFilename());
				upgradefile.setPort(port);
				upgradefile.setIp(ip);
				upgradefile.setOp_ip(op_ip);
				upgradeFileService.update(upgradefile);
			}
			
		}

		OutputStream out = null;
		try {
			out = response.getOutputStream();
			String str = "<script>parent.callback('" + true + "');</script>";
			out.write(str.getBytes());
			out.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		}
	}

	@RequestMapping(value = "/upgradeFile/getFile")
	public @ResponseBody
	UpgradeFile getFile(Long id, HttpServletRequest request)
			throws SystemException {
		UpgradeFile results = null;
		try {
			results = upgradeFileService.get(UpgradeFile.class, id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return results;
	}

	@RequestMapping(value = "/upgradeFile/getUpgradeFileList", method = RequestMethod.POST)
	public @ResponseBody
	List<UpgradeFile> getupgradeList(@RequestBody Upgrade upgrade,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		List<UpgradeFile> upgradeFileList = upgradeFileService
				.getUpgradeFileList();
		return upgradeFileList;
	}
	
	
	@RequestMapping(value = "/upgradeFile/delete")
	public @ResponseBody
	HashMap<String, Object> operateCombo(
			@RequestParam(value = "ids[]", required = false) List<Long> ids,
			Integer type, HttpServletRequest request) throws SystemException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			TestFtp testFtp = new TestFtp();
			String url = Config.getCmdProperties().getProperty("ftp_url");
			String path = Config.getCmdProperties().getProperty("ftp_path");
			String userName = Config.getCmdProperties().getProperty("ftp_username");
			String password = Config.getCmdProperties().getProperty("ftp_password");
			FTPClient ftp = testFtp.getConnectionFTP(url, 21, userName,password);
			for (Long id : ids) {
				UpgradeFile file = upgradeFileService.get(UpgradeFile.class, id);
				if (ftp.isConnected()) {
					testFtp.deleteFile(ftp, path, file.getFilename());
					upgradeFileService.delete(UpgradeFile.class, id);
				}
			}
			testFtp.closeFTP(ftp);
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


}
