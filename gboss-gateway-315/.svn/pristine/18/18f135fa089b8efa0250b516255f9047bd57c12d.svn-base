package cc.chinagps.gateway.unit.kelong.upload.bean;

import java.util.ArrayList;

/**
 *@todo：科隆终端基站包 
 *@author：cj
 *@time：2017年5月25日
 *
 */
public class KelongBaseStationPkg {
	private KeLongBaseStationInfo keLongBaseStationInfo;
	private KeLongOBDInfo keLongOBDInfo;
	private byte status;
	private byte reserve;
		
	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public byte getReserve() {
		return reserve;
	}

	public void setReserve(byte reserve) {
		this.reserve = reserve;
	}

	public KeLongBaseStationInfo getKeLongBaseStationInfo() {
		return keLongBaseStationInfo;
	}

	public void setKeLongBaseStationInfo(KeLongBaseStationInfo keLongBaseStationInfo) {
		this.keLongBaseStationInfo = keLongBaseStationInfo;
	}

	public KeLongOBDInfo getKeLongOBDInfo() {
		return keLongOBDInfo;
	}

	public void setKeLongOBDInfo(KeLongOBDInfo keLongOBDInfo) {
		this.keLongOBDInfo = keLongOBDInfo;
	}

	/**
	 * @todo:解析科隆终端的基站包:基站基本包+obd信息
	 * @author:cj
	 * @param:
	 * @return:
	 * @remark:
	 */
	public static KelongBaseStationPkg parse(byte[] data, int position) throws Exception {
		KelongBaseStationPkg  kelongBaseStationPkg = new KelongBaseStationPkg();
		//一.解析基站基本信息
		KeLongBaseStationInfo baseStaion = new KeLongBaseStationInfo();
		baseStaion = KeLongBaseStationInfo.parse(data, position);
		//二.解析OBD基本信息
		KeLongOBDInfo klObdInfo = new KeLongOBDInfo();
		position += baseStaion.getDataLen();
		klObdInfo = KeLongOBDInfo.parse(data, position);
		position += klObdInfo.getDataLen();
		kelongBaseStationPkg.setStatus(data[position++]);
		kelongBaseStationPkg.setReserve(data[position++]);
		
		kelongBaseStationPkg.setKeLongBaseStationInfo(baseStaion);
		kelongBaseStationPkg.setKeLongOBDInfo(klObdInfo);		
		return kelongBaseStationPkg;
	}
	
	/**
	 * @todo:根据基站包组装gps信息类;
	 * @author:cj
	 * @param:
	 * @return:
	 * @remark:
	 */
	public static KeLongGPSInfo makeupGpsInfoPkg(KelongBaseStationPkg baseObdPkg){		
		KeLongBaseStationInfo baseStaion = baseObdPkg.getKeLongBaseStationInfo();
		KeLongOBDInfo klObdInfo = baseObdPkg.getKeLongOBDInfo();
		//开始组抓鬼科隆Gps数据
		KeLongGPSInfo keLongGPSInfo = new KeLongGPSInfo();
		keLongGPSInfo.setHistory(false);
		keLongGPSInfo.setGpsTime(baseStaion.getBaseStationTime());
		keLongGPSInfo.setGpsTimeStr(baseStaion.getBaseStationTimeStr());
		keLongGPSInfo.setLocStatus((byte)0);
		keLongGPSInfo.setLoc(false);//不定位		
		keLongGPSInfo.setSpeed(klObdInfo.getSpeed());
		
		ArrayList<Integer> statusList = new ArrayList<Integer>();
		byte status = baseObdPkg.getStatus();
		if((status&1)!=0){
			statusList.add(33);
		}else{
			statusList.add(23);
		}
		keLongGPSInfo.setStatus(statusList);//车辆状态信息：如点熄火状态
		
		keLongGPSInfo.setKeLongBaseStationInfo(baseStaion);
		keLongGPSInfo.setKeLongOBDInfo(klObdInfo);
		//keLongGPSInfo.setLat(lat);
		//keLongGPSInfo.setLat(lon);
		//keLongGPSInfo.setCourse(0);
		//keLongGPSInfo.setHeight(0);
		//keLongGPSInfo.setSatelliteCount(KeLongBaseStationInfo.getSatelliteCount(baseStaion));
		//keLongGPSInfo.setPdop(10);
		//keLongGPSInfo.setHdop(10);
		//keLongGPSInfo.setVdop(10);
		//keLongGPSInfo.setDistanceMode(0);
		//keLongGPSInfo.setLocationTime();

		return keLongGPSInfo;
	}
	
	@Override
	public String toString() {
		return "KelongBaseStationPkg [keLongBaseStationInfo=" + keLongBaseStationInfo + ", keLongOBDInfo="
				+ keLongOBDInfo + ", status=" + status + ", reserve=" + reserve + "]";
	}

	public static void main(String[] args) {
		KelongBaseStationPkg aa = new KelongBaseStationPkg();
		KeLongBaseStationInfo baseStationInfo =new KeLongBaseStationInfo();
		KeLongOBDInfo obdInfo = new KeLongOBDInfo();
		aa.setKeLongBaseStationInfo(baseStationInfo);
		aa.setKeLongOBDInfo(obdInfo);
		System.out.println(aa.toString());
		
	}
}
