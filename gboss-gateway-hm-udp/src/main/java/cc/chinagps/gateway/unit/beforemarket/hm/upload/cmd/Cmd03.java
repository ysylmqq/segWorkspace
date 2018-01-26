package cc.chinagps.gateway.unit.beforemarket.hm.upload.cmd;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.seg.lib.util.Util;

import cc.chinagps.gateway.aplan.pkg.APlanUtil;
import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.buff.CommandBuff.CommandResponse.Builder;
import cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.seat.cmd.CmdResponseUtil;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beforemarket.common.CheckLoginHandler;
import cc.chinagps.gateway.unit.beforemarket.common.pkg.BeforeMarketPackage;
import cc.chinagps.gateway.unit.beforemarket.common.util.BeforeMarketPkgUtil;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.HMUploadUtil;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.beans.HMECUConfig;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.beans.HMFaultDefine;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.beans.HMFaultInfo;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.beans.HMGPSInfo;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.beans.HMStatus;
import cc.chinagps.gateway.unit.beforemarket.hm.util.HMProtobufUtil;
import cc.chinagps.gateway.util.Constants;
import cc.chinagps.gateway.util.HexUtil;

/**
 * ��ѯָ��Ӧ��
 */
public class Cmd03 extends CheckLoginHandler {
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	@Override
	public boolean handlerPackageSessionChecked(BeforeMarketPackage ppkg, UnitServer server, UnitSocketSession session)
			throws Exception {
		byte[] body = ppkg.getBody();
		int subCmdId = body[0] & 0xFF;
		logger_debug.debug("[hm] cmd03 subCmd["+subCmdId+"] unit_ack_result:"+(Util.getShort(body, 1) & 0xFFFF));
		if (subCmdId == 0x01) {
			// ��ѯ�������Ӧ��
			queryNetSettingAck(ppkg, server, session);
			return true;
		}

		if (subCmdId == 0x2) {
			// ��ѯ�������ĺ���Ӧ��
			queryTelAck(ppkg, server, session);
			return true;
		}

		if (subCmdId == 0x3) {
			// ��ѯ����ҵ�����ĺ���Ӧ��
			querySMCenterAck(ppkg, server, session);
			return true;
		}

		if (subCmdId == 0x04) {
			// ��ѯ�������2Ӧ��
			queryNetSetting2Ack(ppkg, server, session);
			return true;
		}

		if (subCmdId == 0x12) {
			// ��ѯ��ʱ�ϱ�����Ӧ��
			queryUploadParamAck(ppkg, server, session);
			return true;
		}

		if (subCmdId == 0x13) {
			// ��ѯACC�仯�Ƿ��ϱ�Ӧ��
			ackOneByte(ppkg, server, session);
			return true;
		}

		if (subCmdId == 0x14) {
			// ��ѯ�����Ƿ��ϱ�Ӧ��
			ackOneByte(ppkg, server, session);
			return true;
		}

		if (subCmdId == 0x15) {
			// ��ѯ�ػ��Ƿ��ϱ�Ӧ��
			ackOneByte(ppkg, server, session);
			return true;
		}

		if (subCmdId == 0x16) {
			// ��ѯ����״̬�仯�Ƿ��ϱ�Ӧ��
			ackOneByte(ppkg, server, session);
			return true;
		}

		if (subCmdId == 0x18) {
			// ��ѯ����/��Ԯ�������Ӧ��
			queryTelAck(ppkg, server, session);
			return true;
		}

		if (subCmdId == 0x19) {
			// ��ѯ�����Ƿ��ϱ�Ӧ��
			ackOneByte(ppkg, server, session);
			return true;
		}

		if (subCmdId == 0x1A) {
			// ��ѯ�����������Ӧ��
			queryCallLimitAck(ppkg, server, session);
			return true;
		}

		if (subCmdId == 0x20) {
			// ��ѯOBD����Ӧ��
			queryFault(ppkg, server, session);
			return true;
		}

		if (subCmdId == 0x21) {
			// �鳵Ӧ��
			queryPositionAck(ppkg, server, session);
			return true;
		}

		if (subCmdId == 0x22) {
			// ��ѯ��ص�Ԫ����Ӧ��
			queryECUConfigAck(ppkg, server, session);
			return true;
		}
		
		if (subCmdId == 0x24) {
			// ��ѯ�������3��˫ip��¼��Ӧ��
			queryNetSetting3Ack(ppkg, server, session);
			return true;
		}

		if (subCmdId == 0x25) {
			// ��ѯ������ٶ�оƬУ������Ӧ��
			queryRollParamAck(ppkg, server, session);
			return true;
		}
		
		if (subCmdId == 0x26) {
			// ��ѯ�ն˲���Ӧ��
			queryTboxInfoAck(ppkg, server, session);
			return true;
		}

		if (subCmdId == 0x27) {
			// ��ѯ�յ�����
			queryAirConditioningParamAck(ppkg, server, session);
			return true;
		}
		
		if (subCmdId == 0xF9) {
			// ��ѯ������������3Gģ��״̬
			querySoundConnect3GParamAck(ppkg, server, session);
			return true;
		}

		return false;
	}

	private void querySoundConnect3GParamAck(BeforeMarketPackage ppkg, UnitServer server, UnitSocketSession session)throws IOException {
		// TODO Auto-generated method stub
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = BeforeMarketPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSn());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);

		if (cache != null) {
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			// make protobuf
			Command usercmd = cache.getUserCommand();
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn(usercmd.getSn());
			builder.setCallLetter(callLetter);
			builder.setCmdId(usercmd.getCmdId());
			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);

				int startPosition = 3;
				int result = body[startPosition];
				builder.addParams(result+"");
			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			// byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		} else {
			// δ�ҵ����棬�����ǻ��Ѻ������
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			int subCmdId = body[0] & 0xFF;

			// make protobuf
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn("");
			builder.setCallLetter(callLetter);
			builder.setCmdId(getInnerCmdId(subCmdId));
			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);

				int startPosition = 3;
				int result = body[startPosition];
				builder.addParams(result+"");
			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			builder.setUnitsn(ppkg.getHead().getSn() & 0xFFFF);
			server.getExportMQ().addCommandResponse(builder.build());
		}
	}

	/**
	 * ��ѯ�������Ӧ��
	 * 
	 * @throws IOException
	 */
	private void queryNetSettingAck(BeforeMarketPackage ppkg, UnitServer server, UnitSocketSession session)
			throws IOException {
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = BeforeMarketPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSn());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);

		if (cache != null) {
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			// make protobuf
			Command usercmd = cache.getUserCommand();
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn(usercmd.getSn());
			builder.setCallLetter(callLetter);
			builder.setCmdId(usercmd.getCmdId());
			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);

				int startPosition = 3;
				String ip = cc.chinagps.gateway.util.Util.getIP(body, startPosition);
				int port = Util.getShort(body, startPosition + 4) & 0xFFFF;
				int interval = Util.getShort(body, startPosition + 6) & 0xFFFF;
				String apn = APlanUtil.getCString(body, startPosition + 8, body.length - 11);

				builder.addParams("1");
				builder.addParams(apn);
				builder.addParams(ip);
				builder.addParams(String.valueOf(port));
				builder.addParams("");
				builder.addParams("");
				builder.addParams("");
				builder.addParams(String.valueOf(interval));
			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			builder.setUnitsn(ppkg.getHead().getSn() & 0xFFFF);
			// byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		} else {
			// δ�ҵ����棬�����ǻ��Ѻ������
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			int subCmdId = body[0] & 0xFF;
			// make protobuf
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn("");
			builder.setCallLetter(callLetter);
			builder.setCmdId(getInnerCmdId(subCmdId));
			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);

				int startPosition = 3;
				String ip = cc.chinagps.gateway.util.Util.getIP(body, startPosition);
				int port = Util.getShort(body, startPosition + 4) & 0xFFFF;
				int interval = Util.getShort(body, startPosition + 6) & 0xFFFF;
				String apn = APlanUtil.getCString(body, startPosition + 8, body.length - 11);

				builder.addParams("1");
				builder.addParams(apn);
				builder.addParams(ip);
				builder.addParams(String.valueOf(port));
				builder.addParams("");
				builder.addParams("");
				builder.addParams("");
				builder.addParams(String.valueOf(interval));
			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			// byte[] data = builder.build().toByteArray();
			server.getExportMQ().addCommandResponse(builder.build());
		}
	}

	/**
	 * ��ѯ�������2Ӧ��
	 * 
	 * @throws IOException
	 */
	private void queryNetSetting2Ack(BeforeMarketPackage ppkg, UnitServer server, UnitSocketSession session)
			throws IOException {
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = BeforeMarketPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSn());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);

		if (cache != null) {
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			// make protobuf
			Command usercmd = cache.getUserCommand();
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn(usercmd.getSn());
			builder.setCallLetter(callLetter);
			builder.setCmdId(usercmd.getCmdId());
			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);

				int startPosition = 3;
				String ip = cc.chinagps.gateway.util.Util.getIP(body, startPosition);
				int port = Util.getShort(body, startPosition + 4) & 0xFFFF;

				builder.addParams("2");
				builder.addParams("");
				builder.addParams(ip);
				builder.addParams(String.valueOf(port));
				builder.addParams("");
				builder.addParams("");
				builder.addParams("");
				builder.addParams("");
			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			builder.setUnitsn(ppkg.getHead().getSn() & 0xFFFF);
			// byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		} else {
			// δ�ҵ����棬�����ǻ��Ѻ������
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			int subCmdId = body[0] & 0xFF;
			// make protobuf
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn("");
			builder.setCallLetter(callLetter);
			builder.setCmdId(getInnerCmdId(subCmdId));
			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);

				int startPosition = 3;
				String ip = cc.chinagps.gateway.util.Util.getIP(body, startPosition);
				int port = Util.getShort(body, startPosition + 4) & 0xFFFF;
				int interval = Util.getShort(body, startPosition + 6) & 0xFFFF;
				String apn = APlanUtil.getCString(body, startPosition + 8, body.length - 11);

				int index = subCmdId == 1 ? 1 : 2;
				builder.addParams(String.valueOf(index));
				builder.addParams(apn);
				builder.addParams(ip);
				builder.addParams(String.valueOf(port));
				builder.addParams("");
				builder.addParams("");
				builder.addParams("");
				builder.addParams(String.valueOf(interval));
			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			// byte[] data = builder.build().toByteArray();
			server.getExportMQ().addCommandResponse(builder.build());
		}
	}
	
	/**
	 * ��ѯ�������3Ӧ��
	 * 
	 * @throws IOException
	 */
	private void queryNetSetting3Ack(BeforeMarketPackage ppkg, UnitServer server, UnitSocketSession session)
			throws IOException {
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = BeforeMarketPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSn());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);

		if (cache != null) {
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			// make protobuf
			Command usercmd = cache.getUserCommand();
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn(usercmd.getSn());
			builder.setCallLetter(callLetter);
			builder.setCmdId(usercmd.getCmdId());
			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);

				int startPosition = 3;
				String ip = cc.chinagps.gateway.util.Util.getIP(body, startPosition);
				int port = Util.getShort(body, startPosition + 4) & 0xFFFF;

				builder.addParams("3");
				builder.addParams("");
				builder.addParams(ip);
				builder.addParams(String.valueOf(port));
				builder.addParams("");
				builder.addParams("");
				builder.addParams("");
				builder.addParams("");
			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			builder.setUnitsn(ppkg.getHead().getSn() & 0xFFFF);
			// byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		} else {
			// δ�ҵ����棬�����ǻ��Ѻ������
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			int subCmdId = body[0] & 0xFF;
			// make protobuf
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn("");
			builder.setCallLetter(callLetter);
			builder.setCmdId(getInnerCmdId(subCmdId));
			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);

				int startPosition = 3;
				String ip = cc.chinagps.gateway.util.Util.getIP(body, startPosition);
				int port = Util.getShort(body, startPosition + 4) & 0xFFFF;
				int interval = Util.getShort(body, startPosition + 6) & 0xFFFF;
				String apn = APlanUtil.getCString(body, startPosition + 8, body.length - 11);

				int index = 3;
				builder.addParams(String.valueOf(index));
				builder.addParams(apn);
				builder.addParams(ip);
				builder.addParams(String.valueOf(port));
				builder.addParams("");
				builder.addParams("");
				builder.addParams("");
				builder.addParams(String.valueOf(interval));
			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			// byte[] data = builder.build().toByteArray();
			server.getExportMQ().addCommandResponse(builder.build());
		}
	}

	/**
	 * ��ѯ����Ӧ��
	 * 
	 * @throws IOException
	 */
	private void queryTelAck(BeforeMarketPackage ppkg, UnitServer server, UnitSocketSession session)
			throws IOException {
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = BeforeMarketPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSn());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);

		if (cache != null) {
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			// make protobuf
			Command usercmd = cache.getUserCommand();
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn(usercmd.getSn());
			builder.setCallLetter(callLetter);
			builder.setCmdId(usercmd.getCmdId());
			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);

				int startPosition = 3;
				String call = APlanUtil.getCString(body, startPosition, 16);

				builder.addParams(call);
			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			// byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		} else {
			// δ�ҵ����棬�����ǻ��Ѻ������
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			int subCmdId = body[0] & 0xFF;

			// make protobuf
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn("");
			builder.setCallLetter(callLetter);
			builder.setCmdId(getInnerCmdId(subCmdId));
			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);

				int startPosition = 3;
				String call = APlanUtil.getCString(body, startPosition, 16);

				builder.addParams(call);
			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			builder.setUnitsn(ppkg.getHead().getSn() & 0xFFFF);
			// byte[] data = builder.build().toByteArray();
			server.getExportMQ().addCommandResponse(builder.build());
		}
	}

	/**
	 * ��ѯ����ҵ�����ĺ���Ӧ��
	 * 
	 * @throws IOException
	 */
	private void querySMCenterAck(BeforeMarketPackage ppkg, UnitServer server, UnitSocketSession session)
			throws IOException {
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = BeforeMarketPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSn());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);

		if (cache != null) {
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			// make protobuf
			Command usercmd = cache.getUserCommand();
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn(usercmd.getSn());
			builder.setCallLetter(callLetter);
			builder.setCmdId(usercmd.getCmdId());
			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);

				int startPosition = 3;
				String call1 = APlanUtil.getCString(body, startPosition, 25);
				startPosition += 25;
				String call2 = APlanUtil.getCString(body, startPosition, 25);

				builder.addParams(call1);
				builder.addParams(call2);
			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			// byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		} else {
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			int subCmdId = body[0] & 0xFF;
			// make protobuf
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn("");
			builder.setCallLetter(callLetter);
			builder.setCmdId(getInnerCmdId(subCmdId));
			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);

				int startPosition = 3;
				String call1 = APlanUtil.getCString(body, startPosition, 25);
				startPosition += 25;
				String call2 = APlanUtil.getCString(body, startPosition, 25);

				builder.addParams(call1);
				builder.addParams(call2);
			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			builder.setUnitsn(ppkg.getHead().getSn() & 0xFFFF);
			// byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
			server.getExportMQ().addCommandResponse(builder.build());
		}
	}

	/**
	 * ��ѯ��ʱ�ϱ�����Ӧ��
	 */
	private void queryUploadParamAck(BeforeMarketPackage ppkg, UnitServer server, UnitSocketSession session) {
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = BeforeMarketPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSn());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);

		if (cache != null) {
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			// make protobuf
			Command usercmd = cache.getUserCommand();
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn(usercmd.getSn());
			builder.setCallLetter(callLetter);
			builder.setCmdId(usercmd.getCmdId());
			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);

				int startPosition = 3;
				int interval = Util.getShort(body, startPosition) & 0xFFFF;
				startPosition += 2;
				int count = Util.getShort(body, startPosition) & 0xFFFF;

				builder.addParams(String.valueOf(interval));
				builder.addParams(String.valueOf(count));
			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			// byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		} else {
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			// make protobuf
			int subCmdId = body[0] & 0xFF;
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn("");
			builder.setCallLetter(callLetter);
			builder.setCmdId(getInnerCmdId(subCmdId));

			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);

				int startPosition = 3;
				int interval = Util.getShort(body, startPosition) & 0xFFFF;
				startPosition += 2;
				int count = Util.getShort(body, startPosition) & 0xFFFF;

				builder.addParams(String.valueOf(interval));
				builder.addParams(String.valueOf(count));
			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			builder.setUnitsn(ppkg.getHead().getSn() & 0xFFFF);
			// byte[] data = builder.build().toByteArray();
			server.getExportMQ().addCommandResponse(builder.build());
		}
	}

	/**
	 * Ӧ��1byte
	 */
	private static void ackOneByte(BeforeMarketPackage ppkg, UnitServer server, UnitSocketSession session) {
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = BeforeMarketPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSn());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);

		if (cache != null) {
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			// make protobuf
			Command usercmd = cache.getUserCommand();
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn(usercmd.getSn());
			builder.setCallLetter(callLetter);
			builder.setCmdId(usercmd.getCmdId());
			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);

				int startPosition = 3;
				int upload = body[startPosition] & 0xFF;
				builder.addParams(String.valueOf(upload));
			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			// byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		} else {
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			// make protobuf
			int subCmdId = body[0] & 0xFF;
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn("");
			builder.setCallLetter(callLetter);
			builder.setCmdId(getInnerCmdId(subCmdId));

			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);

				int startPosition = 3;
				int upload = body[startPosition] & 0xFF;
				builder.addParams(String.valueOf(upload));
			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			builder.setUnitsn(ppkg.getHead().getSn() & 0xFFFF);
			// byte[] data = builder.build().toByteArray();
			server.getExportMQ().addCommandResponse(builder.build());
		}
	}

	private static void queryFault(BeforeMarketPackage ppkg, UnitServer server, UnitSocketSession session) {
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = BeforeMarketPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSn());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);

		if (cache != null) {
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			// make protobuf
			Command usercmd = cache.getUserCommand();
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn(usercmd.getSn());
			builder.setCallLetter(callLetter);
			builder.setCmdId(usercmd.getCmdId());
			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);

				int startPosition = 3;
				HMFaultInfo fault = HMFaultInfo.parse(body, startPosition);
				/*
				 * List<String> list = fault.getFaultCodeList(); for(int i = 0;
				 * i < list.size(); i++){ builder.addParams(list.get(i)); }
				 */
				List<HMFaultDefine> list = fault.getFaults();
				for (int i = 0; i < list.size(); i++) {
					HMFaultDefine fd = list.get(i);
					builder.addParams(fd.toString());
				}

				HMUploadUtil.handleFaultInfo(server, callLetter, fault, System.currentTimeMillis(), false);
			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			// byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		} else {
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			// make protobuf
			int subCmdId = body[0] & 0xFF;
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn("");
			builder.setCallLetter(callLetter);
			builder.setCmdId(getInnerCmdId(subCmdId));

			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);

				int startPosition = 3;
				HMFaultInfo fault = HMFaultInfo.parse(body, startPosition);
				/*
				 * List<String> list = fault.getFaultCodeList(); for(int i = 0;
				 * i < list.size(); i++){ builder.addParams(list.get(i)); }
				 */
				List<HMFaultDefine> list = fault.getFaults();
				for (int i = 0; i < list.size(); i++) {
					HMFaultDefine fd = list.get(i);
					builder.addParams(fd.toString());
				}

				HMUploadUtil.handleFaultInfo(server, callLetter, fault, System.currentTimeMillis(), false);
			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			builder.setUnitsn(ppkg.getHead().getSn() & 0xFFFF);
			// byte[] data = builder.build().toByteArray();
			server.getExportMQ().addCommandResponse(builder.build());
		}
	}

	/**
	 * ��ѯ�����������Ӧ��
	 */
	private void queryCallLimitAck(BeforeMarketPackage ppkg, UnitServer server, UnitSocketSession session) {
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = BeforeMarketPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSn());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);

		if (cache != null) {
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			// make protobuf
			Command usercmd = cache.getUserCommand();
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn(usercmd.getSn());
			builder.setCallLetter(callLetter);
			builder.setCmdId(usercmd.getCmdId());
			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);

				int startPosition = 3;
				byte paramIn = body[startPosition];
				int paramOut = Util.getShort(body, startPosition + 1) & 0xFFFF;

				builder.addParams(String.valueOf(paramIn));
				builder.addParams(String.valueOf(paramOut));
			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			// byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		} else {
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			// make protobuf
			int subCmdId = body[0] & 0xFF;
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn("");
			builder.setCallLetter(callLetter);
			builder.setCmdId(getInnerCmdId(subCmdId));

			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);

				int startPosition = 3;
				byte paramIn = body[startPosition];
				int paramOut = Util.getShort(body, startPosition + 1) & 0xFFFF;

				builder.addParams(String.valueOf(paramIn));
				builder.addParams(String.valueOf(paramOut));
			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			builder.setUnitsn(ppkg.getHead().getSn() & 0xFFFF);
			// byte[] data = builder.build().toByteArray();
			server.getExportMQ().addCommandResponse(builder.build());
		}
	}

	/**
	 * �鳵Ӧ��
	 * 
	 * @throws Exception
	 */
	private void queryPositionAck(BeforeMarketPackage ppkg, UnitServer server, UnitSocketSession session)
			throws Exception {
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = BeforeMarketPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSn());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);

		if (cache != null) {
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			// make protobuf
			Command usercmd = cache.getUserCommand();
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn(usercmd.getSn());
			builder.setCallLetter(callLetter);
			builder.setCmdId(usercmd.getCmdId());
			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);

				int startPosition = 3;
				HMGPSInfo gps = HMGPSInfo.parse(body, startPosition);
				HMStatus status = HMStatus.parse(body, startPosition + HMGPSInfo.DATA_LENGTH);

				GpsInfo pgps = HMProtobufUtil.transformGPSInfo(callLetter, ppkg, gps, status, null, null, false);
				builder.addGpsInfos(pgps);

				// ����gps
				// HMUploadUtil.handleGPS(ppkg, server, session, gps, status,
				// null, false);
				HMUploadUtil.handleGPS(ppkg, server, session, pgps);
			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			// byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		} else {
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			// make protobuf
			int subCmdId = body[0] & 0xFF;
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn("");
			builder.setCallLetter(callLetter);
			builder.setCmdId(getInnerCmdId(subCmdId));

			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);

				int startPosition = 3;
				HMGPSInfo gps = HMGPSInfo.parse(body, startPosition);
				HMStatus status = HMStatus.parse(body, startPosition + HMGPSInfo.DATA_LENGTH);

				GpsInfo pgps = HMProtobufUtil.transformGPSInfo(callLetter, ppkg, gps, status, null, null, false);
				builder.addGpsInfos(pgps);

				// ����gps
				// HMUploadUtil.handleGPS(ppkg, server, session, gps, status,
				// null, false);
				HMUploadUtil.handleGPS(ppkg, server, session, pgps);
			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			builder.setUnitsn(ppkg.getHead().getSn() & 0xFFFF);
			// byte[] data = builder.build().toByteArray();
			server.getExportMQ().addCommandResponse(builder.build());
		}
	}

	/**
	 * �鳵��ص�Ԫ����
	 * 
	 * @throws Exception
	 */
	private void queryECUConfigAck(BeforeMarketPackage ppkg, UnitServer server, UnitSocketSession session)
			throws Exception {
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = BeforeMarketPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSn());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);

		if (cache != null) {
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			// make protobuf
			Command usercmd = cache.getUserCommand();
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn(usercmd.getSn());
			builder.setCallLetter(callLetter);
			builder.setCmdId(usercmd.getCmdId());
			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);

				int startPosition = 3;
				HMECUConfig ecuConfig = HMECUConfig.parse(body, startPosition);
				byte[] cfgs = ecuConfig.getConfigs();
				if (cfgs != null) {
					for (int i = 0; i < cfgs.length; i++) {
						String str = HexUtil.byteToString(cfgs[i]);
						builder.addParams(str);
					}
				}

				long updateTime = System.currentTimeMillis();
				HMUploadUtil.handleECUConfig(server, session.getUnitInfo().getCallLetter(), updateTime, ecuConfig);
			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			// byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		} else {
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			// make protobuf
			int subCmdId = body[0] & 0xFF;
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn("");
			builder.setCallLetter(callLetter);
			builder.setCmdId(getInnerCmdId(subCmdId));

			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);

				int startPosition = 3;
				HMECUConfig ecuConfig = HMECUConfig.parse(body, startPosition);
				byte[] cfgs = ecuConfig.getConfigs();
				if (cfgs != null) {
					for (int i = 0; i < cfgs.length; i++) {
						String str = HexUtil.byteToString(cfgs[i]);
						builder.addParams(str);
					}
				}
			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			builder.setUnitsn(ppkg.getHead().getSn() & 0xFFFF);
			server.getExportMQ().addCommandResponse(builder.build());
		}
	}

	/**
	 * ��ѯ�ն˲���
	 * 
	 * @param ppkg
	 * @param server
	 * @param session
	 */
	private void queryTboxInfoAck(BeforeMarketPackage ppkg, UnitServer server, UnitSocketSession session)
			throws Exception {
		// TODO Auto-generated method stub
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = BeforeMarketPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSn());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);

		if (cache != null) {
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			// make protobuf
			Command usercmd = cache.getUserCommand();
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn(usercmd.getSn());
			builder.setCallLetter(callLetter);
			builder.setCmdId(usercmd.getCmdId());
			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);

				int startPosition = 3;
				String softwareVersion = APlanUtil.getCString(body, startPosition, 4);
				startPosition += 4;
				String hardwareVersion = APlanUtil.getCString(body, startPosition, 4);
				startPosition += 4;
				String vin = APlanUtil.getCString(body, startPosition, 17);
				startPosition += 17;
				String barCode = APlanUtil.getCString(body, startPosition, 19);
				startPosition += 19;
				String manufactureDate = APlanUtil.getCString(body, startPosition, 8);
				startPosition += 8;
				String softwareUpgradeDate = APlanUtil.getCString(body, startPosition, 8);

				builder.addParams(softwareVersion);
				builder.addParams(hardwareVersion);
				builder.addParams(vin);
				builder.addParams(barCode);
				builder.addParams(manufactureDate);
				builder.addParams(softwareUpgradeDate);

			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			// byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		} else {
			// δ�ҵ����棬�����ǻ��Ѻ������
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			int subCmdId = body[0] & 0xFF;

			// make protobuf
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn("");
			builder.setCallLetter(callLetter);
			builder.setCmdId(getInnerCmdId(subCmdId));
			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);

				int startPosition = 3;
				String softwareVersion = APlanUtil.getCString(body, startPosition, 4);
				startPosition += 4;
				String hardwareVersion = APlanUtil.getCString(body, startPosition, 4);
				startPosition += 4;
				String vin = APlanUtil.getCString(body, startPosition, 17);
				startPosition += 17;
				String barCode = APlanUtil.getCString(body, startPosition, 19);
				startPosition += 19;
				String manufactureDate = APlanUtil.getCString(body, startPosition, 8);
				startPosition += 8;
				String softwareUpgradeDate = APlanUtil.getCString(body, startPosition, 8);

				builder.addParams(softwareVersion);
				builder.addParams(hardwareVersion);
				builder.addParams(vin);
				builder.addParams(barCode);
				builder.addParams(manufactureDate);
				builder.addParams(softwareUpgradeDate);

			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			builder.setUnitsn(ppkg.getHead().getSn() & 0xFFFF);
			// byte[] data = builder.build().toByteArray();
			server.getExportMQ().addCommandResponse(builder.build());
		}
	}

	/**
	 * ��ѯ�յ�����
	 * 
	 * @param ppkg
	 * @param server
	 * @param session
	 * @throws IOException
	 */
	private void queryAirConditioningParamAck(BeforeMarketPackage ppkg, UnitServer server, UnitSocketSession session)
			throws IOException {
		// TODO Auto-generated method stub
		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = BeforeMarketPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSn());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);

		if (cache != null) {
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			// make protobuf
			Command usercmd = cache.getUserCommand();
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn(usercmd.getSn());
			builder.setCallLetter(callLetter);
			builder.setCmdId(usercmd.getCmdId());
			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);

				int startPosition = 3;
				int param1 = body[startPosition] & 0xFF;
				startPosition++;
				//�¶�
				int param2 = body[startPosition] & 0xFF;
				float temperature = (float)param2;
				temperature = temperature/2;
				
				startPosition++;

				byte param3Byte = body[startPosition];
				int cycleMode = 0;
				if ((param3Byte & 0x1) != 0) {
					cycleMode = 1;
				}

				int acMode = 0;
				if ((param3Byte & 0x2) != 0) {
					acMode = 1;
				}
				
				int autoMode = 0;
				if ((param3Byte & 0x4) != 0) {
					autoMode = 1;
				}
				
				int frontClearFrostMode = 0;
				if ((param3Byte & 0x8) != 0) {
					frontClearFrostMode = 1;
				}
				
				int backClearFrostMode = 0;
				if ((param3Byte & 0x10) != 0) {
					backClearFrostMode = 1;
				}

				startPosition++;
				//����ģʽ
				int param4 = body[startPosition] & 0xFF;
				startPosition++;
				//����
				int param5 = body[startPosition] & 0xFF;

				builder.addParams(String.valueOf(param1));
				builder.addParams(String.valueOf(temperature));
				builder.addParams(String.valueOf(param4));
				builder.addParams(String.valueOf(param5));
				builder.addParams(String.valueOf(cycleMode));
				builder.addParams(String.valueOf(acMode));
				builder.addParams(String.valueOf(autoMode));
				builder.addParams(String.valueOf(frontClearFrostMode));
				builder.addParams(String.valueOf(backClearFrostMode));

			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			// byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		} else {
			// δ�ҵ����棬�����ǻ��Ѻ������
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			int subCmdId = body[0] & 0xFF;

			// make protobuf
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn("");
			builder.setCallLetter(callLetter);
			builder.setCmdId(getInnerCmdId(subCmdId));
			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);

				int startPosition = 3;
				int param1 = body[startPosition] & 0xFF;
				startPosition++;
				//�¶�
				int param2 = body[startPosition] & 0xFF;
				float temperature = (float)param2;
				temperature = temperature/2;
				
				startPosition++;

				byte param3Byte = body[startPosition];
				int cycleMode = 0;
				if ((param3Byte & 0x1) != 0) {
					cycleMode = 1;
				}

				int acMode = 0;
				if ((param3Byte & 0x2) != 0) {
					acMode = 1;
				}
				
				int autoMode = 0;
				if ((param3Byte & 0x4) != 0) {
					autoMode = 1;
				}
				
				int frontClearFrostMode = 0;
				if ((param3Byte & 0x8) != 0) {
					frontClearFrostMode = 1;
				}
				
				int backClearFrostMode = 0;
				if ((param3Byte & 0x10) != 0) {
					backClearFrostMode = 1;
				}

				startPosition++;
				int param4 = body[startPosition] & 0xFF;
				startPosition++;
				int param5 = body[startPosition] & 0xFF;

				builder.addParams(String.valueOf(param1));
				builder.addParams(String.valueOf(temperature));
				builder.addParams(String.valueOf(param4));
				builder.addParams(String.valueOf(param5));
				builder.addParams(String.valueOf(cycleMode));
				builder.addParams(String.valueOf(acMode));
				builder.addParams(String.valueOf(autoMode));
				builder.addParams(String.valueOf(frontClearFrostMode));
				builder.addParams(String.valueOf(backClearFrostMode));

			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			builder.setUnitsn(ppkg.getHead().getSn() & 0xFFFF);
			// byte[] data = builder.build().toByteArray();
			server.getExportMQ().addCommandResponse(builder.build());
		}
	}

	/**
	 * ��ѯ������ٶ�оƬУ������
	 * 
	 * @param ppkg
	 * @param server
	 * @param session
	 */
	private void queryRollParamAck(BeforeMarketPackage ppkg, UnitServer server, UnitSocketSession session)
			throws Exception {
		// TODO Auto-generated method stub

		String callLetter = session.getUnitInfo().getCallLetter();
		String cacheSN = BeforeMarketPkgUtil.getCmdCacheSn(callLetter, ppkg.getHead().getSn());
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(cacheSN);

		if (cache != null) {
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			// make protobuf
			Command usercmd = cache.getUserCommand();
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn(usercmd.getSn());
			builder.setCallLetter(callLetter);
			builder.setCmdId(usercmd.getCmdId());
			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);

				int startPosition = 3;
				int maxAngle = Util.getShort(body, startPosition) & 0xFFFF;
				startPosition += 2;
				int x = Util.getShort(body, startPosition) & 0xFFFF;
				startPosition += 2;
				int y = Util.getShort(body, startPosition) & 0xFFFF;
				startPosition += 2;
				int z = Util.getShort(body, startPosition) & 0xFFFF;
				
				builder.addParams(String.valueOf(maxAngle));
				builder.addParams(String.valueOf(x));
				builder.addParams(String.valueOf(y));
				builder.addParams(String.valueOf(z));

			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			// byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		} else {
			// δ�ҵ����棬�����ǻ��Ѻ������
			byte[] body = ppkg.getBody();
			int unit_ack_result = Util.getShort(body, 1) & 0xFFFF;

			int subCmdId = body[0] & 0xFF;

			// make protobuf
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			builder.setSn("");
			builder.setCallLetter(callLetter);
			builder.setCmdId(getInnerCmdId(subCmdId));
			if (unit_ack_result == 0) {
				builder.setResult(Constants.RESULT_SUCCESS);

				int startPosition = 3;
				int maxAngle = Util.getShort(body, startPosition) & 0xFFFF;
				startPosition += 2;
				int x = Util.getShort(body, startPosition) & 0xFFFF;
				startPosition += 2;
				int y = Util.getShort(body, startPosition) & 0xFFFF;
				startPosition += 2;
				int z = Util.getShort(body, startPosition) & 0xFFFF;
				
				builder.addParams(String.valueOf(maxAngle));
				builder.addParams(String.valueOf(x));
				builder.addParams(String.valueOf(y));
				builder.addParams(String.valueOf(z));

			} else {
				builder.setResult(Constants.RESULT_UNIT_ACK_FAIL);
				builder.addParams(String.valueOf(unit_ack_result));
			}

			builder.setUnitsn(ppkg.getHead().getSn() & 0xFFFF);
			// byte[] data = builder.build().toByteArray();
			server.getExportMQ().addCommandResponse(builder.build());
		}

	}

	private static int getInnerCmdId(int subCmdId) {
		switch (subCmdId) {
		case 0x1:
			return 0x0058;
		case 0x2:
			return 0x0027;
		case 0x3:
			return 0x0030;
		case 0x12:
			return 0x0070;
		case 0x13:
			return 0x0071;
		case 0x14:
			return 0x0072;
		case 0x15:
			return 0x0073;
		case 0x16:
			return 0x0074;
		case 0x18:
			return 0x00A7;
		case 0x19:
			return 0x00AC;
		case 0x1A:
			return 0x00A4;
		case 0x20:
			return 0x00A3;
		case 0x21:
			return 0x0001;
		case 0x22:
			return 0x00B4;
		/*
		 * case 0x24: return 0x00B5;
		 */
		case 0x25:
			return 0x00B7;
		case 0x26:
			return 0x00B5;
		case 0x27:
			return 0x0064;
		default:
			return 0;
		}
	}
}