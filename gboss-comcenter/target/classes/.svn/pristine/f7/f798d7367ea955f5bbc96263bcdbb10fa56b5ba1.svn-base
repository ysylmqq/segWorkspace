/*
********************************************************************************************
Discription:  读终端警情最后信息 
			  
Written By:   ZXZ
Date:         2014-06-13
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
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.GetLastInfo_ACK;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsBaseInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.AlarmInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsReferPosition;

import com.google.protobuf.ByteString;

public class AlarmLastInfo extends HbaseLastInfo {

	public static byte[] family = "alarm".getBytes();
	public static byte[] qualifier_callLetter = "callletter".getBytes();
	public static byte[] qualifier_base = "baseinfo".getBytes();
	public static byte[] qualifier_referposition = "referposition".getBytes();
	public static byte[] qualifier_content = "content".getBytes();

	public AlarmLastInfo(HConnection connection) {
		super(connection);
		super.tablename = "t_alarm";
	}

	@Override
	protected int GetLastInfo1(ReqLastInfo reqinfo, Result rs, GetLastInfo_ACK.Builder lastack) {
		try {
			//判断呼号是否正常
			String callletter1 = new String(rs.getValue(family, qualifier_callLetter));
			if (!callletter1.equals(reqinfo.callletter)) {
				ResponseErrorMsg(reqinfo, ResultCode.Hbase_Error, "取终端警情呼号不一致");
				return ResultCode.OK;	//表示已经应答，可以删除队列
			}
			//gps baseinfo
			byte[] baseData = rs.getValue(family, qualifier_base);
			//参考位置
			byte[] referposition = rs.getValue(family, qualifier_referposition);
			//源码
			byte[] content = rs.getValue(family, qualifier_content);
			AlarmInfo.Builder alarminfo = lastack.addAlarmsBuilder();
			alarminfo.setCallLetter(reqinfo.callletter);
			alarminfo.setBaseInfo(GpsBaseInfo.parseFrom(baseData));
			if (referposition != null && referposition.length > 0) {
				alarminfo.setReferPosition(GpsReferPosition.parseFrom(referposition));
			}
			if (content != null && content.length > 0) {
				alarminfo.setContent(ByteString.copyFrom(content));
			}
			
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
