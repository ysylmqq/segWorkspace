package cc.chinagps.gateway.unit.db44.download;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.exceptions.WrongFormatException;
import cc.chinagps.gateway.seat.cmd.CmdUtil;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.stream.OutputPackageEncoder;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.db44.util.DB44PkgUtil;
import cc.chinagps.gateway.unit.db44.util.DB44TimeFormatUtil;
import cc.chinagps.gateway.unit.seg.download.SegServerToUnitCommand;
import cc.chinagps.gateway.util.Constants;
import cc.chinagps.gateway.util.HexUtil;

/**
 * 打包发给车台的指令
 */
public class DB44DownloadCmdEncoder implements OutputPackageEncoder{
	@Override
	public ServerToUnitCommand encode(Command userCmd,
			UnitSocketSession unitSession) throws Exception {
		int cmdIdx = userCmd.getCmdId();
		List<String> params = userCmd.getParamsList();
		SegServerToUnitCommand serverToUnitCommand = new SegServerToUnitCommand();
		serverToUnitCommand.setUserCommand(userCmd);
		//String sn = SegPkgUtil.getsn();
		//serverToUnitCommand.setServerToUnitSn(sn);
		
		String cachedSn = CmdUtil.getCmdCacheSn(unitSession.getUnitInfo().getCallLetter(), cmdIdx);
		serverToUnitCommand.setCachedSn(cachedSn);
		
		int count, countPerPackage;
		byte[] body;
		byte[] data;
		switch (cmdIdx) {
			case CmdUtil.CMD_ID_POSITION://查车
				data = DB44PkgUtil.encode((byte) 0x01, Constants.ZERO_BYTES_DATA);
				
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_TRACE://跟踪:
				throw new WrongFormatException("(db44)无此指令,请使用定时上报指令");
			case CmdUtil.CMD_ID_STOP_TRACE://停止跟踪
				throw new WrongFormatException("(db44)无此指令,请使用关闭定时上报指令");
			case CmdUtil.CMD_ID_START_UPLOAD_BY_INTERVAL://定时报告
				if(params.size() < 1){
					throw new WrongFormatException("(db44)no param!");
				}
				
				//timeInterval[count][countPerPackage]
				String stime = params.get(0);
				int time = Integer.valueOf(stime);
				if(time < 1){
					time = 1;
				}else if(time > 255){
					time = 255;
				}
				
				count = 0xFFFF;
				if(params.size() >= 2){
					count = Integer.valueOf(params.get(1));
					if(count < 0){
						count = 0;
					}else if(count > 0xFFFF){
						count = 0xFFFF;
					}
				}
				
				countPerPackage = 1;
				if(params.size() >= 3){
					countPerPackage = Integer.valueOf(params.get(2));
					if(countPerPackage < 0){
						countPerPackage = 0;
					}else if(countPerPackage > 0xFF){
						countPerPackage = 0xFF;
					}
				}
				
				body = new byte[4];
				body[0] = (byte) time;
				body[1] = (byte) countPerPackage;
				DB44PkgUtil.copyShort(body, 2, count);
				
				data = DB44PkgUtil.encode((byte) 0x02, body);
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_STOP_UPLOAD_BY_INTERVAL://关闭定时报告
				body = new byte[]{1, 1, 0, 0};
				data = DB44PkgUtil.encode((byte) 0x02, body);
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_START_UPLOAD_BY_DISTANCE://开启定距上传
				if(params.size() < 2){
					throw new WrongFormatException("(db44)need 2 params, current:" + params.size());
				}
				
				int diatance = Integer.valueOf(params.get(0));
				if(diatance < 50){
					diatance = 50;
				}else if(diatance > 9999){
					diatance = 9999;
				}
				
				count = Integer.valueOf(params.get(1));
				if(count <= 0 || count > 0xFFFF){
					count = 0xFFFF;
				}
				
				countPerPackage = 1;
				if(params.size() >= 3){
					countPerPackage = Integer.valueOf(params.get(2));
					if(countPerPackage < 0){
						countPerPackage = 0;
					}else if(countPerPackage > 0xFF){
						countPerPackage = 0xFF;
					}
				}
				
				body = new byte[5];
				DB44PkgUtil.copyShort(body, 0, diatance);
				body[2] = (byte) countPerPackage;
				DB44PkgUtil.copyShort(body, 3, count);
				
				data = DB44PkgUtil.encode((byte) 0x04, body);
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_STOP_UPLOAD_BY_DISTANCE://关闭定距上传
				body = new byte[]{0, 0x32, 1, 0, 0};

				data = DB44PkgUtil.encode((byte) 0x04, body);
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_CUT_OFF_OIL://断油电	
				body = new byte[7];
				body[0] = 1;
				byte[] time_bcd_1;
				if(params.size() > 0){
					time_bcd_1 = Util.str2bcd(DB44TimeFormatUtil.userTimeToBCDStr(params.get(0)));
				}else{
					time_bcd_1 = DEFAUL_TIME_BCD;
				}
				System.arraycopy(time_bcd_1, 0, body, 1, time_bcd_1.length);
				
				data = DB44PkgUtil.encode((byte) 0x19, body);
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_RESUME_OIL://恢复油电
				body = new byte[7];
				body[0] = 0;
				byte[] time_bcd_0;
				if(params.size() > 0){
					time_bcd_0 = Util.str2bcd(DB44TimeFormatUtil.userTimeToBCDStr(params.get(0)));
				}else{
					time_bcd_0 = DEFAUL_TIME_BCD;
				}
				System.arraycopy(time_bcd_0, 0, body, 1, time_bcd_0.length);
				
				data = DB44PkgUtil.encode((byte) 0x19, body);
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_RESET://终端复位
				body = new byte[1];
				int p = 1;
				if(params.size() > 0){
					int param0 = Integer.valueOf(params.get(0));
					if(param0 >= 1 && param0 <= 3){
						p = param0;
					}
				}
				
				body[0] = (byte) p;
				data = DB44PkgUtil.encode((byte) 0x1C, body);
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_LIMIT_SPEED://限速
				if(params.size() < 1){
					throw new WrongFormatException("(db44)no param!");
				}
				
				int speed = Integer.valueOf(params.get(0));
				if(speed < 1){
					speed = 1;
				}else if(speed > 255){
					speed = 255;
				}
				
				int duration = 1;
				if(params.size() >= 2){
					duration = Integer.valueOf(params.get(1));
					if(duration < 1){
						duration = 1;
					}else if(duration > 255){
						duration = 255;
					}
				}
				
				body = new byte[2];
				body[0] = (byte) speed;
				body[1] = (byte) duration;
				
				data = DB44PkgUtil.encode((byte) 0x06, body);
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_CANCEL_LIMIT_SPEED://取消限速
				body = new byte[]{0, 1};
				
				data = DB44PkgUtil.encode((byte) 0x06, body);
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_SEND_SM://下发短信		
				if(params.size() < 1){
					throw new WrongFormatException("(db44)no param!");
				}
				
				String sm = params.get(0);
				body = sm.getBytes(DB44PkgUtil.CN_CHAR_ENCODING);
				
				data = DB44PkgUtil.encode((byte) 0x15, body);
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
				
			case CmdUtil.CMD_ID_SET_TCP_UDP_PARAMS://设置TCP/UDP通信参数	
//				if(params.size() < 3){
//					throw new WrongFormatException("(db44)not enough params, min: 3, current:" + params.size());
//				}
//				
//				String sip = params.get(1);
//				int port = Integer.valueOf(params.get(2));
//				String[] sip_a = sip.split("\\.");
//				body = new byte[7];
//				body[0] = 0xA;
//				for(int i = 0 ; i < sip_a.length; i++){
//					int v = Integer.valueOf(sip_a[i]);
//					body[1 + i] = (byte) v;
//				}
//				DB44PkgUtil.copyShort(body, 5, port);
//				
//				data = DB44PkgUtil.encode((byte) 0x1B, body);
//				serverToUnitCommand.setData(data);
//				serverToUnitCommand.setResponseType(PackageEncoder.SUCCESS_AFTER_RESPONSE);
//				return serverToUnitCommand;
				
				if(params.size() < 4){
					throw new WrongFormatException("(db44)not enough params, min: 4, current:" + params.size());
				}
				
				int index = Integer.valueOf(params.get(0));
				if(index != 1 && index != 2){
					throw new WrongFormatException("(db44)error index(1 or 2):" + index);
				}
				
				String sip = params.get(2);
				String sport = params.get(3);
				byte[] bs_ip = cc.chinagps.gateway.util.Util.getIPBytes(sip);
				int port = Integer.valueOf(sport);
				byte[] bs_port = Util.getShortByte((short) port);
				
				body = new byte[7];
				int position = 0;
				
				body[position] = (byte) (index == 1? 0xA: 0xB);
				position += 1;
				
				System.arraycopy(bs_ip, 0, body, position, bs_ip.length);
				position += bs_ip.length;
				
				System.arraycopy(bs_port, 0, body, position, bs_port.length);
				
				data = DB44PkgUtil.encode((byte) 0x1B, body);
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_SET_SM_CENTER_NUM://设置短消息中心服务号码
				if(params.size() < 1){
					throw new WrongFormatException("(db44)not enough params, min: 2, current:" + params.size());
				}
				
				String call = params.get(0);
				if(call.length() > 15){
					throw new WrongFormatException("(db44)call too long, max 15, current:" + call.length());
				}
		
				body = new byte[16];
				body[0] = 0xC;
				byte[] callBytes = call.getBytes(DB44PkgUtil.EN_CHAR_ENCODING);
				int offset = 16 - callBytes.length;
				System.arraycopy(callBytes, 0, body, offset, callBytes.length);
				
				data = DB44PkgUtil.encode((byte) 0x1B, body);
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_RESPONSE);
				return serverToUnitCommand;
			case CmdUtil.CMD_ID_PASS_THROUGH://透传指令
				if(params.size() < 1){
					throw new WrongFormatException("(db44)no param!");
				}
				
				data = HexUtil.hexToBytes(params.get(0));
				serverToUnitCommand.setData(data);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_SEND);
				return serverToUnitCommand;
			default:
				throw new WrongFormatException("(db44)no encoder, cmdId:" + cmdIdx);
		}
	}
	
	//2099-09-09 09:09:09
	private static byte[] DEFAUL_TIME_BCD = Util.str2bcd("990909090909");
	
	public static void main(String[] args) {
//		for(int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; i++){
//			String s = String.valueOf(i);
//			byte b = Byte.valueOf(s);
//			System.out.println("[" + i + "]:" + b);
//		}
		
		String time = "2013-10-10 11:12:13";
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyMMddHHmmss");
		
		try {
			Date d = sdf1.parse(time);
			System.out.println(sdf2.format(d));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
