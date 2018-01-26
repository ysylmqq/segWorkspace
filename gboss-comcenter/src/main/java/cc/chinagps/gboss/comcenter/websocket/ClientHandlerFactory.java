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
package cc.chinagps.gboss.comcenter.websocket;

import cc.chinagps.gboss.comcenter.WebsocketClientInfo;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff;
import cc.chinagps.gboss.comcenter.buff.MessageType;

/*
 * 客户端请求工厂类，创建对应的请求处理类
 */
public class ClientHandlerFactory {
	
	public static ClientBaseHandler CreateClientHandler(ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage requestmsg, WebsocketClientInfo info) {
		ClientBaseHandler ret = null;
		switch(requestmsg.getId()) {
		case MessageType.GetLastInfo:  //如果是最后信息报文
			ret = new GetLastInfoHandler(requestmsg, info);
			break;
		case MessageType.SendCommand:  //下发指令 
			ret = new SendCommandHandler(requestmsg, info);
			break;
		case MessageType.Login: 	//如果是登录报文
			ret = new LoginHandler(requestmsg, info);
			break;
		case MessageType.Logout:  //如果是退出报文
			ret = new LogoutHandler(requestmsg, info);
			break;
		case MessageType.ActiveLink:  //如果是链路检测报文
			ret = new ActiveLinkHandler(requestmsg, info);
			break;
		case MessageType.AddMonitor:  //如果是添加监控列表报文
			ret = new AddMonitorHandler(requestmsg, info);
			break;
		case MessageType.RemoveMonitor:  //如果是删除监控列表报文
			ret = new RemoveMonitorHandler(requestmsg, info);
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
		case MessageType.TestDeliver:
			ret = new TestDeliverHandler(requestmsg, info);
			break;
		case MessageType.NewAlarm:
			ret = new NewAlarmHandler(requestmsg, info);
			break;
		case MessageType.GetCommandHistory:
			ret = new GetCommandHistoryHandler(requestmsg, info);
			break;
		}
		//if (ret != null && !(ret instanceof ActiveLinkHandler)) {
			//System.out.println("WebSocket: " + ret.toString());
		//}
		return ret;
	}
}
