package test;

import java.util.HashMap;
import java.util.Map;

public class VehicleConfTest extends BaseUnitTest {
	
	public static void confQuery(Scope scope) throws Exception{
		Map<String ,Object> params = new HashMap<String, Object>();
		params.put("call_letter", "13566778899");
		testAPI(scope, "/conf_query/getconfbycl", params);
	}
	
	public static void main(String[] args) throws Exception {
		confQuery(Scope.Local);
	}
	
}	
