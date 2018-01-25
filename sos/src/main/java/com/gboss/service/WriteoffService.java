package com.gboss.service;

import com.gboss.pojo.Writeoff;

public interface WriteoffService extends BaseService {

	public void add(Writeoff writeoff);
	
	public float getLastBalance(Long manager_id);
	
}
