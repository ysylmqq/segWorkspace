package cc.chinagps.gateway.unit.seg.upload;

import java.nio.ByteBuffer;

import cc.chinagps.gateway.stream.InputStreamDecoder;
import cc.chinagps.gateway.stream.InputPackageHandler;
import cc.chinagps.gateway.stream.InputStreamController;
import cc.chinagps.gateway.stream.decoders.SegFlagStreamDecoder;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.common.UnitCommon;
import cc.chinagps.gateway.unit.seg.util.SegPkgUtil;

/**
 * 赛格车台协议处理
 * @author Arvin
 * 2013-5
 */
public class SegStreamHandler implements InputStreamController, InputPackageHandler{
	private InputStreamDecoder decoder = new SegFlagStreamDecoder(this, SegPkgUtil.START_FLAG, SegPkgUtil.END_FLAG);
	private UnitServer server;
	private UnitSocketSession session;
	private SegUploadCmdHandlers uploadHandlers = UnitCommon.segUploadHandlers;
	
	public SegStreamHandler(UnitServer server, UnitSocketSession session){
		this.server = server;
		this.session = session;
	}
	
	@Override
	public void dealData(ByteBuffer buff, int len) throws Exception{
		decoder.handleStream(buff, len);
	}

	@Override
	public void packageReceived(byte[] source) {
		try {
			uploadHandlers.handleUpload(source, server, session);
		} catch (Throwable e) {
			server.exceptionCaught(e, source, session);
		}
	}

	@Override
	public byte[] getCachedData() {
		return decoder.getCachedData();
	}
}