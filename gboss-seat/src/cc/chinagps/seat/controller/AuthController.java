package cc.chinagps.seat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.chinagps.seat.auth.NoSuchUserException;
import cc.chinagps.seat.auth.User;
import cc.chinagps.seat.bean.SeatUser;
import cc.chinagps.seat.service.LDAPService;
import cc.chinagps.seat.util.Consts;

import com.googlecode.jsonplugin.JSONException;
import com.googlecode.jsonplugin.JSONUtil;

@Controller
public class AuthController extends BasicController {
	
	//@Autowired
	private LDAPService ldapService;
	
	@RequestMapping("/auth")
	@ResponseBody
	public String authenticate(
			@RequestParam String loginName,
			@RequestParam String password,
			HttpServletRequest request,
			Locale locale) 
			throws JSONException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", true);
		if (loginName.equals(getLoginUser(request).getLoginName())) {
			resultMap.put("success", false);
			resultMap.put("message", getText("Authenticate_1",
					locale));
		} else {
			int result = ldapService.authenticate(loginName, password);  // 0 2  
			if (result != Consts.SUCCEED) {
				resultMap.put("success", false);
				resultMap.put("message", getText("Authenticate_" + result, 
						locale));
			}
		}
		return JSONUtil.serialize(resultMap);
	}
	
	@RequestMapping("/user")
	@ResponseBody
	public String getUser(
			HttpServletRequest request) 
			throws JSONException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			User user = getLoginUser(request);
			resultMap.put("success", true);
			resultMap.put("user", user);
		} catch (NoSuchUserException e) {
			resultMap.put("success", false);
			resultMap.put("message", e.getMessage());
		}
		return JSONUtil.serialize(resultMap);
	}
	
	@RequestMapping("/users")
	@ResponseBody
	public String getAllUsers() throws JSONException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<SeatUser> userList = ldapService.getOperatorList();
		resultMap.put("success", true);
		resultMap.put("data", userList);
		return JSONUtil.serialize(resultMap);
	}
}
