package com.gboss.dao;

import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Preload;

public interface CallletterDao extends BaseDao {
	
	/**
	 * 根据给定的时间段批量获取车载电话
	 * @param begintime	开始时间
	 * @param endtime	结束时间
	 * @return
	 * @throws SystemException
	 */
	public List<Preload> getCallLettersByTimes( String begintime,String endtime,String barcode) throws SystemException  ;
	
	public List<Preload> getCallLettersByTimes(Map<String,String> params) throws SystemException; 
	
}
