package com.chinaGPS.gtmp.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import com.chinaGPS.gtmp.entity.ConditionsPOJO;
import com.chinaGPS.gtmp.entity.VehicleSalePOJO;

public class ImportCondition {
	/**
	 * 解析Excel文件中的数据并把每行数据封装成一个实体
	 * 
	 * @param fis
	 *            工况信息文件输入流
	 * @return List<EmployeeInfo> Excel中数据封装实体的集合
	 */
	public static List<ConditionsPOJO> importCondition(InputStream fis) {
		List<ConditionsPOJO> infos = new ArrayList<ConditionsPOJO>();
		ConditionsPOJO conditionsPOJO = null;

		try {
			// 打开文件
			Workbook book = Workbook.getWorkbook(fis);
			// 得到第一个工作表对象
			Sheet sheet = book.getSheet(0);
			// 得到第一个工作表中的总行数
			int rowCount = sheet.getRows();
			// 循环取出Excel中的内容
			for (int i = 1; i < rowCount; i++) {
				Cell[] cells = sheet.getRow(i);	
				infos.add(getConditionsPOJO(cells));
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return infos;
	}
	
	/**
	 * 销售信息文件输入流
	 * @param fis
	 * @return
	 */
	public static List<VehicleSalePOJO> importSaleInfo(InputStream fis) {
		List<VehicleSalePOJO> infos = new ArrayList<VehicleSalePOJO>();
		ConditionsPOJO conditionsPOJO = null;

		try {
			// 打开文件
			Workbook book = Workbook.getWorkbook(fis);
			// 得到第一个工作表对象
			Sheet sheet = book.getSheet(0);
			// 得到第一个工作表中的总行数
			int rowCount = sheet.getRows();
			// 循环取出Excel中的内容
			for (int i = 1; i < rowCount; i++) {
				Cell[] cells = sheet.getRow(i);	
				if(cells.length==3){
					VehicleSalePOJO vehicleSalePOJO = new VehicleSalePOJO();
					vehicleSalePOJO.setVehicleDef(cells[0].getContents().toString());
					vehicleSalePOJO.setDealerName(cells[1].getContents().toString());
					vehicleSalePOJO.setOwnerName(cells[2].getContents().toString());
					infos.add(vehicleSalePOJO);
				}
				
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return infos;
	}
	
	
	public static ConditionsPOJO getConditionsPOJO(Cell[] cells){
		ConditionsPOJO conditionsPOJO = new ConditionsPOJO();		
		int i = cells.length;
		if(i>0) {
		switch (i) {
		case 1:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			break;
		case 2:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			break;
		case 3:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			break;
		case 4:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			break;
		case 5:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			if (!cells[4].getContents().isEmpty())
				conditionsPOJO.setEngineFuelLevel(Float.parseFloat(cells[4].getContents().toString()));
			break;
		case 6:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			if (!cells[4].getContents().isEmpty())
				conditionsPOJO.setEngineFuelLevel(Float.parseFloat(cells[4].getContents().toString()));
			if (!cells[5].getContents().isEmpty())
				conditionsPOJO.setEngineSpeed(Integer.parseInt(cells[5].getContents().toString()));
			break;
		case 7:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			if (!cells[4].getContents().isEmpty())
				conditionsPOJO.setEngineFuelLevel(Float.parseFloat(cells[4].getContents().toString()));
			if (!cells[5].getContents().isEmpty())
				conditionsPOJO.setEngineSpeed(Integer.parseInt(cells[5].getContents().toString()));
			if (!cells[6].getContents().isEmpty())
				conditionsPOJO.setHydraulicOilTemperature(Integer.parseInt(cells[6].getContents().toString()));
			break;
		case 8:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			if (!cells[4].getContents().isEmpty())
				conditionsPOJO.setEngineFuelLevel(Float.parseFloat(cells[4].getContents().toString()));
			if (!cells[5].getContents().isEmpty())
				conditionsPOJO.setEngineSpeed(Integer.parseInt(cells[5].getContents().toString()));
			if (!cells[6].getContents().isEmpty())
				conditionsPOJO.setHydraulicOilTemperature(Integer.parseInt(cells[6].getContents().toString()));
			if (!cells[7].getContents().isEmpty())
				conditionsPOJO.setSystemVoltage(Float.parseFloat(cells[7].getContents().toString()));
			break;
		case 9:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			if (!cells[4].getContents().isEmpty())
				conditionsPOJO.setEngineFuelLevel(Float.parseFloat(cells[4].getContents().toString()));
			if (!cells[5].getContents().isEmpty())
				conditionsPOJO.setEngineSpeed(Integer.parseInt(cells[5].getContents().toString()));
			if (!cells[6].getContents().isEmpty())
				conditionsPOJO.setHydraulicOilTemperature(Integer.parseInt(cells[6].getContents().toString()));
			if (!cells[7].getContents().isEmpty())
				conditionsPOJO.setSystemVoltage(Float.parseFloat(cells[7].getContents().toString()));
			if (!cells[8].getContents().isEmpty())
				conditionsPOJO.setBeforePumpMainPressure(Float.parseFloat(cells[8].getContents().toString()));			
			break;
		case 10:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			if (!cells[4].getContents().isEmpty())
				conditionsPOJO.setEngineFuelLevel(Float.parseFloat(cells[4].getContents().toString()));
			if (!cells[5].getContents().isEmpty())
				conditionsPOJO.setEngineSpeed(Integer.parseInt(cells[5].getContents().toString()));
			if (!cells[6].getContents().isEmpty())
				conditionsPOJO.setHydraulicOilTemperature(Integer.parseInt(cells[6].getContents().toString()));
			if (!cells[7].getContents().isEmpty())
				conditionsPOJO.setSystemVoltage(Float.parseFloat(cells[7].getContents().toString()));
			if (!cells[8].getContents().isEmpty())
				conditionsPOJO.setBeforePumpMainPressure(Float.parseFloat(cells[8].getContents().toString()));
			if (!cells[9].getContents().isEmpty())
				conditionsPOJO.setAfterPumpMainPressure(Float.parseFloat(cells[9].getContents().toString()));			
			break;
		case 11:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			if (!cells[4].getContents().isEmpty())
				conditionsPOJO.setEngineFuelLevel(Float.parseFloat(cells[4].getContents().toString()));
			if (!cells[5].getContents().isEmpty())
				conditionsPOJO.setEngineSpeed(Integer.parseInt(cells[5].getContents().toString()));
			if (!cells[6].getContents().isEmpty())
				conditionsPOJO.setHydraulicOilTemperature(Integer.parseInt(cells[6].getContents().toString()));
			if (!cells[7].getContents().isEmpty())
				conditionsPOJO.setSystemVoltage(Float.parseFloat(cells[7].getContents().toString()));
			if (!cells[8].getContents().isEmpty())
				conditionsPOJO.setBeforePumpMainPressure(Float.parseFloat(cells[8].getContents().toString()));
			if (!cells[9].getContents().isEmpty())
				conditionsPOJO.setAfterPumpMainPressure(Float.parseFloat(cells[9].getContents().toString()));
			if (!cells[10].getContents().isEmpty())
				conditionsPOJO.setPilotPressure1(Float.parseFloat(cells[10].getContents().toString()));		
			break;
		case 12:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			if (!cells[4].getContents().isEmpty())
				conditionsPOJO.setEngineFuelLevel(Float.parseFloat(cells[4].getContents().toString()));
			if (!cells[5].getContents().isEmpty())
				conditionsPOJO.setEngineSpeed(Integer.parseInt(cells[5].getContents().toString()));
			if (!cells[6].getContents().isEmpty())
				conditionsPOJO.setHydraulicOilTemperature(Integer.parseInt(cells[6].getContents().toString()));
			if (!cells[7].getContents().isEmpty())
				conditionsPOJO.setSystemVoltage(Float.parseFloat(cells[7].getContents().toString()));
			if (!cells[8].getContents().isEmpty())
				conditionsPOJO.setBeforePumpMainPressure(Float.parseFloat(cells[8].getContents().toString()));
			if (!cells[9].getContents().isEmpty())
				conditionsPOJO.setAfterPumpMainPressure(Float.parseFloat(cells[9].getContents().toString()));
			if (!cells[10].getContents().isEmpty())
				conditionsPOJO.setPilotPressure1(Float.parseFloat(cells[10].getContents().toString()));
			if (!cells[11].getContents().isEmpty())
				conditionsPOJO.setPilotPressure2(Float.parseFloat(cells[11].getContents().toString()));
			break;
		case 13:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			if (!cells[4].getContents().isEmpty())
				conditionsPOJO.setEngineFuelLevel(Float.parseFloat(cells[4].getContents().toString()));
			if (!cells[5].getContents().isEmpty())
				conditionsPOJO.setEngineSpeed(Integer.parseInt(cells[5].getContents().toString()));
			if (!cells[6].getContents().isEmpty())
				conditionsPOJO.setHydraulicOilTemperature(Integer.parseInt(cells[6].getContents().toString()));
			if (!cells[7].getContents().isEmpty())
				conditionsPOJO.setSystemVoltage(Float.parseFloat(cells[7].getContents().toString()));
			if (!cells[8].getContents().isEmpty())
				conditionsPOJO.setBeforePumpMainPressure(Float.parseFloat(cells[8].getContents().toString()));
			if (!cells[9].getContents().isEmpty())
				conditionsPOJO.setAfterPumpMainPressure(Float.parseFloat(cells[9].getContents().toString()));
			if (!cells[10].getContents().isEmpty())
				conditionsPOJO.setPilotPressure1(Float.parseFloat(cells[10].getContents().toString()));
			if (!cells[11].getContents().isEmpty())
				conditionsPOJO.setPilotPressure2(Float.parseFloat(cells[11].getContents().toString()));
			if (!cells[12].getContents().isEmpty())
				conditionsPOJO.setBeforePumpPressure(Float.parseFloat(cells[12].getContents().toString()));
				break;
		case 14:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			if (!cells[4].getContents().isEmpty())
				conditionsPOJO.setEngineFuelLevel(Float.parseFloat(cells[4].getContents().toString()));
			if (!cells[5].getContents().isEmpty())
				conditionsPOJO.setEngineSpeed(Integer.parseInt(cells[5].getContents().toString()));
			if (!cells[6].getContents().isEmpty())
				conditionsPOJO.setHydraulicOilTemperature(Integer.parseInt(cells[6].getContents().toString()));
			if (!cells[7].getContents().isEmpty())
				conditionsPOJO.setSystemVoltage(Float.parseFloat(cells[7].getContents().toString()));
			if (!cells[8].getContents().isEmpty())
				conditionsPOJO.setBeforePumpMainPressure(Float.parseFloat(cells[8].getContents().toString()));
			if (!cells[9].getContents().isEmpty())
				conditionsPOJO.setAfterPumpMainPressure(Float.parseFloat(cells[9].getContents().toString()));
			if (!cells[10].getContents().isEmpty())
				conditionsPOJO.setPilotPressure1(Float.parseFloat(cells[10].getContents().toString()));
			if (!cells[11].getContents().isEmpty())
				conditionsPOJO.setPilotPressure2(Float.parseFloat(cells[11].getContents().toString()));
			if (!cells[12].getContents().isEmpty())
				conditionsPOJO.setBeforePumpPressure(Float.parseFloat(cells[12].getContents().toString()));
			if (!cells[13].getContents().isEmpty())
				conditionsPOJO.setAfterPumpPressure(Float.parseFloat(cells[13].getContents().toString()));			
			break;
		case 15:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			if (!cells[4].getContents().isEmpty())
				conditionsPOJO.setEngineFuelLevel(Float.parseFloat(cells[4].getContents().toString()));
			if (!cells[5].getContents().isEmpty())
				conditionsPOJO.setEngineSpeed(Integer.parseInt(cells[5].getContents().toString()));
			if (!cells[6].getContents().isEmpty())
				conditionsPOJO.setHydraulicOilTemperature(Integer.parseInt(cells[6].getContents().toString()));
			if (!cells[7].getContents().isEmpty())
				conditionsPOJO.setSystemVoltage(Float.parseFloat(cells[7].getContents().toString()));
			if (!cells[8].getContents().isEmpty())
				conditionsPOJO.setBeforePumpMainPressure(Float.parseFloat(cells[8].getContents().toString()));
			if (!cells[9].getContents().isEmpty())
				conditionsPOJO.setAfterPumpMainPressure(Float.parseFloat(cells[9].getContents().toString()));
			if (!cells[10].getContents().isEmpty())
				conditionsPOJO.setPilotPressure1(Float.parseFloat(cells[10].getContents().toString()));
			if (!cells[11].getContents().isEmpty())
				conditionsPOJO.setPilotPressure2(Float.parseFloat(cells[11].getContents().toString()));
			if (!cells[12].getContents().isEmpty())
				conditionsPOJO.setBeforePumpPressure(Float.parseFloat(cells[12].getContents().toString()));
			if (!cells[13].getContents().isEmpty())
				conditionsPOJO.setAfterPumpPressure(Float.parseFloat(cells[13].getContents().toString()));
			if (!cells[14].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent1(Integer.parseInt(cells[14].getContents().toString()));		
			break;
		case 16:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			if (!cells[4].getContents().isEmpty())
				conditionsPOJO.setEngineFuelLevel(Float.parseFloat(cells[4].getContents().toString()));
			if (!cells[5].getContents().isEmpty())
				conditionsPOJO.setEngineSpeed(Integer.parseInt(cells[5].getContents().toString()));
			if (!cells[6].getContents().isEmpty())
				conditionsPOJO.setHydraulicOilTemperature(Integer.parseInt(cells[6].getContents().toString()));
			if (!cells[7].getContents().isEmpty())
				conditionsPOJO.setSystemVoltage(Float.parseFloat(cells[7].getContents().toString()));
			if (!cells[8].getContents().isEmpty())
				conditionsPOJO.setBeforePumpMainPressure(Float.parseFloat(cells[8].getContents().toString()));
			if (!cells[9].getContents().isEmpty())
				conditionsPOJO.setAfterPumpMainPressure(Float.parseFloat(cells[9].getContents().toString()));
			if (!cells[10].getContents().isEmpty())
				conditionsPOJO.setPilotPressure1(Float.parseFloat(cells[10].getContents().toString()));
			if (!cells[11].getContents().isEmpty())
				conditionsPOJO.setPilotPressure2(Float.parseFloat(cells[11].getContents().toString()));
			if (!cells[12].getContents().isEmpty())
				conditionsPOJO.setBeforePumpPressure(Float.parseFloat(cells[12].getContents().toString()));
			if (!cells[13].getContents().isEmpty())
				conditionsPOJO.setAfterPumpPressure(Float.parseFloat(cells[13].getContents().toString()));
			if (!cells[14].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent1(Integer.parseInt(cells[14].getContents().toString()));
			if (!cells[15].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent2(Integer.parseInt(cells[15].getContents().toString()));			
			break;
		case 17:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			if (!cells[4].getContents().isEmpty())
				conditionsPOJO.setEngineFuelLevel(Float.parseFloat(cells[4].getContents().toString()));
			if (!cells[5].getContents().isEmpty())
				conditionsPOJO.setEngineSpeed(Integer.parseInt(cells[5].getContents().toString()));
			if (!cells[6].getContents().isEmpty())
				conditionsPOJO.setHydraulicOilTemperature(Integer.parseInt(cells[6].getContents().toString()));
			if (!cells[7].getContents().isEmpty())
				conditionsPOJO.setSystemVoltage(Float.parseFloat(cells[7].getContents().toString()));
			if (!cells[8].getContents().isEmpty())
				conditionsPOJO.setBeforePumpMainPressure(Float.parseFloat(cells[8].getContents().toString()));
			if (!cells[9].getContents().isEmpty())
				conditionsPOJO.setAfterPumpMainPressure(Float.parseFloat(cells[9].getContents().toString()));
			if (!cells[10].getContents().isEmpty())
				conditionsPOJO.setPilotPressure1(Float.parseFloat(cells[10].getContents().toString()));
			if (!cells[11].getContents().isEmpty())
				conditionsPOJO.setPilotPressure2(Float.parseFloat(cells[11].getContents().toString()));
			if (!cells[12].getContents().isEmpty())
				conditionsPOJO.setBeforePumpPressure(Float.parseFloat(cells[12].getContents().toString()));
			if (!cells[13].getContents().isEmpty())
				conditionsPOJO.setAfterPumpPressure(Float.parseFloat(cells[13].getContents().toString()));
			if (!cells[14].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent1(Integer.parseInt(cells[14].getContents().toString()));
			if (!cells[15].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent2(Integer.parseInt(cells[15].getContents().toString()));
			if (!cells[16].getContents().isEmpty())
				conditionsPOJO.setTotalWorkingHours(Float.parseFloat(cells[16].getContents().toString()));			
			break;
		case 18:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			if (!cells[4].getContents().isEmpty())
				conditionsPOJO.setEngineFuelLevel(Float.parseFloat(cells[4].getContents().toString()));
			if (!cells[5].getContents().isEmpty())
				conditionsPOJO.setEngineSpeed(Integer.parseInt(cells[5].getContents().toString()));
			if (!cells[6].getContents().isEmpty())
				conditionsPOJO.setHydraulicOilTemperature(Integer.parseInt(cells[6].getContents().toString()));
			if (!cells[7].getContents().isEmpty())
				conditionsPOJO.setSystemVoltage(Float.parseFloat(cells[7].getContents().toString()));
			if (!cells[8].getContents().isEmpty())
				conditionsPOJO.setBeforePumpMainPressure(Float.parseFloat(cells[8].getContents().toString()));
			if (!cells[9].getContents().isEmpty())
				conditionsPOJO.setAfterPumpMainPressure(Float.parseFloat(cells[9].getContents().toString()));
			if (!cells[10].getContents().isEmpty())
				conditionsPOJO.setPilotPressure1(Float.parseFloat(cells[10].getContents().toString()));
			if (!cells[11].getContents().isEmpty())
				conditionsPOJO.setPilotPressure2(Float.parseFloat(cells[11].getContents().toString()));
			if (!cells[12].getContents().isEmpty())
				conditionsPOJO.setBeforePumpPressure(Float.parseFloat(cells[12].getContents().toString()));
			if (!cells[13].getContents().isEmpty())
				conditionsPOJO.setAfterPumpPressure(Float.parseFloat(cells[13].getContents().toString()));
			if (!cells[14].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent1(Integer.parseInt(cells[14].getContents().toString()));
			if (!cells[15].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent2(Integer.parseInt(cells[15].getContents().toString()));
			if (!cells[16].getContents().isEmpty())
				conditionsPOJO.setTotalWorkingHours(Float.parseFloat(cells[16].getContents().toString()));
			if (!cells[17].getContents().isEmpty())
				conditionsPOJO.setGear(Integer.parseInt(cells[17].getContents().toString()));			
			break;
		case 19:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			if (!cells[4].getContents().isEmpty())
				conditionsPOJO.setEngineFuelLevel(Float.parseFloat(cells[4].getContents().toString()));
			if (!cells[5].getContents().isEmpty())
				conditionsPOJO.setEngineSpeed(Integer.parseInt(cells[5].getContents().toString()));
			if (!cells[6].getContents().isEmpty())
				conditionsPOJO.setHydraulicOilTemperature(Integer.parseInt(cells[6].getContents().toString()));
			if (!cells[7].getContents().isEmpty())
				conditionsPOJO.setSystemVoltage(Float.parseFloat(cells[7].getContents().toString()));
			if (!cells[8].getContents().isEmpty())
				conditionsPOJO.setBeforePumpMainPressure(Float.parseFloat(cells[8].getContents().toString()));
			if (!cells[9].getContents().isEmpty())
				conditionsPOJO.setAfterPumpMainPressure(Float.parseFloat(cells[9].getContents().toString()));
			if (!cells[10].getContents().isEmpty())
				conditionsPOJO.setPilotPressure1(Float.parseFloat(cells[10].getContents().toString()));
			if (!cells[11].getContents().isEmpty())
				conditionsPOJO.setPilotPressure2(Float.parseFloat(cells[11].getContents().toString()));
			if (!cells[12].getContents().isEmpty())
				conditionsPOJO.setBeforePumpPressure(Float.parseFloat(cells[12].getContents().toString()));
			if (!cells[13].getContents().isEmpty())
				conditionsPOJO.setAfterPumpPressure(Float.parseFloat(cells[13].getContents().toString()));
			if (!cells[14].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent1(Integer.parseInt(cells[14].getContents().toString()));
			if (!cells[15].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent2(Integer.parseInt(cells[15].getContents().toString()));
			if (!cells[16].getContents().isEmpty())
				conditionsPOJO.setTotalWorkingHours(Float.parseFloat(cells[16].getContents().toString()));
			if (!cells[17].getContents().isEmpty())
				conditionsPOJO.setGear(Integer.parseInt(cells[17].getContents().toString()));
			if (!cells[18].getContents().isEmpty())
				conditionsPOJO.setHighEngineCoolantTemperVal(Integer.parseInt(cells[18].getContents().toString()));			
			break;
		case 20:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			if (!cells[4].getContents().isEmpty())
				conditionsPOJO.setEngineFuelLevel(Float.parseFloat(cells[4].getContents().toString()));
			if (!cells[5].getContents().isEmpty())
				conditionsPOJO.setEngineSpeed(Integer.parseInt(cells[5].getContents().toString()));
			if (!cells[6].getContents().isEmpty())
				conditionsPOJO.setHydraulicOilTemperature(Integer.parseInt(cells[6].getContents().toString()));
			if (!cells[7].getContents().isEmpty())
				conditionsPOJO.setSystemVoltage(Float.parseFloat(cells[7].getContents().toString()));
			if (!cells[8].getContents().isEmpty())
				conditionsPOJO.setBeforePumpMainPressure(Float.parseFloat(cells[8].getContents().toString()));
			if (!cells[9].getContents().isEmpty())
				conditionsPOJO.setAfterPumpMainPressure(Float.parseFloat(cells[9].getContents().toString()));
			if (!cells[10].getContents().isEmpty())
				conditionsPOJO.setPilotPressure1(Float.parseFloat(cells[10].getContents().toString()));
			if (!cells[11].getContents().isEmpty())
				conditionsPOJO.setPilotPressure2(Float.parseFloat(cells[11].getContents().toString()));
			if (!cells[12].getContents().isEmpty())
				conditionsPOJO.setBeforePumpPressure(Float.parseFloat(cells[12].getContents().toString()));
			if (!cells[13].getContents().isEmpty())
				conditionsPOJO.setAfterPumpPressure(Float.parseFloat(cells[13].getContents().toString()));
			if (!cells[14].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent1(Integer.parseInt(cells[14].getContents().toString()));
			if (!cells[15].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent2(Integer.parseInt(cells[15].getContents().toString()));
			if (!cells[16].getContents().isEmpty())
				conditionsPOJO.setTotalWorkingHours(Float.parseFloat(cells[16].getContents().toString()));
			if (!cells[17].getContents().isEmpty())
				conditionsPOJO.setGear(Integer.parseInt(cells[17].getContents().toString()));
			if (!cells[18].getContents().isEmpty())
				conditionsPOJO.setHighEngineCoolantTemperVal(Integer.parseInt(cells[18].getContents().toString()));
			if (!cells[19].getContents().isEmpty())
				conditionsPOJO.setLowEngineOilPressureAlarmValue(Float.parseFloat(cells[19].getContents().toString()));			
			break;
		case 21:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			if (!cells[4].getContents().isEmpty())
				conditionsPOJO.setEngineFuelLevel(Float.parseFloat(cells[4].getContents().toString()));
			if (!cells[5].getContents().isEmpty())
				conditionsPOJO.setEngineSpeed(Integer.parseInt(cells[5].getContents().toString()));
			if (!cells[6].getContents().isEmpty())
				conditionsPOJO.setHydraulicOilTemperature(Integer.parseInt(cells[6].getContents().toString()));
			if (!cells[7].getContents().isEmpty())
				conditionsPOJO.setSystemVoltage(Float.parseFloat(cells[7].getContents().toString()));
			if (!cells[8].getContents().isEmpty())
				conditionsPOJO.setBeforePumpMainPressure(Float.parseFloat(cells[8].getContents().toString()));
			if (!cells[9].getContents().isEmpty())
				conditionsPOJO.setAfterPumpMainPressure(Float.parseFloat(cells[9].getContents().toString()));
			if (!cells[10].getContents().isEmpty())
				conditionsPOJO.setPilotPressure1(Float.parseFloat(cells[10].getContents().toString()));
			if (!cells[11].getContents().isEmpty())
				conditionsPOJO.setPilotPressure2(Float.parseFloat(cells[11].getContents().toString()));
			if (!cells[12].getContents().isEmpty())
				conditionsPOJO.setBeforePumpPressure(Float.parseFloat(cells[12].getContents().toString()));
			if (!cells[13].getContents().isEmpty())
				conditionsPOJO.setAfterPumpPressure(Float.parseFloat(cells[13].getContents().toString()));
			if (!cells[14].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent1(Integer.parseInt(cells[14].getContents().toString()));
			if (!cells[15].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent2(Integer.parseInt(cells[15].getContents().toString()));
			if (!cells[16].getContents().isEmpty())
				conditionsPOJO.setTotalWorkingHours(Float.parseFloat(cells[16].getContents().toString()));
			if (!cells[17].getContents().isEmpty())
				conditionsPOJO.setGear(Integer.parseInt(cells[17].getContents().toString()));
			if (!cells[18].getContents().isEmpty())
				conditionsPOJO.setHighEngineCoolantTemperVal(Integer.parseInt(cells[18].getContents().toString()));
			if (!cells[19].getContents().isEmpty())
				conditionsPOJO.setLowEngineOilPressureAlarmValue(Float.parseFloat(cells[19].getContents().toString()));
			if (!cells[20].getContents().isEmpty())
				conditionsPOJO.setHighVoltageAlarmValue(Float.parseFloat(cells[20].getContents().toString()));
			break;
		case 22:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			if (!cells[4].getContents().isEmpty())
				conditionsPOJO.setEngineFuelLevel(Float.parseFloat(cells[4].getContents().toString()));
			if (!cells[5].getContents().isEmpty())
				conditionsPOJO.setEngineSpeed(Integer.parseInt(cells[5].getContents().toString()));
			if (!cells[6].getContents().isEmpty())
				conditionsPOJO.setHydraulicOilTemperature(Integer.parseInt(cells[6].getContents().toString()));
			if (!cells[7].getContents().isEmpty())
				conditionsPOJO.setSystemVoltage(Float.parseFloat(cells[7].getContents().toString()));
			if (!cells[8].getContents().isEmpty())
				conditionsPOJO.setBeforePumpMainPressure(Float.parseFloat(cells[8].getContents().toString()));
			if (!cells[9].getContents().isEmpty())
				conditionsPOJO.setAfterPumpMainPressure(Float.parseFloat(cells[9].getContents().toString()));
			if (!cells[10].getContents().isEmpty())
				conditionsPOJO.setPilotPressure1(Float.parseFloat(cells[10].getContents().toString()));
			if (!cells[11].getContents().isEmpty())
				conditionsPOJO.setPilotPressure2(Float.parseFloat(cells[11].getContents().toString()));
			if (!cells[12].getContents().isEmpty())
				conditionsPOJO.setBeforePumpPressure(Float.parseFloat(cells[12].getContents().toString()));
			if (!cells[13].getContents().isEmpty())
				conditionsPOJO.setAfterPumpPressure(Float.parseFloat(cells[13].getContents().toString()));
			if (!cells[14].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent1(Integer.parseInt(cells[14].getContents().toString()));
			if (!cells[15].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent2(Integer.parseInt(cells[15].getContents().toString()));
			if (!cells[16].getContents().isEmpty())
				conditionsPOJO.setTotalWorkingHours(Float.parseFloat(cells[16].getContents().toString()));
			if (!cells[17].getContents().isEmpty())
				conditionsPOJO.setGear(Integer.parseInt(cells[17].getContents().toString()));
			if (!cells[18].getContents().isEmpty())
				conditionsPOJO.setHighEngineCoolantTemperVal(Integer.parseInt(cells[18].getContents().toString()));
			if (!cells[19].getContents().isEmpty())
				conditionsPOJO.setLowEngineOilPressureAlarmValue(Float.parseFloat(cells[19].getContents().toString()));
			if (!cells[20].getContents().isEmpty())
				conditionsPOJO.setHighVoltageAlarmValue(Float.parseFloat(cells[20].getContents().toString()));
			if (!cells[21].getContents().isEmpty())
				conditionsPOJO.setLowVoltageAlarmValue(Float.parseFloat(cells[21].getContents().toString()));			
			break;
		case 23:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			if (!cells[4].getContents().isEmpty())
				conditionsPOJO.setEngineFuelLevel(Float.parseFloat(cells[4].getContents().toString()));
			if (!cells[5].getContents().isEmpty())
				conditionsPOJO.setEngineSpeed(Integer.parseInt(cells[5].getContents().toString()));
			if (!cells[6].getContents().isEmpty())
				conditionsPOJO.setHydraulicOilTemperature(Integer.parseInt(cells[6].getContents().toString()));
			if (!cells[7].getContents().isEmpty())
				conditionsPOJO.setSystemVoltage(Float.parseFloat(cells[7].getContents().toString()));
			if (!cells[8].getContents().isEmpty())
				conditionsPOJO.setBeforePumpMainPressure(Float.parseFloat(cells[8].getContents().toString()));
			if (!cells[9].getContents().isEmpty())
				conditionsPOJO.setAfterPumpMainPressure(Float.parseFloat(cells[9].getContents().toString()));
			if (!cells[10].getContents().isEmpty())
				conditionsPOJO.setPilotPressure1(Float.parseFloat(cells[10].getContents().toString()));
			if (!cells[11].getContents().isEmpty())
				conditionsPOJO.setPilotPressure2(Float.parseFloat(cells[11].getContents().toString()));
			if (!cells[12].getContents().isEmpty())
				conditionsPOJO.setBeforePumpPressure(Float.parseFloat(cells[12].getContents().toString()));
			if (!cells[13].getContents().isEmpty())
				conditionsPOJO.setAfterPumpPressure(Float.parseFloat(cells[13].getContents().toString()));
			if (!cells[14].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent1(Integer.parseInt(cells[14].getContents().toString()));
			if (!cells[15].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent2(Integer.parseInt(cells[15].getContents().toString()));
			if (!cells[16].getContents().isEmpty())
				conditionsPOJO.setTotalWorkingHours(Float.parseFloat(cells[16].getContents().toString()));
			if (!cells[17].getContents().isEmpty())
				conditionsPOJO.setGear(Integer.parseInt(cells[17].getContents().toString()));
			if (!cells[18].getContents().isEmpty())
				conditionsPOJO.setHighEngineCoolantTemperVal(Integer.parseInt(cells[18].getContents().toString()));
			if (!cells[19].getContents().isEmpty())
				conditionsPOJO.setLowEngineOilPressureAlarmValue(Float.parseFloat(cells[19].getContents().toString()));
			if (!cells[20].getContents().isEmpty())
				conditionsPOJO.setHighVoltageAlarmValue(Float.parseFloat(cells[20].getContents().toString()));
			if (!cells[21].getContents().isEmpty())
				conditionsPOJO.setLowVoltageAlarmValue(Float.parseFloat(cells[21].getContents().toString()));
			if (!cells[22].getContents().isEmpty())
				conditionsPOJO.setHighHydraulicOilTemperAlarmVal(Integer.parseInt(cells[22].getContents().toString()));
		    	break;
		case 24:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			if (!cells[4].getContents().isEmpty())
				conditionsPOJO.setEngineFuelLevel(Float.parseFloat(cells[4].getContents().toString()));
			if (!cells[5].getContents().isEmpty())
				conditionsPOJO.setEngineSpeed(Integer.parseInt(cells[5].getContents().toString()));
			if (!cells[6].getContents().isEmpty())
				conditionsPOJO.setHydraulicOilTemperature(Integer.parseInt(cells[6].getContents().toString()));
			if (!cells[7].getContents().isEmpty())
				conditionsPOJO.setSystemVoltage(Float.parseFloat(cells[7].getContents().toString()));
			if (!cells[8].getContents().isEmpty())
				conditionsPOJO.setBeforePumpMainPressure(Float.parseFloat(cells[8].getContents().toString()));
			if (!cells[9].getContents().isEmpty())
				conditionsPOJO.setAfterPumpMainPressure(Float.parseFloat(cells[9].getContents().toString()));
			if (!cells[10].getContents().isEmpty())
				conditionsPOJO.setPilotPressure1(Float.parseFloat(cells[10].getContents().toString()));
			if (!cells[11].getContents().isEmpty())
				conditionsPOJO.setPilotPressure2(Float.parseFloat(cells[11].getContents().toString()));
			if (!cells[12].getContents().isEmpty())
				conditionsPOJO.setBeforePumpPressure(Float.parseFloat(cells[12].getContents().toString()));
			if (!cells[13].getContents().isEmpty())
				conditionsPOJO.setAfterPumpPressure(Float.parseFloat(cells[13].getContents().toString()));
			if (!cells[14].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent1(Integer.parseInt(cells[14].getContents().toString()));
			if (!cells[15].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent2(Integer.parseInt(cells[15].getContents().toString()));
			if (!cells[16].getContents().isEmpty())
				conditionsPOJO.setTotalWorkingHours(Float.parseFloat(cells[16].getContents().toString()));
			if (!cells[17].getContents().isEmpty())
				conditionsPOJO.setGear(Integer.parseInt(cells[17].getContents().toString()));
			if (!cells[18].getContents().isEmpty())
				conditionsPOJO.setHighEngineCoolantTemperVal(Integer.parseInt(cells[18].getContents().toString()));
			if (!cells[19].getContents().isEmpty())
				conditionsPOJO.setLowEngineOilPressureAlarmValue(Float.parseFloat(cells[19].getContents().toString()));
			if (!cells[20].getContents().isEmpty())
				conditionsPOJO.setHighVoltageAlarmValue(Float.parseFloat(cells[20].getContents().toString()));
			if (!cells[21].getContents().isEmpty())
				conditionsPOJO.setLowVoltageAlarmValue(Float.parseFloat(cells[21].getContents().toString()));
			if (!cells[22].getContents().isEmpty())
				conditionsPOJO.setHighHydraulicOilTemperAlarmVal(Integer.parseInt(cells[22].getContents().toString()));
			if (!cells[23].getContents().isEmpty())
				conditionsPOJO.setToothNumberValue(Integer.parseInt(cells[23].getContents().toString()));			
			break;
		case 25:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			if (!cells[4].getContents().isEmpty())
				conditionsPOJO.setEngineFuelLevel(Float.parseFloat(cells[4].getContents().toString()));
			if (!cells[5].getContents().isEmpty())
				conditionsPOJO.setEngineSpeed(Integer.parseInt(cells[5].getContents().toString()));
			if (!cells[6].getContents().isEmpty())
				conditionsPOJO.setHydraulicOilTemperature(Integer.parseInt(cells[6].getContents().toString()));
			if (!cells[7].getContents().isEmpty())
				conditionsPOJO.setSystemVoltage(Float.parseFloat(cells[7].getContents().toString()));
			if (!cells[8].getContents().isEmpty())
				conditionsPOJO.setBeforePumpMainPressure(Float.parseFloat(cells[8].getContents().toString()));
			if (!cells[9].getContents().isEmpty())
				conditionsPOJO.setAfterPumpMainPressure(Float.parseFloat(cells[9].getContents().toString()));
			if (!cells[10].getContents().isEmpty())
				conditionsPOJO.setPilotPressure1(Float.parseFloat(cells[10].getContents().toString()));
			if (!cells[11].getContents().isEmpty())
				conditionsPOJO.setPilotPressure2(Float.parseFloat(cells[11].getContents().toString()));
			if (!cells[12].getContents().isEmpty())
				conditionsPOJO.setBeforePumpPressure(Float.parseFloat(cells[12].getContents().toString()));
			if (!cells[13].getContents().isEmpty())
				conditionsPOJO.setAfterPumpPressure(Float.parseFloat(cells[13].getContents().toString()));
			if (!cells[14].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent1(Integer.parseInt(cells[14].getContents().toString()));
			if (!cells[15].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent2(Integer.parseInt(cells[15].getContents().toString()));
			if (!cells[16].getContents().isEmpty())
				conditionsPOJO.setTotalWorkingHours(Float.parseFloat(cells[16].getContents().toString()));
			if (!cells[17].getContents().isEmpty())
				conditionsPOJO.setGear(Integer.parseInt(cells[17].getContents().toString()));
			if (!cells[18].getContents().isEmpty())
				conditionsPOJO.setHighEngineCoolantTemperVal(Integer.parseInt(cells[18].getContents().toString()));
			if (!cells[19].getContents().isEmpty())
				conditionsPOJO.setLowEngineOilPressureAlarmValue(Float.parseFloat(cells[19].getContents().toString()));
			if (!cells[20].getContents().isEmpty())
				conditionsPOJO.setHighVoltageAlarmValue(Float.parseFloat(cells[20].getContents().toString()));
			if (!cells[21].getContents().isEmpty())
				conditionsPOJO.setLowVoltageAlarmValue(Float.parseFloat(cells[21].getContents().toString()));
			if (!cells[22].getContents().isEmpty())
				conditionsPOJO.setHighHydraulicOilTemperAlarmVal(Integer.parseInt(cells[22].getContents().toString()));
			if (!cells[23].getContents().isEmpty())
				conditionsPOJO.setToothNumberValue(Integer.parseInt(cells[23].getContents().toString()));
			if (!cells[24].getContents().isEmpty())
				conditionsPOJO.setMonitorSoftwareCode(cells[24].getContents().toString());			
			break;
		case 26:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			if (!cells[4].getContents().isEmpty())
				conditionsPOJO.setEngineFuelLevel(Float.parseFloat(cells[4].getContents().toString()));
			if (!cells[5].getContents().isEmpty())
				conditionsPOJO.setEngineSpeed(Integer.parseInt(cells[5].getContents().toString()));
			if (!cells[6].getContents().isEmpty())
				conditionsPOJO.setHydraulicOilTemperature(Integer.parseInt(cells[6].getContents().toString()));
			if (!cells[7].getContents().isEmpty())
				conditionsPOJO.setSystemVoltage(Float.parseFloat(cells[7].getContents().toString()));
			if (!cells[8].getContents().isEmpty())
				conditionsPOJO.setBeforePumpMainPressure(Float.parseFloat(cells[8].getContents().toString()));
			if (!cells[9].getContents().isEmpty())
				conditionsPOJO.setAfterPumpMainPressure(Float.parseFloat(cells[9].getContents().toString()));
			if (!cells[10].getContents().isEmpty())
				conditionsPOJO.setPilotPressure1(Float.parseFloat(cells[10].getContents().toString()));
			if (!cells[11].getContents().isEmpty())
				conditionsPOJO.setPilotPressure2(Float.parseFloat(cells[11].getContents().toString()));
			if (!cells[12].getContents().isEmpty())
				conditionsPOJO.setBeforePumpPressure(Float.parseFloat(cells[12].getContents().toString()));
			if (!cells[13].getContents().isEmpty())
				conditionsPOJO.setAfterPumpPressure(Float.parseFloat(cells[13].getContents().toString()));
			if (!cells[14].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent1(Integer.parseInt(cells[14].getContents().toString()));
			if (!cells[15].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent2(Integer.parseInt(cells[15].getContents().toString()));
			if (!cells[16].getContents().isEmpty())
				conditionsPOJO.setTotalWorkingHours(Float.parseFloat(cells[16].getContents().toString()));
			if (!cells[17].getContents().isEmpty())
				conditionsPOJO.setGear(Integer.parseInt(cells[17].getContents().toString()));
			if (!cells[18].getContents().isEmpty())
				conditionsPOJO.setHighEngineCoolantTemperVal(Integer.parseInt(cells[18].getContents().toString()));
			if (!cells[19].getContents().isEmpty())
				conditionsPOJO.setLowEngineOilPressureAlarmValue(Float.parseFloat(cells[19].getContents().toString()));
			if (!cells[20].getContents().isEmpty())
				conditionsPOJO.setHighVoltageAlarmValue(Float.parseFloat(cells[20].getContents().toString()));
			if (!cells[21].getContents().isEmpty())
				conditionsPOJO.setLowVoltageAlarmValue(Float.parseFloat(cells[21].getContents().toString()));
			if (!cells[22].getContents().isEmpty())
				conditionsPOJO.setHighHydraulicOilTemperAlarmVal(Integer.parseInt(cells[22].getContents().toString()));
			if (!cells[23].getContents().isEmpty())
				conditionsPOJO.setToothNumberValue(Integer.parseInt(cells[23].getContents().toString()));
			if (!cells[24].getContents().isEmpty())
				conditionsPOJO.setMonitorSoftwareCode(cells[24].getContents().toString());
			if (!cells[25].getContents().isEmpty())
				conditionsPOJO.setMonitorYcSoftwareCode(cells[25].getContents().toString());			
			break;
		case 27:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			if (!cells[4].getContents().isEmpty())
				conditionsPOJO.setEngineFuelLevel(Float.parseFloat(cells[4].getContents().toString()));
			if (!cells[5].getContents().isEmpty())
				conditionsPOJO.setEngineSpeed(Integer.parseInt(cells[5].getContents().toString()));
			if (!cells[6].getContents().isEmpty())
				conditionsPOJO.setHydraulicOilTemperature(Integer.parseInt(cells[6].getContents().toString()));
			if (!cells[7].getContents().isEmpty())
				conditionsPOJO.setSystemVoltage(Float.parseFloat(cells[7].getContents().toString()));
			if (!cells[8].getContents().isEmpty())
				conditionsPOJO.setBeforePumpMainPressure(Float.parseFloat(cells[8].getContents().toString()));
			if (!cells[9].getContents().isEmpty())
				conditionsPOJO.setAfterPumpMainPressure(Float.parseFloat(cells[9].getContents().toString()));
			if (!cells[10].getContents().isEmpty())
				conditionsPOJO.setPilotPressure1(Float.parseFloat(cells[10].getContents().toString()));
			if (!cells[11].getContents().isEmpty())
				conditionsPOJO.setPilotPressure2(Float.parseFloat(cells[11].getContents().toString()));
			if (!cells[12].getContents().isEmpty())
				conditionsPOJO.setBeforePumpPressure(Float.parseFloat(cells[12].getContents().toString()));
			if (!cells[13].getContents().isEmpty())
				conditionsPOJO.setAfterPumpPressure(Float.parseFloat(cells[13].getContents().toString()));
			if (!cells[14].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent1(Integer.parseInt(cells[14].getContents().toString()));
			if (!cells[15].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent2(Integer.parseInt(cells[15].getContents().toString()));
			if (!cells[16].getContents().isEmpty())
				conditionsPOJO.setTotalWorkingHours(Float.parseFloat(cells[16].getContents().toString()));
			if (!cells[17].getContents().isEmpty())
				conditionsPOJO.setGear(Integer.parseInt(cells[17].getContents().toString()));
			if (!cells[18].getContents().isEmpty())
				conditionsPOJO.setHighEngineCoolantTemperVal(Integer.parseInt(cells[18].getContents().toString()));
			if (!cells[19].getContents().isEmpty())
				conditionsPOJO.setLowEngineOilPressureAlarmValue(Float.parseFloat(cells[19].getContents().toString()));
			if (!cells[20].getContents().isEmpty())
				conditionsPOJO.setHighVoltageAlarmValue(Float.parseFloat(cells[20].getContents().toString()));
			if (!cells[21].getContents().isEmpty())
				conditionsPOJO.setLowVoltageAlarmValue(Float.parseFloat(cells[21].getContents().toString()));
			if (!cells[22].getContents().isEmpty())
				conditionsPOJO.setHighHydraulicOilTemperAlarmVal(Integer.parseInt(cells[22].getContents().toString()));
			if (!cells[23].getContents().isEmpty())
				conditionsPOJO.setToothNumberValue(Integer.parseInt(cells[23].getContents().toString()));
			if (!cells[24].getContents().isEmpty())
				conditionsPOJO.setMonitorSoftwareCode(cells[24].getContents().toString());
			if (!cells[25].getContents().isEmpty())
				conditionsPOJO.setMonitorYcSoftwareCode(cells[25].getContents().toString());
			if (!cells[26].getContents().isEmpty())
				conditionsPOJO.setControllerSoftwareCode(cells[26].getContents().toString());			
			break;
		case 28:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			if (!cells[4].getContents().isEmpty())
				conditionsPOJO.setEngineFuelLevel(Float.parseFloat(cells[4].getContents().toString()));
			if (!cells[5].getContents().isEmpty())
				conditionsPOJO.setEngineSpeed(Integer.parseInt(cells[5].getContents().toString()));
			if (!cells[6].getContents().isEmpty())
				conditionsPOJO.setHydraulicOilTemperature(Integer.parseInt(cells[6].getContents().toString()));
			if (!cells[7].getContents().isEmpty())
				conditionsPOJO.setSystemVoltage(Float.parseFloat(cells[7].getContents().toString()));
			if (!cells[8].getContents().isEmpty())
				conditionsPOJO.setBeforePumpMainPressure(Float.parseFloat(cells[8].getContents().toString()));
			if (!cells[9].getContents().isEmpty())
				conditionsPOJO.setAfterPumpMainPressure(Float.parseFloat(cells[9].getContents().toString()));
			if (!cells[10].getContents().isEmpty())
				conditionsPOJO.setPilotPressure1(Float.parseFloat(cells[10].getContents().toString()));
			if (!cells[11].getContents().isEmpty())
				conditionsPOJO.setPilotPressure2(Float.parseFloat(cells[11].getContents().toString()));
			if (!cells[12].getContents().isEmpty())
				conditionsPOJO.setBeforePumpPressure(Float.parseFloat(cells[12].getContents().toString()));
			if (!cells[13].getContents().isEmpty())
				conditionsPOJO.setAfterPumpPressure(Float.parseFloat(cells[13].getContents().toString()));
			if (!cells[14].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent1(Integer.parseInt(cells[14].getContents().toString()));
			if (!cells[15].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent2(Integer.parseInt(cells[15].getContents().toString()));
			if (!cells[16].getContents().isEmpty())
				conditionsPOJO.setTotalWorkingHours(Float.parseFloat(cells[16].getContents().toString()));
			if (!cells[17].getContents().isEmpty())
				conditionsPOJO.setGear(Integer.parseInt(cells[17].getContents().toString()));
			if (!cells[18].getContents().isEmpty())
				conditionsPOJO.setHighEngineCoolantTemperVal(Integer.parseInt(cells[18].getContents().toString()));
			if (!cells[19].getContents().isEmpty())
				conditionsPOJO.setLowEngineOilPressureAlarmValue(Float.parseFloat(cells[19].getContents().toString()));
			if (!cells[20].getContents().isEmpty())
				conditionsPOJO.setHighVoltageAlarmValue(Float.parseFloat(cells[20].getContents().toString()));
			if (!cells[21].getContents().isEmpty())
				conditionsPOJO.setLowVoltageAlarmValue(Float.parseFloat(cells[21].getContents().toString()));
			if (!cells[22].getContents().isEmpty())
				conditionsPOJO.setHighHydraulicOilTemperAlarmVal(Integer.parseInt(cells[22].getContents().toString()));
			if (!cells[23].getContents().isEmpty())
				conditionsPOJO.setToothNumberValue(Integer.parseInt(cells[23].getContents().toString()));
			if (!cells[24].getContents().isEmpty())
				conditionsPOJO.setMonitorSoftwareCode(cells[24].getContents().toString());
			if (!cells[25].getContents().isEmpty())
				conditionsPOJO.setMonitorYcSoftwareCode(cells[25].getContents().toString());
			if (!cells[26].getContents().isEmpty())
				conditionsPOJO.setControllerSoftwareCode(cells[26].getContents().toString());
			if (!cells[27].getContents().isEmpty())
				conditionsPOJO.setControllerYcSoftwareCode(cells[27].getContents().toString());		
			break;
		case 29:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			if (!cells[4].getContents().isEmpty())
				conditionsPOJO.setEngineFuelLevel(Float.parseFloat(cells[4].getContents().toString()));
			if (!cells[5].getContents().isEmpty())
				conditionsPOJO.setEngineSpeed(Integer.parseInt(cells[5].getContents().toString()));
			if (!cells[6].getContents().isEmpty())
				conditionsPOJO.setHydraulicOilTemperature(Integer.parseInt(cells[6].getContents().toString()));
			if (!cells[7].getContents().isEmpty())
				conditionsPOJO.setSystemVoltage(Float.parseFloat(cells[7].getContents().toString()));
			if (!cells[8].getContents().isEmpty())
				conditionsPOJO.setBeforePumpMainPressure(Float.parseFloat(cells[8].getContents().toString()));
			if (!cells[9].getContents().isEmpty())
				conditionsPOJO.setAfterPumpMainPressure(Float.parseFloat(cells[9].getContents().toString()));
			if (!cells[10].getContents().isEmpty())
				conditionsPOJO.setPilotPressure1(Float.parseFloat(cells[10].getContents().toString()));
			if (!cells[11].getContents().isEmpty())
				conditionsPOJO.setPilotPressure2(Float.parseFloat(cells[11].getContents().toString()));
			if (!cells[12].getContents().isEmpty())
				conditionsPOJO.setBeforePumpPressure(Float.parseFloat(cells[12].getContents().toString()));
			if (!cells[13].getContents().isEmpty())
				conditionsPOJO.setAfterPumpPressure(Float.parseFloat(cells[13].getContents().toString()));
			if (!cells[14].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent1(Integer.parseInt(cells[14].getContents().toString()));
			if (!cells[15].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent2(Integer.parseInt(cells[15].getContents().toString()));
			if (!cells[16].getContents().isEmpty())
				conditionsPOJO.setTotalWorkingHours(Float.parseFloat(cells[16].getContents().toString()));
			if (!cells[17].getContents().isEmpty())
				conditionsPOJO.setGear(Integer.parseInt(cells[17].getContents().toString()));
			if (!cells[18].getContents().isEmpty())
				conditionsPOJO.setHighEngineCoolantTemperVal(Integer.parseInt(cells[18].getContents().toString()));
			if (!cells[19].getContents().isEmpty())
				conditionsPOJO.setLowEngineOilPressureAlarmValue(Float.parseFloat(cells[19].getContents().toString()));
			if (!cells[20].getContents().isEmpty())
				conditionsPOJO.setHighVoltageAlarmValue(Float.parseFloat(cells[20].getContents().toString()));
			if (!cells[21].getContents().isEmpty())
				conditionsPOJO.setLowVoltageAlarmValue(Float.parseFloat(cells[21].getContents().toString()));
			if (!cells[22].getContents().isEmpty())
				conditionsPOJO.setHighHydraulicOilTemperAlarmVal(Integer.parseInt(cells[22].getContents().toString()));
			if (!cells[23].getContents().isEmpty())
				conditionsPOJO.setToothNumberValue(Integer.parseInt(cells[23].getContents().toString()));
			if (!cells[24].getContents().isEmpty())
				conditionsPOJO.setMonitorSoftwareCode(cells[24].getContents().toString());
			if (!cells[25].getContents().isEmpty())
				conditionsPOJO.setMonitorYcSoftwareCode(cells[25].getContents().toString());
			if (!cells[26].getContents().isEmpty())
				conditionsPOJO.setControllerSoftwareCode(cells[26].getContents().toString());
			if (!cells[27].getContents().isEmpty())
				conditionsPOJO.setControllerYcSoftwareCode(cells[27].getContents().toString());
			if (!cells[28].getContents().isEmpty())
				conditionsPOJO.setRawData(cells[28].getContents().toString());			
			break;
		case 30:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			if (!cells[4].getContents().isEmpty())
				conditionsPOJO.setEngineFuelLevel(Float.parseFloat(cells[4].getContents().toString()));
			if (!cells[5].getContents().isEmpty())
				conditionsPOJO.setEngineSpeed(Integer.parseInt(cells[5].getContents().toString()));
			if (!cells[6].getContents().isEmpty())
				conditionsPOJO.setHydraulicOilTemperature(Integer.parseInt(cells[6].getContents().toString()));
			if (!cells[7].getContents().isEmpty())
				conditionsPOJO.setSystemVoltage(Float.parseFloat(cells[7].getContents().toString()));
			if (!cells[8].getContents().isEmpty())
				conditionsPOJO.setBeforePumpMainPressure(Float.parseFloat(cells[8].getContents().toString()));
			if (!cells[9].getContents().isEmpty())
				conditionsPOJO.setAfterPumpMainPressure(Float.parseFloat(cells[9].getContents().toString()));
			if (!cells[10].getContents().isEmpty())
				conditionsPOJO.setPilotPressure1(Float.parseFloat(cells[10].getContents().toString()));
			if (!cells[11].getContents().isEmpty())
				conditionsPOJO.setPilotPressure2(Float.parseFloat(cells[11].getContents().toString()));
			if (!cells[12].getContents().isEmpty())
				conditionsPOJO.setBeforePumpPressure(Float.parseFloat(cells[12].getContents().toString()));
			if (!cells[13].getContents().isEmpty())
				conditionsPOJO.setAfterPumpPressure(Float.parseFloat(cells[13].getContents().toString()));
			if (!cells[14].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent1(Integer.parseInt(cells[14].getContents().toString()));
			if (!cells[15].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent2(Integer.parseInt(cells[15].getContents().toString()));
			if (!cells[16].getContents().isEmpty())
				conditionsPOJO.setTotalWorkingHours(Float.parseFloat(cells[16].getContents().toString()));
			if (!cells[17].getContents().isEmpty())
				conditionsPOJO.setGear(Integer.parseInt(cells[17].getContents().toString()));
			if (!cells[18].getContents().isEmpty())
				conditionsPOJO.setHighEngineCoolantTemperVal(Integer.parseInt(cells[18].getContents().toString()));
			if (!cells[19].getContents().isEmpty())
				conditionsPOJO.setLowEngineOilPressureAlarmValue(Float.parseFloat(cells[19].getContents().toString()));
			if (!cells[20].getContents().isEmpty())
				conditionsPOJO.setHighVoltageAlarmValue(Float.parseFloat(cells[20].getContents().toString()));
			if (!cells[21].getContents().isEmpty())
				conditionsPOJO.setLowVoltageAlarmValue(Float.parseFloat(cells[21].getContents().toString()));
			if (!cells[22].getContents().isEmpty())
				conditionsPOJO.setHighHydraulicOilTemperAlarmVal(Integer.parseInt(cells[22].getContents().toString()));
			if (!cells[23].getContents().isEmpty())
				conditionsPOJO.setToothNumberValue(Integer.parseInt(cells[23].getContents().toString()));
			if (!cells[24].getContents().isEmpty())
				conditionsPOJO.setMonitorSoftwareCode(cells[24].getContents().toString());
			if (!cells[25].getContents().isEmpty())
				conditionsPOJO.setMonitorYcSoftwareCode(cells[25].getContents().toString());
			if (!cells[26].getContents().isEmpty())
				conditionsPOJO.setControllerSoftwareCode(cells[26].getContents().toString());
			if (!cells[27].getContents().isEmpty())
				conditionsPOJO.setControllerYcSoftwareCode(cells[27].getContents().toString());
			if (!cells[28].getContents().isEmpty())
				conditionsPOJO.setRawData(cells[28].getContents().toString());
			if (!cells[29].getContents().isEmpty())
				conditionsPOJO.setIsWork(Integer.parseInt(cells[29].getContents().toString()));					
			    break;
		case 31:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			if (!cells[4].getContents().isEmpty())
				conditionsPOJO.setEngineFuelLevel(Float.parseFloat(cells[4].getContents().toString()));
			if (!cells[5].getContents().isEmpty())
				conditionsPOJO.setEngineSpeed(Integer.parseInt(cells[5].getContents().toString()));
			if (!cells[6].getContents().isEmpty())
				conditionsPOJO.setHydraulicOilTemperature(Integer.parseInt(cells[6].getContents().toString()));
			if (!cells[7].getContents().isEmpty())
				conditionsPOJO.setSystemVoltage(Float.parseFloat(cells[7].getContents().toString()));
			if (!cells[8].getContents().isEmpty())
				conditionsPOJO.setBeforePumpMainPressure(Float.parseFloat(cells[8].getContents().toString()));
			if (!cells[9].getContents().isEmpty())
				conditionsPOJO.setAfterPumpMainPressure(Float.parseFloat(cells[9].getContents().toString()));
			if (!cells[10].getContents().isEmpty())
				conditionsPOJO.setPilotPressure1(Float.parseFloat(cells[10].getContents().toString()));
			if (!cells[11].getContents().isEmpty())
				conditionsPOJO.setPilotPressure2(Float.parseFloat(cells[11].getContents().toString()));
			if (!cells[12].getContents().isEmpty())
				conditionsPOJO.setBeforePumpPressure(Float.parseFloat(cells[12].getContents().toString()));
			if (!cells[13].getContents().isEmpty())
				conditionsPOJO.setAfterPumpPressure(Float.parseFloat(cells[13].getContents().toString()));
			if (!cells[14].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent1(Integer.parseInt(cells[14].getContents().toString()));
			if (!cells[15].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent2(Integer.parseInt(cells[15].getContents().toString()));
			if (!cells[16].getContents().isEmpty())
				conditionsPOJO.setTotalWorkingHours(Float.parseFloat(cells[16].getContents().toString()));
			if (!cells[17].getContents().isEmpty())
				conditionsPOJO.setGear(Integer.parseInt(cells[17].getContents().toString()));
			if (!cells[18].getContents().isEmpty())
				conditionsPOJO.setHighEngineCoolantTemperVal(Integer.parseInt(cells[18].getContents().toString()));
			if (!cells[19].getContents().isEmpty())
				conditionsPOJO.setLowEngineOilPressureAlarmValue(Float.parseFloat(cells[19].getContents().toString()));
			if (!cells[20].getContents().isEmpty())
				conditionsPOJO.setHighVoltageAlarmValue(Float.parseFloat(cells[20].getContents().toString()));
			if (!cells[21].getContents().isEmpty())
				conditionsPOJO.setLowVoltageAlarmValue(Float.parseFloat(cells[21].getContents().toString()));
			if (!cells[22].getContents().isEmpty())
				conditionsPOJO.setHighHydraulicOilTemperAlarmVal(Integer.parseInt(cells[22].getContents().toString()));
			if (!cells[23].getContents().isEmpty())
				conditionsPOJO.setToothNumberValue(Integer.parseInt(cells[23].getContents().toString()));
			if (!cells[24].getContents().isEmpty())
				conditionsPOJO.setMonitorSoftwareCode(cells[24].getContents().toString());
			if (!cells[25].getContents().isEmpty())
				conditionsPOJO.setMonitorYcSoftwareCode(cells[25].getContents().toString());
			if (!cells[26].getContents().isEmpty())
				conditionsPOJO.setControllerSoftwareCode(cells[26].getContents().toString());
			if (!cells[27].getContents().isEmpty())
				conditionsPOJO.setControllerYcSoftwareCode(cells[27].getContents().toString());
			if (!cells[28].getContents().isEmpty())
				conditionsPOJO.setRawData(cells[28].getContents().toString());
			if (!cells[29].getContents().isEmpty())
				conditionsPOJO.setIsWork(Integer.parseInt(cells[29].getContents().toString()));					
			if (!cells[30].getContents().isEmpty())
				conditionsPOJO.setFaultCode(Integer.parseInt(cells[30].getContents().toString()));			
			break;
		case 32:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			if (!cells[4].getContents().isEmpty())
				conditionsPOJO.setEngineFuelLevel(Float.parseFloat(cells[4].getContents().toString()));
			if (!cells[5].getContents().isEmpty())
				conditionsPOJO.setEngineSpeed(Integer.parseInt(cells[5].getContents().toString()));
			if (!cells[6].getContents().isEmpty())
				conditionsPOJO.setHydraulicOilTemperature(Integer.parseInt(cells[6].getContents().toString()));
			if (!cells[7].getContents().isEmpty())
				conditionsPOJO.setSystemVoltage(Float.parseFloat(cells[7].getContents().toString()));
			if (!cells[8].getContents().isEmpty())
				conditionsPOJO.setBeforePumpMainPressure(Float.parseFloat(cells[8].getContents().toString()));
			if (!cells[9].getContents().isEmpty())
				conditionsPOJO.setAfterPumpMainPressure(Float.parseFloat(cells[9].getContents().toString()));
			if (!cells[10].getContents().isEmpty())
				conditionsPOJO.setPilotPressure1(Float.parseFloat(cells[10].getContents().toString()));
			if (!cells[11].getContents().isEmpty())
				conditionsPOJO.setPilotPressure2(Float.parseFloat(cells[11].getContents().toString()));
			if (!cells[12].getContents().isEmpty())
				conditionsPOJO.setBeforePumpPressure(Float.parseFloat(cells[12].getContents().toString()));
			if (!cells[13].getContents().isEmpty())
				conditionsPOJO.setAfterPumpPressure(Float.parseFloat(cells[13].getContents().toString()));
			if (!cells[14].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent1(Integer.parseInt(cells[14].getContents().toString()));
			if (!cells[15].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent2(Integer.parseInt(cells[15].getContents().toString()));
			if (!cells[16].getContents().isEmpty())
				conditionsPOJO.setTotalWorkingHours(Float.parseFloat(cells[16].getContents().toString()));
			if (!cells[17].getContents().isEmpty())
				conditionsPOJO.setGear(Integer.parseInt(cells[17].getContents().toString()));
			if (!cells[18].getContents().isEmpty())
				conditionsPOJO.setHighEngineCoolantTemperVal(Integer.parseInt(cells[18].getContents().toString()));
			if (!cells[19].getContents().isEmpty())
				conditionsPOJO.setLowEngineOilPressureAlarmValue(Float.parseFloat(cells[19].getContents().toString()));
			if (!cells[20].getContents().isEmpty())
				conditionsPOJO.setHighVoltageAlarmValue(Float.parseFloat(cells[20].getContents().toString()));
			if (!cells[21].getContents().isEmpty())
				conditionsPOJO.setLowVoltageAlarmValue(Float.parseFloat(cells[21].getContents().toString()));
			if (!cells[22].getContents().isEmpty())
				conditionsPOJO.setHighHydraulicOilTemperAlarmVal(Integer.parseInt(cells[22].getContents().toString()));
			if (!cells[23].getContents().isEmpty())
				conditionsPOJO.setToothNumberValue(Integer.parseInt(cells[23].getContents().toString()));
			if (!cells[24].getContents().isEmpty())
				conditionsPOJO.setMonitorSoftwareCode(cells[24].getContents().toString());
			if (!cells[25].getContents().isEmpty())
				conditionsPOJO.setMonitorYcSoftwareCode(cells[25].getContents().toString());
			if (!cells[26].getContents().isEmpty())
				conditionsPOJO.setControllerSoftwareCode(cells[26].getContents().toString());
			if (!cells[27].getContents().isEmpty())
				conditionsPOJO.setControllerYcSoftwareCode(cells[27].getContents().toString());
			if (!cells[28].getContents().isEmpty())
				conditionsPOJO.setRawData(cells[28].getContents().toString());
			if (!cells[29].getContents().isEmpty())
				conditionsPOJO.setIsWork(Integer.parseInt(cells[29].getContents().toString()));					
			if (!cells[30].getContents().isEmpty())
				conditionsPOJO.setFaultCode(Integer.parseInt(cells[30].getContents().toString()));
			if (!cells[31].getContents().isEmpty())
				conditionsPOJO.setEngineOilTemperature(Integer.parseInt(cells[31].getContents().toString()));		
			break;
		case 33:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			if (!cells[4].getContents().isEmpty())
				conditionsPOJO.setEngineFuelLevel(Float.parseFloat(cells[4].getContents().toString()));
			if (!cells[5].getContents().isEmpty())
				conditionsPOJO.setEngineSpeed(Integer.parseInt(cells[5].getContents().toString()));
			if (!cells[6].getContents().isEmpty())
				conditionsPOJO.setHydraulicOilTemperature(Integer.parseInt(cells[6].getContents().toString()));
			if (!cells[7].getContents().isEmpty())
				conditionsPOJO.setSystemVoltage(Float.parseFloat(cells[7].getContents().toString()));
			if (!cells[8].getContents().isEmpty())
				conditionsPOJO.setBeforePumpMainPressure(Float.parseFloat(cells[8].getContents().toString()));
			if (!cells[9].getContents().isEmpty())
				conditionsPOJO.setAfterPumpMainPressure(Float.parseFloat(cells[9].getContents().toString()));
			if (!cells[10].getContents().isEmpty())
				conditionsPOJO.setPilotPressure1(Float.parseFloat(cells[10].getContents().toString()));
			if (!cells[11].getContents().isEmpty())
				conditionsPOJO.setPilotPressure2(Float.parseFloat(cells[11].getContents().toString()));
			if (!cells[12].getContents().isEmpty())
				conditionsPOJO.setBeforePumpPressure(Float.parseFloat(cells[12].getContents().toString()));
			if (!cells[13].getContents().isEmpty())
				conditionsPOJO.setAfterPumpPressure(Float.parseFloat(cells[13].getContents().toString()));
			if (!cells[14].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent1(Integer.parseInt(cells[14].getContents().toString()));
			if (!cells[15].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent2(Integer.parseInt(cells[15].getContents().toString()));
			if (!cells[16].getContents().isEmpty())
				conditionsPOJO.setTotalWorkingHours(Float.parseFloat(cells[16].getContents().toString()));
			if (!cells[17].getContents().isEmpty())
				conditionsPOJO.setGear(Integer.parseInt(cells[17].getContents().toString()));
			if (!cells[18].getContents().isEmpty())
				conditionsPOJO.setHighEngineCoolantTemperVal(Integer.parseInt(cells[18].getContents().toString()));
			if (!cells[19].getContents().isEmpty())
				conditionsPOJO.setLowEngineOilPressureAlarmValue(Float.parseFloat(cells[19].getContents().toString()));
			if (!cells[20].getContents().isEmpty())
				conditionsPOJO.setHighVoltageAlarmValue(Float.parseFloat(cells[20].getContents().toString()));
			if (!cells[21].getContents().isEmpty())
				conditionsPOJO.setLowVoltageAlarmValue(Float.parseFloat(cells[21].getContents().toString()));
			if (!cells[22].getContents().isEmpty())
				conditionsPOJO.setHighHydraulicOilTemperAlarmVal(Integer.parseInt(cells[22].getContents().toString()));
			if (!cells[23].getContents().isEmpty())
				conditionsPOJO.setToothNumberValue(Integer.parseInt(cells[23].getContents().toString()));
			if (!cells[24].getContents().isEmpty())
				conditionsPOJO.setMonitorSoftwareCode(cells[24].getContents().toString());
			if (!cells[25].getContents().isEmpty())
				conditionsPOJO.setMonitorYcSoftwareCode(cells[25].getContents().toString());
			if (!cells[26].getContents().isEmpty())
				conditionsPOJO.setControllerSoftwareCode(cells[26].getContents().toString());
			if (!cells[27].getContents().isEmpty())
				conditionsPOJO.setControllerYcSoftwareCode(cells[27].getContents().toString());
			if (!cells[28].getContents().isEmpty())
				conditionsPOJO.setRawData(cells[28].getContents().toString());
			if (!cells[29].getContents().isEmpty())
				conditionsPOJO.setIsWork(Integer.parseInt(cells[29].getContents().toString()));					
			if (!cells[30].getContents().isEmpty())
				conditionsPOJO.setFaultCode(Integer.parseInt(cells[30].getContents().toString()));
			if (!cells[31].getContents().isEmpty())
				conditionsPOJO.setEngineOilTemperature(Integer.parseInt(cells[31].getContents().toString()));
			if (!cells[32].getContents().isEmpty())
				conditionsPOJO.setAlock(Integer.parseInt(cells[32].getContents().toString()));			
			break;
		case 34:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			if (!cells[4].getContents().isEmpty())
				conditionsPOJO.setEngineFuelLevel(Float.parseFloat(cells[4].getContents().toString()));
			if (!cells[5].getContents().isEmpty())
				conditionsPOJO.setEngineSpeed(Integer.parseInt(cells[5].getContents().toString()));
			if (!cells[6].getContents().isEmpty())
				conditionsPOJO.setHydraulicOilTemperature(Integer.parseInt(cells[6].getContents().toString()));
			if (!cells[7].getContents().isEmpty())
				conditionsPOJO.setSystemVoltage(Float.parseFloat(cells[7].getContents().toString()));
			if (!cells[8].getContents().isEmpty())
				conditionsPOJO.setBeforePumpMainPressure(Float.parseFloat(cells[8].getContents().toString()));
			if (!cells[9].getContents().isEmpty())
				conditionsPOJO.setAfterPumpMainPressure(Float.parseFloat(cells[9].getContents().toString()));
			if (!cells[10].getContents().isEmpty())
				conditionsPOJO.setPilotPressure1(Float.parseFloat(cells[10].getContents().toString()));
			if (!cells[11].getContents().isEmpty())
				conditionsPOJO.setPilotPressure2(Float.parseFloat(cells[11].getContents().toString()));
			if (!cells[12].getContents().isEmpty())
				conditionsPOJO.setBeforePumpPressure(Float.parseFloat(cells[12].getContents().toString()));
			if (!cells[13].getContents().isEmpty())
				conditionsPOJO.setAfterPumpPressure(Float.parseFloat(cells[13].getContents().toString()));
			if (!cells[14].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent1(Integer.parseInt(cells[14].getContents().toString()));
			if (!cells[15].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent2(Integer.parseInt(cells[15].getContents().toString()));
			if (!cells[16].getContents().isEmpty())
				conditionsPOJO.setTotalWorkingHours(Float.parseFloat(cells[16].getContents().toString()));
			if (!cells[17].getContents().isEmpty())
				conditionsPOJO.setGear(Integer.parseInt(cells[17].getContents().toString()));
			if (!cells[18].getContents().isEmpty())
				conditionsPOJO.setHighEngineCoolantTemperVal(Integer.parseInt(cells[18].getContents().toString()));
			if (!cells[19].getContents().isEmpty())
				conditionsPOJO.setLowEngineOilPressureAlarmValue(Float.parseFloat(cells[19].getContents().toString()));
			if (!cells[20].getContents().isEmpty())
				conditionsPOJO.setHighVoltageAlarmValue(Float.parseFloat(cells[20].getContents().toString()));
			if (!cells[21].getContents().isEmpty())
				conditionsPOJO.setLowVoltageAlarmValue(Float.parseFloat(cells[21].getContents().toString()));
			if (!cells[22].getContents().isEmpty())
				conditionsPOJO.setHighHydraulicOilTemperAlarmVal(Integer.parseInt(cells[22].getContents().toString()));
			if (!cells[23].getContents().isEmpty())
				conditionsPOJO.setToothNumberValue(Integer.parseInt(cells[23].getContents().toString()));
			if (!cells[24].getContents().isEmpty())
				conditionsPOJO.setMonitorSoftwareCode(cells[24].getContents().toString());
			if (!cells[25].getContents().isEmpty())
				conditionsPOJO.setMonitorYcSoftwareCode(cells[25].getContents().toString());
			if (!cells[26].getContents().isEmpty())
				conditionsPOJO.setControllerSoftwareCode(cells[26].getContents().toString());
			if (!cells[27].getContents().isEmpty())
				conditionsPOJO.setControllerYcSoftwareCode(cells[27].getContents().toString());
			if (!cells[28].getContents().isEmpty())
				conditionsPOJO.setRawData(cells[28].getContents().toString());
			if (!cells[29].getContents().isEmpty())
				conditionsPOJO.setIsWork(Integer.parseInt(cells[29].getContents().toString()));					
			if (!cells[30].getContents().isEmpty())
				conditionsPOJO.setFaultCode(Integer.parseInt(cells[30].getContents().toString()));
			if (!cells[31].getContents().isEmpty())
				conditionsPOJO.setEngineOilTemperature(Integer.parseInt(cells[31].getContents().toString()));
			if (!cells[32].getContents().isEmpty())
				conditionsPOJO.setAlock(Integer.parseInt(cells[32].getContents().toString()));
			if (!cells[33].getContents().isEmpty())
				conditionsPOJO.setBlock(Integer.parseInt(cells[33].getContents().toString()));		
			break;
		case 35:
			conditionsPOJO.setUnitId(cells[0].getContents().toString());
			if (!cells[1].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantTemperature(Integer.parseInt(cells[1].getContents().toString()));
			if (!cells[2].getContents().isEmpty())
				conditionsPOJO.setEngineCoolantLevel(Float.parseFloat(cells[2].getContents().toString()));
			if (!cells[3].getContents().isEmpty())
				conditionsPOJO.setEngineOilPressure(Float.parseFloat(cells[3].getContents().toString()));
			if (!cells[4].getContents().isEmpty())
				conditionsPOJO.setEngineFuelLevel(Float.parseFloat(cells[4].getContents().toString()));
			if (!cells[5].getContents().isEmpty())
				conditionsPOJO.setEngineSpeed(Integer.parseInt(cells[5].getContents().toString()));
			if (!cells[6].getContents().isEmpty())
				conditionsPOJO.setHydraulicOilTemperature(Integer.parseInt(cells[6].getContents().toString()));
			if (!cells[7].getContents().isEmpty())
				conditionsPOJO.setSystemVoltage(Float.parseFloat(cells[7].getContents().toString()));
			if (!cells[8].getContents().isEmpty())
				conditionsPOJO.setBeforePumpMainPressure(Float.parseFloat(cells[8].getContents().toString()));
			if (!cells[9].getContents().isEmpty())
				conditionsPOJO.setAfterPumpMainPressure(Float.parseFloat(cells[9].getContents().toString()));
			if (!cells[10].getContents().isEmpty())
				conditionsPOJO.setPilotPressure1(Float.parseFloat(cells[10].getContents().toString()));
			if (!cells[11].getContents().isEmpty())
				conditionsPOJO.setPilotPressure2(Float.parseFloat(cells[11].getContents().toString()));
			if (!cells[12].getContents().isEmpty())
				conditionsPOJO.setBeforePumpPressure(Float.parseFloat(cells[12].getContents().toString()));
			if (!cells[13].getContents().isEmpty())
				conditionsPOJO.setAfterPumpPressure(Float.parseFloat(cells[13].getContents().toString()));
			if (!cells[14].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent1(Integer.parseInt(cells[14].getContents().toString()));
			if (!cells[15].getContents().isEmpty())
				conditionsPOJO.setProportionalCurrent2(Integer.parseInt(cells[15].getContents().toString()));
			if (!cells[16].getContents().isEmpty())
				conditionsPOJO.setTotalWorkingHours(Float.parseFloat(cells[16].getContents().toString()));
			if (!cells[17].getContents().isEmpty())
				conditionsPOJO.setGear(Integer.parseInt(cells[17].getContents().toString()));
			if (!cells[18].getContents().isEmpty())
				conditionsPOJO.setHighEngineCoolantTemperVal(Integer.parseInt(cells[18].getContents().toString()));
			if (!cells[19].getContents().isEmpty())
				conditionsPOJO.setLowEngineOilPressureAlarmValue(Float.parseFloat(cells[19].getContents().toString()));
			if (!cells[20].getContents().isEmpty())
				conditionsPOJO.setHighVoltageAlarmValue(Float.parseFloat(cells[20].getContents().toString()));
			if (!cells[21].getContents().isEmpty())
				conditionsPOJO.setLowVoltageAlarmValue(Float.parseFloat(cells[21].getContents().toString()));
			if (!cells[22].getContents().isEmpty())
				conditionsPOJO.setHighHydraulicOilTemperAlarmVal(Integer.parseInt(cells[22].getContents().toString()));
			if (!cells[23].getContents().isEmpty())
				conditionsPOJO.setToothNumberValue(Integer.parseInt(cells[23].getContents().toString()));
			if (!cells[24].getContents().isEmpty())
				conditionsPOJO.setMonitorSoftwareCode(cells[24].getContents().toString());
			if (!cells[25].getContents().isEmpty())
				conditionsPOJO.setMonitorYcSoftwareCode(cells[25].getContents().toString());
			if (!cells[26].getContents().isEmpty())
				conditionsPOJO.setControllerSoftwareCode(cells[26].getContents().toString());
			if (!cells[27].getContents().isEmpty())
				conditionsPOJO.setControllerYcSoftwareCode(cells[27].getContents().toString());
			if (!cells[28].getContents().isEmpty())
				conditionsPOJO.setRawData(cells[28].getContents().toString());
			if (!cells[29].getContents().isEmpty())
				conditionsPOJO.setIsWork(Integer.parseInt(cells[29].getContents().toString()));					
			if (!cells[30].getContents().isEmpty())
				conditionsPOJO.setFaultCode(Integer.parseInt(cells[30].getContents().toString()));
			if (!cells[31].getContents().isEmpty())
				conditionsPOJO.setEngineOilTemperature(Integer.parseInt(cells[31].getContents().toString()));
			if (!cells[32].getContents().isEmpty())
				conditionsPOJO.setAlock(Integer.parseInt(cells[32].getContents().toString()));
			if (!cells[33].getContents().isEmpty())
				conditionsPOJO.setBlock(Integer.parseInt(cells[33].getContents().toString()));
			if (!cells[34].getContents().isEmpty())
				conditionsPOJO.setClock(Integer.parseInt(cells[34].getContents().toString()));
			break;
		default:
			break;
		}
		return conditionsPOJO;
		}else
		return null;
	}
	
}
