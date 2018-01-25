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

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gboss.comm.SystemConst;
import com.gboss.pojo.FlowCtrolCmd;
import com.gboss.pojo.FlowCtrolCmdHistory;
import com.gboss.service.FCCmdManageService;
import com.gboss.util.DateUtil;
import com.gboss.util.SpringContext;
import com.gboss.util.StringUtils;

import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.SendCommandSend_ACK;
import cc.chinagps.gboss.comcenter.buff.ResultCode;

/**
 * 
 * @ClassName: SendFlowCtrlCommandSend_ACK
 * @Description:网关回应,发送流量控制指令响应;
 * @author:cj
 * @date 2017年7月20日 上午11:29:40
 *
 */
public class SendFlowCtrlCommandSend_ACK extends ClientBaseHandler {
	public String callLetter = ""; // 车辆呼号
	public int cmdId = 0;
	public int cmd_retcode = 0;
	private static Logger logger = LoggerFactory.getLogger(SendFlowCtrlCommandSend_ACK.class);

	public SendFlowCtrlCommandSend_ACK(ComCenterBaseMessage basemsg, CenterClientHandler handler) {
		super(basemsg, handler);
	}

	@Override
	public int decode() {
		try {
			SendCommandSend_ACK sc = SendCommandSend_ACK.parseFrom(msgcontent);
			if (sc.hasCmdId()) {
				cmdId = sc.getCmdId();
			}
			if (sc.hasRetcode()) {
				cmd_retcode = sc.getRetcode();
			}
			if (sc.hasCallLetter()) {
				callLetter = sc.getCallLetter();
			}
			if (sc.hasRetmsg()) {
				retmsg = sc.getRetmsg();
			}
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
		if (ResultCode.OK == cmd_retcode) {
			dealFcCmdResp(cmdId, callLetter, fcCmdManageService);
		}
	}

	/**
	 * 
	 * @Description:处理网关相关响应;
	 * @Params:
	 * @Return:void
	 * @Author:cj
	 * @Date:2017年7月20日 下午4:24:04
	 */
	public void dealFcCmdResp(Integer cmdId, String callLetter, FCCmdManageService fcCmdManageService) {
		if (cmdId == null || cmdId.intValue() < 0 || StringUtils.isBlank(callLetter) || fcCmdManageService == null) {
			System.out.println("###处理网关响应失败，参数为空！");
			return;
		}

		FlowCtrolCmd naviOpenCmd = SystemConst.hm_navi_host_open_cmd.get(callLetter);
		FlowCtrolCmd naviCloseCmd = SystemConst.hm_navi_host_close_cmd.get(callLetter);
		FlowCtrolCmd saveOpenCmd = SystemConst.hm_save_flow_open_cmd.get(callLetter);
		FlowCtrolCmd saveCloseCmd = SystemConst.hm_save_flow_close_cmd.get(callLetter);

		if (cmdId.intValue() == 1) {
			// 1.开启导航主机指令
			if (naviOpenCmd != null
					&& (naviOpenCmd.getFlag().intValue() == 2 || naviOpenCmd.getFlag().intValue() == 3)) {
				// 设置状态查车指令已发下
				String remark = DateUtil.formatNowTime() + " 呼号：" + callLetter + "中心应答开启导航主机指令的查车指令，状态设置为查车已下发";
				naviOpenCmd.setFlag(3);
				naviOpenCmd.setRemark(remark);
				naviOpenCmd.setSendSearchDate(new Date());
				naviOpenCmd.setSendTotal(naviOpenCmd.getSendTotal() + 1);
				SystemConst.hm_navi_host_open_cmd.put(callLetter, naviOpenCmd);
				fcCmdManageService.update(naviOpenCmd);
				logger.info(remark);
				// 更新里历史表
				FlowCtrolCmdHistory flowCtrolCmdHistory = fcCmdManageService.fcSendCmdTransHistory(naviOpenCmd);
				if (flowCtrolCmdHistory != null) {
					fcCmdManageService.update(flowCtrolCmdHistory);
				}
			}
			// 2.关闭导航主机指令
			if (naviCloseCmd != null
					&& (naviCloseCmd.getFlag().intValue() == 2 || naviCloseCmd.getFlag().intValue() == 3)) {
				// 设置状态查车指令已发下
				String remark = DateUtil.formatNowTime() + " 呼号：" + callLetter + "中心应答关闭导航主机指令的查车指令，状态设置为查车已下发";
				naviCloseCmd.setFlag(3);
				naviCloseCmd.setRemark(remark);
				naviCloseCmd.setSendSearchDate(new Date());
				naviCloseCmd.setSendTotal(naviCloseCmd.getSendTotal() + 1);

				SystemConst.hm_navi_host_close_cmd.put(callLetter, naviCloseCmd);
				fcCmdManageService.update(naviCloseCmd);
				logger.info(remark);
				// 更新里历史表
				FlowCtrolCmdHistory flowCtrolCmdHistory = fcCmdManageService.fcSendCmdTransHistory(naviCloseCmd);
				if (flowCtrolCmdHistory != null) {
					fcCmdManageService.update(flowCtrolCmdHistory);
				}
			}
			// 3.打开省流量指令
			if (saveOpenCmd != null
					&& (saveOpenCmd.getFlag().intValue() == 2 || saveOpenCmd.getFlag().intValue() == 3)) {
				// 设置状态查车指令已发下
				String remark = DateUtil.formatNowTime() + " 呼号：" + callLetter + "中心应答打开省流量指令的查车指令，状态设置为查车已下发";
				saveOpenCmd.setFlag(3);
				saveOpenCmd.setRemark(remark);
				saveOpenCmd.setSendSearchDate(new Date());
				saveOpenCmd.setSendTotal(saveOpenCmd.getSendTotal() + 1);

				SystemConst.hm_save_flow_open_cmd.put(callLetter, saveOpenCmd);
				fcCmdManageService.update(saveOpenCmd);
				logger.info(remark);
				// 更新里历史表
				FlowCtrolCmdHistory flowCtrolCmdHistory = fcCmdManageService.fcSendCmdTransHistory(saveOpenCmd);
				if (flowCtrolCmdHistory != null) {
					fcCmdManageService.update(flowCtrolCmdHistory);
				}
			}
			// 4.关闭省流量指令
			if (saveCloseCmd != null
					&& (saveCloseCmd.getFlag().intValue() == 2 || saveCloseCmd.getFlag().intValue() == 3)) {
				// 设置状态查车指令已发下
				String remark = DateUtil.formatNowTime() + " 呼号：" + callLetter + "中心应答关闭省流量指令的查车指令，状态设置为查车已下发";
				saveCloseCmd.setFlag(3);
				saveCloseCmd.setRemark(remark);
				saveCloseCmd.setSendSearchDate(new Date());
				saveCloseCmd.setSendTotal(saveCloseCmd.getSendTotal() + 1);

				SystemConst.hm_save_flow_close_cmd.put(callLetter, saveCloseCmd);
				fcCmdManageService.update(saveCloseCmd);
				logger.info(remark);
				// 更新里历史表
				FlowCtrolCmdHistory flowCtrolCmdHistory = fcCmdManageService.fcSendCmdTransHistory(saveCloseCmd);
				if (flowCtrolCmdHistory != null) {
					fcCmdManageService.update(flowCtrolCmdHistory);
				}
			}
		} else if (cmdId.intValue() == SystemConst.NAVI_HOST_OPEN_CMD && naviOpenCmd != null) {
			System.out.println("************1.网关响应导航主机开启指令*************");
			// 设置开启导航主机指令已发下状态
			String remark = DateUtil.formatNowTime() + " 呼号：" + callLetter + " 中心应答导航主机打开指令，状态设置为指令执行中";
			naviOpenCmd.setFlag(4);
			naviOpenCmd.setSendFCCmdDate(new Date());
			naviOpenCmd.setSendTotal(naviOpenCmd.getSendTotal() + 1);
			naviOpenCmd.setSendTime(new Date());
			naviOpenCmd.setRemark(remark);
			SystemConst.hm_navi_host_open_cmd.put(callLetter, naviOpenCmd);
			fcCmdManageService.update(naviOpenCmd);
			logger.info(remark);
			System.out.println(remark);
			// 更新里历史表
			FlowCtrolCmdHistory flowCtrolCmdHistory = fcCmdManageService.fcSendCmdTransHistory(naviOpenCmd);
			if (flowCtrolCmdHistory != null) {
				fcCmdManageService.update(flowCtrolCmdHistory);
			}
		} else if (cmdId.intValue() == SystemConst.NAVI_HOST_CLOSE_CMD && naviCloseCmd != null) {
			System.out.println("************2.网关响应导航主机关闭指令*************");
			// 设置关闭导航主机指令已发下状态
			String remark = DateUtil.formatNowTime() + " 呼号：" + callLetter + " 中心应答导航主机打开指令，状态设置为指令执行中";

			naviCloseCmd.setFlag(4);
			naviCloseCmd.setSendFCCmdDate(new Date());
			naviCloseCmd.setSendTotal(naviCloseCmd.getSendTotal() + 1);
			naviCloseCmd.setSendTime(new Date());
			naviCloseCmd.setRemark(remark);

			SystemConst.hm_navi_host_close_cmd.put(callLetter, naviCloseCmd);
			fcCmdManageService.update(naviCloseCmd);

			logger.info(remark);
			// 更新里历史表
			FlowCtrolCmdHistory flowCtrolCmdHistory = fcCmdManageService.fcSendCmdTransHistory(naviCloseCmd);
			if (flowCtrolCmdHistory != null) {
				fcCmdManageService.update(flowCtrolCmdHistory);
			}
		} else if (cmdId.intValue() == SystemConst.SAVE_FLOW_OPEN_CMD && saveOpenCmd != null) {
			System.out.println("************3.网关响应省流量开启指令*************");
			// 设置打开省流量指令已发下状态
			String remark = DateUtil.formatNowTime() + " 呼号：" + callLetter + " 中心应答导航主机打开指令，状态设置为指令执行中";
			saveOpenCmd.setFlag(4);
			saveOpenCmd.setSendFCCmdDate(new Date());
			saveOpenCmd.setSendTotal(saveOpenCmd.getSendTotal() + 1);
			saveOpenCmd.setSendTime(new Date());
			saveOpenCmd.setRemark(remark);

			SystemConst.hm_save_flow_open_cmd.put(callLetter, saveOpenCmd);
			fcCmdManageService.update(saveOpenCmd);
			logger.info(remark);
			// 更新里历史表
			FlowCtrolCmdHistory flowCtrolCmdHistory = fcCmdManageService.fcSendCmdTransHistory(saveOpenCmd);
			if (flowCtrolCmdHistory != null) {
				fcCmdManageService.update(flowCtrolCmdHistory);
			}
		} else if (cmdId.intValue() == SystemConst.SAVE_FLOW_CLOSE_CMD && saveCloseCmd != null) {
			System.out.println("************4.网关响应省流量关闭指令*************");
			// 设置关闭省流量指令已发下状态
			String remark = DateUtil.formatNowTime() + " 呼号：" + callLetter + " 中心应答导航主机打开指令，状态设置为指令执行中";
			saveCloseCmd.setFlag(4);
			saveCloseCmd.setSendFCCmdDate(new Date());
			saveCloseCmd.setSendTotal(saveCloseCmd.getSendTotal() + 1);
			saveCloseCmd.setSendTime(new Date());
			saveCloseCmd.setRemark(remark);

			SystemConst.hm_save_flow_close_cmd.put(callLetter, saveCloseCmd);
			fcCmdManageService.update(saveCloseCmd);
			logger.info(remark);
			// 更新里历史表
			FlowCtrolCmdHistory flowCtrolCmdHistory = fcCmdManageService.fcSendCmdTransHistory(saveCloseCmd);
			if (flowCtrolCmdHistory != null) {
				fcCmdManageService.update(flowCtrolCmdHistory);
			}
		}
	}
}
