package com.chinaGPS.gtmp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件打包成zip包
 * @author 卞启威
 *
 */
public class ZIPUtil {

private ZIPUtil() {
}
/**
 * 生成zip包
 * @param filePath	文件所在路径。实例F:/movie
 * @param zipFilePath zip生成路径。实例F:/movie/movie.zip
 */
public void CreateZipFile(String filePath, String zipFilePath) {
   FileOutputStream fos = null;
   ZipOutputStream zos = null;
   try {
    fos = new FileOutputStream(zipFilePath);
    zos = new ZipOutputStream(fos);
    writeZipFile(new File(filePath), zos, "");
   } catch (FileNotFoundException e) {
    e.printStackTrace();
   } finally {
    try {
     if (zos != null)
      zos.close();
    } catch (IOException e) {
     e.printStackTrace();
    }
    try {
     if (fos != null)
      fos.close();
    } catch (IOException e) {
     e.printStackTrace();
    }
   }

}

private void writeZipFile(File f, ZipOutputStream zos, String hiberarchy) {
   if (f.exists()) {
    if (f.isDirectory()) {
     //hiberarchy += f.getName() + "/";	//注释此代码，文件全部打包在文件根目录
     File[] fif = f.listFiles();
     for (int i = 0; i < fif.length; i++) {
      writeZipFile(fif[i], zos, hiberarchy);
     }

    } else {
     FileInputStream fis = null;
     try {
      fis = new FileInputStream(f);
      ZipEntry ze = new ZipEntry(hiberarchy + f.getName());
      zos.putNextEntry(ze);
      byte[] b = new byte[1024];
      while (fis.read(b) != -1) {
       zos.write(b);
       b = new byte[1024];
      }
     } catch (FileNotFoundException e) {
      e.printStackTrace();
     } catch (IOException e) {
      e.printStackTrace();
     } finally {
      try {
       if (fis != null)
        fis.close();
      } catch (IOException e) {
       e.printStackTrace();
      }
     }

    }
   }

}

private static ZIPUtil zu = null;

public static ZIPUtil getInstance() {
   if (zu == null)
    zu = new ZIPUtil();
   return zu;

}

public static void main(String[] args) {

   ZIPUtil.getInstance().CreateZipFile("F:/6",
     "F:/6/6.zip");
}

}