package com.chinaGPS.gtmp.business.command;


import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.chinaGPS.gtmp.entity.CommandSendPOJO;
import com.chinaGPS.gtmp.service.ISendCommandService;
import com.chinaGPS.gtmp.util.SpringContext;


/**
 * 
 * @author dy
 *
 */
public class TimingLockThread extends Thread{
	private static Logger exceptionLogger = Logger.getLogger("EXCEPTION");   
	
	private boolean runningFlag = true;

	@Resource
	private ISendCommandService sendCommandService;	

	@Resource
	private CommandSendPOJO commandSendPOJO;
	
	@Resource
	private SendCommand sendCommand;

	public void run() {
		System.out.println("预约锁车线程启动!!!");
		sendCommandService = (ISendCommandService)SpringContext.getContext().getBean("sendCommandServiceImpl");
		sendCommand = (SendCommand)SpringContext.getContext().getBean("sendCommand");
		if(sendCommandService ==null || sendCommand == null){
			runningFlag = false;
		}
		while (runningFlag) {
			try {
				sleep(10000); // 间隔时间60秒				
				List<CommandSendPOJO> list = sendCommandService.getTiminglock();
				if(null!=list && list.size()>0){
					for(CommandSendPOJO command : list){
						sendCommand.sendCommand(command);
						sendCommandService.updateTiminglock(command.getTiminglockid());
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				try {
					sleep(30000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void close() {
		runningFlag = false;
	}

	public boolean isRunningFlag() {
		return runningFlag;
	}

	public void setRunningFlag(boolean runningFlag) {
		this.runningFlag = runningFlag;
	}
	
}