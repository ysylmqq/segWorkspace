package com.gboss.dao;

import java.util.List;

import com.gboss.comm.SystemException;
import com.gboss.pojo.FeeSimP;

public interface FeeSimPDao extends BaseDao {
	
	public void addFeeSimP(FeeSimP fsp) throws SystemException;
	
	public void modifyFeeSimP(FeeSimP fsp) throws SystemException;
	
	public FeeSimP getFeeSimPByCL(String call_letter,int period);
	
	public List<FeeSimP> getFeeSimsPByCL(String call_letter,int period);
}
