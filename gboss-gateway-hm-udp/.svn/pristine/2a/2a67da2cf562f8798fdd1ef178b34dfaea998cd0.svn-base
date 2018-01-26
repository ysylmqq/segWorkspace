package cc.chinagps.gateway.unit.seg.upload.beans.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.seg.lib.util.Util;

public class ShenZhenService extends ServiceDetail{
	private static final SimpleDateFormat sdf_start = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final SimpleDateFormat sdf_end = new SimpleDateFormat("MMddHHmmss");
	private static final Date ERROR_DATE = new Date(0);
	
	@SuppressWarnings("deprecation")
	public static ServiceDetail parse(byte[] serviceData, int offsetx) throws Exception{
		ServiceDetail service = new ShenZhenService();
		int position = offsetx;
		String time1 = Util.bcd2str(serviceData, position, 7);
		position += 7;
		
		String time2 = Util.bcd2str(serviceData, position, 5);
		position += 5;
		
		String unitPrice = Util.bcd2str(serviceData, position, 2);
		position += 2;
		
		String distance = Util.bcd2str(serviceData, position, 3);
		position += 3;
		
		String timeLength_hour = Util.bcd2str(serviceData, position, 1);
		position += 1;
		
		String timeLength_minus = Util.bcd2str(serviceData, position, 1);
		position += 1;
		
		String timeLength_seconds = Util.bcd2str(serviceData, position, 1);
		position += 1;
		
		String serviceMoney = Util.bcd2str(serviceData, position, 3);
		position += 3;
		
		String emptyDistance = Util.bcd2str(serviceData, position, 3);
		position += 3;
		
		String cardNo = Util.bcd2str(serviceData, position, 8);
		position += 8;
		
		String cardStartMoney = Util.bcd2str(serviceData, position, 3);
		position += 3;
		
		String cardEndMoney = Util.bcd2str(serviceData, position, 3);
		position += 3;
		
		String emptyPowerOffCount = Util.bcd2str(serviceData, position, 2);
		position += 2; 
		
		String emptyPowerOffTime_hour = Util.bcd2str(serviceData, position, 1);
		position += 1;
		String emptyPowerOffTime_minus = Util.bcd2str(serviceData, position, 1);
		position += 1;
		String emptyPowerOffTime_seconds = Util.bcd2str(serviceData, position, 1);
		position += 1;
		
		String overSpeedDistance = Util.bcd2str(serviceData, position, 3);
		position += 3;
		
		String overSpeedCount = Util.bcd2str(serviceData, position, 2);
		position += 2;
		
		String permitNo = Util.bcd2str(serviceData, position, 4);
		position += 4;
		
//		//备用字段
//		position += 4;
//		//计价器交易存储序号
//		String storeIndex = Util.bcd2str(serviceData, position, 2);
//		//校验字节(无)
		
		int proLen = serviceData.length - offsetx;
		int storeIndex;
		if(proLen < 60){
			//部分车台数据到此结束
			storeIndex = 0;
		}else{
			//备用字段
			position += 4;
			//计价器交易存储序号
			String sidx = Util.bcd2str(serviceData, position, 2);
			storeIndex = Integer.valueOf(sidx, 16);
			//校验字节(无)
		}
		
		Date startTime;
		synchronized (sdf_start) {
			try{
				startTime = sdf_start.parse(time1);
			}catch (ParseException e) {
				startTime = ERROR_DATE;
			}
		}
		service.setStartTime(startTime);
		
		Date endTime;
		synchronized (sdf_end) {
			try{
				endTime = sdf_end.parse(time2);
			}catch (ParseException e) {
				endTime = ERROR_DATE;
			}
		}
		
		if(startTime.getMonth() == 11 && startTime.getDate() == 31
				&& endTime.getMonth() == 0 && endTime.getDate() == 1){
			endTime.setYear(startTime.getYear() + 1);
		}else{
			endTime.setYear(startTime.getYear());
		}
		
		service.setEndTime(endTime);
		service.setUnitPrice(getIntValue(unitPrice));
		service.setDistance(getIntValue(distance));
		service.setTimeLength(stringTimeToIntTime(timeLength_hour, timeLength_minus, timeLength_seconds));
		service.setServiceMoney(getIntValue(serviceMoney));
		service.setEmptyDistance(getIntValue(emptyDistance));
		service.setCardNo(cardNo);
		service.setCardStartMoney(getIntValue(cardStartMoney));
		service.setCardEndMoney(getIntValue(cardEndMoney));
		service.setEmptyPowerOffCount(getIntValue(emptyPowerOffCount));
		service.setEmptyPowerOffTime(stringTimeToIntTime(emptyPowerOffTime_hour, emptyPowerOffTime_minus, emptyPowerOffTime_seconds));
		service.setOverSpeedDistance(getIntValue(overSpeedDistance));
		service.setOverSpeedCount(getIntValue(overSpeedCount));
		service.setPermitNo(permitNo);
		//service.setStoreIndex(Integer.valueOf(storeIndex, 16));
		service.setStoreIndex(storeIndex);
		
		return service;
	}
	
	private static int getIntValue(String str){
		try{
			return Integer.valueOf(str);
		}catch (NumberFormatException e) {
			return 0;
		}
	}
	
	private static int stringTimeToIntTime(String hours, String minus, String seconds){
		try{
			int time = Integer.valueOf(hours) * 3600 + Integer.valueOf(minus) * 60 + Integer.valueOf(seconds);
			return time * 1000;
		}catch (NumberFormatException e) {
			return 0;
		}
	}
}