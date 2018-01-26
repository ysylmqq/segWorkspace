package com.chinaGPS.gtmp.business.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

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
import org.springframework.stereotype.Service;

import com.chinaGPS.gtmp.entity.CommandPOJO;
import com.chinaGPS.gtmp.entity.CommandSendPOJO;
import com.chinaGPS.gtmp.entity.CommandUpgradePOJO;
import com.chinaGPS.gtmp.mapper.CommandMapper;
import com.chinaGPS.gtmp.util.PropertyUtil;
import com.chinaGPS.gtmp.util.BytesToHexString;
/**
 * @Package:com.chinaGPS.gtmp.business.command
 * @ClassName:SendCustomerMessage
 * @Description:使用队列发送短信
 * @author:xk
 * @date:Apr 11, 2013 11:41:23 AM
 */
@Service
public class SendCustomerMessage {
    private static Logger exceptionLogger = Logger.getLogger("EXCEPTION"); 
    
    private static Connection connection;   
    private static Session session;   
    private static MessageProducer producer;   
    private static Destination destination;
    @Resource
    private CommandMapper commandMapper;
    
    public SendCustomerMessage(){  
        try {
            init();
        } catch (JMSException e) {
            e.printStackTrace();
            exceptionLogger.error("ActiveMQ连接初始化异常");
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
        String GSMcustomer = "";
        Properties property = PropertyUtil.getProperty("activemq.properties");
        if (property.get("ActiveMQ") != null && !"".equals(property.get("ActiveMQ"))) {
            url = (String) property.get("ActiveMQ");
        }
        if (property.get("GSMcustomer") != null && !"".equals(property.get("GSMcustomer"))) {
            GSMcustomer = (String) property.get("GSMcustomer");
        }
        if("".equals(url) || "".equals(GSMcustomer)){
            exceptionLogger.error("指令下发：ActiveMQ连接参数有误，连接创建失败！");
            return;
        }
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url); //创建连接使用的工厂类JMS ConnectionFactory
        connectionFactory.setUseAsyncSend(false);   //异步发送数据
        connection = (Connection) connectionFactory.createConnection(); //使用管理对象JMS ConnectionFactory建立连接Connection 
        connection.start(); 
        session = (Session) connection.createSession(false,Session.AUTO_ACKNOWLEDGE); //使用连接Connection 建立会话Session
        destination = session.createQueue(GSMcustomer); //目的地
        producer = session.createProducer(destination); //使用会话Session和管理对象Destination创建消息生产者MessageSender
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);// 设置非持久化传输模式
    }

    /**
     * @Title:sendMessage
     * @Description:发送客户短信
     * @param unitId
     * @param messageInfo
     * @throws JMSException
     * @throws
     */
    public void sendMessage(String unitId,String messageInfo) throws JMSException{
        if(session==null || producer==null){
            init();
        }

        BytesMessage message = session.createBytesMessage(); //消息
        message.writeBytes(BytesToHexString.hexString2Bytes(BytesToHexString.string2HexString(unitId)+"7e"+messageInfo));
        producer.send(message); //发送消息
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

