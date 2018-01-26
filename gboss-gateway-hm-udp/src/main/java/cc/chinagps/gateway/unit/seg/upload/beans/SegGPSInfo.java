package cc.chinagps.gateway.unit.seg.upload.beans;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cc.chinagps.gateway.StartServer;
import cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.OBDInfo;
import cc.chinagps.gateway.unit.common.Constants;
import cc.chinagps.gateway.unit.seg.pkg.SegPackage;
import cc.chinagps.gateway.unit.seg.upload.MapEntry;
import cc.chinagps.gateway.unit.seg.upload.SegUploadUtil;
import cc.chinagps.gateway.unit.seg.util.SegLatLngUtil;
import cc.chinagps.gateway.unit.seg.util.SegPkgUtil;
import cc.chinagps.gateway.unit.seg.util.SegProtobufUtil;
import cc.chinagps.gateway.unit.seg.util.SegTimeFormatUtil;
import cc.chinagps.gateway.util.HexUtil;

/**
 * GPS信息
 * 赛格车台格式,包括
 * 1) 位置信息
 * 2) 状态信息
 * 3) 附加状态
 * 4) OBD信息
 */
public class SegGPSInfo {
	private Date gpsTime;
	
	private boolean isLoc;
	
	private double lng;
	
	private double lat;
	
	private double speed;
	
	private int course;
	
	private Integer height;
	
	private Integer accTimeLen;
	
	private Long totalDistance;
	
	private Integer oilPercent;
	
	private Integer temperature1;
	
	private Integer temperature2;
	
	private boolean isAlarm;
	
	private boolean isSpecialAlarm;
	
	private Integer oil;
	
	private List<Integer> status = new ArrayList<Integer>();
	
	private List<MapEntry> extendsStatus = new ArrayList<MapEntry>();
	
	private SegOBDInfo obdInfo;
	
	private SegTravelInfo travelInfo;
	
	private List<SegBaseStationInfo> baseStationInfos = new ArrayList<SegBaseStationInfo>();
	
	private BeiJingInfo beiJingInfo =new BeiJingInfo();
	
	private int history;
	
	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getAccTimeLen() {
		return accTimeLen;
	}

	public void setAccTimeLen(Integer accTimeLen) {
		this.accTimeLen = accTimeLen;
	}

	public int getHistory() {
		return history;
	}

	public void setHistory(int history) {
		this.history = history;
	}

	public SegTravelInfo getTravelInfo() {
		return travelInfo;
	}

	public void setTravelInfo(SegTravelInfo travelInfo) {
		this.travelInfo = travelInfo;
	}

	public SegOBDInfo getObdInfo() {
		return obdInfo;
	}

	public void setObdInfo(SegOBDInfo obdInfo) {
		this.obdInfo = obdInfo;
	}
	
	public List<SegBaseStationInfo> getBaseStationInfos() {
		return baseStationInfos;
	}

	public void setBaseStationInfos(List<SegBaseStationInfo> baseStationInfos) {
		this.baseStationInfos = baseStationInfos;
	}
	
	public BeiJingInfo getBeiJingInfo() {
		return beiJingInfo;
	}

	public void setBeiJingInfo(BeiJingInfo beiJingInfo) {
		this.beiJingInfo = beiJingInfo;
	}

	public void setToSpecialAlarm(){
		isAlarm = true;
		isSpecialAlarm = true;
	}
	
	public boolean isAlarm() {
		return isAlarm;
	}

	public void setAlarm(boolean isAlarm) {
		this.isAlarm = isAlarm;
	}

	public boolean isSpecialAlarm() {
		return isSpecialAlarm;
	}

	public void setSpecialAlarm(boolean isSpecialAlarm) {
		this.isSpecialAlarm = isSpecialAlarm;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
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

	public List<Integer> getStatus() {
		return status;
	}

	//public void setStatus(List<Integer> status) {
	//	this.status = status;
	//}

	public Long getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(Long totalDistance) {
		this.totalDistance = totalDistance;
	}

	public Integer getOil() {
		return oil;
	}

	public void setOil(Integer oil) {
		this.oil = oil;
	}

	public Integer getOilPercent() {
		return oilPercent;
	}

	public void setOilPercent(Integer oilPercent) {
		this.oilPercent = oilPercent;
	}

	public Integer getTemperature1() {
		return temperature1;
	}

	public void setTemperature1(Integer temperature1) {
		this.temperature1 = temperature1;
	}

	public Integer getTemperature2() {
		return temperature2;
	}

	public void setTemperature2(Integer temperature2) {
		this.temperature2 = temperature2;
	}
	
	public List<MapEntry> getExtendsStatus() {
		return extendsStatus;
	}

	//public void setExtendsStatus(List<MapEntry> extendsStatus) {
	//	this.extendsStatus = extendsStatus;
	//}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuilder sb = new StringBuilder();
		sb.append("{gpsTime:").append(sdf.format(gpsTime));
		sb.append(", isLoc:").append(isLoc);
		sb.append(", lat:").append(lat);
		sb.append(", lng:").append(lng);
		sb.append(", speed:").append(speed);
		sb.append(", course:").append(course);
		sb.append(", totalDistance:").append(totalDistance);
		sb.append(", oilPercent:").append(oilPercent);
		sb.append(", temperature1:").append(temperature1);
		sb.append(", temperature2:").append(temperature2);
		sb.append(", status:").append(status);
		sb.append(", obdInfo:").append(obdInfo);
		sb.append(", travelInfo:").append(travelInfo);
		sb.append(", beiJingInfo:").append(beiJingInfo);
		sb.append(", baseStationInfos:").append(baseStationInfos);
		sb.append(", extendsStatus(").append(extendsStatus.size()).append("):");
		sb.append(getExtendInfo(extendsStatus));
		sb.append("}");
		
		return sb.toString();
	}
	
	private StringBuilder getExtendInfo(List<MapEntry> list){
		StringBuilder sb = new StringBuilder("[");
		for(int i = 0; i < list.size(); i++){
			MapEntry map = list.get(i);
			sb.append(map.getKey()).append(":").append(map.getValue()).append(", ");
		}
		
		if(sb.length() > 2){
			sb.delete(sb.length() - 2, sb.length());
		}
		sb.append("]");
		
		return sb;
	}
	
	//private static final BigDecimal ERROR_SPEED = BigDecimal.ZERO;
	
	public static SegGPSInfo parse(String strInfo, int startPosition) throws Exception{
		SegGPSInfo gpsInfos = new SegGPSInfo();
		String strHours = strInfo.substring(startPosition, startPosition + 2);
		String strMinus = strInfo.substring(startPosition + 2, startPosition + 4);
		String strSeconds = strInfo.substring(startPosition + 4, startPosition + 6);	
		String strLoc = strInfo.substring(startPosition + 6, startPosition + 7);
		String unitLat = strInfo.substring(startPosition + 7, startPosition + 17);
		String unitLng = strInfo.substring(startPosition + 17, startPosition + 28);
		String strSpeed = strInfo.substring(startPosition + 28, startPosition + 33);
		String strCourse = strInfo.substring(startPosition + 33, startPosition + 35);
		String strDate = strInfo.substring(startPosition + 35, startPosition + 37);
		String strMonth = strInfo.substring(startPosition + 37, startPosition + 39);
		String strYear = strInfo.substring(startPosition + 39, startPosition + 41);
		
		String strStatu1 = strInfo.substring(startPosition + 41, startPosition + 42);
		String strStatu2 = strInfo.substring(startPosition + 42, startPosition + 43);
		String strStatu3 = strInfo.substring(startPosition + 43, startPosition + 44);
		String strStatu4 = strInfo.substring(startPosition + 44, startPosition + 45);
		String strStatu5 = strInfo.substring(startPosition + 45, startPosition + 46);
		String strStatu6 = strInfo.substring(startPosition + 46, startPosition + 47);
		//状态7, 8部分车台没有
		//String strStatu7 = strInfo.substring(startPosition + 47, startPosition + 48);
		//String strStatu8 = strInfo.substring(startPosition + 48, startPosition + 49);
		//String extendStatus = strInfo.substring(startPosition + 49, strInfo.length() - 1);
		
		int currentPosition = startPosition + 47;
		char cStatu7 = strInfo.charAt(currentPosition);
		char cStatu8 = '0';
		boolean hasS7 = false;
		boolean hasS8 = false;
		if((cStatu7 >= '0' && cStatu7 <= '9') || (cStatu7 >= 'A' && cStatu7 <= 'F')){
			hasS7 = true;
			currentPosition++;
			cStatu8 = strInfo.charAt(currentPosition);
			
			if((cStatu8 >= '0' && cStatu8 <= '9') || (cStatu8 >= 'A' && cStatu8 <= 'F')){
				hasS8 = true;
				currentPosition++;
			}
		}
		String extendStatus = strInfo.endsWith(")")? strInfo.substring(currentPosition, strInfo.length() - 1): strInfo.substring(currentPosition);
		
		StringBuilder strTime = new StringBuilder();
		strTime.append("20").append(strYear).append("-");
		strTime.append(strMonth).append("-");
		strTime.append(strDate).append(" ");
		strTime.append(strHours).append(":");
		strTime.append(strMinus).append(":");
		strTime.append(strSeconds);
		Date gpsTime;
		try{
			gpsTime = SegTimeFormatUtil.parseGMT(strTime.toString());
		}catch (ParseException e) {
			gpsTime = Constants.ERROR_DATE;
		}
		
		boolean isLoc = "A".equals(strLoc);
		
		BigDecimal speed;
		try{
			speed = new BigDecimal(strSpeed);
			speed = speed.multiply(Constants.M_SPEED_JIE_TO_KMH);
		}catch (NumberFormatException e) {
			speed = Constants.ERROR_SPEED;
		}
		
		int course;
		try{
			course = Integer.valueOf(strCourse) * 10;
		}catch (NumberFormatException e) {
			course = 0;
		}
		
		gpsInfos.setGpsTime(gpsTime);
		gpsInfos.setLoc(isLoc);
		
		double userLat;
		double userLng;
		try{
			userLat = SegLatLngUtil.unitLatToUserLat(unitLat);
			userLng = SegLatLngUtil.unitLngToUserLng(unitLng);
		}catch (NumberFormatException e) {
			//经纬度无法解析时，将经纬度置为0
			userLat = 0;
			userLng = 0;
		}
		
		gpsInfos.setLat(userLat);
		gpsInfos.setLng(userLng);
		gpsInfos.setSpeed(speed.doubleValue());
		gpsInfos.setCourse(course);
		
		int status6 = Integer.valueOf(strStatu6, 16);
		analyzeStatus1(Integer.valueOf(strStatu1, 16), gpsInfos);
		analyzeStatus2(Integer.valueOf(strStatu2, 16), status6, gpsInfos);
		analyzeStatus3(Integer.valueOf(strStatu3, 16), gpsInfos);
		analyzeStatus4(Integer.valueOf(strStatu4, 16), gpsInfos);
		analyzeStatus5(Integer.valueOf(strStatu5, 16), gpsInfos);
		analyzeStatus6(status6, gpsInfos);
		//analyzeStatus7(strStatu7, gpsInfos);
		//analyzeStatus8(strStatu8, gpsInfos);
		if(hasS7){
			String strStatu7 = String.valueOf(cStatu7);
			analyzeStatus7(Integer.valueOf(strStatu7, 16), gpsInfos);
			
			if(hasS8){
				String strStatu8 = String.valueOf(cStatu8);
				analyzeStatus8(Integer.valueOf(strStatu8, 16), gpsInfos);
			}
		}
		//分离出车台型号
		int extEnd = extendStatus.lastIndexOf(')');
		if(extEnd != -1 && extEnd != extendStatus.length() - 1){
			String version = extendStatus.substring(extEnd + 1);
			extendStatus = extendStatus.substring(0, extEnd);
			gpsInfos.getExtendsStatus().add(new MapEntry("version", version));
		}
		
		analyzeExtendStatus(extendStatus, gpsInfos);
		
		return gpsInfos;
	}
	
	private static void analyzeStatus1(int v, SegGPSInfo gpsInfos){
		//int v = Integer.valueOf(status1, 16);
		if((v & 1) != 0){
			//申请医疗服务**
			gpsInfos.getStatus().add(1);
			gpsInfos.setAlarm(true);
		}
		
		if((v & 2) != 0){
			//申请故障服务**
			gpsInfos.getStatus().add(2);
			gpsInfos.setAlarm(true);
		}
		
		if((v & 4) != 0){
			//电瓶欠压**
			gpsInfos.getStatus().add(3);
			gpsInfos.setAlarm(true);
		}
		
		if((v & 8) != 0){
			//车载终端主电被切断*
			gpsInfos.getStatus().add(4);
			gpsInfos.setToSpecialAlarm();
		}
	}
	
	private static void analyzeStatus2(int v, int v6, SegGPSInfo gpsInfos){
		//int v = Integer.valueOf(status2, 16);
		if((v & 1) != 0){
			//劫警*
			gpsInfos.getStatus().add(5);
			gpsInfos.setToSpecialAlarm();
		}
		
		if((v & 2) != 0){
			//盗警*
			//gpsInfos.getStatus().add(6);
			//gpsInfos.setToSpecialAlarm();
			if((v6 & 4) != 0){
				//车辆熄火
				gpsInfos.getStatus().add(62);
			}else{
				//车辆发动
				gpsInfos.getStatus().add(63);
			}
		}
		
		if((v & 4) != 0){
			//申请信息服务**
			gpsInfos.getStatus().add(7);
			gpsInfos.setAlarm(true);
		}
		
		if((v & 8) != 0){
			//遥控报警*
			gpsInfos.getStatus().add(8);
			gpsInfos.setToSpecialAlarm();
		}
	}
	
	private static void analyzeStatus3(int v, SegGPSInfo gpsInfos){
		//int v = Integer.valueOf(status3, 16);
		if((v & 1) != 0){
			//手柄故障**
			gpsInfos.getStatus().add(9);
			gpsInfos.setAlarm(true);
		}
		
		if((v & 2) != 0){
			//GPS定位时间过长**
			gpsInfos.getStatus().add(10);
			gpsInfos.setAlarm(true);
		}
		
		if((v & 4) != 0){
			//二重密码锁报警**
			gpsInfos.getStatus().add(11);
			gpsInfos.setAlarm(true);
		}
		
		if((v & 8) != 0){
			//碰撞报警*
			gpsInfos.getStatus().add(12);
			gpsInfos.setToSpecialAlarm();
		}
	}
	
	private static void analyzeStatus4(int v, SegGPSInfo gpsInfos){
		//int v = Integer.valueOf(status4, 16);
		if((v & 1) != 0){
			//越界报警*
			gpsInfos.getStatus().add(13);
			gpsInfos.setToSpecialAlarm();
		}
		
		if((v & 2) != 0){
			//超速报警**
			gpsInfos.getStatus().add(14);
			gpsInfos.setAlarm(true);
		}
		
		if((v & 4) != 0){
			//遥控器电池电压过低
			gpsInfos.getStatus().add(15);
			gpsInfos.setAlarm(true);
		}
		
		if((v & 8) != 0){
			//GPS接收机没有输出**
			gpsInfos.getStatus().add(16);
			gpsInfos.setAlarm(true);
		}
	}
	
	private static void analyzeStatus5(int v, SegGPSInfo gpsInfos){
		//int v = Integer.valueOf(status5, 16);
		if((v & 1) != 0){
			//拖吊报警*
			gpsInfos.getStatus().add(17);
			gpsInfos.setToSpecialAlarm();
		}
		
		if((v & 2) != 0){
			//震动报警**
			gpsInfos.getStatus().add(18);
			gpsInfos.setAlarm(true);
		}
		
		if((v & 4) != 0){
			//货柜门开启**
			gpsInfos.getStatus().add(19);
			gpsInfos.setAlarm(true);
		}
		
		if((v & 8) != 0){
			//遥控器故障**
			gpsInfos.getStatus().add(20);
			gpsInfos.setAlarm(true);
		}
	}
	
	private static void analyzeStatus6(int v, SegGPSInfo gpsInfos){
		//int v = Integer.valueOf(status6, 16);
		if((v & 1) != 0){
			//车门没上锁
			gpsInfos.getStatus().add(21);
		}else{
			//车门已上锁
			gpsInfos.getStatus().add(25);
		}
		
		if((v & 2) != 0){
			//车辆设防
			gpsInfos.getStatus().add(22);
		}else{
			//车辆撤防
			gpsInfos.getStatus().add(32);
		}
		
		if((v & 4) != 0){
			//车辆熄火
			gpsInfos.getStatus().add(23);
		}else{
			//车辆发动
			gpsInfos.getStatus().add(33);
		}
		
		if((v & 8) != 0){
			//禁止时间行驶报警*
			gpsInfos.getStatus().add(24);
			gpsInfos.setToSpecialAlarm();
		}
	}
	
	private static void analyzeStatus7(int v, SegGPSInfo gpsInfos){
		//int v = Integer.valueOf(status7, 16);
		if((v & 1) != 0){
			//轮胎在缓慢泄气 54
			//改为子机断开报警 120
			//gpsInfos.getStatus().add(54);
			gpsInfos.getStatus().add(120);
		}
		
		if((v & 2) != 0){
			//胎压低
			gpsInfos.getStatus().add(55);
		}
		
		if((v & 4) != 0){
			//胎压高
			gpsInfos.getStatus().add(56);
		}
		
		if((v & 8) != 0){
			//胎温高
			gpsInfos.getStatus().add(57);
		}
	}
	
	private static void analyzeStatus8(int v, SegGPSInfo gpsInfos){
		//int v = Integer.valueOf(status8, 16);
		if((v & 1) != 0){
			//超声波报警*
			gpsInfos.getStatus().add(58);
			gpsInfos.setToSpecialAlarm();
		}
		
		if((v & 2) != 0){
			//重车
			gpsInfos.getStatus().add(59);
		}else{
			//空车
			gpsInfos.getStatus().add(60);
		}
		
		if((v & 4) != 0){
			//任务车
			gpsInfos.getStatus().add(61);
		}
		
		if((v & 8) != 0){
			//到达目的地（停留时间过长）
			gpsInfos.getStatus().add(98);
		}
	}
	
	private static void analyzeExtendStatus(String extendStatus, SegGPSInfo gpsInfos){
		int startIndex = extendStatus.indexOf('&');
		if(startIndex != 0 && startIndex != -1){
			extendStatus = extendStatus.substring(startIndex);
		}
		
		String[] statusAll = extendStatus.split("&");
		Map<String, List<String>> extTempMap = new HashMap<String, List<String>>();
		for(int i = 0; i < statusAll.length; i++){
			String status = statusAll[i];
			if(status.length() == 0){
				continue;
			}else if(status.startsWith("K")){
				//总里程
//				String hexTotalDistance = status.substring(2, 10);
//				long totalDistance = Long.valueOf(hexTotalDistance, 16);
//				//gpsInfos.getExtendsStatus().add(new MapEntry("totalDistance", String.valueOf(totalDistance)));
//				gpsInfos.setTotalDistance(totalDistance);
//				
//				if(status.length() == 21){
//					//传感器里程
//					String key = status.substring(11, 13);
//					String hexSensorTotalDistance = status.substring(13, 21);
//					long sensorTotalDistance = Long.valueOf(hexSensorTotalDistance, 16);
//					gpsInfos.getExtendsStatus().add(new MapEntry(key, String.valueOf(sensorTotalDistance)));
//				}
				String[] ks = status.split(",");
				for(int j = 0; j < ks.length; j++){
					String ksj = ks[j];
					String key = ksj.substring(0, 2);
					String value = ksj.substring(2);
					
					if(key.equals("K1")){
						//总里程
						long totalDistance = Long.valueOf(value, 16);
						gpsInfos.setTotalDistance(totalDistance);
					}else{
						gpsInfos.getExtendsStatus().add(new MapEntry(key, value));
					}
				}
			}else if(status.startsWith("T")){
				//温度
				char index = status.charAt(1);
				Double t = getTemperature(status);
				//gpsInfos.getExtendsStatus().add(new MapEntry("temperature" + index, String.valueOf(t)));
				
				if(index == '1'){	
					if(t != null){
						int temperature1 = (int) (t * 10);
						gpsInfos.setTemperature1(temperature1);
					}
				}else if(index == '2'){
					if(t != null){
						int temperature2 = (int) (t * 10);
						gpsInfos.setTemperature2(temperature2);
					}
				}else{
					gpsInfos.getExtendsStatus().add(new MapEntry("temperature" + index, String.valueOf(t)));
				}
			}else if(status.equals("Z1")){
				//开启维修模式(OBD)
				gpsInfos.getStatus().add(70);
			}else if(status.startsWith("Z05")){
				//油耗
				String percent = status.substring(11, 15);
				gpsInfos.setOilPercent(Integer.valueOf(percent, 16));
			}else if(status.startsWith("Z06")){
				//油量报警
				String percent = status.substring(23, 27);
				gpsInfos.setOilPercent(Integer.valueOf(percent, 16));
			}else if(status.startsWith("S3")){
				//ITV补充车辆信息包（OBD）
				if(status.length() == 36){
					String shourOil = status.substring(2, 6);
					String saverageOil = status.substring(6, 10);
					String stotalDistance = status.substring(10, 18);
					String sremainOil = status.substring(18, 22);
					String swaterTemperature = status.substring(22, 24);
					String sreviseOil = status.substring(24, 26);
					String srotationSpeed = status.substring(26, 30);
					String sintakeAirTemperature = status.substring(30, 32);
					String sairDischange = status.substring(32, 36);
					
					SegOBDInfo obdInfo = new SegOBDInfo();
					obdInfo.setHourOil(Integer.valueOf(shourOil, 16));
					obdInfo.setAverageOil(Integer.valueOf(saverageOil, 16));
					long totalDistance = Long.valueOf(stotalDistance, 16);
					obdInfo.setTotalDistance((int) totalDistance);
					
					int remainOil = Integer.valueOf(sremainOil, 16);
					boolean isPercent = (remainOil & 0x8000) != 0;
					int remainOilValue = remainOil & 0x7FFF;
					obdInfo.setOilPercent(isPercent);
					obdInfo.setRemainOil(remainOilValue);
					
					int waterTemperature = Integer.valueOf(swaterTemperature, 16) - 0x28;
					obdInfo.setWaterTemperature(waterTemperature);
					
					int reviseOil = Integer.valueOf(sreviseOil, 16);
					boolean isNegative = (reviseOil & 0x80) != 0;
					int reviseValue = reviseOil & 0x7F;
					if(isNegative){
						reviseValue = -reviseValue;
					}
					obdInfo.setReviseOil(reviseValue);
					
					int rotationSpeed = Integer.valueOf(srotationSpeed, 16);
					obdInfo.setRotationSpeed(rotationSpeed);
					
					int intakeAirTemperature = Integer.valueOf(sintakeAirTemperature, 16) - 0x28;
					obdInfo.setIntakeAirTemperature(intakeAirTemperature);
					
					int airDischange = Integer.valueOf(sairDischange, 16);
					obdInfo.setAirDischange(airDischange);
					
					gpsInfos.setObdInfo(obdInfo);
				}
			}else if(status.startsWith("S5")){
				String send = status.substring(2, 14);
				String sstart = status.substring(14, 26);		
				String sdistance = status.substring(26, 30);
				String smaxSpeed = status.substring(30, 32);
				String soverSpeedTime = status.substring(32, 36);
				String squickBrakeCount = status.substring(36, 40);
				String semergencyBrakeCount = status.substring(40, 44);				
				String squickSpeedUpCount = status.substring(44, 48);
				String semergencySpeedUpCount = status.substring(48, 52);
				String saverageSpeed = status.substring(52, 54);
				String smaxWaterTemperature = status.substring(54, 56);
				String smaxRotationSpeed = status.substring(56, 60);
				String svoltage = status.substring(60, 62);
				String stotalOil = status.substring(62, 66);
				String saverageOil = status.substring(66, 70);
				String stiredDrivingTime = status.substring(70, 74);
				String saverageRotationSpeed = status.substring(74, 78);
				String smaxOil = status.substring(78, 82);
				String sidleTime = status.substring(82, 86);
				
				SegTravelInfo travelInfo = new SegTravelInfo();
				Date endTime, startTime;
				try {
					endTime = SegTimeFormatUtil.parseGMTBCD("20" + send);
				} catch (ParseException e) {
					endTime = Constants.ERROR_DATE;
				}
				
				try {
					startTime = SegTimeFormatUtil.parseGMTBCD("20" + sstart);
				} catch (ParseException e) {
					startTime = Constants.ERROR_DATE;
				}
				
				travelInfo.setEndTime(endTime);
				travelInfo.setStartTime(startTime);
				travelInfo.setDistance(Integer.valueOf(sdistance, 16));
				travelInfo.setMaxSpeed(Integer.valueOf(smaxSpeed, 16));
				travelInfo.setOverSpeedTime(Integer.valueOf(soverSpeedTime, 16));
				travelInfo.setQuickBrakeCount(Integer.valueOf(squickBrakeCount, 16));
				travelInfo.setEmergencyBrakeCount(Integer.valueOf(semergencyBrakeCount, 16));
				travelInfo.setQuickSpeedUpCount(Integer.valueOf(squickSpeedUpCount, 16));
				travelInfo.setEmergencySpeedUpCount(Integer.valueOf(semergencySpeedUpCount, 16));
				travelInfo.setAverageSpeed(Integer.valueOf(saverageSpeed, 16));
				travelInfo.setMaxWaterTemperature(Integer.valueOf(smaxWaterTemperature, 16));
				travelInfo.setMaxRotationSpeed(Integer.valueOf(smaxRotationSpeed, 16));
				travelInfo.setVoltage(Integer.valueOf(svoltage, 16));
				travelInfo.setTotalOil(Integer.valueOf(stotalOil, 16));
				travelInfo.setAverageOil(Integer.valueOf(saverageOil, 16));
				travelInfo.setTiredDrivingTime(Integer.valueOf(stiredDrivingTime, 16));
				travelInfo.setAverageRotationSpeed(Integer.valueOf(saverageRotationSpeed, 16));
				travelInfo.setMaxOil(Integer.valueOf(smaxOil, 16));
				travelInfo.setIdleTime(Integer.valueOf(sidleTime, 16));
				
				gpsInfos.setTravelInfo(travelInfo);
			}else if(status.startsWith("ZA")){
				//上报子机状态
				String zaExtStatus = status.substring(2);
				/*if(zaExtStatus.length()!=8)
					continue;*/
				zaExtStatus = zaExtStatus.substring(0, 11);
				String trgStatusArr[] = zaExtStatus.split(",");
				String value="";
				for (String trgStatus : trgStatusArr) {
					value += trgStatus.substring(1);
				}
				MapEntry mapEntry = new MapEntry("childunit", value);
				gpsInfos.getExtendsStatus().add(mapEntry);
			}else if(status.startsWith("P")){
				//海拔
				String height = status.substring(1);
				gpsInfos.setHeight(Integer.valueOf(height));
			}else if(status.startsWith("Q")){
				//卫星数
				String satellitecount = status.substring(1);
				gpsInfos.getBeiJingInfo().setSatellitecount(Integer.valueOf(satellitecount));
			}else if(status.startsWith("R")){
				//rfid
				String rfid = status.substring(1);
				gpsInfos.getBeiJingInfo().setRfid(Integer.valueOf(rfid));
			}else if(status.startsWith("Z7")){
				//GSM信号强度
				String gsmvalue = status.substring(2);
				gpsInfos.getBeiJingInfo().setGsmvalue(Integer.valueOf(gsmvalue));
			}else if(status.startsWith("Z9")){
				//电池电压值
				String voltage = status.substring(2);
				gpsInfos.getBeiJingInfo().setVoltage(Integer.valueOf(voltage));
			}else if(status.startsWith("A")){
				//acc on or off total time
				String timeLen = status.substring(1);
				gpsInfos.setAccTimeLen(Integer.valueOf(timeLen,16));
			}else if(status.startsWith("H")){
				String content[] = status.split(",");
				int contentNum = content.length;
				
				if(contentNum > 0){
					String totalDistance = content[0].substring(1, 7);//总里程（千米）
					//long totalDistance = Long.valueOf(value, 16);
					long distance = Long.valueOf(totalDistance);
					gpsInfos.setTotalDistance(distance);
				}
				
				if(contentNum > 1){
					String electricity = content[1];//电量百分比(百分之一)
					gpsInfos.getBeiJingInfo().setElectricity(Integer.valueOf(electricity));
				}
					
				if(contentNum > 2){
					String mileage = content[2];
					gpsInfos.getBeiJingInfo().setMileage(Integer.valueOf(mileage));
				}
				
				if(contentNum > 3){
					String chargeStatus = content[3];
					gpsInfos.getBeiJingInfo().setChargeStatus(chargeStatus);
				}
				
				if(contentNum > 4){
					String fillGunStatus= content[4]; // 充枪连接状态 0 半连接 1未连接 2 已连接
					gpsInfos.getBeiJingInfo().setFillGunStatus(fillGunStatus);
				}
				
				
				if (contentNum > 5) {
					String accStatus= content[5];// ACC状态 0 ACC关 1 ACC开
					gpsInfos.getBeiJingInfo().setAccStatus(accStatus);
				}
				
				if (contentNum > 6) {
					String oilStatus= content[6];// 油路状态 0 闭合 1 断开
					gpsInfos.getBeiJingInfo().setOilStatus(oilStatus);
				}
				
				if (contentNum > 7) {
					String defendStatus= content[7];// 防盗状态 0 撤防 1设防
					gpsInfos.getBeiJingInfo().setDefendStatus(defendStatus);
				}
				
				if (contentNum > 8) {
					String centralLockStatus= content[8];// 中控锁状态 0未锁 1锁上
					gpsInfos.getBeiJingInfo().setCentralLockStatus(centralLockStatus);
				}
				
				if (contentNum > 9) {
					String doorStatus= content[9];// 车门状态，第一个字节为驾驶员门，第二个字节为副驾驶员门，第三个字节为驾驶员后门，第四个字节为副驾驶员后门，第五个为后备箱门
					// 0未关 1 关
					gpsInfos.getBeiJingInfo().setDoorStatus(doorStatus);
				}
				
				if (contentNum > 10) {
					String lightStatus= content[10];// 车灯状态 第一个字节为小灯，第二个字节为大灯，第三个字节为后雾灯，第四个字节为前雾灯
					// 0 灭 1亮
					gpsInfos.getBeiJingInfo().setLightStatus(lightStatus);
				}
				
				if (contentNum > 11) {
					String windowStatus= content[11];// 车窗状态 0 全关好1 未关好
					gpsInfos.getBeiJingInfo().setWindowStatus(windowStatus);
				}
				
			}else if(status.startsWith("J")){
				//基站信息
				String content[] = status.split(",");
				int contentNum = content.length;
				String mcc = null;
				String mnc = null;
				int mccI = 0;
				int mncI = 0;
				String lac = null;
				String cellId = null;
				String bsss = null;
				SegBaseStationInfo baseStationInfo = new SegBaseStationInfo();
				if (contentNum > 0) {
					String electricity = content[0].substring(1, 4);
					gpsInfos.getBeiJingInfo().setElectricity(Integer.valueOf(electricity));
					mcc = content[0].substring(4, 7);
					mnc = content[0].substring(7, 9);
					mccI = Integer.valueOf(mcc);
					mncI = Integer.valueOf(mnc);
					baseStationInfo.setMnc(mncI);
					baseStationInfo.setMcc(mccI);
				}

				if (contentNum > 3) {
					lac = content[1];
					cellId = content[2];
					bsss = content[3];
					baseStationInfo.setCellId(Integer.valueOf(cellId));
					baseStationInfo.setLac(Integer.valueOf(lac));
					baseStationInfo.setDbm(Integer.valueOf(bsss));
					gpsInfos.getBaseStationInfos().add(baseStationInfo);
				}

				// 第1个附近基站信息（最多6个）
				if (contentNum > 7) {
					baseStationInfo = new SegBaseStationInfo(mccI, mncI);
					lac = content[5];
					cellId = content[6];
					bsss = content[7];
					baseStationInfo.setLac(Integer.valueOf(lac));
					baseStationInfo.setCellId(Integer.valueOf(cellId));
					baseStationInfo.setDbm(Integer.valueOf(bsss));
					gpsInfos.getBaseStationInfos().add(baseStationInfo);

				}
				// 第2个附近基站信息
				if (contentNum > 10) {
					baseStationInfo = new SegBaseStationInfo(mccI, mncI);
					lac = content[8];
					cellId = content[9];
					bsss = content[10];
					baseStationInfo.setLac(Integer.valueOf(lac));
					baseStationInfo.setCellId(Integer.valueOf(cellId));
					baseStationInfo.setDbm(Integer.valueOf(bsss));
					gpsInfos.getBaseStationInfos().add(baseStationInfo);

				}

				// 第3个附近基站信息
				if (contentNum > 13) {
					baseStationInfo = new SegBaseStationInfo(mccI, mncI);
					lac = content[11];
					cellId = content[12];
					bsss = content[13];
					baseStationInfo.setLac(Integer.valueOf(lac));
					baseStationInfo.setCellId(Integer.valueOf(cellId));
					baseStationInfo.setDbm(Integer.valueOf(bsss));
					gpsInfos.getBaseStationInfos().add(baseStationInfo);

				}

				// 第4个附近基站信息
				if (contentNum > 16) {
					baseStationInfo = new SegBaseStationInfo(mccI, mncI);
					lac = content[13];
					cellId = content[14];
					bsss = content[15];
					baseStationInfo.setLac(Integer.valueOf(lac));
					baseStationInfo.setCellId(Integer.valueOf(cellId));
					baseStationInfo.setDbm(Integer.valueOf(bsss));
					gpsInfos.getBaseStationInfos().add(baseStationInfo);

				}

				// 第5个附近基站信息
				if (contentNum > 19) {
					baseStationInfo = new SegBaseStationInfo(mccI, mncI);
					lac = content[16];
					cellId = content[17];
					bsss = content[18];
					baseStationInfo.setLac(Integer.valueOf(lac));
					baseStationInfo.setCellId(Integer.valueOf(cellId));
					baseStationInfo.setDbm(Integer.valueOf(bsss));
					gpsInfos.getBaseStationInfos().add(baseStationInfo);
				}

				// 第6个附近基站信息
				if (contentNum > 22) {
					baseStationInfo = new SegBaseStationInfo(mccI, mncI);
					lac = content[19];
					cellId = content[20];
					bsss = content[21];
					baseStationInfo.setLac(Integer.valueOf(lac));
					baseStationInfo.setCellId(Integer.valueOf(cellId));
					baseStationInfo.setDbm(Integer.valueOf(bsss));
					gpsInfos.getBaseStationInfos().add(baseStationInfo);
				}
				
			}else{
//				if(status.length() > 2){
//					String key = status.substring(0, 2);
//					String value = status.substring(2);
//					gpsInfos.getExtendsStatus().add(new MapEntry(key, value));
//				}
				if(status.length() > 1){
					String key = status.substring(0, 1);
					String value = status.substring(1);
					
					List<String> list = extTempMap.get(key);
					if(list == null){
						list = new ArrayList<String>();
						extTempMap.put(key, list);
					}
					
					list.add(value);
				}
			}
		}
		
		//add
		Iterator<Entry<String, List<String>>> it = extTempMap.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, List<String>> entry = it.next();
			List<String> l = entry.getValue();
			String value = l.size() == 1? l.get(0): l.toString();
			MapEntry mapEntry = new MapEntry(entry.getKey(), value);
			gpsInfos.getExtendsStatus().add(mapEntry);
		}
	}
	
	private static Double getTemperature(String status){
		char flag = status.charAt(2);
		if(flag == '?'){
			return null;
		}
		
		int value = Integer.valueOf(status.substring(3));
		if(flag == '+'){
			return (double) value;
		}
		
		if(flag == '-'){
			return -value / 10.0;
		}
		
		return null;
	}
	
//	private static Date hexToTime(String hexStr){
//		String sYear = hexStr.substring(0, 2);
//		String sMonth = hexStr.substring(2, 4);
//		String sDate = hexStr.substring(4, 6);
//		String sHour = hexStr.substring(6, 8);
//		String sMinutes = hexStr.substring(8, 10);
//		String sSeconds = hexStr.substring(10, 12);
//
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTimeZone(SegPkgUtil.gmt_timezone);
//		
//		calendar.set(Calendar.YEAR, Integer.valueOf(sYear, 16) + 2000);
//		calendar.set(Calendar.MONTH, Integer.valueOf(sMonth, 16)  - 1);
//		calendar.set(Calendar.DATE, Integer.valueOf(sDate, 16));
//		calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(sHour, 16));
//		calendar.set(Calendar.MINUTE, Integer.valueOf(sMinutes, 16));
//		calendar.set(Calendar.SECOND, Integer.valueOf(sSeconds, 16));
//		
//		return calendar.getTime();
//	}
	
	/**
	 * 解析行车记录的GPS信息
	 * @throws ParseException 
	 */
	public static SegGPSInfo parseGPSfromHis(byte[] data, int offset) throws ParseException{
		int position = offset;
		int year = 2003 + ((data[position] >> 4) & 0xF);
		int month = data[position] & 0xF;
		int date = data[position + 1];
		int hours = data[position + 2];
		int minutes = data[position + 3];
		int seconds = data[position + 4];
		
		int lat_d = data[position + 5];
		int lat_minutes_int = data[position + 6];
		int lat_minutes_12 = data[position + 7];
		int lat_minutes_34 = data[position + 8];
		
		int lng_d = data[position + 9];
		int lng_minutes_int = data[position + 10];
		int lng_minutes_12 = data[position + 11];
		int lng_minutes_34 = data[position + 12];
		
		int course = 10 * data[position + 13];
		
		int speed_int = data[position + 14];
		int speed_float = (data[position + 15] >> 4) & 0xF;
		
		int lat_flag = (data[position + 15] >> 3) & 0x1;
		int lng_flag = (data[position + 15] >> 2) & 0x1;
		int loc_flag = (data[position + 15] >> 1) & 0x1;
		
		byte status1 = data[position + 16];
		byte status2 = data[position + 17];
		byte status3 = data[position + 18];
		byte status4 = data[position + 19];
		
		StringBuilder strTime = new StringBuilder();
		strTime.append(year).append("-");
		strTime.append(month).append("-");
		strTime.append(date).append(" ");
		strTime.append(hours).append(":");
		strTime.append(minutes).append(":");
		strTime.append(seconds);
		Date gpsTime = SegTimeFormatUtil.parseGMT(strTime.toString());
		boolean isLoc = loc_flag == 1;
		
		BigDecimal speed = new BigDecimal(speed_int + "." + speed_float);
		speed = speed.multiply(Constants.M_SPEED_JIE_TO_KMH);
		
		double lat = getLatlng(lat_flag, lat_d, lat_minutes_int, lat_minutes_12, lat_minutes_34);
		double lng = getLatlng(lng_flag, lng_d, lng_minutes_int, lng_minutes_12, lng_minutes_34);
		
		SegGPSInfo gpsInfos = new SegGPSInfo();
		gpsInfos.setHistory(1);
		gpsInfos.setGpsTime(gpsTime);
		gpsInfos.setLoc(isLoc);
		gpsInfos.setLat(lat);
		gpsInfos.setLng(lng);
		gpsInfos.setSpeed(speed.doubleValue());
		gpsInfos.setCourse(course);
		
		int v6 = status3 & 0xF;
		analyzeStatus1((status1 >> 4) & 0xF, gpsInfos);
		analyzeStatus2(status1 & 0xF, v6, gpsInfos);
		
		analyzeStatus3((status2 >> 4) & 0xF, gpsInfos);
		analyzeStatus4(status2 & 0xF, gpsInfos);
		
		analyzeStatus5((status3 >> 4) & 0xF, gpsInfos);
		analyzeStatus6(v6, gpsInfos);
		
		analyzeStatus7((status4 >> 4) & 0xF, gpsInfos);
		analyzeStatus8(status4 & 0xF, gpsInfos);
		
		return gpsInfos;
	}
	
	private static double getLatlng(int flag, int du, int fen, int fen_12, int fen_34){
		StringBuilder fen_float = new StringBuilder();
		fen_float.append(fen_12).append(fen_34);
		double latlng = SegLatLngUtil.unitLatLngToUserLatLng(du, fen, fen_float.toString());
		
		return flag == 1? latlng: -latlng;
	}
	
	public static void main(String[] args) {
		
		
 String s ="5b93303130303130303731344c2849545630393334323141323234302e393839324e31313431362e36383130453030302e3430303232303431363030303030303030264b3130303038424244412c4b353030303030303030295d";
		
		s ="5b9330303030303030303030642849545630393539313341323234302e373636344e31313431362e37363730453030302e3033333232303431363030303030303030264a3130303436303030264b3130303030303531412c4b353030303030303030265a4131312c32302c33302c3430295d";
		
		s ="5b90303030303030303030305f284f4e4531303038343541323234302e383634304e31313431362e37353131453030302e3030333232303431363030303030303030264a3130303436303030264b3130303030303630412c4b35303030303030303029545a4d3330563130305d";
		
		s ="5b703031303031303030303136284f4e4531303231323841323234302e393934374e31313431362e36373931453030302e3030303232303431363830303030363030295d";
		
		s = "5b9330303030303030303030a92849545630343139353956333935322e333638374e31313632372e32353436453030302e303030323430363136303030303034303026513530300000353030264a31303034363030302c343238342c31313431322c34382c3235352c343238342c31333536332c34372c343238342c373737322c34362c343239302c35323339312c33302c343238342c31313431312c3238264b3130303030303439332c4b353030303030303030295d";
		
		s = "5b9330303030303030303030602849545630373338303141333935322e333633354e31313632372e32363938453030302e303030323430363136303030303030303026513530300000353030264a3130303436303030264b3130303030313239352c4b353030303030303030295d";
		
		s = "5b9330303030303030303030642849545630353432333241323234302e393833334e31313431362e36383531453030302e3032303038303731363030303030303030264a3130303436303030264b3130303030303637362c4b353030303030303030265a4131312c32302c33302c3430295d";
		
		s = "5bb2303030303030303030300b28464e532c43544c2c31295d";
		
		s = "5bb2303030303030303030305f28464e532c43544c2c313130383134303241323234302e393537384e31313431362e36383736453030302e3030303031303831362c26483030303030302c3030302c3030302c302c302c302c302c302c302c30303030302c303030302c30295d";
		
		s = "5bb2303030303030303030305e28464e532c43544c2c3130383335313541323234302e393630314e31313431362e36393232453030302e3030303031303831362c26483030303030302c3030302c3030302c302c302c302c302c302c302c30303030302c303030302c30295d";
		
		s = "5bb2303030303030303030306528464e532c43544c2c3130393130343741323234302e393635314e31313431362e36373039453030302e303030303130383136303030303030303026483030303030302c3030302c3030302c302c302c302c302c302c302c30303030302c303030302c30295d";
		
		s = "5b9330303030303030303030602849545630393132333841323331352e383530364e31313334382e34303935453030302e303030303130383136303030303030303026503530302651303035264a3130303436303030264b3130303044383132372c4b353030303030303030295d";
		
		s = "5b93303030303030303030305f2849545630333135303441323331352e383335314e31313334382e34313031453030302e303030303630383136303030303030303026483030313030382c3035322c3131332c302c302c312c302c302c302c31303030302c303030302c30295d";
		
		s = "5bb2303030303030303030306528464e532c43544c2c3130333231353941323331352e383336344e31313334382e34313033453030302e303030303630383136303030303030303026483030313030382c3035322c3131302c302c302c312c302c302c302c31313131302c303030302c30295d";
		
		s = "5b9330303030303030303030752849545630383339323441323331352e383430394e31313334382e34313037453030302e303030303630383136303030303030303026503530302651303130265a37303239265a3933333126483030313030382c3034302c3038372c302c302c312c302c302c302c31303030302c303030302c30295d";
		
		s = "5b93303030303030303030306d2849545631343438323441323833392e343934304e31313535322e36343136453030302e3030393132303831363030303030303030264a3130303436303030264b3130303030333242342c4b353030303030303030264130303030323243265a4131322c32302c33302c3430295d";
		
		s = "5b 93 30 30 30 30 30 30 30 30 32 35 9f 28 49 54 56 30 36 35 38 31 31 41 32 32 33 36 2e 39 30 31 39 4e 31 31 34 30 33 2e 32 33 36 38 45 30 30 30 2e 30 30 30 32 37 31 30 31 36 30 30 30 30 30 30 30 30 26 4b 31 30 30 30 30 30 30 30 30 2c 4b 35 30 30 30 30 30 30 30 30 26 4a 30 39 39 34 36 30 30 30 2c 39 37 31 32 2c 33 35 38 32 2c 31 38 33 2c 38 38 2c 33 2c 31 30 31 37 30 2c 33 38 37 32 2c 31 38 35 2c 38 38 2c 31 30 31 37 30 2c 33 37 32 32 2c 31 38 31 2c 38 38 2c 31 30 31 37 30 2c 33 39 32 33 2c 31 37 39 2c 38 38 29 5d";
		
		//s = "5b 93 30 30 30 30 30 30 30 30 32 36 9f 28 49 54 56 30 36 35 39 31 31 41 32 32 33 36 2e 39 30 31 39 4e 31 31 34 30 33 2e 32 33 36 38 45 30 30 30 2e 30 30 30 32 37 31 30 31 36 30 30 30 30 30 30 30 30 26 4b 31 30 30 30 30 30 30 30 30 2c 4b 35 30 30 30 30 30 30 30 30 26 4a 30 39 39 34 36 30 30 30 2c 39 37 31 32 2c 33 35 38 32 2c 31 38 32 2c 38 38 2c 33 2c 31 30 31 37 30 2c 33 38 37 32 2c 31 38 35 2c 38 38 2c 31 30 31 37 30 2c 33 39 32 33 2c 31 38 31 2c 38 38 2c 31 30 31 37 30 2c 33 37 32 32 2c 31 37 39 2c 38 38 29 5d";
		
		s = "5b9330313030313030363233952849545630323036333641333930352e383038314e31323134332e30363939453030302e3230303035303131373030303030303030264b3130303032373632372c4b353030303030303030264a30393834363030302c31363635382c35303135312c33322c37352c31363635382c35303135352c32302c31363635382c34323239322c31392c31363635382c35303538312c3135295d";
		
		s = "5B903030303030303030303043284F4E4530393237333741323330362E363430394E31313331312E39363330453030332E39313130323033313730303030303030302954475339352D323041563130305D";
		
		s = "5b93303030303030303030307b2849545630363037303056323735322e363832364e31323034382e37323039453030302e3030303134303431373030303030343030265a3934350000003031342651303030265a37303235265a3930313426483030303436352c3032322c3034302c302c302c302c302c302c302c31313030302c303030302c30295d";
		
		s = "5b9330313030313030303031362849545630323538353741323233322e303732394e31313335362e30303330453030302e3032313039303431373030303030303030295d";
		
		s = "5b9330313030313030303031362849545630323538353741323233322e303732394e31313335362e30303330453030302e3032313039303431373030303030303030295d";
		try {
			byte data[] = HexUtil.hexToBytes(s);
			SegPackage pkg = SegPackage.parse(data);
			String strBody = new String(pkg.getBody(), SegPkgUtil.PKG_CHAR_ENCODING);
			System.out.println("strBody:"+strBody);
			SegGPSInfo gps = SegGPSInfo.parse(strBody, 4);
			//SegGPSInfo gps = SegGPSInfo.parse(strBody, 10);
			/*System.out.println(SegUploadUtil.statusListToStr(gps.getStatus()));
			strBody = "&P002&Q003&R004&Z7001&Z9009&J08546001,001,02,03,04,002,002,002,003,003,003";
			gps=new SegGPSInfo();
			analyzeExtendStatus(strBody, gps);
			System.out.println(gps);*/
			System.out.println(gps);
			
			/*String callLetter = "13435642743";
			GpsInfo gpsInfo = SegProtobufUtil.transformGPSInfo(callLetter, gps, pkg);
			GpsBaseInfo gpsBase = gpsInfo.getBaseInfo();
			StartServer.instance.start();
			StartServer.instance.getUnitServer().getExportMQ().addGPS(gpsInfo);
			StartServer.instance.getUnitServer().getExportHBase().addGPS(callLetter, gpsBase, pkg.getSource());
			
			//车况信息
			if(gpsBase.hasObdInfo()){
				OBDInfo obdInfo = gpsBase.getObdInfo();
				StartServer.instance.getUnitServer().getExportMQ().addOBDInfo(obdInfo);
			}		*/	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}