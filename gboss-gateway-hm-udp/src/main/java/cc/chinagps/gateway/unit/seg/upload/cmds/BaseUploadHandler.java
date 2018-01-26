package cc.chinagps.gateway.unit.seg.upload.cmds;

import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.seg.pkg.SegPackage;
import cc.chinagps.gateway.unit.seg.upload.UploadHandler;

/**
 * 报文为完整报文格式，先统一转为SegPackage对象，便于后续处理
 * (短链路链接不属于完整报文格式, 需要检查车台登录的使用CheckLoginHandler)
 */
public abstract class BaseUploadHandler implements UploadHandler{
	@Override
	public boolean handlerPackage(byte[] source, UnitServer server,
			UnitSocketSession session) throws Exception {
		SegPackage p = SegPackage.parse(source);
		return handlerPackage(p, server, session);
	}
	
	public abstract boolean handlerPackage(SegPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception;
}