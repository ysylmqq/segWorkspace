package cc.chinagps.seat.view;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.support.MessageSourceAccessor;

import cc.chinagps.seat.bean.ReportUnreportStat;
import cc.chinagps.seat.bean.table.CustomerTable;
import cc.chinagps.seat.bean.table.LastPositionTable;
import cc.chinagps.seat.bean.table.UnitTable;
import cc.chinagps.seat.bean.table.VehicleTable;
import cc.chinagps.seat.util.Consts;

/**
 * 电话呼入查询报表导出
 * @author Administrator
 *
 */
public class UnreportStatView extends AbstractTemplateExcelExportView {

	@Override
	protected void fillDataToExcel(Map<String, Object> model, 
			HSSFWorkbook wb, HSSFSheet sheet,
			int beginRow,
			MessageSourceAccessor messages, Locale locale) {
		
		@SuppressWarnings("unchecked")
		List<ReportUnreportStat> rbList = (List<ReportUnreportStat>) model.get("data");
		Collections.sort(rbList, new ReportComparator());
		HSSFCell cell;
		int rowNum = beginRow;
		int colNum = 0;
		for (ReportUnreportStat rb : rbList) {
			UnitTable unit = rb.getUnit();
			CustomerTable customer = unit.getCustomer();
			VehicleTable vehicle = unit.getVehicle();
			LastPositionTable lastPosition = rb.getLastPosition();
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, rb.getSn() + "");
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, customer.getCustomerName());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, rb.getPhone());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, vehicle.getPlateNo());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, unit.getCallLetter());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, vehicle.getModelName());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, lastPosition.getStatus());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, Consts.formatDate(lastPosition.getGpsTime()));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, Consts.formatDate(unit.getDate()));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, messages.getMessage("unit.mode." + unit.getMode(), locale));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, unit.getSales());
			
			rowNum++;
			colNum = 0;
		}
	}

	@Override
	protected String getExportNameCode() {
		return "report.unreportstat.title";
	}
	
	@Override
	protected String getTitleCode(int colNum, Map<String, Object> model) {
		return "report.unreportstat.column" + colNum;
	}
	
	@Override
	protected int getColumnCount(Map<String, Object> model) {
		return 11;
	}
}
