package com.hm.bigdata.util.hbase;

import java.io.IOException;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class AlarmFilterQuartz {
	protected static final Logger LOGGER = LoggerFactory.getLogger(AlarmFilterQuartz.class);
	
	@Autowired
	private AlarmFilterHbase alarmFilterHbase;
	
	@Autowired
	private SessionFactory mysql1SessionFactory;
	
	public void filterHbase() throws InterruptedException{
		try {
			alarmFilterHbase.scanAlarmTable();
			Thread.sleep(1000*30);
			alarmFilterHbase.scanFaultTable();
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
	}
	
	public void insertMysql(){
		new Thread(new AlarmInsertMysqlThread(mysql1SessionFactory)).start();
		new Thread(new AlarmInsertMysqlThread(mysql1SessionFactory)).start();
		new Thread(new AlarmInsertMysqlThread(mysql1SessionFactory)).start();
		new Thread(new AlarmInsertMysqlThread(mysql1SessionFactory)).start();
		
		new Thread(new FaultInsertMysqlThread(mysql1SessionFactory)).start();
		new Thread(new FaultInsertMysqlThread(mysql1SessionFactory)).start();
		new Thread(new FaultInsertMysqlThread(mysql1SessionFactory)).start();
		new Thread(new FaultInsertMysqlThread(mysql1SessionFactory)).start();
	}
}