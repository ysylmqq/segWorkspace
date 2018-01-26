package cc.chinagps.gateway.unit.seg.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.common.Constants;
import cc.chinagps.gateway.unit.seg.pkg.SegPackage;
import cc.chinagps.gateway.unit.seg.upload.beans.SegGPSInfo;
import cc.chinagps.gateway.util.HexUtil;

public class SegPkgUtil {
	public static final String PKG_CHAR_ENCODING = "ascii";
	
	public static final byte START_FLAG = 0x5B;
	public static final byte END_FLAG = 0x5D;
	
	private static final byte flagB = 0x5B;
	private static final byte flagC = 0x5C;
	private static final byte flagD = 0x5D;
	
	/**
	 * 老车台转义,转义后长度增加
	 */
	public static byte[] oldEscape(byte[] data){
		List<Integer> positions = new ArrayList<Integer>();
		for(int i = 12; i < data.length - 1; i++){
			//从长度开始
			if(data[i] == flagB || data[i] == flagC || data[i] == flagD){
				positions.add(i);
			}
		}
		
		if(positions.size() == 0){
			return data;
		}
		
		byte[] copy = new byte[data.length + positions.size()];
		int srcPos = 0;
		int tarPos = 0;
		int copyLength;
		for(int i = 0; i < positions.size(); i++){
			int espos = positions.get(i);
			copyLength = espos - srcPos;
			System.arraycopy(data, srcPos, copy, tarPos, copyLength);
			
			srcPos = espos + 1;
			tarPos += copyLength;
			copy[tarPos] = 0x5C;
			copy[tarPos + 1] = (byte) (0x50 ^ data[espos]);
			tarPos += 2;
		}
		
		if(srcPos < data.length){
			copyLength = data.length - srcPos;
			System.arraycopy(data, srcPos, copy, tarPos, copyLength);
		}
		
		//增加长度
		if(data.length > 12){
			byte len = data[12];
			copy[12] = (byte) (len + positions.size());
		}
		
		return copy;
	
	}
	
	/**
	 * 转义
	 */
	public static byte[] escape(byte[] data){
		List<Integer> positions = new ArrayList<Integer>();
		for(int i = 1; i < data.length - 1; i++){
			if(data[i] == flagB || data[i] == flagC || data[i] == flagD){
				positions.add(i);
			}
		}
		
		if(positions.size() == 0){
			return data;
		}
		
		byte[] copy = new byte[data.length + positions.size()];
		int srcPos = 0;
		int tarPos = 0;
		int copyLength;
		for(int i = 0; i < positions.size(); i++){
			int espos = positions.get(i);
			copyLength = espos - srcPos;
			System.arraycopy(data, srcPos, copy, tarPos, copyLength);
			
			srcPos = espos + 1;
			tarPos += copyLength;
			copy[tarPos] = 0x5C;
			copy[tarPos + 1] = (byte) (0x50 ^ data[espos]);
			tarPos += 2;
		}
		
		if(srcPos < data.length){
			copyLength = data.length - srcPos;
			System.arraycopy(data, srcPos, copy, tarPos, copyLength);
		}
		
		return copy;
	}
	
	/**
	 * 反转义
	 */
	private static boolean isEscapeCode(byte c){
		return (c == 0xB || c == 0xC || c == 0xD);
	}
	
	public static byte[] unescape(byte[] data){
		List<Integer> positions = new ArrayList<Integer>();
		for(int i = 1; i < data.length - 1; i++){
			if(data[i] == flagC && isEscapeCode(data[i + 1])){
				positions.add(i);
			}
		}
		
		if(positions.size() == 0){
			return data;
		}
		
		byte[] copy = new byte[data.length - positions.size()];
		
		int srcPos = 0;
		int tarPos = 0;
		int copyLength;
		for(int i = 0; i < positions.size(); i++){
			int uespos = positions.get(i);
			copyLength = uespos - srcPos;
			System.arraycopy(data, srcPos, copy, tarPos, copyLength);
			
			srcPos = uespos + 2;
			tarPos += copyLength;
			copy[tarPos] = (byte) (0x50 ^ data[uespos + 1]);
			tarPos += 1;
		}
		
		if(srcPos < data.length){
			copyLength = data.length - srcPos;
			System.arraycopy(data, srcPos, copy, tarPos, copyLength);
		}
		
		return copy;
	}
	
//	/**
//	 * 上行数据byte数组转成对象格式
//	 */
//	public static SegPackage byteToPackage(byte[] bs) throws Exception{
//		SegPackage pkg = new SegPackage();
//		pkg.setStart(bs[0]);
//		pkg.setType(bs[1]);
//		byte[] serialNumberBytes = new byte[10];
//		System.arraycopy(bs, 2, serialNumberBytes, 0, 10);
//		pkg.setSerialNumberBytes(serialNumberBytes);
//		pkg.setSerialNumber(new String(serialNumberBytes));
//		int len = bs[12] & 0xFF;
//		if(len != bs.length - 14){
//			throw new Exception("data len:" + bs.length + ", len in data:" + len);
//		}
//		pkg.setLength(len);
//		byte[] body = new byte[len];
//		System.arraycopy(bs, 13, body, 0, len);
//		pkg.setBody(body);
//		pkg.setEnd(bs[bs.length - 1]);
//		
//		return pkg;
//	}
	
	/**
	 * 下行数据,根据cmdId, sn和消息体转成字节数组
	 */
	public static byte[] encode(byte cmdId, String sn, byte[] body){
		return encode(cmdId, sn.getBytes(), body);
	}
	
	/**
	 * 下行数据,根据cmdId, sn和消息体转成字节数组
	 */
	public static byte[] encode(byte cmdId, byte[] snBytes, byte[] body){
		byte[] common = encodeCommon(cmdId, snBytes, body);
		
		//转义
		byte[] escape =  SegPkgUtil.escape(common);
		return escape;
	}
	
	/**
	 * 按旧转义格式转义
	 */
	public static byte[] encodeOld(byte cmdId, String sn, byte[] body){
		return encodeOld(cmdId, sn.getBytes(), body);
	}
	
	/**
	 * 按旧转义格式转义
	 */
	public static byte[] encodeOld(byte cmdId, byte[] snBytes, byte[] body){
		byte[] common = encodeCommon(cmdId, snBytes, body);
		
		//转义
		byte[] escape =  SegPkgUtil.oldEscape(common);
		return escape;
	}
	
	/**
	 * 未转义
	 */
	private static byte[] encodeCommon(byte cmdId, byte[] snBytes, byte[] body){
		byte[] response = new byte[14 + body.length];
		response[0] = SegPkgUtil.START_FLAG;
		response[1] = cmdId;
		System.arraycopy(snBytes, 0, response, 2, 10);
		response[12] = (byte) body.length;
		System.arraycopy(body, 0, response, 13, body.length);
		response[response.length - 1] = SegPkgUtil.END_FLAG;
		
		return response;
	}
	
	//private static int VALUE = 0;
//	private static final int MAX_VALUE;
//	public static final String SYSTEM_ID;
//	
//	static{
//		SYSTEM_ID = SystemConfig.getSystemProperty("system_id");
//		if(SYSTEM_ID.length() >= 7){
//			throw new RuntimeException("error SYSTEM_ID:" + SYSTEM_ID);
//		}
//		
//		int length = 10 - SYSTEM_ID.length();
//		MAX_VALUE = (int) (Math.pow(10, length) - 1);
//	}
	
	/**
	 * 生成发给车台的sn
	 */
	public static synchronized String getsn(){
		return "0000000000";
//		VALUE ++;
//		if(VALUE > MAX_VALUE){
//			VALUE = 0;
//		}
//		
//		StringBuilder sb = new StringBuilder(10);
//		sb.append(SYSTEM_ID).append(appendZero(VALUE, MAX_VALUE));
//		return sb.toString();
	}
	
//	private static String appendZero(int v, int maxValue){
//		String s = String.valueOf(v);
//		int strLength = String.valueOf(maxValue).length();
//		int appendCount = strLength - s.length();
//		StringBuilder sb = new StringBuilder();
//		for(int i = 0; i < appendCount; i++){
//			sb.append("0");
//		}
//		sb.append(s);
//		
//		return sb.toString();
//	}
	
//	public static final TimeZone gmt_timezone;
//	private static final SimpleDateFormat sdf_gmt;
//	private static final SimpleDateFormat sdf_local = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	public static final int SCALE = 6;
	public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
//	public static final BigDecimal D60 = new BigDecimal(60);
//	public static final BigDecimal M_SPEED = new BigDecimal("1.852");
//	static{
//		gmt_timezone = TimeZone.getTimeZone("GMT");
//		sdf_gmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		sdf_gmt.setTimeZone(gmt_timezone);
//	}
//	
//	public static Date parseGMT(String time) throws ParseException{
//		synchronized (sdf_gmt) {
//			return sdf_gmt.parse(time);
//		}
//	}
//	
//	public static String formatToLocal(Date date){
//		synchronized (sdf_local) {
//			return sdf_local.format(date);
//		}
//	}

//	/**
//	 * 用户输入的纬度转换成发给车台的纬度
//	 */
//	public static String userLatToUnitLat(String lat){
//		char flag;
//		BigDecimal dec_lat = new BigDecimal(lat);
//		if(dec_lat.compareTo(BigDecimal.ZERO) < 0){
//			flag = 'S';
//			dec_lat = dec_lat.negate();
//		}else{
//			flag = 'N';
//		}
//		
//		int lat_int = dec_lat.intValue();
//		
//		BigDecimal lat_float = dec_lat.subtract(new BigDecimal(lat_int));
//		BigDecimal lat_minus = lat_float.multiply(new BigDecimal(60));
//		
//		int lat_minus_int = lat_minus.intValue();
//		BigDecimal lat_minus_float = lat_minus.subtract(new BigDecimal(lat_minus_int));
//		BigDecimal lat_minus_float_mul = lat_minus_float.multiply(new BigDecimal(10000));
//		BigDecimal lat_minus_float_int = lat_minus_float_mul.setScale(0, RoundingMode.HALF_UP);
//		
//		StringBuilder sb = new StringBuilder();
//		if(lat_int < 10){
//			sb.append("0");
//		}
//		sb.append(lat_int);
//		
//		if(lat_minus_int < 10){
//			sb.append("0");
//		}
//		sb.append(lat_minus_int);
//		sb.append(".");
//		sb.append(lat_minus_float_int);
//		sb.append(flag);
//		
//		return sb.toString();
//	}
//	
//	/**
//	 * 用户输入的经度转换成发给车台的经度
//	 */
//	public static String userLngToUnitLng(String lng){
//		char flag;
//		BigDecimal dec_lng = new BigDecimal(lng);
//		if(dec_lng.compareTo(BigDecimal.ZERO) < 0){
//			flag = 'W';
//			dec_lng = dec_lng.negate();
//		}else{
//			flag = 'E';
//		}
//		
//		int lng_int = dec_lng.intValue();
//		
//		BigDecimal lng_float = dec_lng.subtract(new BigDecimal(lng_int));
//		BigDecimal lng_minus = lng_float.multiply(new BigDecimal(60));
//		
//		int lng_minus_int = lng_minus.intValue();
//		BigDecimal lng_minus_float = lng_minus.subtract(new BigDecimal(lng_minus_int));
//		BigDecimal lng_minus_float_mul = lng_minus_float.multiply(new BigDecimal(10000));
//		BigDecimal lng_minus_float_int = lng_minus_float_mul.setScale(0, RoundingMode.HALF_UP);
//		
//		StringBuilder sb = new StringBuilder();
//		if(lng_int < 10){
//			sb.append("00");
//		}else if(lng_int < 100){
//			sb.append("0");
//		}
//		sb.append(lng_int);
//		
//		if(lng_minus_int < 10){
//			sb.append("0");
//		}
//		sb.append(lng_minus_int);
//		sb.append(".");
//		sb.append(lng_minus_float_int);
//		sb.append(flag);
//		
//		return sb.toString();
//	}
//	
//	/**
//	 * 车台上报的纬度转换成用户纬度
//	 */
//	public static double unitLatToUserLat(String unitLat){
//		String strLat = unitLat.substring(0, 2);
//		String strLatf = unitLat.substring(2, 9);
//		String strLatSign = unitLat.substring(9, 10);
//		
//		BigDecimal lat = new BigDecimal(strLat);
//		BigDecimal lat_f = new BigDecimal(strLatf);
//		lat_f = lat_f.divide(D60, SCALE, ROUNDING_MODE);
//		lat = lat.add(lat_f);
//		if("S".equals(strLatSign)){
//			//南纬
//			lat = lat.negate();
//		}
//		
//		return lat.doubleValue();
//	}
//	
//	/**
//	 * 车台上报的纬度转换成用户纬度
//	 */
//	public static double unitLatLngToUserLatLng(String du, String fen, String fen_dec){
//		BigDecimal dec_du = new BigDecimal(du);
//		BigDecimal dec_fen = new BigDecimal(fen + "." + fen_dec);
//		BigDecimal dec_fen_to_du = dec_fen.divide(D60, SCALE, ROUNDING_MODE);
//		dec_du = dec_du.add(dec_fen_to_du);
//		
//		return dec_du.doubleValue();
//	}
//	
//	/**
//	 * 车台上报的纬度转换成用户纬度
//	 */
//	public static double unitLatLngToUserLatLng(int du, int fen, String fen_dec){
//		BigDecimal dec_du = new BigDecimal(du);
//		BigDecimal dec_fen = new BigDecimal(fen + "." + fen_dec);
//		BigDecimal dec_fen_to_du = dec_fen.divide(D60, SCALE, ROUNDING_MODE);
//		dec_du = dec_du.add(dec_fen_to_du);
//		
//		return dec_du.doubleValue();
//	}
//	
//	/**
//	 * 车台上报的经度转换成用户经度
//	 */
//	public static double unitLngToUserLng(String unitLng){
//		String strLng = unitLng.substring(0, 3);
//		String strLngf = unitLng.substring(3, 10);
//		String strLngSign = unitLng.substring(10, 11);
//		
//		BigDecimal lng = new BigDecimal(strLng);
//		BigDecimal lng_f = new BigDecimal(strLngf);
//		lng_f = lng_f.divide(D60, SCALE, ROUNDING_MODE);
//		lng = lng.add(lng_f);
//		if("W".equals(strLngSign)){
//			lng = lng.negate();
//		}
//		
//		return lng.doubleValue();
//	}
	
	/**
	 * 车台上报的IP转为普通IP
	 */
	public static String unitIPToUserIP(String unitIP){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < 4; i++){
			sb.append(Integer.valueOf(unitIP.substring(i * 3, i * 3 + 3)));
			sb.append(".");
		}
		
		if(sb.length() > 0){
			sb.deleteCharAt(sb.length() - 1);
		}
		
		return sb.toString();
	}
	
	public static int jieToSpeed(int jie){
		BigDecimal jiespeed = new BigDecimal(jie);
		BigDecimal speed = jiespeed.multiply(Constants.M_SPEED_JIE_TO_KMH);
		speed = speed.setScale(0, SegPkgUtil.ROUNDING_MODE);
		return speed.intValue();
	}
	
	public static int speedTOJie(int speed){
		BigDecimal decspeed = new BigDecimal(speed);
		BigDecimal jspeed = decspeed.divide(Constants.M_SPEED_JIE_TO_KMH, 0, SegPkgUtil.ROUNDING_MODE);
		return jspeed.intValue();
	}
	
//	public static cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo transformGPSInfo(String callLetter, SegGPSInfo gps, SegPackage pkg){
//		cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo.Builder builder = GBossDataBuff.GpsInfo.newBuilder();
//		builder.setCallLetter(callLetter);
//		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo baseBuilder = transGPSBaseInfo(gps);
//		builder.setBaseInfo(baseBuilder);
//		builder.setContent(ByteString.copyFrom(pkg.getSource()));
//		return builder.build();
//	}
//	
//	public static cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo transGPSBaseInfo(SegGPSInfo gps){
//		cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo.Builder baseBuilder = GBossDataBuff.GpsBaseInfo.newBuilder();
//		baseBuilder.setGpsTime(gps.getGpsTime().getTime());
//		baseBuilder.setLoc(gps.isLoc());
//		baseBuilder.setLat((int) (gps.getLat() * 1000000));
//		baseBuilder.setLng((int) (gps.getLng() * 1000000));
//		baseBuilder.setSpeed((int) Math.round(gps.getSpeed() * 10));
//		baseBuilder.setCourse(gps.getCourse());
//		baseBuilder.addAllStatus(gps.getStatus());
//		if(gps.getTotalDistance() != null){
//			baseBuilder.setTotalDistance(gps.getTotalDistance().intValue());
//		}
//		
//		if(gps.getOilPercent() != null){
//			baseBuilder.setOilPercent(gps.getOilPercent());
//		}
//		
//		if(gps.getTemperature1() != null){
//			baseBuilder.setTemperature1(gps.getTemperature1());
//		}
//		
//		if(gps.getTemperature2() != null){
//			baseBuilder.setTemperature2(gps.getTemperature2());
//		}
//		
//		List<MapEntry> ex = gps.getExtendsStatus();
//		for(int i = 0; i < ex.size(); i++){
//			MapEntry status = ex.get(i);
//			baseBuilder.addAppendParams(cc.chinagps.gateway.buff.GBossDataBuff.MapEntry
//					.newBuilder().setKey(status.getKey()).setValue(status.getValue()));
//		}
//		
//		SegOBDInfo obdInfo = gps.getObdInfo();
//		if(obdInfo != null){
//			cc.chinagps.gateway.buff.GBossDataBuff.OBDInfo.Builder obdBuilder = OBDInfo.newBuilder();
//			obdBuilder.setHourOil(obdInfo.getHourOil() * 10);
//			obdBuilder.setAverageOil(obdInfo.getAverageOil() * 10);
//			obdBuilder.setTotalDistance(obdInfo.getTotalDistance() * 1000);
//			
//			if(obdInfo.isOilPercent()){
//				obdBuilder.setRemainPercentOil(obdInfo.getRemainOil());
//			}else{
//				obdBuilder.setRemainOil(obdInfo.getRemainOil());
//			}
//
//			obdBuilder.setWaterTemperature(obdInfo.getWaterTemperature());
//			obdBuilder.setReviseOil(obdInfo.getReviseOil() * 10);
//			obdBuilder.setRotationSpeed(obdInfo.getRotationSpeed());
//			obdBuilder.setIntakeAirTemperature(obdInfo.getIntakeAirTemperature());
//			obdBuilder.setAirDischange(obdInfo.getAirDischange());
//			
//			Map<String, String> others = obdInfo.getOtherInfoes();
//			if(others != null){
//				Iterator<Entry<String, String>> ito = others.entrySet().iterator();
//				while(ito.hasNext()){
//					Entry<String, String> other = ito.next();
//					obdBuilder.addOtherInfoes(cc.chinagps.gateway.buff.GBossDataBuff.MapEntry
//							.newBuilder().setKey(other.getKey()).setValue(other.getValue()));
//				}
//			}
//		}
//		
//		return baseBuilder.build();
//	}
	
	/**
	 * 通用应答
	 * 消息体为呼号
	 */
	public static void commonAckUnit(byte cmdId, UnitSocketSession session, SegPackage pkg){
		String callLetter = session.getUnitInfo().getCallLetter();
		byte[] resBody = callLetter.getBytes();
		byte[] res = SegPkgUtil.encode(cmdId, pkg.getSerialNumberBytes(), resBody);
		session.sendData(res);
	}
	
	public static void main(String[] args) {
		String hex = "5b703030303030303030303036284f4e451300000000f919041d00802e000000001028282200002e00000000000000002e0000ec570180a1ca003330303043443030295d";
					//5b703030303030303030303036284f4e4512990040c1004044a301802ef49f00401c98004022002e0000847b004000002e0000ec57018078ca003444303043443030295d
		byte[] bs = HexUtil.hexToBytes(hex);
		try {
			String str = new String(bs, SegPkgUtil.PKG_CHAR_ENCODING);
			System.out.println("str:" + str);
			
			SegPackage pkg = SegPackage.parse(bs);
			byte[] body = pkg.getBody();
			String strBody = new String(body, SegPkgUtil.PKG_CHAR_ENCODING);
			
			SegGPSInfo gps = SegGPSInfo.parse(strBody, 4);
			System.out.println(gps);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}