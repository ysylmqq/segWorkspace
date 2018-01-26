/**
 * 
 */
package test;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.chinaGPS.gtmp.entity.UnitPOJO;
import com.chinaGPS.gtmp.service.impl.UnitServiceImpl;
import com.chinaGPS.gtmp.util.ExcelUtil;

/**
 * @Package:test
 * @ClassName:ExcelUtilTest
 * @Description:
 * @author:lxj
 * @date:Dec 18, 2012 3:07:41 PM
 */
public class ExcelUtilTest {
	private static UnitServiceImpl unitService;

	/**
	 * @Title:setUpBeforeClass
	 * @Description:
	 * @throws java.lang.Exception
	 * @throws
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			Collection<String> files = new ArrayList<String>();
			files.add("WebRoot/WEB-INF/spring/applicationContext-*.xml");
			FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext(
					files.toArray(new String[0]));
			unitService = (UnitServiceImpl) ctx
					.getBean("unitService");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testcreateExcel() throws Exception {
		ExcelUtil excelUtil = new ExcelUtil();
		List list = unitService.getList(new UnitPOJO());
		String fileName = "gpsInfo.xls";
		String[] title = new String[]{"物料条码","终端序列号","终端类型","sim卡号","钢号","软件版本号"};
		
		excelUtil.createExcel(list, fileName, title);
	}
	
	public static void main(String[] args) throws Exception {
		setUpBeforeClass();
		ExcelUtilTest excel = new ExcelUtilTest();
		excel.testcreateExcel();
	}

}

