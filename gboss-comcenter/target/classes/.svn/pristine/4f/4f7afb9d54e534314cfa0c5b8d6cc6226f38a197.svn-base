/*
********************************************************************************************
Discription:  读终端警情历史信息 
			  
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
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.AlarmInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsReferPosition;

import com.google.protobuf.ByteString;

public class AlarmHistoryInfo extends HbaseHistoryInfo {

	public AlarmHistoryInfo(HConnection connection) {
		super(connection);
		super.tablename = "t_alarm";
	}

	@Override
	protected int getHistoryInfo1(ReqHistoryInfo reqinfo, Result rs, GetHistoryInfo_ACK.Builder historyack) {
		try {
			//判断呼号是否正常
			String callletter1 = new String(rs.getValue(AlarmLastInfo.family, AlarmLastInfo.qualifier_callLetter));
			if (!callletter1.equals(reqinfo.callletter)) {
				return ResultCode.Hbase_Error;
			}
			//gps baseinfo
			byte[] baseData = rs.getValue(AlarmLastInfo.family, AlarmLastInfo.qualifier_base);
			//参考位置
			byte[] referposition = rs.getValue(AlarmLastInfo.family, AlarmLastInfo.qualifier_referposition);
			//源码
			byte[] content = rs.getValue(AlarmLastInfo.family, AlarmLastInfo.qualifier_content);
		
			AlarmInfo.Builder alarminfo = historyack.addAlarmsBuilder();
			alarminfo.setCallLetter(reqinfo.callletter);
			alarminfo.setBaseInfo(GpsBaseInfo.parseFrom(baseData));
			if (referposition != null && referposition.length > 0) {
				alarminfo.setReferPosition(GpsReferPosition.parseFrom(referposition));
			}
			if (content != null && content.length > 0) {
				alarminfo.setContent(ByteString.copyFrom(content));
			}
			//2016-10-29返回入库时间
			historyack.addTimestamps(rs.getColumnLatestCell(AlarmLastInfo.family, AlarmLastInfo.qualifier_callLetter).getTimestamp());
			
			return ResultCode.OK;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultCode.Hbase_Error;
	}
}
