package cc.chinagps.seat.view;

import java.util.Collections;
import java.util.Comparator;
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

public class CallinStatisticsView extends AbstractTemplateExcelExportView {

	@SuppressWarnings("unchecked")
	@Override
	protected void fillDataToExcel(Map<String, Object> model, HSSFWorkbook wb,
			HSSFSheet sheet, int beginRow, MessageSourceAccessor messages,
			Locale locale) {

		@SuppressWarnings("unchecked")
		List<Map<String,Object>> rlfList = (List<Map<String,Object>>) model.get("data");
		Collections.sort(rlfList,new Comparator(){
			public int compare(Object o1, Object o2) {
				if(o1!=null && o2!=null){
					Map<String,Object> m1 = (Map<String,Object>)o1;
					Map<String,Object> m2 = (Map<String,Object>)o2;
					Integer sn1 = Integer.parseInt(m1.get("sn").toString());
					Integer sn2 = Integer.parseInt(m2.get("sn").toString());
					return sn1.compareTo(sn2);
				}
				return 0;
			}
		});
		HSSFCell cell;
		int rowNum = beginRow;
		int colNum = 0;
		for (Map<String,Object> rlf : rlfList) {
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"sn"));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"plate_no"));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"customer_name"));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"call_letter"));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"phone"));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"stamp"));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"op_id"));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"super_servertype_name"));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"content"));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"company_name"));
			
			rowNum++;
			colNum = 0;
		}
		
	}
	
	@Override
	protected int getColumnCount(Map<String, Object> model) {
		return 10;
	}

	@Override
	protected String getExportNameCode() {
		return "report.callindetails.title";
	}

	@Override
	protected String getTitleCode(int colNum, Map<String, Object> model) {
		return "report.callindetails.column" + colNum;
	}

}
