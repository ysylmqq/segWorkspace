package com.gboss.service;

import java.util.List;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Barcode;

/**
 * @Package:com.gboss.service
 * @ClassName:BarcodeService
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-4-14 下午5:46:10
 */
public interface BarcodeService extends BaseService {
	
	public void add(Barcode barcode);
	
	public List<Barcode> getByUnit_id(Long unit_id);
	
	public void deleteByUnit_id(Long unit_id);
	
	public Barcode getByUnit_idAndType(Long unit_id,Integer type);

	public Barcode getBarcodeByUnitId(Long unitId)throws SystemException;

}

