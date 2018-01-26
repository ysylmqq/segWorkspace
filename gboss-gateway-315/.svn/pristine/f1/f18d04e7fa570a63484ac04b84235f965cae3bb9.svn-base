package cc.chinagps.gateway.unit.kelong.upload.bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cc.chinagps.gateway.StartServer;
import cc.chinagps.gateway.unit.kelong.util.KeLongTimeFormatUtil;
import cc.chinagps.gateway.util.Util;

public class KeLongGPSInfo {
	
	private boolean isHistory;
	
	private long gpsTime;
	
	private String gpsTimeStr;

	private byte locStatus;

	private boolean isLoc;

	private double lng;

	private double lat;

	private int speed;

	private int course;

	private int height;

	private int satelliteCount;

	private int pdop;

	private int hdop;

	private int vdop;
	
	private int distanceMode;
	
	private String locationTime;

	private List<Integer> status = new ArrayList<Integer>();
	
	private KeLongExtendInfo keLongExtendInfo;
	//2017-05-23 by dy
	private KeLongBaseStationInfo keLongBaseStationInfo;
	private KeLongOBDInfo keLongOBDInfo;
	
	public String getLocationTime() {
		return locationTime;
	}

	public void setLocationTime(String locationTime) {
		this.locationTime = locationTime;
	}

	public String getGpsTimeStr() {
		return gpsTimeStr;
	}

	public void setGpsTimeStr(String gpsTimeStr) {
		this.gpsTimeStr = gpsTimeStr;
	}

	public int getDistanceMode() {
		return distanceMode;
	}

	public void setDistanceMode(int distanceMode) {
		this.distanceMode = distanceMode;
	}

	public boolean isHistory() {
		return isHistory;
	}
	
	public void setHistory(boolean isHistory) {
		this.isHistory = isHistory;
	}

	public KeLongExtendInfo getKeLongExtendInfo() {
		return keLongExtendInfo;
	}

	public void setKeLongExtendInfo(KeLongExtendInfo keLongExtendInfo) {
		this.keLongExtendInfo = keLongExtendInfo;
	}

	public List<Integer> getStatus() {
		return status;
	}

	public void setStatus(List<Integer> status) {
		this.status = status;
	}

	public int getSatelliteCount() {
		return satelliteCount;
	}

	public void setSatelliteCount(int satelliteCount) {
		this.satelliteCount = satelliteCount;
	}

	public int getPdop() {
		return pdop;
	}

	public void setPdop(int pdop) {
		this.pdop = pdop;
	}

	public int getHdop() {
		return hdop;
	}

	public void setHdop(int hdop) {
		this.hdop = hdop;
	}

	public int getVdop() {
		return vdop;
	}

	public void setVdop(int vdop) {
		this.vdop = vdop;
	}

	public long getGpsTime() {
		return gpsTime;
	}

	public void setGpsTime(long gpsTime) {
		this.gpsTime = gpsTime;
	}

	public KeLongOBDInfo getKeLongOBDInfo() {
		return keLongOBDInfo;
	}

	public void setKeLongOBDInfo(KeLongOBDInfo keLongOBDInfo) {
		this.keLongOBDInfo = keLongOBDInfo;
	}

	public byte getLocStatus() {
		return locStatus;
	}

	public void setLocStatus(byte locStatus) {
		this.locStatus = locStatus;
	}

	public boolean isLoc() {
		return isLoc;
	}

	public void setLoc(boolean isLoc) {
		this.isLoc = isLoc;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getCourse() {
		return course;
	}

	public void setCourse(int course) {
		this.course = course;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public KeLongBaseStationInfo getKeLongBaseStationInfo() {
		return keLongBaseStationInfo;
	}

	public void setKeLongBaseStationInfo(KeLongBaseStationInfo keLongBaseStationInfo) {
		this.keLongBaseStationInfo = keLongBaseStationInfo;
	}

	public static KeLongGPSInfo parse(byte[] data, int position) throws Exception {
		KeLongGPSInfo keLongGPSInfo = new KeLongGPSInfo();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String deviceTimeStr = "20" + cc.chinagps.gateway.util.Util.getDateTime(data, position, 6);
		Date deviceTime = KeLongTimeFormatUtil.parseGMT0(deviceTimeStr);
		if ((new Date().getTime() - deviceTime.getTime()) > StartServer.isHistoryTime * 1000) {
			keLongGPSInfo.setHistory(true);
		}
		keLongGPSInfo.setGpsTime(deviceTime.getTime());
		keLongGPSInfo.setGpsTimeStr(sdf.format(deviceTime));
		position += 6;

		boolean isSouth = false;
		boolean isWest = false;
		int lat = Util.getInt(data, position);
		if (isWest)
			lat = -1 * lat;
		position += 4;
		
		int lng = Util.getInt(data, position);
		if (isSouth)
			lng = -1 * lng;
		position += 4;
		
		keLongGPSInfo.setLat(lat);
		keLongGPSInfo.setLng(lng);

		int gpsSpeed = Util.getShort(data, position);
		keLongGPSInfo.setSpeed(gpsSpeed);
		position += 2;

		int course = Util.getShort(data, position);
		keLongGPSInfo.setCourse(course);
		position += 4;

		int height = Util.getInt(data, position);
		keLongGPSInfo.setHeight(height);
		position += 2;

		byte satelliteCount = data[position++];
		keLongGPSInfo.setSatelliteCount(satelliteCount);

		byte pdop = data[position++];
		keLongGPSInfo.setPdop(pdop);

		byte hdop = data[position++];
		keLongGPSInfo.setHdop(hdop);

		byte vdop = data[position++];
		keLongGPSInfo.setVdop(vdop);

		byte locStatus = (byte) (data[position++]&0xFF);
		boolean isLoc = ((locStatus & 0x80) != 0);
		keLongGPSInfo.setLoc(isLoc);
		keLongGPSInfo.setLocStatus(locStatus);
		if ((locStatus & 0x01) != 0) {
			keLongGPSInfo.getStatus().add(33);
		} else {
			keLongGPSInfo.getStatus().add(23);
		}
		int distanceMode = 0;
		if((locStatus & 0x2)!=0)
			distanceMode = 1;
		keLongGPSInfo.setDistanceMode(distanceMode);
		KeLongOBDInfo keLongOBDInfo = KeLongOBDInfo.parse(data, position);
		keLongGPSInfo.setKeLongOBDInfo(keLongOBDInfo);
		position += keLongOBDInfo.getDataLen();

		int gpsTime = Util.getInt(data, position) & 0xFFFFFFFF;
		
		
		keLongGPSInfo.setLocationTime(sdf.format(gpsTime*1000L));

		return keLongGPSInfo;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("KeLongGPSInfo [isHistory=");
		builder.append(isHistory);
		builder.append(", gpsTime=");
		builder.append(gpsTime);
		builder.append(", gpsTimeStr=");
		builder.append(gpsTimeStr);
		builder.append(", locStatus=");
		builder.append(locStatus);
		builder.append(", isLoc=");
		builder.append(isLoc);
		builder.append(", lng=");
		builder.append(lng);
		builder.append(", lat=");
		builder.append(lat);
		builder.append(", speed=");
		builder.append(speed);
		builder.append(", course=");
		builder.append(course);
		builder.append(", height=");
		builder.append(height);
		builder.append(", satelliteCount=");
		builder.append(satelliteCount);
		builder.append(", pdop=");
		builder.append(pdop);
		builder.append(", hdop=");
		builder.append(hdop);
		builder.append(", vdop=");
		builder.append(vdop);
		builder.append(", distanceMode=");
		builder.append(distanceMode);
		builder.append(", locationTime=");
		builder.append(locationTime);
		builder.append(", status=");
		builder.append(status);
		builder.append(", keLongOBDInfo=");
		builder.append(keLongOBDInfo);
		builder.append(", keLongExtendInfo=");
		builder.append(keLongExtendInfo);
		builder.append(", keLongBaseStationInfo=");
		builder.append(keLongBaseStationInfo);
		builder.append("]");
		return builder.toString();
	}
}
