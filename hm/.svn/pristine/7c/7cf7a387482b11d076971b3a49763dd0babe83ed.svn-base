package com.gboss.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;


/**
 * 将list<String[]>反射成具体对象集合
 * @author lic
 *
 */
public class ReflectUtils {
	/**
	 * 将excel文件内容转换为对象(设置默认值、转换属性字段状态)
	 * @param dataList	原始数据对象
	 * @param cols		取值map设置
	 * @param defVals	默认值map设置
	 * @param transfMap	转换map设置
	 * @param obj		要转换的对象类实体
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List buildListForImport(List<String[]> dataList, Map<String, String> cols, Map<String, Object> defVals, 
			Map<String, Map<String, String>> transfMap,
			Object obj){
		List reList = new ArrayList();
		try{
			Field[] fields = obj.getClass().getDeclaredFields();
			for(String[] strs : dataList){	//遍历
				Object obj2 = copy(obj);	//克隆一个空对象,往克隆对象赋值
				for(Field field : fields){
					String method = field.getName();
					if(StringUtils.equals(method, "serialVersionUID")){
						continue;
					}
					method = method.substring(0, 1).toUpperCase() + method.substring(1);
					String type = field.getType().getName();
					String colVal = null;
					if(cols.get(field.getName()) != null){	//从excel取值
						Integer i = Integer.parseInt(cols.get(field.getName()).toString());
						colVal = strs[i-1].trim();//sheet中第一个位置对于java中第0位
						if(transfMap.get(field.getName()) != null){	//特殊处理，将匹配结果转换为相应的值
							Map<String, String> subTransfMap = transfMap.get(field.getName());
							colVal = subTransfMap.get(colVal);
						}
					}else if(defVals.get(field.getName()) != null){	//设置默认值
						colVal = defVals.get(field.getName()).toString();
					}
					if(StringUtils.isNotBlank(colVal)){	//只有在值存在的条件下才做反射处理
						if(StringUtils.equals(type, "java.lang.String")){
							Method me = obj.getClass().getDeclaredMethod("set"+method, new Class[]{String.class});
							me.invoke(obj2, new Object[]{colVal});
							continue;
						}
						if(StringUtils.equals(type, "java.lang.Long")){
							Method me = obj.getClass().getDeclaredMethod("set"+method, new Class[]{Long.class});
							me.invoke(obj2, new Object[]{Long.parseLong(colVal)});
							continue;
						}
						if(StringUtils.equals(type, "java.lang.Integer")){
							Method me = obj.getClass().getDeclaredMethod("set"+method, new Class[]{Integer.class});
							me.invoke(obj2, new Object[]{Integer.parseInt(colVal)});
							continue;
						}
						if(StringUtils.equals(type, "java.lang.Double")){
							Method me = obj.getClass().getDeclaredMethod("set"+method, new Class[]{Double.class});
							me.invoke(obj2, new Object[]{Double.parseDouble(colVal)});
							continue;
						}
						if(StringUtils.equals(type, "java.lang.Float")){
							Method me = obj.getClass().getDeclaredMethod("set"+method, new Class[]{Float.class});
							me.invoke(obj2, new Object[]{Float.parseFloat(colVal)});
							continue;
						}
						if(StringUtils.equals(type, "java.util.Date")){
							Method me = obj.getClass().getDeclaredMethod("set"+method, new Class[]{Date.class});
							me.invoke(obj2, new Object[]{DateUtil.parse(colVal, DateUtil.YMD_DASH_IMPORT)});
							continue;
						}
					}
				}
				reList.add(obj2);
			}
		}catch(NoSuchMethodException e){
			e.printStackTrace();
		}catch(SecurityException e){
			e.printStackTrace();
		}catch(NumberFormatException e){
			e.printStackTrace();
		}catch(IllegalAccessException e){
			e.printStackTrace();
		}catch(IllegalArgumentException e){
			e.printStackTrace();
		}catch(InvocationTargetException e){
			e.printStackTrace();
		}
		return reList;
	}
	
	/**
	 * object克隆一个相同的对象
	 * @param object
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object copy(Object object){
		Class classType = object.getClass();//获得对象的类型		
		//通过默认的构造函数创建一个新的对象
		Object objectCopy = null;
		try{
			objectCopy = classType.getConstructor(new Class[] {}).newInstance(new Object[] {});
			//获得对象的所有属性
			Field fields[] = classType.getDeclaredFields();
			
		    for(int i = 0; i < fields.length; i++) {
		    	Field field = fields[i];
		    	
		    	String fieldName = field.getName();
				if(StringUtils.equals(fieldName, "serialVersionUID")){
					continue;
				}
		    	String firstLetter = fieldName.substring(0,1).toUpperCase();
		    	//获得和属性对应的getXXX（）方法的名字
		    	String getMethodName = "get" + firstLetter + fieldName.substring(1);
		    	//获得和属性对应的setXXX()方法的名字
		    	String setMethodName = "set" + firstLetter + fieldName.substring(1);
		    	
		    	//获得和属性对应的getXXX()方法
		    	Method getMethod = classType.getMethod(getMethodName, new Class[]{});
		    	//获得和属性对应的setXXX()方法
		    	Method setMethod = classType.getMethod(setMethodName, new Class[]{field.getType()});
		    	
		    	//调用原对象的getXXX()方法
		    	Object value = getMethod.invoke(object, new Object[]{});
		    	//调用复制对象的setXXX()方法
		    	setMethod.invoke(objectCopy, new Object[]{value});
		    }
		}catch(IllegalArgumentException e){
			e.printStackTrace();
		}catch(SecurityException e){
			e.printStackTrace();
		}catch(InstantiationException e){
			e.printStackTrace();
		}catch(IllegalAccessException e){
			e.printStackTrace();
		}catch(InvocationTargetException e){
			e.printStackTrace();
		}catch(NoSuchMethodException e){
			e.printStackTrace();
		}
	    return objectCopy;
	}
}
