package com.chinaGPS.gtmp.business.memcache;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.chinaGPS.gtmp.entity.ConditionsPOJO;
import com.chinaGPS.gtmp.util.PropertyUtil;
@Service
public class MemCache {
	private static Logger logger =LoggerFactory.getLogger(MemCache.class); 
	private static MemcachedClient mcc;
	
	public MemCache(){
	    init();
	}	
	/**
	 * @Title:init
	 * @Description:memcache连接初始化
	 * @throws
	 */
	private void init(){
	/*    Properties property = PropertyUtil.getProperty("memcache.properties");
		if(property.get("memIp")!=null){
			memIp = (String)property.get("memIp");
		}
		if(property.get("memPort")!=null){
			try{
				memPort = Short.parseShort((String)property.get("memPort"));
			}catch(NumberFormatException e){
				e.printStackTrace();
				exceptionLogger.error("memcache端口加载失败，目前使用IP:"+memIp+"端口："+memPort);
			}
		}
	      
    		try {
    			mcc=new net.spy.memcached.MemcachedClient(new InetSocketAddress(memIp, memPort));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	    
	         //与myBatis的二级缓存共用一个memcached
		if (mcc == null) {
		        // 默认Memcache连接
		        String memcache = "127.0.0.1:11211";
		        // 加载配置数据
		        Properties property = PropertyUtil.getProperty("memcached.properties");
		        if (property.get("org.mybatis.caches.memcached.servers") != null && !"".equals(property.get("org.mybatis.caches.memcached.servers"))) {
		            memcache = (String) property.get("org.mybatis.caches.memcached.servers");
		        }
		        
		        //连memcached地址
			    if (logger.isDebugEnabled()) {
		    		logger.debug("连memcached地址：" + memcache);
		    	}
			    
		        try {
		        	mcc=new MemcachedClient(new BinaryConnectionFactory(), AddrUtil.getAddresses(memcache));
				} catch (IOException e) {
					e.printStackTrace();
					
					 logger.error(e.getMessage(),e);
				}
		}
	}
	
	public static MemcachedClient getMemcached(){
	    return mcc;
	}
	
	/**
	 * 每五分钟上报的工况
	 * 
	 */
	public ConditionsPOJO getCondition(String unitId){
		String positionStr = (String)mcc.get(unitId+"con");
	    if(positionStr==null){
	        return null;
	    }
	    String[] strs = positionStr.split("~");
	    if(strs.length<38){
	    	return null;
	    }
	    ConditionsPOJO conditions=new ConditionsPOJO();
	    
	    //终端ID
	    if(!"".equals(strs[0]) && strs[0]!=null && !strs[0].equals("null")){
	    	conditions.setUnitId(strs[0]);
	    }
	    //strs[1]是流水号   暂时不要
	    //发动机冷却液温度
	    if(!"".equals(strs[2]) && strs[2]!=null && !strs[2].equals("null")){
	    	conditions.setEngineCoolantTemperature(Integer.valueOf(strs[2]));
	    }
	    //发动机冷却液液位
	    if(!"".equals(strs[3]) && strs[3]!=null && !strs[3].equals("null")){
	    	conditions.setEngineCoolantLevel(Float.valueOf(strs[3]));
	    }
	    //发动机机油压力
	    if(!"".equals(strs[4]) && strs[4]!=null && !strs[4].equals("null")){
	    	conditions.setEngineOilPressure(Float.valueOf(strs[4]));
	    }
	    //发动机燃油油位
	    if(!"".equals(strs[5]) && strs[5]!=null && !strs[5].equals("null")){
	    	conditions.setEngineFuelLevel(Float.valueOf(strs[5]));
	    }
	    //发动机转速
	    if(!"".equals(strs[6]) && strs[6]!=null && !strs[6].equals("null")){
	    	conditions.setEngineSpeed(Integer.valueOf(strs[6]));
	    }
	    //液压油温度
	    if(!"".equals(strs[7]) && strs[7]!=null && !strs[7].equals("null")){
	    	conditions.setHydraulicOilTemperature(Integer.valueOf(strs[7]));
	    }
	    //系统电压
	    if(!"".equals(strs[8]) && strs[8]!=null && !strs[8].equals("null")){
	    	conditions.setSystemVoltage(Float.valueOf(strs[8]));
	    }
	    //挖掘机前泵主压压力
	    if(!"".equals(strs[9]) && strs[9]!=null && !strs[9].equals("null")){
	    	conditions.setBeforePumpMainPressure(Float.valueOf(strs[9]));
	    }
	    //挖掘机后泵主压压力
	    if(!"".equals(strs[10]) && strs[10]!=null && !strs[10].equals("null")){
	    	conditions.setAfterPumpMainPressure(Float.valueOf(strs[10]));
	    }
	    //挖掘机先导压力1
	    if(!"".equals(strs[11]) && strs[11]!=null && !strs[11].equals("null")){
	    	conditions.setPilotPressure1(Float.valueOf(strs[11]));
	    }
	    //挖掘机先导压力2
	    if(!"".equals(strs[12]) && strs[12]!=null && !strs[12].equals("null")){
	    	conditions.setPilotPressure2(Float.valueOf(strs[12]));
	    }
	    //挖掘机前泵负压压力
	    if(!"".equals(strs[13]) && strs[13]!=null && !strs[13].equals("null")){
	    	conditions.setBeforePumpPressure(Float.valueOf(strs[13]));
	    }
	    //挖掘机后泵负压压力
	    if(!"".equals(strs[14]) && strs[14]!=null && !strs[14].equals("null")){
	    	conditions.setAfterPumpPressure(Float.valueOf(strs[14]));
	    }
	    //挖掘机比例阀电流1
	    if(!"".equals(strs[15]) && strs[15]!=null && !strs[15].equals("null")){
	    	conditions.setProportionalCurrent1(Integer.valueOf(strs[15]));
	    }
	    //挖掘机比例阀电流2
	    if(!"".equals(strs[16]) && strs[16]!=null && !strs[16].equals("null")){
	    	conditions.setProportionalCurrent2(Integer.valueOf(strs[16]));
	    }
	    //累计工作小时
	    if(!"".equals(strs[17]) && strs[17]!=null && !strs[17].equals("null")){
	    	conditions.setTotalWorkingHours(Float.valueOf(strs[17]));
	    }
	    //修正时间
	    if(!"".equals(strs[18]) && strs[18]!=null && !strs[18].equals("null")){
	    	conditions.setCorrectHours(Float.valueOf(strs[18]));
	    }
	    //挖机档位
	    if(!"".equals(strs[19]) && strs[19]!=null && !strs[19].equals("null")){
	    	conditions.setGear(Integer.valueOf(strs[19]));
	    }
	    //发动机冷却液温度高报警设置值
	    if(!"".equals(strs[20]) && strs[20]!=null && !strs[20].equals("null")){
	    	conditions.setHighEngineCoolantTemperVal(Integer.valueOf(strs[20]));
	    }
	    //发动机机油压力低报警设置值
	    if(!"".equals(strs[21]) && strs[21]!=null && !strs[21].equals("null")){
	    	conditions.setLowEngineOilPressureAlarmValue(Float.valueOf(strs[21]));
	    }
	    //系统电压高报警设置值
	    if(!"".equals(strs[22]) && strs[22]!=null && !strs[22].equals("null")){
	    	conditions.setHighVoltageAlarmValue(Float.valueOf(strs[22]));
	    }
	    //系统电压低报警设置值
	    if(!"".equals(strs[23]) && strs[23]!=null && !strs[23].equals("null")){
	    	conditions.setLowVoltageAlarmValue(Float.valueOf(strs[23]));
	    }
	    //液压油温高报警值
	    if(!"".equals(strs[24]) && strs[24]!=null && !strs[24].equals("null")){
	    	conditions.setHighHydraulicOilTemperAlarmVal(Integer.valueOf(strs[24]));
	    }
	    //飞轮齿数设置值
	    if(!"".equals(strs[25]) && strs[25]!=null && !strs[25].equals("null")){
	    	conditions.setToothNumberValue(Integer.valueOf(strs[25]));
	    }
	    //监控器（显示器）供应商内部软件代号
	    if(!"".equals(strs[26]) && strs[26]!=null && !strs[26].equals("null")){
	    	conditions.setMonitorSoftwareCode(strs[26]);
	    }
	    //监控器（显示器）玉柴内部软件版本号
	    if(!"".equals(strs[27]) && strs[27]!=null && !strs[27].equals("null")){
	    	conditions.setMonitorYcSoftwareCode(strs[27]);
	    }
	    //控制器供应商内部软件代号
	    if(!"".equals(strs[28]) && strs[28]!=null && !strs[28].equals("null")){
	    	conditions.setControllerSoftwareCode(strs[28]);
	    }
	    //控制器玉柴内部软件代号
	    if(!"".equals(strs[29]) && strs[29]!=null && !strs[29].equals("null")){
	    	conditions.setControllerYcSoftwareCode(strs[29]);
	    }
	    //原始数据
	    if(!"".equals(strs[30]) && strs[30]!=null && !strs[30].equals("null")){
	    	conditions.setRawData(strs[30]);
	    }
	    //是否工作
	    if(!"".equals(strs[31]) && strs[31]!=null && !strs[31].equals("null")){
	    	conditions.setIsWork(Integer.valueOf(strs[31]));
	    }
	    //故障码
	    if(!"".equals(strs[32]) && strs[32]!=null && !strs[32].equals("null")){
	    	conditions.setFaultCode(Integer.valueOf(strs[32]));
	    }
	    //发动机机油温度
	    if(!"".equals(strs[33]) && strs[33]!=null && !strs[33].equals("null")){
	    	conditions.setEngineOilTemperature(Integer.valueOf(strs[33]));
	    }
	    //A锁状态
	    if(!"".equals(strs[34]) && strs[34]!=null && !strs[34].equals("null")){
	    	conditions.setAlock(Integer.valueOf(strs[34]));
	    }
	    //B锁状态
	    if(!"".equals(strs[35]) && strs[35]!=null && !strs[35].equals("null")){
	    	conditions.setBlock(Integer.valueOf(strs[35]));
	    }
	    //使能状态
	    if(!"".equals(strs[36]) && strs[36]!=null && !strs[36].equals("null")){
	    	conditions.setClock(Integer.valueOf(strs[36]));
	    }
	    //时间
		if (strs.length > 37) {
			if (!"".equals(strs[37]) && strs[37] != null && !strs[37].equals("null")) {
				conditions.setNowTime(strs[37]);
			} else {
				SimpleDateFormat formater = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				conditions.setNowTime(formater.format(new Date()));
			}
		}
	    
	    //最后修改时间
	    /*if(!"".equals(strs[32]) && strs[32]!=null){
	    	conditions.setIsWork(Integer.valueOf(strs[32]));
	    }*/
		
		return conditions;
		
		
	}
	
	/**
	 * 指令下发获取最后工况
	 * 
	 */
	public ConditionsPOJO getConditionOrder(String commandSn){
		String positionStr = (String)mcc.get(commandSn+"uback");
	    if(positionStr==null){
	        return null;
	    }
	    String[] strs = positionStr.split("~");
	    
	    if(strs.length<38){
	    	return null;
	    			}
	    ConditionsPOJO conditions=new ConditionsPOJO();
	    //终端ID
	    if(!"".equals(strs[0]) && strs[0]!=null && !strs[0].equals("null")){
	    	conditions.setUnitId(strs[0]);
	    }
	    //strs[1]是流水号   暂时不要
	    //发动机冷却液温度
	    if(!"".equals(strs[2]) && strs[2]!=null && !strs[2].equals("null")){
	    	conditions.setEngineCoolantTemperature(Integer.valueOf(strs[2]));
	    }
	    //发动机冷却液液位
	    if(!"".equals(strs[3]) && strs[3]!=null && !strs[3].equals("null")){
	    	conditions.setEngineCoolantLevel(Float.valueOf(strs[3]));
	    }
	    //发动机机油压力
	    if(!"".equals(strs[4]) && strs[4]!=null && !strs[4].equals("null")){
	    	conditions.setEngineOilPressure(Float.valueOf(strs[4]));
	    }
	    //发动机燃油油位
	    if(!"".equals(strs[5]) && strs[5]!=null && !strs[5].equals("null")){
	    	conditions.setEngineFuelLevel(Float.valueOf(strs[5]));
	    }
	    //发动机转速
	    if(!"".equals(strs[6]) && strs[6]!=null && !strs[6].equals("null")){
	    	conditions.setEngineSpeed(Integer.valueOf(strs[6]));
	    }
	    //液压油温度
	    if(!"".equals(strs[7]) && strs[7]!=null && !strs[7].equals("null")){
	    	conditions.setHydraulicOilTemperature(Integer.valueOf(strs[7]));
	    }
	    //系统电压
	    if(!"".equals(strs[8]) && strs[8]!=null && !strs[8].equals("null")){
	    	conditions.setSystemVoltage(Float.valueOf(strs[8]));
	    }
	    //挖掘机前泵主压压力
	    if(!"".equals(strs[9]) && strs[9]!=null && !strs[9].equals("null")){
	    	conditions.setBeforePumpMainPressure(Float.valueOf(strs[9]));
	    }
	    //挖掘机后泵主压压力
	    if(!"".equals(strs[10]) && strs[10]!=null && !strs[10].equals("null")){
	    	conditions.setAfterPumpMainPressure(Float.valueOf(strs[10]));
	    }
	    //挖掘机先导压力1
	    if(!"".equals(strs[11]) && strs[11]!=null && !strs[11].equals("null")){
	    	conditions.setPilotPressure1(Float.valueOf(strs[11]));
	    }
	    //挖掘机先导压力2
	    if(!"".equals(strs[12]) && strs[12]!=null && !strs[12].equals("null")){
	    	conditions.setPilotPressure2(Float.valueOf(strs[12]));
	    }
	    //挖掘机前泵负压压力
	    if(!"".equals(strs[13]) && strs[13]!=null && !strs[13].equals("null")){
	    	conditions.setBeforePumpPressure(Float.valueOf(strs[13]));
	    }
	    //挖掘机后泵负压压力
	    if(!"".equals(strs[14]) && strs[14]!=null && !strs[14].equals("null")){
	    	conditions.setAfterPumpPressure(Float.valueOf(strs[14]));
	    }
	    //挖掘机比例阀电流1
	    if(!"".equals(strs[15]) && strs[15]!=null && !strs[15].equals("null")){
	    	conditions.setProportionalCurrent1(Integer.valueOf(strs[15]));
	    }
	    //挖掘机比例阀电流2
	    if(!"".equals(strs[16]) && strs[16]!=null && !strs[16].equals("null")){
	    	conditions.setProportionalCurrent2(Integer.valueOf(strs[16]));
	    }
	    //累计工作小时
	    if(!"".equals(strs[17]) && strs[17]!=null && !strs[17].equals("null")){
	    	conditions.setTotalWorkingHours(Float.valueOf(strs[17]));
	    }
	    //修正时间
	    if(!"".equals(strs[18]) && strs[18]!=null && !strs[18].equals("null")){
	    	conditions.setCorrectHours(Float.valueOf(strs[18]));
	    }
	    //挖机档位
	    if(!"".equals(strs[19]) && strs[19]!=null && !strs[19].equals("null")){
	    	conditions.setGear(Integer.valueOf(strs[19]));
	    }
	    //发动机冷却液温度高报警设置值
	    if(!"".equals(strs[20]) && strs[20]!=null && !strs[20].equals("null")){
	    	conditions.setHighEngineCoolantTemperVal(Integer.valueOf(strs[20]));
	    }
	    //发动机机油压力低报警设置值
	    if(!"".equals(strs[21]) && strs[21]!=null && !strs[21].equals("null")){
	    	conditions.setLowEngineOilPressureAlarmValue(Float.valueOf(strs[21]));
	    }
	    //系统电压高报警设置值
	    if(!"".equals(strs[22]) && strs[22]!=null && !strs[22].equals("null")){
	    	conditions.setHighVoltageAlarmValue(Float.valueOf(strs[22]));
	    }
	    //系统电压低报警设置值
	    if(!"".equals(strs[23]) && strs[23]!=null && !strs[23].equals("null")){
	    	conditions.setLowVoltageAlarmValue(Float.valueOf(strs[23]));
	    }
	    //液压油温高报警值
	    if(!"".equals(strs[24]) && strs[24]!=null && !strs[24].equals("null")){
	    	conditions.setHighHydraulicOilTemperAlarmVal(Integer.valueOf(strs[24]));
	    }
	    //飞轮齿数设置值
	    if(!"".equals(strs[25]) && strs[25]!=null && !strs[25].equals("null")){
	    	conditions.setToothNumberValue(Integer.valueOf(strs[25]));
	    }
	    //监控器（显示器）供应商内部软件代号
	    if(!"".equals(strs[26]) && strs[26]!=null && !strs[26].equals("null")){
	    	conditions.setMonitorSoftwareCode(strs[26]);
	    }
	    //监控器（显示器）玉柴内部软件版本号
	    if(!"".equals(strs[27]) && strs[27]!=null && !strs[27].equals("null")){
	    	conditions.setMonitorYcSoftwareCode(strs[27]);
	    }
	    //控制器供应商内部软件代号
	    if(!"".equals(strs[28]) && strs[28]!=null && !strs[28].equals("null")){
	    	conditions.setControllerSoftwareCode(strs[28]);
	    }
	    //控制器玉柴内部软件代号
	    if(!"".equals(strs[29]) && strs[29]!=null && !strs[29].equals("null")){
	    	conditions.setControllerYcSoftwareCode(strs[29]);
	    }
	    //原始数据
	    if(!"".equals(strs[30]) && strs[30]!=null && !strs[30].equals("null")){
	    	conditions.setRawData(strs[30]);
	    }
	    //是否工作
	    if(!"".equals(strs[31]) && strs[31]!=null && !strs[31].equals("null")){
	    	conditions.setIsWork(Integer.valueOf(strs[31]));
	    }
	    //故障码
	    if(!"".equals(strs[32]) && strs[32]!=null && !strs[32].equals("null")){
	    	conditions.setFaultCode(Integer.valueOf(strs[32]));
	    }
	    //发动机机油温度
	    if(!"".equals(strs[33]) && strs[33]!=null && !strs[33].equals("null")){
	    	conditions.setEngineOilTemperature(Integer.valueOf(strs[33]));
	    }
	    //A锁状态
	    if(!"".equals(strs[34]) && strs[34]!=null && !strs[34].equals("null")){
	    	conditions.setAlock(Integer.valueOf(strs[34]));
	    }
	    //B锁状态
	    if(!"".equals(strs[35]) && strs[35]!=null && !strs[35].equals("null")){
	    	conditions.setBlock(Integer.valueOf(strs[35]));
	    }
	    //使能状态
	    if(!"".equals(strs[36]) && strs[36]!=null && !strs[36].equals("null")){
	    	conditions.setClock(Integer.valueOf(strs[36]));
	    }
	    
	  //时间
	  		if (strs.length > 37) {
	  			if (!"".equals(strs[37]) && strs[37] != null && !strs[37].equals("null")) {
	  				conditions.setNowTime(strs[37]);
	  			} else {
	  				SimpleDateFormat formater = new SimpleDateFormat(
	  						"yyyy-MM-dd HH:mm:ss");
	  				conditions.setNowTime(formater.format(new Date()));
	  			}
	  		}
	    //最后修改时间
	    /*if(!"".equals(strs[32]) && strs[32]!=null){
	    	conditions.setIsWork(Integer.valueOf(strs[32]));
	    }*/
		
		return conditions;
	}
	
	/**
	 * 指令下发获取最后位置
	 * 
	 */
	public Position getPositionOrder(String commandSn){
		String positionStr = (String)mcc.get(commandSn+"uback");
	    if(positionStr==null){
	        return null;
	    }
	    String[] strs = positionStr.split("~");
	    
	    if(strs.length<10){
	    	return null;
	    }
	    Position position=new Position();
	    //unitID
	    if(!"".equals(strs[0]) && strs[0]!=null && !strs[0].equals("null")){
	    	position.setUnitId(strs[0]);
	    }
	    //流水号  strs[1]
	    //定位状态
	    if(!"".equals(strs[2]) && strs[2]!=null && !strs[2].equals("null")){
	    	position.setLocationState(strs[2]);
	    }
	    //纬度
	    if(!"".equals(strs[3]) && strs[3]!=null && !strs[3].equals("null")){
	    	position.setLat(strs[3]);
	    }
	    //经度
	    if(!"".equals(strs[4]) && strs[4]!=null && !strs[4].equals("null")){
	    	position.setLon(strs[4]);
	    }
	    //速度
	    if(!"".equals(strs[5]) && strs[5]!=null && !strs[5].equals("null")){
	    	position.setSpeed(strs[5]);
	    }
		//航向
	    if(!"".equals(strs[6]) && strs[6]!=null && !strs[6].equals("null")){
	    	position.setDirection(strs[6]);
	    }
		//gps时间
	    if(!"".equals(strs[7]) && strs[7]!=null && !strs[7].equals("null")){
	    	position.setGpsTime(strs[7]);
	    }
	    //车辆状态
	    if(!"".equals(strs[8]) && strs[8]!=null && !strs[8].equals("null")){
	    	position.setVehicleState(strs[8].replace("熄火", "ACC关").replace("点火", "ACC开"));
	    }
	    //参考位置
	    if(!"".equals(strs[9]) && strs[9]!=null && !strs[9].equals("null")){
	    	position.setReferencePosition(strs[9]);
	    }
	    return position;
	}
	
	/**
	 * 获取最后位置
	 * 0622130223~112.897156~28.227236~0.0~0~2013-04-19 02:03:31~CAN总线故障,开盖,点火,备电断电,~null~null~null~null~1
	 */
	public Position getPosition(String unitId){		
	    String positionStr = (String)mcc.get(unitId+"gps");
	    if(positionStr==null){
	        return null;
	    }
	    String[] strs = positionStr.split("~");
	    if(strs.length<12){
            return null;
        }	    
	    Position position = new Position();
	    if(strs[0]!="" && strs[0]!=null && !strs[0].equals("null")){
	        position.setUnitId(strs[0]);
	    }
	    if(strs[1]!="" && strs[1]!=null && !strs[1].equals("null")){
            position.setLon(strs[1]);
        }
	    if(strs[2]!="" && strs[2]!=null && !strs[2].equals("null")){
            position.setLat(strs[2]);
        }
	    if(strs[3]!="" && strs[3]!=null && !strs[3].equals("null")){
            position.setSpeed(strs[3]);
        }
	    if(strs[4]!="" && strs[4]!=null && !strs[4].equals("null")){
            position.setDirection(strs[4]);
        }
	    if(strs[5]!="" && strs[5]!=null && !strs[5].equals("null")){
            position.setGpsTime(strs[5]);
        }
	    if(strs[6]!="" && strs[6]!=null && !strs[6].equals("null")){
            position.setVehicleState(strs[6].replace("熄火", "ACC关").replace("点火", "ACC开"));
        }
	    if(strs[7]!="" && strs[7]!=null && !strs[7].equals("null")){
            position.setProvince(strs[7]);
        }
	    if(strs[8]!="" && strs[8]!=null && !strs[8].equals("null")){
            position.setCity(strs[8]);
        }
	    if(strs[9]!="" && strs[9]!=null && !strs[9].equals("null")){
            position.setCounty(strs[9]);
        }
	    if(strs[10]!="" && strs[10]!=null && !strs[10].equals("null")){
            position.setReferencePosition(strs[10]);
        }
	    if(strs[11]!="" && strs[11]!=null && !strs[11].equals("null")){
            position.setLocationState(strs[11]);
        }
	    if(strs[12]!="" && strs[12]!=null && !strs[12].equals("null")){
            position.setAccon(Integer.valueOf(strs[12]));
        }
	    if(strs[13]!="" && strs[13]!=null && !strs[13].equals("null")){
            position.setAlarmFlag(Integer.valueOf(strs[13]));
        }
	    if(strs[14]!="" && strs[14]!=null && !strs[14].equals("null")){
            position.setNowTime(strs[14]);
        }
	    return position;
	    
	}
	
	public GatewayBack getGatewayBack(Integer commandSn){
	    String gatewayBackStr = (String)mcc.get(commandSn+"gback");
        if(gatewayBackStr==null){
            return null;
        }
        String[] strs = gatewayBackStr.split("~");
        if(strs.length<3){
            return null;
        }
        GatewayBack gatewayBack = new GatewayBack();
        if(strs[0]!="" && strs[0]!=null && !strs[0].equals("null")){
            gatewayBack.setUnitId(strs[0]);
        }
        if(strs[1]!="" && strs[1]!=null && !strs[1].equals("null")){
            gatewayBack.setCommandSn(strs[1]);
        }
        if(strs[2]!="" && strs[2]!=null && !strs[2].equals("null")){
            gatewayBack.setIsSuccess(strs[2]);
        }
        return gatewayBack;
	}
	
	/**
	 * @Title:getUnitBack
	 * @Description:获取终端回应
	 * @param commandSn
	 * @return
	 * @throws
	 */
	public UnitBack getUnitBack(Integer commandSn){
	    String unitBackStr = (String)mcc.get(commandSn+"uback");
        if(unitBackStr==null){
            return null;
        }
        String[] strs = unitBackStr.split("~");
        if(strs.length<7){
            return null;
        }
        UnitBack unitBack = new UnitBack();
        if(strs[0]!="" && strs[0]!=null && !strs[0].equals("null")){
            unitBack.setUnitId(strs[0]);
        }
        if(strs[1]!="" && strs[1]!=null && !strs[1].equals("null")){
            unitBack.setCommandSn(strs[1]);
        }
        if(strs[2]!="" && strs[2]!=null && !strs[2].equals("null")){
            unitBack.setUnitback(strs[2]);
        }
        if(strs[3]!="" && strs[3]!=null && !strs[3].equals("null")){
            unitBack.setIp(strs[3]);
        }
        if(strs[4]!="" && strs[4]!=null && !strs[4].equals("null")){
            unitBack.setPort(strs[4]);
        }
        if(strs[5]!="" && strs[5]!=null && !strs[5].equals("null")){
            unitBack.setApn(strs[5]);
        }
        if(strs[6]!="" && strs[6]!=null && !strs[6].equals("null")){
            unitBack.setNumber(strs[6]);
        }
        return unitBack;
	}
	
	/**
	 * @Title:getUnitPant
	 * @Description:获取心跳设置终端回应（A锁、B锁、使能）
	 * @param commandSn
	 * @return
	 * @throws
	 */
	public UnitBack getUnitPant(String commandSn){
	    String unitBackStr = (String)mcc.get(commandSn+"uback");
        if(unitBackStr==null){
            return null;
        }
        String[] strs = unitBackStr.split("~");
        if(strs.length<3){
            return null;
        }
        UnitBack unitBack = new UnitBack();
        if(strs[0]!="" && strs[0]!=null && !strs[0].equals("null")){
            unitBack.setUnitId(strs[0]);
        }
        if(strs[1]!="" && strs[1]!=null && !strs[1].equals("null")){
            unitBack.setCommandSn(strs[1]);
        }
        if(strs[2]!="" && strs[2]!=null && !strs[2].equals("null")){
            unitBack.setPantStatus(strs[2]);
        }
       
        return unitBack;
	}
	
	//移除
	public void remove(String key){
	    mcc.delete(key);
	}
	public static void main(String[] args) throws TimeoutException, InterruptedException{
	    MemCache memCachedFactory = new MemCache();
	    MemcachedClient mcc = getMemcached();
		//112.897387~28.227823~169.20~0.00~1~~2012-12-14 09:54:41~0~0~1358987477,
		mcc.get("0622130223gps");
		//终端ID~经度~纬度~速度~方向~GPS时间~车辆状态~省~市~县~参考位置~定位状态
		/*mcc.set("0622130223gps",30000,
			"0622130223~112.897156~28.227236~0.0~0~2013-04-19 02:03:31~CAN总线故障,开盖,点火,备电断电,~null~null~null~null~1");
		*/
		//指令回应
		//终端ID~消息流水号~成功标识
		/*mcc.set("121gback",30000,
			"0622130223~101~00");*/
		System.out.println("指令回应."+mcc.get("176gback")+","+mcc.get("176uback"));
	}

}
