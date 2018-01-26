package cc.chinagps.gateway.unit.kelx.download;

import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.exceptions.WrongFormatException;
import cc.chinagps.gateway.seat.cmd.CmdUtil;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.stream.OutputPackageEncoder;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.kelx.pkg.KlxPackage;
import cc.chinagps.gateway.unit.kelx.util.KlxPkgUtil;
import cc.chinagps.gateway.unit.seg.download.SegServerToUnitCommand;

public class KlxDownloadCmdEncoder implements OutputPackageEncoder{
	@Override
	public ServerToUnitCommand encode(Command userCmd,
			UnitSocketSession unitSession) throws Exception {
		int cmdIdx = userCmd.getCmdId();
		switch (cmdIdx) {
			case CmdUtil.CMD_ID_POSITION://²é³µ
				return getQueryPositionCmd(userCmd, unitSession);
			default:
				throw new WrongFormatException("(klx)no encoder, cmdId:" + cmdIdx);
		}
	}
	
	private ServerToUnitCommand getQueryPositionCmd(Command userCmd,
			UnitSocketSession unitSession) throws Exception{
		byte[] body = {(byte) 0x80, 0x05};		
		byte[] response_id = KlxPkgUtil.getIdByCallLetter(unitSession.getUnitInfo().getCallLetter());
		
		KlxPackage pkg = new KlxPackage();
		pkg.setBody(body);
		pkg.setId(response_id);
		byte sn = KlxPkgUtil.getSn(unitSession);
		pkg.setSn(sn);
		byte[] source = pkg.encode();
		
		SegServerToUnitCommand serverToUnitCommand = new SegServerToUnitCommand();
		serverToUnitCommand.setUserCommand(userCmd);
		String cachedSn = KlxPkgUtil.getCmdCacheSn(unitSession.getUnitInfo().getCallLetter(), sn);
		serverToUnitCommand.setCachedSn(cachedSn);
		serverToUnitCommand.setData(source);
		serverToUnitCommand.setResponseType(OutputPackageEncoder.SUCCESS_AFTER_SEND);
		return serverToUnitCommand;
	}
}