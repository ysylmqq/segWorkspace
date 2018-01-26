package cc.chinagps.gateway.web.setvlet;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seg.lib.net.server.tcp.CommonSocketSession;
import org.seg.lib.net.server.tcp.SocketSession;

import cc.chinagps.gateway.aplan.APlanSocketSession;
import cc.chinagps.gateway.hbase.client.HBaseClient;
import cc.chinagps.gateway.hbase.client.SaveThreads;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beans.UnitInfo;
import cc.chinagps.gateway.unit.udp.UdpServer;
import cc.chinagps.gateway.web.WebServer;
import cc.chinagps.gateway.web.util.Constants;
import cc.chinagps.gateway.web.util.JsonUtil;
import cc.chinagps.gateway.web.util.WebUtil;

public class LoadInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding(Constants.CHAR_ENCODING);
		response.setCharacterEncoding(Constants.CHAR_ENCODING);
		
		String type = request.getParameter("type");
		if("base".equals(type)){
			loadBaseInfo(request, response);
		}else if("mq".equals(type)){
			loadMQInfo(request, response);
		}else if("hbase".equals(type)){
			loadHBaseInfo(request, response);
		}else if("trace".equals(type)){
			loadTraceInfo(request, response);
		}else if("unit".equals(type)){
			loadUnitInfo(request, response);
		}else if("aplan".equals(type)){
			loadAPlanInfo(request, response);
		}else if("alarm".equals(type)){
			loadAPlanAlarmInfo(request, response);
		}else if("seat".equals(type)){
			loadSeatInfo(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void loadBaseInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		UnitServer unitServer = WebServer.getUnitServer();
		if(unitServer == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("no_data", true);
			JsonUtil.responseJsonObject(response, map);
			return;
		}
		
		Map<String, Object> map = WebUtil.getUnitServerBaseInfo(unitServer);
		JsonUtil.responseJsonObject(response, map);
	}
	
	private void loadMQInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		UnitServer unitServer = WebServer.getUnitServer();
		if(unitServer == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("no_data", true);
			JsonUtil.responseJsonObject(response, map);
			return;
		}
		
		boolean enabled = unitServer.getExportMQ().isEnabled();
		if(!enabled){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("disabled", true);
			JsonUtil.responseJsonObject(response, map);
			return;
		}
		
		long throwCount = unitServer.getExportMQ().getThrowCount();
		int cacheSize = unitServer.getExportMQ().getDataCount();
		int savePosition = unitServer.getExportMQ().getPosition();
		long saveLoop = unitServer.getExportMQ().getLoop();
		int readPosition = unitServer.getCmdReader().getPosition();
		long readLoop = unitServer.getCmdReader().getLoop();
		
		//int alarmReadPosition = unitServer.getExportMQ().getSpecialAlarmHandler().getReadPosition();
		//File readFile = unitServer.getExportMQ().getSpecialAlarmHandler().getFileCacher().getReadFile();
		//File writeFile = unitServer.getExportMQ().getSpecialAlarmHandler().getFileCacher().getWriteFile();
		//long readFileSize = readFile.exists()? readFile.length(): -1;
		//long writeFileSize = writeFile.exists()? writeFile.length(): -1;
		//long successCount = unitServer.getExportMQ().getSpecialAlarmHandler().getSuccessCount();
		//long failCount = unitServer.getExportMQ().getSpecialAlarmHandler().getFailCount();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cacheSize", cacheSize);
		map.put("throwCount", throwCount);
		map.put("savePosition", savePosition);
		map.put("saveLoop", saveLoop);
		map.put("readPosition", readPosition);
		map.put("readLoop", readLoop);
		
		//map.put("alarm_readPosition", alarmReadPosition);
		//map.put("alarm_writeFileSize", FileUtil.getFileLength(writeFileSize));
		//map.put("alarm_readFileSize", FileUtil.getFileLength(readFileSize));
		//map.put("alarm_successCount", successCount);
		//map.put("alarm_failCount", failCount);
		
		JsonUtil.responseJsonObject(response, map);
	}
	
	private void loadHBaseInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		UnitServer unitServer = WebServer.getUnitServer();
		if(unitServer == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("no_data", true);
			JsonUtil.responseJsonObject(response, map);
			return;
		}
		
		boolean enabled = unitServer.getExportHBase().isEnabled();
		if(!enabled){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("disabled", true);
			JsonUtil.responseJsonObject(response, map);
			return;
		}
		
		HBaseClient client = unitServer.getExportHBase().getClient();
		SaveThreads[] threads = client.getSaveThreads();
		long throwCount = client.getThrowDataCount();
		long cacheSize = client.getDataList().size();
		long total = 0;
		double totalSpeed = 0;
		
		List<Map<String, Object>> threadsInfo = new ArrayList<Map<String,Object>>();
		
		NumberFormat nf = new DecimalFormat("#.##");
		for(int i = 0; i < threads.length; i++){
			total += threads[i].getTotal();
			totalSpeed += threads[i].getTotal_speed();
			
			Map<String, Object> oneThread = new HashMap<String, Object>();
			oneThread.put("total", threads[i].getTotal());
			oneThread.put("totalSpeed", nf.format(threads[i].getTotal_speed()));
			oneThread.put("min_total_speed", formatDouble(threads[i].getMin_total_speed(), nf));
			oneThread.put("max_total_speed", nf.format(threads[i].getMax_total_speed()));
			oneThread.put("add_speed", nf.format(threads[i].getAdd_speed()));
			oneThread.put("min_add_speed", formatDouble(threads[i].getMin_add_speed(), nf));
			oneThread.put("max_add_speed", nf.format(threads[i].getMax_add_speed()));

			threadsInfo.add(oneThread);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cacheSize", cacheSize);
		map.put("throwCount", throwCount);
		map.put("total", total);
		map.put("totalSpeed", nf.format(totalSpeed));
		map.put("threadsInfo", threadsInfo);
		
		JsonUtil.responseJsonObject(response, map);
	}

	private String formatDouble(Double value, NumberFormat nf){
		if(value == null){
			return "0";
		}
		
		return nf.format(value);
	}
	
	private void loadTraceInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		UnitServer unitServer = WebServer.getUnitServer();
		if(unitServer == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("no_data", true);
			JsonUtil.responseJsonObject(response, map);
			return;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Map<CommonSocketSession, Long>> traceMap = unitServer.getTraceMap();
		
		List<Map<String, Object>> details = new ArrayList<Map<String,Object>>();
		
		Iterator<Entry<String, Map<CommonSocketSession, Long>>> it = traceMap.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, Map<CommonSocketSession, Long>> entry = it.next();
			String callLetter = entry.getKey();
			Map<CommonSocketSession, Long> v = entry.getValue();
			List<String> clientsInfo = new ArrayList<String>();
			Iterator<Entry<CommonSocketSession, Long>> itv = v.entrySet().iterator();
			while(itv.hasNext()){
				Entry<CommonSocketSession, Long> entryv = itv.next();
				CommonSocketSession session = entryv.getKey();
				long time = entryv.getValue();
				StringBuilder info = new StringBuilder();
				info.append(session.getRemoteAddress()).append("(");
				info.append(sdf.format(new Date(time))).append(")");
				clientsInfo.add(info.toString());
			}
			
			Map<String, Object> detail = new HashMap<String, Object>();
			detail.put("callLetter", callLetter);
			detail.put("clientsInfo", clientsInfo);
			details.add(detail);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("size", traceMap.size());
		map.put("details", details);
		JsonUtil.responseJsonObject(response, map);
	}
	
	private void loadUnitInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		UnitServer unitServer = WebServer.getUnitServer();
		if(unitServer == null){
			return;
		}
		
		String callLetter = request.getParameter("callLetter");
		if(callLetter.startsWith("@")){
			try{
				handleCommand(request, response, unitServer, callLetter);
			}catch (Exception e) {
				throw new IOException(e);
			}
			
			return;
		}
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
//		Iterator<UnitSocketSession> it = unitServer.getSessionManager().getSocketSessionMap().values().iterator();
//		while(it.hasNext()){
//			UnitSocketSession session = it.next();
//			UnitInfo unitInfo = session.getUnitInfo();
//			if(unitInfo != null && unitInfo.getCallLetter().equals(callLetter)){
//				Map<String, Object> unit = getUnitInfo(session);
//				list.add(unit);
//			}
//		}
		
		UnitSocketSession session = unitServer.getSessionManager().getSessionByCallLetter(callLetter);
		if(session != null){
			Map<String, Object> unit = getUnitInfo(session);
			list.add(unit);
		}else{
			
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", list.size());
		map.put("list", list);
		JsonUtil.responseJsonObject(response, map);
	}
	
	private void handleCommand(HttpServletRequest request,
			HttpServletResponse response, UnitServer unitServer, String command) throws Exception{
		String[] params = command.substring(1).split("\\s++");
		if("ls".equals(params[0])){
			if(params.length > 1){
				if("login".equals(params[1])){
					searchLoginUnit(unitServer, response);
				}else if("notlogin".equals(params[1])){
					searchNotLoginUnit(unitServer, response);
				}else if("top".equals(params[1])){
					searchUnitTop(unitServer, response, params);
				}else if("start".equals(params[1]) && params.length > 2){
					searchUnitStartWithCall(unitServer, response, params[2]);
				}else if("end".equals(params[1]) && params.length > 2){
					searchUnitEndWithCall(unitServer, response, params[2]);
				}else if("has".equals(params[1]) && params.length > 2){
					searchUnitHasCall(unitServer, response, params[2]);
				}else if("count".equals(params[1])){
					count(unitServer, response);
				}
			}
			
			forceAll(unitServer, response);
		}else if("chkss".equals(params[0])){
			if("u".equals(params[1])){
				if("start".equals(params[2])){
					unitServer.getSessionManager().setCheckFlag(true);
				}else if("stop".equals(params[2])){
					unitServer.getSessionManager().setCheckFlag(false);
				}
				
				responseCommand(response, "current flag:" + unitServer.getSessionManager().isCheckFlag());
			}
		}else{
			responseCommand(response, "command not found!");
		}
	}
	
	private void loadAPlanInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		//String cmd = request.getParameter("cmd");
		UnitServer unitServer = WebServer.getUnitServer();
		if(unitServer == null){
			return;
		}
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Iterator<APlanSocketSession> it = unitServer.getAPlanServer().getSessionManager().getSocketSessionMap().values().iterator();
		while(it.hasNext()){
			APlanSocketSession session = it.next();
			Map<String, Object> client = getAplanClientInfo(session);
			list.add(client);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", list.size());
		map.put("list", list);
		JsonUtil.responseJsonObject(response, map);
	}
	
	private void loadAPlanAlarmInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		//String cmd = request.getParameter("cmd");
		UnitServer unitServer = WebServer.getUnitServer();
		if(unitServer == null){
			return;
		}
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Iterator<APlanSocketSession> it = unitServer.getAPlanAlarmServer().getSessionManager().getSocketSessionMap().values().iterator();
		while(it.hasNext()){
			APlanSocketSession session = it.next();
			Map<String, Object> client = getAplanClientInfo(session);
			list.add(client);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", list.size());
		map.put("list", list);
		JsonUtil.responseJsonObject(response, map);
	}
	
	private static Map<String, Object> getAplanClientInfo(APlanSocketSession session){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> aplan = new HashMap<String, Object>();
		aplan.put("createTime", sdf.format(session.getCreateTime()));
		aplan.put("lastActiveTime", sdf.format(session.getLastActiveTime()));
		aplan.put("address", session.getRemoteAddress().toString());
		
		return aplan;
	}
	
	private void loadSeatInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		//String cmd = request.getParameter("cmd");
		UnitServer unitServer = WebServer.getUnitServer();
		if(unitServer == null){
			return;
		}
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Iterator<SocketSession> it = unitServer.getSeatServer().getTCPServer().getSessionManager().getSocketSessions();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		while(it.hasNext()){
			SocketSession session = it.next();
			Map<String, Object> client = new HashMap<String, Object>();
			client.put("createTime", sdf.format(session.getCreateTime()));
			client.put("lastActiveTime", sdf.format(session.getLastActiveTime()));
			client.put("address", session.getRemoteAddress().toString());
			
			list.add(client);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", list.size());
		map.put("list", list);
		JsonUtil.responseJsonObject(response, map);
	}
	
	private void responseCommand(HttpServletResponse response, String result) throws IOException{
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>(1);
		Map<String, Object> unit = new HashMap<String, Object>();
		unit.put("callLetter", result);
		list.add(unit);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", list.size());
		map.put("list", list);
		JsonUtil.responseJsonObject(response, map);
	}
	
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(System.currentTimeMillis()));
	}
	
	private Map<String, Object> getUnitInfo(UnitSocketSession session){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> unit = new HashMap<String, Object>();
		unit.put("createTime", sdf.format(session.getCreateTime()));
		unit.put("lastActiveTime", sdf.format(session.getLastActiveTime()));
		unit.put("address", session.getRemoteAddress().toString());
		unit.put("uploads", session.getUploadPackageCount());
		unit.put("downloads", session.getDownloadPackageCount());
		unit.put("protocolType", session.getProtocolType());
		
		UnitInfo unitInfo = session.getUnitInfo();
		if(unitInfo != null){
			unit.put("callLetter", unitInfo.getCallLetter());
		}
		
		return unit;
	}
	
	private void forceAll(UnitServer unitServer,
			HttpServletResponse response) throws IOException{
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Iterator<UnitSocketSession> it = unitServer.getSessionManager().getSocketSessionMap().values().iterator();
		while(it.hasNext()){
			UnitSocketSession session = it.next();
			Map<String, Object> unit = getUnitInfo(session);
			list.add(unit);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", list.size());
		map.put("list", list);
		JsonUtil.responseJsonObject(response, map);
	}
	
	private void searchNotLoginUnit(UnitServer unitServer,
			HttpServletResponse response) throws IOException{
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Iterator<UnitSocketSession> it = unitServer.getSessionManager().getSocketSessionMap().values().iterator();
		while(it.hasNext()){
			UnitSocketSession session = it.next();
			if(session.getUnitInfo() == null){
				Map<String, Object> unit = getUnitInfo(session);
				list.add(unit);
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", list.size());
		map.put("list", list);
		JsonUtil.responseJsonObject(response, map);
	}
	
	private void searchLoginUnit(UnitServer unitServer,
			HttpServletResponse response) throws IOException{
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
//		Iterator<UnitSocketSession> it = unitServer.getSessionManager().getSocketSessionMap().values().iterator();
//		while(it.hasNext()){
//			UnitSocketSession session = it.next();
//			if(session.getUnitInfo()!= null){
//				Map<String, Object> unit = getUnitInfo(session);
//				list.add(unit);
//			}
//		}
		
		Iterator<UnitSocketSession> it = unitServer.getSessionManager().getCallSocketSessionMap().values().iterator();
		while(it.hasNext()){
			UnitSocketSession session = it.next();
			Map<String, Object> unit = getUnitInfo(session);
			list.add(unit);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", list.size());
		map.put("list", list);
		JsonUtil.responseJsonObject(response, map);
	}
	
	private void count(UnitServer unitServer,
			HttpServletResponse response) throws IOException{
		Map<String, Integer> countInfo = new HashMap<String, Integer>();
		Iterator<UnitSocketSession> it = unitServer.getSessionManager().getSocketSessionMap().values().iterator();
		while(it.hasNext()){
			UnitSocketSession session = it.next();
			String protocolType = session.getProtocolType();
			
			Integer v = countInfo.get(protocolType);
			if(v == null){
				v = 0;
			}
			
			v++;
			countInfo.put(protocolType, v);
		}
		
		responseCommand(response, countInfo.toString());
	}
	
	private void searchUnitStartWithCall(UnitServer unitServer,
			HttpServletResponse response, String callLetter) throws IOException{
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Iterator<UnitSocketSession> it = unitServer.getSessionManager().getSocketSessionMap().values().iterator();
		while(it.hasNext()){
			UnitSocketSession session = it.next();
			if(session.getUnitInfo()!= null){
				String call = session.getUnitInfo().getCallLetter();
				if(call != null && call.startsWith(callLetter)){
					Map<String, Object> unit = getUnitInfo(session);
					list.add(unit);
				}
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", list.size());
		map.put("list", list);
		JsonUtil.responseJsonObject(response, map);
	}
	
	private void searchUnitEndWithCall(UnitServer unitServer,
			HttpServletResponse response, String callLetter) throws IOException{
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Iterator<UnitSocketSession> it = unitServer.getSessionManager().getSocketSessionMap().values().iterator();
		while(it.hasNext()){
			UnitSocketSession session = it.next();
			if(session.getUnitInfo()!= null){
				String call = session.getUnitInfo().getCallLetter();
				if(call != null && call.endsWith(callLetter)){
					Map<String, Object> unit = getUnitInfo(session);
					list.add(unit);
				}
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", list.size());
		map.put("list", list);
		JsonUtil.responseJsonObject(response, map);
	}
	
	private void searchUnitHasCall(UnitServer unitServer,
			HttpServletResponse response, String callLetter) throws IOException{
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Iterator<UnitSocketSession> it = unitServer.getSessionManager().getSocketSessionMap().values().iterator();
		while(it.hasNext()){
			UnitSocketSession session = it.next();
			if(session.getUnitInfo()!= null){
				String call = session.getUnitInfo().getCallLetter();
				if(call != null && call.indexOf(callLetter) != -1){
					Map<String, Object> unit = getUnitInfo(session);
					list.add(unit);
				}
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", list.size());
		map.put("list", list);
		JsonUtil.responseJsonObject(response, map);
	}
	
	private void searchUnitTop(UnitServer unitServer,
			HttpServletResponse response, String[] params) throws IOException{
		int total = 10;
		if(params.length > 2){
			try{
				total = Integer.valueOf(params[2]);
			}catch (Exception e) {	
			}
		}
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Iterator<UnitSocketSession> it = unitServer.getSessionManager().getSocketSessionMap().values().iterator();
		int count = 0;
		while(it.hasNext()){
			UnitSocketSession session = it.next();
			Map<String, Object> unit = getUnitInfo(session);
			list.add(unit);
			
			count ++;
			if(count >= total){
				break;
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", list.size());
		map.put("list", list);
		JsonUtil.responseJsonObject(response, map);
	}
}