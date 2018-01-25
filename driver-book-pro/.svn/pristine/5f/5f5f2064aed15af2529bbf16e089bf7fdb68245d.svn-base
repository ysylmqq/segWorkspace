package com.chinagps.driverbook.service;

import com.chinagps.driverbook.pojo.CustOilPrice;
import com.chinagps.driverbook.pojo.Fuel;
import com.chinagps.driverbook.pojo.ReturnValue;

public interface IFuelService extends BaseService<Fuel> {
	
	/**
	 * 根据省份名称查找最新油价
	 * @param province 省份名称
	 * @param rv ReturnValue实例
	 * @return ReturnValue返回值实例
	 * @throws Exception
	 */
	public ReturnValue findPriceByProvince(String province, ReturnValue rv) throws Exception;
	
	/**
	 * 查询自定义油价
	 * @param custOilPrice 自定义油价实例
	 * @param rv ReturnValue实例
	 * @return ReturnValue返回值实例
	 * @throws Exception
	 */
	public ReturnValue findCustPrice(CustOilPrice custOilPrice, ReturnValue rv) throws Exception;
	
	/**
	 * 新増/修改自定义油价
	 * @param custOilPrice 自定义油价实例
	 * @param rv ReturnValue实例
	 * @return ReturnValue返回值实例
	 * @throws Exception
	 */
	public ReturnValue addOrEditPrice(CustOilPrice custOilPrice, ReturnValue rv) throws Exception;
	
}
