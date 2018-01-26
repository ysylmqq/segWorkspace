package cc.chinagps.lib.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * IO工具类
 * @author Arvin, Zhangxz修改
 * 2011-12-7
 */
public class IOUtil {
	/**
	 * 关闭输出流
	 * @param os 输出流
	 */
	public static void closeOS(OutputStream os){
		if(os != null){
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 关闭输入流
	 * @param is 输入流
	 */
	public static void closeIS(InputStream is){
		if(is != null){
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 关闭socket
	 * @param socket socket
	 */
	public static void closeSocket(Socket socket){
		if(socket != null){
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 关闭socket
	 * @param socket DatagramSocket
	 */
	public static void closeSocket(DatagramSocket socket){
		if(socket != null){
			try {
				socket.close();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 关闭socketChannel
	 * @param socketChannel socketChannel
	 */
	public static void closeSocketChannel(SocketChannel socketChannel){
		if(socketChannel != null){
			try {
				socketChannel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
    /**
     * 序列化
     * 
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
    	if (object != null) {
	        ObjectOutputStream oos = null;
	        ByteArrayOutputStream baos = null;
	        try {
	            // 序列化
	            baos = new ByteArrayOutputStream();
	            oos = new ObjectOutputStream(baos);
	            oos.writeObject(object);
	            byte[] bytes = baos.toByteArray();
	            return bytes;
	        } catch (Exception e) {
				e.printStackTrace();
	        }
    	}
        return null;
    }

    /**
     * 反序列化
     * 
     * @param bytes
     * @return
     */
    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
			e.printStackTrace();
        }
        return null;
    }
}