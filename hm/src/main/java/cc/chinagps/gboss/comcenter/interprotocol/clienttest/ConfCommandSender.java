package cc.chinagps.gboss.comcenter.interprotocol.clienttest;

import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cc.chinagps.lib.util.Config;

import com.gboss.comm.SystemConst;
import com.gboss.pojo.Upgrade;
import com.gboss.pojo.VehicleConf;
import com.gboss.util.CommandSendHelper;

/**
 * 发送查询配置指令
 */
public class ConfCommandSender {

	ExecutorService es = Executors.newSingleThreadExecutor();

	/**
	 * 版本和状态过滤
	 * 
	 * @param hm_vehicleconf_cache_all
	 * @return
	 */
	public ConcurrentHashMap<String, VehicleConf> filterMap() {
		ConcurrentHashMap<String, VehicleConf> hm_vehicleconf_cache = SystemConst.hm_vehicleconf_cache;
		ConcurrentHashMap<String, Upgrade> hm_call_letter_upgradeAll = SystemConst.hm_call_letter_upgradeAll;

		ConcurrentHashMap<String, VehicleConf> hm_vehicleconf_cache_filter = new ConcurrentHashMap<String, VehicleConf>();
		for (Iterator<String> it = hm_vehicleconf_cache.keySet().iterator(); it
				.hasNext();) {
			String key = it.next();
			VehicleConf vc = hm_vehicleconf_cache.get(key);
			if (vc.getFlag() != 4) {
				Upgrade up = hm_call_letter_upgradeAll.get(key);
				if (up != null) {
					String sendcommand_version = Config.getCmdProperties()
							.getProperty("sendcommand_version");
					if (up.getCur_ver() != null
							&& up.getCur_ver().compareTo(sendcommand_version) >= 0) {
						hm_vehicleconf_cache_filter.put(key, vc);
					}
				}
			}
		}
		if (hm_vehicleconf_cache_filter.size() > 0) {
			return hm_vehicleconf_cache_filter;
		}
		return null;
	}

	public static boolean is_overtime(Date stamp, int n) {
		Date now = new Date();
		if (stamp == null) {
			return true;
		}
		long between = now.getTime() - stamp.getTime();

		if (between >= n) {
			return true;
		}
		return false;
	}

	public void startTask() throws Exception {
		System.out.println("------------------------------查询配置指令下发线程启动------------------------------");
		this.es.execute(new Runnable() {
			//指令下发间隔
			int command_interval = Integer.parseInt(Config.getCmdProperties().getProperty("command_interval"));
			//每轮间隔
			int command_round = Integer.parseInt(Config.getCmdProperties().getProperty("command_round"));
			ConcurrentHashMap<String, Upgrade> hm_call_letter_upgradeAll = SystemConst.hm_call_letter_upgradeAll;
			public void run() {
				while (true) {
					ConcurrentHashMap<String, VehicleConf> hm_vehicleconf_cache = ConfCommandSender.this .filterMap();
					if ((hm_vehicleconf_cache != null) && (hm_vehicleconf_cache.size() > 0)) {
						Iterator<String> wait_keys = hm_vehicleconf_cache.keySet().iterator();
						while (wait_keys.hasNext()) {
							String key = (String) wait_keys.next();
							VehicleConf vc = (VehicleConf) hm_vehicleconf_cache.get(key);
							Upgrade up = (Upgrade) this.hm_call_letter_upgradeAll.get(key);
							if (up != null) {
								int command_total = Integer.parseInt(Config.getCmdProperties().getProperty("command_total"));
								int command_reinterval = Integer.parseInt(Config.getCmdProperties().getProperty("command_reinterval"));
								if ((vc.getCode1().longValue() == 0L)
										&& (vc.getSend_conf_count() < command_total)
										&& (ConfCommandSender.is_overtime( vc.getSend_queryconf(), command_reinterval)) 
										&& (up.getFlag().intValue() == 6)){
									CommandSendHelper.sendCommand(up, vc, SystemConst.QUERYCONF_CMD);
								}
							}
							try {
								Thread.sleep(command_interval);// 2秒发1条
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					try {
						Thread.sleep(command_round);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
}