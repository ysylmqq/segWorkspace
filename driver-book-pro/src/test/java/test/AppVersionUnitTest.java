package test;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试版本
 * @author wx
 *
 */
public class AppVersionUnitTest extends BaseUnitTest {

	public static void versionTest(Scope scope) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("origin", null);
		testAPI(scope, "/app/version", params);
	}

	public static void main(String[] args) throws Exception {
		versionTest(Scope.Local);
	}

}
