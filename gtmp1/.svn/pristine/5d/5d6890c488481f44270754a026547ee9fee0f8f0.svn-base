package com.chinaGPS.gtmp.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CompositeQueryUtil {  
    
    public static Map<String, String> getColumns(){
    	Map<String, String> columns = new HashMap<String, String>();
    	columns.put("vehicleId","整机ID");
    	columns.put("vehicleDef","整机编号");
    	columns.put("typeName","机械型号");
    	columns.put("modelName","机械类型");
    	columns.put("fixMan","安装人");
    	columns.put("fixDate","终端安装时间");
    	columns.put("unitId","终端ID");
    	columns.put("unitSn","终端序列号");
    	columns.put("supperierName","终端供应商");
    	columns.put("materialNo","物料条码");
    	columns.put("simNo","SIM卡号");
    	columns.put("steelNo","钢号");
    	columns.put("unitType","终端类型");
    	columns.put("saleDate","销售时间");
    	columns.put("dealerName","经销商");
    	columns.put("ownerName","机主名称");
    	columns.put("ownerPhone","机主电话");
    	columns.put("position","定位");
    	columns.put("condition","历史工况");
    	columns.put("lon","经度");
    	columns.put("lat","纬度");
    	columns.put("speed","速度");
    	columns.put("direction","方向");
    	columns.put("gpsTime","GPS定位时间");
    	columns.put("vehicleState","机械状态");
    	columns.put("province","省");
    	columns.put("city","市");
    	columns.put("county","县");
    	columns.put("referencePosition","参考位置");
    	columns.put("locationState","是否定位");
    	columns.put("isLogin","是否在线");
    	columns.put("engineCoolantTemperature","发动机冷却液温度");
    	columns.put("engineCoolantLevel","发动机冷却液液位");
    	columns.put("engineOilPressure","发动机机油压力");
    	columns.put("engineFuelLevel","发动机燃油油位");
    	columns.put("engineSpeed","发动机转速");
    	columns.put("hydraulicOilTemperature","液压油温度");
    	columns.put("systemVoltage","系统电压");
    	columns.put("beforePumpMainPressure","挖掘机前泵主压压力");
    	columns.put("afterPumpMainPressure","挖掘机后泵主压压力");
    	columns.put("pilotPressure1","挖掘机先导压力1");
    	columns.put("pilotPressure2","挖掘机先导压力2");
    	columns.put("beforePumpPressure","挖掘机前泵负压压力");
    	columns.put("afterPumpPressure","挖掘机后泵负压压力");
    	columns.put("proportionalCurrent1","挖掘机比例阀电流1");
    	columns.put("proportionalCurrent2","挖掘机比例阀电流2");
    	columns.put("gear","挖机档位");
    	columns.put("totalWorkingHours","累计工作小时");
    	columns.put("highEngineCoolantTemperVal","发动机冷却液温度高报警设置值");
    	columns.put("lowEngineOilPressureAlarmValue","发动机机油压力低报警设置值");
    	columns.put("highVoltageAlarmValue","系统电压高报警设置值");
    	columns.put("lowVoltageAlarmValue","系统电压低报警设置值");
    	columns.put("highHydraulicOilTemperAlarmVal","液压油温高报警值");
    	columns.put("toothNumberValue","飞轮齿数设置值");
    	columns.put("monitorSoftwareCode","监控器（显示器）供应商内部软件代号");
    	columns.put("monitorYcSoftwareCode","监控器（显示器）玉柴内部软件版本号");
    	columns.put("controllerSoftwareCode","控制器供应商内部软件代号");
    	columns.put("controllerYcSoftwareCode","控制器玉柴内部软件代号");
    //	columns.put("productCode","产品编号");
    	columns.put("engineOilTemperature","发动机机油温度");
    	columns.put("faultCode","故障码");
    	columns.put("alock","A锁");
    	columns.put("block","B锁");
    	columns.put("clock","使能状态");
    	columns.put("isWork","是否工作");
    	columns.put("stamp","上报工况时间");
    	return columns;
    }
    public static Map<String, Object> transBean2Map(Object obj) {
        if(obj == null){  
            return null;  
        }          
        Map<String, Object> map = new HashMap<String, Object>();  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
  
                // 过滤class属性  
                if (!key.equals("class")) {  
                    // 得到property对应的getter方法  
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(obj);  
  
                    map.put(key, value);  
                }  
  
            }  
        } catch (Exception e) {  
            System.out.println("transBean2Map Error " + e);  
        }  
  
        return map;  
  
    }  
    
    public static String formatSpeed(Integer speed){
		  if(speed==null)
			return "--";
		 String result = speed.toString();
		 if(result.equals("")){
			 return "--";
		 }
		 else{
			 result = result.concat(ConditionUnit.SPEED.getValue());
		 }
		 
		return result;			
	}
    public static String formatSpeed(Float speed){
		  if(speed==null)
			return "--";
		 String result = speed.toString();
		 if( result.equals("")){
			 return "--";
		 }
		 else{
			 result = result.concat(ConditionUnit.SPEED.getValue());
		 }
		 
		return result;			
	}
    public static String formatDegree(Integer degree){
		  if(degree==null)
			return "--";
		 String result = degree.toString();
		 if(result.equals("0") || result.equals("")){
			 return "--";
		 }
		 else{
			 result = result.concat(ConditionUnit.DEGREE.getValue());
		 }
		 
		return result;			
	}
    public static String formatDegree(Float degree){
		  if(degree==null)
			return "--";
		 String result = degree.toString();
		 if(result.equals("0.0") || result.equals("0.00")|| result.equals("")){
			 return "--";
		 }
		 else{
			 result = result.concat(ConditionUnit.DEGREE.getValue());
		 }
		 
		return result;			
	}
    public static String formatLocation(Integer location){
    	if(location == null)
    		return "否";
    	String result = location.toString();
    	if(result.equals("1")){
    		return "是";
    	}else{
    		return "否";
    	}
    }
    public static String formatOnline(Integer online){
    	if(online == null)
    		return "否";
    	String result = online.toString();
    	if(result.equals("0")){
    		return "是";
    	}else{
    		return "否";
    	}
    }
    public static String formatTemperature(Integer temp){
    	if(temp == null)
    		return "--";
    	String result = temp.toString();
    	if(result.equals("0") || result.equals("")){
    		return "--";
    	}else{
			 result = result.concat(ConditionUnit.TEMPERATURE.getValue());
		}
    	
    	return result;
    }
    public static String formatTemperature(Float temp){
    	if(temp == null)
    		return "--";
    	String result = temp.toString();
    	if(result.equals("0.0") || result.equals("0.00") || result.equals("")){
    		return "--";
    	}else{
			 result = result.concat(ConditionUnit.TEMPERATURE.getValue());
		}
    	
    	return result;
    }
    public static String formatPercent(Float pencent){
    	if(pencent == null)
    		return "--";
    	String result = pencent.toString();
    	if(result.equals("0.0") || result.equals("0.00") || result.equals("")){
    		return "--";
    	}else{
			 result = result.concat(ConditionUnit.PERCENT.getValue());
		}
    	
    	return result;
    }
    public static String formatPercent(Integer pencent){
    	if(pencent == null)
    		return "--";
    	String result = pencent.toString();
    	if(result.equals("0") || result.equals("")){
    		return "--";
    	}else{
			 result = result.concat(ConditionUnit.PERCENT.getValue());
		}
    	
    	return result;
    }
    public static String formatPressure(Integer pressure){
    	if(pressure == null)
    		return "--";
    	String result = pressure.toString();
    	if(result.equals("0") || result.equals("")){
    		return "--";
    	}else{
			 result = result.concat(ConditionUnit.PRESSURE.getValue());
		}
    	
    	return result;
    }
    public static String formatPressure(Float pressure){
    	if(pressure == null)
    		return "--";
    	String result = pressure.toString();
    	if(result.equals("0.0") || result.equals("0.00") || result.equals("")){
    		return "--";
    	}else{
			 result = result.concat(ConditionUnit.PRESSURE.getValue());
		}
    	
    	return result;
    }
    public static String formatRotateSpeed(Integer speed){
    	if(speed == null)
    		return "--";
    	String result = speed.toString();
    	if(result.equals("0") || result.equals("")){
    		return "--";
    	}else{
			 result = result.concat(ConditionUnit.ROTATE_SPEED.getValue());
		}
    	
    	return result;
    }
    public static String formatVoltagePressure(Integer vp){
    	if(vp == null)
    		return "--";
    	String result = vp.toString();
    	if(result.equals("0") || result.equals("")){
    		return "--";
    	}else{
			 result = result.concat(ConditionUnit.VOLTAGE_PRESSURE.getValue());
		}
    	
    	return result;
    }
    public static String formatVoltagePressure(Float vp){
    	if(vp == null)
    		return "--";
    	String result = vp.toString();
    	if(result.equals("0.0") || result.equals("")){
    		return "--";
    	}else{
			 result = result.concat(ConditionUnit.VOLTAGE_PRESSURE.getValue());
		}
    	
    	return result;
    }
    public static String formatPumpPressure(Float pumppressure){
    	if(pumppressure == null)
    		return "--";
    	String result = pumppressure.toString();
    	if(result.equals("0.0") || result.equals("51.0") || result.equals("")){
    		return "--";
    	}else{
			 result = result.concat(ConditionUnit.PRESSURE2.getValue());
		}
    	
    	return result;
    }
    public static String formatPumpPressure(Integer pumppressure){
    	if(pumppressure == null)
    		return "--";
    	String result = pumppressure.toString();
    	if(result.equals("0") || result.equals("51") || result.equals("")){
    		return "--";
    	}else{
			 result = result.concat(ConditionUnit.PRESSURE2.getValue());
		}
    	
    	return result;
    }
    public static String formatCurrent(Integer current){
    	if(current == null)
    		return "--";
    	String result = current.toString();
    	if(result.equals("0") || result.equals("65535") || result.equals("")){
    		return "--";
    	}else{
			 result = result.concat(ConditionUnit.CURRENT.getValue());
		}
    	
    	return result;
    }
    
}
