/*
********************************************************************************************
Discription:  从ActiveMQ中读到Alarm后，用多线程分发给客户端,
 			       第一个线程按呼号均匀分发到其他线程，其他再分发给客户
			  
Written By:   ZXZ
Date:         2014-07-17
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.activemq;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import cc.chinagps.gboss.comcenter.CenterClientManager;
import cc.chinagps.gboss.comcenter.UnitInfoManager;
import cc.chinagps.gboss.comcenter.buff.ActiveMQDataBuff.MQAlarm;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverAlarm;
import cc.chinagps.lib.util.BigHash;

public class AlarmThread extends Thread {
	public AlarmThread(int idx, String name) {
		super(name);
		threadidx = idx;
		if (threadidx == 0) {
			bytelist = new ArrayList<byte[]>();
		} else {
			deliveralarmlist = new ArrayList<DeliverAlarm>();
		}
	}
	
	private int threadidx;
	private ArrayList<byte[]> bytelist = null;	//第一个线程中处理的队列（第一个线程比较特殊，只是分发）
	private ArrayList<DeliverAlarm> deliveralarmlist = null;	//其他线程要处理的队列
	public static AtomicInteger alarmcounter = null;
	public static long alarmtotalcount;

	public static void init() {
		alarmcounter = new AtomicInteger();
		alarmcounter.set(0);
		alarmtotalcount = 0;
	}
	//从ActiveMQ接收Alarm，保存到第一个线程
	public void appendByte(byte[] data) {
		synchronized(bytelist) {
			bytelist.add(data);
		}
	}
	
	//第一个线程分发，加到其他线程处理队列
	private void appendDeliverAlarm(DeliverAlarm deliveralarm) {
		synchronized(deliveralarmlist) {
			deliveralarmlist.add(deliveralarm);
		}
	}

	@Override
	public void run() {
		boolean hasalarm = false;
		if (threadidx == 0) {	//第一个分发线程
			ArrayList<byte[]> tmpbytelist = new ArrayList<byte[]>();
			BigHash bigHash = new BigHash();
			MQAlarm mqalarm = null;
			String callletter = null;
			int hash = 0;
			
			while(true){
				try{
					mqalarm = null;
					callletter = null;
					//用临时队列减少同步时间
					hasalarm = false;
					tmpbytelist.clear();
					synchronized(bytelist) {
						if (bytelist.size() > 0) {
							hasalarm = true;
							tmpbytelist.addAll(bytelist);
							bytelist.clear();
						}
					}
					for(byte[] alarmdata: tmpbytelist) {
						try{
							mqalarm = MQAlarm.parseFrom(alarmdata);
						} catch (Throwable e) {
							e.printStackTrace();
							mqalarm = null;
						}
						if (mqalarm == null) {
							continue;
						}
						int icount = mqalarm.getDeliveralarmsList().size();
						alarmcounter.addAndGet(icount);
						alarmtotalcount += icount;

						for(DeliverAlarm deliveralarm : mqalarm.getDeliveralarmsList()) {
							try {
								//DeliverAlarm alarm = DeliverAlarm.parseFrom(data);
								callletter = deliveralarm.getAlarminfo().getCallLetter().trim();
								//计算分发线程，插入到分发线程
								hash = (int)(bigHash.hash(callletter.getBytes()) % AlarmReader.ALARMTHREADCOUNT);
								AlarmReader.alarmThread[hash+1].appendDeliverAlarm(deliveralarm);
							}catch (Throwable e) {
								e.printStackTrace();
							}
						}
					}
					//如果没有Alarm，则sleep5毫秒, 减少CPU负载
					if (!hasalarm) sleep(5);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
		else {
			ArrayList<DeliverAlarm> tmpdeliveralarmlist = new ArrayList<DeliverAlarm>();
			int gatewayid = 0;
			int gatewaytype = 0;
			String callletter = null;
			
			while(true){
				try{
					//用临时队列减少同步时间
					hasalarm = false;
					tmpdeliveralarmlist.clear();
					gatewayid = 0;
					gatewaytype = 0;
					callletter = null;
					synchronized(deliveralarmlist) {
						if (deliveralarmlist.size() > 0) {
							hasalarm = true;
							tmpdeliveralarmlist.addAll(deliveralarmlist);
							deliveralarmlist.clear();
						}
					}
					for(DeliverAlarm deliveralarm: tmpdeliveralarmlist) {
						try {
							callletter = deliveralarm.getAlarminfo().getCallLetter().trim();
							if (deliveralarm.hasGatewayid() && deliveralarm.hasGatewaytype()) {
								gatewayid = deliveralarm.getGatewayid();
								gatewaytype = deliveralarm.getGatewaytype();
								if (gatewayid > 0) {
									//先改变缓存区终端信息
									UnitInfoManager.unitinfomanager.setGatewayId(callletter, gatewayid, (gatewaytype==0));
								}
							}
							if (deliveralarm.getAlarminfo().hasTrigger()) {
								UnitInfoManager.unitinfomanager.setUnitOnOff(callletter, deliveralarm.getAlarminfo().getTrigger());
							}
							//判断是否要转发到那些客户端
							CenterClientManager.clientManager.deliverClientAlarm(callletter, deliveralarm);
						}catch (Throwable e) {
							e.printStackTrace();
						}
						alarmcounter.decrementAndGet();
					}
					//如果没有Alarm，则sleep5毫秒, 减少CPU负载
					if (!hasalarm) sleep(5);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
	}
}
