package cc.chinagps.gateway.unit.seg.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SegLatLngUtil {
	public static final int SCALE = 6;
	public static final BigDecimal D60 = new BigDecimal(60);
	
	/**
	 * 用户输入的纬度转换成发给车台的纬度
	 */
	public static String userLatToUnitLat(String lat){
		char flag;
		BigDecimal dec_lat = new BigDecimal(lat);
		if(dec_lat.compareTo(BigDecimal.ZERO) < 0){
			flag = 'S';
			dec_lat = dec_lat.negate();
		}else{
			flag = 'N';
		}
		
		int lat_int = dec_lat.intValue();
		
		BigDecimal lat_float = dec_lat.subtract(new BigDecimal(lat_int));
		BigDecimal lat_minus = lat_float.multiply(new BigDecimal(60));
		
		int lat_minus_int = lat_minus.intValue();
		BigDecimal lat_minus_float = lat_minus.subtract(new BigDecimal(lat_minus_int));
		BigDecimal lat_minus_float_mul = lat_minus_float.multiply(new BigDecimal(10000));
		BigDecimal lat_minus_float_int = lat_minus_float_mul.setScale(0, RoundingMode.HALF_UP);
		
		StringBuilder sb = new StringBuilder();
		if(lat_int < 10){
			sb.append("0");
		}
		sb.append(lat_int);
		
		if(lat_minus_int < 10){
			sb.append("0");
		}
		sb.append(lat_minus_int);
		sb.append(".");
		sb.append(lat_minus_float_int);
		sb.append(flag);
		
		return sb.toString();
	}
	
	/**
	 * 用户输入的经度转换成发给车台的经度
	 */
	public static String userLngToUnitLng(String lng){
		char flag;
		BigDecimal dec_lng = new BigDecimal(lng);
		if(dec_lng.compareTo(BigDecimal.ZERO) < 0){
			flag = 'W';
			dec_lng = dec_lng.negate();
		}else{
			flag = 'E';
		}
		
		int lng_int = dec_lng.intValue();
		
		BigDecimal lng_float = dec_lng.subtract(new BigDecimal(lng_int));
		BigDecimal lng_minus = lng_float.multiply(new BigDecimal(60));
		
		int lng_minus_int = lng_minus.intValue();
		BigDecimal lng_minus_float = lng_minus.subtract(new BigDecimal(lng_minus_int));
		BigDecimal lng_minus_float_mul = lng_minus_float.multiply(new BigDecimal(10000));
		BigDecimal lng_minus_float_int = lng_minus_float_mul.setScale(0, RoundingMode.HALF_UP);
		
		StringBuilder sb = new StringBuilder();
		if(lng_int < 10){
			sb.append("00");
		}else if(lng_int < 100){
			sb.append("0");
		}
		sb.append(lng_int);
		
		if(lng_minus_int < 10){
			sb.append("0");
		}
		sb.append(lng_minus_int);
		sb.append(".");
		sb.append(lng_minus_float_int);
		sb.append(flag);
		
		return sb.toString();
	}
	
	/**
	 * 车台上报的纬度转换成用户纬度
	 */
	public static double unitLatToUserLat(String unitLat){
		String strLat = unitLat.substring(0, 2);
		String strLatf = unitLat.substring(2, 9);
		String strLatSign = unitLat.substring(9, 10);
		
		BigDecimal lat = new BigDecimal(strLat);
		BigDecimal lat_f = new BigDecimal(strLatf);
		lat_f = lat_f.divide(D60, SCALE, SegPkgUtil.ROUNDING_MODE);
		lat = lat.add(lat_f);
		if("S".equals(strLatSign)){
			//南纬
			lat = lat.negate();
		}
		
		return lat.doubleValue();
	}
	
	/**
	 * 车台上报的纬度转换成用户纬度
	 */
	public static double unitLatLngToUserLatLng(String du, String fen, String fen_dec){
		BigDecimal dec_du = new BigDecimal(du);
		BigDecimal dec_fen = new BigDecimal(fen + "." + fen_dec);
		BigDecimal dec_fen_to_du = dec_fen.divide(D60, SCALE, SegPkgUtil.ROUNDING_MODE);
		dec_du = dec_du.add(dec_fen_to_du);
		
		return dec_du.doubleValue();
	}
	
	/**
	 * 车台上报的纬度转换成用户纬度
	 */
	public static double unitLatLngToUserLatLng(int du, int fen, String fen_dec){
		BigDecimal dec_du = new BigDecimal(du);
		BigDecimal dec_fen = new BigDecimal(fen + "." + fen_dec);
		BigDecimal dec_fen_to_du = dec_fen.divide(D60, SCALE, SegPkgUtil.ROUNDING_MODE);
		dec_du = dec_du.add(dec_fen_to_du);
		
		return dec_du.doubleValue();
	}
	
	/**
	 * 车台上报的经度转换成用户经度
	 */
	public static double unitLngToUserLng(String unitLng){
		String strLng = unitLng.substring(0, 3);
		String strLngf = unitLng.substring(3, 10);
		String strLngSign = unitLng.substring(10, 11);
		
		BigDecimal lng = new BigDecimal(strLng);
		BigDecimal lng_f = new BigDecimal(strLngf);
		lng_f = lng_f.divide(D60, SCALE, SegPkgUtil.ROUNDING_MODE);
		lng = lng.add(lng_f);
		if("W".equals(strLngSign)){
			lng = lng.negate();
		}
		
		return lng.doubleValue();
	}
}
