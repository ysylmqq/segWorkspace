package com.gboss.service;

import java.util.HashMap;
import java.util.List;

import com.gboss.pojo.EquipCode;

public interface EquipCodeService extends BaseService {
	
	/**
	 * 查询所有简码码表信息：下发设置配置指令用
	 * @param pramas
	 * @return
	 */
	public List<EquipCode> getAllEquipCode(HashMap<String,Object> pramas);
}
