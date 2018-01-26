package cc.chinagps.gateway.unit.beforemarket.yidongwang.download;

import java.text.ParseException;
import java.util.List;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.exceptions.WrongFormatException;
import cc.chinagps.gateway.seat.cmd.CmdUtil;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.stream.OutputPackageEncoder;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beforemarket.common.pkg.BeforeMarketPackage;
import cc.chinagps.gateway.unit.beforemarket.common.pkg.BeforeMarketPackageHead;
import cc.chinagps.gateway.unit.beforemarket.common.util.BeforeMarketConstants;
import cc.chinagps.gateway.unit.beforemarket.common.util.BeforeMarketPkgUtil;
import cc.chinagps.gateway.unit.beforemarket.common.util.BeforeMarketTimeFormatUtil;
import cc.chinagps.gateway.unit.beforemarket.yidongwang.util.YdwConstants;
import cc.chinagps.gateway.unit.seg.download.SegServerToUnitCommand;
import cc.chinagps.gateway.util.Constants;
import cc.chinagps.gateway.util.HexUtil;
import cc.chinagps.gateway.util.SystemConfig;

public class YdwDownloadCmdEncoder implements OutputPackageEncoder{
	private boolean DEFAULT_IS_COMPRESS;
	
	private boolean DEFAULT_IS_ENCRYPT;
	
	public YdwDownloadCmdEncoder(){
		DEFAULT_IS_COMPRESS = Boolean.valueOf(SystemConfig.getSystemProperty("ydw_use_compress"));
		DEFAULT_IS_ENCRYPT = Boolean.valueOf(SystemConfig.getSystemProperty("ydw_use_encrypt"));
	}
	
	@Override
	public ServerToUnitCommand encode(Command userCmd,
			UnitSocketSession unitSession) throws Exception {
		int cmdIdx = userCmd.getCmdId();
		switch (cmdIdx) {
			//设置
			case CmdUtil.CMD_ID_SET_TCP_UDP_PARAMS://设置TCP/UDP通信参数
				return getSetTCPParamCmd(userCmd, unitSession);
			case CmdUtil.CMD_ID_SET_APN://设置APN(TCP/UDP通信参数)
				return getSetAPNCmd(userCmd, unitSession);
			case CmdUtil.CMD_ID_SET_RECONNECT_INTERVAL://设置GPRS重连时间间隔(TCP/UDP通信参数)
				return getSetReconnectParamCmd(userCmd, unitSession);
			case CmdUtil.CMD_ID_SET_SERRVICE_TEL://更改服务电话
				return getSetTelCmd(userCmd, unitSession, (byte) 0x2);
			case CmdUtil.CMD_ID_SET_SM_IN_NUM://设置短信呼入特服号(呼入呼出)
				return getSetSmInNumCmd(userCmd, unitSession);
			case CmdUtil.CMD_ID_TRACE://跟踪(定时报告)
				return getTraceCmd(userCmd, unitSession);
			case CmdUtil.CMD_ID_STOP_TRACE://停止跟踪(定时报告)
				return getStopTraceCmd(userCmd, unitSession);
			case CmdUtil.CMD_ID_START_UPLOAD_BY_INTERVAL://定时报告
				return getStartUploadCmd(userCmd, unitSession);
			case CmdUtil.CMD_ID_STOP_UPLOAD_BY_INTERVAL://关闭定时报告(定时报告)
				return getStopUploadCmd(userCmd, unitSession);
			case CmdUtil.CMD_ID_LIMIT_SPEED://限速
				return getLimitSpeedCmd(userCmd, unitSession);
			case CmdUtil.CMD_ID_CANCEL_LIMIT_SPEED://取消限速
				return getCancelLimitSpeedCmd(userCmd, unitSession);
				
			//设置用户密码
			//设置报警手机号码
			//设置一动网报警参数
			case CmdUtil.CMD_ID_SET_USER_PASSWORD://设置用户密码
				return getSetUserPasswordCmd(userCmd, unitSession);
			case CmdUtil.CMD_ID_SET_ALARM_TEL://设置报警手机号码
				return getSetAlarmTelCmd(userCmd, unitSession);
			case CmdUtil.CMD_ID_SET_ALARM_PARAM://设置一动网报警参数
				return getSetAlarmParamCmd(userCmd, unitSession);
								
			//查询
			case CmdUtil.CMD_ID_QUERY_TCP_UDP_PARAMS://查询TCP/UDP通信参数
				return getCommonQueryCmd(userCmd, unitSession, (byte) 0x1);
			case CmdUtil.CMD_ID_QUERY_CALL_CENTER://呼叫中心
				return getCommonQueryCmd(userCmd, unitSession, (byte) 0x2);	
			case CmdUtil.CMD_ID_QUERY_SM_CENTER://短信业务中心号码
				return getCommonQueryCmd(userCmd, unitSession, (byte) 0x3);
			case CmdUtil.CMD_ID_QUERY_UPLOAD_PARAM://定时上报参数
				return getCommonQueryCmd(userCmd, unitSession, (byte) 0x12);	
			case CmdUtil.CMD_ID_POSITION://查车
				return getCommonQueryCmd(userCmd, unitSession, (byte) 0x21);
				
			//查询超速参数
			//查询用户密码
			//查询报警手机号码
			//查询一动网报警参数
			case CmdUtil.CMD_ID_QUERY_LIMIT_SPEED_PARAM://查询超速参数
				return getCommonQueryCmd(userCmd, unitSession, (byte) 0x18);
			case CmdUtil.CMD_ID_QUERY_USER_PASSWORD://查询用户密码
				return getCommonQueryCmd(userCmd, unitSession, (byte) 0x19);
			case CmdUtil.CMD_ID_QUERY_ALARM_TEL://查询报警手机号码
				return getCommonQueryCmd(userCmd, unitSession, (byte) 0x20);
			case CmdUtil.CMD_ID_QUERY_ALARM_PARAM://查询一动网报警参数
				return getCommonQueryCmd(userCmd, unitSession, (byte) 0x22);
				
			case CmdUtil.CMD_ID_PASS_THROUGH://透传指令
				List<String> params = userCmd.getParamsList();
				if(params.size() < 1){
					throw new WrongFormatException("(ydw)no param!");
				}
				
				byte[] source = HexUtil.hexToBytes(params.get(0));				
				SegServerToUnitCommand serverToUnitCommand = new SegServerToUnitCommand();		
				serverToUnitCommand.setData(source);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_SEND);
				return serverToUnitCommand;
			default:
				throw new WrongFormatException("(ydw)no encoder, cmdId:" + cmdIdx);
		}
	}
	
	/**
	 * [0x3] 通用查询指令
	 * @throws Exception 
	 */
	private ServerToUnitCommand getCommonQueryCmd(Command userCmd,
			UnitSocketSession unitSession, byte cmdId) throws Exception{
		byte[] body = new byte[7];
		addTime(body);
		body[6] = cmdId;
		
		return makeCmd(userCmd, unitSession, (byte) 0x3, body);
	}
	
	/**
	 * 限速
	 */
	private ServerToUnitCommand getLimitSpeedCmd(Command userCmd,
			UnitSocketSession unitSession) throws Exception{
		List<String> params = userCmd.getParamsList();
		if(params.size() < 1){
			throw new WrongFormatException("(ydw)no param!");
		}
		
		int speed = Integer.valueOf(params.get(0));
		if(speed < 1 || speed > 255){
			throw new WrongFormatException("(ydw)speed error[1-255]:" + speed);
		}
		
		int time;
		if(params.size() > 1){
			time = Integer.valueOf(params.get(1));
			if(time < 0 || time > 255){
				throw new WrongFormatException("(ydw)time error[0-255]:" + time);
			}
		}else{
			time = 0;
		}
		
		byte[] body = new byte[9];
		addTime(body);
		body[6] = 0x18;
		body[7] = (byte) speed;
		body[8] = (byte) time;
		
		return makeCmd(userCmd, unitSession, (byte) 0x2, body);
	}
	
	/**
	 * 取消限速
	 * @throws Exception
	 */
	private ServerToUnitCommand getCancelLimitSpeedCmd(Command userCmd,
			UnitSocketSession unitSession) throws Exception{
		byte[] body = new byte[9];
		addTime(body);
		body[6] = 0x18;
		
		return makeCmd(userCmd, unitSession, (byte) 0x2, body);
	}
	
	/**
	 * 设置用户密码
	 * @throws Exception 
	 */
	private ServerToUnitCommand getSetUserPasswordCmd(Command userCmd,
			UnitSocketSession unitSession) throws Exception{
		List<String> params = userCmd.getParamsList();
		if(params.size() < 1){
			throw new WrongFormatException("(ydw)no param!");
		}
		
		String password = params.get(0);
		
		byte[] body = new byte[17];
		addTime(body);
		body[6] = 0x19;
		byte[] bs_password = password.getBytes(YdwConstants.CN_CHAR_ENCODING);
		System.arraycopy(bs_password, 0, body, 7, Math.min(bs_password.length, 10));
		
		return makeCmd(userCmd, unitSession, (byte) 0x2, body);
	}
	
	/**
	 * 设置报警手机号码
	 * @throws Exception 
	 */
	private ServerToUnitCommand getSetAlarmTelCmd(Command userCmd,
			UnitSocketSession unitSession) throws Exception{
		List<String> params = userCmd.getParamsList();
		if(params.size() < 1){
			throw new WrongFormatException("(ydw)no param!");
		}
		
		String tel1 = params.get(0);
		
		byte[] body = new byte[55];
		addTime(body);
		body[6] = 0x20;
		
		byte[] bs_tel1 = tel1.getBytes(Constants.CHAR_ENCODING_ASCII);
		System.arraycopy(bs_tel1, 0, body, 7, Math.min(bs_tel1.length, 16));
		
		if(params.size() > 1){
			String tel2 = params.get(1);
			byte[] bs_tel2 = tel2.getBytes(Constants.CHAR_ENCODING_ASCII);
			System.arraycopy(bs_tel2, 0, body, 23, Math.min(bs_tel2.length, 16));
		}
		
		if(params.size() > 2){
			String tel3 = params.get(2);
			byte[] bs_tel3 = tel3.getBytes(Constants.CHAR_ENCODING_ASCII);
			System.arraycopy(bs_tel3, 0, body, 39, Math.min(bs_tel3.length, 16));
		}
		
		return makeCmd(userCmd, unitSession, (byte) 0x2, body);
	}
	
	/**
	 * 设置报警参数
	 * @throws Exception 
	 */
	private ServerToUnitCommand getSetAlarmParamCmd(Command userCmd,
			UnitSocketSession unitSession) throws Exception{
		List<String> params = userCmd.getParamsList();
		if(params.size() < 2){
			throw new WrongFormatException("(ydw)not enough params, min: 2, current:" + params.size());
		}
		
		int door = Integer.valueOf(params.get(0));
		int type = Integer.valueOf(params.get(1));
		
		byte[] body = new byte[9];
		addTime(body);
		body[6] = 0x22;
		body[7] = (byte) door;
		body[8] = (byte) type;
		
		return makeCmd(userCmd, unitSession, (byte) 0x2, body);
	}
	
	/**
	 * 跟踪
	 * @throws Exception 
	 */
	private ServerToUnitCommand getTraceCmd(Command userCmd,
			UnitSocketSession unitSession) throws Exception{
		List<String> params = userCmd.getParamsList();
		if(params.size() < 2){
			throw new WrongFormatException("ydw cmdIdx:" + userCmd.getCmdId() + ", param count:" + params.size() + ", min param count is 2");
		}
		
		int count = Integer.valueOf(params.get(0));
		int interval = Integer.valueOf(params.get(1));
		if(count < 1 || count > 65535){
			throw new WrongFormatException("ydw cmdIdx:" + userCmd.getCmdId() + ", count:" + count);
		}
		
		if(interval < 1 || interval > 65535){
			throw new WrongFormatException("ydw cmdIdx:" + userCmd.getCmdId() + ", interval:" + interval);
		}
		
		byte[] body = new byte[11];
		addTime(body);
		body[6] = 0x12;
		
		byte[] b_interval = Util.getShortByte((short) interval);
		byte[] b_count = Util.getShortByte((short) count);
		System.arraycopy(b_interval, 0, body, 7, 2);
		System.arraycopy(b_count, 0, body, 9, 2);
		
		return makeCmd(userCmd, unitSession, (byte) 0x2, body);
	}
	
	/**
	 * 停止跟踪
	 * @throws Exception 
	 */
	private ServerToUnitCommand getStopTraceCmd(Command userCmd,
			UnitSocketSession unitSession) throws Exception{
		byte[] body = new byte[11];
		addTime(body);
		body[6] = 0x12;
		
		byte[] b_interval = Util.getShortByte((short) 30);
		byte[] b_count = Util.getShortByte((short) 0);
		System.arraycopy(b_interval, 0, body, 7, 2);
		System.arraycopy(b_count, 0, body, 9, 2);
		
		return makeCmd(userCmd, unitSession, (byte) 0x2, body);
	}
	
	/**
	 * 定时报告
	 * @throws Exception 
	 */
	private ServerToUnitCommand getStartUploadCmd(Command userCmd,
			UnitSocketSession unitSession) throws Exception{
		List<String> params = userCmd.getParamsList();
		if(params.size() < 1){
			throw new WrongFormatException("(ydw)no param!");
		}
		
		String stime = params.get(0);
		int time = Integer.valueOf(stime);
		if(time < 1 || time > 65535){
			throw new WrongFormatException("(ydw)param error[1-65535]:" + time);
		}
		
		byte[] body = new byte[11];
		addTime(body);
		body[6] = 0x12;
		
		byte[] b_time = Util.getShortByte((short) time);
		System.arraycopy(b_time, 0, body, 7, 2);
		body[9] = (byte) 0xFF;
		body[10] = (byte) 0xFF;
		
		return makeCmd(userCmd, unitSession, (byte) 0x2, body);
	}
	
	/**
	 * 关闭定时报告
	 * @throws Exception 
	 */
	private ServerToUnitCommand getStopUploadCmd(Command userCmd,
			UnitSocketSession unitSession) throws Exception{
		byte[] body = new byte[11];
		addTime(body);
		body[6] = 0x12;
		
		body[8] = 0x1E;
		return makeCmd(userCmd, unitSession, (byte) 0x2, body);
	}
	
	/**
	 * 设置TCP通信参数
	 * @throws Exception 
	 */
	private ServerToUnitCommand getSetTCPParamCmd(Command userCmd,
			UnitSocketSession unitSession) throws Exception{
//		List<String> params = userCmd.getParamsList();
//		if(params.size() < 3){
//			throw new WrongFormatException("(ydw)not enough params, min: 3, current:" + params.size());
//		}
//		
//		String sapn = params.get(0);
//		String sip = params.get(1);
//		String sport = params.get(2);
//		
//		byte[] bs_apn = sapn.getBytes();
//		byte[] body = new byte[15 + bs_apn.length];
//		addTime(body);
//		body[6] = 0x1;
//		
//		//IP
//		String[] sip_a = sip.split("\\.");
//		for(int i = 0 ; i < 4; i++){
//			int v = Integer.valueOf(sip_a[i]);
//			body[7 + i] = (byte) v;
//		}
//		
//		//port
//		int port = Integer.valueOf(sport);
//		byte[] b_port = Util.getShortByte((short) port);
//		System.arraycopy(b_port, 0, body, 11, 2);
//		
//		//重连间隔
//		if(params.size() >= 7){
//			String sinterval = params.get(6);
//			if(sinterval.length() > 0){
//				int interval = Integer.valueOf(sinterval) * 60;
//				byte[] b_interval = Util.getShortByte((short) interval);
//				System.arraycopy(b_interval, 0, body, 13, 2);
//			}
//		}
//		
//		//APN
//		System.arraycopy(bs_apn, 0, body, 15, bs_apn.length);
//		
//		return makeCmd(userCmd, unitSession, (byte) 0x2, body);
		
		List<String> params = userCmd.getParamsList();
		if(params.size() < 8){
			throw new WrongFormatException("(ydw)not enough params, min: 8, current:" + params.size());
		}
		
		String sapn = params.get(1);
		String sip = params.get(2);
		String sport = params.get(3);
		String sinterval = params.get(7);
		
		byte[] bs_ip = cc.chinagps.gateway.util.Util.getIPBytes(sip);
		int port = Integer.valueOf(sport);
		byte[] bs_port = Util.getShortByte((short) port);
		int interval = Integer.valueOf(sinterval);
		byte[] b_interval = Util.getShortByte((short) interval);
		byte[] bs_apn = sapn.getBytes();
		
		byte[] body = new byte[15 + bs_apn.length];
		addTime(body);
		body[6] = 0x1;
		int position = 7;
		
		System.arraycopy(bs_ip, 0, body, position, bs_ip.length);
		position += bs_ip.length;
		
		System.arraycopy(bs_port, 0, body, position, bs_port.length);
		position += bs_port.length;
		
		System.arraycopy(b_interval, 0, body, position, b_interval.length);
		position += b_interval.length;
		
		System.arraycopy(bs_apn, 0, body, position, bs_apn.length);
		//position += bs_apn.length;
		
		return makeCmd(userCmd, unitSession, (byte) 0x2, body);
	}
	
	/**
	 * 设置APN
	 * @throws Exception 
	 */
	private ServerToUnitCommand getSetAPNCmd(Command userCmd,
			UnitSocketSession unitSession) throws Exception{
		List<String> params = userCmd.getParamsList();
		if(params.size() < 1){
			throw new WrongFormatException("(ydw)no param!");
		}
		
		String sapn = params.get(0);
		byte[] bs_apn = sapn.getBytes(BeforeMarketPkgUtil.EN_CHAR_ENCODING);
		
		byte[] body = new byte[15 + bs_apn.length];
		addTime(body);
		body[6] = 0x1;
		
		//APN
		System.arraycopy(bs_apn, 0, body, 15, bs_apn.length);
		
		return makeCmd(userCmd, unitSession, (byte) 0x2, body);
	}
	
	/**
	 * 设置重连时间间隔
	 * @throws Exception 
	 */
	private ServerToUnitCommand getSetReconnectParamCmd(Command userCmd,
			UnitSocketSession unitSession) throws Exception{
		List<String> params = userCmd.getParamsList();
		if(params.size() < 1){
			throw new WrongFormatException("(ydw)no param!");
		}
		
		byte[] body = new byte[15];
		addTime(body);
		body[6] = 0x1;
		
		//重连间隔
		String sinterval = params.get(6);
		int interval = Integer.valueOf(sinterval);
		byte[] b_interval = Util.getShortByte((short) interval);
		System.arraycopy(b_interval, 0, body, 13, 2);
		
		return makeCmd(userCmd, unitSession, (byte) 0x2, body);
	}
	
	/**
	 * 设置服务电话(call center)
	 * @throws Exception 
	 */
	private ServerToUnitCommand getSetTelCmd(Command userCmd,
			UnitSocketSession unitSession, byte subCmdId) throws Exception{
		List<String> params = userCmd.getParamsList();
		if(params.size() < 1){
			throw new WrongFormatException("(ydw)no param!");
		}
		
		byte[] body = new byte[23];
		addTime(body);
		body[6] = subCmdId;
		
		byte[] b_tel = params.get(0).getBytes(BeforeMarketPkgUtil.EN_CHAR_ENCODING);
		System.arraycopy(b_tel, 0, body, 7, Math.min(b_tel.length, 16));
		
		return makeCmd(userCmd, unitSession, (byte) 0x2, body);
	}
	
	/**
	 * 设置短信 呼入/呼出  特服号
	 * @throws Exception 
	 */
	private ServerToUnitCommand getSetSmInNumCmd(Command userCmd,
			UnitSocketSession unitSession) throws Exception{
		List<String> params = userCmd.getParamsList();
		if(params.size() < 1){
			throw new WrongFormatException("(ydw)no param");
		}
		
		String call_down = params.get(0);
		String call_up = null;
		if(params.size() > 1){
			call_up = params.get(1);
		}
		
		byte[] body = new byte[57];
		addTime(body);
		body[6] = 0x3;
		
		if(call_down.length() > 0){
			byte[] bs_call_down = call_down.getBytes(BeforeMarketPkgUtil.EN_CHAR_ENCODING);
			System.arraycopy(bs_call_down, 0, body, 7, Math.min(bs_call_down.length, 25));
		}
		
		if(call_up != null && call_up.length() > 0){
			byte[] bs_call_up = call_up.getBytes(BeforeMarketPkgUtil.EN_CHAR_ENCODING);
			System.arraycopy(bs_call_up, 0, body, 32, Math.min(bs_call_up.length, 25));
		}
		
		return makeCmd(userCmd, unitSession, (byte) 0x2, body);
	}

	/**
	 * cmd公共部分
	 * @throws ParseException 
	 */
	private void addTime(byte[] data) throws ParseException{
		addTime(data, 0);
	}
	
	private void addTime(byte[] data, int offset) throws ParseException{
		byte[] bcd = BeforeMarketTimeFormatUtil.getCurrentTimeBCD();
		System.arraycopy(bcd, 0, data, offset, 6);
	}
	
	private ServerToUnitCommand makeCmd(Command userCmd,
			UnitSocketSession unitSession, byte msgType, byte[] body) throws Exception{
		BeforeMarketPackage pkg = new BeforeMarketPackage();
		BeforeMarketPackageHead head = new BeforeMarketPackageHead();
		head.setCompress(DEFAULT_IS_COMPRESS);
		head.setEncrypt(DEFAULT_IS_ENCRYPT);
		head.setMsgType(msgType);
		head.setSn(BeforeMarketPkgUtil.getSn(unitSession));
		head.setTerminalId(unitSession.getUnitInfo().getIMEI());
		head.setTerminalType(BeforeMarketConstants.PROTOCOL_TERMINAL_TYPE_YDW);
		head.setVersion(BeforeMarketConstants.PROTOCOL_UNIT_VERSION);
		
		pkg.setHead(head);
		pkg.setBody(body);
		int c1 = BeforeMarketPkgUtil.getC1(unitSession);
		int d1 = BeforeMarketPkgUtil.getD1(unitSession);
		byte[] source = pkg.encode(c1, d1);
		
		SegServerToUnitCommand serverToUnitCommand = new SegServerToUnitCommand();
		serverToUnitCommand.setUserCommand(userCmd);
		String cachedSn = BeforeMarketPkgUtil.getCmdCacheSn(unitSession.getUnitInfo().getCallLetter(), head.getSn());
		serverToUnitCommand.setCachedSn(cachedSn);
		
		serverToUnitCommand.setData(source);
		serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
		return serverToUnitCommand;
	}
}