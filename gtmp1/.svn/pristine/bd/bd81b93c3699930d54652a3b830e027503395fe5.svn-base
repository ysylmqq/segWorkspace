package com.chinaGPS.gtmp.service;

import java.util.HashMap;
import java.util.List;

import com.chinaGPS.gtmp.entity.CustomerSimPOJO;
import com.chinaGPS.gtmp.entity.SimPayPOJO;
import com.chinaGPS.gtmp.entity.SimServerPOJO;
import com.chinaGPS.gtmp.util.page.PageSelect;

/**
 * @Package:com.chinaGPS.gtmp.service
 * @ClassName:ISimServerService
 * @Description:玉柴重工使用SIM卡service
 * @author:于松亚
 * @date:2017-3-21 上午10:15:43
 */
public interface ICustomerSimService extends BaseService<CustomerSimPOJO> {
    
	/**
	 * 根据simNo获取CustomerSimPOJO
	 * @param simNo
	 * @return
	 */
	public CustomerSimPOJO getCustomerSimById(String simNo);
	
	/*
	 *分页查询
	 * 
	 */
	public HashMap<String, Object> getCustomerSimPage(CustomerSimPOJO customerSimPOJO,
			PageSelect pageSelect) throws Exception;

	/*
	 * 插入客户SIM
	 */
	public boolean insertSelective(CustomerSimPOJO customerSimPOJO)throws Exception;

	/**
	 * 更新
	 * @param customerSimPOJO
	 * @throws Exception
	 */
	public void updateByPrimaryKeySelective(CustomerSimPOJO customerSimPOJO)throws Exception;

	
	public CustomerSimPOJO getCustomerSimPOJOById(String simNo);
	
	/**
	 * 批量导入
	 * @param simServerPOJO
	 * @throws Exception
	 */
	public List<CustomerSimPOJO> batchInsert(List<CustomerSimPOJO> list,Long useId) throws Exception;

	/**
	 * 批量导出
	 * @param simServerPOJO
	 * @return
	 */
	public List<CustomerSimPOJO> allCustomerSim(CustomerSimPOJO simServerPOJO);
	
	public List<CustomerSimPOJO> exportCustomerSim(CustomerSimPOJO simServerPOJO);

	
	public void updateStatus(CustomerSimPOJO customerSimPOJO)throws Exception;
	
	public List<CustomerSimPOJO> customerSimList();
	
	public boolean batchStopSimServer(CustomerSimPOJO customerSim) throws Exception;

	
	/**
	 * 批量停机保号
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<CustomerSimPOJO> batchStopSimServer(List<CustomerSimPOJO> list,Long userId)
			throws Exception;
}