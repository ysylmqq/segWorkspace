package com.chinagps.driverbook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.chinagps.driverbook.dao.LinkmanMapper;
import com.chinagps.driverbook.pojo.Linkman;
import com.chinagps.driverbook.service.ILinkmanService;

@Service
@Scope("prototype")
public class LinkmanServiceImpl extends BaseServiceImpl<Linkman> implements ILinkmanService {
	
	@Autowired
	private LinkmanMapper linkmanMapper;
	
	public Linkman findByCustomerId(Long customerId) throws Exception {
		return linkmanMapper.findByCustomerId(customerId);
	}
	
}
