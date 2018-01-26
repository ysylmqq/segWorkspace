/*
********************************************************************************************
Discription:  读故障灯历史信息 
			  
Written By:   ZXZ
Date:         2014-06-04
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.hbase;

import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.Result;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.GetHistoryInfo_ACK;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsBaseInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsInfo;

public class FaultLightHistoryInfo extends HbaseHistoryInfo {
	public FaultLightHistoryInfo(HConnection connection) {
		super(connection);
		super.tablename = "t_fault_light";
	}

	@Override
	protected int getHistoryInfo1(ReqHistoryInfo reqinfo, Result rs, GetHistoryInfo_ACK.Builder historyack) {
		try {
			//判断呼号是否正常
			String callletter1 = new String(rs.getValue(FaultLightLastInfo.family, FaultLightLastInfo.qualifier_callLetter));
			if (!callletter1.equals(reqinfo.callletter)) {
				return ResultCode.Hbase_Error;
			}
			//gps baseinfo
			byte[] baseData = rs.getValue(FaultLightLastInfo.family, FaultLightLastInfo.qualifier_base);
			
			//源码
			//byte[] content = rs.getValue(FaultLightLastInfo.family, FaultLightLastInfo.qualifier_content);
		
			GpsInfo.Builder gpsinfo = historyack.addGpsesBuilder();
			gpsinfo.setCallLetter(reqinfo.callletter);
			gpsinfo.setBaseInfo(GpsBaseInfo.parseFrom(baseData));
			//GPS历史回放，不带源码
			//if (content != null && content.length > 0) {
			//	gpsinfo.setContent(ByteString.copyFrom(content));
			//}
			//2016-10-29返回入库时间
			historyack.addTimestamps(rs.getColumnLatestCell(FaultLightLastInfo.family, FaultLightLastInfo.qualifier_callLetter).getTimestamp());
			return ResultCode.OK;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultCode.Hbase_Error;
	}
}
