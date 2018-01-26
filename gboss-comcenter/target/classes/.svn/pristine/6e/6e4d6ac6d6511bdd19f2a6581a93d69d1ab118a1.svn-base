/*
********************************************************************************************
Discription:  性能测试时，指令发送和接收处理用单线程处理时，性能都不高，
 			    更新为多线程, 本线程是发送命令线程
			  
Written By:   ZXZ
Date:         2015-04-02
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.interprotocol.clienttest;

import java.util.ArrayList;

public class ClientCommandThread extends Thread {
	public ClientCommandThread() {
	}

	// [start] 线程主函数，从等待队列一次性全部取出，再一个一个发送，写到ActiveMQ
	@Override
	public void run() {
		String sn = null;
		while (true) {
			//只有登录成功才能发送指令
			ArrayList<String> callletterlist = new ArrayList<String>();
			callletterlist.add("13800000033");
			if (CenterClientHandler.clienthandler.isLogined()) {
				ArrayList<String> params = new ArrayList<String>();
				params.add("1");
				sn = CenterClientHandler.clienthandler.SendCommand(callletterlist, 0x0313, params); 
				if (sn==null || sn.isEmpty()) {
					System.out.println("send command 失败");
				} else {
					System.out.println("send command 成功:" + sn);
				}
			}
			callletterlist = null;
			sn = null;
			try {
				Thread.sleep(1000);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
	// [end] 线程主函数
}
