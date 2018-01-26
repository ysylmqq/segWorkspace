/*
********************************************************************************************
Discription:  用一个线程往ActiveMQ中写App通知消息（从队列中取数据, 如果失败，不删除，直到）
                            其他线程先插入队列

//App通知消息protobuf格式
message AppNoticeInfo {
    required string callLetter = 1;                 //车辆呼号
    required string title = 2;                      //通知标题
    required string content = 3;                    //通知内容
    optional GpsBaseInfo baseInfo = 4;              //gps基本信息，请参考基本信息protobuf定义
    optional GpsReferPosition referPosition = 5;    //参考位置，请参考参考位置protobuf定义
};
			  
Written By:   ZXZ
Date:         2014-12-31
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.activemq;

import java.util.ArrayList;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import cc.chinagps.gboss.alarmarray.ZooKeeperAlarm;
import cc.chinagps.gboss.comcenter.ComCenter;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverAppNotice;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.AppNoticeInfo;

public class AppNoticeThread extends Thread {

	private static String mqqueuename = "appnotice";
	
	//写App通知用一个连接
	private Connection mqconnection;
	//通知队列
	private ArrayList<AppNoticeInfo> noticelist;	//APP通知信息队列，采用protobuf格式

	public AppNoticeThread() {
		super("AppNoticeThread");
		noticelist = new ArrayList<AppNoticeInfo>();
		mqconnection = ActiveMQManager.activemq.createConnection();
	}
	//添加通知
	public void addNotice(AppNoticeInfo notice) {
		if (!ComCenter.isdistributed || ZooKeeperAlarm.zookeeperalarm.isMasterd) {
			//System.out.println("发通知给通知中心：" + notice.getCallLetter() + ", " + notice.getContent());
			synchronized(noticelist) {
				noticelist.add(notice);
			}
		}
	}
	
	/*
	 * 有可能部分成功，部分不成功,
	 */
	private boolean writeNotice(ArrayList<AppNoticeInfo> tmplist) {
		try {
			Session session = mqconnection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue(mqqueuename);
			MessageProducer producer = session.createProducer(queue);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			
			for(int i=0; i<tmplist.size(); i++) {
				try {
					BytesMessage sendmsg = session.createBytesMessage();
					DeliverAppNotice.Builder builder = DeliverAppNotice.newBuilder();
					builder.setNoticeinfo(tmplist.get(i));
					sendmsg.writeBytes(builder.build().toByteArray());
					producer.send(sendmsg);
					if (++i > ActiveMQManager.activemq.BATCH_SIZE_WRITE) {
						session.commit();
						while((--i) >= 0) {
							tmplist.remove(i);
						}
						i = 0;
					}
				} catch (JMSException e) {
					session.rollback();
		            System.out.println("activeMQ write app notice:");
					e.printStackTrace();
					return false;
				}
			}
			session.commit();
			tmplist.clear();
			return true;
		} catch (JMSException e1) {
            System.out.println("activeMQ write app notice:");
			e1.printStackTrace();
		}
		return false;
	}
	
	@Override
	public void run() {
		boolean hasnotice = false;
		while(true){
			try {
				mqconnection.start();
				ArrayList<AppNoticeInfo> tmplist = null;
				synchronized(noticelist) {
					if (noticelist.size() > 0) {
						tmplist = new ArrayList<AppNoticeInfo>();
						tmplist.addAll(noticelist);
						noticelist.clear();
					}
				}
				if (tmplist != null) {
					hasnotice = writeNotice(tmplist);
					if (tmplist.size() > 0) {
						//表示有部分没有写成功，必须写回去
						synchronized(noticelist) {
							noticelist.addAll(0, tmplist);
						}
					}
				}
				if (!hasnotice) sleep(5);
			} catch (Exception e) {
				e.printStackTrace();
				try {sleep(5);} catch(Exception ex){};
			}
		}
	}
}
