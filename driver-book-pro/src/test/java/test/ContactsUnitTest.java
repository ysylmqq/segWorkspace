package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 通讯录单元测试
 * @author Ben
 *
 */
public class ContactsUnitTest extends BaseUnitTest {
	
	public static void countTest(Scope scope) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", "1");
		params.put("callLetter", "13800138000");
		testAPI(scope, "/contacts/count", params);
	}
	
	public static void backupTest(Scope scope) throws Exception {
		HashMap<String, String> csMap1 = new HashMap<String, String>();
		csMap1.put("c", "13800138000");
		csMap1.put("l", "");
		csMap1.put("m", "5");
		csMap1.put("t", "1");
		HashMap<String, String> csMap2 = new HashMap<String, String>();
		csMap2.put("c", "13800138001");
		csMap2.put("l", "");
		csMap2.put("m", "5");
		csMap2.put("t", "2");
		ArrayList<HashMap<String, String>> csList = new ArrayList<HashMap<String,String>>();
		csList.add(csMap1);
		csList.add(csMap2);
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("n", "张三");
		dataMap.put("k", "asdfghjkl");
		dataMap.put("cs", csList);
		HashMap<String, String> csMap21 = new HashMap<String, String>();
		csMap21.put("c", "13800138111");
		csMap21.put("l", "");
		csMap21.put("m", "5");
		csMap21.put("t", "2");
		ArrayList<HashMap<String, String>> csList2 = new ArrayList<HashMap<String,String>>();
		csList2.add(csMap21);
		HashMap<String, Object> dataMap2 = new HashMap<String, Object>();
		dataMap2.put("n", "李四");
		dataMap2.put("k", "qwertyuiop");
		dataMap2.put("cs", csList2);
		ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
		dataList.add(dataMap);
		dataList.add(dataMap2);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", 1);
		params.put("callLetter", "13800138000");
		params.put("uploadVersion", "20140626");
		params.put("contactVersion", "20140626");
		params.put("deviceType", 0);
		params.put("curPageNo", 1);
		params.put("pageSize", 20);
		params.put("rowsCount", 2);
		params.put("pageCount", 1);
		params.put("data", dataList);
		
		testGzipAPI(scope, "contacts/backup", params);
	}
	
	public static void restoreTest(Scope scope) throws Exception {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", "1");
		params.put("callLetter", "13800138000");
		params.put("curPageNo", "1");
		params.put("pageSize", "20");
		testGzipAPI(scope, "/contacts/restore", params);
	}
	
	public static void searchTest(Scope scope) throws Exception {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("unitNumber", "123");
		params.put("contactInfo", "13800138000");
		testGzipAPI(scope, "/contacts/search", params);
	}
	
	public static void main(String[] args) throws Exception {
//		countTest(Scope.Gboss); // 联系人总数
		searchTest(Scope.Gboss); // 联系人总数
//		backupTest(Scope.Local); // 通讯录备份
//		restoreTest(Scope.Local); // 通讯录还原
	}
	
}
