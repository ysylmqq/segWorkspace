/**
 * 
 */
package test;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.chinaGPS.gtmp.entity.VehiclePOJO;
import com.chinaGPS.gtmp.service.IVehicleService;
import com.chinaGPS.gtmp.service.impl.VehicleServiceImpl;

/**
 * @Package:test
 * @ClassName:VehicleServiceImplTest
 * @Description:机械信息批量插入service层测试类
 * @author:lxj
 * @date:Dec 18, 2012 10:52:01 AM
 *
 */
public class VehicleServiceImplTest {
	private static IVehicleService vehicleService;
	
	private static Logger log = Logger.getLogger(VehicleServiceImpl.class); 

	@BeforeClass
	public static void setUpBeforeClass() {
		try {
			List<String> files = new ArrayList<String>();
			files.add("WebRoot/WEB-INF/spring/applicationContext-*.xml");
			FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext(
					files.toArray(new String[0]));
			vehicleService = (VehicleServiceImpl) ctx
					.getBean("vehicleService");
		} catch (Exception e) {
			log.error("获得service层失败!" + e.getMessage());
		}
	}
	
	@Test
	public void testAddVehicles() throws Exception {
		List<VehiclePOJO> vehicles = new ArrayList<VehiclePOJO>();
		
		for (int i = 1; i < 10; i++) {
			VehiclePOJO vehicle = new VehiclePOJO();
			vehicle.setUnitId("1001TGL20-20121214" + (3000+i));
			vehicle.setVehicleDef("玉林V" + (100+i));
			vehicles.add(vehicle);
		}
		vehicleService.addVehicles(vehicles);
	}
	
	public static void main(String[] args) throws Exception {
		setUpBeforeClass();
		VehicleServiceImplTest test = new VehicleServiceImplTest();
		test.testAddVehicles();
	}

}

