package cc.chinagps.seat.hbase;

import java.math.BigDecimal;

public final class HBaseUtil {
	
	/**
	 * 经纬度转换
	 * @param lngOrLat
	 * @return
	 */
	public static double translateLngLat(int lngOrLat) {
		return translateLngLat1(lngOrLat).doubleValue();
	}
	
	/**
	 * 经纬度转换
	 * @param lngOrLat
	 * @return
	 */
	public static String translateLngLatToDegree(int lngOrLat) {
		BigDecimal lngLat = translateLngLat1(lngOrLat);
		String lngLatStr = lngLat.intValue() + "°";
		lngLat = lngLat.subtract(new BigDecimal(lngLat.intValue())).multiply(new BigDecimal(60));
		lngLatStr += lngLat.intValue() + "′";
		lngLat = lngLat.subtract(new BigDecimal(lngLat.intValue())).multiply(new BigDecimal(60));
		lngLatStr += lngLat.intValue() + "″";
		return lngLatStr;
	}

	private static BigDecimal translateLngLat1(int lngOrLat) {
		return new BigDecimal(lngOrLat).scaleByPowerOfTen(-6);
	}
	
	/**
	 * 速度转换为km/h
	 * @param speed
	 * @return
	 */
	public static double translateSpeedToKMPerHour(int speed) {
		return new BigDecimal(speed).scaleByPowerOfTen(-1).doubleValue();
	}
}
