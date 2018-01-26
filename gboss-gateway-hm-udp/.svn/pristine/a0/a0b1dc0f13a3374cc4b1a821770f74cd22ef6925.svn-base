package cc.chinagps.gateway.unit.beforemarket;

import java.io.IOException;
import java.nio.ByteBuffer;

import cc.chinagps.gateway.stream.InputStreamController;
import cc.chinagps.gateway.stream.decoders.transition.InnerTransitionDecoder;
import cc.chinagps.gateway.stream.decoders.transition.InnerTransitionPackageHandler;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.beforemarket.common.pkg.BeforeMarketPackage;
import cc.chinagps.gateway.unit.beforemarket.common.pkg.BeforeMarketPackageHead;
import cc.chinagps.gateway.unit.beforemarket.common.util.BeforeMarketConstants;
import cc.chinagps.gateway.unit.beforemarket.common.util.BeforeMarketPkgUtil;
import cc.chinagps.gateway.unit.beforemarket.hm.upload.HMStreamHandler;
import cc.chinagps.gateway.unit.beforemarket.kaiyi.upload.KaiyiStreamHandler;
import cc.chinagps.gateway.unit.beforemarket.yidongwang.upload.YdwStreamHandler;
import cc.chinagps.gateway.unit.common.UnitCommon;

public class BeforeMarketPackageMaker implements InputStreamController, InnerTransitionPackageHandler{
	private UnitServer server;
	private UnitSocketSession session;
	
	private InnerTransitionDecoder decoder = new InnerTransitionDecoder(this, BeforeMarketPkgUtil.START_FLAG, BeforeMarketPkgUtil.START_FLAG);
	
	public BeforeMarketPackageMaker(UnitServer server, UnitSocketSession session){
		this.server = server;
		this.session = session;
	}
	
	@Override
	public void dealData(ByteBuffer buff, int len) throws Exception {
		decoder.handleStream(buff, len);
	}

	@Override
	public  void firstPackageReceived(byte[] firstPKG, byte[] content) throws Exception {
		int c1 = BeforeMarketPkgUtil.getC1(session);
		int d1 = BeforeMarketPkgUtil.getD1(session);
		
		BeforeMarketPackage pkg = BeforeMarketPackage.parse(firstPKG, c1, d1);
		BeforeMarketPackageHead head = pkg.getHead();
		int terminalType = head.getTerminalType();
		switch(terminalType){
			case BeforeMarketConstants.PROTOCOL_TERMINAL_TYPE_HM:
				//海马
				InputStreamController pmHM = new HMStreamHandler(server, session);
				session.setPackageMaker(pmHM);
				session.setPackageEncoder(UnitCommon.hmDownloadHandlers);
				session.setProtocolType("hm");
				pmHM.dealData(ByteBuffer.wrap(content), content.length);
				break;
			case BeforeMarketConstants.PROTOCOL_TERMINAL_TYPE_YDW:
				//一动网
				InputStreamController pmYdw = new YdwStreamHandler(server, session);
				session.setPackageMaker(pmYdw);
				session.setPackageEncoder(UnitCommon.ydwDownloadHandlers);
				session.setProtocolType("ydw");
				pmYdw.dealData(ByteBuffer.wrap(content), content.length);
				break;
			case BeforeMarketConstants.PROTOCOL_TERMINAL_TYPE_KAIYI:
				//凯翼
				InputStreamController pmKaiyi = new KaiyiStreamHandler(server, session);
				session.setPackageMaker(pmKaiyi);
				session.setPackageEncoder(UnitCommon.kaiyiDownloadCmdEncoder);
				session.setProtocolType("kaiyi");
				pmKaiyi.dealData(ByteBuffer.wrap(content), content.length);
				break;
			default:
				//未知
				throw new IOException("(7D...7D)unknown terminalType type:" + terminalType);
		}
	}
	
	@Override
	public byte[] getCachedData() {
		return decoder.getCachedData();
	}
}