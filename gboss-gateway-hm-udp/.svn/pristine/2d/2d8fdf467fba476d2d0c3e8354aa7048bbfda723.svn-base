package cc.chinagps.gateway.unit.pengaoda.download;

import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.exceptions.WrongFormatException;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.stream.OutputPackageEncoder;
import cc.chinagps.gateway.unit.UnitSocketSession;

public class PengAoDaDownloadCmdEncoder implements OutputPackageEncoder{
	@Override
	public ServerToUnitCommand encode(Command userCmd,
			UnitSocketSession unitSession) throws Exception {
		int cmdIdx = userCmd.getCmdId();
		throw new WrongFormatException("(pengAoDa)no encoder, cmdId:" + cmdIdx);
	}
}