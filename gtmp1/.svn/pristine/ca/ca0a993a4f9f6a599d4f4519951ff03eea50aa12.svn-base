package com.chinaGPS.gtmp.business.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.chinaGPS.gtmp.entity.CommandPOJO;
import com.chinaGPS.gtmp.entity.CommandSendPOJO;
import com.chinaGPS.gtmp.entity.CommandUpgradePOJO;
import com.chinaGPS.gtmp.mapper.CommandMapper;
import com.chinaGPS.gtmp.util.BytesToHexString;
import com.chinaGPS.gtmp.util.PropertyUtil;
/**
 * @Package:com.chinaGPS.gtmp.business.command
 * @ClassName:SendCommand
 * @Description:将指令按协议封装，使用topic方式发送到网关，网关使用GPRS的方式下发指令，如果成功，返回成功标识
 * @author:xk
 * @date:Apr 11, 2013 11:41:23 AM
 */
@Service
public class SendCommand {
    private static Logger exceptionLogger = Logger.getLogger("EXCEPTION"); 
    private  org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    private static Connection connection;   
    private static Session session;   
    private static MessageProducer producer;   
    private static Destination destination;
    @Resource
    private CommandMapper commandMapper;
    
    public SendCommand(){  
        try {
            init();
        } catch (JMSException e) {
            e.printStackTrace();
            exceptionLogger.error("ActiveMQ连接初始化异常");
            logger.error(e.getMessage(),e);
        }
    }
    /**
     * @Title:init
     * @Description:初始化ActiveMQ连接
     * @throws JMSException
     * @throws
     */
    private void init() throws JMSException{
        String url = "";
        String commandTopic = "";
        Properties property = PropertyUtil.getProperty("activemq.properties");
        if (property.get("ActiveMQ") != null && !"".equals(property.get("ActiveMQ"))) {
            url = (String) property.get("ActiveMQ");
        }
        if (property.get("commandTopic") != null && !"".equals(property.get("commandTopic"))) {
            commandTopic = (String) property.get("commandTopic");
        }
        if("".equals(url) || "".equals(commandTopic)){
            exceptionLogger.error("指令下发：ActiveMQ连接参数有误，连接创建失败！");
            return;
        }
        //连ActiveMQ地址
	    if (logger.isDebugEnabled()) {
    		logger.debug("连ActiveMQ地址：" + url);
    	}
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url); //创建连接使用的工厂类JMS ConnectionFactory
        connectionFactory.setUseAsyncSend(false);   //异步发送数据
        connection = (Connection) connectionFactory.createConnection(); //使用管理对象JMS ConnectionFactory建立连接Connection 
        connection.start(); 
        session = (Session) connection.createSession(false,Session.AUTO_ACKNOWLEDGE); //使用连接Connection 建立会话Session
        destination = session.createTopic(commandTopic); //目的地
        producer = session.createProducer(destination); //使用会话Session和管理对象Destination创建消息生产者MessageSender
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);// 设置非持久化传输模式
    }
    /**
     * @Title:sendCommand
     * @Description:发送指令，走GPRS
     * @param commandSend
     * @return 指令SN列表
     * @throws JMSException
     * @throws
     */
    public Map<Integer,String> sendCommand(CommandSendPOJO commandSend) throws Exception{
        if(session==null || producer==null){
            init();
        }
        Map<Integer,String> map = new HashMap<Integer,String>();
        String[] strs = commandSend.getUnitIds().split(",");
        
 
        for(int i=0;i<strs.length;i++){
            if(!"".equals(strs[i])){
                CommandPOJO command = new CommandPOJO();
                command.setCommandTypeId(commandSend.getCommandTypeId());
                command.setParam(commandSend.getParam());
                command.setUnitId(strs[i]);
                command.setPathFlag(0);
                command.setUserId(commandSend.getUserId());
                if(!"1".equals(commandSend.getTestFlag())){
                	Long num = (long) commandMapper.add(command);
                }
                //Integer commandSn = commandMapper.getCommandSn(command.getCommandId());
                if("1".equals(commandSend.getTestFlag()) ){
                	command = new CommandPOJO();
                	//command.setCommandSn(commandSn);
                	command.setUnitId(strs[i]);
                	command.setUserId(commandSend.getUserId());
                	command.setPathFlag(0);
                	command.setCommandTypeId(commandSend.getCommandTypeId());
                	if(commandSend.getPHeartbeatContent()!=null && !"".equals(commandSend.getPHeartbeatContent())){
                		command.setpHeartbeatContent(commandSend.getPHeartbeatContent());
                	}
                	commandMapper.addTestCommand(command);
                }
                String commandStr = PackCommand.pack(strs[i],command.getCommandSn(),commandSend);
                if(commandStr==null){
                    exceptionLogger.error("指令封装失败");
                    break;
                }
                String commandPack = BytesToHexString.string2HexString(strs[i])+"7B"+commandStr+xorHexString(commandStr)+"7D";
                BytesMessage message = session.createBytesMessage(); //消息
                message.writeBytes(BytesToHexString.hexString2Bytes(commandPack));
                producer.send(message); //发送消息
                System.out.println("commandPack="+commandPack);
                map.put(command.getCommandSn(),BytesToHexString.string2HexString(command.getUnitId())+"7e"+commandPack + "-" + System.currentTimeMillis());
                //map.put(command.getCommandSn(),BytesToHexString.string2HexString(command.getSimNos())+BytesToHexString.string2HexString(command.getUnitId())+"7e"+commandPack);
            }
        }
        return map;
    }
    
    public Map<Integer,String> sendUpgradeCommand(CommandSendPOJO commandSend) throws Exception{
        Map<Integer,String> map = new HashMap<Integer,String>();
        if(session==null || producer==null){
            init();
        }
        String[] strs = commandSend.getUnitIds().split(",");
        String supperiers = commandSend.getPSupperiers();
        String[] strSupperiers = null;
        if(supperiers!=null){
            String[] temp = supperiers.split(","); 
            if(temp.length==strs.length){
                strSupperiers = temp;
            }
        }
        
        String unitTypes = commandSend.getPUnitTypes();
        String[] strUnitTypes = null;
        if(unitTypes!=null){
            String[] temp = unitTypes.split(","); 
            if(temp.length==strs.length){
                strUnitTypes = temp;
            }
        }                
 
        for(int i=0;i<strs.length;i++){
            if(!"".equals(strs[i])){
                CommandUpgradePOJO commandUpgrade = new CommandUpgradePOJO(); 
                if(strSupperiers!=null && strUnitTypes!=null){
                    commandUpgrade.setSupperierSn(strSupperiers[i]);
                    commandUpgrade.setUnitTypeSn(strUnitTypes[i]);
                    commandSend.setPSupperier(strSupperiers[i]);
                   
                }else{
                    continue;
                }
                commandSend.setPUnitType(commandSend.getPUnitType());
                commandUpgrade.setUnitId(strs[i]);
                commandUpgrade.setUserId(commandSend.getUserId());
                commandUpgrade.setUpdateType(commandSend.getPUpdateType());
                commandUpgrade.setDeviceType(commandSend.getPDeviceType());
                commandUpgrade.setSoftwareVersion(commandSend.getPVersion());
                commandUpgrade.setIp(commandSend.getPIp());
                commandUpgrade.setServerPort(commandSend.getPPort());
                commandUpgrade.setAPN(commandSend.getPAPN());
                commandUpgrade.setLocalPort(commandSend.getPLocalPort());
                commandMapper.addUpgrade(commandUpgrade);
                String commandStr = PackCommand.pack(strs[i],commandUpgrade.getCommandId().intValue(),commandSend);
                if(commandStr==null){
                    exceptionLogger.error("指令封装失败");
                    break;
                }
                String commandPack = BytesToHexString.string2HexString(strs[i])+"7B"+commandStr+xorHexString(commandStr)+"7D";
                BytesMessage message = session.createBytesMessage(); //消息
                message.writeBytes(BytesToHexString.hexString2Bytes(commandPack));
                producer.send(message); //发送消息
                map.put(commandUpgrade.getCommandId().intValue(),BytesToHexString.string2HexString(commandUpgrade.getUnitId())+"7e"+commandPack + "-" + System.currentTimeMillis());
                //map.put(commandUpgrade.getCommandId().intValue(),BytesToHexString.string2HexString(commandUpgrade.getSimNos())+BytesToHexString.string2HexString(commandUpgrade.getUnitId())+"7e"+commandPack);
            }
        }
        return map;
    }
    /**
     * @Title:xorHexString
     * @Description:异或16进制字符串
     * @param hexString
     * @return 16进制异或结果
     * @throws
     */
    private static String xorHexString(String hexString){
        int a = 0;
        for(int i=0;i<hexString.length();i=i+2){
            a^=Integer.valueOf(hexString.substring(i, i+2),16);
        }
        String str2 = Integer.toHexString(a);
        if(str2.length()==1){
        	str2 = "0"+str2;
        }
        return str2;
    }
    
    public void close() throws JMSException{
        producer.close();
        session.close();
        connection.close(); //关闭连接
    }

    public CommandMapper getCommandMapper() {
        return commandMapper;
    }

    public void setCommandMapper(CommandMapper commandMapper) {
        this.commandMapper = commandMapper;
    }
    
}

