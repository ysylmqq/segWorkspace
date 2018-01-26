package cc.chinagps.seat.view;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.support.MessageSourceAccessor;

import cc.chinagps.seat.bean.ReportBrief;
import cc.chinagps.seat.bean.VehicleUnitInfo;
import cc.chinagps.seat.bean.table.BriefTable;
import cc.chinagps.seat.util.Consts;

/**
 * 电话呼入查询报表导出
 * @author Administrator
 *
 */
public class BriefView extends AbstractTemplateExcelExportView {

	@Override
	protected void fillDataToExcel(Map<String, Object> model, 
			HSSFWorkbook wb, HSSFSheet sheet,
			int beginRow,
			MessageSourceAccessor messages, Locale locale) {
		
		@SuppressWarnings("unchecked")
		List<ReportBrief> rbList = (List<ReportBrief>) model.get("data");
		Collections.sort(rbList, new ReportComparator());
		HSSFCell cell;
		int rowNum = beginRow;
		int colNum = 0;
		for (ReportBrief rb : rbList) {
			BriefTable brief = rb.getBrief();
			VehicleUnitInfo vuInfo = rb.getVuInfo();
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, rb.getSn() + "");
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, vuInfo.getPlateNo());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, vuInfo.getCallLetter());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, Consts.formatDate(brief.getStamp()));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, brief.getPhone());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, messages.getMessage("brief.type" + brief.getType(), locale));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, brief.getServiceContent());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, rb.getCustomerName());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, brief.getOp_name());
			
			rowNum++;
			colNum = 0;
		}
	}

	@Override
	protected String getExportNameCode() {
		return "report.brief.title";
	}
	
	@Override
	protected String getTitleCode(int colNum, Map<String, Object> model) {
		return "report.brief.column" + colNum;
	}
	
	@Override
	protected int getColumnCount(Map<String, Object> model) {
		return 9;
	}
}
