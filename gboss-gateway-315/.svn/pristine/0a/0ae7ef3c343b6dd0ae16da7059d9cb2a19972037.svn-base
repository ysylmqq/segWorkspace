package cc.chinagps.gateway.unit.seg.upload.cmds;

import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.buff.CommandBuff.CommandResponse.Builder;
import cc.chinagps.gateway.seat.cmd.CmdResponseUtil;
import cc.chinagps.gateway.seat.cmd.CmdUtil;
import cc.chinagps.gateway.seat.cmd.ServerToUnitCommand;
import cc.chinagps.gateway.unit.UnitServer;
import cc.chinagps.gateway.unit.UnitSocketSession;
import cc.chinagps.gateway.unit.seg.pkg.SegPackage;
import cc.chinagps.gateway.unit.seg.util.SegLatLngUtil;
import cc.chinagps.gateway.unit.seg.util.SegPkgUtil;

/**
 * TN功能协议(安防导航产品协议)应答
 */
public class Cmd68 extends CheckLoginHandler{
	@Override
	public boolean handlerPackageSessionChecked(SegPackage pkg, UnitServer server,
			UnitSocketSession session) throws Exception {
		byte[] body = pkg.getBody();
		String strBody = new String(body, SegPkgUtil.PKG_CHAR_ENCODING);
		
		if(strBody.startsWith("(TNA,001,")){
			//TN功能协议(安防导航产品协议)应答
			handlerTNAck(pkg, server, session, strBody);
			return true;
		}
		
		return false;
	}
	
	/**
	 * TN功能协议(安防导航产品协议)应答
	 */
	public static void handlerTNAck(SegPackage pkg, UnitServer server,
			UnitSocketSession session, String strBody){
		String callLetter = session.getUnitInfo().getCallLetter();
		String sn = CmdUtil.getCmdCacheSn(callLetter, CmdUtil.CMD_ID_SET_TARGET_QUERY_POINT);
		ServerToUnitCommand cache = server.getServerToUnitCommandCache().getAndRemoveCommand(sn);
		if(cache != null) {
			Command usercmd = cache.getUserCommand();
			Builder builder = CommandBuff.CommandResponse.newBuilder();
			//设置回应的通用部分
			CmdResponseUtil.updateResponseProtoSuccessByUserCommand(builder, callLetter, usercmd);
			
			String[] params = strBody.substring(9, strBody.length() - 1).split(",");
			//搜索方式
			builder.addParams(params[0]);
			//目的点纬度
			builder.addParams(String.valueOf(SegLatLngUtil.unitLatToUserLat(params[1])));
			//目的点经度
			builder.addParams(String.valueOf(SegLatLngUtil.unitLngToUserLng(params[2])));
			
			if(params.length > 3){
				//has b or c
				String borc = params[3];
				if(borc.startsWith("B")){
					int b_count = Integer.valueOf(borc.substring(1));
					StringBuilder bstr = new StringBuilder();
					for(int i = 0; i < b_count; i++){
						bstr.append(SegLatLngUtil.unitLatToUserLat(params[4 + 2 * i]));
						bstr.append(",");
						bstr.append(SegLatLngUtil.unitLngToUserLng(params[5 + 2 * i]));
						bstr.append(";");
					}
					
					if(bstr.length() > 0){
						bstr.deleteCharAt(bstr.length() - 1);
					}
					
					builder.addParams(bstr.toString());
					
					//CZ
					//if(logger_debug.isDebugEnabled()){
					//	logger_debug.debug("params.length:" + params.length + ",(4 + 2 * b_count):" + (4 + 2 * b_count));
					//}
					
					if(params.length > 4 + 2 * b_count){
						String cz = params[4 + 2 * b_count];
						if(cz.startsWith("C")){
							StringBuilder cstr = handlerCZ(params, 4 + 2 * b_count);
							builder.addParams(cstr.toString());
						}else{
							//if(logger_debug.isDebugEnabled()){
							//	logger_debug.debug("cz error, strBody:" + strBody);
							//}
							
							builder.addParams("");
						}
					}else{
						//no c
						builder.addParams("");
					}
				}else if(borc.startsWith("C")){
					//no b
					builder.addParams("");
					StringBuilder cstr = handlerCZ(params, 3);
					builder.addParams(cstr.toString());
				}else{
					//if(logger_debug.isDebugEnabled()){
					//	logger_debug.debug("borc error, strBody:" + strBody);
					//}
					
					builder.addParams("");
					builder.addParams("");
				}
			}else{
				//no b and no c
				builder.addParams("");
				builder.addParams("");
			}
			
			//byte[] data = builder.build().toByteArray();
			CmdResponseUtil.simpleCommandResponse(cache, builder);
		}
	}
	
	private static StringBuilder handlerCZ(String[] params, int start){
		String cz = params[start];
		int count = Integer.valueOf(cz.substring(1));
		StringBuilder cstr = new StringBuilder();
		for(int i = 0; i < count; i++){
			cstr.append(SegLatLngUtil.unitLatToUserLat(params[start + 1 + 2 * i]));
			cstr.append(",");
			cstr.append(SegLatLngUtil.unitLngToUserLng(params[start + 2 + 2 * i]));
			cstr.append(";");
		}
		
		if(cstr.length() > 0){
			cstr.deleteCharAt(cstr.length() - 1);
		}
		
		return cstr;
	}
	
	public static void main(String[] args) {
		//String strBody = "(TNA,001,0,2312.3456N,12312.3456E,B1,2412.3456N,12412.3456E,C1,2512.3456N,12512.3456E)";
		//String strBody = "(TNA,001,0,2312.3456N,12312.3456E,B1,2412.3456N,12412.3456E)";
		//String strBody = "(TNA,001,0,2312.3456N,12312.3456E,C1,2512.3456N,12512.3456E)";
		String strBody = "(TNA,001,0,2312.3456N,12312.3456E)";
		
		//String strBody = "(TNA,001,0,2312.3456N,12312.3456E,B3,2412.3456N,12412.3456E,2612.3456N,12612.3456E,2812.3456N,12812.3456E,C2,2512.3456N,12512.3456E,2712.3456N,12712.3456E)";
		//String strBody = "(TNA,001,0,2312.3456N,12312.3456E,B3,2412.3456N,12412.3456E,2612.3456N,12612.3456E,2812.3456N,12812.3456E)";
		//String strBody = "(TNA,001,0,2312.3456N,12312.3456E,C2,2512.3456N,12512.3456E,2712.3456N,12712.3456E)";
		
		byte[] body = strBody.getBytes();
		SegPackage pkg = new SegPackage();
		pkg.setBody(body);
		
		Cmd68 cmd68 = new Cmd68();
		try {
			cmd68.handlerPackageSessionChecked(pkg, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}