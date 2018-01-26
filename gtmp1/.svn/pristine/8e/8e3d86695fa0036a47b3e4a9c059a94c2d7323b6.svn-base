package com.chinaGPS.gtmp.util;
/*
 * 历史工况导出数据格式化
 */
public class ConditionDispose {
	//动机冷却液温度
	public static String formatTemp(Integer temp){
		  if(temp==null)
			return "--";
		 String result = temp.toString();
		 if(/*result.equals("110")  ||*/ result.equals("")){
			 return "--";
		 }
		 else{
			 result = result.concat(ConditionUnit.TEMPERATURE.getValue());
		 }
		 
		return result;			
	}
	
	//发动机冷却液液位   发动机燃油油位
	public static String formatLevel(Float level){
		 if(level==null)
				return "--";
			 String result = level.toString();
			 if(result.equals("255.0") || result.equals("255.00") || result.equals("0.00") || result.equals("0.0") || result.equals("")){
				 return "--";
			 }
			 else{
				 result = result.concat(ConditionUnit.PERCENT.getValue());
			 }
			 
			return result;			
		}
	public static String formatLevel(Integer level){
		 if(level==null)
				return "--";
			 String result = level.toString();
			 if(result.equals("255") || result.equals("0") || result.equals("")){
				 return "--";
			 }
			 else{
				 result = result.concat(ConditionUnit.PERCENT.getValue());
			 }
			 
			return result;			
		}
	
	//发动机机油压力   发动机机油压力低报警值
	public static String formatOilPressure(Float op){                       
		 if(op==null)
				return "--";
			 String result = op.toString();
			 if(result.equals("0.0") ||result.equals("0.00") || result.equals("")){
				 return "--";
			 }
			 else{
				 result = result.concat(ConditionUnit.PRESSURE.getValue());
			 }
			 
			return result;			
		}
	public static String formatOilPressure(Integer op){                       
		 if(op==null)
				return "--";
			 String result = op.toString();
			 if(result.equals("0") || result.equals("")){
				 return "--";
			 }
			 else{
				 result = result.concat(ConditionUnit.PRESSURE.getValue());
			 }
			 
			return result;			
		}
	
	//发动机转速
	public static String formatSpeed(Integer speed){                       
		 if(speed==null)
				return "--";
			 String result = speed.toString();
			 if(result.equals("0") || result.equals("")){
				 return "--";
			 }
			 else{
				 result = result.concat(ConditionUnit.ROTATE_SPEED.getValue());
			 }
			 
			return result;			
		}
	
	//液压油温度
	public static String formatHydTemperature(Integer hydt){                       
		if(hydt==null)
				return "--";
			String result = hydt.toString();
			if(result.equals("0") || result.equals("")){
				return "--";
			}
			else{
				result = result.concat(ConditionUnit.TEMPERATURE.getValue());
			}
				 
			return result;			
		}

	//系统电压   系统电压高报警设置值   系统电压低报警设置值
	public static String formatVoltagePressure(Integer vp){                       
		if(vp==null)
				return "--";
			String result = vp.toString();
			if(result.equals("0") || result.equals("")){
				return "--";
			}
			else{
				result = result.concat(ConditionUnit.VOLTAGE_PRESSURE.getValue());
			}
				 
			return result;			
		}
	public static String formatVoltagePressure(Float vp){                       
		if(vp==null)
				return "--";
			String result = vp.toString();
			if(result.equals("0.00") ||result.equals("0.0") || result.equals("")){
				return "--";
			}
			else{
				result = result.concat(ConditionUnit.VOLTAGE_PRESSURE.getValue());
			}
				 
			return result;			
		}

	//挖掘机前泵主压压力   挖掘机后泵主压压力
	public static String formatPumpPressure(Float pump){                       
		if(pump==null)
				return "--";
			String result = pump.toString();
			if(result.equals("0.00") ||result.equals("0.0")||result.equals("51")||result.equals("51.0") || result.equals("")){
				return "--";
			}
			else{
				result = result.concat(ConditionUnit.PRESSURE2.getValue());
			}
				 
			return result;			
		}
	public static String formatPumpPressure(Integer pump){                       
		if(pump==null)
				return "--";
			String result = pump.toString();
			if(result.equals("0") || result.equals("")){
				return "--";
			}
			else{
				result = result.concat(ConditionUnit.PRESSURE2.getValue());
			}
			return result;			
		}
	
	//挖掘机先导压力1   挖掘机先导压力2   挖掘机前泵负压压力    挖掘机后泵负压压力
	public static String formatPilotPressure(Float pilot){                       
		if(pilot==null)
				return "--";
			String result = pilot.toString();
			if(result.equals("0.00") || result.equals("0.0") || result.equals("7.65") || result.equals("")){
				return "--";
			}
			else{
				result = result.concat(ConditionUnit.PRESSURE2.getValue());
			}
			return result;			
		}
	public static String formatPilotPressure(Integer pilot){                       
		if(pilot==null)
				return "--";
			String result = pilot.toString();
			if(result.equals("0") || result.equals("")){
				return "--";
			}
			else{
				result = result.concat(ConditionUnit.PRESSURE2.getValue());
			}
			return result;			
		}
	
	//挖掘机比例阀电流1    挖掘机比例阀电流2
	public static String formatCurrent(Integer current){                       
		if(current==null)
				return "--";
			String result = current.toString();
			if(result.equals("0") ||result.equals("65535") || result.equals("")){
				return "--";
			}
			else{
				result = result.concat(ConditionUnit.CURRENT.getValue());
			}
			return result;			
		}
	
	//挖机档位
	public static String formatGear(Integer gear){                       
		if(gear==null)
				return "--";
			String result = gear.toString();
			if(result.equals("0") || result.equals("")){
				return "--";
			}
			return result;			
		}
	
	//发动机冷却液温度高报警值    液压油温高报警值   发动机机油温度
		public static String formatOilTemper(Integer ot){                       
			if(ot==null)
					return "--";
				String result = ot.toString();
				if(result.equals("0") || result.equals("")){
					return "--";
				}
				else{
					result = result.concat(ConditionUnit.TEMPERATURE.getValue());
				}
				return result;			
			}
		public static String formatOilTemper(Float ot){                       
			if(ot==null)
					return "--";
				String result = ot.toString();
				if(result.equals("0.0") ||result.equals("0.00") || result.equals("")){
					return "--";
				}
				else{
					result = result.concat(ConditionUnit.TEMPERATURE.getValue());
				}
				return result;			
			}
	
	//故障代码
	public static String formatFaultCode(Integer fcode){
		String result = fcode.toString();
		if(result.equals("65535")){
			return "--";
		}
		return result;				
	}
	/*public static String format0(Integer var){
		String result = var.toString();
		if(var == 0){
			result = "--";
		}
		return result;			
	}
	public static String format0(Float var){
		String result = var.toString();
		if(var == 0){
			result = "--";
		}
		return result;			
	}*/
	
	//是否工作
	public static String isWorking(int engineSpeed,Float engineOilPressure,Float systemVoltage){
		int i=0;
		if(engineSpeed>600){
			i++;
		}
		if(engineOilPressure>0.1){
			i++;
		}
		if((systemVoltage>26.1&&systemVoltage<29)||(systemVoltage>12.5&&systemVoltage<15)){
			i++;
		}
		if(i>1){
			return "是";
		}else{
			return "否";
		}
	}
	
}
