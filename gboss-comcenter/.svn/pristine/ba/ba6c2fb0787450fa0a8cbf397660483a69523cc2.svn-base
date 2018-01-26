package testtools;

import java.net.URI;

import cc.chinagps.interprotocol.InterProtocolTimeoutHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;

public class WebSocketClient {
	private URI uri;
	private WebSocketClientHandler handler = null;
    private WebSocketClientHandshaker handshaker = null;

    public WebSocketClient(URI uri, long startcallletter, int calllettercount, int seatindex) throws Exception {
    	this.uri = uri;
   		this.handshaker = WebSocketClientHandshakerFactory.newHandshaker(
   				this.uri, WebSocketVersion.V13, null, false, new DefaultHttpHeaders());
   		this.handler = new WebSocketClientHandler(handshaker);
   		this.handler.SetCallerInfo(startcallletter, calllettercount, seatindex);
    }
    
    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        // Normal WebSocket
        ChannelInitializer<SocketChannel> initializer;
        initializer = new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline()
                      .addLast("readTimeoutHandler", new InterProtocolTimeoutHandler(5))
                      .addLast("http-codec", new HttpClientCodec())
                      .addLast("aggregator", new HttpObjectAggregator(1048576))
                      .addLast("ws-handler", handler);
                }
        };
        Bootstrap b = new Bootstrap();
        b.group(group)
         .channel(NioSocketChannel.class)
         .handler(initializer);
        int port = uri.getPort();
        if (uri.getPort() == -1) {
        	port = 80;
        }
        //Channel ch = b.connect(uri.getHost(), port).sync().channel();
        b.connect(uri.getHost(), port).sync().channel();
        handler.handshakeFuture().sync();
    }
}
