package cc.chinagps.gateway.test.aplan;

import java.nio.ByteBuffer;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.aplan.APlanPackageDecoder;
import cc.chinagps.gateway.stream.InputPackageHandler;

public class APlanTest {
	public static void main(String[] args) throws Exception {
		AHandler handler = new AHandler();
		APlanPackageDecoder decoder = new APlanPackageDecoder(handler);
		
		byte[] data1 = getData(80);
		byte[] data2 = getData(160);
		
//		ByteBuffer buff0 = ByteBuffer.allocate(data1.length);
//		buff0.put(data1);
//		buff0.flip();
//		decoder.handleStream(buff0, buff0.limit());
		
//		for(int i = 0; i < data1.length; i++){
//			ByteBuffer buff1 = ByteBuffer.allocate(1);
//			buff1.put(data1[i]);
//			buff1.flip();
//			System.out.print("(" + Util.getBytesString(buff1.array()) + ")");
//			System.out.println("buff1:" + buff1);
//			decoder.handleStream(buff1, buff1.limit());
//		}
		
//		ByteBuffer buff2 = ByteBuffer.allocate(data1.length + data2.length);
//		buff2.put(data1);
//		buff2.put(data2);
//		buff2.flip();
//		decoder.handleStream(buff2, buff2.limit());
		
		int len02 = data2.length / 2;
		int len1 = data1.length + len02;
		int len2 = data1.length + data2.length - len1;
		ByteBuffer buff31 = ByteBuffer.allocate(len1);
		ByteBuffer buff32 = ByteBuffer.allocate(len1);
		
		buff31.put(data1);
		buff31.put(data2, 0, len02);
		buff32.put(data2, len02, len2);
		
		buff31.flip();
		buff32.flip();
		
		decoder.handleStream(buff31, buff31.limit());
		decoder.handleStream(buff32, buff32.limit());
	}
	
	private static byte[] getData(int len){
		int left = len - 4;
		byte[] data = new byte[len];
		byte[] head = Util.getIntByte(len);
		System.arraycopy(head, 0, data, 0, 4);
		for(int i = 0;  i < left; i++){
			byte c = (byte) ('a' + i % 10);
			data[i + 4] = c;
		}
		
		return data;
	}
	
	
	private static class AHandler implements InputPackageHandler{
		@Override
		public void packageReceived(byte[] pkg) {
			System.out.println("pkg:" + Util.getHexString(pkg));
		}
	}
}