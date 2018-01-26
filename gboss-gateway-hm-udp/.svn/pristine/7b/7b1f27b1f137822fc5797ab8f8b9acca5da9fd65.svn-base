package cc.chinagps.gateway.stream.decoders.transition;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import cc.chinagps.gateway.stream.InputStreamDecoder;

public class FirstDataTransitionDecoder implements InputStreamDecoder{
	private FirstDataTransitionPackageHandler handler;
	
	private int firstN;
	
	private int byteLength = 0;
	private List<byte []> byteList = new ArrayList<byte []>();
	
	public FirstDataTransitionDecoder(FirstDataTransitionPackageHandler handler, int firstN){
		this.handler = handler;
		this.firstN = firstN;
	}
	
	private boolean packageFinded = false;
	
	private void packageFinded() throws Exception{
		byte[] data = getCachedData();
		handler.firstPackageReceived(data);
	}
	
	@Override
	public void handleStream(ByteBuffer buff, int len) throws Exception {
		if(packageFinded){
			throw new IOException("packageFinded!");
		}
		
		byte[] bs = new byte[len];
		buff.get(bs);
		addBytes(bs);
		
		if(byteLength >= firstN){
			packageFinded();
		}
	}

	@Override
	public byte[] getCachedData() {
		byte[] data = new byte[byteLength];
		int position = 0;
		for(int i = 0; i < byteList.size(); i++){
			byte[] temp = byteList.get(i);
			System.arraycopy(temp, 0, data, position, temp.length);
			position += temp.length;
		}
		
		return data;
	}
	
	private static final int MAX_PACKAGE_SIZE = 10240; //10K
	
	private void addBytes(byte[] data) throws IOException{
		byteList.add(data);
		byteLength += data.length;
		
		if(byteLength > MAX_PACKAGE_SIZE){
			throw new IOException("big package, current package size:" + byteLength);
		}
	}
}
