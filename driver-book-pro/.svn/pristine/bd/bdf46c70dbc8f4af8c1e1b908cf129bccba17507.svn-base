package test;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单
 * @author wx
 *
 */
public class OrderUnitTest extends BaseUnitTest {

	public static void newTest(Scope scope) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		testAPI(scope, "/order/new", params);
	}
	
	public static void orderTest(Scope scope) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		testAPI(scope, "/order", params);
	}

	public static void main(String[] args) throws Exception {
		 newTest(Scope.Local);
	}
}
