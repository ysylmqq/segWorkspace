package cc.chinagps.gateway.stream.decoders.transition;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import cc.chinagps.gateway.stream.InputStreamDecoder;

public class InnerTransitionDecoder implements InputStreamDecoder{
	private InnerTransitionPackageHandler handler;
	
	private int byteLength = 0;
	private List<byte []> byteList = new ArrayList<byte []>();
	private byte startFlag;
	private byte endFlag;

	private boolean headFinded = false;
	
	public InnerTransitionDecoder(InnerTransitionPackageHandler handler, byte startFlag, byte endFlag){
		this.handler = handler;
		this.startFlag = startFlag;
		this.endFlag = endFlag;
	}
	
	private boolean packageFinded = false;
	
	private void packageFinded(int leftCount) throws Exception{
		packageFinded = true;
		
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
		
		byte[] pkg;
		if(leftCount == 0){
			pkg = data;
		}else{
			int pkgLength = data.length - leftCount;
			pkg = new byte[pkgLength];
			System.arraycopy(data, 0, pkg, 0, pkgLength);
		}
		
		handler.firstPackageReceived(pkg, data);
	}
	
	@Override
	public void handleStream(ByteBuffer buff, int len) throws Exception{
		if(packageFinded){
			throw new IOException("packageFinded!");
		}
		
		byte[] bs = new byte[len];
		buff.get(bs);
		addBytes(bs);
		
		int readIndex;
		for(readIndex = 0;  readIndex < len; readIndex++){
			byte current = bs[readIndex];
			if(!headFinded){
				if(current != startFlag){
					throw new IOException("stx error:" + current);
				}
				
				headFinded = true;
			}else if(current == endFlag){
				packageFinded(len - readIndex - 1);
				break;
			}
		}
	}
	
	private static final int MAX_PACKAGE_SIZE = 10240; //10K
	
	private void addBytes(byte[] data) throws IOException{
		byteList.add(data);
		byteLength += data.length;
		
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
}