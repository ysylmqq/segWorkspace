package test;

import java.util.HashMap;
import java.util.Map;

public class MaintainUnitTest extends BaseUnitTest {

	public static void showTest(Scope scope) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("vehicleId", "1");
		params.put("totalMileage", "3000");
		testAPI(scope, "/maintain/show", params);
	}
	
	public static void editTest(Scope scope) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("vehicleId", "1");
		params.put("notice", "1");
		testAPI(scope, "/maintain/edit", params);
	}
	
	public static void confirmTest(Scope scope) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("vehicleId", "1");
		testAPI(scope, "/maintain/confirm", params);
	}
	
	public static void periodTest(Scope scope) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("model", "1");
		testAPI(scope, "/maintain/period", params);
	}
	
	public static void main(String[] args) throws Exception {
//		showTest(Scope.Local);
//		editTest(Scope.Local);
//		confirmTest(Scope.Local);
//		periodTest(Scope.Local);
	}

}
