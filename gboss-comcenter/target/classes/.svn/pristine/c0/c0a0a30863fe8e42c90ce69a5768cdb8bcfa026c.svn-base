/*
********************************************************************************************
Discription: 测试websocket文本传输，先返回websocket页面, 并自动连接
			 netty例子
			  
Written By:   ZXZ
Date:         2014-05-04
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * Generates the demo HTML page which is served at http://localhost:8080/
 */
public final class WebSocketServerIndexPage {

    private static final String NEWLINE = "\r\n";

    public static ByteBuf getContent(String webSocketLocation) {
        return Unpooled.copiedBuffer(
        		"<!DOCTYPE html><html><head><title>测试通信中心WEBSOCKET协议</title></head>" + NEWLINE +
        		"<body><div><textarea id=\"log\" style=\"width: 100%; height: 300px\"></textarea></div>" + NEWLINE + 
				"<div style=\"width: 100%\"></div>" + NEWLINE +
				"<div><span>终端呼号:</span><input type=\"text\" style=\"width: 50%\" id=\"callletter\" placeholder=\"多个呼号用半角分号隔开\"></div>" + NEWLINE +
				"<div style=\"width: 100%; height: 10px\"></div>" + NEWLINE +
        		"<div><button onclick=\"Connect();\">Connect</button><button onclick=\"Logout()\">Logout</button>" + NEWLINE +
        		"<button onclick=\"ActiveLink()\">ActiveLink</button><button onclick=\"AddMonitor()\">添加车辆监控</button>" + NEWLINE +
        		"<button onclick=\"RemoveMonitor()\">删除车辆监控</button><button onclick=\"AddMonitorByType()\">添加信息监控</button>" + NEWLINE +
        		"<button onclick=\"RemoveMonitorByType()\">删除信息监控</button><button onclick=\"GetLastPosition()\">GetLastPosition</button>" + NEWLINE +
        		"<button onclick=\"GetLastTravel()\">GetLastTravel</button><button onclick=\"GetLastFault()\">GetLastFault</button></div>" + NEWLINE +
        		"<div style=\"width: 100%; height: 10px\"></div>" + NEWLINE +
        		"<div><button onclick=\"GetHistoryGPS()\">GetHistoryGPS</button><button onclick=\"GetHistoryTravel()\">GetHistoryTravel</button>" + NEWLINE +
        		"<button onclick=\"GetHistoryFault()\">GetHistoryFault</button><button onclick=\"GetHistoryNextPage()\">GetHistoryNextPage</button>" + NEWLINE +
        		"<button onclick=\"StopHistory()\">StopHistory</button><button onclick=\"SendSMS()\">发送手机短信</button></div>" + NEWLINE +
        		"<div style=\"width: 100%; height: 10px\"></div>" + NEWLINE +
        		"<div><button onclick=\"TestDeliverGPS()\">TestDeliverGPS</button><button onclick=\"TestDeliverOperateData()\">TestDeliverOperateData</button>" + NEWLINE +
        		"<button onclick=\"TestDeliverSMS()\">TestDeliverSMS</button><button onclick=\"TestDeliverUnitLoginOut()\">TestDeliverUnitLoginOut</button>" + NEWLINE +
        		"<button onclick=\"TestDeliverTravel()\">TestDeliverTravel</button><button onclick=\"TestDeliverFault()\">TestDeliverFault</button></div>" + NEWLINE +
        		"<div style=\"width: 100%; height: 10px\"></div>" + NEWLINE +
        		"<div><button onclick=\"Position()\">查车</button><button onclick=\"FindCar()\">找车</button>" + NEWLINE +
        		"<button onclick=\"OpenDoor()\">开门</button><button onclick=\"LockDoor()\">锁门</button>" + NEWLINE +
        		"<button onclick=\"LockOil()\">断油电</button><button onclick=\"OpenOil()\">恢复油电</button>" + NEWLINE +
        		"<button onclick=\"Trace()\">跟踪</button><button onclick=\"IntervalDeliver()\">定时上传</button>" + NEWLINE +
        		"<button onclick=\"Listen()\">监听</button><button onclick=\"UnitSMS()\">终端短信</button></div>" + NEWLINE +
        		"<script src=\"Long.min.js\"></script>" + NEWLINE +
        		"<script src=\"ByteBuffer.min.js\"></script>" + NEWLINE +
        		"<script src=\"ProtoBuf.min.js\"></script>" + NEWLINE +
        		"<script src=\"comcenterprotobuf.js\"></script>" + NEWLINE +
        		"<script src=\"zlib.min.js\"></script>" + NEWLINE +
        		"<script src=\"segutil.js\"></script>" + NEWLINE +
        		"<script src=\"websocketevent.js\"></script>" + NEWLINE +
        		"<script src=\"segseatlib.js\"></script>" + NEWLINE +
        		"<script src=\"testcomcenter.js\"></script>" + NEWLINE +
        		"</body></html>" + NEWLINE, CharsetUtil.UTF_8);
    }

    private WebSocketServerIndexPage() {
        // Unused
    }
}
