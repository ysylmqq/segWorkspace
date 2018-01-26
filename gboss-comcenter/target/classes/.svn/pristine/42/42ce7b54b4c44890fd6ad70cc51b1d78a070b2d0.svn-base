/*
********************************************************************************************
Discription:  读出租车的运营数据的历史数据 
			  
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
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.GetHistoryInfo_ACK;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.OperateData;

public class OperateDataHistoryInfo extends HbaseHistoryInfo {

	public OperateDataHistoryInfo(HConnection connection) {
		super(connection);
		super.tablename = "t_operate";
	}

	@Override
	protected int getHistoryInfo1(ReqHistoryInfo reqinfo, Result rs, GetHistoryInfo_ACK.Builder historyack) {
		try {
			//判断呼号是否正常
			String callletter1 = new String(rs.getValue(OperateDataLastInfo.family, OperateDataLastInfo.qualifier_callLetter));
			if (!callletter1.equals(reqinfo.callletter)) {
				reqinfo.stop();
				responseErrorMsg(reqinfo, ResultCode.Hbase_Error, "取历史运营数据呼号不一致");
				return ResultCode.Hbase_Error;
			}
			//Operate data
			byte[] data = rs.getValue(OperateDataLastInfo.family, OperateDataLastInfo.qualifier_data);
			historyack.addOperates(OperateData.parseFrom(data));
			//2016-10-29返回入库时间
			historyack.addTimestamps(rs.getColumnLatestCell(OperateDataLastInfo.family, OperateDataLastInfo.qualifier_callLetter).getTimestamp());
			
			return ResultCode.OK;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultCode.Hbase_Error;
	}
	
	/*
	@Override
	public int GetNextHistoryInfo(ReqHistoryInfo reqinfo) {
		try {
			Iterator<Result> iter = reqinfo.rsscan.iterator();
			GetHistoryInfo_ACK.Builder historyack = GetHistoryInfo_ACK.newBuilder();
			historyack.setRetcode(ResultCode.OK);
			historyack.setRetmsg("成功");
			historyack.setSn(reqinfo.sn);
			historyack.setLastPage(false);
			int pagenumber = 0;
			while(iter.hasNext()){
				Result rs = iter.next();
				//判断呼号是否正常
				String callletter1 = new String(rs.getValue(OperateDataLastInfo.family, OperateDataLastInfo.qualifier_callLetter));
				if (!callletter1.equals(reqinfo.callletter)) {
					reqinfo.stop();
					ResponseErrorMsg(reqinfo, ResultCode.Hbase_Error, "取历史运营数据呼号不一致");
					return ResultCode.OK;	//表示已经应答，可以删除队列
				}
				byte[] data = rs.getValue(OperateDataLastInfo.family, OperateDataLastInfo.qualifier_data);
				historyack.addOperates(OperateData.parseFrom(data));
				pagenumber ++;
				reqinfo.readnumber ++;
				//如果数量到达每页数量
				if (pagenumber >= reqinfo.pagenumber) {
					//废黜最后读时间
					//结束读一页，等待客户端下一页请求
					if (reqinfo.readnumber >= reqinfo.totalnumber || !iter.hasNext()) {
						reqinfo.stop();
						historyack.setLastPage(true);
					}
					//插入到发送队列
					ComCenterMessage.ComCenterBaseMessage.Builder basemsg = ComCenterMessage.ComCenterBaseMessage.newBuilder();
					basemsg.setId(MessageType.GetHistoryInfo_ACK);
					basemsg.setContent(historyack.build().toByteString());
					reqinfo.clientinfo.AppendSendMessage(basemsg.build());
					historyack.clearOperates();
					pagenumber = 0;
					reqinfo.reqtime = System.currentTimeMillis();
					if ((--reqinfo.nexttimes) <= 0) {
						return ResultCode.OK;
					}
					//循环第二个下一页(有时用户连续二次申请下一页)
				}
			}
			//读完最后一页
			reqinfo.stop();
			historyack.setLastPage(true);
			//插入到发送队列
			ComCenterMessage.ComCenterBaseMessage.Builder basemsg = ComCenterMessage.ComCenterBaseMessage.newBuilder();
			basemsg.setId(MessageType.GetHistoryInfo_ACK);
			basemsg.setContent(historyack.build().toByteString());
			reqinfo.clientinfo.AppendSendMessage(basemsg.build());
			return ResultCode.OK;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reqinfo.stop();
		return ResultCode.Hbase_Error;
	}
	*/
}
