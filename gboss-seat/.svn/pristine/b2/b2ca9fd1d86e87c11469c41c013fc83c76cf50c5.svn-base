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

public class SmsSendDetailsView extends AbstractTemplateExcelExportView {

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
			setText(cell, getVal(rlf,"send_time"));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"plate_no"));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"mobile"));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"content"));
			
			rowNum++;
			colNum = 0;
		}
		
	}
	
	@Override
	protected int getColumnCount(Map<String, Object> model) {
		return 5;
	}

	@Override
	protected String getExportNameCode() {
		return "report.smssenddetails.title";
	}

	@Override
	protected String getTitleCode(int colNum, Map<String, Object> model) {
		return "report.smssenddetails.column" + colNum;
	}

}
