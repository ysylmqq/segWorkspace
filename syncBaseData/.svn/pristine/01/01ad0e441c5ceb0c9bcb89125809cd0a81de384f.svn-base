package com.gboss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.BarcodeDao;
import com.gboss.pojo.Barcode;
import com.gboss.service.BarcodeService;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:BarcodeServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-4-14 下午5:46:53
 */
@Service("BarcodeService")
public class BarcodeServiceImpl extends BaseServiceImpl implements BarcodeService {

	@Autowired  
	@Qualifier("BarcodeDao")
	private BarcodeDao barcodeDao;
	
	 
	public void add(Barcode barcode) {
//		save(barcode);
	}

	 
	public List<Barcode> getByUnit_id(Long unit_id) {
		return barcodeDao.getByUnit_id(unit_id);
	}

	 
	public void deleteByUnit_id(Long unit_id) {
		barcodeDao.deleteByUnit_id(unit_id);
	}

	 
	public Barcode getByUnit_idAndType(Long unit_id, Integer type) {
		return barcodeDao.getByUnit_idAndType(unit_id, type);
	}

}

