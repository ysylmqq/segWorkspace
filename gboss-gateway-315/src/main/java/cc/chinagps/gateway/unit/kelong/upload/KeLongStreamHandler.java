package cc.chinagps.gateway.unit.kelong.upload;

import java.nio.ByteBuffer;

import cc.chinagps.gateway.stream.InputPackageHandler;
import cc.chinagps.gateway.stream.InputStreamController;
import cc.chinagps.gateway.stream.InputStreamDecoder;
import cc.chinagps.gateway.stream.decoders.KeLongStreamDecoder;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.common.UnitCommon;
import cc.chinagps.gateway.unit.kelong.define.KeLongDefines;

public class KeLongStreamHandler implements InputStreamController, InputPackageHandler {
	private InputStreamDecoder decoder = new KeLongStreamDecoder(this, KeLongDefines.HEAD_STX);
	private UnitServer server;
	private UnitSocketSession session;
	private KeLongUploadCmdHandlers uploadHandlers = UnitCommon.keLongUploadCmdHandlers;

	public KeLongStreamHandler(UnitServer server, UnitSocketSession session) {
		this.server = server;
		this.session = session;
	}

	@Override
	public void dealData(ByteBuffer buff, int len) throws Exception {
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