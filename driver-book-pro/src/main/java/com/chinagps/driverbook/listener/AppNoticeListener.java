package com.chinagps.driverbook.listener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQBytesMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.chinagps.driverbook.dao.UnitMapper;
import com.chinagps.driverbook.pojo.AlarmControl;
import com.chinagps.driverbook.protobuf.ComCenterDataBuff.DeliverAppNotice;
import com.chinagps.driverbook.service.IAlarmControlService;
import com.chinagps.driverbook.service.IPushService;
import com.chinagps.driverbook.service.IUnitService;
import com.chinagps.driverbook.service.impl.UnitServiceImpl;
import com.chinagps.driverbook.util.PropertyUtil;

@Service
@Scope("prototype")
public class AppNoticeListener extends TimerTask implements MessageListener  {
	private static Logger logger = Logger.getLogger(AppNoticeListener.class);
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static List<String> callLetterList = new ArrayList<String>();
	public static List<AlarmControl> alarmControlList = new ArrayList<AlarmControl>();
	
	@Autowired
	@Qualifier("pushService")
	private IPushService pushService;
	
	@Autowired
	public IUnitService iUnitService;
	
	@Autowired
	private IAlarmControlService iAlarmService;
	
	/**
	 * 
	 * @param subcoNo
	 * @param callLetter
	 * @param status
	 * @return true 拦截该警情(不发送)  false 不拦截该警情(发送)
	 */
	public boolean isInterceptor(String subcoNo,String callLetter,int status_id){
		List<String> list = AppNoticeListener.callLetterList;
		if(list == null || list.isEmpty()){
			AppNoticeListener.callLetterList = iUnitService.getUnitCallLetterSubcoNo(subcoNo);
			AppNoticeListener.alarmControlList = iAlarmService.getAlarmControlList();
			list = AppNoticeListener.callLetterList;
		}
		if(list != null && !list.isEmpty() && list.size() != 0 ){
			if(list.contains(callLetter)){
				//该手机号码是 海马的用户  ,判断 这个警情是不是要拦截的
				List<AlarmControl> alarmControlList = AppNoticeListener.alarmControlList;
				for (Iterator iterator = alarmControlList.iterator(); iterator
						.hasNext();) {
					AlarmControl alarmControl = (AlarmControl) iterator.next();
					Integer statsId = alarmControl.getStatusId().intValue();
					if(statsId == status_id){
						boolean isOpen = alarmControl.getIsOpen().booleanValue(); // true不发送  
						System.err.println("0");
						return isOpen;
					}
				}
				System.err.println("1");
				return false;
			}else{
				//不是海马用户 
				System.err.println("2");
				return false; 
			}
		}else{
			System.err.println("3");
			return false;
		}
	}
	
	@Override
	public void onMessage(Message msg) {
		//监听推送中心ActiveMQ，有消息时发起推送消息
		ActiveMQBytesMessage appNoticeMsg = (ActiveMQBytesMessage) msg;
		int len = 0;
		try {
			len = (int) appNoticeMsg.getBodyLength();
			byte[] data = new byte[len];
			appNoticeMsg.readBytes(data);
			//appNoticeMsg  推送的消息 从activemq当中获取的
			
			DeliverAppNotice deliverAppNotice = DeliverAppNotice.parseFrom(data);
			
			int status = deliverAppNotice.getNoticeinfo().getAlarmstatus();
			String call_letter = deliverAppNotice.getNoticeinfo().getCallLetter();
			//海马201 
			boolean isInter = this.isInterceptor("201", call_letter, status);
			if( isInter ){
				return ;
			}
			
			String 	test_str = PropertyUtil.getPropertyValue("comm.properties", "test");
			int 	test 	 = 0;
			String test_call_letter = null;
			try{
				test  				= Integer.parseInt(test_str);
				test_call_letter	= PropertyUtil.getPropertyValue("comm.properties", "call_letter");
			}catch(Exception e){
				System.out.println( "["+SDF.format(new Date()) +"]-配置文件中是否测试的标记test/call_letter属性-获取失败。");
			}
			
			if(test==1){
				//String call_letter = deliverAppNotice.getNoticeinfo().getCallLetter();
				if(!call_letter.equals(test_call_letter)){
					return;
				}
			}
			logger.info("##离线推送开始##");
			pushService.notifyCenter(deliverAppNotice.getNoticeinfo());
			logger.info("##离线推送结束##");
		} catch (Exception e) {
			logger.error("##离线推送异常##" + e.getMessage());
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void run() {
		AppNoticeListener.callLetterList = iUnitService.getUnitCallLetterSubcoNo("201");
		AppNoticeListener.alarmControlList = iAlarmService.getAlarmControlList();
	}
}
