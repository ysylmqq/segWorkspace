package cc.chinagps.gateway.unit.leopaard.upload.bean;

public class LeoparrdRequestFileInfo {
	private String fileName;
	
	private int buffLength;
	
	private int maxPkgs;

	public int getMaxPkgs() {
		return maxPkgs;
	}

	public void setMaxPkgs(int maxPkgs) {
		this.maxPkgs = maxPkgs;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getBuffLength() {
		return buffLength;
	}

	public void setBuffLength(int buffLength) {
		this.buffLength = buffLength;
	}
}