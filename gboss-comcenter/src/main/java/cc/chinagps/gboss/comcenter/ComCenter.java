/*
********************************************************************************************
Discription:  通信中心主程序
			  
			  
Written By:   ZXZ
Date:         2014-04-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter;

import java.util.Date;

import cc.chinagps.gboss.Log.LogManager;
import cc.chinagps.gboss.activemq.ActiveMQManager;
import cc.chinagps.gboss.alarmarray.AlarmManager;
import cc.chinagps.gboss.alarmarray.interprotocolsocket.SeatClientServer;
import cc.chinagps.gboss.alarmarray.websocket.SeatWebSocketServer;
import cc.chinagps.gboss.alarmarray.ZooKeeperAlarm;
import cc.chinagps.gboss.comcenter.command.CommandManager;
import cc.chinagps.gboss.comcenter.interprotocolsocket.InterProtocolComcenter;
import cc.chinagps.gboss.comcenter.jmxtool.JmxManager;
import cc.chinagps.gboss.comcenter.outinterface.OutInterfaceServer;
import cc.chinagps.gboss.comcenter.websocket.*;
import cc.chinagps.gboss.database.DBUtil;
import cc.chinagps.lib.util.SystemConfig;
import cc.chinagps.lib.util.Util;

//import cc.chinagps.sreg.client.RegClient;
//import cc.chinagps.sreg.client.RegClientImpl;
import cc.chinagps.gboss.hbase.HbaseClientManager;

public class ComCenter 
{
	public static String starttime = null;
	public static int websocketport = 8070;
	public static int clientport = 5001;
	public static int jmxport = websocketport + 1;
	public static int systemId = 101;
	public static String systemname = "大平台通信中心";
	public static int seatwebsocketport = 18070;
	public static int seatclientport = 15001;
	public static int outinterfaceport = 36001;
	public static boolean haswebsocket = true;		//包含WebSocket
	public static boolean hasalarm = true;			//包含警情处理
	public static boolean hasinterprotocol = true;	//包含内部协议
	public static boolean isdistributed = true;		//是否支持分布式部署
	public static boolean verifypassword = false;	//Websocket是否进行密码校验
	
	public static void init() {
    	//应用程序名称
    	//String appName = "GBossComCenter";
    	//获取注册程序客户端
    	//RegClient rc = new RegClientImpl();
    	//获取程序是否已经注册
    	//boolean isReg = rc.isRegistered(appName);
    	//if(!isReg ){
    		//没有注册，提示用户未注册，程序中断
	    	//System.out.println("大平台通信中心没有注册，请向深圳总公司要注册码！");
	    	//return;
    	//}
		starttime = Util.DatetoString(new Date(System.currentTimeMillis()));
		websocketport = Integer.valueOf(SystemConfig.getSystemProperties("websocket_port"));
		jmxport = Integer.valueOf(SystemConfig.getSystemProperties("jmxport"));
		clientport = Integer.valueOf(SystemConfig.getSystemProperties("client_server_port"));
		systemId = Integer.valueOf(SystemConfig.getSystemProperties("system_id"));
		systemname = SystemConfig.getSystemProperties("system_name");
		systemname = systemname.replace('/', '_');	//名称不能带/字符
		seatwebsocketport = Integer.valueOf(SystemConfig.getSystemProperties("seatwebsocket_port"));
		seatclientport = Integer.valueOf(SystemConfig.getSystemProperties("seat_server_port"));
		outinterfaceport = Integer.valueOf(SystemConfig.getSystemProperties("outinterface_port"));
		haswebsocket = Boolean.valueOf(SystemConfig.getSystemProperties("has_websocket"));
		hasinterprotocol = Boolean.valueOf(SystemConfig.getSystemProperties("has_interptotocol"));
		hasalarm = Boolean.valueOf(SystemConfig.getSystemProperties("has_alarm"));
		verifypassword = Boolean.valueOf(SystemConfig.getSystemProperties("verifypassword"));
		
		//如果没有警情分析，也就不用分布式
		isdistributed = hasalarm && Boolean.valueOf(SystemConfig.getSystemProperties("distributed"));
		
    	System.out.println("systemId: " + systemId);
    	System.out.println("systemname: " + systemname);
    	System.out.println("hasalarm: " + hasalarm);
    	System.out.println("distributed: " + isdistributed);
    	System.out.println("haswebsocket: " + haswebsocket);
    	System.out.println("hasinterprotocol: " + hasinterprotocol);

    	//初始化公共类
    	DBUtil.Init();
		HbaseClientManager.hbaseclientmanager = new HbaseClientManager();
    	ActiveMQManager.activemq = new ActiveMQManager();
    	CenterClientManager.clientManager = new CenterClientManager(); 
    	CommandManager.commandmanager = new CommandManager("CommandManager");
    	UnitInfoManager.unitinfomanager = new UnitInfoManager();
    	
		//如果包含websocket
    	if (haswebsocket) {
    		System.out.println("websocketport: " + websocketport);
    	}
    	if (hasalarm) {
    		//只有包含警情分析的功能，才用坐席端口
    		System.out.println("seatwebsocketport: " + seatwebsocketport);
    		System.out.println("seatclientport: " + seatclientport);
			AlarmManager.alarmmanager = new AlarmManager();
			if (isdistributed) {
				ZooKeeperAlarm.zookeeperalarm = new ZooKeeperAlarm();
			}
    	}
    	if (hasinterprotocol) {
    		System.out.println("clientport: " + clientport);
    		System.out.println("outinterfaceport: " + outinterfaceport);
    	}
	}
	
    public static void main(String[] args) {
	    try {
	    	ComCenter.init();	//系统初始化
			LogManager.init();	//日志初始化
			
			//启动读终端信息的线程
			UnitInfoManager.unitinfomanager.start();

			//启动Hbase客户端
			if (!HbaseClientManager.hbaseclientmanager.start()) {
				//hbase初始化
				return;
			}
			//启动ActiveMQ消息中间件
	    	if (!ActiveMQManager.activemq.start()) {
	    		//ActiveMQ开始
	    		return;
	    	}
	    	
	    	//启动客户端管理（websocket, 内部协议、坐席等）
	    	CenterClientManager.clientManager.start();	//客户端管理开始
	    	
	    	//启动终端命令管理
	    	CommandManager.commandmanager.start();
	    	
	    	//jmx监控器
	    	new JmxManager(jmxport).start();	//启动jmx监控器

			if (hasalarm) {
				if (isdistributed) {
			    	//启动分布式zookeeper
			    	if (!ZooKeeperAlarm.zookeeperalarm.start()) {
			    		return;
			    	}
				}
		    	//启动警情分析及警情队列管理
		    	if (!AlarmManager.alarmmanager.init()) {
		    		return;
		    	}
			}

	    	if (haswebsocket) {
		    	//一般程序（或手机APP）WebSocket接口
	    		//启动websocket侦听端口
		    	new WebSocketServer(websocketport).start(!(hasalarm || hasinterprotocol));
	    	}
	    	if (ComCenter.hasalarm) {
		    	//坐席WebSocket接口
		    	new SeatWebSocketServer(seatwebsocketport).start();	//坐席websocket侦听端口，坐席和一般应用程序用不同的端口，不要相互影响
		    	//坐席内部通信（功能相当于seatwebsocket）,采用内部通信协议
		    	new SeatClientServer(seatclientport).start(!hasinterprotocol);
	    	}

	    	if (hasinterprotocol) {
		    	//外部程序接口服务端
		    	new OutInterfaceServer(outinterfaceport).start();
		    	//一般程序的内部通信（功能相当于websocket）,采用内部通信协议
		    	new InterProtocolComcenter(clientport).start();
	    	}
	    } catch (Exception e) {
	    	System.out.println(e.getMessage());
	    }
    	System.out.println("ComCenter Exit");
	}
}
