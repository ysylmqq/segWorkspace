package com.chinagps.driverbook.service;

import com.chinagps.driverbook.pojo.Maintain;
import com.chinagps.driverbook.pojo.ReturnValue;

public interface IMaintainService extends BaseService<Maintain> {
	
	/**
	 * 初始化保养信息及查找当前保养信息
	 * @param maintain 保养信息
	 * @param rv 空的ReturnValue实例
	 * @return 包含保养信息的ReturnValue实例
	 * @throws Exception
	 */
	public ReturnValue initAndFind(Maintain maintain, ReturnValue rv) throws Exception;
	
	/**
	 * 保养确认，进入下一保养周期
	 * @param vehicleId 车辆ID
	 * @param rv 空的ReturnValue实例
	 * @return 包含操作结果的ReturnValue实例
	 * @throws Exception
	 */
	public ReturnValue nextPeriod(String vehicleId, ReturnValue rv) throws Exception;
	
	public ReturnValue findItemsByModel(String model, ReturnValue rv) throws Exception;
	
}
