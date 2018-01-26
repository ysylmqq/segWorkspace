package cc.chinagps.gateway.unit.kelong.upload.cmds;

import java.util.ArrayList;
import java.util.List;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.memcache.MemcacheManager;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.kelong.pkg.KeLongPackage;
import cc.chinagps.gateway.unit.kelong.upload.KeLongVehicleManager;
import cc.chinagps.gateway.unit.kelong.util.KeLongUploadUtil;

/**
 * 请求obd适配信息
 */
public class Cmd0007 extends CheckLoginHandler {
	@Override
	public boolean handlerPackageSessionChecked(KeLongPackage pkg, UnitServer server, UnitSocketSession session)
			throws Exception {
		byte data[] = getObdAdaptInfo(session);
		if (data != null)
			KeLongUploadUtil.msgAck(session, pkg, (short) 0x8007, data);
		return true;
	}
	
	private byte[] getDefaultObdAdaptInfo()throws Exception{
		int len = 0;
		List<byte[]> data = new ArrayList<byte[]>();
		byte[] protocolType = new byte[4];
		protocolType[0] = 0x01;
		protocolType[1] = 2;
		System.arraycopy(Util.getShortByte((short) 0x0000), 0, protocolType, 2, 2);
		data.add(protocolType);
		len += 4;

		byte[] readFaultCodeWay = new byte[3];
		readFaultCodeWay[0] = 0x02;
		readFaultCodeWay[1] = 1;
		readFaultCodeWay[2] = 0x00;
		data.add(readFaultCodeWay);
		len += 3;

		byte[] antiTheftProtocol = new byte[6];
		antiTheftProtocol[0] = 0x03;
		antiTheftProtocol[1] = 4;
		System.arraycopy(Util.getIntByte(0x00000000), 0, antiTheftProtocol, 2, 4);
		data.add(antiTheftProtocol);
		len += 6;
		// 车型id
		byte[] vehicleBrandId = new byte[6];
		vehicleBrandId[0] = 0x04;
		vehicleBrandId[1] = 4;
		System.arraycopy(Util.getIntByte(0x0000FF00), 0, vehicleBrandId, 2, 4);
		data.add(vehicleBrandId);
		len += 6;
		//
		byte[] frameInterval = new byte[4];
		frameInterval[0] = 0x05;
		frameInterval[1] = 2;
		System.arraycopy(Util.getShortByte((short) 0x0000), 0, frameInterval, 2, 2);
		data.add(frameInterval);
		len += 4;

		byte[] ecuAddress = new byte[6];
		ecuAddress[0] = 0x06;
		ecuAddress[1] = 4;
		System.arraycopy(Util.getIntByte(0x00000000), 0, ecuAddress, 2, 4);
		data.add(ecuAddress);
		len += 6;

		byte[] oilCoefficient = new byte[4];
		oilCoefficient[0] = 0x07;
		oilCoefficient[1] = 2;
		System.arraycopy(Util.getShortByte((short) 0x0000), 0, oilCoefficient, 2, 2);
		data.add(oilCoefficient);
		len += 4;

		byte[] distanceCoefficient = new byte[4];
		distanceCoefficient[0] = 0x08;
		distanceCoefficient[1] = 2;
		System.arraycopy(Util.getShortByte((short) 0x0000), 0, distanceCoefficient, 2, 2);
		data.add(distanceCoefficient);
		len += 4;

		// 排量
		byte[] displacement = new byte[3];
		displacement[0] = 0x09;
		displacement[1] = 1;
		displacement[2] = 0x10;
		data.add(displacement);
		len += 3;

		byte[] oilDensity = new byte[4];
		oilDensity[0] = 0x0A;
		oilDensity[1] = 2;
		System.arraycopy(Util.getShortByte((short) 0x0000), 0, oilDensity, 2, 2);
		data.add(oilDensity);
		len += 4;

		byte[] oilComputeWay = new byte[3];
		oilComputeWay[0] = 0x0B;
		oilComputeWay[1] = 1;
		oilComputeWay[2] = 0x00;
		data.add(oilComputeWay);
		len += 3;

		byte[] dataStreamReadTime = new byte[3];
		dataStreamReadTime[0] = 0x0C;
		dataStreamReadTime[1] = 1;
		dataStreamReadTime[2] = 0x00;
		data.add(dataStreamReadTime);
		len += 3;

		byte[] changeVehicleFlag = new byte[3];
		changeVehicleFlag[0] = 0x0D;
		changeVehicleFlag[1] = 1;
		changeVehicleFlag[2] = 0x00;
		data.add(changeVehicleFlag);
		len += 3;

		// 车辆动力类型
		byte[] vehiclePowerType = new byte[3];
		vehiclePowerType[0] = 0x0E;
		vehiclePowerType[1] = 1;
		vehiclePowerType[2] = 0x00;
		data.add(vehiclePowerType);
		len += 3;


		byte[] obdAdaptData = new byte[len];
		int pos = 0;
		for (byte[] b : data) {
			System.arraycopy(b, 0, obdAdaptData, pos, b.length);
			pos += b.length;
		}

		return obdAdaptData;
	}

	public byte[] getObdAdaptInfo(UnitSocketSession session) throws Exception{
		int len = 0;
		String callLetter = session.getUnitInfo().getCallLetter();
		Object simpleOdbAdaptInfo = MemcacheManager.getMemcachedClient()
				.get(MemcacheManager.OBD_ADAPT_KEY_HEAD + callLetter);
		String obdAdapt = null;
		if (simpleOdbAdaptInfo == null)
			return getDefaultObdAdaptInfo();//默认车型obd适配信息
		else
			obdAdapt = (String) simpleOdbAdaptInfo;

		String[] obdAdaptArray = obdAdapt.split(",");

		List<byte[]> data = new ArrayList<byte[]>();
		byte[] protocolType = new byte[4];
		protocolType[0] = 0x01;
		protocolType[1] = 2;
		System.arraycopy(Util.getShortByte((short) 0x0000), 0, protocolType, 2, 2);
		data.add(protocolType);
		len += 4;

		byte[] readFaultCodeWay = new byte[3];
		readFaultCodeWay[0] = 0x02;
		readFaultCodeWay[1] = 1;
		readFaultCodeWay[2] = 0x00;
		data.add(readFaultCodeWay);
		len += 3;

		byte[] antiTheftProtocol = new byte[6];
		antiTheftProtocol[0] = 0x03;
		antiTheftProtocol[1] = 4;
		System.arraycopy(Util.getIntByte(0x00000000), 0, antiTheftProtocol, 2, 4);
		data.add(antiTheftProtocol);
		len += 6;
		// 车型id
		byte[] vehicleBrandId = new byte[6];
		vehicleBrandId[0] = 0x04;
		vehicleBrandId[1] = 4;
		Integer segId = null;
		try {
			segId = Integer.valueOf(obdAdaptArray[0]);
		} catch (Exception e) {
			// TODO: handle exception
			segId = null;
		}
		Integer code = KeLongVehicleManager.getBrandCode(segId);
		if (code == null)
			System.arraycopy(Util.getIntByte(0x0000FF00), 0, vehicleBrandId, 2, 4);
		else {
			System.arraycopy(Util.getIntByte(code), 0, vehicleBrandId, 2, 4);
		}
		data.add(vehicleBrandId);
		len += 6;
		//
		byte[] frameInterval = new byte[4];
		frameInterval[0] = 0x05;
		frameInterval[1] = 2;
		System.arraycopy(Util.getShortByte((short) 0x0000), 0, frameInterval, 2, 2);
		data.add(frameInterval);
		len += 4;

		byte[] ecuAddress = new byte[6];
		ecuAddress[0] = 0x06;
		ecuAddress[1] = 4;
		System.arraycopy(Util.getIntByte(0x00000000), 0, ecuAddress, 2, 4);
		data.add(ecuAddress);
		len += 6;

		byte[] oilCoefficient = new byte[4];
		oilCoefficient[0] = 0x07;
		oilCoefficient[1] = 2;
		System.arraycopy(Util.getShortByte((short) 0x0000), 0, oilCoefficient, 2, 2);
		data.add(oilCoefficient);
		len += 4;

		byte[] distanceCoefficient = new byte[4];
		distanceCoefficient[0] = 0x08;
		distanceCoefficient[1] = 2;
		System.arraycopy(Util.getShortByte((short) 0x0000), 0, distanceCoefficient, 2, 2);
		data.add(distanceCoefficient);
		len += 4;

		// 排量
		byte[] displacement = new byte[3];
		displacement[0] = 0x09;
		displacement[1] = 1;
		Integer disp = null;
		try {
			disp = (int) (Float.valueOf(obdAdaptArray[1])*10);
		} catch (Exception e) {
			// TODO: handle exception
			disp = null;
		}
		
		if (disp == null) {
			displacement[2] = 0x10;
		} else {
			displacement[2] = disp.byteValue();
		}

		data.add(displacement);
		len += 3;

		byte[] oilDensity = new byte[4];
		oilDensity[0] = 0x0A;
		oilDensity[1] = 2;
		System.arraycopy(Util.getShortByte((short) 0x0000), 0, oilDensity, 2, 2);
		data.add(oilDensity);
		len += 4;

		byte[] oilComputeWay = new byte[3];
		oilComputeWay[0] = 0x0B;
		oilComputeWay[1] = 1;
		oilComputeWay[2] = 0x00;
		data.add(oilComputeWay);
		len += 3;

		byte[] dataStreamReadTime = new byte[3];
		dataStreamReadTime[0] = 0x0C;
		dataStreamReadTime[1] = 1;
		dataStreamReadTime[2] = 0x00;
		data.add(dataStreamReadTime);
		len += 3;

		byte[] changeVehicleFlag = new byte[3];
		changeVehicleFlag[0] = 0x0D;
		changeVehicleFlag[1] = 1;
		changeVehicleFlag[2] = 0x00;
		data.add(changeVehicleFlag);
		len += 3;

		// 车辆动力类型
		byte[] vehiclePowerType = new byte[3];
		vehiclePowerType[0] = 0x0E;
		vehiclePowerType[1] = 1;
		Integer segPowerType = null;
		try {
			segPowerType = Integer.valueOf(obdAdaptArray[2]);
		} catch (Exception e) {
			// TODO: handle exception
			segPowerType = null;
		}
		segPowerType = KeLongVehicleManager.getPowerType(segPowerType);
		if (segPowerType == null)
			vehiclePowerType[2] = 0x00;
		else {
			vehiclePowerType[2] = segPowerType.byteValue();
		}
		data.add(vehiclePowerType);
		len += 3;

		/*byte[] maxTorque = new byte[4];
		maxTorque[0] = 0x0F;
		maxTorque[1] = 2;
		System.arraycopy(Util.getShortByte((short) 0x0000), 0, maxTorque, 2, 2);
		data.add(maxTorque);
		len += 4;

		byte[] engineCylinderNum = new byte[3];
		engineCylinderNum[0] = 0x10;
		engineCylinderNum[1] = 1;
		engineCylinderNum[2] = 0x00;
		data.add(engineCylinderNum);
		len += 3;

		byte[] fullLoadElectricity = new byte[4];
		fullLoadElectricity[0] = 0x11;
		fullLoadElectricity[1] = 2;
		System.arraycopy(Util.getShortByte((short) 0x0000), 0, fullLoadElectricity, 2, 2);
		data.add(fullLoadElectricity);
		len += 4;

		byte[] reserve = new byte[6];
		reserve[0] = 0x12;
		reserve[1] = 4;
		System.arraycopy(Util.getIntByte(0x00000000), 0, reserve, 2, 4);
		data.add(reserve);
		len += 6;*/

		byte[] obdAdaptData = new byte[len];
		int pos = 0;
		for (byte[] b : data) {
			System.arraycopy(b, 0, obdAdaptData, pos, b.length);
			pos += b.length;
		}

		return obdAdaptData;
	}
	
	public static void main(String[] args) {
		String s = ",1.6, ";
		String[] arr = s.split(",");
		System.out.println(arr.length);
		System.out.println(arr[0]+"---"+arr[1]+arr[2]);
		Integer disp = null;
		try {
			disp = (int) (Float.valueOf(arr[1])*10);
		} catch (Exception e) {
			// TODO: handle exception
			disp = null;
		}
		System.out.println(disp);
	}
}