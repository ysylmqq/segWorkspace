package cc.chinagps.gateway.unit.beforemarket.hm.upload.beans;

public class RequestFileInfo {
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestFileInfo [fileName=");
		builder.append(fileName);
		builder.append(", buffLength=");
		builder.append(buffLength);
		builder.append(", maxPkgs=");
		builder.append(maxPkgs);
		builder.append("]");
		return builder.toString();
	}
}