package cc.chinagps.seat.dao;

import java.math.BigInteger;
import java.util.List;

import cc.chinagps.seat.bean.table.BriefTable;

public interface TelDao {

	/**
	 * 获取所有简报、警单信息
	 * @param vehicleId
	 * @param type
	 * @param count
	 * @return
	 */
	List<BriefTable> getBriefList(BigInteger vehicleId,String phone, int count);

	/**
	 * 保存简报、警单信息
	 * @param table
	 * @return
	 */
	long saveBrief(BriefTable table);
}
