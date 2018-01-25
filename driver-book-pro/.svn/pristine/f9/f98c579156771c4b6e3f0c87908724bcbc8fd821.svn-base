package test;

import java.util.HashMap;
import java.util.Map;

/**
 * 指令补查测试
 */
public class SnTest extends BaseUnitTest {
	
	public static void historyquery(Scope scope) throws Exception{
		Map<String ,Object> params = new HashMap<String, Object>();
		params.put("sn", "NV-63999C77-26BD-4CFB-9ADB-0A8C97B33DDB");
		testAPI(scope, "/notice_query/getnoticebysn", params);
	}
	
	public static void main(String[] args) throws Exception {
		historyquery(Scope.Local);
	}
	
}
