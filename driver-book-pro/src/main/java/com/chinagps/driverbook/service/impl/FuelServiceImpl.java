package com.chinagps.driverbook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.chinagps.driverbook.dao.CustOilPriceMapper;
import com.chinagps.driverbook.dao.FuelMapper;
import com.chinagps.driverbook.dao.OilPriceMapper;
import com.chinagps.driverbook.pojo.CustOilPrice;
import com.chinagps.driverbook.pojo.Fuel;
import com.chinagps.driverbook.pojo.OilPrice;
import com.chinagps.driverbook.pojo.ReturnValue;
import com.chinagps.driverbook.service.IFuelService;

@Service
@Scope("prototype")
public class FuelServiceImpl extends BaseServiceImpl<Fuel> implements IFuelService {
	
	@Autowired
	private FuelMapper fuelMapper;
	@Autowired
	private OilPriceMapper oilPriceMapper;
	@Autowired
	private CustOilPriceMapper custOilPriceMapper;

	public ReturnValue findPriceByProvince(String province, ReturnValue rv) throws Exception {
		OilPrice oilPrice = oilPriceMapper.findByProvince(province);
		rv.setDatas(oilPrice);
		return rv;
	}
	
	public ReturnValue findCustPrice(CustOilPrice custOilPrice, ReturnValue rv) throws Exception {
		CustOilPrice cop = custOilPriceMapper.find(custOilPrice);
		rv.setDatas(cop);
		return rv;
	}
	
	public ReturnValue addOrEditPrice(CustOilPrice custOilPrice, ReturnValue rv) throws Exception {
		CustOilPrice cop = custOilPriceMapper.find(custOilPrice);
		if (cop == null) {
			int addCount = custOilPriceMapper.add(custOilPrice);
			if (addCount > 0) {
				rv.setSuccess(true);
			} else {
				rv.saveErrror();
			}
		} else {
			int editCount = custOilPriceMapper.edit(custOilPrice);
			if (editCount > 0) {
				rv.setSuccess(true);
			} else {
				rv.editError();
			}
		}
		return rv;
	}

}
