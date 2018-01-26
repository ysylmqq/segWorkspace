package cc.chinagps.gateway.unit.beforemarket.hm.upload.beans;

public class HMECUConfig {
	private int dataLength;
	
	private byte[] configs;

	//flag1
	private boolean checkEnabled;

	//flag2
	private boolean hasABS;
	
	private boolean hasESP;
	
	private boolean hasPEPS;
	
	private boolean hasTPMS;
	
	private boolean hasSRS;
	
	private boolean hasEPS;
	
	private boolean hasEMS;
	
	private boolean hasIMMO;
	
	//flag3
	private boolean hasBCM;
	
	private boolean hasTCU;
	
	private boolean hasICM;
	
	private boolean hasAPM;

	public int getDataLength() {
		return dataLength;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}
	
	public byte[] getConfigs() {
		return configs;
	}

	public void setConfigs(byte[] configs) {
		this.configs = configs;
	}
	
	public boolean isCheckEnabled() {
		return checkEnabled;
	}

	public void setCheckEnabled(boolean checkEnabled) {
		this.checkEnabled = checkEnabled;
	}
	
	public boolean isHasABS() {
		return hasABS;
	}

	public void setHasABS(boolean hasABS) {
		this.hasABS = hasABS;
	}

	public boolean isHasESP() {
		return hasESP;
	}

	public void setHasESP(boolean hasESP) {
		this.hasESP = hasESP;
	}

	public boolean isHasPEPS() {
		return hasPEPS;
	}

	public void setHasPEPS(boolean hasPEPS) {
		this.hasPEPS = hasPEPS;
	}

	public boolean isHasTPMS() {
		return hasTPMS;
	}

	public void setHasTPMS(boolean hasTPMS) {
		this.hasTPMS = hasTPMS;
	}

	public boolean isHasSRS() {
		return hasSRS;
	}

	public void setHasSRS(boolean hasSRS) {
		this.hasSRS = hasSRS;
	}

	public boolean isHasEPS() {
		return hasEPS;
	}

	public void setHasEPS(boolean hasEPS) {
		this.hasEPS = hasEPS;
	}

	public boolean isHasEMS() {
		return hasEMS;
	}

	public void setHasEMS(boolean hasEMS) {
		this.hasEMS = hasEMS;
	}

	public boolean isHasIMMO() {
		return hasIMMO;
	}

	public void setHasIMMO(boolean hasIMMO) {
		this.hasIMMO = hasIMMO;
	}

	public boolean isHasBCM() {
		return hasBCM;
	}

	public void setHasBCM(boolean hasBCM) {
		this.hasBCM = hasBCM;
	}

	public boolean isHasTCU() {
		return hasTCU;
	}

	public void setHasTCU(boolean hasTCU) {
		this.hasTCU = hasTCU;
	}

	public boolean isHasICM() {
		return hasICM;
	}

	public void setHasICM(boolean hasICM) {
		this.hasICM = hasICM;
	}

	public boolean isHasAPM() {
		return hasAPM;
	}

	public void setHasAPM(boolean hasAPM) {
		this.hasAPM = hasAPM;
	}
	
	public static HMECUConfig parse(byte[] data, int offset){
		int position = offset;
		int dataLength = data[position] & 0xFF;

		byte config1 = dataLength >= 1? data[position + 1]: 0;
		byte config2 = dataLength >= 2? data[position + 2]: 0;
		byte config3 = dataLength >= 3? data[position + 3]: 0;
		//byte config4 = dataLength >= 4? data[position + 4]: 0;
		
		byte[] cfgs = new byte[dataLength - 1];
		System.arraycopy(data, position + 1, cfgs, 0, cfgs.length);
		
		//config1
		boolean checkEnabled = (config1 & 0x1) != 0;
		//config2
		boolean hasABS = (config2 & 0x80) != 0;
		boolean hasESP = (config2 & 0x40) != 0;
		boolean hasPEPS = (config2 & 0x20) != 0;
		boolean hasTPMS = (config2 & 0x10) != 0;
		boolean hasSRS = (config2 & 0x8) != 0;
		boolean hasEPS = (config2 & 0x4) != 0;
		boolean hasEMS = (config2 & 0x2) != 0;
		boolean hasIMMO = (config2 & 0x1) != 0;
		//config3
		boolean hasBCM = (config3 & 0x80) != 0;
		boolean hasTCU = (config3 & 0x40) != 0;
		boolean hasICM = (config3 & 0x20) != 0;
		boolean hasAPM = (config3 & 0x10) != 0;
		
		HMECUConfig ecuConfig = new HMECUConfig();
		ecuConfig.setDataLength(dataLength);
		ecuConfig.setConfigs(cfgs);
		ecuConfig.setCheckEnabled(checkEnabled);
		ecuConfig.setHasABS(hasABS);
		ecuConfig.setHasESP(hasESP);
		ecuConfig.setHasPEPS(hasPEPS);
		ecuConfig.setHasTPMS(hasTPMS);
		ecuConfig.setHasSRS(hasSRS);
		ecuConfig.setHasEPS(hasEPS);
		ecuConfig.setHasEMS(hasEMS);
		ecuConfig.setHasIMMO(hasIMMO);
		ecuConfig.setHasBCM(hasBCM);
		ecuConfig.setHasTCU(hasTCU);
		ecuConfig.setHasICM(hasICM);
		ecuConfig.setHasAPM(hasAPM);
		
		return ecuConfig;
	}
}