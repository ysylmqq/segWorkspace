package cc.chinagps.gateway.web.setvlet;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seg.lib.net.server.tcp.SocketSession;

import cc.chinagps.gateway.aplan.APlanServer;
import cc.chinagps.gateway.aplan.APlanSocketSession;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.web.WebServer;
import cc.chinagps.gateway.web.util.Constants;
import cc.chinagps.gateway.web.util.JsonUtil;

public class OperateServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding(Constants.CHAR_ENCODING);
		response.setCharacterEncoding(Constants.CHAR_ENCODING);
		
		String type = request.getParameter("type");
		if("kickoffunit".equals(type)){
			kickoffUnit(request, response);
		}else if("kickoffaplan".equals(type)){
			kickoffAplan(request, response);
		}else if("kickoffaplanalarm".equals(type)){
			kickoffAplanAlarm(request, response);
		}else if("kickoffinner".equals(type)){
			kickoffInner(request, response);
		}
	}
	
	private void kickoffUnit(HttpServletRequest request, HttpServletResponse response) throws IOException{
		UnitServer unitServer = WebServer.getUnitServer();
		if(unitServer == null){
			JsonUtil.responseSuccess(response, false, "unit_server_is_off");
			return;
		}
		
		String remotes = request.getParameter("remotes");
		String[] rs = remotes.split(";");
		
		Iterator<UnitSocketSession> it = unitServer.getSessionManager().getSocketSessionMap().values().iterator();
		int kickoffcount = 0;
		while(it.hasNext()){
			UnitSocketSession session = it.next();
			if(search(session.getRemoteAddress().toString(), rs) != -1){
				session.close();
				kickoffcount++;
			}
		}
		
		JsonUtil.responseSuccess(response, true, kickoffcount);
	}
	
	private void kickoffAplan(HttpServletRequest request, HttpServletResponse response) throws IOException{
		UnitServer unitServer = WebServer.getUnitServer();
		if(unitServer == null){
			JsonUtil.responseSuccess(response, false, "unit_server_is_off");
			return;
		}
		
		APlanServer aplanServer = unitServer.getAPlanServer();
		if(aplanServer == null){
			JsonUtil.responseSuccess(response, false, "aplan_server_is_off");
			return;
		}
		
		String remotes = request.getParameter("remotes");
		String[] rs = remotes.split(";");
		
		Iterator<APlanSocketSession> it = aplanServer.getSessionManager().getSocketSessionMap().values().iterator();
		int kickoffcount = 0;
		while(it.hasNext()){
			APlanSocketSession session = it.next();
			if(search(session.getRemoteAddress().toString(), rs) != -1){
				session.close();
				kickoffcount++;
			}
		}
		
		JsonUtil.responseSuccess(response, true, kickoffcount);
	}
	
	private void kickoffAplanAlarm(HttpServletRequest request, HttpServletResponse response) throws IOException{
		UnitServer unitServer = WebServer.getUnitServer();
		if(unitServer == null){
			JsonUtil.responseSuccess(response, false, "unit_server_is_off");
			return;
		}
		
		APlanServer aplanAlarmServer = unitServer.getAPlanAlarmServer();
		if(aplanAlarmServer == null){
			JsonUtil.responseSuccess(response, false, "aplan_alarm_server_is_off");
			return;
		}
		
		String remotes = request.getParameter("remotes");
		String[] rs = remotes.split(";");
		
		Iterator<APlanSocketSession> it = aplanAlarmServer.getSessionManager().getSocketSessionMap().values().iterator();
		int kickoffcount = 0;
		while(it.hasNext()){
			APlanSocketSession session = it.next();
			if(search(session.getRemoteAddress().toString(), rs) != -1){
				session.close();
				kickoffcount++;
			}
		}
		
		JsonUtil.responseSuccess(response, true, kickoffcount);
	}
	
	private void kickoffInner(HttpServletRequest request, HttpServletResponse response) throws IOException{
		UnitServer unitServer = WebServer.getUnitServer();
		if(unitServer == null){
			JsonUtil.responseSuccess(response, false, "unit_server_is_off");
			return;
		}
		
		String remotes = request.getParameter("remotes");
		String[] rs = remotes.split(";");
		
		Iterator<SocketSession> it = unitServer.getSeatServer().getTCPServer().getSessionManager().getSocketSessions();
		int kickoffcount = 0;
		while(it.hasNext()){
			SocketSession session = it.next();
			if(search(session.getRemoteAddress().toString(), rs) != -1){
				session.close();
				kickoffcount++;
			}
		}
		
		JsonUtil.responseSuccess(response, true, kickoffcount);
	}
	
	private static int search(String str, String[] strs){
		for(int i = 0; i < strs.length; i++){
			if(strs[i].equals(str)){
				return i;
			}
		}
		
		return -1;
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}