package cc.chinagps.gateway.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.jboss.netty.channel.Channel;

public class IOUtil {
	public static void closeOS(OutputStream os){
		if(os != null){
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void closeIS(InputStream is){
		if(is != null){
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void closeChannel(Channel channel){
		if(channel != null){
			try{
				channel.close();
			}catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
}