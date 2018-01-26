package cc.chinagps.gateway.unit.useat.upload;

import java.nio.ByteBuffer;

import javax.crypto.SecretKey;

import org.seg.lib.net.data.SegPackage;
import org.seg.lib.stream.PackageHandler;

import cc.chinagps.gateway.stream.InputStreamDecoder;
import cc.chinagps.gateway.stream.InputStreamController;
import cc.chinagps.gateway.stream.decoders.BaseStreamDecoder;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.useat.util.USeatUtil;

public class USeatStreamHandler implements InputStreamController, PackageHandler{
	private InputStreamDecoder decoder = new BaseStreamDecoder(this);
	
	private UnitSocketSession unitSocketSession;
	
	private UnitServer unitServer;
	
	public USeatStreamHandler(UnitServer unitServer, UnitSocketSession unitSocketSession){
		this.unitServer = unitServer;
		this.unitSocketSession = unitSocketSession;
	}
	
	private SecretKey desKey;
	
	public SecretKey getDesKey() {
		return desKey;
	}

	public void setDesKey(SecretKey desKey) {
		this.desKey = desKey;
	}

	@Override
	public void packageReceived(byte[] pkg) {
		try{
			SegPackage segpkg = SegPackage.parse(pkg, desKey);
			USeatUtil.segSeatPackageHandler.handlePackage(segpkg, unitServer, unitSocketSession, this);
		}catch (Throwable e) {
			unitServer.exceptionCaught(e);
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