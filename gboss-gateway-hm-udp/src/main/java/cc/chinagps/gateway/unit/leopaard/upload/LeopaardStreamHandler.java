package cc.chinagps.gateway.unit.leopaard.upload;

import java.nio.ByteBuffer;

import cc.chinagps.gateway.stream.InputPackageHandler;
import cc.chinagps.gateway.stream.InputStreamController;
import cc.chinagps.gateway.stream.InputStreamDecoder;
import cc.chinagps.gateway.stream.decoders.LeopaardStreamDecoder;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.common.UnitCommon;
import cc.chinagps.gateway.unit.leopaard.define.LeopaardDefines;

public class LeopaardStreamHandler implements InputStreamController, InputPackageHandler {
	private InputStreamDecoder decoder = new LeopaardStreamDecoder(this, LeopaardDefines.HEAD_STX);
	private UnitServer server;
	private UnitSocketSession session;
	private LeopaardUploadCmdHandlers uploadHandlers = UnitCommon.leopaardUploadCmdHandlers;

	public LeopaardStreamHandler(UnitServer server, UnitSocketSession session) {
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