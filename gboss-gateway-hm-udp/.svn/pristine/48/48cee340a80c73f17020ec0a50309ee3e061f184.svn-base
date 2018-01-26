package cc.chinagps.gateway.unit.pengaoda.pkg;

import cc.chinagps.gateway.unit.pengaoda.util.PengAoDaPkgUtil;

public class PengAoDaAck {
	private String terminalId;
	
	private String param;
	
	private String time;

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public byte[] encode() throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("*HQ,");
		sb.append(terminalId).append(",");
		sb.append("V4").append(",");
		sb.append(param).append(",");
		sb.append(time).append("#");
		
		return sb.toString().getBytes(PengAoDaPkgUtil.EN_CHAR_ENCODING);
	}
}