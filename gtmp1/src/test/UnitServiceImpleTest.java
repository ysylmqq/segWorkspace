package test;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.chinaGPS.gtmp.entity.UnitPOJO;
import com.chinaGPS.gtmp.service.IUnitService;
import com.chinaGPS.gtmp.service.impl.UnitServiceImpl;
import com.chinaGPS.gtmp.util.page.PageSelect;

/**
 * @Package:test
 * @ClassName:UnitServiceImpleTest
 * @Description:批量插入service层测试类
 * @author:lxj
 * @date:Dec 14, 2012 2:50:32 PM
 * 
 */
public class UnitServiceImpleTest {

	private static IUnitService unitService;

	private static Logger log = Logger.getLogger(UnitServiceImpl.class);

	@BeforeClass
	public static void setUpBeforeClass() {
		try {
			ApplicationContext ctx = new FileSystemXmlApplicationContext(
					"classpath:spring/applicationContext-*.xml");
			unitService = (IUnitService) ctx.getBean("unitServiceImpl");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * @Test public void testsaveOrUpdate() throws Exception { UnitPOJO unit =
	 * new UnitPOJO(); unit.setUnitId("1001TGL30-201212111006");
	 * unit.setMaterialNo("A1"); unit.setGpsSn("TGL30-201212111006");
	 * unit.setGpsType("TGL30"); unit.setSimNo("15921867006");
	 * unit.setSteelNo("20121211700006"); unit.setSoftwareVersion("v3.0");
	 * unit.setSupplier("1001");
	 * 
	 * unitService.saveOrUpdate(unit); }
	 */

	@Test
	public void testaddUnitList() throws Exception {
		List<UnitPOJO> units = new ArrayList<UnitPOJO>();

		Long number = 201212143001L, phone = 18627110001L;
		for (int i = 0; i < 10; i++) {
			UnitPOJO unit = new UnitPOJO();
			unit.setMaterialNo("A" + (i + 100));
			// unit.setGpsSn("TGL20-"+number);
			// unit.setGpsType("TGL20");
			unit.setSimNo("" + phone);
			unit.setSteelNo("" + number);
			unit.setSoftwareVersion("v3.0");
			// unit.setRegister("lxj");
			// unit.setSupplierId(1001L);
			units.add(unit);
			number++;
			phone++;
		}
		unitService.addUnits(units);
	}

	public static void main(String[] args) throws Exception {
		setUpBeforeClass();
		UnitServiceImpleTest test = new UnitServiceImpleTest();
		// test.testadd();
		// test.testaddUnitList();
		PageSelect pageSelect = new PageSelect();
		pageSelect.setPage(1);
		pageSelect.setRows(17);
		UnitPOJO unit = new UnitPOJO();
		unit.setSimNo("123");
		unitService.getUnits(unit, pageSelect);
	}

}
