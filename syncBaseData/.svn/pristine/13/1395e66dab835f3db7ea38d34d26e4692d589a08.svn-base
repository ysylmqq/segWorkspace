package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Preload;

/**
 * @Package:com.gboss.dao
 * @ClassName:SimDao
 * @Description:TODO
 * @author:bzhang
 * @date:2014-9-23 上午11:06:07
 */
public interface PreloadDao extends BaseDao {

	
	public Boolean isExist_phone(Preload sim)throws SystemException;
	
	public Boolean isExist_vin(Preload sim)throws SystemException;
	
	public Boolean isExist_barcode(Preload sim)throws SystemException;
	
	public Preload getPreloadByTbox(String barcode)throws SystemException;
	
	public Preload getPreloadByBarcode(String barcode)throws SystemException;
	
	public Preload getPreloadByVin(String vin)throws SystemException;
	
	public List<Preload> getPreloadsByVin(String vin) throws SystemException;
	
	public Preload getPreloadByCl(String call_letter)throws SystemException;
	
	public Preload getPreloadByVinBarcode(String vin,String barcode) throws SystemException;
	
	public Boolean isExist_imei(Preload sim)throws SystemException;
	
	public Boolean isExist_call_letter(Preload sim)throws SystemException;
	
	/**
	 * 批量更新sim卡信息
	 * @param sims
	 * @throws SystemException
	 */
	public void batchUpdateSim(List<Preload> sims) throws SystemException ;
	
	
	public void updateSim(Preload sim);
	public void saveSim(Preload sim);
}

