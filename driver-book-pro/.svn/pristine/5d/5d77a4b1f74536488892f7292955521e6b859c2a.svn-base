package test;

import java.util.HashMap;
import java.util.Map;

public class StatisticsUnitTest extends BaseUnitTest {
	
	public static void weekSummaryTest(Scope scope) throws Exception {
		Map<String ,Object> params = new HashMap<String, Object>();
		params.put("callLetter", "15766442952");
		params.put("tag", "1");
		testAPI(scope, "/statistics/summary", params);
	}
	
	public static void monthSummaryTest(Scope scope) throws Exception {
		Map<String ,Object> params = new HashMap<String, Object>();
		params.put("callLetter", "13528472331");
		params.put("tag", "2");
		params.put("month", "2015-03");
		testAPI(scope, "/statistics/summary", params);
	}
	
	
	public static void main(String[] args) throws Exception {
		weekSummaryTest(Scope.Gboss);
		System.out.println("-");
		monthSummaryTest(Scope.Gboss);
	}
}
