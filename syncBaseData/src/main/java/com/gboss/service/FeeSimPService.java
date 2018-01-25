package com.gboss.service;

import java.util.List;

import com.gboss.comm.SystemException;
import com.gboss.pojo.FeeSimP;

public interface FeeSimPService {

	public void addFeeSimP(FeeSimP fsp) throws SystemException;

	public void modifyFeeSimP(FeeSimP fsp) throws SystemException;
	
	public FeeSimP getFeeSimPByCL(String call_letter,int period);
	
	public List<FeeSimP> getFeeSimsPByCL(String call_letter,int period);
	
	
}
