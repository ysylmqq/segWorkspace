/*
********************************************************************************************
Discription:  读HBASE表 最后信息-基类 
			  
Written By:   ZXZ
Date:         2014-05-30
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.seat.hbase;

import java.util.Iterator;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.GetLastInfo_ACK;
import cc.chinagps.lib.util.HBaseKeyUtil;

public abstract class HbaseLastInfo {
	protected HConnection connection;		//HBASE连接
	protected String tablename = "";

	public HbaseLastInfo(HConnection connection) {
		this.connection = connection;
	}
	
	public int GetLastInfo(ReqLastInfo reqinfo) {
		ResultScanner rsscan = null;
		try {
			rsscan = BeginScan(reqinfo.callletter);
			if (rsscan == null) {
				ResponseErrorMsg(reqinfo, ResultCode.Hbase_Error, "遍历失败");
				return ResultCode.OK;	//表示已经应答，可以删除队列
			}
			Iterator<Result> iter = rsscan.iterator();
			if (!iter.hasNext()) {
				ResponseErrorMsg(reqinfo, ResultCode.LastPosition_Error, "没有最后信息");
				return ResultCode.OK;	//表示已经应答，可以删除队列
			}
			Result rs = iter.next();
			GetLastInfo_ACK.Builder lastack = GetLastInfo_ACK.newBuilder();
			lastack.setRetcode(ResultCode.OK);
			lastack.setRetmsg("成功");
			lastack.setSn(reqinfo.sn);
			int retcode = GetLastInfo(reqinfo, rs, lastack);
			rsscan.close();
			if (retcode == ResultCode.OK) {
				//插入到发送队列
				ComCenterMessage.ComCenterBaseMessage.Builder basemsg = ComCenterMessage.ComCenterBaseMessage.newBuilder();
				basemsg.setId(MessageType.GetLastInfo_ACK);
				basemsg.setContent(lastack.build().toByteString());
				reqinfo.clientinfo.AppendSendMessage(basemsg.build());
				return ResultCode.OK;
			}
			return retcode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (rsscan != null) rsscan.close();
		return ResultCode.Hbase_Error;
	}
	
	protected abstract int GetLastInfo(ReqLastInfo reqinfo, Result rs, GetLastInfo_ACK.Builder lastack);
	
	//开始信息
	protected ResultScanner BeginScan(String callletter) {
		try {
			HTableInterface table = connection.getTable(tablename);
			//按时间倒序
			byte[] startKey = HBaseKeyUtil.getKey(callletter, System.currentTimeMillis() + 24*3600000l); //二天后
			byte[] endKey = HBaseKeyUtil.getKey(callletter, 24l*3600000l*40*365); //2010年
			Scan scan = new Scan(startKey, endKey);
			ResultScanner rsscan = table.getScanner(scan);
			return rsscan;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void ResponseErrorMsg(ReqLastInfo reqinfo, int retcode, String msg) {
		GetLastInfo_ACK.Builder lastack = GetLastInfo_ACK.newBuilder();
		lastack.setRetcode(retcode);
		lastack.setRetmsg(msg);
		lastack.setSn(reqinfo.sn);

		//插入到发送队列
		ComCenterMessage.ComCenterBaseMessage.Builder basemsg = ComCenterMessage.ComCenterBaseMessage.newBuilder();
		basemsg.setId(MessageType.GetLastInfo_ACK);
		basemsg.setContent(lastack.build().toByteString());
		reqinfo.clientinfo.AppendSendMessage(basemsg.build());
	}
}


