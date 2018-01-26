/*
********************************************************************************************
Discription:  管理理客户端连接（包括坐席、APP等接收实时GPS的客户端）
			  
			  
Written By:   ZXZ
Date:         2014-04-28
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.protobuf.ByteString;

import cc.chinagps.gboss.activemq.ActiveMQManager;
import cc.chinagps.gboss.alarmarray.AlarmInfo;
import cc.chinagps.gboss.alarmarray.AlarmManager;
import cc.chinagps.gboss.comcenter.UnitInfoManager.UnitInfo;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverAlarm;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverAppNotice;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverGPS;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverUnitLoginOut;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.AppNoticeInfo;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.command.SendCommandInfo;
import cc.chinagps.gboss.comcenter.outinterface.OutInterfaceClientInfo;

public final class CenterClientManager extends Thread {
	
	//全局唯一变量
	public static CenterClientManager clientManager = null; 
	public CenterClientManager() {
		super("CenterClientManager");
	}
	
	//接收全部终端的客户端队列
	private ArrayList<WebsocketClientInfo> allUnit = new ArrayList<WebsocketClientInfo>();
	//监控列表(线程安全), 有的客户端只是监控某些终端(某终端被监控的客户端) 
	private ConcurrentHashMap<String, ArrayList<WebsocketClientInfo>> monitorcallermap = new ConcurrentHashMap<String, ArrayList<WebsocketClientInfo>>();
	//客户端列表 ConcurrentHashMap 线程安全
	private ConcurrentHashMap<WebsocketClientInfo, Boolean> clientMap = new ConcurrentHashMap<WebsocketClientInfo, Boolean>();
	//海马客户端管理
	public HaiMaClientManager haimaclientmanager = new HaiMaClientManager();
	//外部接口只判断机构
	private ConcurrentHashMap<String, ArrayList<OutInterfaceClientInfo>> outinterfaceclientmap = new ConcurrentHashMap<String, ArrayList<OutInterfaceClientInfo>>();
	
	//测试外部接口是否丢失信息
	public ConcurrentHashMap<String, Integer> outintercalllettermap = new ConcurrentHashMap<String, Integer>();
	
	//监控信息
	public int maxclientcount = 0;	//最大客户端连接数
	public AtomicInteger clientcount = new AtomicInteger(0);		//最新客户端连接数
	public int allunitcount = 0;	//取全部终端数据的客户端
	
	//增加某客户端
	public void addClient(WebsocketClientInfo client) {
		try {
			//客户端不存在
			if (clientMap.put(client, true) == null) {
				int tmp = clientcount.incrementAndGet();
				if (maxclientcount < tmp) {
					maxclientcount = tmp;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//添加外部接口客户端
	public void addOutClient(OutInterfaceClientInfo client, String orgnos) {
		addClient(client);
		//添加监控列表(按机构编号分配)
		try {
			if (!outinterfaceclientmap.containsKey(orgnos)) {//原来没有此呼号的监控列表
				ArrayList<OutInterfaceClientInfo> list = new ArrayList<OutInterfaceClientInfo>();
				list.add(client);
				outinterfaceclientmap.put(orgnos, list);
			} else {//原来有此呼号的监控列表
				if (!outinterfaceclientmap.get(orgnos).contains(client)) {
					outinterfaceclientmap.get(orgnos).add(client);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//删除某客户端
	public void removeClient(WebsocketClientInfo client) {
		try {
			//客户端不存在
			if (clientMap.remove(client) == null) {
				return;
			}
			clientcount.decrementAndGet();
			if (client.allUnitSInfoType.size() > 0)
				allUnit.remove(client);
			for(String callletter : client.monitorcallletterMap.keySet()) {
				ArrayList<WebsocketClientInfo> list = monitorcallermap.get(callletter);
				if (list != null) {
					list.remove(client);
					if (list.isEmpty()) {
						monitorcallermap.remove(callletter);
					}
				}
			}
			for(String orgno : outinterfaceclientmap.keySet()) {
				ArrayList<OutInterfaceClientInfo> list = outinterfaceclientmap.get(orgno);
				if (list != null) {
					list.remove(client);
					if (list.isEmpty()) {
						outinterfaceclientmap.remove(orgno);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//增加客户端车台监控列表
	public void addMonitor(WebsocketClientInfo client, HashSet<String> callletterlist, List<Integer> infotypelist) {
		try {
			clientMap.put(client, true);
			for(String callletter : callletterlist) {
				//如果呼号是ALLUNIT, 则表示监控全部终端
				if (callletter.equals(WebsocketClientInfo.ALLUNIT)) {
					if (infotypelist!= null && !allUnit.contains(client)){
						allUnit.add(client);
						allunitcount = allUnit.size();
					}
				} else {
					if (!monitorcallermap.containsKey(callletter)) {//原来没有此呼号的监控列表
						ArrayList<WebsocketClientInfo> list = new ArrayList<WebsocketClientInfo>();
						list.add(client);
						monitorcallermap.put(callletter, list);
					} else {//原来有此呼号的监控列表
						if (!monitorcallermap.get(callletter).contains(client)) {
							monitorcallermap.get(callletter).add(client);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//取消客户端终端监控列表
	public void removeMonitor(WebsocketClientInfo client, HashSet<String> callletterlist) {
		try {
			for(String callletter : callletterlist) {
				//如果呼号是ALLUNIT, 则表示监控全部终端
				if (callletter.equals(WebsocketClientInfo.ALLUNIT)) {
					allUnit.remove(client);
					allunitcount = allUnit.size();
				} else {
					ArrayList<WebsocketClientInfo> list = monitorcallermap.get(callletter);
					if (list != null) {
						list.remove(client);
						//如果呼号没有被一个客户端监控
						if (list.isEmpty()) monitorcallermap.remove(callletter);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 统计某呼号的某类上传信息，要转发到那些客户端
	 */
	private ArrayList<WebsocketClientInfo> getMustDeliverClient(String callletter, int msgtype) {
		ArrayList<WebsocketClientInfo> retclientlist = new ArrayList<WebsocketClientInfo>();
		try {
			ArrayList<WebsocketClientInfo> clientlist = monitorcallermap.get(callletter);
			if (clientlist != null) {
				retclientlist.addAll(clientlist);
			}
			for(WebsocketClientInfo clientinfo: allUnit) {
				//判断全部呼号的客户端，中否要此类消息
				boolean bfind = false;
				for(Integer infotype: clientinfo.allUnitSInfoType) {
					if (infotype == msgtype || infotype == -1)
					{
						bfind = true;
						break;
					}
				}
				if (bfind) {	//如果找到判断是否重复
					bfind = false;
					for(int i=0; i<retclientlist.size(); i++) {
						if (retclientlist.get(i) == clientinfo) {
							bfind = true;
							break;
						}
					}
					if (!bfind) {	//如果原队列中没有
						retclientlist.add(clientinfo);
					}
				}
			}
			//外部接口客户端和一般不会重复
			if (outinterfaceclientmap.size() > 0) {
				UnitInfo unitinfo = UnitInfoManager.unitinfomanager.getUnitInfo(callletter);
				if (unitinfo != null) {
					ArrayList<OutInterfaceClientInfo> outclientlist = outinterfaceclientmap.get(unitinfo.orgcodes);
					if (outclientlist != null) {
						retclientlist.addAll(outclientlist);
						if (!outintercalllettermap.contains(callletter)) {
							outintercalllettermap.put(callletter, 1);
						} else {
							outintercalllettermap.replace(callletter, outintercalllettermap.get(callletter) + 1);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retclientlist;
	}
	/*
	 * 转发终端上传的信息到客户端，先统计那些客户端已经注册此呼号或该类消息
	 * bhistory: 如果是盲区补报或黑匣子记录，则不转发给APP
	 */
	public void deliverClient(String callletter, int msgtype, byte[] data, boolean bhistory) {
		ArrayList<WebsocketClientInfo> clientinfolist = getMustDeliverClient(callletter, msgtype);
		if (clientinfolist.size() > 0) {
			ComCenterMessage.ComCenterBaseMessage.Builder basemsgbuilder = ComCenterMessage.ComCenterBaseMessage.newBuilder();
			basemsgbuilder.setId(msgtype);
			basemsgbuilder.setContent(ByteString.copyFrom(data));
			for(WebsocketClientInfo clientinfo: clientinfolist) {
				//如果是历史记录，并且客户端是APP，则不转发
				//已经关闭的连接同治发送
				if (clientinfo.isClosed()) {
					continue;
				}
				//盲区补报，并且客户端是APP(WEBSOCKET)
				if (bhistory && (clientinfo.webhandler != null) && clientinfo.isMobileApp()) {
					continue;
				}
				clientinfo.appendSendMessage(basemsgbuilder.build());
			}
		}
	}
	
	//通知手机APPT终端脱网提醒
	public void addAppNoticeInfo(DeliverUnitLoginOut loginout, String callletter) {
		
		//判断是否要发送给海马手机APP或递送中心
		//boolean bnoticed = false;	//是否已经发送了海马通知,APP已经登录
		AppNoticeInfo noticeinfo = haimaclientmanager.createAppNoticeInfo(loginout, callletter);
		if (noticeinfo == null) {
			return;
		}
		//创建通知类消息
		ComCenterMessage.ComCenterBaseMessage.Builder noticebuilder = ComCenterMessage.ComCenterBaseMessage.newBuilder();
		noticebuilder.setId(MessageType.DeliverAppNotice);
		DeliverAppNotice.Builder deliverbuilder = DeliverAppNotice.newBuilder();
		deliverbuilder.setNoticeinfo(noticeinfo);
		noticebuilder.setContent(deliverbuilder.build().toByteString());
		
		//看有没有登录
		ArrayList<WebsocketClientInfo> clientinfolist = getMustDeliverClient(callletter, MessageType.DeliverUnitLoginOut);
		for(WebsocketClientInfo clientinfo: clientinfolist) {
			if (clientinfo.isMobileApp() && !clientinfo.isClosed()) {
				clientinfo.appendSendMessage(noticebuilder.build());
			}
		}
		//海马系统要保存通知, 不管是否已发APP
		ActiveMQManager.activemq.appnoticethread.addNotice(noticeinfo);
	}

	/*
	 * 处理终端上传的警情，因为海马终端如果没有APP登录，则必须写到ActiveMQ, 发送到通知中心
	 * 海马：unittype=3
	 */
	public void deliverClientAlarm(String callletter, DeliverAlarm deliveralarm) {
		ArrayList<WebsocketClientInfo> clientinfolist = getMustDeliverClient(callletter, MessageType.DeliverAlarm);
		
		//判断是否要发送给海马手机APP或递送中心
		//boolean bnoticed = false;	//是否已经发送了海马通知,APP已经登录
		AppNoticeInfo noticeinfo = null;
		boolean bhistory = false;
		if (deliveralarm.getAlarminfo().hasHistory()) {
			//0、或不存在表示是实时GPS，1:表示是黑匣子记录, 2:表示盲点补传
			bhistory = (deliveralarm.getAlarminfo().getHistory() != 0);
			if (bhistory && deliveralarm.getAlarminfo().hasBaseInfo()) {
				long expiredtime = System.currentTimeMillis() - deliveralarm.getAlarminfo().getBaseInfo().getGpsTime();
				bhistory = (expiredtime > 120000);	//超过120秒才是真的补报 
			}
			if (!bhistory) {
				noticeinfo = haimaclientmanager.createAppNoticeInfo(callletter, deliveralarm.getAlarminfo());
			}
		}
		ComCenterMessage.ComCenterBaseMessage.Builder noticebuilder = null;
		if (noticeinfo != null) {
			//创建通知类消息
			noticebuilder = ComCenterMessage.ComCenterBaseMessage.newBuilder();
			noticebuilder.setId(MessageType.DeliverAppNotice);
			DeliverAppNotice.Builder deliverbuilder = DeliverAppNotice.newBuilder();
			deliverbuilder.setNoticeinfo(noticeinfo);
			noticebuilder.setContent(deliverbuilder.build().toByteString());
		}
		if (clientinfolist.size() > 0) {
			ComCenterMessage.ComCenterBaseMessage.Builder basemsgbuilder = ComCenterMessage.ComCenterBaseMessage.newBuilder();
			basemsgbuilder.setId(MessageType.DeliverAlarm);
			basemsgbuilder.setContent(ByteString.copyFrom(deliveralarm.toByteArray()));
	
			for(WebsocketClientInfo clientinfo: clientinfolist) {
				//如果客户端断开
				if (clientinfo.isClosed()) {
					continue;
				}
				//盲区补报，并且客户端是APP(WEBSOCKET)
				if (bhistory && (clientinfo.webhandler != null) && clientinfo.isMobileApp()) {
					continue;
				}
				if (noticeinfo != null && clientinfo.isMobileApp()) {
					//如果是手机APP, 并且要通知
					//bnoticed = true;
					clientinfo.appendSendMessage(noticebuilder.build());
					//System.out.println("递交通知给手机APP客户端:" + clientinfo.username + ", " + noticeinfo.getCallLetter() + ", " + noticeinfo.getContent());
				} else {
					clientinfo.appendSendMessage(basemsgbuilder.build());
				}
			}
		}
		if (noticeinfo != null) { //通知还要送到海马的系统，所以全部都发送给通知中心  && !bnoticed) {
			//如果要通知，但是没有APP登录，则必须写到ActiveMQ, 发送给通知中心
			ActiveMQManager.activemq.appnoticethread.addNotice(noticeinfo);
		}
	}

	/*
	 * 
	 */
	public void deliverClientGPS(DeliverGPS delivergps) {
		String callletter = delivergps.getGpsinfo().getCallLetter().trim();
		ArrayList<WebsocketClientInfo> clientinfolist = getMustDeliverClient(callletter, MessageType.DeliverGPS);
		boolean bhistory = false;
		if (delivergps.getGpsinfo().hasHistory()) {
			//0、或不存在表示是实时GPS，1:表示是黑匣子记录, 2:表示盲点补传
			bhistory = (delivergps.getGpsinfo().getHistory() != 0);
			if (bhistory) {
				long expiredtime = System.currentTimeMillis() - delivergps.getGpsinfo().getBaseInfo().getGpsTime();
				bhistory = (expiredtime > 120000);	//超过120秒才是真的补报 
			}
		}
		if (clientinfolist.size() > 0) {
			DeliverGPS.Builder builder = DeliverGPS.newBuilder(delivergps);
			builder.getGpsinfoBuilder().clearContent();

			if (ComCenter.hasalarm) {
				//如果此车正在发生警情，添加警情信息
				AlarmInfo alarminfo = AlarmManager.alarmmanager.findAlarmInfo(callletter);
				if (alarminfo != null) {
					builder.setAlarmid(alarminfo.alarmid);
					builder.setAlarmname(alarminfo.alarmname);
				}
			}
			ComCenterMessage.ComCenterBaseMessage.Builder basemsgbuilder = ComCenterMessage.ComCenterBaseMessage.newBuilder();
			basemsgbuilder.setId(MessageType.DeliverGPS);
			basemsgbuilder.setContent(builder.build().toByteString());
			for(WebsocketClientInfo clientinfo: clientinfolist) {
				if (clientinfo.isClosed()) {
					continue;
				}
				//盲区补报，并且客户端是APP(WEBSOCKET)
				if (bhistory && (clientinfo.webhandler != null) && clientinfo.isMobileApp()) {
					continue;
				}
				clientinfo.appendSendMessage(basemsgbuilder.build());
			}
		}
	}
	
	// [start] 通知手机APP遥控指令结果
	//通知手机APP遥控指令结果
	public void addAppNoticeInfo(SendCommandInfo sendcmdinfo) {
		//只有APP(websocket)客户端才返回指令通知
		if ((sendcmdinfo.clientinfo.webhandler == null) || !(sendcmdinfo.clientinfo.isMobileApp())) {
			return;
		}

		AppNoticeInfo noticeinfo = haimaclientmanager.createAppNoticeInfo(sendcmdinfo);
		if (noticeinfo != null) {
			//不管是否终端在线，都摄像头到服务器
			ActiveMQManager.activemq.appnoticethread.addNotice(noticeinfo);
			
			if (!sendcmdinfo.clientinfo.isClosed()) {
				//System.out.println("递交通知给手机APP客户端:" + cmdinfo.clientcommand.clientinfo.username + ", " + noticeinfo.getCallLetter());
				ComCenterMessage.ComCenterBaseMessage.Builder basemsgbuilder = ComCenterMessage.ComCenterBaseMessage.newBuilder();
				basemsgbuilder.setId(MessageType.DeliverAppNotice);
				DeliverAppNotice.Builder deliverbuilder = DeliverAppNotice.newBuilder();
				deliverbuilder.setNoticeinfo(noticeinfo);
				basemsgbuilder.setContent(deliverbuilder.build().toByteString());
				sendcmdinfo.clientinfo.appendSendMessage(basemsgbuilder.build());
			}
		}
	}
	// [end] 通知手机APP遥控指令结果

	//循环发送客户端发送队列
	@Override
	public void run() {
		System.out.println("Client manager thread started");
		//开始海马刷新线程
		haimaclientmanager.start();
		while(true){
			try {
				//发送消息给客户端
				boolean sended = false;
				//rwlock.readLock().lock();
				for(WebsocketClientInfo key : clientMap.keySet()) {
					if (key.sendMessage()) sended = true; 
				}
				//rwlock.readLock().unlock();
				if (!sended) sleep(5);	//如果没有数据发送，则休息5毫秒，也就是如果队列中没有消息，则不要占用CPU
			} catch (Throwable e) {
				//rwlock.readLock().unlock();
				e.printStackTrace();
			}
		}
	}
}
