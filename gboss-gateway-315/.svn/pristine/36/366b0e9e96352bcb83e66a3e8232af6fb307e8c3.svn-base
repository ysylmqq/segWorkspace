package cc.chinagps.gateway.unit.kelong.upload.bean;

import java.util.concurrent.ConcurrentHashMap;
/**
 * 
* @ClassName: DataStorage
* @Description: 缓存类
* @author dy
* @date 2017年5月23日 下午12:20:18
*
 */
public class DataStorage {
	//can数据缓存map
    private static ConcurrentHashMap<String,KeLongCANInfo> canMap = new ConcurrentHashMap<String,KeLongCANInfo>();
    //baseStation数据缓存map
    private static ConcurrentHashMap<String,KeLongBaseStationInfo> baseStationMap = new ConcurrentHashMap<String,KeLongBaseStationInfo>();
    
    public static ConcurrentHashMap<String,KeLongCANInfo> getCanMap(){
    	if(canMap==null){
    		canMap = new ConcurrentHashMap<String,KeLongCANInfo>();
    	}
    	return canMap;
    }
  
    public static ConcurrentHashMap<String,KeLongBaseStationInfo> getBaseStationMap(){
    	if(baseStationMap==null){
    		baseStationMap = new ConcurrentHashMap<String,KeLongBaseStationInfo>();
    	}
    	return baseStationMap;
    }
}
