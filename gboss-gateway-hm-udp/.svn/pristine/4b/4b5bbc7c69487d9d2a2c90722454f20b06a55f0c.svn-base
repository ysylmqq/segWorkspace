package cc.chinagps.gateway.unit.beforemarket.kaiyi.download;

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
import cc.chinagps.gateway.unit.seg.download.SegServerToUnitCommand;
import cc.chinagps.gateway.util.HexUtil;
import cc.chinagps.gateway.util.SystemConfig;

public class KaiyiDownloadCmdEncoder implements OutputPackageEncoder{
	private boolean DEFAULT_IS_COMPRESS;
	
	private boolean DEFAULT_IS_ENCRYPT;
	
	public KaiyiDownloadCmdEncoder(){
		DEFAULT_IS_COMPRESS = Boolean.valueOf(SystemConfig.getSystemProperty("kaiyi_use_compress"));
		DEFAULT_IS_ENCRYPT = Boolean.valueOf(SystemConfig.getSystemProperty("kaiyi_use_encrypt"));
	}
	
	@Override
	public ServerToUnitCommand encode(Command userCmd,
			UnitSocketSession unitSession) throws Exception {
		int cmdIdx = userCmd.getCmdId();
		switch (cmdIdx) {
			//设置指令
			case CmdUtil.CMD_ID_SET_TCP_UDP_PARAMS://设置TCP/UDP通信参数
				return getSetTCPParamCmd(userCmd, unitSession);
			case CmdUtil.CMD_ID_SET_RECONNECT_INTERVAL://设置GPRS重连时间间隔
				return getSetReconnectParamCmd(userCmd, unitSession);
			case CmdUtil.CMD_ID_SET_APN://设置APN
				return getSetAPNCmd(userCmd, unitSession);
			case CmdUtil.CMD_ID_SET_SERRVICE_TEL://更改服务电话
				return getSetTelCmd(userCmd, unitSession, (byte) 0x2);
			case CmdUtil.CMD_ID_SET_SM_IN_NUM://设置短信呼入特服号(呼入呼出)
				return getSetSmInNumCmd(userCmd, unitSession);
			case CmdUtil.CMD_ID_START_UPLOAD_BY_INTERVAL://定时报告
				return getStartUploadCmd(userCmd, unitSession);
			case CmdUtil.CMD_ID_STOP_UPLOAD_BY_INTERVAL://关闭定时报告
				return getStopUploadCmd(userCmd, unitSession);
			case CmdUtil.CMD_ID_START_UPLOAD_WHEN_ACC_ON_OFF://开启点火熄火报告
				return getCommonSetCmd(userCmd, unitSession, (byte) 0x13, true);
			case CmdUtil.CMD_ID_STOP_UPLOAD_WHEN_ACC_ON_OFF://关闭点火熄火报告
				return getCommonSetCmd(userCmd, unitSession, (byte) 0x13, false);
			case CmdUtil.CMD_ID_START_UPLOAD_WHEN_VEHICLE_STATUS_CHANGED://开启车身状态变化报告
				return getCommonSetCmd(userCmd, unitSession, (byte) 0x16, true);
			case CmdUtil.CMD_ID_STOP_UPLOAD_WHEN_VEHICLE_STATUS_CHANGED://关闭车身状态变化报告
				return getCommonSetCmd(userCmd, unitSession, (byte) 0x16, false);
			case CmdUtil.CMD_ID_SET_HELP_TEL://设置故障/救援服务号码
				return getSetTelCmd(userCmd, unitSession, (byte) 0x18);
			case CmdUtil.CMD_ID_SET_UPLOAD_FAULT://设置上报故障码
				return getCommonSetCmd(userCmd, unitSession, (byte) 0x19, true);
			case CmdUtil.CMD_ID_SET_NOT_UPLOAD_FAULT://设置不上报故障码
				return getCommonSetCmd(userCmd, unitSession, (byte) 0x19, false);
				
			//查询指令
			case CmdUtil.CMD_ID_QUERY_TCP_UDP_PARAMS://查询TCP/UDP通信参数
				return getQueryNetParamsCmd(userCmd, unitSession);
			case CmdUtil.CMD_ID_QUERY_CALL_CENTER://呼叫中心
				return getCommonQueryCmd(userCmd, unitSession, (byte) 0x2);
			case CmdUtil.CMD_ID_QUERY_SM_CENTER://短信业务中心号码
				return getCommonQueryCmd(userCmd, unitSession, (byte) 0x3);
			case CmdUtil.CMD_ID_QUERY_HELP_TEL://查询故障/救援服务号码
				return getCommonQueryCmd(userCmd, unitSession, (byte) 0x5);
			case CmdUtil.CMD_ID_QUERY_UPLOAD_PARAM://定时上报参数
				return getCommonQueryCmd(userCmd, unitSession, (byte) 0x12);
			case CmdUtil.CMD_ID_QUERY_ACC_CHANFE_PARAM://ACC变化上报参数
				return getCommonQueryCmd(userCmd, unitSession, (byte) 0x13);
			case CmdUtil.CMD_ID_QUERY_CAR_BODY_CHANGE_PARAM://车身状态变化上报参数
				return getCommonQueryCmd(userCmd, unitSession, (byte) 0x16);
			case CmdUtil.CMD_ID_POSITION://查车
				return getCommonQueryCmd(userCmd, unitSession, (byte) 0x21);
			case CmdUtil.CMD_ID_TRACE://跟踪
				return getTraceCmd(userCmd, unitSession);
			case CmdUtil.CMD_ID_STOP_TRACE://停止跟踪
				return getStopTraceCmd(userCmd, unitSession);
				
			//控制指令
			case CmdUtil.CMD_ID_LOCK_DOOR: //锁车门
				return getCommonControlCmd(userCmd, unitSession, (byte) 0x1, false);
			case CmdUtil.CMD_ID_OPEN_DOOR: //开车门
				return getCommonControlCmd(userCmd, unitSession, (byte) 0x1, true);
			case CmdUtil.CMD_ID_CLOSE_WINDOW://关车窗
				return getCommonControlCmd(userCmd, unitSession, (byte) 0x3, false);
			case CmdUtil.CMD_ID_OPEN_WINDOW://开车窗
				return getCommonControlCmd(userCmd, unitSession, (byte) 0x3, true);
			case CmdUtil.CMD_ID_BOX_OEPN_TAIL_DOOR://开后尾箱
				return getCommonControlCmd(userCmd, unitSession, (byte) 0x5, true);
			case CmdUtil.CMD_ID_SEARCH_VEHICLE://找车
				return getCommonControlCmd(userCmd, unitSession, (byte) 0x6, true);
			case CmdUtil.CMD_ID_QUERY_FAULT://OBD故障
				return getCommonControlCmd(userCmd, unitSession, (byte) 0x09, true);
				
			//导航	
			case CmdUtil.CMD_ID_SET_TARGET_QUERY_POINT://(TN)设置导航仪目的查询点
				return getSetNavigationDestinationCmd(userCmd, unitSession);
				
			//升级
			case CmdUtil.CMD_ID_UPDATE_UNIT://通知终端升级
				return getUpdateUnitCmd(userCmd, unitSession);
			case CmdUtil.CMD_ID_QUERY_UPDATE_STATUS:
				return getQueryUpdateStatusCmd(userCmd, unitSession);
				
			//透传指令
			case CmdUtil.CMD_ID_PASS_THROUGH:
				List<String> params = userCmd.getParamsList();
				if(params.size() < 1){
					throw new WrongFormatException("(kaiyi)no param!");
				}
				
				byte[] source = HexUtil.hexToBytes(params.get(0));				
				SegServerToUnitCommand serverToUnitCommand = new SegServerToUnitCommand();		
				serverToUnitCommand.setData(source);
				serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_SEND);
				return serverToUnitCommand;
			default:
				throw new WrongFormatException("(kaiyi)no encoder, cmdId:" + cmdIdx);
		}
	}
	
	/**
	 * [0x2] 通用设置指令(开、关量)
	 * @throws Exception 
	 */
	private ServerToUnitCommand getCommonSetCmd(Command userCmd,
			UnitSocketSession unitSession, byte cmdId, boolean open) throws Exception{
		byte[] body = new byte[8];
		addTime(body);
		body[6] = cmdId;
		body[7] = (byte) (open? 0x1: 0x0);
		
		return makeCmd(userCmd, unitSession, (byte) 0x2, body);
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
	 * [0x5] 通用控制指令(开、关量)
	 * @throws Exception 
	 */
	private ServerToUnitCommand getCommonControlCmd(Command userCmd,
			UnitSocketSession unitSession, byte cmdId, boolean open) throws Exception{
		byte[] body = new byte[8];
		addTime(body);
		body[6] = cmdId;
		body[7] = (byte) (open? 0x1: 0x0);
		
		return makeCmd(userCmd, unitSession, (byte) 0x5, body);
	}
	
	/**
	 * 查询网络通信参数
	 * @throws Exception 
	 */
	private ServerToUnitCommand getQueryNetParamsCmd(Command userCmd,
			UnitSocketSession unitSession) throws Exception{
		byte subCmdId = 0x1;
		return getCommonQueryCmd(userCmd, unitSession, subCmdId);
	}
	
	/**
	 * 通知终端升级
	 * @throws Exception 
	 */
	private ServerToUnitCommand getUpdateUnitCmd(Command userCmd,
			UnitSocketSession unitSession) throws Exception{
		List<String> params = userCmd.getParamsList();
		if(params.size() < 3){
			throw new WrongFormatException("(kaiyi)not enough params, need: 3, current:" + params.size());
		}
		
		byte[] body = new byte[27];
		body[0] = 0x1;
		
		String sip = params.get(0);
		String[] sip_a = sip.split("\\.");
		for(int i = 0 ; i < 4; i++){
			int v = Integer.valueOf(sip_a[i]);
			body[1 + i] = (byte) v;
		}
		
		int port = Integer.valueOf(params.get(1));
		byte[] bs_port = Util.getShortByte((short) port);
		System.arraycopy(bs_port, 0, body, 5, bs_port.length);
		
		byte[] bs_fileName = params.get(2).getBytes(SystemConfig.getSystemProperty("update_file_name_encoding"));
		System.arraycopy(bs_fileName, 0, body, 7, Math.min(bs_fileName.length, 20));
		
		return makeCmd(userCmd, unitSession, (byte) 0x1A, body);
	}
	
	/**
	 * 查询升级状态
	 * @throws Exception 
	 */
	private ServerToUnitCommand getQueryUpdateStatusCmd(Command userCmd,
			UnitSocketSession unitSession) throws Exception{
		byte[] body = new byte[1];
		body[0] = 0x5;
		
		return makeCmd(userCmd, unitSession, (byte) 0x1A, body);
	}
	
	/**
	 * 跟踪
	 * @throws Exception 
	 */
	private ServerToUnitCommand getTraceCmd(Command userCmd,
			UnitSocketSession unitSession) throws Exception{
		List<String> params = userCmd.getParamsList();
		if(params.size() < 2){
			throw new WrongFormatException("hm cmdIdx:" + userCmd.getCmdId() + ", param count:" + params.size() + ", min param count is 2");
		}
		
		int count = Integer.valueOf(params.get(0));
		int interval = Integer.valueOf(params.get(1));
		if(count < 1 || count > 65535){
			throw new WrongFormatException("hm cmdIdx:" + userCmd.getCmdId() + ", count:" + count);
		}
		
		if(interval < 1 || interval > 65535){
			throw new WrongFormatException("hm cmdIdx:" + userCmd.getCmdId() + ", interval:" + interval);
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
			throw new WrongFormatException("(kaiyi)no param!");
		}
		
		String stime = params.get(0);
		int time = Integer.valueOf(stime);
		if(time < 1 || time > 65535){
			throw new WrongFormatException("(kaiyi)param error[1-65535]:" + time);
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
		List<String> params = userCmd.getParamsList();
//		if(params.size() < 1){
//			throw new WrongFormatException("(kaiyi)no param");
//		}
//		
//		int index = Integer.valueOf(params.get(0));
//		if(index == 1){
//			//设置网络参数1
//			if(params.size() < 8){
//				throw new WrongFormatException("(kaiyi)not enough params, min: 8, current:" + params.size());
//			}
//			
//			String sapn = params.get(1);
//			String sip = params.get(2);
//			String sport = params.get(3);
//			String sinterval = params.get(7);
//			
//			byte[] bs_ip = cc.chinagps.gateway.util.Util.getIPBytes(sip);
//			int port = Integer.valueOf(sport);
//			byte[] bs_port = Util.getShortByte((short) port);
//			int interval = Integer.valueOf(sinterval);
//			byte[] b_interval = Util.getShortByte((short) interval);
//			byte[] bs_apn = sapn.getBytes();
//			
//			byte[] body = new byte[15 + bs_apn.length];
//			addTime(body);
//			body[6] = 0x1;
//			int position = 7;
//			
//			System.arraycopy(bs_ip, 0, body, position, bs_ip.length);
//			position += bs_ip.length;
//			
//			System.arraycopy(bs_port, 0, body, position, bs_port.length);
//			position += bs_port.length;
//			
//			System.arraycopy(b_interval, 0, body, position, b_interval.length);
//			position += b_interval.length;
//			
//			System.arraycopy(bs_apn, 0, body, position, bs_apn.length);
//			//position += bs_apn.length;
//			
//			return makeCmd(userCmd, unitSession, (byte) 0x2, body);
//		}
//
//		throw new WrongFormatException("(kaiyi)error index:" + index);
		
		if(params.size() < 8){
			throw new WrongFormatException("(kaiyi)not enough params, min: 8, current:" + params.size());
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
			throw new WrongFormatException("(kaiyi)no param!");
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
			throw new WrongFormatException("(kaiyi)no param!");
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
			throw new WrongFormatException("(kaiyi)no param!");
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
//		List<String> params = userCmd.getParamsList();
//		if(params.size() < 2){
//			throw new WrongFormatException("(kaiyi)not enough params, min: 2, current:" + params.size());
//		}
//		
//		String sms_tel = params.get(0);
//		String sms_type = params.get(1);
//		
//		byte[] body = new byte[57];
//		addTime(body);
//		body[6] = 0x3;
//		
//		byte[] b_tel = sms_tel.getBytes(HMPkgUtil.EN_CHAR_ENCODING);
//		if("1".equals(sms_type)){
//			//呼入
//			System.arraycopy(b_tel, 0, body, 32, Math.min(b_tel.length, 25));
//		}else if("2".equals(sms_type)){
//			//呼出
//			System.arraycopy(b_tel, 0, body, 7, Math.min(b_tel.length, 25));
//		}else{
//			//同时
//			System.arraycopy(b_tel, 0, body, 7, Math.min(b_tel.length, 25));
//			System.arraycopy(b_tel, 0, body, 32, Math.min(b_tel.length, 25));
//		}
		
		List<String> params = userCmd.getParamsList();
		if(params.size() < 1){
			throw new WrongFormatException("(kaiyi)no param");
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
	 * 查询TCP通信参数
	 * @throws Exception 
	 */
//	private ServerToUnitCommand getQueryTCPParamCmd(Command userCmd,
//			UnitSocketSession unitSession) throws Exception{
//		byte[] body = new byte[7];
//		addTime(body);
//		body[6] = 0x1;
//		
//		return makeCmd(userCmd, unitSession, (byte) 0x3, body);
//	}
	
	/**
	 * 设置导航终点
	 * @throws Exception 
	 */
	private ServerToUnitCommand getSetNavigationDestinationCmd(Command userCmd,
			UnitSocketSession unitSession) throws Exception{
		List<String> params = userCmd.getParamsList();
		if(params.size() < 3){
			throw new WrongFormatException("(kaiyi)not enough params, min: 3, current:" + params.size());
		}
		
		int type = Integer.valueOf(params.get(0));
		int lon = (int) (Double.valueOf(params.get(1)) * 10000000);
		int lat = (int) (Double.valueOf(params.get(2)) * 10000000);
		
		//必经点
		String[] a_latlngs = null;
		if(params.size() >= 4 && params.get(3).length() > 0){
			a_latlngs = params.get(3).split(";");
			if(a_latlngs.length > 3){
				throw new WrongFormatException("(kaiyi)必经点过多,最大3,当前:" + a_latlngs.length);
			}
		}
		
		//回避点
		String[] b_latlngs = null;
		if(params.size() >= 5 && params.get(4).length() > 0){
			b_latlngs = params.get(4).split(";");
			if(b_latlngs.length > 2){
				throw new WrongFormatException("(kaiyi)回避点过多,最大2,当前:" + b_latlngs.length);
			}
		}
		
		int len = 17;
		if(a_latlngs != null){
			len += 8 * a_latlngs.length;
		}
		
		if(b_latlngs != null){
			len += 8 * b_latlngs.length;
		}
		
		byte[] body = new byte[len];
		addTime(body);
		body[6] = (byte) type;
		body[7] = (byte) (a_latlngs == null? 0: a_latlngs.length);
		body[8] = (byte) (b_latlngs == null? 0: b_latlngs.length);
		
		//byte[] bs_lat = HMNumberUtil.getHMIntByte(lat);
		//byte[] bs_lon = HMNumberUtil.getHMIntByte(lon);
		byte[] bs_lat = Util.getIntByte(lat);
		byte[] bs_lon = Util.getIntByte(lon);
		System.arraycopy(bs_lat, 0, body, 9, 4);
		System.arraycopy(bs_lon, 0, body, 13, 4);
		
		int current_offset = 17;
		if(a_latlngs != null){
			for(String latlng: a_latlngs){
				String[] alatlng = latlng.split(",");
				int a_lon = (int) (Double.valueOf(alatlng[0]) * 10000000);
				int a_lat = (int) (Double.valueOf(alatlng[1]) * 10000000);
				
				//byte[] bs_a_lon = HMNumberUtil.getHMIntByte(a_lon);
				//byte[] bs_a_lat = HMNumberUtil.getHMIntByte(a_lat);
				byte[] bs_a_lon = Util.getIntByte(a_lon);
				byte[] bs_a_lat = Util.getIntByte(a_lat);
				
				System.arraycopy(bs_a_lat, 0, body, current_offset, 4);
				current_offset += 4;
				System.arraycopy(bs_a_lon, 0, body, current_offset, 4);
				current_offset += 4;
			}
		}
		
		if(b_latlngs != null){
			for(String latlng: b_latlngs){
				String[] blatlng = latlng.split(",");
				int b_lon = (int) (Double.valueOf(blatlng[0]) * 10000000);
				int b_lat = (int) (Double.valueOf(blatlng[1]) * 10000000);
				
				//byte[] bs_b_lon = HMNumberUtil.getHMIntByte(b_lon);
				//byte[] bs_b_lat = HMNumberUtil.getHMIntByte(b_lat);
				byte[] bs_b_lon = Util.getIntByte(b_lon);
				byte[] bs_b_lat = Util.getIntByte(b_lat);
				
				System.arraycopy(bs_b_lat, 0, body, current_offset, 4);
				current_offset += 4;
				System.arraycopy(bs_b_lon, 0, body, current_offset, 4);
				current_offset += 4;
			}
		}
		
		return makeCmd(userCmd, unitSession, (byte) 0x11, body);
	}
	
//	/**
//	 * 清除故障码
//	 * @throws Exception 
//	 */
//	private ServerToUnitCommand getClearFaultCmd(Command userCmd,
//			UnitSocketSession unitSession) throws Exception{
//		byte[] body = new byte[7];
//		addTime(body);
//		body[6] = 0x8;
//		
//		return makeCmd(userCmd, unitSession, (byte) 0x5, body);
//	}
	
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
		head.setTerminalType(BeforeMarketConstants.PROTOCOL_TERMINAL_TYPE_KAIYI);
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