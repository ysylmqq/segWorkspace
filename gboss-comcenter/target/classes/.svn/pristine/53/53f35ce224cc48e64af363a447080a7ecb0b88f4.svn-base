/*
********************************************************************************************
Discription:  读行程历史信息 
			  
Written By:   ZXZ
Date:         2014-06-04
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.hbase;

import java.io.IOException;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.Result;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.GetHistoryInfo_ACK;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.TravelInfo;

public class TravelHistoryInfo extends HbaseHistoryInfo {

	//取开始时间的GPS;
	private GpsTable gpstable = null;
	//如果取到了开始时间的位置，写回Hbase
	private TravelTable traveltable = null;

	public TravelHistoryInfo(HConnection connection) {
		super(connection);
		super.tablename = "t_travel";
		try {
			gpstable = new GpsTable(connection);
			traveltable = new TravelTable(connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected int getHistoryInfo1(ReqHistoryInfo reqinfo, Result rs, GetHistoryInfo_ACK.Builder historyack) {
		try {
			//判断呼号是否正常
			String callletter1 = new String(rs.getValue(TravelLastInfo.family, TravelLastInfo.qualifier_callLetter));
			if (!callletter1.equals(reqinfo.callletter)) {
				return ResultCode.Hbase_Error;
			}
			//Fault data
			byte[] data = rs.getValue(TravelLastInfo.family, TravelLastInfo.qualifier_data);
			TravelInfo travel = TravelInfo.parseFrom(data);
			//如果行程信息没有开始GPS，则从Hbase取
			if (!travel.hasStartGps()) {
				try {
					//读开始点gps
					GpsInfo startgps = gpstable.GetFirstGpsInfo(reqinfo.callletter, travel.getStartTime(), travel.getEndTime());
					if (startgps != null) {
						TravelInfo.Builder builder = travel.toBuilder();
						if (startgps.hasBaseInfo())
							builder.setStartGps(startgps.getBaseInfo());
						if (startgps.hasReferPosition())
							builder.setStartReferPos(startgps.getReferPosition());
						travel = builder.build();
						traveltable.WriteTravel(travel);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			historyack.addTravels(travel);
			
			//2016-10-29返回入库时间
			historyack.addTimestamps(rs.getColumnLatestCell(TravelLastInfo.family, TravelLastInfo.qualifier_callLetter).getTimestamp());
			
			return ResultCode.OK;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultCode.Hbase_Error;
	}
}
