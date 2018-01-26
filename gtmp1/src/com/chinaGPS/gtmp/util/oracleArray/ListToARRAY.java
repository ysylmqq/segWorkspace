package com.chinaGPS.gtmp.util.oracleArray;

import java.sql.SQLException;
import java.util.ArrayList;

import oracle.jdbc.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.support.nativejdbc.C3P0NativeJdbcExtractor;

import com.chinaGPS.gtmp.entity.UnitPOJO;
import com.chinaGPS.gtmp.entity.VehiclePOJO;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @Package:com.chinaGPS.gtmp.util.oracleArray
 * @ClassName:ListToARRAY
 * @Description:
 * @author:lxj
 * @date:Dec 18, 2012 11:27:40 AM
 */
public class ListToARRAY {
	public static ARRAY getArray(String oracleType, String oracleArray, ArrayList<Object> objList) throws SQLException{
		//获得数据库的连接
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml"); 
		C3P0NativeJdbcExtractor cp30NativeJdbcExtractor = new C3P0NativeJdbcExtractor();
		OracleConnection con = (OracleConnection) cp30NativeJdbcExtractor.getNativeConnection(((ComboPooledDataSource) ctx.getBean("dataSource")).getConnection());
		
		ARRAY list = null;
		if (objList != null && objList.size() > 0) {
			StructDescriptor structDesc = new StructDescriptor(oracleType,con);
			STRUCT[] structs = new STRUCT[objList.size()];
			Object[] result = new Object[0];
			for (int i = 0; i < objList.size(); i++) {
				Object o = objList.get(i);
				if (o instanceof UnitPOJO) {
					UnitPOJO unit = (UnitPOJO) o;
					result = new Object[16];
					result[0] = unit.getUnitId();
					result[1] = unit.getSupperierSn(); 
					result[2] = unit.getUnitSn();
					result[3] = unit.getUnitTypeSn();
					result[4] = unit.getHardwareVersion();
					result[5] = unit.getSoftwareVersion();
					result[6] = unit.getSimNo();
					result[7] = unit.getMaterialNo();
					result[8] = unit.getSteelNo();
					result[9] = unit.getProductionDate();
					result[10] = unit.getRegistedTime();
					result[11] = unit.getUserId();
					result[12] = unit.getState();
					result[13] = unit.getRemark();
					result[14] = unit.getIsValid();
					result[15] = unit.getStamp();
					
				} else if (o instanceof VehiclePOJO) {
					VehiclePOJO vehicle = (VehiclePOJO) o;
					
					result = new Object[13];
					result[0] = vehicle.getVehicleId();
					result[1] = vehicle.getUnitId();
					result[2] = vehicle.getVehicleDef(); 
					result[3] = vehicle.getTypeId();
					result[4] = vehicle.getModelId();
					result[5] = vehicle.getFixMan();
					result[6] = vehicle.getFixDate();
					result[7] = vehicle.getStatus();
					result[8] = vehicle.getStamp();
					result[9] = vehicle.getStatusName();
					result[10] = vehicle.getTypeName();
					result[11] = vehicle.getModelName();
					result[12] = vehicle.getMaterialNo();
				}
				structs[i] = new STRUCT(structDesc,con,result);
			}
			ArrayDescriptor desc = ArrayDescriptor.createDescriptor(oracleArray,con);
			list = new ARRAY(desc,con,structs);
		} else {
			ArrayDescriptor desc = ArrayDescriptor.createDescriptor(oracleArray,con);
			STRUCT[] structs = new STRUCT[0];
			list = new ARRAY(desc,con,structs);
		}
		return list;
	};
/*	public static ARRAY getArray(String oracleType, String oracleList, List<Object> objList) throws BeansException, SQLException, SecurityException, NoSuchFieldException {
		ApplicationContext ctx = new FileSystemXmlApplicationContext("WebRoot/WEB-INF/spring/applicationContext-*.xml"); 
		C3P0NativeJdbcExtractor cp30NativeJdbcExtractor = new C3P0NativeJdbcExtractor();
		OracleConnection con = (OracleConnection) cp30NativeJdbcExtractor.getNativeConnection(((ComboPooledDataSource) ctx.getBean("dataSource")).getConnection());
		
		ARRAY oracleArray = null;
		if (objList != null && objList.size() > 0) {
			int size = objList.size();
			StructDescriptor structDesc = new StructDescriptor(oracleType,con);
			STRUCT[] structs = new STRUCT[size];
			Object[] result = new Object[0];
			for (int i = 0; i < size; i++) {
				Object o = objList.get(i);
				Class clazz = o.getClass();
				Field[] fields = clazz.getDeclaredFields();
				result = new Object[fields.length-1];
				for (int j = 0; j < fields.length-1; j++) {
					result[j] = clazz.getDeclaredField(fields[j+1].getName());
				}
				structs[i] = new STRUCT(structDesc,con,result);
			}
			ArrayDescriptor desc = ArrayDescriptor.createDescriptor(oracleList,con);
			oracleArray = new ARRAY(desc,con,structs);
		} else {
			ArrayDescriptor desc = ArrayDescriptor.createDescriptor(oracleList,con);
			STRUCT[] structs = new STRUCT[0];
			oracleArray = new ARRAY(desc,con,structs);
		}
		return oracleArray;
		
	}*/
	
}

