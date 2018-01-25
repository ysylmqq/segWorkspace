/*
 ********************************************************************************************
Discription:  通信中心单元测试工具用
			  
			  
Written By:   ZXZ
Date:         2014-05-22
Version:      1.0

Modified by:
Modified Date:
Version:
 ********************************************************************************************
 */
package cc.chinagps.gboss.comcenter.interprotocol.clienttest;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import com.gboss.comm.SystemConst;
import com.gboss.pojo.FlowCtrolCmd;
import com.gboss.pojo.FlowCtrolCmdHistory;
import com.gboss.pojo.SimCardInfo;
import com.gboss.service.FCCmdManageService;
import com.gboss.util.DateUtil;
import com.gboss.util.SpringContext;
import com.gboss.util.StringUtils;

import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.SendCommand_ACK;
import cc.chinagps.gboss.comcenter.buff.ResultCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendFlowCtrlCommand_ACK extends ClientBaseHandler {

	public String callLetter = ""; // 车辆呼号
	public int cmdId = 0;
	public int cmd_retcode = 0;
	private static Logger logger =  LoggerFactory.getLogger(SendFlowCtrlCommand_ACK.class);

	public SendFlowCtrlCommand_ACK(ComCenterBaseMessage basemsg,
			CenterClientHandler handler) {
		super(basemsg, handler);
	}

	@Override
	public int decode() {
		try {
			SendCommand_ACK sc = SendCommand_ACK.parseFrom(msgcontent);
			
			if(sc.hasCmdId())
			cmdId = sc.getCmdId();
			if(sc.hasRetcode())
				cmd_retcode = sc.getRetcode();
			if(sc.hasCallLetter())
			callLetter = sc.getCallLetter();		
			
			
		} catch (Exception e) {
			e.printStackTrace();
			retcode = ResultCode.Decode_Error;
			retmsg = "解码失败";
		}		
		return retcode;
	}

	@Override
	public void run() {
		FCCmdManageService fcCmdManageService = (FCCmdManageService) SpringContext.getBean("fcCmdManageService");
		
		if(cmdId == 1){
			/**** 1.下发导航主机开启指令 ****/
			sendCmdService(SystemConst.hm_navi_host_open_cmd, callLetter, SystemConst.NAVI_HOST_OPEN_CMD,
					fcCmdManageService);
			/**** 2.下发导航主机关闭指令 ****/
			sendCmdService(SystemConst.hm_navi_host_close_cmd, callLetter, SystemConst.NAVI_HOST_CLOSE_CMD,
					fcCmdManageService);
			/**** 3.下发省流量开启指令 ****/
			sendCmdService(SystemConst.hm_save_flow_open_cmd, callLetter, SystemConst.SAVE_FLOW_OPEN_CMD,
					fcCmdManageService);
			/**** 4.下发省流量关闭指令 ****/
			sendCmdService(SystemConst.hm_save_flow_close_cmd, callLetter, SystemConst.SAVE_FLOW_CLOSE_CMD,
					fcCmdManageService);
			/////////////////////测试
			/*ArrayList<String> callletterList = new ArrayList<String>();
			callletterList.add("14912383858");
			SystemConst.clienthandler.SendCommand(callletterList, 0x01, null, true);
			String remark = "@@@@@@@@@@@@"+DateUtil.formatNowTime() + "测试：开启省流量模式接收到查车指令.";
			System.out.println(remark);*/
		}
		
		/*********1.导航主机打开指令终端回应*********/
		if(cmdId == SystemConst.NAVI_HOST_OPEN_CMD){
			ConcurrentHashMap<String, FlowCtrolCmd> naviOpenMap = SystemConst.hm_navi_host_open_cmd;
			FlowCtrolCmd flowCtrolCmd = naviOpenMap.get(callLetter);
			
			if(cmd_retcode == ResultCode.OK){
				String remark = "";
				if(flowCtrolCmd != null){//导航主机开启指令成功
					flowCtrolCmd.setFlag(7);
					flowCtrolCmd.setRespTime(new Date());
					flowCtrolCmd.setRespCode(cmd_retcode);
					flowCtrolCmd.setSendTime(new Date());
					remark = DateUtil.formatNowTime() + " 呼号：" + callLetter + "接收开启导航主机指令成功";
					flowCtrolCmd.setRemark(remark);
					fcCmdManageService.update(flowCtrolCmd);
					naviOpenMap.remove(callLetter);//需要发送的队列里面删除
				}
				//指令执行成功,更新t_ba_sim表中的sim卡的当前状态;
				SimCardInfo newSimStatus = fcCmdManageService.getSimByCallLetter(callLetter);
				newSimStatus.setCurrNavihostStatus(1);
				fcCmdManageService.update(newSimStatus);
				//更新历史表
				FlowCtrolCmdHistory flowCtrolCmdHistory = fcCmdManageService.fcSendCmdTransHistory(flowCtrolCmd);
				if(flowCtrolCmdHistory != null){
					fcCmdManageService.update(flowCtrolCmdHistory);
				}
				logger.info(remark);
				System.out.println(remark);
			}else{
				// 失败
				String remark = "";
				if (null != flowCtrolCmd) {
					flowCtrolCmd.setFlag(6);
					flowCtrolCmd.setRespTime(new Date());
					flowCtrolCmd.setRespCode(cmd_retcode);
					remark = DateUtil.formatNowTime() + " 呼号：" + callLetter + "指令执行失败";
					flowCtrolCmd.setRemark(remark);
					fcCmdManageService.update(flowCtrolCmd);
					//更新历史表
					FlowCtrolCmdHistory flowCtrolCmdHistory = fcCmdManageService.fcSendCmdTransHistory(flowCtrolCmd);
					if(flowCtrolCmdHistory != null){
						fcCmdManageService.update(flowCtrolCmdHistory);
					}
					naviOpenMap.remove(callLetter);
					logger.info(remark);
					System.out.println(remark);
				}
			}
		}
		/*********2.导航主机关闭指令终端回应*********/
		if(cmdId == SystemConst.NAVI_HOST_CLOSE_CMD){
			ConcurrentHashMap<String, FlowCtrolCmd> naviCloseMap = SystemConst.hm_navi_host_close_cmd;	
			FlowCtrolCmd flowCtrolCmd = naviCloseMap.get(callLetter);
			
			if(cmd_retcode == ResultCode.OK){
				String remark = "";
				if(flowCtrolCmd != null){//导航主机关闭指令成功
					flowCtrolCmd.setFlag(7);
					flowCtrolCmd.setRespTime(new Date());
					flowCtrolCmd.setRespCode(cmd_retcode);
					flowCtrolCmd.setSendTime(new Date());
					remark = DateUtil.formatNowTime() + " 呼号：" + callLetter + "接收关闭导航主机指令成功";
					flowCtrolCmd.setRemark(remark);
					fcCmdManageService.update(flowCtrolCmd);
					naviCloseMap.remove(callLetter);//需要发送的队列里面删除
					//更新历史表
					FlowCtrolCmdHistory flowCtrolCmdHistory = fcCmdManageService.fcSendCmdTransHistory(flowCtrolCmd);
					if(flowCtrolCmdHistory != null){
						fcCmdManageService.update(flowCtrolCmdHistory);
					}
				}
				logger.info(remark);
				//指令执行成功,更新t_ba_sim表中的sim卡的当前状态;
				SimCardInfo newSimStatus = fcCmdManageService.getSimByCallLetter(callLetter);
				newSimStatus.setCurrNavihostStatus(0);
				fcCmdManageService.update(newSimStatus);
			}else{
				// 指令执行失败
				String remark = "";
				if (null != flowCtrolCmd) {
					flowCtrolCmd.setFlag(6);
					flowCtrolCmd.setRespTime(new Date());
					flowCtrolCmd.setRespCode(cmd_retcode);
					remark = DateUtil.formatNowTime() + " 呼号：" + callLetter + "指令执行失败";
					flowCtrolCmd.setRemark(remark);
					fcCmdManageService.update(flowCtrolCmd);
					naviCloseMap.remove(callLetter);
					logger.info(remark);
					//更新历史表
					FlowCtrolCmdHistory flowCtrolCmdHistory = fcCmdManageService.fcSendCmdTransHistory(flowCtrolCmd);
					if(flowCtrolCmdHistory != null){
						fcCmdManageService.update(flowCtrolCmdHistory);
					}
				}
			}
		}
		/*********3.省流量开启指令终端回应*********/
		if(cmdId == SystemConst.SAVE_FLOW_OPEN_CMD){
			ConcurrentHashMap<String, FlowCtrolCmd> saveFlowOpenMap = SystemConst.hm_save_flow_open_cmd;	
			FlowCtrolCmd flowCtrolCmd = saveFlowOpenMap.get(callLetter);
			
			if(cmd_retcode == ResultCode.OK){
				String remark = "";
				if(flowCtrolCmd != null){//指令成功
					flowCtrolCmd.setFlag(7);
					flowCtrolCmd.setRespTime(new Date());
					flowCtrolCmd.setRespCode(cmd_retcode);
					flowCtrolCmd.setSendTime(new Date());
					remark = DateUtil.formatNowTime() + " 呼号：" + callLetter + "接收开启省流量指令成功";
					flowCtrolCmd.setRemark(remark);
					fcCmdManageService.update(flowCtrolCmd);
					saveFlowOpenMap.remove(callLetter);//需要发送的队列里面删除
					//更新历史表
					FlowCtrolCmdHistory flowCtrolCmdHistory = fcCmdManageService.fcSendCmdTransHistory(flowCtrolCmd);
					if(flowCtrolCmdHistory != null){
						fcCmdManageService.update(flowCtrolCmdHistory);
					}
				}
				logger.info(remark);
				//指令执行成功,更新t_ba_sim表中的sim卡的当前状态;
				SimCardInfo newSimStatus = fcCmdManageService.getSimByCallLetter(callLetter);
				newSimStatus.setCurrSaveflowStatus(1);
				/**
				 * 2017-09-18要求: 开启省流量模式之后，关闭sim卡的反控指令;
				 */
				newSimStatus.setRemoteCtrlStatus(0);
				fcCmdManageService.update(newSimStatus);
			}else{
				// 指令执行失败
				String remark = "";
				if (null != flowCtrolCmd) {
					flowCtrolCmd.setFlag(6);
					flowCtrolCmd.setRespTime(new Date());
					flowCtrolCmd.setRespCode(cmd_retcode);
					remark = DateUtil.formatNowTime() + " 呼号：" + callLetter + "指令执行失败";
					flowCtrolCmd.setRemark(remark);
					fcCmdManageService.update(flowCtrolCmd);
					saveFlowOpenMap.remove(callLetter);
					//更新历史表
					FlowCtrolCmdHistory flowCtrolCmdHistory = fcCmdManageService.fcSendCmdTransHistory(flowCtrolCmd);
					if(flowCtrolCmdHistory != null){
						fcCmdManageService.update(flowCtrolCmdHistory);
					}
					logger.info(remark);
				}
			}
		}
		
		/*********4.省流量关闭指令终端回应*********/
		if(cmdId == SystemConst.SAVE_FLOW_CLOSE_CMD){
			ConcurrentHashMap<String, FlowCtrolCmd> saveFlowCloseMap = SystemConst.hm_save_flow_close_cmd;	
			FlowCtrolCmd flowCtrolCmd = saveFlowCloseMap.get(callLetter);
			if(cmd_retcode == ResultCode.OK){
				String remark = "";
				if(flowCtrolCmd != null){//导航主机关闭指令成功
					flowCtrolCmd.setFlag(7);
					flowCtrolCmd.setRespTime(new Date());
					flowCtrolCmd.setRespCode(cmd_retcode);
					remark = DateUtil.formatNowTime() + " 呼号：" + callLetter + "接收关闭省流量指令成功";
					flowCtrolCmd.setRemark(remark);
					flowCtrolCmd.setSendTime(new Date());
					fcCmdManageService.update(flowCtrolCmd);
					saveFlowCloseMap.remove(callLetter);//需要发送的队列里面删除
					//更新历史表
					FlowCtrolCmdHistory flowCtrolCmdHistory = fcCmdManageService.fcSendCmdTransHistory(flowCtrolCmd);
					if(flowCtrolCmdHistory != null){
						fcCmdManageService.update(flowCtrolCmdHistory);
					}
				}				
				//指令执行成功,更新t_ba_sim表中的sim卡的当前状态;
				SimCardInfo newSimStatus = fcCmdManageService.getSimByCallLetter(callLetter);
				newSimStatus.setCurrSaveflowStatus(0);
				fcCmdManageService.update(newSimStatus);
				logger.info(remark);
			}else{
				// 指令执行失败
				String remark = "";
				if (null != flowCtrolCmd) {
					flowCtrolCmd.setFlag(6);
					flowCtrolCmd.setRespTime(new Date());
					flowCtrolCmd.setRespCode(cmd_retcode);
					remark = DateUtil.formatNowTime() + " 呼号：" + callLetter + "指令执行失败";
					flowCtrolCmd.setRemark(remark);
					fcCmdManageService.update(flowCtrolCmd);
					saveFlowCloseMap.remove(callLetter);
					//更新历史表
					FlowCtrolCmdHistory flowCtrolCmdHistory = fcCmdManageService.fcSendCmdTransHistory(flowCtrolCmd);
					if(flowCtrolCmdHistory != null){
						fcCmdManageService.update(flowCtrolCmdHistory);
					}
					logger.info(remark);
				}
			}
		}
	}
	
	/**
	 * 
	 * @Description:下发指令;
	 * @Params: cmdId -- 十六进制数
	 * @Return:void
	 * @Author:cj
	 * @Date:2017年7月21日 上午10:06:39
	 */
	public void sendCmdService(ConcurrentHashMap<String, FlowCtrolCmd> fcSendMap, String callLetter, int cmdId,
			FCCmdManageService fcCmdManageService) {

		if (fcSendMap == null || callLetter == null || callLetter.equals("") || cmdId <= 0) {
			System.out.println("sendCmdService>>参数为空，下发指令失败！");
			return;
		}
		FlowCtrolCmd flowCtrolCmd = fcSendMap.get(callLetter);

		if (flowCtrolCmd != null) {
			if (flowCtrolCmd.getFlag() == 2 || flowCtrolCmd.getFlag() == 3) {
				// 下发打开导航主机指令
				ArrayList<String> callletterList = new ArrayList<String>();
				callletterList.add(flowCtrolCmd.getCallLetter());

				if (SystemConst.clienthandler.isLogined() && StringUtils.isNullOrEmpty(flowCtrolCmd.getSendTime())) {
					String cmdsn = SystemConst.clienthandler.SendCommand(callletterList, cmdId, null, false);

					if (!cmdsn.equals("")) {
						flowCtrolCmd.setCmdSn(cmdsn);// 更新cmdsn
						fcCmdManageService.update(flowCtrolCmd);
						String remark = DateUtil.formatNowTime() + " 呼号：" + flowCtrolCmd.getCallLetter()
								+ "从【查车指令响应】回应下发cmdId=" + cmdId + "的指令: sn=" + cmdsn;
						logger.info(remark);
						System.out.println(remark);
					}
				}
			}
		}
	}
}
