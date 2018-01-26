/*
********************************************************************************************
Discription:  从ActiveMQ接收GPS信息，并处理(转发到客户端)
			  
2.1 GPS信息
队列名称：gps
队列类型：topic
消息类型：BytesMessage
消息内容：DeliverGPS(protobuf结构，参见comcenter.proto的定义)的二进制数据
			  
Written By:   ZXZ
Date:         2014-05-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.activemq;

import javax.jms.Connection;
import cc.chinagps.gboss.alarmarray.AlarmAnalyseThread;
import cc.chinagps.gboss.comcenter.ComCenter;

public class GPSReader extends InfoReader {

	public static final int GPSTHREADCOUNT = 10;	 		//取历史信息的线程数量
	public static final int ALARMANALYSETHREADCOUNT = 10;	//分析警情的线程数量
	public static GpsThread[] gpsThread;
	public static AlarmAnalyseThread[] alarmanalyseThread;
	
	public GPSReader(Connection connection) {
		super("gps", Type.Topic, connection);
		GpsThread.init();
		//gps分发线程
		gpsThread = new GpsThread[GPSTHREADCOUNT + 1];
		for(int i=0; i<=GPSTHREADCOUNT; i++) {
			gpsThread[i] = new GpsThread(i);
			gpsThread[i].start();
			System.out.printf("deliver gps thread %d started!%n", i);
		}
		
		if (ComCenter.hasalarm) {
			//警情分析线程
			alarmanalyseThread = new AlarmAnalyseThread[ALARMANALYSETHREADCOUNT];
			for(int i=0; i<ALARMANALYSETHREADCOUNT; i++) {
				alarmanalyseThread[i] = new AlarmAnalyseThread(i);
				alarmanalyseThread[i].start();
				System.out.printf("Alarm Analyse thread %d started!%n", i);
			}
		}
	}

	/*
	 * 处理GPSDeliver信息 , 每次只有一个DeliverGPS结构
	 */
	@Override
	protected void processByteMessage(byte[] data) {
		try {
			gpsThread[0].appendByte(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
