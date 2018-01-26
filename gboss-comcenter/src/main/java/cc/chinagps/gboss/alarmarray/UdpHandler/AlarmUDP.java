/*
********************************************************************************************
Discription: 通信中心分布式之间，采用zookeeper协调，
                   但是警情的生成、处理、结果如果用zookeeper协调，效率不高，并且很麻烦，所以用UDP广播通知（本来想用UDP组播，但netty组播不知怎么实现）  
                  另外次通信中心的座席信息，也要通过UDP通知主通信中心
			  
Written By:   ZXZ
Date:         2014-08-28
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.alarmarray.UdpHandler;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

public class AlarmUDP {
    private final int port;				//着实端口
    private final String multicastip;	//广播IP
    private Channel channel;
    //protected final Logger logger = Logger.getLogger(AlarmUDP.class.getName());

    public AlarmUDP(String multicastip, int port) {
        this.multicastip = multicastip;
    	this.port = port;
    }

    /*
     * 加入组播, netty 组播实验不成功
     */
    /*private void JoinGroup(NioDatagramChannel udpch) throws Exception {
        //String groupip = SystemConfig.getAlarmProperties("udpgroupip");
        //int groupport = Integer.valueOf(SystemConfig.getAlarmProperties("udpgroupport"));
    	//InetSocketAddress multicastAddress = new InetSocketAddress(groupip, groupport);
    	//NetworkInterface nwi = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
		//udpch.joinGroup(multicastAddress);	//, nwi);
    	byte[] addr = new byte[4];
    	addr[0] = (byte)(235 & 0xFF);
    	addr[1] = (byte)(167 & 0xFF);
    	addr[2] = (byte)(49 & 0xFF);
    	addr[3] = (byte)(80 & 0xFF);
    	InetAddress inetaddr = InetAddress.getByAddress(addr);
		udpch.joinGroup(inetaddr);
    	//Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
    	//while(e.hasMoreElements()) {
    	//	NetworkInterface item = e.nextElement();
    	//	if (item.isUp() && item.supportsMulticast()) {
        //		System.out.println(item.getName());
    	//		udpch.joinGroup(multicastAddress, item);
    	//	}
    	//}
    }*/
    
    /****************************************************************************************
     * UDP广播
     */
    public boolean multicast(String send) {
    	try {
	        InetSocketAddress address = new InetSocketAddress(multicastip, port);
	        ByteBuf sendbuf = Unpooled.copiedBuffer(send, CharsetUtil.UTF_8);
			//isSuccess()是异步操作，不会马上完成
			//while(!cf.isSuccess());
    		if (channel.isWritable()) {
    			channel.writeAndFlush(new DatagramPacket(sendbuf, address));
    			//cf.await(); //加了等待，有可能会死锁
    			return true;
    		}
        } catch(Exception e) {
        	System.out.println("UDP MultiCast Fail:");
        	e.printStackTrace();
        }
        return false;
    }
    
    public boolean multicast(byte[] send) {
    	try {
	        InetSocketAddress address = new InetSocketAddress(multicastip, port);
	        ByteBuf sendbuf = Unpooled.copiedBuffer(send);
			//isSuccess()是异步操作，不会马上完成
			//while(!cf.isSuccess());
    		if (channel.isWritable()) {
    			channel.writeAndFlush(new DatagramPacket(sendbuf, address));
    			//cf.await();//加了等待，有可能会死锁
    			return true;
    		}
        } catch(Exception e) {
        	System.out.println("UDP MultiCast Fail:");
        	e.printStackTrace();
        }
        return false;
    }

    public boolean multicast(ByteBuf send) {
    	//ByteBuf copy = send.copy();	//失败时，只能用copy做备份, 因为send的offset已经改变
    	try {
	        InetSocketAddress address = new InetSocketAddress(multicastip, port);
    		if (channel.isWritable()) {
    			channel.writeAndFlush(new DatagramPacket(send, address));
    			return true;
    		}
        } catch(Exception e) {
        	System.out.println("UDP MultiCast Fail:");
        	e.printStackTrace();
        }
        return false;
    }
    
    /****************************************************************************************
     * UDP初始化, 打开端口，开始侦听消息
     */
    public boolean start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group);
            b.channel(NioDatagramChannel.class);
            b.option(ChannelOption.SO_BROADCAST, true);		//设置没什么用途
            b.option(ChannelOption.SO_REUSEADDR, true);		//广播UDP地址可以重用，使得一台服务器上可以运行多个通信中心
            //b.option(ChannelOption.IP_MULTICAST_LOOP_DISABLED, true);	//自己不接收自己的广播
            b.handler(new AlarmUDPHandler());
            channel = b.bind(port).channel();	//.sync().closeFuture().await();
            return true;
            
            /**************************************************************************************************/
            //String groupip = SystemConfig.getAlarmProperties("udpgroupip");
            //int groupport = Integer.valueOf(SystemConfig.getAlarmProperties("udpgroupport"));
            //NetworkInterface nwi = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
        	//byte[] addr = new byte[4];
        	//addr[0] = (byte)(235 & 0xFF);
        	//addr[1] = (byte)(167 & 0xFF);
        	//addr[2] = (byte)(49 & 0xFF);
        	//addr[3] = (byte)(80 & 0xFF);
        	//InetAddress inetaddr = InetAddress.getByAddress(addr);
            //NetworkInterface nwi = NetworkInterface.getByInetAddress(inetaddr);
        	//b.option(ChannelOption.IP_MULTICAST_IF, nwi);
        	//b.option(ChannelOption.IP_MULTICAST_ADDR, inetaddr);

            
            //b.option(ChannelOption.IP_MULTICAST_IF, NetworkInterface.getByInetAddress(InetAddress.getLocalHost()));
        	//b.option(ChannelOption.IP_MULTICAST_ADDR, new InetSocketAddress(groupip, groupport).getAddress());
            //b.handler(new AlarmUDPHandler());
        	//b.option(ChannelOption.IP_MULTICAST_IF, NetworkInterface.getByInetAddress(InetAddress.getLocalHost()));
        	//b.option(ChannelOption.IP_MULTICAST_ADDR, new InetSocketAddress(groupip, groupport).getAddress());
            //Channel ch = b.bind(port).channel();	//.sync().closeFuture().await();
            //if (ch instanceof NioDatagramChannel) {
            //	JoinGroup((NioDatagramChannel)ch);
            //}
            /**************************************************************************************************/
        } catch(Exception e) {
        	e.printStackTrace();
            //group.shutdownGracefully();
        }
        return false;
    }

}
