package test;

import java.util.HashMap;
import java.util.Map;

public class HealthUnitTest extends BaseUnitTest {

	public static void newTest(Scope scope) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("callLetter", "18975391677");
		params.put("score",2);
		testAPI(scope, "/health/new", params);
	}
	
	public static void indexTest(Scope scope) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("callLetter", "18975391677");
		params.put("pageNum", 1);
		params.put("numPerPage", 10);
		testAPI(scope, "/health", params);
	}

	public static void main(String[] args) throws Exception {
//		newTest(Scope.Local);
		indexTest(Scope.Local);
	}

}
