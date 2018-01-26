package cc.chinagps.gateway.unit.seg.upload.beans;

public class SegBaseStationInfo {
	private int cellId;

	private int lac;

	private int mcc;

	private int mnc;

	private int dbm;

	public SegBaseStationInfo() {

	}

	public SegBaseStationInfo(int mcc, int mnc) {
		this.mcc = mcc;
		this.mnc = mnc;
	}

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

	public int getDbm() {
		return dbm;
	}

	public void setDbm(int dbm) {
		this.dbm = dbm;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{cellId:").append(cellId);
		sb.append(", lac:").append(lac);
		sb.append(", mcc:").append(mcc);
		sb.append(", mnc:").append(mnc);
		sb.append(", dbm:").append(dbm);
		sb.append("}");

		return sb.toString();
	}
}
