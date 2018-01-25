package com.hm.bigdata.util.hbase;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.hm.bigdata.entity.po.Alarm;
import com.hm.bigdata.entity.po.Fault;


public class FaultInsertMysqlThread implements Runnable{

	private SessionFactory mysql1SessionFactory;
	
	public FaultInsertMysqlThread(SessionFactory mysql1SessionFactory) {
		this.mysql1SessionFactory = mysql1SessionFactory;
	}
	
	@Override
	public void run() {
    	int count = 0;
		while(true){
			Fault fault = AlarmFilterHbase.faultQueue.poll();
    		if (fault == null) {
    			try {  // 队列为空时等待30s
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    			continue;
    		}
    		count++;
    		Session session = mysql1SessionFactory.openSession();
    		Transaction  tx = session.beginTransaction();
    		session.save(fault);
    		if (count%100 == 0) {
    			session.flush();
    			session.clear();
    		}
    		session.flush();
			session.clear();
			tx.commit();
			session.close();
		}
	}
}