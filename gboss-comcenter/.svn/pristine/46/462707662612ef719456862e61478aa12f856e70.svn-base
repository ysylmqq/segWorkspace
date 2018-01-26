/*
********************************************************************************************
Discription:  从ActiveMQ中读到GPS后，用多线程分发给客户端,
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

import cc.chinagps.gboss.alarmarray.ZooKeeperAlarm;
import cc.chinagps.gboss.comcenter.CenterClientManager;
import cc.chinagps.gboss.comcenter.ComCenter;
import cc.chinagps.gboss.comcenter.UnitInfoManager;
import cc.chinagps.gboss.comcenter.buff.ActiveMQDataBuff.MQGPS;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverGPS;
import cc.chinagps.lib.util.BigHash;

public class GpsThread extends Thread {
	public GpsThread(int idx) {
		super(String.format("GPSThread%02d",idx));
		threadidx = idx;
		if (threadidx == 0) {
			bytelist = new ArrayList<byte[]>();
		} else {
			delivergpslist = new ArrayList<DeliverGPS>();
		}
	}
	
	private int threadidx;
	private ArrayList<byte[]> bytelist;	//第一个线程中处理的队列（第一个线程比较特殊，只是分发）
	private ArrayList<DeliverGPS> delivergpslist;	//其他线程要处理的队列
	public static AtomicInteger gpscounter = null;
	public static long gpstotalcount;

	public static void init() {
		gpscounter = new AtomicInteger();
		gpscounter.set(0);
		gpstotalcount = 0;
	}
	
	//从ActiveMQ接收GPS，保存到第一个线程
	public void appendByte(byte[] data) {
		synchronized(bytelist) {
			bytelist.add(data);
		}
	}
	
	//从ActiveMQ接收GPS，保存到第一个线程
	public void appendDeliverGPS(DeliverGPS delivergps) {
		synchronized(delivergpslist) {
			delivergpslist.add(delivergps);
		}
	}

	@Override
	public void run() {
		boolean hasgps = false;
		if (threadidx == 0) {	//第一个分发线程
			ArrayList<byte[]> tmpbytelist = new ArrayList<byte[]>();
			BigHash bigHash = new BigHash();

			MQGPS mqgps = null;
			String callletter = null;
			
			long bighash;
			int hash;
			int hashalarm;

			while(true){
				//先处理第一次请求
				try{
					//用临时队列减少同步时间
					mqgps = null;
					callletter = null;
					hasgps = false;
					tmpbytelist.clear();
					synchronized(bytelist) {
						if (bytelist.size() > 0) {
							hasgps = true;
							tmpbytelist.addAll(bytelist);
							bytelist.clear();
						}
					}
					for(byte[] gpsdata: tmpbytelist) {
						try{
							mqgps = MQGPS.parseFrom(gpsdata);
						} catch (Throwable e) {
							e.printStackTrace();
							mqgps = null;
						}
						if (mqgps == null) {
							continue;
						}
						int icount = mqgps.getDeliverpgsesList().size();
						gpscounter.addAndGet(icount);
						gpstotalcount += icount;
						for(DeliverGPS delivergps : mqgps.getDeliverpgsesList()) {
							try {
								//DeliverGPS delivergps = DeliverGPS.parseFrom(data);
								callletter = delivergps.getGpsinfo().getCallLetter().trim();
								//计算分发线程，插入到分发线程
								bighash = bigHash.hash(callletter.getBytes());
								hash = (int)(bighash % GPSReader.GPSTHREADCOUNT);
								GPSReader.gpsThread[hash+1].appendDeliverGPS(delivergps);
								
								if (ComCenter.hasalarm) {
									hashalarm = (int)(bighash % GPSReader.ALARMANALYSETHREADCOUNT);
									//如果包含警情分析功能
									if (ComCenter.isdistributed) {
										//如果分布式，并且此终端属于此节点分析警情，
										if (ZooKeeperAlarm.zookeeperalarm.callLetterBelong(callletter)) {
											GPSReader.alarmanalyseThread[hashalarm].appendDeliverGPS(delivergps.getGpsinfo());
										}
									} else {
										//没有分布式
										GPSReader.alarmanalyseThread[hashalarm].appendDeliverGPS(delivergps.getGpsinfo());
									}
								}
							} catch (Throwable e) {
								e.printStackTrace();
							}
						}
					}
					//如果没有GPS，则sleep5毫秒, 减少CPU负载
					if (!hasgps) sleep(5);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
		else {
			ArrayList<DeliverGPS> tmpgpslist = new ArrayList<DeliverGPS>();
			String callletter = null;
			int gatewayid = 0;
			int gatewaytype = 0;
			
			while(true){
				//先处理第一次请求
				try{
					//用临时队列减少同步时间
					hasgps = false;
					tmpgpslist.clear();
					callletter = null;
					gatewayid = 0;
					gatewaytype = 0;
					synchronized(delivergpslist) {
						if (delivergpslist.size() > 0) {
							hasgps = true;
							tmpgpslist.addAll(delivergpslist);
							delivergpslist.clear();
						}
					}
					for(DeliverGPS delivergps: tmpgpslist) {
						try {
							if (delivergps.hasGatewayid() && delivergps.hasGatewaytype()) {
								callletter = delivergps.getGpsinfo().getCallLetter().trim();
								gatewayid = delivergps.getGatewayid();
								gatewaytype = delivergps.getGatewaytype();
								//先改变缓存区终端信息
								UnitInfoManager.unitinfomanager.setGatewayId(callletter, gatewayid, (gatewaytype==0));
							}
							//判断是否要转发到那些客户端
							CenterClientManager.clientManager.deliverClientGPS(delivergps);
						}catch (Throwable e) {
							e.printStackTrace();
						}
						gpscounter.decrementAndGet();
					}
					//如果没有GPS，则sleep5毫秒, 减少CPU负载
					if (!hasgps) sleep(5);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
	}
}
