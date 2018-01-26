package cc.chinagps.gateway.unit.pengaoda.upload;

import java.nio.ByteBuffer;

import cc.chinagps.gateway.stream.InputPackageHandler;
import cc.chinagps.gateway.stream.InputStreamController;
import cc.chinagps.gateway.stream.InputStreamDecoder;
import cc.chinagps.gateway.stream.decoders.FlagStreamDecoder;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.common.UnitCommon;
import cc.chinagps.gateway.unit.pengaoda.util.PengAoDaPkgUtil;

public class PengAoDaStreamHandler implements InputStreamController, InputPackageHandler{
	private InputStreamDecoder decoder = new FlagStreamDecoder(this, PengAoDaPkgUtil.START_FLAG, PengAoDaPkgUtil.START_FLAG);
	private UnitServer server;
	private UnitSocketSession session;
	
	private PengAoDaUploadCmdHandlers uploadHandlers = UnitCommon.pengAoDaUploadHandlers;
	
	public PengAoDaStreamHandler(UnitServer server, UnitSocketSession session){
		this.server = server;
		this.session = session;
	}
	
	@Override
	public void packageReceived(byte[] pkg) {
		try {
			uploadHandlers.handleUpload(pkg, server, session);
		} catch (Throwable e) {
			server.exceptionCaught(e, pkg, session);
		}
	}

	@Override
	public void dealData(ByteBuffer buff, int len) throws Exception {
		decoder.handleStream(buff, len);
	}

	@Override
	public byte[] getCachedData() {
		return decoder.getCachedData();
	}
}