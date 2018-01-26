package cc.chinagps.seat.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * 呼入业务统计导出
 * 
 */
public class CallinBizzExcelView extends AbstractRangExcelView {
	
	private int type = 1;//1来电；2去电
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public CallinBizzExcelView(){
		type = 1;
		String title = (type == 1 ? "呼入业务统计" : "呼出业务统计");
		setTitle(title);
		setColumnCount(7);
	}
	
	public CallinBizzExcelView(int type){
		this.type = type;
		String title = (type == 1 ? "呼入业务统计" : "呼出业务统计");
		setTitle(title);
		setColumnCount(7);
	}
	
	 /**
     * 初始化sheet样式
     * @param sheet
     * @param style
     */
    public void initHeader(HSSFSheet sheet,HSSFCellStyle style){
        HSSFRow row1 = sheet.createRow(3);
    	createCell(row1, 0, "序号", style);
        createCell(row1, 1, "归属公司", style);
        createCell(row1, 2, "日期", style);
        createCell(row1, 3, "服务类型", style);
        createCell(row1, 4, "数量", style);
        createCell(row1, 5, "业务类型", style);
        createCell(row1, 6, "数量", style);
    }
    
    
	@SuppressWarnings("unchecked")
	@Override
	public void fillDataToExcel(Map<String, Object> model, HSSFWorkbook workbook,HSSFSheet sheet
			,HSSFCellStyle centerStyle,HttpServletRequest request, HttpServletResponse response) {
		List<Map<String, Object>> rlfList = initSortData(model);
		//数据单元格整理
		int rowNum = 3;
		// 按要求创建各单元格
		for (Map<String,Object> rlf : rlfList) {
			List<Map<String,Object>> sub_datas = (List<Map<String,Object>>) rlf.get("sub_data");
			int len = sub_datas.size();
			HSSFRow row = sheet.createRow(++rowNum);
			
			int 	sn = Integer.valueOf(getVal(rlf,"sn"));
			String 	company_name =  getVal(rlf,"company_name");
			String 	super_time =  getVal(rlf,"super_time");
			String 	super_st_name =  getVal(rlf,"super_st_name");
			int 	super_num = Integer.valueOf(getVal(rlf,"super_num"));

			createCell(row, 0, sn, centerStyle);
			createCell(row, 1, company_name, centerStyle);
			createCell(row, 2, super_time, centerStyle);
			createCell(row, 3, super_st_name, centerStyle);
			createCell(row, 4, super_num, centerStyle);
			int stop_row_index = rowNum;
			if(len > 0){
				stop_row_index = rowNum + len - 1 ;
			}
			
			CellRangeAddress region1 = new CellRangeAddress(rowNum, stop_row_index, 0,0);
			sheet.addMergedRegion(region1);
			
			CellRangeAddress region2 = new CellRangeAddress(rowNum,stop_row_index, 1,1);
			sheet.addMergedRegion(region2);
			
			CellRangeAddress region3 = new CellRangeAddress(rowNum, stop_row_index, 2,2);
			sheet.addMergedRegion(region3);
			
			CellRangeAddress region4 = new CellRangeAddress(rowNum, stop_row_index, 3,3);
			sheet.addMergedRegion(region4);
			
			CellRangeAddress region5 = new CellRangeAddress(rowNum, stop_row_index, 4,4);
			sheet.addMergedRegion(region5);
			if(len>0){
				createCell(row, 5, sub_datas.get(0).get("sub_st_name"),centerStyle);// 
				createCell(row, 6, sub_datas.get(0).get("sub_num"),centerStyle);//
				for(int i = 1 ; i < len ;i++){
					Map<String,Object> sub_data = sub_datas.get(i);
					HSSFRow row2 = sheet.createRow(++rowNum);
					createCell(row2, 5, sub_data.get("sub_st_name"),centerStyle);//
					createCell(row2, 6, sub_data.get("sub_num"), centerStyle);// 
					
				}
			}else{
				createCell(row, 5, "",centerStyle);// 
				createCell(row, 6, "",centerStyle);// 
				for(int i = 1 ; i < len ;i++){
					HSSFRow row2 = sheet.createRow(++rowNum);
					createCell(row2, 5, "",centerStyle);// 
					createCell(row2, 6, "", centerStyle);//
				}
			}
			updateRegionStyle(workbook, sheet, region1);
			updateRegionStyle(workbook, sheet, region2);
			updateRegionStyle(workbook, sheet, region3);
			updateRegionStyle(workbook, sheet, region4);
			updateRegionStyle(workbook, sheet, region5);
		}
	}
}
