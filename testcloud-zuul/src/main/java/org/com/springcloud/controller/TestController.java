package org.com.springcloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping(value="/login")
	public String login(){
		return "login";
	}
	
	@GetMapping(value="/local")
	public String local(){
		return "local";
	}
}
