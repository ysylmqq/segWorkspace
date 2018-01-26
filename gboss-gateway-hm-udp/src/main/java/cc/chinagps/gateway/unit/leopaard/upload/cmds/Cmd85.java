package cc.chinagps.gateway.unit.leopaard.upload.cmds;

import org.apache.log4j.Logger;
import org.seg.lib.util.Util;

import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.leopaard.pkg.LeopaardPackage;
import cc.chinagps.gateway.unit.leopaard.upload.BigDataDisposer;
import cc.chinagps.gateway.unit.leopaard.util.LeopaardTimeFormatUtil;
import cc.chinagps.gateway.unit.leopaard.util.LeopaardUploadUtil;

/**
 * 大数据上传
 */
public class Cmd85 extends CheckLoginHandler {
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);

	@Override
	public boolean handlerPackageSessionChecked(LeopaardPackage pkg, UnitServer server, UnitSocketSession session)
			throws Exception {
		byte body[] = pkg.getDataUnit();
		int pos = 0;
		BigDataDisposer bigDataDisposer = session.getBigDataDisposer();
		String callLetter = session.getUnitInfo().getCallLetter();
		String responseTime = cc.chinagps.gateway.util.Util.getDateTime(body, pos, 6);
		logger_debug.debug("[leopaard] cmd85 responseTime:" + responseTime);
		pos += 6;
		int dataProperty = body[pos++] & 0xFF;
		switch (dataProperty) {
		case 0x01:
			int cmdId = body[pos++] & 0xFF;
			switch (cmdId) {
			case 0x01:// 终端请求上传数据
				bigDataDisposer.reset();
				int dataLen = Util.getInt(body, pos) & 0xFFFFFFFF;
				bigDataDisposer.setDataLen(dataLen);
				pos += 4;
				int packageDataLen = cc.chinagps.gateway.util.Util.getShort(body, pos);
				pos += 2;
				bigDataDisposer.setPackageDataLen(packageDataLen);
				commonDataTranferAck(session, pkg, (byte) 0x01, new byte[] { 0x00, 0x3D }, 0x11);
				commonDataTranferAck(session, pkg, (byte) 0xFE, new byte[] { 0x00, 0x00 }, 0x12);
				break;
			case 0x02:
				int packageIndex = cc.chinagps.gateway.util.Util.getShort(body, pos);
				pos += 2;
				int recevDataLen = 0;
				if (packageIndex > bigDataDisposer.getPackageNum() - 1)
					packageIndex = bigDataDisposer.getPackageNum() - 1;
				if (packageIndex < bigDataDisposer.getPackageNum() - 1) {
					recevDataLen = bigDataDisposer.getPackageDataLen();
				} else {
					recevDataLen = bigDataDisposer.getDataLen() - bigDataDisposer.getPackageDataLen() * packageIndex;
				}
				byte[] data = new byte[recevDataLen];
				System.arraycopy(body, pos, data, 0, recevDataLen);
				pos += recevDataLen;
				bigDataDisposer.addRecevData(packageIndex, data, callLetter);

				if (packageIndex == bigDataDisposer.getPackageNum() - 1) {
					commonDataTranferAck(session, pkg, (byte) 0xFE, new byte[] { 0x00, 0x3B }, 0x13);
				} else {
					byte packageIndexByte[] = Util.getShortByte((short) (bigDataDisposer.getCurrPackageIndex() + 1));
					commonDataTranferAck(session, pkg, (byte) 0x01, packageIndexByte, 0x12);
				}
				break;
			case 0x03:
				int res = cc.chinagps.gateway.util.Util.getShort(body, pos);
				pos += 2;
				logger_debug.debug("[leopaard] cmd85 finished upload data ack result:" + res);
				break;
			default:
				break;
			}

			break;

		default:
			break;
		}
		return true;
	}

	private void commonDataTranferAck(UnitSocketSession session, LeopaardPackage pkg, byte responseFlag, byte[] data,
			int cmdId) throws Exception {
		byte[] respData = new byte[8 + data.length];
		int pos = 0;
		byte[] dateTime = LeopaardTimeFormatUtil.getCurrentTime8();
		System.arraycopy(dateTime, 0, respData, pos, 6);
		pos += 6;
		respData[pos++] = 0x01;
		respData[pos++] = (byte) cmdId;
		System.arraycopy(data, 0, respData, pos, data.length);
		pos += 2;
		LeopaardUploadUtil.commonAck(session, pkg, responseFlag, respData);
	}

}