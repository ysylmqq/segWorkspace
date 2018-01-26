package cc.chinagps.gateway.unit.kelx.upload.beans;

import org.seg.lib.util.Util;

public class KlxBaseStation {
	private int cellId;
	
	private int lac;
	
	private int mcc;
	
	private int mnc;

	public int getCellId() {
		return cellId;
	}

	public void setCellId(int cellId) {
		this.cellId = cellId;
	}

	public int getLac() {
		return lac;
	}

	public void setLac(int lac) {
		this.lac = lac;
	}

	public int getMcc() {
		return mcc;
	}

	public void setMcc(int mcc) {
		this.mcc = mcc;
	}

	public int getMnc() {
		return mnc;
	}

	public void setMnc(int mnc) {
		this.mnc = mnc;
	}
	
	public static KlxBaseStation parse(byte[] data, int offset){
		int position = offset;
		
		byte[] cellId_bytes = new byte[4];
		System.arraycopy(data, position, cellId_bytes, 1, 3);
		int cellId = Util.getInt(cellId_bytes);
		position += 3;
		
		int lac = Util.getShort(data, position) & 0xFFFF;
		position += 2;
		
		int mcc = Util.getShort(data, position) & 0xFFFF;
		position += 2;
		
		int mnc = data[position] & 0xFF;
		
		KlxBaseStation baseStation = new KlxBaseStation();
		baseStation.setCellId(cellId);
		baseStation.setLac(lac);
		baseStation.setMcc(mcc);
		baseStation.setMnc(mnc);
		
		return baseStation;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{cellId:").append(cellId);
		sb.append(", lac:").append(lac);
		sb.append(", mcc:").append(mcc);
		sb.append(", mnc:").append(mnc);
		sb.append("}");
		
		return sb.toString();
	}
}