package cc.chinagps.seat.view;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.support.MessageSourceAccessor;

import cc.chinagps.seat.bean.ReportLocateFault;
import cc.chinagps.seat.bean.table.CustomerTable;
import cc.chinagps.seat.bean.table.LastPositionTable;
import cc.chinagps.seat.bean.table.UnitTable;
import cc.chinagps.seat.bean.table.VehicleTable;
import cc.chinagps.seat.util.Consts;

/**
 * 定位故障报表导出
 * @author Administrator
 *
 */
public class LocateFaultView extends AbstractTemplateExcelExportView {

	@Override
	protected void fillDataToExcel(Map<String, Object> model, 
			HSSFWorkbook wb, HSSFSheet sheet,
			int beginRow,
			MessageSourceAccessor messages, Locale locale) {
		
		@SuppressWarnings("unchecked")
		List<ReportLocateFault> rlfList = (List<ReportLocateFault>) model.get("data");
		Collections.sort(rlfList, new ReportComparator());
		HSSFCell cell;
		int rowNum = beginRow;
		int colNum = 0;
		for (ReportLocateFault rlf : rlfList) {
			UnitTable unit = rlf.getUnit();
			CustomerTable customer = unit.getCustomer();
			VehicleTable vehicle = unit.getVehicle();
			LastPositionTable lastPosition = rlf.getLastPosition();
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, rlf.getSn() + "");
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, customer.getCustomerName());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, rlf.getPhone());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, vehicle.getPlateNo());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, unit.getCallLetter());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, messages.getMessage("unit.mode." + unit.getMode(), locale));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, lastPosition.getStatus());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, Consts.formatDate(lastPosition.getStamp()));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, Consts.formatDate(lastPosition.getGpsTime()));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			Double lon = lastPosition.getLon();
			if (lon == null) {
				lon = 0D;
			}
			setText(cell, Double.toString(lon));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			Double lat = lastPosition.getLat();
			if (lat == null) {
				lat = 0D;
			}
			setText(cell, Double.toString(lat));
			
			rowNum++;
			colNum = 0;
		}
	}

	@Override
	protected String getExportNameCode() {
		return "report.locatefault.title";
	}
	
	@Override
	protected String getTitleCode(int colNum, Map<String, Object> model) {
		return "report.locatefault.column" + colNum;
	}
	
	@Override
	protected int getColumnCount(Map<String, Object> model) {
		return 11;
	}
}
