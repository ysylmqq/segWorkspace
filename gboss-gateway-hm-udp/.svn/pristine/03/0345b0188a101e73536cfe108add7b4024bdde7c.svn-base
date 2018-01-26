package cc.chinagps.gateway.mq.export;

import java.io.IOException;

import javax.jms.JMSException;

import cc.chinagps.gateway.buff.GBossDataBuff.ECUConfig;
import cc.chinagps.gateway.unit.beans.Loginout;

public interface ExportMQInf {
	public boolean isEnabled();
	
	public void setEnabled(boolean enabled);
	
	public Object getDataLock();
	
	public int getDataCount();
	
	public long getThrowCount();
	
	public int getPosition();
	
	public long getLoop();
	
	public void init() throws JMSException, IOException;
	
	public void addCommandResponse(cc.chinagps.gateway.buff.CommandBuff.CommandResponse commandResponse);
	
	public void addSendCommandResult(cc.chinagps.gateway.buff.CommandBuff.SendCommandResult sendCommandResult);
	
	public void addShortMessage(String callLetter, String message);
	
	public void addGPS(cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo gpsInfo);
	
	public void addCommonAlarm(cc.chinagps.gateway.buff.GBossDataBuff.AlarmInfo alarmInfo);
	
	public boolean addSpecialAlarm(cc.chinagps.gateway.buff.GBossDataBuff.AlarmInfo alarmInfo);
	
	public void addOperateData(cc.chinagps.gateway.buff.GBossDataBuff.OperateData operateData);
	
	public void addOBDInfo(cc.chinagps.gateway.buff.GBossDataBuff.OBDInfo obdInfo);
	
	public void addTravelInfo(cc.chinagps.gateway.buff.GBossDataBuff.TravelInfo travelInfo);
	
	public void addFaultInfo(cc.chinagps.gateway.buff.GBossDataBuff.FaultInfo faultInfo);
	
	public void addUnitVersion(cc.chinagps.gateway.buff.GBossDataBuff.UnitVersion unitVersion);
	
	public void addUnitLoginOut(Loginout loginout);
	
	public void addECUConfig(ECUConfig ecuConfig);
	
	public void startWorker();
}