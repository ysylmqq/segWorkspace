package com.org.netty.heartbeat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 虽然压缩能够减小码流，但是增大了CPU的开销(压缩是很消耗CPU资源的)
 *
 */
public class GzipUtil {

	/**
	 *	byte数组进行压缩 
	 **/
	public static byte[] gzip(byte data[]) throws IOException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(bos);
		gzip.write(data);
		gzip.finish();
		gzip.close();
		byte res [] = bos.toByteArray();
		return res;
	}
	
	/**
	 * 压缩之后的直接数组进行解压
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static byte[] unzip(byte data[]) throws IOException{
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		GZIPInputStream gzip = new GZIPInputStream(bis);
		byte []buf = new byte[1024*1024];
		int num = -1;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while( (num =gzip.read(buf, 0, buf.length)) != -1){
			bos.write(buf,0,num);
		}
		gzip.close();
		bis.close();
		byte res[] = bos.toByteArray();
		bos.flush();
		bos.close();
		return res;
	}
	
	public static void main(String[] args) throws IOException {
		File file = new File("E:/soft/elk李进.mp4");
		FileInputStream fis =new FileInputStream(file);
		byte fileByte [] =new byte[fis.available()];
		fis.read(fileByte);
		fis.close();
		
		System.err.println("压缩之前的大小 "+fileByte.length);
		
		byte [] gzip = GzipUtil.gzip(fileByte);
		System.err.println("压缩之后的大小 "+gzip.length);
		
		byte unzip [] = GzipUtil.unzip(gzip);
		System.err.println("还原后的大小 "+unzip.length);

	}
}
