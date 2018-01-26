package cc.chinagps.gateway.web.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class FileUtil {
	private static final long FILE_SIZE_G = 1073741824L;
	private static final long FILE_SIZE_M = 1048576L;
	private static final long FILE_SIZE_K = 1024L;
	
	public static String getFileLength(long lenght){
		NumberFormat nf = new DecimalFormat("#.##");
		if(lenght < 0){
			return null;
		}
		
		if(lenght >= FILE_SIZE_G){
			return nf.format(1.0 * lenght / FILE_SIZE_G) + " G";
		}
		
		if(lenght >= FILE_SIZE_M){
			return nf.format(1.0 * lenght / FILE_SIZE_M) + " M";
		}
		
		if(lenght >= FILE_SIZE_K){
			return nf.format(1.0 * lenght / FILE_SIZE_K) + " K";
		}
		
		return lenght + " B";
	}
}