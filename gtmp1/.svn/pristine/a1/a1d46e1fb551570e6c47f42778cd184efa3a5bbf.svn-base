package com.chinaGPS.gtmp.action.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.chinaGPS.gtmp.entity.CustomerPayPOJO;
import com.chinaGPS.gtmp.entity.CustomerSimPOJO;
import com.chinaGPS.gtmp.entity.SimPayPOJO;
import com.chinaGPS.gtmp.entity.SimServerPOJO;
import com.chinaGPS.gtmp.entity.VehiclePOJO;

public class ExcelUtils {

	/**
	 * 读取Excel文件的内容
	 * 
	 * @param file
	 *            待读取的文件
	 * @return
	 */
	public Map<String, Object> readExcel(File file) throws Exception {
		Map<String, Object> eMap = new HashMap<String, Object>();
		List<VehiclePOJO> mlist = new ArrayList<VehiclePOJO>();
		StringBuffer sb = new StringBuffer();

		Workbook wb = null;
		// 构造Workbook（工作薄）对象
		wb = Workbook.getWorkbook(file);

		if (wb == null)
			return null;

		// 获得了Workbook对象之后，就可以通过它得到Sheet（工作表）对象了
		Sheet sheet = wb.getSheet(0);

		if (sheet != null) {
			// 得到当前工作表的行数
			int rowNum = sheet.getRows();

			Map<String, Object> itemMap = new HashMap<String, Object>();

			for (int j = 0; j < rowNum; j++) {
				// 得到当前行的所有单元格
				Cell[] cells = sheet.getRow(j);
				if (cells != null && cells.length > 0) {
					if (j == 1) {
						// 对每个单元格进行循环
						for (int k = 0; k < cells.length; k++) {
							// 读取当前单元格的值
							String cellValue = cells[k].getContents().trim();
							cellValue = cellValue.replaceAll(" ", "");
							cellValue = cellValue.replaceAll("　", "");

							if (k < cells.length - 1) {
								sb.append(cellValue + ",");
							} else {
								sb.append(cellValue);
							}
							itemMap.put(cellValue, k);
						}
						// 要保存的数据项
						eMap.put("items", sb.toString());
					} else {
						VehiclePOJO vehiclePOJO = new VehiclePOJO();
						// 整机编号
						if (itemMap.get("整机编号") != null) {
							int k = (Integer) itemMap.get("整机编号");
							String vehicleDef = cells[k].getContents().trim();
							vehicleDef = vehicleDef.replaceAll(" ", "");
							vehicleDef = vehicleDef.replaceAll("　", "");
							if (!("").equals(vehicleDef)) {
								vehiclePOJO.setVehicleDef(vehicleDef);
							}

						}

						// 终端序列号
						if (itemMap.get("终端序列号") != null) {
							int k = (Integer) itemMap.get("终端序列号");
							String unitSn = cells[k].getContents().trim();
							unitSn = unitSn.replaceAll(" ", "");
							unitSn = unitSn.replaceAll("　", "");
							if (!("").equals(unitSn)) {
								vehiclePOJO.setUnitSn(unitSn);
							}

						}

						// 机械类型
						if (itemMap.get("机械类型") != null) {
							int k = (Integer) itemMap.get("机械类型");
							String typeId = cells[k].getContents().trim();
							typeId = typeId.replaceAll(" ", "");
							typeId = typeId.replaceAll("　", "");
							if (!("").equals(typeId)) {
								vehiclePOJO.setTypeId(Long.valueOf(typeId));
							}

						}

						// 机械型号
						if (itemMap.get("机械型号") != null) {
							int k = (Integer) itemMap.get("机械型号");
							String modelId = cells[k].getContents().trim();
							modelId = modelId.replaceAll(" ", "");
							modelId = modelId.replaceAll("　", "");
							if (!("").equals(modelId)) {
								vehiclePOJO.setModelId(Long.valueOf(modelId));
							}

						}

						// sim卡号
						if (itemMap.get("sim卡号") != null) {
							int k = (Integer) itemMap.get("sim卡号");
							String simNo = cells[k].getContents().trim();
							simNo = simNo.replaceAll(" ", "");
							simNo = simNo.replaceAll("　", "");
							if (!("").equals(simNo)) {
								vehiclePOJO.setSimNo(simNo);
							}

						}

						// 物料条码
						if (itemMap.get("物料条码") != null) {
							int k = (Integer) itemMap.get("物料条码");
							String materialNo = cells[k].getContents().trim();
							materialNo = materialNo.replaceAll(" ", "");
							materialNo = materialNo.replaceAll("　", "");
							if (!("").equals(materialNo)) {
								vehiclePOJO.setMaterialNo(materialNo);
							}

						}

						// 钢号
						if (itemMap.get("钢号") != null) {
							int k = (Integer) itemMap.get("钢号");
							String steelNo = cells[k].getContents().trim();
							steelNo = steelNo.replaceAll(" ", "");
							steelNo = steelNo.replaceAll("　", "");
							if (!("").equals(steelNo)) {
								vehiclePOJO.setSteelNo(steelNo);
							}

						}

						// 安装时间
						if (itemMap.get("安装时间") != null) {
							String cellcon = null;
							int k = (Integer) itemMap.get("安装时间");
							Date date = new Date();
							if (cells[k].getType() == CellType.DATE) {
								DateCell dc = (DateCell) cells[k];
								date = dc.getDate();
								SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd");
								cellcon = ds.format(date);

							}
							if (!("").equals(cellcon)) {
								java.sql.Date dates = java.sql.Date
										.valueOf(cellcon);
								vehiclePOJO.setFixDate(dates);
							}
						}

						// 安装人
						if (itemMap.get("安装人") != null) {
							int k = (Integer) itemMap.get("安装人");
							String fixMan = cells[k].getContents().trim();
							fixMan = fixMan.replaceAll(" ", "");
							fixMan = fixMan.replaceAll("　", "");
							if (!("").equals(fixMan)) {
								vehiclePOJO.setFixMan(fixMan);
							}
						}
						// 机械代号
						if (itemMap.get("机械代号") != null) {
							int k = (Integer) itemMap.get("机械代号");
							String vehicleCode = cells[k].getContents().trim();
							vehicleCode = vehicleCode.replaceAll(" ", "");
							vehicleCode = vehicleCode.replaceAll("　", "");
							if (!("").equals(vehicleCode)) {
								vehiclePOJO.setVehicleCode(vehicleCode);
							}
						}// 机械配置
						if (itemMap.get("机械配置") != null) {
							int k = (Integer) itemMap.get("机械配置");
							String vehicleArg = cells[k].getContents().trim();
							vehicleArg = vehicleArg.replaceAll(" ", "");
							vehicleArg = vehicleArg.replaceAll("　", "");
							if (!("").equals(vehicleArg)) {
								vehiclePOJO.setVehicleArg(vehicleArg);
							}
						}
						mlist.add(vehiclePOJO);
					}
				}
			}
			// 第一条是空的
			mlist.remove(0);
			// 要保存的数据值
			eMap.put("values", mlist);
		}
		// 最后关闭资源，释放内存
		wb.close();
		return eMap;
	}

	// 注册用户获得Uid
	public static String getUid(String memberid, String oldPass) throws Exception {
		// 注册URL
		String registerUrl = "http://portal.cdeledu.com/api/index.php";
		// 需要参数
		String cmd = "ucRegister";
		String pkey = "fJ3UjIFyTu";
		String time = getToday();// 格式yyyy-MM-dd HH:mm:ss
		String md5 = MD5Encode("32", pkey + time + cmd);

		String param = "cmd=" + cmd + "&pkey=" + md5 + "&time=" + time
				+ "&username=" + memberid + "&passwd=" + oldPass
				+ "&domain=@chinaacc.com";

		// 取得的uid
		String ssouid = null;

		URL httpurl = new URL(registerUrl);
		HttpURLConnection httpConn = (HttpURLConnection) httpurl
				.openConnection();
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);
		PrintWriter out = new PrintWriter(httpConn.getOutputStream());
		out.print(param);
		out.flush();
		out.close();
		BufferedReader in = new BufferedReader(new InputStreamReader(httpConn
				.getInputStream()));
		String result = "";
		String line;
		while ((line = in.readLine()) != null) {
			result += line;
		}

		in.close();

		Document document = DocumentHelper.parseText(result);
		Element root = document.getRootElement();

		String code = root.elementTextTrim("code");

		if ("0".equals(code)) {
			ssouid = root.elementTextTrim("ssouid");
		}

		return ssouid;
	}

	public static String MD5Encode(String digit, String charset)
			throws Exception {
		String resultString = null;
		StringBuffer buf = new StringBuffer();

		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(charset.getBytes());
		byte b[] = md.digest();
		int i;
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}

		if (digit != null && "16".equals(digit)) {
			resultString = buf.toString().substring(8, 24);
		} else {
			resultString = buf.toString();
		}

		md = null;
		buf = null;

		return resultString;
	}

	public static String getToday() {
		// SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd
		// HH:mm:ss"); // 规定日期格式
		// Date date = new Date(); // 将符合格式的String转换为Date
		// String today = formatter.format(date); // 将Date转换为符合格式的String
		// System.out.print(today);
		return "";
	}

	public Map < String,Object > readExcelBatchCancelSimServer(File file,String tempTitle) throws Exception {
	    List < SimServerPOJO > mlist = new ArrayList < SimServerPOJO > ();
	    Map < String,
	    Object > map = new HashMap < String,
	    Object > ();
	    Map <String,Integer> eMetaData = new HashMap<String,Integer>();
	    Workbook wb = null;
	    // 构造Workbook（工作薄）对象
	    wb = Workbook.getWorkbook(file);

	    if (wb == null) return null;
	    // 获得了Workbook对象之后，就可以通过它得到Sheet（工作表）对象了
	    Sheet sheet = wb.getSheet(0);
	    if (sheet != null) {
	        // 得到当前工作表的行数
	        int rowNum = sheet.getRows();
            Cell[] titleCells = sheet.getRow(1); // 获取表头
            //根据表头判断输入的文件是否符合标准要求
            StringBuilder titleStr = new StringBuilder("");
            for( int i = 0; i < titleCells.length ;i++ ){
            	titleStr.append(titleCells[i].getContents()+"!");
            	eMetaData.put(titleCells[i].getContents().trim(), i);
            }
            if("".equalsIgnoreCase(titleStr.toString())){
            	map.put("flag",false);
            	map.put("message","模板不正确");
        	    return map;
            }else{
            	if(!tempTitle.trim().equalsIgnoreCase(titleStr.toString().trim())){
            		map.put("flag",false);
                	map.put("message","模板不正确");
            	    return map;
            	}
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        //SIM卡号!缴费金额!客户姓名!备注!
	        for (int j = 2; j < rowNum; j++) {
	            // 得到当前行的所有单元格
	            Cell[] cells = sheet.getRow(j); // 一行记录
	            if (cells != null && cells.length > 0) {
                	SimServerPOJO customerPayPOJO = new SimServerPOJO();
	            	// sim卡号
					if (eMetaData.get("SIM卡号") != null) {
						int k = (Integer) eMetaData.get("SIM卡号");
						String simNo = cells[k].getContents().trim();
						simNo = simNo.replaceAll(" ", "");
						simNo = simNo.replaceAll("　", "");
						if (!("").equals(simNo)) {
							customerPayPOJO.setSimNo(simNo);
						}
					}
					
					//注销日期
					String startTime = cells[eMetaData.get("注销日期")].getContents();
                    if( startTime != null  && !"".equalsIgnoreCase(startTime)){
                        customerPayPOJO.setStopTime(sdf.parse(startTime));
                    }
                    //备注
                    customerPayPOJO.setStopReason(cells[eMetaData.get("注销原因")].getContents());
                    
                    if("".equalsIgnoreCase(customerPayPOJO.getSimNo()) 
                    		&&  customerPayPOJO.getStopTime() == null 
                    		&& "".equalsIgnoreCase(customerPayPOJO.getStopReason())){
                    	continue;
                    }else{
                    	mlist.add(customerPayPOJO);
                    }
	            }
	        }
	    }
	    wb.close();
	    map.put("values", mlist);
	    map.put("flag",true);
    	map.put("message","模板正确");
	    return map;
	}
	
	public Map < String,Object > readExcelSimServer(File file,String tempTitle) throws Exception {
	    List < SimServerPOJO > mlist = new ArrayList < SimServerPOJO > ();
	    Map < String,
	    Object > map = new HashMap < String,
	    Object > ();
	    Map <String,Integer> eMetaData = new HashMap<String,Integer>();
	    Workbook wb = null;
	    // 构造Workbook（工作薄）对象
	    wb = Workbook.getWorkbook(file);

	    if (wb == null) return null;
	    // 获得了Workbook对象之后，就可以通过它得到Sheet（工作表）对象了
	    Sheet sheet = wb.getSheet(0);
	    if (sheet != null) {
	        // 得到当前工作表的行数
	        int rowNum = sheet.getRows();
	     
            Cell[] titleCells = sheet.getRow(1); // 获取表头
            //根据表头判断输入的文件是否符合标准要求
            StringBuilder titleStr = new StringBuilder("");
            for( int i = 0; i < titleCells.length ;i++ ){
            	titleStr.append(titleCells[i].getContents()+"!");
            	eMetaData.put(titleCells[i].getContents().trim(), i);
            }
            System.err.println(" titleString "+titleStr +" tempTitle "+tempTitle+"   res "+tempTitle.trim().equalsIgnoreCase(titleStr.toString().trim()));
            if("".equalsIgnoreCase(titleStr.toString())){
            	map.put("flag",false);
            	map.put("message","模板不正确");
        	    return map;
            }else{
            	if(!tempTitle.trim().equalsIgnoreCase(titleStr.toString().trim())){
            		map.put("flag",false);
                	map.put("message","模板不正确");
            	    return map;
            	}
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	        //SIM卡号!缴费金额!客户姓名!备注!
	        for (int j = 2; j < rowNum; j++) {
	            // 得到当前行的所有单元格
	            Cell[] cells = sheet.getRow(j); // 一行记录
	            if (cells != null && cells.length > 0) {
                	SimServerPOJO customerPayPOJO = new SimServerPOJO();
	            	// sim卡号
					if (eMetaData.get("SIM卡号") != null) {
						int k = (Integer) eMetaData.get("SIM卡号");
						String simNo = cells[k].getContents().trim();
						simNo = simNo.replaceAll(" ", "");
						simNo = simNo.replaceAll("　", "");
						if (!("").equals(simNo)) {
							customerPayPOJO.setSimNo(simNo);
						}
					}
					
					//payAmount
					if (eMetaData.get("SIM开卡费") != null) {
						int k = (Integer) eMetaData.get("SIM开卡费");
						String unid = cells[k].getContents().trim();
						unid = unid.replaceAll(" ", "");
						unid = unid.replaceAll("　", "");
						if (!("").equals(unid)) {
							customerPayPOJO.setPayAmount(new BigDecimal(Integer.parseInt(unid)));
						}else{
							customerPayPOJO.setPayAmount(new BigDecimal(0));
						}
					}
					
					//服务开始日期
					String startTime = cells[eMetaData.get("服务开始日期")].getContents();
                    if( startTime != null  && !"".equalsIgnoreCase(startTime)){
                        customerPayPOJO.setOpenTime(sdf.parse(startTime));
                    }
					
					//服务结束日期
					String endTime = cells[eMetaData.get("服务结束日期")].getContents();
                    if( endTime != null  && !"".equalsIgnoreCase(endTime)){
                        customerPayPOJO.setEndTime(sdf.parse(endTime));
                    }
                    
                    //备注
                    customerPayPOJO.setRemark(cells[eMetaData.get("备注")].getContents());
                    
                    if("".equalsIgnoreCase(customerPayPOJO.getSimNo()) 
                    		&& customerPayPOJO.getPayAmount() == null
                    		&& customerPayPOJO.getOpenTime() == null 
                    		&& customerPayPOJO.getEndTime() == null 
                    		&& "".equalsIgnoreCase(customerPayPOJO.getRemark())
                    	){
                    	continue;
                    }else{
                    	mlist.add(customerPayPOJO);
                    }
	            }
	        }
	    }
	    wb.close();
	    map.put("values", mlist);
	    map.put("flag",true);
    	map.put("message","模板正确");
	    return map;
	}
	
	public Map < String,Object > readExcelCustomerSimServer(File file,String tempTitle) throws Exception {
	    List < CustomerSimPOJO > mlist = new ArrayList < CustomerSimPOJO > ();
	    Map < String,
	    Object > map = new HashMap < String,
	    Object > ();
	    Map <String,Integer> eMetaData = new HashMap<String,Integer>();
	    Workbook wb = null;
	    // 构造Workbook（工作薄）对象
	    wb = Workbook.getWorkbook(file);

	    if (wb == null) return null;
	    // 获得了Workbook对象之后，就可以通过它得到Sheet（工作表）对象了
	    Sheet sheet = wb.getSheet(0);
	    if (sheet != null) {
	        // 得到当前工作表的行数
	        int rowNum = sheet.getRows();
	     
            Cell[] titleCells = sheet.getRow(1); // 获取表头
            //根据表头判断输入的文件是否符合标准要求
            StringBuilder titleStr = new StringBuilder("");
            for( int i = 0; i < titleCells.length ;i++ ){
            	titleStr.append(titleCells[i].getContents()+"!");
            	eMetaData.put(titleCells[i].getContents().trim(), i);
            }
            if("".equalsIgnoreCase(titleStr.toString())){
            	map.put("flag",false);
            	map.put("message","模板不正确");
        	    return map;
            }else{
            	if(!tempTitle.trim().equalsIgnoreCase(titleStr.toString().trim())){
            		map.put("flag",false);
                	map.put("message","模板不正确");
            	    return map;
            	}
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	        //SIM卡号!缴费金额!客户姓名!备注!
	        for (int j = 2; j < rowNum; j++) {
	            // 得到当前行的所有单元格
	            Cell[] cells = sheet.getRow(j); // 一行记录
	            if (cells != null && cells.length > 0) {
                	CustomerSimPOJO customerPayPOJO = new CustomerSimPOJO();
	            	// sim卡号
					if (eMetaData.get("SIM卡号") != null) {
						int k = (Integer) eMetaData.get("SIM卡号");
						String simNo = cells[k].getContents().trim();
						simNo = simNo.replaceAll(" ", "");
						simNo = simNo.replaceAll("　", "");
						if (!("").equals(simNo)) {
							customerPayPOJO.setSimNo(simNo);
						}
					}
					//服务开始日期
					String startTime = cells[eMetaData.get("服务开始日期")].getContents();
                    if( startTime != null  && !"".equalsIgnoreCase(startTime)){
                        customerPayPOJO.setStartTime(sdf.parse(startTime));
                    }
					
					//服务结束日期
					String endTime = cells[eMetaData.get("服务结束日期")].getContents();
                    if( endTime != null  && !"".equalsIgnoreCase(endTime)){
                        customerPayPOJO.setEndTime(sdf.parse(endTime));
                    }
                    
                    //备注
                    customerPayPOJO.setRemark(cells[eMetaData.get("备注")].getContents());
                   
                    if("".equalsIgnoreCase(customerPayPOJO.getSimNo()) 
                    		&& customerPayPOJO.getStartTime() == null
                    		&& customerPayPOJO.getEndTime() == null 
                    		&& "".equalsIgnoreCase(customerPayPOJO.getRemark())
                    	){
                    	continue;
                    }else{
                    	mlist.add(customerPayPOJO);
                    }
	            }
	        }
	    }
	    wb.close();
	    map.put("values", mlist);
	    map.put("flag",true);
    	map.put("message","模板正确");
	    return map;
	}
	
	public Map < String,Object > readExcelCustomerStopSimServer(File file,String tempTitle) throws Exception {
	    List < CustomerSimPOJO > mlist = new ArrayList < CustomerSimPOJO > ();
	    Map < String,
	    Object > map = new HashMap < String,
	    Object > ();
	    Map <String,Integer> eMetaData = new HashMap<String,Integer>();
	    Workbook wb = null;
	    // 构造Workbook（工作薄）对象
	    wb = Workbook.getWorkbook(file);

	    if (wb == null) return null;
	    // 获得了Workbook对象之后，就可以通过它得到Sheet（工作表）对象了
	    Sheet sheet = wb.getSheet(0);
	    if (sheet != null) {
	        // 得到当前工作表的行数
	        int rowNum = sheet.getRows();
	     
            Cell[] titleCells = sheet.getRow(1); // 获取表头
            //根据表头判断输入的文件是否符合标准要求
            StringBuilder titleStr = new StringBuilder("");
            for( int i = 0; i < titleCells.length ;i++ ){
            	titleStr.append(titleCells[i].getContents()+"!");
            	eMetaData.put(titleCells[i].getContents().trim(), i);
            }
            if("".equalsIgnoreCase(titleStr.toString())){
            	map.put("flag",false);
            	map.put("message","模板不正确");
        	    return map;
            }else{
            	if(!tempTitle.trim().equalsIgnoreCase(titleStr.toString().trim())){
            		map.put("flag",false);
                	map.put("message","模板不正确");
            	    return map;
            	}
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	        //SIM卡号!缴费金额!客户姓名!备注!
	        for (int j = 2; j < rowNum; j++) {
	            // 得到当前行的所有单元格
	            Cell[] cells = sheet.getRow(j); // 一行记录
	            if (cells != null && cells.length > 0) {
                	CustomerSimPOJO customerPayPOJO = new CustomerSimPOJO();
	            	// sim卡号
					if (eMetaData.get("SIM卡号") != null) {
						int k = (Integer) eMetaData.get("SIM卡号");
						String simNo = cells[k].getContents().trim();
						simNo = simNo.replaceAll(" ", "");
						simNo = simNo.replaceAll("　", "");
						if (!("").equals(simNo)) {
							customerPayPOJO.setSimNo(simNo);
						}
					}
					
					//服务开始日期
					String startTime = cells[eMetaData.get("停机保号开始日期")].getContents();
                    if( startTime != null  && !"".equalsIgnoreCase(startTime)){
                        customerPayPOJO.setStopStartTime(sdf.parse(startTime));
                    }
					
					//服务结束日期
					String endTime = cells[eMetaData.get("停机保号结束日期")].getContents();
                    if( endTime != null  && !"".equalsIgnoreCase(endTime)){
                        customerPayPOJO.setStopEndTime(sdf.parse(endTime));
                    }
                    
                    //备注
                    customerPayPOJO.setStopReason(cells[eMetaData.get("停机保号原因")].getContents());
                    
                    //停机保号月费用
                    int  stopFeeMonthStr =  Integer.parseInt(cells[eMetaData.get("停机保号月费用")].getContents());
                    customerPayPOJO.setStopFeeMonth( new BigDecimal(stopFeeMonthStr) );

                    if("".equalsIgnoreCase(customerPayPOJO.getSimNo()) 
                    		&& customerPayPOJO.getStopStartTime() == null
                    		&& customerPayPOJO.getStopEndTime() == null 
                    		&& customerPayPOJO.getStopFeeMonth() == null 
                    		&& "".equalsIgnoreCase(customerPayPOJO.getStopReason())
                    	){
                    	continue;
                    }else{
                    	mlist.add(customerPayPOJO);
                    }
	            }
	        }
	    }
	    wb.close();
	    map.put("values", mlist);
	    map.put("flag",true);
    	map.put("message","模板正确");
	    return map;
	}
	
	public Map < String,Object > readExcelCompanyPay(File file,String tempTitle) throws Exception {
	    List < SimPayPOJO > mlist = new ArrayList < SimPayPOJO > ();
	    Map < String,
	    Object > map = new HashMap < String,
	    Object > ();
	    Map <String,Integer> eMetaData = new HashMap<String,Integer>();

	    Workbook wb = null;
	    // 构造Workbook（工作薄）对象
	    wb = Workbook.getWorkbook(file);

	    if (wb == null) return null;
	    // 获得了Workbook对象之后，就可以通过它得到Sheet（工作表）对象了
	    Sheet sheet = wb.getSheet(0);
	    if (sheet != null) {
	        // 得到当前工作表的行数
	        int rowNum = sheet.getRows();
	     
            Cell[] titleCells = sheet.getRow(1); // 获取表头
            //根据表头判断输入的文件是否符合标准要求
            StringBuilder titleStr = new StringBuilder("");
            for( int i = 0; i < titleCells.length ;i++ ){
            	titleStr.append(titleCells[i].getContents()+"!");
            	eMetaData.put(titleCells[i].getContents().trim(), i);
            }
            System.out.println(" titleString "+titleStr);
            if("".equalsIgnoreCase(titleStr.toString())){
            	map.put("flag",false);
            	map.put("message","模板不正确");
        	    return map;
            }else{
            	if(!tempTitle.trim().equalsIgnoreCase(titleStr.toString().trim())){
            		map.put("flag",false);
                	map.put("message","模板不正确");
            	    return map;
            	}
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        //SIM卡号!缴费金额!客户姓名!备注!
	        for (int j = 2; j < rowNum; j++) {
	            // 得到当前行的所有单元格
	            Cell[] cells = sheet.getRow(j); // 一行记录
	            if (cells != null && cells.length > 0) {
                	SimPayPOJO customerPayPOJO = new SimPayPOJO();
	            	// sim卡号
					if (eMetaData.get("SIM卡号") != null) {
						int k = (Integer) eMetaData.get("SIM卡号");
						String simNo = cells[k].getContents().trim();
						simNo = simNo.replaceAll(" ", "");
						simNo = simNo.replaceAll("　", "");
						if (!("").equals(simNo)) {
							customerPayPOJO.setSimNo(simNo);
						}
					}
					
					//缴费金额
					if (eMetaData.get("缴费金额") != null) {
						int k = (Integer) eMetaData.get("缴费金额");
						String payAmount = cells[k].getContents().trim();
						payAmount = payAmount.replaceAll(" ", "");
						payAmount = payAmount.replaceAll("　", "");
						if (!("").equals(payAmount)) {
							customerPayPOJO.setPayAmount(new BigDecimal(payAmount));
						}
					}
					
					//服务结束日期
					String endTime = cells[eMetaData.get("服务结束日期")].getContents();
                    if( endTime != null  && !"".equalsIgnoreCase(endTime)){
                        customerPayPOJO.setEndTime(sdf.parse(endTime));
                    }
                    
                    //备注
                    customerPayPOJO.setRemark(cells[eMetaData.get("备注")].getContents());
                    
                    if("".equalsIgnoreCase(customerPayPOJO.getSimNo()) 
                    		&& customerPayPOJO.getPayAmount() == null
                    		&& customerPayPOJO.getEndTime() == null 
                    		&& "".equalsIgnoreCase(customerPayPOJO.getRemark())
                    	){
                    	continue;
                    }else{
                    	mlist.add(customerPayPOJO);
                    }
	            }
	        }
	    }
	    wb.close();
	    map.put("values", mlist);
	    map.put("flag",true);
    	map.put("message","模板正确");
	    return map;
	}
	
	public Map < String,Object > readExcelCustomerPay(File file,String tempTitle) throws Exception {
	    List < CustomerPayPOJO > mlist = new ArrayList < CustomerPayPOJO > ();
	    Map < String, Object > map = new HashMap < String, Object > ();
	    Map <String,Integer> eMetaData = new HashMap<String,Integer>();
	    Workbook wb = null;
	    // 构造Workbook（工作薄）对象
	    wb = Workbook.getWorkbook(file);

	    if (wb == null) return null;
	    // 获得了Workbook对象之后，就可以通过它得到Sheet（工作表）对象了
	    Sheet sheet = wb.getSheet(0);
	    if (sheet != null) {
	        // 得到当前工作表的行数
	        int rowNum = sheet.getRows();
	     
            Cell[] titleCells = sheet.getRow(1); // 获取表头
            //根据表头判断输入的文件是否符合标准要求
            StringBuilder titleStr = new StringBuilder("");
            for( int i = 0; i < titleCells.length ;i++ ){
            	titleStr.append(titleCells[i].getContents()+"!");
            	eMetaData.put(titleCells[i].getContents().trim(), i);
            }
            if("".equalsIgnoreCase(titleStr.toString())){
            	map.put("flag",false);
            	map.put("message","模板不正确");
        	    return map;
            }else{
            	if(!tempTitle.trim().equalsIgnoreCase(titleStr.toString().trim())){
            		map.put("flag",false);
                	map.put("message","模板不正确");
            	    return map;
            	}
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        //SIM卡号!缴费金额!客户姓名!备注!
	        for (int j = 2; j < rowNum; j++) {
	            // 得到当前行的所有单元格
	            Cell[] cells = sheet.getRow(j); // 一行记录
	            if (cells != null && cells.length > 0) {
	            	 CustomerPayPOJO customerPayPOJO = new CustomerPayPOJO();
	            	 
	            	 String simNo = cells[eMetaData.get("SIM卡号")].getContents();
	            	 if(simNo != null && !"".equalsIgnoreCase(simNo) ){
		            	 customerPayPOJO.setSimNo(simNo);
	            	 }
	            	 
	            	 String payAmount = cells[eMetaData.get("缴费金额")].getContents();
	            	 if(payAmount != null && !"".equalsIgnoreCase(payAmount) ){
	                     customerPayPOJO.setPayAmount(new BigDecimal( payAmount ));
	            	 }
	            	 
                     String endTime = cells[eMetaData.get("服务结束日期")].getContents();
                     if( endTime != null  && !"".equalsIgnoreCase(endTime)){
                         customerPayPOJO.setEndTime(sdf.parse(endTime));
                     }
                     
                     customerPayPOJO.setRemark(cells[eMetaData.get("备注")].getContents());
                     
                     if( "".equalsIgnoreCase(customerPayPOJO.getSimNo()) 
                     		&& customerPayPOJO.getPayAmount() == null
                     		&& customerPayPOJO.getEndTime() == null 
                     		&& "".equalsIgnoreCase(customerPayPOJO.getRemark())
                     	){
                     	continue;
                     }else{
                     	mlist.add(customerPayPOJO);
                     }
	            }
	        }
	    }
	    wb.close();
	    map.put("values", mlist);
	    map.put("flag",true);
    	map.put("message","模板正确");
	    return map;
	}
	
	public static void main (String args [] ) throws Exception{
		//File file = new File("E:/玉柴重工/customer_pay_templates.xls");
		ExcelUtils excel  = new ExcelUtils();
		//excel.readExcelCustomerPay(file,"SIM卡号!缴费金额!服务结束日期!备注!");
		//excel.readExcelCompanyPay(file,"SIM卡号!缴费金额!服务结束日期!备注!");
		File file = new File("E:/玉柴重工/sim_templates.xls");
		excel.readExcelSimServer(file,"SIM卡号!终端ID!服务开始日期!服务结束日期!备注!");
		
		/*try {
			Map<String,Object> map  = excel.readExcelCustomerPay(file,"SIM卡号!缴费金额!服务开始日期!服务结束日期!备注!");
			System.err.println(" map "+map );
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
	}
	
}
