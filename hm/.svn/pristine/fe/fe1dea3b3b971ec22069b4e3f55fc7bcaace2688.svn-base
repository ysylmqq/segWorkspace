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
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gboss.comm.SystemConst;
import com.gboss.pojo.FlowCtrolCmd;
import com.gboss.service.FCCmdManageService;
import com.gboss.util.DateUtil;
import com.gboss.util.SpringContext;
import com.gboss.util.StringUtils;

import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverUnitLoginOut;
import cc.chinagps.gboss.comcenter.buff.ResultCode;


public class FCDeliverUnitLoginOutHandler extends ClientBaseHandler {
	public String callLetter = "";	//车辆呼号
	//public String unitversion = ""; //版本号
	//public int gatewayid = 0;  		//网关编号
	public int inorout = 1;         //0:退录，1:登录
	private static Logger logger =  LoggerFactory.getLogger(FCDeliverUnitLoginOutHandler.class);

	public FCDeliverUnitLoginOutHandler(ComCenterBaseMessage basemsg, CenterClientHandler handler) {
		super(basemsg, handler);
	}

	@Override
	public int decode() {
		try {
			DeliverUnitLoginOut unitloginout = DeliverUnitLoginOut.parseFrom(msgcontent);
			/*if (unitloginout.hasGatewayid())
				gatewayid = unitloginout.getGatewayid();*/
			if (unitloginout.hasInorout())
				inorout = unitloginout.getInorout();
			if(unitloginout.hasCallLetter())
			    callLetter = unitloginout.getCallLetter();	
			/*if(unitloginout.hasUnitversion())
			    unitversion  = unitloginout.getUnitversion();*/	
        } catch(Exception e) {
			e.printStackTrace();
			retcode = ResultCode.Decode_Error;
			retmsg = "解码失败";
		}
		return retcode;
	}

	
	@Override
	public void run() {		
		if (inorout == 1) {
			FCCmdManageService fcCmdManageService = (FCCmdManageService) SpringContext.getBean("fcCmdManageService");
			//检查队列中是否有相关记录，然后进行发送指令;
			//1.开启导航主机指令;
			ConcurrentHashMap <String, FlowCtrolCmd> naviOpenMap = SystemConst.hm_navi_host_open_cmd;
			//2.关闭导航主机指令;
			ConcurrentHashMap<String, FlowCtrolCmd> naviCloseMap = SystemConst.hm_navi_host_close_cmd;
			//3.开启省流量指令;
			ConcurrentHashMap<String, FlowCtrolCmd> saveFlowOpenMap = SystemConst.hm_save_flow_open_cmd;			
			//4.关闭省流量指令;
			ConcurrentHashMap<String, FlowCtrolCmd> saveFlowCloseMap = SystemConst.hm_save_flow_close_cmd;			
			ArrayList<String> callletterlist = new ArrayList<String>();
			///////////测试
			callLetter = "14912383858";
			callletterlist.add(callLetter);
			
			//下发开启导航主机指令
			FlowCtrolCmd flowCtrolCmd = naviOpenMap.get(callLetter);
			if(flowCtrolCmd != null){
				if (SystemConst.clienthandler.isLogined() && StringUtils.isNullOrEmpty(flowCtrolCmd.getSendTime())) {
					String cmdsn = SystemConst.clienthandler.SendCommand(callletterlist, SystemConst.NAVI_HOST_OPEN_CMD, null, false);
					
					if (!cmdsn.equals("")) {
						flowCtrolCmd.setCmdSn(cmdsn);//更新cmdsn
						fcCmdManageService.update(flowCtrolCmd);
						String remark = DateUtil.formatNowTime() + " 呼号：" + callLetter + "从登录下发开启导航主机指令: sn=" + cmdsn;
						logger.info(remark);
						System.out.println(remark);
					}
				}
			}
			//下发关闭导航主机指令
			flowCtrolCmd = naviCloseMap.get(callLetter);
			if(flowCtrolCmd != null){
				if (SystemConst.clienthandler.isLogined() && StringUtils.isNullOrEmpty(flowCtrolCmd.getSendTime())) {
					String cmdsn = SystemConst.clienthandler.SendCommand(callletterlist, SystemConst.NAVI_HOST_CLOSE_CMD, null, false);
					
					if (!cmdsn.equals("")) {
						flowCtrolCmd.setCmdSn(cmdsn);//更新cmdsn
						fcCmdManageService.update(flowCtrolCmd);
						String remark = DateUtil.formatNowTime() + " 呼号：" + callLetter + "从登录下发关闭导航主机指令: sn=" + cmdsn;
						logger.info(remark);
						System.out.println(remark);
					}
				}
			}
			//下发开启省流量指令
			flowCtrolCmd = saveFlowOpenMap.get(callLetter);
			if(flowCtrolCmd != null){
				if (SystemConst.clienthandler.isLogined() && StringUtils.isNullOrEmpty(flowCtrolCmd.getSendTime())) {
					String cmdsn = SystemConst.clienthandler.SendCommand(callletterlist, SystemConst.SAVE_FLOW_OPEN_CMD, null, false);
					
					if (!cmdsn.equals("")) {
						flowCtrolCmd.setCmdSn(cmdsn);//更新cmdsn
						fcCmdManageService.update(flowCtrolCmd);
						String remark = DateUtil.formatNowTime() + " 呼号：" + callLetter + "从登录下发开启省流量指令: sn=" + cmdsn;
						logger.info(remark);
						System.out.println(remark);
					}
				}
			}
			//下发关闭省流量指令
			flowCtrolCmd = saveFlowCloseMap.get(callLetter);
			if(flowCtrolCmd != null){
				if (SystemConst.clienthandler.isLogined() && StringUtils.isNullOrEmpty(flowCtrolCmd.getSendTime())) {
					String cmdsn = SystemConst.clienthandler.SendCommand(callletterlist, SystemConst.SAVE_FLOW_CLOSE_CMD, null, false);
					
					if (!cmdsn.equals("")) {
						flowCtrolCmd.setCmdSn(cmdsn);//更新cmdsn
						fcCmdManageService.update(flowCtrolCmd);
						String remark = DateUtil.formatNowTime() + " 呼号：" + callLetter + "从登录下发关闭省流量指令: sn=" + cmdsn;
						logger.info(remark);
						System.out.println(remark);
					}
				}
			}
		}
	}
}
