package cc.chinagps.gateway.aplan.pkg.bodys;

import java.util.ArrayList;
import java.util.List;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.aplan.pkg.APlanUtil;

public class DeliverBody {
	private String callLetter;
	
	private byte priority = 1;
	
	private int currentChannel;
	
	private int newChannel;
	
	private List<byte[]> msgs = new ArrayList<byte[]>();
	
	private String dllName;
	
	private String unitType;

	private List<String> addInfoList = new ArrayList<String>();

	public List<byte[]> getMsgs() {
		return msgs;
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

	public String getDllName() {
		return dllName;
	}

	public void setDllName(String dllName) {
		this.dllName = dllName;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public List<String> getAddInfoList() {
		return addInfoList;
	}

	//public void setAddInfoList(List<String> addInfoList) {
	//	this.addInfoList = addInfoList;
	//}
	
	public static DeliverBody parseFrom(byte[] data, int offset) throws Exception{
		DeliverBody deliverBody = new DeliverBody();
		
		int index = APlanUtil.indexOfZero(data, offset);
		if(index == -1){
			throw new Exception("search callLetter end fail!");
		}
		
		int position = offset;
		int length = index - position;
		deliverBody.setCallLetter(new String(data, offset, length));
		position += length + 1;
		deliverBody.setPriority(data[position]);
		position += 1;
		
		deliverBody.setCurrentChannel(Util.getInt(data, position));
		position += 4;
		deliverBody.setNewChannel(Util.getInt(data, position));
		position += 4;
		byte msgNumber = data[position];
		position += 1;
		
		List<byte[]> msgs	= deliverBody.getMsgs();
		for(int i = 0; i < msgNumber; i++){
			int msg_length = Util.getInt(data, position);
			position += 4;
			byte[] msg_data = new byte[msg_length];
			System.arraycopy(data, position, msg_data, 0, msg_length);
			position += msg_length;
			
			msgs.add(msg_data);
		}
		
		byte dllName_length = data[position];
		position += 1;
		deliverBody.setDllName(new String(data, position, dllName_length));
		position += dllName_length;
		
		byte unitType_length = data[position];
		position += 1;
		deliverBody.setUnitType(new String(data, position, unitType_length));
		position += unitType_length;
		
		byte addInfoNum = data[position];
		position += 1;
		
		//List<String> addInfos = new ArrayList<String>(addInfoNum);
		for(int i = 0; i < addInfoNum; i++){
			int addInfo_length = Util.getInt(data, position);
			position += 4;
			
			//addInfos.add(new String(data, position, addInfo_length));
			deliverBody.getAddInfoList().add(new String(data, position, addInfo_length));
			position += addInfo_length;
		}
		
		//deliverBody.setAddInfoList(addInfos);
		
		return deliverBody;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("callLetter:").append(callLetter).append(", ");
		sb.append("priority:").append(priority).append(", ");
		sb.append("currentChannel:").append(currentChannel).append(", ");
		sb.append("newChannel:").append(newChannel).append(", ");
		sb.append("msgNumber:").append(msgs.size()).append(", ");
		sb.append("msgs:[");
		for(int i = 0; i < msgs.size(); i++){
			byte[] m = msgs.get(i);
			sb.append("{");
			sb.append(Util.getHexString(m, " ").toUpperCase());
			sb.append("}");
		}
		sb.append("], ");
		sb.append("dllName:").append(dllName).append(", ");
		sb.append("unitType:").append(unitType).append(", ");
		sb.append("addInfoNumber").append(addInfoList == null? 0: addInfoList.size()).append(", ");
		if(addInfoList != null){
			sb.append(", addInfos:[");
			for(int i = 0; i < addInfoList.size(); i++){
				String n = addInfoList.get(i);
				sb.append("{");
				sb.append(n);
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
		
		//Dll_Name_length
		len += 1;
		byte[] dllName_data = null;
		if(dllName != null){
			dllName_data = dllName.getBytes();
			len += dllName_data.length;
		}
		
		//Unit_Type_length
		len += 1;
		byte[] unitType_data = null;
		if(unitType != null){
			unitType_data = unitType.getBytes();
			len += unitType_data.length;
		}
		
		//AddedInfo_Num
		len += 1;
		for(int i = 0; i < addInfoList.size(); i++){
			len += 4;
			len += addInfoList.get(i).getBytes().length;
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
		//Dll_Name
		if(dllName != null){
			data[position] = (byte) dllName_data.length;
			position += 1;
			position = APlanUtil.copyData(dllName_data, data, position);
		}else{
			data[position] = 0;
			position += 1;
		}
		
		//Unit_Type
		if(unitType != null){
			data[position] = (byte) unitType_data.length;
			position += 1;
			position = APlanUtil.copyData(unitType_data, data, position);
		}else{
			data[position] = 0;
			position += 1;
		}
		
		//AddedInfo
		data[position] = (byte) addInfoList.size();
		position += 1;
		for(int i = 0; i < addInfoList.size(); i++){
			byte[] addInfo_data = addInfoList.get(i).getBytes();
			position = APlanUtil.copyData(Util.getIntByte(addInfo_data.length), data, position);
			position = APlanUtil.copyData(addInfo_data, data, position);
		}
		
		return data;
	}
}