package cc.chinagps.seat.bean;

import cc.chinagps.seat.bean.table.SendCmdTable;

public class ReportSendCmd extends ReportCommon {

	private SendCmdTable sendCmd;    
	public SendCmdTable getSendCmd() {
		return sendCmd;
	}

	public void setSendCmd(SendCmdTable sendCmd) {
		this.sendCmd = sendCmd;
	}	
}
