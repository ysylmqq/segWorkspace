/*
********************************************************************************************
Discription:  读短信的历史数据 
			  
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
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.ShortMessage;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.GetHistoryInfo_ACK;

public class SmsHistoryInfo extends HbaseHistoryInfo {

	public SmsHistoryInfo(HConnection connection) {
		super(connection);
		super.tablename = "t_sm";
	}

	@Override
	protected int getHistoryInfo1(ReqHistoryInfo reqinfo, Result rs, GetHistoryInfo_ACK.Builder historyack) {
		try {
			//判断呼号是否正常
			String callletter1 = new String(rs.getValue(SmsLastInfo.family, SmsLastInfo.qualifier_callLetter));
			if (!callletter1.equals(reqinfo.callletter)) {
				return ResultCode.Hbase_Error;
			}
			//sm data
			byte[] data = rs.getValue(SmsLastInfo.family, SmsLastInfo.qualifier_data);
			historyack.addSms(ShortMessage.parseFrom(data));

			//2016-10-29返回入库时间
			historyack.addTimestamps(rs.getColumnLatestCell(SmsLastInfo.family, SmsLastInfo.qualifier_callLetter).getTimestamp());
			
			return ResultCode.OK;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultCode.Hbase_Error;
	}
}
