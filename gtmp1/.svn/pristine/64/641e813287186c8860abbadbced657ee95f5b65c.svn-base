package com.chinaGPS.gtmp.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.chinaGPS.gtmp.entity.UnitPOJO;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.mapper.CustomerPayPOJOMapper;
import com.chinaGPS.gtmp.mapper.CustomerSimPOJOMapper;
import com.chinaGPS.gtmp.mapper.SimPayPOJOMapper;
import com.chinaGPS.gtmp.mapper.SimServerPOJOMapper;
import com.chinaGPS.gtmp.service.ICustomerPayService;
import com.chinaGPS.gtmp.service.ICustomerSimService;
import com.chinaGPS.gtmp.service.ISimPayService;
import com.chinaGPS.gtmp.service.IUnitService;
import com.chinaGPS.gtmp.util.page.PageSelect;

/**
 * @Package:com.chinaGPS.gtmp.service
 * @ClassName:ISimServerService
 * @Description:玉柴重工使用SIM卡service
 * @author:于松亚
 * @date:2017-3-21 上午10:15:43
 */
@Service
public class CustomerSimServiceImpl extends BaseServiceImpl<CustomerSimPOJO> implements ICustomerSimService {
	@Resource
	private CustomerSimPOJOMapper<CustomerSimPOJO> customerSimPOJOMapper;
	
	@Resource
	private SimServerPOJOMapper<SimServerPOJO> simServerPOJOMapper;
	
	@Resource
	private IUnitService iUnitService;

	@Override
	protected BaseSqlMapper<CustomerSimPOJO> getMapper() {
		return this.customerSimPOJOMapper;
	}

	@Override
	public CustomerSimPOJO getCustomerSimById(String simNo) {

		return customerSimPOJOMapper.getCustomerSimPOJOById(simNo);
	}

	@Override
	public HashMap<String, Object> getCustomerSimPage(CustomerSimPOJO customerSimPOJO,
			PageSelect pageSelect) throws Exception {
		Map map = new HashMap();
		map.put("customerSimPOJO", customerSimPOJO);
		int total = 0;
		List<CustomerSimPOJO> resultList = new ArrayList<CustomerSimPOJO>();
		try{
			 total = customerSimPOJOMapper.countAll(map);
			 resultList = customerSimPOJOMapper.getByPage(map,new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		}catch (Exception e) {
			e.printStackTrace();
		}
		HashMap<String, Object> result=new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}

	@Override
	public boolean insertSelective(CustomerSimPOJO customerSimPOJO) {
		//判读是哦福村咋
		//插入的时候 先判断是否存在 
		 CustomerSimPOJO temp = customerSimPOJOMapper.getCustomerSimPOJOById(customerSimPOJO.getSimNo());
			if( temp ==  null){
				 customerSimPOJOMapper.insertSelective(customerSimPOJO);
				return true;
			}else{
				return false;
			}
	}

	@Override
	public CustomerSimPOJO getCustomerSimPOJOById(String simNo) {
		return customerSimPOJOMapper.getCustomerSimPOJOById(simNo);
	}

	@Override
	public void updateByPrimaryKeySelective(CustomerSimPOJO customerSimPOJO)
			throws Exception {
		customerSimPOJOMapper.updateByPrimaryKeySelective(customerSimPOJO);
	}
	
	@Override
	public List<CustomerSimPOJO> batchInsert(List<CustomerSimPOJO> list,Long userId) throws Exception {
		Map map = new HashMap();
		map.put("simServerPOJO", null);
		List<SimServerPOJO> listSimServerPOJO = simServerPOJOMapper.allSimServer(map);
		List<String> simNoList  = new ArrayList<String>();
		for (Iterator iterator = listSimServerPOJO.iterator(); iterator
				.hasNext();) {
			SimServerPOJO simServerPOJO = (SimServerPOJO) iterator.next();
			simNoList.add(simServerPOJO.getSimNo());
		}
		
		List<CustomerSimPOJO> errorList = new ArrayList<CustomerSimPOJO>();
		boolean res = true;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			CustomerSimPOJO simServerPOJO = (CustomerSimPOJO) iterator.next();
			String simNo = simServerPOJO.getSimNo();
			Date endTime = simServerPOJO.getEndTime();
			Date startTime = simServerPOJO.getStartTime();

			StringBuilder erroTips = new StringBuilder("");
			boolean flag = true;
			if(simNo == null || "".equalsIgnoreCase(simNo)){
				erroTips.append("simNO为空！");
				flag = false;
			}else {
				int len = simNo.length();
				if( len != 11){
					erroTips.append("simNO有误");
					flag = false;
				}
			}
			
			if(startTime == null){
				erroTips.append("服务开始日期为空！");
				flag = false;
			}
			
			if(endTime == null){
				erroTips.append("服务结束日期为空！");
				flag = false;
			}
			
			if(flag){
				//包含了 判断是否存在	
				simServerPOJO.setOperId(userId.toString());
				//先判断公司是否开通了SIM卡功能
				if(simNoList.contains(simServerPOJO.getSimNo())){
					res = this.insertSelective(simServerPOJO); 
					if(!res){
						simServerPOJO.setRemark("已经开通");
						errorList.add(simServerPOJO);
					}
				}else{
					simServerPOJO.setRemark("请先开通公司SIM卡功能");
					errorList.add(simServerPOJO);
				}
			}else{
				simServerPOJO.setRemark(erroTips.toString());
				errorList.add(simServerPOJO);
			}
		}
		return errorList;
	}

	@Override
	public List<CustomerSimPOJO> allCustomerSim(CustomerSimPOJO simServerPOJO) {
		Map map = new HashMap();
		map.put("customerSimPOJO", simServerPOJO);
		return customerSimPOJOMapper.allCustomerSim(map);
	}

	@Override
	public List<CustomerSimPOJO> exportCustomerSim(CustomerSimPOJO simServerPOJO) {
		Map map = new HashMap();
		map.put("customerSimPOJO", simServerPOJO);
		return customerSimPOJOMapper.exportCustomerSim(map);
	}
	@Override
	public void updateStatus(CustomerSimPOJO customerSimPOJO) throws Exception {
		customerSimPOJOMapper.updateStatus(customerSimPOJO);
	}

	@Override
	public List<CustomerSimPOJO> customerSimList() {
		return customerSimPOJOMapper.customerSimList();
	}

	@Override
	public boolean batchStopSimServer(CustomerSimPOJO customerSimPojo) throws Exception {
		String simNo = customerSimPojo.getSimNo();
		if(!"".equalsIgnoreCase(simNo) && simNo != null ){
			String [] simNoList = simNo.split(",");
			for( int i = 0 ; i<simNoList.length; i++){
				CustomerSimPOJO customerSim = new CustomerSimPOJO();
				customerSim.setSimNo(simNoList[i]);
				customerSim.setStatus(new BigDecimal(1));
				customerSim.setStopStartTime(customerSimPojo.getStopStartTime());
				customerSim.setStopReason(customerSimPojo.getStopReason());
				customerSim.setStopEndTime(customerSimPojo.getStopEndTime());
				customerSim.setOperId(customerSimPojo.getOperId());
				customerSimPOJOMapper.updateStatus(customerSim);
			}
			return true;
		}
		return false;
	}

	@Override
	public List<CustomerSimPOJO> batchStopSimServer(List<CustomerSimPOJO> list,Long userId) throws Exception {
		List<CustomerSimPOJO> errorList = new ArrayList<CustomerSimPOJO>();
		boolean res = true;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			CustomerSimPOJO simServerPOJO = (CustomerSimPOJO) iterator.next();
			String simNo = simServerPOJO.getSimNo();
			Date endTime = simServerPOJO.getStopEndTime();
			Date startTime = simServerPOJO.getStopStartTime();

			StringBuilder erroTips = new StringBuilder("");
			boolean flag = true;
			if(simNo == null || "".equalsIgnoreCase(simNo)){
				erroTips.append("simNO为空！");
				flag = false;
			}else {
				int len = simNo.length();
				if( len != 11){
					erroTips.append("simNO有误");
					flag = false;
				}
			}
			
			if(startTime == null){
				erroTips.append("服务开始日期为空！");
				flag = false;
			}
			
			if(endTime == null){
				erroTips.append("服务结束日期为空！");
				flag = false;
			}
			
			if(flag){
				// 判断SIM卡对应的终端是否存在
				UnitPOJO unitPOJO = new UnitPOJO();
				unitPOJO.setSimNo(simNo);
				List<UnitPOJO> unitPOJOList =  iUnitService.getUnitBySimNo(unitPOJO);
				if(list == null || list.size() == 0){
					simServerPOJO.setRemark("终端表当中不存在该SIM卡号");
					errorList.add(simServerPOJO);
				}else{
					//判断是否该SIM 是否开通
					CustomerSimPOJO temp =  customerSimPOJOMapper.selectByPrimaryKey(simServerPOJO.getSimNo());
					if( temp == null){
						//没有开通SIM服务
						simServerPOJO.setRemark("没有开通SIM服务,请申请开通");
						errorList.add(simServerPOJO);
					}else{
						//开通服务已有 判断是否 停机保号了
						if(temp.getStatus().intValue() == 1){
							simServerPOJO.setRemark("已处于停机保号状态");
							errorList.add(simServerPOJO);
						}else{
							simServerPOJO.setStatus(new BigDecimal(1));
							simServerPOJO.setOperId(userId.toString());
							customerSimPOJOMapper.updateStatus(simServerPOJO);
						}
					}
				}
			
			}else{
				simServerPOJO.setRemark(erroTips.toString());
				errorList.add(simServerPOJO);
			}
		}
		return errorList;
	}

}
