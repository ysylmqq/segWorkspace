/*
********************************************************************************************
Discription:  Hbase总体入口，全局一个实例，生成读历史信息和最后信息的线程
              HBASE操作用线程处理。从客户端收到请求后，把请求放入队列，根据队列，再用从Hbase读信息，写回客户端发送缓存区
                                                用线程操作（长时间）不会影响客户端连接事件  
			  
Written By:   ZXZ
Date:         2014-05-30
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.hbase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

import cc.chinagps.gboss.comcenter.WebsocketClientInfo;
import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.GetCommandHistory_ACK;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage;
import cc.chinagps.gboss.comcenter.buff.MessageType;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.database.DBUtil;
import cc.chinagps.lib.util.BigHash;

public class HbaseClientManager {
	public static HbaseClientManager hbaseclientmanager = null;
	private static int HISTORYTHREADCOUNT = 10;	 //取历史信息的线程数量
	private static int LASTTHREADCOUNT = 4;	 	 //取最后信息的线程数量

	public Configuration config;
	private GetHistoryInfoThread[] historyThreads;
	private GetLastInfoThread[] lastThreads;

	//构造函数
	public HbaseClientManager() {
		config = HBaseConfiguration.create();
		historyThreads = new GetHistoryInfoThread[HISTORYTHREADCOUNT];
		lastThreads = new GetLastInfoThread[LASTTHREADCOUNT];
	}
	
	//开始读Hbase的线程
	public boolean start() {
		try {
			for(int i=0; i<HISTORYTHREADCOUNT; i++) {
				historyThreads[i] = new GetHistoryInfoThread("GetHistoryInfo" + i);
				historyThreads[i].start();
				System.out.printf("get history info thread %d started!%n", i);
			}
			for(int i=0; i<LASTTHREADCOUNT; i++) {
				lastThreads[i] = new GetLastInfoThread("GetLastInfo" + i);
				lastThreads[i].start();
				System.out.printf("get last info thread %d started!%n", i);
			}
			System.out.println("Hbase start.");
			return true;
		} catch (Throwable e) {
			System.out.println("Hbase not start: ");
			e.printStackTrace();
		}
		return false;
	}
	
	//将客户端请求添加到读最后信息的队列
	public void appendLastInfo(WebsocketClientInfo clientinfo, String callletter, int infotype, String sn) {
		int hashcode = (int)(BigHash.bigHash.hash(callletter.getBytes()) % LASTTHREADCOUNT);
		lastThreads[hashcode].AppendLastInfo(clientinfo, callletter, infotype, sn);
	}
	//添加历史请求到线程队列
	public void appendHistoryInfo(WebsocketClientInfo clientinfo, String callletter, int infotype, 
		long starttime, long endtime, int pagenumber, int totalnumber, boolean autonextpage,
		String sn, boolean reversed, boolean norepeat) {
		int hashcode = (int)(BigHash.bigHash.hash(callletter.getBytes()) % HISTORYTHREADCOUNT);
		historyThreads[hashcode].appendHistoryInfo(clientinfo, callletter, infotype,
				starttime, endtime, pagenumber, totalnumber, autonextpage, sn, reversed, norepeat);
	}
	
	//将下一页请求到线程队列
	public int nextHistoryInfo(WebsocketClientInfo clientinfo, String callletter, int infotype, String sn) {
		int hashcode = (int)(BigHash.bigHash.hash(callletter.getBytes()) % HISTORYTHREADCOUNT);
		return historyThreads[hashcode].nextHistoryInfo(clientinfo, callletter, infotype, sn);
	}
	
	//终止历史信息请求
	public int stopHistoryInfo(WebsocketClientInfo clientinfo, String callletter, int infotype, String sn) {
		int hashcode = (int)(BigHash.bigHash.hash(callletter.getBytes()) % HISTORYTHREADCOUNT);
		return historyThreads[hashcode].stopHistoryInfo(clientinfo, callletter, infotype, sn);
	}
	
	public int GetCommandHistory(WebsocketClientInfo clientinfo, String callletter, long starttime, long endtime) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int nret = ResultCode.OK; 
		try {
			String sql = "SELECT plate_no, op_name, op_src, op_ip, cmd_id, cmd_time, gateway_id, MODE, send_time, " +
		                 "send_flag, res_time, res_flag, res_params FROM t_ss_send_cmd " + 
		                 "WHERE call_letter=? and cmd_time>=? and cmd_time<=? order by cmd_time";  
			java.sql.Timestamp startdate = new java.sql.Timestamp(starttime);
			java.sql.Timestamp enddate = new java.sql.Timestamp(endtime);
			conn = DBUtil.openConnection();
			pst = conn.prepareStatement(sql);
			// 设置参数
			pst.setString(1, callletter);
			pst.setTimestamp(2, startdate);
			pst.setTimestamp(3, enddate);
			rs = pst.executeQuery();
			
			GetCommandHistory_ACK.Builder historyack = GetCommandHistory_ACK.newBuilder();
			historyack.setRetcode(ResultCode.OK);
			historyack.setRetmsg("成功");
			historyack.setCallLetter(callletter);
			while (rs.next()) {
				try {
					String strtemp = DBUtil.GetStringFromColumn(rs, 1);
					if (strtemp != null && strtemp.length() > 0) {
						historyack.setPlateno(strtemp.trim());
					}
					GetCommandHistory_ACK.CommandHistoryInfo.Builder command = GetCommandHistory_ACK.CommandHistoryInfo.newBuilder();
					strtemp = DBUtil.GetStringFromColumn(rs, 2);
					if (strtemp != null && strtemp.length() > 0) {
						command.setOpName(strtemp.trim());
					}
					strtemp = DBUtil.GetStringFromColumn(rs, 3);
					if (strtemp != null && strtemp.length() > 0) {
						command.setOpSrc(strtemp.trim());
					}
					strtemp = DBUtil.GetStringFromColumn(rs, 4);
					if (strtemp != null && strtemp.length() > 0) {
						command.setOpIp(strtemp.trim());
					}
					command.setCmdId(rs.getInt(5));
					long time = DBUtil.GetLongFromTimeColumn(rs, 6);
					if (time > 0) {
						command.setCmdTime(time);
					}
					command.setGatewayId(rs.getInt(7));
					command.setMode(rs.getInt(8));
					time = DBUtil.GetLongFromTimeColumn(rs, 9);
					if (time > 0) {
						command.setSendTime(time);
					}
					command.setSendFlag(rs.getInt(10));
	
					time = DBUtil.GetLongFromTimeColumn(rs, 11);
					if (time > 0) {
						command.setResTime(time);
					}
					command.setResFlag(rs.getInt(12));
					strtemp = DBUtil.GetStringFromColumn(rs, 13);
					if (strtemp != null && strtemp.length() > 0) {
						command.setResMsg(strtemp.trim());
					}
					historyack.addCommandinfoes(command.build());
				} catch (Exception e) {
					e.printStackTrace();
					nret = ResultCode.DataBase_Error;
				}
			}
			ComCenterMessage.ComCenterBaseMessage.Builder basemsg = ComCenterMessage.ComCenterBaseMessage.newBuilder();
			basemsg.setId(MessageType.GetCommandHistory_ACK);
			basemsg.setContent(historyack.build().toByteString());
			clientinfo.appendSendMessage(basemsg.build());
		} catch (Exception e) {
			e.printStackTrace();
			nret = ResultCode.DataBase_Error;
		}
		DBUtil.closeDB(rs, pst, conn);
		return nret;
	}
}
