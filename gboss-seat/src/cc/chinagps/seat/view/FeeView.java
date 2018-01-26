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
import org.springframework.util.StringUtils;

public class FeeView extends AbstractTemplateExcelExportView {

	@Override
	protected String getTitleCode(int colNum, Map<String, Object> model) {
		return "report.fee.column"+ colNum;
	}

	@Override
	protected void fillDataToExcel(Map<String, Object> model, HSSFWorkbook wb,
			HSSFSheet sheet, int beginRow, MessageSourceAccessor messages,
			Locale locale) {
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> rscList = (List<Map<String,Object>>) model.get("data");
		Collections.sort(rscList,new Comparator<Object>(){
			@SuppressWarnings("unchecked")
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
			setText(cell, getVal(rlf,"unittype"));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"sales"));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"service_date"));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"ac_amount"));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"month_fee"));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getPayModel(getVal(rlf,"pay_model")));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"fee_sedate"));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, getVal(rlf,"subco_name"));
			
			rowNum++;
			colNum = 0;
			
		}
	}
	
	public String getPayModel(String key){
		if(StringUtils.hasText(key)){
			int p_m = Integer.parseInt(key);
			String val = "";//预留, 付费方式, 冗余, 系统值3050, 0=托收, 1=现金, 2=转账, 3=刷卡
			switch(p_m){
				case 0:val="托收";break;
				case 1:val="现金";break;
				case 2:val="转账";break;
				case 4:val="刷卡";break;
			}
			return val;
		}
		return "";
	}

	@Override
	protected String getExportNameCode() {
		return "report.fee.title";
	}

	@Override
	protected int getColumnCount(Map<String, Object> model) {
		return 12;
	}

}
