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
import cc.chinagps.seat.bean.ReportSendCmd;
import cc.chinagps.seat.bean.table.CustomerTable;
import cc.chinagps.seat.bean.table.LastPositionTable;
import cc.chinagps.seat.bean.table.SendCmdTable;
import cc.chinagps.seat.bean.table.UnitTable;
import cc.chinagps.seat.bean.table.VehicleTable;
import cc.chinagps.seat.util.Consts;

/**
 * 指令操作表导出
 * @author dy
 *
 */
public class SendCmdView extends AbstractTemplateExcelExportView {

	@Override
	protected void fillDataToExcel(Map<String, Object> model, 
			HSSFWorkbook wb, HSSFSheet sheet,
			int beginRow,
			MessageSourceAccessor messages, Locale locale) {
		
		@SuppressWarnings("unchecked")
		List<ReportSendCmd> rscList = (List<ReportSendCmd>) model.get("data");
		Collections.sort(rscList, new ReportComparator());
		HSSFCell cell;
		int rowNum = beginRow;
		int colNum = 0;
		for (ReportSendCmd rsc : rscList) {
			SendCmdTable sendcmd = rsc.getSendCmd();			
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, rsc.getSn() + "");
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, sendcmd.getCompanyName());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, sendcmd.getOpName());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, sendcmd.getPlateNo());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, sendcmd.getCallLetter());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, Consts.formatDate(sendcmd.getCmdTime()));
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, sendcmd.getMode()==1?"短信":"数据流量");
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, sendcmd.getCmdName());
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, sendcmd.getSendParams());
			
			int flag = sendcmd.getSendFlag()==null?1:sendcmd.getSendFlag();
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, flag==0?"成功":"失败");
			
			cell = getCellWithDefaultStyle(wb, sheet, rowNum, colNum++);
			setText(cell, sendcmd.getOpIp());	
			rowNum++;
			colNum = 0;
		}
	}

	@Override
	protected String getExportNameCode() {
		return "report.operationlog.title";
	}
	
	@Override
	protected String getTitleCode(int colNum, Map<String, Object> model) {
		return "report.operationlog.column" + colNum;
	}
	
	@Override
	protected int getColumnCount(Map<String, Object> model) {
		return 11;
	}
}
