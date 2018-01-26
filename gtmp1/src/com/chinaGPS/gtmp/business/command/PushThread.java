package com.chinaGPS.gtmp.business.command;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.chinaGPS.gtmp.entity.CommandSendPOJO;
import com.chinaGPS.gtmp.service.IMaintainService;
import com.chinaGPS.gtmp.service.ISendCommandService;
import com.chinaGPS.gtmp.util.SpringContext;


/**
 * 
 * @author dy
 *
 */
public class PushThread extends Thread{
	private static Logger exceptionLogger = Logger.getLogger("EXCEPTION");   
	
	private boolean runningFlag = true;

	@Resource
	private IMaintainService maintainService;	

	

	public void run() {
		System.out.println("强保提醒线程启动!!!");
		maintainService = (IMaintainService)SpringContext.getContext().getBean("maintainServiceImpl");
		while (runningFlag) {
			try {
				List<HashMap<String, Object>> maintainPeriod = maintainService.getMaintainPeriod();
				List<HashMap<String, Object>> vehicleList = maintainService.getVehicleInfo();
				List<HashMap<String, String>> pushList= getPush(maintainPeriod, vehicleList);
				for(HashMap<String, String> entity:pushList){		
					ArrayList<String> ids= new ArrayList<String>();
					ids.add(entity.get("user_ids"));
					Map pump=new HashMap();
					pump.put("userId", entity.get("user_ids"));
					pump.put("maintainName", entity.get("MAINTAIN_NAME"));
					pump.put("vehicleDef", entity.get("vehicleDef"));
				    int  is=maintainService.countPushLogByUserId(pump);
					if(is<1){
						maintainService.insertPushLog(pump);
						Push.pushMsg2Multiple(entity.get("title"), entity.get("content"), ids);		
					}
				}
				sleep(3600000); // 间隔时间1小时				
			} catch (Exception ex) {
				ex.printStackTrace();
				try {
					sleep(300000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public List<HashMap<String,String>> getPush(List<HashMap<String, Object>> maintainPeriod,List<HashMap<String, Object>> vehicleList){
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		for(HashMap<String, Object> vehicle:vehicleList){
			BigDecimal total = (BigDecimal)vehicle.get("TOTAL");			
			for(HashMap<String, Object> period:maintainPeriod){
				BigDecimal trigger_time = (BigDecimal) period.get("TRIGGER_TIME");
				if(trigger_time.subtract(total).doubleValue()>=0 && trigger_time.subtract(total).doubleValue()<=100){
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("title", period.get("MAINTAIN_NAME")+"小时"+period.get("MAINTAIN_TYPE")+"提醒");
					map.put("content","您的"+ vehicle.get("VEHICLE_DEF")+"机械已经工作了"+vehicle.get("TOTAL")+"小时，请尽快进行"+period.get("MAINTAIN_NAME")+"小时保养");
					map.put("user_ids",vehicle.get("USER_ID").toString());
					map.put("MAINTAIN_NAME",period.get("MAINTAIN_NAME")+"");
					map.put("vehicleDef",vehicle.get("VEHICLE_DEF")+"");
					list.add(map);
				}
			}
		}
		return list;
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