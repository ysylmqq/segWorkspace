package cc.chinagps.gateway.stream;

import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.unit.UnitSocketSession;

public interface OutputPackageEncoder {
	public ServerToUnitCommand encode(CommandBuff.Command userCmd, UnitSocketSession unitSession) throws Exception;
	
	/**
	 * 指令回应类型
	 * 1: 下发成功即成功
	 * 2： 需回应成功
	 */
	public static final int SUCCESS_AFTER_SEND = 1;

	public static final int SUCCESS_AFTER_RESPONSE = 2;
}