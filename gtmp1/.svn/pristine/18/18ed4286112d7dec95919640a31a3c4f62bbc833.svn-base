package com.chinaGPS.gtmp.service;

import java.util.HashMap;

import com.chinaGPS.gtmp.entity.UnitReplacePOJO;
import com.chinaGPS.gtmp.util.page.PageSelect;

/**
 * 终端换装Service接口
 * @author Ben
 *
 */
public interface IUnitReplaceService extends BaseService<UnitReplacePOJO> {
	
	/**
	 * 获取终端换装日志列表
	 * @param unitReplacePOJO
	 * @param pageSelect
	 * @return
	 */
	public HashMap<String, Object> getUnitReplaceLogs(UnitReplacePOJO unitReplacePOJO,PageSelect pageSelect) throws Exception;
	
	/**
	 * 终端换装
	 * @param unitReplacePOJO
	 * @return
	 */
	public boolean unitReplace(UnitReplacePOJO unitReplacePOJO) throws Exception;
}
