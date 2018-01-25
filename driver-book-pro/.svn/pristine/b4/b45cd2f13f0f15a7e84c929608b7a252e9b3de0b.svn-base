package com.chinagps.driverbook.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinagps.driverbook.pojo.Unit;

public interface UnitMapper extends BaseSqlMapper<Unit> {
	
	public Unit findForRegister(@Param("callLetter") String callLetter, @Param("servPwd") String servPwd);
	
	public String getCallLetterByBarcode(String barcode);
	
	public String getVinByBarcode(String barcode);
	
	public String getCallLetterByVin(String vin);
	
	public String getBarcodeByVin(String vin);
	
	public Unit findIsUsed(String callLetter);
	
	public List<String> getUnitCallLetterSubcoNo(String subco_no);

}
