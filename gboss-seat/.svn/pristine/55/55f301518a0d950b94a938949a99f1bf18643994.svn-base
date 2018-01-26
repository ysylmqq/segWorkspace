package cc.chinagps.seat.view;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.support.MessageSourceAccessor;

import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsBaseInfo;
import cc.chinagps.seat.bean.GpsBasicInformation;
import cc.chinagps.seat.bean.HistoryTrack;
import cc.chinagps.seat.hbase.HBaseUtil;
import cc.chinagps.seat.util.Consts;

/**
 * 车辆监控-历史轨迹导出
 * @author Administrator
 *
 */
public class VehicleMonitorView extends AbstractTemplateExcelExportView {

	@Override
	protected void fillDataToExcel(Map<String, Object> model, 
			HSSFWorkbook wb, HSSFSheet sheet,
			int beginRow,
			MessageSourceAccessor messages, Locale locale) {
		
		@SuppressWarnings("unchecked")
		List<GpsBasicInformation> infoList = 
				(List<GpsBasicInformation>) model.get("gpsInfo");
		HistoryTrack historyTrack = 
				(HistoryTrack) model.get("historyTrack");
		
		int rowNum = beginRow;
		int colNum = 0;
		
		HSSFCell cell;
		String title;
		int serialNo = 1;
		for (GpsBasicInformation info : infoList) {
			GpsBaseInfo baseInfo = info.getBaseInfo();
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, serialNo + "");
			serialNo++;
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, historyTrack.getPlateNumber());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, historyTrack.getCallLetter());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			title = messages.getMessage("report.historytrack.loc." + 
					Boolean.toString(baseInfo.getLoc()), 
					locale);
			setText(cell, title);
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, info.getStatus());
			
			if (historyTrack.isExportRefPos()) {
				cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
				setText(cell, info.getReferPosition()+"");
			}
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			String lat = HBaseUtil.translateLngLatToDegree(baseInfo.getLat());
			String lng = HBaseUtil.translateLngLatToDegree(baseInfo.getLng());
			setText(cell, lng + "," + lat);
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, HBaseUtil.translateSpeedToKMPerHour(
					baseInfo.getSpeed()) + "");
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, Consts.formatDate(new Date(baseInfo.getGpsTime())));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, messages.getMessage(
					getDirectionMsgCode(baseInfo.getCourse()), locale));
			
			rowNum++;
			colNum = 0;
		}
	}

	@Override
	protected String getTitleCode(int colNum, Map<String, Object> model) {
		HistoryTrack historyTrack = (HistoryTrack) model.get("historyTrack");
		if (colNum >= 5) {
			if (!historyTrack.isExportRefPos()) {
				colNum += 1;
			}
		}
		return "report.historytrack.column" + colNum;
	}

	@Override
	protected String getExportNameCode() {
		return "report.historytrack.title";
	}
	
	@Override
	protected int getColumnCount(Map<String, Object> model) {
		HistoryTrack historyTrack = 
				(HistoryTrack) model.get("historyTrack");
		return historyTrack.isExportRefPos() ? 10 : 9;
	}
	
	private String getDirectionMsgCode(int course) {
		String code = "report.historytrack.dir.";
		
		//每45度为一种情况。如在[-45/2,45/2],则为north.如在[45/2,45/2+45],则为northeast
		//1、将course定位在[0,360]内，因为后续要对45/2操作，先扩大10倍
		course = course % 360 * 10;
		
		//2、course缩小(45/2)*10倍
		course = course / 225;
		
		//3、定位course
		course = (course + 1) / 2;
		if (course >= 8) {
			course = 0;
		}
		String[] dir = {"north", "northeast", "east", "southeast",
				"south", "southwest", "west", "northwest"}; 
		
		return code + dir[course];
	}
}
