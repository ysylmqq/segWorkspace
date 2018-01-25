package com.gboss.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * @Package:com.gboss.controller
 * @ClassName:ExcelMain
 * @Description:TODO
 * @author:bzhang
 * @date:2014-12-24 上午9:01:41
 */
public class UpdateExcel {

	public static void main(String args[]) throws Exception {

		updaeExcsel();

		// readfile("http://192.168.3.151:18080/gboss-comcenter/obd/test2ss.xlsx");

		/*WritableWorkbook book = null;
		HashMap<String, String> map = new HashMap<String, String>();
		map = getPCKV();
		try {
			// Excel获得文件
			Workbook wb = Workbook.getWorkbook(new File(
					"D:/test/excel/upload/test.xls")); // 打开一个文件的副本，并且指定数据写回到原文件
			book = Workbook.createWorkbook(new File(
					"D:/test/excel/upload/AAA.xls"), wb);
			Sheet sheet = book.getSheet(0);
			WritableSheet wsheet = book.getSheet(0);
			int colunms = sheet.getColumns(); // 不读表头
			for (int i = 1; i < sheet.getRows(); i++) {
				StringBuffer pcin = new StringBuffer(); // 将省市组合起来与HashMap进行匹配
				String province = sheet.getCell(4, i).getContents().trim();
				String city = sheet.getCell(5, i).getContents().trim();
				pcin = pcin.append(province).append("-").append(city); // 如果不匹配，则在该行的最后加入标注信息
				if (!map.containsValue(pcin.toString())) {
					Label label = new Label(colunms, i, "省市选择出错",
							getDataCellFormat());
					wsheet.addCell(label);
				}
			}

			Label label = new Label(colunms, 0, "vin");
			wsheet.addCell(label);

			book.write();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				book.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
*/
		/*
		 * String url=""; String date = DateUtil.formatToday(); File fileDir =
		 * new File(date); if (!fileDir.exists()) { // 判断路径是否存在，不存在则创建
		 * fileDir.mkdirs(); }
		 */

	}

	public void updaeExcel() throws Exception {
		WritableWorkbook book = null;
		HashMap<String, String> map = new HashMap<String, String>();
		map = getPCKV();
		try {
			// Excel获得文件
			Workbook wb = Workbook.getWorkbook(new File("update_test.xls"));
			// 打开一个文件的副本，并且指定数据写回到原文件
			book = Workbook.createWorkbook(new File("update_test.xls"), wb);
			Sheet sheet = book.getSheet(0);
			WritableSheet wsheet = book.getSheet(0);
			int colunms = sheet.getColumns();
			// 不读表头
			for (int i = 1; i < sheet.getRows(); i++) {
				StringBuffer pcin = new StringBuffer();
				// 将省市组合起来与HashMap进行匹配
				String province = sheet.getCell(4, i).getContents().trim();
				String city = sheet.getCell(5, i).getContents().trim();
				pcin = pcin.append(province).append("-").append(city);
				// 如果不匹配，则在该行的最后加入标注信息
				if (!map.containsValue(pcin.toString())) {
					Label label = new Label(colunms, i, "省市选择出错",
							getDataCellFormat());
					wsheet.addCell(label);
				}
			}
			book.write();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				book.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 设置标注的格式为黄底红字
	public static WritableCellFormat getDataCellFormat() {
		WritableCellFormat wcf = null;
		try {
			WritableFont wf = new WritableFont(WritableFont.TIMES, 10,
					WritableFont.BOLD, false);
			// 字体颜色
			wf.setColour(Colour.RED);
			wcf = new WritableCellFormat(wf);
			// 对齐方式
			wcf.setAlignment(Alignment.CENTRE);
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
			// 设置上边框
			wcf.setBorder(Border.TOP, BorderLineStyle.THIN);
			// 设置下边框
			wcf.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
			// 设置左边框
			wcf.setBorder(Border.LEFT, BorderLineStyle.THIN);
			// 设置右边框
			wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
			// 设置背景色
			wcf.setBackground(Colour.YELLOW);
			// 自动换行
			wcf.setWrap(true);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return wcf;
	}

	// 修改excel，填充车架号 jxl 怎么获取到Label
	public static void updaeExcsel() throws Exception {
		WritableWorkbook book = null;
		try {
			// Excel获得文件
			Workbook wb = Workbook
					.getWorkbook(new File(
							"F:/EclipseWorkSpace/GBOSS/sos/obdFile/20141225/20141225_101.xls"));
			book = Workbook
					.createWorkbook(
							new File(
									"F:/EclipseWorkSpace/GBOSS/sos/obdFile/20141225/20141225_101_abc.xls"),
							wb);
			Sheet sheet = book.getSheet(0);
			WritableSheet wsheet = book.getSheet(0);
			int colunms = sheet.getColumns(); // 不读表头
			for (int i = 4; i < sheet.getRows() - 1; i++) {

				/*
				 * WritableCell cell = sheet.getWritableCell(1, i);// 获取第一个单元格
				 * 
				 * jxl.format.CellFormat cf = cell.getCellFormat();//
				 * 获取第一个单元格的格式 jxl.write.Label lbl = new jxl.write.Label(1, i,
				 * "BBBBBBBggggBBBBBBB");// 将第一个单元格的值改为“修改後的值”
				 * lbl.setCellFormat(cf);// 将修改后的单元格的格式设定成跟原来一样
				 */
				Label label = new Label(1, i, "省市选择出错", getDataCellFormat());
				wsheet.addCell(label);
			}
			book.write();// 将修改保存到workbook --》一定要保存
			// 关闭workbook，释放内存 ---》一定要释放内存

			/*
			 * // 打开一个文件的副本，并且指定数据写回到原文件 book = Workbook.createWorkbook(new
			 * File("D:/test/excel/upload/20141225.xls"), wb); //Sheet sheet =
			 * book.getSheet(0); WritableSheet wsheet = book.getSheet(0); // int
			 * colunms = sheet.getColumns(); // 不读表头 //for (int i = 4; i < 5;
			 * i++) { // String call_letter = sheet.getCell(0, i).getContents();
			 * // if(com.gboss.util.StringUtils.isNotBlank(call_letter)){
			 * 
			 * WritableCell cell = wsheet.getWritableCell(4,1 );
			 * jxl.format.CellFormat cf = cell.getCellFormat(); jxl.write.Label
			 * lbl = new jxl.write.Label(4, 1, "123456789ABCDFGHU");
			 * lbl.setCellFormat(cf); wsheet.addCell(lbl); book.write();
			 */
			// }
			// }
			// Label label = new Label(colunms, 3, "vin");
			// wsheet.addCell(label);

		} catch (Exception e) {
			System.out.println(e);
		} finally {

			try {
				book.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 
	 * @Title:isExistFile
	 * @Description:检查服务器文件是否存在
	 * @param fileUrl
	 * @return
	 * @throws Exception
	 * @throws
	 */
	public static boolean isExistFile(String fileUrl) throws Exception {
		boolean falg = false;
		URL url = new URL(fileUrl);
		HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
		String message = urlcon.getHeaderField(0);
		// System.out.println(message);
		// 文件存在‘HTTP/1.1 200 OK’ 文件不存在 ‘HTTP/1.1 404 Not Found’
		/*
		 * Long TotalSize = Long.parseLong(urlcon
		 * .getHeaderField("Content-Length"));
		 */
		if (message.contains("200")) {
			falg = true;
		}
		return falg;
	}

	// 省市对应关系Map
	public static HashMap<String, String> getPCKV() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("01", "河北省-石家庄");
		map.put("02", "河北省-秦皇岛");
		map.put("03", "河北省-唐山");
		map.put("04", "四川省-成都");
		map.put("05", "四川省-绵阳");
		map.put("06", "四川省-达州");
		map.put("07", "广西省-桂林");
		map.put("08", "广西省-南宁");
		map.put("09", "广西省-柳州");
		return map;
	}

	/**
	 * 下载远程文件并保存到本地
	 * 
	 * @param remoteFilePath
	 *            远程文件路径
	 * @param localFilePath
	 *            本地文件路径
	 */
	public static void downloadFile(String remoteFilePath, String localFilePath) {
		URL urlfile = null;
		HttpURLConnection httpUrl = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		File f = new File(localFilePath);
		try {
			urlfile = new URL(remoteFilePath);
			httpUrl = (HttpURLConnection) urlfile.openConnection();
			httpUrl.connect();
			bis = new BufferedInputStream(httpUrl.getInputStream());
			bos = new BufferedOutputStream(new FileOutputStream(f));
			int len = 2048;
			byte[] b = new byte[len];
			while ((len = bis.read(b)) != -1) {
				bos.write(b, 0, len);
			}
			bos.flush();
			bis.close();
			httpUrl.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bis.close();
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean readfile(String filepath)
			throws FileNotFoundException, IOException {
		try {

			File file = new File(filepath);
			if (!file.isDirectory()) {
				System.out.println("文件");
				System.out.println("path=" + file.getPath());
				System.out.println("absolutepath=" + file.getAbsolutePath());
				System.out.println("name=" + file.getName());

			} else if (file.isDirectory()) {
				System.out.println("文件夹");
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filepath + "\\" + filelist[i]);
					if (!readfile.isDirectory()) {
						downloadFile(filepath + "/" + readfile.getName(),
								"D:/obd/excel/" + readfile.getName());
						System.out.println("path=" + readfile.getPath());
						System.out.println("absolutepath="
								+ readfile.getAbsolutePath());
						System.out.println("name=" + readfile.getName());

					} else if (readfile.isDirectory()) {
						readfile(filepath + "\\" + filelist[i]);
					}
				}

			}

		} catch (FileNotFoundException e) {
			System.out.println("readfile()   Exception:" + e.getMessage());
		}
		return true;
	}
}