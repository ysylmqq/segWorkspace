package com.gboss.comm.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gboss.comm.SystemConst;
import com.gboss.pojo.Equipcode;
import com.gboss.service.EquipcodeService;
import com.gboss.util.DateUtil;

public class ContextListener implements ServletContextListener {
	
	private ApplicationContext context;
	
	private final static String[] taskNames = {
			"accountsSyncStrategyServices",//帐号信息同步任务
			"bandInfoSyncStrategyService",//绑定信息同步任务
			"baseDataSyncStrategyService",//基础资料同步任务
			"comboSyncStrategyService",//套餐资料同步任务
			"commonCompanySyncStrategyService",//4s店资料信息同步任务
			"insuranceSyncStrategyService",//保险信息同步任务
			"modelsSyncStrategyService",//车型信息同步任务
			"seriesSyncStrategyService"//车系信息同步任务
		};

	static final Map<String,String> task_mapping  = new HashMap<String,String>();
	
	static{
		task_mapping.put(taskNames[0], "帐号信息同步任务");
		task_mapping.put(taskNames[1], "绑定信息同步任务");
		task_mapping.put(taskNames[2], "基础资料同步任务");
		task_mapping.put(taskNames[3], "套餐资料同步任务");
		task_mapping.put(taskNames[4], "4s店资料信息同步任务");
		task_mapping.put(taskNames[5], "保险信息同步任务");
		task_mapping.put(taskNames[6], "车型信息同步任务");
		task_mapping.put(taskNames[7], "车系信息同步任务");
	}
	
	private final static ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(1);
	
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println(DateUtil.formatNowTime() +  " 启动初始化开始!" );
		initEquipCodeCache();
//		initScheduled();
		System.out.println(DateUtil.formatNowTime() +  " 启动初始化结束!");
	}

	private void initEquipCodeCache() {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		EquipcodeService equipcodeService = context.getBean("equipcodeService", EquipcodeService.class);
		try {
			List<Equipcode>  equipcodes = equipcodeService.getAllEquidcode(null);
			if(equipcodes!=null && equipcodes.size() > 0){
				for(Equipcode equipcode:equipcodes){
					SystemConst.equipcode_Cache.put(equipcode.getEquip_code(), equipcode);
				}
			}
		} catch (Exception e) {
			System.out.println(DateUtil.formatNowTime() +  " 启动初始化出错!");
			e.printStackTrace();
		}
	}

	public void contextDestroyed(ServletContextEvent sce) {
		context = null;
		SystemConst.equipcode_Cache.clear();
//		closeScheduled();
	}
	
	public void initScheduled(){
		int count =0;
		for(String task_name:taskNames){
			Runnable sss = context.getBean(task_name, Runnable.class);
			 //启动后30秒执行同步程序，每5分钟执行一次程序
			SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(sss, (count+1) , 300L, TimeUnit.SECONDS);
			System.out.println(task_mapping.get(task_name)+"启动成功");
			count++;
		}
		System.out.println("当前运行的任务数：" + count);
	}
	
	public void closeScheduled(){
		try {
			SCHEDULED_EXECUTOR_SERVICE.shutdown();
			SCHEDULED_EXECUTOR_SERVICE.awaitTermination(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			if (!SCHEDULED_EXECUTOR_SERVICE.isTerminated()) {
		        System.err.println("关闭没有结束的任务");
		    }
			SCHEDULED_EXECUTOR_SERVICE.shutdownNow();
			System.err.println("##任务调度关闭##");
		}
	}

}
