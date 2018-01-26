/*
********************************************************************************************
Discription: 保存命令到数据库采用线程操作，因为保存数据库较慢，要用批量操作
			  
Written By:   ZXZ
Date:         2015-06-05
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import cc.chinagps.gboss.database.DBUtil;

public class SaveCommandToDBThread extends Thread {
	
	private ArrayList<SendCommandInfo> savedbcmdlist = null;
	public int maxcount = 0;
	public int getCount() {
		return savedbcmdlist.size();
	}
	
	public SaveCommandToDBThread() {
		super("SaveCommandToDB");
		savedbcmdlist = new ArrayList<SendCommandInfo>(); 
	}

	//添加已经发送的命令或者失败的命令到数据库保存队列
	public void addSendCommandInfo(SendCommandInfo sendcmdinfo) {
		synchronized (savedbcmdlist) {
			savedbcmdlist.add(sendcmdinfo);
			if (maxcount < savedbcmdlist.size()) {
				maxcount = savedbcmdlist.size();
			}
		}
	}
	public void addSendCommandInfo(ArrayList<SendCommandInfo> sendcmdinfolist) {
		synchronized (savedbcmdlist) {
			savedbcmdlist.addAll(sendcmdinfolist);
			if (maxcount < savedbcmdlist.size()) {
				maxcount = savedbcmdlist.size();
			}
		}
	}
	
	// [start] 线程主函数，从等待队列一次性全部取出，再一个一个发送，写到ActiveMQ
	@Override
	public void run() {
		ArrayList<SendCommandInfo> tmplist = new ArrayList<SendCommandInfo>();
		while (true) {
			// 先把等待队列的命令写到MQ, 把等待队列同步到临时变量
			tmplist.clear();
			synchronized (savedbcmdlist) {
				tmplist.addAll(savedbcmdlist);
				savedbcmdlist.clear();
			}
			try {
				if (tmplist.isEmpty()) {// 如果没有命令发送,则休息5毫秒
					sleep(5);
				} else {
					SaveCommandToDB(tmplist);
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
	// [end] 线程主函数
	
	//批量保存指令到数据库
	private void SaveCommandToDB(ArrayList<SendCommandInfo> tmpcmdlist) {
		//最多100条一批
		int batchcount = 50;
		int first = 0;
		int last = batchcount;
		while(first < tmpcmdlist.size()) {
			if (last > tmpcmdlist.size()) {
				last = tmpcmdlist.size();
			}
			SaveCommandToDB(tmpcmdlist, first, last);
			first = last;
			last += batchcount;
		}
	}
	private void SaveCommandToDB(ArrayList<SendCommandInfo> tmpcmdlist, int first, int last) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO t_ss_send_cmd (cmd_sn,unit_id,call_letter,plate_no,op_id,op_name,op_src,op_srcversion,op_ip,cmd_id,cmd_time,gateway_id,send_params,MODE,send_time,send_flag,res_time,res_flag,res_params) VALUES ");
		for(int i=first; i<last; i++) {
			sb.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			if (i < last-1) {
				sb.append(',');
			} else {
				sb.append(';');
			}
		}
		String strSQL = sb.toString();
		sb = null;
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBUtil.openConnection();
			pst = conn.prepareStatement(strSQL);
			int ioffset = 0;
			for(int i=first; i<last; i++) {
				SendCommandInfo sendcmdinfo = tmpcmdlist.get(i);
				//cmd_sn,unit_id,call_letter,plate_no,op_id,op_name,op_src,op_srcversion,op_ip,cmd_id,
				pst.setString(ioffset + 1, sendcmdinfo.cmdsn + sendcmdinfo.cmdtime1);
				pst.setLong(ioffset   + 2, sendcmdinfo.unitid);
				pst.setString(ioffset + 3, sendcmdinfo.callletter);
				pst.setString(ioffset + 4, sendcmdinfo.plateno);
				pst.setString(ioffset + 5, sendcmdinfo.clientinfo.userid);
				pst.setString(ioffset + 6, sendcmdinfo.clientinfo.username);
				pst.setString(ioffset + 7, sendcmdinfo.clientinfo.clienttype1);
				pst.setString(ioffset + 8, sendcmdinfo.clientinfo.clientversion);
				pst.setString(ioffset + 9, sendcmdinfo.clientinfo.ipaddr);
				pst.setInt(ioffset    +10, sendcmdinfo.cmdId);
				//cmd_time,gateway_id,send_params,MODE,send_time,send_flag,res_time,res_flag,res_params
				java.sql.Timestamp cmdtimestamp = new java.sql.Timestamp(sendcmdinfo.cmdtime1);
				pst.setTimestamp(ioffset + 11, cmdtimestamp);
				pst.setInt(ioffset + 12, sendcmdinfo.channelId);
				if (sendcmdinfo.sendparams.size() == 0) {
					pst.setString(ioffset+ 13, "");
				} else {
					sb = new StringBuilder();
					for (String param : sendcmdinfo.sendparams) {
						if (sb.length() > 0) {
							sb.append('~');
						}
						sb.append(param);
					}
					pst.setString(ioffset + 13, sb.toString());
				}
				pst.setInt(ioffset + 14, sendcmdinfo.mode);

				java.sql.Timestamp sendtimestamp = new java.sql.Timestamp(sendcmdinfo.sendtime);
				pst.setTimestamp(ioffset + 15, sendtimestamp);

				pst.setInt(ioffset + 16, sendcmdinfo.sendcode);
				if (sendcmdinfo.restime <= 0) {
					sendcmdinfo.restime = System.currentTimeMillis();
				}
				java.sql.Timestamp restimestamp = new java.sql.Timestamp(sendcmdinfo.restime);
				pst.setTimestamp(ioffset + 17, restimestamp);

				pst.setInt(ioffset + 18, sendcmdinfo.rescode);
				pst.setString(ioffset + 19, sendcmdinfo.resmsg);
				
				ioffset += 19;
			}
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeDB(pst, conn);
		}
	}
}
