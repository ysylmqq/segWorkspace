package com.gboss.util;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 简单图像工具类,处理图像缩放等
 * 
 * @author LXB 
 *  
 */
public class ImageUtils {

	static private Log logger = LogFactory.getLog(ImageUtils.class);

	public static BufferedImage resize(BufferedImage srcBufImage, int width,
			int height, int mode) {

		double rate = (double) srcBufImage.getWidth() / srcBufImage.getHeight();

		switch (mode) {
		case 0:// 自适应方式?

		case 1:// 不保持长宽比,缩放至目标像素;
			break;
		case 2:// 保持长宽比,宽度为优先条件
			height = (int) (width / rate);
			break;
		case 3:// 保持长宽比,高度为优先条件
			width = (int) (height * rate);
			break;
		}

		BufferedImage bufTarget = new BufferedImage((int) width, (int) height,
				BufferedImage.TYPE_INT_RGB);
		/*
		 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
		 */
		bufTarget.getGraphics()
				.drawImage(
						srcBufImage.getScaledInstance(width, height,
								Image.SCALE_SMOOTH), 0, 0, null);
		return bufTarget;
	}

	/**
	 * @param image
	 *            图像文件字节流
	 * @param width
	 *            缩放后的宽度（像素）
	 * @param height
	 *            绽放后的高度（像素）
	 * @param mode
	 *            工作模式：1 不保持长宽比,缩放至目标像素; 2 保持长宽比,宽度为优先条件; 3 保持长宽比,高度为优先条件
	 * @param quality
	 *            压缩质量比
	 * @return 缩放后的图像字节流
	 */
	public static byte[] resize(byte[] image, int width, int height, int mode,
			float quality) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		resize(new ByteArrayInputStream(image), baos, width, height, mode,
				quality);
		return baos.toByteArray();
	}

	/**
	 * @param image
	 *            源图像文件
	 * @param width
	 *            缩放后的宽度（像素）
	 * @param height
	 *            绽放后的高度（像素）
	 * @param mode
	 *            工作模式：1 不保持长宽比,缩放至目标像素; 2 保持长宽比,宽度为优先条件; 3 保持长宽比,高度为优先条件
	 * @param quality
	 *            压缩质量比
	 * @return 缩放后的图像字节流
	 */
	public static byte[] resize(File image, int width, int height, int mode,
			float quality) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			resize(new FileInputStream(image), baos, width, height, mode,
					quality);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		}
		return baos.toByteArray();
	}

	/**
	 * @param image
	 *            源图像文件
	 * @param out
	 *            　输出流
	 * @param width
	 *            缩放后的宽度（像素）
	 * @param height
	 *            绽放后的高度（像素）
	 * @param mode
	 *            工作模式：1 不保持长宽比,缩放至目标像素; 2 保持长宽比,宽度为优先条件; 3 保持长宽比,高度为优先条件
	 * @param quality
	 *            压缩质量比
	 * @return 是否成功
	 */
	public static boolean resize(InputStream image, OutputStream out,
			int width, int height, int mode, float quality) {
		try {

			BufferedImage srcImage = ImageIO.read(image);
			BufferedImage result = resize(srcImage, width, height, mode);

			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(result);
			/* 压缩质量 */
			if (quality > 0) {
				jep.setQuality(quality, true);
			}
			encoder.encode(result, jep);
			out.flush();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * @param orginImage
	 *            源图像文件路径
	 * @param desImage
	 *            目标图像文件路径　
	 * @param width
	 *            缩放后的宽度（像素）
	 * @param height
	 *            绽放后的高度（像素）
	 * @param mode
	 *            工作模式：1 不保持长宽比,缩放至目标像素; 2 保持长宽比,宽度为优先条件; 3 保持长宽比,高度为优先条件
	 * @return 是否成功
	 */
	public static boolean resize(String orginImage, String desImage, int width,
			int height, int mode) {
		try {
			FileOutputStream f = new FileOutputStream(desImage);
			return resize(new FileInputStream(orginImage), f, width, height,
					mode, 0);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	/**
	 * @param orginImage
	 *            源图像文件路径
	 * @param desImage
	 *            目标图像文件路径　
	 * @param width
	 *            缩放后的宽度（像素）
	 * @param height
	 *            绽放后的高度（像素）
	 * @param mode
	 *            工作模式：1 不保持长宽比,缩放至目标像素; 2 保持长宽比,宽度为优先条件; 3 保持长宽比,高度为优先条件 * @param
	 * @param quality
	 *            压缩质量比
	 * @return 是否成功
	 */
	public static boolean resize(String orginImage, String desImage, int width,
			int height, int mode, float quality) {
		try {
			return resize(new FileInputStream(orginImage),
					new FileOutputStream(desImage), width, height, mode,
					quality);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	/**
     * 图像切割(按指定起点坐标和宽高切割)
     * @param srcImageFile 源图像地址
     * @param result 切片后的图像地址
     * @param x 目标切片起点坐标X
     * @param y 目标切片起点坐标Y
     * @param width 目标切片宽度
     * @param height 目标切片高度
     */
    public  static byte[] cut(byte[] imageOld, int x, int y, int width, int height) {
		FileInputStream is = null;
		ImageInputStream iis = null;
		try {
			
        	String filePath = "C:\\TEST_FILE.JPG";
        	 File file = new File(filePath);  
             FileOutputStream fops = new FileOutputStream(file);  
             fops.write(imageOld);  
             fops.flush();  
             fops.close(); 
			// 读取图片文件
			Byte[] a = new Byte[1024];
			is = new FileInputStream(filePath);
			
			/*
			 * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader 声称能够解码指定格式。
			 * 参数：formatName - 包含非正式格式名称 .（例如 "jpeg" 或 "tiff"）等 。
			 */
			Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName("jpg");
			ImageReader reader = it.next();
			// 获取图片流
			iis = ImageIO.createImageInputStream(is);

			/*
			 * <p>iis:读取源.true:只向前搜索 </p>.将它标记为 ‘只向前搜索’。
			 * 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader 避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。
			 */
			reader.setInput(iis, true);

			/*
			 * <p>描述如何对流进行解码的类<p>.用于指定如何在输入时从 Java Image I/O
			 * 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件 将从其 ImageReader 实现的
			 * getDefaultReadParam 方法中返回 ImageReadParam 的实例。
			 */
			ImageReadParam param = reader.getDefaultReadParam();

			/*
			 * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象
			 * 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。
			 */
			Rectangle rect = new Rectangle(x, y, width, height);

			// 提供一个 BufferedImage，将其用作解码像素数据的目标。
			param.setSourceRegion(rect);

			/*
			 * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将 它作为一个完整的
			 * BufferedImage 返回。
			 */
			BufferedImage bi = reader.read(0, param);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(bi,"jpg", out);
			return out.toByteArray();
			// 保存新图片
			//ImageIO.write(bi, "jpg", outfile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (iis != null) {
				try {
					iis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return imageOld;
    }
    
   
    /**
     * 图片剪切工具方法
     * 
     * @param srcfile 源图片
     * @param outfile 剪切之后的图片
     * @param x 剪切顶点 X 坐标
     * @param y 剪切顶点 Y 坐标
     * @param width 剪切区域宽度
     * @param height 剪切区域高度
     * 
     * @throws IOException
     * @author cevencheng
     */
	public static void cut(File srcfile, File outfile, int x, int y, int width, int height) throws IOException {
		FileInputStream is = null;
		ImageInputStream iis = null;
		try {
			// 读取图片文件
			Byte[] a = new Byte[1024];
			is = new FileInputStream(srcfile);
			
			/*
			 * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader 声称能够解码指定格式。
			 * 参数：formatName - 包含非正式格式名称 .（例如 "jpeg" 或 "tiff"）等 。
			 */
			Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName("jpg");
			ImageReader reader = it.next();
			// 获取图片流
			iis = ImageIO.createImageInputStream(is);

			/*
			 * <p>iis:读取源.true:只向前搜索 </p>.将它标记为 ‘只向前搜索’。
			 * 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader 避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。
			 */
			reader.setInput(iis, true);

			/*
			 * <p>描述如何对流进行解码的类<p>.用于指定如何在输入时从 Java Image I/O
			 * 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件 将从其 ImageReader 实现的
			 * getDefaultReadParam 方法中返回 ImageReadParam 的实例。
			 */
			ImageReadParam param = reader.getDefaultReadParam();

			/*
			 * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象
			 * 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。
			 */
			Rectangle rect = new Rectangle(x, y, width, height);

			// 提供一个 BufferedImage，将其用作解码像素数据的目标。
			param.setSourceRegion(rect);

			/*
			 * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将 它作为一个完整的
			 * BufferedImage 返回。
			 */
			BufferedImage bi = reader.read(0, param);

			// 保存新图片
			ImageIO.write(bi, "jpg", outfile);
		} finally {
			if (is != null) {
				is.close();
			}
			if (iis != null) {
				iis.close();
			}
		}
    }
	/** 
     * 将图片写入到磁盘 
     * @param img 图片数据流 
     * @param fileName 文件保存时的名称 
     */  
    public static void writeImageToDisk(byte[] img, String fileName){  
        try {  
            File file = new File("C:\\" + fileName);  
            FileOutputStream fops = new FileOutputStream(file);  
            fops.write(img);  
            fops.flush();  
            fops.close();  
            System.out.println("图片已经写入到C盘");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    /** 
     * 根据地址获得数据的字节流 
     * @param strUrl 网络连接地址 
     * @return 
     */  
    public static byte[] getImageFromNetByUrl(String strUrl){  
        try {  
            URL url = new URL(strUrl);  
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
            conn.setRequestMethod("GET");  
            conn.setConnectTimeout(5 * 1000);  
            InputStream inStream = conn.getInputStream();//通过输入流获取图片数据  
            byte[] btImg = readInputStream(inStream);//得到图片的二进制数据  
            return btImg;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
    /** 
     * 从输入流中获取数据 
     * @param inStream 输入流 
     * @return 
     * @throws Exception 
     */  
    public static byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        while( (len=inStream.read(buffer)) != -1 ){  
            outStream.write(buffer, 0, len);  
        }  
        inStream.close();  
        return outStream.toByteArray();  
    } 
	public static void main(String[] args) throws IOException {
//		resize("E:\\images\\0002.JPG", "E:\\images\\0002_resize.JPG", 600, 300, 2);
		//cut(new File("c:\\test_file.jpg"),new File("E:\\images\\testg.JPG"),0,0,600,300);
	cut(getImageFromNetByUrl("http://90.0.29.196:8888/group1/M00/00/01/wKgDxFRWXqKEL0tGAAAAAPX6tD0001.jpg"),0,0,600,300);
	}

}