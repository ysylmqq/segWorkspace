package com.gboss.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ldap.mysql.IdCreater;
import ldap.objectClasses.CommonOperator;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.gboss.comm.SystemException;
import com.gboss.dao.CustVehicleDao;
import com.gboss.dao.CustomerDao;
import com.gboss.dao.LinkmanDao;
import com.gboss.dao.ModelDao;
import com.gboss.dao.VehicleDao;
import com.gboss.pojo.CustSales;
import com.gboss.pojo.CustVehicle;
import com.gboss.pojo.Customer;
import com.gboss.pojo.Linkman;
import com.gboss.pojo.Model;
import com.gboss.pojo.UbiSales;
import com.gboss.pojo.Vehicle;
import com.gboss.service.CustomerService;
import com.gboss.util.DateUtil;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

@Service("CustomerService")
@Transactional
public class CustomerServiceImpl extends BaseServiceImpl implements CustomerService {

	@Autowired
	@Qualifier("CustomerDao")
	private CustomerDao customerDao;

	@Autowired
	@Qualifier("VehicleDao")
	private VehicleDao vehicleDao;

	@Autowired
	@Qualifier("CustVehicleDao")
	private CustVehicleDao custVehicleDao;

	@Autowired
	@Qualifier("modelDao")
	private ModelDao modelDao;

	@Autowired
	@Qualifier("linkmanDao")
	private LinkmanDao linkmanDao;

	@Override
	public boolean is_repeat(Customer customer) throws SystemException {
		return customerDao.is_repeat(customer);
	}

	@Override
	public Long add(Customer customer) throws SystemException {
		save(customer);
		return customer.getCustomer_id();
	}

	@Override
	public Page<Customer> search(PageSelect<Customer> pageSelect, Long subco_no) throws SystemException {
		return customerDao.search(pageSelect, subco_no);
	}

	@Override
	public String getCustomerPhone(Long id) throws SystemException {
		return customerDao.getCustomerPhones(id);
	}

	@Override
	public void delete(Long id) throws SystemException {
		delete(Customer.class, id);
	}

	@Override
	public HashMap<String, Object> getDetailMsg(Long id) throws SystemException {
		HashMap<String, Object> result = customerDao.getDetailMsg(id);
		return result;
	}

	@Override
	public Customer getCustomer(Long id) throws SystemException {
		return customerDao.getCustomer(id);
	}

	@Override
	public HashMap<String, Object> getDetailMsgBycl(String call_letter) throws SystemException {
		return customerDao.getDetailMsgBycl(call_letter);
	}

	@Override
	public Customer getCustomer(String customer_name) throws SystemException {
		return customerDao.getCustomer(customer_name);
	}

	@Override
	public CustSales getCustSales(Long customerId) throws SystemException {
		return customerDao.getCustSales(customerId);
	}

	/**
	 * 查询APP用户 分页
	 */
	public Page<HashMap<String, Object>> findUserPage(PageSelect<HashMap<String, Object>> pageSelect) throws SystemException {
		HashMap<String, Object> map = null;
		List<HashMap<String, Object>> userList = new ArrayList<HashMap<String, Object>>();
		String filter = "(&(objectclass=commonOperator)(|(status=10)(status=11))";
		String loginname = (String) pageSelect.getFilter().get("loginname");
		String opname = (String) pageSelect.getFilter().get("opname");
		String mobile = (String) pageSelect.getFilter().get("mobile");
		String numberplate = (String) pageSelect.getFilter().get("numberplate");
		String mail = (String) pageSelect.getFilter().get("mail");
		if (StringUtils.isBlank(loginname) && StringUtils.isBlank(opname) && StringUtils.isBlank(mobile) && StringUtils.isBlank(numberplate)
				&& StringUtils.isBlank(mail)) {
			return PageUtil.getPage(0, pageSelect.getPageNo(), userList, pageSelect.getPageSize());
		}
		if (StringUtils.isNotBlank(loginname)) {
			filter += "(loginname=*" + loginname + "*)";
		}
		if (StringUtils.isNotBlank(opname)) {
			filter += "(opname=*" + opname + "*)";
		}
		if (StringUtils.isNotBlank(mobile)) {
			filter += "(mobile=*" + mobile + "*)";
		}
		if (StringUtils.isNotBlank(numberplate)) {
			filter += "(numberplate=*" + numberplate + "*)";
		}
		if (StringUtils.isNotBlank(mail)) {
			filter += "(mail=*" + mail + "*)";
		}
		filter += ")";
		OpenLdap ldap = OpenLdapManager.getInstance();
		List<CommonOperator> operatorList = ldap.searchOperator("", filter);
		Long subco_no = Long.valueOf((String) pageSelect.getFilter().get("companyId"));
		List<String> custids = customerDao.getCustids(subco_no);
		List<CommonOperator> list = new ArrayList<CommonOperator>();
		for (CommonOperator operator : operatorList) {
			if (custids.contains(operator.getCustomerid())) {
				list.add(operator);
			}
		}

		int total = list.size();
		int start = (pageSelect.getPageNo() - 1) * pageSelect.getPageSize();
		int end = pageSelect.getPageNo() * pageSelect.getPageSize();
		int num = total < end ? total : end;
		for (int i = start; i < num; i++) {
			CommonOperator operator = list.get(i);
			map = new HashMap<String, Object>();
			map.put("dn", operator.getDn());
			map.put("opid", operator.getOpid());
			map.put("loginname", operator.getLoginname());
			map.put("opname", operator.getOpname());
			map.put("idcard", operator.getIdcard());
			map.put("jobnumber", operator.getJobnumber());
			map.put("phone", operator.getPhone());
			map.put("companyname", operator.getCompanyname());
			map.put("remark", operator.getRemark());
			map.put("sex", operator.getSex());
			map.put("fax", operator.getFax());
			map.put("mail", operator.getMail());
			map.put("mobile", operator.getMobile());
			map.put("rolename", operator.getRolename());
			map.put("order", operator.getOrder());
			map.put("numberplate", operator.getNumberplate());
			userList.add(map);
		}
		return PageUtil.getPage(total, pageSelect.getPageNo(), userList, pageSelect.getPageSize());
	}

	public Page<UbiSales> listUbiSales(PageSelect<UbiSales> pageSelect) throws SystemException {
		return this.customerDao.getUbiSales(pageSelect);
	}

	@Override
	public UbiSales getUbiSales(int salesId) throws SystemException {
		return customerDao.getUbiSaler(salesId);
	}
	
	
	/**
	 * 查询APP用户 列表
	 */
	public List<CommonOperator> listAccount(Map<String, Object> map) throws SystemException {
		String filter = "(&(objectclass=commonOperator)(|(status=10)(status=11))";
		String loginname = (String) map.get("loginname");
		String opname = (String) map.get("opname");
		String mobile = (String) map.get("mobile");
		String numberplate = (String) map.get("numberplate");
		String mail = (String) map.get("mail");
		if (StringUtils.isBlank(loginname) && StringUtils.isBlank(opname) && StringUtils.isBlank(mobile) && StringUtils.isBlank(numberplate)
				&& StringUtils.isBlank(mail)) {
			return new ArrayList<CommonOperator>();
		}
		if (StringUtils.isNotBlank(loginname)) {
			filter += "(loginname=*" + loginname + "*)";
		}
		if (StringUtils.isNotBlank(opname)) {
			filter += "(opname=*" + opname + "*)";
		}
		if (StringUtils.isNotBlank(mobile)) {
			filter += "(mobile=*" + mobile + "*)";
		}
		if (StringUtils.isNotBlank(numberplate)) {
			filter += "(numberplate=*" + numberplate + "*)";
		}
		if (StringUtils.isNotBlank(mail)) {
			filter += "(mail=*" + mail + "*)";
		}
		filter += ")";
		OpenLdap ldap = OpenLdapManager.getInstance();
		List<CommonOperator> operatorList = ldap.searchOperator("", filter);
		Long subco_no = Long.valueOf((String) map.get("companyId"));
		List<String> custids = customerDao.getCustids(subco_no);
		List<CommonOperator> list = new ArrayList<CommonOperator>();
		for (CommonOperator operator : operatorList) {
			if (custids.contains(operator.getCustomerid())) {
				list.add(operator);
			}
		}
		return list;
	}

	@Override
	public Map<String, Object> importCustomerVehicle(List<String[]> dataList, Long compannyId, Long userId, String companyName, String companyCode)
			throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		Date date = new Date(); // 导入时间
		String markDate = DateUtil.format(date, "yyyy-MM-dd");
		String message = "";
		Integer sussNum = 0;
		Integer failNum = 0;
		Integer repeatNum = 0;
		Integer total = dataList.size();
		try {
			Map<String, Long> mMap = getModelMap(); // 车型列表
			/*
			 * List<Vehicle> vList = vehicleDao.listAll(Vehicle.class);
			 * Map<String, Object> plateNoMap = new HashMap<String, Object>();
			 * Map<String, Object> vinMap = new HashMap<String, Object>();
			 * Map<String, Object> engineNoMap = new HashMap<String, Object>();
			 * for(Vehicle v : vList){ //将数据表中数据存在内存中，导入时不再查询数据库
			 * if(StringUtils.isNotBlank(v.getPlate_no())){
			 * plateNoMap.put(v.getPlate_no(), "has"); }
			 * if(StringUtils.isNotBlank(v.getVin())){ vinMap.put(v.getVin(),
			 * "has"); } if(StringUtils.isNotBlank(v.getEngine_no())){
			 * engineNoMap.put(v.getEngine_no(), "has"); } }
			 */
			for (int i = 0; i < total; i++) {
				Integer rowNum = i + 2;
				String[] column = dataList.get(i); // 读取一行
				String customerName = column[0].trim(); // 客户名称
				String plateNo = column[1].trim(); // 车牌号码
				String phone = column[2].trim(); // 联系电话
				Long model = null; // 车型id
				String modelName = column[3].trim(); // 车型
				String vin = column[4].trim(); // 车架号
				String engineNo = column[5].trim(); // 发动机号
				String dateStr = column[6].trim(); // 车辆初次登记日期
				String bdateStr = column[7].trim(); // 保险起保时间
				String edateStr = column[8].trim(); // 保险截止时间
				String isCorp = column[9].trim(); // 是否从公司购买商业保险, 0=否, 1=是
				String isPilfer = column[10].trim();// 是否享有综合盗抢险, 0=否, 1=是
				// 空值判断
				if (StringUtils.isBlank(customerName)) { // 客户姓名
					message += "第<span style=color:green>" + rowNum + "</span>行,<span style=color:red>客户姓名</span>为空";
					continue;
				}
				if (StringUtils.isBlank(plateNo)) { // 车牌号码
					message += "第<span style=color:green>" + rowNum + "</span>行,<span style=color:red>车牌号码</span>为空";
					continue;
				}
				if (StringUtils.isBlank(vin)) { // 车架号
					message += "第<span style=color:green>" + rowNum + "</span>行,<span style=color:red>车架号</span>为空";
					continue;
				}
				if (StringUtils.isBlank(engineNo)) { // 发动机号
					message += "第<span style=color:green>" + rowNum + "</span>行,<span style=color:red>发动机号</span>为空";
					continue;
				}
				// 判断车牌、车架号、发动机号是否重复
				// if(plateNoMap.get(plateNo) != null){
				if (vehicleDao.isVehicleepeat(null, plateNo, null, null)) {
					message += "第<span style=color:green>" + rowNum + "</span>行,<span style=color:red>车牌号码</span>重复";
					repeatNum++;
					continue;
				}
				// if(vinMap.get(vin) != null){
				if (vehicleDao.isVehicleepeat(null, null, vin, null)) {
					message += "第<span style=color:green>" + rowNum + "</span>行,<span style=color:red>车架号</span>重复";
					repeatNum++;
					continue;
				}
				// if(engineNoMap.get(engineNo) != null){
				if (vehicleDao.isVehicleepeat(null, null, null, engineNo)) {
					message += "第<span style=color:green>" + rowNum + "</span>行,<span style=color:red>发动机号</span>重复";
					repeatNum++;
					continue;
				}
				// 只有在验证非空情况下加入查询map（3项都不重复且非空）
				/*
				 * if(StringUtils.isNotBlank(plateNo)){ plateNoMap.put(plateNo,
				 * "has"); } if(StringUtils.isNotBlank(vin)){ vinMap.put(vin,
				 * "has"); } if(StringUtils.isNotBlank(engineNo)){
				 * engineNoMap.put(engineNo, "has"); }
				 */
				if (StringUtils.isNotBlank(modelName)) {
					model = mMap.get("modelName");
				}
				Date bDate = null;
				Date isBdate = null;
				Date isEdate = null;
				try {
					if (StringUtils.isNotBlank(dateStr)) {
						bDate = DateUtil.parse(dateStr, "yyyy-MM-dd");
					}
					if (StringUtils.isNotBlank(bdateStr)) {
						isBdate = DateUtil.parse(bdateStr, "yyyy-MM-dd");
					}
					if (StringUtils.isNotBlank(edateStr)) {
						isEdate = DateUtil.parse(edateStr, "yyyy-MM-dd");
					}
				} catch (Exception el) {

				}
				Integer is_corp = 0;
				if (StringUtils.isNotBlank(isCorp)) {
					if ("是".equals(isCorp)) {
						is_corp = 1;
					}
				}
				Integer is_pilfer = 0;
				if (StringUtils.isNotBlank(isPilfer)) {
					if ("是".equals(isPilfer)) {
						is_pilfer = 1;
					}
				}
				try {
					// 保存客户信息
					Customer c = new Customer();
					c.setCustomer_name(customerName);
					c.setSubco_no(compannyId);
					c.setSubco_name(companyName);
					c.setSubco_code(companyCode);
					c.setCustco_no(0L);
					c.setCust_type(0);
					c.setIdtype(0);
					c.setTrade(0);
					c.setVip(1);
					c.setFlag(1);
					c.setPay_model(0);
					c.setOp_id(userId);
					c.setStamp(date);
					c.setRemark(markDate + "导入记录");
					Long cId = customerDao.add(c);
					// 保存车辆信息
					Vehicle v = new Vehicle();
					v.setSubco_no(compannyId);
					v.setPlate_no(plateNo);
					v.setPlate_color(1);
					v.setVehicle_type(5);
					v.setVehicle_status(0);
					v.setFlag(1);
					v.setModel(model);
					v.setModel_name(modelName);
					v.setVin(vin);
					v.setEngine_no(engineNo);
					v.setRegister_date(bDate);
					v.setVload(5);
					v.setOp_id(userId);
					v.setStamp(date);
					v.setIs_bdate(isBdate);
					v.setIs_edate(isEdate);
					v.setIs_corp(is_corp);
					v.setIs_pilfer(is_pilfer);
					v.setRemark(markDate + "导入记录");
					Long vId = vehicleDao.add(v);
					// 保存联系电话(非空)
					Linkman l = new Linkman();
					l.setCustomer_id(cId);
					l.setVehicle_id(vId);
					l.setLinkman_type(1);
					l.setLinkman(customerName);
					l.setStamp(date);
					l.setPhone(phone);
					l.setAppsign(0);
					linkmanDao.add(l);
					// 保存车辆与客户的关联关系
					CustVehicle cv = new CustVehicle();
					cv.setCustomer_id(cId);
					cv.setVehicle_id(vId);
					cv.setStamp(date);
					custVehicleDao.add(cv);
					// 将客户添加到ldap中
					CommonOperator op = new CommonOperator();
					String opid = IdCreater.getOperatorId();
					op.setCustomerid(cId + "");
					op.setOpid(opid);
					op.setNumberplate(plateNo);
					op.setLoginname(plateNo);
					op.setStatus("11");
					op.setOpname(customerName);
					op.setOrder("0");
					OpenLdap ldap = OpenLdapManager.getInstance();
					ldap.add(op);
					sussNum++;
				} catch (Exception e) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 回滚本次插入
					message += "第<span style=color:green>" + rowNum + "</span>行,<span style=color:red>插入异常，原因:</span>" + e.getMessage();
					continue;
				}
			}
		} catch (Exception e) {

		}
		message = message + "<br />";
		failNum = total - sussNum - repeatNum;
		message = "共<span style=color:green>" + total + "</span>条数据，其中成功插入<span style=color:green>" + sussNum + "</span>条，重复<span style=color:red>"
				+ repeatNum + "</span>条，" + "失败<span style=color:red>" + failNum + "</span>条<br />" + message;

		map.put("success", true);
		map.put("msg", message);
		return map;
	}

	private Map<String, Long> getModelMap() {
		Map<String, Long> mMap = new HashMap<String, Long>();
		List<Model> list = modelDao.listAll(Model.class);
		for (Model m : list) {
			mMap.put(m.getName(), m.getId());
		}
		return mMap;
	}
}
