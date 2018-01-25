package com.chinagps.driverbook.service;

import java.util.Map;

import com.chinagps.driverbook.pojo.AddressBook;
import com.chinagps.driverbook.pojo.ReturnValue;

public interface IContactsService extends BaseService<AddressBook> {
	
	public String isMember(String unitNumber) throws Exception;

	/**
	 * 为坐席查找通讯录信息
	 * @param unitNumber 车台呼号
	 * @param contactInfo 姓名/电话号码
	 * @return
	 * @throws Exception
	 */
	public String findForSeat(String unitNumber, String contactInfo) throws Exception;
	
	public ReturnValue findByCustomerIdAndCallLetter(AddressBook addressBook, ReturnValue rv) throws Exception;
	
	public ReturnValue backup(Map<String, Object> params, ReturnValue rv) throws Exception;
	
	public ReturnValue restore(Map<String, Object> params,ReturnValue rv) throws Exception;
	
}
