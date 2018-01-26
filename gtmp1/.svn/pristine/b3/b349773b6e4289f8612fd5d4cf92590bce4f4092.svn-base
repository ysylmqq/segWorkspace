package com.chinaGPS.gtmp.entity;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

public class DynamicMalfunctionPOJO {
    /** 
     * 实体Object 
     */  
   private  Object object = null;
  
   /** 机械类型ID */
   private String typeId;
   /** 机械编号名称 */
   private String typeName;
   /** 日期 */
   private String dateLabel;
   /** 机械代号 */
   private String vehicleCode;
   /** 机械配置 */
   private String vehicleArg;
   /** 机械类型名称 */
   private String vehicleType;
   /** 动态列 */  
   private  BeanMap beanMap = null;  
 
   public DynamicMalfunctionPOJO() {  
       super();  
   }  
     
   @SuppressWarnings("unchecked")  
   public DynamicMalfunctionPOJO(Map propertyMap) {  
     this.object = generateBean(propertyMap);  
     this.beanMap = BeanMap.create(this.object);  
   }  
 
   /** 
     * 给bean属性赋值 
     * @param property 属性名 
     * @param value 值 
     */  
   public void setValue(String property, Object value) {  
     beanMap.put(property, value);  
   }  
 
   /** 
     * 通过属性名得到属性值 
     * @param property 属性名 
     * @return 值 
     */  
   public Object getValue(String property) {  
     return beanMap.get(property);  
   }  
 
   /** 
     * 得到该实体bean对象 
     * @return 
     */  
   public Object getObject() {  
     return this.object;  
   }  
 
   /** 
    * @param propertyMap 
    * @return 
    */  
   @SuppressWarnings("unchecked")  
   private Object generateBean(Map propertyMap) {  
     BeanGenerator generator = new BeanGenerator();  
     Set keySet = propertyMap.keySet();  
     for (Iterator i = keySet.iterator(); i.hasNext();) {  
      String key = (String) i.next();  
      generator.addProperty(key, (Class) propertyMap.get(key));  
     }  
     return generator.create();  
   }
   


public String getDateLabel() {
    return dateLabel;
}

public void setDateLabel(String dateLabel) {
    this.dateLabel = dateLabel;
}

public BeanMap getBeanMap() {
    return beanMap;
}

public void setBeanMap(BeanMap beanMap) {
    this.beanMap = beanMap;
}

public void setObject(Object object) {
    this.object = object;
}

public String getTypeId() {
    return typeId;
}

public void setTypeId(String typeId) {
    this.typeId = typeId;
}

public String getTypeName() {
    return typeName;
}

public void setTypeName(String typeName) {
    this.typeName = typeName;
}

public String getVehicleCode() {
	return vehicleCode;
}

public void setVehicleCode(String vehicleCode) {
	this.vehicleCode = vehicleCode;
}

public String getVehicleArg() {
	return vehicleArg;
}

public void setVehicleArg(String vehicleArg) {
	this.vehicleArg = vehicleArg;
}

public String getVehicleType() {
	return vehicleType;
}

public void setVehicleType(String vehicleType) {
	this.vehicleType = vehicleType;
}

}
