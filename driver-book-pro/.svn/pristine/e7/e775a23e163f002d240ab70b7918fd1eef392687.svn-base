package test;

import java.util.HashMap;
import java.util.Map;

/**
 * 客户资料
 * @author wx
 *
 */
public class CustomerUnitTest extends BaseUnitTest {

	public static void showTest(Scope scope) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", 46862);
		testAPI(scope, "/customer/show", params);
	}
	public static void showBindTest(Scope scope) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("callLetter","13725595989");
		testAPI(scope, "/customer/bind/show", params);
	}
	
	public static void editTest(Scope scope) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", 1);
		params.put("linkmanId", 1);
		params.put("linkman", "");
		params.put("phone", "");
		params.put("driverId", null);
		params.put("driverName", "张三");
		params.put("drClass", "C1");
		params.put("drBdate", "2014-12-31");
		params.put("period", 6);
		params.put("subcoNo", 201);
		testAPI(scope, "/customer/edit", params);
	}
	
	public static void vehicleTest(Scope scope) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", "50948");
		testAPI(scope, "/customer/vehicle", params);//customer/vehicle
	}
	
	public static void bindTest(Scope scope) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", "10");
		params.put("origin", 1);
		params.put("deviceType", "1");
		params.put("appId", "587753217");
		params.put("deviceToken", "0ef73165a0a9384d20fc430c5b0d1e153e8a299d849e2df5bd75a3dce0f7a846");
		testAPI(scope, "/customer/bind", params);
	}
	
	public static void servpwdCheckTest(Scope scope) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", "41385");
		params.put("servicePwd", "123456");
		testAPI(scope, "/customer/servpwd/check", params);
	}
	
	public static void main(String[] args) throws Exception {
//		editTest(Scope.Local);
//		showTest(Scope.Gboss);//true
		vehicleTest(Scope.Gboss);//true
		vehicleTest(Scope.Local);//true
//		vehicleTest(Scope.Local);//true
//		bindTest(Scope.Local);
//		servpwdCheckTest(Scope.Haima);
	}

}
