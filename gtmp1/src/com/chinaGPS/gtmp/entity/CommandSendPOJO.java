package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 下发指令内容
 * @author xk
 *
 */
@Component
@Scope("prototype")
public class CommandSendPOJO implements Serializable{
    /**
     * unitIds,userId,commandTypeId 必须项
     */
    private String unitIds;             //下发终端Id，以,分割
    private String simNos;             //下发终端sim卡号，以,分割
    private Integer commandTypeId;
    private Long userId;
    private String testFlag;
    
    private Integer commandSn;
    
    //无参数指令：20H 22H 35H D7H
    
    private Integer pTraceTimes;        //跟踪次数,单位为秒  =0 停止跟踪 21H
    private Integer pTraceInterval;     //跟踪时间间隔 单位为秒。最大为65535秒。 21H
    
    private String pType;               //指令类型 00-查询，01=设置 30H 31H 32H 33H 36H E1H
    
    private String pMessageNumber;       //短信中心号码，手机号码 30H
    
    
    
    private Integer pHeartbeatInterval;   //心跳间隔，心跳时间设置，单位为S。（默认值为2S，可设定）    34H
    private String pCanId;                 //CAN ID号。（默认值0x00AC，可设定）    34H 53H
    /*
     * 心跳内容   34H
     * 5A0000   使能防拆保护
     * A50000   禁止防拆保护
     * 005BC5   使能A级锁车
     * 00B500   解除A级锁
     * 00B55C   使能B级锁车
     * 0000C5   解除B级锁
     */
    private String pHeartbeatContent; 
    
    private Integer pReportInterval;    //定时报告间隔,单位为秒    36H
    
    private Integer pCanSendTime;   //can数据发送时长,单位为秒 =0 停止追踪     51H
    private Integer pCollectInterval;   //采集间隔,单位为ms。最大为255ms      51H
    
    private String pCanCommand; //can指令，1-8字节  53H
    
    private Integer pLockTimes; //锁车次数  E1H
    
    private String pFineId;     //精细数据ID，最多7位，如需上传ID为A0A2A5,则内容为A0A2A5FFFFFFFF  E3H 
    
    private String pSupperier;  //供应商标识 1个字节，2个字符
    private String pUpdateType; //升级类型 00-普通升级；01-强制升级
    private String pDeviceType; //设备类型 00-GPS终端；01-控制器；02-显示器
    private String pVersion;    //程序版本号 如0100
    private String pUnitType;   //终端型号 长度为5的字符串，少于5个字符补字节00
    private String pIp;         //ip地址，如192.110.100.66  31H 33H
    private Integer pPort;      //端口，实际的端口号最大为65535    31H 33H
    private String pAPN;        //APN，如：CMNET  32H 33H
    private Integer pLocalPort; //本地端口 0~65535
    private String sjType;//升级类型
    private String pSupperiers;  //供应商标识,以,分割
    private String pUnitTypes;   //终端型号 ,以,分割
    private Date plockTime;//预约锁车时间
    private int sendstatus;//预约锁车指令状态
    private long timinglockid;//预约锁车指令id
    
//    private String param;
    public Integer getCommandSn() {
        return commandSn;
    }
    public void setCommandSn(Integer commandSn) {
        this.commandSn = commandSn;
    }
    public String getUnitIds() {
        return unitIds;
    }
    public void setUnitIds(String unitIds) {
        this.unitIds = unitIds;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Integer getCommandTypeId() {
        return commandTypeId;
    }
    public void setCommandTypeId(Integer commandTypeId) {
        this.commandTypeId = commandTypeId;
    }
    public Integer getPTraceTimes() {
        return pTraceTimes;
    }
    public void setPTraceTimes(Integer traceTimes) {
        pTraceTimes = traceTimes;
    }
    public Integer getPTraceInterval() {
        return pTraceInterval;
    }
    public void setPTraceInterval(Integer traceInterval) {
        pTraceInterval = traceInterval;
    }
    public String getPType() {
        return pType;
    }
    public void setPType(String type) {
        pType = type;
    }
    public String getPMessageNumber() {
        return pMessageNumber;
    }
    public void setPMessageNumber(String messageNumber) {
        pMessageNumber = messageNumber;
    }
    public String getPIp() {
        return pIp;
    }
    public void setPIp(String ip) {
        pIp = ip;
    }
    public Integer getPPort() {
        return pPort;
    }
    public void setPPort(Integer port) {
        pPort = port;
    }
    public String getPAPN() {
        return pAPN;
    }
    public void setPAPN(String papn) {
        pAPN = papn;
    }
    public Integer getPHeartbeatInterval() {
        return pHeartbeatInterval;
    }
    public void setPHeartbeatInterval(Integer heartbeatInterval) {
        pHeartbeatInterval = heartbeatInterval;
    }
    public String getPCanId() {
        return pCanId;
    }
    public void setPCanId(String canId) {
        pCanId = canId;
    }
    public String getPHeartbeatContent() {
        return pHeartbeatContent;
    }
    public void setPHeartbeatContent(String heartbeatContent) {
        pHeartbeatContent = heartbeatContent;
    }
    public Integer getPReportInterval() {
        return pReportInterval;
    }
    public void setPReportInterval(Integer reportInterval) {
        pReportInterval = reportInterval;
    }
    public Integer getPCanSendTime() {
        return pCanSendTime;
    }
    public void setPCanSendTime(Integer canSendTime) {
        pCanSendTime = canSendTime;
    }
    public Integer getPCollectInterval() {
        return pCollectInterval;
    }
    public void setPCollectInterval(Integer collectInterval) {
        pCollectInterval = collectInterval;
    }
    public String getPCanCommand() {
        return pCanCommand;
    }
    public void setPCanCommand(String canCommand) {
        pCanCommand = canCommand;
    }
    public Integer getPLockTimes() {
        return pLockTimes;
    }
    public void setPLockTimes(Integer lockTimes) {
        pLockTimes = lockTimes;
    }
    public String getPFineId() {
        return pFineId;
    }
    public String getPSupperier() {
        return pSupperier;
    }
    public void setPSupperier(String supperier) {
        pSupperier = supperier;
    }
    public String getPUpdateType() {
        return pUpdateType;
    }
    public void setPUpdateType(String updateType) {
        pUpdateType = updateType;
    }
    public String getPDeviceType() {
        return pDeviceType;
    }
    public void setPDeviceType(String deviceType) {
        pDeviceType = deviceType;
    }
    public String getPVersion() {
        return pVersion;
    }
    public void setPVersion(String version) {
        pVersion = version;
    }
    public String getPUnitType() {
        return pUnitType;
    }
    public void setPUnitType(String unitType) {
        pUnitType = unitType;
    }
    public Integer getPLocalPort() {
        return pLocalPort;
    }
    public void setPLocalPort(Integer localPort) {
        pLocalPort = localPort;
    }
    public void setPFineId(String fineId) {
        pFineId = fineId;
    }
    public String getParam(){
        String returnStr = "";
        if(pTraceTimes!=null){
            returnStr += pTraceTimes+",";
        }
        if(pTraceInterval!=null){
            returnStr += pTraceInterval+",";
        }
        if(pType!=null){
            returnStr += pType+",";
        }
        if(pMessageNumber!=null){
            returnStr += pMessageNumber+",";
        }    
        if(pSupperier!=null){
            returnStr += pSupperier+",";
        }
        if(pUpdateType!=null){
            returnStr += pUpdateType+",";
        }
        if(pDeviceType!=null){
            returnStr += pDeviceType+",";
        }
        if(pVersion!=null){
            returnStr += pVersion+",";
        }
        if(pUnitType!=null){
            returnStr += pUnitType+",";
        }
        if(pIp!=null){
            returnStr += pIp+",";
        }
        if(pPort!=null){
            returnStr += pPort+",";
        } 
        if(pAPN!=null){
            returnStr += pAPN+",";
        } 
        if(pLocalPort!=null){
            returnStr += pLocalPort+",";
        } 
        if(pHeartbeatInterval!=null){
            returnStr += pHeartbeatInterval+",";
        } 
        if(pCanId!=null){
            returnStr += pCanId+",";
        } 
        if(pHeartbeatContent!=null){
            returnStr += pHeartbeatContent+",";
        } 
        if(pReportInterval!=null){
            returnStr += pReportInterval+",";
        } 
        if(pCanSendTime!=null){
            returnStr += pCanSendTime+",";
        } 
        if(pCollectInterval!=null){
            returnStr += pCollectInterval+",";
        } 
        if(pCanCommand!=null){
            returnStr += pCanCommand+",";
        } 
        if(pLockTimes!=null){
            returnStr += pLockTimes+",";
        } 
        if(pFineId!=null){
            returnStr += pFineId+",";
        } 
        return returnStr;
    }
    public String getPSupperiers() {
        return pSupperiers;
    }
    public void setPSupperiers(String supperiers) {
        pSupperiers = supperiers;
    }
    public String getPUnitTypes() {
        return pUnitTypes;
    }
    public void setPUnitTypes(String unitTypes) {
        pUnitTypes = unitTypes;
    }
	public String getTestFlag() {
		return testFlag;
	}
	public void setTestFlag(String testFlag) {
		this.testFlag = testFlag;
	}
	public String getSjType() {
		return sjType;
	}
	public void setSjType(String sjType) {
		this.sjType = sjType;
	}
	public String getSimNos() {
		return simNos;
	}
	public void setSimNos(String simNos) {
		this.simNos = simNos;
	}
	public Date getPlockTime() {
		return plockTime;
	}
	public void setPlockTime(Date plockTime) {
		this.plockTime = plockTime;
	}
	public int getSendstatus() {
		return sendstatus;
	}
	public void setSendstatus(int sendstatus) {
		this.sendstatus = sendstatus;
	}
	public long getTiminglockid() {
		return timinglockid;
	}
	public void setTiminglockid(long timinglockid) {
		this.timinglockid = timinglockid;
	}
    
}
