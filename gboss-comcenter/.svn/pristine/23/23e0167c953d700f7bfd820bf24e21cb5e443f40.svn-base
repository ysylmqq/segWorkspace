/*
********************************************************************************************
Discription:  海马客户端管理，定时刷新数据库中海马用户要递交的警情和故障的触发条件
optional int32 triggercond = 7; //触发条件(
    //0x21：点火上报
    //0x22：熄火上报
    //0x23：休眼上报
    //0x24：关机上报
    //0x25: 警情上报
    //0x2B：熄火未关灯上报
    //0x2C：熄火未关门上报
    //0x2D：熄火未锁门上报
Written By:   ZXZ
Date:         2015-01-02
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import cc.chinagps.gboss.comcenter.UnitInfoManager.UnitInfo;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverUnitLoginOut;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.AlarmInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.AppNoticeInfo;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.command.SendCommandInfo;
import cc.chinagps.gboss.database.DBUtil;

public class HaiMaClientManager extends Thread {
	public static final short HMUNITTYPE = 13;	//定义海马终端类型

	//定义AlarmInfo上报的类型
	public static final short TIMEDELIVER = 0x20;   	//定时上报
	public static final short ONDELIVER = 0x21;			//点火上报
	public static final short OFFDELIVER = 0x22;		//熄火上报
	public static final short DOWMANTDELIVER = 0x23;	//休眼上报
	public static final short SHUTDOWNDELIVER = 0x24;	//关机上报
	public static final short ALARMDELIVER = 0x25;		//警情上报
	public static final short STATECHGDELIVER = 0x26;   //车身状态变化上报
	public static final short ONEKEYDELIVER = 0x27;   	//一键导航服务请求上报(协议保留，未实现)
	public static final short SOSDELIVER = 0x28;   		//SOS紧急救援服务请求上报
	public static final short OBDFAULTDELIVER = 0x2A;	//OBD故障码上报
	public static final short OFFLAMPDELIVER = 0x2B;	//熄火未关灯上报
	public static final short OFFDOORDELIVER = 0x2C;	//熄火未关门上报
	public static final short OFFLOCKDELIVER = 0x2D;	//熄火未锁门上报
	public static final short ENGINEONDELIVER = 0x2E;	//发动机点火上报(协议保留，未实现)
	public static final short ENGINEOFFDELIVER = 0x2F;	//发动机熄火上报(协议保留，未实现)
	public static final short LAMPFAULTDELIVER = 0x30;  //故障灯上报
	public static final short ONDOOROPENDELIVER = 0x31; //车辆防盗异常熄火上报
	public static final short ONTIMEOFFDELIVER = 0x32; 	//远程热车定时熄火上报
	public static final short EXITREMOTEONDELIVER = 0x33; //退出远程启动模式上报

	//每辆车可以设置那些警情是可以不送到手机APP，默认是全部发送到手机APP 
	private ConcurrentHashMap<String, ArrayList<Short>> triggercondMap = null;
	private ConcurrentHashMap<Integer, String> cmdNameMap = null;

	public HaiMaClientManager() {
		super("HaiMaClientManager");
		triggercondMap = new ConcurrentHashMap<String, ArrayList<Short>>();
		cmdNameMap = new ConcurrentHashMap<Integer, String>(); 
	}
	
	//取命令名称
	public String GetCmdName(int cmdid) {
		String strname = cmdNameMap.get(cmdid);
		if (strname == null) {
			strname = "[未知名]";
		}
		return strname;
	}
	
	//生成海马警情推送信息
	//判断终端上传的某警情，是否是海马用户注册过的消息
	public AppNoticeInfo createAppNoticeInfo(String callletter, AlarmInfo unitalarm) {
		if (unitalarm.getUnittype() != HMUNITTYPE) {
			//不是海马终端
			return null;
		}
		if (unitalarm.getTrigger() == 0x21 || unitalarm.getTrigger() == 0x22 || unitalarm.getTrigger() == 0x23) {
			//点火、熄火、休眠海马不要求递送
			return null;
		}
		boolean bdelive = true;
		ArrayList<Short> triggercond =  triggercondMap.get(callletter);
		if (triggercond != null) {
			bdelive = false;
			//如果有筛选条件，判断是否合适
			for(Short trigger: triggercond) {
				if ((int)trigger == unitalarm.getTrigger()) {
					bdelive = true;
					break;
				}
			}
		}
		if (!bdelive) {
			//这种警情不用递交到手机APP
			return null;
		}
		UnitInfo unitinfo = UnitInfoManager.unitinfomanager.getUnitInfo(callletter);
		if (unitinfo == null) {
			return null;
		}
		AppNoticeInfo.Builder builder = AppNoticeInfo.newBuilder();
		builder.setCallLetter(callletter);
		builder.setNoticetype(unitalarm.getTrigger());
		if (unitalarm.hasBaseInfo()) {
			builder.setBaseInfo(unitalarm.getBaseInfo());
		}
		if (unitalarm.hasReferPosition()) {
			builder.setReferPosition(unitalarm.getReferPosition());
		}
		String title = title(unitalarm);
		String content = content(unitalarm, unitinfo.plateno, builder);
		if (title.isEmpty() || content.isEmpty()) {
			return null;
		}
		builder.setTitle(title);
		builder.setContent(content);
		AppNoticeInfo noticeinfo = builder.build();
		insertNoticeInfoToDatabase(noticeinfo, unitinfo, 3);
		return noticeinfo;
	}
	
	//通知标题
	private String title(AlarmInfo unitalarm) {
	    if (unitalarm.getTrigger() == ALARMDELIVER) {	//0x25) {
	    	//return "海马助手警情提醒";
	    	return titlealarm(unitalarm);
	    }
	    //return "海马助手通知提醒";
	    return "推送消息";
	}

	/*
	 * 通知内容
	 * 爱车于XXXX-XX-XX XX:XX发出ZZ报警
	 * 爱车于XXXX-XX-XX XX:XX 脱网/熄火未关灯/熄火未关门/熄火未锁门
	 */
	private String titlealarm(AlarmInfo unitalarm) {
    	String strname = "";
    	for(int status : unitalarm.getBaseInfo().getStatusList()) {
    		switch(status) {
    			case 3: 
    				strname += "欠压";
    				break;
    			case 5: 
    				strname += "被劫";
    				break;
    			case 6: 
    				strname += "被盗";
    				break;
    			case 12:
    				strname += "碰撞";
    				break;
    			case 62:
    				strname += "被盗";
    				break;
    			case 63:
    				strname += "被盗";
    				break;
    			// 海马第一期不用，不报侧翻, 2016-05-25陶工要求打开
    			case 78:
    				strname += "侧翻";
    				break;
    		} 
    	}
    	if (strname.isEmpty()) {
    		return strname;
    	}
    	return strname + "报警";
	}
	private String content(AlarmInfo unitalarm, String plateno, AppNoticeInfo.Builder builder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String strtime = sdf.format(new Date(unitalarm.getBaseInfo().getGpsTime()));
	    if (unitalarm.getTrigger() == ALARMDELIVER) {
	    	//警情
	    	String strname = "";
	    	for(int status : unitalarm.getBaseInfo().getStatusList()) {
	    		switch(status) {
	    			case 3: 
	    				strname += "欠压";
	    				builder.setAlarmstatus(status);
	    				break;
	    			case 5: 
	    				strname += "劫警";
	    				builder.setAlarmstatus(status);
	    				break;
	    			case 6: 
	    				strname += "盗警";
	    				builder.setAlarmstatus(status);
	    				break;
	    			case 12:
	    				strname += "碰撞";
	    				builder.setAlarmstatus(status);
	    				break;
	    			case 62:
	    				strname += "开门盗警";
	    				builder.setAlarmstatus(status);
	    				break;
	    			case 63:
	    				strname += "点火盗警";
	    				builder.setAlarmstatus(status);
	    				break;
	    			case 78:
	    				strname += "侧翻";
	    				break;
	    			case 208:
	    				strname += "远程热车异常熄火盗警";
	    				break;
	    			case 211:
	    				strname += "SOS紧急救援";
	    				break;
	    		} 
	    	}
	    	if (strname.isEmpty()) {
	    		return strname;
	    	}
	    	return "爱车" + plateno + "于" + strtime + "发出[" + strname + "]报警";
	    }
	    //提醒
	    String triggerwarn = triggerwarn(unitalarm.getTrigger());
	    if (triggerwarn.isEmpty()) {
	    	return triggerwarn;
	    }
	    return "爱车" + plateno + "于" + strtime + triggerwarn;
	}
	
	//提醒内容
	private String triggerwarn(int trigger) {
		switch(trigger) {
		//case DOWMANTDELIVER:
		//	return "休眠";
		case SHUTDOWNDELIVER:
			return "关机";
		//case ALARMDELIVER:
		//	return "警情";
		case OFFLAMPDELIVER:
			return "熄火未关灯";
		case OFFDOORDELIVER:
			return "熄火未关门";
		case OFFLOCKDELIVER:
			return "熄火未锁门";
		case LAMPFAULTDELIVER:
			return "故障灯";
		case ONDOOROPENDELIVER:
			return "远程热车防盗异常熄火";
		case ONTIMEOFFDELIVER:
			return "远程热车定时熄火";
		case EXITREMOTEONDELIVER:
			return "退出远程启动模式";
		}
		return "";
	}
	
	//生成海马终端脱网提醒推送信息
	//遥控：爱车XXXX于XXXX-XX-XX XX:XX脱网
	public AppNoticeInfo createAppNoticeInfo(DeliverUnitLoginOut loginout, String callletter) {
		return null;
		/*UnitInfo unitinfo = UnitInfoManager.unitinfomanager.getUnitInfo(callletter);
		if (unitinfo == null) {
			return null;
		}
		if (unitinfo.unittype != HMUNITTYPE) {
			//不是海马终端
			return null;
		}
		AppNoticeInfo.Builder builder = AppNoticeInfo.newBuilder();
		builder.setCallLetter(callletter);
		builder.setTitle("推送消息");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String strtime = sdf.format(new Date(loginout.getLogoutTime()));
		String strcontent = "爱车" + unitinfo.plateno + "于" + strtime + "脱网";
		builder.setContent(strcontent);
		AppNoticeInfo noticeinfo = builder.build();
		insertNoticeInfoToDatabase(noticeinfo, unitinfo, 3);
		return noticeinfo;*/
	}

	//生成海马遥控（终端指令返回）推送信息
	//遥控：您于XXXX-XX-XX XX:XX发出YY指令，执行成功/失败
	public AppNoticeInfo createAppNoticeInfo(SendCommandInfo cmdinfo) {
		//海马查车、空调指令、升级指令不需要通知
		if (cmdinfo.cmdId == 0x01 || 
			cmdinfo.cmdId == 0x64 || //cmdinfo.cmdId == 0x65 || cmdinfo.cmdId == 0x66 || //空调查询、开、关
			cmdinfo.cmdId == 0xA5 || cmdinfo.cmdId == 0xB2) { //升级及升级查询
			return null;
		}
		if (cmdinfo.unittype != HMUNITTYPE) {
			//不是海马终端
			return null;
		}
		AppNoticeInfo.Builder builder = AppNoticeInfo.newBuilder();
		builder.setCallLetter(cmdinfo.callletter);
		//builder.setTitle("海马助手遥控提醒");
		builder.setTitle(titlecmd(cmdinfo.cmdId));
		builder.setContent(contentcmd(cmdinfo));
		builder.setCmdId(cmdinfo.cmdId);
		builder.setCmdretcode(cmdinfo.rescode);
		builder.setCmdretmsg(cmdinfo.resmsg);
		builder.setCmdsn(cmdinfo.cmdsn);
		AppNoticeInfo noticeinfo = builder.build();
		insertNoticeInfoToDatabase(noticeinfo, cmdinfo, 2);
		return noticeinfo;
	}
	private String titlecmd(int cmdId) {
		return GetCmdName(cmdId) + "指令";
	}
	//遥控命令通知内容
	private String contentcmd(SendCommandInfo cmdinfo) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strtime = sdf.format(new Date(cmdinfo.cmdtime1));
		String ret = "您于" + strtime + "发出" + GetCmdName(cmdinfo.cmdId) + "指令，执行";
		if (cmdinfo.rescode == ResultCode.OK) {
			ret += "成功";
		} else {
			if (cmdinfo.resmsg.isEmpty()) {
				ret += "失败";
			} else {
				ret += "失败-" + cmdinfo.resmsg;
			}
		}
		return ret;
	}
	
	/*
	 * 定时从数据库读用户设置的递交信息，更新triggercondMap
	 * 
	 */
	@Override
	public void run() {
		int icount = 0;
		while(true) {
			try {
				icount++;
				if ((icount % 1000) == 1) {
					updateCmdName();
				}
				sleep(10000);	//定时10秒更新设置
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//坐席接受警情后，更新警情
	private boolean insertNoticeInfoToDatabase(AppNoticeInfo noticeinfo, UnitInfo unitinfo, int n_type){
		//更新接警时间及接警响应率（时长）
		String sql = "INSERT INTO t_app_notice_his (customer_id, unit_id, call_letter," +
				" plate_no, n_type, title, content) VALUES (?,?,?,?,?,?,?)";
		Connection con = null;
		PreparedStatement pst = null;
		boolean bret = true;
		try {
			//获取数据库连接
			con = DBUtil.openConnection();
			//创建PreparedStatement
			pst = con.prepareStatement(sql);
			pst.setLong(1, unitinfo.customerid);
			pst.setLong(2, unitinfo.unitid);
			pst.setString(3, unitinfo.callletter);
			pst.setString(4, unitinfo.plateno);
			pst.setInt(5, n_type);
			pst.setString(6, noticeinfo.getTitle());
			pst.setString(7, noticeinfo.getContent());
			pst.executeUpdate();
		} catch(Exception e){
			//多个通信中心时，可能插入多条，现在数据库加了唯一索引，可能会出现异常
			//e.printStackTrace();
			bret = false;
		}
		DBUtil.closeDB(pst, con);
		return bret;
	}
	private boolean insertNoticeInfoToDatabase(AppNoticeInfo noticeinfo, SendCommandInfo cmdinfo, int n_type){
		//更新接警时间及接警响应率（时长）
		String sql = "INSERT INTO t_app_notice_his (customer_id, unit_id, call_letter," +
				" plate_no, n_type, title, content) VALUES (?,?,?,?,?,?,?)";
		Connection con = null;
		PreparedStatement pst = null;
		boolean bret = true;
		try {
			//获取数据库连接
			con = DBUtil.openConnection();
			//创建PreparedStatement
			pst = con.prepareStatement(sql);
			pst.setLong(1, cmdinfo.customerid);
			pst.setLong(2, cmdinfo.unitid);
			pst.setString(3, cmdinfo.callletter);
			pst.setString(4, cmdinfo.plateno);
			pst.setInt(5, n_type);
			pst.setString(6, noticeinfo.getTitle());
			pst.setString(7, noticeinfo.getContent());
			pst.executeUpdate();
		} catch(Exception e){
			//多个通信中心时，可能插入多条，现在数据库加了唯一索引，可能会出现异常
			//e.printStackTrace();
			bret = false;
		}
		DBUtil.closeDB(pst, con);
		return bret;
	}
	
	//更新命令ID的名称
	public void updateCmdName(){
		//更新接警时间及接警响应率（时长）
		String sql = "SELECT cmd_id, cmd_name FROM t_ss_command";
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			//获取数据库连接
			con = DBUtil.openConnection();
			//创建PreparedStatement
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()){
				//遍历结果集
				int cmd_id = rs.getInt(1);
				String cmd_name = DBUtil.GetStringFromColumn(rs, 2);
				cmdNameMap.put(cmd_id, cmd_name);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		DBUtil.closeDB(rs, pst, con);
	}
}
