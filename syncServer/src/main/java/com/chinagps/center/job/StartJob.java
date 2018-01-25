package com.chinagps.center.job;

import java.util.List;

import org.apache.log4j.Logger;

import com.chinagps.center.pojo.SynUrl;

/**
 *
 */
public class StartJob extends AbstractJob {

	private static Logger logger = Logger.getLogger(StartJob.class);

	public void start() {
		try {
			String interval = getSystemConfig().getSyncInterval(); // 2
			int syncInverval = 5;
			if (interval != null)
				syncInverval = Integer.valueOf(interval);
			getDataService().initCache(); // 初始化缓存数据  终端类型和  车型 查询出来放到map当中
			List<SynUrl> synUrls = getDataService().listSynUrl();  // 需要去分公司同步数据的地址
			for (SynUrl synUrl : synUrls) {
				long subcoNo = synUrl.getSubco_no(); // 102 103  106  112  122
				new ThreadJob(subcoNo, syncInverval).run();// 一个分公司开启一个线程去同步数据
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("StartJob  error :" + e.getMessage());
		}
	}
}
