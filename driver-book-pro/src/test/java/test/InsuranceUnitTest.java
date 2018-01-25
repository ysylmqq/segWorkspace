package test;


public class InsuranceUnitTest extends BaseUnitTest {
	
	public static void indexText(Scope scope) throws Exception {
		testAPI(scope, "/insurance", null);
	}
	
	public static void main(String[] args) throws Exception {
		indexText(Scope.Local);
	}
}
