package com.chinagps.driverbook.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.chinagps.driverbook.dao.ContactsMapper;
import com.chinagps.driverbook.dao.CustomerMapper;
import com.chinagps.driverbook.pojo.AddressBook;
import com.chinagps.driverbook.pojo.Contact;
import com.chinagps.driverbook.pojo.Customer;
import com.chinagps.driverbook.pojo.ReturnValue;
import com.chinagps.driverbook.service.IContactsService;
import com.chinagps.driverbook.util.CipherTool;
import com.chinagps.driverbook.util.PropertyUtil;
import com.chinagps.driverbook.util.RequestUtil;
import com.chinagps.driverbook.util.SGErrorInfoConstants;
import com.chinagps.driverbook.util.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Scope("prototype")
public class ContactsServiceImpl extends BaseServiceImpl<AddressBook> implements IContactsService {
	private static Logger logger = LoggerFactory.getLogger(ContactsServiceImpl.class);

	@Autowired
	private ContactsMapper contactsMapper;
	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private RestTemplate rest;
	
	public String isMember(String unitNumber) throws Exception {
		Customer customer = customerMapper.findByCallLetter(unitNumber);
		if (customer == null) {
			String form = CipherTool.getCipherString("{\"callLetter\":\""+ unitNumber +"\"}");
			String url = PropertyUtil.getPropertyValue("interface.properties", "ice.isMember");
			String response = rest.postForObject(url, form, String.class);
			ReturnValue rv = RequestUtil.getParameters(response, ReturnValue.class);
			if (rv.isSuccess()) {
				return "{\"flag\":1}";
			} else {
				return "{\"flag\":0}";
			}
		} else {
			return "{\"flag\":1}";
		}
	}
	
	public String findForSeat(String unitNumber, String contactInfo) throws Exception {
		Map<String, String> parameterMap = new HashMap<String, String>();
		if (!StringUtils.isNumeric(contactInfo)) {
			parameterMap.put("contactName", contactInfo);
		} else {
			parameterMap.put("contactInfo", contactInfo);
		}
		parameterMap.put("callLetter", unitNumber);
		
		// 当成老车台进行查询
		List<Map<String, String>> result = contactsMapper.findForSeatWithCallLetter(parameterMap);
		// 如果无数据，再按新车台进行查询
		if (result == null || result.size() == 0) {
			result = contactsMapper.findForSeat(parameterMap);
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("flag", 0);
		resultMap.put("data", result);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(resultMap);
	}

	public ReturnValue findByCustomerIdAndCallLetter(AddressBook addressBook, ReturnValue rv) throws Exception {
		AddressBook ab = contactsMapper.findByCustomerIdAndCallLetter(addressBook);
		rv.setDatas(ab);
		return rv;
	}

	public ReturnValue backup(Map<String, Object> params, ReturnValue rv) throws Exception {
		// 用户ID
		String customerId = null;
		String callLetter = null;
		// 上传版本号，手机通讯录版本号
		String uploadVersion = null, contactVersion = null;
		// 终端类型，当前页码，每页条数，总页码数
		int deviceType = 0, curPageNo = 0, pageSize = 0, rowsCount = 0, pageCount = 0;

		try {
			if (params != null) {
				if (params.containsKey("customerId")) {
					if (StringUtils.isNotBlank(params.get("customerId").toString())
							&& StringUtils.isNumeric(params.get("customerId").toString())) {
						customerId = params.get("customerId").toString();
					}
				}
				if (params.containsKey("callLetter")) {
					callLetter = params.get("callLetter").toString();
				}
				if (params.containsKey("curPageNo")
						&& StringUtils.isNotBlank(params.get("curPageNo").toString())
						&& StringUtils.isNumeric(params.get("curPageNo").toString())) {
					curPageNo = Integer.valueOf(params.get("curPageNo").toString());
				}
				if (params.containsKey("uploadVersion")) {
					uploadVersion = params.get("uploadVersion").toString();
				}
				if (params.containsKey("contactVersion")) {
					contactVersion = params.get("contactVersion").toString();
				}
				if (params.containsKey("deviceType")
						&& StringUtils.isNotBlank(params.get("deviceType").toString())
						&& StringUtils.isNumeric(params.get("deviceType").toString())) {
					deviceType = Integer.valueOf(params.get("deviceType").toString());
				}
				if (params.containsKey("pageSize")
						&& StringUtils.isNotBlank(params.get("pageSize").toString())
						&& StringUtils.isNumeric(params.get("pageSize").toString())) {
					pageSize = Integer.valueOf(params.get("pageSize").toString());
				}
				if (params.containsKey("rowsCount")
						&& StringUtils.isNotBlank(params.get("rowsCount").toString())
						&& StringUtils.isNumeric(params.get("rowsCount").toString())) {
					rowsCount = Integer.valueOf(params.get("rowsCount").toString());
				}
				if (params.containsKey("pageCount")
						&& StringUtils.isNotBlank(params.get("pageCount").toString())
						&& StringUtils.isNumeric(params.get("pageCount").toString())) {
					pageCount = Integer.valueOf(params.get("pageCount").toString());
				}
				// 先判断分页信息和用户ID及版本号是否上传
				if (uploadVersion == null || customerId == null || curPageNo == 0) {
					rv.setErrorCode(SGErrorInfoConstants.SG_CODE_401);
					rv.setErrorMsg(SGErrorInfoConstants.SG_MSG_401);
					return rv;
				}
				logger.info("用户云备份操作,当前用户为：" + customerId + ",版本号为:"
						+ uploadVersion + ",总共上传联系人个数:" + rowsCount + ",当前页码:"
						+ curPageNo + ",每页个数为:" + pageSize + ",总计页数:"
						+ pageCount);
				List<Contact> contacts = new ArrayList<Contact>();// 联系人联系记录
				// 如果是第一页，先保存用户版本信息
				if (curPageNo == 1) {
					AddressBook addressBook = new AddressBook();
					addressBook.setCustomerId(customerId);
					addressBook.setDeviceType(deviceType);
					addressBook.setUploadVersion(uploadVersion);
					addressBook.setCallLetter(callLetter);
					if (contactsMapper.addUserContact(addressBook) < 1) {
						rv.saveErrror();
						return rv;
					}
				}
				// 保存联系人数据
				if (params.containsKey("data")) {
					@SuppressWarnings("unchecked")
					ArrayList<HashMap<String, Object>> datas = (ArrayList<HashMap<String, Object>>) params.get("data");
					if (datas != null && datas.size() > 0) {
						for (HashMap<String, Object> data : datas) {
							String contactName = data.get("n").toString();
							String contactKey = data.get("k").toString();
							// 解析联系人的多个联系方式
							@SuppressWarnings("unchecked")
							List<HashMap<String, Object>> l = (List<HashMap<String, Object>>) data.get("cs");
							if (l != null && l.size() > 0) {
								for (HashMap<String, Object> c : l) {
									Contact pojo = new Contact();
									pojo.setContactInfo(c.get("c").toString());
									pojo.setContactKey(contactKey);
									pojo.setContactName(contactName);
									pojo.setContactVersion(contactVersion);
									pojo.setLabel(c.get("l").toString());
									pojo.setMinitype(Integer.parseInt(c.get("m").toString()));
									pojo.setType(Integer.parseInt(c.get("t").toString()));
									pojo.setUploadVersion(uploadVersion);
									// 批存储联系人信息
									contacts.add(pojo);
								}
							} else {
								Contact pojo = new Contact();
								pojo.setContactInfo("");
								pojo.setContactKey(contactKey);
								pojo.setContactName(contactName);
								pojo.setContactVersion(contactVersion);
								pojo.setLabel("");
								pojo.setMinitype(1);
								pojo.setType(1);
								pojo.setUploadVersion(uploadVersion);
								// 批存储联系人信息
								contacts.add(pojo);
							}
						}

						int num = contactsMapper.addContactList(contacts);
						if (num == contacts.size()) {
							rv.setSuccess(true);
							logger.info("用户云备份操作,当前用户为：" + customerId
									+ ",版本号为:" + uploadVersion + ",总共上传联系人个数:"
									+ rowsCount + ",当前页码:" + curPageNo
									+ ",每页个数为:" + pageSize + ",总计页数:"
									+ pageCount
									+ ",操作成功!!!!!!!!!!!!!!!!!!!!!!!!!!");
						} else {
							rv.saveErrror();
							logger.info("用户云备份操作,当前用户为：" + customerId
									+ ",版本号为:" + uploadVersion + ",总共上传联系人个数:"
									+ rowsCount + ",当前页码:" + curPageNo
									+ ",每页个数为:" + pageSize + ",总计页数:"
									+ pageCount
									+ ",操作失败!!!!!!!!!!!!!!!!!!!!!!!!!!入库数量;"
									+ num);
						}
					} else {
						rv.setErrorCode(SGErrorInfoConstants.SG_CODE_405);
						rv.setErrorMsg(SGErrorInfoConstants.SG_MSG_405);
					}
				}
			} else {
				rv.setErrorCode(SGErrorInfoConstants.SG_CODE_406);
				rv.setErrorMsg(SGErrorInfoConstants.SG_MSG_406);
			}
		} catch (Exception e) {
			logger.error("用户云备份操作,当前用户为：" + customerId + ",版本号为:"
					+ uploadVersion + ",总共上传联系人个数:" + rowsCount + ",当前页码:"
					+ curPageNo + ",每页个数为:" + pageSize + ",总计页数:" + pageCount
					+ e);
			// 如果出现异常，则要将已经保存的记录进行删除，包括版本号
			if (customerId != null && StringUtils.isNotBlank(uploadVersion)) {
				try {
					AddressBook ab = new AddressBook();
					ab.setCustomerId(customerId);
					ab.setUploadVersion(uploadVersion);
					if (contactsMapper.removeUserContact(ab) > 0) {
						logger.info("用户云备份撤销成功,当前用户为：" + customerId + ",版本号为:"
								+ uploadVersion + ",总共上传联系人个数:" + rowsCount
								+ ",当前页码:" + curPageNo + ",每页个数为:" + pageSize
								+ ",总计页数:" + pageCount + e);
					}
				} catch (Exception ee) {
					logger.error("用户云备份撤销失败,当前用户为：" + customerId + ",版本号为:"
							+ uploadVersion + ",总共上传联系人个数:" + rowsCount
							+ ",当前页码:" + curPageNo + ",每页个数为:" + pageSize
							+ ",总计页数:" + pageCount + ee);
				}
			}
			throw e; // 将异常抛向Controller层进行日志记录
		}
		return rv;
	}

	public ReturnValue restore(Map<String, Object> params, ReturnValue rv) throws Exception {
		// 默认分页参数
		int curPageNo = 1, pageSize = 50;
		// 用户ID
		String customerId = null;
		String callLetter = null;

		if (params != null) {
			if (params.containsKey("customerId")
					&& StringUtils.isNotBlank(params.get("customerId").toString())
					&& StringUtils.isNumeric(params.get("customerId").toString()))
				customerId = params.get("customerId").toString();
			if (params.containsKey("callLetter"))
				callLetter = params.get("callLetter").toString();
			if (params.containsKey("curPageNo")
					&& StringUtils.isNotBlank(params.get("curPageNo").toString())
					&& StringUtils.isNumeric(params.get("curPageNo").toString()))
				curPageNo = Integer.valueOf(params.get("curPageNo").toString());
			if (params.containsKey("pageSize")
					&& StringUtils.isNotBlank(params.get("pageSize").toString())
					&& StringUtils.isNumeric(params.get("pageSize").toString()))
				pageSize = Integer.valueOf(params.get("pageSize").toString());
			// 如果用户ID为空,则返回参数错误
			if (customerId == null) {
				rv.setErrorCode(SGErrorInfoConstants.SG_CODE_404);
				rv.setErrorMsg(SGErrorInfoConstants.SG_MSG_406);
				return rv;
			}

			// 开始查询数据---------------------------------------
			AddressBook ab = new AddressBook();
			ab.setCustomerId(customerId);
			ab.setCallLetter(callLetter);
			AddressBook addressBook = contactsMapper.findByCustomerIdAndCallLetter(ab);
			// 计算返回分页的页码信息----------------------------
			Page page = new Page();
			page.setPageNum(curPageNo);
			page.setNumPerPage(pageSize);
			page.setTotalCount(addressBook.getTotalCount());

			// 计算页码end-------------------------------------
			// 获取分页后的联系人列表
			List<Map<String, String>> contactList = contactsMapper.findContactKeys(addressBook.getUploadVersion(), new RowBounds(page.getOffset(), page.getNumPerPage()));
			// 获取联系人列表的所有联系信息
			List<HashMap<String ,String>> contactInfoList = contactsMapper.findContacts(contactList);
			// 最终返回的联系信息列表
			List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
			// 迭代比较contact_key，相同时进行数据合并及结构处理
			for (Map<String, String> contact : contactList) {
				HashMap<String, Object> result = new HashMap<String, Object>();
				List<HashMap<String, String>> csList = new ArrayList<HashMap<String, String>>();
				for (HashMap<String, String> contactInfo : contactInfoList) {
					if (contactInfo.get("k").equals(contact.get("k"))) {
						csList.add(contactInfo);
					}
				}
				result.put("n", contact.get("n"));
				result.put("k", contact.get("k"));
				result.put("cs", csList);
				resultList.add(result);
			}
			
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("page", page);
			resultMap.put("uploadVersion", addressBook.getUploadVersion());
			resultMap.put("deviceType", addressBook.getDeviceType());
			resultMap.put("contact", resultList);
			rv.setDatas(resultMap);
		} else {
			rv.setErrorCode(SGErrorInfoConstants.SG_CODE_403);
			rv.setErrorMsg(SGErrorInfoConstants.SG_MSG_403);
		}
		return rv;
	}

}
