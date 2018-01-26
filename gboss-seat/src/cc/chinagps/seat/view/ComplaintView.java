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

import cc.chinagps.seat.bean.ReportComplanint;

/**
 * 
*      
* 类名称：ComplaintView   
* 类描述：   投诉报表
* 创建人：dengyan  
* 创建时间：2015-6-3 上午9:17:12    
* 修改备注：   
* @version    
*
 */
public class ComplaintView extends AbstractTemplateExcelExportView {

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
			ReportComplanint complanint = (ReportComplanint)rscList.get(i);
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, complanint.getSn()+"");
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, complanint.getComplaintTable().getCp_no());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, complanint.getComplaintTable().getAccept_time()+"");
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, complanint.getCustomer_name());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, complanint.getPlate_no());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, complanint.getCall_letter());	
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, complanint.getSname()+"");
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, complanint.getComplaintTable().getCp_name()+"");
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, complanint.getComplaintTable().getCp_phone()+"");
					
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, this.typename(complanint.getComplaintTable().getCp_type_id()));
					
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, complanint.getComplaintTable().getCp_content()+"");
					
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, complanint.getComplaintTable().getFollow_time()+"");
					
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, complanint.getComplaintTable().getFollow_rec()+"");
			rowNum++;
			colNum = 0;
		}
	}
	@Override
	protected String getExportNameCode() {
		return "report.complanint.title";
	}
	
	@Override
	protected String getTitleCode(int colNum, Map<String, Object> model) {
		return "report.complanint.column" + colNum;
	}
	
	@Override
	protected int getColumnCount(Map<String, Object> model) {
		return 13;
	}
	
	protected String typename(int type) {
		String name = "";
		switch (type) {
		case 1:
			name = "服务质量";
			break; 
		case 2:
			name = "安装质量";
			break;
		case 3:
			name = "产品质量";
			break;
		case 4:
			name = "销售服务";
			break;
		case 5:
			name = "其它";
			break;
		default:
			break;
		}
		return name;
	}
}
