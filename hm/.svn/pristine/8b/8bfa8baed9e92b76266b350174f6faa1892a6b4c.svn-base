/*
********************************************************************************************
Discription:  性能测试时，指令发送和接收处理用单线程处理时，性能都不高，
 			    更新为多线程, 本线程是发送命令线程
			  
Written By:   ZXZ
Date:         2015-04-02
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.interprotocol.clienttest;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cc.chinagps.lib.util.Config;

import com.gboss.comm.SystemConst;
import com.gboss.pojo.FlowCtrolCmd;
import com.gboss.pojo.FlowCtrolCmdHistory;
import com.gboss.pojo.Upgrade;
import com.gboss.pojo.VehicleConf;
import com.gboss.service.FCCmdManageService;
import com.gboss.service.UpgradeService;
import com.gboss.util.CommandSendHelper;
import com.gboss.util.DateUtil;
import com.gboss.util.SpringContext;
import com.gboss.util.StringUtils;

/**
 * 
 * @ClassName: FCClientCommandThread
 * @Description:流量控制系统的查车指令下发线程;
 * @author:cj
 * @date 2017年7月20日 上午8:46:55
 *
 */
public class FCClientCommandThread {
	ExecutorService es = Executors.newSingleThreadExecutor();
	private static Logger logger = LoggerFactory.getLogger(FCClientCommandThread.class);

	public void startTask() throws Exception {
		es.execute(new Runnable() {
			@Override
			public void run() {
				System.out.println("------------------------------查车指令下发线程启动------------------------------");
				int i = 1;
				while (true) {
					// 查车指令下发总次数
					int command_total = Integer.parseInt(Config.getCmdProperties().getProperty("command_total"));
					// 同一台车每次下发指令后无应答，重发间隔
					int command_reinterval = Integer
							.parseInt(Config.getCmdProperties().getProperty("command_reinterval"));
					// 指令下发间隔
					int command_interval = Integer.parseInt(Config.getCmdProperties().getProperty("command_interval"));
					// 每轮间隔
					int command_round = Integer.parseInt(Config.getCmdProperties().getProperty("command_round"));

					FCCmdManageService fcCmdService = (FCCmdManageService) SpringContext.getBean("fcCmdManageService");

					System.out.println("**************1.下发开启导航主机查车指令*************************");
					sendSearchCarCmd(SystemConst.hm_navi_host_open_cmd, SystemConst.hm_navi_host_cancle_open_cmd,
							fcCmdService, command_total, command_reinterval, command_interval, command_round);
					System.out.println("**************2.下发关闭导航主机查车指令*************************");
					sendSearchCarCmd(SystemConst.hm_navi_host_close_cmd, SystemConst.hm_navi_host_cancle_close_cmd,
							fcCmdService, command_total, command_reinterval, command_interval, command_round);
					System.out.println("**************3.下发开启省流量查车指令*************************");
					sendSearchCarCmd(SystemConst.hm_save_flow_open_cmd, SystemConst.hm_save_flow_cancle_open_cmd,
							fcCmdService, command_total, command_reinterval, command_interval, command_round);
					System.out.println("**************4.下发关闭省流量查车指令*************************");
					sendSearchCarCmd(SystemConst.hm_save_flow_close_cmd, SystemConst.hm_save_flow_cancle_close_cmd,
							fcCmdService, command_total, command_reinterval, command_interval, command_round);
					///////////////////// 测试
					// ArrayList<String> callletterList = new
					// ArrayList<String>();
					// callletterList.add("14912383858");
					// SystemConst.clienthandler.SendCommand(callletterList,
					// 0x01, null, true);
					// String remark = "#########"+DateUtil.formatNowTime() +
					// "测试：开启省流量模式下发查车指令.";
					// System.out.println(remark);

					try {
						Thread.sleep(command_round);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	/**
	 * @Description:发送查车指令;
	 * @Params:
	 * @Return:void
	 * @Author:cj
	 * @Date:2017年7月21日 上午9:29:19
	 */
	public void sendSearchCarCmd(ConcurrentHashMap<String, FlowCtrolCmd> sendMap,
			Hashtable<String, FlowCtrolCmd> cancleMap, FCCmdManageService fcCmdService, int command_total,
			int command_reinterval, int command_interval, int command_round) {
		ConcurrentHashMap<String, FlowCtrolCmd> searchSendMap = sendMap;
		Hashtable<String, FlowCtrolCmd> searchCancleMap = cancleMap;

		Iterator<String> wait_keys = searchSendMap.keySet().iterator();
		while (wait_keys.hasNext()) {
			String key = (String) wait_keys.next();
			FlowCtrolCmd fcCmdSend = searchSendMap.get(key);
			FlowCtrolCmd fcCmdCancel = searchCancleMap.get(key);

			if (fcCmdCancel != null) {
				searchSendMap.remove(key);
				searchCancleMap.remove(key);
				System.out.println(DateUtil.formatNowTime() + " 呼号：" + fcCmdSend.getCallLetter() + " 取消下发指令");
				continue;
			}

			if (StringUtils.isNotNullOrEmpty(fcCmdSend)) {
				// 导航主机
				/*
				 * if
				 * (StringUtils.isNotNullOrEmpty(fcCmdSend.getCurrNaviStatus())
				 * &&
				 * StringUtils.isNotNullOrEmpty(fcCmdSend.getTosetNaviStatus())
				 * && (fcCmdSend.getCurrNaviStatus().intValue() ==
				 * fcCmdSend.getTosetNaviStatus().intValue())) { //
				 * 当前指令状态和要设置的指令状态一致，直接更新状态，设置为指令下发成功成功 fcCmdSend.setFlag(7);
				 * fcCmdSend.setStamp(new Date());
				 * 
				 * String remark = DateUtil.formatNowTime() + " 呼号：" +
				 * fcCmdSend.getCallLetter() +
				 * " 导航主机指令当前状态和要设置的状态一致，直接更新状态，设置为下发成功！";
				 * fcCmdSend.setRemark(remark); fcCmdService.update(fcCmdSend);
				 * searchSendMap.remove(fcCmdSend.getCallLetter()); //
				 * 一致的话就不用更新t_ba_sim表了 logger.info(remark); // 更新里历史表
				 * FlowCtrolCmdHistory flowCtrolCmdHistory =
				 * fcCmdService.fcSendCmdTransHistory(fcCmdSend); if
				 * (flowCtrolCmdHistory != null) {
				 * fcCmdService.update(flowCtrolCmdHistory); } } else if
				 * (StringUtils.isNotNullOrEmpty(fcCmdSend.getCurrFlowctrlStatus
				 * ()) &&
				 * StringUtils.isNotNullOrEmpty(fcCmdSend.getTosetFlowctrlStatus
				 * ()) && (fcCmdSend .getCurrFlowctrlStatus().intValue() ==
				 * fcCmdSend.getTosetFlowctrlStatus().intValue())) {// 省流量 //
				 * 当前指令状态和要设置的指令状态一致，直接更新状态，设置为指令下发成功成功 fcCmdSend.setFlag(7);
				 * fcCmdSend.setStamp(new Date());
				 * 
				 * String remark = DateUtil.formatNowTime() + " 呼号：" +
				 * fcCmdSend.getCallLetter() +
				 * " 省流量指令当前状态和要设置的状态一致，直接更新状态，设置为下发成功！";
				 * fcCmdSend.setRemark(remark); fcCmdService.update(fcCmdSend);
				 * searchSendMap.remove(fcCmdSend.getCallLetter()); //
				 * 一致的话就不用更新t_ba_sim表了 logger.info(remark); // 更新里历史表
				 * FlowCtrolCmdHistory flowCtrolCmdHistory =
				 * fcCmdService.fcSendCmdTransHistory(fcCmdSend); if
				 * (flowCtrolCmdHistory != null) {
				 * fcCmdService.update(flowCtrolCmdHistory); } } else {
				 */
				if ((fcCmdSend.getFlag() == 2 || fcCmdSend.getFlag() == 3)) {
					if (fcCmdSend.getSendTotal() <= command_total && (StringUtils.isNullOrEmpty(fcCmdSend.getSendSearchDate())
							|| is_overtime(fcCmdSend.getSendSearchDate(), command_reinterval))) {

						ArrayList<String> callletterList = new ArrayList<String>();
						callletterList.add(fcCmdSend.getCallLetter());

						// 发送查车指令
						String cmdsn = SystemConst.clienthandler.SendCommand(callletterList, 0x01, null, true);
						if (!cmdsn.equals("")) {
							fcCmdSend.setCmdSn(cmdsn);
							fcCmdSend.setSendSearchDate(new Date());
							fcCmdSend.setSendTotal(fcCmdSend.getSendTotal() + 1);
							searchSendMap.put(key, fcCmdSend);
							logger.info(DateUtil.formatNowTime() + " 呼号：" + fcCmdSend.getCallLetter() + " 第"
									+ (fcCmdSend.getSendTotal()) + "次查车指令下发， sn=" + cmdsn);
							System.out.println(DateUtil.formatNowTime() + " 呼号：" + fcCmdSend.getCallLetter() + " 第"
									+ (fcCmdSend.getSendTotal()) + "次查车指令下发， sn=" + cmdsn);
						}

						try {
							Thread.sleep(command_interval);// 2秒发1条
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					// 如果下发查车次数大于最大次数，车辆还是不上线那么设置为指令执行失败;
					if (fcCmdSend.getSendTotal() > command_total) {
						fcCmdSend.setStamp(new Date());
						fcCmdSend.setRemark("发送查车指令数超过最大次数：" + command_total + ",设置指令取消");
						fcCmdSend.setFlag(6);
						
						fcCmdService.update(fcCmdSend);
						// 更新历史表
						FlowCtrolCmdHistory flowCtrolCmdHistory = fcCmdService.fcSendCmdTransHistory(fcCmdSend);
						if (flowCtrolCmdHistory != null) {
							fcCmdService.update(flowCtrolCmdHistory);
						}
						
						searchSendMap.remove(fcCmdSend.getCallLetter());
						searchCancleMap.put(fcCmdSend.getCallLetter(), fcCmdSend);
					}
				}
			}
		}
	}

	/**
	 * @Description:发送查车指令;
	 * @Params:
	 * @Return:void
	 * @Author:cj
	 * @Date:2017年7月21日 上午9:29:19
	 */
	public void sendSearchCarCmd1(ConcurrentHashMap<String, FlowCtrolCmd> sendMap,
			Hashtable<String, FlowCtrolCmd> cancleMap, FCCmdManageService fcCmdService, int command_total,
			int command_reinterval, int command_interval, int command_round) {
		ConcurrentHashMap<String, FlowCtrolCmd> searchSendMap = sendMap;
		Hashtable<String, FlowCtrolCmd> searchCancleMap = cancleMap;

		Iterator<String> wait_keys = searchSendMap.keySet().iterator();
		while (wait_keys.hasNext()) {
			String key = (String) wait_keys.next();
			FlowCtrolCmd fcCmdSend = searchSendMap.get(key);
			FlowCtrolCmd fcCmdCancel = searchCancleMap.get(key);

			if (fcCmdCancel != null) {
				searchSendMap.remove(key);
				searchCancleMap.remove(key);
				System.out.println(DateUtil.formatNowTime() + " 呼号：" + fcCmdSend.getCallLetter() + " 取消下发指令");
				continue;
			}

			if (StringUtils.isNotNullOrEmpty(fcCmdSend)) {// 当前指令状态和要设置的指令状态一致，那么就取消下发
				// 导航主机
				if (StringUtils.isNotNullOrEmpty(fcCmdSend.getCurrNaviStatus())
						&& StringUtils.isNotNullOrEmpty(fcCmdSend.getTosetNaviStatus())
						&& (fcCmdSend.getCurrNaviStatus().intValue() == fcCmdSend.getTosetNaviStatus().intValue())) {
					// 当前指令状态和要设置的指令状态一致，直接更新状态，设置为指令下发成功成功
					fcCmdSend.setFlag(7);
					fcCmdSend.setStamp(new Date());

					String remark = DateUtil.formatNowTime() + " 呼号：" + fcCmdSend.getCallLetter()
							+ " 导航主机指令当前状态和要设置的状态一致，直接更新状态，设置为下发成功！";
					fcCmdSend.setRemark(remark);
					fcCmdService.update(fcCmdSend);
					searchSendMap.remove(fcCmdSend.getCallLetter());
					// 一致的话就不用更新t_ba_sim表了
					logger.info(remark);
					// 更新里历史表
					FlowCtrolCmdHistory flowCtrolCmdHistory = fcCmdService.fcSendCmdTransHistory(fcCmdSend);
					if (flowCtrolCmdHistory != null) {
						fcCmdService.update(flowCtrolCmdHistory);
					}
				} else if (StringUtils.isNotNullOrEmpty(fcCmdSend.getCurrFlowctrlStatus())
						&& StringUtils.isNotNullOrEmpty(fcCmdSend.getTosetFlowctrlStatus()) && (fcCmdSend
								.getCurrFlowctrlStatus().intValue() == fcCmdSend.getTosetFlowctrlStatus().intValue())) {// 省流量
					// 当前指令状态和要设置的指令状态一致，直接更新状态，设置为指令下发成功成功
					fcCmdSend.setFlag(7);
					fcCmdSend.setStamp(new Date());

					String remark = DateUtil.formatNowTime() + " 呼号：" + fcCmdSend.getCallLetter()
							+ " 省流量指令当前状态和要设置的状态一致，直接更新状态，设置为下发成功！";
					fcCmdSend.setRemark(remark);
					fcCmdService.update(fcCmdSend);
					searchSendMap.remove(fcCmdSend.getCallLetter());
					// 一致的话就不用更新t_ba_sim表了
					logger.info(remark);
					// 更新里历史表
					FlowCtrolCmdHistory flowCtrolCmdHistory = fcCmdService.fcSendCmdTransHistory(fcCmdSend);
					if (flowCtrolCmdHistory != null) {
						fcCmdService.update(flowCtrolCmdHistory);
					}
				} else {
					if ((fcCmdSend.getFlag() == 2 || fcCmdSend.getFlag() == 3)) {
						if (fcCmdSend.getSendTotal() < command_total
								&& (StringUtils.isNullOrEmpty(fcCmdSend.getSendSearchDate())
										|| is_overtime(fcCmdSend.getSendSearchDate(), command_reinterval))) {

							ArrayList<String> callletterList = new ArrayList<String>();
							callletterList.add(fcCmdSend.getCallLetter());

							// 发送查车指令
							String cmdsn = SystemConst.clienthandler.SendCommand(callletterList, 0x01, null, true);
							if (!cmdsn.equals("")) {
								fcCmdSend.setCmdSn(cmdsn);
								fcCmdSend.setSendSearchDate(new Date());
								fcCmdSend.setSendTotal(fcCmdSend.getSendTotal() + 1);
								searchSendMap.put(key, fcCmdSend);
								logger.info(DateUtil.formatNowTime() + " 呼号：" + fcCmdSend.getCallLetter() + " 第"
										+ (fcCmdSend.getSendTotal()) + "次查车指令下发， sn=" + cmdsn);
							}

							try {
								Thread.sleep(command_interval);// 2秒发1条
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}

						// 如果下发查车次数大于最大次数，车辆还是不上线那么设置为指令执行失败;
						if (fcCmdSend.getSendTotal() > command_total) {
							fcCmdSend.setStamp(new Date());
							fcCmdSend.setRemark("发送查车指令数超过最大次数：" + command_total + ",设置指令取消");
							fcCmdSend.setFlag(6);
							fcCmdService.update(fcCmdSend);
							searchSendMap.remove(fcCmdSend.getCallLetter());
							searchCancleMap.put(fcCmdSend.getCallLetter(), fcCmdSend);
							// 更新里历史表
							FlowCtrolCmdHistory flowCtrolCmdHistory = fcCmdService.fcSendCmdTransHistory(fcCmdSend);
							if (flowCtrolCmdHistory != null) {
								fcCmdService.update(flowCtrolCmdHistory);
							}
						}
					}
				}
			}
		}
	}

	public boolean is_overtime(Date stamp, int n) {
		if (stamp == null) {// 首次stamp为空
			return true;
		}
		Date now = new Date();
		long between = (now.getTime() - stamp.getTime());
		if (between >= n) {
			return true;
		}
		return false;
	}
}
