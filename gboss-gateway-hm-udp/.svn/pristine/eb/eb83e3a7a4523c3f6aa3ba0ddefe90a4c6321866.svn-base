package cc.chinagps.gateway.unit.pengaoda.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PengAoDaLatLngUtil {
	public static final int SCALE = 6;
	public static final BigDecimal D60 = new BigDecimal(60);
	public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
	
	/**
	 * 车台上报的纬度转换成用户纬度
	 */
	public static double parseUnitLat(String unitLat, String flag){
		String strLat = unitLat.substring(0, 2);
		String strLatf = unitLat.substring(2, 9);
		
		BigDecimal lat = new BigDecimal(strLat);
		BigDecimal lat_f = new BigDecimal(strLatf);
		lat_f = lat_f.divide(D60, SCALE, ROUNDING_MODE);
		lat = lat.add(lat_f);
		if(!"N".equals(flag)){
			//南纬
			lat = lat.negate();
		}
		
		return lat.doubleValue();
	}
	
	/**
	 * 车台上报的经度转换成用户经度
	 */
	public static double parseUnitLng(String unitLng, String flag){
		String strLng = unitLng.substring(0, 3);
		String strLngf = unitLng.substring(3, 10);
		
		BigDecimal lng = new BigDecimal(strLng);
		BigDecimal lng_f = new BigDecimal(strLngf);
		lng_f = lng_f.divide(D60, SCALE, ROUNDING_MODE);
		lng = lng.add(lng_f);
		if(!"E".equals(flag)){
			//西经
			lng = lng.negate();
		}
		
		return lng.doubleValue();
	}
	
	/**
	 * 车台定时上报的纬度
	 */
	public static double parseUnitLatTime(String unitLat, boolean isNorth){
		String strLat = unitLat.substring(0, 2);
		String strLatf = unitLat.substring(2, 4) + "." + unitLat.substring(4);
		
		BigDecimal lat = new BigDecimal(strLat);
		BigDecimal lat_f = new BigDecimal(strLatf);
		lat_f = lat_f.divide(D60, SCALE, ROUNDING_MODE);
		lat = lat.add(lat_f);
		if(!isNorth){
			//南纬
			lat = lat.negate();
		}
		
		return lat.doubleValue();
	}
	
	/**
	 * 车台定时上报的经度
	 */
	public static double parseUnitLngTime(String unitLng, boolean isEast){
		String strLng = unitLng.substring(0, 3);
		String strLngf = unitLng.substring(3, 5) + "." + unitLng.substring(5);
		
		BigDecimal lng = new BigDecimal(strLng);
		BigDecimal lng_f = new BigDecimal(strLngf);
		lng_f = lng_f.divide(D60, SCALE, ROUNDING_MODE);
		lng = lng.add(lng_f);
		if(!isEast){
			//西经
			lng = lng.negate();
		}
		
		return lng.doubleValue();
	}
}