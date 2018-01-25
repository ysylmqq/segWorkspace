package com.org.netty.heartbeat;

import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

/**
 * 自定义marshalling编解码器
 * @author ysy
 *
 */
public class MarshallingCodeCFactory {

	
	/**
	 * 解码器
	 * @return
	 */
	public static MarshallingDecoder  builderMarshallingDecoder(){
		//使用marshalling工具类，获取Marshalling序列化工厂
		final MarshallerFactory  marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
		final MarshallingConfiguration marshallingConfiguration = new MarshallingConfiguration();
		marshallingConfiguration.setVersion(5);
		UnmarshallerProvider unmarshallerProvider = new DefaultUnmarshallerProvider(marshallerFactory, marshallingConfiguration);
		//第二个参数是 序列化的大小 1M
		MarshallingDecoder marshallingDecoder = new MarshallingDecoder(unmarshallerProvider, 1024*1024);
		return 	marshallingDecoder;	
	}
	
	/**
	 * 编码器
	 * @return
	 */
	public static MarshallingEncoder builderMarshallingEncoder(){
		final MarshallerFactory  marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
		final MarshallingConfiguration marshallingConfiguration = new MarshallingConfiguration();
		marshallingConfiguration.setVersion(5);
		MarshallerProvider marshallerProvider = new DefaultMarshallerProvider(marshallerFactory, marshallingConfiguration);
		MarshallingEncoder marshallingEncoder = new MarshallingEncoder(marshallerProvider);
		return marshallingEncoder;
	}
}
