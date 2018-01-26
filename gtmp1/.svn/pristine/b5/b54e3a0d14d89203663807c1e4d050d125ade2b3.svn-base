package com.chinaGPS.gtmp.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.chinaGPS.gtmp.entity.CustomerPayPOJO;
import com.chinaGPS.gtmp.entity.CustomerPayPOJOExample;
import com.chinaGPS.gtmp.entity.CustomerSimPOJO;
import com.chinaGPS.gtmp.entity.SimServerPOJO;
import com.chinaGPS.gtmp.entity.VehiclePOJO;
import com.chinaGPS.gtmp.entity.CustomerPayPOJOExample.Criteria;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.mapper.CustomerPayPOJOMapper;
import com.chinaGPS.gtmp.mapper.CustomerSimPOJOMapper;
import com.chinaGPS.gtmp.mapper.SimServerPOJOMapper;
import com.chinaGPS.gtmp.service.ICustomerPayService;
import com.chinaGPS.gtmp.util.page.PageSelect;

/**
 * @Package:com.chinaGPS.gtmp.service
 * @ClassName:ISimServerService
 * @Description:玉柴重工使用SIM卡service
 * @author:于松亚
 * @date:2017-3-21 上午10:15:43
 */
@Service
public class CustomerPayServiceImpl extends BaseServiceImpl<CustomerPayPOJO> implements ICustomerPayService {
	@Resource
	private CustomerPayPOJOMapper<CustomerPayPOJO> customerPayPOJOMapper;
	
	@Resource
	private CustomerSimPOJOMapper<CustomerSimPOJO> customerSimPOJOMapper;
	
	@Resource
	private SimServerPOJOMapper<SimServerPOJO> simServerPOJOMapper;

	@Override
	protected BaseSqlMapper<CustomerPayPOJO> getMapper() {
		return this.customerPayPOJOMapper;
	}

	@Override
	public HashMap<String, Object> getCustomerPays(
			CustomerPayPOJO customerPayPOJO, PageSelect pageSelect)
			throws Exception {
		Map map = new HashMap();
		map.put("customerPayPOJO", customerPayPOJO);
		int total = customerPayPOJOMapper.countAll(map);
		List<CustomerPayPOJO> resultList =  customerPayPOJOMapper.getByPage(map,new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result=new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}

	@Override
	public int custPay(CustomerPayPOJO customerPayPOJO) throws Exception {
		//客户缴费之前先判断 客户是否开通了SIM卡功能 和 公司是否开通了SIM卡功能
		CustomerSimPOJO customerSimPOJO = customerSimPOJOMapper.getCustomerSimPOJOById(customerPayPOJO.getSimNo());
		if(customerSimPOJO == null ){
			return 0; //客户没有申请开通SIM服务
		}
		SimServerPOJO simServerPOJO  = simServerPOJOMapper.getSimServerPOJOById(customerPayPOJO.getSimNo());
		if( simServerPOJO == null ){
			return -1; //该终端没有开通SIM服务
		}
		
		Date customerPayStartTime = customerSimPOJO.getEndTime();
		customerPayPOJO.setStartTime(customerPayStartTime);//sim卡结束服时间 是下一次的缴费的开始时间
		//插入新的记录之前 update isLast = 0;
		customerPayPOJO.setIsLast(new BigDecimal(0));
		customerPayPOJOMapper.updateIsLast(customerPayPOJO);
		//插入新的记录   
		customerPayPOJO.setIsLast(new BigDecimal(1));
		customerPayPOJO.setStatus(new BigDecimal(1));
		customerPayPOJOMapper.insert(customerPayPOJO);
		
		int freePeriod = customerSimPOJO.getFreePeriod().intValue(); //免费周期
		//暂时不考虑免费周期
		Date customerPayEndTime = customerPayPOJO.getEndTime();
		customerSimPOJO.setEndTime(customerPayEndTime);
		customerSimPOJO.setOperId(customerPayPOJO.getOperId());
		customerSimPOJOMapper.updateByPrimaryKey(customerSimPOJO);
		return 1;
	}

	@Override
	public List<CustomerPayPOJO> simList() {
		return customerPayPOJOMapper.simList();
	}

	@Override
	public List<CustomerPayPOJO> importCustomerPay(List<CustomerPayPOJO> list,String userId) throws Exception {
		List<CustomerPayPOJO> errorList = new ArrayList<CustomerPayPOJO>();
		CustomerPayPOJO res = null;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			CustomerPayPOJO customerPayPOJO = (CustomerPayPOJO) iterator.next();
			String simNo = customerPayPOJO.getSimNo();
			BigDecimal payAmount = customerPayPOJO.getPayAmount();
			Date startTime = customerPayPOJO.getStartTime();
			Date endTime = customerPayPOJO.getEndTime();
			StringBuilder erroTips = new StringBuilder("");
			boolean flag = true;
			if(simNo == null || "".equalsIgnoreCase(simNo)){
				erroTips.append("simNO为空");
				flag = false;
			}else {
				int len = simNo.length();
				if( len != 11){
					erroTips.append("simNO有误");
					flag = false;
				}
			}
			if(payAmount == null){
				erroTips.append("付款金额为空");
				flag = false;
			}
			if(endTime == null){
				erroTips.append("服务截止日期为空");
				flag = false;
			}
			customerPayPOJO.setOperId(userId);
			if(flag){
			//开始缴费	
				res = this.batchCustPay(customerPayPOJO); //返回null标示成功
				if(res != null){
					errorList.add(customerPayPOJO);
				}
			}else{
				customerPayPOJO.setRemark(erroTips.toString());
				errorList.add(customerPayPOJO);
			}
		}
		return errorList;
	}

	@Override
	public CustomerPayPOJO getCustomerPayPOJO(BigDecimal custPayId) throws Exception {
		return customerPayPOJOMapper.selectByPrimaryKey(custPayId);
	}

	@Override
	public CustomerPayPOJO batchCustPay(CustomerPayPOJO customerPayPOJO)
			throws Exception {
		//客户缴费之前先判断 客户是否开通了SIM卡功能 和 公司是否开通了SIM卡功能
		CustomerSimPOJO customerSimPOJO = customerSimPOJOMapper.getCustomerSimPOJOById(customerPayPOJO.getSimNo());
		if(customerSimPOJO == null ){
			customerPayPOJO.setRemark("客户没有申请开通SIM服务");
			return customerPayPOJO;
		}
		
		SimServerPOJO simServerPOJO  = simServerPOJOMapper.getSimServerPOJOById(customerPayPOJO.getSimNo());
		if( simServerPOJO == null ){
			//该终端没有开通SIM服务
			customerPayPOJO.setRemark("该终端没有开通SIM服务");
			return customerPayPOJO;
		}
		
		Date customerPayStartTime = customerSimPOJO.getEndTime();
		customerPayPOJO.setStartTime(customerPayStartTime);//sim卡结束服时间 是下一次的缴费的开始时间
		//插入新的记录之前 update isLast = 0;
		customerPayPOJO.setIsLast(new BigDecimal(0));
		customerPayPOJOMapper.updateIsLast(customerPayPOJO);
		//插入新的记录   
		customerPayPOJO.setIsLast(new BigDecimal(1));
		customerPayPOJO.setStatus(new BigDecimal(1));
		customerPayPOJOMapper.insert(customerPayPOJO);
		
		int freePeriod = customerSimPOJO.getFreePeriod().intValue(); //免费周期
		//暂时不考虑免费周期
		/*if( freePeriod > 0 ){
			customerPayEndTime = null; 
		}*/
		Date customerPayEndTime = customerPayPOJO.getEndTime();
		customerSimPOJO.setEndTime(customerPayEndTime);
		customerSimPOJO.setOperId(customerPayPOJO.getOperId());
		customerSimPOJOMapper.updateByPrimaryKey(customerSimPOJO);
		return null;
	}

	@Override
	public void updateIsLast(CustomerPayPOJO customerPayPOJO) throws Exception {
		customerPayPOJOMapper.updateIsLast(customerPayPOJO);
	}

	@Override
	public void updateStatus(CustomerPayPOJO customerPayPOJO) throws Exception {
		customerPayPOJO = customerPayPOJOMapper.selectByPrimaryKey(customerPayPOJO.getCustPayId());
		CustomerSimPOJO customerServerPOJO = customerSimPOJOMapper.getCustomerSimPOJOById(customerPayPOJO.getSimNo());
		
		Date endTime = customerPayPOJO.getStartTime();
		customerServerPOJO.setEndTime(endTime);
		customerSimPOJOMapper.updateByPrimaryKey(customerServerPOJO);
		customerPayPOJO.setStatus(new BigDecimal(0));
		customerPayPOJO.setIsLast(new BigDecimal(0));
		customerPayPOJOMapper.updateStatus(customerPayPOJO);		
	}

	@Override
	public List<CustomerPayPOJO> allCustomerPay(CustomerPayPOJO customerPayPOJO)
			throws Exception {
		Map map = new HashMap();
		map.put("customerPayPOJO", customerPayPOJO);
		return customerPayPOJOMapper.allCustomerPay(map);
	}

	@Override
	public List<CustomerPayPOJO> vehicleInfo() {
		return customerPayPOJOMapper.vehicleInfo();
	}
	
}
