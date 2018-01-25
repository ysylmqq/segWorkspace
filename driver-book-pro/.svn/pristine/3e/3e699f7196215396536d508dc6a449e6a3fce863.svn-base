package com.chinagps.driverbook.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinagps.driverbook.pojo.AddressBook;
import com.chinagps.driverbook.pojo.ReturnValue;
import com.chinagps.driverbook.service.IContactsService;
import com.chinagps.driverbook.util.RequestUtil;
import com.chinagps.driverbook.util.ResponseUtil;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * 通讯录Controller
 * @author Ben
 * 
 */
@RestController
@RequestMapping(value="/contacts")
public class ContactsController {
	private Logger logger = LoggerFactory.getLogger(ContactsController.class);
	
	@Autowired
	@Qualifier("contactsServiceImpl")
	private IContactsService contactsService;
	
	/**
	 * 通讯录联系人总数接口
	 * @param encryptStr
	 * @param rv
	 * @return
	 */
	@RequestMapping(value="/count")
	public String count(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			AddressBook addressBook = RequestUtil.getParameters(encryptStr, AddressBook.class);
			rv = contactsService.findByCustomerIdAndCallLetter(addressBook, rv);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
	@RequestMapping(value="/backup")
	public String backup(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			Map<String,Object> params = RequestUtil.getGzipParameters(encryptStr, new TypeReference<HashMap<String,Object>>(){ });
			rv = contactsService.backup(params, rv);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
	@RequestMapping(value="/restore")
	public String restore(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			Map<String,Object> params = RequestUtil.getParameters(encryptStr, new TypeReference<HashMap<String,Object>>(){ });
			rv = contactsService.restore(params, rv);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.getGzipEncryptString(rv);
	}
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String search(@RequestParam String unitNumber, @RequestParam(required=false) String contactInfo) {
		String result = "";
		try {
			if (contactInfo != null) {
				contactInfo = new String(contactInfo.getBytes("ISO-8859-1"), "UTF-8");
				result = contactsService.findForSeat(unitNumber, contactInfo);
			} else {
				result = contactsService.isMember(unitNumber);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = "{\"flag\":1}";
		}
		return result;
	}
}
