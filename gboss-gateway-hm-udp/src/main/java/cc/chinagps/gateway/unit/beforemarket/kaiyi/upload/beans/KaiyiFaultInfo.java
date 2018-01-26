package cc.chinagps.gateway.unit.beforemarket.kaiyi.upload.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.aplan.pkg.APlanUtil;

public class KaiyiFaultInfo {
	//数据长度(字节)
	private int dataLength;

	private List<String> faults = new ArrayList<String>();

	public int getDataLength() {
		return dataLength;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}
	
	public List<String> getFaults() {
		return faults;
	}

	public void setFaults(List<String> faults) {
		this.faults = faults;
	}

	public static KaiyiFaultInfo parse(byte[] data, int offset) throws IOException{
		KaiyiFaultInfo faultInfo = new KaiyiFaultInfo();
		int count = Util.getShort(data, offset) & 0xFFFF;
		int position = offset + 2;
		
		for(int i = 0; i < count; i++){
			String str = APlanUtil.getCString(data, position, 8);
			faultInfo.getFaults().add(str);
			
			position += 8;
		}
		
		faultInfo.setDataLength(8 * count + 2);
		return faultInfo;
	}
}