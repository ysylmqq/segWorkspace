/*
********************************************************************************************
Discription:  读终端OBD历史信息 
			  
Written By:   ZXZ
Date:         2014-11-06
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
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.OBDInfo;

public class OBDHistoryInfo extends HbaseHistoryInfo {

	public OBDHistoryInfo(HConnection connection) {
		super(connection);
		super.tablename = "t_obd";
	}

	@Override
	protected int getHistoryInfo1(ReqHistoryInfo reqinfo, Result rs, GetHistoryInfo_ACK.Builder historyack) {
		try {
			//判断呼号是否正常
			String callletter1 = new String(rs.getValue(OBDTable.family, OBDTable.qualifier_callLetter));
			if (!callletter1.equals(reqinfo.callletter)) {
				return ResultCode.Hbase_Error;
			}
			//Fault data
			byte[] data = rs.getValue(OBDTable.family, OBDTable.qualifier_data);
			historyack.addObds(OBDInfo.parseFrom(data));
			//2016-10-29返回入库时间
			historyack.addTimestamps(rs.getColumnLatestCell(OBDTable.family, OBDTable.qualifier_callLetter).getTimestamp());
			
			return ResultCode.OK;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultCode.Hbase_Error;
	}
}
