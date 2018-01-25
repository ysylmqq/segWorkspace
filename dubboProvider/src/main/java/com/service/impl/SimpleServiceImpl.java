package com.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.entity.Simple;
import com.service.SimpleService;


@Service("simpleService")
@com.alibaba.dubbo.config.annotation.Service(interfaceClass=com.service.SimpleService.class, protocol={"dubbo"}, retries=0)
public class SimpleServiceImpl implements SimpleService{

	@Override
	public String sayHello(String name) {
		System.err.println(" 我是服务端  本机 ");
		return "hello" + name;
	}

	@Override
	public Simple getSimple() {
		System.err.println("我是 provider  本机");
        Map<String,Integer> map = new HashMap<String, Integer>(2);  
        map.put("zhang0", 1);  
        map.put("zhang1", 2);  
        return new Simple("zhang3", 21, map);
	}

}
