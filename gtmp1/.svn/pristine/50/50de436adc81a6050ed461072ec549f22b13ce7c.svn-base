package com.chinaGPS.gtmp.action.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chinaGPS.gtmp.business.command.PushThread;
import com.chinaGPS.gtmp.business.command.TimingLockThread;
/**
 * 系统启动时自动启动程序
 * @author xk
 *
 */
public class SysInit extends HttpServlet {
	private static final long serialVersionUID = 7353460863571414966L;

	public void init() throws ServletException {
		TimingLockThread timingLockThread = new TimingLockThread();
		timingLockThread.setDaemon(true);
		timingLockThread.start();
		PushThread pushThread = new PushThread();
		pushThread.setDaemon(true);
		pushThread.start();
	}

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	public void destroy() {
		
	}
}