package test;

import java.util.HashMap;
import java.util.Map;


public class LoginInfoUnitTest extends BaseUnitTest {
	
	public static void newTest(Scope scope) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", "10");
		params.put("userType", "0");
		params.put("brand", "iPhone 5C");
		params.put("deviceType", "1");
		params.put("province", "湖南");
		params.put("city", "长沙");
		params.put("county", "");
		params.put("version", "5.0.0");
		testAPI(scope, "/logininfo/new", params);
	}
	
	public static void main(String[] args) throws Exception {
		newTest(Scope.Local);
	}
	
}
