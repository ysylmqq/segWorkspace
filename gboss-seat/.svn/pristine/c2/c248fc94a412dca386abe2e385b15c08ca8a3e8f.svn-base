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

import cc.chinagps.seat.bean.ReportManyGps;

/**
 * 
*      
* 类名称：AlarmView   
* 类描述：    大量上报前50报表导出
* 创建人：dengyan  
* 创建时间：2015-3-6 下午4:54:54    
* 修改备注：   
* @version    
*
 */
public class ManyGpsView extends AbstractTemplateExcelExportView {

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
			ReportManyGps manyGps = (ReportManyGps)rscList.get(i);
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, manyGps.getSn()+"");
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, manyGps.getPlateNo());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, manyGps.getCallLetter());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, manyGps.getFix_time());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, manyGps.getGps_time());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, manyGps.getAlarmcount());	
			rowNum++;
			colNum = 0;
		}
	}

	@Override
	protected String getExportNameCode() {
		return "report.manyGps.title";
	}
	
	@Override
	protected String getTitleCode(int colNum, Map<String, Object> model) {
		return "report.manyGps.column" + colNum;
	}
	
	@Override
	protected int getColumnCount(Map<String, Object> model) {
		return 6;
	}
}
