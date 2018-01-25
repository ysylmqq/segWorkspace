package com.gboss.dao;

import java.util.List;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Barcode;

/**
 * @Package:com.gboss.dao
 * @ClassName:BarcodeDao
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-4-15 下午7:53:02
 */
public interface BarcodeDao extends BaseDao {
	
	public List<Barcode> getByUnit_id(Long unit_id);
	
	public void deleteByUnit_id(Long unit_id);
	
	public Barcode getByUnit_idAndType(Long unit_id,Integer type);

	public Barcode getBarcodeByUnitId(Long unitId)throws SystemException;

}

