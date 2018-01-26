/*
********************************************************************************************
Discription:  客户端的基本信息，内部通信客户端、websocket客户端通用的信息
			  
			  
Written By:   ZXZ
Date:         2014-04-28
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage;
import cc.chinagps.gboss.comcenter.interprotocolsocket.InterProtocolComcenterHandler;
import cc.chinagps.gboss.comcenter.outinterface.OutInterfaceServerHandler;
import cc.chinagps.gboss.comcenter.websocket.WebSocketServerHandler;

public class WebsocketClientInfo {

	public final static String ALLUNIT = "ALLUNIT";
	public String username = "";	//操作员登录名
	public String userid = "";		//操作员ID
	public String clienttype1 = "webclient";	//客户端类型
	public String clientversion = "1.0";		//客户端版本号
	public String ipaddr = "";					//客户端IP地址
	
	//全部终端转发类型, 有的客户端是要全部终端的某类递交信息（如短信，行程信息）
	public ArrayList<Integer> allUnitSInfoType = new ArrayList<Integer>();
	//监控列表(线程安全), 有的客户端只是监控某些终端， 
	public ConcurrentHashMap<String, Boolean> monitorcallletterMap = new ConcurrentHashMap<String, Boolean>();
	//发送到客户端的内容，用一个线程去统一发送，其他线程只是添加
	protected ArrayList<ClientComCenterBaseMessage> sendmsglist = new ArrayList<ClientComCenterBaseMessage>(); 

	//客户端通信通道, 发送时要用(WEBSOCKET通道)
	public WebSocketServerHandler webhandler = null;
	//客户端通信通道, 发送时要用(内部协议通道)
	public InterProtocolComcenterHandler interhandler = null;
	//客户端通信通道, 发送时要用(对外接口协议通道)
	public OutInterfaceServerHandler outinterfacehandler = null;
	
	protected boolean closed = true;
	protected boolean logined = false;
	
	public void setClosed(boolean closed) {
		this.closed = closed;
		if (this.closed) {
			logined = false;
		}
	}
	//判断是否已经关闭
	public boolean isClosed() {
		return closed;
	}
	
	public void setLogin(boolean login) {
		this.logined = login;
	}
	//判断是否已经登录
	public boolean isLogin() {
		return (logined && (!closed));
	}
	//判断是否是手机用户
	public boolean isMobileApp() {
		return (clienttype1.startsWith("IOS-") || clienttype1.startsWith("ANDROID-"));
	}
	
	/*
	 * 返回正在监控的列表
	 */
	public HashSet<String> getMonitorList() {
		HashSet<String> ret = new HashSet<String>();
		Iterator<String> iter = monitorcallletterMap.keySet().iterator();
		while(iter.hasNext()) {
			ret.add(iter.next());
		}
		return ret;
	}
	
	/*
	 * 返回正在监控的列表
	 */
	public boolean hasCallLetter(String callletter) {
		return monitorcallletterMap.containsKey(callletter);
	}
	
	/*
	 * 返回正在监控的列表
	 */
	public boolean hasAllUnit(int infotype) {
		for(Integer v: allUnitSInfoType) {
			if (v == infotype || v == -1) return true;
		}
		return false;
	}
	public boolean hasAllUnit() {
		return !(allUnitSInfoType.isEmpty());
	}
	
	/*
	 * 添加监控列表, 返回是否用全部终端
	 */
	public void AddAllMonitor() {
		HashSet<String> callletterlist = new HashSet<String>();
		callletterlist.add(WebsocketClientInfo.ALLUNIT);
		ArrayList<Integer> infotypelist = new ArrayList<Integer>(); 
		infotypelist.add((int)(MessageType.DeliverGPS));
		infotypelist.add((int)(MessageType.DeliverUnitLoginOut));
		infotypelist.add((int)(MessageType.DeliverFault));
		infotypelist.add((int)(MessageType.DeliverAlarm));
		infotypelist.add((int)(MessageType.DeliverOBD));
		addMonitor(callletterlist, infotypelist, true);
	}
	public void addMonitor(HashSet<String> callletterlist, List<Integer> infotypelist, boolean clearold) {
		if (clearold) {
			CenterClientManager.clientManager.removeMonitor(this, getMonitorList());
			allUnitSInfoType.clear();
			monitorcallletterMap.clear();
		}
		for(String callletter : callletterlist) {
			if (callletter.isEmpty()) continue;
			//如果呼号是ALLUNIT, 则表示监控全部终端
			if (callletter.equals(ALLUNIT)) { //只有内部协议才支持ALLUNIT
				if (infotypelist != null) {
					for(Integer v: infotypelist) {
						//如果是全部类型，则 
						if (v == -1) {
							allUnitSInfoType.clear();
							allUnitSInfoType.add(-1);
							break;
						} else {
							//查找是否已经存在，如果已经存在，则不插入
							boolean find = false;
							for(Integer i: allUnitSInfoType) {
								if (i == v || i == -1) {
									find = true;
									break;
								}
							}
							if (!find) {
								allUnitSInfoType.add(v);
							}
						}
					}
				}
				
			} else if (!monitorcallletterMap.containsKey(callletter)) {	//如果不在监控列表
					monitorcallletterMap.put(callletter, true);
			}
		}
		CenterClientManager.clientManager.addMonitor(this, callletterlist, infotypelist);
	}

	/*
	 * 添加监控列表, 返回是否删除全部终端
	 */
	public void removeMonitor(HashSet<String> callletterlist) {
		synchronized(monitorcallletterMap) {
			//如果不是是全部终端转发
			for(String callletter : callletterlist) {
				callletter = callletter.trim();
				//如果呼号是ALLUNIT, 则表示取消全部监控
				if (callletter.equals(ALLUNIT)) {
					allUnitSInfoType.clear();
				} else {
					monitorcallletterMap.remove(callletter);
				}
			}
		}
		CenterClientManager.clientManager.removeMonitor(this, callletterlist);
	}

	/*
	 * 清空监控列表
	 */
	public void clearMonitor() {
		synchronized(monitorcallletterMap) {
			CenterClientManager.clientManager.removeMonitor(this, getMonitorList());
			allUnitSInfoType.clear();
			monitorcallletterMap.clear();
		}
	}
	
	/*
	 * 添加要发送给客户端的消息, 发送到客户端的消息都先保存到队列，则一个线程统一发送，不管WEBSOCKET登录或其他， 内部协议的登录、链路检测，由接收线程返回
	 */
	public void appendSendMessage(ComCenterMessage.ComCenterBaseMessage basemsg) {
		if (isLogin()) {
			int msglen = basemsg.toByteArray().length;
			ClientComCenterBaseMessage item = new ClientComCenterBaseMessage(basemsg, msglen);
			synchronized(sendmsglist) {
				sendmsglist.add(item);
			}
		}
	}

	/*
	 * 发送消息队列
	 */
	public boolean sendMessage() {
		//如果已经关闭，则不用发送
		if (this.isClosed()) {
			return false;
		}
		ComCenterMessage.Builder msg = ComCenterMessage.newBuilder();
		int totalLen = 0;
		synchronized(sendmsglist) {
			if (sendmsglist.isEmpty() || 
				((webhandler != null) && !webhandler.isWritable()) ||
				((interhandler != null) && !interhandler.isWritable()) ||
				((outinterfacehandler != null) && !outinterfacehandler.isWritable())) {
				return false;
			}
			while(sendmsglist.size() > 0) {
				ClientComCenterBaseMessage clientMsg = sendmsglist.get(0);
				if (totalLen == 0 || totalLen + clientMsg.length <= 65500) {
					msg.addMessages(clientMsg.basemsg);
					totalLen += clientMsg.length;
					sendmsglist.remove(0);
				}
				else {
					break;
				}
			}
		}
		if (webhandler != null) {
			webhandler.WriteByteArray(msg.build().toByteArray());
			return true;
		}
		if (interhandler != null) {
			interhandler.WriteContent(msg.build().toByteArray());
			return true;
		}
		if (outinterfacehandler != null) {
			outinterfacehandler.WriteBody(msg.build().toByteArray());
			return true;
		}
		return false;
	}
}
