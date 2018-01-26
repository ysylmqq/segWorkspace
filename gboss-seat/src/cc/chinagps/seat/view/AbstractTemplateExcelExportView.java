package cc.chinagps.seat.view;

import java.net.URLEncoder;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import cc.chinagps.seat.springutil.SpringContextHolder;

/**
 * 导出模板类
 * @author Administrator
 *
 */
public abstract class AbstractTemplateExcelExportView 
	extends AbstractExcelView {

	protected String getVal(Map<String,Object> rlf,String key){
		return rlf.get(key)==null ? "":rlf.get(key).toString();
	}
	
	private ThreadLocal<HSSFCellStyle> titleCellStyleTL =
			new ThreadLocal<HSSFCellStyle>();
	private ThreadLocal<HSSFCellStyle> cellStyleTL =
			new ThreadLocal<HSSFCellStyle>();
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MessageSourceAccessor messages = new MessageSourceAccessor(
				(MessageSource) SpringContextHolder.getBean(
						"messageSource"));
		Locale locale = RequestContextUtils.getLocale(request);
		String title = messages.getMessage(getExportNameCode(), locale);
		wb.setSheetName(0, title);
		HSSFSheet sheet = wb.getSheetAt(0);
		sheet.setDefaultColumnWidth(12);
		int columnCount = getColumnCount(model);
		CellRangeAddress region = new CellRangeAddress(0, 0, 0, 
				columnCount - 1);
		sheet.addMergedRegion(region);
		updateRegionStyle(wb, sheet, region);
		region = new CellRangeAddress(1, 1, 0, columnCount - 1);
		sheet.addMergedRegion(region);
		updateRegionStyle(wb, sheet, region);
		region = new CellRangeAddress(2, 2, 0, columnCount - 1);
		sheet.addMergedRegion(region);
		updateRegionStyle(wb, sheet, region);
		HSSFCell cell = getCell(sheet, 2, 0);
		cell.setCellStyle(getDefaultCellStyle(wb, true));
		setText(cell, title);
		int beginRow = 3;
		
		if (isFillTitleToExcel(model, wb, sheet)) {
			fillTitleIntoExcel(model, wb, sheet, messages, locale, 
					columnCount, beginRow);
		}
		
		beginRow++;
		fillDataToExcel(model, wb, sheet, beginRow, messages, 
				locale);
		
		// 下载文件名
		title = URLEncoder.encode(title, "UTF-8");
		response.setHeader("Content-disposition", 
				"attachment; filename*=UTF-8''" + title + ".xls");
		
		// 清空缓存的style
		clearCacheCellStyle();
	}

	private void clearCacheCellStyle() {
		titleCellStyleTL.remove();
		cellStyleTL.remove();
	}

	private void fillTitleIntoExcel(Map<String, Object> model, 
			HSSFWorkbook wb, HSSFSheet sheet,
			MessageSourceAccessor messages, Locale locale,
			int columnCount, int rowNum) {
		HSSFCell cell;
		String titleCode;
		for (int i = 0; i < columnCount; i++) {
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, i);
			titleCode = getTitleCode(i, model);
			setText(cell, messages.getMessage(titleCode, locale));
		}
	}

	/**
	 * 获取指定列的国际化字符串
	 * @param colNum 
	 * @param model
	 * @return
	 */
	protected abstract String getTitleCode(int colNum, 
			Map<String, Object> model);

	/**
	 * 导出的excel是否包含标题。默认为包含
	 * @param model 
	 * @return
	 */
	protected boolean isFillTitleToExcel(Map<String, Object> model, 
			HSSFWorkbook wb, 
			HSSFSheet sheet) {
		return true;
	}

	private void updateRegionStyle(HSSFWorkbook wb, 
			HSSFSheet sheet, CellRangeAddress region) {
		RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, region, 
				sheet, wb);
		RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, region, 
				sheet, wb);
		RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, region, 
				sheet, wb);
		RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, region, 
				sheet, wb);
	}
	
	protected HSSFCell getCellWithDefaultStyle(HSSFWorkbook wb, 
			HSSFSheet sheet, int row, int col) {
		HSSFCell cell = getCell(sheet, row, col);
		HSSFCellStyle cellStyle = getDefaultCellStyle(wb, false);
		cell.setCellStyle(cellStyle);
		return cell;
	}
	
	private HSSFCellStyle getDefaultCellStyle(HSSFWorkbook wb, 
			boolean titleCellStyle) {
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
	 * 数据填入excel
	 * @param model
	 * @param wb
	 * @param sheet 
	 * @param beginRow
	 * @param messages 
	 * @param locale 
	 */
	protected abstract void fillDataToExcel(
			Map<String, Object> model, HSSFWorkbook wb,
			HSSFSheet sheet, int beginRow,
			MessageSourceAccessor messages, Locale locale);

	/**
	 * 获取导出页面的名称
	 * @return
	 */
	protected abstract String getExportNameCode();
	
	/**
	 * 获取列数
	 * @return
	 */
	protected abstract int getColumnCount(Map<String, Object> model);
}
