/*
********************************************************************************************
Discription:  从Hbase读最扣信息的线程
			  
Written By:   ZXZ
Date:         2014-06-02
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

public class GetLastInfoThread extends Thread {
	//获取连接
	private HConnection connection = null;
	private HbaseClientManager clientmanager;
	private ArrayList<ReqLastInfo> lastinfolist;
	
	public GetLastInfoThread(String name) throws IOException {
		super(name);
		this.clientmanager = HbaseClientManager.hbaseclientmanager;
		this.connection = HConnectionManager.createConnection(clientmanager.config);
		this.lastinfolist = new ArrayList<ReqLastInfo>();
	}
	
	public void AppendLastInfo(WebsocketClientInfo clientinfo, String callletter, int infotype, String sn) {
		synchronized(lastinfolist) {
			lastinfolist.add(new ReqLastInfo(clientinfo, callletter, infotype, sn));
		};
	}

	@Override
	public void run() {
		boolean haslast = false;
		ArrayList<ReqLastInfo> tmplistinfo = new ArrayList<ReqLastInfo>();
		while(true){
			haslast = false;
			try{
				synchronized(lastinfolist) {
					if (lastinfolist.size() > 0) {
						tmplistinfo.addAll(lastinfolist);
						lastinfolist.clear();
					}
				}
				while(tmplistinfo.size() > 0) {
					ReqLastInfo reqinfo = tmplistinfo.get(0);
					int retcode = GetLastInfoFromHbase(reqinfo);
					tmplistinfo.remove(0);
					if (retcode == ResultCode.OK) {
						haslast = true;
					} else {
						HbaseLastInfo.ResponseErrorMsg(reqinfo, ResultCode.Hbase_Error, "读最后信息异常");
					}
				}
				//如果没有最后信息请求，则sleep5毫秒, 减少CPU负载
				if (!haslast) sleep(5);
			}catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
	
	//从Hbase读最后信息
	private int GetLastInfoFromHbase(ReqLastInfo reqinfo) {
		HbaseLastInfo hbaselastinfo = null;
		switch(reqinfo.infotype) {
		case MessageType.DeliverGPS: 
			hbaselastinfo = new GpsLastInfo(connection);
			break;
		case MessageType.DeliverTravel: 
			hbaselastinfo = new TravelLastInfo(connection);
			break;
		case MessageType.DeliverFault: 
			hbaselastinfo = new FaultLastInfo(connection);
			break;
		case MessageType.DeliverOperateData:
			hbaselastinfo = new OperateDataLastInfo(connection);
			break;
		case MessageType.DeliverAlarm:
			hbaselastinfo = new AlarmLastInfo(connection);
			break;
		case MessageType.DeliverSMS:
			hbaselastinfo = new SmsLastInfo(connection);
			break;
		case MessageType.DeliverOBD:
			hbaselastinfo = new OBDLastInfo(connection);
			break;
		case MessageType.DeliverUnitLoginOut:
			hbaselastinfo = new UnitLoginoutLastInfo(connection);
			break;
		case MessageType.DeliverFaultLight:
			hbaselastinfo = new FaultLightLastInfo(connection);
			break;
		default: //在GetLastInfoHandler.java中已经控制
			break;
		}
		if (hbaselastinfo != null) {
			return hbaselastinfo.GetLastInfo(reqinfo);
		}
		return ResultCode.OK;
	}
}
