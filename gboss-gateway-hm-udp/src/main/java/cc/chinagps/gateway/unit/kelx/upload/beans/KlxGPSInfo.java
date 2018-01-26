package cc.chinagps.gateway.unit.kelx.upload.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.unit.common.Constants;
import cc.chinagps.gateway.unit.kelx.pkg.KlxPackage;
import cc.chinagps.gateway.unit.kelx.util.KlxLatLngUtil;
import cc.chinagps.gateway.unit.kelx.util.KlxTimeFormatUtil;
import cc.chinagps.gateway.util.HexUtil;

public class KlxGPSInfo {
	private boolean isLoc;
	
	private boolean isHistory;
	
	private Date gpsTime;
	
	//alarm flag
	private byte[] alarmFlag;
	
	//flag
	private byte flag;
	
	//gps
	private int satelliteCount;
	
	private double lng;
	
	private double lat;
	
	private int course;
	
	private int speed;
	
	//基站
	private KlxBaseStation baseStation;

	//gps和基站都有
	private int obdSpeed;
	
	private int waterTemperature;
	
	//add
	private List<Integer> status = new ArrayList<Integer>();

	private boolean isAlarm;
	
	public boolean isLoc() {
		return isLoc;
	}

	public void setLoc(boolean isLoc) {
		this.isLoc = isLoc;
	}

	public boolean isHistory() {
		return isHistory;
	}

	public void setHistory(boolean isHistory) {
		this.isHistory = isHistory;
	}

	public List<Integer> getStatus() {
		return status;
	}

	public void setStatus(List<Integer> status) {
		this.status = status;
	}

	public boolean isAlarm() {
		return isAlarm;
	}

	public void setAlarm(boolean isAlarm) {
		this.isAlarm = isAlarm;
	}

	public Date getGpsTime() {
		return gpsTime;
	}

	public void setGpsTime(Date gpsTime) {
		this.gpsTime = gpsTime;
	}

	public byte[] getAlarmFlag() {
		return alarmFlag;
	}

	public void setAlarmFlag(byte[] alarmFlag) {
		this.alarmFlag = alarmFlag;
	}

	public byte getFlag() {
		return flag;
	}

	public void setFlag(byte flag) {
		this.flag = flag;
	}

	public int getSatelliteCount() {
		return satelliteCount;
	}

	public void setSatelliteCount(int satelliteCount) {
		this.satelliteCount = satelliteCount;
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

	public int getCourse() {
		return course;
	}

	public void setCourse(int course) {
		this.course = course;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public KlxBaseStation getBaseStation() {
		return baseStation;
	}

	public void setBaseStation(KlxBaseStation baseStation) {
		this.baseStation = baseStation;
	}

	public int getObdSpeed() {
		return obdSpeed;
	}

	public void setObdSpeed(int obdSpeed) {
		this.obdSpeed = obdSpeed;
	}

	public int getWaterTemperature() {
		return waterTemperature;
	}

	public void setWaterTemperature(int waterTemperature) {
		this.waterTemperature = waterTemperature;
	}
	
	public static KlxGPSInfo parse(byte[] data, int offset) throws Exception{
		KlxGPSInfo gps = new KlxGPSInfo();
		
		int position = offset;
		
		String str_time = "20" + Util.bcd2str(data, position, 6);
		Date gpsTime;
		try{
			gpsTime = KlxTimeFormatUtil.parseGMT8(str_time);;
		}catch (ParseException e) {
			gpsTime = Constants.ERROR_DATE;
		}
		position += 6;
		gps.setGpsTime(gpsTime);
		
		byte[] alarm_flag = new byte[2];
		System.arraycopy(data, position, alarm_flag, 0, 2);
		position += 2;
		gps.setAlarmFlag(alarm_flag);
		
		byte flag = data[position ++];
		gps.setFlag(flag);
		
		boolean isLoc = (flag & 0x2) != 0;
		boolean isHistory = (flag & 0x40) == 0;
		
		gps.setLoc(isLoc);
		gps.setHistory(isHistory);
		if(isLoc){
			//gps定位
			int satelliteCount = data[position ++] & 0xFF;
			gps.setSatelliteCount(satelliteCount);
			
			double lng;
			try{
				lng = KlxLatLngUtil.getLng(data, position, flag);
			}catch (NumberFormatException e) {
				lng = 0;
			}
			position += 4;
			gps.setLng(lng);
			
			double lat;
			try{
				lat = KlxLatLngUtil.getLat(data, position, flag);
			}catch (NumberFormatException e) {
				lat = 0;
			}
			position += 4;
			gps.setLat(lat);
			
			int course = (data[position++] & 0xFF) * 2;
			gps.setCourse(course);
			
			int speed = data[position++] & 0xFF;
			gps.setSpeed(speed);
		}else{
			//基站定位
			KlxBaseStation baseStation = KlxBaseStation.parse(data, position);
			position += 8;
			gps.setBaseStation(baseStation);
		}
		
		int obdSpeed = data[position++] & 0xFF;
		gps.setObdSpeed(obdSpeed);
		
		int tem = data[position++] & 0xFF;
		gps.setWaterTemperature(tem);
		
		handleAlarmFlag(gps, alarm_flag);
		handleFlag(gps, flag);
		
		return gps;
	}
	
	//7 -- 0x80
	//6 -- 0x40
	//5 -- 0x20
	//4 -- 0x10
	//3 -- 0x8
	//2 -- 0x4
	//1 -- 0x2
	//0 -- 0x1
	private static final byte[] STATUS_AND = {0x1, 0x2, 0x4, 0x8, 0x10, 0x20, 0x40, (byte) 0x80};
		
	/**
	 * 处理警情标志位
	 */
	private static void handleAlarmFlag(KlxGPSInfo gps, byte[] alarmFlag){
		byte flag0 = alarmFlag[0];
		//1 超速报警
		if((flag0 & STATUS_AND[1]) != 0){
			gps.getStatus().add(14);
			gps.setAlarm(true);
		}
		
		//3电池欠压
		if((flag0 & STATUS_AND[3]) != 0){
			gps.getStatus().add(3);
			gps.setAlarm(true);
		}
		
		//5进矩形区域报警
		if((flag0 & STATUS_AND[5]) != 0){
			gps.getStatus().add(98);
			gps.setAlarm(true);
		}
		
		//6出矩形区域报警
		if((flag0 & STATUS_AND[6]) != 0){
			gps.getStatus().add(81);
			gps.setAlarm(true);
		}
		
		//7碰撞报警
		if((flag0 & STATUS_AND[7]) != 0){
			gps.getStatus().add(12);
			gps.setAlarm(true);
		}
		
		byte flag1 = alarmFlag[1];
		//x-8
		//9 水温高报警
		if((flag1 & STATUS_AND[1]) != 0){
			gps.getStatus().add(44);
			gps.setAlarm(true);
		}
		
		//12 震动报警
		if((flag1 & STATUS_AND[4]) != 0){
			gps.getStatus().add(18);
			gps.setAlarm(true);
		}
	}
	
	/**
	 * 处理标志位
	 */
	private static void handleFlag(KlxGPSInfo gps, byte flag){
		//0 ACC
		if((flag & STATUS_AND[0]) != 0){
			gps.getStatus().add(33);
		}else{
			gps.getStatus().add(23);
		}
		
		//4 疲劳驾驶
		if((flag & STATUS_AND[4]) != 0){
			gps.getStatus().add(36);
		}
	}
	
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuilder sb = new StringBuilder();
		sb.append("{isLoc:").append(isLoc);
		sb.append(", isHistory:").append(isHistory);
		sb.append(", gpsTime:").append(sdf.format(gpsTime));
		sb.append(", alarmFlag:").append(HexUtil.byteToHexStr(alarmFlag));
		sb.append(", flag:0x").append(HexUtil.toHexStr(flag));
		sb.append(", isAlarm:").append(isAlarm);
		sb.append(", status:").append(status);
		
		if(isLoc){
			sb.append(", satelliteCount:").append(satelliteCount);
			sb.append(", lng:").append(lng);
			sb.append(", lat:").append(lat);
			sb.append(", course:").append(course);
			sb.append(", speed:").append(speed);
			sb.append("").append("");
			sb.append("").append("");
		}else{
			sb.append(", baseStation:").append(baseStation);
		}
		
		sb.append(", obdSpeed:").append(obdSpeed);
		sb.append(", waterTemperature:").append(waterTemperature);
		sb.append("}");
		
		return sb.toString();
	}
	
	public static void main(String[] args) throws Exception {
		String hex = "AA000000000145292753224A0022000415051519560300004F081133377522225501812C2D55020006061100B800003DB894AA";
		byte[] source = HexUtil.hexToBytes(hex);
		KlxPackage pkg = KlxPackage.parse(source);
		System.out.println("pkg:" + pkg);
		
		byte[] body = pkg.getBody();
		KlxGPSInfo gps = KlxGPSInfo.parse(body, 2);
		System.out.println(gps);
	}
}