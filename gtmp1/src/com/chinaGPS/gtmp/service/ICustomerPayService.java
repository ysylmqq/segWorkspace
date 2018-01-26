package com.chinaGPS.gtmp.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chinaGPS.gtmp.entity.CustomerPayPOJO;
import com.chinaGPS.gtmp.util.page.PageSelect;

/**
 * @Package:com.chinaGPS.gtmp.service
 * @ClassName:ISimServerService
 * @Description:玉柴重工使用SIM卡service
 * @author:于松亚
 * @date:2017-3-21 上午10:15:43
 */
public interface ICustomerPayService extends BaseService<CustomerPayPOJO> {
	
	/**
	 * 分页查询用户的缴费情况
	 * @param customerPayPOJO
	 * @param pageSelect
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> getCustomerPays(CustomerPayPOJO customerPayPOJO,PageSelect pageSelect) throws Exception;

	/**
	 * 用户缴费  1:缴费成功   0：客户没有开通SIM卡服务  -1：终端没有开通SIM卡服务，也就是注册终端时没有网t_sim_server当中添加一条记录
	 * @param customerPayPOJO
	 * @throws Exception
	 */
	public int custPay(CustomerPayPOJO customerPayPOJO) throws Exception;
	
	/**
	 * 获取t_unit当中的所有的sim_no
	 * @return
	 */
    public  List<CustomerPayPOJO> simList();
    
    
	/**
	 * 获取t_unit当中的所有的sim_no
	 * @return  返回错误的list
	 * @throws Exception 
	 */
    public  List<CustomerPayPOJO> importCustomerPay(List<CustomerPayPOJO> list,String userId) throws Exception;
    
    

    public CustomerPayPOJO getCustomerPayPOJO(BigDecimal custPayId) throws Exception;
    
    
    /**
	 * 批量用户缴费
	 * @param customerPayPOJO
	 * @throws Exception
	 */
	public CustomerPayPOJO batchCustPay(CustomerPayPOJO customerPayPOJO) throws Exception;
    
	
    
    /**
	 * 更新是否是最新缴费记录
	 * @throws Exception
	 */
	public void updateIsLast(CustomerPayPOJO customerPayPOJO) throws Exception;
	
	
	
	   /**
	 * 更新是否是status
	 * @throws Exception
	 */
	public void updateStatus(CustomerPayPOJO customerPayPOJO) throws Exception;
	
	
	/**
	 * 导出客户付款信息表
	 * @param customerPayPOJO
	 * @return
	 * @throws Exception
	 */
	public List<CustomerPayPOJO> allCustomerPay(CustomerPayPOJO customerPayPOJO) throws Exception;

	/**
	 * 查村车辆的所有相关信息
	 * @return
	 */
	public List<CustomerPayPOJO> vehicleInfo();



}