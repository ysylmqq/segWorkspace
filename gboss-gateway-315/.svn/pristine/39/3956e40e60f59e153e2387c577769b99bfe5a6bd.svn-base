package cc.chinagps.gateway.stream.decoders;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import cc.chinagps.gateway.stream.InputStreamDecoder;
import cc.chinagps.gateway.stream.InputPackageHandler;
import cc.chinagps.gateway.util.HexUtil;

/**
 * 针对seg车台 上报数据会有错乱数据的情况 在一定范围内进行自动纠错
 * 在流水号3-12位出现0x5B, 0x5C直接替换成0x42(字符'B')和0x43(字符'C')
 * 在流水号3-12位出现0x5D时,排除两种正常情况0x5B 0xF0 0x5D和0x5B 0xF4 0x4C 0x55 0x5D后用0x44(字符'D')替换
 */
public class SegFlagStreamDecoder implements InputStreamDecoder{
	private InputPackageHandler handler;
	
	private int byteLength = 0;
	private List<byte []> byteList = new ArrayList<byte []>();
	private byte startFlag;
	private byte endFlag;

	private boolean headFinded = false;
	
	public SegFlagStreamDecoder(InputPackageHandler handler, byte startFlag, byte endFlag){
		this.handler = handler;
		this.startFlag = startFlag;
		this.endFlag = endFlag;
	}
	
	private void reset(){
		byteList.clear();
		byteLength = 0;
		headFinded = false;
	}
	
	private void dealPackage() throws Exception{
		try{
			byte[] data;
			if(byteList.size() == 1){
				data = byteList.get(0);
			}else{
				ByteBuffer buff = ByteBuffer.allocate(byteLength);
				for(int i = 0; i < byteList.size(); i++){
					buff.put(byteList.get(i));
				}
				
				data = buff.array();
			}
			
			handler.packageReceived(data);
		}finally{
			reset();
		}
	}
	
	//private static final int snStart = 2;
	//private static final int snEnd = 11;
	
	//private static final byte _5C = 0x5C;
	
	//private static final byte[] normal1 = new byte[]{0x5B, (byte) 0xF0};
	//private static final byte[] normal2 = new byte[]{0x5B, (byte) 0xF4, 0x4C, 0x55};
	
	@Override
	public void handleStream(ByteBuffer buff, int len) throws Exception{
		byte[] bs = new byte[len];
		buff.get(bs);
		
		int copyStart = 0;
		int readIndex;
		for(readIndex = 0; readIndex < len; readIndex++){
			byte current = bs[readIndex];
			if(!headFinded){
				if(current != startFlag){
					throw new IOException("stx error:" + current);
				}
				
				headFinded = true;
				copyStart = readIndex;
			}else{
				if(current == endFlag){
					//需要处理包中有5D的情况
					//5B...5D...5D
					//(13632666741)5b963030303030303030303056585d4122318460011402515734554122360919011402801434442013101601515310160206020312011284000130004360002325000000000000000000000000000000000000000000000000000298220000000009495d
					if((readIndex < len - 1) && (bs[readIndex + 1] != startFlag)){
						//报文还有数据且下个字节不是0x5B
						continue;
					}
					
					doCopy(bs, copyStart, readIndex + 1);
					dealPackage();
					copyStart = -1;
				}
			}
		}
		
		if(copyStart != -1){
			doCopy(bs, copyStart, readIndex);
		}
	}
	
//	private byte[] getReaded(byte[] last, int readIndex){
//		ByteBuffer buff = ByteBuffer.allocate(byteLength + readIndex);
//		for(int i = 0; i < byteList.size(); i++){
//			buff.put(byteList.get(i));
//		}
//		
//		buff.put(last, 0, readIndex);
//		return buff.array();
//	}
	
	private static final int MAX_PACKAGE_SIZE = 10240; //10K
	
	private void doCopy(byte[] bs, int start, int end) throws IOException{
		if(start == 0 && end == bs.length){
			byteList.add(bs);
			byteLength += bs.length;
		}else{
			byte[] copy = new byte[end - start];
			System.arraycopy(bs, start, copy, 0, copy.length);		
			byteList.add(copy);
			byteLength += copy.length;
		}
		
		if(byteLength > MAX_PACKAGE_SIZE){
			throw new IOException("big package, current package size:" + byteLength);
		}
	}

	@Override
	public byte[] getCachedData() {
		int len = 0;
		for(int i = 0; i < byteList.size(); i++){
			len += byteList.get(i).length;
		}
		
		byte[] data = new byte[len];
		int position = 0;
		for(int i = 0; i < byteList.size(); i++){
			byte[] temp = byteList.get(i);
			System.arraycopy(temp, 0, data, position, temp.length);
			position += temp.length;
		}
		
		return data;
	}
	
	public static void main(String[] args) {
		InputPackageHandler h = null;
		byte b = 0;
		SegFlagStreamDecoder d = new SegFlagStreamDecoder(h, b, b);
		byte[] h1 = {1};
		byte[] h2 = {2, 3};
		byte[] h3 = {4, 5, 6};
		d.byteList.add(h1);
		d.byteList.add(h2);
		d.byteList.add(h3);
		
		byte[] c = d.getCachedData();
		System.out.println(c.length);
		String hex = HexUtil.byteToHexStr(c);
		System.out.println("hex:" + hex);
	}
}