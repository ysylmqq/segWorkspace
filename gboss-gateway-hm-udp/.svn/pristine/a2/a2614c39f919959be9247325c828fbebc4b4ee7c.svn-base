package cc.chinagps.gateway.unit.pengaoda.upload.beans;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.unit.common.Constants;
import cc.chinagps.gateway.unit.pengaoda.pkg.PengAoDaPackage;
import cc.chinagps.gateway.unit.pengaoda.util.PengAoDaLatLngUtil;
import cc.chinagps.gateway.unit.pengaoda.util.PengAoDaTimeFormatUtil;
import cc.chinagps.gateway.util.HexUtil;

public class PengAoDaTimeGPSInfo {
	private String terminalId;
	
	private boolean isHistory;
	
	private Date gpsTime;
	
	private boolean isLoc;
	
	private double lng;
	
	private double lat;
	
	private double speed;
	
	private int course;
	
	private long distance;
	
	private int height;
	
	private Set<Integer> status = new HashSet<Integer>();
	
	//基站
	private PengAoDaBaseStation baseStation;
	
	//备电电量
	private int battery;
	
	private boolean isAlarm;
	
	private PengAoDaOBDInfo obdInfo;
	
	private PengAoDaFaultInfo faultInfo;

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public boolean isHistory() {
		return isHistory;
	}

	public void setHistory(boolean isHistory) {
		this.isHistory = isHistory;
	}

	public Date getGpsTime() {
		return gpsTime;
	}

	public void setGpsTime(Date gpsTime) {
		this.gpsTime = gpsTime;
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

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getCourse() {
		return course;
	}

	public void setCourse(int course) {
		this.course = course;
	}

	public long getDistance() {
		return distance;
	}

	public void setDistance(long distance) {
		this.distance = distance;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Set<Integer> getStatus() {
		return status;
	}

	public void setStatus(Set<Integer> status) {
		this.status = status;
	}

	public PengAoDaBaseStation getBaseStation() {
		return baseStation;
	}

	public void setBaseStation(PengAoDaBaseStation baseStation) {
		this.baseStation = baseStation;
	}

	public int getBattery() {
		return battery;
	}

	public void setBattery(int battery) {
		this.battery = battery;
	}

	public boolean isAlarm() {
		return isAlarm;
	}

	public void setAlarm(boolean isAlarm) {
		this.isAlarm = isAlarm;
	}

	public PengAoDaOBDInfo getObdInfo() {
		return obdInfo;
	}

	public void setObdInfo(PengAoDaOBDInfo obdInfo) {
		this.obdInfo = obdInfo;
	}

	public PengAoDaFaultInfo getFaultInfo() {
		return faultInfo;
	}

	public void setFaultInfo(PengAoDaFaultInfo faultInfo) {
		this.faultInfo = faultInfo;
	}
	
	public static PengAoDaTimeGPSInfo parse(byte[] data, int offset) throws Exception{
		PengAoDaTimeGPSInfo gps = new PengAoDaTimeGPSInfo();
		int position = offset;
		//固定头0x24
		position++;
		
		String terminalId = Util.bcd2str(data, position, 5);
		position += 5;
		
		String stime = Util.bcd2str(data, position, 3);
		position += 3;
		String sdate = Util.bcd2str(data, position, 3);
		position += 3;
		String date_time = "20" + sdate.substring(4) + sdate.substring(0, 4) + " " + stime;
		Date gpsTime = PengAoDaTimeFormatUtil.parseGMT(date_time);
		
		String slat = Util.bcd2str(data, position, 4);
		position += 4;
		
		byte bbattery = data[position++];
		handleBattery(gps, bbattery);
		
		byte neFlag = data[position + 4];
		String slng = Util.bcd2str(data, position, 5);
		slng = slng.substring(0, slng.length() - 1);
		position += 5;
		boolean isEast = (neFlag & 0x8) != 0;
		boolean isNorth = (neFlag & 0x4) != 0;
		boolean isLoc = (neFlag & 0x2) != 0;		
		double lat = PengAoDaLatLngUtil.parseUnitLatTime(slat, isNorth);
		double lng = PengAoDaLatLngUtil.parseUnitLngTime(slng, isEast);
		
		String speedCourse = Util.bcd2str(data, position, 3);
		position += 3;
		String sspeed = speedCourse.substring(0, 3);
		String scourse = speedCourse.substring(3, 6);
		BigDecimal speed;
		try{
			speed = new BigDecimal(sspeed);
			speed = speed.multiply(Constants.M_SPEED_JIE_TO_KMH);
		}catch (NumberFormatException e) {
			speed = Constants.ERROR_SPEED;
		}
		int course = Integer.valueOf(scourse);
		byte[] flag = new byte[4];
		System.arraycopy(data, position, flag, 0, flag.length);
		position += flag.length;
		handleFlag(gps, flag);
		
		byte alarmFlag = data[position++];
		handleAlarmFlag(gps, alarmFlag);
		
		//报警设置标识(1) 报警设置状态(2)  里程标识(1)
		position += 4;
		
		long distance = Util.getInt(data, position) & 0xFFFFFFFFL;
		position += 4;
		
		//加速度标识(1) 加速度值(4)
		position += 5;
		
		byte dataType = data[position++];
		
		int mcc = Util.getShort(data, position) & 0xFFFF;
		position += 2;
		
		int mnc = data[position++] & 0xFF;
		
		int cellId = Util.getShort(data, position) & 0xFFFF;
		position += 2;
		
		int lac = Util.getShort(data, position) & 0xFFFF;
		position += 2;
		
		int height = Util.getShort(data, position) & 0xFFFF;
		position += 2;
		
		//扩展数据，根据数据类型读取
		switch (dataType) {
			case 1:
				//obd
				PengAoDaOBDInfo pobdInfo = PengAoDaOBDInfo.parse(data, position);
				gps.setObdInfo(pobdInfo);
				break;
			case 2:
				//故障码
				PengAoDaFaultInfo pfaultInfo = PengAoDaFaultInfo.parse(data, position);
				gps.setFaultInfo(pfaultInfo);
				break;
			case 3:
				//车身状态
				handleVehicleBodyStatus(gps, data, position);
				break;
			default:
				break;
		}
		
		gps.setTerminalId(terminalId);
		gps.setGpsTime(gpsTime);
		gps.setLoc(isLoc);
		gps.setLat(lat);
		gps.setLng(lng);
		gps.setSpeed(speed.doubleValue());
		gps.setCourse(course);
		gps.setDistance(distance);
		
		PengAoDaBaseStation baseStation = new PengAoDaBaseStation();
		baseStation.setMcc(mcc);
		baseStation.setMnc(mnc);
		baseStation.setLac(lac);
		baseStation.setCellId(cellId);
		
		gps.setHeight(height);
		
		gps.setBaseStation(baseStation);
		return gps;
	}
	
	private static void handleBattery(PengAoDaTimeGPSInfo gps, byte bbattery){
		int battery = bbattery & 0xF;
		gps.setBattery(battery);
		if((bbattery & 0x80) == 0){
			//侧翻
			//gps.getStatus().add(78);
			//gps.setAlarm(true);
		}
		if((bbattery & 0x40) == 0){
			//碰撞
			//gps.getStatus().add(12);
			//gps.setAlarm(true);
		}
	}
	
	/**
	 * 车辆状态
	 */
	private static void handleFlag(PengAoDaTimeGPSInfo gps, byte[] flag){
		//---flag1---
		byte flag1 = flag[1];
		//1 补报
		if((flag1 & STATUS_AND[1]) == 0){
			gps.setHistory(true);
		}
		
		//7 震动报警
		if((flag1 & STATUS_AND[7]) == 0){
			gps.getStatus().add(18);
			gps.setAlarm(true);
		}
		
		//---flag2---
		byte flag2 = flag[2];
		//0 车门开
		if((flag2 & STATUS_AND[0]) == 0){
			gps.getStatus().add(21);
		}else{
			gps.getStatus().add(25);
		}
		
		//1 车辆设防
		if((flag2 & STATUS_AND[1]) == 0){
			gps.getStatus().add(22);
		}else{
			gps.getStatus().add(32);
		}
		
		//2 ACC
		if((flag2 & STATUS_AND[2]) == 0){
			gps.getStatus().add(23);
		}else{
			gps.getStatus().add(33);
		}
		
		//---flag3---
		byte flag3 = flag[3];
		//0 盗警
		if((flag3 & STATUS_AND[0]) == 0){
			gps.getStatus().add(6);
			gps.setAlarm(true);
		}
		
		//2 超速报警
		if((flag3 & STATUS_AND[2]) == 0){
			gps.getStatus().add(14);
			gps.setAlarm(true);
		}
		
		//3 点火盗警
		if((flag3 & STATUS_AND[3]) == 0){
			gps.getStatus().add(63);
			gps.setAlarm(true);
		}
	}
	
	/**
	 * 警情状态
	 */
	private static void handleAlarmFlag(PengAoDaTimeGPSInfo gps, byte alarmFlag){
		//0 电池电量低
		if((alarmFlag & STATUS_AND[0]) == 0){
			gps.getStatus().add(3);
		}
		
		//2 补报数据
		if((alarmFlag & STATUS_AND[2]) == 0){
			gps.setHistory(true);
		}
		
		//4 水温异常
		if((alarmFlag & STATUS_AND[4]) == 0){
			gps.getStatus().add(44);
		}
		
		//5 疲劳驾驶
		if((alarmFlag & STATUS_AND[5]) == 0){
			gps.getStatus().add(36);
		}
		
		//6 超速报警
		if((alarmFlag & STATUS_AND[6]) == 0){
			gps.getStatus().add(14);
			gps.setAlarm(true);
		}
		
		//7  电瓶欠压
		if((alarmFlag & STATUS_AND[7]) == 0){
			gps.getStatus().add(3);
		}
	}
	
	
	/**
	 * 车身状态(扩展信息)
	 */
	private static void handleVehicleBodyStatus(PengAoDaTimeGPSInfo gps, byte[] data, int offset){
		int position = offset;
		//长度(2) 0xC4(1)
		position += 3;
		
		//门信号
		byte flag1 = data[position ++];
		handleVehicleBodyFlag1(gps, flag1);
		
		//ACC信号
		byte flag2 = data[position ++];
		if(flag2 == 0){
			gps.getStatus().add(23);
		}else{
			gps.getStatus().add(33);
		}
		
		//遥控信号
		//byte flag3 = data[position ++];
		position ++;
		
		//档位信号	
		byte flag4 = data[position ++];
		if(flag4 == 0x50){
			gps.getStatus().add(111);
		}else if(flag4 == 0x52){
			gps.getStatus().add(113);
		}else if(flag4 == 0x4E){
			gps.getStatus().add(112);
		}else if(flag4 == 0x44){
			gps.getStatus().add(114);
		}
		
		//胎压
		//byte flag5 = data[position ++];
		position ++;
		
		//灯信号
		//byte flag6 = data[position ++];
		position ++;
	}
	
	private static final byte[] STATUS_AND = {0x1, 0x2, 0x4, 0x8, 0x10, 0x20, 0x40, (byte) 0x80};
	
	private static void handleVehicleBodyFlag1(PengAoDaTimeGPSInfo gps, byte flag){
		//左前门
		if((flag & STATUS_AND[0]) != 0){
			gps.getStatus().add(107);
		}else{
			gps.getStatus().add(108);
		}
		
		//右前门
		if((flag & STATUS_AND[1]) != 0){
			gps.getStatus().add(105);
		}else{
			gps.getStatus().add(106);
		}
		
		//左后门
		if((flag & STATUS_AND[2]) != 0){
			gps.getStatus().add(103);
		}else{
			gps.getStatus().add(104);
		}
		
		//右后门
		if((flag & STATUS_AND[3]) != 0){
			gps.getStatus().add(101);
		}else{
			gps.getStatus().add(102);
		}
		
		//后尾箱
		if((flag & STATUS_AND[4]) != 0){
			gps.getStatus().add(19);
		}else{
			gps.getStatus().add(67);
		}
	}
	
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuilder sb = new StringBuilder();
		sb.append("{terminalId:").append(terminalId);
		sb.append(", isHistory:").append(isHistory);
		sb.append(", gpsTime:").append(sdf.format(gpsTime));
		sb.append(", isLoc:").append(isLoc);
		sb.append(", lng:").append(lng);
		sb.append(", lat:").append(lat);
		sb.append(", speed:").append(speed);
		sb.append(", course:").append(course);
		sb.append(", distance:").append(distance);
		sb.append(", height:").append(height);
		sb.append(", status:").append(status);
		sb.append(", isAlarm:").append(isAlarm);
		sb.append(", baseStation:").append(baseStation);
		sb.append(", battery:").append(battery);
		sb.append(", obdInfo:").append(obdInfo);
		sb.append(", faultInfo:").append(faultInfo);
		sb.append("}");
		
		return sb.toString();
	}
	
	public static void main(String[] args) throws Exception {
		//String hex_obd = "7E 24 86 36 00 00 01 01 50 48 14 05 14 22 33 87 00 C6 11 40 57 76 0E 00 00 00 FF FB DF FF ED 41 30 00 52 00 00 00 00 63 07 03 03 5E 01 01 CC 00 26 3A 10 60 01 95 00 47 C2 01 33 44 55 66 04 33 44 55 66 05 AA 00 00 00 0C 4E 20 00 00 0D AA 00 00 00 1F 33 44 55 66 42 30 30 30 30 44 33 44 55 66 46 33 44 55 66 4C 33 44 55 66 88 00 00 00 00 89 00 00 00 02 8A 00 08 05 E3 8B 00 00 00 87 00 AF 7E";
		//String hex_fault = "7E 24 86 36 00 00 01 01 51 23 14 05 14 22 33 87 60 C6 11 40 57 59 0E 00 00 00 FF FB DF FF E5 41 30 00 52 00 00 00 00 63 07 03 03 5E 02 01 CC 00 26 3A 10 60 01 95 00 08 C7 00 02 05 02 00 93 02 02 4A 7E";
		String hex_body = "7E 24 86 36 00 00 01 01 51 23 14 05 14 22 33 87 60 C6 11 40 57 59 0E 00 00 00 FF FB DF FF E5 41 30 00 52 00 00 00 00 63 07 03 03 5E 03 01 CC 00 26 3A 10 60 01 95 00 08 C4 1F 00 00 4E 00 10 00 2C 7E";
		byte[] data = HexUtil.hexToBytes(hex_body);
		
		PengAoDaPackage pkg = PengAoDaPackage.parse(data);
		byte[] body = pkg.getBody();
		
		PengAoDaTimeGPSInfo gps = PengAoDaTimeGPSInfo.parse(body, 0);
		System.out.println(gps);
	}
}