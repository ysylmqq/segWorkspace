package com.chinagps.driverbook.service;

import java.util.List;

import com.chinagps.driverbook.pojo.ReturnValue;
import com.chinagps.driverbook.pojo.Unit;

public interface IUnitService extends BaseService<Unit> {

	public ReturnValue getCallLetterByBarcode(String imei, String barcode, ReturnValue rv) throws Exception;
	
	public ReturnValue getCallLetterByVin(String imei, String vin, ReturnValue rv) throws Exception;
	
	/*
	 * 根据车台联系电话 查询 车台信息
	 * */
	public Unit findIsUsed(String callLetter);
	
	public List<String> getUnitCallLetterSubcoNo(String subcoNo); 
	
}
