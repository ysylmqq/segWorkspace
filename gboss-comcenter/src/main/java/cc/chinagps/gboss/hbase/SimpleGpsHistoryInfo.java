package cc.chinagps.gboss.hbase;

import java.util.Iterator;

import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;

import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.GetHistoryInfo_ACK;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.GetHistorySimpleGpsInfo_ACK;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.OBDInfo;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsBaseInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsSimpleInfo;

public class SimpleGpsHistoryInfo extends HbaseHistoryInfo {

	private int priorlat; 	//上一条纬度
	private int priorlng; 	//上一条经度
	
	public SimpleGpsHistoryInfo(HConnection connection) {
		super(connection);
		super.tablename = "t_history";
		priorlat = Integer.MIN_VALUE;
		priorlng = Integer.MIN_VALUE;
	}

	@Override
	protected int getHistoryInfo1(ReqHistoryInfo reqinfo, Result rs, GetHistoryInfo_ACK.Builder historyack) {
		return 0;
	}

	protected int getHistoryInfo1(ReqHistoryInfo reqinfo, Result rs, GetHistorySimpleGpsInfo_ACK.Builder historyack) {
		try {
			//判断呼号是否正常
			String callletter1 = new String(rs.getValue(GpsLastInfo.family, GpsLastInfo.qualifier_callLetter));
			if (!callletter1.equals(reqinfo.callletter)) {
				return ResultCode.Hbase_Error;
			}
			//gps baseinfo
			byte[] baseData = rs.getValue(GpsLastInfo.family, GpsLastInfo.qualifier_base);
			GpsBaseInfo baseinfo = GpsBaseInfo.parseFrom(baseData);
			int lat = baseinfo.getLat();
			int lng = baseinfo.getLng();
			if (reqinfo.norepeat && lat == priorlat && lng == priorlng) {
				return ResultCode.Repeat_Error;
			}
			priorlat = lat;
			priorlng = lng;
			GpsSimpleInfo.Builder simplegpsinfo = GpsSimpleInfo.newBuilder();
			simplegpsinfo.setGpsTime(baseinfo.getGpsTime());
			simplegpsinfo.setLoc(baseinfo.getLoc());
			simplegpsinfo.setLat(lat);
			simplegpsinfo.setLng(lng);
			simplegpsinfo.setSpeed(baseinfo.getSpeed());
			simplegpsinfo.setCourse(baseinfo.getCourse());
			simplegpsinfo.addAllStatus(baseinfo.getStatusList());
			if (baseinfo.hasTotalDistance())
				simplegpsinfo.setTotalDistance(baseinfo.getTotalDistance());
			if (baseinfo.hasOil())
				simplegpsinfo.setOil(baseinfo.getOil());
			if (baseinfo.hasOilPercent())
				simplegpsinfo.setRemainPercentOil(baseinfo.getOilPercent());
			if (baseinfo.hasObdInfo()) {
				OBDInfo obdinfo = baseinfo.getObdInfo();
				if (obdinfo.hasTotalDistance()) {
					simplegpsinfo.setTotalDistance(obdinfo.getTotalDistance());
				}
				if (obdinfo.hasRemainOil()) {
					simplegpsinfo.setOil(obdinfo.getRemainOil());
				}
				if (obdinfo.hasRemainPercentOil()) {
					simplegpsinfo.setRemainPercentOil(obdinfo.getRemainPercentOil());
				}
				if (obdinfo.hasRemainDistance()) {
					simplegpsinfo.setRemainDistance(obdinfo.getRemainDistance());
				}
			}
			historyack.addGpses(simplegpsinfo.build());
			//2016-10-29返回入库时间
			historyack.addTimestamps(rs.getColumnLatestCell(GpsLastInfo.family, GpsLastInfo.qualifier_callLetter).getTimestamp());
			return ResultCode.OK;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultCode.Hbase_Error;
	}

	@Override
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
			GetHistorySimpleGpsInfo_ACK.Builder historyack = GetHistorySimpleGpsInfo_ACK.newBuilder();
			historyack.setCallLetter(reqinfo.callletter);
			historyack.setRetcode(ResultCode.OK);
			historyack.setRetmsg("成功");
			historyack.setSn(reqinfo.sn);
			historyack.setLastPage(false);
			
			int pagenumber = 0;
			while(iter.hasNext()){
				Result rs = iter.next();
				int retcode = getHistoryInfo1(reqinfo, rs, historyack);
				if (retcode == ResultCode.Repeat_Error) {
					continue;
				}
				if (retcode != ResultCode.OK) {
					reqinfo.stop();
					return retcode;
				}
				//重复的点可能没读
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
					basemsg.setId(MessageType.GetHistorySimpleGpsInfo_ACK);
					basemsg.setContent(historyack.build().toByteString());
					reqinfo.clientinfo.appendSendMessage(basemsg.build());
					//清空每页读数
					pagenumber = 0;
					historyack.clearGpses();
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
			basemsg.setId(MessageType.GetHistorySimpleGpsInfo_ACK);
			basemsg.setContent(historyack.build().toByteString());
			reqinfo.clientinfo.appendSendMessage(basemsg.build());
			return ResultCode.OK;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		reqinfo.stop();
		return ResultCode.Hbase_Error;
	}
	
	@Override
	public int getNextHistoryInfo(ReqHistoryInfo reqinfo) {
		try {
			Iterator<Result> iter = reqinfo.rsscan.iterator();
			GetHistorySimpleGpsInfo_ACK.Builder historyack = GetHistorySimpleGpsInfo_ACK.newBuilder();
			historyack.setRetcode(ResultCode.OK);
			historyack.setCallLetter(reqinfo.callletter);
			historyack.setRetmsg("成功");
			historyack.setSn(reqinfo.sn);
			historyack.setLastPage(false);
			int pagenumber = 0;
			//System.out.println("nextpage begin 1");
			while(iter.hasNext()){
				Result rs = iter.next();
				int retcode = getHistoryInfo1(reqinfo, rs, historyack);
				if (retcode == ResultCode.Repeat_Error) {
					continue;
				}
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
					basemsg.setId(MessageType.GetHistorySimpleGpsInfo_ACK);
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
				}
			}
			//读完最后一页
			reqinfo.stop();
			historyack.setLastPage(true);
			//插入到发送队列
			ComCenterMessage.ComCenterBaseMessage.Builder basemsg = ComCenterMessage.ComCenterBaseMessage.newBuilder();
			basemsg.setId(MessageType.GetHistorySimpleGpsInfo_ACK);
			basemsg.setContent(historyack.build().toByteString());
			reqinfo.clientinfo.appendSendMessage(basemsg.build());
			return ResultCode.OK;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		reqinfo.stop();
		return ResultCode.Hbase_Error;
	}

}
