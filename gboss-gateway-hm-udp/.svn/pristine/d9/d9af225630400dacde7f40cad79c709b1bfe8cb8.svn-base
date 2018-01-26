package cc.chinagps.gateway.unit.pengaoda.util;

import java.util.ArrayList;
import java.util.List;

import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.pengaoda.pkg.PengAoDaAck;

public class PengAoDaPkgUtil {
	public static final String EN_CHAR_ENCODING = "ascii";
	
	public static final byte START_FLAG = (byte) 0x7E;
	
	private static final byte FLAG_A = START_FLAG;
	private static final byte FLAG_B = 0x7D;

	private static final byte ESCAPE_CODE_A = 0x2;
	private static final byte ESCAPE_CODE_B = 0x1;
	
	private static boolean isEscapeCode(byte c){
		return (c == ESCAPE_CODE_A || c == ESCAPE_CODE_B);
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
	
	public static byte[] getCommonResponseToUnit(UnitSocketSession session, String terminalId, String param) throws Exception{
		PengAoDaAck ack = new PengAoDaAck();
		ack.setTerminalId(terminalId);
		ack.setParam(param);
		ack.setTime(PengAoDaTimeFormatUtil.getTime(System.currentTimeMillis()));
		
		return ack.encode();
	}
}