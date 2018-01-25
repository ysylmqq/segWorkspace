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
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverGPS;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsInfo;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.lib.util.Config;

import com.gboss.comm.SystemConst;
import com.gboss.pojo.FlowCtrolCmd;
import com.gboss.pojo.Upgrade;
import com.gboss.service.FCCmdManageService;
import com.gboss.service.UpgradeService;
import com.gboss.util.DateUtil;
import com.gboss.util.SpringContext;
import com.gboss.util.StringUtils;

/**
 * 
 * @ClassName: FCDeliverGPSHandler
 * @Description:流量控制系统的gps监听处理器；
 * @author:cj
 * @date 2017年7月20日 下午1:43:29
 *
 */
public class FCDeliverGPSHandler extends ClientBaseHandler {

	public int gatewayid = 0; // 网关编号
	public int gatewaytype = 0; // 网关类型：0或无:表示NET网关，1:短信网关
	public GpsInfo gpsinfo = null;
	public String callLetter;
	private static Logger logger = LoggerFactory.getLogger(SendFlowCtrlCommandSend_ACK.class);

	public FCDeliverGPSHandler(ComCenterBaseMessage basemsg, CenterClientHandler handler) {
		super(basemsg, handler);
	}

	@Override
	public int decode() {
		try {
			DeliverGPS delivergps = DeliverGPS.parseFrom(msgcontent);
			if (delivergps.hasGatewayid())
				gatewayid = delivergps.getGatewayid();
			if (delivergps.hasGatewaytype())
				gatewaytype = delivergps.getGatewaytype();
			gpsinfo = delivergps.getGpsinfo();
			callLetter = gpsinfo.getCallLetter();
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
								+ "从【GPS】回应下发cmdId=" + cmdId + "的指令: sn=" + cmdsn;
						logger.info(remark);
					}
				}
			}
		}
	}
}
