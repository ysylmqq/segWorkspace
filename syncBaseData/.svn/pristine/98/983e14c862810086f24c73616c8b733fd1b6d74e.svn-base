package com.gboss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Preload;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service
 * @ClassName:SimService
 * @Description:TODO
 * @author:bzhang
 * @date:2014-9-23 上午11:11:13
 */
public interface PreloadService extends BaseService {
	
	public void testTask() throws Exception; 
	
	public void gethmCall_letter() throws Exception; 

	public int addSim(Preload sim)throws SystemException;
	
	public Preload getPreloadByCl(String call_letter)throws SystemException;
	
	/**
	 * 根据barcode查询sim信息
	 * @param barcode
	 * @return
	 * @throws SystemException
	 */
	public Preload getPreloadByBarCode(String barcode) throws SystemException;
	
	/**
	 * 根据vin码查询sim卡的信息
	 * @param vin
	 * @return
	 * @throws SystemException
	 */
	public Preload getPreloadByVin(String vin) throws SystemException;
	
	/**
	 * 根据给定的vin返回sim卡集合
	 * @param vin
	 * @return
	 * @throws SystemException
	 */
	public List<Preload> getPreloadsByVin(String vin) throws SystemException;
	
	/**
	 * 根据vin 和barcode 查询是否存在sim的信息
	 * @param vin
	 * @param barcode
	 * @return
	 * @throws SystemException
	 */
	public Preload getPreloadByVinBarcode(String vin,String barcode) throws SystemException;
	
	/**
	 * 批量更新sim卡信息
	 * @param sims
	 * @throws SystemException
	 */
	public void batchUpdateSim(List<Preload> sims) throws SystemException ;
	
	public void updateSim(Preload sim);
	public void saveSim(Preload sim);
	
	
}

