package com.chinaGPS.gtmp.business.command;

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
import org.springframework.stereotype.Service;

import com.chinaGPS.gtmp.business.memcache.GatewayBack;
import com.chinaGPS.gtmp.mapper.CommandMapper;
import com.chinaGPS.gtmp.util.BytesToHexString;
import com.chinaGPS.gtmp.util.PropertyUtil;
/**
 * @Package:com.chinaGPS.gtmp.business.command
 * @ClassName:SendCommandMessage
 * @Description:使用队列发送短信
 * @author:xk
 * @date:Apr 11, 2013 11:41:23 AM
 */
@Service
public class SendCommandMessage {
    private static Logger exceptionLogger = Logger.getLogger("EXCEPTION"); 
    
    private static Connection connection;   
    private static Session session;   
    private static MessageProducer producer;   
    private static Destination destination;
    @Resource
    private CommandMapper commandMapper;
    
    public SendCommandMessage(){  
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
        String GSMcommand = "";
        Properties property = PropertyUtil.getProperty("activemq.properties");
        if (property.get("ActiveMQ") != null && !"".equals(property.get("ActiveMQ"))) {
            url = (String) property.get("ActiveMQ");
        }
        if (property.get("GSMcommand") != null && !"".equals(property.get("GSMcommand"))) {
            GSMcommand = (String) property.get("GSMcommand");
        }
        if("".equals(url) || "".equals(GSMcommand)){
            exceptionLogger.error("指令下发：ActiveMQ连接参数有误，连接创建失败！");
            return;
        }
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url); //创建连接使用的工厂类JMS ConnectionFactory
        connectionFactory.setUseAsyncSend(false);   //异步发送数据
        connection = (Connection) connectionFactory.createConnection(); //使用管理对象JMS ConnectionFactory建立连接Connection 
        connection.start(); 
        session = (Session) connection.createSession(false,Session.AUTO_ACKNOWLEDGE); //使用连接Connection 建立会话Session
        destination = session.createQueue(GSMcommand); //目的地
        producer = session.createProducer(destination); //使用会话Session和管理对象Destination创建消息生产者MessageSender
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);// 设置非持久化传输模式
    }
    
    /**
     * @Title:sendCommand
     * @Description:将本地缓存的GPRS发送不成功的指令通过GSM发送
     * @param map
     * @throws JMSException
     * @throws
     */
    public void sendCommand(Map<Integer,String> map) throws Exception{
        if(session==null || producer==null){
            init();
        }
        for (Map.Entry<Integer,String> command : map.entrySet()) { 
            BytesMessage message = session.createBytesMessage(); //消息
            message.writeBytes(BytesToHexString.hexString2Bytes(command.getValue()));
            //303833323435343137 7e 303833323435343137 7B 20 000542 0000 67 7D
            String str = command.getValue();
            //str=str.substring(0,str.lastIndexOf("7e"));
            
            //取流水号  000542
            str = str.substring(str.lastIndexOf("7B"));
            str = str.substring(str.lastIndexOf("7B"));
            str = str.substring(4, 10);
        	int a =Integer.parseInt(str, 16);
            GatewayBack back = new GatewayBack();
            back.setPathFlag(1);  // 0：GPS   1：GSM
            back.setCommandSn(a+"");
			commandMapper.updateTab(back);
            producer.send(message); //发送消息
        } 
    }
  
    public void close() throws JMSException{
        producer.close();
        session.close();
        connection.close(); //关闭连接
    }
 
}

