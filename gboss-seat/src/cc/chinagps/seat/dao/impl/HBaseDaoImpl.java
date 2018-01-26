package cc.chinagps.seat.dao.impl;

import java.util.List;

import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsBaseInfo;
import cc.chinagps.gboss.comcenter.buff.HBaseKeyUtil;
import cc.chinagps.seat.bean.HistoryTrack;
import cc.chinagps.seat.dao.HBaseDao;
import cc.chinagps.seat.hbase.ResultsExtractor;

@Repository
public class HBaseDaoImpl implements HBaseDao {
	
	@Autowired
	private HConnection conn;
	
	@Override
	public List<GpsBaseInfo> getGpsBasicInfoList(String tableName, 
			HistoryTrack historyTrack, ResultsExtractor<List<GpsBaseInfo>> extractor) throws Exception {
		HTableInterface table = null;
		ResultScanner scanner = null;
		try {
			table = conn.getTable(tableName);
			byte[] startRow = HBaseKeyUtil.getKey(historyTrack.getCallLetter(),
					historyTrack.getStartTime().getTime());
			byte[] stopRow = HBaseKeyUtil.getKey(historyTrack.getCallLetter(),
					historyTrack.getEndTime().getTime());
			Scan scan = new Scan(startRow, stopRow);
			scan.setReversed(true);
			scanner = table.getScanner(scan);
			return extractor.extractData(scanner);
		} finally {
			if (scanner != null) {
				scanner.close();
			}
			if (table != null) {
				table.close();
			}
		}
	}
}
