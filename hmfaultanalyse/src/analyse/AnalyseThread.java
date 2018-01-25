/*
********************************************************************************************
Discription:  从Hbase读海马历史故障，写到数据库，开始时间和结束时间，连续只写一条
			  
Written By:   ZXZ
Date:         2016-11-07
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package analyse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import analyse.DBFaultInfoManager.DBFaultInfo;
import hbase.FaultHistoryInfo;
import hbase.HbaseManager;
import hbase.FaultDataBuff.FaultDefine;
import hbase.FaultDataBuff.FaultInfo;
import utils.SystemConfig;
import utils.Util;

public class AnalyseThread extends Thread {
	public static AnalyseThread analysethread = null;
	private Connection hbaseconnection = null;
	private FaultHistoryInfo faulthistoryinfo = null;
	private long lasttime = 0;	//最后读的历史故障时间
	private long nowtime = 0;	//现在时间
	public  long maxtaketime = 0;	//最一次的花费时间
	
	public AnalyseThread() {
		super("HMFaultAnalyse");
		try {
			//从配置文件读最后时间
			String strlasttime = SystemConfig.getSystemProperties("lasttime");
			lasttime = Util.StringToDate(strlasttime).getTime();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//线程重载函数
	//读Hbase异常怎么处理，对统计结果有什么影响没有认真考虑，后续应认真考虑，
	//结束时间比开始时间小（可能重读Hbase的情况)
	@Override
	public void run() {
		ConcurrentHashMap<String, Boolean> unitinfomap = null;
		ArrayList<FaultInfo> faultlist = null;
		String strlasttime;
		while(true){
			try{
				//先从数据库读全部海马TBOX呼号
				unitinfomap = UnitInfo.getUnitInfo(false);
				//每个呼号从HBASE读历史故障
				nowtime = System.currentTimeMillis() - 10800000;	//只统计3小时以前的数据，尽量减少漏统计
				//一次最多扫描10天
				if (nowtime > (lasttime + 240 * 3600000)) {
					nowtime = lasttime + 240 * 3600000;
				}
				//记录一次循环最长时间
				long begintime = System.currentTimeMillis();
				hbaseconnection = ConnectionFactory.createConnection(HbaseManager.config);
				faulthistoryinfo = new FaultHistoryInfo(hbaseconnection);
				
				for(String callletter : unitinfomap.keySet()) {
					if (faultlist != null) {
						faultlist.clear();
					}
					if (faulthistoryinfo.GetHistoryInfo(callletter, lasttime, nowtime)) {
						faultlist = faulthistoryinfo.getFaultList();
						for(FaultInfo faultinfo : faultlist) {
							AnalyseFault(callletter, faultinfo);
						}
					}
				}
				hbaseconnection.close();;
				faulthistoryinfo = null;
				//把故障保存到数据库
				DBFaultInfoManager.SaveFaultInfo();
				lasttime = nowtime;
				//把最后时间写到配置文件
				strlasttime = Util.DatetoString(new Date(lasttime));
				SystemConfig.setSystemProperties("lasttime", strlasttime);
				System.out.println(strlasttime);
				
				//记录最长一次的时间
				begintime = System.currentTimeMillis() - begintime;
				if (maxtaketime < begintime) {
					maxtaketime = begintime;
				}
				//如果是统计比较早的数据，只间隔2分钟（程序启动时的情况）
				//统计到一天内后，间隔3小时统计一次
				if (lasttime < (System.currentTimeMillis() - 24*3600000)) {
					sleep(120000);		//3小时后统计一次
				} else {
					sleep(10800000);	//3小时后统计一次
				}
			} catch (Throwable e) {
				e.printStackTrace();
			} finally {
			}
		}
	}

	//分析每条故障信息，判断是否开始或结束
	/*
	 message FaultDefine {
	    optional int32 faultType = 1;           //故障类别
	    repeated string faultCode = 2;          //故障码
	}
	
	message FaultInfo {
	    required string callLetter = 1;         //车辆呼号
	    required int64 faultTime = 2;           //故障时间(从1970-1-1 0:0:0开始的秒数，格林威治时间), 为了提高处理速度,不用字符串
	    repeated FaultDefine faults = 3;        //新的分类故障码
	    optional int32 unitType = 4;            //终端协议类型
	    optional int32 history = 5;             //是否是黑匣子记录（0、或不存在表示是实时GPS，1:表示是黑匣子记录, 2:表示盲点补传）
	}
	*/
	private void AnalyseFault(String callletter, FaultInfo faultinfo) {
		ArrayList<DBFaultInfo> dbfaultlist = DBFaultInfoManager.dbfaultinfomap.get(callletter);
		if (dbfaultlist == null) {
			dbfaultlist = new ArrayList<DBFaultInfo>(); 
			DBFaultInfoManager.dbfaultinfomap.put(callletter, dbfaultlist);
		}
		//先判断已经有的故障是否已经结束，在新的故障中不存在
		boolean bfind = false;
		List<FaultDefine> faultdefinelist = faultinfo.getFaultsList(); // hbase当中这个呼号对应的故障
		for(DBFaultInfo dbfaultinfo : dbfaultlist) {
			if (dbfaultinfo.endtime > 0) {	//已经结束
				continue;
			}
			bfind = false;
			for(FaultDefine faultdefine : faultdefinelist) {
				if (faultdefine.getFaultType() == dbfaultinfo.faulttypeid) {  // mysql当中对应呼号故障类别 == hbase当中呼号的故障类别
					for(String faultcode : faultdefine.getFaultCodeList()) {  // 数据库当中的 呼号所对应的故障码
						if (faultcode.equals(dbfaultinfo.faultcode)) {
							bfind = true;
							break;
						}
					}
				}
			}
			if (!bfind) {	//如果没有找到，则表示故障结束
				dbfaultinfo.endtime = faultinfo.getFaultTime();
			}
		}
		//再判断新故障是否已经存在，不存在，则添加
		for(FaultDefine faultdefine : faultdefinelist) {
			for(String faultcode : faultdefine.getFaultCodeList()) {
				//在dbfaultlist中查找是否已经存在此故障, 类型和代码都相同
				bfind = false;
				for(DBFaultInfo dbfaultinfo : dbfaultlist) {
					if (dbfaultinfo.faulttypeid == faultdefine.getFaultType() &&  
						dbfaultinfo.faultcode.equals(faultcode)) {
						bfind = true;
					}
				}
				//如果新故障不存在, 则插入到数据库队列，等待保存
				if (!bfind) {
					DBFaultInfo newfault = new DBFaultInfo();
					newfault.callletter = callletter;
					newfault.starttime = faultinfo.getFaultTime();
					newfault.faulttypeid = (short)faultdefine.getFaultType();
					newfault.faultcode = faultcode;
					dbfaultlist.add(newfault);
				}
			}
		}
	}
}
