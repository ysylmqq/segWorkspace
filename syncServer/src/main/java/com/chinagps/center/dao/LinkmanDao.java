package com.chinagps.center.dao;

import java.util.List;

import com.chinagps.center.common.SystemException;
import com.chinagps.center.pojo.Linkman;

public interface LinkmanDao extends BaseDao {

	public List<Linkman> listByVehicleId(Long vehicle_id)throws SystemException;

}
