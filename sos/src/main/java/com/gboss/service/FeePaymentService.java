package com.gboss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.gboss.comm.SystemException;
import com.gboss.pojo.FeePayment;
import com.gboss.pojo.web.FeePaymentMsg;
import com.gboss.pojo.web.PrivateNetworkEntry;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service
 * @ClassName:FeePaymentService
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-6-18 上午8:36:52
 */
public interface FeePaymentService extends BaseService {
	
	public Long add(FeePayment feePayment);
	
	public List<HashMap<String, Object>> getPaymentRecords(Long cust_id,Long vehicle_id,Long unit_id)throws SystemException;
	
	public Boolean getFeePayment(Long unitId, Integer feetype_id);
	
	public FeePayment getLastFeePayment(Long unitId, Integer feetype_id);
	
	public Page<HashMap<String, Object>> findPaymentRecordsPage(PageSelect<Map<String, Object>> pageSelect) throws SystemException;
	
	public Page<HashMap<String, Object>> findPaymentRecordsPage2(PageSelect<Map<String, Object>> pageSelect) throws SystemException;
	
	public void addfeePayMent(FeePaymentMsg feePaymentMsg,String userId,String username,String companyid,String org_id, HttpServletRequest request);
	
	public Page<HashMap<String, Object>> findfeeDetailPage(PageSelect<Map<String, Object>> pageSelect) throws SystemException;
	
}

