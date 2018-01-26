package cc.chinagps.seat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.chinagps.seat.bean.table.CommandTable;
import cc.chinagps.seat.service.CmdService;
import cc.chinagps.seat.util.Consts;

import com.googlecode.jsonplugin.JSONException;
import com.googlecode.jsonplugin.JSONUtil;

@Controller
@RequestMapping("/cmd")
public class CmdController extends BasicController {
	
	//@Autowired
	private CmdService cmdService;
	
	@ResponseBody
	@RequestMapping("cmdlist")
	public String getCmdList(
			@RequestParam String param, 
			@RequestParam int type,
			Locale locale) 
					throws JSONException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", false);
		if (param.equals("")) {
			resultMap.put("message", getText("wrong.param", 
					locale));
		}
		if (type != Consts.TYPE_PHONENO && 
				type != Consts.TYPE_VEHICLEID) {
			resultMap.put("message", getText("wrong.type", 
					locale));
		} else {
			List<CommandTable> cmdList = cmdService.getCmdList(param, type);
			resultMap.put("success", true);
			resultMap.put("command", cmdList);
		}
		
		return JSONUtil.serialize(resultMap);
	}
}
