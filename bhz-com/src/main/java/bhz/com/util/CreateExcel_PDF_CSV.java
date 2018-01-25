/**
 * 生成EXCEL,PDF,CSV文件。
 */
package bhz.com.util;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

public class CreateExcel_PDF_CSV {
	
	public static void createExcel(List list, HttpServletResponse response, String filename, String title[]) {
		int row = 1;
		int page = 1;
		OutputStream os = null;
		WritableWorkbook workbook = null;
		try {
			os = response.getOutputStream();
			response.setHeader("Content-disposition", "attachment; filename="+ java.net.URLEncoder.encode(filename,"UTF-8")+ ".xls"); //设定输出文件头
			response.setContentType("application/excel"); //定义输出类型
			workbook = Workbook.createWorkbook(os);
			WritableSheet sheet = null;
			WritableFont font = new WritableFont(WritableFont.createFont("宋体"),10, WritableFont.BOLD, false,jxl.format.UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat format = new WritableCellFormat(font);
			format.setBackground(Colour.GRAY_25);
			format.setAlignment(jxl.format.Alignment.CENTRE);
			DecimalFormat df  = new DecimalFormat("###.00"); 
	        DecimalFormat df2  = new DecimalFormat("###");
	            Double dVal=null;
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					if (i % 60000 == 0 || i == 0) {
						sheet = workbook.createSheet("页面" + page, page++);
						for (int k = 0; k < title.length; k++) {
							sheet.addCell(new Label(k, 0, title[k], format));
						}
						row = 1;
					}
					String str[] = (String[]) list.get(i);
					for (int j = 0; j < str.length; j++) {
						if(StringUtils.isNumeric(str[j])){//数字
							dVal=Double.valueOf(str[j]);
							if(dVal==dVal.intValue()){//没小数
								str[j]=df2.format(dVal);
							}else{
								str[j]=df.format(dVal);
							}
						}
						sheet.addCell(new Label(j, row, str[j]));
					}
					++row;
				}
			} else {
				sheet = workbook.createSheet("页面" + page, page++);
				for (int i = 0; i < title.length; i++) {
					sheet.addCell(new Label(i, 0, title[i], format));
				}
			}
			workbook.write();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				workbook.close();
				os.close();
			} catch (Exception ex) {
				System.out.println("Close os or workbook to error"+ex);
			}
		}
	}
	/**
	 * @Title:createExcel
	 * @Description:统一页眉
	 * @param list
	 * @param response
	 * @param filename
	 * @param title
	 * @param companyCHName
	 * @param companyEnName
	 * @throws
	 */
	public static void createExcel(List list, HttpServletResponse response, String filename, String title[][],String companyCHName,String companyEnName,boolean isCount) {
		int row = 1;
		int page = 1;
		OutputStream os = null;
		WritableWorkbook workbook = null;
		try {
			os = response.getOutputStream();
			response.setHeader("Content-disposition", "attachment; filename="+ java.net.URLEncoder.encode(filename,"UTF-8")+ ".xls"); //设定输出文件头
			response.setContentType("application/excel"); //定义输出类型
			workbook = Workbook.createWorkbook(os);
			WritableSheet sheet = null;
		/*	WritableFont font = new WritableFont(WritableFont.createFont("宋体"),10, WritableFont.BOLD, false,jxl.format.UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat format = new WritableCellFormat(font);
			//format.setBackground(Colour.GRAY_25);
			format.setAlignment(jxl.format.Alignment.CENTRE);
			format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN); 
	           */
			
			WritableFont wf_title = new jxl.write.WritableFont(WritableFont.createFont("宋体"), 14,WritableFont.BOLD); 
            WritableCellFormat  wcf_title = new WritableCellFormat(wf_title); 
            wcf_title.setAlignment(Alignment.CENTRE); 
            wcf_title.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
            wcf_title.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.THIN); 
            wcf_title.setBorder(jxl.format.Border.RIGHT, jxl.format.BorderLineStyle.THIN);
            
            WritableFont wf_value = new jxl.write.WritableFont(WritableFont.createFont("宋体"), 10,WritableFont.NO_BOLD); 
            WritableCellFormat wcf_value = new WritableCellFormat(wf_value); 
            wcf_value.setAlignment(jxl.format.Alignment.CENTRE); 
            wcf_value.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
            wcf_value.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN); 
            wcf_value.setWrap(true);//设置文字自适应列宽（注：是文字适应列宽，不是列宽适应文字）
            
            WritableFont wf_value_total = new jxl.write.WritableFont(WritableFont.createFont("宋体"), 10,WritableFont.BOLD); 
            WritableCellFormat wcf_value_total = new WritableCellFormat(wf_value_total); 
            wcf_value_total.setAlignment(jxl.format.Alignment.RIGHT); 
            wcf_value_total.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
            wcf_value_total.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN); 
            wcf_value_total.setWrap(true);//设置文字自适应列宽（注：是文字适应列宽，不是列宽适应文字）
            
            HashMap<Integer, Object> countMap=new HashMap<Integer, Object>();//需要合计的列的索引
            String isCountStr="0";
            
            DecimalFormat df  = new DecimalFormat("###.00"); 
        	DecimalFormat df2  = new DecimalFormat("###");
            Double dVal=null;
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					if (i % 60000 == 0 || i == 0) {
						sheet = workbook.createSheet("页面" + page, page++);
						
						//去掉整个sheet中的网格线  
			            sheet.getSettings().setShowGridLines(false);  
			         
						//页眉:第一行分公司中文全称，第二行分公司英文名,第三行文件名
						sheet.addCell(new Label(0, 0, companyCHName,wcf_title));
						sheet.mergeCells(0,0, title.length-1,0); 
						
						sheet.addCell(new Label(0, 1, companyEnName,wcf_title));
						sheet.mergeCells(0,1, title.length-1,1); 
						
						sheet.addCell(new Label(0, 2, filename,wcf_title));
						sheet.mergeCells(0,2, title.length-1,2); 

						for (int k = 0; k < title.length; k++) {
							//设置表格宽度
							sheet.setColumnView(k, title[k][1]==null?20:Integer.parseInt( title[k][1]));
							sheet.addCell(new Label(k, 3, title[k][0], wcf_value));
							//设置合计单元格
							if(isCount&&title[k].length>2){
								isCountStr=title[k][2];
								if(Integer.parseInt(isCountStr)==1){//需要累加
									countMap.put(k,0);
								}
							}
						}
						row = 4;
					}
					String str[] = (String[]) list.get(i);
					for (int j = 0; j < str.length; j++) {
						if(StringUtils.isNumeric(str[j])){//数字
							dVal=Double.valueOf(str[j]);
							if(dVal==dVal.intValue()){//没小数
								str[j]=df2.format(dVal);
							}else{
								str[j]=df.format(dVal);
							}
						}
						sheet.addCell(new Label(j, row, str[j],wcf_value));
						//累加
						if(isCount){
							if(countMap.get(j)!=null){
								//判断是整数、还是浮点数
								if(StringUtils.isNotBlank(str[j])){
									if(str[j].indexOf(".")>0){//浮点数
										countMap.put(j,Double.parseDouble(countMap.get(j).toString())+Double.parseDouble(StringUtils.isNotBlank(str[j])?str[j].toString():"0"));
									}else{//整数
										countMap.put(j,Long.parseLong(countMap.get(j).toString())+Long.parseLong(StringUtils.isNotBlank(str[j])?str[j].toString():"0"));
										
									}
								}
							}
						}
					}
					++row;
				}
				//合计
				if(isCount){
					String numVal=null;//合计的值
					int firstIndex=0;//第一列需要计数的列索引
					for (int k = 0; k < title.length; k++) {
						numVal="";
						
						if(k==0){
							sheet.addCell(new Label(k, row, "合计", wcf_value_total));
						}else{
							//设置合计单元格
							if(title[k].length>2){
								isCountStr=title[k][2];
								if(Integer.parseInt(isCountStr)==1){//需要累加
									if(firstIndex==0){
										firstIndex=k;
									}
									if(countMap.get(k)!=null){
										numVal=countMap.get(k).toString();
									}
								}
								sheet.addCell(new Label(k, row, numVal, wcf_value_total));
							}else if(firstIndex!=0){
								sheet.addCell(new Label(k, row, "", wcf_value_total));
							}
						}
					}
					if(firstIndex!=0){
						sheet.mergeCells(0, row, firstIndex-1, row);
					}
					++row;
				}
			} else {
				sheet = workbook.createSheet("页面" + page, page++);
				 //去掉整个sheet中的网格线  
	            sheet.getSettings().setShowGridLines(false);  
	         
				//页眉:第一行分公司中文全称，第二行分公司英文名,第三行文件名
				sheet.addCell(new Label(0, 0, companyCHName,wcf_title));
				sheet.mergeCells(0,0, title.length-1,0); 
				
				sheet.addCell(new Label(0, 1, companyEnName,wcf_title));
				sheet.mergeCells(0,1, title.length-1,1); 
				
				sheet.addCell(new Label(0, 2, filename,wcf_title));
				sheet.mergeCells(0,2, title.length-1,2); 
				
				for (int i = 0; i < title.length; i++) {
					//设置表格宽度
					sheet.setColumnView(i, title[i][1]==null?20:Integer.parseInt( title[i][1]));
					sheet.addCell(new Label(i, 3, title[i][0], wcf_value));
					//设置合计单元格
					if(isCount&&title[i].length>2){
						isCountStr=title[i][2];
						if(Integer.parseInt(isCountStr)==1){//需要累加
							countMap.put(i,0);
						}
					}
				}
				row=4;
				//合计
				if(isCount){
					String numVal=null;//合计的值
					int firstIndex=0;//第一列需要计数的列索引
					for (int k = 0; k < title.length; k++) {
						if(k==0){
							sheet.addCell(new Label(k, row, "合计", wcf_value_total));
						}else{
							//设置合计单元格
							if(title[k].length>2){
								isCountStr=title[k][2];
								if(Integer.parseInt(isCountStr)==1){//需要累加
									if(firstIndex==0){
										firstIndex=k;
									}
									if(countMap.get(k)!=null){
										numVal=countMap.get(k).toString();
									}
								}
								sheet.addCell(new Label(k, row, numVal, wcf_value_total));
							}else if(firstIndex!=0){
								sheet.addCell(new Label(k, row, "", wcf_value_total));
							}
						}
					}
					if(firstIndex!=0){
						sheet.mergeCells(0, row, firstIndex-1, row);
					}
					++row;
				}
			}
			workbook.write();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				workbook.close();
				os.close();
			} catch (Exception ex) {
				System.out.println("Close os or workbook to error"+ex);
			}
		}
	}
	
	/**
	 * 第一列合并输出表格
	 * @param setList	表格集合设置	
	 * @param filename	文件名
	 * @param title		excel表名
	 * @param companyCHName	公司中文名称
	 * @param companyEnName	公司英文名称
	 * @param request	
	 * @param response
	 */
	public static void createColMegerExcel(List<Map<String, Object>> setList, String filename, String title[][],String companyCHName,String companyEnName, HttpServletRequest request, HttpServletResponse response){
		int row = 1;
		int page = 1;
		OutputStream os = null;
		WritableWorkbook workbook = null;
		try {
			os = response.getOutputStream();
			response.setHeader("Content-disposition", "attachment; filename="+ encodeOutputFileName(filename, request)+ ".xls"); //设定输出文件头
			response.setContentType("application/excel"); //定义输出类型
			workbook = Workbook.createWorkbook(os);
			WritableSheet sheet = null;
			
			WritableFont wf_title = new jxl.write.WritableFont(WritableFont.createFont("宋体"), 14,WritableFont.BOLD); 
            WritableCellFormat  wcf_title = new WritableCellFormat(wf_title); 
            wcf_title.setAlignment(Alignment.CENTRE); 
            wcf_title.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
            wcf_title.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.THIN); 
            wcf_title.setBorder(jxl.format.Border.RIGHT, jxl.format.BorderLineStyle.THIN);
            
            WritableFont wf_value = new jxl.write.WritableFont(WritableFont.createFont("宋体"), 10,WritableFont.NO_BOLD); 
            WritableCellFormat wcf_value = new WritableCellFormat(wf_value); 
            wcf_value.setAlignment(jxl.format.Alignment.CENTRE); 
            wcf_value.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
            wcf_value.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN); 
            wcf_value.setWrap(true);//设置文字自适应列宽（注：是文字适应列宽，不是列宽适应文字）
            
            WritableFont wf_value_total = new jxl.write.WritableFont(WritableFont.createFont("宋体"), 10,WritableFont.BOLD); 
            WritableCellFormat wcf_value_total = new WritableCellFormat(wf_value_total); 
            wcf_value_total.setAlignment(jxl.format.Alignment.RIGHT); 
            wcf_value_total.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
            wcf_value_total.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN); 
            wcf_value_total.setWrap(true);//设置文字自适应列宽（注：是文字适应列宽，不是列宽适应文字）
            
            if (setList.size() > 0) {
            	sheet = workbook.createSheet("页面" + page, page);
            	//去掉整个sheet中的网格线  
	            sheet.getSettings().setShowGridLines(false);  
	         
				//页眉:第一行分公司中文全称，第二行分公司英文名,第三行文件名
				sheet.addCell(new Label(0, 0, companyCHName,wcf_title));
				sheet.mergeCells(0,0, title.length-1,0); 
				
				sheet.addCell(new Label(0, 1, companyEnName,wcf_title));
				sheet.mergeCells(0,1, title.length-1,1); 
				
				sheet.addCell(new Label(0, 2, filename,wcf_title));
				sheet.mergeCells(0,2, title.length-1,2); 
				int rows = 3;	// 第一列合并计数
				for(int i = 0; i < setList.size(); i++){
					if(i == 0 ){
						//设置表格宽度
						sheet.setColumnView(i, title[0][1]==null?20:Integer.parseInt( title[0][1]));	//第一个合并的列设置宽度
					}
					Map<String, Object> setMap = setList.get(i);
					List<Map<String, Object>> sublist =  (List<Map<String, Object>>) setMap.get("subList");	//获取子集合
					int subSize = sublist.size();
					//设置第一列合并
					String firstLabel = (setMap.get(title[0][0]) == null ? "" : setMap.get(title[0][0]).toString());
					sheet.addCell(new Label(0, rows, firstLabel, wcf_value));
					sheet.mergeCells(0, rows, 0, rows+(subSize-1));
					//
					for(int j = 0 ; j < sublist.size(); j++){	//子集合变量
						Map<String, Object> subMap = sublist.get(j);
						for(int k = 1; k < title.length; k++){	//不合并的列取值
							if(j == 0){
								//设置表格宽度
								sheet.setColumnView(k, title[k][1]==null?20:Integer.parseInt( title[k][1]));	//设置第一列宽度
							}
							String key = title[k][0];
							String celVal = (subMap.get(key) == null ? "" : subMap.get(key).toString());
							sheet.addCell(new Label(k, rows+j, celVal, wcf_value));
						}
					}
					rows = rows + subSize;
				}
            }else{
            	
            }
			workbook.write();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				workbook.close();
				os.close();
			} catch (Exception ex) {
				System.out.println("Close os or workbook to error"+ex);
			}
		}
	}
	
	/**
	 * 3层表头excel导出(列多行合并在前)
	 * @param list		结果集
	 * @param valKey	取值字段
	 * @param rowSum	列合并设置[名称][宽度]
	 * @param firstLayer	多表头第一行[名称][合并列数]
	 * @param secondLayer	多表头第二行[名称][合并列数]
	 * @param thirdLayer	多表头第三行[名称][宽度]
	 * @param filename		文件到出名称
	 * @param companyCHName	中文名
	 * @param companyEnName	英文名
	 * @param request
	 * @param response
	 */
	public static void exprotThreeLayerExcel(List list, String[] valKey, String[][] rowSum, 
			String[][] firstLayer, String[][] secondLayer, String[][] thirdLayer, 
			String filename, String companyCHName,String companyEnName,
			HttpServletRequest request, HttpServletResponse response){
		int row = 1;
		int page = 1;
		OutputStream os = null;
		WritableWorkbook workbook = null;
		try {
			os = response.getOutputStream();
			response.setHeader("Content-disposition", "attachment; filename="+ encodeOutputFileName(filename, request)+ ".xls"); //设定输出文件头
			response.setContentType("application/excel"); //定义输出类型
			workbook = Workbook.createWorkbook(os);
			WritableSheet sheet = null;
			
			WritableFont wf_title = new jxl.write.WritableFont(WritableFont.createFont("宋体"), 14,WritableFont.BOLD); 
            WritableCellFormat  wcf_title = new WritableCellFormat(wf_title); 
            wcf_title.setAlignment(Alignment.CENTRE); 
            wcf_title.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
            wcf_title.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.THIN); 
            wcf_title.setBorder(jxl.format.Border.RIGHT, jxl.format.BorderLineStyle.THIN);
            
            WritableFont wf_value = new jxl.write.WritableFont(WritableFont.createFont("宋体"), 10,WritableFont.NO_BOLD); 
            WritableCellFormat wcf_value = new WritableCellFormat(wf_value); 
            wcf_value.setAlignment(jxl.format.Alignment.CENTRE); 
            wcf_value.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
            wcf_value.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN); 
            wcf_value.setWrap(true);//设置文字自适应列宽（注：是文字适应列宽，不是列宽适应文字）
            
            WritableFont wf_value_total = new jxl.write.WritableFont(WritableFont.createFont("宋体"), 10,WritableFont.BOLD); 
            WritableCellFormat wcf_value_total = new WritableCellFormat(wf_value_total); 
            wcf_value_total.setAlignment(jxl.format.Alignment.RIGHT); 
            wcf_value_total.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
            wcf_value_total.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN); 
            wcf_value_total.setWrap(true);//设置文字自适应列宽（注：是文字适应列宽，不是列宽适应文字）
            
            if (list.size() > 0) {
            	sheet = workbook.createSheet("页面" + page, page);
            	//去掉整个sheet中的网格线  
	            sheet.getSettings().setShowGridLines(false); 
	            
	            //表格总宽度 合并行行数+第三列长度
	            Integer tableWidth = rowSum.length + thirdLayer.length;
	         
				//页眉:第一行分公司中文全称，第二行分公司英文名,第三行文件名
				sheet.addCell(new Label(0, 0, companyCHName,wcf_title));
				sheet.mergeCells(0,0, tableWidth-1,0); 
				
				sheet.addCell(new Label(0, 1, companyEnName,wcf_title));
				sheet.mergeCells(0,1, tableWidth-1,1); 
				
				sheet.addCell(new Label(0, 2, filename,wcf_title));
				sheet.mergeCells(0,2, tableWidth-1,2); 
				int rows = 3;	// 第一列合并计数
				//行合并设置
				for(int i = 0; i < rowSum.length; i++){
					//设置宽度
					sheet.setColumnView(i, rowSum[i][1]==null?20:Integer.parseInt( rowSum[i][1]));
					sheet.addCell(new Label(i, rows, rowSum[i][0], wcf_value));
					sheet.mergeCells(i, rows, i, rows+(3-1));
				}
				//3列设置并合并
				Integer colStart = rowSum.length;	//设置3列起始填充格数
				for(int i = 0; i < firstLayer.length; i++){
					if(i > 0){
						colStart += Integer.valueOf(firstLayer[i-1][1]);
					}
					//在哪个格子写
					sheet.addCell(new Label(colStart, rows, firstLayer[i][0], wcf_value));
					Integer cellWid = Integer.valueOf(firstLayer[i][1]);
					if(cellWid > 1){
						//在合并数大于1 进行合并格子
						sheet.mergeCells(colStart, rows, colStart+cellWid-1, rows);
					}
				}
				
				//第二列设置
				rows++;
				colStart = rowSum.length;	// 初始化
				for(int i = 0; i < secondLayer.length; i++){
					if(i > 0){
						colStart += Integer.valueOf(secondLayer[i-1][1]);
					}
					//在哪个格子写
					sheet.addCell(new Label(colStart, rows, secondLayer[i][0], wcf_value));
					Integer cellWid = Integer.valueOf(secondLayer[i][1]);
					if(cellWid > 1){
						//在合并数大于1 进行合并格子
						sheet.mergeCells(colStart, rows, colStart+cellWid-1, rows);
					}
				}
				
				//第三列设置
				rows++;
				colStart = rowSum.length;	// 初始化
				for(int i = 0; i < thirdLayer.length; i++){
					sheet.setColumnView(i+colStart, thirdLayer[i][1]==null?20:Integer.parseInt( thirdLayer[i][1]));
					sheet.addCell(new Label(i+colStart, rows, thirdLayer[i][0], wcf_value));
				}
				
				//填充数据
				colStart = rowSum.length;	// 初始化
				for(int i = 0; i < list.size(); i++){
					rows++;
					Map<String, Object> map = (Map<String, Object>) list.get(i);
					//填充序号
					sheet.addCell(new Label(0, rows, (i+1)+"", wcf_value));
					for(int j = 0; j < valKey.length; j++){
						String val = (map.get(valKey[j]) == null? "": map.get(valKey[j]).toString());
						sheet.addCell(new Label(j+1, rows, val, wcf_value));
					}
				}
            }else{
            	
            }
			workbook.write();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				workbook.close();
				os.close();
			} catch (Exception ex) {
				System.out.println("Close os or workbook to error"+ex);
			}
		}
	}
	
	/**
	 * 导出两层合并行excel
	 * @param list		结果集
	 * @param valKey	取值字段
	 * @param rowSum	列合并设置[名称][宽度]
	 * @param firstLayer	多表头第一行[名称][合并列数]
	 * @param secondLayer	多表头第二行[名称][宽度]
	 * @param filename		导出文件名
	 * @param companyCHName	公司中文名
	 * @param companyEnName	公司英文名
	 * @param hasNum		是否添加计数序号
	 * @param request
	 * @param response
	 */
	public static void exprotTwoLayerExcel(List list, String[] valKey, String[][] rowSum, 
			String[][] firstLayer, String[][] secondLayer,
			String filename, String companyCHName,String companyEnName, boolean hasNum,
			HttpServletRequest request, HttpServletResponse response){
		int row = 1;
		int page = 1;
		OutputStream os = null;
		WritableWorkbook workbook = null;
		try {
			os = response.getOutputStream();
			response.setHeader("Content-disposition", "attachment; filename="+ encodeOutputFileName(filename, request)+ ".xls"); //设定输出文件头
			response.setContentType("application/excel"); //定义输出类型
			workbook = Workbook.createWorkbook(os);
			WritableSheet sheet = null;
			
			WritableFont wf_title = new jxl.write.WritableFont(WritableFont.createFont("宋体"), 14,WritableFont.BOLD); 
            WritableCellFormat  wcf_title = new WritableCellFormat(wf_title); 
            wcf_title.setAlignment(Alignment.CENTRE); 
            wcf_title.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
            wcf_title.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.THIN); 
            wcf_title.setBorder(jxl.format.Border.RIGHT, jxl.format.BorderLineStyle.THIN);
            
            WritableFont wf_value = new jxl.write.WritableFont(WritableFont.createFont("宋体"), 10,WritableFont.NO_BOLD); 
            WritableCellFormat wcf_value = new WritableCellFormat(wf_value); 
            wcf_value.setAlignment(jxl.format.Alignment.CENTRE); 
            wcf_value.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
            wcf_value.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN); 
            wcf_value.setWrap(true);//设置文字自适应列宽（注：是文字适应列宽，不是列宽适应文字）
            
            WritableFont wf_value_total = new jxl.write.WritableFont(WritableFont.createFont("宋体"), 10,WritableFont.BOLD); 
            WritableCellFormat wcf_value_total = new WritableCellFormat(wf_value_total); 
            wcf_value_total.setAlignment(jxl.format.Alignment.RIGHT); 
            wcf_value_total.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
            wcf_value_total.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN); 
            wcf_value_total.setWrap(true);//设置文字自适应列宽（注：是文字适应列宽，不是列宽适应文字）
            
            if (list.size() > 0) {
            	sheet = workbook.createSheet("页面" + page, page);
            	//去掉整个sheet中的网格线  
	            sheet.getSettings().setShowGridLines(false); 
	            
	            //表格总宽度 合并行行数+第三列长度
	            Integer tableWidth = rowSum.length + secondLayer.length;
	         
				//页眉:第一行分公司中文全称，第二行分公司英文名,第三行文件名
				sheet.addCell(new Label(0, 0, companyCHName,wcf_title));
				sheet.mergeCells(0,0, tableWidth-1,0); 
				
				sheet.addCell(new Label(0, 1, companyEnName,wcf_title));
				sheet.mergeCells(0,1, tableWidth-1,1); 
				
				sheet.addCell(new Label(0, 2, filename,wcf_title));
				sheet.mergeCells(0,2, tableWidth-1,2); 
				int rows = 3;	// 第一列合并计数
				//行合并设置
				for(int i = 0; i < rowSum.length; i++){
					//设置宽度
					sheet.setColumnView(i, rowSum[i][1]==null?20:Integer.parseInt( rowSum[i][1]));
					sheet.addCell(new Label(i, rows, rowSum[i][0], wcf_value));
					sheet.mergeCells(i, rows, i, rows+(2-1));
				}
				//2列合并设置
				Integer colStart = rowSum.length;	//设置3列起始填充格数
				for(int i = 0; i < firstLayer.length; i++){
					if(i > 0){
						colStart += Integer.valueOf(firstLayer[i-1][1]);
					}
					//在哪个格子写
					sheet.addCell(new Label(colStart, rows, firstLayer[i][0], wcf_value));
					Integer cellWid = Integer.valueOf(firstLayer[i][1]);
					if(cellWid > 1){
						//在合并数大于1 进行合并格子
						sheet.mergeCells(colStart, rows, colStart+cellWid-1, rows);
					}
				}
				
				//第二列设置
				rows++;
				colStart = rowSum.length;	// 初始化
				for(int i = 0; i < secondLayer.length; i++){
					sheet.setColumnView(i+colStart, secondLayer[i][1]==null?20:Integer.parseInt( secondLayer[i][1]));
					sheet.addCell(new Label(i+colStart, rows, secondLayer[i][0], wcf_value));
				}
				
				//填充数据
				colStart = rowSum.length;	// 初始化
				for(int i = 0; i < list.size(); i++){
					rows++;
					Map<String, Object> map = (Map<String, Object>) list.get(i);
					Integer num = 0;
					if(hasNum){
						//填充序号
						sheet.addCell(new Label(0, rows, (i+1)+"", wcf_value));
						num = 1;
					}
					for(int j = 0; j < valKey.length; j++){
						String val = (map.get(valKey[j]) == null? "": map.get(valKey[j]).toString());
						sheet.addCell(new Label(j+num, rows, val, wcf_value));
					}
				}
            }else{
            	
            }
			workbook.write();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				workbook.close();
				os.close();
			} catch (Exception ex) {
				System.out.println("Close os or workbook to error"+ex);
			}
		}
	}
	
	//文件名编码
	private static String encodeOutputFileName(String fileName, HttpServletRequest request){
		try{
			if(request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0){
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");//firefox浏览器
			}else{
				fileName = URLEncoder.encode(fileName, "UTF-8");//IE内核浏览器、webkit浏览器内核
			}
		}catch(Exception e){
			
		}
		return fileName;
	}
	
	//生成PDF
	public static void createPDF(List list, HttpServletResponse response,
			String filename, String title[]) {
		response.setContentType("application/pdf"); //定义输出类型
		Document doc = new Document();
		OutputStream out = null;
		try {
			response.setHeader("Content-disposition", "attachment; filename="+ java.net.URLEncoder.encode(filename,"UTF-8")+ ".pdf"); //设定输出文件头
			out = response.getOutputStream();
			PdfWriter.getInstance(doc, out);
			doc.open();
			BaseFont bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			Font font = new Font(bfChinese, 10, com.lowagie.text.Font.NORMAL);
			Table table = new Table(title.length);
			table.setPadding(5);
			table.setWidth(110);
			table.setSpacing(0);
			table.setBorderWidth(1);
			com.lowagie.text.Cell cell = null;
			Paragraph pragraph = null;
			for (int i = 0; i < title.length; i++) {
				pragraph = new Paragraph(title[i], font);
				cell = new com.lowagie.text.Cell(pragraph);
				cell.setBackgroundColor(Color.cyan);
				cell.setHeader(true);
				table.addCell(cell);
			}
			table.endHeaders();
			for (int k = 0; k < list.size(); k++) {
				String str[] = (String[]) list.get(k);
				for (int j = 0; j < title.length; j++) {
					pragraph = new Paragraph(str[j], font);
					cell = new com.lowagie.text.Cell(pragraph);
					table.addCell(cell);
				}
			}
			doc.add(table);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				doc.close();
				out.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	//生成CSV
	public static void createCSV(List list, HttpServletResponse response,
			String filename, String title[]) {
		response.setContentType("application/csv"); //定义输出类型
		PrintWriter out = null;
		try {
			response.setHeader("Content-disposition", "attachment; filename="+ java.net.URLEncoder.encode(filename,"UTF-8")+ ".csv"); //设定输出文件头
			out = new PrintWriter(response.getOutputStream());
			String str = "";
			for (int i = 0; i < title.length; i++) {
				str += title[i] + ",";
			}
			out.write(str.substring(0, str.length() - 1));
			out.println();
			StringBuffer buff;
			for (int j = 0; j < list.size(); j++) {
				String[] s = (String[]) list.get(j);
				buff = new StringBuffer();
				for (int k = 0; k < s.length; k++) {
					buff.append(s[k]);
					buff.append(",");
				}
				out.write(buff.toString().substring(0,buff.toString().length() - 1));
				out.println();
			}
			out.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}
	
	public static void createElctricianStatExcel(String str1, String str2, String str3, 
			String[] title1, String[] title2, String[] title3, List list1, List list2,
			List list3, HttpServletResponse response, String filename) {
		int row = 1;
		int page = 1;
		OutputStream os = null;
		WritableWorkbook workbook = null;
		try {
			os = response.getOutputStream();
			response.setHeader("Content-disposition", "attachment; filename="+ java.net.URLEncoder.encode(filename,"UTF-8")+ ".xls"); //设定输出文件头
			response.setContentType("application/excel"); //定义输出类型
			workbook = Workbook.createWorkbook(os);
			WritableSheet sheet = null;
			WritableFont font = new WritableFont(WritableFont.createFont("宋体"),10, WritableFont.BOLD, false,jxl.format.UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat format = new WritableCellFormat(font);
			format.setBackground(Colour.GRAY_25);
			format.setAlignment(jxl.format.Alignment.CENTRE);
			
			WritableFont font2 = new WritableFont(WritableFont.createFont("宋体"),10, WritableFont.NO_BOLD, false,jxl.format.UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat format2 = new WritableCellFormat(font2);
			format2.setAlignment(jxl.format.Alignment.CENTRE);
			
			WritableFont font3 = new WritableFont(WritableFont.createFont("宋体"),16, WritableFont.BOLD, false,jxl.format.UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat format3 = new WritableCellFormat(font3);
			format3.setAlignment(jxl.format.Alignment.CENTRE);
			if (list1.size() > 0) {
				for (int i = 0; i < list1.size(); i++) {
					if (i % 60000 == 0 || i == 0) {
						sheet = workbook.createSheet("维护", page++);
						for (int j = 1; j < 32; j++) {
							sheet.setColumnView(j, 4);
						}
						sheet.setColumnView(34, 20);
						//添加页标题
						sheet.addCell(new Label(0, 0, str1, format3));
						for (int k = 1; k < title1.length; k++) {
							sheet.addCell(new Label(k, 0, "", format3));
						}
						sheet.mergeCells(0, 0, 34, 0);
						for (int k = 0; k < title1.length; k++) {
							sheet.addCell(new Label(k, 1, title1[k], format));
						}
						row = 2;
					}
					String str[] = (String[]) list1.get(i);
					for (int j = 0; j < str.length; j++) {
						sheet.addCell(new Label(j, row, str[j], format2));
					}
					++row;
				}
			} else {
				sheet = workbook.createSheet("维护", page++);
				for (int j = 1; j < 32; j++) {
					sheet.setColumnView(j, 4);
				}
				sheet.setColumnView(34, 20);
				//添加页标题
				sheet.addCell(new Label(0, 0, str1, format3));
				for (int k = 1; k < title1.length; k++) {
					sheet.addCell(new Label(k, 0, "", format3));
				}
				sheet.mergeCells(0, 0, 34, 0);
				for (int i = 0; i < title1.length; i++) {
					sheet.addCell(new Label(i, 1, title1[i], format));
				}
			}
			
			if (list2.size() > 0) {
				for (int i = 0; i < list2.size(); i++) {
					if (i % 60000 == 0 || i == 0) {
						sheet = workbook.createSheet("安装", page++);
						for (int j = 1; j < 32; j++) {
							sheet.setColumnView(j, 4);
						}
						sheet.setColumnView(34, 20);
						//添加页标题
						sheet.addCell(new Label(0, 0, str2, format3));
						for (int k = 1; k < title2.length; k++) {
							sheet.addCell(new Label(k, 0, "", format3));
						}
						sheet.mergeCells(0, 0, 34, 0);
						for (int k = 0; k < title2.length; k++) {
							sheet.addCell(new Label(k, 1, title2[k], format));
						}
						row = 2;
					}
					String str[] = (String[]) list2.get(i);
					for (int j = 0; j < str.length; j++) {
						sheet.addCell(new Label(j, row, str[j], format2));
					}
					++row;
				}
			} else {
				sheet = workbook.createSheet("安装", page++);
				for (int j = 1; j < 32; j++) {
					sheet.setColumnView(j, 4);
				}
				sheet.setColumnView(34, 20);
				//添加页标题
				sheet.addCell(new Label(0, 0, str2, format3));
				for (int k = 1; k < title2.length; k++) {
					sheet.addCell(new Label(k, 0, "", format3));
				}
				sheet.mergeCells(0, 0, 34, 0);
				for (int i = 0; i < title2.length; i++) {
					sheet.addCell(new Label(i, 1, title2[i], format));
				}
			}
			
			if (list3.size() > 0) {
				for (int i = 0; i < list3.size(); i++) {
					if (i % 60000 == 0 || i == 0) {
						sheet = workbook.createSheet("考评", page++);
						sheet.setColumnView(0, 10);
						sheet.setColumnView(1, 10);
						sheet.setColumnView(2, 10);
						sheet.setColumnView(3, 10);
						sheet.setColumnView(4, 30);
						//添加页标题
						sheet.addCell(new Label(0, 0, str3, format3));
						for (int k = 1; k < title3.length; k++) {
							sheet.addCell(new Label(k, 0, "", format3));
						}
						sheet.mergeCells(0, 0, 4, 0);
						for (int k = 0; k < title3.length; k++) {
							sheet.addCell(new Label(k, 1, title3[k], format));
						}
						row = 2;
					}
					String str[] = (String[]) list3.get(i);
					for (int j = 0; j < str.length; j++) {
						sheet.addCell(new Label(j, row, str[j], format2));
					}
					++row;
				}
			} else {
				sheet = workbook.createSheet("考评", page++);
				sheet.setColumnView(0, 10);
				sheet.setColumnView(1, 10);
				sheet.setColumnView(2, 10);
				sheet.setColumnView(3, 10);
				sheet.setColumnView(4, 30);
				//添加页标题
				sheet.addCell(new Label(0, 0, str3, format3));
				for (int k = 1; k < title3.length; k++) {
					sheet.addCell(new Label(k, 0, "", format3));
				}
				sheet.mergeCells(0, 0, 4, 0);
				for (int i = 0; i < title3.length; i++) {
					sheet.addCell(new Label(i, 1, title3[i], format));
				}
			}
			workbook.write();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				workbook.close();
				os.close();
			} catch (Exception ex) {
				System.out.println("Close os or workbook to error"+ex);
			}
		}
	}
	
	public static void createSubsidyStatExcel(String title1, String title2, 
			String[] totalTitle, String[] singleTitle, List pageList, List totalList,
			List singleLists, HttpServletResponse response, String filename){
		int row = 1;
		int page = 1;
		OutputStream os = null;
		WritableWorkbook workbook = null;
		try {
			os = response.getOutputStream();
			response.setHeader("Content-disposition", "attachment; filename="+ java.net.URLEncoder.encode(filename,"UTF-8")+ ".xls"); //设定输出文件头
			response.setContentType("application/excel"); //定义输出类型
			workbook = Workbook.createWorkbook(os);
			WritableSheet sheet = null;
			WritableFont font = new WritableFont(WritableFont.createFont("宋体"),10, WritableFont.BOLD, false,jxl.format.UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat format = new WritableCellFormat(font);
			format.setBackground(Colour.GRAY_25);
			format.setAlignment(jxl.format.Alignment.CENTRE);
			
			WritableFont font2 = new WritableFont(WritableFont.createFont("宋体"),10, WritableFont.NO_BOLD, false,jxl.format.UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat format2 = new WritableCellFormat(font2);
			format2.setAlignment(jxl.format.Alignment.CENTRE);
			
			WritableFont font3 = new WritableFont(WritableFont.createFont("宋体"),16, WritableFont.BOLD, false,jxl.format.UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat format3 = new WritableCellFormat(font3);
			format3.setAlignment(jxl.format.Alignment.CENTRE);
			if (totalList.size() > 0) {
				for (int i = 0; i < totalList.size(); i++) {
					if (i % 60000 == 0 || i == 0) {
						sheet = workbook.createSheet("总表", page++);
						for (int j = 1; j < 15; j++) {
							sheet.setColumnView(j, 15);
						}
						//添加页标题
						sheet.addCell(new Label(0, 0, title1, format3));
						for (int k = 1; k < totalTitle.length; k++) {
							sheet.addCell(new Label(k, 0, "", format3));
						}
						sheet.mergeCells(0, 0, 13, 0);
						for (int k = 0; k < totalTitle.length; k++) {
							sheet.addCell(new Label(k, 1, totalTitle[k], format));
						}
						row = 2;
					}
					String str[] = (String[]) totalList.get(i);
					for (int j = 0; j < str.length; j++) {
						sheet.addCell(new Label(j, row, str[j], format2));
					}
					++row;
				}
			} else {
				sheet = workbook.createSheet("总表", page++);
				for (int j = 1; j < 15; j++) {
					sheet.setColumnView(j, 15);
				}
				//添加页标题
				sheet.addCell(new Label(0, 0, title1, format3));
				for (int k = 1; k < totalTitle.length; k++) {
					sheet.addCell(new Label(k, 0, "", format3));
				}
				sheet.mergeCells(0, 0, 13, 0);
				for (int i = 0; i < totalTitle.length; i++) {
					sheet.addCell(new Label(i, 1, totalTitle[i], format));
				}
			}
			
			for(int index = 0; index < singleLists.size(); index++) {
				String pageName = (String) pageList.get(index);
				List<String[]> singleList = (List<String[]>)singleLists.get(index);
				if (singleList.size() > 0) {
					for (int i = 0; i < singleList.size(); i++) {
						if (i % 60000 == 0 || i == 0) {
							sheet = workbook.createSheet(pageName, page++);
							for (int j = 1; j < 17; j++) {
								sheet.setColumnView(j, 15);
							}
							//添加页标题
							sheet.addCell(new Label(0, 0, title2, format3));
							for (int k = 1; k < singleTitle.length; k++) {
								sheet.addCell(new Label(k, 0, "", format3));
							}
							sheet.mergeCells(0, 0, 15, 0);
							for (int k = 0; k < singleTitle.length; k++) {
								sheet.addCell(new Label(k, 1, singleTitle[k], format));
							}
							row = 2;
						}
						String str[] = (String[]) singleList.get(i);
						for (int j = 0; j < str.length; j++) {
							sheet.addCell(new Label(j, row, str[j], format2));
						}
						++row;
					}
				} else {
					sheet = workbook.createSheet(pageName, page++);
					for (int j = 1; j < 17; j++) {
						sheet.setColumnView(j, 15);
					}
					//添加页标题
					sheet.addCell(new Label(0, 0, title2, format3));
					for (int k = 1; k < singleTitle.length; k++) {
						sheet.addCell(new Label(k, 0, "", format3));
					}
					sheet.mergeCells(0, 0, 15, 0);
					for (int i = 0; i < singleTitle.length; i++) {
						sheet.addCell(new Label(i, 1, singleTitle[i], format));
					}
				}
			}
			workbook.write();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				workbook.close();
				os.close();
			} catch (Exception ex) {
				System.out.println("Close os or workbook to error"+ex);
			}
		}
	}
	
	public static void createStopAnalysisExcel(List lists, HttpServletResponse response, String companyname, String filename, String[] title1, String[] title2) {
		int row = 1;
		int page = 1;
		OutputStream os = null;
		WritableWorkbook workbook = null;
		try {
			os = response.getOutputStream();
			response.setHeader("Content-disposition", "attachment; filename="+ java.net.URLEncoder.encode(filename,"UTF-8")+ ".xls"); //设定输出文件头
			response.setContentType("application/excel"); //定义输出类型
			workbook = Workbook.createWorkbook(os);
			WritableSheet sheet = null;
			WritableFont font = new WritableFont(WritableFont.createFont("宋体"),10, WritableFont.BOLD, false,jxl.format.UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat format = new WritableCellFormat(font);
			format.setBackground(Colour.GRAY_25);
			format.setAlignment(jxl.format.Alignment.CENTRE);
			
			WritableFont font2 = new WritableFont(WritableFont.createFont("宋体"),10, WritableFont.NO_BOLD, false,jxl.format.UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat format2 = new WritableCellFormat(font2);
			format2.setAlignment(jxl.format.Alignment.CENTRE);
			
			WritableFont font3 = new WritableFont(WritableFont.createFont("宋体"),16, WritableFont.BOLD, false,jxl.format.UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat format3 = new WritableCellFormat(font3);
			format3.setAlignment(jxl.format.Alignment.CENTRE);
			
			WritableFont font4 = new WritableFont(WritableFont.createFont("宋体"),12, WritableFont.BOLD, false,jxl.format.UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat format4 = new WritableCellFormat(font4);
			format3.setAlignment(jxl.format.Alignment.LEFT);
			
			for(int index = 0; index < 12; index++) {
				List<String[]> list = (List<String[]>)lists.get(index);
				for (int i = 0; i < 5; i++) {
					if (i == 0) {
						sheet = workbook.createSheet((index+1)+"月", page++);
						sheet.setColumnView(0, 30);
						sheet.setColumnView(1, 15);
						sheet.setColumnView(2, 15);
						sheet.setColumnView(3, 30);
						//添加页标题1
						sheet.addCell(new Label(0, 0, "("+companyname+")办停分析总表", format3));
						for (int k = 1; k < 4; k++) {
							sheet.addCell(new Label(k, 0, "", format3));
						}
						sheet.mergeCells(0, 0, 3, 0);
						//添加页标题2
						sheet.addCell(new Label(0, 1, "营业厅("+(index+1)+"月份)办理客户停止服务分析", format4));
						for (int k = 1; k < 4; k++) {
							sheet.addCell(new Label(k, 1, "", format4));
						}
						sheet.mergeCells(0, 1, 3, 1);
						for (int k = 0; k < title1.length; k++) {
							sheet.addCell(new Label(k, 2, title1[k], format));
						}
						row = 3;
					}
					String str[] = (String[]) list.get(i);
					for (int j = 0; j < str.length; j++) {
						sheet.addCell(new Label(j, row, str[j], format2));
					}
					++row;
				}
				for (int i = 5; i < 15; i++) {
					if (i == 5) {
						//添加页标题3
						sheet.addCell(new Label(0, 8, "客户办停原因分析统计", format4));
						for (int k = 1; k < 4; k++) {
							sheet.addCell(new Label(k, 8, "", format4));
						}
						sheet.mergeCells(0, 8, 3, 8);
						for (int k = 0; k < title2.length; k++) {
							sheet.addCell(new Label(k, 9, title1[k], format));
						}
						row = 10;
					}
					String str[] = (String[]) list.get(i);
					for (int j = 0; j < str.length; j++) {
						sheet.addCell(new Label(j, row, str[j], format2));
					}
					++row;
				}
			}
			workbook.write();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				workbook.close();
				os.close();
			} catch (Exception ex) {
				System.out.println("Close os or workbook to error"+ex);
			}
		}
	}
	 /**
     * @Title:getData
     * @Description:excel导入处理方法
     * @param file
     * @return
     * @throws
     */
    public static List<String[]>  getData(File file){
        Workbook workbook=null;
        List<String[]> result=null;
        try {
            workbook = Workbook.getWorkbook(file);
            //使用第一个工作表
            Sheet sheet = workbook.getSheet(0);
            result=new ArrayList<String[]>();
            String[] rowData=null;
            int colnum = sheet.getColumns();
            int row=sheet.getRows();
            String[] rowContect=null;
            Cell cell=null;
            for (int i = 0; i < row; i++) {
                rowContect= new String[colnum];
                 for(int j = 0; j < colnum; j++){
                     cell= sheet.getCell(j,i); 
                     rowContect[j]=cell.getContents();
                }
                result.add(rowContect);
            }
           
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
        	if(workbook!=null){
        		workbook.close();
        	}
        }
        return result; 
    }
}