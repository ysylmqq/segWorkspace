package com.chinagps.driverbook.service.impl;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.chinagps.driverbook.dao.AlarmControlMapper;
import com.chinagps.driverbook.listener.AppNoticeListener;
import com.chinagps.driverbook.pojo.AlarmControl;
import com.chinagps.driverbook.pojo.AlarmControlExample;
import com.chinagps.driverbook.pojo.AlarmControlExample.Criteria;
import com.chinagps.driverbook.service.IAlarmControlService;

@Service
@Scope("prototype")
public class AlarmControlServiceImpl implements IAlarmControlService {
	
	@Autowired
	private AlarmControlMapper alarmControlMapper;

	@Override
	public AlarmControl getAlarmControlByStatusId(Integer status_id) {
		AlarmControlExample  example = new AlarmControlExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusIdEqualTo(status_id);
		List<AlarmControl>  list = alarmControlMapper.selectByExample(example);
		if( list != null && !list.isEmpty() && list.size() != 0 ){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public void updateAlarmControl(AlarmControl alarmControl) {
		alarmControlMapper.updateAlarmControl(alarmControl);
	}

	@Override
	public List<AlarmControl> getAlarmControlList() {
		return alarmControlMapper.selectAll();
	}

	@Override
	public void updateAlarmControlIsOpen(String statusIdList, boolean isOpen) {
		//在开启或关闭的时候 要维护 内存当中的数据
		String [] statusList = statusIdList.split(",");
		List<String> list = Arrays.asList(statusList);
		if(isOpen){
			//开启 
			alarmControlMapper.updateAlarmControlIsOpenTrue(list);
		}else{
			alarmControlMapper.updateAlarmControlIsOpenFalse(list);
		}
		//更新完警情控制表以后 ，重新更新内存当中的数据
		AppNoticeListener.alarmControlList = alarmControlMapper.selectAll();
	}
	
	public static void main (String args[]){
		ApplicationContext ap = new ClassPathXmlApplicationContext("classpath:/config/spring/applicationContext-dao.xml");
		IAlarmControlService  app = ap.getBean(AlarmControlServiceImpl.class);
		/*app.updateAlarmControlIsOpen("4,3,5,6,8,7",false);
	    List<AlarmControl> alarmControlList = AppNoticeListener.alarmControlList;
		System.err.println("之后");
		for (Iterator iterator = alarmControlList.iterator(); iterator
				.hasNext();) {
			AlarmControl alarmControl = (AlarmControl) iterator.next();
			System.err.println("alarmControlMark "+alarmControl.getRemark()+" isOpen "+alarmControl.getIsOpen());
		}*/
		
		AppNoticeListener  appNoticeListener = ap.getBean(AppNoticeListener.class);
		boolean res = appNoticeListener.isInterceptor("201","18117504171", 5);
		System.err.println("result "+res);

	}
}
