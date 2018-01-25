package com.gboss.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.ObdFault;
import com.gboss.service.ObdFaultService;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.controller
 * @ClassName:ObdFaultController
 * @Description:TODO
 * @author:bzhang
 * @date:2014-12-25 下午2:21:10
 */
@Controller
public class ObdFaultController extends BaseController {

	@Autowired
	@Qualifier("obdFaultService")
	private ObdFaultService obdFaultService;

	@RequestMapping(value = "/obd/findObdFaultByPage")
	public @ResponseBody
	Page<HashMap<String, Object>> findObdFaultByPage(
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
			result = obdFaultService.findObdFaultByPage(id, pageSelect);
		} catch (Exception e) {
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return result;
	}
	
	
	@RequestMapping(value = "/obd/downLoadObdExcel")
	public void download(@RequestBody Long file_id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ObdFault obd = obdFaultService.get(ObdFault.class, file_id);
		if(null == obd)
			throw new Exception("参数错误");
		FileInputStream in = null;
		OutputStream out = null;
		File file = new File(obd.getUrl());
		String fileName = file.getName();
		fileName = obd.getFile_name()+ fileName.substring(fileName.lastIndexOf("."));
		// 设置response的编码方式
		response.setContentType("application/octet-stream");
		// 写明要下载的文件的大小
		response.setCharacterEncoding("UTF-8"); 
		response.setContentLength((int)file.length());
		// 解决中文乱码
		fileName = new String(fileName.getBytes("gbk"),"iso-8859-1");
		// 设置响应头为attachment(附件)
		response.setHeader("Content-Disposition",
				"attachment; filename=" + fileName);

		// 输出文件供用户下载
		in = new FileInputStream(file);
		try {
			out = response.getOutputStream();
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0)
				out.write(buf, 0, len);
			try {
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			throw new Exception("下载文件失败！" + e.getMessage(), e);
		} finally {
			try {
				if (in != null)
					in.close();
				if (out != null)
					out.close();
				if (file != null)
					file = null;
			} catch (Exception ex) {
			}
		}
		
	}
	
	
}
