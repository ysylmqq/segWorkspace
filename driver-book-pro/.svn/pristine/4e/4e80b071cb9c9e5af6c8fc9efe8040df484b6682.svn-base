package com.chinagps.driverbook.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {
	@RequestMapping(value = "/error", produces = "application/json")
	public Map<String, Object> handle(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", request.getAttribute("javax.servlet.error.status_code"));
		map.put("message", request.getAttribute("javax.servlet.error.message"));
		return map;
	}
}
