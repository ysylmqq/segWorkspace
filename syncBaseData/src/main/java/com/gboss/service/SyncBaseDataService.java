package com.gboss.service;
/**
 * @Package:com.gboss.service
 * @ClassName:SyncBaseData
 * @Description:TODO
 * @author:bzhang
 * @date:2015-2-28 上午8:51:34
 */
public interface SyncBaseDataService extends BaseService {
	
	public boolean checkConnect();
	
	public boolean syncSeries()throws Exception ;
	
	public boolean syncModel()throws Exception ;

	public boolean syncCommonCompany()throws Exception ; 
	
	public boolean syncInsuracer()throws Exception ; 
	
	public boolean syncCombo() throws Exception;
	
	public boolean syncInfo()throws Exception;
	
	public boolean syncBindInfo()throws Exception;

}

