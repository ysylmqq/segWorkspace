package cc.chinagps.gateway.unit.kelx.util;

import java.util.ArrayList;
import java.util.List;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.kelx.pkg.KlxAck;
import cc.chinagps.gateway.unit.kelx.pkg.KlxPackage;
import cc.chinagps.gateway.util.HexUtil;

public class KlxPkgUtil {
	public static final String EN_CHAR_ENCODING = "ascii";
	
	public static final byte START_FLAG = (byte) 0xAA;
	
	private static final byte FLAG_A = START_FLAG;
	private static final byte FLAG_B = 0x55;

	private static final byte ESCAPE_CODE_A = 0x1;
	private static final byte ESCAPE_CODE_B = 0x2;
	
	private static boolean isEscapeCode(byte c){
		return (c == ESCAPE_CODE_A || c == ESCAPE_CODE_B);
	}
	
	public static void main(String[] args) {
		byte[] data = {(byte) 0x51, (byte) 0x52, (byte) 0x53, (byte) 0x54, (byte) 0x55, 
				(byte) 0xA8, (byte) 0xA9, (byte) 0xAA, (byte) 0xAB, (byte) 0xAC};
		byte[] es = escape(data);
		
		byte[] to_unes = new byte[es.length + 2];
		System.arraycopy(es, 0, to_unes, 1, es.length);
		to_unes[0] = (byte) 0xAA;
		to_unes[to_unes.length - 1] = (byte) 0xAA;
		
		byte[] unes = unescape(to_unes);
		
		System.out.println(HexUtil.byteToHexStr(es));
		System.out.println(HexUtil.byteToHexStr(unes));
	}
	
	/**
	 * 反转义
	 */
	public static byte[] unescape(byte[] data){
		List<Integer> positions = new ArrayList<Integer>();
		for(int i = 1; i < data.length - 1; i++){
			if(data[i] == FLAG_B && isEscapeCode(data[i + 1])){
				positions.add(i);
			}
		}
		
		if(positions.size() == 0){
			return data;
		}
		
		byte[] copy = new byte[data.length - positions.size()];
		
		int srcPos = 0;
		int tarPos = 0;
		int copyLength;
		for(int i = 0; i < positions.size(); i++){
			int uespos = positions.get(i);
			copyLength = uespos - srcPos;
			System.arraycopy(data, srcPos, copy, tarPos, copyLength);
			
			srcPos = uespos + 2;
			tarPos += copyLength;
			//copy[tarPos] = (byte) (MASK_BYTE ^ data[uespos + 1]);
			byte uedata = data[uespos + 1];
			byte repdata = (uedata == ESCAPE_CODE_A? FLAG_A: FLAG_B);
			copy[tarPos] = repdata;
			
			tarPos += 1;
		}
		
		if(srcPos < data.length){
			copyLength = data.length - srcPos;
			System.arraycopy(data, srcPos, copy, tarPos, copyLength);
		}
		
		return copy;
	}
	
	/**
	 * 转义
	 */
	public static byte[] escape(byte[] data){
		List<Integer> positions = new ArrayList<Integer>();
		for(int i = 0; i < data.length; i++){
			if(data[i] == FLAG_A || data[i] == FLAG_B){
				positions.add(i);
			}
		}
		
		if(positions.size() == 0){
			return data;
		}
		
		byte[] copy = new byte[data.length + positions.size()];
		int srcPos = 0;
		int tarPos = 0;
		int copyLength;
		for(int i = 0; i < positions.size(); i++){
			int espos = positions.get(i);
			copyLength = espos - srcPos;
			System.arraycopy(data, srcPos, copy, tarPos, copyLength);
			
			srcPos = espos + 1;
			tarPos += copyLength;
			copy[tarPos] = FLAG_B;
			//copy[tarPos + 1] = (byte) (MASK_BYTE ^ data[espos]);
			byte esdata = data[espos];
			byte rep = (esdata == FLAG_A? ESCAPE_CODE_A: ESCAPE_CODE_B);
			copy[tarPos + 1] = rep;
			tarPos += 2;
		}
		
		if(srcPos < data.length){
			copyLength = data.length - srcPos;
			System.arraycopy(data, srcPos, copy, tarPos, copyLength);
		}
		
		return copy;
	}
	
	/**
	 * 获取指令sn
	 */
	public static byte getSn(UnitSocketSession unitSession){
		synchronized (unitSession) {
			Object obj = unitSession.getAttribute(KlxConstants.KLX_SESSION_KEY_SN);
			Byte v;
			if(obj == null){
				v = 1;
			}else{
				v = (Byte) obj;
				v++;
			}
			
			unitSession.setAttribute(KlxConstants.KLX_SESSION_KEY_SN, v);
			return v;
		}
	}
	
	/**
	 * 获取下发指令缓存sn
	 */
	public static String getCmdCacheSn(String callLetter, byte headSN){
		return callLetter + "_" + headSN;
	}
	
	public static byte[] getIdByCallLetter(String callLetter){
		byte[] call = Util.str2bcd("0" + callLetter);
		byte[] id = new byte[10];
		System.arraycopy(call, 0, id, 4, 6);
		
		return id;
	}
	
	public static byte[] getCommonResponseToUnit(UnitSocketSession session, short receivedCmdId, 
			byte receivedSn, byte success) throws Exception{
		KlxAck ack = new KlxAck();
		ack.setReceivedCmdId(receivedCmdId);
		ack.setReceivedSn(receivedSn);
		ack.setSuccess(success);
		
		byte[] response_body = ack.encode();
		byte[] response_id = KlxPkgUtil.getIdByCallLetter(session.getUnitInfo().getCallLetter());
		
		KlxPackage rpkg = new KlxPackage();
		rpkg.setBody(response_body);
		rpkg.setId(response_id);
		rpkg.setSn(KlxPkgUtil.getSn(session));
		
		byte[] source = rpkg.encode();
		
		return source;
	}
}