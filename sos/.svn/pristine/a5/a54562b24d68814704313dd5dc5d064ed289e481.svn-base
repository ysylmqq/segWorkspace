package com.gboss.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gboss.comm.SystemException;
import com.gboss.service.FaultcodesService;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.controller
 * @ClassName:FaultcodesController
 * @Description:TODO
 * @author:bzhang
 * @date:2014-4-28 上午10:54:04
 */
@Controller
public class FaultcodesController extends BaseController {
	@Autowired
	private FaultcodesService faultcodesService;
	
	
	@RequestMapping(value = "/faultcodes/findfaultcodesByPage")
	public @ResponseBody
	Page<HashMap<String, Object>> findTasksByPage(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		Page<HashMap<String, Object>> result = null;
		try {
			if (pageSelect != null) {
				Map map = pageSelect.getFilter();
				if (map == null) {
					map = new HashMap<String, Object>();
				}
				pageSelect.setFilter(map);
			}
			result = faultcodesService.findFaultcodesPage(pageSelect);
		} catch (Exception e) {
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return result;
	}
	
	

}

