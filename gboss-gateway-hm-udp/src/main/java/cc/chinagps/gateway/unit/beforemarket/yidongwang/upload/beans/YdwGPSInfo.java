package cc.chinagps.gateway.unit.beforemarket.yidongwang.upload.beans;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.unit.beforemarket.common.util.BeforeMarketTimeFormatUtil;

public class YdwGPSInfo {
	private Date gpsTime;
	
	private boolean isLoc;
	
	private double lng;
	
	private double lat;
	
	private int speed;
	
	private int course;
	
	private int height;

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
	
	private static final double LAT_LNG_MUL = 10000000;
	public static final BigDecimal M_SPEED = new BigDecimal("0.1852");
	
	public static YdwGPSInfo parse(byte[] data, int offset) throws Exception{
		YdwGPSInfo gpsInfo = new YdwGPSInfo();
		String str_time = "20" + Util.bcd2str(data, offset, 6);
		Date gpsTime = BeforeMarketTimeFormatUtil.parseGMT0(str_time);
		byte flag = data[offset + 6];
		boolean isLoc = (flag & 0x80) != 0;
		boolean isBelowSea = (flag & 0x40) != 0;
		
		int course = (data[offset + 7] & 0xFF) * 10;
		double lat = Util.getInt(data, offset + 8) / LAT_LNG_MUL;
		double lng = Util.getInt(data, offset + 12) / LAT_LNG_MUL;
		int i_speed = Util.getShort(data, offset + 16) & 0xFFFF;
		int i_height = Util.getShort(data, offset + 18) & 0xFFFF;
		if(isBelowSea){
			i_height = -i_height;
		}
		
		gpsInfo.setGpsTime(gpsTime);
		gpsInfo.setLng(lng);
		gpsInfo.setLat(lat);
		gpsInfo.setSpeed(i_speed);
		gpsInfo.setCourse(course);
		gpsInfo.setHeight(i_height);
		gpsInfo.setLoc(isLoc);
		
		return gpsInfo;
	}
	
	public static String byteStr(byte flag){
		String str = Integer.toBinaryString(flag);
		if(str.length() > 8){
			str = str.substring(str.length() - 8, str.length() - 1);
			return str;
		}
		
		if(str.length() < 8){
			int ap = 8 - str.length();
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < ap; i++){
				sb.append("0");
			}
			sb.append(str);
			
			return sb.toString();
		}
		
		return str;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuilder sb = new StringBuilder();
		sb.append("{isLoc:").append(isLoc);
		sb.append(", gpsTime:").append(sdf.format(gpsTime));
		sb.append(", lng:").append(lng);
		sb.append(", lat:").append(lat);
		sb.append(", speed:").append(speed);
		sb.append(", course:").append(course);
		sb.append(", height:").append(height);
		sb.append("}");
		
		return sb.toString();
	}
}