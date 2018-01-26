/*
********************************************************************************************
Discription:  读HBASE表历史信息-基类 
			  
Written By:   ZXZ
Date:         2014-06-04
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.hbase;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.GetHistoryInfo_ACK;
import cc.chinagps.lib.util.HBaseKeyUtil;

public abstract class HbaseHistoryInfo {
	protected HConnection connection;		//HBASE连接
	protected String tablename = "";

	public HbaseHistoryInfo(HConnection connection) {
		this.connection = connection;
	}
	
	protected abstract int getHistoryInfo1(ReqHistoryInfo reqinfo, Result rs, GetHistoryInfo_ACK.Builder historyack);
	public int getHistoryInfo(ReqHistoryInfo reqinfo) {
		ResultScanner rsscan = null;
		try {
			rsscan = beginScan(reqinfo);
			if (rsscan == null) {
				reqinfo.stop();
				responseErrorMsg(reqinfo, ResultCode.Hbase_Error, "遍历失败");
				return ResultCode.OK;	//表示已经应答，可以删除队列
			}
			Iterator<Result> iter = rsscan.iterator();
			if (!iter.hasNext()) {
				rsscan.close();
				reqinfo.stop();
				responseErrorMsg(reqinfo, ResultCode.HistoryPosition_Error, "没有历史信息");
				return ResultCode.OK;	//表示已经应答，可以删除队列
			}
			GetHistoryInfo_ACK.Builder historyack = GetHistoryInfo_ACK.newBuilder();
			historyack.setRetcode(ResultCode.OK);
			historyack.setRetmsg("成功");
			historyack.setSn(reqinfo.sn);
			historyack.setLastPage(false);
			
			int pagenumber = 0;
			while(iter.hasNext()){
				Result rs = iter.next();
				int retcode = getHistoryInfo1(reqinfo, rs, historyack);
				if (retcode != ResultCode.OK) {
					reqinfo.stop();
					return retcode;
				}
				pagenumber ++;
				reqinfo.readnumber ++;
				//如果数量到达每页数量
				if (pagenumber >= reqinfo.pagenumber) {
					//废黜最后读时间
					reqinfo.reqtime = System.currentTimeMillis();
					if (reqinfo.readnumber >= reqinfo.totalnumber || !iter.hasNext()) {
						reqinfo.stop();
						historyack.setLastPage(true);
					}
					
					//插入到发送队列
					ComCenterMessage.ComCenterBaseMessage.Builder basemsg = ComCenterMessage.ComCenterBaseMessage.newBuilder();
					basemsg.setId(MessageType.GetHistoryInfo_ACK);
					basemsg.setContent(historyack.build().toByteString());
					reqinfo.clientinfo.appendSendMessage(basemsg.build());
					//清空每页读数
					pagenumber = 0;
					historyack.clearGpses();
					historyack.clearAlarms();
					historyack.clearFaults();
					historyack.clearOperates();
					historyack.clearSms();
					historyack.clearTravels();
					//已经结束 或者 不读下一页
					if (reqinfo.readstop || !reqinfo.autonext) {
						return ResultCode.OK;
					}
				}
				//如果已经读完总数量, 结束读取(可能totalnumber不是pagenumber的整数倍)
				//解码时，已经变为整数倍，
				if (reqinfo.readnumber >= reqinfo.totalnumber) {
					break;
				}
			}
			reqinfo.stop();
			historyack.setLastPage(true);
			//插入到发送队列
			ComCenterMessage.ComCenterBaseMessage.Builder basemsg = ComCenterMessage.ComCenterBaseMessage.newBuilder();
			basemsg.setId(MessageType.GetHistoryInfo_ACK);
			basemsg.setContent(historyack.build().toByteString());
			reqinfo.clientinfo.appendSendMessage(basemsg.build());
			return ResultCode.OK;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		reqinfo.stop();
		return ResultCode.Hbase_Error;
	}
	
	public int getNextHistoryInfo(ReqHistoryInfo reqinfo) {
		try {
			Iterator<Result> iter = reqinfo.rsscan.iterator();
			GetHistoryInfo_ACK.Builder historyack = GetHistoryInfo_ACK.newBuilder();
			historyack.setRetcode(ResultCode.OK);
			historyack.setRetmsg("成功");
			historyack.setSn(reqinfo.sn);
			historyack.setLastPage(false);
			int pagenumber = 0;
			//System.out.println("nextpage begin 1");
			while(iter.hasNext()){
				Result rs = iter.next();
				int retcode = getHistoryInfo1(reqinfo, rs, historyack);
				if (retcode != ResultCode.OK) {
					reqinfo.stop();
					return retcode;
				}
				pagenumber ++;
				reqinfo.readnumber ++;
				//如果数量到达每页数量
				if (pagenumber >= reqinfo.pagenumber) {
					if (reqinfo.readnumber >= reqinfo.totalnumber || !iter.hasNext()) {
						reqinfo.stop();
						historyack.setLastPage(true);
					}
					//插入到发送队列
					ComCenterMessage.ComCenterBaseMessage.Builder basemsg = ComCenterMessage.ComCenterBaseMessage.newBuilder();
					basemsg.setId(MessageType.GetHistoryInfo_ACK);
					basemsg.setContent(historyack.build().toByteString());
					reqinfo.clientinfo.appendSendMessage(basemsg.build());
					reqinfo.reqtime = System.currentTimeMillis();
					if ((--reqinfo.nexttimes) <= 0) {
						return ResultCode.OK;
					}
					//取第二页
					//清空每页读数
					pagenumber = 0;
					historyack.clearGpses();
					historyack.clearAlarms();
					historyack.clearFaults();
					historyack.clearOperates();
					historyack.clearSms();
					historyack.clearTravels();
				}
			}
			//读完最后一页
			reqinfo.stop();
			historyack.setLastPage(true);
			//插入到发送队列
			ComCenterMessage.ComCenterBaseMessage.Builder basemsg = ComCenterMessage.ComCenterBaseMessage.newBuilder();
			basemsg.setId(MessageType.GetHistoryInfo_ACK);
			basemsg.setContent(historyack.build().toByteString());
			reqinfo.clientinfo.appendSendMessage(basemsg.build());
			return ResultCode.OK;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		reqinfo.stop();
		return ResultCode.Hbase_Error;
	}
	
	//开始信息
	protected ResultScanner beginScan(ReqHistoryInfo reqinfo) {
		try {
			HTableInterface table = connection.getTable(tablename);
			if (reqinfo.reversed) {
				//按时间倒序
				byte[] startKey = HBaseKeyUtil.getKey(reqinfo.callletter, reqinfo.starttime); //2010年
				byte[] endKey = HBaseKeyUtil.getKey(reqinfo.callletter, reqinfo.endtime); //二天后
				Scan scan = new Scan(startKey, endKey);
				scan.setReversed(false);	//存储时按时间逆序，读时按时间顺序
				scan.setBatch(reqinfo.totalnumber);
				reqinfo.rsscan = table.getScanner(scan);
			} else {
				//按时间倒序
				byte[] startKey = HBaseKeyUtil.getKey(reqinfo.callletter, reqinfo.endtime); //二天后
				byte[] endKey = HBaseKeyUtil.getKey(reqinfo.callletter, reqinfo.starttime); //2010年
				Scan scan = new Scan(startKey, endKey);
				scan.setReversed(true);	//存储时按时间逆序，读时按时间顺序
				scan.setBatch(reqinfo.totalnumber);
				reqinfo.rsscan = table.getScanner(scan);
			}
			return reqinfo.rsscan;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void responseErrorMsg(ReqHistoryInfo reqinfo, int retcode, String msg) {
		GetHistoryInfo_ACK.Builder historyack = GetHistoryInfo_ACK.newBuilder();
		historyack.setRetcode(retcode);
		historyack.setRetmsg(msg);
		historyack.setSn(reqinfo.sn);
		historyack.setLastPage(true);

		//插入到发送队列
		ComCenterMessage.ComCenterBaseMessage.Builder basemsg = ComCenterMessage.ComCenterBaseMessage.newBuilder();
		basemsg.setId(MessageType.GetHistoryInfo_ACK);
		basemsg.setContent(historyack.build().toByteString());
		reqinfo.clientinfo.appendSendMessage(basemsg.build());
	}

}
