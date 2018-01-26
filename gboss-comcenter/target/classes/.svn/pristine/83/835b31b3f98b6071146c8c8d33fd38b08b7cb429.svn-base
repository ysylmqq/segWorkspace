/*
 ********************************************************************************************
Discription: 终端信息管理类，
			 
			  
Written By:   ZXZ
Date:         2015-04-09
Version:      1.0

Modified by:
Modified Date:
Version:
 ********************************************************************************************
*/
package cc.chinagps.gboss.comcenter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

import cc.chinagps.gboss.comcenter.command.SendCommandInfo;
import cc.chinagps.gboss.database.DBUtil;
import cc.chinagps.lib.util.SystemConfig;
import cc.chinagps.lib.util.Util;

public class UnitInfoManager extends Thread {

	public static UnitInfoManager unitinfomanager = null;
	
	// [start] 终端信息
	public class UnitInfo {
		public static final short INNET = 0;	//入网
		public static final short OUTNET = 1;	//离网
		public static final short FEEOUTNET = 2;//欠费离网
		public static final short NOTINNET = 3;	//非入网
		public static final short RD_TEST = 4;  //研发测试
		public static final short WO_TEST = 5;  //电工测试

		public static final short ONSTATUS = 0;	//正常状态
		public static final short DOWMANT = 1;	//休眠状态
		public static final short SHUTDOWN = 2;	//关机状态
		public String callletter;		//呼号
		public String plateno;			//车牌号
		public String customername;		//客户名称
		public String orgcodes;			//属于那级组织机构（组织机构合并起来）
		public long unitid;				//终端ID
		public long vehicleid;			//车辆ID
		public long customerid;			//用户ID
		public short unitmode; 			//终端模式(0:未定义、1:短信、2:网络、3：网络+短信,
		public short netgatewayid; 		//网络网关ID
		public short smsgatewayid; 		//短信网关ID
		public short unittype; 			//终端协议类型, 2:T3688, 8:DB44, 9:龙翰, 10:科联星,
								   		//11:依迅, 12:部标808, 13:海马
										//请参考数据库记录
		public short onoff;				//海马有正常、休眠、关机状态
		private long freshtime;			//更新时间

		public short trade;				//所属行业
		public short reg_status;		//入网状态
		
		public String ToString() {
			return "calller:" + callletter + "," + 
				   "plateno:" + plateno + "," +
				   "custname:" + customername + "," +
				   "orgcodes:" + orgcodes + "," +
				   "unitid:" + unitid + "," + 
				   "vehicleid:" + vehicleid + "," + 
				   "customerid:" + customerid + "," + 
				   "unitmode:" + unitmode + "," + 
				   "netgatewayid:" + netgatewayid + "," + 
				   "smsgatewayid:" + smsgatewayid + "," + 
				   "unittype:" + unittype + "," + 
				   "onoff:" + onoff + "," + 
				   "trade:" + trade + "," + 
				   "reg_status:" + reg_status + "," + 
				   "freshtime:" + Util.DatetoString(new Date(freshtime));
		};
		
		public UnitInfo() {
			callletter = "";
			unitid = 0;
			vehicleid = 0;
			customerid = 0;
			unitmode = 0;
			netgatewayid = 0;
			smsgatewayid = 0;
			unittype = 2;
			onoff = ONSTATUS;
			plateno = "";
			customername = "";
			trade = 0;
			orgcodes = "";
			freshtime = System.currentTimeMillis() - 3600000;	//1小时后过期，重新从Memcache读流量网关编号;
		};
		
		//是否关机
		public boolean isshutdown() {
			return (onoff == SHUTDOWN);
		}
		//是否离网。不再服务
		public boolean isoutservice() {
			return (reg_status == OUTNET || reg_status == FEEOUTNET);
		}
	};
	// [end]
	
	// [start] 客户信息
	public class CustomerInfo {
		public String customername;
		public String password;
		public String orgno;
		public String orgcode;
		public short custtype;
		public String custno;
		public String custcode;
		public ArrayList<String> callletterlist;
		public CustomerInfo() {
			customername = "";
			password = "";
			orgno = "";
			orgcode = "";
			custtype = 0;
			custno = "";
			custcode = "";
			callletterlist = null;
		};
	};
	// [end]

	//终端信息列表
	private String strSQL;	//更新终端信息的SQL语句
	public ConcurrentHashMap<String, UnitInfo> unitinfomap = null;
	private long lastreadtime;	//数据库最后更新时间
	
	private static SockIOPool pool = null;	//从Memcache取流量网关信息
	
	private int maxlogintime;	//登退录系统最大相差时间
	private String maxcallletter;
	private int maxgatewayid;
	
	public UnitInfoManager() {
		super("UnitInfoManager");
		strSQL = "SELECT u.unit_id,u.call_letter,u.trade,u.reg_status,u.mode,u.sms_node,u.stamp,ut.protocol_id," +
				 "u.vehicle_id,v.plate_no,v.stamp,u.customer_id,c.customer_name,c.subco_no,c.cust_type,c.subco_code,c.stamp " +
				 "FROM t_ba_unit u,t_ba_unittype ut,t_ba_vehicle v,t_ba_customer c " +
				 "WHERE ut.unittype_id=u.unittype_id AND u.vehicle_id=v.vehicle_id AND u.customer_id=c.customer_id " +
				 "AND (u.stamp>? OR v.stamp>? OR c.stamp>?)"; // ORDER BY u.unit_id";
		unitinfomap = new ConcurrentHashMap<String, UnitInfo>();
		lastreadtime = 0;
		maxlogintime = 0;
		maxcallletter = "";
		maxgatewayid = 0;
		//初始化memcache
		try {
			String servernames = SystemConfig.getMemcacheProperties("servers");
			System.out.println(servernames);
			String[] servers = servernames.split(",");
			pool = SockIOPool.getInstance();
	        pool.setServers(servers);
			pool.setFailover(Boolean.valueOf(SystemConfig.getMemcacheProperties("failover")));
			pool.setInitConn(Integer.valueOf(SystemConfig.getMemcacheProperties("initConn")));
			pool.setMinConn(Integer.valueOf(SystemConfig.getMemcacheProperties("minConn")));
			pool.setMaxConn(Integer.valueOf(SystemConfig.getMemcacheProperties("maxConn")));
			pool.setNagle(Boolean.valueOf(SystemConfig.getMemcacheProperties("nagle")));
			//pool.setSocketTO(Integer.valueOf(SystemConfig.getMemcacheProperties("socketTO")));
			pool.setAliveCheck(Boolean.valueOf(SystemConfig.getMemcacheProperties("aliveCheck")));
	        //pool.setMaxIdle(1000*60*10);	//10分钟  
	        //pool.setMaint(30);  
	        //pool.setSocketConnectTO(0);
			pool.initialize();
		} catch (Exception e) {
			System.out.println("memcached not start: ");
			e.printStackTrace();
		}
	}
	
	public String getMaxLoginoutTime() {
		return maxcallletter + ",gateway:" + maxgatewayid + ",delaysecond:" + maxlogintime;
	}
	public void ClearMaxLoginoutTime() {
		maxcallletter = "";
		maxgatewayid = 0;
		maxlogintime = 0;
	}
	
	//刷新全部终端信息
	public void freshAllUnitInfo() {
		lastreadtime = 0;
	}
	
	//取某呼号的信息
	public CustomerInfo getCustomerInfo(String customername) {
		try {
			// 取通信模式
			MemCachedClient memcacheclient = new MemCachedClient();
			String strusername = (String) memcacheclient.get("username:" + customername);
			if (strusername == null) {
				return null;
			}
			CustomerInfo customerinfo = new CustomerInfo();
			customerinfo.customername = customername;
			String field[] = strusername.split("[,]");
			/*
			 *  1	密码密文		
				2	分公司NO	字符串，如果其中有“,”，则用“;”替换	
				3	分公司CODE	字符串，如果其中有“,”，则用“;”替换	
				4	客户类型		cust_type
				5	客户NO	字符串，如果其中有“,”，则用“;”替换	
				6	客户CODE	字符串，如果其中有“,”，则用“;”替换	
				7	私家车的终端呼号	多个终端，用半角分号隔开";"	
			*/
			if (field.length >= 1) {
				customerinfo.password = field[0];
				if (field.length >= 2) {
					customerinfo.orgno = field[1];
				}
				if (field.length >= 3) {
					customerinfo.orgcode = field[2];
				}
				if (field.length >= 4) {
					try {
						customerinfo.custtype = Short.valueOf(field[3]);
					} catch (Exception e) {};
				}
				if (field.length >= 5) {
					customerinfo.orgno = field[4];
				}
				if (field.length >= 6) {
					customerinfo.orgcode = field[5];
				}
				if (field.length >= 7) {
					if (field[6].length() > 0) {
						 String calllist[] = field[6].split(";");
						 customerinfo.callletterlist = new ArrayList<String>();
						 for(String callletter : calllist) {
							 customerinfo.callletterlist.add(callletter);
						 }
					}
				}
			}
			return customerinfo;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getUnitInfoString(String callletter) {
		UnitInfo unitinfo = unitinfomap.get(callletter);
		if (unitinfo != null) {
			return unitinfo.ToString();
		}
		return "null";
	}
	
	//取某呼号的信息
	public UnitInfo getUnitInfo(String callletter) {
		UnitInfo unitinfo = unitinfomap.get(callletter);
		if (unitinfo != null) {
			return unitinfo;
		}
		MemCachedClient memcacheclient = new MemCachedClient();
		unitinfo = getUnitInfoFromMemcached1(callletter, memcacheclient);
		return unitinfo;
	}
	/*
	 * 1	车台协议类型	2: T3688
						8: DB44
						9: 龙翰
						10: 科联星
						11: 依迅
						12: 部标808
						13: 海马	
		2	IMEI	IMEI或MEID(CDMA)	
		3	车台模式	十进制数字
					48:GPRS   
					49:SM  
					51:GPRS+SM	
		4	短信网关编号	十进制数字	
		5	车台Id	十进制数字	
		6	车辆Id	十进制数字	
		7	客户Id	十进制数字	
		8	车牌号码	字符串，如果其中有“,”，则用“;”替换	
		9	客户名称	字符串，如果其中有“,”，则用“;”替换	
		10	分公司NO	字符串，如果其中有“,”，则用“;”替换	subco_no
		11	分公司CODE	字符串，如果其中有“,”，则用“;”替换	subco_code
		12	客户类型		cust_type
		13	客户NO	字符串，如果其中有“,”，则用“;”替换	custco_no
		14	客户CODE	字符串，如果其中有“,”，则用“;”替换	custco_code
	 */
	public UnitInfo getUnitInfo(String callletter,	MemCachedClient memcacheclient) {
		UnitInfo unitinfo = unitinfomap.get(callletter);
		if (unitinfo == null) {
			unitinfo = getUnitInfoFromMemcached1(callletter, memcacheclient);
			return unitinfo;
		}
		if (unitinfo.netgatewayid > 0) {
			return unitinfo;
		}
		String strnetgatewayid = (String) memcacheclient.get("routing:" + unitinfo.callletter);
		// 网络网关编号
		if (strnetgatewayid != null) {
			try {
				unitinfo.netgatewayid = (short)Short.valueOf(strnetgatewayid);
				unitinfo.unitmode |= SendCommandInfo.NETMODE;
			} catch (Exception ex) {
			}
		} else {
			unitinfo.netgatewayid = 0;
		}
		unitinfo.freshtime = System.currentTimeMillis();
		return unitinfo;
	}
	private UnitInfo getUnitInfoFromMemcached1(String callletter, MemCachedClient memcacheclient) {
		try {
			UnitInfo unitinfo = new UnitInfo();
			unitinfo.callletter = callletter;
			// 取通信模式
			String strdata = (String) memcacheclient.get("data:" + callletter);
			String strnetgatewayid = (String) memcacheclient.get("routing:" + callletter);
			// 网络网关编号
			if (strnetgatewayid != null) {
				unitinfo.netgatewayid = Short.valueOf(strnetgatewayid);
				if (unitinfo.netgatewayid != 0) {
					unitinfo.onoff = UnitInfo.ONSTATUS;
				}
			}
			if (strdata != null) {
				String field[] = strdata.split("[,]");
				if (field.length >= 1 && field[0].length() > 0) { // 车台类型协议
					try {
						unitinfo.unittype = Short.valueOf(field[0]);
					} catch (Exception e) {};
				}
				if (field.length >= 3 && field[2].length() > 0) {
					//memcache中通信模式和数据库中有不一致
					unitinfo.unitmode = Short.valueOf(field[2]);
					if (unitinfo.unitmode == 48) {
						unitinfo.unitmode = SendCommandInfo.NETMODE;
					} else if (unitinfo.unitmode == 49) {
						unitinfo.unitmode = SendCommandInfo.SMSMODE;
					} else {
						unitinfo.unitmode = (short)(SendCommandInfo.SMSMODE + SendCommandInfo.NETMODE);
					}
				}
				if (field.length >= 4 && field[3].length() > 0) {
					unitinfo.smsgatewayid = Short.valueOf(field[3]);
					if (unitinfo.smsgatewayid > 0) {
						unitinfo.unitmode |= SendCommandInfo.SMSMODE;
					}
				}
				if (field.length >= 5 && field[4].length() > 0) {
					unitinfo.unitid = Long.valueOf(field[4]);
				}
				if (field.length >= 6 && field[5].length() > 0) {
					unitinfo.vehicleid = Long.valueOf(field[5]);
				}
				if (field.length >= 7 && field[6].length() > 0) {
					unitinfo.customerid = Long.valueOf(field[6]);
				}
				if (field.length >= 8) {
					unitinfo.plateno = field[7].trim();
				}
				if (field.length >= 9) {
					unitinfo.customername = field[8].trim();
				}
				if (field.length >= 14) {
					String subco_no = field[9].trim();
					String subco_code = field[10].trim();
					String cust_type = field[11].trim();
					unitinfo.orgcodes = subco_no + cust_type;
					//集团用户或私家车
					if (subco_code.length() > 1) {
						unitinfo.orgcodes += subco_code;
					}
				}
				unitinfomap.put(callletter, unitinfo);
			} else {
				unitinfo = null;
			}
			return unitinfo;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//当终端上传数据时, 设置终端网关编号
	public void setGatewayId(String callletter, int gatewayId, boolean bnet) {
		UnitInfo unitinfo = unitinfomap.get(callletter);
		if (unitinfo != null) {
			if (bnet) {
				unitinfo.netgatewayid = (short) gatewayId;
				unitinfo.unitmode |= SendCommandInfo.NETMODE;
				unitinfo.freshtime = System.currentTimeMillis();
				unitinfo.onoff = UnitInfo.ONSTATUS;
			} else {
				unitinfo.smsgatewayid = (short) gatewayId;
				unitinfo.unitmode |= SendCommandInfo.SMSMODE;
			}
		}
	}
	public void setGatewayId(String callletter, long ltime, int gatewayId, int inorout, boolean bnet) {
		if ((inorout <= 1) && (ltime > 0) && (gatewayId == 2001)) {
			//检查登退录延迟原因
			ltime = (System.currentTimeMillis() - ltime) / 1000;
			if (ltime > maxlogintime) {
				maxlogintime = (int) ltime;
				maxcallletter = callletter + " " + Util.DatetoString(new java.util.Date(System.currentTimeMillis())) + " " + inorout;
				maxgatewayid = gatewayId;
			}
		}
		UnitInfo unitinfo = unitinfomap.get(callletter);
		if (unitinfo != null) {
			if (inorout != 0) { // 登录
				if (bnet) {
					unitinfo.netgatewayid = (short) gatewayId;
					unitinfo.unitmode |= SendCommandInfo.NETMODE;
					unitinfo.freshtime = System.currentTimeMillis();
				} else {
					unitinfo.smsgatewayid = (short) gatewayId;
					unitinfo.unitmode |= SendCommandInfo.SMSMODE;
				}
			} else if (bnet) { // 退录
				if (unitinfo.netgatewayid == (short) gatewayId) { // 只有是对应的登录网关Id才清空
					unitinfo.netgatewayid = 0;
				}
			}
		}
	}
	
	//清空终端的流量网关
	public void clearGatewayId(String callletter) {
		UnitInfo unitinfo = unitinfomap.get(callletter);
		if (unitinfo != null) {
			unitinfo.netgatewayid = 0;
			unitinfo.freshtime = System.currentTimeMillis()-3600000;
		}
	}
	
	//设置终端休眠、关机状态
	public void setUnitOnOff(String callletter, int trigger) {
		UnitInfo unitinfo = unitinfomap.get(callletter);
		if (unitinfo != null) {
			if (trigger == HaiMaClientManager.SHUTDOWNDELIVER) {
				unitinfo.onoff = UnitInfo.SHUTDOWN;
			} else if (trigger == HaiMaClientManager.DOWMANTDELIVER) {
				unitinfo.onoff = UnitInfo.DOWMANT;
			} else if (trigger == HaiMaClientManager.ONDELIVER) {
				unitinfo.onoff = UnitInfo.ONSTATUS;
			}
		}
	}
	
	/****************************************************************************************************
	 * 线程，实时更新终端信息条件
	 */
	@Override
	public void run() {
		// 定时从数据库更新警情分析设置
		int nday = 0;
		//int icount = 0;
		while (true) {
			try {
				//每天全部更新一次(早上3点开始更新)
				Calendar calendar = Calendar.getInstance();
				int tmpday = calendar.get(Calendar.DAY_OF_MONTH);
				int hour = calendar.get(Calendar.HOUR_OF_DAY);
				if (tmpday != nday && hour >= 3) {
					lastreadtime = 0;
					nday = tmpday;
				}
				//开始更新终端基本信息
				readUnitInfoFromDB();
				
				//从Memcache更新流量网关编号
				refreshNetGatewayId();
				
				// 等待5秒，重新读
				Thread.sleep(15000);
				/*
				//系统自动垃圾回收，有时程序会死，必须配置恰当的JVM参数
				icount ++;
				if ((icount & 0xFF) == 0) {
					//垃圾回收2560秒一次
					System.gc();
				}*/
			} catch (Throwable e) {
				// 出现异常，关闭连接
				e.printStackTrace();
				nday = 0;	//重新全部读
			}
		}
	}
	
	// [start] 从数据库读更新的终端信息
	/******************************************************************************************
	 * 从数据库读更新的终端信息
	 */
	private void readUnitInfoFromDB() {
		Connection readcon = null; // 使用连接池
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			ArrayList<UnitInfo> unitinfolist = new ArrayList<UnitInfo>();
			//因为lastreadtime中间会改变值
			boolean ball = (lastreadtime==0);
			// 创建PreparedStatement
			readcon = DBUtil.getConnection();
			pst = readcon.prepareStatement(strSQL);
			java.sql.Timestamp lastdate = new java.sql.Timestamp(lastreadtime);
			// 设置参数
			pst.setTimestamp(1, lastdate);
			pst.setTimestamp(2, lastdate);
			pst.setTimestamp(3, lastdate);
			// 查询
			rs = pst.executeQuery();
			while (rs.next()) {
				UnitInfo unitinfo = new UnitInfo();
				//Unit信息
				unitinfo.unitid = rs.getLong(1);
				unitinfo.callletter = DBUtil.GetStringFromColumn(rs, 2);
				unitinfo.trade = rs.getShort(3);
				unitinfo.reg_status = rs.getShort(4);
				unitinfo.unitmode = rs.getShort(5);
				unitinfo.smsgatewayid = rs.getShort(6);
				long last = rs.getTimestamp(7).getTime();
				if (last > lastreadtime) {
					lastreadtime = last;
				}
				//终端类型
				unitinfo.unittype = rs.getShort(8);
				//车辆信息
				unitinfo.vehicleid = rs.getLong(9);
				unitinfo.plateno = DBUtil.GetStringFromColumn(rs, 10);
				last = rs.getTimestamp(11).getTime();
				if (last > lastreadtime) {
					lastreadtime = last;
				}
				//客户信息
				unitinfo.customerid = rs.getLong(12);
				unitinfo.customername = DBUtil.GetStringFromColumn(rs, 13);
				String subco_no = DBUtil.GetStringFromColumn(rs, 14);
				String cust_type = DBUtil.GetStringFromColumn(rs, 15);
				String subco_code = DBUtil.GetStringFromColumn(rs, 16);
				unitinfo.orgcodes = subco_no + cust_type;
				//集团用户或私家车
				if (subco_code != null && subco_code.length() > 1) {
					unitinfo.orgcodes += subco_code;
				}
				last = rs.getTimestamp(17).getTime();
				if (last > lastreadtime) {
					lastreadtime = last;
				}
				unitinfolist.add(unitinfo);
			}
			DBUtil.closeDB(rs, pst, readcon);
			// 如果有新的状态设置
			if (unitinfolist.size() > 0) {
				refreshUnitInfo(unitinfolist, ball);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeDB(rs, pst, readcon);
		}
	}
	//all 表示全部终端，是不是要删除已经不存在的终端？
	private void refreshUnitInfo(ArrayList<UnitInfo> unitinfolist, boolean ball) {
		try {
			if (ball) {
				//删除已经不存在的记录, 如果用ArrayList<UnitInfo>循环查找速度慢，先放到临时map，更快
				//这时对海马那些在t_ba_sim表中的卡的资料将被删除，只能从memcache重新读取
				HashSet<String> tmpset = new HashSet<String>();
				for (UnitInfo unitinfo : unitinfolist) {
					tmpset.add(unitinfo.callletter);
				}
				for(String key: unitinfomap.keySet()) {
					if (!tmpset.contains(key)) {
						//没找到表示已经删除了,从队列中删除
						unitinfomap.remove(key);
					}
				}
				tmpset.clear();
				tmpset = null;
			}
			for (UnitInfo unitinfo : unitinfolist) {
				if (unitinfo.isoutservice()) {
					unitinfomap.remove(unitinfo.callletter);
				} else { // 否则插入队列
					UnitInfo tmp = unitinfomap.get(unitinfo.callletter);
					if (tmp == null) {
						unitinfomap.put(unitinfo.callletter, unitinfo);
					} else {
						tmp.unitid = unitinfo.unitid;
						tmp.vehicleid = unitinfo.vehicleid;
						tmp.customerid = unitinfo.customerid;
						tmp.unitmode = unitinfo.unitmode;
						tmp.smsgatewayid = unitinfo.smsgatewayid;
						tmp.unittype = unitinfo.unittype;
						tmp.plateno = unitinfo.plateno;
						tmp.customername = unitinfo.customername;
						tmp.orgcodes = unitinfo.orgcodes;
						tmp.trade = unitinfo.trade;
						tmp.reg_status = unitinfo.reg_status;
					}
				}
			}
		} catch (Exception e) {
		}
	}
	// [end]
	
	//从Memcache更新流量网关编号
	private void refreshNetGatewayId() {
		MemCachedClient memcacheclient = new MemCachedClient();
		long expiretime = System.currentTimeMillis() - 3600000;	//1小时前更新者
		String strnetgatewayid;
		for(UnitInfo unitinfo : unitinfomap.values()) {
			if (unitinfo.freshtime > expiretime) {
				continue;
			}
			strnetgatewayid = (String) memcacheclient.get("routing:" + unitinfo.callletter);
			// 网络网关编号
			if (strnetgatewayid != null) {
				try {
					unitinfo.netgatewayid = (short)Short.valueOf(strnetgatewayid);
					unitinfo.unitmode |= SendCommandInfo.NETMODE;
				} catch (Exception ex) {
				}
			} else {
				unitinfo.netgatewayid = 0;
			}
			unitinfo.freshtime = System.currentTimeMillis();
		}
		memcacheclient = null;
	}
}
