package cc.chinagps.gateway.unit.leopaard.upload.cmds;

import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.leopaard.pkg.LeopaardPackage;
import cc.chinagps.gateway.unit.leopaard.upload.bean.VehicleRegisterInfo;

/**
 * ×¢²á£¨D£©
 */
public class Cmd01 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(LeopaardPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception{
		byte[] data = pkg.getDataUnit();
		VehicleRegisterInfo vehicleRegisterInfo = VehicleRegisterInfo.parse(data, 0);
		
		//ack
		
		return false;
	}
}