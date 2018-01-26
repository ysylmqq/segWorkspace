package cc.chinagps.gateway.stream.decoders;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import cc.chinagps.gateway.stream.InputStreamDecoder;
import cc.chinagps.gateway.stream.InputPackageHandler;
import cc.chinagps.gateway.util.HexUtil;

public class FlagStreamDecoder implements InputStreamDecoder{
	private InputPackageHandler handler;
	
	private int byteLength = 0;
	private List<byte []> byteList = new ArrayList<byte []>();
	private byte startFlag;
	private byte endFlag;

	private boolean headFinded = false;
	
	public FlagStreamDecoder(InputPackageHandler handler, byte startFlag, byte endFlag){
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
	
	@Override
	public void handleStream(ByteBuffer buff, int len) throws Exception{
		byte[] bs = new byte[len];
		buff.get(bs);
		
		int copyStart = 0;
		int readIndex;
		for(readIndex = 0;  readIndex < len; readIndex++){
			byte current = bs[readIndex];
			if(!headFinded){
				if(current != startFlag){
					throw new IOException("stx error:" + current);
				}
				
				headFinded = true;
				copyStart = readIndex;
			}else{
				//if(current == startFlag){
				//	throw new IOException("stx stx!");
				//}
				
				if(current == endFlag){
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
		FlagStreamDecoder d = new FlagStreamDecoder(h, b, b);
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