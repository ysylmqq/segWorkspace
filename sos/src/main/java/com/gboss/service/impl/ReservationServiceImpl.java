package com.gboss.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.ReservationDao;
import com.gboss.pojo.Reservation;
import com.gboss.service.ReservationService;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:ReservationServiceImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-4-3 下午6:28:11
 */
@Service("reservationService")
@Transactional
public class ReservationServiceImpl extends BaseServiceImpl implements
		ReservationService {

	@Autowired
	@Qualifier("reservationDao")
	private ReservationDao reservationDao;

	@Override
	public  HashMap<String, Object> getReservationBytask(Long taskId) {
		return reservationDao.getReservationBytask(taskId);
	}

}
