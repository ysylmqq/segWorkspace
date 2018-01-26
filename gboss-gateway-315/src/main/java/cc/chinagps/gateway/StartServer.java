package cc.chinagps.gateway;

import cc.chinagps.gateway.aplan.APlanServer;
import cc.chinagps.gateway.hbase.export.ExportHBase;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.memcache.MemcacheUpdator;
import cc.chinagps.gateway.mq.CmdReader;
import cc.chinagps.gateway.mq.MQManager;
import cc.chinagps.gateway.mq.export.ExportMQInf;
import cc.chinagps.gateway.mq.export.ExportMQMult;
import cc.chinagps.gateway.seat.SeatServer;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.common.BaseStationManager;
import cc.chinagps.gateway.unit.kelong.upload.KeLongVehicleManager;
import cc.chinagps.gateway.unit.udp.UdpServer;
import cc.chinagps.gateway.util.SystemConfig;
import cc.chinagps.gateway.web.WebServer;

public class StartServer {
	// 登录 5B80303130303130303030310B31333531323334353637385D
	// 上行短消息
	// 5BE0303130303130303030311865365230901A77E5FF0C660E5929003770B951C665F652305D
	// 分类短消息
	// 5BE0303130303130303030312000230031901A77E5FF0C660E5929003770B951C665F65230516C53F85F004F1A5D
	// 链路检测 5BF030303030303030303030005D
	// 退录 5B81303130303130303030310B31333531323334353637385D

	public static StartServer instance = new StartServer();
	public static int isHistoryTime = 180;
	public static boolean isParseBaseStation = false;
	private UnitServer unitServer;

	public UnitServer getUnitServer() {
		return unitServer;
	}

	public void setUnitServer(UnitServer unitServer) {
		this.unitServer = unitServer;
	}


	public void start() {
		
		LogManager.init();

		int unitServerPort = Integer.valueOf(SystemConfig.getSystemProperty("unit_server_port"));
		int udpServerPort = Integer.valueOf(SystemConfig.getSystemProperty("udp_server_port"));

		int seatServerPort = Integer.valueOf(SystemConfig.getSystemProperty("seat_server_port"));
		int aPlanServerPort = Integer.valueOf(SystemConfig.getSystemProperty("a_plan_server_port"));

		int aPlanAlarmServerPort = Integer.valueOf(SystemConfig.getSystemProperty("a_plan_alarm_server_port"));
		
		String isParse = SystemConfig.getSystemProperty("parse_basestation");
		if (isParse != null) {
			isParseBaseStation = Boolean.valueOf(isParse);
		}
		
		unitServer = new UnitServer(unitServerPort);
		UdpServer udpServer = new UdpServer(udpServerPort); //10001
		SeatServer seatServer = new SeatServer(seatServerPort);
		APlanServer aPlanServer = new APlanServer(aPlanServerPort, "APlan Server");
		APlanServer aPlanAlarmServer = new APlanServer(aPlanAlarmServerPort, "APlan Alarm Server");

		// seatServer -- unitServer
		unitServer.setSeatServer(seatServer);
		seatServer.setUnitServer(unitServer);

		udpServer.setSeatServer(seatServer);
		seatServer.setUdpServer(udpServer);

		// aPlanServer -- unitServer
		unitServer.setAPlanServer(aPlanServer);
		aPlanServer.setUnitServer(unitServer);

		udpServer.setAPlanServer(aPlanServer);
		aPlanServer.setUdpServer(udpServer);

		// aPlanAlarmServer -- unitServer
		unitServer.setAPlanAlarmServer(aPlanAlarmServer);
		aPlanAlarmServer.setUnitServer(unitServer);

		udpServer.setAPlanAlarmServer(aPlanAlarmServer);
		aPlanAlarmServer.setUdpServer(udpServer);

		// web
		WebServer webServer = new WebServer();
		webServer.setUnitServer(unitServer);
		webServer.setUdpServer(udpServer);

		try {
			String historyTime = SystemConfig.getSystemProperty("is_history_time");
			if (historyTime != null) {
				isHistoryTime = Integer.valueOf(historyTime);
			}

			String obdAdapt = SystemConfig.getSystemProperty("kelong_obd_adapt");
			if (obdAdapt != null) {
				boolean kelongObdAdapt = Boolean.valueOf(obdAdapt);
				if (kelongObdAdapt) {
					KeLongVehicleManager.start();
				}
			}
			
			// init hbase
			boolean hbaseEnabled = Boolean.valueOf(SystemConfig.getHBaseProperty("enabled"));
			ExportHBase exportHBase = new ExportHBase();
			exportHBase.setEnabled(hbaseEnabled);
			unitServer.setExportHBase(exportHBase);

			udpServer.setExportHBase(exportHBase);
			if (hbaseEnabled) {
				exportHBase.init();
			} else {
				System.err.println("HBase is disabled!");
			}
			
			if(isParseBaseStation){
				BaseStationManager.getInstance().init();
			}
			// init memcache
			// MemcacheManager.init();
			MemcacheUpdator memcacheUpdator = new MemcacheUpdator();
			memcacheUpdator.setUnitServer(unitServer);
			unitServer.setMemcacheUpdator(memcacheUpdator);

			udpServer.setMemcacheUpdator(memcacheUpdator);

			memcacheUpdator.startWork();

			// init mq
			boolean mqEnabled = Boolean.valueOf(SystemConfig.getMQProperty("enabled"));

			// ExportMQInf exportMQ = new ExportMQSingle();
			ExportMQInf exportMQ = new ExportMQMult();
			exportMQ.setEnabled(mqEnabled);
			unitServer.setExportMQ(exportMQ);

			udpServer.setExportMQ(exportMQ);

			if (mqEnabled) {
				MQManager.init();

				// write cmd-response to mq
				exportMQ.init();
				exportMQ.startWorker();

				CmdReader reader = new CmdReader(exportMQ, unitServer, udpServer);
				unitServer.setCmdReader(reader);

				udpServer.setCmdReader(reader);
				// read cmd from mq
				reader.init();
				reader.startWorker();
			} else {
				System.err.println("ActiveMQ is disabled!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		unitServer.startService();
		seatServer.startService();
		aPlanServer.startService();
		aPlanAlarmServer.startService();
		udpServer.startService();
		try {
			webServer.startService();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("system ready!");
	}

	public static void main(String[] args) {
		instance.start();
	}
}