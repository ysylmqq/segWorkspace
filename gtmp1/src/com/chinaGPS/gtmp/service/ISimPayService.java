package com.chinaGPS.gtmp.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.chinaGPS.gtmp.entity.CustomerPayPOJO;
import com.chinaGPS.gtmp.entity.SimPayPOJO;
import com.chinaGPS.gtmp.util.page.PageSelect;

/**
 * @Package:com.chinaGPS.gtmp.service
 * @ClassName:ISimServerService
 * @Description:玉柴重工使用SIM卡service
 * @author:于松亚
 * @date:2017-3-21 上午10:15:43
 */
public interface ISimPayService extends BaseService<SimPayPOJO> {
	/**
	 * 分页查询用户的缴费情况
	 * @param SimPayPOJO
	 * @param pageSelect
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> getSimPays(SimPayPOJO simPayPOJO,PageSelect pageSelect) throws Exception;

	/**
	 * 公司缴费
	 * @param customerPayPOJO
	 * @throws Exception
	 */
	public boolean companyPay(SimPayPOJO simPayPOJO) throws Exception;
	
	/**
	 * 获取t_sim_server当中的所有的sim_no
	 * @return
	 */
    public  List<SimPayPOJO> simList();
    
    
	/**
	 * 获取t_unit当中的所有的sim_no
	 * @return  返回错误的list
	 * @throws Exception 
	 */
    public  List<SimPayPOJO> importCompanyPay(List<SimPayPOJO> list,String userId) throws Exception;
    
    

    public SimPayPOJO getSimPayPOJO(BigDecimal custPayId) throws Exception;
    
    /**
	 * 批量用户缴费
	 * @param SimPayPOJO
	 * @throws Exception
	 */
	public SimPayPOJO batchSimPay(SimPayPOJO simPayPOJO) throws Exception;
    
	
    
    /**
	 * 更新是否是最新缴费记录
	 * @throws Exception
	 */
	public void updateIsLast(SimPayPOJO simPayPOJO) throws Exception;
	
	
	
	   /**
	 * 更新是否是status
	 * @throws Exception
	 */
	public void updateStatus(SimPayPOJO simPayPOJO) throws Exception;

	/**
	 * 根据条件导出 
	 * @param customerPayPOJO
	 * @return
	 */
	public List<SimPayPOJO> allCompanyPay(SimPayPOJO customerPayPOJO);

	
}