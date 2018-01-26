package cc.chinagps.gateway.unit.beans;

public class BaseStationInfo {
	//mcc 移动国家代码（中国为460） 
	private String mcc;
	
	//mnc 移动网络号码（中国移动为0，中国联通为1，中国电信为2）
	private String mnc;
	
	//lac 位置区域码 
	private int lac;
	
	//cid 基站编号 
	private int cid;
	
	//bsss 基站信号强度 
	private int bsss;

	public String getMcc() {
		return mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	public String getMnc() {
		return mnc;
	}

	public void setMnc(String mnc) {
		this.mnc = mnc;
	}

	public int getLac() {
		return lac;
	}

	public void setLac(int lac) {
		this.lac = lac;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getBsss() {
		return bsss;
	}

	public void setBsss(int bsss) {
		this.bsss = bsss;
	}

	@Override
	public String toString() {
		return "BaseStationInfo [mcc=" + mcc + ", mnc=" + mnc + ", lac=" + lac + ", cid=" + cid + ", bsss=" + bsss
				+ "]";
	}
	
}