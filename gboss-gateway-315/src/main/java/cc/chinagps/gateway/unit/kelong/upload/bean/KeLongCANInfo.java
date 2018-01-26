package cc.chinagps.gateway.unit.kelong.upload.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import cc.chinagps.gateway.StartServer;
import cc.chinagps.gateway.unit.kelong.pkg.KeLongPackage;
import cc.chinagps.gateway.unit.kelong.util.KeLongTimeFormatUtil;
import cc.chinagps.gateway.util.HexUtil;
import cc.chinagps.gateway.util.Util;
/**
 * 
* @ClassName: KeLongCANInfo
* @Description: CAN信息
* @author dy
* @date 2017年5月23日 上午9:07:37
*
 */
public class KeLongCANInfo {
	
	private boolean isHistory;
	
	private long canTime;
	
	private String canTimeStr;
	
	private int remainingFuel;	

	public boolean isHistory() {
		return isHistory;
	}

	public void setHistory(boolean isHistory) {
		this.isHistory = isHistory;
	}

	public long getCanTime() {
		return canTime;
	}

	public void setCanTime(long canTime) {
		this.canTime = canTime;
	}

	public String getCanTimeStr() {
		return canTimeStr;
	}

	public void setCanTimeStr(String canTimeStr) {
		this.canTimeStr = canTimeStr;
	}

	public int getRemainingFuel() {
		return remainingFuel;
	}

	public void setRemainingFuel(int remainingFuel) {
		this.remainingFuel = remainingFuel;
	}

	public static KeLongCANInfo parse(byte[] data, int position,Integer capacity) throws Exception {
		KeLongCANInfo keLongCANInfo = new KeLongCANInfo();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String deviceTimeStr = "20" + cc.chinagps.gateway.util.Util.getDateTime(data, position, 6);
		Date deviceTime = KeLongTimeFormatUtil.parseGMT0(deviceTimeStr);
		if ((new Date().getTime() - deviceTime.getTime()) > StartServer.isHistoryTime * 1000) {
			keLongCANInfo.setHistory(true);
		}
		keLongCANInfo.setCanTime(deviceTime.getTime());
		keLongCANInfo.setCanTimeStr(sdf.format(deviceTime));
		position += 6;        
		//2数据流掩码，暂时没解析
		position +=5;		
		//3协议类型，暂时没解析
		position += 2;
		//4点火状态
		position++;
		//5ACC状态
		position++;
		//6电瓶电压
		position += 2;
		//7，暂时没解析
		position ++;
		//8，暂时没解析
		position ++;
		//9，暂时没解析
		position += 2;
		//10，暂时没解析
		position ++;
		//11，暂时没解析
		position += 4;
		//12，暂时没解析
		position ++;
		//13，暂时没解析
		position += 4;
		//14，暂时没解析
		position += 4;
		//15，暂时没解析
		position += 4;
		//16剩余油量，单位 L 或% bit15 ==0 百分比% OBD 都为百分比 bit15 ==1 单位 L 显示值为上报值/10		
		
		int fuel=Util.getShort(data,position);
		//转为2进制
		String fuelto2 = Integer.toBinaryString(fuel);
		int zeroLen = 16 - fuelto2.length();
		StringBuffer bf = new StringBuffer();
		for (int i = 0; i < zeroLen; i++) {
			bf.append("0");
		}
		fuelto2 = bf.toString() + fuelto2;
		
		if(fuelto2!=null&&fuelto2!=""){
			//去掉bit15类型标示位
			fuelto2 = fuelto2.substring(1);
			//处理后的2进制字符转int
			fuel = Integer.parseInt(fuelto2, 2);
			if((data[position] & 0x80)!=0){
				//bit15 ==1
				keLongCANInfo.setRemainingFuel(fuel*10);
			}else{
				//bit15 ==0
			    keLongCANInfo.setRemainingFuel((fuel/10)*capacity);	
			}
		}
			
		return keLongCANInfo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("keLongCANInfo [isHistory=");
		builder.append(isHistory);
		builder.append(", canTime=");
		builder.append(canTime);
		builder.append(", canTimeStr=");
		builder.append(canTimeStr);		
		builder.append(", remainingFuel=");
		builder.append(remainingFuel);
		builder.append("]");
		return builder.toString();
	}
	
}
