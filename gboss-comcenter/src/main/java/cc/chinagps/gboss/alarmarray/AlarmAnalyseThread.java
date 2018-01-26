/*
********************************************************************************************
Discription:  从ActiveMQ中读到GPS后，用多线程分析警情,
			  
Written By:   ZXZ
Date:         2014-09-09
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.alarmarray;

import java.util.ArrayList;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsInfo;

public class AlarmAnalyseThread extends Thread {
	public AlarmAnalyseThread(int idx) {
		super(String.format("AlarmAnalyse%02d", idx));
		gpsinfolist = new ArrayList<GpsInfo>();
	}
	
	private ArrayList<GpsInfo> gpsinfolist;	//其他线程要处理的队列

	//从ActiveMQ接收GPS，保存到第一个线程
	public void appendDeliverGPS(GpsInfo gpsinfo) {
		synchronized(gpsinfolist) {
			gpsinfolist.add(gpsinfo);
		}
	}

	@Override
	public void run() {
		boolean hasgps = false;
		ArrayList<GpsInfo> tmpgpslist = new ArrayList<GpsInfo>();
		while(true){
			//先处理第一次请求
			try{
				//用临时队列减少同步时间
				hasgps = false;
				tmpgpslist.clear();
				synchronized(gpsinfolist) {
					if (gpsinfolist.size() > 0) {
						hasgps = true;
						tmpgpslist.addAll(gpsinfolist);
						gpsinfolist.clear();
					}
				}
				//分析每条GPS是否包含警情
				for(GpsInfo gpsinfo: tmpgpslist) {
					try {
						hasgps = true;
						AlarmManager.alarmanalyse.analyse(gpsinfo);
					}catch (Throwable e) {
						e.printStackTrace();
					}
				}
				//如果没有GPS，则sleep5毫秒, 减少CPU负载
				if (!hasgps) sleep(5);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
}
