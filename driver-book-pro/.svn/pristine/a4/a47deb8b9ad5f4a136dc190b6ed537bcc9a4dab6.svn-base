package test;

import java.util.HashMap;
import java.util.Map;

public class NoticeConfigUnitTest extends BaseUnitTest {
	
	public static void showTest(Scope scope) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", "10");
		testAPI(scope, "/noticecfg/show", params);
	}
	
	public static void editTest(Scope scope) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", 10);
//		params.put("maintain", 0);
//		params.put("charge", 0);
//		params.put("fault", 0);
//		params.put("illegal", 0);
		params.put("offline", 1);
//		params.put("light", 1);
//		params.put("door", 1);
//		params.put("unlock", 1);
		testAPI(scope, "/noticecfg/edit", params);
	}
	
	public static void main(String[] args) throws Exception {
//		showTest(Scope.Haima);
		editTest(Scope.Haima);
	}
}
