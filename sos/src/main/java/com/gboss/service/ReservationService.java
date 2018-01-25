package com.gboss.service;

import java.util.HashMap;

import com.gboss.pojo.Reservation;

/**
 * @Package:com.gboss.service
 * @ClassName:ReservationService
 * @Description:工单预约业务层接口
 * @author:bzhang
 * @date:2014-4-3 下午3:18:01
 */
public interface ReservationService extends BaseService {
	public  HashMap<String, Object> getReservationBytask(Long taskId);

}

