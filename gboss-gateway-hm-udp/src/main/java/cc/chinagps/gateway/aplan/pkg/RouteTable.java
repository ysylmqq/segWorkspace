package cc.chinagps.gateway.aplan.pkg;

import java.util.ArrayList;
import java.util.List;

public class RouteTable {
	public static class Node{
		public static final int NODE_INTERFACE = 1;
		public static final int NODE_COMMUNICATION = 2;
		public static final int NODE_SEAT = 3;
		public static final int NODE_CALL_CENTER = 4;
		public static final int NDE_DATA_CENTER = 5;
		public static final int NODE_BUND = 6;
		
		private int nodeType = 1;
		
		private int nodeId;
		
		private String nodeIP;
		
		public int getNodeType() {
			return nodeType;
		}
		public void setNodeType(int nodeType) {
			this.nodeType = nodeType;
		}
		
		public int getNodeId() {
			return nodeId;
		}
		
		public void setNodeId(int nodeId) {
			this.nodeId = nodeId;
		}
		
		public String getNodeIP() {
			return nodeIP;
		}
		
		public void setNodeIP(String nodeIP) {
			this.nodeIP = nodeIP;
		}
	}

	private int currentPointer;
	
	private List<Node> nodeList = new ArrayList<Node>();
	
	public int getCurrentPointer() {
		return currentPointer;
	}

	public void setCurrentPointer(int currentPointer) {
		this.currentPointer = currentPointer;
	}

	public List<Node> getNodeList() {
		return nodeList;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("routeSize:").append(nodeList.size()).append(", ");
		sb.append("currentPointer:").append(currentPointer);
		sb.append(", nodes:[");
		for(int i = 0; i < nodeList.size(); i++){
			Node n = nodeList.get(i);
			sb.append("{");
			sb.append("nodeType:").append(n.getNodeType()).append(", ");
			sb.append("nodeId:").append(n.getNodeId()).append(", ");
			sb.append("nodeIP:").append(n.getNodeIP());
			sb.append("}");
		}
		sb.append("]");
		
		return sb.toString();
	}
}