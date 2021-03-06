package cc.chinagps.gateway.unit.beforemarket.hm.upload;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beforemarket.common.UploadHandler;
import cc.chinagps.gateway.unit.beforemarket.common.pkg.BeforeMarketPackage;
import cc.chinagps.gateway.unit.beforemarket.common.util.BeforeMarketPkgUtil;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.cmd.Cmd01;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.cmd.Cmd02;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.cmd.Cmd03;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.cmd.Cmd05;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.cmd.Cmd10;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.cmd.Cmd11;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.cmd.Cmd1A;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.cmd.Cmd1B;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.cmd.Cmd1C;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.cmd.Cmd41;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.cmd.Cmd42;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.cmd.Cmd43;
import cc.chinagps.gateway.unit.common.UnitCommon;
import cc.chinagps.gateway.util.HexUtil;

public class HMUploadCmdHandlers {
	private Logger logger_unkown = Logger.getLogger(LogManager.LOGGER_NAME_UNKNOWN_CMD);
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);

	public HMUploadCmdHandlers() {
		initHandlers();
	}

	private Map<Byte, UploadHandler> handlers = new HashMap<Byte, UploadHandler>();

	private void initHandlers() {
		handlers.put((byte) 0x01, new Cmd01());
		handlers.put((byte) 0x02, new Cmd02());
		handlers.put((byte) 0x03, new Cmd03());
		handlers.put((byte) 0x05, new Cmd05());

		handlers.put((byte) 0x10, new Cmd10());
		handlers.put((byte) 0x11, new Cmd11());
		handlers.put((byte) 0x1A, new Cmd1A());
		handlers.put((byte) 0x1B, new Cmd1B());

		handlers.put((byte) 0x41, new Cmd41());
		handlers.put((byte) 0x42, new Cmd42());
		handlers.put((byte) 0x43, new Cmd43());
		handlers.put((byte) 0x1c, new Cmd1C());  // 整车升级
	}

	private static final byte CMD_ID_CONNECT = 0x1;
	private static final byte SUB_CMD_ID_LOGIN = 0x1;

	// 是否为登录包
	private boolean isLogin(BeforeMarketPackage pkg) {
		byte cmdId = pkg.getHead().getMsgType();
		if (cmdId != CMD_ID_CONNECT) {
			return false;
		}

		byte[] body = pkg.getBody();
		byte sub_cmdId = body[0];
		if (sub_cmdId == SUB_CMD_ID_LOGIN) {
			return true;
		}

		return false;
	}

	public void handleUpload(byte[] source, UnitServer server, UnitSocketSession session) throws Exception {
		int c1 = BeforeMarketPkgUtil.getC1(session);
		int d1 = BeforeMarketPkgUtil.getD1(session);
		String callLetter = null;
		if (session.getUnitInfo() != null) {
			callLetter = session.getUnitInfo().getCallLetter();
		}
		logger_debug.debug("[hm] recev data [" + callLetter + "]:" + HexUtil.byteToHexStr(source));

		BeforeMarketPackage pkg = BeforeMarketPackage.parse(source, c1, d1);
		// (登录包)在解析出呼号后发送trace数据
		if (!isLogin(pkg)) {
			// Trace功能(处理中会回复车台,放在处理前面)
			UnitCommon.sendUploadTrace(source, server, session);
		}

		byte cmdId = pkg.getHead().getMsgType();
		UploadHandler handler = handlers.get(cmdId);
		if (handler != null) {
			boolean canHandle = handler.handlerPackage(pkg, server, session);
			if (!canHandle) {
				String call = null;
				if (session.getUnitInfo() != null) {
					call = session.getUnitInfo().getCallLetter();
				}

				logger_unkown.info("[hm]没有子处理函数, 呼号:" + call + ", 源码:" + HexUtil.byteToHexStr(source));
			}
		} else {
			String call = null;
			if (session.getUnitInfo() != null) {
				call = session.getUnitInfo().getCallLetter();
			}

			logger_unkown.info("[hm]没有处理函数, 呼号:" + call + ", 源码:" + HexUtil.byteToHexStr(source));
		}

		// A计划
		UnitCommon.sendDataToAPlanClients(source, server, session);

		// 警情
		UnitCommon.sendDataToAPlanAlarmClients(source, server, session);
	}
}
