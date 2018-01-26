package cc.chinagps.gateway.aplan.pkg.bodys;

import cc.chinagps.gateway.aplan.pkg.APlanUtil;

public class SubmitAckBody {
	private String callLetter;

	public String getCallLetter() {
		return callLetter;
	}

	public void setCallLetter(String callLetter) {
		this.callLetter = callLetter;
	}
	
	public static SubmitAckBody parseFrom(byte[] data, int offset) throws Exception{
		SubmitAckBody submitAckBody = new SubmitAckBody();
		
		int index = APlanUtil.indexOfZero(data, offset);
		if(index == -1){
			throw new Exception("search callLetter end fail!");
		}
		
		int position = offset;
		int length = index - position;
		submitAckBody.setCallLetter(new String(data, offset, length));
		
		return submitAckBody;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("callLetter:").append(callLetter);
		return sb.toString();
	}
	
	public byte[] encode(){
		byte[] callLetter_data = callLetter.getBytes();
		byte[] data = new byte[callLetter_data.length + 1];
		System.arraycopy(callLetter_data, 0, data, 0, callLetter_data.length);
		
		return data;
	}
}