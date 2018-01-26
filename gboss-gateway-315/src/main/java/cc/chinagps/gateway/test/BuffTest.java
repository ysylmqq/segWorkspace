package cc.chinagps.gateway.test;

import java.util.List;

import com.google.protobuf.InvalidProtocolBufferException;

import cc.chinagps.gateway.buff.CommandBuff;
import cc.chinagps.gateway.buff.CommandBuff.Command;
import cc.chinagps.gateway.buff.CommandBuff.Command.Builder;
import cc.chinagps.gateway.buff.CommandBuff.CommandResponse;

public class BuffTest {
	public static void main(String[] args) {
		cc.chinagps.gateway.buff.CommandBuff.CommandResponse.Builder builder = CommandBuff.CommandResponse.newBuilder();
		builder.setSn("xxxxxx654321");
		builder.setCallLetter("13912345002");
		builder.setCmdId(456);
		builder.setResult(2);
		builder.addParams("xxx");
		builder.addParams("yyy");
		builder.addParams("zzz");
		
		//cc.chinagps.gateway.buff.GBossDataBuff.MapEntry.Builder m1 = cc.chinagps.gateway.buff.GBossDataBuff.MapEntry.newBuilder();
		//builder.addAppendParams(m1.setKey("key1").setValue("value1"));
		
		//cc.chinagps.gateway.buff.GBossDataBuff.MapEntry.Builder m2 = cc.chinagps.gateway.buff.GBossDataBuff.MapEntry.newBuilder();
		//builder.addAppendParams(m2.setKey("key2").setValue("value2"));
		
		CommandResponse res = builder.build();
		show(res);
		System.out.println("-----------------------");
		byte[] data = res.toByteArray();
		try {
			CommandResponse res2 = CommandBuff.CommandResponse.parseFrom(data);
			show(res2);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}
	
	public static void testCmd(){
		Builder builder = CommandBuff.Command.newBuilder();
		builder.setSn("000000123456");
		builder.addCallLetter("13912345001");
		builder.setCmdId(123);
		builder.addParams("aaa");
		builder.addParams("bbb");
		builder.addParams("ccc");
		
		Command cmd = builder.build();
		show(cmd);
		System.out.println("---------------------------");
		byte[] data = cmd.toByteArray();
		try {
			Command cmd2 = CommandBuff.Command.parseFrom(data);
			show(cmd2);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}
	
	private static void show(Command cmd){
		System.out.println("sn:" + cmd.getSn());
		System.out.println("callLetter:" + cmd.getCallLetterList());
		System.out.println("cmdId:" + cmd.getCmdId());
		System.out.println("params:" + cmd.getParamsCount());
		List<String> list = cmd.getParamsList();
		for(int i = 0; i < list.size(); i++){
			String param = list.get(i);
			System.out.println("[" + (i + 1) + "]:" + param);
		}
	}
	
	private static void show(CommandResponse cmd){
		System.out.println("sn:" + cmd.getSn());
		System.out.println("callLetter:" + cmd.getCallLetter());
		System.out.println("cmdId:" + cmd.getCmdId());
		System.out.println("result:" + cmd.getResult());
		System.out.println("params:" + cmd.getParamsCount());
		List<String> list = cmd.getParamsList();
		for(int i = 0; i < list.size(); i++){
			String param = list.get(i);
			System.out.println("[" + (i + 1) + "]:" + param);
		}
	}
}