package cc.chinagps.gateway.unit.seg.download;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.exceptions.WrongFormatException;
import cc.chinagps.gateway.seat.cmd.CmdUtil;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.stream.OutputPackageEncoder;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.seg.util.SegLatLngUtil;
import cc.chinagps.gateway.unit.seg.util.SegPkgUtil;
import cc.chinagps.gateway.util.HexUtil;
import cc.chinagps.gateway.util.Util;

public class SegDownloadCmdEncoder implements OutputPackageEncoder{
	private static final String DEFAULT_CMD_ENCODE_CHARSET = "ascii";
	
	@Override
	public ServerToUnitCommand encode(CommandBuff.Command userCmd, UnitSocketSession unitSession) throws Exception {
		int cmdIdx = userCmd.getCmdId();
		List<String> params = userCmd.getParamsList();
		SegServerToUnitCommand serverToUnitCommand = new SegServerToUnitCommand();
		serverToUnitCommand.setUserCommand(userCmd);
		String sn = SegPkgUtil.getsn();
		//serverToUnitCommand.setServerToUnitSn(sn);
		
		String cachedSn = CmdUtil.getCmdCacheSn(unitSession.getUnitInfo().getCallLetter(), cmdIdx);
		serverToUnitCommand.setCachedSn(cachedSn);
		
		String cachedSn_split;
		
		String sbody;
		StringBuilder bbody;
		byte[] data;
		
		switch (cmdIdx) {
			case CmdUtil.CMD_ID_POSITION://查车
				sbody = "(CTR,000)";
				data = SegPkgUtil.encode((byte) 0x10, sn, sbody.getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_TRACE://跟踪
				if(params.size() < 2){
					throw new WrongFormatException("seg cmdIdx:" + cmdIdx + ", param count:" + params.size() + ", min param count is 2");
				}
				
				int count = Integer.valueOf(params.get(0));
				int interval = Integer.valueOf(params.get(1));
				if(count < 1 || count > 255){
					throw new WrongFormatException("seg cmdIdx:" + cmdIdx + ", count:" + count);
				}
				
				if(interval < 1 || interval > 255){
					throw new WrongFormatException("seg cmdIdx:" + cmdIdx + ", interval:" + interval);
				}
				
				bbody = new StringBuilder(15);
				bbody.append("(CMD,002,");
				appendHex(bbody, count, 2);
				bbody.append(",");
				appendHex(bbody, interval, 2);
				bbody.append(")");
				data = SegPkgUtil.encode((byte) 0x11, sn, bbody.toString().getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_STOP_TRACE://停止跟踪
				sbody = "(CMD,002,00,00)";
				data = SegPkgUtil.encode((byte) 0x11, sn, sbody.getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_START_UPLOAD_BY_INTERVAL://定时报告
				if(params.size() < 1){
					throw new WrongFormatException("(seg)no param!");
				}
				
				String stime = params.get(0);
				int time = Integer.valueOf(stime);
				if(time < 1 || time > 921599){
					throw new WrongFormatException("(seg)param error[1-921599]:" + time);
				}
				int left = time;
				int hours = left / 3600;
				left -= hours * 3600;
				int minus = left / 60;
				left -= minus * 60;
				int seconds = left;
				
				bbody = new StringBuilder(19);
				bbody.append("(CMD,ITV,FF,");
				appendHex(bbody, hours, 2);
				appendHex(bbody, minus, 2);
				appendHex(bbody, seconds, 2);
				bbody.append(")");
				data = SegPkgUtil.encode((byte) 0x13, sn, bbody.toString().getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_STOP_UPLOAD_BY_INTERVAL://关闭定时报告
				sbody = "(CMD,ITV,00,000000)";
				data = SegPkgUtil.encode((byte) 0x13, sn, sbody.getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_START_ACC_OFF_DELIVERY://开启熄火定时上报
				if(params.size() < 1){
					throw new WrongFormatException("(seg)no param!");
				}
				
				String accOffTime = params.get(0);
				int timeAccOff = Integer.valueOf(accOffTime);
				if(timeAccOff < 1 || timeAccOff > 921599){
					throw new WrongFormatException("(seg)param error[1-921599]:" + timeAccOff);
				}
				int left1 = timeAccOff;
				int hours1 = left1 / 3600;
				left1 -= hours1 * 3600;
				int minus1 = left1 / 60;
				left1 -= minus1 * 60;
				int seconds1 = left1;
				
				bbody = new StringBuilder(21);
				bbody.append("(CMD,ITV,22,1,");
				appendHex(bbody, hours1, 2);
				appendHex(bbody, minus1, 2);
				appendHex(bbody, seconds1, 2);
				bbody.append(")");
				data = SegPkgUtil.encode((byte) 0x13, sn, bbody.toString().getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_STOP_ACC_OFF_DELIVERY://关闭熄火定时上报
				
				bbody = new StringBuilder(21);
				bbody.append("(CMD,ITV,22,0,");
				appendHex(bbody, 0, 2);
				appendHex(bbody, 0, 2);
				appendHex(bbody, 0, 2);
				bbody.append(")");
				data = SegPkgUtil.encode((byte) 0x13, sn, bbody.toString().getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_START_UPLOAD_BY_DISTANCE://开启定距上传
				if(params.size() < 2){
					throw new WrongFormatException("(seg)need 2 params, current:" + params.size());
				}
				
				bbody = new StringBuilder(19);
				bbody.append("(CMD,DUT,FF,");
				
				//距离(单位100m) 最大0xFFF * 100 = 409500
				int pdiatance = Integer.valueOf(params.get(0));
				if(pdiatance < 100){
					pdiatance = 100;
				}else if(pdiatance > 0xFFF * 100){
					pdiatance = 0xFFF * 100;
				}
				
				//条数 最大0xFFF = 4095
				int pcount = Integer.valueOf(params.get(1));
				if(pcount <= 0 || pcount > 0xFFF){
					pcount = 0xFFF;
				}
				
				appendHex(bbody, pcount, 3);	//参数1
				bbody.append(",");
				
				int d = (int) Math.round(pdiatance / 100.0);
				if(d == 0){
					d = 1;
				}
				appendHex(bbody, d, 3);	//参数2
				bbody.append(")");
				
				data = SegPkgUtil.encode((byte) 0x10, sn, bbody.toString().getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_STOP_UPLOAD_BY_DISTANCE://关闭定距上传
				sbody = "(CMD,DUT,00,000,000)";
				
				data = SegPkgUtil.encode((byte) 0x10, sn, sbody.getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_CUT_OFF_OIL://断油电
				sbody = "(CTR,555)";
				data = SegPkgUtil.encode((byte) 0x20, sn, sbody.getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_RESUME_OIL://恢复油电
				sbody = "(CTR,666)";
				data = SegPkgUtil.encode((byte) 0x21, sn, sbody.getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_RESET://终端复位
				sbody = "(CTR,111)";
				data = SegPkgUtil.encode((byte) 0x36, sn, sbody.getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_SEND);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_FORBID_TEL_IN://禁止电话打入
				sbody = "(CTR,DON)";
				data = SegPkgUtil.encode((byte) 0x38, sn, sbody.getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_FORBID_TEL_OUT://禁止电话打出
				sbody = "(CTR,LON)";
				data = SegPkgUtil.encode((byte) 0x39, sn, sbody.getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_GROUP_TEL://设定集群通话
				sbody = "(CTR,DAL)";
				data = SegPkgUtil.encode((byte) 0x73, sn, sbody.getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_RESUME_TEL://恢复电话功能
				sbody = "(CTR,DUL)";
				data = SegPkgUtil.encode((byte) 0x74, sn, sbody.getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_MONITOR://监听
				if(params.size() < 1){
					throw new WrongFormatException("(seg)no param!");
				}
				
				String tel = params.get(0);
				bbody = new StringBuilder(10 + tel.length());
				bbody.append("(CMD,003,").append(tel).append(")");
				
				data = SegPkgUtil.encode((byte) 0x72, sn, bbody.toString().getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_SET_MONITOR_TEL://设置监听号码
				if(params.size() < 1){
					throw new WrongFormatException("(seg)no param!");
				}
				
				if(params.size() > 4){
					throw new WrongFormatException("(seg)too much tel, max 4, current:" + params.size());
				}
				
				bbody = new StringBuilder();
				bbody.append("(CMD,033");
				for(int i = 0; i < params.size(); i++){
					String param = params.get(i);
					if(param == null || param.length() == 0 || param.length() > 17){
						throw new WrongFormatException("(seg)param error, index:" + i + ", value:" + param);
					}
					bbody.append(",").append(param);
				}
				bbody.append(")");
				data = SegPkgUtil.encode((byte) 0x72, sn, bbody.toString().getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_LIMIT_SPEED://限速
				if(params.size() < 1){
					throw new WrongFormatException("(seg)no param!");
				}
				
				//String sspeed = params.get(0);
				int speed = Integer.valueOf(params.get(0));
				//if(speed < 1 || speed > 1851){
				//	throw new WrongFormatException("speed error[1-1851]:" + speed);
				//}
				if(speed < 1){
					speed = 1;
				}else if(speed > 1851){
					speed = 1851;
				}
				
				bbody = new StringBuilder(13);
				bbody.append("(CMD,SPD,");
				
				int jspeed = SegPkgUtil.speedTOJie(speed);
				if(jspeed < 10){
					bbody.append("00");
				}else if(jspeed < 100){
					bbody.append("0");
				}
				bbody.append(jspeed);
				bbody.append(")");
				
				data = SegPkgUtil.encode((byte) 0x31, sn, bbody.toString().getBytes(DEFAULT_CMD_ENCODE_CHARSET));				
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_CANCEL_LIMIT_SPEED://取消限速
				sbody = "(CMD,SPD,000)";
				data = SegPkgUtil.encode((byte) 0x31, sn, sbody.getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_SEND_SM://下发短信
				if(params.size() < 1){
					throw new WrongFormatException("(seg)no param!");
				}
				
				String sm = params.get(0);
				data = SegPkgUtil.encode((byte) 0xe0, sn, sm.getBytes("UnicodeBigUnmarked"));
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_SEND);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_UPDATE_UNIT://远程升级
				if(params.size() < 2){
					throw new WrongFormatException("(seg)no param!");
				}
				
				String ip = params.get(0);
				String port = params.get(1);
				bbody = new StringBuilder();
				bbody.append("(CTR,UPD,");
				bbody.append(ip);
				bbody.append(",");
				bbody.append(port);
				bbody.append(",SZANT.GD,,)");
				
				data = SegPkgUtil.encode((byte) 0xDC, sn, bbody.toString().getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_SEND);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_SET_UPGRADE_PARAM:
				if(params.size() < 11){
					throw new WrongFormatException("(seg)not enough params, min: 11, current:" + params.size());
				}
				
				String flag = params.get(0);
				String protocolType = params.get(1);
				String type = params.get(2);
				if(!"1".equals(flag)||!"2".equals(flag)){
					type = "0";
				}
				String auto = params.get(3);
				if(!"0".equals(flag)){
					auto = "0";
				}
				String serverIp = params.get(4);
				serverIp = HexUtil.byteToHexStr(Util.getIPBytes(serverIp)).toUpperCase();
				String apn = params.get(5);
				String serverPort = params.get(6);
				String localPort = params.get(7);
				String userName = params.get(8);
				String pwd = params.get(9);
				String fileName = params.get(10);
				if(!"2".equals(flag)){
					fileName = "";
				}
				bbody = new StringBuilder();
				bbody.append("(UPS,");
				appendHex(bbody, Integer.valueOf(flag), 2);
				bbody.append(",");
				bbody.append(protocolType);
				bbody.append(",");
				appendHex(bbody, Integer.valueOf(type), 2);
				bbody.append(",");
				appendHex(bbody, Integer.valueOf(auto), 2);
				bbody.append(",");
				bbody.append(serverIp);
				bbody.append(",");
				bbody.append(apn);
				bbody.append(",");
				bbody.append(serverPort);
				bbody.append(",");
				bbody.append(localPort);
				bbody.append(",");
				bbody.append(userName);
				bbody.append(",");
				bbody.append(pwd);
				bbody.append(",");
				bbody.append(fileName);
				bbody.append(")");
				
				data = SegPkgUtil.encode((byte) 0x10, sn, bbody.toString().getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_SEND);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_SET_TCP_UDP_PARAMS://设置TCP/UDP通信参数
//				if(params.size() < 7){
//					throw new WrongFormatException("(seg)not enough params, min: 7, current:" + params.size());
//				}
//				
//				String apn_x = params.get(0);
//				if(apn_x.length() == 0){
//					bbody = new StringBuilder();
//					bbody.append("(RPM,").append(unitSession.getUnitInfo().getCallLetter()).append(",");
//					//IP
//					String ip_a = params.get(1);
//					String[] sip_a = ip_a.split("\\.");
//					for(int i = 0; i < sip_a.length; i++){
//						sppendString3(bbody, sip_a[i]);
//					}
//					//port-server
//					bbody.append(",").append(params.get(2));
//					//port-local
//					bbody.append(",").append(params.get(3));
//					//TCP or UDP
//					bbody.append(",").append(params.get(4));
//					//1 or 3
//					bbody.append(",").append(params.get(5));
//					//interval
//					int reconnect_interval_a = Integer.valueOf(params.get(6));
//					bbody.append(",");
//					appendHex(bbody, reconnect_interval_a, 2);
//					//用户名、密码(可能没有)
//					if(params.size() >= 9){
//						bbody.append(",").append(params.get(7));
//						bbody.append(",").append(params.get(8));
//					}
//					bbody.append(")");
//					
//					data = SegPkgUtil.encode((byte) 0x37, sn, bbody.toString().getBytes(DEFAULT_CMD_ENCODE_CHARSET));
//					serverToUnitCommand.setData(data);
//					serverToUnitCommand.setResponseType(PackageEncoder.SUCCESS_AFTER_RESPONSE);
//					return serverToUnitCommand;
//				}
//				
//				//APN and IP,Port
//				cachedSn_split = CmdUtil.getCmdCacheSn(unitSession.getUnitInfo().getCallLetter(), CmdUtil.CMD_ID_SET_TCP_UDP_APN_PARAMS);
//				serverToUnitCommand.setCachedSn(cachedSn_split);
//				
//				bbody = new StringBuilder();
//				bbody.append("(CGP,");
//				//APN
//				bbody.append(params.get(0)).append(",");
//				//IP
//				String ip_b = params.get(1);
//				String[] sip_b = ip_b.split("\\.");
//				for(int i = 0; i < sip_b.length; i++){
//					sppendString3(bbody, sip_b[i]);
//				}
//				//port-server
//				bbody.append(",").append(params.get(2));
//				//port-local
//				bbody.append(",").append(params.get(3));
//				//TCP or UDP
//				bbody.append(",").append(params.get(4));
//				//1 or 3
//				bbody.append(",").append(params.get(5));
//				//interval
//				int reconnect_interval_b = Integer.valueOf(params.get(6));
//				bbody.append(",");
//				appendHex(bbody, reconnect_interval_b, 2);
//				//用户名、密码(可能没有)
//				if(params.size() >= 9){
//					bbody.append(",").append(params.get(7));
//					bbody.append(",").append(params.get(8));
//				}
//				bbody.append(")");
//				
//				data = SegPkgUtil.encode((byte) 0x37, sn, bbody.toString().getBytes(DEFAULT_CMD_ENCODE_CHARSET));
//				serverToUnitCommand.setData(data);
//				serverToUnitCommand.setResponseType(PackageEncoder.SUCCESS_AFTER_RESPONSE);
//				return serverToUnitCommand;
				
				if(params.size() < 8){
					throw new WrongFormatException("(seg)not enough params, min: 8, current:" + params.size());
				}
				
				String apn_x = params.get(1);
				if(apn_x.length() == 0){
					bbody = new StringBuilder();
					bbody.append("(RPM,").append(unitSession.getUnitInfo().getCallLetter()).append(",");
					//IP
					String ip_a = params.get(2);
					String[] sip_a = ip_a.split("\\.");
					for(int i = 0; i < sip_a.length; i++){
						sppendString3(bbody, sip_a[i]);
					}
					//port-server
					bbody.append(",").append(params.get(3));
					//port-local
					bbody.append(",").append(params.get(4));
					//TCP or UDP
					bbody.append(",").append(params.get(5));
					//1 or 3
					bbody.append(",").append(params.get(6));
					//interval
					int reconnect_interval_s = Integer.valueOf(params.get(7));
					int reconnect_interval_m = (int) Math.ceil(reconnect_interval_s / 60.0);
					
					bbody.append(",");
					appendHex(bbody, reconnect_interval_m, 2);
					//用户名、密码(可能没有)
					if(params.size() >= 10){
						bbody.append(",").append(params.get(8));
						bbody.append(",").append(params.get(9));
					}
					bbody.append(")");
					
					data = SegPkgUtil.encode((byte) 0x37, sn, bbody.toString().getBytes(DEFAULT_CMD_ENCODE_CHARSET));
					serverToUnitCommand.setData(data);
					serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
					return serverToUnitCommand;
				}
				
				//APN and IP,Port
				cachedSn_split = CmdUtil.getCmdCacheSn(unitSession.getUnitInfo().getCallLetter(), CmdUtil.CMD_ID_SET_TCP_UDP_APN_PARAMS);
				serverToUnitCommand.setCachedSn(cachedSn_split);
				
				bbody = new StringBuilder();
				bbody.append("(CGP,");
				//APN
				bbody.append(params.get(1)).append(",");
				//IP
				String ip_b = params.get(2);
				String[] sip_b = ip_b.split("\\.");
				for(int i = 0; i < sip_b.length; i++){
					sppendString3(bbody, sip_b[i]);
				}
				//port-server
				bbody.append(",").append(params.get(3));
				//port-local
				bbody.append(",").append(params.get(4));
				//TCP or UDP
				bbody.append(",").append(params.get(5));
				//1 or 3
				bbody.append(",").append(params.get(6));
				//interval
				int reconnect_interval_s = Integer.valueOf(params.get(7));
				int reconnect_interval_m = (int) Math.ceil(reconnect_interval_s / 60.0);
				
				bbody.append(",");
				appendHex(bbody, reconnect_interval_m, 2);
				//用户名、密码(可能没有)
				if(params.size() >= 10){
					bbody.append(",").append(params.get(8));
					bbody.append(",").append(params.get(9));
				}
				bbody.append(")");
				
				data = SegPkgUtil.encode((byte) 0x37, sn, bbody.toString().getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_QUERY_TCP_UDP_PARAMS://查询TCP/UDP通信参数
				sbody = "(QPM,GRS)";
				data = SegPkgUtil.encode((byte) 0x47, sn, sbody.getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_SET_APN://设置APN
				if(params.size() < 1){
					throw new WrongFormatException("(seg)no param!");
				}
				
				String apn_a = params.get(0);
				bbody = new StringBuilder(6 + apn_a.length());
				bbody.append("(RPD,").append(apn_a).append(")");
				
				data = SegPkgUtil.encode((byte) 0x40, sn, bbody.toString().getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
//			case CmdUtil.CMD_ID_SET_TCP_UDP_APN_PARAMS://设置APN 与 TCP/UDP通信参数
//				if(params.size() < 7){
//					throw new WrongFormatException("(seg)not enough params, min: 6, current:" + params.size());
//				}
//				
//				bbody = new StringBuilder();
//				bbody.append("(CGP,");
//				//APN
//				bbody.append(params.get(0)).append(",");
//				//IP
//				String ip_b = params.get(1);
//				String[] sip_b = ip_b.split("\\.");
//				for(int i = 0; i < sip_b.length; i++){
//					sppendString3(bbody, sip_b[i]);
//				}
//				//port-server
//				bbody.append(",").append(params.get(2));
//				//port-local
//				bbody.append(",").append(params.get(3));
//				//TCP or UDP
//				bbody.append(",").append(params.get(4));
//				//1 or 3
//				bbody.append(",").append(params.get(5));
//				//interval
//				int reconnect_interval_b = Integer.valueOf(params.get(6));
//				bbody.append(",");
//				appendHex(bbody, reconnect_interval_b, 2);
//				//用户名、密码(可能没有)
//				if(params.size() >= 9){
//					bbody.append(",").append(params.get(7));
//					bbody.append(",").append(params.get(8));
//				}
//				bbody.append(")");
//				
//				data = SegPkgUtil.encode((byte) 0x37, sn, bbody.toString().getBytes(DEFAULT_CMD_ENCODE_CHARSET));
//				serverToUnitCommand.setData(data);
//				serverToUnitCommand.setResponseType(PackageEncoder.SUCCESS_AFTER_RESPONSE);
//				return serverToUnitCommand;
			case CmdUtil.CMD_ID_SET_SERRVICE_TEL://更改服务电话
				if(params.size() < 1){
					throw new WrongFormatException("(seg)no param!");
				}
				
				String service_tel_a = params.get(0);
				bbody = new StringBuilder(15 + 2 * service_tel_a.length());
				bbody.append("(CMD,006,006,");
				bbody.append(service_tel_a).append(",");
				bbody.append(service_tel_a).append(")");
				
				data = SegPkgUtil.encode((byte) 0x10, sn, bbody.toString().getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_SET_SM_CENTER_NUM://设置短消息中心服务号码
				if(params.size() < 1){
					throw new WrongFormatException("(seg)no param!");
				}
				
				String service_tel_b = params.get(0);
				bbody = new StringBuilder(15 + 2 * service_tel_b.length());
				bbody.append("(CMD,009,009,");
				bbody.append(service_tel_b).append(",");
				bbody.append(service_tel_b).append(")");
				
				data = SegPkgUtil.encode((byte) 0x10, sn, bbody.toString().getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_SET_SM_IN_NUM://设置短信呼入/呼出特服号(赛格只能一次设一个)
				if(params.size() < 1){
					throw new WrongFormatException("(seg)no param!");
				}
				
				String sms_down = params.get(0);
				if(sms_down.length() > 0){
					//设置短信呼出特服号
					cachedSn_split = CmdUtil.getCmdCacheSn(unitSession.getUnitInfo().getCallLetter(), CmdUtil.CMD_ID_SET_SM_OUT_NUM);
					serverToUnitCommand.setCachedSn(cachedSn_split);
					
					bbody = new StringBuilder(15 + 2 * sms_down.length());
					bbody.append("(CMD,008,008,");
					bbody.append(sms_down).append(",");
					bbody.append(sms_down).append(")");
					
					data = SegPkgUtil.encode((byte) 0x10, sn, bbody.toString().getBytes(DEFAULT_CMD_ENCODE_CHARSET));
					serverToUnitCommand.setData(data);
					serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
					return serverToUnitCommand;
				}else if(params.size() > 1){
					//设置短信呼入特服号
					String sms_upload = params.get(1);
					if(sms_upload.length() > 0){
						bbody = new StringBuilder(15 + 2 * sms_upload.length());
						bbody.append("(CMD,007,007,");
						bbody.append(sms_upload).append(",");
						bbody.append(sms_upload).append(")");
						
						data = SegPkgUtil.encode((byte) 0x10, sn, bbody.toString().getBytes(DEFAULT_CMD_ENCODE_CHARSET));
						serverToUnitCommand.setData(data);
						serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
						return serverToUnitCommand;
					}
				}
				
				throw new WrongFormatException("(seg)param error: empty");			
				/*
				String sms_tel_a = params.get(0);
				String sms_type = params.get(1);
				if("1".equals(sms_type)){
					bbody = new StringBuilder(15 + 2 * sms_tel_a.length());
					bbody.append("(CMD,007,007,");
					bbody.append(sms_tel_a).append(",");
					bbody.append(sms_tel_a).append(")");
					
					data = SegPkgUtil.encode((byte) 0x10, sn, bbody.toString().getBytes(DEFAULT_CMD_ENCODE_CHARSET));
					serverToUnitCommand.setData(data);
					serverToUnitCommand.setResponseType(PackageEncoder.SUCCESS_AFTER_RESPONSE);
					return serverToUnitCommand;
				}
				
				//设置短信呼出特服号
				if("2".equals(sms_type)){
					cachedSn_split = CmdUtil.getCmdCacheSn(unitSession.getUnitInfo().getCallLetter(), CmdUtil.CMD_ID_SET_SM_OUT_NUM);
					serverToUnitCommand.setCachedSn(cachedSn_split);
					
					bbody = new StringBuilder(15 + 2 * sms_tel_a.length());
					bbody.append("(CMD,008,008,");
					bbody.append(sms_tel_a).append(",");
					bbody.append(sms_tel_a).append(")");
					
					data = SegPkgUtil.encode((byte) 0x10, sn, bbody.toString().getBytes(DEFAULT_CMD_ENCODE_CHARSET));
					serverToUnitCommand.setData(data);
					serverToUnitCommand.setResponseType(PackageEncoder.SUCCESS_AFTER_RESPONSE);
					return serverToUnitCommand;
				}
				
				throw new WrongFormatException("(seg)error type:" + sms_type);	
				*/
				
				
				
//			case CmdUtil.CMD_ID_SET_SM_OUT_NUM://设置短信呼出特服号
//				if(params.size() < 1){
//					throw new WrongFormatException("(seg)no param!");
//				}
//				
//				String sms_tel_b = params.get(0);
//				bbody = new StringBuilder(15 + 2 * sms_tel_b.length());
//				bbody.append("(CMD,008,008,");
//				bbody.append(sms_tel_b).append(",");
//				bbody.append(sms_tel_b).append(")");
//				
//				data = SegPkgUtil.encode((byte) 0x10, sn, bbody.toString().getBytes(DEFAULT_CMD_ENCODE_CHARSET));
//				serverToUnitCommand.setData(data);
//				serverToUnitCommand.setResponseType(PackageEncoder.SUCCESS_AFTER_RESPONSE);
//				return serverToUnitCommand;
			case CmdUtil.CMD_ID_SET_RECONNECT_INTERVAL://设置GPRS重连时间间隔
				if(params.size() < 1){
					throw new WrongFormatException("(seg)no param!");
				}
				
				String p = params.get(0);
				int reconnect_interval = Integer.valueOf(p);
				if(reconnect_interval < 1 || reconnect_interval> 99){
					throw new WrongFormatException("(seg)error param, param is:" + p + "[1-99]");
				}
				bbody = new StringBuilder(12);
				bbody.append("(CMD,RGS,");
				if(reconnect_interval < 10){
					bbody.append("0");
				}
				bbody.append(reconnect_interval).append(")");
				
				data = SegPkgUtil.encode((byte) 0x7B, sn, bbody.toString().getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_SET_TARGET_QUERY_POINT://(TN)设置导航仪目的查询点
				if(params.size() < 3){
					throw new WrongFormatException("(seg)not enough params, min: 3, current:" + params.size());
				}
				
				bbody = new StringBuilder();
				bbody.append("(TNC,002,");
				//搜索方式
				bbody.append(params.get(0)).append(",");
				////纬度
				//bbody.append(SegLatLngUtil.userLatToUnitLat(params.get(1))).append(",");
				////经度
				//bbody.append(SegLatLngUtil.userLngToUnitLng(params.get(2)));
				//纬度
				bbody.append(SegLatLngUtil.userLatToUnitLat(params.get(2))).append(",");
				//经度
				bbody.append(SegLatLngUtil.userLngToUnitLng(params.get(1)));
				
				//必经点
				if(params.size() >= 4 && params.get(3).length() > 0){
					String[] latlngs = params.get(3).split(";");
					int latlngs_length = latlngs.length;
					if(latlngs_length > 3){
						throw new WrongFormatException("(seg)count of b is bigger than max value:3,value is:" + latlngs_length);
					}
					
					if(latlngs_length > 0){
						bbody.append(",B").append(latlngs_length);
						for(String latlng: latlngs){
							String[] alatlng = latlng.split(",");
							//bbody.append(",").append(SegLatLngUtil.userLatToUnitLat(alatlng[0]));
							//bbody.append(",").append(SegLatLngUtil.userLngToUnitLng(alatlng[1]));
							bbody.append(",").append(SegLatLngUtil.userLatToUnitLat(alatlng[1]));
							bbody.append(",").append(SegLatLngUtil.userLngToUnitLng(alatlng[0]));
						}
					}
				}
				
				//回避点
				if(params.size() >= 5 && params.get(4).length() > 0){
					String[] latlngs = params.get(4).split(";");
					int latlngs_length = latlngs.length;
					if(latlngs_length > 2){
						throw new WrongFormatException("(seg)count of c is bigger than max value:2,value is:" + latlngs_length);
					}
					
					if(latlngs_length > 0){
						bbody.append(",C").append(latlngs_length);
						for(String latlng: latlngs){
							String[] alatlng = latlng.split(",");
							//bbody.append(",").append(SegLatLngUtil.userLatToUnitLat(alatlng[0]));
							//bbody.append(",").append(SegLatLngUtil.userLngToUnitLng(alatlng[1]));
							bbody.append(",").append(SegLatLngUtil.userLatToUnitLat(alatlng[1]));
							bbody.append(",").append(SegLatLngUtil.userLngToUnitLng(alatlng[0]));
						}
					}
				}
				bbody.append(")");
				
				data = SegPkgUtil.encode((byte) 0x68, sn, bbody.toString().getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_LOCK_DOOR: //锁车门
				sbody = "(CTR,333)";
				data = SegPkgUtil.encode((byte) 0x10, sn, sbody.getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_OPEN_DOOR: //开车门
				sbody = "(CTR,444)";
				data = SegPkgUtil.encode((byte) 0x10, sn, sbody.getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_UPLOAD_HISTORY: //上传行车记录
				if(params.size() < 2){
					throw new WrongFormatException("seg cmdIdx:" + cmdIdx + ", param count:" + params.size() + ", min param count is 2");
				}
				
				String time1 = localToGMT(params.get(0));
				String time2 = localToGMT(params.get(1));
				bbody = new StringBuilder();
				
				bbody.append("(CMD,001,");
				bbody.append(time1);
				bbody.append(",");
				bbody.append(time2);
				bbody.append(")");
				
				data = SegPkgUtil.encode((byte) 0x12, sn, bbody.toString().getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_SEARCH_VEHICLE://找车
				sbody = "(CTR,FDC)";
				data = SegPkgUtil.encode((byte) 0x10, sn, sbody.getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_CLOSE_WINDOW://关车窗
				sbody = "(CTR,CLW)";
				data = SegPkgUtil.encode((byte) 0x10, sn, sbody.getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_OPEN_WINDOW://开车窗
				sbody = "(CTR,OPW)";
				data = SegPkgUtil.encode((byte) 0x10, sn, sbody.getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_READ_DEFINED_TYPE://读取车型
				sbody = "(SAF,161)";
				data = SegPkgUtil.encode((byte) 0x10, sn, sbody.getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_DEFINED_TYPE://标定车型
				if(params.size() < 1){
					throw new WrongFormatException("(seg)no param!");
				}
				
				String ptype = params.get(0);
				if(ptype == null || ptype.length() != 4){
					throw new WrongFormatException("(seg)error param, param is:" + ptype);
				}
				
				bbody = new StringBuilder(14);
				bbody.append("(SAF,163,");
				bbody.append(ptype);
				bbody.append(")");
				data = SegPkgUtil.encode((byte) 0x10, sn, bbody.toString().getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_CLEAR_FAULT_CODE://清除故障码
				sbody = "(SAF,141)";
				data = SegPkgUtil.encode((byte) 0x10, sn, sbody.getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_CLOSE_REPAIR_MODE://关闭维修模式
				sbody = "(CTR,OPO)";
				data = SegPkgUtil.encode((byte) 0x10, sn, sbody.getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_OPEN_REPAIR_MODE://开启维修模式
				sbody = "(CTR,CLO)";
				data = SegPkgUtil.encode((byte) 0x10, sn, sbody.getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand; 
			case CmdUtil.CMD_ID_PASS_THROUGH://透传指令
				if(params.size() < 1){
					throw new WrongFormatException("(seg)no param!");
				}
				
				data = HexUtil.hexToBytes(params.get(0));
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_SEND);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_QUERY_TRG://查询子机编号
				sbody = "(QPM,TRG)";
				data = SegPkgUtil.encode((byte) 0x10, sn, sbody.getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_DEL_TRG://删除子机编号
				if(params.size() < 1){
					throw new WrongFormatException("(seg)no param!");
				}
				String trgId=params.get(0);
				sbody = "(DEL,TRG,"+trgId+")";
				data = SegPkgUtil.encode((byte) 0x10, sn, sbody.getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_OPEN_BASESTATION_UPLOAD://打开基站数据上报 
				sbody = "(CMD,BS,1)";
				data = SegPkgUtil.encode((byte) 0x20, sn, sbody.getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_CLOSE_BASESTATION_UPLOAD://关闭基站数据上报 
				sbody = "(CMD,BS,0)";
				data = SegPkgUtil.encode((byte) 0x20, sn, sbody.getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_SET_TRG_INTERVAL://设置蓝牙断开时子机上传间隔 
				if(params.size() < 1){
					throw new WrongFormatException("(seg)no param!");
				}
				
				String intervalTime = params.get(0);
				int timeT = Integer.valueOf(intervalTime);
				if(timeT < 1 || timeT > 921599){
					throw new WrongFormatException("(seg)param error[1-921599]:" + timeT);
				}
				int leftTime = timeT;
				int hour = leftTime / 3600;
				leftTime -= hour * 3600;
				int min = leftTime / 60;
				leftTime -= min * 60;
				int second = leftTime;
				
				bbody = new StringBuilder(15);
				bbody.append("(CMD,BT,");
				appendHex(bbody, hour, 2);
				appendHex(bbody, min, 2);
				appendHex(bbody, second, 2);
				bbody.append(")");
				data = SegPkgUtil.encode((byte) 0x21, sn, bbody.toString().getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_DELIVER_ORDER://北汽下发订单
				if (params.size() < 2) {
					throw new WrongFormatException("seg cmdIdx:" + cmdIdx + ", param count:" + params.size() + ", min param count is 2");
				}
	
				String rfid = params.get(0);
				String validTime = params.get(1);
				int vTime = Integer.valueOf(validTime);
	
				if (vTime < 1 || vTime > 99) {
					throw new WrongFormatException("(seg)error param, param is:" + validTime + "[1-99]");
				}
	
				bbody = new StringBuilder(7 + rfid.length() + 2);
				bbody.append("(CRD,");
				bbody.append(rfid);
				bbody.append(",");
				if (vTime < 10) {
					bbody.append("0");
				}
				bbody.append(validTime);
				bbody.append(")");
				data = SegPkgUtil.encode((byte) 0x30, sn, bbody.toString().getBytes(DEFAULT_CMD_ENCODE_CHARSET));
	
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_FINISH_ORDER://北汽结束订单
				sbody = "(CRS)";
				data = SegPkgUtil.encode((byte) 0x31, sn, sbody.getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_PICK_UP_CAR:
				sbody = "(QUCAR)";
				data = SegPkgUtil.encode((byte) 0x33, sn, sbody.getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_RETURN_CAR:
				sbody = "(HUANCAR)";
				data = SegPkgUtil.encode((byte) 0x34, sn, sbody.getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case  CmdUtil.CMD_ID_REMOTE_CONTROL_VEHICLE://北汽远程控制车辆
				if (params.size() < 1) {
					throw new WrongFormatException("(seg)no param!");
				}
				bbody = new StringBuilder(7);
				bbody.append("(CTL,");
				String cmd = params.get(0);
				bbody.append(cmd);
				bbody.append(")");
				data = SegPkgUtil.encode((byte) 0x32, sn, bbody.toString().getBytes(DEFAULT_CMD_ENCODE_CHARSET));
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			default:
				throw new WrongFormatException("(seg)no encoder, cmdId:" + cmdIdx);
		}
	}
	
	private SimpleDateFormat sdf_gmt_his;
	private SimpleDateFormat sdf_local;
	
	//本地时间串 转成 GMT时间串(转换上传行车记录指令参数)
	private synchronized String localToGMT(String localTime) throws ParseException{
		if(sdf_local == null){
			sdf_local = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf_gmt_his = new SimpleDateFormat("yyMMdd,HHmmss");
			sdf_gmt_his.setTimeZone(TimeZone.getTimeZone("GMT"));
		}
		
		Date date = sdf_local.parse(localTime);
		return sdf_gmt_his.format(date);
	}
	
	//添加固定长度为3的字符串,不足3位,前补0
	private static void sppendString3(StringBuilder sb, String append){
		if(append.length() == 1){
			sb.append("00").append(append);
		}else if(append.length() == 2){
			sb.append("0").append(append);
		}else{
			sb.append(append);
		}
	}
	
	//添加转成16进制的字符串,不足两位,前补0
	private static void appendHex(StringBuilder sb, int value, int length) throws WrongFormatException{
		String str = Integer.toHexString(value).toUpperCase();
		
		if(str.length() > length){
			throw new WrongFormatException("param error:" + value);
		}
		
		int append = length - str.length();
		for(int i = 0; i < append; i++){
			sb.append("0");
		}
		
		sb.append(str);
	}
	
	public static void main(String[] args) throws Exception {
		StringBuilder sb = new StringBuilder();
		int v = 0xFF;
		appendHex(sb, v, 3);
		
		System.out.println(sb.toString());
	}
}