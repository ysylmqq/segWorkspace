package com.chinagps.center.job;

import java.util.Timer;
import java.util.TimerTask;

public class ThreadJob extends AbstractJob {
	private long subcoNo;
	private int interval;

	public ThreadJob(long subcoNo, int interval) {
		this.subcoNo = subcoNo;
		this.interval = interval;
	}

	public void run() {
		// TODO Auto-generated method stub
		Timer timer = new Timer();
        
		// 一个背景线程
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				synData(subcoNo);
			}
		}, 0, interval * 1000);
	}
}
