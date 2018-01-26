package cc.chinagps.gateway.unit.pengaoda.upload.beans;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import cc.chinagps.gateway.unit.common.Constants;
import cc.chinagps.gateway.unit.pengaoda.util.PengAoDaLatLngUtil;
import cc.chinagps.gateway.unit.pengaoda.util.PengAoDaPkgUtil;
import cc.chinagps.gateway.unit.pengaoda.util.PengAoDaTimeFormatUtil;
import cc.chinagps.gateway.util.HexUtil;

public class PengAoDaGPSInfo {
	private String terminalId;
	
	private String cmdId;
	
	private boolean isHistory;
	
	private Date gpsTime;
	
	private boolean isLoc;
	
	private double lng;
	
	private double lat;
	
	private double speed;
	
	private int course;
	
	private Set<Integer> status = new HashSet<Integer>();
	
	//基站
	private PengAoDaBaseStation baseStation;
	
	//备电电量
	private int battery;
	
	private boolean isAlarm;

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getCmdId() {
		return cmdId;
	}

	public void setCmdId(String cmdId) {
		this.cmdId = cmdId;
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

	public boolean isHistory() {
		return isHistory;
	}

	public void setHistory(boolean isHistory) {
		this.isHistory = isHistory;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuilder sb = new StringBuilder();
		sb.append("{terminalId:").append(terminalId);
		sb.append(", cmdId:").append(cmdId);
		sb.append(", isHistory:").append(isHistory);
		sb.append(", gpsTime:").append(sdf.format(gpsTime));
		sb.append(", isLoc:").append(isLoc);
		sb.append(", lng:").append(lng);
		sb.append(", lat:").append(lat);
		sb.append(", speed:").append(speed);
		sb.append(", course:").append(course);
		sb.append(", status:").append(status);
		sb.append(", battery:").append(battery);
		sb.append(", isAlarm:").append(isAlarm);
		sb.append(", baseStation:").append(baseStation);
		sb.append("}");
		
		return sb.toString();
	}

	public static PengAoDaGPSInfo parse(String[] params) throws ParseException{
		String terminalId = params[1];
		String cmdId =  params[2];
		String stime = params[3];
		String sloc = params[4];
		String slat = params[5];
		String isN = params[6];
		String slng = params[7];
		String isE = params[8];
		String sspeed = params[9];
		String scourse = params[10];
		String sdate = params[11];
		String sstatus = params[12];
		String salarm = params[13];
		//String salarmSet = params[14];
		String smcc = params[15];
		String smnc = params[16];
		String scid = params[17];
		String slac = params[18];
		String sbattery = params[19];
		
		String date_time = "20" + sdate.substring(4) + sdate.substring(0, 4) + " " + stime;
		Date gpsTime = PengAoDaTimeFormatUtil.parseGMT(date_time);
		
		PengAoDaGPSInfo gps = new PengAoDaGPSInfo();
		gps.setTerminalId(terminalId);
		gps.setCmdId(cmdId);
		gps.setGpsTime(gpsTime);
		gps.setLoc("A".equals(sloc));
		
		double lat = PengAoDaLatLngUtil.parseUnitLat(slat, isN);
		gps.setLat(lat);
		
		double lng = PengAoDaLatLngUtil.parseUnitLng(slng, isE);
		gps.setLng(lng);
		
		BigDecimal speed;
		try{
			speed = new BigDecimal(sspeed);
			speed = speed.multiply(Constants.M_SPEED_JIE_TO_KMH);
		}catch (NumberFormatException e) {
			speed = Constants.ERROR_SPEED;
		}
		gps.setSpeed(speed.doubleValue());
		
		int course = Integer.valueOf(scourse);
		gps.setCourse(course);
		
		byte[] flag = HexUtil.hexToBytes(sstatus);
		handleFlag(gps, flag);
		
		int ialarm = Integer.valueOf(salarm, 16);
		byte alarmFlag = (byte) ialarm;
		handleAlarmFlag(gps, alarmFlag);
		
		PengAoDaBaseStation baseStation = new PengAoDaBaseStation();
		baseStation.setMcc(Integer.valueOf(smcc));
		baseStation.setMnc(Integer.valueOf(smnc));
		baseStation.setLac(Integer.valueOf(slac));
		baseStation.setCellId(Integer.valueOf(scid));
		gps.setBaseStation(baseStation);
		
		int ibattery = Integer.valueOf(sbattery, 16);
		
		int battery = ibattery & 0xF;
		gps.setBattery(battery);
		
		if((ibattery & 0x80) == 0){
			//侧翻
			//gps.getStatus().add(78);
			//gps.setAlarm(true);
		}
		
		if((ibattery & 0x40) == 0){
			//碰撞
			//gps.getStatus().add(12);
			//gps.setAlarm(true);
		}
		
		return gps;
	}
	
	public static PengAoDaGPSInfo parse(byte[] data) throws Exception{
		//*HQ,8801000297,V8,054743,V,...#
		String str = new String(data, 0, data.length - 1, PengAoDaPkgUtil.EN_CHAR_ENCODING);
		String[] params = str.split(",");
		
		return parse(params);
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
	
	private static void handleFlag(PengAoDaGPSInfo gps, byte[] flag){
		//--flag0--
		byte flag0 = flag[0];
		//4 电瓶拆除报警
		if((flag0 & STATUS_AND[4]) == 0){
			gps.getStatus().add(4);
			gps.setAlarm(true);
		}
				
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
	
	private static void handleAlarmFlag(PengAoDaGPSInfo gps, byte alarmFlag){
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
	
	public static void main(String[] args) throws Exception {
		String str = "*HQ,8801000297,V8,054743,V,2256.7686,N,11313.1467,E,000.00,000,171013,FFFFFBFF,FF,7505,460,01,9619,31503,C6#";
		//String str = "*HQ, 8856000001,V10,015358,A,2233.8843,N,11405.7480,E,000.50,000,130514,EFE7FBFF,E5,3000,460,00,9786,4192,C6,27376,0,170,168,10, 574,0,0,0# ";
		//String str = "";
	
		byte[] data = str.getBytes(PengAoDaPkgUtil.EN_CHAR_ENCODING);
		PengAoDaGPSInfo gps = parse(data);
		System.out.println("gps:" + gps);
	}
}