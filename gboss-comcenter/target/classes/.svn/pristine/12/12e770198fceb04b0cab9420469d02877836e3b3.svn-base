/*
********************************************************************************************
Discription:  从ActiveMQ接收GPS信息，并处理(转发到客户端)
			  
2.2警情信息
队列名称：alarm
队列类型：topic
消息类型：BytesMessage
消息内容：
			  
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

public class AlarmReader extends InfoReader {

	public static int ALARMTHREADCOUNT = 2;	 //取警情的线程数量
	public static AlarmThread[] alarmThread = null;

	public AlarmReader(Connection connection) {
		super("alarm", Type.Topic, connection);
		AlarmThread.init();
		alarmThread = new AlarmThread[ALARMTHREADCOUNT + 1];
		for(int i=0; i<=ALARMTHREADCOUNT; i++) {
			alarmThread[i] = new AlarmThread(i, "AlarmDeliver" + i);
			alarmThread[i].start();
			System.out.printf("deliver alarm thread %d started!%n", i);
		}
	}

	/*
	 * 处理alarm信息 , 每次只有一个DeliverAlarm结构
	 */
	@Override
	protected void processByteMessage(byte[] data) {
		try {
			alarmThread[0].appendByte(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
