package com.chinagps.driverbook.controller.admin;

import org.springframework.web.servlet.ModelAndView;

public class BaseController {
	
	protected ModelAndView ajaxDone(int statusCode, String message, String confirmMsg, String navTabId, String rel, String callbackType, String forwardUrl) {
		ModelAndView mav = new ModelAndView("/admin/ajaxDone");
		mav.addObject("statusCode", statusCode);
		mav.addObject("message", message);
		mav.addObject("confirmMsg", confirmMsg);
		mav.addObject("rel", rel);
		mav.addObject("callbackType", callbackType);
		mav.addObject("forwardUrl", forwardUrl);
		return mav;
	}
	
	protected ModelAndView ajaxDoneSuccess(String message) {
		return ajaxDone(200, message, "", "", "", "", "");
	}

	protected ModelAndView ajaxDoneError(String message) {
		return ajaxDone(300, message, "", "", "", "", "");
	}
	
	protected ModelAndView dialogAjaxDoneSuccess(String message, String navTabId) {
		return ajaxDone(200, message, "", navTabId, "", "closeCurrent", "");
	}
	
}
