package cc.chinagps.gateway.unit.beforemarket.hm.download;

import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;
import org.seg.lib.util.Util;

import cc.chinagps.gateway.aplan.pkg.APlanUtil;
import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.exceptions.WrongFormatException;
import cc.chinagps.gateway.log.LogManager;
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

public class HMDownloadCmdEncoder implements OutputPackageEncoder {

	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);

	private boolean DEFAULT_IS_COMPRESS;

	private boolean DEFAULT_IS_ENCRYPT;

	public HMDownloadCmdEncoder() {
		DEFAULT_IS_COMPRESS = Boolean.valueOf(SystemConfig.getSystemProperty("hm_use_compress"));
		DEFAULT_IS_ENCRYPT = Boolean.valueOf(SystemConfig.getSystemProperty("hm_use_encrypt"));
	}

	@Override
	public ServerToUnitCommand encode(Command userCmd, UnitSocketSession unitSession) throws Exception {
		int cmdIdx = userCmd.getCmdId();
		switch (cmdIdx) {
		case CmdUtil.CMD_ID_ENTIRE_CAR_OTA_UPGRADE://整车远程升级
			return allRemoteUpgrade(userCmd, unitSession);
		/*case CmdUtil.CMD_ID_ENTIRE_CAR_OTA_UPGRADE_STATUS://网关向终端查询升级状态
			return getQueryHMUpdateStatusCmd(userCmd, unitSession); */
		case CmdUtil.CMD_ID_POSITION:// 查车
			return getCommonQueryCmd(userCmd, unitSession, (byte) 0x21);
		case CmdUtil.CMD_ID_TRACE:// 跟踪
			return getTraceCmd(userCmd, unitSession);
		case CmdUtil.CMD_ID_STOP_TRACE:// 停止跟踪
			return getStopTraceCmd(userCmd, unitSession);
		case CmdUtil.CMD_ID_START_UPLOAD_BY_INTERVAL:// 定时报告
			return getStartUploadCmd(userCmd, unitSession);
		case CmdUtil.CMD_ID_STOP_UPLOAD_BY_INTERVAL:// 关闭定时报告
			return getStopUploadCmd(userCmd, unitSession);

		case CmdUtil.CMD_ID_START_UPLOAD_BY_DISTANCE:// 开启定距上传
		case CmdUtil.CMD_ID_STOP_UPLOAD_BY_DISTANCE:// 关闭定距上传
		case CmdUtil.CMD_ID_CUT_OFF_OIL:// 断油电
		case CmdUtil.CMD_ID_RESUME_OIL:// 恢复油电
			throw new WrongFormatException("(hm)无此指令");
		case CmdUtil.CMD_ID_RESET:// 终端复位
			return getResetCmd(userCmd, unitSession);
		case CmdUtil.CMD_ID_SET_ECU_CONFIG: // 设置电控单元配置
			return getSetECUConfigCmd(userCmd, unitSession);
		case CmdUtil.CMD_ID_QUERY_ECU_CONFIG: // 查询电控单元配置
			return getCommonQueryCmd(userCmd, unitSession, (byte) 0x22);

		case CmdUtil.CMD_ID_FORBID_TEL_IN:// 禁止电话打入
			return getForbidCallInCmd(userCmd, unitSession);
		case CmdUtil.CMD_ID_FORBID_TEL_OUT:// 禁止电话打出
			return getForbidCallOutCmd(userCmd, unitSession);

		case CmdUtil.CMD_ID_GROUP_TEL:// 设定集群通话
			throw new WrongFormatException("(hm)无此指令");
		case CmdUtil.CMD_ID_RESUME_TEL:// 恢复电话功能
			return getResumeTelCmd(userCmd, unitSession);

		case CmdUtil.CMD_ID_MONITOR:// 监听
		case CmdUtil.CMD_ID_SET_MONITOR_TEL:// 设置监听号码
		case CmdUtil.CMD_ID_LIMIT_SPEED:// 限速
		case CmdUtil.CMD_ID_CANCEL_LIMIT_SPEED:// 取消限速
		case CmdUtil.CMD_ID_SEND_SM:// 下发短信
			throw new WrongFormatException("(hm)无此指令");

		case CmdUtil.CMD_ID_SET_TCP_UDP_PARAMS:// 设置TCP/UDP通信参数
			return getSetTCPParamCmd(userCmd, unitSession);
		case CmdUtil.CMD_ID_QUERY_TCP_UDP_PARAMS:// 查询TCP/UDP通信参数
			// return getCommonQueryCmd(userCmd, unitSession, (byte) 0x1);
			return getQueryNetParamsCmd(userCmd, unitSession);
		case CmdUtil.CMD_ID_SET_APN:// 设置APN
			return getSetAPNCmd(userCmd, unitSession);
		case CmdUtil.CMD_ID_SET_SERRVICE_TEL:// 更改服务电话
			return getSetTelCmd(userCmd, unitSession, (byte) 0x2);

		case CmdUtil.CMD_ID_SET_SM_CENTER_NUM:// 设置短消息中心服务号码
			throw new WrongFormatException("(hm)无此指令");

		case CmdUtil.CMD_ID_SET_SM_IN_NUM:// 设置短信呼入特服号(呼入呼出)
			return getSetSmInNumCmd(userCmd, unitSession);
		case CmdUtil.CMD_ID_SET_RECONNECT_INTERVAL:// 设置GPRS重连时间间隔
			return getSetReconnectParamCmd(userCmd, unitSession);
		case CmdUtil.CMD_ID_SET_TARGET_QUERY_POINT:// (TN)设置导航仪目的查询点
			return getSetNavigationDestinationCmd(userCmd, unitSession);
		case CmdUtil.CMD_ID_LOCK_DOOR: // 锁车门
			return getCommonControlCmd(userCmd, unitSession, (byte) 0x1, false);
		case CmdUtil.CMD_ID_OPEN_DOOR: // 开车门
			return getCommonControlCmd(userCmd, unitSession, (byte) 0x1, true);
		case CmdUtil.CMD_ID_UPLOAD_HISTORY: // 上传行车记录
			throw new WrongFormatException("(hm)无此指令");

		case CmdUtil.CMD_ID_SEARCH_VEHICLE:// 找车
			return getCommonControlCmd(userCmd, unitSession, (byte) 0x6, true);
		case CmdUtil.CMD_ID_CLOSE_WINDOW:// 关车窗
			return getCommonControlCmd(userCmd, unitSession, (byte) 0x3, false);
		case CmdUtil.CMD_ID_OPEN_WINDOW:// 开车窗
			return getCommonControlCmd(userCmd, unitSession, (byte) 0x3, true);

		case CmdUtil.CMD_ID_READ_DEFINED_TYPE:// 读取车型
		case CmdUtil.CMD_ID_DEFINED_TYPE:// 标定车型
			throw new WrongFormatException("(hm)无此指令");

		case CmdUtil.CMD_ID_CLEAR_FAULT_CODE:// 清除故障码
			return getClearFaultCmd(userCmd, unitSession);

		// add for海马
		// 设置指令
		case CmdUtil.CMD_ID_SET_HELP_TEL:// 设置故障/救援服务号码
			return getSetTelCmd(userCmd, unitSession, (byte) 0x18);
		case CmdUtil.CMD_ID_SET_UPLOAD_FAULT:// 设置上报故障码
			return getCommonSetCmd(userCmd, unitSession, (byte) 0x19, true);
		case CmdUtil.CMD_ID_SET_NOT_UPLOAD_FAULT:// 设置不上报故障码
			return getCommonSetCmd(userCmd, unitSession, (byte) 0x19, false);
		case CmdUtil.CMD_ID_START_UPLOAD_WHEN_ACC_ON_OFF:// 开启点火熄火报告
			return getCommonSetCmd(userCmd, unitSession, (byte) 0x13, true);
		case CmdUtil.CMD_ID_STOP_UPLOAD_WHEN_ACC_ON_OFF:// 关闭点火熄火报告
			return getCommonSetCmd(userCmd, unitSession, (byte) 0x13, false);
		case CmdUtil.CMD_ID_START_UPLOAD_WHEN_VEHICLE_STATUS_CHANGED:// 开启车身状态变化报告
			return getCommonSetCmd(userCmd, unitSession, (byte) 0x16, true);
		case CmdUtil.CMD_ID_STOP_UPLOAD_WHEN_VEHICLE_STATUS_CHANGED:// 关闭车身状态变化报告
			return getCommonSetCmd(userCmd, unitSession, (byte) 0x16, false);
		case CmdUtil.CMD_ID_START_UPLOAD_WHEN_POWER_OFF:// 开启关机报告
			return getCommonSetCmd(userCmd, unitSession, (byte) 0x15, true);
		case CmdUtil.CMD_ID_STOP_UPLOAD_WHEN_POWER_OFF:// 关闭关机报告
			return getCommonSetCmd(userCmd, unitSession, (byte) 0x15, false);
		case CmdUtil.CMD_ID_START_UPLOAD_WHEN_SLEEP:// 开启休眠报告
			return getCommonSetCmd(userCmd, unitSession, (byte) 0x14, true);
		case CmdUtil.CMD_ID_STOP_UPLOAD_WHEN_SLEEP:// 关闭休眠报告
			return getCommonSetCmd(userCmd, unitSession, (byte) 0x14, false);
		case CmdUtil.CMD_ID_SET_ROLL_PARAM:// 设置侧翻校正参数
			return getSetRollParamCmd(userCmd, unitSession);
		// 控制命令
		// 锁门前面已有
		// 开门前面已有
		// 关窗前面已有
		// 开窗前面已有
		case CmdUtil.CMD_ID_BOX_CLOSE_LIGHT:// 关灯
			return getCommonControlCmd(userCmd, unitSession, (byte) 0x2, false);
		case CmdUtil.CMD_ID_BOX_OPEN_LIGHT:// 开灯
			return getCommonControlCmd(userCmd, unitSession, (byte) 0x2, true);
		// 找车前面已有
		case CmdUtil.CMD_ID_BOX_OEPN_TAIL_DOOR:// 开后尾箱
			return getCommonControlCmd(userCmd, unitSession, (byte) 0x5, true);
		case CmdUtil.CMD_ID_BOX_CLOSE_AIR_CONDITIONING:// 关空调
			return getCloseAirConditioningCmd(userCmd, unitSession);
		case CmdUtil.CMD_ID_BOX_OEPN_AIR_CONDITIONING:// 开空调
			return getOpenOrSetAirConditioningCmd(userCmd, unitSession, 1);
		/*
		 * case CmdUtil.CMD_ID_BOX_SET_AIR_CONDITIONING: //设置空调 return
		 * getOpenOrSetAirConditioningCmd(userCmd, unitSession, 2);
		 */
		case CmdUtil.CMD_ID_BOX_START_ENGINE:// 开启发动机
			List<String> paramList = userCmd.getParamsList();
			if (paramList.size() < 1) {
				throw new WrongFormatException("(hm)not enough params, min: 1, current:" + paramList.size());
			}
			byte[] body = new byte[9];
			addTime(body);
			body[6] = (byte) 0xA;
			body[7] = (byte) 0x1;
			body[8] = Byte.valueOf(paramList.get(0));
			return makeCmd(userCmd, unitSession, (byte) 0x5, body);
		// return getCommonControlCmd(userCmd, unitSession, (byte) 0xA, true);
		case CmdUtil.CMD_ID_BOX_STOP_ENGINE:// 关闭发动机
			return getCommonControlCmd(userCmd, unitSession, (byte) 0xA, false);

		// 新增查询
		case CmdUtil.CMD_ID_QUERY_HELP_TEL:// 查询故障/救援服务号码
			return getCommonQueryCmd(userCmd, unitSession, (byte) 0x18);
		case CmdUtil.CMD_ID_QUERY_UPLOAD_FAULT_OR_NOT:// 查询故障是否上报
			return getCommonQueryCmd(userCmd, unitSession, (byte) 0x19);

		case CmdUtil.CMD_ID_QUERY_CALL_CENTER:// 呼叫中心
			return getCommonQueryCmd(userCmd, unitSession, (byte) 0x2);
		case CmdUtil.CMD_ID_QUERY_SM_CENTER:// 短信业务中心号码
			return getCommonQueryCmd(userCmd, unitSession, (byte) 0x3);
		case CmdUtil.CMD_ID_QUERY_UPLOAD_PARAM:// 定时上报参数
			return getCommonQueryCmd(userCmd, unitSession, (byte) 0x12);
		case CmdUtil.CMD_ID_QUERY_ACC_CHANFE_PARAM:// ACC变化上报参数
			return getCommonQueryCmd(userCmd, unitSession, (byte) 0x13);
		case CmdUtil.CMD_ID_QUERY_SLEEP_PARAM:// 休眼上报参数
			return getCommonQueryCmd(userCmd, unitSession, (byte) 0x14);
		case CmdUtil.CMD_ID_QUERY_POWEROFF_PARAM:// 关机上报参数
			return getCommonQueryCmd(userCmd, unitSession, (byte) 0x15);
		case CmdUtil.CMD_ID_QUERY_CAR_BODY_CHANGE_PARAM:// 车身状态变化上报参数
			return getCommonQueryCmd(userCmd, unitSession, (byte) 0x16);
		case CmdUtil.CMD_ID_QUERY_FAULT:// OBD故障
			return getCommonQueryCmd(userCmd, unitSession, (byte) 0x20);
		case CmdUtil.CMD_ID_QUERY_CALL_LIMIT:// 呼入呼出限制
			return getCommonQueryCmd(userCmd, unitSession, (byte) 0x1A);
		case CmdUtil.CMD_ID_UPDATE_UNIT:// 通知终端升级
			return getUpdateUnitCmd(userCmd, unitSession);
		case CmdUtil.CMD_ID_QUERY_UPDATE_STATUS:
			return getQueryUpdateStatusCmd(userCmd, unitSession);
		case CmdUtil.CMD_ID_QUERY_TBOX_INFO:// 读取终端（TBOX）信息
			return getCommonQueryCmd(userCmd, unitSession, (byte) 0x26);
		case CmdUtil.CMD_ID_QUERY_ROLL_PARAM:// 查询侧翻校正参数
			return getCommonQueryCmd(userCmd, unitSession, (byte) 0x25);
		case CmdUtil.CMD_ID_QUERY_AIR_CONDITIONING_PRARM:// 查询空调配置参数
			return getCommonQueryCmd(userCmd, unitSession, (byte) 0x27);
		// 新增查询end

		case CmdUtil.CMD_ID_TO_HARDWARE:// 透传给硬件
			return toHardware(userCmd, unitSession);
		// add for海马 end

		case CmdUtil.CMD_ID_PASS_THROUGH:// 透传指令
			List<String> params = userCmd.getParamsList();
			if (params.size() < 1) {
				throw new WrongFormatException("(hm)no param!");
			}

			byte[] source = HexUtil.hexToBytes(params.get(0));
			SegServerToUnitCommand serverToUnitCommand = new SegServerToUnitCommand();
			serverToUnitCommand.setData(source);
			serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_SEND);
			return serverToUnitCommand;
		case CmdUtil.CMD_ID_WIFI_AUTH:// wifi认证
			return wifiAuth(userCmd, unitSession, (byte) 0x01);
			
		case CmdUtil.CMD_ID_OPEN_SOUND_CONNECT_3G:// 允许音响主机连接3G模块
			return getCommonSetCmd(userCmd, unitSession, (byte) 0xF9, true); 
		case CmdUtil.CMD_ID_CLOSE_SOUND_CONNECT_3G:// 禁止音响主机连接3G模块
			return getCommonSetCmd(userCmd, unitSession, (byte) 0xF9, false);
		case CmdUtil.CMD_ID_QUERY_SOUND_CONNECT_3G:// 查询音响主机连接3G模块状态
			return getCommonQueryCmd(userCmd, unitSession, (byte) 0xF9);
		case CmdUtil.CMD_ID_OPEN_TBOX_SAVE_TRAFFIC:
			return getCommonSetCmd(userCmd, unitSession, (byte) 0xF8, true); 
		case CmdUtil.CMD_ID_CLOSE_TBOX_SAVE_TRAFFIC:
			return getCommonSetCmd(userCmd, unitSession, (byte) 0xF8, false);
		case CmdUtil.CMD_ID_OPEN_FRONT_WINDSHIELD_WIPER:// 打开前雨刷
			return getCommonControlCmd2(userCmd, unitSession, (byte) 0x0B, true);
		case CmdUtil.CMD_ID_CLOSE_FRONT_WINDSHIELD_WIPER:// 关闭前雨刷
			return getCommonControlCmd2(userCmd, unitSession, (byte) 0x0B, false);
			
		case CmdUtil.CMD_ID_OPEN_BACK_WINDSHIELD_WIPER:// 打开后雨刷
			return getCommonControlCmd2(userCmd, unitSession, (byte) 0x0C, true);
		case CmdUtil.CMD_ID_CLOSE_BACK_WINDSHIELD_WIPER:// 关闭后雨刷
			return getCommonControlCmd2(userCmd, unitSession, (byte) 0x0C, false);
			
		case CmdUtil.CMD_ID_EXPAND_BACK_MIRROR:// 展开后视镜
			return getCommonControlCmd2(userCmd, unitSession, (byte) 0x0D, true);
		case CmdUtil.CMD_ID_SHRINK_BACK_MIRROR:// 收缩后视镜
			return getCommonControlCmd2(userCmd, unitSession, (byte) 0x0D, false);
		default:
			throw new WrongFormatException("(hm)no encoder, cmdId:" + cmdIdx);
		}
	}

	/**
	 * 整车远程升级
	 * 
	 * @throws Exception
	 */
	private ServerToUnitCommand allRemoteUpgrade(Command userCmd, UnitSocketSession unitSession) throws Exception {
		logger_debug.debug(" allRemoteUpgrade start");
		List<String> params = userCmd.getParamsList(); 
		if (params.size() < 9) {
			throw new WrongFormatException("(hm)not enough params, need: 9, current:" + params.size());
		}

		byte[] body = new byte[79];
		body[0] = (byte) 0x01;
		
		String updateType = params.get(0);// 升级类型
		int intUpateType = Integer.valueOf(updateType);
		byte bsUpateType = (byte)intUpateType;
		body[1] = bsUpateType;

		String updateTime = params.get(1);// 升级时机
		int intUpateTime = Integer.valueOf(updateTime);
		byte bsupdateTime = (byte)intUpateTime;
		body[2] = bsupdateTime;


		String sip1 = params.get(2); // 升级服务IP1 192.168.2.135
		String[] sip_a1 = sip1.split("\\.");
		for (int i = 0; i < 4; i++) {
			int v = Integer.valueOf(sip_a1[i]);
			body[3 + i] = (byte) v;
		}
		
		int port1 = Integer.valueOf(params.get(3)); // 升级服务器PORT1 8090
		byte[] bs_port1 = Util.getShortByte((short) port1);
		System.arraycopy(bs_port1, 0, body, 7, 2);
		
		String sip2 = params.get(4); // 升级服务IP2
		String[] sip_a2 = sip2.split("\\.");
		for (int i = 0; i < 4; i++) {
			int v = Integer.valueOf(sip_a2[i]);
			body[9 + i] = (byte) v;
		}
		
		int port2 = Integer.valueOf(params.get(5)); // 升级服务器PORT2
		byte[] bs_port2 = Util.getShortByte((short) port2);
		System.arraycopy(bs_port2, 0, body, 13, 2);
		
		//
		int canId = Integer.valueOf(params.get(6)); // 升级目标CAN ID   0X758
		byte[] bs_canId = Util.getShortByte((short) canId);
		System.arraycopy(bs_canId, 0, body, 15, 2);
		
		//缓存canid  在网管查询升级结果时用到
/*		unitSession.setAttribute(BeforeMarketConstants.HM_OTA_SESSION_CAN_ID_KEY,canId);
*/
		
		String fileName = params.get(7);//升级文件名
		byte[] bs_fileName = APlanUtil.getBytesAddZero(fileName,30);
		System.arraycopy(bs_fileName, 0, body, 17, 30);
		
		unitSession.setAttribute(BeforeMarketConstants.HM_OTA_SESSION_KEY_REQUEST_FILE_INFO, fileName);
		
		String md5 = params.get(8);//md5  从通信中心 获取的md5字符串,32位
		byte[] bs_md5 = md5.getBytes();
		logger_debug.debug("md5 length: "+bs_md5.length);
		System.arraycopy(bs_md5, 0, body, 47, 32);
		logger_debug.debug("[allRemoteUpgrade] send data:"+HexUtil.byteToHexStr(body));

		logger_debug.debug("allRemoteUpgrade end");

		return makeCmd(userCmd, unitSession, (byte) 0x1C, body);
	}
	

	/**
	 * 海马 网关向终端查询升级状态
	 * 
	 * @throws Exception
	 */
	private ServerToUnitCommand getQueryHMUpdateStatusCmd(Command userCmd, UnitSocketSession unitSession)
			throws Exception {
		byte[] body = new byte[3];
		body[0] = 0x05;
		// canId 两个字节
		String canId = "";
		int canIdInt = Integer.parseInt(canId.trim());
		logger_debug.debug("[ hm update canId ] "+canIdInt);
		byte[] bs_canId = Util.getShortByte((short) canIdInt); // 总报数 占用2个字节  
		System.arraycopy(bs_canId, 0, body, 1, bs_canId.length);
		logger_debug.debug("[hm gateway query update result] "+HexUtil.byteToHexStr(body));
		return makeCmd(userCmd, unitSession, (byte) 0x1C, body);
	}
	
	/**
	 * [0x2] 通用设置指令(开、关量)
	 * 
	 * @throws Exception
	 */
	private ServerToUnitCommand getCommonSetCmd(Command userCmd, UnitSocketSession unitSession, byte cmdId,
			boolean open) throws Exception {
		byte[] body = new byte[8];
		addTime(body);
		body[6] = cmdId;
		body[7] = (byte) (open ? 0x1 : 0x0);

		return makeCmd(userCmd, unitSession, (byte) 0x2, body);
	}

	/**
	 * [0x3] 通用查询指令
	 * 
	 * @throws Exception
	 */
	private ServerToUnitCommand getCommonQueryCmd(Command userCmd, UnitSocketSession unitSession, byte cmdId)
			throws Exception {
		byte[] body = new byte[7];
		addTime(body);
		body[6] = cmdId;

		return makeCmd(userCmd, unitSession, (byte) 0x3, body);
	}

	/**
	 * [0x5] 通用控制指令(开、关量)
	 * 
	 * @throws Exception
	 */
	private ServerToUnitCommand getCommonControlCmd(Command userCmd, UnitSocketSession unitSession, byte cmdId,
			boolean open) throws Exception {
		byte[] body = new byte[8];
		addTime(body);
		body[6] = cmdId;
		body[7] = (byte) (open ? 0x1 : 0x0);

		return makeCmd(userCmd, unitSession, (byte) 0x5, body);
	}
	
	/**
	 * [0x5] 通用控制指令(开、关量)
	 * 前后雨刷、后视镜控制
	 * @throws Exception
	 */
	private ServerToUnitCommand getCommonControlCmd2(Command userCmd, UnitSocketSession unitSession, byte cmdId,
			boolean open) throws Exception {
		byte[] body = new byte[8];
		addTime(body);
		body[6] = cmdId;
		body[7] = (byte) (open ? 0x2 : 0x1);

		return makeCmd(userCmd, unitSession, (byte) 0x5, body);
	}

	/**
	 * [0x10] 透传给硬件
	 * 
	 * @throws Exception
	 */
	private ServerToUnitCommand toHardware(Command userCmd, UnitSocketSession unitSession) throws Exception {
		List<String> params = userCmd.getParamsList();
		if (params.size() < 1) {
			throw new WrongFormatException("(hm)no param!");
		}

		String hex = params.get(0);
		// bs max length = 65535
		if (hex.length() > 131070) {
			throw new WrongFormatException("(hm)big data, max:" + 131070 + ", current:" + hex.length());
		}

		byte[] data = HexUtil.hexToBytes(hex);
		byte[] body = new byte[data.length + 3];
		body[0] = 0x01;
		byte[] b_len = Util.getShortByte((short) data.length);
		System.arraycopy(b_len, 0, body, 1, 2);
		System.arraycopy(data, 0, body, 3, data.length);

		return makeCmd(userCmd, unitSession, (byte) 0x10, body);
	}

	/**
	 * 关闭空调
	 * 
	 * @throws Exception
	 */
	private ServerToUnitCommand getCloseAirConditioningCmd(Command userCmd, UnitSocketSession unitSession)
			throws Exception {
		return getControlAirConditioningCmd(userCmd, unitSession, 0, 0, 0, 0, 0);
	}

	/**
	 * 开/设置 空调
	 * 
	 * @throws Exception
	 */
	private ServerToUnitCommand getOpenOrSetAirConditioningCmd(Command userCmd, UnitSocketSession unitSession, int type)
			throws Exception {
		List<String> params = userCmd.getParamsList();
		if (params.size() < 8) {
			throw new WrongFormatException("(hm)not enough params, need: 8, current:" + params.size());
		}

		int temperature = (int) (Float.valueOf(params.get(0)) * 2);
		int airOutMode = Integer.valueOf(params.get(1));
		int windSpeed = Integer.valueOf(params.get(2));
		String cycleMode = params.get(3);
		if (!"0".equals(cycleMode) && !"1".equals(cycleMode))
			cycleMode = "0";
		String acMode = params.get(4);
		if (!"0".equals(acMode) && !"1".equals(acMode))
			acMode = "0";
		String autoMode = params.get(5);
		if (!"0".equals(autoMode) && !"1".equals(autoMode))
			autoMode = "0";
		String frontClearFrostMode = params.get(6);
		if (!"0".equals(frontClearFrostMode) && !"1".equals(frontClearFrostMode))
			frontClearFrostMode = "0";
		String backClearFrostMode = params.get(7);
		if (!"0".equals(backClearFrostMode) && !"1".equals(backClearFrostMode))
			backClearFrostMode = "0";

		String param3BinString = "000" + backClearFrostMode + frontClearFrostMode + autoMode + acMode + cycleMode;
		logger_debug.debug("[hm] OpenAirConditioningCmd param:" + params + ",param3BinString:" + param3BinString);
		int param3 = Integer.valueOf(param3BinString, 2);

		return getControlAirConditioningCmd(userCmd, unitSession, type, temperature, param3, airOutMode, windSpeed);
	}

	/**
	 * 控制空调指令(开、关和设置)
	 * 
	 * @throws Exception
	 */
	private ServerToUnitCommand getControlAirConditioningCmd(Command userCmd, UnitSocketSession unitSession, int type,
			int temperature, int param3, int airOutMode, int windSpeed) throws Exception {
		byte[] body = new byte[12];
		addTime(body);
		body[6] = 0x4;
		body[7] = (byte) type;
		body[8] = (byte) temperature;
		body[9] = (byte) param3;
		body[10] = (byte) airOutMode;
		body[11] = (byte) windSpeed;

		return makeCmd(userCmd, unitSession, (byte) 0x5, body);
	}

	/**
	 * 查询网络通信参数
	 * 
	 * @throws Exception
	 */
	private ServerToUnitCommand getQueryNetParamsCmd(Command userCmd, UnitSocketSession unitSession) throws Exception {
		List<String> params = userCmd.getParamsList();
		if (params.size() < 1) {
			throw new WrongFormatException("(hm)no param");
		}

		int index = Integer.valueOf(params.get(0));
		if (index != 1 && index != 2 && index != 3) {
			throw new WrongFormatException("(hm)error index(1 or 2 or 3):" + index);
		}
		byte subCmdId = (byte) 0x1;
		if (index == 1) {
			subCmdId = (byte) 0x1;
		} else if (index == 2) {
			subCmdId = (byte) 0x4;
		} else if (index == 3) {
			subCmdId = (byte) 0x24;
		}
		return getCommonQueryCmd(userCmd, unitSession, subCmdId);
	}

	/**
	 * 通知终端升级
	 * 
	 * @throws Exception
	 */
	private ServerToUnitCommand getUpdateUnitCmd(Command userCmd, UnitSocketSession unitSession) throws Exception {
		List<String> params = userCmd.getParamsList();
		if (params.size() < 3) {
			throw new WrongFormatException("(hm)not enough params, need: 3, current:" + params.size());
		}

		byte[] body = new byte[27];
		body[0] = 0x1;

		String sip = params.get(0);
		String[] sip_a = sip.split("\\.");
		for (int i = 0; i < 4; i++) {
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
	 * 
	 * @throws Exception
	 */
	private ServerToUnitCommand getQueryUpdateStatusCmd(Command userCmd, UnitSocketSession unitSession)
			throws Exception {
		byte[] body = new byte[1];
		body[0] = 0x5;

		return makeCmd(userCmd, unitSession, (byte) 0x1A, body);
	}

	/**
	 * 跟踪
	 * 
	 * @throws Exception
	 */
	private ServerToUnitCommand getTraceCmd(Command userCmd, UnitSocketSession unitSession) throws Exception {
		List<String> params = userCmd.getParamsList();
		if (params.size() < 2) {
			throw new WrongFormatException(
					"hm cmdIdx:" + userCmd.getCmdId() + ", param count:" + params.size() + ", min param count is 2");
		}

		int count = Integer.valueOf(params.get(0));
		int interval = Integer.valueOf(params.get(1));
		if (count < 1 || count > 65535) {
			throw new WrongFormatException("hm cmdIdx:" + userCmd.getCmdId() + ", count:" + count);
		}

		if (interval < 1 || interval > 65535) {
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
	 * 
	 * @throws Exception
	 */
	private ServerToUnitCommand getStopTraceCmd(Command userCmd, UnitSocketSession unitSession) throws Exception {
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
	 * 
	 * @throws Exception
	 */
	private ServerToUnitCommand getStartUploadCmd(Command userCmd, UnitSocketSession unitSession) throws Exception {
		List<String> params = userCmd.getParamsList();
		if (params.size() < 1) {
			throw new WrongFormatException("(hm)no param!");
		}

		String stime = params.get(0);
		int time = Integer.valueOf(stime);
		if (time < 1 || time > 65535) {
			throw new WrongFormatException("(hm)param error[1-65535]:" + time);
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
	 * 
	 * @throws Exception
	 */
	private ServerToUnitCommand getStopUploadCmd(Command userCmd, UnitSocketSession unitSession) throws Exception {
		byte[] body = new byte[11];
		addTime(body);
		body[6] = 0x12;

		body[8] = 0x1E;
		return makeCmd(userCmd, unitSession, (byte) 0x2, body);
	}

	/**
	 * 限制呼入
	 */
	private ServerToUnitCommand getForbidCallInCmd(Command userCmd, UnitSocketSession unitSession) throws Exception {
		byte[] body = new byte[11];
		addTime(body);
		body[6] = 0x1A;
		body[7] = (byte) 0x80;

		return makeCmd(userCmd, unitSession, (byte) 0x2, body);
	}

	/**
	 * 限制呼出
	 */
	private ServerToUnitCommand getForbidCallOutCmd(Command userCmd, UnitSocketSession unitSession) throws Exception {
		byte[] body = new byte[11];
		addTime(body);
		body[6] = 0x1A;
		body[7] = (byte) 0x40;

		List<String> params = userCmd.getParamsList();
		if (params.size() > 0) {
			int time = Integer.valueOf(params.get(0));
			if (time < 1 || time > 65535) {
				throw new WrongFormatException("(hm)param error[1-65535]:" + time);
			}

			byte[] bs_minus = Util.getShortByte((short) time);
			System.arraycopy(bs_minus, 0, body, 9, bs_minus.length);
		}

		return makeCmd(userCmd, unitSession, (byte) 0x2, body);
	}

	/**
	 * 恢复电话功能
	 * 
	 * @throws Exception
	 */
	private ServerToUnitCommand getResumeTelCmd(Command userCmd, UnitSocketSession unitSession) throws Exception {
		byte[] body = new byte[11];
		addTime(body);
		body[6] = 0x1A;
		body[7] = (byte) 0xC0;
		body[8] = 1;
		body[9] = (byte) 0xFF;
		body[10] = (byte) 0xFF;

		return makeCmd(userCmd, unitSession, (byte) 0x2, body);
	}

	/**
	 * 设置TCP通信参数
	 * 
	 * @throws Exception
	 */
	private ServerToUnitCommand getSetTCPParamCmd(Command userCmd, UnitSocketSession unitSession) throws Exception {
		// List<String> params = userCmd.getParamsList();
		// if(params.size() < 3){
		// throw new WrongFormatException("(hm)not enough params, min: 3,
		// current:" + params.size());
		// }
		//
		// String sapn = params.get(0);
		// String sip = params.get(1);
		// String sport = params.get(2);
		//
		// byte[] bs_apn = sapn.getBytes();
		// byte[] body = new byte[15 + bs_apn.length];
		// addTime(body);
		// body[6] = 0x1;
		//
		// //IP
		// String[] sip_a = sip.split("\\.");
		// for(int i = 0 ; i < 4; i++){
		// int v = Integer.valueOf(sip_a[i]);
		// body[7 + i] = (byte) v;
		// }
		//
		// //port
		// int port = Integer.valueOf(sport);
		// byte[] b_port = Util.getShortByte((short) port);
		// System.arraycopy(b_port, 0, body, 11, 2);
		//
		// //重连间隔
		// if(params.size() >= 7){
		// String sinterval = params.get(6);
		// if(sinterval.length() > 0){
		// int interval = Integer.valueOf(sinterval) * 60;
		// byte[] b_interval = Util.getShortByte((short) interval);
		// System.arraycopy(b_interval, 0, body, 13, 2);
		// }
		// }
		//
		// //APN
		// System.arraycopy(bs_apn, 0, body, 15, bs_apn.length);
		//
		// return makeCmd(userCmd, unitSession, (byte) 0x2, body);

		List<String> params = userCmd.getParamsList();
		if (params.size() < 1) {
			throw new WrongFormatException("(hm)no param");
		}

		int index = Integer.valueOf(params.get(0));
		if (index == 1) {
			// 设置网络参数1
			if (params.size() < 8) {
				throw new WrongFormatException("(hm)not enough params, min: 8, current:" + params.size());
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
			// position += bs_apn.length;

			return makeCmd(userCmd, unitSession, (byte) 0x2, body);
		}

		if (index == 2) {
			// 设置网络参数2
			if (params.size() < 4) {
				throw new WrongFormatException("(hm)not enough params, min: 4, current:" + params.size());
			}

			String sip = params.get(2);
			String sport = params.get(3);

			byte[] bs_ip = cc.chinagps.gateway.util.Util.getIPBytes(sip);
			int port = Integer.valueOf(sport);
			byte[] bs_port = Util.getShortByte((short) port);

			byte[] body = new byte[13];
			addTime(body);
			body[6] = 0x4;
			int position = 7;

			System.arraycopy(bs_ip, 0, body, position, bs_ip.length);
			position += bs_ip.length;

			System.arraycopy(bs_port, 0, body, position, bs_port.length);
			// position += bs_port.length;

			return makeCmd(userCmd, unitSession, (byte) 0x2, body);
		}
		if (index == 3) {
			// 设置网络参数3
			if (params.size() < 4) {
				throw new WrongFormatException("(hm)not enough params, min: 4, current:" + params.size());
			}

			String sip = params.get(2);
			String sport = params.get(3);

			byte[] bs_ip = cc.chinagps.gateway.util.Util.getIPBytes(sip);
			int port = Integer.valueOf(sport);
			byte[] bs_port = Util.getShortByte((short) port);

			byte[] body = new byte[13];
			addTime(body);
			body[6] = 0x24;
			int position = 7;

			System.arraycopy(bs_ip, 0, body, position, bs_ip.length);
			position += bs_ip.length;

			System.arraycopy(bs_port, 0, body, position, bs_port.length);
			// position += bs_port.length;

			return makeCmd(userCmd, unitSession, (byte) 0x2, body);
		}

		throw new WrongFormatException("(hm)error index(1 or 2 or 3):" + index);
	}

	/**
	 * 设置APN
	 * 
	 * @throws Exception
	 */
	private ServerToUnitCommand getSetAPNCmd(Command userCmd, UnitSocketSession unitSession) throws Exception {
		List<String> params = userCmd.getParamsList();
		if (params.size() < 1) {
			throw new WrongFormatException("(hm)no param!");
		}

		String sapn = params.get(0);
		byte[] bs_apn = sapn.getBytes(BeforeMarketPkgUtil.EN_CHAR_ENCODING);

		byte[] body = new byte[15 + bs_apn.length];
		addTime(body);
		body[6] = 0x1;

		// APN
		System.arraycopy(bs_apn, 0, body, 15, bs_apn.length);

		return makeCmd(userCmd, unitSession, (byte) 0x2, body);
	}

	private ServerToUnitCommand getSetRollParamCmd(Command userCmd, UnitSocketSession unitSession) throws Exception {
		List<String> params = userCmd.getParamsList();
		if (params.size() < 2) {
			throw new WrongFormatException("(hm)not enough params, min: 2, current:" + params.size());
		}
		byte[] body = new byte[10];
		addTime(body);
		body[6] = 0x25;
		int autoAdjust = Integer.valueOf(params.get(0));
		int maxAngle = Integer.valueOf(params.get(1));
		byte autoAdjustByte[] = new byte[]{(byte) autoAdjust};
		byte maxAngleByte[] = Util.getShortByte((short) maxAngle);

		int position = 7;
		System.arraycopy(autoAdjustByte, 0, body, position, 1);
		position += 1;

		System.arraycopy(maxAngleByte, 0, body, position, maxAngleByte.length);
		position += maxAngleByte.length;

		return makeCmd(userCmd, unitSession, (byte) 0x2, body);
	}

	/**
	 * 设置重连时间间隔
	 * 
	 * @throws Exception
	 */
	private ServerToUnitCommand getSetReconnectParamCmd(Command userCmd, UnitSocketSession unitSession)
			throws Exception {
		List<String> params = userCmd.getParamsList();
		if (params.size() < 1) {
			throw new WrongFormatException("(hm)no param!");
		}

		byte[] body = new byte[15];
		addTime(body);
		body[6] = 0x1;

		// 重连间隔
		String sinterval = params.get(6);
		int interval = Integer.valueOf(sinterval);
		byte[] b_interval = Util.getShortByte((short) interval);
		System.arraycopy(b_interval, 0, body, 13, 2);

		return makeCmd(userCmd, unitSession, (byte) 0x2, body);
	}

	/**
	 * 设置服务电话(call center)
	 * 
	 * @throws Exception
	 */
	private ServerToUnitCommand getSetTelCmd(Command userCmd, UnitSocketSession unitSession, byte subCmdId)
			throws Exception {
		List<String> params = userCmd.getParamsList();
		if (params.size() < 1) {
			throw new WrongFormatException("(hm)no param!");
		}

		byte[] body = new byte[23];
		addTime(body);
		body[6] = subCmdId;

		byte[] b_tel = params.get(0).getBytes(BeforeMarketPkgUtil.EN_CHAR_ENCODING);
		System.arraycopy(b_tel, 0, body, 7, Math.min(b_tel.length, 16));

		return makeCmd(userCmd, unitSession, (byte) 0x2, body);
	}

	/**
	 * 设置短信 呼入/呼出 特服号
	 * 
	 * @throws Exception
	 */
	private ServerToUnitCommand getSetSmInNumCmd(Command userCmd, UnitSocketSession unitSession) throws Exception {
		// List<String> params = userCmd.getParamsList();
		// if(params.size() < 2){
		// throw new WrongFormatException("(hm)not enough params, min: 2,
		// current:" + params.size());
		// }
		//
		// String sms_tel = params.get(0);
		// String sms_type = params.get(1);
		//
		// byte[] body = new byte[57];
		// addTime(body);
		// body[6] = 0x3;
		//
		// byte[] b_tel = sms_tel.getBytes(HMPkgUtil.EN_CHAR_ENCODING);
		// if("1".equals(sms_type)){
		// //呼入
		// System.arraycopy(b_tel, 0, body, 32, Math.min(b_tel.length, 25));
		// }else if("2".equals(sms_type)){
		// //呼出
		// System.arraycopy(b_tel, 0, body, 7, Math.min(b_tel.length, 25));
		// }else{
		// //同时
		// System.arraycopy(b_tel, 0, body, 7, Math.min(b_tel.length, 25));
		// System.arraycopy(b_tel, 0, body, 32, Math.min(b_tel.length, 25));
		// }

		List<String> params = userCmd.getParamsList();
		if (params.size() < 1) {
			throw new WrongFormatException("(hm)no param");
		}

		String call_down = params.get(0);
		String call_up = null;
		if (params.size() > 1) {
			call_up = params.get(1);
		}

		byte[] body = new byte[57];
		addTime(body);
		body[6] = 0x3;

		if (call_down.length() > 0) {
			byte[] bs_call_down = call_down.getBytes(BeforeMarketPkgUtil.EN_CHAR_ENCODING);
			System.arraycopy(bs_call_down, 0, body, 7, Math.min(bs_call_down.length, 25));
		}

		if (call_up != null && call_up.length() > 0) {
			byte[] bs_call_up = call_up.getBytes(BeforeMarketPkgUtil.EN_CHAR_ENCODING);
			System.arraycopy(bs_call_up, 0, body, 32, Math.min(bs_call_up.length, 25));
		}

		return makeCmd(userCmd, unitSession, (byte) 0x2, body);
	}

	/**
	 * 查询TCP通信参数
	 * 
	 * @throws Exception
	 */
	// private ServerToUnitCommand getQueryTCPParamCmd(Command userCmd,
	// UnitSocketSession unitSession) throws Exception{
	// byte[] body = new byte[7];
	// addTime(body);
	// body[6] = 0x1;
	//
	// return makeCmd(userCmd, unitSession, (byte) 0x3, body);
	// }

	/**
	 * 设置导航终点
	 * 
	 * @throws Exception
	 */
	private ServerToUnitCommand getSetNavigationDestinationCmd(Command userCmd, UnitSocketSession unitSession)
			throws Exception {
		List<String> params = userCmd.getParamsList();
		if (params.size() < 3) {
			throw new WrongFormatException("(hm)not enough params, min: 3, current:" + params.size());
		}

		int type = Integer.valueOf(params.get(0));
		int lon = (int) (Double.valueOf(params.get(1)) * 10000000);
		int lat = (int) (Double.valueOf(params.get(2)) * 10000000);

		// 必经点
		String[] a_latlngs = null;
		if (params.size() >= 4 && params.get(3).length() > 0) {
			a_latlngs = params.get(3).split(";");
			if (a_latlngs.length > 3) {
				throw new WrongFormatException("(hm)必经点过多,最大3,当前:" + a_latlngs.length);
			}
		}

		// 回避点
		String[] b_latlngs = null;
		if (params.size() >= 5 && params.get(4).length() > 0) {
			b_latlngs = params.get(4).split(";");
			if (b_latlngs.length > 2) {
				throw new WrongFormatException("(hm)回避点过多,最大2,当前:" + b_latlngs.length);
			}
		}

		int len = 17;
		if (a_latlngs != null) {
			len += 8 * a_latlngs.length;
		}

		if (b_latlngs != null) {
			len += 8 * b_latlngs.length;
		}

		byte[] body = new byte[len];
		addTime(body);
		body[6] = (byte) type;
		body[7] = (byte) (a_latlngs == null ? 0 : a_latlngs.length);
		body[8] = (byte) (b_latlngs == null ? 0 : b_latlngs.length);

		// byte[] bs_lat = HMNumberUtil.getHMIntByte(lat);
		// byte[] bs_lon = HMNumberUtil.getHMIntByte(lon);
		byte[] bs_lat = Util.getIntByte(lat);
		byte[] bs_lon = Util.getIntByte(lon);
		System.arraycopy(bs_lat, 0, body, 9, 4);
		System.arraycopy(bs_lon, 0, body, 13, 4);

		int current_offset = 17;
		if (a_latlngs != null) {
			for (String latlng : a_latlngs) {
				String[] alatlng = latlng.split(",");
				int a_lon = (int) (Double.valueOf(alatlng[0]) * 10000000);
				int a_lat = (int) (Double.valueOf(alatlng[1]) * 10000000);

				// byte[] bs_a_lon = HMNumberUtil.getHMIntByte(a_lon);
				// byte[] bs_a_lat = HMNumberUtil.getHMIntByte(a_lat);
				byte[] bs_a_lon = Util.getIntByte(a_lon);
				byte[] bs_a_lat = Util.getIntByte(a_lat);

				System.arraycopy(bs_a_lat, 0, body, current_offset, 4);
				current_offset += 4;
				System.arraycopy(bs_a_lon, 0, body, current_offset, 4);
				current_offset += 4;
			}
		}

		if (b_latlngs != null) {
			for (String latlng : b_latlngs) {
				String[] blatlng = latlng.split(",");
				int b_lon = (int) (Double.valueOf(blatlng[0]) * 10000000);
				int b_lat = (int) (Double.valueOf(blatlng[1]) * 10000000);

				// byte[] bs_b_lon = HMNumberUtil.getHMIntByte(b_lon);
				// byte[] bs_b_lat = HMNumberUtil.getHMIntByte(b_lat);
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

	/**
	 * 终端复位
	 * 
	 * @throws Exception
	 */
	private ServerToUnitCommand getResetCmd(Command userCmd, UnitSocketSession unitSession) throws Exception {
		List<String> params = userCmd.getParamsList();
		byte[] body = new byte[8];
		addTime(body);
		body[6] = (byte) 0xFA;

		int p = 1;
		if (params.size() > 0) {
			int param0 = Integer.valueOf(params.get(0));
			p = param0;
		}

		body[7] = (byte) p;

		return makeCmd(userCmd, unitSession, (byte) 0x2, body);
	}

	/**
	 * 设置电控单元配置
	 * 
	 * @throws Exception
	 */
	private ServerToUnitCommand getSetECUConfigCmd(Command userCmd, UnitSocketSession unitSession) throws Exception {
		List<String> params = userCmd.getParamsList();
		if (params.size() < 3) {
			throw new WrongFormatException("(hm)not enough params, min: 3, current:" + params.size());
		}

		if (params.size() > 254) {
			throw new WrongFormatException("(hm)too many params, max: 254, current:" + params.size());
		}

		// String param1 = params.get(0);
		// if(!HexUtil.isOneByteBinaryString(param1)){
		// throw new WrongFormatException("(hm)param1 error:" + param1);
		// }
		//
		// String param2 = params.get(1);
		// if(!HexUtil.isOneByteBinaryString(param2)){
		// throw new WrongFormatException("(hm)param2 error:" + param2);
		// }
		//
		// String param3 = params.get(2);
		// if(!HexUtil.isOneByteBinaryString(param3)){
		// throw new WrongFormatException("(hm)param3 error:" + param3);
		// }
		//
		// int config4 = 0;
		// if(params.size() > 3){
		// String param4 = params.get(3);
		// if(!HexUtil.isOneByteBinaryString(param4)){
		// throw new WrongFormatException("(hm)param4 error:" + param4);
		// }
		//
		// config4 = Integer.valueOf(param4, 2);
		// }
		//
		// byte[] body = new byte[12];
		// addTime(body);
		// body[6] = (byte) 0x22;
		//
		// //len
		// body[7] = 5;
		//
		// //config1
		// int config1 = Integer.valueOf(param1, 2);
		// body[8] = (byte) config1;
		//
		// //config2
		// int config2 = Integer.valueOf(param2, 2);
		// body[9] = (byte) config2;
		//
		// //config3
		// int config3 = Integer.valueOf(param3, 2);
		// body[10] = (byte) config3;
		//
		// //config4
		// body[11] = (byte) config4;
		//
		// return makeCmd(userCmd, unitSession, (byte) 0x2, body);

		int configSize = params.size();
		byte[] configs = new byte[configSize == 3 ? 4 : configSize];
		for (int i = 0; i < configSize; i++) {
			String parami = params.get(i);
			if (!HexUtil.isOneByteBinaryString(parami)) {
				throw new WrongFormatException("(hm)param" + (i + 1) + " error:" + parami);
			}

			int v = Integer.valueOf(parami, 2);
			configs[i] = (byte) v;
		}

		byte[] body = new byte[8 + configs.length];
		addTime(body);
		body[6] = (byte) 0x22;
		body[7] = (byte) (configs.length + 1);
		System.arraycopy(configs, 0, body, 8, configs.length);

		return makeCmd(userCmd, unitSession, (byte) 0x2, body);
	}

	/**
	 * wifi认证
	 * 
	 * @param userCmd
	 * @param unitSession
	 * @param cmdId
	 * @return
	 * @throws Exception
	 */
	public ServerToUnitCommand wifiAuth(Command userCmd, UnitSocketSession unitSession, byte cmdId) throws Exception {
		List<String> params = userCmd.getParamsList();
		if (params.size() < 3) {
			throw new WrongFormatException("(hm)not enough params, min: 3, current:" + params.size());
		}
		String mac = params.get(0);
		String meid = params.get(1);
		String phone = params.get(2);
		byte[] macBs = new byte[6];
		macBs = getMacBytes(mac);
		byte[] body = new byte[37];
		body[0] = cmdId;
		int position = 1;
		System.arraycopy(macBs, 0, body, position, macBs.length);
		byte[] meidBs = meid.getBytes(BeforeMarketPkgUtil.EN_CHAR_ENCODING);
		position += macBs.length;
		System.arraycopy(meidBs, 0, body, position, meidBs.length);
		byte[] phoneBs = phone.getBytes(BeforeMarketPkgUtil.EN_CHAR_ENCODING);
		position += meidBs.length;
		System.arraycopy(phoneBs, 0, body, position, phoneBs.length);

		return makeCmd(userCmd, unitSession, (byte) 0x44, body);
	}

	/**
	 * 12位mac转换为6位byte
	 * 
	 * @param mac
	 *            "0013e4c44c67"
	 * @return
	 */
	private byte[] getMacBytes(String mac) throws Exception {
		if (mac == null || mac.length() != 12)
			throw new Exception("mac error ,mac :" + mac);
		byte[] macBytes = new byte[6];
		macBytes[0] = (byte) Integer.parseInt(mac.substring(0, 2), 16);
		macBytes[1] = (byte) Integer.parseInt(mac.substring(2, 4), 16);
		macBytes[2] = (byte) Integer.parseInt(mac.substring(4, 6), 16);
		macBytes[3] = (byte) Integer.parseInt(mac.substring(6, 8), 16);
		macBytes[4] = (byte) Integer.parseInt(mac.substring(8, 10), 16);
		macBytes[5] = (byte) Integer.parseInt(mac.substring(10, 12), 16);
		return macBytes;
	}

	public static void main(String[] args) {
		// String s = "00000000";
		// System.out.println(HexUtil.isOneByteBinaryString(s));
		//
		// int v = Integer.valueOf(s, 2);
		// System.out.println(v);
		//
		// System.out.println(7 & 1);

		for (int i = 0; i <= 255; i++) {
			String str = HexUtil.byteToString((byte) i);
			if (!HexUtil.isOneByteBinaryString(str)) {
				System.err.println("xxxxxxxxx:" + i);
				break;
			}

			int v = Integer.valueOf(str, 2);
			if (v != i) {
				System.err.println("yyyyyyy:" + i);
				break;
			}

			System.out.println("[" + i + "]:" + str);
		}

		System.out.println("end");
	}

	/**
	 * 清除故障码
	 * 
	 * @throws Exception
	 */
	private ServerToUnitCommand getClearFaultCmd(Command userCmd, UnitSocketSession unitSession) throws Exception {
		byte[] body = new byte[7];
		addTime(body);
		body[6] = 0x8;

		return makeCmd(userCmd, unitSession, (byte) 0x5, body);
	}

	/**
	 * cmd公共部分
	 * 
	 * @throws ParseException
	 */
	private void addTime(byte[] data) throws ParseException {
		addTime(data, 0);
	}

	private void addTime(byte[] data, int offset) throws ParseException {
		byte[] bcd = BeforeMarketTimeFormatUtil.getCurrentTimeBCD();
		System.arraycopy(bcd, 0, data, offset, 6);
	}

	private ServerToUnitCommand makeCmd(Command userCmd, UnitSocketSession unitSession, byte msgType, byte[] body)
			throws Exception {
		BeforeMarketPackage pkg = new BeforeMarketPackage();
		BeforeMarketPackageHead head = new BeforeMarketPackageHead();
		head.setCompress(DEFAULT_IS_COMPRESS);
		head.setEncrypt(DEFAULT_IS_ENCRYPT);
		head.setMsgType(msgType);
		head.setSn(BeforeMarketPkgUtil.getSn(unitSession));
		head.setTerminalId(unitSession.getUnitInfo().getIMEI());
		head.setTerminalType(BeforeMarketConstants.PROTOCOL_TERMINAL_TYPE_HM);
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
		logger_debug.debug("[hm] download cmd encoder makeCmd data:"+HexUtil.byteToHexStr(source)+";cmd:"+userCmd);
		return serverToUnitCommand;
	}
}