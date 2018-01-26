package cc.chinagps.gateway.stream.decoders;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.seg.lib.util.Util;

import cc.chinagps.gateway.exceptions.WrongFormatException;
import cc.chinagps.gateway.stream.InputPackageHandler;
import cc.chinagps.gateway.stream.InputStreamDecoder;
import cc.chinagps.gateway.unit.leopaard.define.LeopaardDefines;

/**
 * 
 */
public class LeopaardStreamDecoder implements InputStreamDecoder {

	private static final int HEAD_SIZE = LeopaardDefines.HEAD_SIZE;
	private List<byte[]> headByteList = new ArrayList<byte[]>();
	private List<byte[]> bodyByteList = new ArrayList<byte[]>();
	private int headNeed = HEAD_SIZE;
	private int bodyNeed = 0;
	private InputPackageHandler handler;
	private byte[] startFlag;
	private boolean headFinded = false;
	private int tempTotalSize;

	public LeopaardStreamDecoder(InputPackageHandler handler, byte[] startFlag) {
		this.handler = handler;
		this.startFlag = startFlag;
	}

	@Override
	public void handleStream(ByteBuffer buff, int len) throws Exception {
		// TODO Auto-generated method stub
		int leftBytes;
		byte[] temp;
		while ((leftBytes = buff.limit() - buff.position()) > 0) {
			if (headNeed == 0 && bodyNeed == 0) {
				reset();
				throw new IOException("###error:0 0  left:" + leftBytes);
			}
			if (headNeed > 0) {
				if (leftBytes < headNeed) {
					temp = new byte[leftBytes];
					buff.get(temp);
					headByteList.add(temp);
					headNeed = headNeed - leftBytes;
					break;
				} else {
					temp = new byte[headNeed];
					buff.get(temp);
					headByteList.add(temp);
					byte[] head = getHeadBytes();
					checkHeadStx(head);
					if (!headFinded) {
						reset();
						throw new WrongFormatException("[Leopaard]wrong stx");
					}
					headNeed = 0;
					bodyNeed = calBodyLen(head) + 1;
					if (bodyNeed > 65535) {
						reset();
						throw new WrongFormatException("[Leopaard]big package,size:" + bodyNeed);
					}
				}
			}
			leftBytes = buff.limit() - buff.position();
			if (leftBytes == 0)
				continue;
			if (bodyNeed > 0) {
				if (leftBytes < bodyNeed) {
					temp = new byte[leftBytes];
					buff.get(temp);
					bodyByteList.add(temp);
					bodyNeed = bodyNeed - leftBytes;
					break;
				} else {
					temp = new byte[bodyNeed];
					buff.get(temp);
					bodyByteList.add(temp);
					bodyNeed = 0;
					dealPackage();
				}
			}
		}
	}

	private boolean checkHeadStx(byte[] head) {
		if (head[0] == startFlag[0] && head[1] == (byte) startFlag[1]) {
			headFinded = true;
			return true;
		}

		return false;
	}

	private byte[] getHeadBytes() {
		int len = 0;
		for (int i = 0; i < headByteList.size(); i++) {
			len += headByteList.get(i).length;
		}
		byte[] data = new byte[len];
		int position = 0;
		for (int i = 0; i < headByteList.size(); i++) {
			byte[] temp = headByteList.get(i);
			System.arraycopy(temp, 0, data, position, temp.length);
			position += temp.length;
		}
		return data;
	}

	private void dealPackage() throws Exception {
		try {
			ByteBuffer buff = ByteBuffer.allocate(tempTotalSize);
			for (int i = 0; i < headByteList.size(); i++) {
				buff.put(headByteList.get(i));
			}
			for (int i = 0; i < bodyByteList.size(); i++) {
				buff.put(bodyByteList.get(i));
			}
			handler.packageReceived(buff.array());

		} finally {
			reset();
		}
	}

	private void reset() {
		headByteList.clear();
		bodyByteList.clear();
		headNeed = HEAD_SIZE;
		bodyNeed = 0;
		headFinded = false;
	}

	private int calBodyLen(byte[] head) {
		byte[] bodyLenBytes = new byte[2];
		System.arraycopy(head, LeopaardDefines.HEAD_SIZE - 2, bodyLenBytes, 0, bodyLenBytes.length);
		int bodyLen = Util.getShort(bodyLenBytes);
		tempTotalSize = bodyLen + HEAD_SIZE + 1;
		return bodyLen;
	}

	@Override
	public byte[] getCachedData() {
		// TODO Auto-generated method stub
		int len = 0;
		for (int i = 0; i < headByteList.size(); i++) {
			len += headByteList.get(i).length;
		}

		for (int i = 0; i < bodyByteList.size(); i++) {
			len += bodyByteList.get(i).length;
		}

		byte[] data = new byte[len];
		int position = 0;
		for (int i = 0; i < headByteList.size(); i++) {
			byte[] temp = headByteList.get(i);
			System.arraycopy(temp, 0, data, position, temp.length);
			position += temp.length;
		}

		for (int i = 0; i < bodyByteList.size(); i++) {
			byte[] temp = bodyByteList.get(i);
			System.arraycopy(temp, 0, data, position, temp.length);
			position += temp.length;
		}

		return data;
	}

}
