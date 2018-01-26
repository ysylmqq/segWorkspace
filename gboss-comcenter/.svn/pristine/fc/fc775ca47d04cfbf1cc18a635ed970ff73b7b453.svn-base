/*
********************************************************************************************
Discription:  读出租车运营数据的最后信息 
			  
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
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.GetLastInfo_ACK;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.OperateData;

public class OperateDataLastInfo extends HbaseLastInfo {

	public static byte[] family = "operate".getBytes();
	public static byte[] qualifier_callLetter = "callletter".getBytes();
	public static byte[] qualifier_data = "data".getBytes();

	public OperateDataLastInfo(HConnection connection) {
		super(connection);
		super.tablename = "t_operate";
	}

	@Override
	protected int GetLastInfo1(ReqLastInfo reqinfo, Result rs, GetLastInfo_ACK.Builder lastack) {
		try {
			//判断呼号是否正常
			String callletter1 = new String(rs.getValue(family, qualifier_callLetter));
			if (!callletter1.equals(reqinfo.callletter)) {
				ResponseErrorMsg(reqinfo, ResultCode.Hbase_Error, "取最后运营数据呼号不一致");
				return ResultCode.OK;	//表示已经应答，可以删除队列
			}
				
			//operate data
			byte[] data = rs.getValue(family, qualifier_data);
			OperateData operatedata = OperateData.parseFrom(data);
			lastack.addOperates(operatedata);
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
