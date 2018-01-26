package cc.chinagps.gateway.aplan.pkg.bodys;

import java.util.ArrayList;
import java.util.List;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.aplan.pkg.APlanUtil;

public class SubmitBody {
	private String callLetter;

	private byte priority = 1;

	private int currentChannel;
	
	private int newChannel;
	
	private List<byte[]> msgs;

	public List<byte[]> getMsgs() {
		return msgs;
	}

	public void setMsgs(List<byte[]> msgs) {
		this.msgs = msgs;
	}

	public String getCallLetter() {
		return callLetter;
	}

	public void setCallLetter(String callLetter) {
		this.callLetter = callLetter;
	}

	public byte getPriority() {
		return priority;
	}

	public void setPriority(byte priority) {
		this.priority = priority;
	}

	public int getCurrentChannel() {
		return currentChannel;
	}

	public void setCurrentChannel(int currentChannel) {
		this.currentChannel = currentChannel;
	}

	public int getNewChannel() {
		return newChannel;
	}

	public void setNewChannel(int newChannel) {
		this.newChannel = newChannel;
	}
	
	public static SubmitBody parseFrom(byte[] data, int offset) throws Exception{
		SubmitBody submitBody = new SubmitBody();
		
		int index = APlanUtil.indexOfZero(data, offset);
		if(index == -1){
			throw new Exception("search callLetter end fail!");
		}
		
		int position = offset;
		int length = index - position;
		submitBody.setCallLetter(new String(data, offset, length));
		position += length + 1;
		submitBody.setPriority(data[position]);
		position += 1;
		
		submitBody.setCurrentChannel(Util.getInt(data, position));
		position += 4;
		submitBody.setNewChannel(Util.getInt(data, position));
		position += 4;
		byte msgNumber = data[position];
		position += 1;
		
		List<byte[]> msgs = new ArrayList<byte[]>(msgNumber);
		for(int i = 0; i < msgNumber; i++){
			int msg_length = Util.getInt(data, position);
			position += 4;
			byte[] msg_data = new byte[msg_length];
			System.arraycopy(data, position, msg_data, 0, msg_length);
			position += msg_length;
			
			msgs.add(msg_data);
		}
		submitBody.setMsgs(msgs);
		
		
		return submitBody;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("callLetter:").append(callLetter).append(", ");
		sb.append("priority:").append(priority).append(", ");
		sb.append("currentChannel:").append(currentChannel).append(", ");
		sb.append("newChannel:").append(newChannel).append(", ");
		sb.append("msgNumber:").append(msgs == null? 0: msgs.size()).append(", ");
		if(msgs != null){
			sb.append("msgs:[");
			for(int i = 0; i < msgs.size(); i++){
				byte[] m = msgs.get(i);
				sb.append("{");
				sb.append(Util.getHexString(m, " ").toUpperCase());
				sb.append("}");
			}
			sb.append("]");
		}
		
		return sb.toString();
	}
	
	public byte[] encode(){
		int len = 0;
		byte[] callLetter_data = callLetter.getBytes();
		len += callLetter_data.length + 1;
		len += 10;//Priority(1) Current_Channel(4) New_Channel(4) Msg_Num(1)
		for(int i = 0; i < msgs.size(); i++){
			len += 4;
			len += msgs.get(i).length;
		}
		
		//make data
		byte[] data = new byte[len];
		int position = 0;
		//Call_Letter
		position = APlanUtil.copyData(callLetter_data, data, position);
		position += 1;
		//Priority
		data[position] = priority;
		position += 1;
		//Current_Channel
		position = APlanUtil.copyData(Util.getIntByte(currentChannel), data, position);
		//New_Channel
		position = APlanUtil.copyData(Util.getIntByte(newChannel), data, position);
		//Msg_Num
		data[position] = (byte) msgs.size();
		position += 1;
		//Msgs
		for(int i = 0; i < msgs.size(); i++){
			byte[] msg = msgs.get(i);
			position = APlanUtil.copyData(Util.getIntByte(msg.length), data, position);
			position = APlanUtil.copyData(msg, data, position);
		}
		
		return data;
	}
}