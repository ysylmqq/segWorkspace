package test;

import java.util.HashMap;
import java.util.Map;

/**
 * 燃油检查
 *
 */
public class FuelUnitTest extends BaseUnitTest {
	
	public static void indexTest(Scope scope) throws Exception {
		Map<String ,Object> params = new HashMap<String, Object>();
		params.put("province", "北京");
		testAPI(scope, "/fuel", params);
	}
	
	public static void priceTest(Scope scope) throws Exception {
		Map<String ,Object> params = new HashMap<String, Object>();
		params.put("customerId", "10");
		params.put("vehicleId", "66");
		testAPI(scope, "/fuel/price", params);
	}
	
	public static void editPriceTest(Scope scope) throws Exception {
		Map<String ,Object> params = new HashMap<String, Object>();
		params.put("customerId", "10");
		params.put("vehicleId", "1");
		params.put("price90", "");
		params.put("price93", "7.46");
		params.put("price97", "");
		params.put("price0", "");
		testAPI(scope, "/fuel/price/edit", params);
	}
	
	public static void main(String[] args) throws Exception {
//		indexTest(Scope.Local); // 每日油价
//		priceTest(Scope.Local); // 自定义油价查询
		editPriceTest(Scope.Local); // 自定义油价设置
	}
}
