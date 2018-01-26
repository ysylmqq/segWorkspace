/*
********************************************************************************************
Discription:  内容协议客户端接收报文处理，测试用，有通信中心中没有内容协议客户端
			  
			  
Written By:   ZXZ
Date:         2014-04-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/

package cc.chinagps.gboss.comcenter.interprotocol.clienttest;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import com.google.protobuf.ByteString;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.AddMonitor;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage;
import cc.chinagps.gboss.comcenter.interprotocol.clienttest.ClientBaseHandler;
import cc.chinagps.gboss.comcenter.interprotocol.clienttest.ClientHandlerFactory;
import cc.chinagps.interprotocol.Constant;
import cc.chinagps.interprotocol.InterProtocolClientHandler;
import cc.chinagps.interprotocol.InterProtocolInfo;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.SendCommand;

public class CenterClientHandler extends InterProtocolClientHandler {

	public static CenterClientHandler clienthandler = null;
    protected static final Logger logger = Logger.getLogger(CenterClientHandler.class.getName());
	private ClientCommandThread commandthread = new ClientCommandThread();

	public AtomicLong gpstotalcount = new AtomicLong(); //gps总数
	
    @Override
	protected void OnLoginAck(short retcode) {
		System.out.println("内部协议登录通信中心:" + retcode);
		if (retcode == ResultCode.OK) {	//登录成功开始发送线程
			commandthread.start();
		}
		ArrayList<Integer> typelist = new ArrayList<Integer>();
		typelist.add((int)MessageType.DeliverGPS);
		typelist.add((int)MessageType.DeliverUnitLoginOut);
		typelist.add((int)MessageType.DeliverUnitVersion);
		typelist.add((int)MessageType.DeliverECUConfig);
		AddAllmonitor(typelist);
		typelist = null;
	}

    @Override
	protected void onClose() {
    	super.onClose();
		commandthread.interrupt();
		System.out.println("通信中心连接断开");
	}
	/*
	 *处理业务报文 
	 */
	@Override
	protected void ProcessNormal(InterProtocolInfo recv) {
		//如果是通信中心内部协议的PROTOBUF报文体格式
		//和WEBSOCKET协议处理类似
		if (recv.msgId == Constant.MESSAGE_ID_COMCENTER_PROTOBUF) {
			try {
	        	ComCenterDataBuff.ComCenterMessage messages = ComCenterDataBuff.ComCenterMessage.parseFrom(recv.content);
	        	for(ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage msg : messages.getMessagesList()) {
	        		ClientBaseHandler handler = ClientHandlerFactory.CreateClientHandler(msg, this);
	        		//所有事件都分三步处理，如果没有三步，不用overwrite基类
	        		if (handler != null) {
	        			if ((gpstotalcount.incrementAndGet() % 10000) == 0) {
	        				System.out.println(gpstotalcount.get());
	        			}
	        			//先解码
	        			int retcode = handler.decode(); 
	        			if (retcode == ResultCode.OK) {
		        			handler.run(); 
	        			} else {
	        	            logger.warning(String.format("run failed[%s]: %s, %s", retcode, msg.getId(), handler.toString()));
	        	        }
	        		}
	        	}
            } catch(Exception e) {
    			e.printStackTrace();
        	}
		}
	}
	
	/*
	 * 将一个基础包（protobuf）,序列化字节串
	 */
	protected byte[] Serialize(int msgtype, ByteString bytestring) {
		ComCenterMessage.ComCenterBaseMessage.Builder basemsg = ComCenterMessage.ComCenterBaseMessage.newBuilder();
		basemsg.setId(msgtype);
		basemsg.setContent(bytestring);
		ComCenterMessage.Builder msg = ComCenterMessage.newBuilder();
		msg.addMessages(basemsg.build());
		return msg.build().toByteArray();
	}
	
	//添加监控列表
	public boolean Addmonitor(ArrayList<String> callletterlist) {
		if (islogined) {
			if (callletterlist.size() == 0) {
				return false;
			}
			//打登录返回的报文
			AddMonitor.Builder builder = AddMonitor.newBuilder();
			builder.addAllCallLetters(callletterlist);
			builder.addInfotypes(-1);
			byte[] sendbuf = Serialize(MessageType.AddMonitor, builder.build().toByteString());
			return WriteContent(sendbuf);
		}
		return false;
	}
	//监控全部车辆，infotype某类信息(-1)表示所有类型信息
	public boolean AddAllmonitor(int infotype) {
		if (islogined) {
			//打登录返回的报文
			AddMonitor.Builder builder = AddMonitor.newBuilder();
			builder.addCallLetters("ALLUNIT");
			builder.addInfotypes(infotype);
			byte[] sendbuf = Serialize(MessageType.AddMonitor, builder.build().toByteString());
			return WriteContent(sendbuf);
		}
		return false;
	}
	public boolean AddAllmonitor(ArrayList<Integer> infotypes) {
		if (islogined) {
			//打登录返回的报文
			AddMonitor.Builder builder = AddMonitor.newBuilder();
			builder.addCallLetters("ALLUNIT");
			builder.addAllInfotypes(infotypes);
			byte[] sendbuf = Serialize(MessageType.AddMonitor, builder.build().toByteString());
			return WriteContent(sendbuf);
		}
		return false;
	}
	
	//发送命令(多个车同时发送同一命令)
	//终端升级 cmdid = 0xA5;
	//       params = [升级服务端IP(格式：192.168.10.1), 升级服务端端口号（格式：十进制字符串，如11223）, 升级文件名]
	//返回：发送给通信中心成功：指令唯一SN, 通信中心返回指令应答中有sn
	//                 失败：空字符串       
	public String SendCommand(ArrayList<String> callletterlist, int cmdid, ArrayList<String> params) {
		if (islogined) {
			SendCommand.Builder builder = cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.SendCommand.newBuilder();
			UUID uuid = UUID.randomUUID();
			String cmdsn = uuid.toString();
			builder.addAllCallLetters(callletterlist);
			builder.setCmdId(cmdid);
			builder.setSn(cmdsn);
			if (params != null && params.size() > 0) {
				builder.addAllParams(params);
			}
			byte[] sendbuf = Serialize(MessageType.SendCommand, builder.build().toByteString());
			if (WriteContent(sendbuf)) {
				return cmdsn;
			}
		}
		return "";
	}
}
