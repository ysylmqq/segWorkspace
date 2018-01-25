package test;

import java.util.HashMap;
import java.util.Map;

public class NewsUnitTest extends BaseUnitTest {

	public static void indexTest(Scope scope) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("vehicldId", 57647);
		params.put("orgId", 201);
		params.put("sg", 0);
		params.put("numPerPage", 10);
		params.put("pageNum", 1);
		testAPI(scope, "/news", params);
	}
	
	public static void main(String[] args) throws Exception {
		indexTest(Scope.Gboss);
	}

}
