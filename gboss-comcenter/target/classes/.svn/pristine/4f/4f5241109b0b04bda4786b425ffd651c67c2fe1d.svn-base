/*
********************************************************************************************
Discription:  读GPS最后信息 
			  
Written By:   ZXZ
Date:         2014-05-30
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.hbase;

import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.Result;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.GetLastInfo_ACK.Builder;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsBaseInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsReferPosition;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.OBDInfo;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsInfo;

public class GpsLastInfo extends HbaseLastInfo {

	public static byte[] family = "gps".getBytes();
	public static byte[] qualifier_callLetter = "callletter".getBytes();
	public static byte[] qualifier_base = "baseinfo".getBytes();
	public static byte[] qualifier_referposition = "referposition".getBytes();
	public static byte[] qualifier_content = "content".getBytes();

	public GpsLastInfo(HConnection connection) {
		super(connection);
		super.tablename = "t_history";
	}

	@Override
	protected int GetLastInfo1(ReqLastInfo reqinfo, Result rs, Builder lastack) {
		try {
			//判断呼号是否正常
			String callletter1 = new String(rs.getValue(family, qualifier_callLetter));
			if (!callletter1.equals(reqinfo.callletter)) {
				ResponseErrorMsg(reqinfo, ResultCode.Hbase_Error, "取最后位置呼号不一致");
				return ResultCode.OK;	//表示已经应答，可以删除队列
			}
			//gps baseinfo
			byte[] baseData = rs.getValue(family, qualifier_base);
			//参考位置
			byte[] referposition = rs.getValue(family, qualifier_referposition);
			
			//源码
			//byte[] content = rs.getValue(family, qualifier_content);
			GpsInfo.Builder gpsinfo = lastack.addGpsesBuilder();
			gpsinfo.setCallLetter(reqinfo.callletter);
			GpsBaseInfo baseinfo = GpsBaseInfo.parseFrom(baseData);
			if (!baseinfo.hasObdInfo()) {	//如果最后位置没有obd数据，则从obd数据表
				try {
					OBDTable obdtable = new OBDTable(this.connection);
					OBDInfo obdinfo = obdtable.GetLastOBDInfo(reqinfo.callletter);
					if (obdinfo != null) {
						GpsBaseInfo.Builder builder = baseinfo.toBuilder();
						builder.setObdInfo(obdinfo);
						baseinfo = builder.build();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			gpsinfo.setBaseInfo(baseinfo);
			if (referposition != null && referposition.length > 0) {
				GpsReferPosition refer = GpsReferPosition.parseFrom(referposition);
				gpsinfo.setReferPosition(refer);
			}
			//最后位置接口中，不带源码
			//if (content != null && content.length > 0) {
				//gpsinfo.setContent(ByteString.copyFrom(content));
			//}
			
			//2016-10-29返回入库时间
			lastack.addTimestamps(rs.getColumnLatestCell(family, qualifier_callLetter).getTimestamp());
			
			return ResultCode.OK;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultCode.Hbase_Error;
	}
}
