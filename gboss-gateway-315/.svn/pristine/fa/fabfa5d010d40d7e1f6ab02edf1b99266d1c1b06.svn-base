package cc.chinagps.gateway.unit.oem.test;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;

import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.mq.MQManager;
import cc.chinagps.gateway.mq.export.ExportMQMult;
import cc.chinagps.gateway.seat.cmd.CmdUtil;

public class Sender {

	public static void navi(ExportMQMult exportMQMult) {
		String callLetter = "14910261884";
		int cmdId = CmdUtil.CMD_ID_SET_TARGET_QUERY_POINT;
		// cmdId = CmdUtil.CMD_ID_DELIVER_ORDER;
		List<String> params = new ArrayList<String>();
		params.add("0");
		params.add("114.27757300");
		params.add("22.68334500");
		try {
			exportMQMult.addCommand(callLetter, cmdId, params);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void position(ExportMQMult exportMQMult){
		String callLetter = "1613912345001";
		int cmdId =CmdUtil.CMD_ID_POSITION;
		List<String> params = new ArrayList<String>();
		try {
			exportMQMult.addCommand(callLetter, cmdId , params);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void remoteControl(ExportMQMult exportMQMult) {
		String callLetter = "13800000088";
		int cmdId = CmdUtil.CMD_ID_REMOTE_CONTROL_VEHICLE;
		List<String> params = new ArrayList<String>();
		params.add("1");
		try {
			exportMQMult.addCommand(callLetter, cmdId, params);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void accOffDeliver(ExportMQMult exportMQMult,boolean action, int secs) {
		String callLetter = "64854664565";
		int cmdId = action == true ? CmdUtil.CMD_ID_START_ACC_OFF_DELIVERY : CmdUtil.CMD_ID_STOP_ACC_OFF_DELIVERY;
		List<String> params = new ArrayList<String>();
		params.add(secs+"");
		try {
			exportMQMult.addCommand(callLetter, cmdId, params);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ExportMQMult exportMQMult = new ExportMQMult();
		LogManager.init();
		MQManager.init();
		try {
			exportMQMult.init();
			//navi(exportMQMult);
			//accOffDeliver(exportMQMult, true, 10);
			position(exportMQMult);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
