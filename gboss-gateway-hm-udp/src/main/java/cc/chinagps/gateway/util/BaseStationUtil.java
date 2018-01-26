package cc.chinagps.gateway.util;

import cc.chinagps.gateway.memcache.MemcacheManager;
import cc.chinagps.gateway.unit.beans.BaseStationAddress;
import cc.chinagps.gateway.unit.beans.BaseStationInfo;

public class BaseStationUtil {
	private static final String KEY_SPLIT = "_";
	private static final String VALUE_SPLIT = "_";
	
	public static BaseStationAddress getBaseStationAddress(BaseStationInfo info){
		StringBuilder key = new StringBuilder();
		key.append(MemcacheManager.BASE_STATION_INFO_KEY_HEAD);
		key.append(info.getMcc()).append(KEY_SPLIT);
		if(info.getMnc().length() == 1){
			key.append("0");
		}
		key.append(info.getMnc()).append(KEY_SPLIT);
		key.append(info.getLac()).append(KEY_SPLIT);
		key.append(info.getCid());
		
		Object objValue = MemcacheManager.getMemcachedClient().get(key.toString());
		
		if(objValue == null){
			return null;
		}
		
		String value = objValue.toString();
		int idx1 = value.indexOf(VALUE_SPLIT);
		int idx2 = value.indexOf(VALUE_SPLIT, idx1 + 1);
		int idx3 = value.indexOf(VALUE_SPLIT, idx2 + 1);
		
		if(idx1 == -1 || idx2 == -1 || idx3 == -1){
			return null;
		}
		
		String slon = value.substring(0, idx1);
		String slat = value.substring(idx1 + 1, idx2);
		String sprecision = value.substring(idx2 + 1, idx3);
		String addressDesc = value.substring(idx3 + 1);
		
		BaseStationAddress address = new BaseStationAddress();
		address.setAddress(addressDesc);
		address.setLat(Double.valueOf(slat));
		address.setLon(Double.valueOf(slon));
		if(sprecision.length() > 0){
			address.setPrecision(Integer.valueOf(sprecision));
		}
		
		return address;
	}
	
	public static void main(String[] args) {
		System.out.println(MemcacheManager.BASE_STATION_INFO_KEY_HEAD);
		
		//120.295784_31.575298_3162_江苏省无锡市人民中路
		BaseStationInfo info = new BaseStationInfo();
		info.setMcc("460");
		info.setMnc("0");
		info.setLac(0);
		info.setCid(12977);
		long t1 = System.currentTimeMillis();
		BaseStationAddress address = getBaseStationAddress(info);
		long t2 = System.currentTimeMillis();
		System.out.println("time:" + (t2 - t1) + ", address:" + address);
	}
}