package com.chinaGPS.gtmp.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts2.ServletActionContext;

/**
 * @Package:com.chinaGPS.gtmp.util
 * @ClassName:ExcelUtil
 * @Description:excel文档导入导出处理类
 * @author:lxj
 * @date:Dec 18, 2012 2:47:26 PM
 *
 */
public class ExcelUtil {

    /**
     * @Title:createExcel
     * @Description:excel文档导出处理方法
     * @param list
     * @param filename
     * @param title
     * @throws
     */
    public static void createExcel(List<Object> list, String filename, String title[]) {
        HttpServletResponse response = ServletActionContext.getResponse();
        int row = 1;
        int page = 1;
        OutputStream os = null;
        WritableWorkbook workbook = null;
        try {
            os = response.getOutputStream();
         //   response.setHeader("Content-disposition", "attachment; filename="+ java.net.URLEncoder.encode(filename,"UTF-8")+ ".xls"); //设定输出文件头
            response.addHeader("Content-Disposition", new String(("attachment; filename=" + filename).getBytes("GBK"), "ISO-8859-1")+ ".xls");
            response.setContentType("application/excel"); //定义输出类型
            workbook = Workbook.createWorkbook(os);
            WritableSheet sheet = null;
            WritableFont font = new WritableFont(WritableFont.createFont("宋体"),10, WritableFont.BOLD, false,jxl.format.UnderlineStyle.NO_UNDERLINE);
            WritableCellFormat format = new WritableCellFormat(font);
            format.setBackground(Colour.GRAY_25);
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    if (i % 60000 == 0 || i == 0) {
                        sheet = workbook.createSheet("页面" + page, page++);
                        for (int k = 0; k < title.length; k++) {
                            sheet.addCell(new Label(k, 0, title[k], format));
                        }
                        row = 1;
                    }
                    String str[] = (String[])((Object)list.get(i));
                    for (int j = 0; j < str.length; j++) {
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
                workbook.close();
            }
            return result; 
        }
       
}
