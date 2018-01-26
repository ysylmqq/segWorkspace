package cc.chinagps.gateway.aplan.pkg;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import cc.chinagps.gateway.aplan.APlanServer;
import cc.chinagps.gateway.aplan.pkg.bodys.DeliverBody;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.util.Constants;

public class APlanUtil {
	//public static final String EN_CHAR_ENCODING = "ascii";
	
	/**
	 * 获取以\0结束的字符串
	 * 默认为英文字符集
	 * @throws IOException 
	 */
	public static String getCString(byte[] data, int offset) throws IOException{
		int length = data.length - offset;
		return getCString(data, offset, length);
	}
	
	/**
	 * 获取以\0结束的字符串
	 * 默认为英文字符集
	 * 限制最大长度
	 * @throws IOException 
	 */
	public static String getCString(byte[] data, int offset, int length) throws IOException{
		/*
		int index = indexOfZero(data, offset, length);
		if(index == -1){
			return new String(data, offset, length);
		}
		
		try {
			return new String(data, offset, index - offset, EN_CHAR_ENCODING);
		} catch (UnsupportedEncodingException e) {
			return new String(data, offset, index - offset);
		}*/
		return getCString(data, offset, length, Constants.CHAR_ENCODING_ASCII);
	}
	
	/**
	 * 获取以\0结束的字符串
	 */
	public static String getCString(byte[] data, int offset, int length, String encoding) throws IOException{
		int index = indexOfZero(data, offset, length);
		if(index == -1){
			return new String(data, offset, length, encoding);
		}
		
		return new String(data, offset, index - offset, encoding);
	}
	
	public static int indexOfZero(byte[] data, int offset){
		int length = data.length - offset;
		return  indexOfZero(data, offset, length);
	}
	
	public static int indexOfZero(byte[] data, int offset, int length){
		int max = Math.min(offset + length, data.length);
		for(int i = offset; i < max; i++){
			if(data[i] == 0){
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * 把str转换成指定长度的字节数，不足时后面补0
	 * @param str  转换字符串
	 * @param length 指定转换的长度
	 * @return
	 */
	public static byte[] getBytesAddZero(String str,int length){
    	byte bs_str[] = null;
		try {
			bs_str = str.getBytes("GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	byte bs_res[] = new byte[length];

    	if(bs_str.length < length){
    		int count = length-bs_str.length;
        	byte bs_zero[] = new byte[count];
        	for (int i = 0; i < bs_zero.length; i++) {
        		bs_zero[i] = 0;
			}
        	System.arraycopy(bs_str, 0, bs_res, 0, bs_str.length);
        	System.arraycopy(bs_zero, 0, bs_res, bs_str.length, bs_zero.length);
    	}else if(bs_str.length > length){
    		for (int i = 0; i < length; i++) {
    			bs_res[i] = bs_str[i];
			}
    	}
		return bs_res;
	}
	
	public static int copyData(byte[] add, byte[] data, int offset){
		System.arraycopy(add, 0, data, offset, add.length);
		return offset + add.length;
	}
	
	private static int sequenceNo = 0;
	
	public static synchronized int getSequenceNo(){
		return sequenceNo++;
	}
	
	/**
	 * 组装A计划发送数据
	 */
	public static byte[] encodeAPlanDeliverData(byte[] pkg,  APlanServer server, UnitSocketSession session){
		DeliverBody deliverBody = new DeliverBody();
		String call = null;
		if(session.getUnitInfo() != null){
			call = session.getUnitInfo().getCallLetter();
		}
		deliverBody.setCallLetter(call != null? call: "0");
		deliverBody.getMsgs().add(pkg);
		byte[] bodyData = deliverBody.encode();
		
		APlanHead head = new APlanHead();
		head.setCommandId(APlanHead.CMD_ID_DELIVER);
		head.setCommandStatus(APlanHead.COMMU_MODE_GPRS);
		head.setCommandExecute(APlanHead.CMD_EXECUTE_SUBMIT_PTP);
		head.setCommandType(APlanHead.CODE_ENGLISH);
		head.setSequenceNo(APlanUtil.getSequenceNo());
		
		RouteTable rt = new RouteTable();
//		Node node = new Node();
//		node.setNodeType(Node.NODE_INTERFACE);
//		node.setNodeIP("127.0.0.1");
		rt.getNodeList().add(server.getServerNode());
		
		byte[] encode = APlanPackage.encode(head, rt, bodyData);
		return encode;
	}
}