package cc.chinagps.gateway.unit.mult;

import java.io.IOException;
import java.nio.ByteBuffer;

import cc.chinagps.gateway.stream.InputStreamController;
import cc.chinagps.gateway.stream.decoders.transition.FirstDataTransitionDecoder;
import cc.chinagps.gateway.stream.decoders.transition.FirstDataTransitionPackageHandler;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.common.UnitCommon;
import cc.chinagps.gateway.unit.db44.upload.DB44StreamHandler;
import cc.chinagps.gateway.unit.pengaoda.upload.PengAoDaStreamHandler;

public class OX7EPackageMaker implements InputStreamController, FirstDataTransitionPackageHandler{
	private UnitServer server;
	private UnitSocketSession session;
	
	private FirstDataTransitionDecoder decoder = new FirstDataTransitionDecoder(this, 2);
	
	public OX7EPackageMaker(UnitServer server, UnitSocketSession session){
		this.server = server;
		this.session = session;
	}

	@Override
	public void firstPackageReceived(byte[] content) throws Exception {
		int flag = content[1] & 0xFF;
		switch (flag) {
			case 0x52:
				//DB44
				InputStreamController pm = new DB44StreamHandler(server, session);
				session.setPackageMaker(pm);
				session.setPackageEncoder(UnitCommon.db44DownloadCmdEncoder);
				session.setProtocolType("db44");
				pm.dealData(ByteBuffer.wrap(content), content.length);
				break;
			case 0x2A:
				//鹏奥达
				InputStreamController pm_2a = new PengAoDaStreamHandler(server, session);
				session.setPackageMaker(pm_2a);
				//无下行
				session.setPackageEncoder(UnitCommon.pengAoDaDownloadCmdEncoder);
				session.setProtocolType("pengAoDa");
				pm_2a.dealData(ByteBuffer.wrap(content), content.length);
				break;
			case 0x01:
				//部标
				throw new IOException("not support bubiao yet!");
			default:
				break;
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