package com.chinaGPS.gtmp.service;

import java.util.HashMap;
import java.util.List;

import com.chinaGPS.gtmp.entity.SimReplaceLogPOJO;
import com.chinaGPS.gtmp.entity.UnitPOJO;
import com.chinaGPS.gtmp.util.page.PageSelect;

/**
 * @Package:com.chinaGPS.gtmp.service
 * @ClassName:ISimServerService
 * @Description:玉柴重工使用SIM卡service
 * @author:于松亚
 * @date:2017-6-21 上午10:15:43
 */
public interface ISimReplaceLogService extends BaseService<SimReplaceLogPOJO> {
	/**
	 * 分页查询Sim卡换装
	 * @param SimPayPOJO
	 * @param pageSelect
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> getSimReplaceLog(SimReplaceLogPOJO simPayPOJO,PageSelect pageSelect) throws Exception;
	/**
	 * 
	 * @param simPayPOJO
	 * @param pageSelect
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> getUnitPage(UnitPOJO unit,PageSelect pageSelect) throws Exception;

    /**
	 * 保存终端换装
	 * @param SimPayPOJO
	 * @throws Exception
	 */
	public void save(SimReplaceLogPOJO simReplaceLog) throws Exception;
    
	/**
	 * 根据条件导出 
	 * @param SimReplaceLogPOJO
	 * @return
	 */
	public List<SimReplaceLogPOJO> exportSimReplaceLog(SimReplaceLogPOJO customerPayPOJO);
}