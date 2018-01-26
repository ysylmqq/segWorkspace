package cc.chinagps.seat.dao;

import java.math.BigInteger;
import java.util.List;

import cc.chinagps.seat.bean.table.CommandTable;

public interface CmdDao {
	
	/**
	 * 获取指令列表
	 * @param phoneNo
	 * @return
	 */
	List<CommandTable> getCmdListByPhoneNo(String phoneNo);

	/**
	 * 获取指令列表
	 * @param vehicleId
	 * @return
	 */
	List<CommandTable> getCmdListByVehicleId(BigInteger vehicleId);
}
