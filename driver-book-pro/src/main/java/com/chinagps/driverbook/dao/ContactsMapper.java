package com.chinagps.driverbook.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chinagps.driverbook.pojo.AddressBook;
import com.chinagps.driverbook.pojo.Contact;

public interface ContactsMapper extends BaseSqlMapper<AddressBook> {
	
	public List<Map<String, String>> findForSeatWithCallLetter(Map<String, String> parameterMap);
	
	public List<Map<String, String>> findForSeat(Map<String, String> parameterMap);

	public AddressBook findByCustomerIdAndCallLetter(AddressBook addressBook);
	
	public int addUserContact(AddressBook addressBook);
	
	public int removeUserContact(AddressBook addressBook);
	
	public int addContactList(List<Contact> contactList);
	
	public int countByCustomerId(String customerId);
	
	public List<Map<String, String>> findContactKeys(String uploadVersion, RowBounds rowBounds);
	
	public List<HashMap<String, String>> findContacts(List<Map<String, String>> parameterList);
	
}
