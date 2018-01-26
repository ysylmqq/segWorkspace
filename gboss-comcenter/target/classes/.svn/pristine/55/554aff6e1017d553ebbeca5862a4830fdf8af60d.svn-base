/*
********************************************************************************************
Discription:  模拟手机APP客户端，用websocket连接通信中心
			  
Written By:   ZXZ
Date:         2014-08-19
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/

package testtools;

import java.net.URI;

public final class WebSocketClientManager {

    private final URI uri;
    private WebSocketClient[] clientlist = null; 

    public WebSocketClientManager(String host, int clientcount) throws Exception {
        this.uri = new URI("ws://" + host + "/websocket");
        if (clientcount <= 0 || clientcount > 8192) {
        	clientcount = 128;
        }
        clientlist = new WebSocketClient[clientcount];
    }

    public void run(long startcallletter, int calllettercount) throws Exception {
        try {
            final String protocol = uri.getScheme();
            if (!"ws".equals(protocol)) {
                System.out.println("Unsupported protocol: " + protocol);
                return;
            }
            for(int i=0; i<clientlist.length; i++) {
            	clientlist[i] = new WebSocketClient(uri, startcallletter, calllettercount, i);
            	clientlist[i].run();
            }
            /*
            handler = new WebSocketClientHandler(handshaker);
            // Normal WebSocket
            ChannelInitializer<SocketChannel> initializer;
            initializer = new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline()
                      .addLast("readTimeoutHandler", new InterProtocolTimeoutHandler(10))
                      .addLast("http-codec", new HttpClientCodec())
                      .addLast("aggregator", new HttpObjectAggregator(1048576))
                      .addLast("ws-handler", handler);
                }
            };
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .handler(initializer);

            //Channel ch = b.connect(uri.getHost(), port).sync().channel();
            b.connect(uri.getHost(), port).sync().channel();
            
            handler.handshakeFuture().sync();
            */

        } finally {
            //group.shutdownGracefully();
        }
    }
    
    public void Stop() {
    	
    }
    
}
