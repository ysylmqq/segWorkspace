package cc.chinagps.seat.dao;

import java.util.List;

import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsBaseInfo;
import cc.chinagps.seat.bean.HistoryTrack;
import cc.chinagps.seat.hbase.ResultsExtractor;

public interface HBaseDao {

	/**
	 * 获取所有车辆的gps基础信息
	 * @param tableName
	 * @param historyTrack
	 * @param extractor
	 * @return
	 * @throws Exception
	 */
	List<GpsBaseInfo> getGpsBasicInfoList(
			String tableName, HistoryTrack historyTrack,
			ResultsExtractor<List<GpsBaseInfo>> extractor) 
					throws Exception;
	
}
