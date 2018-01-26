package cc.chinagps.gateway.unit.kelong.upload.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import cc.chinagps.gateway.unit.kelong.util.KeLongTimeFormatUtil;
import cc.chinagps.gateway.util.Util;

/**
 * 
* @ClassName: KeLongBaseStationInfo
* @Description: 基站数据
* @author dy
* @date 2017年5月23日 下午2:01:13
*
 */
public class KeLongBaseStationInfo {
	// 1时间信息（年-月-日，时-分-秒）
	private long baseStationTime;
	private String baseStationTimeStr;
	private static int mcc = 460;
	
	private static int dbm = 0;//信号强度
	// 2 运营商：1-移动，2-联通，3-电信
	private byte operator;
	//服务器 LAC
	private int serverLAC;
	//服务器 CellID
	private int serverCellID;
	private int n1LAC;
	private int n1CellID;
	private int n2LAC;
	private int n2CellID;
	private int n3LAC;
	private int n3CellID;
	private int n4LAC;
	private int n4CellID;
	private int n5LAC;
	private int n5CellID;
	private int n6LAC;
	private int n6CellID;
	private int dataLen;//包的字节长度
	
	public static int getDbm() {
		return dbm;
	}

	public static void setDbm(int dbm) {
		KeLongBaseStationInfo.dbm = dbm;
	}

	public static int getMcc() {
		return mcc;
	}

	public static void setMcc(int mcc) {
		KeLongBaseStationInfo.mcc = mcc;
	}

	public long getBaseStationTime() {
		return baseStationTime;
	}

	public void setBaseStationTime(long baseStationTime) {
		this.baseStationTime = baseStationTime;
	}

	public String getBaseStationTimeStr() {
		return baseStationTimeStr;
	}

	public void setBaseStationTimeStr(String baseStationTimeStr) {
		this.baseStationTimeStr = baseStationTimeStr;
	}

	public byte getOperator() {
		return operator;
	}

	public void setOperator(byte operator) {
		this.operator = operator;
	}

	public int getServerLAC() {
		return serverLAC;
	}

	public void setServerLAC(int serverLAC) {
		this.serverLAC = serverLAC;
	}

	public int getServerCellID() {
		return serverCellID;
	}

	public void setServerCellID(int serverCellID) {
		this.serverCellID = serverCellID;
	}

	public int getN1LAC() {
		return n1LAC;
	}

	public void setN1LAC(int n1lac) {
		n1LAC = n1lac;
	}

	public int getN1CellID() {
		return n1CellID;
	}

	public void setN1CellID(int n1CellID) {
		this.n1CellID = n1CellID;
	}

	public int getN2LAC() {
		return n2LAC;
	}

	public void setN2LAC(int n2lac) {
		n2LAC = n2lac;
	}

	public int getN2CellID() {
		return n2CellID;
	}

	public void setN2CellID(int n2CellID) {
		this.n2CellID = n2CellID;
	}

	public int getN3LAC() {
		return n3LAC;
	}

	public void setN3LAC(int n3lac) {
		n3LAC = n3lac;
	}

	public int getN3CellID() {
		return n3CellID;
	}

	public void setN3CellID(int n3CellID) {
		this.n3CellID = n3CellID;
	}

	public int getN4LAC() {
		return n4LAC;
	}

	public void setN4LAC(int n4lac) {
		n4LAC = n4lac;
	}

	public int getN4CellID() {
		return n4CellID;
	}

	public void setN4CellID(int n4CellID) {
		this.n4CellID = n4CellID;
	}

	public int getN5LAC() {
		return n5LAC;
	}

	public void setN5LAC(int n5lac) {
		n5LAC = n5lac;
	}

	public int getN5CellID() {
		return n5CellID;
	}

	public void setN5CellID(int n5CellID) {
		this.n5CellID = n5CellID;
	}

	public int getN6LAC() {
		return n6LAC;
	}

	public void setN6LAC(int n6lac) {
		n6LAC = n6lac;
	}

	public int getN6CellID() {
		return n6CellID;
	}

	public void setN6CellID(int n6CellID) {
		this.n6CellID = n6CellID;
	}

	public int getDataLen() {
		return dataLen;
	}

	public void setDataLen(int dataLen) {
		this.dataLen = dataLen;
	}
	
	/**
	 * @todo:解析基站包;
	 * @author:cj
	 * @param:
	 * @return:
	 * @remark:
	 */
	public static KeLongBaseStationInfo parse(byte[] data, int position) throws Exception {
		int startPos = position;
		KeLongBaseStationInfo baseStaion = new KeLongBaseStationInfo();
		//时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String deviceTimeStr = "20" + cc.chinagps.gateway.util.Util.getDateTime(data, position, 6);
		Date deviceTime = KeLongTimeFormatUtil.parseGMT0(deviceTimeStr);		
		baseStaion.setBaseStationTime(deviceTime.getTime());
		baseStaion.setBaseStationTimeStr(sdf.format(deviceTime));		
		//mnc
		position += 6;
		byte operator = data[position];
		baseStaion.setOperator((byte)(operator-1));//注意:科隆协议里的运营商编号是:1-移动；2-联通；3-电信；但是标准是:0-移动；1-联通；2-电信；
		position += 1;
		//server lac
		int serverLAC= Util.getShort(data, position) & 0xFFFF;
		baseStaion.setServerLAC(serverLAC);
		position += 2;
		//server  cellid
		int serverCellID = Util.getShort(data, position) & 0xFFFF;
		baseStaion.setServerCellID(serverCellID);
		position += 2;
		
		int n1LAC= Util.getShort(data, position) & 0xFFFF;
		baseStaion.setN1CellID(n1LAC);
		position += 2;
		int n1CellID= Util.getShort(data, position) & 0xFFFF;
		baseStaion.setN1CellID(n1CellID);
		
		position += 2;
		int n2LAC= Util.getShort(data, position) & 0xFFFF;
		baseStaion.setN2LAC(n2LAC);
		position += 2;
		int n2CellID= Util.getShort(data, position) & 0xFFFF;
		baseStaion.setN2CellID(n2CellID);
		
		position += 2;
		int n3LAC= Util.getShort(data, position) & 0xFFFF;
		baseStaion.setN3LAC(n3LAC);
		position += 2;
		int n3CellID= Util.getShort(data, position) & 0xFFFF;
		baseStaion.setN3CellID(n3CellID);
		
		position += 2;
		int n4LAC= Util.getShort(data, position) & 0xFFFF;
		baseStaion.setN4LAC(n4LAC);
		position += 2;
		int n4CellID= Util.getShort(data, position) & 0xFFFF;
		baseStaion.setN4CellID(n4CellID);
		
		position += 2;
		int n5LAC= Util.getShort(data, position) & 0xFFFF;
		baseStaion.setN5LAC(n5LAC);
		position += 2;
		int n5CellID= Util.getShort(data, position) & 0xFFFF;
		baseStaion.setN5CellID(n5CellID);
		
		position += 2;
		int n6LAC= Util.getShort(data, position) & 0xFFFF;
		baseStaion.setN6LAC(n6LAC);
		position += 2;
		int n6CellID= Util.getShort(data, position) & 0xFFFF;
		baseStaion.setN6CellID(n6CellID);						
		position += 2;
		
		baseStaion.setDataLen(position - startPos);//设置包的长度,35字节
		return baseStaion;
	}

	@Override
	public String toString() {
		return "KeLongBaseStationInfo [baseStationTime=" + baseStationTime + ", baseStationTimeStr="
				+ baseStationTimeStr + ", operator=" + operator + ", serverLAC=" + serverLAC + ", serverCellID="
				+ serverCellID + ", n1LAC=" + n1LAC + ", n1CellID=" + n1CellID + ", n2LAC=" + n2LAC + ", n2CellID="
				+ n2CellID + ", n3LAC=" + n3LAC + ", n3CellID=" + n3CellID + ", n4LAC=" + n4LAC + ", n4CellID="
				+ n4CellID + ", n5LAC=" + n5LAC + ", n5CellID=" + n5CellID + ", n6LAC=" + n6LAC + ", n6CellID="
				+ n6CellID + ", dataLen=" + dataLen + "]";
	}
}
