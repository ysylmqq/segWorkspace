package com.gboss.service;

import java.util.Date;

import com.gboss.pojo.Onduty;

/**
 * @Package:com.gboss.service
 * @ClassName:OndutyService
 * @Description:电工值班业务层接口
 * @author:bzhang
 * @date:2014-3-26 下午3:59:08
 */
public interface OndutyService extends BaseService {

	public Onduty getOndutyByIdAndTime(String userId, Date time);
}

