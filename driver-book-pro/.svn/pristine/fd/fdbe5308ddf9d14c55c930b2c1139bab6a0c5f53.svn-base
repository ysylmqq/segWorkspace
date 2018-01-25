package test;

import java.util.HashMap;
import java.util.Map;

public class VehicleUnitTest extends BaseUnitTest {
	
	public static void showTest(Scope scope) throws Exception {
		Map<String ,Object> params = new HashMap<String, Object>();
		params.put("vehicleId", "57882");
		testAPI(scope, "/vehicle/show", params);
	}
	
	public static void editTest(Scope scope) throws Exception {
		Map<String ,Object> params = new HashMap<String, Object>();
		params.put("customerId", "38802");
		params.put("vehicleId", "57647");
		params.put("registerDate", "2015-01-07");
		params.put("oilGrade", "0");
		params.put("price", "0");
		params.put("insuranceId", 5);
		params.put("vlBdate", "2015-01-07");
		params.put("subcoNo", 201);
		params.put("customerName", "海马测试02");
		testAPI(scope, "/vehicle/edit", params);
	}
	
	public static void listTest(Scope scope) throws Exception {
		Map<String ,Object> params = new HashMap<String, Object>();
		params.put("apiVersion", "1.0");
		params.put("appVersion", "2015012202");
		params.put("origin", 1);
		params.put("deviceType", 0);
		params.put("opId", 66286);
		testAPI(scope, "/vehicle/list", params);
	}
	
	public static void verifyServPwdTest(Scope scope) throws Exception {
		Map<String ,Object> params = new HashMap<String, Object>();
		params.put("apiVersion", "1.0");
		params.put("appVersion", "5.0.2");
		params.put("origin", 1);
		params.put("deviceType", 1);
		params.put("vehicleId", "714");
		params.put("servicePwd", "120530");
		testAPI(scope, "/vehicle/servpwd/verify", params);
	}
	
	public static void barcodeTest(Scope scope) throws Exception {
		Map<String ,Object> params = new HashMap<String, Object>();
		params.put("imei", "864264021750044");
		params.put("barcode", "0203443F41521000783");
		testAPI(scope, "/vehicle/barcode", params);
	}
	
	public static void vinTest(Scope scope) throws Exception {
		Map<String ,Object> params = new HashMap<String, Object>();
		params.put("imei", "864264021750044");
		params.put("vin", "LMVAFLFC5FA000530");
		testAPI(scope, "/vehicle/vin", params);
	}
	
	public static void main(String[] args) throws Exception {
		showTest(Scope.Gboss);
//		editTest(Scope.Local);
//		listTest(Scope.Gboss);
//		verifyServPwdTest(Scope.Local);
//		barcodeTest(Scope.Local);
//		vinTest(Scope.Local);
//		historyquery(Scope.Local);
	}
}
