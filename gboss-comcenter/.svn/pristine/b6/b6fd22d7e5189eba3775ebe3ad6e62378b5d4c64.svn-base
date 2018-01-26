/*
********************************************************************************************
Discription:  读终端终端登退录最后信息 
			  
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

import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverUnitLoginOut;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.GetLastInfo_ACK;

public class UnitLoginoutLastInfo extends HbaseLastInfo {

	public UnitLoginoutLastInfo(HConnection connection) {
		super(connection);
		super.tablename = "t_unitloginout";
	}

	@Override
	protected int GetLastInfo1(ReqLastInfo reqinfo, Result rs, GetLastInfo_ACK.Builder lastack) {
		try {
			//判断呼号是否正常
			String callletter1 = new String(rs.getValue(UnitLoginoutTable.family, UnitLoginoutTable.qualifier_callLetter));
			if (!callletter1.equals(reqinfo.callletter)) {
				ResponseErrorMsg(reqinfo, ResultCode.Hbase_Error, "取终端登退录时呼号不一致");
				return ResultCode.OK;	//表示已经应答，可以删除队列
			}
			//2016-10-29返回入库时间
			lastack.addTimestamps(rs.getColumnLatestCell(UnitLoginoutTable.family, UnitLoginoutTable.qualifier_callLetter).getTimestamp());
			
			//终端登退录 data
			byte[] data = rs.getValue(UnitLoginoutTable.family, UnitLoginoutTable.qualifier_data);
			DeliverUnitLoginOut deliverunitloginout = DeliverUnitLoginOut.parseFrom(data);
			lastack.addUnitloginout(deliverunitloginout);
			return ResultCode.OK;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultCode.Hbase_Error;
	}
}
