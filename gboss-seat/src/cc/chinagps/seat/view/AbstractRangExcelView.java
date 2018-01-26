package cc.chinagps.seat.view;

import java.io.OutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public abstract class AbstractRangExcelView extends AbstractExcelView {
	
	private ThreadLocal<HSSFCellStyle> titleCellStyleTL =
		new ThreadLocal<HSSFCellStyle>();
	
	private ThreadLocal<HSSFCellStyle> cellStyleTL =
			new ThreadLocal<HSSFCellStyle>();
	
	protected void initButtomTitleCell(HSSFWorkbook workbook, String title,
			HSSFSheet sheet, int columnCount) {
		CellRangeAddress region;
		region = new CellRangeAddress(2, 2, 0, columnCount - 1);
		sheet.addMergedRegion(region);
		updateRegionStyle(workbook, sheet, region);
		HSSFCell cell3 = getCell(sheet, 2, 0);
		cell3.setCellStyle(getDefaultCellStyle(workbook, true));
		setText(cell3, title);
	}

	protected void initMiddleTitleCell(HSSFWorkbook workbook, HSSFSheet sheet,
			int columnCount) {
		CellRangeAddress region = new CellRangeAddress(1, 1, 0, columnCount - 1);
		sheet.addMergedRegion(region);
		updateRegionStyle(workbook, sheet, region);
		String totitle = "SHENZHEN SEG SCIENTIFIC NAVIGATIONS CO., LTD.";
		HSSFCell cell2 = getCell(sheet, 1, 0);
		cell2.setCellStyle(getDefaultCellStyle(workbook, true));
		setText(cell2, totitle);
	}

	protected void initToptitleCell(HSSFWorkbook workbook, HSSFSheet sheet,
			int columnCount) {
		CellRangeAddress region = new CellRangeAddress(0, 0, 0, columnCount - 1);
		sheet.addMergedRegion(region);
		updateRegionStyle(workbook, sheet, region);
		String topitle = "深圳市赛格导航科技股份有限公司";
		HSSFCell cell = getCell(sheet, 0, 0);
		cell.setCellStyle(getDefaultCellStyle(workbook, true));
		setText(cell, topitle);
	}
	
	@SuppressWarnings("unchecked")
	protected List<Map<String, Object>> initSortData(Map<String, Object> model) {
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
		return rlfList;
	}
	
	protected HSSFCellStyle getDefaultCellStyle(HSSFWorkbook wb,boolean titleCellStyle) {
		HSSFCellStyle cellStyle;
		if (titleCellStyle) {
			cellStyle = titleCellStyleTL.get();
		} else {
			cellStyle = cellStyleTL.get();
		}
		if (cellStyle == null) {
			cellStyle = wb.createCellStyle();
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			
			cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
			cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
			cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
			cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
			
			if (titleCellStyle) {
				 HSSFFont font = wb.createFont();
				 font.setFontHeightInPoints((short) 14);
				 font.setFontName("宋体");
			     font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			     cellStyle.setFont(font);
				titleCellStyleTL.set(cellStyle);
			} else {
				cellStyleTL.set(cellStyle);
			}
		}
		return cellStyle;
	}
	 /**
     * 获取单元格默认样式
     * @param wb
     * @param sheet
     * @param row
     * @param col
     * @return
     */
	protected HSSFCell getCellWithDefaultStyle(HSSFWorkbook wb, 
			HSSFSheet sheet, int row, int col) {
		HSSFCell cell = getCell(sheet, row, col);
		HSSFCellStyle cellStyle = getDefaultCellStyle(wb, false);
		cell.setCellStyle(cellStyle);
		return cell;
	}
	
	/**
     * 合并区域样式更新
     * @param wb
     * @param sheet
     * @param region
     */
	protected void updateRegionStyle(HSSFWorkbook wb, HSSFSheet sheet, CellRangeAddress region) {
    	RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, region,sheet, wb);
		RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, region,sheet, wb);
		RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, region,sheet, wb);
		RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, region,sheet, wb);
	}
	
	/**
	 * 初始化sheet
	 * @param sheet
	 */
	protected void initSheet(HSSFSheet sheet){  
	    sheet.setColumnWidth(0, (int) (35.7 * 150));  
	    sheet.setColumnWidth(1, (int) (35.7 * 100));  
	    sheet.setColumnWidth(2, (int) (35.7 * 150));  
	    sheet.setColumnWidth(3, (int) (35.7 * 100));  
	    sheet.setColumnWidth(4, (int) (35.7 * 120));  
	    sheet.setColumnWidth(5, (int) (35.7 * 120));  
	    sheet.setColumnWidth(6, (int) (35.7 * 120));  
	}  
	
	abstract void initHeader(HSSFSheet sheet,HSSFCellStyle style);
	
	/**
     * 创建单元格
     * @param row 行
     * @param column 列位置
     * @param value 值
     * @param style 样式
     */
	protected void createCell(HSSFRow row,int column,Object value,HSSFCellStyle style){
        HSSFCell cell = row.createCell(column);
        cell.setCellValue(String.valueOf(value));
        cell.setCellStyle(style);
    } 
	
	protected String getVal(Map<String,Object> rlf,String key){
		return rlf.get(key)==null ? "":rlf.get(key).toString();
	}
    
    /**
	 * 清除样式
	 */
	protected void clearCacheCellStyle() {
		titleCellStyleTL.remove();
		cellStyleTL.remove();
	}
	
	private String title;
	private int columnCount;
	
	public int getColumnCount() {
		return columnCount;
	}

	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HSSFCellStyle centerStyle = getDefaultCellStyle(workbook,false);
		HSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(0, getTitle());
		initToptitleCell(workbook, sheet, getColumnCount());
		initMiddleTitleCell(workbook, sheet, getColumnCount());
		initButtomTitleCell(workbook, title, sheet, getColumnCount());
		initSheet(sheet);// 初始化sheet，设置列数和每列宽度
		initHeader(sheet, centerStyle);// 初始化头部为水平居中
		
		fillDataToExcel(model, workbook,sheet,centerStyle,request, response);
		
		response.setContentType("application/ms-excel;charset=UTF-8");
		response.setHeader("Content-disposition", "attachment; filename=" + new String(title.getBytes("GBK"), "ISO-8859-1") + ".xls");
		OutputStream out = response.getOutputStream();
		workbook.write(out);
		out.flush();
		out.close();
		clearCacheCellStyle();
	}
	
	//填充数据
	public abstract void fillDataToExcel(Map<String, Object> model,HSSFWorkbook workbook,HSSFSheet sheet,HSSFCellStyle centerStyle, HttpServletRequest request, HttpServletResponse response);
	
}
