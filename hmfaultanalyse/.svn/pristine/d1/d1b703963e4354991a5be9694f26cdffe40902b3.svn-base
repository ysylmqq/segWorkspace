/*
 ********************************************************************************************
Discription: 故障管理类，
			 
			  
Written By:   ZXZ
Date:         2016-11-09
Version:      1.0

Modified by:
Modified Date:
Version:
 ********************************************************************************************
*/
package analyse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import database.DBUtil;
import utils.SystemConfig;

public class DBFaultInfoManager {

	// [start] 故障信息
	public static class DBFaultInfo {
		public String callletter;		//呼号
		public long starttime;			//开始时间
		public long endtime;			//结束时间
		public short faulttypeid; 		//故障类别,
		public String faultcode; 		//故障代码
		public int insertdb;			//是否插入数据库(第一次发生时) 0:没有， 1:正在插入(有可能失败)， 2:已经插入
		public int updatedb;			//是否结束数据库 0:没有， 1:正在更新(有可能失败)， 2:已经更新，可以删除
		
		public DBFaultInfo() {
			callletter = "";
			starttime = 0;
			endtime = 0;
			faulttypeid = 0;
			faultcode = "";
			insertdb = 0;
			updatedb = 0;
		};
	};
	// [end]
	
	//故障信息列表
	public static ConcurrentHashMap<String, ArrayList<DBFaultInfo>> dbfaultinfomap = new ConcurrentHashMap<String, ArrayList<DBFaultInfo>>();
	private static int batchsize = Integer.valueOf(SystemConfig.getSystemProperties("DBBatchSize"));
	
	// [start] 从数据库读已经开始但未结束的故障信息，系统如果重启，则上次未结束的故障要从数据库初始化
	/******************************************************************************************
	 */
	public static void Init() {
		Connection readcon = null; // 使用连接池
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "SELECT call_letter, starttime, faulttypeid, faultcode FROM t_hm_faultinfo WHERE subco_no=201 and endtime is null order by call_letter";
		String oldcallletter = "";
		String newcallletter = "";
		ArrayList<DBFaultInfo> faultlist = null;
		try {
			// 创建PreparedStatement
			readcon = DBUtil.getConnection();
			pst = readcon.prepareStatement(sql);
			// 查询
			rs = pst.executeQuery();
			while (rs.next()) {
				newcallletter = DBUtil.GetStringFromColumn(rs, 1);
				//如果是新的呼号
				if (!newcallletter.equals(oldcallletter) || faultlist == null) {
					faultlist = new ArrayList<DBFaultInfo>();
					dbfaultinfomap.put(newcallletter, faultlist);
					oldcallletter = newcallletter;
				}
				DBFaultInfo fault = new DBFaultInfo();
				fault.callletter = newcallletter;
				fault.starttime = DBUtil.GetLongFromTimeColumn(rs, 2);
				fault.faulttypeid = rs.getShort(3);
				fault.faultcode = DBUtil.GetStringFromColumn(rs, 4);
				fault.insertdb = 2;	//已经插入
				faultlist.add(fault);
			}
			DBUtil.closeDB(rs, pst, readcon);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeDB(rs, pst, readcon);
		}
	}
	
	//(1)把dbfaultinfomap中新的故障插入到数据库
	//(2)把dbfaultinfomap已经结束的故障更新数据库, 并且从map中删除
	public static void SaveFaultInfo() {
		insertFaultInfo();
		updateFaultInfo();
		ClearFaultInfo();
	}

	//批量插入新的故障信息到t_hm_faultinfo
	private static void insertFaultInfo() {
		Connection conn = null;
		PreparedStatement pst = null;
		int batchcount = 0;	//已经插入的数量, 201j是海马(第一版只统计海马）
		String strsql = "INSERT INTO t_hm_faultinfo (subco_no,starttime,faulttypeid,faultcode,call_letter) values (201,?,?,?,?)";
		try{
			for(ArrayList<DBFaultInfo> faultlist : dbfaultinfomap.values()) {
				for(DBFaultInfo faultinfo : faultlist) {
					if (faultinfo.insertdb == 1) {	//如果原来正在插入
						faultinfo.insertdb = 0;
					}
				}
			}
			conn = DBUtil.openConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement(strsql);
			for(ArrayList<DBFaultInfo> faultlist : dbfaultinfomap.values()) {
				for(DBFaultInfo faultinfo : faultlist) {
					if (faultinfo.insertdb >= 2) {	//如果已经插入
						continue;
					}
					pst.setTimestamp(1, new Timestamp(faultinfo.starttime));
					pst.setShort(2, faultinfo.faulttypeid);
					pst.setString(3, faultinfo.faultcode);
					pst.setString(4, faultinfo.callletter);
					pst.addBatch();	//注意批处理不要忘记写个条语名
					batchcount++;
					faultinfo.insertdb = 1;
					if(batchcount >= batchsize){
						pst.executeBatch();
						conn.commit();
						batchcount = 0;
						changeInsertFlag();
					}
				}
			}
			if(batchcount > 0){
				pst.executeBatch();
				conn.commit();
				changeInsertFlag();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeDB(pst, conn);
		}
	}
	//修改插入标志，将正在写入的改成已经保存的
	private static void changeInsertFlag() {
		for(ArrayList<DBFaultInfo> faultlist : dbfaultinfomap.values()) {
			for(DBFaultInfo faultinfo : faultlist) {
				if (faultinfo.insertdb == 1) {	//如正在插入
					faultinfo.insertdb = 2;	//已经插入
				}
			}
		}
	}

	
	//批量更新已经结束的故障信息(只要更新结束时间
	public static void updateFaultInfo() {
		for(ArrayList<DBFaultInfo> faultlist : dbfaultinfomap.values()) {
			for(DBFaultInfo faultinfo : faultlist) {
				if (faultinfo.updatedb == 1) {	//如果原来正在更新
					faultinfo.updatedb = 0;
				}
			}
		}
		Connection conn = null;
		PreparedStatement pst = null;
		int batchcount = 0;	//已经插入的数量
		String strsql = "UPDATE t_hm_faultinfo SET endtime=? where starttime=? and faulttypeid=? and faultcode=? and call_letter=?";
		try{
			conn = DBUtil.openConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement(strsql);
			for(ArrayList<DBFaultInfo> faultlist : dbfaultinfomap.values()) {
				for(DBFaultInfo faultinfo : faultlist) {
					if (faultinfo.updatedb >= 2 || faultinfo.endtime == 0) {	//如果未结束或者已经更新
						continue;
					}
					pst.setTimestamp(1, new Timestamp(faultinfo.endtime));
					pst.setTimestamp(2, new Timestamp(faultinfo.starttime));
					pst.setShort(3, faultinfo.faulttypeid);
					pst.setString(4, faultinfo.faultcode);
					pst.setString(5, faultinfo.callletter);
					pst.addBatch();	//注意批处理不要忘记写个条语名
					batchcount++;
					faultinfo.updatedb = 1;
					if(batchcount >= batchsize){
						pst.executeBatch();
						conn.commit();
						batchcount = 0;
						changeUpdateFlag();
					}
				}
			}
			if(batchcount > 0){
				pst.executeBatch();
				conn.commit();
				changeUpdateFlag();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeDB(pst, conn);
		}
	}
	//修改更新标志，将正在写入的改成已经保存的
	private static void changeUpdateFlag() {
		for(ArrayList<DBFaultInfo> faultlist : dbfaultinfomap.values()) {
			for(DBFaultInfo faultinfo : faultlist) {
				if (faultinfo.updatedb == 1) {	//如正在结束
					faultinfo.updatedb = 2;	//已经结束
				}
			}
		}
	}
	
	//从队列中删除已经结束的故障
	private static void ClearFaultInfo() {
		ArrayList<DBFaultInfo> faultlist = null;
		for(String callletter : dbfaultinfomap.keySet()) {
			faultlist = dbfaultinfomap.get(callletter); 
			for(int i=faultlist.size() - 1; i>=0; i--) {
				if (faultlist.get(i).updatedb == 2) {	//如已经结束，并且更新到数据库，则删除
					faultlist.remove(i);
				}
			}
			if (faultlist.isEmpty()) {
				dbfaultinfomap.remove(callletter);
			}
		}
	}

	/*
    public static void main(String[] args) {
    	Init();
    	for(Integer i=0; i<199; i++) {
    		ArrayList<DBFaultInfo> faultlist = new ArrayList<DBFaultInfo>();
    		dbfaultinfomap.put(i.toString(), faultlist);
    		for(Integer j=0; j<49; j++) {
    			DBFaultInfo faultinfo = new DBFaultInfo();
    			faultinfo.callletter = i.toString();
    			faultinfo.starttime = System.currentTimeMillis();
    			faultinfo.endtime = System.currentTimeMillis() + i.intValue() + j.intValue();
    			faultinfo.faulttypeid = j.shortValue();
    			faultinfo.faultcode = "B180200";
    			faultlist.add(faultinfo);
    		}
    	}
	    try {
	    	SaveFaultInfo();	    	
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
    }*/
}
