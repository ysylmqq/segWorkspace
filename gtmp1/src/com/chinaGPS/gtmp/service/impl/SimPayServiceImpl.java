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

import com.chinaGPS.gtmp.entity.CustomerPayPOJO;
import com.chinaGPS.gtmp.entity.CustomerSimPOJO;
import com.chinaGPS.gtmp.entity.SimPayPOJO;
import com.chinaGPS.gtmp.entity.SimServerPOJO;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.mapper.SimPayPOJOMapper;
import com.chinaGPS.gtmp.mapper.SimServerPOJOMapper;
import com.chinaGPS.gtmp.service.ISimPayService;
import com.chinaGPS.gtmp.util.page.PageSelect;

/**
 * @Package:com.chinaGPS.gtmp.service
 * @ClassName:ISimServerService
 * @Description:玉柴重工使用SIM卡service
 * @author:于松亚
 * @date:2017-3-21 上午10:15:43
 */
@Service
public class SimPayServiceImpl extends BaseServiceImpl<SimPayPOJO> implements ISimPayService {
	@Resource
	private SimPayPOJOMapper<SimPayPOJO> simPayPOJOMapper;
	
	@Resource
	private SimServerPOJOMapper<SimServerPOJO> simServerPOJOMapper;

	@Override
	protected BaseSqlMapper<SimPayPOJO> getMapper() {
		return this.simPayPOJOMapper;
	}

	@Override
	public SimPayPOJO batchSimPay(SimPayPOJO simPayPOJO) throws Exception {
		SimServerPOJO simServerPOJO = simServerPOJOMapper.getSimServerPOJOById(simPayPOJO.getSimNo());
		if(simServerPOJO == null ){
			simPayPOJO.setRemark("终端没有开通SIM服务");
			return simPayPOJO;
		}
		Date customerPayStartTime = simServerPOJO.getEndTime();
		simPayPOJO.setStartTime(customerPayStartTime);//sim卡结束服时间 是下一次的缴费的开始时间
		//插入新的记录之前 update isLast = 0;
		simPayPOJO.setIsLast(new BigDecimal(0));
		simPayPOJOMapper.updateIsLast(simPayPOJO);
		//插入新的记录   
		simPayPOJO.setIsLast(new BigDecimal(1));
		simPayPOJO.setStatus(new BigDecimal(1));
		simPayPOJOMapper.insert(simPayPOJO);
		
		Date customerPayEndTime = simPayPOJO.getEndTime();
		simServerPOJO.setEndTime(customerPayEndTime);
		simServerPOJO.setOperId(simPayPOJO.getOperId());
		simServerPOJOMapper.updateByPrimaryKey(simServerPOJO);
		return null;
	}

	@Override
	public boolean companyPay(SimPayPOJO simPayPOJO) throws Exception {
		SimServerPOJO simServerPOJO = simServerPOJOMapper.getSimServerPOJOById(simPayPOJO.getSimNo());
		if(simServerPOJO == null ){
			return false;
		}
		Date customerPayStartTime = simServerPOJO.getEndTime();
		simPayPOJO.setStartTime(customerPayStartTime);//sim卡结束服时间 是下一次的缴费的开始时间
		//插入新的记录之前 update isLast = 0;
		simPayPOJO.setIsLast(new BigDecimal(0));
		simPayPOJOMapper.updateIsLast(simPayPOJO);
		//插入新的记录   
		simPayPOJO.setIsLast(new BigDecimal(1));
		simPayPOJO.setStatus(new BigDecimal(1));
		simPayPOJOMapper.insert(simPayPOJO);
		
		Date customerPayEndTime = simPayPOJO.getEndTime();
		simServerPOJO.setEndTime(customerPayEndTime);
		simServerPOJO.setOperId(simPayPOJO.getOperId());
		simServerPOJOMapper.updateByPrimaryKey(simServerPOJO);
		return true;
	}

	@Override
	public SimPayPOJO getSimPayPOJO(BigDecimal custPayId) throws Exception {
		return simPayPOJOMapper.selectByPrimaryKey(custPayId);
	}

	@Override
	public HashMap<String, Object> getSimPays(SimPayPOJO simPayPOJO,
			PageSelect pageSelect) throws Exception {
		Map map = new HashMap();
		map.put("simPayPOJO", simPayPOJO);
		int total = simPayPOJOMapper.countAll(map);
		List<SimPayPOJO> resultList =  simPayPOJOMapper.getByPage(map,new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result=new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}

	@Override
	public List<SimPayPOJO> importCompanyPay(List<SimPayPOJO> list,String userId)
			throws Exception {
		List<SimPayPOJO> errorList = new ArrayList<SimPayPOJO>();
		SimPayPOJO res = null;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			SimPayPOJO customerPayPOJO = (SimPayPOJO) iterator.next();
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
				res = this.batchSimPay(customerPayPOJO); //返回null标示成功
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
	public List<SimPayPOJO> simList() {
		return simPayPOJOMapper.simList();

	}

	@Override
	public void updateIsLast(SimPayPOJO simPayPOJO) throws Exception {
		simPayPOJOMapper.updateIsLast(simPayPOJO);
	}

	@Override
	public void updateStatus(SimPayPOJO simPayPOJO) throws Exception {
		simPayPOJO = simPayPOJOMapper.selectByPrimaryKey(simPayPOJO.getSimPayId());
		Date endTime = simPayPOJO.getStartTime();
		SimServerPOJO simServerPOJO = simServerPOJOMapper.getSimServerPOJOById(simPayPOJO.getSimNo());
		simServerPOJO.setEndTime(endTime);
		
		simServerPOJOMapper.updateByPrimaryKey(simServerPOJO);
		simPayPOJO.setStatus(new BigDecimal(0));
		simPayPOJO.setIsLast(new BigDecimal(0));
		simPayPOJOMapper.updateStatus(simPayPOJO);		

	}

	@Override
	public List<SimPayPOJO> allCompanyPay(SimPayPOJO customerPayPOJO) {
		Map map = new HashMap();
		map.put("simPayPOJO", customerPayPOJO);
		return simPayPOJOMapper.allCustomerPay(map);
	}
}
