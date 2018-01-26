package cc.chinagps.seat.dao.impl;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Repository;

import cc.chinagps.seat.bean.table.CommandTable;
import cc.chinagps.seat.dao.CmdDao;

@Repository("cmdDao")
public class CmdDaoImpl extends BasicDao implements CmdDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<CommandTable> getCmdListByPhoneNo(String phoneNo) {
		// 如果电话号码前有0，则去掉
		if (phoneNo.startsWith("0")) {
			phoneNo = phoneNo.substring(1);
		}
		return getSession().getNamedQuery("SelectCommandByPhoneNo")
				.setParameter("phoneNo", phoneNo).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public java.util.List<CommandTable> getCmdListByVehicleId(BigInteger vehicleId) {
		return getSession().getNamedQuery("SelectCommandByVehicleId")
				.setParameter("vehicleId", vehicleId).list();
	}

}
