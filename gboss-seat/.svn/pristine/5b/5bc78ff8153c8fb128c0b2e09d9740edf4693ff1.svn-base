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

public class MarketingView extends AbstractTemplateExcelExportView {

	@Override
	protected String getTitleCode(int colNum, Map<String, Object> model) {
		return "report.marketing.column"+ colNum;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void fillDataToExcel(Map<String, Object> model, HSSFWorkbook wb,
			HSSFSheet sheet, int beginRow, MessageSourceAccessor messages,
			Locale locale) {
		List<Map<String,Object>> rscList = (List<Map<String,Object>>) model.get("data");
		Collections.sort(rscList,new Comparator<Object>(){
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
		for(Map<String,Object> rlf : rscList){
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"sn"));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"customer_name"));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"plate_no"));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"call_letter"));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"color"));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"id_no"));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"fix_time"));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"linkman"));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"model_name"));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"engine_no"));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"vin"));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"is_pilfer").equals("1") ? "是":"否");
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"is_corp").equals("1") ? "是":"否");

			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"register_date"));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"subco_name"));
			
			rowNum++;
			colNum = 0;
			
		}
	}

	@Override
	protected String getExportNameCode() {
		return "report.marketing.title";
	}

	@Override
	protected int getColumnCount(Map<String, Object> model) {
		return 15;
	}

}
