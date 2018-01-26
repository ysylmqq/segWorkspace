package test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import oracle.jdbc.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.support.nativejdbc.C3P0NativeJdbcExtractor;

import com.chinaGPS.gtmp.entity.UnitPOJO;
import com.chinaGPS.gtmp.service.IUnitService;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class TestProcAddBatch {
	/**
	 * @param args
	 */
	private static OracleConnection getConnection() throws SQLException {
		ApplicationContext ctx = new FileSystemXmlApplicationContext(
				"WebRoot/WEB-INF/spring/applicationContext-*.xml");
		C3P0NativeJdbcExtractor cp30NativeJdbcExtractor = new C3P0NativeJdbcExtractor();
		OracleConnection connection = (OracleConnection) cp30NativeJdbcExtractor
				.getNativeConnection(((ComboPooledDataSource) ctx
						.getBean("dataSource")).getConnection());
		return connection;
	}

	private static ARRAY getArray(Connection con, String oracleList,
			ArrayList<UnitPOJO> objList) throws SQLException {
		ARRAY list = null;
		if (objList != null && objList.size() > 0) {
			StructDescriptor structDesc = new StructDescriptor("GPSINFO", con);
			STRUCT[] structs = new STRUCT[objList.size()];
			Object[] result = new Object[0];
			for (int i = 0; i < objList.size(); i++) {
				result = new Object[8];
				result[0] = new String(((UnitPOJO) (objList.get(i)))
						.getMaterialNo());
				result[1] = new String(((UnitPOJO) (objList.get(i)))
						.getUnitSn());
				result[2] = new String(((UnitPOJO) (objList.get(i)))
						.getUnitTypeSn());
				result[3] = new String(((UnitPOJO) (objList.get(i))).getSimNo());
				result[4] = new String(((UnitPOJO) (objList.get(i)))
						.getSteelNo());
				result[5] = new String(((UnitPOJO) (objList.get(i)))
						.getSoftwareVersion());
				result[6] = ((UnitPOJO) (objList.get(i))).getUserId();
				result[7] = new Long(((UnitPOJO) (objList.get(i))).getRemark());
				structs[i] = new STRUCT(structDesc, con, result);
			}
			ArrayDescriptor desc = ArrayDescriptor.createDescriptor(oracleList,
					con);
			list = new ARRAY(desc, con, structs);
		} else {
			ArrayDescriptor desc = ArrayDescriptor.createDescriptor(oracleList,
					con);
			STRUCT[] structs = new STRUCT[0];
			list = new ARRAY(desc, con, structs);
		}
		return list;
	};

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		// 批量上传测试
		ApplicationContext ctx = new FileSystemXmlApplicationContext(
				"classpath:spring/applicationContext-*.xml");
		IUnitService service = (IUnitService) ctx.getBean("unitServiceImpl");
		List<UnitPOJO> units = new ArrayList<UnitPOJO>();
		Long number = 201212145001L, phone = 18627110010L;
		for (int i = 0; i < 10; i++) {
			StringBuffer buff = new StringBuffer("TGL20");
			UnitPOJO unit = new UnitPOJO();
			unit.setUnitId(i + "");
			unit.setMaterialNo("A" + (i + 100));
			unit.setSupperierSn("sa");
			// 重复的Unit_sn
			// unit.setGpsSn("TGL20-201212144000");
			unit.setUnitSn("TGL20-" + number);
			unit.setUnitTypeSn("2");
			unit.setHardwareVersion("v2");
			unit.setSoftwareVersion("v3");
			// 重复的SIM卡号
			// unit.setSimNo("18627112001");
			unit.setSimNo("" + phone);
			unit.setMaterialNo("B00" + i);
			unit.setSteelNo("" + number);
			unit
					.setProductionDate(new java.sql.Date(System
							.currentTimeMillis()));// 无时分秒
			// unit.setProductionDate(new
			// Timestamp(System.currentTimeMillis()));//有时分秒
			// unit.setProductionDate(new
			// java.sql.Date(System.currentTimeMillis()));
			// unit.setRegistedTime(new
			// java.sql.Date(System.currentTimeMillis()));
			unit.setUserId(1l);
			unit.setState(1);
			unit.setRemark("rem" + i);
			unit.setIsValid(0);
			// unit.setStamp(new java.sql.Date(System.currentTimeMillis()));
			units.add(unit);
			number++;
			phone++;
			// System.out.println("{"+unit.getMaterialNo()+","+unit.getGpsSn()+","+unit.getGpsType()+","+unit.getSimNo()+","+unit.getSteelNo()+","+unit.getSoftwareVersion()+","+unit.getRegister()+","+unit.getSupplierId()+"}");
		}
		HashMap<String, Object> result = service.addUnits(units);
		System.out.println(result.get("msg") + "," + result.get("count"));

	}

}
