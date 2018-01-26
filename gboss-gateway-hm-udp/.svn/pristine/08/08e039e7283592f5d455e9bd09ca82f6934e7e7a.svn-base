package cc.chinagps.gateway.aplan.pkg.bodys;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.aplan.pkg.APlanUtil;

public class LoginBody {
	private int nodeId;
	
	private int nodeType;
	
	private String user;
	
	private String password;
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

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
	
	public static LoginBody parseFrom(byte[] data, int offset) throws Exception{
		LoginBody loginBody = new LoginBody();
		int position = offset;
		int nodeId = Util.getInt(data, position);
		position += 4;
		int nodeType = Util.getInt(data, position);
		position += 4;
		
		int index_user_end = APlanUtil.indexOfZero(data, position);
		if(index_user_end == -1){
			throw new Exception("search user end fail!");
		}
		
		int length_user = index_user_end - position;
		String user = new String(data, position, length_user);
		
		position += length_user + 1;
		int index_pwd_end = APlanUtil.indexOfZero(data, position);
		if(index_pwd_end == -1){
			throw new Exception("search pwd end fail!");
		}
		int length_pwd = index_pwd_end - position;
		String pwd = new String(data, position, length_pwd);
		
		loginBody.setNodeId(nodeId);
		loginBody.setNodeType(nodeType);
		loginBody.setUser(user);
		loginBody.setPassword(pwd);
		
		return loginBody;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{nodeId:").append(nodeId);
		sb.append(", nodeType:").append(nodeType);
		sb.append(", user:").append(user);
		sb.append(", password:").append(password).append("}");
		
		return sb.toString();
	}
}