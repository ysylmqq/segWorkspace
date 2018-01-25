package com.gboss.service.impl;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ldap.objectClasses.CommonOperator;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.dao.PolicyDao;
import com.gboss.pojo.Policy;
import com.gboss.service.PolicyService;
import com.gboss.util.DateUtil;
import com.gboss.util.PageSelect;
import com.gboss.util.Utils;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:PolicyServiceImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-4-29 下午4:15:17
 */
@Service("policyService")
@Transactional
public class PolicyServiceImpl extends BaseServiceImpl implements PolicyService {

	@Autowired  
	@Qualifier("policyDao")
	private PolicyDao policyDao;

	@Override
	public String getPolicytNo() throws SystemException {
		//流水号加1，前面不足4位，用0补充
		String serialNoStr=Utils.formatSerial(policyDao.getMaxPolicyNo(DateUtil.formatToday()));
		return SystemConst.POLICY_NO_PREFIX+DateUtil.format(new Date(), DateUtil.YMD)+serialNoStr;
	}

	@Override
	public HashMap<String, Object> getDetailMsgByCarNum(String carNum) throws SystemException {
		HashMap<String, Object> map = policyDao.getDetailMsgByCarNum(carNum);
		if(null != map){
			OpenLdap ldap = OpenLdapManager.getInstance();
			CommonOperator operator = ldap.getOperatorById(String.valueOf(map.get("opid")));
			if(null != operator){
				String loginname = operator.getLoginname();//入网名
				map.put("loginname", loginname);
			}
			String vId = map.get("vId") == null ? null : map.get("vId").toString();
			Long vehicle_id = vId == null ? null : Long.valueOf(vId); 
			Policy policy = policyDao.getPolicy(vehicle_id);
			DateFormat df = DateUtil.getFormat(DateUtil.YMD_DASH);
			if(policy != null){
				map.put("id", policy.getInsurance_id());
				map.put("policyno", policy.getPolicy_no());
				map.put("company", policy.getIc_no());
				map.put("person", policy.getPolicyholder());
				map.put("ywidcard", policy.getPh_id_no());
				map.put("birthday", policy.getBirthday());
				map.put("ywphone", policy.getPhone());
				map.put("is_buypilfer", policy.getIs_buy_tp());
				map.put("start_time", df.format(policy.getIs_bdate()));
				map.put("end_time", df.format(policy.getIs_edate()));
				map.put("ywmoney", policy.getVehicle_price());
				map.put("fee", policy.getFee());
				map.put("address", policy.getAddress());
				map.put("sales_person", policy.getSales());
				map.put("payment", policy.getPayment());
				map.put("is_contain", policy.getIs_contain());
				//map.put("loginname", policy.getLoginname());
			}


		}
		return map;
	}
	

	@Override
	public int operatePolicy(List<Long> ids, Integer type) throws SystemException {
		int result=1;
		if(ids==null || ids.isEmpty()){
			result=-1;
		}else{
			//开启检查
			if(type ==2){
				for (Long id : ids) {
					Policy policy = policyDao.get(Policy.class, id);
					if(!isRightTime(id, policy.getVehicle_id(), policy.getIs_bdate(), policy.getIs_edate())){
						return -100;
					}
				}
			}
			policyDao.operatePolicy(ids, type);
		}
		return result;
	}


	@Override
	public List<HashMap<String, Object>> getTodayPolicy(Long companyId)
			throws SystemException {
		List<HashMap<String, Object>> results = policyDao.getTodayPolicy(companyId);
		/*if(null !=results && results.size() >0 ){
			for (HashMap<String, Object> map : results) {
				OpenLdap ldap = OpenLdapManager.getInstance();
				CommonOperator operator = ldap.getOperatorById(String.valueOf(map.get("opid")));
				if(null != operator){
					String loginname = operator.getLoginname();//入网名
					map.put("loginname", loginname);
				}
			}
		}*/
		return results;
	}

	@Override
	public Page<HashMap<String, Object>> findPolicys(Long companyno, 
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total=policyDao.countPolicys(companyno,pageSelect.getFilter());
		List<HashMap<String, Object>> list=policyDao.findPolicys(companyno,pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

	@Override
	public HashMap<String, Object> getEditMsgByCarNum(Long id)
			throws SystemException {
		return policyDao.getEditMsgByCarNum(id);
	}

	@Override
	public Page<HashMap<String, Object>> findCusInfos(
			PageSelect<Map<String, Object>> pageSelect,Long companyno) throws SystemException {
		int total=policyDao.countCustomerInfo(pageSelect.getFilter(), companyno);
		List<HashMap<String, Object>> list=policyDao.findCustomerInfo(pageSelect.getFilter(), companyno, pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

	@Override
	public HashMap<String, Object> getCusMsg(Long vehicle_id, Long opid)
			throws SystemException {
		HashMap<String, Object> results = policyDao.getCusMsg(vehicle_id);
				OpenLdap ldap = OpenLdapManager.getInstance();
				CommonOperator operator = ldap.getOperatorById(opid.toString());
				if(null != operator){
					String loginname = operator.getLoginname();//入网名
					results.put("loginname", loginname);
				}
		return results;
	}

	@Override
	public List<HashMap<String, Object>> getexportExcelPolicy(
			Long companyno,Map<String, Object> map) throws SystemException {
		return policyDao.findPolicys(companyno,map, null, false, 0,0);
	}

	@Override
	public HashMap<String, Object> getPolicyMsgByNum(Long pid)
			throws SystemException {
		
		return policyDao.getPolicyMsgByNum(pid);
	}

	@Override
	public Policy getPolicyBynum(String policy_num) throws SystemException {
		return policyDao.getPolicyBynum(policy_num);
	}

	@Override
	public Page<HashMap<String, Object>> findPolicyPage(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total=policyDao.countPolicysRecords(pageSelect.getFilter());
		List<HashMap<String, Object>> list=policyDao.findPolicysRecords(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

	@Override
	public Boolean isRightTime(Long insurance_id, Long vehicle_id, Date startDate, Date endDate)
			throws SystemException {
		return policyDao.isRightTime(insurance_id, vehicle_id, startDate, endDate);
	}

	@Override
	public boolean is_exist(Long insurance_id, String policy_no) {
		return policyDao.is_exist(insurance_id, policy_no);
	}

	@Override
	public List<Long> getPolicys(Policy policy) {
		return policyDao.getPolicys(policy);
	}
	
}

