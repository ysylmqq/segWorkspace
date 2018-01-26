/*
 ********************************************************************************************
Discription:  警情分析主线程
			       从数据库更新分析设置(根据数据库不同而不同)
			警情判断流程： 
			(1) 先用呼号判断是否总是某警，或总不是某警

			  
Written By:   ZXZ
Date:         2014-09-09
Version:      1.0

Modified by:
Modified Date:
Version:
 ********************************************************************************************
*/
package cc.chinagps.gboss.alarmarray;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;

import cc.chinagps.gboss.comcenter.UnitInfoManager;
import cc.chinagps.gboss.comcenter.UnitInfoManager.UnitInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsInfo;
import cc.chinagps.gboss.database.DBUtil;

public class AlarmAnalyse extends Thread {
	
	/*
	 * 警情状态信息
	 */
	static class StatusAlarm {
		public int statusid;
		public String statusname;
		public short begintime;
		public short endtime;
		public short level;
		public boolean flag;		//是否必须报警，除呼号规定以外，其他的呼号设置、机构过滤、行业过滤都不考虑
		public boolean isdeleted;

		public StatusAlarm() {
			statusid = 0;
			statusname = "";
			begintime = 0;
			endtime = 0;
			level = 1;
			flag = false;
			isdeleted = false;
		};
		
		public StatusAlarm(StatusAlarm alarm) {
			this.statusid = alarm.statusid;
			this.statusname = alarm.statusname;
			this.begintime = alarm.begintime;
			this.endtime = alarm.endtime;
			this.level = alarm.level;
			this.flag = alarm.flag;
			this.isdeleted = alarm.isdeleted;
		};
	};

	/*
	 * 警情结果类型
	 */
	public static final short ALARMFLAG = 0;	//是警情，并要处警
	public static final short NOALARMFLAG = 1;	//不是警情
	public static final short DATABASEFLAG = 2;	//是警情，只要保存数据库，不用处警
	
	/*
	 * 根据呼号判断，某些呼号一直是警情(如被盗抢车)，或者某些呼号一直不作为警情(例如测试车)
	 */
	private static class CallletterStatus {
		public String callLetter;
		public long stoptime;
		public int alarmid;
		public short flag;
		public boolean isdeleted;
		public CallletterStatus() {
			callLetter = "";
			stoptime = 0;
			flag = ALARMFLAG;
			alarmid = 0;
			isdeleted = false;
		}
	};

	/*
	 * 呼号、机构、行业过滤规则
	 */
	private static class AlarmSetInfo {
		public ArrayList<Integer> statuses;
		public long analyseid;
		public short flag;
		public short begintime;
		public short endtime;
		public boolean isdeleted;

		public AlarmSetInfo() {
			statuses = new ArrayList<Integer>();
			analyseid = 0;
			flag = 0;
			begintime = 0;
			endtime = 0;
			isdeleted = false;
		}
	};

	/*
	 * 呼号设置规则
	 */
	private static class VehicleAlarm {
		public String callLetter;
		public ArrayList<AlarmSetInfo> filterlist;
		public VehicleAlarm() {
			callLetter = "";
			filterlist = new ArrayList<AlarmSetInfo>();
		}
	};

	/*
	 * 机构设置规则
	 */
	private static class OrgAlarm {
		public String orgcode;
		public ArrayList<AlarmSetInfo> filterlist;
		public OrgAlarm() {
			orgcode = "";
			filterlist = new ArrayList<AlarmSetInfo>();
		}
	};

	/*
	 * 行业过滤规则
	 */
	private static class TradeAlarm {
		public short trade;
		public ArrayList<AlarmSetInfo> filterlist;
		public TradeAlarm() {
			trade = 0;
			filterlist = new ArrayList<AlarmSetInfo>();
		}
	};

	/***************************************************************************************
	 * 所有设置信息都从数据库定时更新
	 */
	// ConcurrentHashMap是线程安全的HashMap
	//private ConcurrentHashMap<String, UnitInfo> callerinfomap; // 终端对应机构，行业，是否停机
	//private long callerinfolasttime;
	private ConcurrentHashMap<String, CallletterStatus> callletterstatusmap; // 呼号规则0判断警情列表
	private long vehiclestatuslasttime;
	private ConcurrentHashMap<String, VehicleAlarm> vehiclealarmmap; // 呼号规则判断警情列表
	private long vehiclealarmlasttime;
	private ConcurrentHashMap<String, OrgAlarm> orgalarmmap; // 机构规则判断警情列表
	private long orgalarmlasttime;
	private ConcurrentHashMap<Short, TradeAlarm> tradealarmmap; // 行业规则判断警情列表
	private long tradealarmlasttime;
	private ConcurrentHashMap<Integer, StatusAlarm> statusmap; // 状态警情(按照级别高低排列，1最高，3低)
	private long statuslasttime;

	public AlarmAnalyse() {
		super("ReadAlarmFilterFromDatabase");
		//callerinfomap = new ConcurrentHashMap<String, UnitInfo>();
		//callerinfolasttime = 0;
		callletterstatusmap = new ConcurrentHashMap<String, CallletterStatus>();
		vehiclestatuslasttime = 0;
		vehiclealarmmap = new ConcurrentHashMap<String, VehicleAlarm>();
		vehiclealarmlasttime = 0;
		orgalarmmap = new ConcurrentHashMap<String, OrgAlarm>();
		orgalarmlasttime = 0;
		tradealarmmap = new ConcurrentHashMap<Short, TradeAlarm>();
		tradealarmlasttime = 0;
		statusmap = new ConcurrentHashMap<Integer, StatusAlarm>();
		statuslasttime = 0;
	}
	
	/****************************************************************************************************
	 * 从警情ID取警情名称
	 */
	public String getAlarmName(int alarmid) {
		StatusAlarm alarm = statusmap.get(alarmid);
		if (alarm == null) {
			return "";
		}
		return alarm.statusname;
	}

	/****************************************************************************************************
	 * 下面是警情判断模块
	 */
	/*
	 * 判断警情的结果
	 */
	public class AlarmResult {
		public UnitInfo unitinfo; 	// 终端信息
		public StatusAlarm alarm; 	// 警情状态
		public short flag; 			// 0: 为警情、1: 不作为警情、2: 作为警情、但只保存数据库，不分配给坐席处警
		private short gpstime; 		// GPS时间（用于判断白天、晚上）, 为了提高性能，不要每次判断都计算

		public AlarmResult(GpsInfo gpsinfo) { // 初始化为不是警情
			unitinfo = UnitInfoManager.unitinfomanager.getUnitInfo(gpsinfo.getCallLetter());

			alarm = null;
			flag = NOALARMFLAG;
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(gpsinfo.getBaseInfo().getGpsTime());
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			int minute = calendar.get(Calendar.MINUTE);
			gpstime = (short) ((hour * 60) + minute);
		};
	};

	/*
	 * 判断GPS时间是否要判断白天或晚上
	 */
	public static boolean compareAlarmTime(short begin, short end, short time) {
		if (begin != end) {
			if (begin < end) {
				return (begin <= time && time <= end);
			} else {
				return (begin <= time || time <= end);
			}
		}
		return true;
	}

	/****************************************************************************************************
	 * 处理某GPS，先判断是不是警情，再判断是否要写保存数据库，或者分发到坐席
	 */
	public void analyse(GpsInfo gpsinfo) {
		AlarmResult alarmresult = new AlarmResult(gpsinfo);
		//如果没有找到呼号记录，或者已经离网, 不分析警情
		if (alarmresult.unitinfo == null || alarmresult.unitinfo.isoutservice()) {
			return;
		}
		// 先判断是否有警情
		analyse(gpsinfo, alarmresult);
		// 警情不用处理
		if (alarmresult.flag == NOALARMFLAG || alarmresult.alarm == null)
			return;
		// 添加到警情队列(或者只保存到数据库)
		AlarmManager.alarmmanager.appendAlarm(alarmresult, gpsinfo);
	}

	/****************************************************************************************************
	 * 判断某GPS是不是警情, 行业先不分析
	 */
	private void analyse(GpsInfo gpsinfo, AlarmResult alarmresult) {
		// 根据呼号判断，如果此车一直是警情, 或者一直不是警情（如测试）
		// t_al_vehiclestatus
		if (analyseCallletterStatus(gpsinfo, alarmresult))
			return;
		// 如果某状态一定是报警状态(在状态表中定义)
		// t_al_status 中flag定义
		if (analyseMustStatus(gpsinfo, alarmresult))
			return;
		// 如果有呼号状态设置规则
		// t_al_vehiclealarm
		if (analyseVehicleAlarm(gpsinfo, alarmresult))
			return;
		// 如果有机构状态设置规则
		// t_al_org_alarm
		if (analyseOrgAlarm(gpsinfo, alarmresult))
			return;
		//一般状态判断, 根据数据库中状态, 但要过滤行业规则
		//t_al_status
		analyseStatus(gpsinfo, alarmresult);
	}

	/*
	 * 判断呼号是不是一直是警情，或者一直不是警情？, t_al_vehiclestatus
	 */
	private boolean analyseCallletterStatus(GpsInfo gpsinfo, AlarmResult alarmresult) {
		CallletterStatus callletterstatus = callletterstatusmap.get(gpsinfo.getCallLetter());
		// 如果没找到，则没有判断
		if (callletterstatus == null)
			return false;
		// 如果时间已经过期，则不判断
		if (callletterstatus.isdeleted	|| callletterstatus.stoptime < System.currentTimeMillis())
			return false;
		//此呼号不用判断警情(可以是测试车等)
		alarmresult.flag = callletterstatus.flag;
		if (callletterstatus.flag != NOALARMFLAG) {
			alarmresult.alarm = statusmap.get(callletterstatus.alarmid);
		}
		return true;
	}

	/*
	 * 判断某状态是必须为警情，不管是否离网，什么行业、机构（如盗警、劫警） t_al_status 中flag定义
	 */
	private boolean analyseMustStatus(GpsInfo gpsinfo, AlarmResult alarmresult) {
		short level = AlarmManager.AlarmListCount + 1;
		for (int statusid : gpsinfo.getBaseInfo().getStatusList()) {
			StatusAlarm statusalarm = statusmap.get(statusid);
			//要找最高级别的报警
			if (statusalarm == null) {
				continue;
			}
			if (!statusalarm.flag) {
				//不是必须报警的警情ID
				continue;
			}
			if (statusalarm.level >= level || statusalarm.level <= 0) {
				continue;
			}
			if (compareAlarmTime(statusalarm.begintime, statusalarm.endtime, alarmresult.gpstime)) {
				//在报警时间范围内
				level = statusalarm.level;
				alarmresult.alarm = statusalarm;
				alarmresult.flag = ALARMFLAG;	//一定是警情(这种判断，不设置非警情)
				if (level == 1) {	//如果已经是最高级警情，则不用再循环
					break;
				}
			}
		}
		return (alarmresult.alarm != null);
	}

	/*
	 * 判断是否有经过呼号设置, 呼号没有过滤条件，只有设置条件, 但要查找出最高级别 如果车辆有设置，就不再判断机构和行业 public
	 * ArrayList<Integer> statuses; //状态队列 public long analyseid; //分析ID public
	 * short filter; //0: 为警情、1: 不作为警情、2: 作为警情、但只保存数据库，不分配给坐席处警 public short
	 * begintime; //每天开始时间(从0时开始的分钟), 如果daybegin和dayend都==0，表示全天 public short
	 * endtime; //每天结束时间(从0时开始的分钟) public boolean isdeleted; //是否被删除 public
	 * AlarmSetInfo() { statuses = new ArrayList<Integer>(); analyseid = 0;
	 * filter = 0; begintime = 0; endtime = 0; isdeleted = false; }
	 * 判断结果，警情都是1级警情，不
	 * t_al_vehiclealarm
	 */
	private boolean analyseVehicleAlarm(GpsInfo gpsinfo, AlarmResult alarmresult) {
		VehicleAlarm vehiclealarm = vehiclealarmmap.get(gpsinfo.getCallLetter());
		//如果此呼号设置了判断条件,则不再进行机构判断、普通(行业过滤)判断
		if (vehiclealarm != null) {
			// 判断每条设置条件
			for (AlarmSetInfo setinfo : vehiclealarm.filterlist) {
				if (!compareAlarmTime(setinfo.begintime, setinfo.endtime, alarmresult.gpstime)) // 如果不在时间段内
					continue;
				//判断设置状态是否和GPS状态有相同
				for(int gpsstatus : gpsinfo.getBaseInfo().getStatusList()) {
					for (int setstatus : setinfo.statuses) {
						if (gpsstatus == setstatus) {
							StatusAlarm statusalarm = statusmap.get(gpsstatus);
							if (statusalarm != null) {
								StatusAlarm alarm = new StatusAlarm(statusalarm);
								alarm.level = 1;
								alarmresult.alarm = alarm;
								alarmresult.flag = setinfo.flag;	//根据呼号设置是警情，还是只要保存到数据库
								return true;
							}
						}
					}
				}
			}
			return true;
		}
		return false;
	}

	/*
	 * 只判断私家车、或集团用户一级机构
	 */
	private OrgAlarm getOrgAlarm(String unitorg) {
		OrgAlarm orgalarm = orgalarmmap.get(unitorg);
		if (orgalarm == null) {
			int orgcodelen = 0;
			//找到最长的orgcode; 表示最接近
			for(String orgcode : orgalarmmap.keySet()) {
				if ((orgcode.length() > orgcodelen) && unitorg.startsWith(orgcode)) {
					orgalarm = orgalarmmap.get(orgcode);
					orgcodelen = orgcode.length();
				}
			}
		}
		return orgalarm; 
	}
	private boolean analyseOrgAlarm(GpsInfo gpsinfo, AlarmResult alarmresult) {
		OrgAlarm orgalarm = getOrgAlarm(alarmresult.unitinfo.orgcodes);
		if (orgalarm == null) {
			return false;
		}
		// 判断每条设置条件
		for (AlarmSetInfo info : orgalarm.filterlist) {
			if (info.isdeleted)	//被删除的不能包含在队列中
				continue;
			if (!compareAlarmTime(info.begintime, info.endtime,	alarmresult.gpstime)) // 如果不在时间段内
				continue;
			for(int gpsstatus : gpsinfo.getBaseInfo().getStatusList()) {
				for (int status : info.statuses) {
					if (gpsstatus == status ) { //|| status == -1) {
						StatusAlarm statusalarm = statusmap.get(gpsstatus);
						if (statusalarm != null) {
							StatusAlarm alarm = new StatusAlarm(statusalarm);
							alarm.level = 1;
							alarmresult.alarm = alarm;
							alarmresult.flag = info.flag;
							return true;
						}
					}
				}
			}
		}
		return true;	//可以不是警情，但经过了机构判断
	}

	/*
	 * 判断是否有经过行业过滤 , 行业只是过滤 先判断gpsinfo中是否有警情，同时判断此警情是否在行业的过滤规则中
	 */
	private boolean analyseStatus(GpsInfo gpsinfo, AlarmResult alarmresult) {
		short level = AlarmManager.AlarmListCount + 1;
		TradeAlarm tradealarm = tradealarmmap.get(alarmresult.unitinfo.trade);
		for (int statusid : gpsinfo.getBaseInfo().getStatusList()) {
			StatusAlarm statusalarm = statusmap.get(statusid);
			if (statusalarm != null && statusalarm.level > 0 && statusalarm.level <= AlarmManager.AlarmListCount) {
				//如果不在时间内
				if (!compareAlarmTime(statusalarm.begintime, statusalarm.endtime, alarmresult.gpstime)) {
					continue;
				}
				// 如果警情级别比原来的低（数值大）
				if (statusalarm.level >= level)
					continue;
				// 判断行业规则中是否有过滤, 如果有行业过滤规则
				if (tradeIsFilter(statusid, tradealarm, alarmresult)) {
					continue;
				}
				level = statusalarm.level;
				alarmresult.alarm = statusalarm;
				alarmresult.flag = ALARMFLAG;
				if (level == 1) {	//如果已经是最高级警情，则不用再循环
					break;
				}
			}
		}
		return (alarmresult.alarm != null);
	}

	// 判断某行业中，某警情是否要过滤
	private boolean tradeIsFilter(int alarmid, TradeAlarm tradealarm, AlarmResult alarmresult) {
		if (tradealarm == null) {	//未设置
			return false;
		}
		for (AlarmSetInfo info : tradealarm.filterlist) {
			if (compareAlarmTime(info.begintime, info.endtime, alarmresult.gpstime)) {// 如果在时间段内
				for (int status : info.statuses) {
					if (status == alarmid)
						return true; // 如果ID相同，表示要过滤
				}
			}
		}
		// 表示没有过滤
		return false;
	}

	/****************************************************************************************************
	 * 线程，实时更新警情判断条件
	 * 
	 */
	@Override
	public void run() {
		// 定时从数据库更新警情分析设置
		Connection readcon = null; // 使用连接池
		int nday = 0;
		while (true) {
			try {
				//每天全部更新一次(早上3点开始更新)
				Calendar calendar = Calendar.getInstance();
				int tmpday = calendar.get(Calendar.DAY_OF_MONTH);
				int hour = calendar.get(Calendar.HOUR_OF_DAY);
				if (tmpday != nday && hour >= 3) {
					vehiclestatuslasttime = 0;
					vehiclealarmlasttime = 0;
					orgalarmlasttime = 0;
					tradealarmlasttime = 0;
					statuslasttime = 0;
					nday = tmpday;
				}
				//开始更新
				readcon = DBUtil.openConnection();
				if (readcon != null) {
					readStatus(readcon);
					readVehicleStatus(readcon);
					readVehicleAlarm(readcon);
					readOrgAlarm(readcon);
					readTradeAlarm(readcon);
				}
			} catch (Exception e) {
				// 出现异常，关闭连接
				e.printStackTrace();
				nday = 0;	//重新全部读
			} finally {
				DBUtil.closeDB(readcon);
			}
			try {
				if (readcon != null) {
					readcon.close();
					readcon = null;
				}
				// 等待5秒，重新读
				Thread.sleep(5000);
			} catch (Exception e) {
			}
		}
	}

	/*
	 * 将 HH:MM格式字符串变成short(从0时0分开始的分钟数)
	 */
	private short getMinutes(String strHourMinute) {
		int ret = 0;
		if (strHourMinute != null) {
			String[] strlist = strHourMinute.split("[:;, ]");
			if (strlist.length == 2) {
				ret = Integer.valueOf(strlist[0]) * 60;
				ret += Integer.valueOf(strlist[1]);
			}
		}
		return (short) ret;
	}

	/*
	 * 将状态字符串(用分号隔开), 转换成状态数组
	 */
	private ArrayList<Integer> getStatuses(String statuses) {
		ArrayList<Integer> ret = new ArrayList<Integer>();
		String[] statuslist = statuses.split("[:;, ]");
		if (statuslist.length > 0) {
			for (String status : statuslist) {
				int i = Integer.valueOf(status);
				if (i > 0) {
					ret.add(Integer.valueOf(status));
				}
			}
		}
		return ret;
	}

	/*****************************************************************************************
	 * 从数据库读车辆状态最新信息(状态是否是警情)，插入队列
	 */
	private void readStatus(Connection readcon) throws Exception {
		PreparedStatement pst = null;
		ResultSet rs = null;
		boolean ball = (statuslasttime == 0);
		try {
			String sql = "SELECT status_id, status_name, LEVEL, flag, begin_time, end_time, is_del, stamp FROM t_al_status WHERE stamp > ? ORDER BY status_id";
			// 创建PreparedStatement
			pst = readcon.prepareStatement(sql);
			java.sql.Timestamp lastdate = new java.sql.Timestamp(statuslasttime);
			// 设置参数
			pst.setTimestamp(1, lastdate);
			// 查询
			rs = pst.executeQuery();
			ArrayList<StatusAlarm> statuslist = new ArrayList<StatusAlarm>();
			while (rs.next()) {
				StatusAlarm status = new StatusAlarm();
				status.statusid = rs.getInt(1);
				status.statusname = DBUtil.GetStringFromColumn(rs, 2);
				status.level = rs.getShort(3);
				status.flag = rs.getBoolean(4);
				status.begintime = getMinutes(DBUtil.GetStringFromColumn(rs, 5));
				status.endtime = getMinutes(DBUtil.GetStringFromColumn(rs, 6));
				status.isdeleted = rs.getBoolean(7);
				long last = rs.getTimestamp(8).getTime();
				if (last > statuslasttime) {
					statuslasttime = last;
				}
				statuslist.add(status);
			}
			DBUtil.closeDB(rs, pst);
			// 如果有新的状态设置
			if (ball) {
				statusmap.clear();
			}
			if (statuslist.size() > 0) {
				refreshStatusAlarm(statuslist);
			}
		} finally {
			DBUtil.closeDB(rs, pst);
		}
	}

	private void refreshStatusAlarm(ArrayList<StatusAlarm> statuslist) {
		try {
			for (StatusAlarm statusalarm : statuslist) {
				// 如果某个状态被删删除或变成非警情状态, 
				//因为呼号、机构设置时，可以把普通状态设置为警情，所以还需保留
				//if (statusalarm.isdeleted || statusalarm.level == 0) {
				if (statusalarm.isdeleted) {
					statusmap.remove(statusalarm.statusid);
				} else { // 否则插入队列
					statusmap.put(statusalarm.statusid, statusalarm);
				}
			}
		} catch (Exception e) {
		}
	}

	/**********************************************************************************
	 * 从数据库读某终端是否一直是警情，还是一直不用判断警情
	 */
	private void readVehicleStatus(Connection readcon) throws Exception {
		PreparedStatement pst = null;
		ResultSet rs = null;
		boolean ball = (vehiclestatuslasttime == 0);
		try {
			String sql = "SELECT call_letter, flag, status_id, end_time, is_del, stamp FROM	t_al_vehiclestatus WHERE stamp > ? ORDER BY call_letter";
			// 创建PreparedStatement
			pst = readcon.prepareStatement(sql);
			Timestamp lastdate = new java.sql.Timestamp(vehiclestatuslasttime);
			// 设置参数
			pst.setTimestamp(1, lastdate);
			// 查询
			rs = pst.executeQuery();
			ArrayList<CallletterStatus> vehiclestatuslist = new ArrayList<CallletterStatus>();
			while (rs.next()) {
				CallletterStatus status = new CallletterStatus();
				status.callLetter = DBUtil.GetStringFromColumn(rs, 1);
				status.flag = rs.getShort(2);
				status.alarmid = rs.getInt(3);
				Timestamp stoptime = rs.getTimestamp(4); 
				if (stoptime == null) {
					status.stoptime = Long.MAX_VALUE;
				} else {
					status.stoptime = stoptime.getTime();
				}
				status.isdeleted = rs.getBoolean(5);
				long last = rs.getTimestamp(6).getTime();
				if (last > vehiclestatuslasttime) {
					vehiclestatuslasttime = last;
				}
				vehiclestatuslist.add(status);
			}
			DBUtil.closeDB(rs, pst);
			if (ball) {
				callletterstatusmap.clear();
			}
			// 如果有新的状态设置
			if (vehiclestatuslist.size() > 0) {
				refreshVehicleStatus(vehiclestatuslist);
			}
		} finally {
			DBUtil.closeDB(rs, pst);
		}
	}

	private void refreshVehicleStatus(ArrayList<CallletterStatus> vehiclestatuslist) {
		try {
			for (CallletterStatus status : vehiclestatuslist) {
				// 如果某个状态被删删除或变成非警情状态
				if (status.isdeleted || status.stoptime < System.currentTimeMillis()) {
					callletterstatusmap.remove(status.callLetter);
				} else { // 否则插入队列
					callletterstatusmap.put(status.callLetter, status);
				}
			}
		} catch (Exception e) {
		}
	}

	/************************************************************************************
	 * 从数据库读最新呼号警情过滤信息，更新到内存队列中 先从数据库读最新的过滤条件，根据过滤ID判断
	 */
	private void readVehicleAlarm(Connection readcon) throws Exception {
		PreparedStatement pst = null;
		ResultSet rs = null;
		boolean ball = (vehiclealarmlasttime == 0);
		try {
			String sql = "SELECT analyse_id, call_letter, flag, status_ids, begin_time, end_time, is_del, stamp FROM t_al_vehiclealarm  WHERE stamp > ? ORDER BY call_letter, analyse_id";
			// 创建PreparedStatement
			pst = readcon.prepareStatement(sql);
			java.sql.Timestamp lastdate = new java.sql.Timestamp(vehiclealarmlasttime);
			// 设置参数
			pst.setTimestamp(1, lastdate);
			// 查询
			rs = pst.executeQuery();
			ArrayList<VehicleAlarm> alarmlist = new ArrayList<VehicleAlarm>();
			VehicleAlarm vehiclealarm = null;
			String oldcaller = "";
			String tmpcaller = "";
			while (rs.next()) {
				tmpcaller = DBUtil.GetStringFromColumn(rs, 2);
				if (tmpcaller.isEmpty()) {
					continue;
				}
				AlarmSetInfo filter = new AlarmSetInfo();
				filter.analyseid = rs.getLong(1);
				filter.flag = rs.getShort(3);
				filter.statuses = getStatuses(DBUtil.GetStringFromColumn(rs, 4));
				filter.begintime = getMinutes(DBUtil.GetStringFromColumn(rs, 5));
				filter.endtime = getMinutes(DBUtil.GetStringFromColumn(rs, 6));
				filter.isdeleted = rs.getBoolean(7);

				long last = rs.getTimestamp(8).getTime();
				if (last > vehiclealarmlasttime) {
					vehiclealarmlasttime = last;
				}

				if (!tmpcaller.equals(oldcaller) || (vehiclealarm == null)) {
					//新呼号
					vehiclealarm = new VehicleAlarm();
					vehiclealarm.callLetter = tmpcaller;
					oldcaller = tmpcaller;
					alarmlist.add(vehiclealarm);
				}
				vehiclealarm.filterlist.add(filter);
			}

			DBUtil.closeDB(rs, pst);
			if (ball) {
				vehiclealarmmap.clear();
			}
			// 如果有新的状态设置
			if (alarmlist.size() > 0) {
				refreshVehicleAlarm(alarmlist);
			}
		} finally {
			DBUtil.closeDB(rs, pst);
		}
	}
	private void refreshVehicleAlarm(ArrayList<VehicleAlarm> alarmlist) {
		try {
			for (VehicleAlarm newalarm : alarmlist) {
				VehicleAlarm oldalarm = vehiclealarmmap.get(newalarm.callLetter);
				if (oldalarm == null) {
					// 如果原来不存在，则删除新车辆警情
					for (int i = newalarm.filterlist.size() - 1; i >= 0; i--) {
						AlarmSetInfo info = newalarm.filterlist.get(i);
						// 去除被删除标示
						if (info.isdeleted || info.statuses == null || info.statuses.isEmpty()) {
							newalarm.filterlist.remove(i);
						}
					}
					// 如果过滤条件不空，则插入map
					if (!newalarm.filterlist.isEmpty()) {
						vehiclealarmmap.put(newalarm.callLetter, newalarm);
					}
				} else {
					// 如果已经存在。则比较过滤ID是否相同，相同，如果新是是被删除，
					for (AlarmSetInfo info : newalarm.filterlist) {
						// 去除被删除标示
						if (info.isdeleted || info.statuses == null || info.statuses.isEmpty()) {
							// 删除
							for (int i = 0; i < oldalarm.filterlist.size(); i++) {
								if (oldalarm.filterlist.get(i).analyseid == info.analyseid) {
									oldalarm.filterlist.remove(i);
									if (oldalarm.filterlist.isEmpty()) {
										vehiclealarmmap.remove(oldalarm.callLetter);
									}
									break;
								}
							}
						} else {
							// 更新
							for (int i = 0; i < oldalarm.filterlist.size(); i++) {
								if (oldalarm.filterlist.get(i).analyseid == info.analyseid) {
									oldalarm.filterlist.remove(i);
									break;
								}
							}
							oldalarm.filterlist.add(info);
						}

					}
				}
			}
		} catch (Exception e) {
		}
	}

	/********************************************************************************
	 * 类似呼号设置，更新机构设置条件
	 */
	private void readOrgAlarm(Connection readcon) throws Exception {
		PreparedStatement pst = null;
		ResultSet rs = null;
		boolean ball = (orgalarmlasttime == 0);
		try {
			String sql = "SELECT a.analyse_id, o.org_type, o.org_no, o.org_code, a.flag, a.status_ids, a.begin_time, a.end_time, a.is_del, a.stamp " +
						 "FROM t_al_org_alarm a, t_ba_org o " +
						 "WHERE a.org_id = o.org_id AND a.stamp > ? ORDER BY o.org_no, o.org_type, o.org_code, analyse_id DESC";
			// 创建PreparedStatement
			pst = readcon.prepareStatement(sql);
			java.sql.Timestamp lastdate = new java.sql.Timestamp(orgalarmlasttime);
			// 设置参数
			pst.setTimestamp(1, lastdate);
			// 查询
			rs = pst.executeQuery();
			ArrayList<OrgAlarm> alarmlist = new ArrayList<OrgAlarm>();
			OrgAlarm orgalarm = null;
			String oldorgcode = "";
			String tmporgno = "";
			while (rs.next()) {
				tmporgno = DBUtil.GetStringFromColumn(rs, 3);
				if (tmporgno.isEmpty()) {
					continue;
				}
				AlarmSetInfo filter = new AlarmSetInfo();
				filter.analyseid = rs.getLong(1);
				//下面三行组成组织机构ID
				String custtype = DBUtil.GetStringFromColumn(rs, 2);
				String orgcode = DBUtil.GetStringFromColumn(rs, 4);
				tmporgno += custtype;
				//集团用户或私家车
				if (orgcode.length() > 1) {
					tmporgno += orgcode;
				}
				filter.flag = rs.getShort(5);
				filter.statuses = getStatuses(DBUtil.GetStringFromColumn(rs, 6));
				filter.begintime = getMinutes(DBUtil.GetStringFromColumn(rs, 7));
				filter.endtime = getMinutes(DBUtil.GetStringFromColumn(rs, 8));
				filter.isdeleted = rs.getBoolean(9);
				long last = rs.getTimestamp(10).getTime();
				if (last > orgalarmlasttime) {
					orgalarmlasttime = last;
				}
				if (!tmporgno.equals(oldorgcode) || (orgalarm == null) ) {
					//新机构
					orgalarm = new OrgAlarm();
					orgalarm.orgcode = tmporgno;
					oldorgcode = tmporgno;
					alarmlist.add(orgalarm);
				}
				orgalarm.filterlist.add(filter);
			}
			
			DBUtil.closeDB(rs, pst);
			if (ball) {
				orgalarmmap.clear();
			}
			// 如果有新的状态设置
			if (alarmlist.size() > 0) {
				refreshOrgAlarm(alarmlist);
			}
		} finally {
			DBUtil.closeDB(rs, pst);
		}
	}

	private void refreshOrgAlarm(ArrayList<OrgAlarm> alarmlist) {
		try {
			for (OrgAlarm newalarm : alarmlist) {
				OrgAlarm oldalarm = orgalarmmap.get(newalarm.orgcode);
				if (oldalarm == null) {
					// 如果原来不存在，则删除新车辆警情
					for (int i = newalarm.filterlist.size() - 1; i >= 0; i--) {
						AlarmSetInfo info = newalarm.filterlist.get(i);
						// 去除被删除标示
						if (info.isdeleted || info.statuses == null	|| info.statuses.isEmpty()) {
							newalarm.filterlist.remove(i);
						}
					}
					// 如果过滤条件不空，则插入map
					if (!newalarm.filterlist.isEmpty()) {
						orgalarmmap.put(newalarm.orgcode, newalarm);
					}
				} else {
					// 如果已经存在。则比较过滤ID是否相同，相同，如果新是是被删除，
					for (AlarmSetInfo info : newalarm.filterlist) {
						if (info.isdeleted || info.statuses == null || info.statuses.isEmpty()) {
							// 删除
							for (int i = 0; i < oldalarm.filterlist.size(); i++) {
								if (oldalarm.filterlist.get(i).analyseid == info.analyseid) {
									oldalarm.filterlist.remove(i);
									if (oldalarm.filterlist.isEmpty()) {
										orgalarmmap.remove(oldalarm.orgcode);
									}
									break;
								}
							}
						} else {
							// 更新
							for (int i = 0; i < oldalarm.filterlist.size(); i++) {
								if (oldalarm.filterlist.get(i).analyseid == info.analyseid) {
									oldalarm.filterlist.remove(i);
									break;
								}
							}
							oldalarm.filterlist.add(info);
						}

					}
				}
			}
		} catch (Exception e) {
		}
	}

	/*******************************************************************************************
	 * 从数据库读行业过滤条件
	 */
	private void readTradeAlarm(Connection readcon) throws Exception {
		PreparedStatement pst = null;
		ResultSet rs = null;
		boolean ball = (tradealarmlasttime == 0);
		try {
			String sql = "SELECT analyse_id, trade, status_ids, begin_time, end_time, is_del, stamp FROM t_al_tradealarm WHERE stamp > ? ORDER BY trade, analyse_id";
			// 创建PreparedStatement
			pst = readcon.prepareStatement(sql);
			java.sql.Timestamp lastdate = new java.sql.Timestamp(tradealarmlasttime);
			// 设置参数
			pst.setTimestamp(1, lastdate);
			// 查询
			rs = pst.executeQuery();
			ArrayList<TradeAlarm> tradelist = new ArrayList<TradeAlarm>();
			TradeAlarm tradealarm = null;
			short oldtradeid = -0x7FFF;
			short tmptradeid = -1;
			while (rs.next()) {
				tmptradeid = rs.getShort(2);
				if (tmptradeid < 0) {
					continue;
				}
				//读数据库记录
				AlarmSetInfo filter = new AlarmSetInfo();
				filter.analyseid = rs.getLong(1);
				filter.flag = NOALARMFLAG;
				filter.statuses = getStatuses(DBUtil.GetStringFromColumn(rs, 3));
				filter.begintime = getMinutes(DBUtil.GetStringFromColumn(rs, 4));
				filter.endtime = getMinutes(DBUtil.GetStringFromColumn(rs, 5));
				filter.isdeleted = rs.getBoolean(6);
				long last = rs.getTimestamp(7).getTime();
				if (last > tradealarmlasttime) {
					tradealarmlasttime = last;
				}
				
				if (tradealarm == null || tmptradeid != oldtradeid) {
					//新行业
					tradealarm = new TradeAlarm();
					tradealarm.trade = tmptradeid;
					oldtradeid = tmptradeid;
					tradelist.add(tradealarm);
				}
				//同行业有多个过滤条件
				tradealarm.filterlist.add(filter);
			}
			DBUtil.closeDB(rs, pst);
			if (ball) {
				tradealarmmap.clear();
			}
			// 如果有新的状态设置
			if (tradelist.size() > 0) {
				refreshTradeAlarm(tradelist);
			}
		} finally {
			DBUtil.closeDB(rs, pst);
		}
	}
	private void refreshTradeAlarm(ArrayList<TradeAlarm> tradelist) {
		try {
			for (TradeAlarm newalarm : tradelist) {
				TradeAlarm oldalarm = tradealarmmap.get(newalarm.trade);
				if (oldalarm == null) {
					// 如果原来不存在此行业，先删除无用的过滤条件
					for (int i = newalarm.filterlist.size() - 1; i >= 0; i--) {
						AlarmSetInfo info = newalarm.filterlist.get(i);
						if (info.isdeleted || info.statuses == null	|| info.statuses.isEmpty()) {
							//删除
							newalarm.filterlist.remove(i);
						}
					}
					// 如果过滤条件不空，则插入map
					if (!newalarm.filterlist.isEmpty()) {
						tradealarmmap.put(newalarm.trade, newalarm);
					}
				} else {
					// 如果行业已经存在。则比较过滤ID是否相同，相同，如果新是是被删除，
					for (AlarmSetInfo info : newalarm.filterlist) {
						if (info.isdeleted || info.statuses == null || info.statuses.isEmpty()) {
							// 删除旧的
							for (int i = 0; i < oldalarm.filterlist.size(); i++) {
								if (oldalarm.filterlist.get(i).analyseid == info.analyseid) {
									oldalarm.filterlist.remove(i);
									if (oldalarm.filterlist.isEmpty()) {
										tradealarmmap.remove(oldalarm.trade);
									}
									break;
								}
							}
						} else {
							// 更新
							for (int i = 0; i < oldalarm.filterlist.size(); i++) {
								if (oldalarm.filterlist.get(i).analyseid == info.analyseid) {
									oldalarm.filterlist.remove(i);
									break;
								}
							}
							oldalarm.filterlist.add(info);
						}
					}
				}
			}
		} catch (Exception e) {
		}
	}
}
