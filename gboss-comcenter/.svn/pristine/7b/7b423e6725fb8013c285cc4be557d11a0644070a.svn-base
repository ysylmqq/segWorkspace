/*
********************************************************************************************
Discription:  客户端请求基类生产工厂，简单工厂模式，工厂类
			  
			  
Written By:   ZXZ
Date:         2014-04-28
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.outinterface;

import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.websocket.ClientBaseHandler;
import cc.chinagps.gboss.comcenter.websocket.GetHistoryInfoHandler;
import cc.chinagps.gboss.comcenter.websocket.GetHistoryInfoNextPageHandler;
import cc.chinagps.gboss.comcenter.websocket.GetLastInfoHandler;
import cc.chinagps.gboss.comcenter.websocket.SendCommandHandler;
import cc.chinagps.gboss.comcenter.websocket.StopHistoryInfoHandler;

/*
 * 客户端请求工厂类，创建对应的请求处理类
 */
public class OutInterfaceServerHandlerFactory {
	
	public static ClientBaseHandler CreateClientHandler(OutInterfaceServerHandler serverhandler, ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage requestmsg, OutInterfaceClientInfo info) {
		ClientBaseHandler ret = null;
		switch(requestmsg.getId()) {
		case MessageType.SendCommand:  //下发指令
			ret = new SendCommandHandler(requestmsg, info);
			break;
		case MessageType.Login: 	//如果是登录报文
			ret = new LoginHandler(serverhandler, requestmsg, info);
			break;
		case MessageType.Logout:  //如果是退出报文
			ret = new LogoutHandler(requestmsg, info);
			break;
		case MessageType.ActiveLink:  //如果是链路检测报文
			ret = new ActiveLinkHandler(requestmsg, info);
			break;
		case MessageType.ActiveLink_ACK:  //如果是链路检测报文
			//System.out.println("ActiveLink_ACK");
			break;
		case MessageType.GetLastInfo:  //如果是最后信息报文
			ret = new GetLastInfoHandler(requestmsg, info);
			break;
		case MessageType.GetHistoryInfo:  //取历史位置、历史行程、历史故障（如果换了一辆车，前一辆车的历史查询自动结束）
			ret = new GetHistoryInfoHandler(requestmsg, info);
			break;
		case MessageType.GetHistoryInfoNextPage:  //取下一页历史位置、历史行程、历史故障
			ret = new GetHistoryInfoNextPageHandler(requestmsg, info);
			break;
		case MessageType.StopHistoryInfo:  //结束读历史位置、历史行程、历史故障（如果分页全部取完了，自动结束）
			ret = new StopHistoryInfoHandler(requestmsg, info);
			break;
		}
		//if (ret != null) {
			//System.out.println(ret.toString());
		//}
		return ret;
	}
}
