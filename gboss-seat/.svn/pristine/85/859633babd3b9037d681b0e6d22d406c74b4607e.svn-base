package cc.chinagps.seat.view;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.support.MessageSourceAccessor;

import cc.chinagps.seat.bean.ReportResponseRatio;
import cc.chinagps.seat.util.Consts;

/**
 * 电话呼入查询报表导出
 * @author Administrator
 *
 */
public class ResponseRatioView extends AbstractTemplateExcelExportView {

	@Override
	protected void fillDataToExcel(Map<String, Object> model, 
			HSSFWorkbook wb, HSSFSheet sheet,
			int beginRow,
			MessageSourceAccessor messages, Locale locale) {
		
		@SuppressWarnings("unchecked")
		List<ReportResponseRatio> rrList = (List<ReportResponseRatio>) model.get("data");
		Collections.sort(rrList, new ReportComparator());
		HSSFCell cell;
		int rowNum = beginRow;
		int colNum = 0;
		for (ReportResponseRatio ratio : rrList) {
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, ratio.getSn() + "");
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, Consts.formatDate(ratio.getBeginTime()) + "-" + Consts.formatDate(ratio.getEndTime()));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, ratio.getSum() + "");
			
			for (String key : Consts.RESP_RATIO.keySet()) {
				cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
				setText(cell, ratio.getStatItem(key) + "");
				
				cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
				setText(cell, ratio.getStatItem(key + "%") + "");
			}
			
			rowNum++;
			colNum = 0;
		}
	}

	@Override
	protected String getExportNameCode() {
		return "report.respratio.title";
	}
	
	@Override
	protected String getTitleCode(int colNum, Map<String, Object> model) {
		return "report.respratio.column" + colNum;
	}
	
	@Override
	protected int getColumnCount(Map<String, Object> model) {
		return 25;
	}
}
