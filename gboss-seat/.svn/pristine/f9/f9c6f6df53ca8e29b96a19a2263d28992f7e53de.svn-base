package cc.chinagps.seat.view;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.support.MessageSourceAccessor;

import cc.chinagps.seat.bean.ReportUnitCom;

/**
 * 
*      
* 类名称：UnitComView   
* 类描述：   车台通信导出
* 创建人：dengyan  
* 创建时间：2015-4-3 上午9:19:50    
* 修改备注：   
* @version    
*
 */
public class UnitComView extends AbstractTemplateExcelExportView {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void fillDataToExcel(Map<String, Object> model, 
			HSSFWorkbook wb, HSSFSheet sheet,
			int beginRow,
			MessageSourceAccessor messages, Locale locale) {
		
		List rscList = (List) model.get("data");
		Collections.sort(rscList, new ReportComparator());
		HSSFCell cell;
		int rowNum = beginRow;
		int colNum = 0;
		rscList = (List<HashMap<String, Object>>) rscList;
		for (int i=0;i<rscList.size();i++) {
			ReportUnitCom unitCom = (ReportUnitCom)rscList.get(i);		
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, unitCom.getSn()+"");
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, unitCom.getCustomer()+"");
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, unitCom.getPlateNo()+ "");
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, unitCom.getCallLetter()+ "");
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, unitCom.getMode().equals("1")?"短信":"数据流量");
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, unitCom.getGpstime()+"");
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, unitCom.getStatus()+ "");	
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, unitCom.getCompanyName()+ "");	
			rowNum++;
			colNum = 0;
		}
	}

	@Override
	protected String getExportNameCode() {
		return "report.unitCom.title";
	}
	
	@Override
	protected String getTitleCode(int colNum, Map<String, Object> model) {
		return "report.unitCom.column" + colNum;
	}
	
	@Override
	protected int getColumnCount(Map<String, Object> model) {
		return 8;
	}
}
