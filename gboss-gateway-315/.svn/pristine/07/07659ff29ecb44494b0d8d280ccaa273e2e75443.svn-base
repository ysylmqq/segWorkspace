package cc.chinagps.gateway.aplan.pkg.bodys;

import org.seg.lib.util.Util;

public class LoginAckBody {
	private int nodeId;
	
	private int nodeType;
	
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

	public byte[] encode(){
		byte[] data = new byte[8];
		byte[] nodeIdBs = Util.getIntByte(nodeId);
		byte[] nodeTypeBs = Util.getIntByte(nodeType);
		
		System.arraycopy(nodeIdBs, 0, data, 0, 4);
		System.arraycopy(nodeTypeBs, 0, data, 4, 4);
		
		return data;
	}
}
