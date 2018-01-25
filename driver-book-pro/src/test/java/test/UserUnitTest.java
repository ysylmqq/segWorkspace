package test;

import java.util.HashMap;
import java.util.Map;


public class UserUnitTest extends BaseUnitTest {

	public static void loginTest(Scope scope) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginname", "13712567456");
		params.put("userPassword", "848333");
		testAPI(scope, "/login", params);
	}
	
	public static void updateOperatorTest(Scope scope) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("opid", "4");
		params.put("oldPassword", "123456");
		params.put("userPassword", "123456");
		testAPI(scope, "/updateOperator", params);
	}
	
	public static void main(String[] args) throws Exception {
		loginTest(Scope.Ris);
//		updateOperatorTest(Scope.Local);
	}

}
