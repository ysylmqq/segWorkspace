package cc.chinagps.gateway.aplan.pkg.bodys;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.aplan.pkg.APlanUtil;

public class NetStatusReportBody {
	private int nodeId;
	
	private int nodeType;
	
	private String nodeIP;
	
	public int getNodeId() {
		return nodeId;
	}
	
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	
	public int getNodeType() {
		return nodeType;
	}
	
	public void setNodeType(int nodeType) {
		this.nodeType = nodeType;
	}
	
	public String getNodeIP() {
		return nodeIP;
	}
	
	public void setNodeIP(String nodeIP) {
		this.nodeIP = nodeIP;
	}
	
	public static NetStatusReportBody parseFrom(byte[] data, int offset) throws Exception{
		NetStatusReportBody netStatusReportBody = new NetStatusReportBody();
		
		int position = offset;
		netStatusReportBody.setNodeId(Util.getInt(data, position));
		position += 4;
		netStatusReportBody.setNodeType(Util.getInt(data, position));
		position += 4;
		netStatusReportBody.setNodeIP(APlanUtil.getCString(data, position));
		
		return netStatusReportBody;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("nodeId:").append(nodeId).append(", ");
		sb.append("nodeType:").append(nodeType).append(", ");
		sb.append("nodeIP:").append(nodeIP);
		
		return sb.toString();
	}
	
	public byte[] encode(){
		byte[] nodeIP_data = nodeIP.getBytes();
		byte[] data = new byte[nodeIP_data.length + 9];
		int position = 0;
		//Node_ID
		position = APlanUtil.copyData(Util.getIntByte(nodeId), data, position);
		//Node_Type
		position = APlanUtil.copyData(Util.getIntByte(nodeType), data, position);
		//Node_IP
		APlanUtil.copyData(nodeIP_data, data, position);
		
		return data;
	}
}