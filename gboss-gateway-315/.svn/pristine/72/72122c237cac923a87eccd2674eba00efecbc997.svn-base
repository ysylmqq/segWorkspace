package cc.chinagps.gateway.stream.decoders;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.seg.lib.net.Constant;
import org.seg.lib.stream.PackageHandler;
import org.seg.lib.util.Util;

import cc.chinagps.gateway.stream.InputStreamDecoder;
import cc.chinagps.gateway.util.HexUtil;

public class BaseStreamDecoder implements InputStreamDecoder {
	private PackageHandler handler;

	private static final int HEAD_SIZE = 16;

	private List<byte[]> headByteList = new ArrayList<byte[]>();
	private List<byte[]> bodyByteList = new ArrayList<byte[]>();
	private int headNeed = HEAD_SIZE;
	private int bodyNeed = 0;

	public BaseStreamDecoder(PackageHandler handler) {
		this.handler = handler;
	}

	private void reset() {
		headByteList.clear();
		bodyByteList.clear();
		headNeed = HEAD_SIZE;
		bodyNeed = 0;
	}

	private int tempTotalSize;

	private int calTotalSize() {
		ByteBuffer buff = ByteBuffer.allocate(HEAD_SIZE);
		for (byte[] b : headByteList) {
			buff.put(b);
		}

		int totalSize = Util.getInt(buff.array(), 12);
		tempTotalSize = totalSize;
		return totalSize;
	}

	private void dealPackage() throws Exception {
		try {
			// checkSize();
			ByteBuffer buff = ByteBuffer.allocate(tempTotalSize);
			for (byte[] b : headByteList) {
				buff.put(b);
			}

			for (byte[] b : bodyByteList) {
				buff.put(b);
			}

			handler.packageReceived(buff.array());
		} finally {
			reset();
		}
	}

	@Override
	public void handleStream(ByteBuffer buff, int len) throws Exception {
		int leftBytes;
		while ((leftBytes = (buff.limit() - buff.position())) > 0) {
			if (headNeed == 0 && bodyNeed == 0) {
				reset();
				throw new IOException("###error:0 0  left:" + leftBytes);
			}

			if (headNeed > 0) {
				// System.out.println("headNeed:" + headNeed);
				if (leftBytes < headNeed) {
					byte[] temp = new byte[leftBytes];
					buff.get(temp);

					// check stx
					if (headNeed == HEAD_SIZE) {
						if (temp[0] != Constant.STX) {
							throw new IOException("stx is wrong:" + temp[0]);
						}
					}// check over

					headByteList.add(temp);
					headNeed -= leftBytes;
					break;
				} else {
					byte[] temp = new byte[headNeed];
					buff.get(temp);
					// check stx
					if (headNeed == HEAD_SIZE) {
						if (temp[0] != Constant.STX) {
							throw new IOException("stx is wrong:" + temp[0]);
						}
					}

					headByteList.add(temp);
					headNeed = 0;
					int totalSize = calTotalSize();
					if (totalSize < HEAD_SIZE || totalSize > 16777216) {
						reset();
						throw new IOException("###large package, length:"
								+ totalSize);
					}
					bodyNeed = totalSize - HEAD_SIZE;
					if (bodyNeed == 0) {
						dealPackage();
					}
				}
			}

			leftBytes = buff.limit() - buff.position();
			if (leftBytes == 0) {
				continue;
			}
			if (bodyNeed > 0) {
				if (leftBytes < bodyNeed) {
					byte[] temp = new byte[leftBytes];
					buff.get(temp);
					bodyByteList.add(temp);
					bodyNeed -= leftBytes;
					break;
				} else {
					byte[] temp = new byte[bodyNeed];
					buff.get(temp);
					bodyByteList.add(temp);
					bodyNeed = 0;
					dealPackage();
				}
			}
		}
	}

	@Override
	public byte[] getCachedData() {
		int len = 0;
		for(int i = 0; i < headByteList.size(); i++){
			len += headByteList.get(i).length;
		}

		for(int i = 0; i < bodyByteList.size(); i++){
			len += bodyByteList.get(i).length;
		}
		
		byte[] data = new byte[len];
		int position = 0;
		for(int i = 0; i < headByteList.size(); i++){
			byte[] temp = headByteList.get(i);
			System.arraycopy(temp, 0, data, position, temp.length);
			position += temp.length;
		}
		
		for(int i = 0; i < bodyByteList.size(); i++){
			byte[] temp = bodyByteList.get(i);
			System.arraycopy(temp, 0, data, position, temp.length);
			position += temp.length;
		}
		
		return data;
	}
	
	public static void main(String[] args) {
		BaseStreamDecoder d = new BaseStreamDecoder(null);
		byte[] h1 = {1};
		byte[] h2 = {2, 3};
		byte[] h3 = {4, 5, 6};
		d.headByteList.add(h1);
		d.headByteList.add(h2);
		d.headByteList.add(h3);
		
		byte[] b1 = {0x11};
		byte[] b2 = {0x12, 0x13};
		byte[] b3 = {0x14, 0x15, 0x16};
		d.bodyByteList.add(b1);
		d.bodyByteList.add(b2);
		d.bodyByteList.add(b3);
		
		byte[] c = d.getCachedData();
		System.out.println(c.length);
		String hex = HexUtil.byteToHexStr(c);
		System.out.println("hex:" + hex);
	}
}