package cc.chinagps.gateway.web.setvlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.util.SystemConfig;
import cc.chinagps.gateway.web.util.Constants;
import cc.chinagps.gateway.web.util.ErrorCode;
import cc.chinagps.gateway.web.util.JsonUtil;
import cc.chinagps.gateway.web.util.MakePicUtil;

public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding(Constants.CHAR_ENCODING);
		response.setCharacterEncoding(Constants.CHAR_ENCODING);
		
		String type = request.getParameter("type");
		if("checkSession".equals(type)){
			HttpSession session = request.getSession();
			Object o = session.getAttribute(Constants.SESSION_INFO_KEY);
			JsonUtil.responseSuccess(response, o != null);
		}else if("login".equals(type)){
			login(request, response);
		}else if("logout".equals(type)){
			logout(request, response);
		}else if("getCertPic".equals(type)){
			getCertPic(request, response);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	private Map<String, String> userMap = new HashMap<String, String>();
	
	@Override
	public void init() throws ServletException {
		userMap.put(Constants.DEFAULT_USER, Constants.DEFAULT_PASSWORD);
		userMap.put(SystemConfig.getWebProperty("user"), SystemConfig.getWebProperty("password"));
	}
	
	private void getCertPic(HttpServletRequest request, HttpServletResponse response) throws IOException{
		int width, height;
		try{
			width = Integer.parseInt(request.getParameter("width"));
		}catch(Exception e){
			width = 0;
		}
		try{
			height = Integer.parseInt(request.getParameter("height"));
		}catch(Exception e){
			height = 0;
		}
		
		int imageType;
		try{
			imageType = Integer.parseInt(request.getParameter("imageType"));
		}catch(Exception e){
			imageType = 0;
		}
		HttpSession session = request.getSession();
		MakePicUtil.outPutCertPic(width, height, response, imageType, session);
	}
	
	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession session = request.getSession();
		String userName = request.getParameter("userName");
		String passWord = request.getParameter("passWord");
		String loginCode = request.getParameter("loginCode");
		
		Object code = session.getAttribute(Constants.SESSION_LOGIN_CODE);
		if(loginCode == null || code== null || !loginCode.equalsIgnoreCase(code.toString())){
			JsonUtil.responseSuccess(response, false, ErrorCode.LOGIN_CODE_WRONG);
			return;
		}
		
		String pwd = userMap.get(userName);
		if(pwd == null){
			JsonUtil.responseSuccess(response, false, ErrorCode.LOGIN_USER_NOT_EXIST);
			return;
		}
		
		if(passWord == null || !pwd.toUpperCase().equals(Util.MD5(passWord).toUpperCase())){
			JsonUtil.responseSuccess(response, false, ErrorCode.LOGIN_PASSWORD_WRONG);
			return;
		}
		
		session.setAttribute(Constants.SESSION_INFO_KEY, "user");
		JsonUtil.responseSuccess(response, true);
	}
	
	private void logout(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		session.removeAttribute(Constants.SESSION_INFO_KEY);
		session.invalidate();
	}
}