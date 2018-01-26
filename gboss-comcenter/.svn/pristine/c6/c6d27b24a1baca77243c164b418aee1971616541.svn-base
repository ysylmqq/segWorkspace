/*
********************************************************************************************
Discription:  读行程最后信息，要补充开始点的GPS位置信息 
			  
Written By:   ZXZ
Date:         2014-06-03
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.hbase;

import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.Result;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.TravelInfo;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.GetLastInfo_ACK;

public class TravelLastInfo extends HbaseLastInfo {

	public static byte[] family = "travel".getBytes();
	public static byte[] qualifier_callLetter = "callletter".getBytes();
	public static byte[] qualifier_data = "data".getBytes();

	public TravelLastInfo(HConnection connection) {
		super(connection);
		super.tablename = "t_travel";
	}

	@Override
	protected int GetLastInfo1(ReqLastInfo reqinfo, Result rs, GetLastInfo_ACK.Builder lastack) {
		try {
			//判断呼号是否正常
			try {
				String callletter1 = new String(rs.getValue(family, qualifier_callLetter));
				if (!callletter1.equals(reqinfo.callletter)) {
					ResponseErrorMsg(reqinfo, ResultCode.Hbase_Error, "取最后行程呼号不一致");
					return ResultCode.OK;	//表示已经应答，可以删除队列
				}
			} catch (Exception e) {
				ResponseErrorMsg(reqinfo, ResultCode.Hbase_Error, "没有呼号");
				return ResultCode.OK;	//表示已经应答，可以删除队列
			}
				
			//travel data
			byte[] data = rs.getValue(family, qualifier_data);
			//byte[] data = Util.hexToBytes("0A0B313336303330323530313310D8B189EAF52818C0E5A4EAF52820B81728CE043000380040004800500058D402607C68AA1370810178048001870188010098019C09A0019021A80178B2013A08D8B189EAF528100118D7D8DE0A20FD9AAB362800305A381938203821383C72191094051800200028B8E4B91C305338E209408018483050C402C2012008C0E5A4EAF528100118E9E8DE0A20E2D6AA36280030DE02381938203817383C");
			TravelInfo travel = TravelInfo.parseFrom(data);
			//如果行程信息没有开始GPS，则从Hbase取
			if (!travel.hasStartGps()) {
				try {
					GpsTable gpstable = new GpsTable(connection);
					//读开始点gps
					GpsInfo startgps = gpstable.GetFirstGpsInfo(reqinfo.callletter, travel.getStartTime(), travel.getEndTime());
					if (startgps != null) {
						TravelInfo.Builder builder = travel.toBuilder();
						if (startgps.hasBaseInfo())
							builder.setStartGps(startgps.getBaseInfo());
						if (startgps.hasReferPosition())
							builder.setStartReferPos(startgps.getReferPosition());
						travel = builder.build();
						//重新写回到HBASE
						TravelTable traveltable = new TravelTable(connection);
						traveltable.WriteTravel(travel);
						traveltable = null;
					}
					gpstable = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			lastack.addTravels(travel);

			//2016-10-29返回入库时间
			lastack.addTimestamps(rs.getColumnLatestCell(family, qualifier_callLetter).getTimestamp());
			
			return ResultCode.OK;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultCode.Hbase_Error;
	}
}
