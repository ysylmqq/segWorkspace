/*
********************************************************************************************
Discription:  性能测试时，指令发送和接收处理用单线程处理时，性能都不高，
 			    更新为多线程, 本线程是处理命令返回的线程
			  
Written By:   ZXZ
Date:         2015-04-02
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.command;

import java.util.ArrayList;

import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.SendCommandSend_ACK;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.SendCommand_ACK;


public class CommandResponseThread extends Thread {
	public CommandResponseThread() {
		super("CommandResponseThread");
		responselist = new ArrayList<Object>();
	}
	
	private ArrayList<Object> responselist;

	//从ActiveMQ读到指令发送回应和终端应答后，添加到队列，等待线程处理
	public void appendResponse(Object obj) {
		try {
			synchronized (responselist) {
				responselist.add(obj);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	// [start] 线程主函数，从等待队列一次性全部取出，再一个一个处理，处理时要分发送回应，还是终端应答
	@Override
	public void run() {
		ArrayList<Object> tmplist = new ArrayList<Object>();
		while (true) {
			// 先把等待队列的命令写到MQ, 把等待队列同步到临时变量
			tmplist.clear();
			synchronized (responselist) {
				tmplist.addAll(responselist);
				responselist.clear();
			}
			if (tmplist.isEmpty()) {// 如果没有命令发送,则休息5毫秒
				try {
					sleep(5);
				} catch (Throwable e) {
					e.printStackTrace();
				}
				continue;
			}
			//因为ActiveMQ, 最好是批量写，所以先插入数据库
			for(Object obj : tmplist) {
				try {
					if (obj instanceof SendCommandSend_ACK) {
						CommandManager.commandmanager.handleRresponseCommand((SendCommandSend_ACK)obj);
					} else if (obj instanceof SendCommand_ACK) {
						CommandManager.commandmanager.handleRresponseCommand((SendCommand_ACK)obj);
					}
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
	}
	// [end] 线程主函数
}
