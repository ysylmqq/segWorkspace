/*
********************************************************************************************
Discription:  从HBASE读历史信息线程
			  
Written By:   ZXZ
Date:         2014-06-03
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.hbase;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import cc.chinagps.gboss.comcenter.WebsocketClientInfo;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.lib.util.SystemConfig;

public class GetHistoryInfoThread extends Thread {

	private int scantimeout = 60000; //Integer.valueOf(SystemConfig.getHbaseProperties("scantimeoutmills")); 
	//获取连接
	private HConnection connection = null;
	private HbaseClientManager clientmanager;
	private ArrayList<ReqHistoryInfo> firstinfolist;		//第一次申请
	private ArrayList<ReqHistoryInfo> waitnextinfolist;		//等待下一页申请
	
	public GetHistoryInfoThread(String name) throws IOException {
		super(name);
		this.clientmanager = HbaseClientManager.hbaseclientmanager;
		this.connection = HConnectionManager.createConnection(clientmanager.config);
		this.firstinfolist = new ArrayList<ReqHistoryInfo>();
		this.waitnextinfolist = new ArrayList<ReqHistoryInfo>();
		scantimeout = Integer.valueOf(SystemConfig.getHbaseProperties("scantimeoutmills")); 
		if (scantimeout <= 0) {
			scantimeout = 300000;	//5分钟
		}
	}
	
	//添加历史信息请求
	public void appendHistoryInfo(WebsocketClientInfo clientinfo, String callletter, int infotype, 
			long starttime, long endtime, int pagenumber, int totalnumber, boolean autonextpage,
			String sn, boolean reversed, boolean norepeat) {
		synchronized(firstinfolist) {
			firstinfolist.add(new ReqHistoryInfo(clientinfo, callletter, infotype,
					starttime, endtime, pagenumber, totalnumber, autonextpage, sn, reversed, norepeat));
		};
	}

	//客户端请求下一页历史信息
	public int nextHistoryInfo(WebsocketClientInfo clientinfo, String callletter, int infotype, String sn) {
		synchronized(waitnextinfolist) {
			//for(ReqHistoryInfo reqinfo: waitnextinfolist) {
			for(int i=0; i<waitnextinfolist.size(); i++) {
				ReqHistoryInfo reqinfo = waitnextinfolist.get(i);
				if (reqinfo.clientinfo == clientinfo &&
					reqinfo.callletter.equals(callletter) &&
					reqinfo.infotype == infotype &&
					reqinfo.sn.equals(sn)) {
					reqinfo.nexttimes ++;
					return ResultCode.OK;
				}
			}
		};
		return ResultCode.Parameters_Error;
	}
	
	//客户端终止历史信息请求(30分钟自动终止)
	public int stopHistoryInfo(WebsocketClientInfo clientinfo, String callletter, int infotype, String sn) {
		synchronized(waitnextinfolist) {
			for(int i=0; i<waitnextinfolist.size(); i++) {
				ReqHistoryInfo reqinfo = waitnextinfolist.get(i);
				if (reqinfo.clientinfo == clientinfo &&
					reqinfo.callletter.equals(callletter) &&
					reqinfo.infotype == infotype &&
					reqinfo.sn.equals(sn)) {
					reqinfo.rsscan.close();
					waitnextinfolist.remove(i);
					return ResultCode.OK;
				}
			}
		};
		return ResultCode.Parameters_Error;
	}
	
	@Override
	public void run() {
		boolean hashistory = false;
		ArrayList<ReqHistoryInfo> tmplistinfo = new ArrayList<ReqHistoryInfo>();
		while(true){
			//先处理第一次请求
			hashistory = false;
			try{
				//用临时队列减少同步时间
				tmplistinfo.clear();
				synchronized(firstinfolist) {
					if (firstinfolist.size() > 0) {
						tmplistinfo.addAll(firstinfolist);
						firstinfolist.clear();
					}
				}
				int i = 0;
				while(i < tmplistinfo.size()) {
					ReqHistoryInfo reqinfo = tmplistinfo.get(i);
					int retcode = getHistoryInfo(reqinfo);	//第一次
					//如果读历史信息正常
					if (retcode == ResultCode.OK) {
						hashistory = true;
						//如果已经读完历史信息
						if (reqinfo.readstop) {	//取完历史记录
							reqinfo.stop();
							tmplistinfo.remove(i);
						} else {
							i++;
						}
					} else {	//读历史信息异常
						reqinfo.stop();
						tmplistinfo.remove(i);
						HbaseHistoryInfo.responseErrorMsg(reqinfo, ResultCode.Hbase_Error, "读历史信息异常");
					}
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
			//后处理下一页请求
			try{
				//将前面要读下一页历史信息的请求添加到下一页队列
				//tmplistinfo剩下的都是不自动下发分页的请求
				if (tmplistinfo.size() > 0) {
					synchronized(waitnextinfolist) {
						for(ReqHistoryInfo reqinfo:tmplistinfo) {
							//清空读下一页标志
							reqinfo.reqtime = System.currentTimeMillis();
							reqinfo.nexttimes = 0;
						}
						waitnextinfolist.addAll(tmplistinfo);
					}
					tmplistinfo.clear();
				}
				
				//用临时队列减少同步时间
				synchronized(waitnextinfolist) {
					if (waitnextinfolist.size() > 0) {
						tmplistinfo.addAll(waitnextinfolist);
					}
				}
				for(ReqHistoryInfo reqinfo: tmplistinfo) {
					//判断是否超时查询下一页
					if (reqinfo.nexttimes <= 0) {
						if (reqinfo.clientinfo.isClosed() ||  //客户端已经关闭
							((System.currentTimeMillis() - reqinfo.reqtime) > scantimeout)) {	//超过5分钟，则结束历史请求
							HbaseHistoryInfo.responseErrorMsg(reqinfo, ResultCode.Timeout_Error, "超时读下一页，结束读历史信息");
							reqinfo.stop();
							synchronized(waitnextinfolist) {
								waitnextinfolist.remove(reqinfo);
							}
						}
						continue;
					}
					int retcode = getNextHistoryInfo(reqinfo);
					if (retcode == ResultCode.OK) {
						hashistory = true;
					} else {
						HbaseHistoryInfo.responseErrorMsg(reqinfo, ResultCode.Hbase_Error, "读历史信息异常");
					}
					if (reqinfo.readstop) {	//已经结束读
						reqinfo.stop();
						synchronized(waitnextinfolist) {
							waitnextinfolist.remove(reqinfo);
						}
					}
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
			try{
				//如果没有最后信息请求，则sleep5毫秒, 减少CPU负载
				if (!hashistory) sleep(5);
			}catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
	
	//从Hbase读历史信息（第一页）,如果自动返回下一页，则继续取，否则加入nextinfolist
	private int getHistoryInfo(ReqHistoryInfo reqinfo) {
		HbaseHistoryInfo hbaseinfo = null;
		switch(reqinfo.infotype) {
		case MessageType.DeliverSimpleGPS:
			hbaseinfo = new SimpleGpsHistoryInfo(connection);
			break;
		case MessageType.DeliverGPS: 
			hbaseinfo = new GpsHistoryInfo(connection);
			break;
		case MessageType.DeliverTravel: 
			hbaseinfo = new TravelHistoryInfo(connection);
			break;
		case MessageType.DeliverFault: 
			hbaseinfo = new FaultHistoryInfo(connection);
			break;
		case MessageType.DeliverOperateData:
			hbaseinfo = new OperateDataHistoryInfo(connection);
			break;
		case MessageType.DeliverAlarm:
			hbaseinfo = new AlarmHistoryInfo(connection);
			break;
		case MessageType.DeliverSMS:
			hbaseinfo = new SmsHistoryInfo(connection);
			break;
		case MessageType.DeliverOBD:
			hbaseinfo = new OBDHistoryInfo(connection);
			break;
		case MessageType.DeliverUnitLoginOut:
			hbaseinfo = new UnitLoginoutHistoryInfo(connection);
			break;
		case MessageType.DeliverFaultLight:
			hbaseinfo = new FaultLightHistoryInfo(connection);
			break;
		default: //在GetLastInfoHandler.java中已经控制
			break;
		}
		if (hbaseinfo != null) {
			return hbaseinfo.getHistoryInfo(reqinfo);
		}
		return ResultCode.OK;
	}
	
	//从Hbase读历史信息（下一页）
	private int getNextHistoryInfo(ReqHistoryInfo reqinfo) {
		HbaseHistoryInfo hbaseinfo = null;
		switch(reqinfo.infotype) {
		case MessageType.DeliverSimpleGPS:
			hbaseinfo = new SimpleGpsHistoryInfo(connection);
			break;
		case MessageType.DeliverGPS: 
			hbaseinfo = new GpsHistoryInfo(connection);
			break;
		case MessageType.DeliverTravel: 
			hbaseinfo = new TravelHistoryInfo(connection);
			break;
		case MessageType.DeliverFault: 
			hbaseinfo = new FaultHistoryInfo(connection);
			break;
		case MessageType.DeliverOperateData:
			hbaseinfo = new OperateDataHistoryInfo(connection);
			break;
		case MessageType.DeliverAlarm:
			hbaseinfo = new AlarmHistoryInfo(connection);
			break;
		case MessageType.DeliverSMS:
			hbaseinfo = new SmsHistoryInfo(connection);
			break;
		case MessageType.DeliverOBD:
			hbaseinfo = new OBDHistoryInfo(connection);
			break;
		case MessageType.DeliverUnitLoginOut:
			hbaseinfo = new UnitLoginoutHistoryInfo(connection);
			break;
		default: //在GetLastInfoHandler.java中已经控制
			break;
		}
		if (hbaseinfo != null) {
			return hbaseinfo.getNextHistoryInfo(reqinfo);
		}
		return ResultCode.OK;
	}
}
