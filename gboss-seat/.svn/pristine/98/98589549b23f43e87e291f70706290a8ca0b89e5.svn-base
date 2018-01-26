package cc.chinagps.seat.view;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.support.MessageSourceAccessor;

import cc.chinagps.seat.bean.ReportStolenVehicle;
import cc.chinagps.seat.bean.table.StolenVehicleTable;
import cc.chinagps.seat.bean.table.UnitTable;
import cc.chinagps.seat.util.Consts;

/**
 * 案发车辆查询报表导出
 * @author Administrator
 *
 */
public class StolenVehicleView extends AbstractTemplateExcelExportView {

	@Override
	protected void fillDataToExcel(Map<String, Object> model, 
			HSSFWorkbook wb, HSSFSheet sheet,
			int beginRow,
			MessageSourceAccessor messages, Locale locale) {
		
		@SuppressWarnings("unchecked")
		List<ReportStolenVehicle> rbList = (List<ReportStolenVehicle>) model.get("data");
		Collections.sort(rbList, new ReportComparator());
		HSSFCell cell;
		int rowNum = beginRow;
		int colNum = 0;
		for (ReportStolenVehicle rb : rbList) {
			UnitTable unit = rb.getUnit();
			StolenVehicleTable sv = rb.getStolenVehicle();
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, rb.getSn() + "");
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, rb.getCustomer().getCustomerName());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, unit.getVehicle().getPlateNo());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, unit.getCallLetter());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, unit.getVehicle().getType());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, unit.getType());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, unit.getPlace());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, Consts.formatDate(unit.getCreateDate()));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, messages.getMessage("casetype." + sv.getCaseType(), locale));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, Consts.formatDate(sv.getBeginTime()));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, messages.getMessage("yesno." + sv.getIsCallPolice(), locale));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, messages.getMessage("yesno." + sv.getIsCapture(), locale));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, messages.getMessage("yesno." + unit.getBuyTp(), locale));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, Consts.formatDate(sv.getEndTime()));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, messages.getMessage("yesno." + unit.getTamperBox(), locale));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, messages.getMessage("yesno." + unit.getTamperSmart(), locale));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, messages.getMessage("yesno." + unit.getTamperWireless(), locale));
			
			rowNum++;
			colNum = 0;
		}
	}

	@Override
	protected String getExportNameCode() {
		return "report.stolenvehicle.title";
	}
	
	@Override
	protected String getTitleCode(int colNum, Map<String, Object> model) {
		return "report.stolenvehicle.column" + colNum;
	}
	
	@Override
	protected int getColumnCount(Map<String, Object> model) {
		return 17;
	}
}
