package cc.chinagps.gateway.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.slf4j.common.RunLogger;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.udp.UdpServer;
import cc.chinagps.gateway.util.SystemConfig;
import cc.chinagps.gateway.util.Util;
import cc.chinagps.gateway.web.setvlet.LoadInfoServlet;
import cc.chinagps.gateway.web.setvlet.LoginServlet;
import cc.chinagps.gateway.web.setvlet.OperateServlet;
import cc.chinagps.gateway.web.util.WebUtil;

public class WebServer {
	
	public static void main(String[] args) {
		WebServer webServer = new WebServer();
		try {
			webServer.startService();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static UnitServer unitServer;
	
	private UdpServer udpServer;
	
	public  UdpServer getUdpServer() {
		return udpServer;
	}

	public  void setUdpServer(UdpServer udpServer) {
		this.udpServer = udpServer;
	}

	public static UnitServer getUnitServer() {
		return unitServer;
	}

	public void setUnitServer(UnitServer unitServer) {
		WebServer.unitServer = unitServer;
	}

	public void startService() throws Exception{
		int port = Integer.valueOf(SystemConfig.getWebProperty("port"));
		String resourceBase = SystemConfig.getWebProperty("resourceBase");
		Server server = new Server(port); // 8082

		ServletContextHandler servletHandler = new ServletContextHandler();
		servletHandler.setSessionHandler(new SessionHandler());
		servletHandler.addServlet(LoginServlet.class, "/login");
		servletHandler.addServlet(LoadInfoServlet.class, "/loadInfo");
		servletHandler.addServlet(OperateServlet.class, "/operate");
		
		ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setWelcomeFiles(new String[]{"index.html"});
        resource_handler.setResourceBase(resourceBase);
        
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] {resource_handler, servletHandler});
        server.setHandler(handlers);
		
		server.start();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("[" + sdf.format(new Date()) + "]web server ready!(" + port +")");
		//server.join();
		
		RunLogger runLogger = RunLogger.getLogger();
		runLogger.startService();
		String info = Util.getMapInfo(WebUtil.getUnitServerBaseInfo(unitServer));
		runLogger.info(info);
	}
}