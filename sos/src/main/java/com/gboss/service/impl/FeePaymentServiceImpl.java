package com.gboss.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.dao.CustVehicleDao;
import com.gboss.dao.DatalockDao;
import com.gboss.dao.FeeInfoDao;
import com.gboss.dao.FeePaymentDao;
import com.gboss.dao.UnitDao;
import com.gboss.pojo.FeeInfo;
import com.gboss.pojo.FeePayment;
import com.gboss.pojo.FeePaymentDetail;
import com.gboss.pojo.LargeCustLock;
import com.gboss.pojo.PaymentItem;
import com.gboss.pojo.Unit;
import com.gboss.pojo.web.ErrorMsg;
import com.gboss.pojo.web.FeePaymentMsg;
import com.gboss.service.FeePaymentService;
import com.gboss.util.DateUtil;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:FeePaymentServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-6-18 上午8:40:51
 */
@Repository("FeePaymentService")  
@Transactional 
public class FeePaymentServiceImpl extends BaseServiceImpl implements FeePaymentService {
	
	@Autowired  
	@Qualifier("feePaymentDao")
	private FeePaymentDao feePaymentDao;
	
	@Autowired
	@Qualifier("FeeInfoDao")
	private FeeInfoDao feeInfoDao;
	
	@Autowired
	@Qualifier("UnitDao")
	private UnitDao unitDao;
	
	
	@Autowired
	@Qualifier("CustVehicleDao")
	private CustVehicleDao custVehicleDao;
	
	@Autowired
	@Qualifier("DatalockDao")
	private DatalockDao datalockDao;
	
	
	

	@Override
	public Long add(FeePayment feePayment) {
		save(feePayment);
		return feePayment.getPayment_id();
	}

	@Override
	public List<HashMap<String, Object>> getPaymentRecords(Long cust_id,
			Long vehicle_id, Long unit_id) throws SystemException {
		return feePaymentDao.getPaymentRecords(cust_id, vehicle_id, unit_id);
	}

	@Override
	public Boolean getFeePayment(Long unitId, Integer feetype_id) {
		return feePaymentDao.getFeePayment(unitId, feetype_id);
	}

	@Override
	public FeePayment getLastFeePayment(Long unitId, Integer feetype_id) {
		return feePaymentDao.getLastFeePayment(unitId, feetype_id);
	}

	@Override
	public Page<HashMap<String, Object>> findPaymentRecordsPage(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total = feePaymentDao.countPaymentRecords(pageSelect.getFilter());
		List<HashMap<String, Object>> list=feePaymentDao.findPaymentRecordsPage( pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

	@Override
	public Page<HashMap<String, Object>> findPaymentRecordsPage2(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total = feePaymentDao.countPaymentRecords2(pageSelect.getFilter());
		List<HashMap<String, Object>> list=feePaymentDao.findPaymentRecordsPage2( pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

	@Override
	public void addfeePayMent(FeePaymentMsg feePaymentMsg, String userId,
			String username, String companyid, String org_id,HttpServletRequest request) {
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			if (feePaymentMsg ==null ||(feePaymentMsg != null && feePaymentMsg.getFeePayment() == null)) {
				flag = false;
				msg="数据有误，请检查后再提交!";
				throw new Exception(msg);
			}
			FeePayment feePayment = feePaymentMsg.getFeePayment();
			Long[] vehicleIds = feePaymentMsg.getVehicleIds();
			feePayment.setSubco_no(companyid == null ? null : Long.valueOf(companyid));
			feePayment.setOp_id(userId == null ? null : Long.valueOf(userId));
			if(feePayment.getVehicle_id() == null)
				feePayment.setVehicle_id(0L);
			if(feePayment.getUnit_id() == null)
				feePayment.setUnit_id(0L);
			feePayment.setFeetype_id(0);
			feePayment.setItem_id(0);
			feePayment.setFlag(0);
			feePayment.setAgent_name(username);
			feePayment.setOrg_id(org_id == null ? null : Long.valueOf(org_id));
			Long cust_id = feePayment.getCustomer_id();
			String message = "缴费";
			if(feePayment.getS_months() !=null && feePayment.getS_months() !=0){
				message = message + feePayment.getS_months()+"月";
			}
			if(feePayment.getS_days() !=null && feePayment.getS_days() !=0){
				message = message +feePayment.getS_days()+"天 ";
			}
			feePayment.setRemark(message+feePayment.getRemark());
			if(feePayment.getVehicle_id() ==null)
				feePayment.setVehicle_id(0L);
			if(feePayment.getUnit_id() == null)
				feePayment.setUnit_id(0L);
			if(feePayment.getCustomer_id() == null){
				flag = false;
				msg="客户id不能为空，请检查后再提交!";
				throw new Exception(msg);
			}/*else{
				List<Long> lockcustids = datalockDao.getLockCustomer();
				if(lockcustids.contains(cust_id)){
					flag = false;
					msg="该客户正被托收，暂不能缴费!";
					throw new Exception(msg);
				}
			}*/
			
			if(feePaymentMsg.getCust_type()==0 && (vehicleIds==null ||(vehicleIds!=null && vehicleIds.length ==0))){
				flag = false;
				msg="请选择要缴费的车辆，请检查后再提交!";
				throw new Exception(msg);
			}
			feePaymentDao.save(feePayment);
	//保存缴费比例表  t_fee_payment_item  前端传递过来的   根据这个 判断 用户具体的缴费详情  也就是往t_fee_payment_dt表当中插入记录
			List<PaymentItem> paymentItems = feePaymentMsg.getPaymentItems();
			for (PaymentItem paymentItem : paymentItems) {
				paymentItem.setSubco_no(feePayment.getSubco_no());
				paymentItem.setOp_id(feePayment.getOp_id());
				if(feePayment.getPayment_id() == null || feePayment.getFeetype_id() ==null){
					flag = false;
					msg="数据有误，请检查后再提交!";
					throw new Exception(msg);
				}
				paymentItem.setPayment_id(feePayment.getPayment_id());
				feePaymentDao.save(paymentItem);
			}
			if(feePaymentMsg.getCust_type()==0){
				for (Long id : vehicleIds) {
					for (PaymentItem paymentItem : paymentItems) {
						if(id ==null){
							flag = false;
							msg="数据有误，请检查后再提交!";
							throw new Exception(msg);
						}
						// t_fee_info  如果存在  更新服务结束时间  在私家车入网的时候插入的记录  只有入网的私家车才拥有该服务
						// 根据vehicle_id 和 feetype_id  可以确认唯一的一条记录 
						FeeInfo info = feeInfoDao.findFeeInfo(id, paymentItem.getFeetype_id());
						if(null != info){
							//延长截止服务时间 t_fee_info
							Date start_date = info.getFee_sedate();
							Date e_date = getEndDate(info.getFee_sedate(), feePayment.getS_months(), feePayment.getS_days());
							info.setFee_sedate(e_date);
							feeInfoDao.update(info);
							// t_fee_payment_dt 插入具体的付费详情
							FeePaymentDetail detail = new FeePaymentDetail();
							detail.setPayment_id(feePayment.getPayment_id());
							detail.setSubco_no(feePayment.getSubco_no());
							detail.setCustomer_id(feePayment.getCustomer_id());
							detail.setOrg_id(feePayment.getOrg_id());
							detail.setVehicle_id(id);
							detail.setFlag(0L);
							List<Unit> unitList = unitDao.getByVehicle_id(id);
							if(unitList != null && unitList.size() >0){
								detail.setUnit_id(unitList.get(0).getUnit_id());
							}else{
								detail.setUnit_id(0L);
							}
							detail.setPay_time(new Date());
							detail.setFeetype_id(paymentItem.getFeetype_id());
							detail.setPay_model(feePayment.getPay_model());
							detail.setItem_id(feePayment.getItem_id());
							detail.setS_months(feePayment.getS_months());
							detail.setS_days(feePayment.getS_days());
							// 问题就在这 
							float fee = info.getMonth_fee()*feePayment.getS_months() + info.getMonth_fee()*feePayment.getS_days()/30;
							fee = (float) Math.floor(fee);
							float real_fee = 0f;
							if(paymentItem.getAc_amount() !=0 ){
								real_fee =  fee*paymentItem.getReal_amount()/paymentItem.getAc_amount();
								real_fee = (float) Math.floor(real_fee);
							}
							System.err.println("fee "+fee+" real_fee "+real_fee);
							detail.setAc_amount(fee);
							detail.setReal_amount(real_fee);
							detail.setBw_no(feePayment.getBw_no());
							detail.setE_date(info.getFee_sedate());
							detail.setS_date(start_date);
							detail.setOp_id(feePayment.getOp_id());
							
							/*//测试
							if(detail.getFeetype_id() ==2 ){
								detail.setVehicle_id(null);
							}*/
							
							//检查必填字段 回滚
							if(detail.getVehicle_id() == null || detail.getUnit_id() == null|| detail.getAc_amount()==null || detail.getReal_amount()== null){
								flag = false;
								msg="数据信息不完整，请检查后再提交!";
								throw new Exception(msg);
							}
							feePaymentDao.save(detail);
						}
					}
				}
			}else{
				//集团客户整体缴费
				LargeCustLock lock = custVehicleDao.get(LargeCustLock.class, cust_id);
				String locktime = null;
				if(null != lock && lock.getLocktag() ==1)
					locktime = DateUtil.format(lock.getStamp(), DateUtil.YMD_DASH_WITH_FULLTIME);
				
				List<HashMap<String, Object>> list=custVehicleDao.findLargeVehicleList(cust_id,locktime);
				for (HashMap<String, Object> map : list) {
					Long vehicle_id = map.get("vehicle_id") == null ? null : Long.valueOf(map.get("vehicle_id").toString());
					Long unit_id = map.get("unit_id") == null ? null : Long.valueOf(map.get("unit_id").toString());
					for (PaymentItem paymentItem : paymentItems) {
						FeeInfo info = feeInfoDao.getFeeInfo(unit_id, paymentItem.getFeetype_id());
						if(null != info){
							//延长截止服务时间
							Date start_date = info.getFee_sedate();
							Date e_date = getEndDate(info.getFee_sedate(), feePayment.getS_months(), feePayment.getS_days());
							info.setFee_sedate(e_date);
							feeInfoDao.update(info);
							FeePaymentDetail detail = new FeePaymentDetail();
							detail.setPayment_id(feePayment.getPayment_id());
							detail.setSubco_no(feePayment.getSubco_no());
							detail.setCustomer_id(feePayment.getCustomer_id());
							detail.setOrg_id(feePayment.getOrg_id());
							detail.setVehicle_id(vehicle_id);
							detail.setUnit_id(unit_id);
							detail.setFlag(0L);
							detail.setFeetype_id(paymentItem.getFeetype_id());
							detail.setPay_model(feePayment.getPay_model());
							detail.setItem_id(feePayment.getItem_id());
							detail.setS_months(feePayment.getS_months());
							detail.setS_days(feePayment.getS_days());
							//问题就在这 
							float fee = info.getMonth_fee()*feePayment.getS_months() + info.getMonth_fee()*feePayment.getS_days()/30;
							fee = (float) Math.floor(fee);
							float real_fee = 0f;
							if(paymentItem.getAc_amount() !=0 ){
								real_fee =  fee*paymentItem.getReal_amount()/paymentItem.getAc_amount();
								real_fee = (float) Math.floor(real_fee);
							}
							System.err.println(" fee "+fee+" real_fee  "+real_fee);
							detail.setAc_amount(fee);
							detail.setReal_amount(real_fee);
							detail.setPay_time(new Date());
							detail.setBw_no(feePayment.getBw_no());
							detail.setE_date(info.getFee_sedate());
							detail.setS_date(start_date);
							detail.setOp_id(feePayment.getOp_id());
							//检查必填字段 回滚
							if(detail.getVehicle_id() == null || detail.getUnit_id() == null|| detail.getAc_amount()==null || detail.getReal_amount()== null){
								flag = false;
								msg="数据信息不完整，请检查后再提交!";
								throw new Exception(msg);
							}
							feePaymentDao.save(detail);
						}
					}
				}
				//缴费完成解锁
				if(null != lock && lock.getLocktag() ==1){
					lock.setLocktag(0);
					custVehicleDao.update(lock);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		ErrorMsg error = new ErrorMsg();
		error.setFlag(flag);
		error.setMsg(msg);
		request.getSession().setAttribute(SystemConst.ERROR_MSG, error);
		
	}
	

	public Date getEndDate(Date s_date, Integer s_month, Integer s_days){
		if(null !=s_date ){
			Calendar cl = Calendar.getInstance();
			cl.setTime(s_date);
			cl.add(Calendar.MONTH, s_month);
			cl.add(Calendar.DATE, s_days);
			return cl.getTime();
		}
		return null;
	}

	@Override
	public Page<HashMap<String, Object>> findfeeDetailPage(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total = feePaymentDao.countFeeDetail(pageSelect.getFilter());
		List<HashMap<String, Object>> list=feePaymentDao.findFeeDetailPage( pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

}

