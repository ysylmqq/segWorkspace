/*
********************************************************************************************
Discription:  退录
			  
			  
Written By:   ZXZ
Date:         2014-05-09
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.outinterface;

import cc.chinagps.gboss.comcenter.CenterClientManager;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.buff.seginterfaceDataBuff.Logout;
import cc.chinagps.gboss.comcenter.buff.seginterfaceDataBuff.Logout_ACK;
import cc.chinagps.gboss.comcenter.websocket.ClientBaseHandler;

public class LogoutHandler extends ClientBaseHandler {

	private String srcnodeid;
	private String dstnodeid;
	public LogoutHandler(ComCenterBaseMessage basemsg, OutInterfaceClientInfo info) {
		super(basemsg, info);
	}

	@Override
	public int decode() {
		try {
			Logout logout = Logout.parseFrom(msgcontent);
			srcnodeid = logout.getSrcnode();
			dstnodeid = logout.getDstnode();
        } catch(Exception e) {
			e.printStackTrace();
			retcode = ResultCode.Decode_Error;
			retmsg = "解码失败";
		}
		return retcode;
	}

	@Override
	public void run() {
		if (retcode == ResultCode.OK) {
			//添加到客户端管理容器
	    	CenterClientManager.clientManager.removeClient(clientinfo);
		}
	}

	/*
	 * 返回退录应答
	 */
	@Override
	public byte[] response() {
		Logout_ACK.Builder logoutack = Logout_ACK.newBuilder();
		logoutack.setRetcode(retcode);
		logoutack.setRetmsg(retmsg);
		logoutack.setDstnode(srcnodeid);
		logoutack.setSrcnode(dstnodeid);
		return Serialize(MessageType.Logout_ACK, logoutack.build().toByteString());
	}
}
