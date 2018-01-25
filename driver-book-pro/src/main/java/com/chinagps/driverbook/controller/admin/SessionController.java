package com.chinagps.driverbook.controller.admin;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value="/admin")
public class SessionController {
	private static Logger logger = LoggerFactory.getLogger(SessionController.class);
	
	@RequestMapping(value="/signin", method = RequestMethod.GET)
	public String index() {
		return "/admin/signin";
	}
	
	@RequestMapping(value="/signin", method = RequestMethod.POST)
	public String _new(String loginName, String password, @RequestParam("checkCode") String checkCode, HttpSession session, RedirectAttributes redirectAttributes) {
		String ckcode = (String) session.getAttribute("checkCode");
		try {
			if (ckcode != null) {
				if (ckcode.equalsIgnoreCase(checkCode)) {
					if (!"admin".equals(loginName)) {
						redirectAttributes.addFlashAttribute("message", "用户不存在！");
						return "redirect:/admin/signin";
					} else {
						if("123456".equals(password)){
							session.setAttribute("loginName", loginName);
							return "redirect:/admin";
						} else {
							redirectAttributes.addFlashAttribute("message", "密码不正确！");
							return "redirect:/admin/signin";
						}
					}
				} else {
					redirectAttributes.addFlashAttribute("message", "验证码不正确！");
					return "redirect:/admin/signin";
				}
			} else {
				redirectAttributes.addFlashAttribute("message", "验证码失效！");
				return "redirect:/admin/signin"; 
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			redirectAttributes.addFlashAttribute("message", "系统内部错误，请联系管理员！");
			return "redirect:/signin";
		}
	}
	
	@RequestMapping(value = "/signout", method=RequestMethod.GET)
	public String destroy(HttpSession session, RedirectAttributes redirectAttributes) {
		try {
			session.invalidate();
			return "redirect:/admin/signin";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			redirectAttributes.addFlashAttribute("message", "系统内部错误，请联系管理员！");
			return "redirect:/";
		}
	}
	
}
